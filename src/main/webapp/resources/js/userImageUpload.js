// 이미지 파일 확장자만 가능
var regex = new RegExp("(.*?)\.(jpeg|gif|png|jpg)$");
// 최대크기 5MB
var maxSize = 5242880;


// 파일크기, 파일 확장자 체크
function checkExtension(fileName, fileSize){

    if(fileSize >= maxSize) { 
        alert("파일 크기 초과");
        
        return false;
    } //if

    if(!regex.test(fileName)){
        alert("이미지 파일만 선택할 수 있습니다.");

        return false;
    } //if

    return true;
} //checkExtension


// 파일이 첨부되기 전 uploadDiv복제
var cloneObj = $("#uploadDiv-userImage").clone();

// 목록 보여주는 부분 별도 함수로 처리
var uploadResult = $(".uploadResult-userImage");

// 썸네일 처리
function showUploadResult(uploadResultArr){

    // 파일이 업로드되지 않았으면... 
    if(!uploadResultArr || uploadResultArr.length == 0) {
        return;
    } // if

    // 기본 프사 숨기기
    $("#userImg").hide();
    
    var uploadUL = $(".uploadResult-userImage ul");
    var str = "";

    $(uploadResultArr).each(function(i, obj){

        // 썸네일용 파일 이름 인코딩(공백문자, 다국어문자 등)
        var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid+ "_" + obj.fileName);

        str += "<li data-path='"+obj.uploadPath+"'";
        str += " data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"'>";
        
        // user/display 컨트롤러 호출
        str += "<img src='/user/display?fileName="+fileCallPath+"'";

		// 썸네일 크기 조절
		str += " style= 'object-fit: cover; width: 90px; height: 90px; border-radius: 100%;'>"
        str += "</div>";
        str + "</li>";

        // 삭제 버튼에 파일 정보 속성 추가
        $("#delete-userImage").attr("data-file", fileCallPath);

    }); //traverse uploadResultArr

    uploadUL.html(str);
} //showUploadResult



$(function(){
    console.debug("userImage Upload jq started...");

    // 파일 업로드 처리 (별도 클릭 없이)
    $("input[type='file']").change(function(e){

        var formData = new FormData();
        var inputFile = $("input[name='uploadUserImg']");
        var files = inputFile[0].files;

        for(var i=0; i<files.length; i++){
            if(!checkExtension(files[i].name, files[i].size) ){
                return false;
            } //if : 파일 크기 및 확장자가 조건에 어긋나면 false

            formData.append("uploadFile", files[i]);
        } //for

        $.ajax({
            url: "/user/userImageUpload",
            processData: false,
            contentType: false, 
            data: formData, 
            method: "post",
            dataType: "json",

            success: function(result){
                console.log(result);
                
                showUploadResult(result); //업로드 결과 처리 함수
            } //success

        }); //.ajax
    }); //change for input type=file


    //-------------------파일삭제버튼(x) 클릭 이벤트 
        $("#delete-userImage").on("click", function(){

        console.debug("delete user Image button is clicked...");
        
        var targetFile = $(this).data("file");
        
        // var targetLi = $(this).closest("li");
        var targetLi = $(".uploadResult-userImage ul li");
        
        $.ajax({
            url: "/user/deleteUserImage",
            data: {fileName: targetFile},
            dataType: "text",
            method: "post",
            
            success: function(result){
                alert(result);
                // 썸네일영역 삭제
                targetLi.remove();

                // 삭제버튼의 file 정보 삭제
                $("#delete-userImage").removeAttr("data-file");

                // 기본이미지 보여주기
                $("#userImg").show();
            } //success

            ,error: function(){
                alert("삭제할 이미지가 없습니다");
            }
        }); //.ajax

    }); //on click for X


}) //.jq