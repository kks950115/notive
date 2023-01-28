<%@ page 
   language="java" 
   contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    
    
<!DOCTYPE html>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <title>NOTIVE :: 비밀번호찾기</title>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.3.2/jquery-migrate.min.js"></script>
	
	<link rel="stylesheet" href="../../resources/css/userCommon.css" type="text/css">
	
	<script src="../../resources/js/userCommon.js"></script>
	
	<script>

	var result = "${result}";
	
	if (result == "failure"){
		alert("새로 입력한 비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
	} if else (result == "passCheck-failure""){
		alert ("현재 비밀번호가 일치하지 않습니다. 다시 확인해주세요.");
	} //if-else
		

	$(function(){
	    console.debug("js started...");
	    
    	$("#cancleBtn").click(function(){
    		console.debug("cancleBtn is clicked...");
    		
	        location.href="/user/myPage";
	    }) //onclick for cancleBtn
    }) //.js
    
	</script>
</head>
<body>

	<form action="/user/modifyUserPass" method="post">
		<div class="form-input">현재 비밀번호<br>
			<input class="input-box" id="form-input-userPassCur" name="userPassCur" type="password" placeholder="현재 비밀번호 입력">
		</div>
		<div class="form-input">새 비밀번호<br>
			<input class="input-box" placeholder="비밀번호 (8~23자리)" name="userPass" id="userPass"
				type="password" />
			<div class="checkMsg" id="checkPass"></div>
		</div>
		<div class="form-input">새 비밀번호 재입력<br>
			<input class="input-box" placeholder="비밀번호 재입력" name="userPassConfirm" id="userPassConfirm"
				type="password" />
			<div class="checkMsg" id="checkPassConfirm"></div>
		</div>
		<button type="submit" class="submitBtn-modifyUserInfo" id="submitBtn-modifyUserPass">비밀번호 변경</button>
	</form>
		<button type="button" id="cancleBtn">취소</button>
</body>
</html>