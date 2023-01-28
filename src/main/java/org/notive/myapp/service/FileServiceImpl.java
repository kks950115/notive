package org.notive.myapp.service;

import java.util.List;
import java.util.Objects;

import org.notive.myapp.domain.AttachFileVO;
import org.notive.myapp.domain.TrashVO;
import org.notive.myapp.mapper.FileMapper;
import org.notive.myapp.mapper.TrashMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Service
public class FileServiceImpl implements FileService {

	@Setter(onMethod_=@Autowired)
	private FileMapper mapper;
	
	@Setter(onMethod_=@Autowired)
	private TrashMapper trashMapper;
	

	@Override
	public AttachFileVO getFileVO(Integer fileNo, String userId) {
		log.debug("getlist() invoked.");
		
		Objects.requireNonNull(this.mapper);

		return this.mapper.selectFileVO(fileNo, userId);
	}//remove
	
	@Override
	public List<AttachFileVO> getlistAll(String userId, Integer state) {
		log.debug("getlist() invoked.");
		
		Objects.requireNonNull(this.mapper);

		return this.mapper.selectAllFileOfUser(userId, state);
	} //getSearchList
	
	@Override
	public List<AttachFileVO> getlist(Integer dirNo, String userId, Integer state) {
		log.debug("getlist() invoked.");
		
		Objects.requireNonNull(this.mapper);

		return this.mapper.selectAll(dirNo, userId, state);
	}//getlist
	
	

	@Override
	public boolean register(AttachFileVO file) {
		log.debug("register() invoked.");
				
		Objects.requireNonNull(this.mapper);
		return (this.mapper.insertSelectKey(file) == 1) ;
	}//register
	
	
	

	@Override
	public boolean remove(AttachFileVO fileVO) {
		log.debug("remove() invoked.");
		
		Objects.requireNonNull(this.mapper);
		
		this.trashMapper.insertTrash(new TrashVO(
				   null,
				    fileVO.getUserId(),
				  2,
				  fileVO.getFileNo(),
				fileVO.getFileName(),
				null
		));
		
		this.mapper.changeState(2, fileVO.getFileNo(), fileVO.getUserId());
		
		return true;
	}

	@Override
	public List<AttachFileVO> getSearchList(String userId, String search) {
		log.debug("getSearchList({}, {}) invoked.", userId, search);
		
		return this.mapper.selectSearchList(userId, search);
	}




}
