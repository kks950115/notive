<%@ page 
   language="java" 
   contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.Date" %>
<%@ page import="org.notive.myapp.domain.UserVO" %>
<%@ page import="java.text.SimpleDateFormat" %>

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

	<%
	UserVO user = (UserVO)session.getAttribute("__LOGIN__");
	Date d = user.getUserBday();
	
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	String strd = sdf.format(d);
	
	String[] splitStrd = strd.split("/");

	String yyyy= splitStrd[0];
	String mm = splitStrd[1];
	String dd = splitStrd[2];

	String userID = user.getUserID();
	
	%>

	<style>
		.modal-check {
			text-align: center;
		}

		#id_insert {
			display: flex;
			justify-content: center;
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
		//민지짱
		var loginUserBday_yyyy = "<%=yyyy%>";
		var loginUserBday_mm = "<%=mm%>";
		var loginUserBday_dd = "<%=dd%>";

		var userID = "<%=userID%>";


		//영진
		var temp = "${cancelResult}";

		console.log("temp: ", temp);

		if (temp == "S") {
			alert("기존 결제 취소 신청에 성공했습니다.")
		} else if (temp == "F") {
			console.debug("결제내역 없음 temp: ", temp);
			alert("결제 신청 내역이 없거나, 결제 취소 기간이 경과되었습니다.")
		} //if-else if
	</script>

	<link rel="stylesheet" href="../../resources/css/userSection.css" type="text/css">

	<script src="../../resources/js/userCommon.js"></script>
	<script src="../../resources/js/myPage.js"></script>

</head>

<body>
	<div class="myPage-wrapper">
		<div>

			<h1>마이페이지</h1>

			<fieldSet class="userSection-fieldSet">

				<form>
					<div class="uploadResult-userImage">
						<!-- 기본이미지 -->
						<img src="../../resources/images/defaultImage.png" alt="Profile" id="userImg" />
						<ul id="userImage-userDefine">
							<!-- 프사 썸네일 표시영역 -->
						</ul>
					</div>


					<!-- 파일 첨부영역 -->
					<div class="uploadDiv-userImage-myPage">
						<!-- 파일 선택버튼 css적용 위해, 기존 input태그 숨기고 label태그 이용하기 -->
						<label for="input-userImage" class="defaultBtn">업로드</label>
						<br>
						<button type="button" id="delete-userImage" class="crossBtn">이미지 삭제</button>
						<input type="file" name="uploadUserImg" id="input-userImage">
					</div>
					<p></p>
				</form>

				<div class="myPage">이메일<br>
					<div class="myPage-userInfo" id="myPage-userInfo-userID">${__LOGIN__.userID}</div>
				</div>
				<p></p>

				<div class="myPage">비밀번호<br>
					<button class="defaultBtn" id="modifyBtn-userPass" type="button">비밀번호 변경</button>
				</div>
				<p></p>


				<form>
					<div class="myPage">이름 <br>
						<input class="input-box-myPage" value="${__LOGIN__.userName }" name="userName"
							id="input-userName" type="text" readonly />
						<div class="checkMsg" id="checkName"></div>

						<button type="button" class="defaultBtn" id="submitBtn-modifyUserName">확인</button>

						<button type="button" class="defaultBtn" id="modifyBtn-userName">변경</button>
					</div>
				</form>

				<form action="/user/modifyUserBday" method="POST">
					<div class="myPage" id="form-input-userBday">생년월일<br>
						<div class="selectbox-bday">
							<select name="year" id="myPage-year" class="input-selectBox" disabled></select>
							<select name="month" id="myPage-month" class="input-selectBox" disabled></select>
							<select name="day" id="myPage-day" class="input-selectBox" disabled></select>
						</div>
					</div>


					<button type="submit" class="defaultBtn" id="submitBtn-modifyUserBday">확인</button>

					<button type="button" class="defaultBtn" id="modifyBtn-userBday">변경</button>
				</form>
				<p></p>


				<div class="myPage">결제<br>
					<button type="button" class="defaultBtn" data-toggle="modal" data-target="#cancelPurchase">결제
						취소</button>
				</div>
				<!-- 모달 시작! -->

				<!-- 결제취소 확인 모달 -->
				<div class="modal" id="cancelPurchase">

					<div class="modal-dialog">
						<div class="modal-content">

							<!-- 모달창 Header -->
							<div class="modal-header">
								<h5 class="modal-title">결제 신청 취소</h5>
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
								<div class="cb"><input type="checkbox" name="basic_Capa"> 기본 용량</div>
								<div class="cb"><input type="checkbox" name="price"> 유사 서비스 이용</div>
								<div class="cb"><input type="checkbox" name="reliability"> 기업 신뢰도</div>
								<div class="cb"><input type="checkbox" name="quality"> 서비스 만족도</div>
								<div class="cb"><input type="checkbox" name="similarService"> 가격</div>
								<div class="cb"><input type="checkbox" name="other"> 기타</div>
							</div>

							<!-- 모달창 footer -->
							<div class="modal-footer">
								<button type="button" class="btn btn-danger" data-toggle="modal"
									data-target="#IDInfo">확인</button>
								<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
							</div>
						</div>
					</div>
				</div><!-- 취소사유 확인 -->

				<!--===================================================================================================================-->

				<!-- 이메일 확인 모달 -->

				<div class="modal" id="IDInfo">

					<div class="modal-dialog">
						<div class="modal-content">

							<!-- 모달창 Header -->
							<div class="modal-header">
								<h5 class="modal-title">결제 취소 확인</h5>
							</div>

							<!-- 모달창 body -->	
								<div class="modal-body" id="id_insert">
									<form action="/buy/updateCancel" method="POST">
										<table>
											<p>- <b>'결제 신청 취소'</b>를 희망하신다면, 고객님의 <b>아이디</b>를 입력해주세요. -
												<p>
													<tr>
														<td><input type="text" name="user_id"
																placeholder="(ex) itCamp@gmail.com" size="50" required
																value="${buy.user_id}">
														</td>
													</tr>
										</table>
							<!-- 마지막 버튼 -->
										<div class="modal-footer">
											<div class="buttons">
												<button type="submit" id="cancel_finish" class="btn btn-danger">확인</button>
												<button type="button" class="btn btn-secondary" data-dismiss="modal">닫기</button>
											</div>
										</div>
									</form>
								</div>
						</div>
					</div>
				</div>


				<br>

				<form action="/user/withdraw" method="POST">
					<div id="mypage-dangerZone">
						<b>DANGER ZONE</b>
						<p></p>
						회원 탈퇴시 Notive의 서비스를 이용할 수 없으며, 업로드된 게시물들은 모두 삭제됩니다.<br>
						한 번 삭제된 계정은 복구할 수 없습니다.
						<p></p>
						<button type="submit" class="dangerBtn" id="myPage-withdrawBtn">회원탈퇴</button>
					</div>
				</form>
			</fieldSet>
			<br>
			<button type="button" class="defaultBtn" id="myPage-cancleBtn">확인</button>

		</div>
	</div>
</body>

</html>