<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 
<div class="aside">

	<!-- 검색 -->
    <div class="search_box">
        <div class="search_area" action="/memo/getSearchList" method="GET">
            <div class="search_icon">
                <i class="xi-search xi-2x"></i>
            </div>
            <input id="search" type="text" name="search" placeholder="Search" autocomplete="off">
            
        </div>
    </div>

    <!-- Drive 메뉴 -->
    <div class="aside_menu" id="drive">
        <span>
            <i class="xi-caret-down-min xi-rotate-270"></i>
        </span>
        <div class="aside_icon">
            <i class="xi-cloudy xi-2x"></i>
        </div>

        <div id="topDir">
            <span class="aside_text">드라이브</span>
            <i class="fa fa-angle-down"></i>
        </div>

        <button type="button" class="dirRegisterBtn" id="drive_new" onclick="dirRegisterBtn(0,0,0)">      
            <span><i class="bi bi-plus-square"></i></span>
        </button>

        
        <!-- <button type="button" class="drive_new"><i class="bi bi-plus-square"></i></button> -->
    </div>

    <!-- 폴더 리스트 -->
    <!-- DB에서 받아서 출력해야하는 부분 -->
    <div class="directory">
        <ul id="topDir_0_0">
            <!-- 동적추가 -->
        </ul>
    </div>








    <!-- Group 메뉴 -->
    
    <div class="aside_menu" id="group">
        <span>
            <i class="xi-caret-down-min xi-rotate-270"></i>
        </span>
        <div class="aside_icon">
            <i class="xi-users-o xi-2x"></i>
        </div>
        <span class="aside_text">그룹</span>
        <button type="button" class="group_new"><i class="bi bi-plus-square"></i></button>
    </div>

    <!-- 그룹 리스트 -->
    <!-- DB에서 받아서 출력해야하는 부분 -->
    
            <!-- 새 그룹 생성시 jquery를 통해 삽입되는 코드 -->
            <!-- <a href="#" class="temp_a">
                <li class="group1" >
                    <i class="xi-folder-shared"></i>
                    <input class="temp_input" name="group_name" autocomplete="off">
                </li>
            </a> -->
            <!-- <span class="duplicated" style="display: none;">이미 존재하는 그룹 이름입니다.</span> -->
            
            <!-- <a href="/main/group/folder?group_no=${group.group_no}&group_name=${group.user_group_name}"> -->
    <ul class="group" id="ab">
        <c:forEach items="${list}" var="group" > 
            <li class="groupli" id="groupli_${group.group_no}" value="${group.group_no}">
            	<i class="xi-folder-shared"></i>${group.user_group_name}
                    <form>
                        <input type="hidden" name="group_no" value="${group.group_no}">
                        <input type="hidden" name="user_id" value="${group.user_id}">
                        <input type="hidden" name="user_group_name" value="${group.user_group_name}">
                    </form>
            </li>
        </c:forEach>
    </ul>

    <!-- 휴지통 메뉴 -->
    <div class="aside_menu" id="bin">
        <span class="blank_space"></span>
        <div class="aside_icon" id="btn_bin">
            <i class="xi-trash-o xi-2x"></i>
        </div>
        <span class="aside_text">휴지통</span>
    </div>
    
</div>

<div class="aside_fold">
    <i class="bi bi-three-dots-vertical"></i>
</div>