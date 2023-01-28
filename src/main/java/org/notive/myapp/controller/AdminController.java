package org.notive.myapp.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.AdminDTO;
import org.notive.myapp.domain.AdminVO;
import org.notive.myapp.domain.Criteria;
import org.notive.myapp.domain.PageDTO;
import org.notive.myapp.domain.UserListVO;
import org.notive.myapp.service.AdminService;
import org.notive.myapp.service.UserMgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Controller
@RequestMapping("/")
public class AdminController {
	
	
	@Setter(onMethod_=@Autowired)
	private AdminService adminService ;
	private UserMgtService userService;
	
	
	public static final String adminLoginKey = "__ADMIN__";
	
	
	@GetMapping("adminLogin")
	public String adminLogin () {
		log.debug("adminLogin() invoked");
		
		return "adminLogin";
	} // adminLogin
	
	
	@PostMapping("adminLoginPost")
	public String adminLoginPost (
			AdminDTO dto,
			Model model,
			HttpSession session,
			RedirectAttributes rttrs) {
		
		log.debug("adminLogin(dto, model, session, rttrs) invoked");
		
		AdminVO adminVO = this.adminService.adminLogin(dto);
		
		if(adminVO != null) {
			
			model.addAttribute(adminLoginKey,adminVO);
			
			return "/adminLoginPost";
			
		} else {	// 로그인 실패
			
			rttrs.addFlashAttribute("result", "failure");
			
			return "/adminLogin";
		}
		
	} // adminLogin
	
	
	
	
} // end class
