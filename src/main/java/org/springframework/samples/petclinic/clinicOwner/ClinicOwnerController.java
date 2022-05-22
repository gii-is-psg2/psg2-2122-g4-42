package org.springframework.samples.petclinic.clinicOwner;


import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.user.Authorities;
import org.springframework.samples.petclinic.user.AuthoritiesService;
import org.springframework.samples.petclinic.user.UserService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ClinicOwnerController {

	private static final String VIEWS_UPGRADE_PLAN = "clinics/upgradePlan";
	private static final String VIEWS_LOG_OUT= "clinics/pleaseLogOut";
	

	@Autowired
	private final ClinicOwnerService clinicOwnerService;

	@Autowired
	private final UserService userService;

	@Autowired
	public ClinicOwnerController(ClinicOwnerService clinicOwnerService, UserService userService,
			AuthoritiesService authoritiesService) {
		this.clinicOwnerService = clinicOwnerService;
		this.userService = userService;
	}

	@GetMapping(value = { "/plans" })
	public String showCauseList(Model model) {
		ClinicOwner c= getClinicOwner();
		ClinicOwnerPlanType a= c.getPlan();
		Set<Authorities> aut= c.getUser().getAuthorities();
		model.addAttribute("clinics", a);
		model.addAttribute("authorities", aut);
		return VIEWS_UPGRADE_PLAN;
	}



	public ClinicOwner getClinicOwner() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		var user = this.userService.findByUsername(username);
		Set<Authorities> authorities = user.getAuthorities();
		ClinicOwner clinicOwner = new ClinicOwner();
		for (Authorities authority : authorities) {
			if (authority.getAuthority().equals("basic") || authority.getAuthority().equals("advanced")
					|| authority.getAuthority().equals("pro")) {
				clinicOwner = this.clinicOwnerService.findByUsername(username);

			}
		}
		return clinicOwner;
	}

	@GetMapping(value = "/plans/basico")
	public String upgradeToBasic() {
		ClinicOwner c = getClinicOwner();
		this.clinicOwnerService.updatePlanBasico(c);
		return VIEWS_LOG_OUT;

	}
	
	@GetMapping(value = "/plans/avanzado")
	public String upgradeToAdvanced() {
		ClinicOwner c = getClinicOwner();
		this.clinicOwnerService.updatePlanAdvanced(c);
		return VIEWS_LOG_OUT;

	}
	
	@GetMapping(value = "/plans/profesional")
	public String upgradeToPro() {
		ClinicOwner c = getClinicOwner();
		this.clinicOwnerService.updatePlanProfesional(c);
		return VIEWS_LOG_OUT;

	}

}
