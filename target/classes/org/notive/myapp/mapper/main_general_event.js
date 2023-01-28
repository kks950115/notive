// 메인페이지 이벤트 관리 스크립트
// Hover, Click, Fold ... 

$(function () { // 이미지 호버링시 색상 바꾼 이미지로 교체
    console.log("main_general_event.js invoked.");
    console.log(sessionStorage.getItem("menu"));

        //---------------------------------------------------//
    // Function List
    // (1)  accept          그룹초대 수락    
    // (2)  refuse          그룹초대 거절
    //---------------------------------------------------//


    // 그룹 초대 수락 함수
    var accept = function () {
        var selected = $(this).parent("div");
        user_group_name = selected.prevAll("input[name=group_name]").val();

        var data = { 
            "group_no" : selected.prevAll("input[name=group_no]").val(),
            "user_id" : selected.prevAll("input[name=user_id]").val(),
            "user_group_name" : selected.prevAll("input[name=group_name]").val()
        };

        $.post('invitation/accept', data, function () {
            selected.parent().parent("li").remove();
            $("#accept").on('click', accept);
        });
    }

    // 그룹 초대 거절 함수
    var refuse = function () {
        var selected = $(this).parent("div");
        var group_name = selected.prevAll("input[name=group_name]").val();
        var data = { group_name, group_name};

        $.post('invitation/refuse', data, function () {
            selected.parent().parent("li").remove();
            $("#refuse").on('click', refuse);
        });
    }

    // 이전 페이지에서 펼친 메뉴 유지
    if(sessionStorage.getItem("menu") != null){
        console.log("menu open");
        $('.' + sessionStorage.getItem("menu")).css("display","block");
        sessionStorage.clear();
    };

    // a태그의 #링크 무력화
    $('a[href="#"]').click(function(e) {
        e.preventDefault();
    }); // a tag clicked

    // 모든 폴더종류 클릭시 
    $(".aside-left ul li").click( function () {
        
        $(".aside-left ul li").removeClass('active');
        $("#bin").removeClass('active');
        $(".aside-left").removeClass('fold');
        
        $(this).toggleClass('active');
    }); // ("aside ul li").on('click', function () {}

    // Drive 클릭
    $("#drive").click( function () {
        $(".folder").toggle();

        // 사이드바 접힌 상태에서 Drive 선택시
        // --1. 접힌 사이드바 열기
        // --2. Group 접기
        // --3. Drive 펼치기
        if($(".aside-left").hasClass("fold")){

            $(".group").hide();     // Group 접기
            $(".folder").show();    // Drive 펼치기

            $(".aside-left").toggleClass('fold'); // 사이드바 펼치기
            $(".aside_fold i").toggleClass("bi-caret-right bi-three-dots-vertical");
        }   // if
        
    }); // ("#drive").on('click', function () {}

    // Group 클릭
    $("#group").click( function () {
        $(".group").toggle();

        // 사이드바 접힌 상태에서 Group 선택시
        // --1. 접힌 사이드바 열기
        // --2. Drive 접기
        // --3. Group 펼치기
        if($(".aside-left").hasClass("fold")){

            $(".folder").hide();    // Drive 접기
            $(".group").show();     // Group 펼치기

            $(".aside-left").toggleClass('fold'); // 사이드바 펼치기
            $(".aside_fold i").toggleClass("bi-caret-right bi-three-dots-vertical");
        } // if

    }); // ("#group").on('click', function () {}

        // 휴지통 클릭
    $("#bin").click( function () {

        
        $("#bin").removeClass('active');
        $(".aside-left ul li").removeClass('active');
        
        if($(".aside-left").hasClass("fold")){
            
            $(".aside-left").toggleClass('fold');
            $(".aside_fold i").toggleClass("bi-caret-right bi-three-dots-vertical");
        } // if
        
        $(this).toggleClass('active');

    });

    // 접기 아이콘 클릭 이벤트
    $(".aside_fold").on('click', function () {

        $("aside").toggleClass('fold');

        // 접기 아이콘 모양 변경
        $(".aside_fold i").toggleClass("bi-three-dots-vertical bi-caret-right");
    });

    // 우측 알림 메뉴 클릭 이벤트
    $("#message").on('click', function () {
        console.log("message cliked.");

        // jquery-confirm의 $.dialog로 진행
        $.dialog({
            title: "알림",
            draggable: true, 
            bgOpacity: 0,               // 배경 흐림 X
            backgroundDismiss: true,    // 배경 클릭시 모달 닫힘
            type: 'blue',               // 모달 색상
            // 모달 내부에서 ul태그 생성
            content: '<ul class="list-group"></ul>' , 
            onContentReady: function () {

                // 로그인한 유저의 아이디 전송
                var data = { "user_id": user_id };
                
                // 초대받은 리스트 가져오기
                $.post('/invitation/show', data, function (list) {
                    console.log(list);

                    // 초대 받은 모든 리스트 출력
                    list.forEach( invitGroup => {
                        var inviteList = 
                            '<div class="invite-group-list">' +
                            '<input type="hidden" name="group_no" value="' + invitGroup.group_no + '">' +
                            '<input type="hidden" name="user_id" value="' + invitGroup.user_id + '">' +
                            '<input type="hidden" name="group_name" value="' + invitGroup.group_name + '">' +
                            '<div>['+ invitGroup.invit_id + ']</div>' +
                            '<div class="invit-content">"'+ invitGroup.group_name + '" 에서 회원님을 초대했습니다.</div>' +
                            '<div class="invit-buttons">' +
                            '<button class="btn btn-primary" id="accept" type="button">수락</button>' +
                            '<button class="btn btn-primary" id="refuse" type="button">거절</button><div></div>';

                        var li = $("<li>");
                        li.html(inviteList);
                        $(".list-group").append(li);

                    });

                    // 초대 목록이 없을 때
                    if(list == 0){
                        var span = $("<span>");
                        span.text("새로운 알림이 없습니다.");
                        $(".list-group").append(span);
                    }

                    // 수락 버튼 클릭이벤트 바인드
                    $("#accept").on('click', accept);
                    
                    // 거절 버튼 클릭이벤트 바인드
                    $("#refuse").on('click', refuse);
                    
                    



                })
            }
        })
    });
}); // jquery end

