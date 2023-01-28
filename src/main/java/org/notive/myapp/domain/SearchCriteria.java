package org.notive.myapp.domain;

import org.springframework.web.util.UriComponentsBuilder;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2

@Data
public class SearchCriteria {
	
	// 페이징 처리
	private int currPage = 1;		// 현재 보고자 하는 페이지 번호
	private int amount = 10;		// 한 페이지당 보여줄 레코드 건수
	private int pagesPerPage = 5;	// 각 페이지 아래에 보여줄 페이지번호의 개수
	
	// 검색조건 필드
	private String searchType;			// 검색유형
	private String keyword;			// 검색어
	
	public String getPagingUri() {
		log.debug("getPagingUri() invoed");
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromPath("");
		
		builder.queryParam("currPage", this.currPage);
		builder.queryParam("amount", this.amount);
		builder.queryParam("pagesPerPagePage", this.pagesPerPage);
		builder.queryParam("type", this.searchType);
		builder.queryParam("keyword", this.keyword);		
		
		log.info("\t+ pagingUri : " + builder.toUriString());
		
		return builder.toUriString();
		
	} // getPagingUri
	

} //end class
