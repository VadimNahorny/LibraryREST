package by.nahorny.library.service;

import by.nahorny.library.dao.NewspaperRepository;
import by.nahorny.library.entity.printEdition.Newspaper;
import by.nahorny.library.exeptions.PrintEditionDasNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class NewspaperService {

    @Autowired
    NewspaperRepository newspaperRepository;

    @Cacheable("newsPapers")
    public Newspaper findNewsPaperById (long id) throws PrintEditionDasNotExistException {
        return newspaperRepository.findById(id).orElseThrow(PrintEditionDasNotExistException::new);
    }

    public void save (Newspaper newspaper) {
        newspaperRepository.save(newspaper);
    }
}
