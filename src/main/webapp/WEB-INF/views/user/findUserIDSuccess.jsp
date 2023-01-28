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

            <h1>Notive 아이디 찾기</h1>

            <fieldset>

                <%
                
                String userID = (String)request.getAttribute("__userID__");
                
                String[] idSplit = userID.split("@"); 
                
                String idSubstr= idSplit[0].substring(0,4);
                
                int len = idSplit[0].length() - 4;
                
                StringBuffer buffer = new StringBuffer();
                
                while (buffer.length() < len){
                    buffer.append("*");
                }
                
                String secuID = idSubstr + buffer + "@" + idSplit[1];    
                %>
                
                회원님의 아이디는 <%= secuID %> 입니다.
            </fieldset>
            <p></p>
                <a href="/user/login"><button type="button" class="defaultBtn" id="find-id-pass-submitBtn">로그인</button></a>
                <a href="/user/findUserPassPage"><button type="button" class="crossBtn" id="find-id-pass-cancleBtn">비밀번호 찾기</button></a>
        </div>
    </div>
</body>
</html>