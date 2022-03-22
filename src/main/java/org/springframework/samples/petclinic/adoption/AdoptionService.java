package org.springframework.samples.petclinic.adoption;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionService {
    
    private AdoptionRepository adoptionRepository;

    @Autowired
    public AdoptionService(AdoptionRepository adoptionRepository){
        this.adoptionRepository = adoptionRepository;
    }

    @Transactional(readOnly = true)
    public Adoption findAdoptionById(int id) throws DataAccessException{
        return adoptionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Adoption> findAllAdoptions(){
        return adoptionRepository.findAll();
    }

    @Transactional
    public void saveAdoption(Adoption adoption) throws DataAccessException{
        adoptionRepository.save(adoption);
    }

    @Transactional
    public void deleteAdoption(int id){
        adoptionRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllAdoptions(){
        adoptionRepository.deleteAll();
    }
}