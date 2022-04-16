package org.springframework.samples.petclinic.adoption;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class RequestAdoptionService {
    
    private RequestAdoptionRepository requestAdoptionRepository;

    @Autowired
    public RequestAdoptionService(RequestAdoptionRepository requestAdoptionRepository){
        this.requestAdoptionRepository = requestAdoptionRepository;
    }

    @Transactional(readOnly = true)
    public RequestAdoption findRequestAdoptionById(int id) throws DataAccessException{
        return requestAdoptionRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<RequestAdoption> findAllRequestAdoptions(){
        return requestAdoptionRepository.findAll();
    }

    @Transactional
    public void saveRequestAdoption(RequestAdoption requestAdoption) throws DataAccessException{
        requestAdoptionRepository.save(requestAdoption);
    }

    @Transactional
    public void deleteRequestAdoption(int id){
        requestAdoptionRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllRequestAdoptions(){
        requestAdoptionRepository.deleteAll();
    }
}