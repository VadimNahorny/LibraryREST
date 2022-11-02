package by.nahorny.library.entity.printEdition;

import by.nahorny.library.enums.NewspaperPrintColor;
import by.nahorny.library.enums.PeriodicalsThematicType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Table
public class Newspaper extends PeriodicalEdition {

   private NewspaperPrintColor newspaperPrintColor;

    public Newspaper(String name, short countInLibrary, short countReadersHave, String publishingHouse, String number,
                     LocalDate dateOfPublication, PeriodicalsThematicType thematicType, NewspaperPrintColor newspaperPrintColor) {
        super(name, countInLibrary, countReadersHave, publishingHouse, number, dateOfPublication, thematicType);
        this.newspaperPrintColor = newspaperPrintColor;
    }

    public Newspaper() {
    }

    @Override
    public String toString() {
        return "Newspaper{" +
                "newspaperPrintColor=" + newspaperPrintColor +
                ", number='" + number + '\'' +
                ", dateOfPublication=" + dateOfPublication +
                ", thematicType=" + thematicType +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", countInLibrary=" + countInLibrary +
                ", countReadersHave=" + countReadersHave +
                ", generalCount=" + generalCount +
                ", publishingHouse='" + publishingHouse + '\'' +
                '}';
    }
}
