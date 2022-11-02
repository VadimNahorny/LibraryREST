package by.nahorny.library.controllers;

import by.nahorny.library.dao.BookEditionRepository;
import by.nahorny.library.dto.EditionNameDto;
import by.nahorny.library.entity.printEdition.BookEdition;
import by.nahorny.library.entity.printEdition.PrintEdition;
import by.nahorny.library.entity.WritingInLibraryCard;
import by.nahorny.library.exeptions.NotSuchCountEditionException;
import by.nahorny.library.exeptions.PrintEditionDasNotExistException;
import by.nahorny.library.exeptions.ReaderDasNotExistException;
import by.nahorny.library.exeptions.UnknownEditionTypeException;
import by.nahorny.library.service.BookEditionService;
import by.nahorny.library.service.MagazineService;
import by.nahorny.library.service.NewspaperService;
import by.nahorny.library.service.ReaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping("/libraryController")
public class LibraryController {

    private static final String EDITION_READERS_HAVE = "The book cannot be deleted because readers have it";


    @Autowired
    MagazineService magazineService;
    @Autowired
    NewspaperService newspaperService;
    @Autowired
    BookEditionService bookEditionService;
    @Autowired
    ReaderService readerService;


    @Autowired
    BookEditionRepository bookEditionRepository;


    @PostMapping("/addNewPartBookEdition")
    public void addNewPartBookEdition(@RequestBody BookEdition bookEdition) {
        bookEditionService.addNewPartEdition(bookEdition);
    }

    @PostMapping("/subtractPartBookEdition")
    public ResponseEntity<?> subtractPartBookEdition(@RequestBody BookEdition bookEdition) {
        int request = bookEditionService.subtractEdition(bookEdition);
        return switch (request) {
            case (-1) -> ResponseEntity.badRequest().body
                    ("A book edition exists, but its count is less than expected to remove");
            case (0) -> ResponseEntity.badRequest().body
                    ("A book edition is null");
            case (2) -> ResponseEntity.badRequest().body
                    ("The requested book edition does not exists");
            default -> ResponseEntity.ok().build();
        };
    }

    @GetMapping("/findBookEditionByName")
    public ResponseEntity<?> findBookEditionByName(@RequestBody String name) {
        List<BookEdition> list = bookEditionService.findBookEditionByName(name);
        System.out.println(list.isEmpty());
        if (list.isEmpty()) {
            return ResponseEntity.badRequest().body("The requested book edition does not exists");
        }
        return ResponseEntity.ok(list);
    }

    @GetMapping("/findBookEditionByAuthor")
    public ResponseEntity<?> findBookEditionByAuthor(@RequestBody String author) {
        List<BookEdition> list = bookEditionService.findBookEditionByAuthor(author);
        System.out.println(list.isEmpty());
        if (list.isEmpty()) {
            return ResponseEntity.badRequest().body("The requested book edition does not exists");
        }
        return ResponseEntity.ok(list);
    }

    @PostMapping("/correctCountInLibraryBookEditionById")
    public void correctCountInLibraryBookEditionById(@RequestBody int[] list) {
        System.out.println(Arrays.toString(list));
        bookEditionService.correctCountInLibraryBookEditionById
                (list[0], list[1]);
    }

    @PostMapping("/lendEditionToReader")
    public ResponseEntity<?> lendEditionToReader(@RequestBody WritingInLibraryCard writingInLibraryCard) {
        try {
            readerService.lendEditionToReader(writingInLibraryCard);
        } catch (ReaderDasNotExistException | PrintEditionDasNotExistException | UnknownEditionTypeException
                | NotSuchCountEditionException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }


    @PostMapping("/returnEditionFromReader")
    public ResponseEntity<?> returnEditionFromReader(@RequestBody WritingInLibraryCard writingInLibraryCard) {
        try {
            readerService.returnEditionFromReader(writingInLibraryCard);
        } catch (ReaderDasNotExistException | PrintEditionDasNotExistException | UnknownEditionTypeException
                | NotSuchCountEditionException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping("/deleteBookEditionById")
    public ResponseEntity<?> deleteBookEditionById(@RequestBody long id) {
        try {
            if (bookEditionService.deleteBookEditionById(id))
                ResponseEntity.ok().build();
            else return ResponseEntity.badRequest().body(EDITION_READERS_HAVE);
        } catch (PrintEditionDasNotExistException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getAllReadersBooks")
    public ResponseEntity<?> getAllReadersBooks(@RequestBody long id) {
        try {
            List<PrintEdition> list = readerService.getAllReadersBooks(id);
            if (!list.isEmpty())
                return ResponseEntity.ok(list);
            else ResponseEntity.badRequest().body("Reader does not have books");
        } catch (PrintEditionDasNotExistException | ReaderDasNotExistException |
                UnknownEditionTypeException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok().build();
    }


    @GetMapping("/findByExpressionInBookEditionName")
    public ResponseEntity<?> findByExpressionInBookEditionName
            (@RequestBody String expression) {
        try {
            List<PrintEdition> list =
                    bookEditionService.findByExpressionInBookEditionName(expression);
            if (!list.isEmpty())
                return ResponseEntity.ok(list);
            else ResponseEntity.badRequest().body("No matches found");
        } catch (PrintEditionDasNotExistException e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok().build();
    }

}

