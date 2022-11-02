package by.nahorny.library.dao;

import by.nahorny.library.entity.printEdition.Magazine;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazineRepository extends CrudRepository<Magazine, Long> {


}
