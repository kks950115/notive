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

        function close_reload(){
        window.opener.document.location.href = window.opener.document.URL;
        }

        $(function () {
            $('#closeBtn').click(function () {

                window.close();                
            })
            
        
    })

    </script>
    
</head>
<body onunload="close_reload()">
	<h1>itemModify</h1>

    <hr>

    <div id="wrapper">

        <form action="/admin/itemModify" method="POST">
            
            <fieldset>
                <table>
                <legend>상품 수정</legend>

                <tr>
                    <td><label for="itemNo">상품 번호</label></td>
                    <td><input type="hidden" name="itemNo" value="${getItem.itemNo}">${getItem.itemNo}</td>
                </tr>
                <tr>
                    <td><label for="itemName">상품명</label></td>
                    <td><input type="text" name="itemName" value="${getItem.itemName}"></td>
                </tr>
                <tr>
                    <td><label for="itemCapa">추가용량</label></td>
                    <td><input type="text" name="itemCapa" value="${getItem.itemCapa}"></td>
                </tr>
                <tr>
                    <td><label for="itemPrice">상품 가격</label></td>
                    <td><input type="text" name="itemPrice" value="${getItem.itemPrice}"></td>
                </tr>
                <tr>
                    <td><label for="itemRegiDate">등록 일자</label></td>
                    <td>${getItem.itemRegiDate}</td>
                </tr>
                <tr>
                    <td><label for="itemDelDate">관리 일자</label></td>
                    <td>${getItem.itemDelDate}</td>
                </tr>
                <tr>
                    <td><label for="itemState">상품상태</label></td>
                    <td>${getItem.itemState}</td>
                </tr>
            </table>
            </fieldset>

            <button type="submit">수정</button>
            <button type="button" id="closeBtn">나가기</button>
        </form>

    </div>

</body>
</html>