package org.notive.myapp.controller;

import java.util.ArrayList;
import java.util.List;

import org.notive.myapp.domain.DirectoryVO;
import org.notive.myapp.domain.TrashVO;
import org.notive.myapp.service.DirectoryService;
import org.notive.myapp.service.TrashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Controller
@RequestMapping("/main/trash")
public class TrashController {
	
	@Autowired
	TrashService trashService;
	
	@Autowired
	DirectoryService directoryService;
	
	@ResponseBody
	@GetMapping("/addTrashIntoBin")
	public TrashVO addTrashIntoBin(String user_id, Integer dirNo) {
		log.debug("list({}) invoked.", user_id);
		
//		삭제된 전체파일 업데이트 
//		this.trashService.toTrashBinAllUndeleted();
		
		return this.trashService.getTrash(user_id, dirNo);
	} // list
	
	@ResponseBody
	@GetMapping("/list")
	public List<TrashVO> list(String user_id) {
		log.debug("list({}) invoked.", user_id);
		
//		삭제된 전체파일 업데이트 
//		this.trashService.toTrashBinAllUndeleted();
		
		return this.trashService.getTrashShowList(user_id);
		
	} // list
	
	@ResponseBody
	@PostMapping("/remove")
	public String remove(@RequestParam("selectedTrash[]") List<Integer> listTrash, String user_id) {
		List<TrashVO> listTrashSelected = new ArrayList<TrashVO>();
		
		listTrash.forEach(e -> {
			listTrashSelected.add(this.trashService.getTrashListSelected(e, user_id));
		});
		
		log.info(listTrashSelected);
		listTrashSelected.forEach(trashService::permanentDeleteTrash);

		return "성공";
	}
	
	@ResponseBody
	@PostMapping("/restore")
	public List<DirectoryVO> restore(@RequestParam("selectedTrash[]") List<Integer> listTrash, String user_id) {
		List<TrashVO> listTrashSelected = new ArrayList<TrashVO>();
		
		List<DirectoryVO> restoreList = new ArrayList<DirectoryVO>();
		
		listTrash.forEach(e -> {
			
			listTrashSelected.add(this.trashService.getTrashListSelected(e, user_id));
			
		});
		
		listTrashSelected.forEach(e -> {
			
			if(e.getSort_code() == 1) {
				restoreList.add(this.directoryService.get(e.getOrigin_no(), user_id));
			}
			
		});
		log.info("!!!"+ listTrashSelected);
		listTrashSelected.forEach(trashService::restoreTrash);

		return restoreList;
	}
	
	@ResponseBody
	@PostMapping("/restoreAll")
	public List<DirectoryVO> restoreAll(@RequestParam("user_id") String user_id) {
		List<TrashVO> trashList = this.trashService.getTrashList(user_id);
		
		List<DirectoryVO> directoryVO = this.directoryService.getList(user_id, 2);
		
		trashList.forEach(trashService::restoreTrash);
		
		return directoryVO;
	}
	
	@ResponseBody
	@PostMapping("/removeAll")
	public String removeAll(@RequestParam("user_id") String user_id) {
		List<TrashVO> trashList = this.trashService.getTrashList(user_id);
		
		trashList.forEach(trashService::permanentDeleteTrash);
		return "성공";
	}
	
} // end class
