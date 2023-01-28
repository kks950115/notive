 
  	<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<!-- 메모영역 -->
<div id="memo_area" >
  	
	<ul class="memo"  id="registerBtn" onclick="memoRegisterBtn('${dirNo}')">
		 
			<i class="glyphicon glyphicon-plus" style="font-size:25pt"></i>     
	 
    </ul>
  
   	<div id="memoList">
   		<!-- 동적추가 -->
   	</div>
   	
   	
   	
  
</div>  

 <!-- <div id="memoRegisterForm" style="display:none;">
 
	<form id="memoForm">
		<input type="hidden" name="memoNo">
	    <input type="hidden" name="dirNo" value="${dirNo}">
	   
	     제목
	     <input type="text" name="memoTitle" class="form-control" placeholder="제목"> 
	     내용
	     <textarea id="memoContent"class="summernote" name="editordata" cols="1" rows="1" placeholder="메모를 입력하세요">
	     		
	     </textarea><br>
	     
	     <button type="button" id="memoRegisterBtn" >저장</button>
	     <button type="button" id="memoModifyBtn" >수정</button>
	     <button type="button" id="memoRemoveBtn" >삭제</button>
	     <button type="button" id="memoListBtn">목록으로</button>
	     
	     
	 </form>
</div> -->


 