package org.notive.myapp.domain;

import lombok.Value;

@Value
public class ItemInsertVO {
	
	private String itemName;		// 상품명
	private String itemCapa;		// 용량
	private String itemPrice;		// 상품가격

}
