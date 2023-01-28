<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<%@ page import="org.notive.myapp.domain.UserVO" %>
<%@ page import="org.notive.myapp.domain.AdminVO" %>

<%	
	String userId ;
	
	if (session.getAttribute("__LOGIN__") != null) {		
	
    UserVO user = (UserVO)session.getAttribute("__LOGIN__");
    userId = user.getUserID();	
	} else{
    AdminVO admin = (AdminVO)session.getAttribute("__ADMIN__");
    userId = admin.getAdminID();
	}

%>

<!DOCTYPE html>
<html lang="ko">

<head>
    <!-- favicon 에러 제거 -->
    <link rel="icon" href="data:;base64,iVBORw0KGgo=">

    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <script>       
          var user_id = "<%=userId%>";    
          var wsUri = "ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/chat";        
    </script>
    <!-- Jquery -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.3.2/jquery-migrate.min.js" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" integrity="sha512-uto9mlQzrs59VwILcLiRYeLKPPbS/bT71da/OEBYEwcdNUk8jYIy+D176RYoop1Da+f9mvkYrmj5MCLZWEtQuA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>
    
    <!-- BootStrap -->
    <link rel="stylesheet" href="../../resources/bootstrap-3.3.2-dist/css/bootstrap.min.css">
    <link rel="stylesheet" href="../../resources/bootstrap-3.3.2-dist/css/bootstrap-theme.min.css">
    <script src="../../resources/bootstrap-3.3.2-dist/js/bootstrap.min.js"></script>
    
    <!-- CSS -->
    
    <link rel="stylesheet" type="text/css" href="../../resources/css/main_page.css">
    <link rel="stylesheet" type="text/css" href="../../resources/css/contextMenu.css">
    <link rel="stylesheet" href="../../resources/css/chat.css">
    <link rel="stylesheet" href="../../resources/css/icono.min.css"> 
	<link rel="stylesheet" href="/resources/css/dirSection.css"> 
	<link rel="stylesheet" href="/resources/css/schedule.css"> 
	
    <!-- Link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">


    <!-- Jquery Confirm -->
    <link rel="stylesheet" href="../../resources/modal/jquery-confirm.min.css">
    <script src="../../resources/modal/jquery-confirm.min.js"></script>
    
    
 	<!-- Summer note -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/summernote-lite.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/summernote/0.8.18/lang/summernote-ko-KR.min.js"></script>
  
  
    <!-- Java Script -->
    <script src="../../resources/js/main_general_event.js"></script>
    <script src="../../resources/js/main_crud_event.js"></script>
    <script src="../../resources/js/groupContextMenu.js"></script>
    <script src="../../resources/js/chat.js"></script>
    <script src="/resources/js/dir_crud.js"></script>
    <script src="/resources/js/file_crud.js"></script>
    <script src="/resources/js/memo_crud.js"></script>
	<script src="/resources/js/dirContextMenu.js"></script>
	<script src="/resources/js/dirSection.js"></script>
	<script src="/resources/js/dirSectionContext.js"></script>
	<script src="/resources/js/memoContextMenu.js"></script>
	<script src="/resources/js/fileContextMenu.js"></script>
	<script src="/resources/js/trashContextMenu.js"></script>
	
	
    <title>Notive</title>
</head>

<body id="bootstrap-overrides">
    <div id="wrap" class="wrap">

        <!-- header -->
        <header>
            <tiles:insertAttribute name="header" />
        </header>
        
        <!-- main -->
        <main>

            <!-- aside-left -->
            <aside class="aside-left">
                <tiles:insertAttribute name="aside-left" />
            </aside>

            <!-- section -->
            <section>
                <tiles:insertAttribute name="section" />
            </section>

            <!-- aside-right -->
            <aside class="aside-right">
                <tiles:insertAttribute name="aside-right" />
            </aside>
            
            

        </main>

    </div>

    <!-- Context Menu -->
    <tiles:insertAttribute name="contextMenu" />

    <!-- <form action="" class="newGroupUser">
        <div class="form-newGroupUser"> 
            <input type="text" placeholder="초대할 회원 아이디" class="user_id form-control" required >
        </div>
    </form> -->

</body>

</html>
