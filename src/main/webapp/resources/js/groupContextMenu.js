// 그룹 우클릭 js

$(function(){
    console.log("gfolderContext start...");

    var group_no;

    var targeted;   // 클릭한 요소
    var tParent;    // 클릭한 요소의 부모 a태그

    var origin_name
    var group_name;
    var isDuplicated;
 
    //---------------------------------------------------//
    // Function List
    // (1)  funcContext         메뉴 열기       
    // (2)  modifyGroupName     그룹 이름 수정
    // (3)  withdrawGroup       그룹 나가기
    // (4)  inputChk            유효성검사 출력 alert창 띄우기
    //---------------------------------------------------//

    // ContextMenu 함수
    var funcContext = function(e){
        console.log("funcContext invoked.");

        targeted = $(this);
        tParent = $(this).closest("a");

        group_no = $(this).find("input[name=group_no]").val();
        user_id = $(this).find("input[name=user_id]").val();

 
		
        //user_id = $(this).find("input[name=user_id]").val();


        // 전체 윈도우 사이즈
        var winWidth = $(document).width();
        var winHeight = $(document).height();
        // 클릭한 위치 좌표
        var posX = e.pageX;
        var posY = e.pageY;
        // 클릭한 요소 사이즈
        var menuWidth = $(e.target).width();
        var menuHeight = $(e.target).height();
        // SecurityMargin
        var secMargin = 1;

        // Contextmenu의 위치가 창을 벗어났을 때 위치를 잡아줌

        // Right Top 방향으로 ContextMenu가 위치하게 된면
        if(posX + menuWidth + secMargin >= winWidth
        && posY + menuHeight + secMargin >= winHeight){
            // ContextMenu의 위치 값은 클릭위치 기준으로
            // Left Bottom 방향으로 형성
            posLeft = posX - menuWidth - secMargin + "px";
            posTop = posY - menuHeight - secMargin + "px";
        }
        // 오른쪽 으로만 벗어났을 때 
        else if(posX + menuWidth + secMargin >= winWidth){
            // Left Bottom 방향
            posLeft = posX - menuWidth - secMargin + "px";
            posTop = posY + secMargin + "px";
        }
        // 아래쪽 으로만 벗어났을 떄
        else if(posY + menuHeight + secMargin >= winHeight){
            // Right Top 방향
            posLeft = posX + secMargin + "px";
            posTop = posY - menuHeight - secMargin + "px";
        }
        else {
            // 화면을 넘어가지 않을 때 Right Bottom
            posLeft = posX + secMargin + "px";
            posTop = posY + secMargin + "px";
        };

        // ContextMenu 출력
        $(".gfolderContext").css({
            "left": posLeft,
            "top": posTop
        }).show();
        
        // 브라우저 기본 클릭 무력화 
        return false;
    };













    // 그룹이름 변경 함수
    var modifyGroupName = function (e) {
        e.stopPropagation();
        console.log("func modifyGroupName invoked.");
        console.log(origin_name);
    
        console.log("\t + group_name : ", group_name);  // 입력받은 그룹이름
        console.log("\t + isDuplicated : ", isDuplicated);  // DB 중복체크 결과

        // 입력한 그룹 이름이 있고, 중복되지 않을 때 그룹이름 변경
        if(isDuplicated == 0 && group_name != ""){
            console.log("request post...");

            // 화면전환을 위해 동적 폼 생성 후 보냄
            let formObj = targeted.find("form").serialize();

            console.log(formObj);

            $.ajax({
                type: "post",
                url: "main/group/changeGroupName",
                data: formObj,
                dataType: "json",
                success: function () {

                    console.log("ok");
                    
                } // success
                
            }) // ajax

            $(".temp-a-change i").after(group_name);   // 그룹명 지정
            
        } else {
            // 그룹이름 변경 실패
            console.log("changeGroupName Faild!");
            $(".temp-a-change i").after(origin_name);   // 그룹명 지정
            
        } // if-else
        
        
        $(".temp-a-change").removeAttr('style');   // 테두리 삭제
        targeted.removeClass(); // 클래스 삭제
        targeted.attr('class','groupli'); // 클래스명 원래대로

        $(".duplicated").remove();  // 중복 안내창 태그 삭제

        // input창 활성화
        targeted.find("input:last").attr({
            type: "hidden",
            class: ""
        })

        // $(".groupli").on('click', clickGroup);


    }; // var createGroup define

    // 그룹나가기 함수
    var withdrawGroup = function () {
        console.log("func withdrawGroup invoked.");

        console.log(targeted);
        // 화면전환을 위해 동적 폼 생성 후 보냄
        let formObj = targeted.find("form").serialize();

        console.log(formObj);

        $.ajax({
            type: "post",
            url: "main/group/withdraw",
            data: formObj,
            dataType: "json",
            success: function () {

                console.log("ok");

            } // success
        }) // ajax
        targeted.remove();
    }

    // alert창 내용 사용자 정의 값 넣고 띄우기
    var inputChk =  function (message) {
        console.log("func inputChk invoked.");

        $.alert({
            title: false,
            type: 'red',
            content: message,
            buttons: {
                확인: {
                    btnClass: 'btn-red',
                    action: function(){
                    }
                }
            }
        });
    }

    //---------------------------------------------------//
    // ContextMenu Show and Hide
    //---------------------------------------------------//

    // contextmenu 표시
    $(document).on("contextmenu", ".groupli", funcContext);

    // contextmenu 숨김
    $(document).click(function(){
        $(".gfolderContext").hide();

    });

    //---------------------------------------------------//
    // ContextMenu Click Event
    //---------------------------------------------------//

 
	// // 폴더추가
    // $(document).on('click', ".dirRegisterContext", function (e) {
    //     console.log("gdirRegisterContext clicked.");

	// 	dirRegisterBtn(dirLevel, dirNo, dirTopNo, 2);
 
    // });// dirModifyContext clicked..



    
    // // 폴더이름 변경
    // $(document).on('click', ".dirModifyContext", function (e) {
    //     console.log("gdirModifyContext clicked.");

	// 	dirModifyBtn(dirLevel, dirNo, dirTopNo, 2);
 
    // });// dirModifyContext clicked..

	

    // // 폴더삭제
    // $(document).on('click', ".dirRemoveContext", function (e) {
    //     console.log("gdirRemoveContext clicked.");

	// 	dirRemoveBtn(dirLevel, dirNo, dirTopNo, 2);
 
    // });// dirModifyContext clicked..


    // 그룹원 보기
    $(document).on('click',".groupList", function () {
        console.log(targeted.children("input"));

        $.alert({
            title: "그룹원",
            content:  ''+
            '<div class="userList">' +
            '<div class="col-12"><ul class="user-group list-group"></ul></div>' ,
            bgOpacity: 0.5,
            animateFromElement: false,
            animation: "opacity",
            closeAnimation: "scale",
            animationSpeed: 400,
            onOpenBefore: function () {

                var data = { "group_no" : group_no};

                $.get(
                    "/main/group/getUserList",
                    data,
                    function (result) {
                        // 결과 리스트 반복으로 저장
                        console.log(result);
                        result.forEach( element => {
                            
                            var li = $("<li>");
                            li.attr("class","user-group-item list-group-item");
                            li.text(element);
                            $(".user-group").append(li);
							
                        });

                    },
                    "json"
                );
            },
            buttons:{
                확인:{
                    btnClass: 'btn-notive-blue'
                }
            }
        });
    
    });

    // 채팅방 열기
    $(document).on('click', ".openChatThisRoom", function () {

        if($(".chat").hasClass("display-none")){

            var group_no = targeted.find("input[name=group_no]").val()
            // 클릭한 방에 입장하기 위한 요소 추가
            $(this).attr("id", group_no);
    
            // 채팅방 열고
            openChat();
    
            // 클릭한 방에 입장
            enterRoom(this);

        } // if
    });

    // 그룹원 초대하기
    $(document).on('click', ".invite", function () {
        console.log(targeted.children("input"));

        targeted.children("input");

        // 그룹원 초대 모달
        $.confirm({
            title: '그룹원 초대',

            // 초대 목록을 추가하는 태그를 삽입한다.
            content: ''+
            '<div class="invite-user-list">' +
            '<div class="col-12"><ul class="list-group invit"></ul></div>' +
            '<div class="input-group mb-3">' +
            '<input id="userinput" type="text" class="form-control" placeholder="회원 아이디" aria-label="Add an item" aria-describedby="basic-addon2">' +
            '<div class="input-group-append">' +
            '<button class="btn btn-primary" id="addUserId" type="button">추가</button>' +
            '</div></div></div></div>',

            // 컨텐츠가 준비되면 수행한다.
            bgOpacity: 0.5,
            animateFromElement: false,
            animation: "opacity",
            closeAnimation: "scale",
            animationSpeed: 300,
            onOpenBefore: function () {
                // 초대 회원 추가
                $("#addUserId").on("click", function() {

                    // ajax로 server로 보내서 검증을 하기 위한 data

                    var data = {
                        "group_no" : group_no,
                        "user_id" : $("#userinput").val()
                    };

                    $.ajax({
                        type : "post",
                        url : "/main/group/chkAddId",
                        data : JSON.stringify(data),
                        contentType : "application/json",
                        dataType : "json",
                        success : function (map) {

                            // 유효성 검증 변수 선언
                            var regExpEmail = /^[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/;
                            var isExist = false;

                            console.log(map);
                            // 목록에 이미 존재하는지 검사
                            $(".list-group li").each(function() {
                                if (data.user_id == $(this).text()){
                                    isExist = true;
                                    return false;
                                };
                            });

                            if(data.user_id == ""){ // 값이 0이 아니면 회원존재
                                inputChk("아이디를 입력해 주세요.");

                            } else if(data.user_id.length < 6 || !regExpEmail.test(data.user_id)){
                                inputChk("메일 형식이 맞지 않습니다.");

                            } else if(isExist){
                                inputChk("초대 목록에 존재하는 아이디 입니다.");

                            } else if(map.chkId == 0){
                                inputChk("존재하는 회원이 아닙니다.");

                            } else if(map.chkInGroup != 0){
                                inputChk("이미 그룹의 회원입니다.");

                            } else if(map.chkInInvitation != 0){
                                inputChk("이미 초대된 회원입니다.");

                            } else {    // result == 1  유효성 검증 성공시
                
                                // 추가된 리스트 보여주고
                                var li = $("<li>");
                                li.attr("class","list-group-item");
                                li.text($("#userinput").val())
                                $(".list-group.invit").append(li);
                                $("#userinput").val("");

                                // 추가된 아이디를 클릭하면 삭제한다
                                $(".list-group-item").on('click', function () {
                                    console.log(this);
                                    $(this).remove(); 
                                });
                            }; // if-else
                        }
                    })

                })

            },
            type: 'notive-blue',
            buttons: {
                초대하기: {
                    btnClass: 'btn-notive-blue',
                    action: function () {
                        
                        var list = [];
                        $('.list-group.invit li').each(function () {
                            console.log($(this).text());
                            
                            var data = {
                                "group_no" : group_no,
                                "invit_id" : user_id,
                                "user_id" : $(this).text()
                            };

                            list.push(data);
                        });
                        console.log(list);

                        $.ajax({
                            type : "post",
                            url : "/invitation/invite",
                            data : JSON.stringify(list),
                            contentType : "application/json",
                            dataType : "json",
                            success : function (result) {
                                console.log(result);
                            }
                        })
                    }
                },
                취소: function () {
                    //close
                },
            },
        });


    });

    // 그룹이름 변경
    $(document).on('click', ".changeGroupName", function (e) {
        console.log(tParent);
        console.log("changeGroupName clicked.");
        
        // 취소하면 되돌리기 위한 기존 그룹명 저장
        origin_name = targeted.find("input:last").val();
        console.log(origin_name);

        // "새 그룹" 이벤트 바인드 해제
        // 중복클릭 방지
        

        // // 중복 안내 메세지 span 태그 삽입
        var messageDuplicate = '<span class="duplicated" style="display: none;">이미 존재하는 그룹 이름입니다.</span>'

        targeted.removeClass(); // 클래스 삭제
        console.log(targeted.contents()[1]);
        targeted.contents()[1].textContent = ""; // 기존 그룹이름 삭제
        targeted.attr('class','temp-a-change'); // 임시 클래스명 지정

        // input창 활성화
        targeted.find("input:last").attr({
            type: "text",
            class: "temp-input-change",
            autocomplete: "off"
        })

        //  중복 안내창 삽입
        targeted.after(messageDuplicate);

        setTimeout(function () { // 포커스 버그 방지
            $(".temp-input-change").focus();   // 그룹이름 입력창 강제 포커스
        }, 0); 

        $(".temp-input-change").focusin(function () {
            console.log(".temp-input-change focused.");

            // 아무것도 입력 안했을 경우를 위한 변수 정의
            group_name = $('.temp-input-change').val();
            isDuplicated = 1;
            
            // 입력되는 값 실시간 검사
            // 모든 변경사항을 Check해서 이벤트를 발생시킨다.
            $(".temp-input-change").on("propertychange change keyup paste", function(){
                console.log("temp-input-change changed.");

                group_name = $('.temp-input-change').val();


                console.log("\t + group_name : ", group_name);

                // post로 보낼 데이터 바인딩
                var data = {"user_group_name" : group_name};
                
                // Post Request 전송
                // DB에서 중복값 검증
                // 중복X : result = 0;
                // 중복 : result = 1;
                $.post(
                    "/common/groupNameCheck", 
                    data, 
                    function(result){ // 컨트롤러에서 넘어온 result값을 받는다 
                        if(result != 0){ // result가 0이 아니면 중복

                            // 중복일 때 안내 표시
                            $(".duplicated").css("display", "inline-block");
                            // 중복일 때 스타일
                            $(".temp-a-change").css("border", "1px dashed #d50000");

                        } else{

                            // 중복 아닐 때 안내 숨기기
                            $(".duplicated").css("display", "none");
                            // 중복 아닐 때 스타일
                            $(".temp-a-change").css("border", "1px dashed #3f51b5");

                        } // if-else

                        // 결과값 변수에 담기 
                        isDuplicated = result;

                    }, // success fuction
                    "json"
                ); // ajax 종료	

            }); // focusin event
            
        });// focusin function 종료

        //===========================================================//
        // input에서 포커스가 사라지거나
        // Enter 키를 입력할 때
        // ESC 키를 입력 할 때
        //===========================================================//

        $(".temp-input-change").focusout(modifyGroupName);

        // Enter 입력할 때
        $(".temp-input-change").keydown(function (key) {
            if (key.keyCode == 13 || key.which == 13 ) { // keyCode 13 = Enter
                
                console.log("Enter Key Pressed.");

                // 입력한 그룹 이름이 있고, 중복되지 않을 때
                if(isDuplicated == 0 && group_name != ""){

                    // input에 포커스를 제거시키면 자동으로 .focusout(createGroup)이 수행된다.
                    $(".temp-input-change").blur();    // input에 포커스를 없앤다.
                }   // if
            }; // if
        }); // keydwon enter Key

        // ESC 입력할 때
        // 무조건 그룹을 생성하지 않음
        $(".temp-input-change").keydown(function (key) {
            if (key.keyCode == 27 || key.which == 27 ) { // keyCode 27 = ESC
                
                console.log("ESC Key Pressed.");
                isDuplicated = 1;           
                $(".temp-input-change").blur();    // input에 포커스를 없앤다.

            }; // if
        }); // keydwon esc Key
    });

    // 그룹에서 나가기
    $(document).on('click', ".withdraw", function () {

        $.confirm({
            title: false,
            content: '정말 그룹에서 나가시겠습니까?',
            type: 'red',
            // boxWidth: '500px',
            buttons: {
                나가기: {
                    btnClass:'btn-red',
                    action: function () {
                        withdrawGroup();
                    }
                },
                취소:{}
            }
        });

    });

    
});