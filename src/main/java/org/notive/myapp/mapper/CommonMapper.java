package org.notive.myapp.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CommonMapper {
	
	// 테이블의 특정 컬럼의 숫자를 반환
	public abstract Integer selectCountValue(
										@Param("tbl_name") String tbl_name, 
										@Param("col_name") String col_name, 
										@Param("value") String value);
	
	// 테이블의 특정 하나의 컬럼값(=Varchar2)과 일치하는 모든 데이터 반환
	public abstract List<Map<String, Object>> selectAllByStringParam(
										@Param("tbl_name") String tbl_name, 
										@Param("col_name") String col_name, 
										@Param("value") String value);
	
	// 테이블의 특정 하나의 컬럼값(=Number)과 일치하는 모든 데이터 반환
	public abstract List<Map<String, Object>> selectAllByIntegerParam(
										@Param("tbl_name") String tbl_name, 
										@Param("col_name") String col_name, 
										@Param("value") Integer value);
} // end interface
