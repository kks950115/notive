
// =================== 기본 버튼 조작 =================== //

$(function () {
    

    // 채팅방 close 버튼
    $(document).on('click', ".chat-close", function () {
        // 이전 연결이 있을 때는 삭제
        if(websocket != null){                        // else ) 채팅방이 열려있다면,
            $('div.chatMiddle:not(.format) ul').html("");
            websocket.close();
            
        } 
        $('.chat').toggleClass('display-none');           // 채팅방을 닫는다.
        clearInterval(roomReload);  
        
    }); // chat-room close event

    // 채팅방 아이콘 클릭
    $(document).on('click', "#chat", function () {
        // 채팅방 open
        openChat();

        // 첫번째 채팅방 선택
        var firstRoom = $(".chat-group-list li:first-child");

        // 기본으로 열기
        firstRoom.trigger("onclick");

    }); // chat-icon click event
    
    // 전송 버튼 활성/비활성
    $(".message-area input").on("propertychange change keyup paste input", function() {
        if($(this).val() != "" ){
            $(".chatBottom").prop("disabled", false);
        } else {
            $(".chatBottom").prop("disabled", true);
        }
    }); // message-area input acive/inactive event

    // enter 키 이벤트
    $(document).on('keydown', '.message-area input', function(e){
        console.log("keyDowned");
        if(e.keyCode == 13 && !e.shiftKey) {

            // 엔터키가 입력되는 것을 막아준다.
            e.preventDefault(); 

            // 현재 입력된 메세지를 담는다.
            const message = $(".message-area input").val();  
            
            // 전송
            sendMessage(message);
            
             // textarea 비우기
            clearTextarea();

            // 전송버튼 비활성화
            $(".chatBottom").prop("disabled", true);
        } // if
    }); // enterKey Keydown event

    $(document).on('click', ".chatBottom", function () {

        // 현재 입력된 메세지를 담는다.
        const message = $(".message-area input").val();  

        // 전송
        sendMessage(message);

         // textarea 비우기
        clearTextarea();

        // 전송버튼 비활성화
        $(".chatBottom").prop("disabled", true);
    }); // .chatBottom click event

}); // jq

// 채팅방 리로드 인터벌 변수
var roomReload;

// 채팅방 unread 리로드
var unreadReload = function () {

    var data = { "user_id" : user_id };

    // 방마다의 unreadCount를 받아 업데이트한다
    // 읽지않음 값이 있을 때 1초마다 업데이트
    $.ajax({
        url: "/chatUnreadReload",
        data: data,
        async:false,
        contentType : "application/json",
        dataType:"json",
        success:function(data){

            // unread-count 값을 모든 채팅방을 돌며 업데이트한다.
            $(".chat-group-item").each(function (index, element) {

                    $(element).find(".unread-count").text(data[index]);

                    if(data[index] > 0){
                        $(element).find(".unread-count").removeClass("display-none");
                    } else {
                        $(element).find(".unread-count").addClass("display-none");
                    }

            });

        } // success function
    
        }); // ajax
} // unreadReload

// 채팅창 열기
var openChat = function () {

    // 채팅방 열릴 상대좌표 지정
    var chatPosX = $("#chat").offset().left - 457;
    $(".chat").css("left", chatPosX);

    if($('.chat').hasClass("display-none")){

        $('.chat').toggleClass('display-none'); 
        $(".chat").draggable();

        // 2초마다 채팅방 리로드 : 채팅방 켤 때만 동작
        roomReload = setInterval(unreadReload, 1000); // setInterval 2000
        
    }else{
        // 이전 연결이 있을 때는 삭제   
        if(websocket != null){                   
            $('div.chatMiddle:not(.format) ul').html("");
            websocket.close();
        }

        $('.chat').toggleClass('display-none'); 

    }

    getRoomList();

}; // chatOpen

// 채팅방 목록받기
var getRoomList = function () {

    // 리스트 초기화
    $(".chat-group-item").remove();
    
    var data ={"user_id": user_id};
                    console.log(data);
    // 채팅방 리스트
    $.ajax({
        method: "get",
        url: "/chatRoomList",
        async: false,
        data : data,
        contentType : "application/json",
        dataType : "json",
        success:function(data){
    
            var ul = $(".chat-group-list");
            ul.html("");
    
            if(data.length > 0){
                
                for(var i in data) {
                //     $div = $("<div class='chatList_box enterRoomList' onclick='enterRoom(this);'>").attr("id",data[i].group_no);
                    $li = $('<li class="chat-group-item list-group-item" onclick="enterRoom(this);" >').attr("id",data[i].room_no);

                    $div1 = $("<div class='user-group-name'>").text(data[i].group_name);
                    $div2 = $("<div class='unread-count'>").text(data[i].unread_count);

                    if(data[i].unread_count > 0){
                        $div2.removeClass("display-none");
                    } else {
                        $div2.addClass("display-none");
                    }
                    $li.append($div1);
                    $li.append($div2);
                    ul.append($li);


                    
                } // for
    
            } // if
    
        } // success function
    
    }); // ajax get /chatRoomList

} // roomList

