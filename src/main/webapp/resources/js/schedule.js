var calendar;
var id =user_id;
document.addEventListener('DOMContentLoaded', function () {

    var calendarEl = document.getElementById('calendar');

    calendar = new FullCalendar.Calendar(calendarEl, {

        headerToolbar: {     //상단에 메뉴
            left: 'prev,next today', // 이전달, 다음달, 오늘날짜로
            center: 'title',  		// yyyy년m월
            right: ''				//안쓸 경우 ''처리
        }, //headerToolbar

        initialView: 'dayGridMonth',  //달력표기형식 기본값 

        locale: 'ko',    			//한국시간	
        navLinks: false,			//날짜 클릭시 달력표기가 바뀌는 기능
        selectable: true,					//날짜를 선택해서 일정추가 가능하게 만드는 기능
        selectMirror: true,			//timegrid에서만 쓰임. default: false,
        unselectAuto: true,			//selectable이 true일때만 적용됨

        //editable: false,
        dayMaxEventRows: false,  //true로 하면 많은 일정을 추가할 때 넘치는 부분을 숨김  


		//일정을 드래그해서 선택하면...
        select: function (arg) {   
            console.log('select invoked ');
            //$(".fc-body").unbind('click');
			

			//날짜드래그 시 뜨는 모달창 초기화
            $('.modal-title').html('일정 등록'); //타이틀 제목 입력
            $('#editId').val(id);		//id값 가져오기
            $('#editNo').val('');		//no값 초기화
            $('#editTitle').val(''); // title명 초기화
            $('#editContent').val(''); //content 초기화
            $('#editTime').val(''); //time 초기화
            $('#editColor').val('#2478FF'); //color 초기화	
            $('#editStart').val( arg.startStr ) ; //start
            $('#editEnd').val(moment(arg.endStr).subtract(1,'days').format('YYYY-MM-DD') ); //end

            $('.modalBtnContainer-addEvent').show(); //추가할 떄 나오는 버튼들 보인다
            $('.modalBtnContainer-modifyEvent').hide(); //수정할 때 나오는 버튼들 숨김
            $('#eventModal').modal('show'); //모달 띄우기
			
            saveEvent();  //saveEvent 함수 
            calendar.refetchEvents(); //수정된 날짜 다시 불러오기
            calendar.unselect(); //일정 선택 해제

         
            calendar.render();   //켈린더 렌더

           
        }, // select


		//만들어진 일정을 클릭 시...
        eventClick: function (arg) {
            console.log('eventclick invoked', arg);

			var clickEndDate= moment(arg.event.endStr).subtract(1,'days').format('YYYY-MM-DD')
           	var clickStartDate = arg.event.startStr;
			if(arg.event.endStr===""){
				clickEndDate=arg.event.startStr
			}
			$('.modal-title').html('일정 수정');  //모달의 title 변경
			console.log('clickEndDate:', clickEndDate);
			console.log('clickStartDate:', clickStartDate);
			//extendedProps는 fullcalendar 라이브러리에 포함되어있지 않은 사용자정의 키값을 가리킬 때 쓴다.
            $('#editNo').val(arg.event.extendedProps.no);  //no값 불러오기 
            $('#editId').val(arg.event.id);    				//id값 불러오기
            $('#editTitle').val(arg.event.title);			//title 불러오기
            $('#editStart').val(clickStartDate);		//startStr은 2018-06-01 문자열로 불러온다. start는 2018-06-01T12:30:00-05:00  형식
            $('#editEnd').val( clickEndDate);               //endStr 불러오기
            $('#editContent').val(arg.event.extendedProps.content);	//content불러오기
            $('#editColor').val(arg.event.backgroundColor);			//color불러오기
            $('#editTime').val(arg.event.extendedProps.schtime);	//schtime 불러오기
            $('#editAllDay').val(arg.event.extendedProps.schallDay);//schallDay불러오기
            $('#editDDay').val(arg.event.extendedProps.dday);		//dday불러오기




            if (arg.event.extendedProps.dday === 1) {  //1=참 , 0=false
                $('#editDDay').prop("checked", true);

            } else {
                $('#editDDay').prop("checked", false);

            }; //if-else

            if (arg.event.extendedProps.schallDay === 1) { //1=참 , 0=false

                $('#editAllDay').prop("checked", true);


            } else {
                $('#editAllDay').prop("checked", false);

            } //if-else	


            $('.modalBtnContainer-addEvent').hide();//추가할 떄 나오는 버튼들 숨긴다.
            $('.modalBtnContainer-modifyEvent').show(); //수정할 때 나오는 버튼들 보인다
            $('#eventModal').modal('show');  //모달 보여주기

            //=========delete=============//
            $('#deleteEvent').off().click(function () { //모달에서 삭제 버튼 클릭 시....
                    var deleteData = {   // ajax에 매개변수로 넣어줄 no를 담는다.
                        "no": $('#editNo').val()      
                    }

                    $('#deleteEvent').unbind();
                    arg.event.remove();		//이벤트 삭제
                    $('#eventModal').modal('hide');  //모달 숨김



                    $.ajax({ // ajax를 이용해 db로 전송  
                        url: "/schedule/deleteSchedule",
                        async: false,
                        method: "POST",
                        //dataType: "json",
                        // contentType: "application/json",	// 데이터 보내는 타입 강제로 정하는 부분
                        // 이거 없이 보내면 타입 보통 알아서 들어감
                        // 그래서 int로 들어감
                        data: deleteData,
                        success: function (result) {
                            console.log(result);

                        }, //success
                        error: function (request, status, error) {
                            alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                        }

                    }) //ajax

                    //==================================//	
                }), //click deleteEvent
                //==================================

                //=========update=============//

                $('#updateEvent').off().on('click', function () { //업데이트 버튼 클릭 시....
	
                    var updateScheduleData = { 	//ajax에 매개변수로 넣어줄 변수들. key값은 mapper.xml에 #{value}와 이름이 같아야한다.(대소문자 구분)
                        "no": $('#editNo').val(),
                        "title": $('#editTitle').val(),
                        "content": $('#editContent').val(),
                        "schtime": $('#editTime').val(),
                        "start": $('#editStart').val(),
                        "end": $('#editEnd').val(),
                        "backgroundColor": $('#editColor').val(),
                        "dday": $('#editDDay').val(),
                        "id": $('#editId').val(),
                        "schallDay": $('#editAllDay').val(),
						"sch_alarm": 0

                    } //updateScheduleDate	
					 if (updateScheduleData.start > updateScheduleData.end) { //날짜 유효성 검증
			            alert('시작일이 종료일보다 더 큽니다.');
			            return false;
			        } //if
			
			
			        if (updateScheduleData.title === '') { //title 공백 체크
			            alert('제목은 공백일 수 없습니다.');
			            return false;
			        } //if
                    if ($('#editAllDay').is(':checked')) { //종일 체크 시 db에 저장될 값
                        updateScheduleData.schallDay = 1;
                    } else {
                        updateScheduleData.schallDay = 0;
                    } //if-else

                    if ($('#editDDay').is(':checked')) {//dday 체크 시 db에 저장될 값
                        updateScheduleData.dday = 1;
						updateScheduleData.sch_alarm = 1;
                    } else {
                        updateScheduleData.dday = 0;
						updateScheduleData.sch_alarm = 0;
                    } //if-else


                    $('#eventModal').modal('hide');   //모달 숨기기

                    $.ajax({
                        async: false,
                        url: "/schedule/updateSchedule",
                        method: "POST",
                        //dataType:"json",
                        contentType: "application/json;", // 써야 할때도 있고 아닐때도 있으니 잘 해보셔유

                        data: JSON.stringify(updateScheduleData), //json으로 변환해서 매개변수로 넣어줌
                        success: function (result) { //성공했을 떄 실행
                            console.log(updateScheduleData);
                            console.log(result);
                        },
                        error: function (request, status, error) {
                            alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                        }

                    }) //ajax

                    calendar.refetchEvents(); //캘린더 일정 새로고침

                }) //update click

            calendar.render();
        }, //eventClick

        eventSources: 
        [
            {
            events: function (info, successCallback, failureCallback) {

                console.log('event 불러오기 호출됨.');

                $.ajax({

                    url: "/schedule/SelectScheduleVO",
                    method: 'POST',
                    dataType: "json",

                    success: function (response) {
                        //console.log("response: ",response);
                         var fixedDate = response.map(function (array) {
				          if (array.schallDay==1 && array.start !== array.end) {
				            array.end = moment(array.end).add(1, 'days').format('YYYY-MM-DD'); // 이틀 이상 AllDay 일정인 경우 달력에 표기시 하루를 더해야 정상출력
				          }
				          return array;
				        });
						successCallback(fixedDate);
                    }, //success
                    error: function (request, status, error) {
                        alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
                    }

                }) //ajax

       

            }//function

            },
			{
				events: function(info,successCallback,failureCallback){
				    $.ajax({
                        url: "/schedule/callAPI",
                        method: "GET",
                        dataType: "json",
    
                        success: function (api) {
                        //console.log("api response: ",api);

                           var modified = api.response.body.items.item.map(
                               obj => {
                                   return {
                                       //"start" : obj.locdate,
                                       "start" :  String(obj.locdate).replace(/(\d{4})(\d{2})(\d{2})/, '$1-$2-$3'),
                                       "title" : obj.dateName,
                                       "schallDay" : 1,
                                      
                                       "id": user_id
                                   }
                               }
                               
                           )
                          // console.log("api modified: ",modified);
                            
                            
                           
                            successCallback(modified);
                        }
                       
                   
                   })//ajax
					
				 },//function

			  	    color: "#ff0000",
					display: 'background'
					
					
					

                 
               
            },
	
            
        ] //eventsource
    }); //calendar

    calendar.render();

}); //addeventlistner



