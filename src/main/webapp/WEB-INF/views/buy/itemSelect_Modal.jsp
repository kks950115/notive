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

    <title>item1PurchaseCheck_Modal.jsp</title>

    <!-- ======================== JQUERY ======================= -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.6.0/jquery.min.js" referrerpolicy="no-referrer">
    </script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-migrate/3.3.2/jquery-migrate.min.js"
        referrerpolicy="no-referrer"></script>

    <!-- ======================== Bootstrap ======================= -->
    <!-- 합쳐지고 최소화된 최신 CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
    <!-- 부가적인 테마 -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
    <!-- 합쳐지고 최소화된 최신 자바스크립트 -->
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
    <!-- 버튼효과 -->
    <link href="https://fonts.googleapis.com/css2?family=Lato&display=swap" rel="stylesheet">

    <script>
        //카드번호 및 CVC 검사
        $(document).ready(function () {

            $('#submit_finish').click(function () {
                console.debug(">>> click on #submit_finish invoked.");

                //cardNcvc 객체 생성하여 값을 객체에 넣음
                var cardNcvc = {
                    "buy_credit_no": $('.buy_credit_no').val(),
                    "cvc_no": $('.cvc_no').val(),
                } //var cardNcvc

                //--------------------------------------------------------------------//

                //1. 카드번호 확인 => 룬 알고리즘을 통한 실제 사용 가능 카드만 입력 가능.
                if (cardNcvc.buy_credit_no.length != 16) {
                    alert('카드번호를 확인해주세요!');
                    return false;
                    //카드번호 16자 아닐 경우 alert!
                } else {
                    //룬 알고리즘 시작!
                    var digits = cardNcvc.buy_credit_no.split(''); //혹시나 있을 빈칸 제거
                    for (var i = 0; i < digits.length; i++) {
                        digits[i] = parseInt(digits[i], 10);
                    } //for

                    //상기 배열을 대상으로 알고리즘 실행
                    var sum = 0;
                    var alt = false;

                    for (var i = digits.length - 1; i >= 0; i--) {
                        if (alt) {
                            digits[i] *= 2;
                            if (digits[i] > 9) {
                                digits[i] -= 9;
                            } //if
                        } //if
                        sum += digits[i];
                        alt = !alt;
                    } //for

                    if (sum % 10 == 0) {
                        alert('카드결제가 완료되었습니다.');
                        return true;
                        //유요한 카드번호!
                    } else {
                        alert('유효하지 않은 카드번호입니다.');
                        return false;
                        //실제 카드번호가 아닌 경우에는 유효하지 않은 카드번호라고 alert
                    } //if-else
                } //if-else 카드체크 유효성 완료!


                //2. CVC번호 확인 => 3자리 숫자만 가능, 별도의 알고리즘 없음.
                if (cardNcvc.cvc_no.length != 3) {
                    alert('CVC번호를 확인하세요!');
                    return false;
                } //if
            }); //#submit_finish
        }); //jQuery
    </script>

    <style>
        #item_images {
            display: flex;
            justify-content: space-around;
            margin-bottom: 10px;

            position: relative;
        }

        #item_button {
            display: flex;
            justify-content: space-around
        }

        #item_button {
            margin-top: 20px;
            margin-bottom: 20px;
        }

        .modal-check {
            text-align: center;
        }

        .item_info {
            display: flex;
            justify-content: space-around;
            font-weight: bold;
            font-size: 16px;
            color: rgb(58, 75, 139);
        }

        #item_name {
            display: flex;
            justify-content: space-around;
            margin-left: 10px;
            margin-bottom: 5px;

            font-size: 28px;
            font-weight: bold;
            color: black;
        }

        #item_price {
            display: flex;
            justify-content: space-around
        }

        #item_price {
            display: flex;
            justify-content: space-around;

            font-size: 16px;
            font-color: black;
        }

        #cloud_1, #cloud_2 {
            margin-top: 20px;
        }
    </style>

</head>

