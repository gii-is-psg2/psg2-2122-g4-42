package org.springframework.samples.petclinic.web;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ContactPageController {
	
	
	  @GetMapping(value="/contactpage")
	  public String welcome(Map<String, Object> model) {	    

	    return "contactpage";
	  }
}
