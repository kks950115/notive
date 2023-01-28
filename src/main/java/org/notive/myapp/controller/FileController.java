package org.notive.myapp.controller;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.AttachFileDTO;
import org.notive.myapp.domain.AttachFileVO;
import org.notive.myapp.domain.UserVO;
import org.notive.myapp.service.FileService;
import org.notive.myapp.service.TrashService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import net.coobird.thumbnailator.Thumbnails;

@Log4j2
@NoArgsConstructor

@RequestMapping("/file/")
@Controller
public class FileController {
	
	@Autowired
	private FileService service;
	
	@Autowired
	private TrashService trashService;
	
	public static final String loginKey = "__LOGIN__";
	
	
	//첫화면
	@GetMapping("list")
	public String list(@RequestParam("dirNo") Integer dirNo, Model model) {
		log.debug("list() invoked.");
		model.addAttribute("dirNo",dirNo);
		return "section/dirFile";
	}//list
		
	
	@GetMapping(
			path="getlistAll",
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	@ResponseBody
	public ResponseEntity<List<AttachFileVO>> getlistAll(
			Model model,
			HttpSession session
			) {
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		
		Objects.requireNonNull(this.service);
		List<AttachFileVO> list = this.service.getlistAll(user.getUserID(), 1);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}//getList
	
	@GetMapping(
			path="getlist",
			produces = MediaType.APPLICATION_JSON_VALUE
		)
	@ResponseBody
	public ResponseEntity<List<AttachFileVO>> getlist(
						@RequestParam("dirNo") Integer dirNo, 
						Model model,
						HttpSession session
			) {
		log.debug("getlist({}, model) invoked.", dirNo);
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		
		Objects.requireNonNull(this.service);
		List<AttachFileVO> list = this.service.getlist(dirNo, user.getUserID(), 1);
  	
		model.addAttribute("__FILE__",list);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}//getList
	
	
	
	//검색
	@GetMapping(
			path="getSearchList",
			produces = MediaType.APPLICATION_JSON_VALUE
		)
	@ResponseBody
	public ResponseEntity<List<AttachFileVO>> getSearchList(String userId, String search, HttpSession session, Model model) {
		log.debug("getSearchList({},{}, model) invoked.", userId, search);
		
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		
		Objects.requireNonNull(this.service);
		List<AttachFileVO> list = this.service.getSearchList(user.getUserID(), search);

		model.addAttribute("__S__FILE__",list);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	} //getSearchList
	

	//업로드 폼
	@GetMapping({ "uploadForm", "uploadAjax" })
	public void uploadForm() {
		log.debug("uploadForm() invoked.");
		
	}//upload
	
	
	//업로드
	@ResponseBody
	@PostMapping(path="upload", produces=MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<AttachFileVO>> upload(
			@RequestParam("uploadFile") List<MultipartFile> files,
			@RequestParam("dirNo") Integer dirNo,
			HttpSession session
		) {
		log.debug("upload({}) invoked.", files);
		 
		UserVO user =(UserVO)session.getAttribute(loginKey);
		List<AttachFileVO> list = new ArrayList<>();
		
		String uploadDir = "C:/Temp/file_upload/";

		//폴더생성
		File uploadPath = new File(uploadDir, getFolder());
		log.info("\t+ uploadPath: {}", uploadPath);
		
		if(uploadPath.exists() == false) {
			
			uploadPath.mkdirs();		
		}//if
		
		
		
		
		files.forEach(f->{
			
			AttachFileDTO fileDTO = new AttachFileDTO();
			
			log.info("-----------------------------------");
			log.info("upload File Type: {}", f.getContentType());
			log.info("upload File Name: {}", f.getOriginalFilename());
			log.info("upload File Size: {}", f.getSize());

			fileDTO.setFileSize((int) f.getSize());								//DTO-파일크기 저장	
			fileDTO.setDirNo(dirNo);
			
			
			//UUID로 파일명 변경하여 업로드(중복파일)
			String fileUUID =f.getOriginalFilename();
			
			fileUUID = fileUUID.substring(fileUUID.lastIndexOf("\\")+1);
			fileDTO.setFileName(fileUUID);										//DTO-원래 파일이름 저장
			
			UUID uuid = UUID.randomUUID();			
			fileUUID = uuid.toString()+"_"+fileUUID;
			
			log.info("\t+ fileUUID: {}", fileUUID);
			
			
			//파일 업로드
			if(f.getSize()>0) {
				
				try{			
					byte[] filedata = f.getBytes();  //바가지
					
					File file = new File(uploadPath,fileUUID);
					log.info("\t+ file: {}", file);
					
					FileOutputStream fos = new FileOutputStream(file);//append default는 false!! 새로만들때마다 기존꺼없앰
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					
					fileDTO.setFileUuid(uuid.toString()); 						//DTO-UUID저장
					fileDTO.setFilePath(getFolder());							//DTO-폴더경로 저장
					fileDTO.setFileType('N');									//DTO-일반파일 디폴트 저장
					//이미지파일 체크 후 썸네일 생성
					if(checkImageType(file)) { 
						fileDTO.setFileType('I');								//DTO-이미지 여부 저장
						
						BufferedImage image = ImageIO.read(f.getInputStream());
		                double getWidth = image.getWidth();
		                double getHeight = image.getHeight();
		                
		                double resizeRatio = getWidth / getHeight;

		                // 지정한 높이, 높이와 비율로 구한 너비
		                int mediumHeight = 2000;             
		                int mediumWidth = (int) (resizeRatio * mediumHeight);
		                
						FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath,"s_"+fileUUID));//썸네일 이미지생성
						BufferedOutputStream bosThumbnail = new BufferedOutputStream(thumbnail);
						
						try(thumbnail; bosThumbnail;){
						
							Thumbnailator.createThumbnail(f.getInputStream(), bosThumbnail, mediumHeight, mediumWidth);
							log.info(mediumHeight + "+" + mediumWidth);
							bosThumbnail.flush();
						}//try-with
					}//if-image file checked..
					
					
					//파일출력
					try( fos; bos;  ){
						bos.write(filedata);
			
						bos.flush();
					}//try-with-resources
					
 
					
					
					AttachFileVO fileVO = new AttachFileVO(null,
							fileDTO.getFileName(),
							fileDTO.getFileUuid(),
							fileDTO.getFilePath(),
							fileDTO.getFileType(),
							fileDTO.getFileSize(),
							null,
							null,
							fileDTO.getDirNo(), 
							null,
							user.getUserID(),
							null,
							null);
					
					
					this.service.register(fileVO);
					
					list.add(fileVO); //리스트에 VO 추가
				}catch(IOException e) {
					e.printStackTrace(); // 위로 던지면 메소드의 쓰로우절을 가진다. << 가 좋지만, 지금은 foreach구문 안에서 수행하는 부분이라 e.print로 해야함. 
				}//try-catch			
			}//of	
			
		});//foreach
		
		
		list.forEach(log::info);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}//upload
	
	
	
	
	
