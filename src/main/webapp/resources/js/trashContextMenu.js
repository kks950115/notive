// 휴지통 우클릭 js

var targeted;

$(function(){
    console.log("dirContext start...");
    //---------------------------------------------------//
    // Function List
    // (1)  funcContext                 메뉴 열기       
    // (2)  click -> ".trashRestore"    복원     
    // (3)  click -> ".trashDelete"     영구 삭제
    //---------------------------------------------------//

    // ContextMenu 함수
    var funcContext = function(e){
        targeted = $(this);

        // ContextMenu 출력
        $(".trashContext").css({
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
    $(document).on("contextmenu",".trash-list-item", funcContext);

    // contextmenu 숨김
    $(document).click(function(){
        $(".trashContext").hide();

    });//document click

    //---------------------------------------------------//
    // ContextMenu Click Event
    //---------------------------------------------------//

	// 복원
    $(document).on('click', ".trashRestore", function (e) {
        
        // 선택된 파일이 있으면 선택된 파일 모두 복원
		if($(".trash-list-item").hasClass("selectedTrash")){
            restoreTrash(); 

        // 없으면 타겟 복원
        } else {    

            var selectedTrash = [];
            selectedTrash.push(targeted.val());
            console.log(targeted);

			data = { 
                "selectedTrash" : selectedTrash,
                "user_id" : user_id
            };
    
            $.post(
                "/main/trash/restore", 
                data,
                function(result){
                    targeted.remove();

                    console.log(result);
                    
                    for(var i in result) {
                        showRegister(result[i]);
                    };
                },
                "json"
            ); // ajax post /restore
        }

        getAllFileSize(function (storage) {
            updateProgress(storage);
        })

    });// trashRestore clicked..


    // 영구 삭제
    $(document).on('click', ".trashDelete", function (e) {

        // 선택된 파일이 있으면 선택된 파일 모두 삭제
		if($(".trash-list-item").hasClass("selectedTrash")){
            deleteTrash();

        // 없으면 타겟 삭제
        } else {

            var selectedTrash = [];
            selectedTrash.push(targeted.val());
    
			data = { 
                "selectedTrash" : selectedTrash,
                "user_id" : user_id
            };
    
            $.post(
                "/main/trash/remove", 
                data,
                function(result){

                    targeted.remove();

                }
            ); // ajax post /remove
        }

    });// trashDelete clicked..

    
});//jq
