<%@ page 
   language="java" 
   contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <script>    
    
    function close_reload(){
    window.opener.document.location.href = window.opener.document.URL;
    }

        $(function () {
            
            $('#listBtn').click(function () {
                
                opener.parent.location.reload();

                window.close();
            })

            $('#modifyBtn').on('click', function (e) {

                e.preventDefault();

                var modifyForm = $('#registerForm');

                modifyForm.attr('action', '/admin/userStateModify');
                modifyForm.attr('method', 'post');

                modifyForm.submit();
                
            })

        }); // .jq

    </script>


    <style>

        * {
            margin: 0 auto;
            padding: 0;
        }

        #wrapper {
            width: 700px;
           
        }

        #userInfo {
            width: 70p!important;
        }

        th {
            width: 150px;
        }

        td {
            padding : 10px;
        }

        #regiBtn {
            width: 60px;
            height: 30px;

            background-color: blue;
            color: white;
            
        }

        #listBtn {
            width: 60px;
            height: 30px;

            background-color: red;
            color: white;
        }    
        
        #mgtInfo {
            overflow: scroll;
            width: 720px;
            height: 100px;
        }

    </style>
</head>
<body onunload="close_reload()">
    
    <h1>GET.jsp</h1>

    <hr>
	
	<p>&nbsp;</p>
	
    <div id="wrapper">

        <form action="/admin/mgtCmtRegister" id="registerForm" method="POST">
            
       
            <table id="userInfo">

                <tr>
                    <th></th>
                    <th></th>
                </tr>
                
                <tr>
                    <td><label for="userID" class="label">아이디</label></td>
                    <td><input type="hidden" name="userID" value="${userGetVO.userID}">${userGetVO.userID}</td>
                </tr>
                <tr>
                    <td><label for="userName">이름</label></td>
                    <td>${userGetVO.userName}</td>
                </tr>
                <tr>
                    <td><label for="userBday">생년월일</label></td>
                    <td>${userGetVO.userBday}</td>
                </tr>
                <tr>
                    <td><label for="userSignDate">가입일자</label></td>
                    <td><fmt:formatDate pattern="yyyy-MM-dd" value="${userGetVO.userSignupDate}" /></td>
                </tr>               
                <tr>
                    <td><label for="userGrade">회원등급</label></td>
                    <td>
                        <select name="userGrade" id="">
                            <c:if test = "${userGetVO.userGrade eq 1}">
                                <option value="1">무료회원</option>
                                <option value="2">100GB</option>
                                <option value="3">200GB</option>
                            </c:if>
                            <c:if test = "${userGetVO.userGrade eq 2}">
                                <option value="2">100GB</option>
                                <option value="1">무료회원</option>
                                <option value="3">200GB</option>
                            </c:if>
                            <c:if test = "${userGetVO.userGrade eq 3}">
                                <option value="3">200GB</option>
                                <option value="1">무료회원</option>
                                <option value="2">100GB</option>
                            </c:if>
                        </select>
                    </td>
                    
                </tr>
                <tr>
                    <td><label for="userState">회원상태</label></td>
                    <td>                      
                        <select name="userState" id="">
                            <c:if test = "${userGetVO.userState eq 1}">
                                <option value="1">활성</option>
                                <option value="2">비활성</option>
                            </c:if>
                            <c:if test = "${userGetVO.userState eq 2}">
                                <option value="2">비활성</option>
                                <option value="1">활성</option>
                            </c:if>
                        </select>                                                   
                    </td>                    
                </tr>
                <tr>
                    <td>
                        <button type="button" id="modifyBtn">수정</button>
                    </td>
                </tr>
            

            </table>

            <hr>
            <p>&nbsp;</p>

            <select name="userMgtCode" id="">
                <option value="1">회원등급변경</option>
                <option value="2">회원상태변경</option>
                <option value="3">기타</option>
            </select>
            <textarea name="userMgtCmt" id="mgtCmt" cols="90" rows="5" placeholder="내용을 입력하세요"></textarea>
			<button type="submit" id="regiBtn">수정완료</button>
			<p>&nbsp;</p>
                  
			
            <div id="mgtInfo">
                <table>
                
                    <tr>
                        <th>아이디</th>
                        <th>관리자</th>
                        <th>관리분류</th>
                        <th>관리내용</th>
                        <th>관리일자</th>
                    </tr>

                    <c:forEach items="${userMgtCmtVO}" var="userMgtCmtVO">
                    <tr>
                        <td>${userMgtCmtVO.userID}</td>
                        <td>${userMgtCmtVO.adminID}</td>
                        <td>${userMgtCmtVO.userMgtCode}</td>
                        <td>${userMgtCmtVO.userMgtCmt}</td>
                        <td>
                            <fmt:timeZone value="GMT+9">
                            <fmt:formatDate pattern="yyyy-MM-dd HH:mm" value="${userMgtCmtVO.userMgtDate}" />
                            </fmt:timeZone>
                        </td>
                    </tr>
                    </c:forEach>
                </table>
            </div>
            
            <button type="button" id="listBtn">취소</button>
            
            
            
        </form>
    </div>

    
</body>
