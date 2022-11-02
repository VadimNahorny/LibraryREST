package by.nahorny.library.enums;

public enum EditionType {

    BOOK_EDITION ("BookEdition"), MAGAZINE ("Magazine"), NEWSPAPER ("Newspaper");

    final String editionName;


    EditionType(String editionName) {
        this.editionName = editionName;
    }
}
