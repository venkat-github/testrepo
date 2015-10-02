package com.test.app.web.rest;

import javax.inject.Inject;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.test.app.domain.UserDoctorVisitRecord;
import com.test.app.repository.UserRecordRepository;


@Controller
public class AppController {

	@Inject
	UserRecordRepository repo;
	
	@RequestMapping(value="/download_record/{recordid}", method = RequestMethod.GET)
	public String getPizza(@PathVariable String recordid, ModelMap model) {
 
		UserDoctorVisitRecord dto = repo.findOne(recordid);
		
		com.test.app.domain.Pizza pizza = new com.test.app.domain.Pizza(recordid);
		model.addAttribute("pizza", pizza);
		model.addAttribute("record", dto);
		
		return "pizza";
 
	}
	
}
