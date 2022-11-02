package by.nahorny.library.entity.printEdition;

import by.nahorny.library.enums.MagazineFormat;
import by.nahorny.library.enums.PeriodicalsThematicType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Table
public  class Magazine extends PeriodicalEdition {

    private MagazineFormat magazineFormat;

    public Magazine(String name, short countInLibrary, short countReadersHave, String publishingHouse, String number,
                    LocalDate dateOfPublication, PeriodicalsThematicType thematicType, MagazineFormat magazineFormat) {
        super(name, countInLibrary, countReadersHave, publishingHouse, number, dateOfPublication, thematicType);
        this.magazineFormat = magazineFormat;
    }

    public Magazine() {
    }

    @Override
    public String toString() {
        return "Magazine{" +
                "number='" + number + '\'' +
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
