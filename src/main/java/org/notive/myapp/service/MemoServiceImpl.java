package org.notive.myapp.service;

import java.util.List;
import java.util.Objects;

import org.notive.myapp.domain.MemoVO;
import org.notive.myapp.domain.TrashVO;
import org.notive.myapp.mapper.MemoMapper;
import org.notive.myapp.mapper.TrashMapper;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Service
public class MemoServiceImpl implements MemoService, InitializingBean {

	@Setter(onMethod_=@Autowired)
	private MemoMapper mapper;

	@Setter(onMethod_=@Autowired)
	private TrashMapper trashMapper;
	
	@Override
	public List<MemoVO> getlistAll(String userId, Integer state) {
		log.debug("getlist({}, {}) invoked.", userId, state);
		
		Objects.requireNonNull(this.mapper);
		
		return mapper.selectAllOfUser(userId, state);
	}
	
	@Override
	public List<MemoVO> getlist(Integer dirNo, String userId, Integer state) {
		log.debug("getlist({}, {}) invoked.", dirNo, userId);
		
		Objects.requireNonNull(this.mapper);
		
		return mapper.selectAll(dirNo, userId, state);
	}//getlist
	
	
//	@Override
	public List<MemoVO> getSearchList(String userId, String search){
		log.debug("getSearchList invoked.");
		
		return mapper.selectSearchList(userId, search);
	} //getSearchList

	
	

	@Override
	public MemoVO get(Integer memoNo, String userId) {
		log.debug("getl() invoked.");
		
		Objects.requireNonNull(this.mapper);		
		
		return this.mapper.selectMemoVO(memoNo, userId);
	}//afterPropertiesSet


	
	@Override
	public boolean register(MemoVO memo) {
		log.debug("register({}) invoked.",memo);
		
		Objects.requireNonNull(this.mapper);
		
		return (this.mapper.insertSelectKey(memo) == 1);
	}//register
	

	@Override
	public boolean remove(MemoVO memoVO) {
		log.debug("remove({}) invoked.",memoVO);
		
		Objects.requireNonNull(this.mapper);
		
	
		this.trashMapper.insertTrash(new TrashVO(
				   null,
				    memoVO.getUserId(),
				  3,
				  memoVO.getMemoNo(),
				memoVO.getMemoTitle(),
				null
			));
		
		this.mapper.changeState(2, memoVO.getMemoNo(), memoVO.getUserId());
		
		return true;
	}//remove
	


	@Override
	public boolean modify(MemoVO memo) {
		log.debug("modify({}) invoked.",memo);
		
		Objects.requireNonNull(this.mapper);
		
		return (this.mapper.update(memo) == 1);
	}

	

	
	//======================================================//
	@Override
	public void afterPropertiesSet() throws Exception {
		log.debug("afterPropertiesSet() invoked.");
		
		assert this.mapper != null;
		log.info("\t+ mapper: {}", mapper);
				
		
	}




	

}//end class