$(function () {
	$('.innerModal').click(function(){ //eventmodal의 취소버튼 클릭 시....
		$('#eventModal').modal('hide');
	})//click
	
    $('#calendarModal').draggable({//calendar모달창을 움직일 수 있게...
        handle: ".modal-header"
    })

    $('#calendarModal').on('shown.bs.modal', function () {//모달이 보여질 때 달력을 렌더링 한다. 이 함수가 없으면 달력이 제대로 불러와지지않음 
        calendar.render();
    })

    //종일 클릭 시 시간창 비활성화
    $('#editAllDay').off().on('click', function () {
        var editallday = $('#editAllDay');
        if (editallday.is(':checked')) {
            $('#editTime').attr('disabled', true);
            $('#editTime').val('');
        } else {
            $('#editTime').removeAttr('disabled');
        } //if-else
    }) //click

	  
    //알람 띄우는 모달
	
    function alarm_invoke() {//알람으로 알려줘야하는 일정을 json으로 반환 
        return new Promise(function (resolve,reject) { //promise패턴
            console.log('alarm invoked')
            if(resolve==null || resolve==undefined){
                return false;
            } //if
            $.ajax({
            
                url: '/schedule/getScheduleAlarm',
                method: 'get',
                success:function (schAlarmData) { 
                    item= JSON.parse (schAlarmData); //json을 자바 객체로 변환하여 item에 넣음
					resolve(item);
                    console.log("item : ",item);
                }
               
            })//ajax
        })//promise
    }//promise
   
  	
          
      
    function sch_modal(item) {
        return new Promise(function (resolve,reject) {//promise패턴
            console.log("pro item:",item);
        
			
          for(var i=0; i<item.length;i++){//호출되는 알람의 개수만큼 for문을 돌림

            var now = moment().format('YYYY-MM-DD');   //현재 날짜 YYYY-MM-DD
            var startDay=moment(item[i].SCH_START_DATE).format('YYYY-MM-DD'); //start
            var endDay = moment(item[i].SCH_END_DATE).format('YYYY-MM-DD'); //end
			var diffDay= moment(item[i].SCH_START_DATE).diff(moment(), 'days')+1;//d-day까지 남은 일수
			var printDiffDay =(diffDay<1) ?'현재 D-day 입니' :'D-day까지 '+diffDay+'일 남았습니' //d-day 남은 일수 표기
				
           	var sTitle =  item[i].SCH_TITLE //title 
            var sColor= item[i].SCH_COLOR  //color 


         
           
                   
          		  	


        $.confirm({
         type: 'schcustom', 
		 
		 title: 'D-day 알람!!',

         content:'<div id="alarmContent"><h3 >'+ sTitle +'</h3><br>'+
               startDay+' 부터 '+ endDay+'까지 일정이 있습니다.<br><br>'+
                '<h4>'+printDiffDay+"다.</h4></div>" 
         ,
     
		 buttons: {
             ok:{
				text: '확인',
                btnClass: "btn-schcustom",
			    }
        }//button
    }) //jqueryconfirm



    
 
    }//for


     })//promise
    }//schmodal    
  
    

    alarm_invoke().then(sch_modal); ////promise패턴 실행
	
	

}) //jq

