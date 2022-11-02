package by.nahorny.library.service;

import by.nahorny.library.entity.printEdition.BookEdition;
import by.nahorny.library.entity.printEdition.Magazine;
import by.nahorny.library.entity.printEdition.Newspaper;
import by.nahorny.library.entity.printEdition.PrintEdition;
import by.nahorny.library.enums.EditionType;
import by.nahorny.library.exeptions.PrintEditionDasNotExistException;
import by.nahorny.library.exeptions.UnknownEditionTypeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PrintEditionService {

    @Autowired
    BookEditionService bookEditionService;
    @Autowired
    NewspaperService newspaperService;
    @Autowired
    MagazineService magazineService;



    public PrintEdition printEditionSupply(EditionType editionType, long editionId)
            throws PrintEditionDasNotExistException, UnknownEditionTypeException {
        return switch (editionType) {
            case BOOK_EDITION -> bookEditionService.findBookEditionById(editionId);
            case MAGAZINE -> magazineService.findMagazineById(editionId);
            case NEWSPAPER -> newspaperService.findNewsPaperById(editionId);
            default -> throw new UnknownEditionTypeException();
        };
    }
    public void printEditionSave(EditionType editionType, PrintEdition printEdition)
            throws PrintEditionDasNotExistException, UnknownEditionTypeException {
         switch (editionType) {
            case BOOK_EDITION -> bookEditionService.save((BookEdition) printEdition);
            case MAGAZINE -> magazineService.save((Magazine) printEdition);
            case NEWSPAPER -> newspaperService.save((Newspaper) printEdition);
            default -> throw new UnknownEditionTypeException();
        };
    }
}
