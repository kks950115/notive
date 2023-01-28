package org.notive.myapp.service;

import java.util.List;
import java.util.Objects;

import org.notive.myapp.domain.DirectoryVO;
import org.notive.myapp.mapper.DirectoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Service
public class DirectoryServiceImpl implements DirectoryService {

	
	@Autowired
	private DirectoryMapper mapper;
	
	
	
	@Override
	public List<DirectoryVO> getList(String userId, Integer state) {
		log.debug("getList() invoked.");
		
		
		Objects.requireNonNull(this.mapper);
		
		return this.mapper.selectAll(userId, state);
	}// getList

	
	
	@Override
	public DirectoryVO get(Integer dirNo, String userId) {
		log.debug("get({}, {}) invoked.",dirNo, userId);
		
		Objects.requireNonNull(this.mapper);
		
		return this.mapper.select(dirNo, userId);
	}//get

	
	



	@Override
	public List<DirectoryVO> subGet(Integer dirTopNo, String userId) {
		log.debug("subGet({}, {}) invoked.",dirTopNo,userId);
		
		Objects.requireNonNull(this.mapper);
		
		return this.mapper.selectSub(dirTopNo, userId);
	}//subGet

	
	
	@Override
	public DirectoryVO getdirNo(Integer groupNo) {
		log.debug("getdirNo({}) invoked.",groupNo);
		
		Objects.requireNonNull(this.mapper);
		 
		return this.mapper.selectDirNo(groupNo);  
	}//getdirNo
	
	
	
	@Override
	public boolean register(DirectoryVO dir) {
		log.debug("register({}) invoked.",dir);
		
		Objects.requireNonNull(this.mapper);
		 
		return (this.mapper.insertSelectKey(dir) == 1);  
	}//register
	
	
	
	
	@Override
	public boolean gregister(DirectoryVO dir) {
		log.debug("gregister({}) invoked.",dir);
		
		Objects.requireNonNull(this.mapper);
		 
		return (this.mapper.ginsertSelectKey(dir) == 1);  
	}//register
	

	
	

	@Override
	public boolean modify(DirectoryVO dir) {
		log.debug("modify() invoked.");
		
		
		return (this.mapper.update(dir) == 1);   
	}//modify






	@Override
	public boolean remove(Integer dirNo, String userId) {
		log.debug("remove() invoked.");
		
		
		Objects.requireNonNull(this.mapper);
		this.mapper.changeState(2, dirNo, userId);
		
		return true;
	}//remove




	
}// end class
