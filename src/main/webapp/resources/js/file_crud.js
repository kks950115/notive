
$(function () {
 
    console.log('file jq started..');
	
	
	var dirNo;
	
  	if(sessionStorage.getItem("dirNo") != null){
		dirNo = sessionStorage.getItem("dirNo");
		// console.log('dirNo', dirNo);
	}//if


	//객체 처음 진입할때
    $("#file_area").on("dragenter", function(e) {
        e.stopPropagation();
        e.preventDefault();
        // 드롭다운 영역 css
        $("#file_area").css('background-color', '#E3F2FC');
    });

	//객체 위에 머무를때
 	$("#file_area").on("dragover", function(e) {
        e.stopPropagation();
        e.preventDefault();
        // 드롭다운 영역 css
        $("#file_area").css('background-color', '#E3F2FC');
    });

	//객체 떠날때
    $("#file_area").on("dragleave", function(e) {
        e.stopPropagation();
        e.preventDefault();
        // 드롭다운 영역 css
        $("#file_area").css('background-color', '#FFFFFF');
    });

   


    //파일업로드-드래그앤드롭
    $("#file_area").on("drop", function(e) {
	    e.preventDefault();
	
        var files = e.originalEvent.dataTransfer.files;	// 기본 event를 불러오고.기본 evnet로 전송된 데이터를 가져와서.files 를 찾는다.
        //var file = files[0];    // 하나를 drop했다고 가정.
        
        formUploadFile(files);

    });

 

	//파일업로드-버튼
    $(document).on("click","#fileIcon",function () {   //업로드 버튼 클릭 시 업로드+ajax
        console.log('uploadFileBtn clicked..');
 
		var dirNo = $("#inputDirNo").val();
		// console.log('dirNo', dirNo);
		
        $('.inputFile').click();
    });// uploadFileBtn clicked..

 
	//파일선택
    $(document).on("change",".inputFile",function () {
        console.log('inputFile change..');
                		                
        var inputFile = $("input[name='uploadFile']");
        var files = inputFile[0].files;

        formUploadFile(files);
    }); //inputFile change
            
    //파일삭제
    $(document).on("click",".fileDeleteBtn",function () { //업로드된 파일 옆 X 클릭시
		console.log('fileDeleteBtn clicked.. ');
		
		var fileNo = $(this).data('no');
        var dirNo = $(this).data('dirno');
		// console.log('fileNo:', fileNo,'dirNo',dirNo);
		
		filDelete(dirNo, fileNo);
        
 		
    });// uploadResult span(x) clicked..
    

	//파일검색
	$("#search").keypress(function(e){
		console.debug("keypress for search...");
		
		var search = $("#search").val();
		var resultStr = search + "에 대한 검색 결과 : ";
	
		
		if(search != "" ) {
			$("#fileIcon").hide();
		} else {
			$("#fileIcon").show();
		} //if-else
		
		
		$(".uploadResult li").remove();
		
		showSearchFile(search);
		
	}); //keypress for search
    
	


});//jq

//파일삭제
function fileDelete(dirNo, fileNo){
	
	$.ajax({
            url: '/file/deleteFile',
                data: {fileNo: fileNo, dirNo: dirNo},       //경로,이름,파일종류 전송
                dataType: 'text',           //리턴타입
                type: 'POST',
                    success: function () {               
                        
                    }//sucess
        }); //$.ajax
	
}//fileDownload

// 첫화면 파일 목록
function showAllFile(){
    
    // $(".uploadResult ul").html("");
    $.getJSON('/file/getlistAll', function(result){

        //각각의 요소에 대해 함수실행
        $(result).each(function(i, obj){
            
        if(obj.fileType!='I'){ //일반파일 일때(썸네일, 다운로드)

            fileIcon(obj)

        }else{ // 이미지파일 일때(썸네일, 다운로드)

            imgIcon(obj);

        }//if-else

            $(".uploadResult ul").append($li);
            
        });// each
        
    });//getJSON
}//showFile

//업로드된 파일리스트 가져오기
function showFile(dirNo){
		
 	console.log('showFile invoked'); 
	// console.log('dirNo', dirNo);

    $.getJSON('/file/getlist', { 'dirNo' : dirNo },function(arr){

        //각각의 요소에 대해 함수실행
        $(arr).each(function(i, obj){
            
        if(obj.fileType!='I'){ //일반파일 일때(썸네일, 다운로드)

            fileIcon(obj)

        }else{ // 이미지파일 일때(썸네일, 다운로드)

            imgIcon(obj);

        }//if-else

            $(".uploadResult ul").append($li);
            
        });// each
        
    });//getJSON
}//showFile

