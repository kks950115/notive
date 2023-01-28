package org.notive.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@RequestMapping("/paymentTest/")
@Controller
public class PaymentAPITest {
	
	@GetMapping("payment")
	public String payment() {
		log.debug("payment is invoked.");
		
		return "/paymentTest/payment";
	} //paymentTest


	
} //end class
