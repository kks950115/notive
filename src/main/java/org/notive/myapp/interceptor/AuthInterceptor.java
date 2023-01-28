package org.notive.myapp.interceptor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.UserVO;
import org.notive.myapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.util.WebUtils;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;



@Log4j2
@NoArgsConstructor

// 역할 : 로그인 성공 여부를 체크하는 interceptor.
@Component
public class AuthInterceptor 
	implements HandlerInterceptor {
	
	
	public static final String loginKey = "__LOGIN__";
	public static final String requestURIKey = "__REQUEST_URI__";
	public static final String queryStringKey = "__QUERYSTRING__";
	
	@Autowired
	private UserService service;
	
	
	@Override
	public boolean preHandle(
			HttpServletRequest request, 
			HttpServletResponse response, 
			Object handler
		) throws Exception {
		log.debug("====================================================================");
		log.debug("1. preHandle(req, res, {}) invoked.", handler.getClass().getName());
		log.debug("====================================================================");
		
		// 이미 로그인에 성공한 유저인지 아닌지를 판단 ==> 인증(Authentication)
		HttpSession session = request.getSession();	//매개변수 없는 getSession은, session 이 있으면 반환하고 없으면 새로 만들어서 반환. 따라서 반드시 결과값이 반환된다.
		UserVO user = (UserVO)session.getAttribute(loginKey);
		
		if(user!= null) { 	//이전에 이미 로그인 한 사용자라면
			log.info("Authenticated...OK!");
			
			return true;	// 이전에 로그인 한 사용자가 아니라면
		} else {		  	// 현재 요청이 컨트롤러로 가지 못한다
			
			log.info("user: {}", user);
			
			//----------------------------------------------------------//
			// 1. Original Request URI + 전송 파라미터도 함께 저장 (Session Scope)
			//----------------------------------------------------------//
//			post는 화면이 먼저 뜬 후에 버튼을 클릭하든지 하므로 문제가 되지 않는다.
//			get은 버튼을 클릭해서 화면을 이동해야 하기 때문에 requestURI와 전송파라미터(즉 query String)을 저장해야 함.
			String originalRequestURI = request.getRequestURI();	//원래의 요청 URI획득
			String originalQueryString = request.getQueryString();	//원래의 Query String획득
			
			session.setAttribute(requestURIKey, originalRequestURI);
			session.setAttribute(queryStringKey, originalQueryString);
			
			//----------------------------------------------------------//
			// 2. To authentication a user through Remember-Me feauture.
			//----------------------------------------------------------//
			Cookie rememberMeCookie = WebUtils.getCookie(request, LoginInterceptor.rememberMeKey);
			
			if(rememberMeCookie != null) { //이번 요청을 보내기 이전에, 이미 로그인에 성공했고, remember-me 옵션을 on 시킨 사용자이다!
				String rememberMe = rememberMeCookie.getValue();
				log.info("\t+ prevSessionID: {} ", rememberMe);
				
				// 현재의 SessionID로 유지되는 SEssion Scope에 UserVO객체를 생성해서 바인딩 등록
				// UserService 객체의 findUserByRememberMe() 메소드로 이전 Session ID로 
				// 사용자 정보를 select해서, UserVO객체를 얻고, 이 UserVO 객체를 Session Scope에 바인딩해서
				// 마치 로그인창에서 로그인한 것과 동일한 결과를 생성해냄
				
				user = this.service.findUserByRememberMe(rememberMe);
				
				if(user != null) {
					log.info("********** user : {}", user);
					// 마치 사용자가 정상 로그인 한 것처럼 session scope에 userVO(신분증)를 넣어주자
					session.setAttribute(loginKey, user);

					return true;	// 무사통과 시켜줌 -> 컨트롤러 호출
				} //if : rememerMe에 해당하는 사용자를 찾아냈을 때!!
				
			}  //if : rememberMeCookie가 request문서에 들어왔을 때!!
			
			//----------------------------------------------------------//
			// 3. To direct to intro page
			//----------------------------------------------------------//
			response.sendRedirect("/intro");
			log.info("Redirected to /intro");
			
			return false;
			
		} //if-else
	} //preHandle	
} //end class
