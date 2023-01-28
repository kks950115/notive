package org.notive.myapp.mapper;

import java.util.List;

import org.notive.myapp.domain.BuyVO;
import org.notive.myapp.domain.Item_OriginVO;


// tbl_buy 테이블의 목록 + CRUD 메소드를 선언
public interface BuyMapper {
	
	//1. buy_tbl 목록조회 ==> 테이블 테스트용으로 이용.
	public abstract List<BuyVO> getList();	
	
	//2. buy_tbl 행 추가 ==> 회원이 유료 상품 구매할 경우, 구매 정보를 해당 테이블에 저장.
	public abstract int insertSelectKey(BuyVO buy);
	
	//3-1. 결제 취소 버튼 클릭 시, buy_no를 매개로 buy_tbl에서 결제 정보(행) 삭제.
	public abstract int delete(Integer buy_no);
	
	//3-1. 결제 취소 버튼 클릭 시, user_id를 매개로 buy_tbl에서 결제 정보(행) 삭제.
	public abstract int delete2(String user_id);
	
	public abstract int charged_update(String user_id);
	
	//4. buy_item 목록조회 ==> 상품명 및 가격 DB에서 가져오기.
	public abstract List<Item_OriginVO> getItemList();
	
	//5.ID로 buy_date 조회
	public abstract int update(String user_id);
	
	


} // end class
