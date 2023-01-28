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
	
	<script>
		var result = "${result}";

		if(result == "failure"){
			
			alert("가입된 정보가 없습니다. 입력하신 내용을 다시 확인해주세요.");
		} //if
	</script>

</head>
<body>
    <div class="wrapper">

        <div>

            <h1>Notive 아이디 찾기</h1>
            
            <fieldset>
                
                <form action="/user/findUserID" method="get">
                    <div class="form-input">이름<br> 
                        <input class="input-box" placeholder="이름" name="userName" id="userName"type="text" />
                    </div>
                    
                    <br>
                    
                    <div class="form-input">생년월일<br>
                        <select name="year" id="year" class="input-selectBox"></select>
                        <select name="month" id="month" class="input-selectBox"></select>
                        <select name="day" id="day" class="input-selectBox"></select>
                    </div>
                    
                    <br>
                    
                    <button type="submit" class="defaultBtn" id="find-id-pass-submitBtn">찾기</button>
                    <br>
                    <a href="/user/login"><button type="button" class="crossBtn" id="find-id-pass-cancleBtn">취소</button></a>
                </form>
            </fieldset>
        </div>
    </div>
</body>

</html>