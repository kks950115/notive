package org.notive.myapp.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.notive.myapp.domain.Criteria;
import org.notive.myapp.domain.PageDTO;
import org.notive.myapp.domain.UserGetVO;
import org.notive.myapp.domain.UserListVO;
import org.notive.myapp.domain.UserMgtCmtVO;
import org.notive.myapp.service.UserMgtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Controller
@RequestMapping("/admin/")
public class UserMgtController {
	
	@Setter(onMethod_=@Autowired)
	private UserMgtService userService ;
	
	public static final String adminLoginKey = "__ADMIN__"; 
	
	
	
//	@GetMapping(path = "userlist")
//	public String getUserList(Model model) {			// 회원 리스트 조회
//		log.debug("getUserList() invoked");
//		
//		List<UserListVO> userList = this.userService.getUserList();
//		
//		Objects.requireNonNull(userService);
//		
//		model.addAttribute("userlist", userService.getUserList()) ;
//		
//		return "/admin/userList"; 
//		
//	} // getList
	
	
//	@GetMapping("userListPerPage")
//	public String getUserListPerPage (
//				@ModelAttribute("cri")
//				Criteria cri,
//				Model model
//			) {
//		log.debug("UserlistPerPage({}) invoked", model);
//		
//		List<UserListVO> userListVO = this.userService.getUserListPerPage(cri);
//		
//		Objects.requireNonNull(userListVO);
//		userListVO.forEach(log::info);
//		
//		PageDTO pageDTO = new PageDTO(cri, this.userService.getTotal(cri));
//		
//		model.addAttribute("userlist", userListVO);
//		model.addAttribute("pageMaker", pageDTO);
//		
//		return "admin/userList";
//		
//		
//	} // listPerPage
	
	
	@GetMapping("userList")
	public String getUserListPerPageSort (
				@ModelAttribute("cri")
				Criteria cri,
				Model model
			) {
		log.debug("listPerPageSort({}) invoked", model);
		
		
		List<UserListVO> userListVO = this.userService.getUserListPerPageSort(cri);
		
		Objects.requireNonNull(userListVO);
		userListVO.forEach(log::info);
//		cri.setAmount(15);
		PageDTO pageDTO = new PageDTO(cri, this.userService.getTotal(cri));	
		
		model.addAttribute("userlist", userListVO);
		model.addAttribute("pageMaker", pageDTO);
		
		return "/admin/userList";
		
		
	} // listPerPage
		
	
	@ResponseBody	
	@GetMapping("getUser")
	public ResponseEntity<Map<String, Object>> getUser (@RequestParam ("userID") String userID) {		// 특정 회원 상세 조회
		log.debug("getUser({}) invoked", userID);
		
		UserGetVO userGetVO = this.userService.userGet(userID);
		List<UserMgtCmtVO> userMgtCmtVO = this.userService.userCmtGet(userID);
		
		Map<String, Object> map = new HashMap<>();
		
		map.put("userGetVO", userGetVO);
		map.put("userMgtCmtVO", userMgtCmtVO);
		
		return new ResponseEntity<>(map,HttpStatus.OK);
		
	} // get
	
	
	@ResponseBody
	@PostMapping("mgtCmtRegister")
	public UserGetVO mgtCmtRegister (
			String userID,
			String adminID,
			String userMgtCode,
			String userMgtCmt) {		
		
		
		UserMgtCmtVO userMgtCmtVO = new UserMgtCmtVO(userID, adminID, userMgtCode, userMgtCmt, null);
		
		this.userService.mgtCmtRegister(userMgtCmtVO);
		
		return this.userService.userGet(userID);
		
	} // register
	
	
	@ResponseBody
	@PostMapping("userStateModify")
	public UserGetVO userStateModify(
			String userID,
			String userState,
			String userGrade) {
		
		UserGetVO userGetVO = new UserGetVO (userID, null, null, null,userGrade, userState); 
		
		this.userService.userStateModify(userGetVO);
	
		return this.userService.userGet(userID);
	} // modify
	
	
	
	
	
	
	

} // end class
