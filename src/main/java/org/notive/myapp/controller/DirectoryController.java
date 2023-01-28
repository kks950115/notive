package org.notive.myapp.controller;

import java.util.List;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.AttachFileVO;
import org.notive.myapp.domain.DirectoryDTO;
import org.notive.myapp.domain.DirectoryVO;
import org.notive.myapp.domain.MemoVO;
import org.notive.myapp.domain.TrashVO;
import org.notive.myapp.domain.UserVO;
import org.notive.myapp.service.DirectoryService;
import org.notive.myapp.service.FileService;
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

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor


@RequestMapping("/dir/")
@Controller
public class DirectoryController {

	
	@Autowired
	private DirectoryService service;
	
	@Autowired
	private TrashService trashService;
	
	@Autowired
	private FileService fileService;
	
	@Autowired
	private MemoService memoService;
	
	public static final String loginKey = "__LOGIN__";
	
	
	
	//==========================================================//
	//  Request Mapping table 설계
	//==========================================================//
//	(1) /dir/list			GET			list() 					--> /dir/listAjax
//	(2) /dir/getlist		GET			getlist() 				--> 리스트	
//	(3) /dir/get			GET			get() 				--> 리스트
//	(4) /dir/modify			POST		modify(memo,model)		-->  
//	(5) /dir/register		POST		register(dir)  			-->  
//	(6) /dir/remove			POST		remove(dirNo)  			-->  
	//==========================================================//
	

	
	
