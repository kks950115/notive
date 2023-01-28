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

    <style>

        /* 레이아웃  */
        /* ========================================================================== */

        * {
            margin: 0 auto;
            padding: 0;
            text-decoration: none!important;
        }

        body {
            background: linear-gradient(to right, #003bab, #72a0f6);
        }

        #wrapper {
            width: 1800px;
            height: 900px;

            font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande',
                            'Lucida Sans Unicode', Geneva, Verdana, sans-serif;

            display: flex;
            justify-content: center;
            align-items: center;

            /* border: 1px solid red; */
        }

        #admin_Login_Box {
            width: 1200px;
            height: 500px;

            background: white;
            border-radius: 20px;

            box-shadow:  70px 0px 100px -90px #000000,
                         0px 70px 100px -70px #000000;
        }



        /* 왼쪽   */
        /* ========================================================================== */

        
        .logo_area {
            width: 599px;
            height: 500px;

            float: left;

            /* border-right: 1px solid rgb(170, 168, 168); */

            display: flex;
            justify-content: center;
            align-items: center;
            
        }

        #logo_block {
            width: 599px;
            height: 460px;

            border-right: 1px dashed rgb(170, 168, 168);

            display: flex;
            justify-content: center;
            align-items: center;
        }

        #logo_images {
            width: 550px;
            height: 400px;

            background: url("../resources/images/클라우드이미지.png");
            background-size: 540px 360px;
            background-repeat: no-repeat;
        }


         /*  오른쪽 */
        /* ========================================================================== */


        .login_area {
            width: 600px;
            height: 500px;

            float: left;
        }

        #logo_box {
            width: 600px;
            height: 80px;

            margin-top: 10px;

            /* border: 1px solid red; */

            display: flex;
            justify-content: center;
            align-items: center;

            clear: both;
        }

        #logo {
            padding-top: 30px;
        }

        p {
            float: right;
        }

        .login_box {
            width: 600px;
            height: 260px;

            margin-top: -20px;

            display: flex;
            justify-content: center;
            align-items: center;

            /* border: 1px solid red; */
        }

        #login_form {
            width: 350px;
            height: 150px;

            /* border: 1px solid red; */
        }

        .input-box {
            width: 248px;
            height: 45px;

            outline: none;
            padding-left: 10px;

            font-size: 20px;

            border: white;
        }

        hr {
            border: 1px solid #003bab;
        }

        #login_button_box {
            width: 600px;
            height: 100px;

            display: flex;
            justify-content: center;

            /* border: 1px solid red; */
        }

        #login_Btn {
            width: 250px;
            height: 50px;

            border-radius: 50px;
            /* border:  1px solid red; */
            box-shadow:  4px 4px 9px 0px #3b3b3b;
        }

        #submitBtn {
            width: 250px;
            height: 50px;

            background: linear-gradient(to right bottom, #72a0f6 50%,#ffffff);
            border-radius: 50px;

            font-size: 20px;
            color: white;

            cursor: pointer;
        }

        


        



    </style>
</head>
<body>

    <div id="wrapper">

        <div id="admin_Login_Box">

            <div class="logo_area">       <!-- left area -->

                <div id="logo_block">

                    <div id="logo_images">

                    </div>
                </div>
            </div>

            <!-- ===============================================================================  -->


            <div class="login_area">       <!-- right area -->

                <div id="logo_box">
                    <div id="logo">
                        <img src="../resources/images/로고.png" width="180" height="50"><br>
                        <p>ADMIN</p>
                    </div>
                </div>

                <br>

                <form action="/adminLoginPost" method="post">
                    <div class="login_box">
                        
                        <div id="login_form">
                            <input class="input-box" placeholder="Username" name="adminID" id="adminID" type="email" />                       
                            <hr>
                            <br><br>
                            <input class="input-box" placeholder="Password" name="adminPass" id="adminPass" type="password" />
                            <hr>
                        </div>
                    </div>

                    <div id="login_button_box">
                        <div id="login_Btn">
                            <button type="submit" id="submitBtn">LOGIN</button>
                        </div>
                    </div>
                </form>

            </div>

        </div>


















        <!-- <form action="/admin/adminLoginPost" method="post">
    
            
                <div class="admin-form-input">관리자 이메일<br>
                    <input class="input-box" placeholder="이메일" name="adminID" id="adminID" type="email" />
                </div>

                <p></p>
                
                <div class="form-input">비밀번호<br>
                    <input class="input-box" name="adminPass" id="adminPass" type="password" />
                </div>

                
            

        </form> -->

    </div>
</body>


</html>