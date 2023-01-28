package org.notive.myapp.controller;


import java.util.List;
import java.util.Objects;

import org.notive.myapp.domain.BuyVO;
import org.notive.myapp.domain.Item_OriginVO;
import org.notive.myapp.service.BuyService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.gson.Gson;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@NoArgsConstructor
@Log4j2

@RequestMapping("/buy/")
@Controller
public class BuyController implements InitializingBean {

	@Setter(onMethod_=@Autowired)	
	private BuyService service;
	
	//=================================================
	//** ReqestMapping table 설계 **
	//(1) /buy/list			GET			void list(model)		  ---> **DB적재 확인용!
	//(2) /buy/main			POST(?)		register(buy, rttrs)      ---> redirect:/buy/main
	//(3) /buy/register 	POST
	//====================================================

	//==============================Modal===============================================//

		
	@GetMapping("myPage_Modal")
	public String myPage_Modal() {
		log.debug("myPage_Modal() invoked.");
		
		return "/buy/myPage_Modal";
	}//myPage_Modal

	
	@GetMapping("main")
	public String main() {
		log.debug("main() invoked.");
		
		return "/buy/main";
	}//main

	//------------------------------------------------------------------------------//
	
	
	//**테스트용. 안 써도 되면 굳이X
	@GetMapping("list")
	public void list(Model model) {
		log.debug("list({}) invoked.", model);
		
		List<BuyVO> buys = this.service.getList();
		Objects.requireNonNull(buys);
		
		buys.forEach(log::info);
		model.addAttribute("list", buys);
	}//list
	
	
	@ResponseBody
	@GetMapping(value="itemSelect_Modal", produces="text/plain;charset=UTF-8")
	public String itemList(Model model) {
		log.debug("itemList({}) invoked.", model);
		
		List<Item_OriginVO> items = this.service.bringItemList();
		Objects.requireNonNull(items);
		
		Gson gson = new Gson();
		String jsonitem = gson.toJson(items);
		//items.forEach(log::info);
		model.addAttribute("itemList", jsonitem);
		return jsonitem;
		
	}//itemList

	
	//결제 시 구매 정보 등록
	@PostMapping("register")
	public String register(BuyVO buy, RedirectAttributes rttrs) {
		log.debug("register({}, {}) invoked.", buy, rttrs);
		
		this.service.register(buy);
		rttrs.addFlashAttribute("result", buy.getBuy_no());
		
		return "redirect:/main";
	}//register
	
	
	
	//24시간이라는 조건이 맞을 경우, 구매 상태를 DeActivated로 바꿔주는 컨트롤러.
	@PostMapping("updateCancel")
	public String updateCancel(@RequestParam("user_id") String user_id, RedirectAttributes rttrs) {
		log.debug("updateCancel({}, {}) invoked.", user_id);
			
		//조건이 맞을 경우 Activate = > DeActivate 변경
		int isModified = this.service.updateCancel(user_id);
			
		log.info("isModified: " + isModified);//
		
		//update가 1개면, update되는 것이 없으면.
		if(isModified==1) {
			rttrs.addFlashAttribute("cancelResult", "S");
			log.info("작동!!!!");
		}else if(isModified==0){
			rttrs.addFlashAttribute("cancelResult", "F");
			log.info("비작동!!!!");
		}//if-else
			
		return "redirect:/user/myPage";
	} //updateCancel
		
	
	//===================================================================================================//
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		log.debug("afterPropertiesSet() invoked.");
		assert this.service != null;
		log.info("\t+ service: {}", this.service);
	}
	
	
	
}//end class
