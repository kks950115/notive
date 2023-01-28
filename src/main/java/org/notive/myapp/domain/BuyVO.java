package org.notive.myapp.domain;

import java.sql.Timestamp;

import lombok.Value;


@Value
public class BuyVO {
	
	private Integer buy_no;				//Data-type: NUMBER
	private Integer item_no;			//Data-type: NUMBER
	private Timestamp buy_date;			//Data-type: TIMESTAMP
	private String buy_credit_no;		//Data-type: String
	private String user_id;				//Data-type: VARCHAR(2)
	private String charged;				//Data-type: VARCHAR(2)
} // end class