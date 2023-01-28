package org.notive.myapp.service;

import java.util.List;

import org.notive.myapp.domain.BuyVO;
import org.notive.myapp.domain.Item_OriginVO;

public interface BuyService {
	
	//등록, 삭제가 제대로 이뤄지는지 테스트용.
	public abstract List<BuyVO> getList();		
	
	public abstract List<Item_OriginVO> bringItemList();		
	
	//tbl_buy에 등록 성공 or 실패 확인
	public abstract boolean register(BuyVO buy);
	
	//tbl_buy에서 삭제 성공 or 실패 확인
	public abstract boolean remove(Integer buy_no);
	
	public abstract boolean remove2(String user_id);
	
	public abstract boolean getBuy_no(String user_id);
	
	public abstract boolean modify(String user_id);
	
	public abstract int updateCancel(String user_id);
	

	
	
}//end interface
