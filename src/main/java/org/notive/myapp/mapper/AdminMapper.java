package org.notive.myapp.mapper;

import org.notive.myapp.domain.AdminDTO;
import org.notive.myapp.domain.AdminVO;

public interface AdminMapper {
	
	public abstract AdminVO adminLogin (AdminDTO dto);

} // end interface 
