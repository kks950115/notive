package org.notive.myapp.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.UserVO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Log4j2
@NoArgsConstructor

// 역할 : 오직 로그인,로그아웃 처리에만!! 특화된 인터셉터. 인증처리와 관련된 인터셉터가 아니다!
@Component
public class LoginInterceptor 
	implements HandlerInterceptor {
	
	
	public static final String loginKey = "__LOGIN__";
	public static final String rememberMeKey = "__REMEMBER_ME__";
	public static final String userGrade = "__USER_GRADE__";
	
	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler
		) throws Exception {
		log.debug("====================================================================");
		log.debug("1. preHandle(req, res, {}) invoked.", handler.getClass().getName());
		log.debug("====================================================================");
		
		// 사용자가 로그인 성공한 상태인지 체크
		HttpSession session = request.getSession();
		
		UserVO userVO = (UserVO)session.getAttribute(LoginInterceptor.loginKey);
		
		if(userVO != null) {	//로그인 성공한 상태라면
			// 기존에 session scope에 있는 UserVO 객체를 삭제 
			session.removeAttribute(LoginInterceptor.loginKey);
			
			log.info("\t+ 기존에 바인딩 된 UserVO 객체를 Session Scope에서 삭제 완료.");
		} //if
	
		return true;
	}//preHandle

	@Override
	public void postHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler,
			ModelAndView modelAndView
		) throws Exception {
		log.debug("====================================================================");
		log.debug("2. postHandle(req, res, {}, {}) invoked.", handler, modelAndView);
		log.debug("====================================================================");
		
		// Session scope에 UserVO객체를 바인딩하는 작업
		HttpSession session = request.getSession();
		
		UserVO userVO = (UserVO) modelAndView.getModelMap().get(loginKey);
		log.info("--- userVO: " + userVO);
		
		if(userVO != null) {	// 로그인 성공했다면			
			//------------------------------------------------//
			// 1. Session scope에 로그인 정보로, UserVO 객체를 바인딩
			//------------------------------------------------//
			session.setAttribute(loginKey, userVO);
			
			log.info("\t+ 1. UserVO 객체를 Session Scope에 바인딩 완료.");
			
			//------------------------------------------------//
			// 2. Remember-Me 기능 처리
			//------------------------------------------------//
			// 조건 : (1) 로그인 성공(이 조건은 if블록 안에서 이미 만족)
			//		 (2) Remember-Me 옵션 체크
			String rememberMe = request.getParameter("rememberMe");
			if(rememberMe != null) {	//Remember-Me기능처리 해야 함
				
				log.info("\t+ 3. Remember-Me 기능 처리...");
				
				//이 기능은 쿠키(Cookie)를 사용해야 함.
				// (1) 새로운 쿠키를 생성 (for Remember-Me 기능)
				// (2) Response 문서의 헤더에 저장해서 응답으로 웹브라우저에 전송
				// (3) 웹브라우저는 응답으로 받는 쿠키들은 모두 파일로 보관
				// (4) 다시 동일 웹사이트로 새로운 Request를 보낼 때마다, 
				//	   해당 웹사이트와 연관된 쿠키를 Request의 헤더에 담아서 항상 전송하게 되어있다! (****)
				
				String sessionID = session.getId(); //웹브라우저에 할당된 이름 (=세션ID)
				
				Cookie rememberMeCookie = 
						new Cookie(rememberMeKey, sessionID);
				
				// 우리가 만든 데이터 조각인 쿠키에 대한 설정을 수행
				rememberMeCookie.setMaxAge(60*60*24*7);	// 초 단위로 쿠키의 유효기간 설정
				rememberMeCookie.setPath("/");
				
				response.addCookie(rememberMeCookie);
			
				log.info("\t+ rememberMeKey: " + rememberMeKey);
				log.info("\t 응답문서의 헤더에 rememberMeCookie 쿠키설정 완료");
			} //if
			
		} else { //로그인에 실패했다면...
			modelAndView = modelAndView.addObject("result", "failure");
			
			response.sendRedirect("/user/login");
			
			log.info("\t+1. 로그인 실패 - 다시 로그인 창으로 되돌림." );
		} //if-else
	}//postHandle

	
} //end class
