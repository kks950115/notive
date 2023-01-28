package org.notive.myapp.domain;

import java.util.Date;

import lombok.Value;

@Value
public class ItemVO {
	
	private Integer itemNo;			// 상품번호
	private String itemName;		// 상품명
	private String itemCapa;		// 용량
	private String itemPrice;		// 상품가격
	private Date   itemRegiDate;	// 상품등록일
	private Date   itemDelDate;		// 상품관리일
	private String itemState;		// 상품 상태

} // end class
