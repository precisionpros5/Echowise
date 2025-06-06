package com.project.Precision_pros.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class example {
   
	@RequestMapping("/hello")
	public String hello(){
		return "index";
	}
	 @PostMapping("/calculate")
	    public String calculateTip(
	            @RequestParam("billAmount") int billAmount,
	            @RequestParam("serviceRating") int serviceRating,
	            @RequestParam("numPeople") int numPeople,
	            Model model) {

	        if (billAmount <= 0 || numPeople <= 0) {
	            model.addAttribute("error", "Please enter valid values.");
	            return "index";
	        }

	        double tipPercentage = serviceRating / 100.0;
	        double tipAmount = billAmount * tipPercentage;
	        double totalAmount = billAmount + tipAmount;
	        double shareAmount = totalAmount / numPeople;

	        model.addAttribute("tipAmount", tipAmount);
	        model.addAttribute("totalAmount", totalAmount);
	        model.addAttribute("shareAmount", shareAmount);
	      

	        return "index";
	    }
}


