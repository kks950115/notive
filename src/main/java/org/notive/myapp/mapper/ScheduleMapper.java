package org.notive.myapp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;
import org.notive.myapp.domain.ScheduleVO;

public interface ScheduleMapper {
	
	public abstract List<Map<String, Object>> SelectScheduleVO(String user_id);
	
	public abstract List<Map<String, Object>> ScheduleAlarm(String user_id);
	
	public abstract int insertSchedule(ScheduleVO chedule);
	
	public abstract int deleteSchedule(@Param("no") Integer no);
	
	public abstract int updateSchedule(ScheduleVO chedule);
	
	public abstract int alarmOff (@Param("sch_no") Integer no);
	
} // end interface
