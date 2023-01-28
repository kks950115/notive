package org.notive.myapp.domain;

import lombok.NoArgsConstructor;
import lombok.Value;

@NoArgsConstructor(force = true)
@Value
public class ScheduleVO {
	
	private Integer no;
	private String title;
	private String content;
	private String schtime;
	private String start;
	private String end;
	private String backgroundColor;
	private Integer dday;
	private String id;
	private Integer schallDay;
	private Integer sch_alarm;
	
}
