package org.notive.myapp.controller;

import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.UserDTO;
import org.notive.myapp.domain.UserImageDTO;
import org.notive.myapp.domain.UserImageVO;
import org.notive.myapp.domain.UserVO;
import org.notive.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
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
@RequestMapping("/user/")
public class UserLoginController {

   @Setter(onMethod_=@Autowired)
   private UserService userService;
   
   @Autowired
   private BCryptPasswordEncoder pwEncoder;
   
   public static final String loginKey = "__LOGIN__";
   public static final String userGrade = "__USER_GRADE__";
   public static final String rememberMeKey = "__REMEMBER_ME__";
   
   //---------------로그인 처리
   @PostMapping("loginPost")
   public void loginPost(UserDTO dto, Model model, HttpSession session, RedirectAttributes rttrs) throws Exception{
      log.debug("loginPost(loginDTO, httpSession, model) invoked.");
         
      UserVO userVO = this.userService.login(dto);
      
      // 해당되는 사용자가 있고 and 입력한 비밀번호가 DB의 암호화된 비밀번호와 일치하면
      if(userVO != null
            && 
            pwEncoder.matches(dto.getUserPass(), userVO.getUserPass())) {
         
         // request scope에 바인딩 (회원정보, 회원등급)
         model.addAttribute(loginKey, userVO);
         
         // remember-me 기능을 위한 로직처리
         if(dto.isRememberMe()) {
            int maxAge = 1000*60 * 60 * 24 * 7; // 일주일(7일) 쿠키는 초 단위이지만, 오라클의 TimeStamp는 ms 단위임
            
            String userID = dto.getUserID();
            String rememberMe = session.getId();
            
            log.info("\t+ rememberMe :" + rememberMe);
            
            Date rememberMeAge = 
                  new Date(System.currentTimeMillis() + maxAge);
            
            int affectedLines = 
                  this.
                     userService.
                        modifyUserWithRememberMe(
                           userID, 
                           rememberMe,
                           rememberMeAge
                           );
            log.info("\t+ affectedLines: {}", affectedLines);
         } //if: remember-me 옵션이 on이라면...
  
      } //if: 로그인 성공했다면...
   } //loginPOST
   
   
   //---------------로그아웃
   @GetMapping("logout")
   public String logout(HttpSession session, HttpServletRequest req) throws Exception {   
      log.debug("logout({}) invoked.", session);
      
      // 1. 로그아웃을 수행한 사용자 정보를 출력
      UserVO user = (UserVO) session.getAttribute(loginKey);
      String userID = user.getUserID();
      
      log.info("\t+ userID: {}", userID);
      
      // 2. 사용자의 remember-me관련 컬럼 2개 null로 변경
      this.userService.modifyUserWithoutRememberMe(userID);
      
      // 3. 세션 객체 무효화
      session.invalidate();
      log.debug("\t+ Session destroyed.");
      
      // 4. 다시 인트로 페이지로 이동
      return "redirect:/intro";
   } //logout
   
   
   //---------------마이페이지 접근 (로그인된 회원정보 재확인)
   @PostMapping("userCheck")
   public String userCheck(UserDTO dto, HttpSession session, Model model) throws Exception{
      log.debug("userCheck() invoked.");
      
      UserVO loginUser = (UserVO)session.getAttribute(loginKey);
      
      String loginUserID = loginUser.getUserID();
      String loginUserPass = loginUser.getUserPass();
      
      String inputID = dto.getUserID();
      String inputPass = dto.getUserPass();
      
      if (loginUserID.equals(inputID)      // session에 저장되어있는 아이디(로그인된 아이디)와, 입력된 아이디가 일치하고
            && pwEncoder.matches(inputPass, loginUserPass)){   // 비밀번호도 일치하면
         
         return "/section/myPage";   //myPage로 이동

		} else {

         model.addAttribute("result", "failure");
         
         return "/section/userCheckPage";   //정보 다시 입력받기
      } //if-else
   } //userCheck
   
   
	
