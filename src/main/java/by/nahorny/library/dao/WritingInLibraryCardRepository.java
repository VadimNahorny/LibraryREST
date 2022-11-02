package by.nahorny.library.dao;

import by.nahorny.library.entity.WritingInLibraryCard;
import by.nahorny.library.enums.EditionType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WritingInLibraryCardRepository extends CrudRepository<WritingInLibraryCard, Long> {
    boolean existsByPrintEditionIdAndEditionType(long id, EditionType type);
}
