<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>


<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>



<!-- 파일영역 -->
 <div class="fileDrop" data-dirno="${dirNo}">
	    
    <div class="uploadDiv">
    	<input type="hidden" name="user_id" value="${user_id}" >
    	<input type="hidden" name="dirNo" id="inputDirNo" value="${dirNo}" >
        <input type="file" class="inputFile" name="uploadFile" style="display:none;"multiple>
     
    </div>

    <ul id="fileIcon" >
		<i class="glyphicon glyphicon-plus" style="font-size:25pt"></i>       
	 </ul>
	 <div class="uploadResult">
     
        <ul>
           <!-- 동적추가 -->
        </ul>
    </div>
</div> 


 