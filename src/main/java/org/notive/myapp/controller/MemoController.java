package org.notive.myapp.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.MemoDTO;
import org.notive.myapp.domain.MemoVO;
import org.notive.myapp.domain.TrashVO;
import org.notive.myapp.domain.UserVO;
import org.notive.myapp.service.MemoService;
import org.notive.myapp.service.TrashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
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

@RequestMapping("/memo/")
@Controller
public class MemoController {

	@Setter(onMethod_=@Autowired)
	private MemoService service;
	
	@Autowired
	private TrashService trashService;
	
	public static final String loginKey = "__LOGIN__";
	
		//==========================================================//
		//  Request Mapping table 설계
		//==========================================================//
//		(1) /memo/list			GET			list(model) 			--> 게시물 목록화면으로 이동
//		(2) /memo/get			GET			modify(memoNO)				--> redirect:/memo/modify
//		(2) /memo/modify		POST		modify(memo,model)		--> redirect:/memo/list
//		(3) /memo/register		POST		register(memo, rttrs)   --> redirect:/memo/list
//		(4) /memo/remove		POST		remove(memoNo, rttrs)  	--> redirect:/memo/list
		//==========================================================//
	
	
	//첫화면
		@GetMapping("list")
		public String list(@RequestParam("dirNo") Integer dirNo, Model model) {
			log.debug("list() invoked.");
			model.addAttribute("dirNo",dirNo);
			return "section/dirMemo";
	}//list
	
	// 모든 메모 목록
	@ResponseBody
	@GetMapping(path="getlistAll", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MemoVO>> listAll(
			Model model,
			HttpSession session
			) {
		UserVO user =(UserVO)session.getAttribute(loginKey);
			
		List<MemoVO> list = this.service.getlistAll(user.getUserID(), 1);
		
		Objects.requireNonNull(list);	//null값이면 상자에 안들어감.
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}//listAll
	
	//메모 목록
	@ResponseBody
	@GetMapping(path="getlist", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MemoVO>> list(
					@RequestParam("dirNo") Integer dirNo,
					Model model,
					HttpSession session
				) {
		log.debug("list({}, {}, model) invoked.", dirNo, session);
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		
		List<MemoVO> list = this.service.getlist(dirNo, user.getUserID(), 1);
		
		Objects.requireNonNull(list);	//null값이면 상자에 안들어감.
		
		list.forEach(log::info);
		
		model.addAttribute("dirNo", dirNo);
	
		return new ResponseEntity<>(list, HttpStatus.OK);
	}//list
	
	
	//검색 목록
	@ResponseBody
	@GetMapping(path="getSearchList", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<MemoVO>> getSearchList(
			@RequestParam("search") String search,
			Model model,
			HttpSession session
			){
		log.debug("getSearchList({},model, session) invoked.", search);
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		
		List<MemoVO> list = this.service.getSearchList(user.getUserID(), search);
		
		list.forEach(log::info);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	} //memoSearchList
	
	
	
	
	//상세보기-수정가능폼
	@GetMapping(path="modify", produces=MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<MemoVO> modify(
				@RequestParam("memoNo") Integer memoNo, 
				@RequestParam("dirNo") Integer dirNo,
				Model model,
				HttpSession session
			) {
		log.debug("get({}, {}, {}, model) invoked.", memoNo, dirNo, session, model);
		UserVO user =(UserVO)session.getAttribute(loginKey);
		
		MemoVO memo = this.service.get(memoNo, user.getUserID());
		
		Objects.requireNonNull(memo);
		model.addAttribute("__MEMO__", memo);
		
		return new ResponseEntity<>(memo, HttpStatus.OK);
	}//modify
	
	
	
	//수정하기
	@ResponseBody
	@PostMapping(path="modify", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MemoVO> modify(
					MemoDTO memo, 
					RedirectAttributes rttrs,
					HttpSession session
				) {
		log.debug("modify({}, {}, {}) session invoked.", memo, rttrs, session);
		
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		
		MemoVO memoVO = new MemoVO(memo.getMemoNo(), 
									memo.getMemoTitle(),
									memo.getEditordata(),
									null,
									null,
									memo.getDirNo(),
									null,
									user.getUserID(),
									null,
									null
									);
		
		log.info("\t+ memoVO: {}", memoVO);
		 if(this.service.modify(memoVO)) {
			 //rttrs.addAttribute("__RESULT__", "성공");
			 rttrs.addAttribute("dirNo", memo.getDirNo());
		 }//if
		 		 
		 return new ResponseEntity<>(memoVO, HttpStatus.OK);
	}//modify
	
	
	//삭제하기
	@ResponseBody
	@PostMapping(path="remove", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Integer> remove(
					@RequestParam("memoNo") Integer memoNo, 
					@RequestParam("dirNo") Integer dirNo,
					RedirectAttributes rttrs,
					HttpSession session
			) {
		log.debug("remove({}, {}, {}, {}) invoked.", memoNo, dirNo, rttrs, session);
		

		UserVO user =(UserVO)session.getAttribute(loginKey);
		
		MemoVO memoVO = this.service.get(memoNo, user.getUserID());
		
		if(this.service.remove(memoVO)) {
			rttrs.addAttribute("dirNo",dirNo);
			log.info("삭제성공");
		}//if 
		
		return new ResponseEntity<>(memoNo, HttpStatus.OK);
	}//remove
	
	 
	
	//추가하기
	@ResponseBody
	@PostMapping(path="register", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<MemoVO> register(
					MemoDTO memo, 
					RedirectAttributes rttrs,
					HttpSession session
			) {
		log.debug("register({}, {}, rttrs) invoked.", memo,  session);
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		log.info("\t+ userd: {}", user);
		
		MemoVO memoVO = new MemoVO(null, 
									memo.getMemoTitle(),
									memo.getEditordata(),
									null,
									null,
									memo.getDirNo(),//memo.getDirNo()
									null,
									user.getUserID(),
									null,
									null
									);
		
		log.info("\t+ memoVO: {}", memoVO);
		//rttrs.addAttribute("dirNo",memoVO.getDirNo());
		if(this.service.register(memoVO)) {
			rttrs.addAttribute("dirNo",memoVO.getDirNo());
		}
		
		return new ResponseEntity<>(memoVO, HttpStatus.OK);
	}//register
	
	
	
}//end class
