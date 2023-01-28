// 폴더 우클릭 js

$(function(){
    console.log("dirContext start...");

    var dirNo;
	var dirTopNo;
	var dirLevel;

    var targeted;   // 클릭한 요소
    var tChild;    // 클릭한 요소의 자식 a태그

    var dirName;
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

     

        dirNo = $(this).data("no");
		console.log("dirNo:", dirNo);
		
		dirTopNo = $(this).data("topno");
		console.log("dirTopNo:", dirTopNo);
		
		dirLevel = $(this).data("level");
		console.log("dirLevel:", dirLevel);
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
        };//다중if

        // ContextMenu 출력
        $(".dirContext").css({
            "left": posLeft,
            "top": posTop
        }).show();
        
        // 브라우저 기본 클릭 무력화 
        return false;
    };//funcContext()

    

    //---------------------------------------------------//
    // ContextMenu Show and Hide
    //---------------------------------------------------//

    // contextmenu 표시
    $(document).on("contextmenu",".dirli", funcContext);

    // contextmenu 숨김
    $(document).click(function(){
        $(".dirContext").hide();

    });//document click

    //---------------------------------------------------//
    // ContextMenu Click Event
    //---------------------------------------------------//



	// 폴더추가
    $(document).on('click', ".dirRegisterContext", function (e) {
        console.log("dirModifyContext clicked.");

		dirRegisterBtn(dirLevel, dirNo, dirTopNo);

    });// dirModifyContext clicked..



    
    // 폴더이름 변경
    $(document).on('click', ".dirModifyContext", function (e) {
        console.log("dirModifyContext clicked.");

		dirModifyBtn(dirLevel, dirNo, dirTopNo);

    });// dirModifyContext clicked..

	

    // 폴더삭제
    $(document).on('click', ".dirRemoveContext", function (e) {
        console.log("dirModifyContext clicked.");

		dirRemoveBtn(dirLevel, dirNo, dirTopNo);

    });// dirModifyContext clicked..

    
});//jq
