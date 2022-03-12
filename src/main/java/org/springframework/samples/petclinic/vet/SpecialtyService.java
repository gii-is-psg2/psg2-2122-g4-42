package org.springframework.samples.petclinic.vet;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SpecialtyService {
    
    private SpecialtyRepository specialtyRepository;

    @Autowired
    public SpecialtyService(SpecialtyRepository specialtyRepository) {
        this.specialtyRepository = specialtyRepository;
    }

    public List<Specialty> getAllSpecialties() {
        List<Specialty> specialties = new ArrayList<>();
        specialtyRepository.findAll().forEach(s -> specialties.add(s));
        return specialties;
    }

    public Specialty getSpecialtyByName(String name) {
        return specialtyRepository.findByName(name);
    }

}
