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
	
	
	//?????????
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
	
	
	
	//??????
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
	

	//????????? ???
	@GetMapping({ "uploadForm", "uploadAjax" })
	public void uploadForm() {
		log.debug("uploadForm() invoked.");
		
	}//upload
	
	
	//?????????
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

		//????????????
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

			fileDTO.setFileSize((int) f.getSize());								//DTO-???????????? ??????	
			fileDTO.setDirNo(dirNo);
			
			
			//UUID??? ????????? ???????????? ?????????(????????????)
			String fileUUID =f.getOriginalFilename();
			
			fileUUID = fileUUID.substring(fileUUID.lastIndexOf("\\")+1);
			fileDTO.setFileName(fileUUID);										//DTO-?????? ???????????? ??????
			
			UUID uuid = UUID.randomUUID();			
			fileUUID = uuid.toString()+"_"+fileUUID;
			
			log.info("\t+ fileUUID: {}", fileUUID);
			
			
			//?????? ?????????
			if(f.getSize()>0) {
				
				try{			
					byte[] filedata = f.getBytes();  //?????????
					
					File file = new File(uploadPath,fileUUID);
					log.info("\t+ file: {}", file);
					
					FileOutputStream fos = new FileOutputStream(file);//append default??? false!! ????????????????????? ???????????????
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					
					fileDTO.setFileUuid(uuid.toString()); 						//DTO-UUID??????
					fileDTO.setFilePath(getFolder());							//DTO-???????????? ??????
					fileDTO.setFileType('N');									//DTO-???????????? ????????? ??????
					//??????????????? ?????? ??? ????????? ??????
					if(checkImageType(file)) { 
						fileDTO.setFileType('I');								//DTO-????????? ?????? ??????
						
						BufferedImage image = ImageIO.read(f.getInputStream());
		                double getWidth = image.getWidth();
		                double getHeight = image.getHeight();
		                
		                double resizeRatio = getWidth / getHeight;

		                // ????????? ??????, ????????? ????????? ?????? ??????
		                int mediumHeight = 2000;             
		                int mediumWidth = (int) (resizeRatio * mediumHeight);
		                
						FileOutputStream thumbnail = new FileOutputStream(new File(uploadPath,"s_"+fileUUID));//????????? ???????????????
						BufferedOutputStream bosThumbnail = new BufferedOutputStream(thumbnail);
						
						try(thumbnail; bosThumbnail;){
						
							Thumbnailator.createThumbnail(f.getInputStream(), bosThumbnail, mediumHeight, mediumWidth);
							log.info(mediumHeight + "+" + mediumWidth);
							bosThumbnail.flush();
						}//try-with
					}//if-image file checked..
					
					
					//????????????
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
					
					list.add(fileVO); //???????????? VO ??????
				}catch(IOException e) {
					e.printStackTrace(); // ?????? ????????? ???????????? ??????????????? ?????????. << ??? ?????????, ????????? foreach?????? ????????? ???????????? ???????????? e.print??? ?????????. 
				}//try-catch			
			}//of	
			
		});//foreach
		
		
		list.forEach(log::info);
		
		return new ResponseEntity<>(list, HttpStatus.OK);
	}//upload
	
	
	
	
	
	//???????????????
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
	
	
	
	
	
	
	
	//??????????????????
	@ResponseBody
	@GetMapping(path="download", produces= {//MediaType.APPLICATION_OCTET_STREAM_VALUE,
											MediaType.ALL_VALUE})
	public ResponseEntity<Resource> downloadFile(
			@RequestHeader("User-Agent") String userAgent,	//IE????????? ?????? ???????????? ?????? ???????????? ?????? ??????
			String fileName 
		) {
		log.debug("downloadFile({}) invoked.", fileName );
		
		
		
		Resource resource = new FileSystemResource("c:/Temp/file_upload/"+fileName);
		log.info("\t+ resource: {}", resource);
		
		if(resource.exists() == false) { 					//????????? ???????????? ????????????,
			return new ResponseEntity<Resource>(HttpStatus.NOT_FOUND);
			
		}//if
		
		
		String resourceName = resource.getFilename();		//UUID ????????????
			
		
		String resourceNameOriginalName = 
				resourceName.substring(resourceName.indexOf("_")+1); //UUID?????? ??? ?????? ???????????? ??????
		log.info("\t+ resourceNameOriginalName: {}",resourceNameOriginalName); 
		
		HttpHeaders headers = new HttpHeaders();
		try {
			
			String downloadName = null;
			
			if(userAgent.contains("Trident")) { 					//IE browser ??????,
				log.info("\t+ IE browser");
			
				downloadName= URLEncoder.
								encode(resourceNameOriginalName, "UTF-8").
								replaceAll("\\+", " ");
			}else if(userAgent.contains("Edge")) {					//Edge browser ??????,
				log.info("\t+ Edge browser");
				
				downloadName= URLEncoder.encode(resourceNameOriginalName, "UTF-8");
				
				log.info("Edge name: {}", downloadName);
				
			}else {													//??? ??? ??????????????????,
				log.info("\t+ ?????? browser");
				
				downloadName= new String( resourceNameOriginalName.getBytes("UTF-8"), "ISO-8859-1");
				
			}// ?????? if-else
			
			headers.add(
					"Content-Disposition", 
					"attachment; filename="+ downloadName);
			
		}catch(UnsupportedEncodingException e) {
			e.printStackTrace();
			
		}//try-catch
		
		
		return new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
	}//downloadFile
	
	
	
	
	
	
	
	
	//????????????!!!
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
	
	
	
	
	
	
	
	
	
	//???????????? ?????????
	private String getFolder() {
		log.debug("getFolder() invoked.");
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Date date = new Date();
		String str = sdf.format(date);
		
		return str.replace("/", File.separator);
	}//getFolder
	
	
	
	
	
	//????????? ?????? ?????????
	private boolean checkImageType(File file) {
		log.debug("checkImageType({}) invoked.", file);
		
		try {
			String contentType = Files.probeContentType(file.toPath());
			
			log.info("\t+ contentType: {}", contentType);
			
			if(contentType!=null && contentType.startsWith("image")) { 	//???????????????
				return true;						// true ??????
			}else {									//???????????? ????????????(null??????)
				log.info("\t+ return false");
				return false;						// false ??????	
			}//if-else			
			
		} catch (IOException e) {
			e.printStackTrace();
		}//try-catch
		
		
		
		
		return false;
	}//checkImageType
	
	
}//end class
