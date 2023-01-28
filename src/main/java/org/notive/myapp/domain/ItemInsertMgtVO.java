package org.notive.myapp.domain;

import lombok.Value;

@Value
public class ItemInsertMgtVO {
	
	
	private Integer itemNo ;		// 상품번호
	private String adminID ;		// 관리자아이디
	private Integer item_state ;	// 상품 상태

}
