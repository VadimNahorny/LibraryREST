package by.nahorny.library.entity.person;

import by.nahorny.library.entity.WritingInLibraryCard;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.relational.core.mapping.MappedCollection;

import java.time.LocalDate;
import java.util.*;

@EqualsAndHashCode(callSuper = true)
@Data
public class Reader extends Person {

    long libraryCardNumber;
    @MappedCollection(idColumn = "reader_id", keyColumn = "reader_key")
    private List<WritingInLibraryCard> libraryCard = new ArrayList<>();

    public Reader(String firstName, String surname, LocalDate birthday,
                  long libraryCardNumber) {
        super(firstName, surname, birthday);
        this.libraryCardNumber = libraryCardNumber;
    }

    public void setLibraryCard(List<WritingInLibraryCard> libraryCard) {
        this.libraryCard = libraryCard;
    }

    public Reader() {
    }

    public long getLibraryCardNumber() {
        return libraryCardNumber;
    }

    public void setLibraryCardNumber(long libraryCardNumber) {
        this.libraryCardNumber = libraryCardNumber;
    }

    public List<WritingInLibraryCard> getLibraryCards() {
        return libraryCard;
    }

    public void setWritingInLibraryCard(WritingInLibraryCard writingInLibraryCard) {
        List<WritingInLibraryCard> libraryCard = this.getLibraryCard();
        libraryCard.add(writingInLibraryCard);

    }

    public Optional<WritingInLibraryCard> getWritingInLibraryCard(WritingInLibraryCard writingInLibraryCard) {
        List<WritingInLibraryCard> libraryCard = this.getLibraryCard();
        if (libraryCard.contains(writingInLibraryCard)) {
            for (WritingInLibraryCard wlb : libraryCard) {
                if (wlb.equals(writingInLibraryCard)) return Optional.of(wlb);
            }
        }
        return Optional.empty();
    }


}
