package org.notive.myapp.service;

import java.util.List;

import org.notive.myapp.domain.DirectoryVO;

public interface DirectoryService {

	
	abstract public List<DirectoryVO> getList(String userId, Integer state);
	abstract public DirectoryVO get(Integer dirNo, String userId);
	abstract public List<DirectoryVO> subGet(Integer dirTopNo, String userId);
	abstract public boolean register(DirectoryVO dir);
	abstract public boolean gregister(DirectoryVO dir);
	abstract public DirectoryVO getdirNo(Integer groupNo);
	abstract public boolean modify(DirectoryVO dir);
	abstract public boolean remove(Integer dirNo, String userId);
	
	
}//end interface
