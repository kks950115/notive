<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<!-- 메모영역 -->
<div id="search-memo-area" >
  	
	<ul class="memo"  >
		<div id="registerBtn" onclick="memoRegisterBtn()">
			<i class="glyphicon glyphicon-plus" style="font-size:25pt"></i>     
		</div>  
    </ul>
  
   	<div id="memoList">
   		<!-- 동적추가 -->
   	</div>
   	
</div>

메모영역