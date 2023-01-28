// 폴더 우클릭 js

$(function(){
    console.log("memoContext start...");

    var dirNo;
	var memoNo;
 

    var targeted;   // 클릭한 요소
  

    // memoContextMenu 함수
    var funcContext = function(e){
        console.log("funcContext invoked.");

        dirNo = $(this).data("dirno");
		console.log("dirNo:", dirNo);
		
		memoNo = $(this).data("memono");
		console.log("memoNo:", memoNo);

		memoTitle = $(this).data("memotitle");
		console.log("memoTitle:", memoTitle);

        // ContextMenu 출력
        $(".memoContext").css({
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
    $(document).on("contextmenu","#memoList .memo", funcContext);


    // contextmenu 숨김
    $(document).click(function(){
        $(".memoContext").hide();

    });//document click

    //---------------------------------------------------//
    // ContextMenu Click Event
    //---------------------------------------------------//



	// 메모 열기
    $(document).on('click', ".memoShow", function (e) {
        console.log("memoShow clicked.");
		
        console.log("dirNo:", dirNo, "memoNo", memoNo);
		openMemo(dirNo, memoNo, memoTitle)

    });// memoRegisterContext clicked..



    
    // 메모수정
    $(document).on('click', ".memoModifyContext", function (e) {
        console.log("memoModifyContext clicked.");

		console.log("dirNo:", dirNo, "memoNo", memoNo);
		
        memoModifyForm(dirNo, memoNo);
        // memoModifyForm(dirNo, memoNo);

    });// memoModifyContext clicked..

	

    // 메모삭제
    $(document).on('click', ".memoRemoveContext", function (e) {
        console.log("memoRemoveContext clicked.");

        if($(".memo ").hasClass("select")){

            $(".select").each(function (i, obj) {
                var memoNo = $(obj).data("memono");
                var dirNo = $(obj).data("dirno");

                memoRemove(dirNo, memoNo);
            })

        } else {

            memoRemove(dirNo, memoNo);

        } // 
		

    });// memoRemoveContext clicked..

});//jq