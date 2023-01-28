package org.notive.myapp.domain;

import lombok.Value;


@Value
public class Item_OriginVO {
	
	private Integer item_no;	//Data-type: NUMBER
	private String item_name;	//Data-type: VARCHAR(100)
	private String item_capa;	//Data-type: VARCHAR(100)
	private String item_price;	//Data-type: VARCHAR(100)
	

} // end class