// 휴지통 우클릭 js

var targeted;

$(function(){
    console.log("dirSectionContext start...");
    //---------------------------------------------------//
    // Function List
    // (1)  funcContext                 메뉴 열기       
    // (2)  click -> ".trashRestore"    복원     
    // (3)  click -> ".trashDelete"     영구 삭제
    //---------------------------------------------------//

    // ContextMenu 함수
    var funcContext = function(e){
        targeted = $(this);

        dirNo = $(this).data("dirno");
		console.log("dirNo:", dirNo);

        // ContextMenu 출력
        $(".dirSectionContext").css({
            "left": e.pageX,
            "top": e.pageY
        }).show();
        
        // 브라우저 기본 클릭 무력화 
        return false;
    };//funcContext()

    //---------------------------------------------------//
    // ContextMenu Show and Hide
    //---------------------------------------------------//

    // contextmenu 표시
    $(document).on("contextmenu","#dirSection #pfolderMemo, #dirSection #file_area", funcContext);

    // contextmenu 숨김
    $(document).click(function(){
        $(".dirSectionContext").hide();

    });//document click

    //---------------------------------------------------//
    // ContextMenu Click Event
    //---------------------------------------------------//

	// 메모 추가
    $(document).on('click', ".addMemo", function (e) {
        
        $('#registerBtn').click();

    });// addMemo clicked..


    // 파일 추가
    $(document).on('click', ".addFile", function (e) {

        var dirNo = $("#inputDirNo").val();

        $('.inputFile').click();

    });// addFile clicked..


    
});//jq