// 해당 그룹의 채팅방 입장
function enterRoom(obj){
    if(websocket != null){
        websocket.close();
    }

    // 현재 html에 추가되었던 동적 태그 전부 지우기
    $('div.chatMiddle:not(.format) ul').html("");

    // obj(this)로 들어온 태그에서 id에 담긴 방번호 추출
    var data = { "user_id" : user_id };

    // 채팅방의 번호 가져오기
    room_no = obj.getAttribute("id");
    
    // 해당 채팅 방의 메세지 목록 불러오기
    $.ajax({
    url: room_no + "/message",
    data: data,
    async:false,
    dataType:"json",
    success:function(data){
        for(var i = 0; i < data.length; i++){
            // 채팅 목록 동적 추가
            CheckLR(data[i]);
        
        } // for

    } // success function

    }); // ajax

    // 웹소켓 연결
    connect();
    
    // 새로 갱신 채팅방 목록 불러오기
    // 안읽은 채팅 갱신 목적
    getRoomList();

    // 선택된 채팅방 활성화
    $("#" + room_no).addClass("room-active");

    console.log("enterRoom");
}; // enterRoom

// 웹소켓 준비
let websocket;

//입장 버튼을 눌렀을 때 호출되는 함수
function connect() {
    // 웹소켓 주소
    // var wsUri = "ws://${pageContext.request.serverName}:${pageContext.request.serverPort}${pageContext.request.contextPath}/chat";
    // var wsUri = "ws://localhost:8090/chat";
    console.log(wsUri)
    // 소켓 객체 생성
    websocket = new WebSocket(wsUri);

    //웹 소켓에 이벤트가 발생했을 때 호출될 함수 등록
    websocket.onopen = onOpen;  // 방 열기
    websocket.onmessage = onMessage; // 메시지 수신
}; // connect

//웹 소켓에 연결되었을 때 호출될 함수
// ENTER-CHAT 이라는 메세지를 보내어, Java Map에 session 추가
// 이부분은 채팅방에 출력되지 않는다.
function onOpen() {
    
// 현재 시간
var chat_date = new Date();

const data = {

        "room_no" : room_no,
        "user_id" : user_id,
        "chat_content" : "ENTER-CHAT",
        "chat_date" : chat_date

    };

    // 채팅방 접속 내용 전송
    let jsonData = JSON.stringify(data);
    websocket.send(jsonData);

}; // onOpen

// 1. 메시지 전송
// chatDTO = [room_no, user_id, chat_content, chat_date, chat_unread_count];
function sendMessage(chat_content){

    // 현재시간
    var chat_date = new Date();
    
    const data = {
        "room_no" : room_no,
        "user_id" : user_id,
        "chat_content" : chat_content,
        "chat_date" : chat_date,
        "chat_unread_count" : null

    };
    
    // 메시지 만들어서 출력까지 진행 시키는 체이닝 시작
    // 이 부분은 자신이 입력한 채팅을 출력
    
    // 메시지 내용 전송
    // 자신이 입력한 채팅을 채팅방 다른 세션에 모두 전송
    let jsonData = JSON.stringify(data);
    websocket.send(jsonData);
    
    CheckLR(data);
    
}; // sendMessage

    // 2. 메세지 수신
function onMessage(evt) {
        
    // chatHandler에서 받은 값을 분리 시켜서 Array 저장
    console.log(evt.data);
    let receive = evt.data.split("|");
        
    // 각 값들 뽑아서 Map Data화
    const data = {
            "user_id" : receive[0],
            "chat_content" : receive[1],
            "chat_date" : receive[2],
            "chat_unread_count" : receive[3]
    };

    console.log(receive[2]);
    
    // 읽지않은 채팅
    let unreadArray = null;
    // 데이터 정리
    if(receive[4] != "null"){
        var unread = receive[4];
        var regex = / /gi;
        unread = unread.replace(regex, "");
        unread = unread.slice(1, unread.length-1);
        unreadArray = unread.split(",");

        console.log(unreadArray)
    }

    console.log(data);

    // 본인이 보낸 채팅이 아닐 때 채팅방에 출력
    // 이거 없으면 본인 채팅이 중복으로 입력됨
    if(data.user_id != user_id && unreadArray == null){
        CheckLR(data);
    } else if(unreadArray != null) {
        updateUnread(unreadArray);
    } // if-else

    // 보낸 메시지 업데이트
    const data2 = {
        "room_no" : room_no,
        "user_id" : user_id
    }

    $.ajax({
        url: "/modifyUnread",
        data: data2,
        async:false,
        dataType:"json"
    }); // ajax

}; // onMessage
    

