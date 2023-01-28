<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

    <!DOCTYPE html>

    <html lang="ko">
    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        
        <title>list.jsp</title>
    
        <style>
            *{
                margin: 0 auto;
                padding: 0;
            }
    
            #wrapper {
                width: 1024px;
    
                font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande', 'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
                font-size: 14px;
            }
    
            table {
                width: 95%;
    
                border: 1px ridge green;
                border-collapse: collapse;
    
                text-align: center;
            }
    
            th {
                padding: 10px;
    
                color: white;
                background-color: blue;
    
                font-size: 16px;
            }
    
            td {
                padding: 3px;
            }
    
            caption {
                font-size: 16px;
                font-weight: bold;
    
                padding: 0;
            }
    
           #topmenu > li {
               float: left;
    
               text-align: center;
               line-height: 50px;
               list-style: none;
    
               width: 33%;
               height: 50px;
           }
    
           #regBtn {
               width: 150px;
               height: 40px;
    
               border: 0;
    
               font-size: 15px;
               font-weight: bold;
    
               color: white;
               background-color: red;
           }
    
           tr:hover {
               background-color: rgb(239, 253, 226);
           }
    
           a, a:link, a:visited {
                text-decoration: none;
                
                color: black;
    
                cursor: pointer;
           }
    
           td:nth-child(2) {
               text-align: left;
    
               width: 40%;
               padding-left: 10px;
           }
        </style>
    
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.3.2/jquery-migrate.min.js" referrerpolicy="no-referrer"></script>

    </head>
    <body>
       
    
    
    
        <div id="wrapper">
    
            <table border = "2">
                
                <thead>
                    <tr>
                        <th>buy_no</th>
                        <th>item_no</th>
                        <th>buy_date</th>
                        <th>buy_credit_no</th>
                        <th>user_id</th>
    
                    </tr>
                </thead>
    
                <tbody>
                    
                    <c:forEach items="${list}" var="buy">
                        
                        <tr>    
                            <td><c:out value="${buy.buy_no}"/></td>
                            <td><c:out value="${buy.item_no}"/></td>
                            <td><fmt:formatDate pattern="yyyy/MM/dd HH:mm:ss" value="${buy.buy_date}" /></td>
                            <td><c:out value="${buy.buy_credit_no}"/></td>
                            <td><c:out value="${buy.user_id}"/></td>
                        </tr>
                    </c:forEach>
                </tbody>
            
            
            </table>
    
        </div>
    
        <p>${result}</p>
    
    </body>
    </html>