<body>


    <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#myModal">
        구름구름
    </button>


    <!-- 모달창 설정 -->
    <div class="modal" id="myModal">

        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <!-- 모달창 Header -->
                <div class="modal-header">
                    <h5 class="modal-title">상품 선택</h5>
                </div>

                <!-- 모달창 body -->
                <div class="modal-body">
                    <!-- 상품 이미지 -->
                    <div id="item_images" class="images">
                        <img id="basic" src="../../resources/images/cloud_gray.JPG" width="200" height="132">
                        <img id="item_1" src="../../resources/images/cloud.JPG" width="200" height="132">
                        <img id="item_2" src="../../resources/images/cloud.JPG" width="200" height="132">
                    </div>



                    <!--  DB에서 상품명 수정 시 해당 부분에 반영 -->
                    <div id="item_name" class="names">

                        <c:forEach items="${itemList}" var="item">

                            <span>
                                <c:out value="${item.item_name}" /></span>

                        </c:forEach>
                    </div>

                    <!--  DB에서 상품 가격 수정 시 해당 부분에 반영 -->
                    <div id="item_price">

                        <c:forEach items="${itemList}" var="item">

                            <span>
                                <c:out value="${item.item_price}" /></span>

                        </c:forEach>
                    </div>


                    <!-- 상품 선택 버튼 -->
                    <div id="item_button">
                        <button type="button" id="basic" class="btn btn-secondary" disabled>기본</button>
                        <button type="button" id="100GB" class="btn btn-primary" data-toggle="modal"
                            data-target="#item1Check">선택</button>
                        <button type="button" id="200GB" class="btn btn-primary" data-toggle="modal"
                            data-target="#item2Check">선택</button>

                    </div>

                    <!-- 모달창 footer -->
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                    </div>

                </div>
            </div>
        </div>
    </div>
    <!-- 구름버튼 모달 끝 -->


    <!-- ===================================================================================================================== -->


    <!-- 상품1 선택 확인 모달 -->
    <div class="modal" id="item1Check">

        <div class="modal-dialog">
            <div class="modal-content">

                <!-- 모달창 Header -->
                <div class="modal-header">
                </div>

                <!-- 모달창 body -->
                <div class="modal-body">
                    <h4 class="modal-check">해당 상품의 결제 신청을 진행하시겠습니까?</h4>
                </div>

                <!-- 모달창 footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-toggle="modal"
                        data-target="#item1InfoInsert">확인</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 상품1 선택 확인 모달 끝 -->


    <!-- 상품2 선택 확인 모달 -->
    <div class="modal" id="item2Check">

        <div class="modal-dialog">
            <div class="modal-content">

                <!-- 모달창 Header -->
                <div class="modal-header">
                    <h5 class="modal-title">상품 선택 확인</h5>

                </div>

                <!-- 모달창 body -->
                <div class="modal-body">
                    <h4 class="modal-check">해당 상품의 결제 신청을 진행하시겠습니까?</h4>
                </div>

                <!-- 모달창 footer -->
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-toggle="modal"
                        data-target="#item2InfoInsert">확인</button>
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                </div>
            </div>
        </div>
    </div>
    <!-- 상품2 선택 확인 모달 끝 -->

    <!-- ===================================================================================================================================== -->


    <!-- 상품1 카드 정보 입력 모달 -->
    <div class="modal" id="item1InfoInsert">

        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <!-- 모달창 Header -->
                <div class="modal-header">
                    <h5 class="modal-title">카드 정보 입력</h5>
                </div>

                <!-- 모달창 body -->
                <div class="modal-body">


                    <div id="item_images">
                        <img id="cloud_1" src="../../resources/images/cloud.JPG" width="200" height="132">

                        <div id="wrapper">

                            <form action="/buy/register" method="POST">

                                <input type="hidden" id="item_no" name="item_no" value="1">

                                <table>

                                    <h4>※ 고객님의 신용카드 정보와 구매 확인를 위한 아이디를 입력해주세요.</h4>

                                    <tr>
                                        <td><label for="buy_credit_no">- 카드번호 : </label></td>
                                        <td><input type="text" class="buy_credit_no" id="buy_credit_no1" size="16"
                                                maxlength="16" name="buy_credit_no" placeholder="(-)없이 입력해주세요."
                                                value="${buy.buy_credit_no}" required
                                                oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <td><label for="cvc_no">- C&nbsp;&nbsp;V&nbsp;&nbsp;C : </label></td>
                                        <td><input type="text" class="cvc_no" id="cvc_no_1" name="cvc_no"
                                                placeholder="3자리 숫자를 입력해주세요." required maxlength="3"
                                                oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <td><label for="valid_date">- 유효기간 : </label></td>
                                        <td><input type="month" id="valid_date" name="valid_date"
                                                placeholder="유효기간을 입력해주세요." required min="2021-09" max="2026-09" /></td>
                                    </tr>

                                    <br>

                                    <tr>
                                        <td><label for="user_id">- 아 이 디 : </label></td>
                                        <td><input type="text" id="user_id" name="user_id" placeholder="아이디를 입력해주세요."
                                                required value="${buy.user_id}" /></td>
                                    </tr>

                                </table>


                                <input type="hidden" id="charged" name="charged" value="Activated">
                               	
                               	<br><br>
                                <!-- 모달창 footer -->
                                <div class="modal-footer">
                                    <div class="buttons">
                                        <button type="submit" id="submit_finish" class="btn btn-danger"
                                            data-toggle="modal" data-target="#itemInfoDone">확인</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 상품1 카드 정보 입력 모달 끝 -->


    <!-- 상품2 카드 정보 입력 모달 -->
    <div class="modal" id="item2InfoInsert">

        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <!-- 모달창 Header -->
                <div class="modal-header">
                    <h5 class="modal-title">카드 정보 입력</h5>
                </div>

                <!-- 모달창 body -->
                <div class="modal-body">


                    <div id="item_images">
                        <img id="cloud_2" src="../../resources/images/cloud.JPG" width="200" height="132">

                        <div id="wrapper">

                            <form action="/buy/register" method="POST">

                                <input type="hidden" id="item_no" name="item_no" value="2">

                                <table>

                                    <h4>※ 고객님의 신용카드 정보와 구매 확인를 위한 아이디를 입력해주세요.</h4>

                                    <tr>
                                        <td><label for="buy_credit_no">- 카드번호 : </label></td>
                                        <td><input type="text" class="buy_credit_no" id="buy_credit_no2" size="16"
                                                maxlength="16" name="buy_credit_no" placeholder="(-)없이 입력해주세요."
                                                value="${buy.buy_credit_no}" required
                                                oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <td><label for="cvc_no">- C&nbsp;&nbsp;V&nbsp;&nbsp;C : </label></td>
                                        <td><input type="text" class="cvc_no" id="cvc_no_1" name="cvc_no"
                                                placeholder="3자리 숫자를 입력해주세요." required maxlength="3"
                                                oninput="this.value = this.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');" />
                                        </td>
                                    </tr>

                                    <tr>
                                        <td><label for="valid_date">- 유효기간 : </label></td>
                                        <td><input type="month" id="valid_date" name="valid_date"
                                                placeholder="유효기간을 입력해주세요." required min="2021-09" max="2026-09" /></td>
                                    </tr>

                                    <br>

                                    <tr>
                                        <td><label for="user_id">- 아 이 디 : </label></td>
                                        <td><input type="text" id="user_id" name="user_id" placeholder="아이디를 입력해주세요."
                                                required value="${buy.user_id}" /></td>
                                    </tr>

                                </table>
                                <input type="hidden" id="charged" name="charged" value="Activated">
								
								<br><br>
                                <!-- 모달창 footer -->
                                <div class="modal-footer">
                                    <div class="buttons">
                                        <button type="submit" id="submit_finish2" class="btn btn-danger"
                                            data-toggle="modal" data-target="#itemInfoDone">확인</button>
                                        <button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
                                    </div>
                                </div>

                            </form>
                            
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- 상품2 카드 정보 입력 모달 끝 -->

    <!-- ===================================================================================================================== -->







</body>

</html>