package by.nahorny.library.entity;

import by.nahorny.library.enums.EditionType;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Data
public class WritingInLibraryCard {

    @Id
    private long id;
    private long readerId;
    private long printEditionId;
    private EditionType editionType;
    private short countEdition;

    public WritingInLibraryCard() {
    }

    public WritingInLibraryCard(long printEditionId, EditionType editionType, short countEdition) {
        this.printEditionId = printEditionId;
        this.editionType = editionType;
        this.countEdition = countEdition;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getReaderId() {
        return readerId;
    }

    public void setReaderId(long readerId) {
        this.readerId = readerId;
    }

    public long getPrintEditionId() {
        return printEditionId;
    }

    public void setPrintEditionId(long printEditionId) {
        this.printEditionId = printEditionId;
    }

    public EditionType getEditionType() {
        return editionType;
    }

    public void setEditionType(EditionType editionType) {
        this.editionType = editionType;
    }

    public short getCount() {
        return countEdition;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WritingInLibraryCard)) return false;
        WritingInLibraryCard that = (WritingInLibraryCard) o;
        return getReaderId() == that.getReaderId() && getPrintEditionId() == that.getPrintEditionId()
                && getEditionType() == that.getEditionType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getReaderId(), getPrintEditionId(), getEditionType());
    }

}

