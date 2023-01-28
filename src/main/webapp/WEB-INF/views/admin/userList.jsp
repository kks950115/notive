<%@ page 
   language="java" 
   contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

    <script>   	

        var userID;

        $(document).ready(function () {
        

            $("a.prev, a.next").on('click', function (e) {
                console.debug('onclicked for a.next or a.prev...');
                console.log('\t+ this:' , this);

                    // Event에 의한 선택된 요소의 기본동작을 금지 (무력화)
                e.preventDefault();

                // 아래 지역변수에는 Rvalue 선택자에 의해서 선택된 요소
                // (즉 form 태그)가 저장됨
                var paginationForm = $('#paginationForm');

                var sortType = $('#sortType');
                console.log(sortType);

                paginationForm.attr('action', '${requestScope["javax.servlet.forward.request_uri"]}');
                paginationForm.attr('mathod', 'GET');

                paginationForm.find('input[name=currPage]').val($(this).attr('href'));
                paginationForm.find('input[name=amount]').val( '${pageMaker.cri.amount}' );
                paginationForm.find('input[name=pagesPerPage]').val( '${pageMaker.cri.pagesPerPage}');
                

                paginationForm.submit();

            }); // onclick for prev, next button

            $('.sort1').on('change', function () {

                $('.sort1').val($(this).val()); // 선택된 정렬조건

                $('#sortForm').submit();           
            }); 



            $('.admin_userList_selBtn').click(function () {

                userID = $(this).html();
                console.log(userID);

                var data = {"userID":userID}
                console.log(data);

                function getUserInfo() {

                $.get (

                    "/admin/getUser",
                    data,

                    function (result) {                       
                       
                        
                        var date1 = new Date(result.userGetVO.userBday);
                        var date2 = new Date(result.userGetVO.userSignupDate);
                        

                        var bday = date1.getFullYear() + "-" + (date1.getMonth()+1) + "-" + date1.getDate();
                        var signUpDay = date2.getFullYear() + "-" + (date2.getMonth()+1) + "-" + date2.getDate();
                        

                        console.log(result);

                        var user = $('.userInfo').clone();

                        user.find('.userID').text('아이디');
                        user.find('.showUserID').text(result.userGetVO.userID);

                        user.find('.userName').text('회원이름');
                        user.find('.showUserName').text(result.userGetVO.userName);

                        user.find('.userBday').text('생년월일');
                        user.find('.showUserBday').text(bday);

                        user.find('.userSignupDate').text('가입일');
                        user.find('.showUserSignupDate').text(signUpDay);
                        
                        user.find('.userGrade').text('회원등급');                                    
                        $gradeSelect = $('<select name="userGrade" id="userGrade" class="form-select">');
                        if (result.userGetVO.userGrade == '무료회원') {
                            $gradeOption1 = $('<option id="grade-1" value="1">무료회원</option>');
                            $gradeOption2 = $('<option id="grade-2" value="2">100G</option>');
                            $gradeOption3 = $('<option id="grade-3" value="3">200G</option>');
                        } else if (result.userGetVO.userGrade == '100G') {
                            $gradeOption1 = $('<option id="grade-1" value="2">100G</option>');
                            $gradeOption2 = $('<option id="grade-2" value="1">무료회원</option>');
                            $gradeOption3 = $('<option id="grade-3" value="3">200G</option>');                                        
                        } else {
                            $gradeOption1 = $('<option id="grade-1" value="3">200G</option>');
                            $gradeOption2 = $('<option id="grade-2" value="2">100G</option>');
                            $gradeOption3 = $('<option id="grade-3" value="1">무료회원</option>');                
                        }
                        user.find('.gradeSelBox').append($gradeSelect);
                        $gradeSelect.append($gradeOption1)
                        $gradeSelect.append($gradeOption2)
                        $gradeSelect.append($gradeOption3)
                        $gradeModifyBtn = $('<button class="gradeModifyBtn btn btn-outline-secondary" type="submit" id="gradeModifyBtn">수정</button>');
                        user.find('.gradeSelBox').append($gradeModifyBtn);

                        
                        user.find('.userState').text('회원상태');
                        $stateSelect = $('<select name="userState" id="userState" class="form-select">');
                        if (result.userGetVO.userState == '활성') {
                            $stateOption1 = $('<option id="state-1" value="1">활성</option>')
                            $stateOption2 = $('<option id="state-1" value="2">비활성</option>')
                        } else {
                            $stateOption1 = $('<option id="state-1" value="2">비활성</option>')
                            $stateOption2 = $('<option id="state-1" value="1">활성</option>')
                        }
                        user.find('.stateSelBox').append($stateSelect);
                        $stateSelect.append($stateOption1);
                        $stateSelect.append($stateOption2);
                        $stateModifyBtn = $('<button class="gradeModifyBtn btn btn-outline-secondary" type="submit" id="stateModifyBtn">수정</button>');
                        user.find('.stateSelBox').append($stateModifyBtn);


                        $mgrCodeSelect = $('<select name="userMgtCode" id="userMgtCode" class="userMgtCodeSelect">');
                        $codeOption1 = $('<option value="1">회원등급변경</option>');
                        $codeOption2 = $('<option value="2">회원상태변경</option>');
                        $codeOption3 = $('<option value="3">기타</option>');

                        user.find('.inputCmt-area').append($mgrCodeSelect);
                        $mgrCodeSelect.append($codeOption1);
                        $mgrCodeSelect.append($codeOption2);
                        $mgrCodeSelect.append($codeOption3);


                        $text = $('<input type="text" name="userMgtCmt" class="form-control" placeholder="comments" id="cmtText" style="width:95%">');
                        $cmtInsertBtn = $('<div><button class="cmtInsertBtn btn btn-outline-secondary" type="submit" id="cmtInsertBtn">등록</button></div>');

                        

                        user.find('.inputCmt-area').append($text);
                        user.find('.inputCmt-area').append($cmtInsertBtn);
                        

                        user.find('.thead-1').text('아이디');
                        user.find('.thead-2').text('관리자');
                        user.find('.thead-3').text('관리분류');
                        user.find('.thead-4').text('관리내용');
                        user.find('.thead-5').text('관리일자');
                        
                        
                        
                        for (var i in result.userMgtCmtVO) {

                            var date3 = new Date(result.userMgtCmtVO[i].userMgtDate);
                            var mgtDate = date3.getFullYear() + "-" + (date3.getMonth()+1) + "-" + date3.getDate();

                            $tr = $('<tr>')
                            $td1 = $('<td scope="col">').text(result.userMgtCmtVO[i].userID);
                            $td2 = $('<td>').text(result.userMgtCmtVO[i].adminID);                                 
                            $td3 = $('<td class="mgtCmtCode">').text(result.userMgtCmtVO[i].userMgtCode);
                            $td4 = $('<td class="mgtCmt">').text(result.userMgtCmtVO[i].userMgtCmt);
                            $td5 = $('<td class="mgtCmtDate">').text(mgtDate);

                            $tr.append($td1);
                            $tr.append($td2);
                            $tr.append($td3);
                            $tr.append($td4);
                            $tr.append($td5);
                            user.find('.mgtCmt-area').append($tr);    
                            
                        }
                        
                        $('.userInfoList').append(user);
                        console.log(userID);
                        console.log(user_id);
                        
                    },
                    
                    "json"
                    
                    
                    ) // get 
                } // getUserInfo()

                    $.confirm ({
                        title : "회원정보",
                        content : "" +
                        '<div class="userInfoList"></div>',

                        // theme : 'supervan',
                        type : 'dark',
                        dragWindowGap: 0,
                        closeIcon: true,

                        columnClass : 'large', 
                        
                        buttons : {
                             OK:{
                                text : '확인',
                                btnClass : 'btn-blue',

                                action : function () {
                                    location.reload();
                                }

                            },

                        },

                        onOpenBefore : function () {

                            getUserInfo();
                                
                            } // onOpen
                            
                        }); // .confirm
                        
                        
                    }) // onClick event
                    
                    
            $(document).on('click', '.cmtInsertBtn', function () {
                        
                    
                        var userMgtCode = $('#userMgtCode option:selected').val();
                        var userMgtCmt = $('#cmtText').val();
                        
                        $.ajax ({
                            type :'post',
                            url : '/admin/mgtCmtRegister',
                            data : {userID:userID,adminID:user_id,userMgtCode:userMgtCode,userMgtCmt:userMgtCmt},
                            dataType: "json",
    
                            success : function (result) {
                            }  
                            
                        })

                            if(userMgtCode == 1) {
                                userMgtCode = '회원등급변경'
                            } else if (userMgtCode == 2) {
                                userMgtCode = '회원상태변경'
                            } else {
                                userMgtCode = '기타'
                            }

                            var date3 = new Date();
                            var mgtDate = date3.getFullYear() + "-" + (date3.getMonth()+1) + "-" + date3.getDate();

                            $tr = $('<tr>')
                            $td1 = $('<td scope="col">').text(userID);
                            $td2 = $('<td>').text(user_id);                            
                            $td3 = $('<td class="mgtCmtCode">').text(userMgtCode);
                            $td4 = $('<td class="mgtCmt">').text(userMgtCmt);
                            $td5 = $('<td class="mgtCmtDate">').text(mgtDate);

                            $tr.append($td1);
                            $tr.append($td2);
                            $tr.append($td3);
                            $tr.append($td4);
                            $tr.append($td5);
                            $('.mgtCmt-area').append($tr);    
                        
                    })  // cmtInsert
                    
                    
            $(document).on('click', '.gradeModifyBtn', function () {

                        var userState = $('#userState option:selected').val();
                        var userGrade = $('#userGrade option:selected').val();

                        $.ajax ({
                            type:'post',
                            url : '/admin/userStateModify',
                            data : {userID:userID,userState:userState,userGrade:userGrade},
                            dataType:"json",

                            success : function () {
                                $.alert('수정완료')  
                            }
                        })
                        
                    })
                    
                    
                }); // .jq  


    </script>

    <style>

        header {
            padding: 0px;
            height: 0px;
        }

        section {
            border: 10px solid rgb(218, 216, 216);
            background-color: white;
        }
        
        .cmtTable {
            margin-top: 20px!important;
        }

        #gradeModifyBtn, #stateModifyBtn {

            margin-left: 20px;
        }

        #userGrade, #userState {
            margin-left: 54px;

            /* text-align: center; */
            width: 100px;
            height: 25px;
            border: 1px solid #999;
            border-radius: 8px;

            padding:0 28px 0 10px;

            -webkit-appearance: none;
            -moz-appearance: none;
            background: url("../resources/images/arrow.jpg") no-repeat 100% 50%/15px auto;
            background-size: 30%;
        }

        .col-sm-2 , .col-sm-10{

            float: left!important;
            padding-top: calc(.375rem + 18px);
            padding-bottom: calc(.375rem + 1px);

            text-align: center;

        }

        #cmtText {
            width: 72%!important;

            margin-left: 10px;
            margin-top: 25px;
            margin-bottom: 25px;
            float: left;
        }
        #cmtInsertBtn {
            float: right;
            margin-top: 25px;
        }

        #userMgtCode {
            margin-top: 25px;

            float: left;

            width: 120px;
            height: 32px;
            border: 1px solid #999;
            border-radius: 8px;

            padding:0 28px 0 10px;

            -webkit-appearance: none;
            -moz-appearance: none;
            background: url("../resources/images/arrow.jpg") no-repeat 100% 50%/15px auto;
            background-size: 30%;
        }

        

        label {
            margin-bottom: auto!important;
        }

        /* * {
            margin: 0 auto;
            padding: 0;
            text-decoration: none!important;
        } */

        a {
            color: black;
        }

        #wrapper {
            /* width: 100%; */

            height: 100%;

            font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande',
                            'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
            font-size: 14px;

        }

        #admin_userList_area {
            width: 100%;
        }

        .admin_userList {
            width: 100%;

            display: flex;
            justify-content: center;

            clear: both;
        }

        #admin_userList_Table {
            width: 100%;
            margin-top: 20px;
            
            border-collapse: collapse;           

            text-align: center;
        }

        #admin_userList_Table tr td {
            padding: 6px;
            cursor: pointer;
        }

        #admin_userList_tr:hover {
            background-color: #e0dfdf;            
        }
        
        #admin_userList_Table tr th {
			padding : 10px;
            font-size: 15px;
            /* background: linear-gradient(to bottom, #0043ca, #206bff); */

            border-top: 2px solid black;
            border-bottom: 2px solid black;
            color: black;

            text-align: center;
        }

        .admin_userList_selBtn {
            text-decoration: none;
            color: black;
        }
       

        #pagination {
            width: 95%;
            margin: 20px;
            color: white;

            display: flex;
            justify-content: center;

            clear: both;

            margin-top: 50px;
        }

        #pagination ul {
            /* float: right; */
            /* position:relative; */

            top: 80%;
            left: 45%;
        }

        #pagination li {
            list-style-type: none;

            /* display: inline; */
            float: left;

            width: 30px;
            height: 30px;

            margin: 3px;

            text-align: center;
            line-height: 30px;

            border:1px solid #ccc;
        }
        #admin_userListPagenation {
            font-size: 15px;

            width: 30px;
            height: 30px;
            
        }

        .prev {            
            width: 40px!important;

            font-size: 25px;
            color: black!important;
            
            text-decoration: none!important;
            background: url("../resources/images/page_prev.png")no-repeat center center;
            background-size: 5px;
        }

        .next {            
            width: 40px!important;

            font-size: 25px;
            color: black!important;
            
            text-decoration: none!important;
            background: url("../resources/images/page_next.png")no-repeat center center;
            background-size: 5px;
        }

        .currPage {
            width: 30px!important;

            font-size: 23px!important;
            font-weight: bold;
            color: #0043ca!important;
        }
       
        #sortBtn {
            float: right;
        }

        #idSort , #nameSort{
            cursor: pointer;
        }

        .sort1{      
            float: right;      
            margin-bottom: 10px;
            margin-right: 10px;
            text-align: center;
            width: 100px;
            height: 25px;
            border: 1px solid #999;
            border-radius: 8px;

            padding:0 28px 0 10px;

            -webkit-appearance: none;
            -moz-appearance: none;
            background: url("../resources/images/arrow.jpg") no-repeat 100% 50%/15px auto;
            background-size: 30%;
        }

        .admin_Search {
            width: 100px;
            height: 25px;
            border: 1px solid #999;
            border-radius: 8px;

            padding:0 28px 0 10px;

            -webkit-appearance: none;
            -moz-appearance: none;
            background: url("../resources/images/arrow.jpg") no-repeat 100% 50%/15px auto;
            background-size: 30%;
        }

        #admin_SearchBtn {
            width: 80px;
            height: 25px;
            border-radius: 8px;
            /* background: no-repeat 95% 50%; */
        }

        .admin_SearchInput {
            width: 200px;
            height: 20px;
        }

        #sort_block {
            clear: both;
        }

        .search_Block {
            display: flex;
            justify-content: center;

            clear: both;

            margin-top: 60px;
        }


    </style>
