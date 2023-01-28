package org.notive.myapp.service;

import java.util.List;

import org.notive.myapp.domain.TrashVO;

public interface TrashService {
	
	public abstract TrashVO getTrash(String user_id, Integer origin_no );
	public abstract TrashVO getTrashListSelected(Integer trash_no, String user_id);
	public abstract List<TrashVO> getTrashList(String user_id);		// 휴지통 목록 얻기
	public abstract List<TrashVO> getTrashShowList(String user_id);		// 휴지통 목록 얻기
	public abstract Integer addTrash(TrashVO trash);				// 휴지통에 추가
	public abstract Integer restoreTrash(TrashVO trash);			// 복원
	public abstract Integer permanentDeleteTrash(TrashVO trash);	// 영구삭제
	
	
	
	// Util
	
	// 폴더, 파일, 메모를 전수조사해서 삭제됨(=2) 코드로 되어있지만 tbl_trash에 없는 자료들을 모두 등록한다.
	// Junit에서 간단히 실행해서 수행한다.
	public abstract void toTrashBinAllUndeleted();	

} // end interface
