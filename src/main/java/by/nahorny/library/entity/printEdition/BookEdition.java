package by.nahorny.library.entity.printEdition;
import by.nahorny.library.enums.BookThematicType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

@EqualsAndHashCode(callSuper = true)
@Data
@Table
public class BookEdition extends PrintEdition {


    private String author;
    private BookThematicType thematicType;
    private short yearOfPublication;
    private short editionNumber;

    public BookEdition(String name, short countInLibrary, short countReadersHave, String publishingHouse,
                       String author, BookThematicType thematicType, short yearOfPublication, short editionNumber) {
        super(name, countInLibrary, countReadersHave, publishingHouse);
        this.author = author;
        this.thematicType = thematicType;
        this.yearOfPublication = yearOfPublication;
        this.editionNumber = editionNumber;
    }

    @Override
    public String toString() {
        return "BookEdition{" +
                "author='" + author + '\'' +
                ", thematicType=" + thematicType +
                ", yearOfPublication=" + yearOfPublication +
                ", editionNumber=" + editionNumber +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", countInLibrary=" + countInLibrary +
                ", countReadersHave=" + countReadersHave +
                ", generalCount=" + generalCount +
                ", publishingHouse='" + publishingHouse + '\'' +
                '}';
    }

    public BookEdition() {
    }


}



