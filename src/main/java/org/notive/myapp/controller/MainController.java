package org.notive.myapp.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.UserGroupVO;
import org.notive.myapp.domain.UserVO;
import org.notive.myapp.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/")
@Controller
public class MainController {
	
	public static final String loginKey = "__LOGIN__";
	
	@Setter(onMethod_=@Autowired)
	private GroupService service;
	
	
	// test용
	// 컨트롤러 동적 매핑
	// section 부분만 변동
	// 각 메뉴는 컨트롤러로 분배될 예정
	@RequestMapping(value = "/main/{url}")
	public String testt(@PathVariable String url) {
		log.info("testt() invoked.");
		
		return "/section/" + url;
	} // folder
	
	@GetMapping
	public String home(Locale locale, Model model) {
		log.info("Welcome intro! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "/main";
		
	} // home
	
	@GetMapping("intro")
	public String intro(Locale locale, Model model) {
		log.info("Welcome intro! The client locale is {}.", locale);
		
		return "intro";
	} // intro
	
	@GetMapping("main")
	public String main(HttpSession session) {
		log.info("main() invoked.");
		
		UserVO user = (UserVO)session.getAttribute(loginKey);
		String user_id = user.getUserID();

		List<UserGroupVO> list = this.service.getUserGrouplist(user_id);
		
		log.info(user_id);
		session.setAttribute("list", list);
		
		return "/main";

	} // main
	
}
