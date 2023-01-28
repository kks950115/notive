package org.notive.myapp.exception;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@ControllerAdvice
public class CommonExceptionHandler {
	// 중요한 예외는 여기서 처리를 해준다.

	@ResponseStatus(code=HttpStatus.NOT_FOUND)
	
	@ExceptionHandler(NoHandlerFoundException.class)
	public String handleNoHandlerFoundException(Exception e, Model model) {
		log.debug("NoHandlerFoundException(e, model) invoked.");
		
		log.error("1. Exception Type: " + e.getClass().getName());
		log.error("2. Exception Message : " + e.getMessage() ); 
		
		model.addAttribute("exception", e);
		
		return "errorPage";
	} // handleNoHandlerFoundException
	
	@ExceptionHandler( BindException.class)
	public String handleBindException(Exception e, Model model) {
		log.debug("handleBindException(e, model) invoked.");
		
		log.error("1. Exception Type: " + e.getClass().getName());
		log.error("2. Exception Message : " + e.getMessage() ); 
		
		model.addAttribute("exception", e);
		
		return "errorPage";
	} // handleBindException
	
	@ExceptionHandler( IllegalStateException.class)
	public String IllegalStateException(Exception e, Model model) {
		log.debug("IllegalStateException(e, model) invoked.");
		
		log.error("1. Exception Type: " + e.getClass().getName());
		log.error("2. Exception Message : " + e.getMessage() ); 
		
		model.addAttribute("exception", e);
		
		return "errorPage";
	} // IllegalStateException
	
} // end class
