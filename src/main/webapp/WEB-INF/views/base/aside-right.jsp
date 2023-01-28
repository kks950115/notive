<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<!--schedule library -->  
<link rel="stylesheet" href="/resources/fullcalendar/lib/main.css">
<script src="/resources/fullcalendar/lib/main.js"></script>
<script  src="https://cdnjs.cloudflare.com/ajax/libs/moment.js/2.24.0/moment.min.js"></script>
<script src="/resources/js/schedule.js"></script> 

<!-- ref css : main_page.css -->

<!-- 알림 아이콘 클릭시 태그로 삽입되는 내용 -->
<!-- <ul class="list-group">
    <div class="invite-group-list">
        <input type="hidden" name="group_no" value="invitGroup.group_no" >
        <input type="hidden" name="user_id" value="invitGroup.user_id" >
        <input type="hidden" name="group_name" value="invitGroup.group_name" >
        <div>[invitGroup.invit_id]</div>
        <div class="invit-content">"invitGroup.group_name" 에서 회원님을 초대했습니다.</div>
        <div class="invit-buttons">
            <button class="btn btn-primary" id="refuse" type="button">거절</button>
            <button class="btn btn-primary" id="accept" type="button">수락</button>
        </div>
    </div>
</ul> -->

<!-- add-on_list_icon -->
<div class="icon-list">
    <i class="bi bi-envelope" id="message"></i>
    <i class="bi bi-chat" id="chat"></i>
  
 	<i class="bi bi-calendar-week" id="calendar-icon" data-toggle="modal" 
 	   href="#calendarModal"
 	   role="button"></i>	   
</div>

<!-- calendar -->
<div class="modal fade" tabindex="-1" role="dialog" id="calendarModal" data-backdrop="false">
    <div class="modal-dialog schout" >
        <div class="modal-content" id="calendarModalContent">
           

<!-- 삽입될 모달 -->
 <div class="modal-header"></div>
 <div class="modal-body"  >
	
		<div id="calendar"></div>
	
<!-- modal -->
		<div class="modal fade" tabindex="-1" role="dialog" id="eventModal" data-backdrop="false">
			<div class="modal-dialog schin" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title"></h4>
						
					</div>

					<div class="modal-body">

						<div class="row innerModalrow">
							<div>
								<input class="inputModal" name="editId" id="editId" type="text" readonly hidden>
								<input class="inputModal" name="editNo" id="editNo" type="text" readonly hidden>
							</div>
						</div>

						<div class="row innerModalrow">
								
								<label class="modalLabel col-xs-3 labeAlign " for="editTitle">일정명&nbsp;&nbsp;&nbsp;</label>
								<input class="inputModal col-xs-6 labeAlign inputRadius" type="text" name="editTitle" id="editTitle"
									required="required" placeholder="제목을 입력해주세요." />
								<div class="col-xs-3">
								<label for="editColor" class="labeAlign" >색상</label>
								<input class="inputModal labeAlign inputRadius" name="editColor" id="editColor" type="color">
								</div>
						</div>
						<div class="row innerModalrow">
							
								<label for="editStart" class="col-xs-3 labeAlign">시작날짜</label>
								<input class="inputModal col-xs-9 inputRadius" type="date" name="editStart" id="editStart" />
							
						</div>
						<div class="row innerModalrow">
							<div>
								<label for="editEnd" class="col-xs-3 labeAlign">종료날짜</label>
								<input class="inputModal col-xs-9 inputRadius" type="date" name="editEnd" id="editEnd" />
							</div>
						</div>
						<div class="row innerModalrow">
							<div>
								<label for="editTime" class="col-xs-3 labeAlign">시간&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
								<input class="inputModal col-xs-4 inputRadius" name="editTime" id="editTime" type="time">
								<div class="col-xs-2 ">
								<label for="editAllDay" class="labeAlign" >종일</label>
								<input class='allDayNewEvent' name="editAllDay" id="editAllDay" type="checkbox">
								</div>
								<div class="col-xs-3 ">
								<label for="editDDay" class="labeAlign">D-day</label>
								<input class='DDayNewEvent' name="editDDay" id="editDDay" type="checkbox">
								</div>
							</div>
						</div>


						<div class="row innerModalrow">
							<div class="description">
								<label for="editContent" class="col-xs-3 labeAlign">설명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
								<textarea rows="8" cols="35" class="inputModal col-xs-9 inputRadius" name="editContent" id="editContent"
									placeholder="설명을 입력해주세요."></textarea>
							</div>
						</div>
					</div> <!-- /.modal-body -->
					<div class="modal-footer modalBtnContainer-addEvent">
						<button type="button" class="btn btn-default innerModal" >취소</button>
						<button type="button" class="btn btn-primary" id="save-event">저장</button>
					</div>
					<div class="modal-footer modalBtnContainer-modifyEvent">
						<button type="button" class="btn btn-default innerModal" >닫기</button>
						<button type="button" class="btn btn-danger" id="deleteEvent">삭제</button>
						<button type="button" class="btn btn-primary" id="updateEvent">저장</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->

	
	 </div>  <!-- 모달바디 -->  
<div class="modal-footer"> <button type="button" class="btn btn-secondary" data-dismiss="modal">close</button></div>

        </div><!-- model content -->
    </div><!-- modal-dialog -->
    
</div><!-- modal -->



<!-- chat format -->
<div class="chat display-none">
    <div class="chat-header">
        <div class="chat-title">채팅방</div>
        <div class="chat-close">
            <i class="bi bi-x"></i>
        </div>
    </div>
    <div class="chat-section">
        <div class="chat-room-list ">
            <ul class="chat-group-list list-group">
                <!-- 동적 추가 -->
            </ul>
        </div>

        <div class="chat-area">
            <div class="chatMiddle">
                <ul>
                    <!-- 동적 추가 -->
                </ul>
            </div>
            <div class="message-area">
                <input type="textarea">
                <button class="chatBottom btn btn-primary" disabled>전송</button>
            </div>
        </div>
    </div>
</div>
<!-- format -->
<div class="chatMiddle format">
    <ul>
        <li>
            <div class="sender">
                <span></span>
            </div>
            <div class="message-container">
                <div class="message">
                    <span></span>
                </div>
                <div class="unread-time">
                    <div class="unread">
                        <span></span>
                    </div>
                    <div class="time">
                        <span></span>
                    </div>
                </div>
            </div>
        </li>
    </ul>
</div>

