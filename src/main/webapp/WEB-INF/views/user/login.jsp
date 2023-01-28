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
	    
	<link rel="stylesheet" href="../../resources/css/userCommon.css" type="text/css"> 
	<script src="../../resources/js/userCommon.js"></script>
	
	<script>
		var result = "${result}";
	
		if(result == "failure"){
			
			alert("아이디와 비밀번호를 다시 확인해주세요.");
		} //if
	</script>
	

</head>
<body>
    <div class="wrapper">

        <div>

            <h1 id="user-title">Notive 로그인</h1>
            <form action="/user/loginPost" method="post">
            
                <fieldset>
                    <div class="form-input">이메일<br>
                        <input class="input-box" placeholder="이메일" name="userID" id="userID" type="email" />
                    </div>
                    <br>
                    <div class="form-input">비밀번호<br>
                        <input class="input-box" placeholder="비밀번호 (8~16자리)" name="userPass" id="userPass" type="password" />
                    </div>
                    <br>

                    <div>Remember-me <input type="checkbox" name="rememberMe"></div>
                    
                    <button type="submit" class="defaultBtn" id="submitBtn">LOGIN</button>
                </fieldset>
            
            </form>
        </div>
        <br>
        <div class="login-subContainer">

            <a href="/user/signUp">회원가입</a>
            <div class="login-findContainer">
	            <a href="/user/findUserIDPage">아이디 찾기</a>
	            &nbsp|&nbsp 
	            <a href="/user/findUserPassPage">비밀번호 찾기</a>
        	</div>
        </div>
    
    </div>
</body>
</html>