	@GetMapping("list")
	public String list() {
		return "dir/listAjax";
	}//list
	
	
	@ResponseBody
	@GetMapping(path="getlist" , produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<DirectoryVO>> getList(
				Model model, 
				HttpSession session
			) {
		log.debug("getList(model, {}) invoked.", session);
		
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		
		Objects.requireNonNull(service);
		List<DirectoryVO> list = this.service.getList(user.getUserID(), 1);
		 
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}//getList
	
  
	
	@GetMapping("get*")
	public String get(@RequestParam("dirNo")Integer dirNo, Model model) {
		log.debug("get({}, model) invoked.", dirNo);
		model.addAttribute("dirNo",dirNo);
		
		return "";
	}//list
	
	 
	
	
	@ResponseBody
	@PostMapping("remove")
	public String remove(
				@RequestParam("dirNo") Integer dirNo, 
				HttpSession session 
			) {
		log.debug("remove({}, {}) invoked.", dirNo, session);
		
		UserVO user = (UserVO)session.getAttribute(loginKey);
		DirectoryVO topFolder = this.service.get(dirNo, user.getUserID());
			
		Objects.requireNonNull(service);

		
		this.trashService.addTrash(new TrashVO(
				   null,
				    user.getUserID(),
				  1,
				  topFolder.getDirNo(),
				topFolder.getDirName(),
				null
		));
		
		// 해당 폴더의 모든 파일 삭제
		List<AttachFileVO> fileList1 = this.fileService.getlist(dirNo, user.getUserID(), 1);
		fileList1.forEach(fileService::remove);
		
		// 해당 폴더의 모든 메모 삭제
		List<MemoVO> memoList1 = this.memoService.getlist(dirNo, user.getUserID(), 1);
		memoList1.forEach(memoService::remove);

		this.service.remove(dirNo, user.getUserID());
		
		// ---1. 하위폴더의 TopNo=삭제하려는 폴더번호
		Integer subDirTopNo = dirNo;
		
		while(true) {	//하위폴더가 있다면(상위폴더번호가 dirNo) 하위폴더의 dirNo를 저장하고,  상위폴더상태 2로 변경(=휴지통이동) 
						// 하위폴더가 없을때까지 반복
				
			// ---2. 하위폴더 객체생성
			List<DirectoryVO> subFolders = this.service.subGet(subDirTopNo, user.getUserID());		
			
			if(subFolders.isEmpty()) {
				log.info("하위폴더 존재X");
				break; //하위폴더 존재X 	
			}//if
			
			DirectoryVO subFolder = subFolders.get(0);
			
			// ---3. 하위폴더의 폴더번호 얻기 > 하위폴더의 하위폴더가 있을 경우 대비
			Integer subDirNo = subFolder.getDirNo();
			
			//하위폴더 삭제
			
			this.trashService.addTrash(new TrashVO(
					   null,
					    user.getUserID(),
					  1,
					  subFolder.getDirNo(),
					subFolder.getDirName(),
					null
			));
			
			// 해당 폴더의 모든 파일 삭제
			List<AttachFileVO> fileList2 = this.fileService.getlist(subDirNo, user.getUserID(), 1);
			fileList2.forEach(fileService::remove);
			
			// 해당 폴더의 모든 메모 삭제
			List<MemoVO> memoList2 = this.memoService.getlist(subDirNo, user.getUserID(), 1);
			memoList2.forEach(memoService::remove);
			
			this.service.remove(subDirNo, user.getUserID());
			
			// ---4. 2번째 하위폴더의 TopNo하위폴더의 폴더번호
			subDirTopNo = subDirNo;
					
		}//while

		return "삭제완료";
	}//remove
	
	
	
	
	
	@ResponseBody
	@PostMapping(path="register", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DirectoryVO> register(
					DirectoryDTO dto,
					HttpSession session
			){
		log.debug("register({}, {}) invoked.", dto, session);
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		

		DirectoryVO vo = new DirectoryVO(null,
											dto.getDirName(),
											dto.getDirTopNo(),
											dto.getDirLevel(),
											null,
											null,
											null,   //그룹X
											user.getUserID(),
											1,
											1);
		
		Objects.requireNonNull(service);
		log.info("service ok");
		this.service.register(vo);

		log.info("vo: {}",vo);
		return new ResponseEntity<>(vo, HttpStatus.OK);
	}//register
	
	
	
	
	
	
	@ResponseBody
	@PostMapping(path="gregister", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DirectoryVO> gregister(
					DirectoryDTO dto,
					HttpSession session
			){
		log.debug("gregister({}, {}) invoked.", dto, session);
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		

		DirectoryVO vo = new DirectoryVO(null,
											dto.getDirName(),
											0,
											0,
											null,
											null,
											dto.getGroupNo(),   //그룹X
											user.getUserID(),
											1,
											2);
		
		Objects.requireNonNull(service);
		log.info("service ok");
		this.service.gregister(vo);

		log.info("vo: {}",vo);
		return new ResponseEntity<>(vo, HttpStatus.OK);
	}//gregister
	
	
	@ResponseBody
	@GetMapping(path="gdirNo", produces = MediaType.APPLICATION_JSON_VALUE)
	public Integer gdirNo(@RequestParam("groupNo") Integer groupNo ) {
		
		Objects.requireNonNull(service);
		log.info("service ok");
		DirectoryVO dir = this.service.getdirNo(groupNo);
		
		Integer dirNo = dir.getDirNo();
		log.info("dirNo:{}", dirNo);
		return dirNo;
	}//getDirNo
	
	
	
	
	
	
	
	
	
	@ResponseBody
	@PostMapping(path="modify", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<DirectoryVO> modify(
					DirectoryDTO dto,
					HttpSession session
			){
		log.debug("modify({}, {}) invoked.", dto, session);
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		
		DirectoryVO vo = new DirectoryVO(dto.getDirNo(),
										dto.getDirName(),
										dto.getDirTopNo(),
										dto.getDirLevel(),
										null,
										null,
										null,   //그룹X
										user.getUserID(),
										1,
										1);
		
		this.service.modify(vo);

		log.info("vo: {}",vo);
		return new ResponseEntity<>(vo, HttpStatus.OK);
	}//modify
	
	
	
	
	
	
}//end class
