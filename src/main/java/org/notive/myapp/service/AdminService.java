package org.notive.myapp.service;

import org.notive.myapp.domain.AdminDTO;
import org.notive.myapp.domain.AdminVO;

public interface AdminService {
	
	
	public abstract AdminVO adminLogin(AdminDTO dto) ;

} // end interface
