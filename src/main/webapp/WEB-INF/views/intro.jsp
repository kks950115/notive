<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta name=viewport content=width=device-width, initial-scale=1.0>
    <title>NOTIVE :: Intro</title>

    <!-- 아이콘 라이브러리 -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" referrerpolicy="no-referrer" />

    <!-- jquery -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.3.2/jquery-migrate.min.js"></script>
    
    <!-- slick -->
    <link rel="stylesheet" type="text/css" href="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.css" />
    <script type="text/javascript" src="//cdn.jsdelivr.net/npm/slick-carousel@1.8.1/slick/slick.min.js"></script>

    <link rel="stylesheet" href="../../resources/css/intro.css" type="text/css"> 
    <script src="../../resources/js/intro.js"></script>


</head>

<body>

    <div id="wrapper">

        <header>
           <!-- <img id="logo" src="../../resources/images/logo.img"> --> 
            <div id="logo"><div id="N">N</div>otive</div>
        </header>

        <div class="clear"></div>
        
        <section>

            <div class="slick-sample">
                <div id="1"><img class="slick-contents" src="../../resources/images/slick1_400px.png"></div>
                <div id="2"><img class="slick-contents" src="../../resources/images/slick2_400px.png"></div>
                <div id="3"><img class="slick-contents" src="../../resources/images/slick3_400px.png"></div>
            </div>

            <br><br>

        </section>
        <div id="enter">
            <div id="login" class="enter_btn">
                <a href="/user/login"> 로그인</a></div>
            <div id="join" class="enter_btn">
                <a href="/user/signUp"> 회원가입 </a></div>
        </div>

    </div>
    

</body>
</html>
