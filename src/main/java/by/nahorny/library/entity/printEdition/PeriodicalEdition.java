package by.nahorny.library.entity.printEdition;
import by.nahorny.library.enums.PeriodicalsThematicType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@Component
public abstract class PeriodicalEdition extends PrintEdition {

    String number;
    LocalDate dateOfPublication;
    PeriodicalsThematicType thematicType;


    public PeriodicalEdition(String name, short countInLibrary, short countReadersHave, String publishingHouse,
                             String number, LocalDate dateOfPublication, PeriodicalsThematicType thematicType) {
        super(name, countInLibrary, countReadersHave, publishingHouse);
        this.number = number;
        this.dateOfPublication = dateOfPublication;
        this.thematicType = thematicType;
    }

    public PeriodicalEdition() {
    }
}
