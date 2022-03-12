package org.springframework.samples.petclinic.vet;

import java.text.ParseException;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class SpecialtyFormatter implements Formatter<Specialty>{

    private final SpecialtyService specialtyService;

    @Autowired
    public SpecialtyFormatter(SpecialtyService specialtyService) {
        this.specialtyService = specialtyService;
    }

    @Override
    public String print(Specialty specialty, Locale locale) {
        return specialty.getName();
    }

    @Override
    public Specialty parse(String text, Locale locale) throws ParseException {
        List<Specialty> allSpecialties = this.specialtyService.getAllSpecialties();
        for(Specialty sp : allSpecialties) {
            if (sp.getName().equals(text)) {
                return sp;
            }
        }
        throw new ParseException("Specialty not found with: " + text, 0);
    }

}
