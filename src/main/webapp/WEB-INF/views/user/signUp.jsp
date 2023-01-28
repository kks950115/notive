<%@ page 
   language="java" 
   contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    

<!DOCTYPE html>

<html lang="ko">

<head>
	<meta charset="UTF-8">
	<meta name=viewport content="width=device-width, initial-scale=1.0">
	<title>signUp.jsp</title>
	
	
    <!-- jquery -->
	<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.3.2/jquery-migrate.min.js"></script>
	    
    <!-- BootStrap -->
    <link rel="stylesheet" href="../../resources/bootstrap-3.3.2-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../resources/bootstrap-3.3.2-dist/css/bootstrap-theme.min.css">
    <script src="../../resources/bootstrap-3.3.2-dist/js/bootstrap.min.js"></script>


    <!-- font-awsome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" referrerpolicy="no-referrer" />


    <!-- Jquery Confirm -->
    <link rel="stylesheet" href="../../resources/modal/jquery-confirm.min.css">
    <script src="../../resources/modal/jquery-confirm.min.js"></script>

    
	<!-- 외부 script, style 태그 -->
    <link rel="stylesheet" href="../../resources/css/userCommon.css" type="text/css"> 
   
	<script src="../../resources/js/userImageUpload.js"></script>
    <script src="../../resources/js/userCommon.js"></script>
    
    
</head>

<body>
    <div class="wrapper">
        <div class="wrapper-column">
            
            
            <h1 id="title">Notive</h1>
            
            
            <fieldset>
                <div>

                    <h3>회원가입을 시작합니다!<br>
                    Notive 계정 정보를 입력해주세요.</h3>
                    <form action="/user/signUp" method="post" enctype="multipart/form-data">
                        
                        <!-- 썸네일 표시 영역 -->
                        <div class="uploadResult-userImage">
                            <!-- 기본 이미지 -->
                            <img src="../../resources/images/defaultImage.png" alt="Profile" id="userImg" />
                            <ul>
                                <!-- 파일 첨부시 썸네일 표시 -->
                            </ul>
                        </div>
                        
                        <!-- 파일 첨부영역 -->
                        <div class="uploadDiv-userImage" id="uploadDiv-userImage"> 
                            <!-- 파일 선택버튼 css적용 위해, 기존 input태그 숨기고 label태그 이용하기 -->
                            <label for="input-userImage" class="defaultBtn">업로드</label>
                            <br>
                            <button type="button" id="delete-userImage" class="crossBtn">이미지 삭제</button>
                            <input type="file" name="uploadUserImg" id="input-userImage">
                        </div>
                        
                        <p></p>
                        <div class="form-input"><b>이메일</b><br>
                            <input class="input-box" placeholder="이메일" name="userID" id="signUp-input-userID" type="email" />
                            <input type="button" class="defaultBtn" id="checkIDBtn" value="중복확인" />
                            <div class="checkMsg" id="checkIDRule"></div>
                            <div class="checkMsg" id="checkID"></div>
                            <div class="signup-desc">
                                &#183; 입력한 이메일로 Notive 계정에 로그인할 수 있습니다.<br>
                                &#183; 한 번 가입한 이메일은 변경할 수 없으니, 오타가 없도록 신중히 확인해주세요.<br>
                            </div>
                        </div>
                        <p></p>
                        
                        <div class="form-input"><b>비밀번호</b><br>
                            <input class="input-box" placeholder="비밀번호 (숫자, 알파벳 포함 8~16자리)" name="userPass" id="userPass"
                            type="password" />
                            <div class="checkMsg" id="checkPass"></div>
                        </div>
                        <div class="form-input">
                            <input class="input-box" placeholder="비밀번호 재입력" name="userPassConfirm" id="userPassConfirm"
                            type="password" />
                            <div class="checkMsg" id="checkPassConfirm"></div>
                        </div>
                        <p></p>
                        <div class="form-input"><b>이름 / 생년월일</b><br>
                            <input class="input-box" placeholder="이름" name="userName" id="userName" type="text" />
                            <div class="checkMsg" id="checkName"></div>
                        </div>
                        <div class="form-input">
                            <select name="year" id="year" class="input-selectBox"></select>
                            <select name="month" id="month" class="input-selectBox"></select>
                            <select name="day" id="day" class="input-selectBox"></select>
                            <div class="checkMsg" id="checkSelectBday"></div>
                        </div>
                        <div class="signup-desc">
                            &#183; 이름과 생년월일은 이메일 및 비밀번호 찾기에 이용됩니다.<br>
                            &#183; 정확하게 입력해주세요.
                        </div>
                        <p></p>
                        <div class="checkBox"> <input type="checkbox" id="agreeTerms" name="terms" value="agree">
                            <a id="showTerms">이용약관</a> 동의
                            <div class="checkMsg" id="checkAgreeTerms"></div>
                        </div>
                        
                        <p></p>
                        <button type="button" id="signUp-submitBtn" class="defaultBtn">SignUp</button>
                    </form>
                </div>
            </fieldset>
            <p></p>
            <a href="/user/login" class="text-center">I already signed-up</a>
        </div>
    </div>
</body>

</html>