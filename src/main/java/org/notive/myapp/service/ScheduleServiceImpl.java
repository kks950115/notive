package org.notive.myapp.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.notive.myapp.domain.ScheduleVO;
import org.notive.myapp.mapper.ScheduleMapper;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@AllArgsConstructor

@Service
public class ScheduleServiceImpl implements ScheduleService{

	private ScheduleMapper mapper;
	
//	@Override
//	public List<ScheduleVO> getDate()  throws Exception{
//		log.debug("getDate() invoked");
//		
//		Objects.requireNonNull(this.mapper);
//		
//		return this.mapper.SelectScheduleVO();
//	}//getDate
	
	@Override
	public boolean addSchedule(ScheduleVO schedule) throws Exception {
		log.debug("addSchedule invoked");
		
		Objects.requireNonNull(this.mapper);
		
		
		return (this.mapper.insertSchedule(schedule)==1);
	
	}//addschedule

	@Override
	public boolean removeSchedule(Integer no)  throws Exception{
		log.debug("removeSchedule invoked");
		
		Objects.requireNonNull(this.mapper);
		
		
		
		return (this.mapper.deleteSchedule(no)==1);
	}//removeSchedule

	@Override
	public boolean modifySchedule(ScheduleVO schedule) throws Exception {
		log.debug("removeSchedule invoked");
		
		Objects.requireNonNull(this.mapper);
		
		
		
		return (this.mapper.updateSchedule(schedule)==1);
	}//modifySchedule

	@Override
	public List<Map<String, Object>> selectScheduleVO(String user_id)  {
		log.debug("selectScheduleVO invoked");
		Objects.requireNonNull(this.mapper);
		
		List<Map<String, Object>> ScheduleData =  this.mapper.SelectScheduleVO(user_id);
	
		return ScheduleData;	
			
	}

	@Override
	public List<Map<String, Object>> getScheduleAlarm(String user_id) throws Exception {
		log.debug("SelectOne invoked");
		Objects.requireNonNull(this.mapper);
		
		List<Map<String, Object>> ScheduleAlarm = this.mapper.ScheduleAlarm(user_id);
		
		return ScheduleAlarm;
	}

	@Override
	public boolean alarmOff(Integer sch_no) throws Exception {
		log.debug("alarmOff({}) invoked",sch_no);
		
		Objects.requireNonNull(this.mapper);
		return (this.mapper.alarmOff(sch_no)==1);
	}

	@Override
	public String getScheduleAPI() throws Exception {
		log.info("getScheduleAPI() invoked");
		
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String today = sdf.format(cal.getTime());
		String currYear = today.substring(0,4);
		
        StringBuilder urlBuilder = new StringBuilder("http://apis.data.go.kr/B090041/openapi/service/SpcdeInfoService/getRestDeInfo"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("ServiceKey","UTF-8") + "=wPKSnhEmKeTSpI60GYZ8ITHvIIfjSvDK2IqmCS%2BOG1wXeBAn5t%2BKxk%2FI9pV55PhG86E2NhyZj8%2BVCnkG3AVCTQ%3D%3D"); /*Service Key*/
        urlBuilder.append("&" + URLEncoder.encode("ServiceKey","UTF-8") + "=" + URLEncoder.encode("-", "UTF-8")); /*공공데이터포털에서 받은 인증키*/
        urlBuilder.append("&" + URLEncoder.encode("solYear","UTF-8") + "=" + URLEncoder.encode(currYear, "UTF-8")); /*연*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows","UTF-8") + "=" + URLEncoder.encode("100", "UTF-8")); 
        //urlBuilder.append("&" + URLEncoder.encode("solMonth","UTF-8") + "=" + URLEncoder.encode(currMonth, "UTF-8")); /*월*/
        urlBuilder.append("&" + URLEncoder.encode("_type","UTF-8") + "=" + URLEncoder.encode("json", "UTF-8")); /*타입*/
        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
       
        BufferedReader rd;
        if(conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {
            sb.append(line);
        }
        rd.close();
        conn.disconnect();
       
        
        
		return sb.toString();
	}//selectScheduleVO

}//class
