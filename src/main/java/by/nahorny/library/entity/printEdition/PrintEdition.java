package by.nahorny.library.entity.printEdition;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@Data
@Table
public abstract class PrintEdition {

    @Id
    long id;
    String name;
    short countInLibrary;
    short countReadersHave;
    short generalCount;
    String publishingHouse;

    public PrintEdition(String name, short countInLibrary,
                        short countReadersHave, String publishingHouse) {
        this.name = name;
        this.countInLibrary = countInLibrary;
        this.countReadersHave = countReadersHave;
        this.generalCount = (short) (this.countInLibrary + this.countReadersHave);
        this.publishingHouse = publishingHouse;
    }

    public PrintEdition() {
    }

    public void setGeneralCount() {
        this.generalCount = (short) (this.countInLibrary + this.countReadersHave);
    }

    public void setCountInLibrary(short countInLibrary) {
        this.countInLibrary = countInLibrary;
        this.setGeneralCount();
    }

    public void setCountReadersHave(short countReadersHave) {
        this.countReadersHave = countReadersHave;
        this.setGeneralCount();
    }

    public void givePrintEdition (short count) {
        this.countInLibrary = (short) (this.countInLibrary - count);
        this.countReadersHave = (short) (this.countReadersHave + count);
    }

    public void returnPrintEdition (short count) {
        this.countInLibrary = (short) (this.countInLibrary + count);
        this.countReadersHave = (short) (this.countReadersHave - count);
    }
    public void setId(Long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }


}