//파일사이즈 사전체크
checkExtencion= function ( fileSize) {        
    console.log('checkExtencion(fileSize) invoked.');

    //5MB초과 라면
    if(fileSize > 8192000000 ){
        alert('파일 사이즈 초과');

        return false;
    }//if

    return true;
}//checkExtencion

formUploadFile = function (files) {
    
    var formData = new FormData();

    console.log("uploadFile");

    // console.log('files.length', files.length);
    
    for(var i=0; i<files.length; i++){

        if(!checkExtencion(files[i].size)){ //파일사이즈 체크
            return false;
        }//if
        // console.log(files[i]);
        formData.append("uploadFile", files[i]);
    }//for
    var dirNo = $("#inputDirNo").val();
    console.log('dirNo', dirNo);
    formData.append("dirNo",dirNo);

    // console.log(formData);

    // processData
    // - 기본 값은 true이다.
    // - 해당 값이 true일때는 data 값들이 쿼리스트링 형태인 
    //   key1=value1&key2=value2 형태로 전달된다.
    // - 하지만 이렇게 하면 file 값들은 제대로 전달되지 못한다.
    // - 그래서 해당 값을 false로 해주어 { key1 : 'value1', key2 : 'value2' } 
    //   형태로 전달해 주어야 file 값들이 제대로 전달된다.
    // contentTyp
    // - 기본값은 'application/x-www-form-urlencoded'이다. 해당 기본 타입으로는 파일이 전송 안되기 때문에 false로 해주어야 한다.
    $.ajax({    //post방식으로 폼데이터 전송

        url: '/file/upload',
        
            processData: false,
            contentType: false,
            data: formData,
            type: 'POST',
            dataType: 'JSON',     //반환타입
            success: function(result){
                    // console.log(result);

                showUploadedFile(result);                       //업로드 후 파일리스트 출력
                
            }//success
    }); // $.ajax

    getAllFileSize(function (storage) {
        updateProgress(storage);
    })
}//formUploadFile

//Ajax 호출 후 업로드된 결과를 처리
showUploadedFile = function (uploadResultArr) {    
        
        if(!uploadResultArr || uploadResultArr.length == 0){
            return;
        }//if

        $(uploadResultArr).each(function (i, obj) { //업로드된 파일 하나하나 당 속성부여
            // console.log('obj.fileName:',obj.fileName);
        
            // console.log('obj:', obj);
            
            if(obj.fileType!='I'){ //일반파일 일때(썸네일, 다운로드)

                fileIcon(obj);

            }else{ // 이미지파일 일때(썸네일, 다운로드)

                imgIcon(obj);

            }//if-else
            
            $(".uploadResult ul").append($li);
            
        });//each

}//showUploadedFile

// 이미지 파일 아이콘 및 디자인 추가 함수
function imgIcon(obj) {

    //공백,한글 등 문제발생 할수있으니 URI호출에 적합한 문자열로 인코딩 처리
    var fileCallPath = encodeURIComponent(obj.filePath+"/s_"+obj.fileUuid+"_"+obj.fileName);
    var originPath = encodeURIComponent(obj.filePath+"/"+obj.fileUuid+"_"+obj.fileName);
    
    // 날짜 포멧(yyyy-mm-dd)
    var convertedDate = convertDateFormat(new Date());

    // 업로드 날짜
    var uploadDate = (obj.fileUploadDate == null) ? convertedDate : obj.fileUploadDate; 

    $li = $('<li class="file" data-fileNo = "'+ obj.fileNo +'" data-dirNo="'+ obj.dirNo+'">').attr('data-fileCallPath', fileCallPath);
    $a = $('<a>').attr('onclick', 'openImg("'+ fileCallPath +'","'+ obj.fileName + '")');
    // $a = $('<a href="/file/download?fileName="' + originPath +'">')
    $img = $('<img src="/file/display?fileName=' + fileCallPath + '">')
    
    $svg = icon.img.mini;
    
    $divTypeName = $('<div class="file-type-name">').text(obj.fileName)
    $divTypeName.prepend($svg)

    $divWriteDate = $('<div class="writeDate">').text('등록일자 : ' +  uploadDate )	

    $a.append($img)
    
    $li.append($a)
    $li.append($divTypeName)
    $li.append($divWriteDate)
}

