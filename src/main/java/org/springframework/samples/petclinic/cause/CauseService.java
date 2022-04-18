package org.springframework.samples.petclinic.cause;

import java.util.Collection;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CauseService {
    
    private CauseRepository causeRepository;
    private  DonationRepository donationRepository;

    @Autowired
    public CauseService(CauseRepository causeRepository,
     DonationRepository donationRepository){
        this.causeRepository = causeRepository;
        this.donationRepository = donationRepository;
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
        for(Donation donation: donationRepository.findByCauseId(id)){
            this.donationRepository.deleteById(donation.getId());
        }
        causeRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllCauses(){
        causeRepository.deleteAll();
    }

    @Transactional
    public void saveDonation(Donation  donation)  {
    	var cause = donation.getCause();
    	if (cause.getMoneyRaised() > cause.getBudgetTarget())
    	    throw new IllegalArgumentException();
        double leftToFulfill = cause.getBudgetTarget() - cause.getMoneyRaised();
        donation.setAmount(Math.min(donation.getAmount(), leftToFulfill));
        cause.setMoneyRaised(cause.getMoneyRaised() + donation.getAmount());
    
    	causeRepository.save(cause);
        donationRepository.save(donation);
    }
   

    @Transactional(readOnly = true)
    public Collection<Donation> findDonations() throws DataAccessException {
        return donationRepository.findAll();
    }

    @Transactional
    public void deleteDonation(int id) throws DataAccessException {
        donationRepository.deleteById(id);
    }

    @Transactional
    public Collection<Donation> findDonationsByCauseId(int causeId) {
        return donationRepository.findByCauseId(causeId);
    }

}
