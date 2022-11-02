package by.nahorny.library.service;

import by.nahorny.library.dao.BookEditionRepository;
import by.nahorny.library.dao.WritingInLibraryCardRepository;
import by.nahorny.library.dto.EditionNameDto;
import by.nahorny.library.entity.printEdition.BookEdition;
import by.nahorny.library.entity.printEdition.PrintEdition;
import by.nahorny.library.enums.EditionType;
import by.nahorny.library.exeptions.PrintEditionDasNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
public class BookEditionService {

    @Autowired
    private BookEditionRepository bookEditionRepository;

    @Autowired
    private WritingInLibraryCardRepository writingInLibraryCardRepository;

    @Cacheable("bookEditions")
    public BookEdition findBookEditionById(long id) throws PrintEditionDasNotExistException {
        return bookEditionRepository.findById(id).orElseThrow(PrintEditionDasNotExistException::new);
    }

    public void save(BookEdition bookEdition) {
        bookEditionRepository.save(bookEdition);
    }

    @Transactional
    public void addNewPartEdition(BookEdition bookEdition) {
        if (bookEdition == null) return;
        if (bookEditionRepository.
                existsBookEditionByAuthorAndNameAndPublishingHouseAndYearOfPublicationAndEditionNumber
                        (bookEdition.getAuthor(), bookEdition.getName(), bookEdition.getPublishingHouse(),
                                bookEdition.getYearOfPublication(), bookEdition.getEditionNumber())) {
            BookEdition bookEditionFromBaze =
                    bookEditionRepository.findByAuthorAndNameAndPublishingHouseAndYearOfPublicationAndEditionNumber
                            (bookEdition.getAuthor(), bookEdition.getName(), bookEdition.getPublishingHouse(),
                                    bookEdition.getYearOfPublication(), bookEdition.getEditionNumber());
            bookEditionFromBaze.setCountInLibrary((short) (bookEdition.getGeneralCount() + bookEditionFromBaze.getCountInLibrary()));
            bookEditionRepository.save(bookEditionFromBaze);
        } else {
            bookEditionRepository.save(bookEdition);
        }
    }

    @Transactional
    public int subtractEdition(BookEdition bookEdition) {
        if (bookEdition == null) return 0;
        if (bookEditionRepository.
                existsBookEditionByAuthorAndNameAndPublishingHouseAndYearOfPublicationAndEditionNumber
                        (bookEdition.getAuthor(), bookEdition.getName(), bookEdition.getPublishingHouse(),
                                bookEdition.getYearOfPublication(), bookEdition.getEditionNumber())) {
            BookEdition bookEditionFromBaze =
                    bookEditionRepository.findByAuthorAndNameAndPublishingHouseAndYearOfPublicationAndEditionNumber
                            (bookEdition.getAuthor(), bookEdition.getName(), bookEdition.getPublishingHouse(),
                                    bookEdition.getYearOfPublication(), bookEdition.getEditionNumber());
            short newCountInLibrary = (short) (bookEditionFromBaze.getCountInLibrary() - bookEdition.getGeneralCount());
            if (newCountInLibrary < 0) return -1;
            if (newCountInLibrary == 0 && bookEditionFromBaze.getCountReadersHave() == 0)
                bookEditionRepository.deleteById(bookEditionFromBaze.getId());
            else {
                bookEditionFromBaze.setCountInLibrary(newCountInLibrary);
                bookEditionRepository.save(bookEditionFromBaze);
            }
            return 1;
        } else {
            return 2;
        }
    }

    @Transactional
    public List<BookEdition> findBookEditionByName(String name) {
        return bookEditionRepository.findBookEditionByName(name);
    }

    @Transactional
    public List<BookEdition> findBookEditionByAuthor(String author) {
        return bookEditionRepository.findBookEditionByAuthor(author);
    }

    @Transactional
    public boolean correctCountInLibraryBookEditionById(int id, int count) {
        if (bookEditionRepository.existsById((long) id)) {
            bookEditionRepository.correctCountInLibraryBookEditionById(id, count);
            int countReadershave = bookEditionRepository
                    .findCountBookEditionReadersHaveById(id);
            int newGeneralCount = countReadershave + count;
            bookEditionRepository.correctGeneralCountBookEditionById(id, newGeneralCount);
            return true;
        } else return false;
    }

    @Transactional
    @CacheEvict("users")
    public boolean deleteBookEditionById(long id) throws PrintEditionDasNotExistException {
        if (!bookEditionRepository.existsById(id)) throw new PrintEditionDasNotExistException();
        if (!writingInLibraryCardRepository.existsByPrintEditionIdAndEditionType
                (id, EditionType.BOOK_EDITION)) {
            bookEditionRepository.deleteById(id);
            return true;
        } else return false;
    }

    public List<PrintEdition> findByExpressionInBookEditionName(String expression) throws PrintEditionDasNotExistException {
        List<EditionNameDto> listEditionNameDto = bookEditionRepository.findAllName();
        List<PrintEdition> printEditions = new ArrayList<>();
        for (EditionNameDto editionNameDto : listEditionNameDto)
            if (editionNameDto.getName().contains(expression)) {
                printEditions.add(findBookEditionById(editionNameDto.getId()));
            }
        return printEditions;
    }
}
