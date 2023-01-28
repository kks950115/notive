package org.notive.myapp.controller;


import java.util.List;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpSession;

import org.notive.myapp.domain.ScheduleVO;
import org.notive.myapp.domain.UserVO;
import org.notive.myapp.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.io.BufferedReader;
import com.google.gson.Gson;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@RequestMapping("/schedule/")
@Controller
public class ScheduleController{
	
	@Setter(onMethod_= {@Autowired})
	private ScheduleService service;
	
	public static final String loginKey = "__LOGIN__";
	
	@GetMapping("calendarMain")
	public String calendarMain( ) throws Exception {
		log.info("calendarMain() invoked");
		
		return "/calendarMain";
		
	}//calendarmain
	
	
	
	//일정 전체 불러오기
	@RequestMapping(value="SelectScheduleVO", 
				produces="text/plain;charset=UTF-8")
	@ResponseBody
	public String SelectScheduleVO(HttpSession session, String user_id) throws Exception {
		log.debug("SelectScheduleVO ({}) invoked",user_id);
		Objects.requireNonNull(this.service);
		
		UserVO user =(UserVO)session.getAttribute(loginKey);
		user_id=user.getUserID();
		
		
		List<Map<String,Object>> mapList = this.service.selectScheduleVO(user_id);
		
		Gson gson = new Gson();
		String JsonmapList =gson.toJson(mapList );
		
		
		
		return JsonmapList;
	}//SelectScheduleVO
	
	
	  //일정 하나만 불러오기
      @ResponseBody
	  @GetMapping(value="getScheduleAlarm", produces="text/plain;charset=UTF-8")
	  public  String getSchedule(HttpSession session, String user_id) throws Exception{
	  log.debug("getSchedule ({}) invoked",user_id);
	  
	  Objects.requireNonNull(this.service); 
	  
	  UserVO user =(UserVO)session.getAttribute(loginKey);
	  user_id=user.getUserID();
	  
	  List< Map<String,Object>> map =this.service.getScheduleAlarm(user_id);
	  
	  Gson gson= new Gson();
	  String jsonmapList = gson.toJson(map);
	  
	  return jsonmapList;
	  
	  };//getSchedule
	
	  
	 //일정추가
	@ResponseBody
	@PostMapping("addSchedule")
	public void addSchedule(@RequestBody ScheduleVO vo) throws Exception {
		log.info("addschedule ({}) invoked",vo);
		
		Objects.requireNonNull(this.service);
		
		
		 this.service.addSchedule(vo);
		log.info(">>vo: {}",vo);
		
		
		
	}//addschedule
	
	//일정삭제
	@ResponseBody
	@PostMapping("deleteSchedule")
	public void deleteSchedule(Integer no) throws Exception {
		log.debug("deleteschedule({}) invoked",no);
//		int aa = Integer.parseInt(no);
		
		Objects.requireNonNull(this.service);
		
		this.service.removeSchedule(no);
		
	}//deleteschedule
	
	//일정업데이트
	@ResponseBody
	@PostMapping("updateSchedule")
	public void updateSchedule(@RequestBody ScheduleVO vo) throws Exception {
		log.debug("updateSchedule ({}) invoked",vo);
		
		Objects.requireNonNull(this.service);
		
		this.service.modifySchedule(vo);
		
		
		
		log.info(" >>>>>>updateScheduleVO: {}", vo);
		
	}//updateSchedule
	
	@ResponseBody
	@PostMapping("alarmOff")
	public void alarmOff(Integer sch_no) throws Exception {
		log.debug("deleteschedule({}) invoked",sch_no);
		
		Objects.requireNonNull(this.service);
		
		this.service.alarmOff(sch_no);
	}//alarm off
	
	@ResponseBody
	@GetMapping("callAPI")
	public String callAPI() throws Exception{
		log.debug("callAPI() invoked");
		
		Objects.requireNonNull(this.service);
		
		
		
		return this.service.getScheduleAPI();
		
	}

}//controller
