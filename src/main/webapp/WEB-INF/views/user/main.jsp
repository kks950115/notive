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
    <title>main.jsp</title>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.3.2/jquery-migrate.min.js"></script>
    
    <script>
	    $(function(){
	    	console.clear; console.debug("jq started...");
	    	
	    	$("#logoutBtn").click(function(){
	    		console.debug("#logoutBtn clicked...");
	    		
	    		location.href="/user/logout";
	    	}) //onclick for logoutBtn
	    }); //.jq
    </script>
</head>
<body>
	<h1>main</h1>
	<hr>

	
	${__LOGIN__.userID} 님 안녕하세요!

	<p></p>
	<form>
		<button type="submit" class="submitBtn" formaction="/user/userCheckPage">마이페이지</button>
		
		<button type="submit" id="logoutBtn" formaction="/user/logout" formmethod="GET">log-out</button>
	</form>
</body>
</html>