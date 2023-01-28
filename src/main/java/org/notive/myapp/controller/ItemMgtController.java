package org.notive.myapp.controller;

import java.util.List;
import java.util.Objects;

import org.notive.myapp.domain.ItemInsertMgtVO;
import org.notive.myapp.domain.ItemInsertVO;
import org.notive.myapp.domain.ItemVO;
import org.notive.myapp.service.ItemMgtService;
import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
@RequestMapping("/admin/")
public class ItemMgtController {
	
	
	@Setter(onMethod_=@Autowired)
	private ItemMgtService itemMgtService;
	
	
	
	@GetMapping("itemList")
	public String getItemList(Model model) {
		
		log.debug("getItemList() invoked");
		
		List<ItemVO> itemList = this.itemMgtService.getItemList();
		Objects.requireNonNull(itemMgtService);
		
		model.addAttribute("itemlist", itemList);
		
		return "/admin/itemList";
		
	} // getItemList
	
	
	@ResponseBody
	@GetMapping("itemRegister")
	public String itemRegister () {
		log.debug("itemRegister() invoked");
		
		return "admin/itemRegister";
	} // itemRegister
	
	@ResponseBody
	@PostMapping("itemRegister")
	public List<ItemVO> itemRegister(
			String itemName,
			String itemCapa,
			String itemPrice,
			String adminID
			)	{
		
		ItemInsertVO itemInsertVO = new ItemInsertVO (itemName,itemCapa,itemPrice);
		ItemInsertMgtVO itemInsertMgtVO = new ItemInsertMgtVO (null, adminID, null);
		
		this.itemMgtService.itemRegister(itemInsertVO, itemInsertMgtVO);
		
		return this.itemMgtService.getItemList() ;
	} // itemRegister
	
	
	@PostMapping("itemRemove")
	public String itemRemove(
			@RequestParam ("itemNo") Integer itemNo,
			ItemVO itemVO
			) {
		log.debug("itemModify({}) invoked",itemVO);
		
		boolean isItemModified = this.itemMgtService.itemRemove(itemVO);
		
		log.info("\t+ isItemModified : " + isItemModified);
		
		return "redirect:/admin/itemList";
	} // itemModify
	
	
	
//	@GetMapping("getItem")
//	public String getItem(@RequestParam("itemNo") Integer itemNo, Model model) { 
//		log.debug("getItem() invoked");
//		
//		ItemVO getItem = this.itemMgtService.getItem(itemNo);
//		Objects.requireNonNull(itemMgtService);
//		
//		log.info("\t+ getItem : " + getItem);
//		
//		model.addAttribute("getItem",getItem);
//		
//		return "/admin/getItem";
//		
//	} // getItem
	
	@ResponseBody
	@GetMapping("getItem")
	public ItemVO getItem(Integer itemNo) { 
		log.debug("getItem() invoked");
		
		return this.itemMgtService.getItem(itemNo);
		
	} // getItem
	
	
	@ResponseBody
	@PostMapping("itemModify")
	public ItemVO itemModify(
			String itemName,
			String itemCapa,
			String itemPrice,
			Integer itemNo) {
		log.info(">>>>>>>>>>>>>>>>" + itemName,itemCapa,itemPrice,itemNo);
		
		ItemVO itemVO = new ItemVO(itemNo, itemName, itemCapa,itemPrice,null,null,null);
		
		this.itemMgtService.itemModify(itemVO);
		
		
		return this.itemMgtService.getItem(itemVO.getItemNo());
	}

} // end class
