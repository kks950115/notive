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

    <script>

        $(function () {

            $('#closeBtn').on('click', function () {

                location.href = "/admin/itemList";
                
            })
            
        })

    </script>

    <style>

        * {
            margin: 0 auto;
            padding: 0;
            text-decoration: none!important;
        }

        #wrapper {
            width: 1024px;
            height: 900px;

            font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande',
                            'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
            font-size: 14px;

            display: flex;
            justify-content: center;
            align-items: center;

        }

        #register_Block {
            width: 600px;
            height: 600px;

            justify-content: center;
            align-items: center;

            box-shadow: -60px 0px 100px -90px #000000,
                         60px 0px 100px -90px #000000;

        }

        input {
            width: 160px;
            height: 30px;

            margin-bottom: 10px;

            border: white;
            border-bottom: 2px solid #0043ca;

            text-align: center;
            font-size: 15px;
        }

        input:focus {
            animation-name: border-focus;
            animation-duration: 1s;
            animation-fill-mode: forwards;
            outline: none;

            text-align: center;
            font-size: 15px;
        }

        @keyframes border-focus {
            from {
            border-radius: 0;
            }
            to {
            border-radius: 15px;
            border: 1px solid rgb(102, 102, 102);
            }
        }

        fieldset {
            width: 600px;
            height: 600px;
            border: 2px solid #b6b6b6;
            border-radius: 10px;

            background-image: url("../resources/images/구름.png");
            background-repeat: no-repeat;
            background-size: 580px 390px;   
            
        }

        legend {

            font-size: 20px;

            padding: 5px;
            
        }

        #registerForm {
            width: 600px;
            height: 490px;
            display: flex;

            justify-content: center;
            align-items: center;
        }

        table tr {
            font-size: 18px;
        }

        #btnBox {
            clear: both;

            display: flex;

            justify-content: center;

            
        }

        #registerBtn {
            width: 80px;
            height: 40px;

            font-size: 20px;

            border-radius: 10px;

            cursor: pointer;
        }

        #registerBtn:hover {
            background-color: #dbdbdb;
        }

        #closeBtn {
            width: 80px;
            height: 40px;
            
            font-size: 20px;

            border-radius: 10px;

            cursor: pointer;
        }

        #closeBtn:hover {
            background-color: #dbdbdb;
        }

        button {
            margin: 0 0 0 15px;
        }



    </style>
</head>
<body>
    <div id="wrapper">      
        
        <div id="register_Block">

            
                <fieldset>
                    <legend><img src="../resources/images/로고.png" width="100" height="30"></legend>
                    <div id="registerForm">
                        <form action="/admin/itemRegister" method="POST">

                            <!-- <images src="../resources/images/구름.png" alt=""> -->
                            <div>
                                <table>
                                    <tr>
                                        <td>상품명 &nbsp;&nbsp;&nbsp;<input type="text" name="itemName" id="itemName">&nbsp;GB</td>
                                    </tr>
                                    <tr>
                                        <td>추가용량&nbsp;<input type="text" name="itemCapa" id="itemCapa">&nbsp;GB</td>
                                    </tr>
                                    <tr>
                                        <td>상품가격&nbsp;<input type="text" name="itemPrice" id="itemPrice">&nbsp;원</td>
                                    </tr>
                                </table>
                            </div>
                            
                        
                        
                    </div>
                    <div id="btnBox">
                        <div>
                            <button id="registerBtn">등록</button>
                            <button type="button" id="closeBtn">취소</button>
                        </div>
                    </div>
                    </form>
                </fieldset>
            

        </div>

    </div>
    
</body>
</html>