package org.springframework.samples.petclinic.cause;

import java.time.LocalDate;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.cause.Cause;
import org.springframework.samples.petclinic.cause.CauseService;
import org.springframework.samples.petclinic.user.User;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/causes/{causeId}/donations")
public class DonationController {

    private final String VIEWS_CREATE_DONATIONS_FORM = "causes/donationForm";
    
    private CauseService causeService;


    @Autowired
    public DonationController( CauseService causeService){
        this.causeService = causeService;
    }

    @GetMapping("/new")
    public String initCreationForm(@PathVariable(name = "causeId") int causeId, Model model) {
        model.addAttribute("cause", causeService.findCauseById(causeId));
        model.addAttribute("donation", new Donation());
        return VIEWS_CREATE_DONATIONS_FORM;
    }


    @PostMapping("/new")
    public String processCreationForm(RedirectAttributes redirect, HttpSession session, @PathVariable(name = "causeId") int causeId, @Valid Donation donation, BindingResult result, String client) {
    	 Cause cause = causeService.findCauseById(causeId);
     	donation.setCause(cause);
         donation.setDate(LocalDate.now());
         donation.setClient(client);
         causeService.saveDonation(donation);
    	
    	if (result.hasErrors()) {
        	 redirect.addFlashAttribute("message", "Cantidad a donar erronea");
            return "redirect:/causes/"+causeId+"/donations/new";
    	}
   if (cause.getMoneyRaised() > cause.getBudgetTarget()) {
	   redirect.addFlashAttribute("message", "Cantidad erronea, sobrepaso el presupuesto");
       return "redirect:/causes/"+causeId+"/donations/new";
            }
         else {
         
            return "redirect:/causes/"; 
    }

}
}