package org.notive.myapp.service;

import org.notive.myapp.domain.AdminDTO;
import org.notive.myapp.domain.AdminVO;
import org.notive.myapp.mapper.AdminMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;

@Log4j2
@NoArgsConstructor

@Service
public class AdminServiceImpl implements AdminService {
	
	@Setter(onMethod_=@Autowired)
	private AdminMapper adminMapper ;
	
	

	@Override
	public AdminVO adminLogin(AdminDTO dto) {
		log.debug("adminLogin(dto) invoked");
		
		return this.adminMapper.adminLogin(dto);
	} // adminLogin
	
	
	

} // end class
