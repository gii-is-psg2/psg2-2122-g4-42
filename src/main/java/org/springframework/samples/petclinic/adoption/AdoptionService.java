package org.springframework.samples.petclinic.adoption;

import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AdoptionService {

    private AdoptionRepository adoptionRepository;

    private RequestAdoptionRepository requestAdoptionRepository;

    @Autowired
    public AdoptionService(AdoptionRepository adoptionRepository, RequestAdoptionRepository requestAdoptionRepository) {
        this.adoptionRepository = adoptionRepository;
        this.requestAdoptionRepository = requestAdoptionRepository;
    }

    @Transactional(readOnly = true)
    public Adoption findAdoptionById(int id) throws DataAccessException {
        return adoptionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Adoption> findAllAdoptions() {
        return adoptionRepository.findAll();
    }

    @Transactional
    public void saveAdoption(Adoption adoption) throws DataAccessException {
        adoptionRepository.save(adoption);
    }

    @Transactional
    public void deleteAdoption(int id) {
        Collection<RequestAdoption> requestAdoptions = this.adoptionRepository.findById(id).getRequestAdoptions();
        for (RequestAdoption requestAdoption : requestAdoptions) {
            this.requestAdoptionRepository.deleteById(requestAdoption.getId());
        }
        adoptionRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllAdoptions() {
        this.requestAdoptionRepository.deleteAll();
        Collection<Adoption> adoptions = this.adoptionRepository.findAll();
        for(Adoption adoption: adoptions){
            deleteAdoption(adoption.getId());
        }
    }
}