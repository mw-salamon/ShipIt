package pl.lets_eat_together.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import pl.lets_eat_together.model.Office;
import pl.lets_eat_together.repository.OfficeRepository;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeService {

    private final OfficeRepository officeRepository;

    @Autowired
    public OfficeService(@Qualifier("officeRepository") OfficeRepository officeRepository
                        ) {
        this.officeRepository = officeRepository;
    }

    public List<Office> getAllOffices(){
        return officeRepository.findAll();
    }

    public Office getOfficeById(Long id){
        Optional<Office> found = officeRepository.findById(id);
        return found.orElseThrow();
    }

    //TODO proper Exceptions classes

    public Office addOffice(Office newOffice){
        return officeRepository.saveAndFlush(newOffice);
    }


    public String deleteOffice(Long id){
         Office office = getOfficeById(id);
         officeRepository.delete(office);
         return "Office with id=" + id + " deleted";
    }

}
