package org.notive.myapp.service;

import java.util.List;
import java.util.Map;

import org.notive.myapp.domain.ScheduleVO;

public interface ScheduleService {
	//public abstract List<ScheduleVO> getDate() throws Exception;
	
	public abstract List<Map<String, Object>> selectScheduleVO(String user_id) throws Exception;
	
	public abstract List<Map<String, Object>> getScheduleAlarm(String user_id) throws Exception;
	public abstract boolean addSchedule(ScheduleVO schedule) throws Exception;
	public abstract boolean removeSchedule(Integer no) throws Exception;
	public abstract boolean modifySchedule(ScheduleVO schedule) throws Exception;
	
	public abstract boolean alarmOff(Integer sch_no) throws Exception;
	
	public abstract String getScheduleAPI() throws Exception;
}//interface
