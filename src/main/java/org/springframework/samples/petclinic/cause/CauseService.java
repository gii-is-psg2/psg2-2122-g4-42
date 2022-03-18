package org.springframework.samples.petclinic.cause;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {
    
    private CauseRepository causeRepository;

    @Autowired
    public CauseService(CauseRepository causeRepository){
        this.causeRepository = causeRepository;
    }

    @Transactional(readOnly = true)
    public Cause findCauseById(int id) throws DataAccessException{
        return causeRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Cause> findAllCauses(){
        return causeRepository.findAll();
    }

    @Transactional
    public void saveCause(Cause cause) throws DataAccessException{
        causeRepository.save(cause);
    }

    @Transactional
    public void deleteCause(int id){
        causeRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllCauses(){
        causeRepository.deleteAll();
    }
}
