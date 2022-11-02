package by.nahorny.library.service;

import by.nahorny.library.dao.MagazineRepository;
import by.nahorny.library.entity.printEdition.Magazine;
import by.nahorny.library.exeptions.PrintEditionDasNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class MagazineService {

    @Autowired
    MagazineRepository magazineRepository;

    @Cacheable("magazines")
    public Magazine findMagazineById (long id) throws PrintEditionDasNotExistException {
        return magazineRepository.findById(id).orElseThrow(PrintEditionDasNotExistException::new);
    }
    public void save (Magazine magazine) {
        magazineRepository.save(magazine);
    }
}
