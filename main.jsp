<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>

<html lang="ko">

<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- CSS -->
    <link rel="stylesheet" type="text/css" href="../../resources/css/main_page.css">
    <link rel="stylesheet" href="../../resources/css/icono.min.css">

    <!-- Link -->
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.5.0/font/bootstrap-icons.css">
    <link rel="stylesheet" href="//cdn.jsdelivr.net/npm/xeicon@2.3.3/xeicon.min.css">

    <!-- Jquery -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.3.2/jquery-migrate.min.js" referrerpolicy="no-referrer"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jqueryui/1.12.1/jquery-ui.min.js" integrity="sha512-uto9mlQzrs59VwILcLiRYeLKPPbS/bT71da/OEBYEwcdNUk8jYIy+D176RYoop1Da+f9mvkYrmj5MCLZWEtQuA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

    <!-- popup plugin -->
    <script src="../../resources/js/jquery.bpopup.min.js"></script>

    <!-- Java Script -->
    <script src="../../resources/js/main_general_event.js"></script>
    <script src="../../resources/js/main_crud_event.js"></script>

    <title>main.jsp</title>
</head>

<body>
    <div id="wrap" class="wrap">

        <!-- header -->
        <header>
            <a href="/main" id="title">Notive</a>

            <i class="xi-cloud-o xi-2x"></i>

            <div class="progress">
                <div class="bar-container">
                    <div id="Content_bar" class="bar"></div>
                </div>
                <span>1.5GB / 20GB</span>
            </div>

            <span class="mypage_icon">
                <a href="/main/mypage"><i class="xi-profile xi-3x"></i></a>
            </span>

        </header>
        
        <!-- main -->
        <main>

            <!-- aside -->
            <aside>

                <div class="aside">

                    <div class="search_box">
                        <form class="search_area">
                            <div class="search_icon">
                                <i class="xi-search xi-2x"></i>
                            </div>
                            <input type="search" name="q" placeholder="Search" autocomplete="off">
                        </form>
                    </div>

                    <!-- Drive 메뉴 -->
                    <div class="aside_menu" id="drive">
                        <span>
                            <i class="xi-caret-down-min xi-rotate-270"></i>
                        </span>
                        <div class="aside_icon">
                            <i class="xi-cloudy xi-2x"></i>
                        </div>
                        <span class="aside_text">Drive</span>
                        <button type="button" class="drive_new"><i class="bi bi-plus-square"></i></button>
                    </div>

                    <!-- 폴더 리스트 -->
                    <!-- DB에서 받아서 출력해야하는 부분 -->
                    <ul class="folder">
                        <a href="#">
                            <li class="folder1" ><i class="xi-folder-o"></i>폴더1</li>
                        </a>
                        <a href="#">
                            <li class="folder1" ><i class="xi-folder-o"></i>폴더1</li>
                        </a>
                    </ul>

                    <!-- Group 메뉴 -->
                    <div class="aside_menu" id="group">
                        <span>
                            <i class="xi-caret-down-min xi-rotate-270"></i>
                        </span>
                        <div class="aside_icon">
                            <i class="xi-users-o xi-2x"></i>
                        </div>
                        <span class="aside_text">Group</span>
                        <button type="button" class="group_new"><i class="bi bi-plus-square"></i></button>
                    </div>

                    <!-- 그룹 리스트 -->
                    <!-- DB에서 받아서 출력해야하는 부분 -->
                    <ul class="group">

                        <!-- 새 그룹 생성시 jquery를 통해 삽입되는 코드 -->
                        <!-- <a href="#" class="temp_a">
                            <li class="group1" >
                                <i class="xi-folder-shared"></i>
                                <input class="temp_input" name="group_name" autocomplete="off">
                            </li>
                        </a> -->
                        <!-- <span class="duplicated" style="display: none;">이미 존재하는 그룹 이름입니다.</span> -->
                        
                        <c:forEach items="${list}" var="group" >
                            <a href="/main/group/folder?group_no=${group.group_no}&group_name=${group.group_name}">
                                <li class="group1" >
                                        <i class="xi-folder-shared"></i>${group.group_name}
                                </li>
                            </a>
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

            </aside>

            <!-- section -->
            <section>
                <div class="section_title">Drive</div>
                <div>
                    <div class="file_area">
                        <div class="file">
                            <div class="name">제목</div>
                            <div class="content">내용...</div>
                        </div>
                        <div class="file">
                            <div class="name">제목</div>
                            <div class="content">내용...</div>
                        </div>
                        <div class="file">
                            <div class="name">제목</div>
                            <div class="content">내용...</div>
                        </div>
                        <div class="file">
                            <div class="name">제목</div>
                            <div class="content">내용...</div>
                        </div>
                        <div class="file">
                            <div class="name">제목</div>
                            <div class="content">내용...</div>
                        </div>
                        <div class="file">
                            <div class="name">제목</div>
                            <div class="content">내용...</div>
                        </div>

                    </div>
                </div>
            </section>

            <!-- add-on -->
            <div class="add_on">
                <div class="message">message</div>
                <div class="chat">chat</div>
                <div class="calendar">calendar</div>
            </div>

            <!-- add-on_list_icon -->
            <div class="add_on_list">
                <i class="bi bi-envelope" id="message"></i>
                <i class="bi bi-chat" id="chat"></i>
                <i class="bi bi-calendar-week" id="calendar"></i>
            </div>

        </main>

    </div>
</body>

</html>
