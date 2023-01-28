package org.notive.myapp.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.AdminVO;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Log4j2
@NoArgsConstructor

// 역할 : 오직 로그인,로그아웃 처리에만!! 특화된 인터셉터. 인증처리와 관련된 인터셉터가 아니다!
@Component
public class AdminLoginInterceptor 
	implements HandlerInterceptor {
	
	
	public static final String adminLoginKey = "__ADMIN__";
	
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
		
		AdminVO adminVO = (AdminVO)session.getAttribute(AdminLoginInterceptor.adminLoginKey);
		
		if(adminVO != null) {	//로그인 성공한 상태라면
			// 기존에 session scope에 있는 AdminVO 객체를 삭제 
			session.removeAttribute(AdminLoginInterceptor.adminLoginKey);
			
			log.info("\t+ 기존에 바인딩 된 AdminVO 객체를 Session Scope에서 삭제 완료.");
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
		
		AdminVO adminVO = (AdminVO) modelAndView.getModelMap().get(adminLoginKey);
//		String tempUserGrade = (String)modelAndView.getModelMap().get(userGrade);
		log.info("--- adminVO: " + adminVO);
//		log.info("--- tempUserGrade: " + tempUserGrade);
		
		if(adminVO != null) {	// 로그인 성공했다면			
			//------------------------------------------------//
			// 1. Session scope에 로그인 정보로, UserVO 객체를 바인딩
			//------------------------------------------------//
			session.setAttribute(adminLoginKey, adminVO);
			
			log.info("\t+ 1. AdminVO 객체를 Session Scope에 바인딩 완료.");
			
			//------------------------------------------------//
			// 2. 원래 사용자의 Request URI를 복구하여, 요청대로 이동시킴
			//------------------------------------------------//
//			String originalRequestURI = (String) session.getAttribute(AdminAuthInterceptor.requestURIKey);
//			String originalQueryString = (String) session.getAttribute(AdminAuthInterceptor.queryStringKey);
//			
//			if(originalRequestURI != null) { //원래의 요청URI가 있었다면... (**get방식만 고려함**)
//				response.sendRedirect(
//						originalRequestURI +
//						originalQueryString != null && !"".equals(originalQueryString) ? 
//								("?"+originalQueryString) : ""
//				);	//조건이 false라면 empty String 반환
//				
//				log.info("\t+ 2. 원래의 요청URI({})로 강제 이동시킴.", originalRequestURI);
//			} else { // 처음부터 사용자가 로그인 화면에서 로그인을 수행했다면...
//				log.info("\t+ 2. 사용자는 로그인부터 수행해서 들어옴.");
//
//			} //if-else			
			
	} // if

  } //postHandle
	
} //end class