// 이미지 외에 파일 아이콘 및 디자인 추가 함수
function fileIcon(obj) {
    
    // 확장자 추출
    var ext = obj.fileName.split('.').pop().toLowerCase();

    // 파일 경로
    var fileCallPath = encodeURIComponent(obj.filePath+"/"+obj.fileUuid+"_"+obj.fileName);

    // 날짜 포멧(yyyy-mm-dd)
    var convertedDate = convertDateFormat(new Date());

    // 업로드 날짜
    var uploadDate = (obj.fileUploadDate == null) ? convertedDate : obj.fileUploadDate; 

    $li = $('<li class="file" data-fileNo = "'+ obj.fileNo +'" data-dirNo="'+ obj.dirNo+'">').attr('data-fileCallPath', fileCallPath);
    $a = $('<a href="/file/download?fileName=' + fileCallPath +'">')

    $divPreview = $('<div class="preview">')

    if(ext == "txt"){
        var $svgPreview = icon.txt.preview;
        var $svgTypeName = icon.txt.mini;
    } else if(ext == "pptx"){
        var $svgPreview = icon.pptx.preview;
        var $svgTypeName = icon.pptx.mini;
    } else if(ext == "pdf"){
        var $svgPreview = icon.pdf.preview;
        var $svgTypeName = icon.pdf.mini;
    } else if(ext == "doc" || ext == "docx"){
        var $svgPreview = icon.word.preview;
        var $svgTypeName = icon.word.mini;
    } else if(ext == "xlsx"){
        var $svgPreview = icon.xlsx.preview;
        var $svgTypeName = icon.xlsx.mini;
    } else if(ext == "zip" || ext == "7z" ){
        var $svgPreview = icon.zip.preview;
        var $svgTypeName = icon.zip.mini;
    } else {
        var $svgPreview = icon.etc.preview;
        var $svgTypeName = icon.etc.mini;
    }
    
    $divPreview.html($svgPreview)

    $divTypeName = $('<div class="file-type-name">').text(obj.fileName)
    $divTypeName.prepend($svgTypeName)

    $divWriteDate = $('<div class="writeDate">').text('등록일자 : ' +  uploadDate )	
    
    $a.append($divPreview)
    
    $li.append($a)
    $li.append($divTypeName)
    $li.append($divWriteDate)
}

// 날짜 포멧 함수
function convertDateFormat(date) {
    var year = date.getFullYear();
    var month = date.getMonth() + 1;
    month = month >= 10 ? month : '0' + month;
    var day = date.getDate();
    day = day >= 10 ? day : '0' + day;
    return [year, month, day].join('-');
}

//파일 검색
function showSearchFile(search){
	console.debug("showSearchFile invoked.");
	// console.debug("search : ", search);
	
	$.getJSON('/file/getSearchList', { 'search' : search },function(arr){

        console.log("arr: " , arr);
		console.log("arr.length: " , arr.length);
		
		if(arr.length == 0) {
			$(".uploadResult ul").html("검색결과가 없습니다.");
		} else {
			$(".uploadResult ul").html("");
		}//if-else (검색결과가 없으면)

        //각각의 요소에 대해 함수실행
        $(arr).each(function(i, obj){
	
			console.log(obj);
	        
	        if(obj.fileType!='I'){ //일반파일 일때(썸네일, 다운로드)
	
	            fileIcon(obj);
	
	        }else{ // 이미지파일 일때(썸네일, 다운로드)
	
	            imgIcon(obj);
	
	        }//if-else
	
	        $(".uploadResult ul").append($li);
            
        });// each
        
    });//getJSON
} //showSearchFile

// 이미지 열기
function openImg(fileCallPath, fileName) {
    console.log('openImg');

    // var fileCallPath = encodeURIComponent(finlNo.filePath+"/s_"+finlNo.fileUuid+"_"+finlNo.fileName);

    $.confirm({
        title: fileName,
        closeIcon: true,
        content: '<div class="img-form"></div>',
        boxWidth: '1030px',
        useBootstrap: false,
        bgOpacity: 0.9,
        animateFromElement: false,
        animation: "zoom",
        closeAnimation: "scale",
        animationSpeed: 400,
        onOpenBefore: function(){

            $img = $('<img class="img-open" src="/file/display?fileName=' + fileCallPath + '">')
            $(".img-form").append($img);
            
        },
        buttons: {
            다운로드 : {
                btnClass: 'btn-notive-blue',
                action: function () {
                    location.href = "/file/download?fileName=" + fileCallPath;
                }
            },
            닫기 : {

            }
            
        }
    })
    
}

// 다운로드
function downloadFile(fileCallPath) {

    location.href = "/file/download?fileName=" + fileCallPath;

}
// 총 파일 사이즈
function getAllFileSize(callback) {
    var sumFileSize = 0;

    $.ajax({
            url: '/file/getlistAll',
            type: 'get',
            async: false,
            success: function (result) {  

                $(result).each(function(i, obj){

                    sumFileSize += obj.fileSize;
        
                });

            }
        }); //$.ajax

        sumFIleSize = (sumFileSize / (1024 * 1024 * 1024)).toFixed(2);

        callback(sumFIleSize);

}