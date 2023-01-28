package org.notive.myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/main/")
@Controller
public class UserController {

	@GetMapping("mypage")
	public String myPage() {
		log.info("myPage() invoked.");

		return "/user/myPage";
	} // myPage

} // end class
