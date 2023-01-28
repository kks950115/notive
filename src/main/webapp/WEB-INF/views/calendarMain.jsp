

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>


<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>


<!-- 삽입될 모달 -->
 <div class="modal-header">

        </div>
 <div class="modal-body" >
	

		
		
			<div id="calendar"></div>
	

		<!-- modal -->
		<div class="modal fade" tabindex="-1" role="dialog" id="eventModal">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<h4 class="modal-title"></h4>
						<button type="button" class="close" data-dismiss="modal" aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>


					</div>

					<div class="modal-body">

						<div class="row">
							<div>

							</div>
						</div>

						<div class="row">
							<div>
								
								<input class="inputModal" name="editId" id="editId" type="text" readonly hidden>
								<input class="inputModal" name="editNo" id="editNo" type="text" readonly hidden>
								<label class="modalLabel" for="editTitle">일정명&nbsp;&nbsp;&nbsp;</label>
								<input class="inputModal" type="text" name="editTitle" id="editTitle"
									required="required" placeholder="제목을 입력해주세요." />
								<label for="editColor">색상</label>
								<input class="inputModal" name="editColor" id="editColor" type="color">
							</div>
						</div>
						<div class="row">
							<div>
								<label for="editStart">시작날짜</label>
								<input class="inputModal" type="date" name="editStart" id="editStart" />
							</div>
						</div>
						<div class="row">
							<div>
								<label for="editEnd">종료날짜</label>
								<input class="inputModal" type="date" name="editEnd" id="editEnd" />
							</div>
						</div>
						<div class="row">
							<div>
								<label for="editTime">시간&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
								<input class="inputModal" name="editTime" id="editTime" type="time">
								<label for="editAllDay">종일</label>
								<input class='allDayNewEvent' name="editAllDay" id="editAllDay" type="checkbox">
								<label for="editDDay">D-day</label>
								<input class='DDayNewEvent' name="editDDay" id="editDDay" type="checkbox">
							</div>
						</div>


						<div class="row">
							<div class="description">
								<label for="editContent">설명&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</label>
								<textarea rows="16" cols="55" class="inputModal" name="editContent" id="editContent"
									placeholder="설명을 입력해주세요."></textarea>
							</div>
						</div>
					</div> <!-- /.modal-body -->
					<div class="modal-footer modalBtnContainer-addEvent">
						<button type="button" class="btn btn-default" data-dismiss="modal">취소</button>
						<button type="button" class="btn btn-primary" id="save-event">저장</button>
					</div>
					<div class="modal-footer modalBtnContainer-modifyEvent">
						<button type="button" class="btn btn-default" data-dismiss="modal">닫기</button>
						<button type="button" class="btn btn-danger" id="deleteEvent">삭제</button>
						<button type="button" class="btn btn-primary" id="updateEvent">저장</button>
					</div>
				</div><!-- /.modal-content -->
			</div><!-- /.modal-dialog -->
		</div><!-- /.modal -->

	
	 </div>  <!-- 모달바디 -->  
<div class="modal-footer"> <button type="button" class="btn btn-secondary" data-dismiss="modal">close</button></div>

