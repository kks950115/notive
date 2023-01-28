<%@ page 
   language="java" 
   contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    
<!DOCTYPE html>

<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    
    <title>LOGIN</title>

    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.3.2/jquery-migrate.min.js"></script>
	
	<script>
		var result = "${result}";
	
		if(result == "failure"){
			
			alert("아이디와 비밀번호를 다시 확인해주세요.");
		} //if
	</script>
	
    <link rel="stylesheet" href="../../resources/css/userSection.css" type="text/css"> 
    <script src="../../resources/js/userCommon.js"></script>
</head>
<body>
    
    <div class="myPage-wrapper">
        <div>

            <h1>마이페이지</h1>
            <p></p>
            아이디와 비밀번호를 다시 입력해주세요.
            <p></p>

            <fieldset class="userSection-fieldSet" id="userCheckPage-fieldSet">
                <form action="/user/userCheck" method="post">
                
                    <div class="form-input" id="userCheck-form-input">이메일<br>
                        <input class="input-box" placeholder="이메일" name="userID" id="userCheck-userID" type="email" />
                    </div>
                    <br>
                    <p></p>
                    <div class="form-input">비밀번호<br>
                        <input class="input-box" placeholder="비밀번호" name="userPass" id="userCheck-userPass" type="password" />
                    </div>
                    
                    <br>
                    <button type="submit" class="defaultBtn" id="myPage-submitBtn">확인</button>
                    <button type="button" class="crossBtn" id="myPage-cancleBtn">취소</button>
                </form>
            </fieldset>
            <br>
            
        </div>
    </div>
</body>
</html>