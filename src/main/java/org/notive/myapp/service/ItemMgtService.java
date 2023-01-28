package org.notive.myapp.service;

import java.util.List;

import org.notive.myapp.domain.ItemInsertMgtVO;
import org.notive.myapp.domain.ItemInsertVO;
import org.notive.myapp.domain.ItemVO;

public interface ItemMgtService {

	public abstract List<ItemVO> getItemList();		// 상품 목록 조회
	
	// 상품 등록
	public abstract boolean itemRegister(ItemInsertVO itemInsertVO ,ItemInsertMgtVO itemInsertMgtVO);
	
	public abstract boolean itemRemove(ItemVO itemVO);	// 상품삭제	 	
	
	public abstract ItemVO getItem(Integer itemNo);		// 특정상품 조회
	
	public abstract boolean itemModify(ItemVO itemVO);
} // end interface
