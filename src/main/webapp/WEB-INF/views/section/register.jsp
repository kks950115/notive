<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
    
 
  	<div id="memoRegisterForm" style="display:none;">
	    <div class="section_title">Memo</div>
	
	    <form action="/memo/register" method="post">
	        <input type="hidden" name="memoNo">
	        <input type="hidden" name="dirNo" value="${dirNo}">
	      
	        제목
	        <input type="text" name = "memoTitle" class="form-control" placeholder="제목"> 
	        내용
	        <textarea class="summernote" name="editordata" cols="1" rows="1" placeholder="메모를 입력하세요">
	        		
	        </textarea>
	        
	    </form>
    </div>
    <script>

	</script>
 