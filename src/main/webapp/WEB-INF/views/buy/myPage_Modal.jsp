<%@ page language="java" contentType="text/html; charset=UTF-8"
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

    <title>myPage_Modal</title>

    <!-- ======================== JQUERY ======================= -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.3.2/jquery-migrate.min.js"></script>

    <!-- ======================== Bootstrap ======================= -->
    <!-- 합쳐지고 최소화된 최신 CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <!-- 부가적인 테마 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <!-- 합쳐지고 최소화된 최신 자바스크립트 -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <!-- 버튼효과 -->
    <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">


    <style>
        .modal-check {
            text-align: center;
        }



        .cb {
            /* display:flex; 
       		width:200px; 
       		line-height:50px;
       		justify-content: 
               -around; */
            display: block;
            font-size: 15px;
            margin-bottom: 10px;

        }
    </style>

    <script>
        var temp = "${cancelResult}";

        console.debug(temp);

        if (temp == "S") {
            alert("기존 결제 취소에 성공했습니다.")
        } else if (temp == "F") {
            console.debug(temp);
            alert("결제 내역이 없거나, 결제 취소 기간이 경과되었습니다.")
            
        } //if-else if
    </script>

</head>

<body>
    <!--  ${cancelResult}-->

    <!-- <input type="text" id="cancel_info" name="cancel_Info" value="${cancelResult}"> -->


    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#cancelPurchase">
        결제취소
    </button>


    <!-- 결제취소 확인 모달 -->
    <div class="modal" id="cancelPurchase">

        <div class="modal-dialog">
            <div class="modal-content">

                <!-- 모달창 Header -->
                <div class="modal-header">
                    <h5 class="modal-title">결제 취소</h5>
                </div>

                <!-- 모달창 body -->
                <div class="modal-body">
                    <h4 class="modal-check">기존 결제 신청을 취소하시겠습니까?</h4>

                </div>

                <!-- 모달창 footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-toggle="modal"
                        data-target="#whyCancel">확인</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div><!-- 결제취소 확인 모달 -->


    <!--===================================================================================================================-->
    <!-- 취소 사유 확인 모달 모달 -->

    <div class="modal" id="whyCancel">

        <div class="modal-dialog">
            <div class="modal-content">

                <!-- 모달창 Header -->
                <div class="modal-header">
                    <h5 class="modal-title"><span class=bold>결제 신청 취소 사유를 선택해주세요.</span></h5>
                </div>

                <!-- 모달창 body -->
                <div class="modal-body">
                    <div class="cb"><input type="checkbox" name="basic_Capa"> 기본 제공 용량</div>
                    <div class="cb"><input type="checkbox" name="price"> 가격</div>
                    <div class="cb"><input type="checkbox" name="reliability"> 기업 네임밸류</div>
                    <div class="cb"><input type="checkbox" name="quality"> 서비스 만족도</div>
                    <div class="cb"><input type="checkbox" name="similarService"> 유사 서비스 이용 중</div>
                    <div class="cb"><input type="checkbox" name="other"> 기타</div>
                </div>

                <!-- 모달창 footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#cardInfo">확인</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div><!-- 취소사유 확인 -->

    <!--===================================================================================================================-->

    <!-- 이메일 확인 모달 -->

    <div class="modal" id="cardInfo">

        <div class="modal-dialog">
            <div class="modal-content">

                <!-- 모달창 Header -->
                <div class="modal-header">
                    <h5 class="modal-title">결제 신청 취소</h5>
                </div>

                <!-- 모달창 body -->
                <div class="modal-body" id="id_insert">
                    <form action="/buy/updateCancel" method="POST">
                        <table>
                                <p><b>- '결제 신청 취소'</b>를 희망하신다면, 고객님의 Notive ID를 입력해주세요.
                                    <p>
                                        <tr>
                                            <td><label for="user_id"> Notive ID 입 력 : &nbsp</label></td>
                                            <td><input type="text" name="user_id" placeholder="이메일 형식 ID 입력!"
                                                    required value="${buy.user_id}">
                                            </td>
                                            
                                        </tr>
                        </table>
                    </form>
                </div>

                <!-- 마지막 버튼 -->

                <div class="modal-footer">
                    <div class="buttons">
                        <button type="submit" id="cancel_finish" class="btn btn-danger">확인</button>
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                    </div>
                </div>


            </div>

        </div>
    </div>
    </div><!-- 카드번호 확인 -->

</body>

</html>