	//--------------유저 프로필사진 조회
	@GetMapping(value="/getUserImage", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public ResponseEntity<UserImageVO> getUserImage(String userID) throws Exception{
		log.debug("getUserImage({}) invoked.", userID);
		
		return new ResponseEntity<>(userService.getUserImage(userID), HttpStatus.OK);
	} //getUserImage
	
	
	
	//--------------유저 프로필사진 수정
	@PostMapping("modifyUserImage")
	@ResponseBody
	public Map<String, String> modifyUserImage(
//			@RequestBody Map<String,Object> map,
			String uuid, 
			String uploadPath, 
			String fileName, 
			HttpSession session) throws Exception{
		log.debug("modifyUserImage({}, {}, {}, session) invoked.", uuid, uploadPath, fileName);
//		log.debug("modifyUserImage({}, session) invoked.", map);
		
//		if(map == null) { //새롭게 첨부된 이미지 파일이 없으면
		if(uuid == null) { //새롭게 첨부된 이미지 파일이 없으면
			log.debug("\t ***** uuid is null");
			
			return null;
		} //메소드 종료
		
		UserImageDTO dto = new UserImageDTO();
		
		dto.setUuid(uuid);
		dto.setUploadPath(uploadPath);
		dto.setFileName(fileName);
		
		this.userService.modifyUserImage(dto, session);
		
		Map<String, String> map = new HashMap<>();
		
		map.put("uuid", uuid);
		map.put("fileName", fileName);
		map.put("fileName", fileName);
//		
		return map;
	} //modifyUserImage
	
	
	//------------------유저 프로필사진 삭제
	@PostMapping("removeUserImage")
	@ResponseBody
	public void removeUserImage(String fileName, String uuid) throws Exception{
		log.debug("removeUserImage({}, {}) invoked.", fileName, uuid);
		
		this.userService.removeUserImage(fileName, uuid);
		
	} //removeUserImage 
	
	
   
   //----------------비밀번호 수정
   @PostMapping("modifyUserPass") 
   public String modifyUserPass(UserDTO dto, HttpSession session, Model model, RedirectAttributes rttrs) throws Exception {
      log.debug("modifyUserPass(dto, session, model) invoked.");
      
      UserVO loginUser = (UserVO)session.getAttribute(loginKey);
      log.debug("loginUser : ${}", loginUser);
      
      String loginUserPass = loginUser.getUserPass();
      
      String curPass = dto.getUserPassCur();
      
      if(pwEncoder.matches(curPass,  loginUserPass))   { // 현재 비밀번호가 로그인된 정보와 일치하고,
         
         if(dto.getUserPass().equals(dto.getUserPassConfirm())) {   // 새로 입력한 두 비밀번호가 일치하면..
            
            this.userService.modifyUserPass(session, dto);   // 비밀번호를 수정!
            
            model.addAttribute("result", "success");
            
            return "redirect:/user/myPage";
         } else {   // 새로 입력한 두 비밀번호가 일치하지 않으면..
        	log.info("새로 입력한 비밀번호가 일치하지 않음");
        	
            rttrs.addAttribute("result", "passCheck-failure");
            
            return "redirect:/user/modifyUserPassPage";   // 비밀번호 다시 입력받기
         } //inner if-else : 새로 입력한 두 비밀번호 일치 여부
         
      } else { //현재 비밀번호가 로그인된 정보와 일치하지 않으면..
    	  log.info("현재 비밀번호와 일치하지 않음");
    	  
         model.addAttribute("result", "failure");
         
         return "redirect:/user/modifyUserPassPage";   // 비밀번호 다시 입력받기
      } //outer if-else : 현재 비밀번호 일치 여부
   } //modifyUserPass
   
   
   //----------------이름 수정
   @PostMapping("modifyUserName")
   @ResponseBody
   public void modifyUserName(@RequestBody String userName, HttpSession session, Model model) throws Exception {
      log.debug("modifyUserName({},{}) invoked.", session, userName);
      
      assert(userName != null);
      log.info("userName : {}", userName);
      
      String decodedUserName = 
            URLDecoder.decode(userName, "utf8")
            .replace("=", "");

      log.info(decodedUserName);
      
      UserVO userVO = this.userService.modifyUserName(session, decodedUserName);
      assert(userVO != null);
      log.info("\t ++userVO: {}", userVO);
      
      // session scope에 바인딩 되어있던 기존의 유저 정보를 삭제하고,
      // 업데이트된 정보로 다시 바인딩
      session.removeAttribute(loginKey);
      session.setAttribute(loginKey, userVO);   
   } //modifyUserInfo
   
   
   //----------------생일 수정
   @PostMapping("modifyUserBday")
   public String modifyUserBday(String year, String month, String day, HttpSession session) throws Exception{
      log.debug("modifyUserBday({}, {}, {}, {}) invoked.", session, year, month, day);
         
      UserVO userVO = this.userService.modifyUserBday(session, year, month, day);
      
      // session scope에 바인딩 되어있던 기존의 유저 정보를 삭제하고,
      // 업데이트된 정보로 다시 바인딩
      session.removeAttribute(loginKey);
      session.setAttribute(loginKey, userVO);   

      return "redirect:/user/myPage";
   } //modifyUserInfo
   
   
   //----------------아이디 찾기
   @GetMapping("findUserID")
   public String findUserID(UserDTO dto, Model model) throws Exception {
      log.debug("findUserID(dto, model) invoked.");
      
      String userID = this.userService.findUserID(dto);
      
      if (userID != null) {   //입력받은 정보와 일치하는 사용자가 있으면
         log.info("--userID : " + userID);
         
         model.addAttribute("__userID__", userID);   //request scope에 바인딩
         
         return "/user/findUserIDSuccess";
         
      } else { //입력받은 정보와 일치하는 사용자가 없으면
         log.info("failed");
         
         model.addAttribute("result","failure");
         
         return "redirect:/user/findUserIDPage";
      } //if-else
   } //findUserID
   
   
   //-----------------비밀번호 찾기
   @PostMapping("findUserPass")
   public String findUserPass(UserDTO dto, Model model) throws Exception{
      log.debug("findUserPass(dto, model) invoked.");
      
      // 입력받은 정보와 일치하는 사용자가 있는지 검색
      String userID = this.userService.findUserID(dto);
      
      if (userID != null) {   //입력받은 정보와 일치하는 사용자가 있으면
         this.userService.findUserPass(dto); //임시 비밀번호 발급
         
         return "/user/findUserPassSuccess";
         
      } else { //입력받은 정보와 일치하는 사용자가 없으면         
         model.addAttribute("result","failure");
         
         return "/user/findUserPassPage";   //다시 입력받기
      } //if-else
   } //findUserPass
   
   
   //-----------------회원탈퇴
   @PostMapping("withdraw")
   public String withdraw(UserDTO dto, HttpSession session) throws Exception{
      log.debug("withdraw({}, session) invoked.", dto);
      
      UserVO userVO = (UserVO)session.getAttribute(loginKey);
      String userID = userVO.getUserID();
      
      userService.withdraw(userID);
      
      return "redirect:/intro";
   } //withdraw
   
} //end class