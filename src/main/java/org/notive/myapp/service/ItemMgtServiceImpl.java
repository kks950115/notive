package org.notive.myapp.service;

import java.util.List;
import java.util.Objects;

import org.notive.myapp.domain.ItemInsertMgtVO;
import org.notive.myapp.domain.ItemInsertVO;
import org.notive.myapp.domain.ItemVO;
import org.notive.myapp.mapper.ItemMgtMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Service
public class ItemMgtServiceImpl implements ItemMgtService{
	
	
	@Setter(onMethod_=@Autowired)
	private ItemMgtMapper itemMgtMapper;
	
	
	
	@Override
	public List<ItemVO> getItemList() {
		
		log.debug("getItemList() invoked");
		
		Objects.requireNonNull(itemMgtMapper);
		
		return itemMgtMapper.getItemList();
	}



	@Override
	public boolean itemRegister(ItemInsertVO itemInsertVO, ItemInsertMgtVO itemInsertMgtVO) {
		log.debug("itemRegister(itemInsertVO) invoked");
		
		Objects.requireNonNull(itemMgtMapper);
		
		int affectedLines1 = this.itemMgtMapper.itemInsert(itemInsertVO);
		int affectedLines2 = this.itemMgtMapper.itemInsertMgt(itemInsertMgtVO);
		
		return affectedLines1 == 1 && affectedLines2 == 1 ;
	}



	@Override
	public boolean itemRemove(ItemVO itemVO) {
		log.debug("itemModify({}) invoked", itemVO);
		
		Objects.requireNonNull(itemMgtMapper);
		
		return(this.itemMgtMapper.itemDelete(itemVO) == 1);
		
	}



	@Override
	public ItemVO getItem(Integer itemNo) {
		log.debug("getItem({}) invoked", itemNo);
		
		Objects.requireNonNull(this.itemMgtMapper);
		
		return this.itemMgtMapper.getItem(itemNo);
	}



	@Override
	public boolean itemModify(ItemVO itemVO) {
		log.debug("itemModify({}) invoked", itemVO);
		
		Objects.requireNonNull(this.itemMgtMapper);
		
		return(this.itemMgtMapper.itemUpdate(itemVO) == 1);
		
	} // itemModify
	
	
	
	
	

} // end class
