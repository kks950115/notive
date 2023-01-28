package org.notive.myapp.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.notive.myapp.domain.AttachFileVO;
import org.notive.myapp.domain.DirectoryVO;
import org.notive.myapp.domain.MemoVO;
import org.notive.myapp.domain.TrashVO;
import org.notive.myapp.mapper.CommonMapper;
import org.notive.myapp.mapper.DirectoryMapper;
import org.notive.myapp.mapper.FileMapper;
import org.notive.myapp.mapper.MemoMapper;
import org.notive.myapp.mapper.TrashMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Service
public class TrashServiceImpl implements TrashService {
	
	@Autowired
	private CommonMapper commonMapper;
	
	@Autowired
	private TrashMapper trashMapper;
	
	@Autowired
	private DirectoryMapper directoryMapper;
	
	@Autowired
	private FileMapper fileMapper;
	
	@Autowired
	private MemoMapper memoMapper;
	
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Override
	public TrashVO getTrash(String user_id, Integer origin_no) {
		
		return this.trashMapper.selectTrashByOriginNo(user_id, origin_no);
		
	} // getTrash
	
	@Override
	public List<TrashVO> getTrashList(String user_id) {
		
		return this.trashMapper.selectTrashList(user_id);
		
	} // getTrashList
	
	@Override
	public TrashVO getTrashListSelected(Integer trash_no, String user_id) {
	
		return this.trashMapper.selectTrash(user_id, trash_no);
	}

	@Override
	public List<TrashVO> getTrashShowList(String user_id) {
		// 휴지통 리스트
		// 폴더 자체가 삭제됐다면 폴더 내에 파일은 반환X
		
		// 삭제된 전체 리스트
		List<TrashVO> trashList = this.trashMapper.selectTrashList(user_id);
		
		// 보여줄 리스트
		List<TrashVO> resultList = new ArrayList<TrashVO>();
		
		trashList.forEach(e -> {
			
			DirectoryVO topDirVO = null;
			
			switch(e.getSort_code()) {
			// switch 
			// 분류코드 1 = 폴더, 2 = 파일, 3 = 메모
			
			case 1 : 
				// 폴더 객체 얻기
				DirectoryVO dirVO = this.directoryMapper.select(e.getOrigin_no(), user_id);
				
				// 상위폴더 검사
				// 최상위 폴더일 때 결과 리스트에 바로 담기
				if(dirVO.getDirTopNo() == 0) {
					resultList.add(e);
					
				// 최상위 폴더가 아닐 때 현재 폴더의 바로 상위 폴더를 정의한다.
				} else if (dirVO.getDirTopNo() != 0){
					topDirVO = this.directoryMapper.select(dirVO.getDirTopNo(), user_id);
				} // if-else if
				
				break;
				
			case 2 :
				// 파일 객체 얻기
				AttachFileVO fileVO = this.fileMapper.selectFileVO(e.getOrigin_no(), user_id);
				// 파일 객체의 소속 폴더 정의
				topDirVO = this.directoryMapper.select(fileVO.getDirNo(), user_id);
				
				break;
				
			case 3 :
				// 메모 객체 얻기
				MemoVO memoVO = this.memoMapper.selectMemoVO(e.getOrigin_no(), user_id);
				// 메모 객체의 소속 폴더 정의
				topDirVO = this.directoryMapper.select(memoVO.getDirNo(), user_id);
				
				break;
			
			} // switch
			
			// 소속폴더(폴더일 경우엔 상위폴더)가 정의되었고 && 그 소속 폴더가 삭제된 상태가 아니면 (!= 2)
			// 현재 TrashVO를 보여줄 리스트에 담는다.
			if(topDirVO != null && topDirVO.getDirState() != 2) {
				resultList.add(e);
			} // if

		}); // forEach
		
		return resultList;
	} // getTrashList

	@Override
	public Integer addTrash(TrashVO trash) {
		return trashMapper.insertTrash(trash);
	} // addTrash