	//썸네일전송
	@ResponseBody
	@GetMapping("display")
	public ResponseEntity<byte[]> getFile(String fileName) {
		log.debug("getFile({}) invoked.", fileName);
		
		File file = new File("c:/Temp/file_upload/"+fileName);
		log.info("\t+ file: {}", file);
		
		ResponseEntity<byte[]> result=null;
		
		try {
			HttpHeaders header = new HttpHeaders();
			
			header.add("Content-Type", Files.probeContentType(file.toPath()));
			result = new ResponseEntity<byte[]>(
					FileCopyUtils.copyToByteArray(file), header, HttpStatus.OK);

		}catch(IOException e) {
			e.printStackTrace();
			
		}//try-catch
		
	
		return result;
	}//getFile
	
	
	
	
	
	
	
	//파일다운로드
	@ResponseBody
	@GetMapping(path="download", produces= {//MediaType.APPLICATION_OCTET_STREAM_VALUE,
											MediaType.ALL_VALUE})
	public ResponseEntity<Resource> downloadFile(
			@RequestHeader("User-Agent") String userAgent,	//IE처리를 위해 디바이저 정보 알수있는 헤더 이용
			String fileName 
		) {
		log.debug("downloadFile({}) invoked.", fileName );
		
		
		
		Resource resource = new FileSystemResource("c:/Temp/file_upload/"+fileName);
		log.info("\t+ resource: {}", resource);
		
		if(resource.exists() == false) { 					//파일이 존재하지 않는다면,
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
			
		}//if
		
		
		String resourceName = resource.getFilename();		//UUID 파일이름
			
		
		String resourceNameOriginalName = 
				resourceName.substring(resourceName.indexOf("_")+1); //UUID제거 후 원래 파일이름 얻기
		log.info("\t+ resourceNameOriginalName: {}",resourceNameOriginalName); 
		
		HttpHeaders headers = new HttpHeaders();
		try {
			
			String downloadName = null;
			
			if(userAgent.contains("Trident")) { 					//IE browser 라면,
				log.info("\t+ IE browser");
			
				downloadName= URLEncoder.
								encode(resourceNameOriginalName, "UTF-8").
								replaceAll("\\+", " ");
			}else if(userAgent.contains("Edge")) {					//Edge browser 라면,
				log.info("\t+ Edge browser");
				
				downloadName= URLEncoder.encode(resourceNameOriginalName, "UTF-8");
				
				log.info("Edge name: {}", downloadName);
				
			}else {													//그 외 브라우저라면,
				log.info("\t+ 그외 browser");
				
				downloadName= new String( resourceNameOriginalName.getBytes("UTF-8"), "ISO-8859-1");
				
			}// 다중 if-else
			
			headers.add(
					"Content-Disposition", 
					"attachment; filename="+ downloadName);
			
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			
		}//try-catch
		
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}//downloadFile
	
	
	
	
	
	
	
	
	//파일삭제!!!
	@ResponseBody
	@PostMapping("deleteFile")
	public String deleteFile(
						@RequestParam("fileNo") Integer fileNo,
						@RequestParam("dirNo") Integer dirNo,
						HttpSession session
				) {
		log.debug("deleteFile({}, {}, {}) invoked.", fileNo, dirNo, session);
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		
		AttachFileVO fileVO = this.service.getFileVO(fileNo, user.getUserID());
 
		
		try {
 					this.service.remove(fileVO);
 
		}catch(Exception e) {
			e.printStackTrace();	
		}//try-catch
		
		return "section/dirFile";
	}//deleteFile
	
	
	
	
	
	
	
	
	
	//폴더생성 메소드
	private String getFolder() {
		log.debug("getFolder() invoked.");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String str = sdf.format(date);
		
		return str.replace("/", File.separator);
	}//getFolder
	
	
	
	
	
	//이미지 체크 메소드
	private boolean checkImageType(File file) {
		log.debug("checkImageType({}) invoked.", file);
		
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			log.info("\t+ contentType: {}", contentType);
			
			if(contentType!=null && contentType.startsWith("image")) { 	//이미지라면
				return true;						// true 반환
			}else {									//이미지가 아니라면(null포함)
				log.info("\t+ return false");
				return false;						// false 반환	
			}//if-else			
			
		} catch (IOException e) {
			e.printStackTrace();
		}//try-catch
		
		
		
		
		return false;
	}//checkImageType
	
	
}//end class
