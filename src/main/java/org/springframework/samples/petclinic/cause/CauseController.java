package org.springframework.samples.petclinic.cause;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;


@Controller
public class CauseController {

	private final CauseService causeService;

	@Autowired
	public CauseController(CauseService causeService) {
		this.causeService = causeService;
		
	}

	@GetMapping(value = {"/causes"})
	public String showCauseList(Map<String, Object> model) {
		List<Cause> c = this.causeService.findAllCauses();
		model.put("causes", c);
		return "causes/causeList";
	}
	@GetMapping("/causes/detail/{causeId}")
	public ModelAndView showCauseDetails(@PathVariable("causeId") int causeId) {
		ModelAndView mav = new ModelAndView("/causes/causeDetails");
		mav.addObject(this.causeService.findCauseById(causeId));
		mav.addObject(this.causeService.findDonationsByCauseId(causeId));
		return mav;
	}

}
