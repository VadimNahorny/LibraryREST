package by.nahorny.library.dao;

import by.nahorny.library.entity.printEdition.Newspaper;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewspaperRepository extends CrudRepository<Newspaper, Long> {

}
