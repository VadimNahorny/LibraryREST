package by.nahorny.library.dao;
import by.nahorny.library.dto.EditionNameDto;
import by.nahorny.library.entity.printEdition.BookEdition;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface BookEditionRepository extends CrudRepository <BookEdition, Long> {
    boolean existsBookEditionByAuthorAndNameAndPublishingHouseAndYearOfPublicationAndEditionNumber
            (String author, String name, String publishingHouse, short yearOfPublication,
             short editionNumber);

    BookEdition findByAuthorAndNameAndPublishingHouseAndYearOfPublicationAndEditionNumber
            (String author, String name, String publishingHouse, short yearOfPublication,
             short editionNumber);

    List <BookEdition> findBookEditionByName (String name);

    List <BookEdition> findBookEditionByAuthor (String author);

    @Modifying
    @Query(value = "UPDATE book_edition SET count_in_library = :countInLibrary where id = :id")
    void correctCountInLibraryBookEditionById(@Param("id") int id,
                                              @Param("countInLibrary") int countInLibrary);

    @Query(value = "SELECT count_readers_have from book_edition where id = :id")
    short findCountBookEditionReadersHaveById(@Param("id") int id);

    @Modifying
    @Query(value = "UPDATE book_edition book_edition SET general_count = :generalCount where id = :id")
    void correctGeneralCountBookEditionById(@Param("id") int id,
                                              @Param("generalCount") int generalCount);

    @Query(value = "SELECT id,name from book_edition")
    List <EditionNameDto> findAllName();

}
