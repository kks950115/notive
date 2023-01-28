package org.notive.myapp.service;

import java.util.List;
import java.util.Objects;

import org.notive.myapp.domain.BuyVO;
import org.notive.myapp.domain.ItemVO;
import org.notive.myapp.domain.Item_OriginVO;
import org.notive.myapp.mapper.BuyMapper;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;


@Log4j2
@NoArgsConstructor

@Service
public class BuyServiceImpl 
	implements BuyService, InitializingBean, DisposableBean {
	
	@Setter(onMethod_={@Autowired})
	private BuyMapper mapper;
	
	//------------------------------------//

	@Override
	public List<BuyVO> getList() {
		log.debug("getList() invoked.");
		
		Objects.requireNonNull(this.mapper);
		return mapper.getList();
	}//getList
	
	
	@Override
	public List<Item_OriginVO> bringItemList() {
		log.debug("bringItemList() invoked.");
		
		Objects.requireNonNull(this.mapper);
		return mapper.getItemList();
	}//bringItemList

	
	@Override
	public boolean register(BuyVO buy) {
		log.debug("register({}) invoked.", buy);
		
		Objects.requireNonNull(this.mapper);
		
		int affectedLines = this.mapper.insertSelectKey(buy);
		return (affectedLines == 1);
	}//register

	
	@Override
	public boolean remove(Integer buy_no) {
		log.debug("remove({}) invoked.", buy_no);
		
		Objects.requireNonNull(this.mapper);
	
		return (this.mapper.delete(buy_no)==1);
	}//remove
	
	@Override
	public boolean remove2(String user_id) {
		log.debug("remove({}) invoked.", user_id);
		
		Objects.requireNonNull(this.mapper);
	
		return (this.mapper.delete2(user_id)==1);
	}//remove2
	
	
	@Override
	public boolean modify(String user_id) {
		log.debug("modify({}) invoked.", user_id);
		
		Objects.requireNonNull(this.mapper);
	
		return (this.mapper.charged_update(user_id)==1);
	}//modify

	
	@Override
	public int updateCancel(String user_id) {
		log.debug("updateCancel({}) invoked.", user_id);
		
		Objects.requireNonNull(this.mapper);
		int affectedLines = this.mapper.update(user_id);
		
		return affectedLines;
	}//updateCancel
	
	
	//--------------------------------------------------//


	@Override
	public void destroy() throws Exception {
		log.debug("destroy() invoked.");
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		log.debug("afterPropertiesSet() invoked.");
		assert this.mapper != null;
		log.info("\t+ mapper: {}", this.mapper);
	}


	@Override
	public boolean getBuy_no(String user_id) {
		// TODO Auto-generated method stub
		return false;
	}

	
	

}//end class