function saveEvent() {


    $('#save-event').off().on('click', function () { //save-event 클릭 시...
        console.debug(">>> onclick on #save-event invoked.");

        var scheduleData = {
            "no": $('#editNo').val(),
            "title": $('#editTitle').val(),
            "content": $('#editContent').val(),
            "schtime": $('#editTime').val(),
            "start": $('#editStart').val(),
            "end": $('#editEnd').val(),
            "backgroundColor": $('#editColor').val(),
            "dday": $('#editDDay').val(),
            "id": id,
            "schallDay": $('#editAllDay').val(),
			"sch_alarm": 0



        }; //스케줄데이터


        if (scheduleData.start > scheduleData.end) {
            alert('시작일이 종료일보다 더 큽니다.');
            return false;
        } //if


        if (scheduleData.title === '') {
            alert('제목은 공백일 수 없습니다.');
            return false;
        } //if

    


        if ($('#editAllDay').is(':checked')) {
            scheduleData.schallDay = 1;
			
        } else {
            scheduleData.schallDay = 0;
        } //if-else

   

        if ($('#editDDay').is(':checked')) {
            scheduleData.dday = 1;
			scheduleData.sch_alarm = 1;
        } else {
            scheduleData.dday = 0;
        } //if-else

    



        $.ajax({
            async: false,
            method: "POST", //전송방식
            url: "/schedule/addSchedule", //호출url
            data: JSON.stringify(scheduleData), //파라미터
            //dataType: "json", //전송받을 타입 예)xml,html,text,json
            contentType: "application/json",
            success: function (result) {

                console.log(result);
            },
            error: function (request, status, error) {
                alert("code:" + request.status + "\n" + "message:" + request.responseText + "\n" + "error:" + error);
            }


        }); //saveEvent ajax  	
        calendar.refetchEvents();

    


        $('#eventModal').modal('hide');


    }); // save-event onlick         
} //saveEvent

function scheduleAlarm() {
    console.log('');
} //scheduleAlarm