package org.notive.myapp.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.notive.myapp.domain.UserDTO;
import org.notive.myapp.domain.UserImageDTO;
import org.notive.myapp.domain.UserImageVO;
import org.notive.myapp.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;


@Log4j2
@NoArgsConstructor

@RequestMapping("/user/")
@Controller("userSignupController")
public class UserSignupController {
	
	@Setter(onMethod_=@Autowired)
	private UserServiceImpl userService;
		
	
	// 회원가입 페이지
	@GetMapping("signUp")
	public String signUpGET() throws Exception{
		log.debug("signUpGET() invoked.");
		
		return "/user/signUp";
	} //signUpGET
	
	
	 
	// 회원가입 처리
	@PostMapping("signUp")
	public String signUpPost(UserDTO dto, UserImageDTO imgDto, RedirectAttributes rttrs) throws Exception {
		log.debug("signUpPost({}, {}, rttrs) invoked.",  dto, imgDto);
		
		userService.signUp(dto, imgDto);
		
		String authKey = userService.sendAuthMail(dto.getUserID());
		dto.setUserAuthKey(authKey);
		
		// DB에 authKey 업데이트
		userService.updateAuthKey(dto);
		
		rttrs.addAttribute("result","mail send");
		
		return "redirect:/user/sendAuthMail";
	} //signUpPost
	
	
	
	// 아이디 중복확인
	@RequestMapping("checkID")
	@ResponseBody
	public String checkID(@RequestBody String userID) throws Exception{
		log.debug("checkID(userID) invoked.");
		
		String result;
		
		// " @ " 디코딩
		String decodedUserID = URLDecoder.decode(userID, "utf8");

		int IDCount =  userService.checkID(decodedUserID.replace("=",""));
		
		if(IDCount == 0) {	// 중복 아이디가 없으면
			result = "success";
		} else {
			result = "failure";
		} //if-else
		
		return result;
	} //checkID
	
	
	
	// 인증메일 발송
	@GetMapping("sendAuthMail")
	public String sendAuthMail() {
		log.debug("sendAuthMail() invoked.");
		
		return "user/sendAuthMail";
	} //sendAuthMail
	
	
	
	// 회원 인증상태 업데이트
	@GetMapping("signUpConfirm")
	public String signUpConfirm(UserDTO dto, Model model) throws Exception {
		log.debug("signUpConfirm({}, {}) invoked.", dto, model);
		
		userService.updateAuthStatus(dto);
		
		return "/user/signUpConfirm";
	}//signUpConfirm
	
	
	
	//-----------------------프사 업로드
	// 이미지가 저장될 yyyy-MM -dd 폴더 경로 String으로 생성
	private String getFolder() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		Date date = new Date();
		log.info("date : {}", date);
		
		String str = sdf.format(date);
		log.info(str);
		
		return str.replace("-", File.separator);
	} //getFolder
	
	
	@PostMapping(value = "userImageUpload", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	// 사진 첨부
	public ResponseEntity<List<UserImageDTO>> userImageUpload(MultipartFile[] uploadFile) {
		log.debug("userImageTestPost(uploadFile) invoked.");
		
		List<UserImageDTO> list = new ArrayList<>();
		String uploadFolder = "C:/Temp/upload";
		
		// 이미지 저장될 폴더 만들기
		String uploadFolderPath = getFolder();
		
		File uploadPath = new File(uploadFolder, uploadFolderPath);
		log.info("upload path: " + uploadPath);
		
		if (uploadPath.exists() == false) {
			uploadPath.mkdirs();
		} //if
		
		for(MultipartFile multipartFile : uploadFile) {
			log.info("--------------------------");
			log.info("UploadFile Name : " + multipartFile.getOriginalFilename());
			log.info("Upload File Size : " + multipartFile.getSize());
			
			UserImageDTO dto = new UserImageDTO();
			
			String uploadFileName = multipartFile.getOriginalFilename();
			
			// IE 브라우저에서의 파일첨부 처리
			uploadFileName = uploadFileName.substring(uploadFileName.lastIndexOf("\\") + 1);
			
			log.info("only file name : " + uploadFileName);
			
			dto.setFileName(uploadFileName);
			
			// 파일명 중복방지 위한 uuid 생성
			UUID uuid = UUID.randomUUID();
			
			uploadFileName = uuid.toString() + "_" + uploadFileName;
			
			try {		
				File saveFile = new File(uploadPath, uploadFileName);

				multipartFile.transferTo(saveFile);
				
				dto.setUuid(uuid.toString());
				dto.setUploadPath(uploadFolderPath);
					
				// 썸네일용 이미지파일 (파일경로 / s_파일명) 생성
				FileOutputStream thumbnail = 
						new FileOutputStream(new File(uploadPath, "s_" + uploadFileName));
				
				Thumbnailator.createThumbnail(multipartFile.getInputStream(), thumbnail, 100, 100);
				
				thumbnail.close();	
				// 파일목록에 더하기
				list.add(dto);
				
			} catch (Exception e) {
				log.error(e.getMessage());
			} //try-catch
			
		}//enhanced for
		
		return new ResponseEntity<>(list, HttpStatus.OK);
		
	} //userImageTestPost
	
	
	@GetMapping("/display")
	@ResponseBody
	// 썸네일 처리
	// 문자열로 파일 경로가 포함된 fileName을 파라미터로 받고, byte[] 를 전송.
	public ResponseEntity<byte[]> getFile(String fileName){
		log.debug("getFile({}) invoked.", fileName);
		
		File file = new File("c:/Temp/upload/" + fileName);
		
		log.info("file : " + file);
		
		ResponseEntity<byte[]> result = null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			// 브라우저에 보내주는 MIME타입이 파일 종류에 따라 달라지므로,
			// probeContentType()을 이용해 적절한 MIME 타입 데이터를 Http헤더 메시지에 포함
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);
		} catch(IOException e) {
			e.printStackTrace();
		} //try-catch
		
		return result;
	} //getFile
	
	
	@PostMapping("/deleteUserImage")
	@ResponseBody
	// 프로필사진 삭제
	public ResponseEntity<String> deleteUserImage(String fileName){
		log.debug("deleteUserImage({}) invoked.", fileName);
		
		File file;
		
		try {
			// 1. 원본파일 삭제
			file = new File("c:/Temp/upload/" + URLDecoder.decode(fileName, "utf8"));			
			
			file.delete();
			
			// 2. 썸네일용 파일 삭제
			String largeFileName = file.getAbsolutePath().replace("s_", "");
			
			log.info("largeFileName : " + largeFileName);
			
			file = new File(largeFileName);
			
			file.delete();

			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} //try-catch
		
		return new ResponseEntity<String>("deleted", HttpStatus.OK);
	} // deleteUserImage
	

	
	
} //end class