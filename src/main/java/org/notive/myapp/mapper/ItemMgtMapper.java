package org.notive.myapp.mapper;

import java.util.List;

import org.notive.myapp.domain.ItemInsertMgtVO;
import org.notive.myapp.domain.ItemInsertVO;
import org.notive.myapp.domain.ItemVO;

public interface ItemMgtMapper {
	
	public abstract List<ItemVO> getItemList();		// 상품 목록 조회
	
	// ===============================================================
	// 상품 등록
	public abstract int itemInsert(ItemInsertVO itemInsertVO);		// 상품등록
	public abstract int itemInsertMgt(ItemInsertMgtVO itemInsertMgtVO);		// 상품관리내역등록
	// ===============================================================
	
	public abstract int itemDelete(ItemVO itemVO);	// 상품삭제
	
	public abstract ItemVO getItem(Integer itemNo);		// 특정 상품정보 조회
	
	public abstract int itemUpdate(ItemVO itemVO);
	
} // end interface
