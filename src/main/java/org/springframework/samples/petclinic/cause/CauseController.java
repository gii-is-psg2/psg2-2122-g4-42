package org.springframework.samples.petclinic.cause;

import org.h2.util.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.validation.Valid;

import com.google.gson.Gson;

@Controller
public class CauseController {

	private final CauseService causeService;

	private static final String VIEWS_CAUSE_CREATE_FORM = "causes/createCauseForm";

	@Autowired
	public CauseController(CauseService causeService) {
		this.causeService = causeService;

	}

	@GetMapping(value = { "/causes" })
	public String showCauseList(Model model) {
		List<Cause> c = this.causeService.findAllCauses();
		model.addAttribute("causes", c);
		model.addAttribute("search", new Search());
		model.addAttribute("toSearch", new ArrayList<>());
		return "causes/causeList";
	}

	@PostMapping(value = { "/causes" })
	public String processSearchForm(@Valid Search search,
			BindingResult result, ModelMap model) throws IOException {
		if (result.hasErrors()) {
			model.addAttribute("search", search);
			model.addAttribute("causes", this.causeService.findAllCauses());
		} else {
			String toSearch = search.getText().replace(" ", "+");

			OkHttpClient client = new OkHttpClient();

			Request request = new Request.Builder()
					.url("https://google-search3.p.rapidapi.com/api/v1/search/q=" + toSearch)
					.get()
					.addHeader("Content-Type", "application/json")
					.addHeader("X-User-Agent", "desktop")
					.addHeader("X-Proxy-Location", "GB")
					.addHeader("X-RapidAPI-Host", "google-search3.p.rapidapi.com")
					.addHeader("X-RapidAPI-Key", "517571cf85msh45d17fba27da8dep11b959jsne7f8befb75ff")
					.build();

			Response response = client.newCall(request).execute();
			ResultAPI data = new Gson().fromJson(response.body().string(), ResultAPI.class);

			List<String> results = data.getResults().stream().map(x->x.getLink()+","+x.getTitle()).collect(Collectors.toList());
			model.addAttribute("results", results);
			model.addAttribute("causes", this.causeService.findAllCauses());
		}
		return "causes/causeList";
	}

	@GetMapping(value = "/search?{toSearch}")
	public String showResults(@PathVariable("toSearch") String toSearch) {
		System.out.println("AAAAAAAAAAAAAAAAAAA" + toSearch);
		return "causes/search";
	}

	@GetMapping(value = "/causes/new")
	public String initCreationForm(Map<String, Object> model) {
		var cause = new Cause();
		model.put("cause", cause);
		return VIEWS_CAUSE_CREATE_FORM;
	}

	@PostMapping(value = "/causes/new")
	public String processCreationForm(RedirectAttributes redirect, HttpSession session, @Valid Cause cause,
			BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_CAUSE_CREATE_FORM;
		} else if (cause.getBudgetTarget() <= 0) {
			result.rejectValue("budgetTarget", "negativeNumber",
					"el presupuesto objetivo no puede ser negativo o nulo");
			return VIEWS_CAUSE_CREATE_FORM;
		} else {
			this.causeService.saveCause(cause);
			return "redirect:/causes";
		}
	}

	@GetMapping("/causes/detail/{causeId}")
	public ModelAndView showCauseDetails(@PathVariable("causeId") int causeId) {
		var mav = new ModelAndView("/causes/causeDetails");
		mav.addObject(this.causeService.findCauseById(causeId));
		mav.addObject(this.causeService.findDonationsByCauseId(causeId));
		return mav;
	}

}
