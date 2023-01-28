// 폴더 우클릭 js

$(function(){
    console.log("fileContext start...");

    var dirNo;
	var fileNo;
    var fileCallPath;
 

    var targeted;   // 클릭한 요소
 
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
		
		
		if(sessionStorage.getItem("dirNo") != null){
			dirNo = sessionStorage.getItem("dirNo");
			console.log('dirNo', dirNo);
		}//if
	
		targeted = $(this);
        children = $(this).find("a");
        console.log("targeted:", targeted);
        console.log("children:", children);

        dirNo = targeted.data("dirno")
		fileNo = targeted.data("fileno");
        fileCallPath = targeted.data("filecallpath");
	
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
        $(".fileContext").css({
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
    $(document).on("contextmenu",".uploadResult li", funcContext);

    // contextmenu 숨김
    $(document).click(function(){
        $(".fileContext").hide();

    });//document click

    //---------------------------------------------------//
    // ContextMenu Click Event
    //---------------------------------------------------//
    
    // 다운로드
    $(document).on('click', ".fileDownloadContext", function (e) {
        console.log("fileDownloadContext clicked.");
        if($(".file").hasClass("select")){
            
                $(".select").each(function (i, obj) {
                    var fileCallPath = $(obj).data("filecallpath");

                    location.href = "/file/download?fileName=" + fileCallPath;

                })
            
            // 개별 삭제
        } else {
            
            downloadFile(fileCallPath);
            
        } // 

    });// fileModifyContext clicked..

	

    // 파일삭제
    $(document).on('click', ".fileRemoveContext", function (e) {
        console.log("fileRemoveContext clicked.");
        
        // 다중 삭제
        if($(".file").hasClass("select")){
            
            $(".select").each(function (i, obj) {
                var fileNo = $(obj).data("fileno");
                var dirNo = $(obj).data("dirno");
                
                fileDelete(dirNo, fileNo);
                $(obj).remove();
            })
            
            // 개별 삭제
        } else {
            
            fileDelete(dirNo, fileNo);
            // $(".uploadResult ul").html("");
            targeted.remove();
            
        } // 

        getAllFileSize(function (storage) {
            updateProgress(storage);
        })
    });// fileRemoveContext clicked..
    
});//jq