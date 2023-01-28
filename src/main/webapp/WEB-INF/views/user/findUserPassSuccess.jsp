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
    <title>Document</title>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.3.2/jquery-migrate.min.js"></script>
	
	<link rel="stylesheet" href="../../resources/css/userCommon.css" type="text/css">
	<script src="../../resources/js/userCommon.js"></script>
	
</head>
<body>
    <div class="wrapper">
        <div>

            <h1>Notive 비밀번호 찾기</h1>

            <fieldset>

                임시 비밀번호가 메일로 발송되었습니다.

            </fieldset>
            <p></p>
                <a href="/user/login"><button type="button" class="defaultBtn" id="find-id-pass-submitBtn">로그인</button></a>
        </div>
     </div>
</body>
</html>