	@Override
	public Integer restoreTrash(TrashVO trash) {
		
		switch(trash.getSort_code()) {
		case 1 : 
			
			List<MemoVO> memoList = this.memoMapper.selectAll(trash.getOrigin_no(), trash.getUser_id(), 2);
			memoList.forEach(e ->{
				this.memoMapper.changeState(1, e.getMemoNo(), e.getUserId());
				this.trashMapper.deleteTrashByOriginNo(3, e.getMemoNo());
			});
			
			List<AttachFileVO> fileList = this.fileMapper.selectAll(trash.getOrigin_no(), trash.getUser_id(), 2);
			fileList.forEach(e ->{
				this.fileMapper.changeState(1, e.getFileNo(), e.getUserId());
				this.trashMapper.deleteTrashByOriginNo(2, e.getFileNo());
			});
			
			this.directoryMapper.changeState(1, trash.getOrigin_no(), trash.getUser_id());
			
			break;
			
		case 2 :
			
			this.fileMapper.changeState(1, trash.getOrigin_no(), trash.getUser_id());
						
			break;
			
		case 3 :
			
			this.memoMapper.changeState(1, trash.getOrigin_no(), trash.getUser_id());
			
		}		
		
		return this.trashMapper.deleteTrash(trash);
	} // restoreTrash

	@Override
	public Integer permanentDeleteTrash(TrashVO trash) {
		
		switch(trash.getSort_code()) {
		case 1 : 
			
			List<MemoVO> memoList = this.memoMapper.selectAll(trash.getOrigin_no(), trash.getUser_id(), 2);
			log.info("memoList!!!! , {}", memoList);
			memoList.forEach(e ->{
				this.memoMapper.deleteMemo(e.getMemoNo());
				this.trashMapper.deleteTrashByOriginNo(3, e.getMemoNo());
			});
			
			List<AttachFileVO> fileList = this.fileMapper.selectAll(trash.getOrigin_no(), trash.getUser_id(), 2);
			log.info("fileList!!!! , {}", fileList);
			fileList.forEach(e ->{
				this.fileMapper.deleteFile(e.getFileNo());
				this.trashMapper.deleteTrashByOriginNo(2, e.getFileNo());
			});
			
			this.directoryMapper.deleteDirectory(trash.getOrigin_no());
			
			break;
			
		case 2 :
			
			this.fileMapper.deleteFile(trash.getOrigin_no());
						
			break;
			
		case 3 :
			
			this.memoMapper.deleteMemo(trash.getOrigin_no());
			
		}
		
		return this.trashMapper.deleteTrash(trash);
	} // permanentDeleteTrash

	@Override
	public void toTrashBinAllUndeleted() {
		
		// trashVO를 담을 리스트
		List<TrashVO> listTrash = new ArrayList<TrashVO>();
		
		// 각 테이블에서 삭제상태(state = 2)인 모든 데이터를 추출
		List<Map<String, Object>> listDirMap = this.commonMapper.selectAllByIntegerParam("tbl_directory", "dir_state", 2);
		List<Map<String, Object>> listFileMap = this.commonMapper.selectAllByIntegerParam("tbl_file", "file_state", 2);
		List<Map<String, Object>> listMemoMap = this.commonMapper.selectAllByIntegerParam("tbl_memo", "memo_state", 2);
		
		// 추출한 데이터를 list에 담음
		
		listDirMap.forEach(e -> listTrash.add(new TrashVO(
												null, 
												 		  String.valueOf(e.get("USER_ID")), 
											   1, 
											   Integer.valueOf(String.valueOf(e.get("DIR_NO"))), 
											 String.valueOf( e.get("DIR_NAME")), 
											 null)));
		
		listFileMap.forEach(e -> listTrash.add(new TrashVO(
												null, 
												 		  String.valueOf(e.get("USER_ID")), 
											   2, 
											   Integer.valueOf(String.valueOf(e.get("FILE_NO"))), 
											 String.valueOf( e.get("FILE_NAME")), 
											 null)));
												
		listMemoMap.forEach(e -> listTrash.add(new TrashVO(
												null, 
												 		  String.valueOf(e.get("USER_ID")), 
											   3, 
											   Integer.valueOf(String.valueOf(e.get("MEMO_NO"))), 
											 String.valueOf( e.get("MEMO_TITLE")), 
											 null)));
		
		log.info(listTrash);
		
		// 모든 데이터 테이블에 insert
		listTrash.forEach(trashMapper::insertTrash);
		
	}







} // end class