// 2-1. 추가 된 것이 내가 보낸 것인지, 상대방이 보낸 것인지 확인하기
function CheckLR(data) {
    console.log("1. CheckLR invoked.", data);

    // 내가 보낸 채팅 오른쪽, 다른 유저의 채팅 왼쪽
    const LR = (data.user_id != user_id) ? "left" : "right";

    // ========== 본인이 보낸 데이터 표시할 때만 아래 수행=========== //
    const data2 = {
        "room_no" : room_no,
        "user_id" : user_id
    }
    
    // 메시지 보냄 -> 읽은 사람수 체크 -> DB에 저장
    // 이러한 로직 때문에 실시간으로 본인이 보낼 때 읽은 사람수는 여기서 재설정해준다.
    if(data.chat_unread_count == null){

        $.ajax({
            url: "/chatUnreadCount",
            data: data2,
            async:false,
            dataType:"json",
            success:function(result){
                data.chat_unread_count = result;
                console.log(data.chat_unread_count)
            } // success function
    
        }); // ajax
    } // if

    // 채팅 태그 만드는 함수 호출
    appendMessageTag(LR, data.user_id, data.chat_content, data.chat_date, data.chat_unread_count);

}; // CheckLR
    
// 3. 메세지 태그 append
function appendMessageTag(LR_className, user_id, chat_content, chat_date, chat_unread_count) {
    console.log("2. appendMessageTag invoked.", LR_className);

    // DB에서 받은 채팅시간, 실시간으로 입력한 채팅시간을 같은 형태로 만든다.
    var d = new Date(chat_date); // JavaScript Date 타입으로 만듦

    // 화면에 표시할 형태 
    var chat_date = d.toLocaleTimeString('ko-KR', { hour: '2-digit', minute: '2-digit' }); // 오전|오후 00: 00

    // 태그화 함수에 보내 태그화 시킨 후 리턴 값 변수 저장
    const chatLi = createMessageTag(LR_className, user_id, chat_content, chat_date, chat_unread_count);
    
    // 현재 채팅창에 출력
    $('div.chatMiddle:not(.format) ul').append(chatLi);
    
    // 스크롤바 아래 고정
    $('div.chatMiddle').scrollTop($('div.chatMiddle').prop('scrollHeight'));

}; // appendMessageTag
    
// 4. 메세지 태그 생성
function createMessageTag(LR_className, user_id, chat_content, chat_date , chat_unread_count) {
        console.log("3. createMessageTag invoked.", LR_className );
        
        // 형식 가져오기
        let chatLi = $('div.chatMiddle.format ul li').clone();

        // left : right 클래스 추가
        chatLi.addClass(LR_className);    
        chatLi.find('.message-container').addClass(LR_className);     
        
        // find() : chatLi의 하위 요소 찾기

        // 내가 쓴 채팅의 이름은 표시하지 않는다.
        if(LR_className == "left"){
            chatLi.find('.sender span').text('[' + user_id + ']');      // 이름 추가
        }

        chatLi.find('.message-container .message span').text(chat_content); // 메세지 추가
        // chatLi.find('.message-container .unread span').text(chat_unread_count); // 메세지 추가
        if(chat_unread_count > 0){
            chatLi.find('.message-container .unread span').text(chat_unread_count); // 메세지 추가
        } else {
            chatLi.find('.message-container .unread span').text("");
        }
        chatLi.find('.message-container .time span').text(chat_date); // 메세지 추가
    
        return chatLi; 

}; // createMessageTag
    
// 4. 채팅방 비우기
function clearTextarea() {
    $(".message-area input").val("");

    return false;
}; // clearTextarea

// 
function updateUnread(unread) {
    console.log("updateUnread invoked");

    $('div.chatMiddle:not(.format) ul li').each(function (i, element) {
        if(Number(unread[i]) > 0) {
            $(element).find('.unread span').text(unread[i]);
        } else {
            $(element).find('.unread span').text("");
        }
    })

}