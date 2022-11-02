package by.nahorny.library.service;

import by.nahorny.library.dao.ReaderRepository;
import by.nahorny.library.entity.printEdition.PrintEdition;
import by.nahorny.library.entity.WritingInLibraryCard;
import by.nahorny.library.entity.person.Reader;
import by.nahorny.library.exeptions.NotSuchCountEditionException;
import by.nahorny.library.exeptions.PrintEditionDasNotExistException;
import by.nahorny.library.exeptions.ReaderDasNotExistException;
import by.nahorny.library.exeptions.UnknownEditionTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Component
public class ReaderService {

    final private static String NOT_HAVE_EDITION = "Reader does not have the edition";
    final private static String NOT_HAVE_SUCH_COUNT_IN_LIBRARY = "according to the library," +
            " users have fewer books than expected to be returned";


    @Autowired
    ReaderRepository readerRepository;

    @Autowired
    PrintEditionService printEditionService;

    @Cacheable("readers")
    public Reader findReaderById(long id) throws ReaderDasNotExistException {
        return readerRepository.findById(id).orElseThrow(ReaderDasNotExistException::new);
    }

    private void addOrUpdateWritingInLibraryCard(Reader reader, WritingInLibraryCard writingInLibraryCard) {
        List<WritingInLibraryCard> libraryCard = reader.getLibraryCard();
        if (libraryCard.contains(writingInLibraryCard)) {
            for (WritingInLibraryCard writingFromCard : libraryCard) {
                if (writingInLibraryCard.getPrintEditionId() == writingFromCard.getPrintEditionId() &&
                        writingInLibraryCard.getEditionType() == writingFromCard.getEditionType()) {
                    writingFromCard.setCountEdition
                            ((short) (writingFromCard.getCountEdition() + writingInLibraryCard.getCountEdition()));
                    break;
                }
            }
        } else libraryCard.add(writingInLibraryCard);
    }

    private void subtractCountEditionInLibraryCard(Reader reader, WritingInLibraryCard writingInLibraryCard)
            throws PrintEditionDasNotExistException {
        List<WritingInLibraryCard> libraryCard = reader.getLibraryCard();
        if (libraryCard.contains(writingInLibraryCard)) {
            for (int i = 0; i <= libraryCard.size(); i++) {
                if (writingInLibraryCard.getPrintEditionId() == libraryCard.get(i).getPrintEditionId() &&
                        writingInLibraryCard.getEditionType() == libraryCard.get(i).getEditionType()) {
                    short resultCount = (short) (libraryCard.get(i).getCountEdition() - writingInLibraryCard.getCountEdition());
                    if (resultCount == 0) libraryCard.remove(i);
                    else
                        libraryCard.get(i).setCountEdition
                                (resultCount);
                    break;
                }
            }
        } else throw new PrintEditionDasNotExistException(NOT_HAVE_EDITION);
    }


    @Transactional
    public void lendEditionToReader(WritingInLibraryCard writingInLibraryCard)
            throws ReaderDasNotExistException, PrintEditionDasNotExistException,
            UnknownEditionTypeException, NotSuchCountEditionException {
        Reader reader = findReaderById(writingInLibraryCard.getReaderId());
        PrintEdition printEdition = printEditionService.printEditionSupply(writingInLibraryCard.getEditionType(),
                writingInLibraryCard.getPrintEditionId());
        if (printEdition.getCountInLibrary() > writingInLibraryCard.getCount()) {
            printEdition.givePrintEdition(writingInLibraryCard.getCount());
            addOrUpdateWritingInLibraryCard(reader, writingInLibraryCard);
        } else throw new NotSuchCountEditionException();
        readerRepository.save(reader);
        printEditionService.printEditionSave(writingInLibraryCard.getEditionType(), printEdition);
    }

    @Transactional
    public void returnEditionFromReader(WritingInLibraryCard writingInLibraryCard)
            throws ReaderDasNotExistException, PrintEditionDasNotExistException, UnknownEditionTypeException, NotSuchCountEditionException {
        Reader reader = findReaderById(writingInLibraryCard.getReaderId());
        PrintEdition printEdition = printEditionService.printEditionSupply(writingInLibraryCard.getEditionType(),
                writingInLibraryCard.getPrintEditionId());
        if (printEdition.getCountReadersHave() >= writingInLibraryCard.getCount()) {
            printEdition.returnPrintEdition(writingInLibraryCard.getCount());
            subtractCountEditionInLibraryCard(reader, writingInLibraryCard);
        } else throw new NotSuchCountEditionException(NOT_HAVE_SUCH_COUNT_IN_LIBRARY);
        readerRepository.save(reader);
        printEditionService.printEditionSave(writingInLibraryCard.getEditionType(), printEdition);
    }

    public List<PrintEdition> getAllReadersBooks(long readerId)
            throws ReaderDasNotExistException, PrintEditionDasNotExistException, UnknownEditionTypeException {
        Reader reader = findReaderById(readerId);
        List<WritingInLibraryCard> writingInLibraryCardList = reader.getLibraryCard();
        List<PrintEdition> printEditions = new ArrayList<>();
        if (writingInLibraryCardList.isEmpty()) return printEditions;
        for (WritingInLibraryCard writingInLibraryCard : writingInLibraryCardList) {
            printEditions.add(
                    printEditionService.printEditionSupply
                            (writingInLibraryCard.getEditionType(), writingInLibraryCard.getPrintEditionId()));
        }
        return printEditions;
    }
}