</head>
<body>
    <div id="wrapper">

        <h2><a href="/admin/userList">&nbsp;&nbsp;&nbsp;회원 목록</a></h2>
    
        <hr>

        <br>
    
        <div id="sort_Block">
            <form action="/admin/userList" id="sortForm" method="get">

                <select name="sortType" class="sort1">
                    <option value="">　정렬조건　</option>
                    <option value="idSortU">ID 올림차순</option>
                    <option value="idSortD">ID 내림차순</option>
                    <option value="nameSortU">이름 올림차순</option>
                    <option value="nameSortD">이름 내림차순</option>
                </select>

                <select name="sortType" class="sort1">
                    <option value="">개수조건</option>
                    <option id="amount15" value="display15">15개씩</option>
                    <option id="amount20" value="display20">20개씩</option>
                    <option id="amount30" value="display30">30개씩</option>
                </select>
            </form>
        </div>



        <div class="admin_userList">
            <div id="admin_userList_area">   
                <table id="admin_userList_Table">
                        <tr>
                            <th id="idSortBtn">ID</a></th>
                            <th>이름</th>
                            <th>생년월일</th>
                            <th>가입날짜</th>
                            <th>회원등급</th>
                            <th>회원상태</th>
                    
                        </tr>
                        
                        <c:forEach items="${userlist}" var="user">
                        <tr id="admin_userList_tr">                        
                            <td><a class="admin_userList_selBtn">${user.userID}</a></td>
                            <td><a class="admin_userList_selBtn">${user.userName}</a></td>
                            <td><a class="admin_userList_selBtn"><fmt:formatDate pattern="yyyy-MM-dd" value="${user.userBday}" /></a></td>
                            <td><a class="admin_userList_selBtn"><fmt:formatDate pattern="yyyy-MM-dd" value="${user.userSignupDate}" /></a></td>
                            <td><a class="admin_userList_selBtn">${user.userGrade}</a></td>
                            <td><a class="admin_userList_selBtn">${user.userState}</a></td>
                                                                            
                        </tr> 
                        </c:forEach>          
                        

                </table>
            </div> 
        </div>  

        <div class="search_Block">
            <div>
                <form id="searchForm" action="/admin/userList" method="GET">
                    <input type="hidden" name="currPage"     value="1">
                    <input type="hidden" name="amount"       value="${pageMaker.cri.amount}">
                    <input type="hidden" name="pagesPerPage" value="${pageMaker.cri.pagesPerPage}">

                    <select name="searchType" class="admin_Search">
                        <option>검색조건</option>
                        <option value="N" ${ ( "N" eq pageMaker.cri.searchType ) ? "selected" : "" }>이름</option>
                        <option value="I" ${ ( "I" eq pageMaker.cri.searchType ) ? "selected" : "" }>아이디</option>
                        <option value="G" ${ ( "G" eq pageMaker.cri.searchType ) ? "selected" : "" }>회원등급</option>                                       
                        <option value="S" ${ ( "S" eq pageMaker.cri.searchType ) ? "selected" : "" }>회원상태</option>                                       
                    </select>

                        <input type="text" name="keyword" class="admin_SearchInput" value="${pageMaker.cri.keyword}" placeholder="검색어를 입력해주세요">                        
                   
                    <button type="submit" id="admin_SearchBtn">Search</button>
                </form>
            </div>
        </div>       
        
        
        <div id="pagination">
            <div id="page">
            <form action='' id="paginationForm">
                
                <!-- 어느 화면에서든 , 게시판 목록 페이지로 이동시 반드시 아래 3개의 기준 전송파라미터를
                전송 시키지 위해 hidden 값으로 설정 -->
                <input type="hidden" name="currPage" value="${cri.currPage}">
                <input type="hidden" name="amount" value="${pageMaker.cri.amount}">
                <input type="hidden" name="pagesPerPage" value="${cri.pagesPerPage}">
                <input type="hidden" name="sortType" value="${cri.sortType}" id="sortType">
                
                
                <ul>
                    <c:if test="${pageMaker.prev}">
                        <li class="prev"><a class="prev" href="${pageMaker.startPage - 1}">　　</a></li>
                    </c:if>
                    <% String value1 = request.getParameter("sortType"); %>

                <!--  begin ~ end까지 반복하고 현재의 번호값은 var 속성에 넣어준다 -->
                
                    <c:forEach 
                        begin="${pageMaker.startPage}" 
                        end="${pageMaker.endPage}" 
                        var="pageNum">
                        <li>                            
                            <a class="${pageMaker.cri.currPage == pageNum? 'currPage' : ''}"
                            href="${requestScope['javax.servlet.forward.request_uri']}?currPage=${pageNum}&amount=${pageMaker.cri.amount}&pagesPerPage=${pageMaker.cri.pagesPerPage}&sortType=${cri.sortType}"
                            id="admin_userListPagenation">
                            ${pageNum}</a>
                        </li>                    
                        
                    </c:forEach>                   

                    <c:if test="${pageMaker.next}">
                        <li class="next"><a class="next" href="${pageMaker.endPage + 1}">　　</a></li>
                    </c:if>
                </ul>
            </form>
            </div>
        </div>      
               
    </div>

    <div class="userInfo">
        <div>
        <label for="userID" class="userID col-sm-2"></label>
        <div class="showUserID col-sm-10"></div>
        </div>

        <label for="userName" class="userName col-sm-2"></label>
        <div class="showUserName col-sm-10"></div>            

        <label for="userBday" class="userBday col-sm-2"></label>
        <div class="showUserBday col-sm-10"></div>

        <label for="userSignupDate" class="userSignupDate col-sm-2"></label>
        <div class="showUserSignupDate col-sm-10"></div>

        <label for="userGrade" class="userGrade col-sm-2"></label>

        <div class="gradeSelBox col-sm-10">

        </div>

        <label for="userState" class="userState col-sm-2"></label>

        <div class="stateSelBox col-sm-10">

        </div>

        <div class="inputCmt-area"> 

       </div>

       <table class="cmtTable table">
           <thead>
               <tr>
                   <th scope="col" class="thead-1"></th>
                   <th scope="col" class="thead-2"></th>
                   <th scope="col" class="thead-3"></th>
                   <th scope="col" class="thead-4"></th>
                   <th scope="col" class="thead-5"></th>
               </tr>
           </thead>
           <tbody class="mgtCmt-area">
           </tbody>
       </table>        
    </div>
</body>


