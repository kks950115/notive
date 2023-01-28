
// ajax 처리 위한 전역변수 선언
var result;
var uuid;
var fileName;
var uploadPath;

// 이미지 파일 확장자만 가능
var regex = new RegExp("(.*?)\.(jpeg|gif|png|jpg|JPG|JPEG|GIF|PNG)$");
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

//---------------------------------------

// 파일이 첨부되기 전 uploadDiv복제
var cloneObj = $(".uploadDiv-userImage-myPage").clone();

// 목록 보여주는 부분 별도 함수로 처리
var uploadResult = $(".uploadResult-userImage-myPage");

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


//----------------------Promise 사용
function promise_imgUpload(){
    return new Promise(function(resolve, reject){
        console.debug("upload promise invoked.");

        // 이미지 서버 저장
        var formDataForUpload = new FormData();
        var inputFile = $("input[name='uploadUserImg']");
        var files = inputFile[0].files;
        
        console.log("inputFile : " + inputFile);
        console.log("files : " + files);
        
        for(var i=0; i<files.length; i++){
            if(!checkExtension(files[i].name, files[i].size) ){
                return false;
            } //if : 파일 크기 및 확장자가 조건에 어긋나면 false
            
            formDataForUpload.append("uploadFile", files[i]);
        } //for

        $.ajax({
            url: "/user/userImageUpload",
            processData: false, //일반적으로 서버에 전송되는 데이터는 쿼리스트링을 생성하는데,
                                // 파일전송의 경우 이 부분을 막기 위한 설정
            contentType: false, 
            data: formDataForUpload, 
            method: "post",
            dataType: "json",
            
            success: function(resp){
                console.log("resp : " + resp);

                showUploadResult(resp); //업로드 결과 처리 함수

                // 응답(resp)를 전역변수 result에 담기 (다음 ajax의 전송 데이터로 사용하기 위해)
                // result = resp;
                uuid = resp[0].uuid;
                fileName = resp[0].fileName;
                uploadPath = resp[0].uploadPath;

                console.log("result : " + result);
                
                resolve(result);
                } //success
            }); //.ajax
    }) //new Promise
} //promise_imgUpload

function promise_modifyImg(result){
    return new Promise(function(resolve, reject){
        console.debug("modify promise invoked.");

        $.ajax({
            url: "/user/modifyUserImage",
            method: "post",
            data: {uuid: uuid, fileName: fileName, uploadPath: uploadPath},
            dataType: "json",
            
            success : function(resp){
                console.log("after modify resp : " + resp);

            } //success
        }) //.ajax
    }) //new Promise
} //modifyImg


$(function(){
    console.debug("myPage.js started...");

    //--------------이미 저장된 프로필이미지 가져오기
    // (function(){ ;; })(); : 즉시실행함수(IIFE : Immediately invoked Function Expression)
    //                         정의됨과 동시에 실행되는 함수
    // myPage가 로드됨과 동시에 유저 이미지를 조회하기
    (function(){

        $.getJSON("/user/getUserImage", {userID:userID}, function(img){
            console.log(img);

            // 저장된 이미지가 있다면 기본 프사 가리기
            if(img.uuid != ""){
                $("#userImg").hide();
            } //if

            //전역변수 uuid에 저장
            uuid = img.uuid;

            var str = "";

            var fileCallPath = encodeURIComponent(img.uploadPath + "/s_" + img.uuid + "_" + img.fileName);

            str += "<li data-path='"+img.uploadPath+"' data-uuid='"+img.uuid+"' data-filename='"+img.fileName+"'><div>";
           
            // user/display 컨트롤러 호출
            str += "<img src='/user/display?fileName="+fileCallPath+"'";
    
            // 썸네일 크기 조절
            str += " style= 'object-fit: cover; width: 90px; height: 90px; border-radius: 100%;'>"
            str += "</div>";
            str + "</li>";

            $(".uploadResult-userImage ul").html(str);

            // 삭제 버튼에 파일 정보 속성 추가
            $("#delete-userImage").attr("data-file", fileCallPath);

        }); //getJSON
    })(); //IIFE : 프로필 이미지 조회


    //------------------파일 업로드(수정) 처리
    $("input[type='file']").change(function(e){

        // 이미지 서버 저장
        var formDataForUpload = new FormData();
        var inputFile = $("input[name='uploadUserImg']");
        var files = inputFile[0].files;
        
        console.log("inputFile : " + inputFile);
        console.log("files : " + files);
        
        for(var i=0; i<files.length; i++){
            if(!checkExtension(files[i].name, files[i].size) ){
                return false;
            } //if : 파일 크기 및 확장자가 조건에 어긋나면 false
            
            formDataForUpload.append("uploadFile", files[i]);
        } //for


        // 이미지 업로드 ajax 성공 후,
        // 그 결과값을 이용해서 modify 수행
        promise_imgUpload()
        .then(promise_modifyImg);
        
    }); //change for input type=file


    //-------------------파일삭제버튼(x) 클릭 이벤트 
    $("#delete-userImage").on("click", function(){

        console.debug("delete user Image button is clicked...");

        //삭제될 파일정보
        var targetFile = $(this).data("file");

        //썸네일 영역
        var targetLi = $(".uploadResult-userImage ul li");
        
        $.ajax({
            url: "/user/removeUserImage",
            data: {fileName: targetFile, uuid: uuid},
            dataType: "text",
            method: "post",
            
            success: function(){
                // 썸네일 영역 삭제
                targetLi.remove();

                // 삭제버튼의 file 정보 삭제
                $("#delete-userImage").removeAttr("data-file");
                
                // 기본 이미지 보여주기
                $("#userImg").show();
            } //success
        }); //.ajax
    }); //on click for X



    //--------------비밀번호 변경
    $("#modifyBtn-userPass").on("click", function(){
        console.debug("##modifyBtn-userPass is clicked...");
        
        location.href="/user/modifyUserPassPage";
    }) //onclick for modifyBtn-userPass

    //-------------이름 변경
    $("#modifyBtn-userName").on("click", function(){
        console.debug("modifyBtn-userName is clicked.");

        $(this).hide();
        $("#submitBtn-modifyUserName").show();

        $("#input-userName").attr("readonly", false);

    }) //onclick for modifyBtn-userName

    
    $("#submitBtn-modifyUserName").on("click", function(){
        console.debug("submitBtn-modifyUserInfo is clikced");
    
        var userName = $("#input-userName").val();
		console.log("userName: " + userName);

        $.ajax({
            async: false,
            method: "post",
            url: "modifyUserName",
            data: userName,
            contentType: "application/x-www-form-urlencoded; charset=utf8",
            
            success: function(data){
                $(this).hide();
                $("#modifyBtn-userName").show();
        
                $("#input-userName").attr("readonly", true);

                location.href="/user/myPage";
            }, //.ajax success
    
            error: function( req, status, errThrown ){
                $("#checkName").css("color", "red");
                $("#checkName").text("이름을 입력해주세요.");

            } //.ajax error
        }) //ajax
    }) //onclick for submitBtn-modifyUserName


    //-------------생일 변경
    $("#modifyBtn-userBday").on("click", function(){
        console.debug("modifyBtn-userBday is clicked.");

        $(this).hide();
        $("#submitBtn-modifyUserBday").show();

        $(".input-selectBox").attr("disabled", false);
    }) //onclick for modifyBtn-userBday


    $("#submitBtn-modifyUserBday").on("click", function(){
        console.debug("submitBtn-modifyUserInfo is clikced");

        $(this).hide();
        $("#modifyBtn-userBday").show();

        $("#input-userName").attr("readonly", true);
    }) //onlick for submitBtn-userInfo


    //--------------------select-box default 설정
    var now = new Date(); 
    var y = now.getFullYear();
    
    //년도 selectbox만들기 
    for(var i = 1900 ; i <= y ; i++) { 
        $('#myPage-year').append('<option value="' + i + '">' + i + '년</option>'); 
    } 
        
    // 월별 selectbox 만들기 
    for(var i=1; i <= 12; i++) {
        var mm = i > 9 ? i : "0"+i ; 
        
        $('#myPage-month').append('<option value="' + mm + '">' + mm + '월</option>'); 
    } 
        
    // 일별 selectbox 만들기 
    for(var i=1; i <= 31; i++) { 
        var dd = i > 9 ? i : "0"+i ;
        $('#myPage-day').append('<option value="' + dd + '">' + dd+ '일</option>'); 
    }
    //
    $("#myPage-year > option[value="+loginUserBday_yyyy+"]").attr("selected", "true"); 
    $("#myPage-month > option[value="+loginUserBday_mm+"]").attr("selected", "true"); 
    $("#myPage-day > option[value="+loginUserBday_dd+"]").attr("selected", "true");
    

    //-------------------회원탈퇴
    $("#myPage-withdrawBtn").on("click", function(){
        if(confirm("탈퇴시 기존에 저장된 자료들은 모두 삭제되며, Notive의 서비스를 이용할 수 없습니다. 정말 탈퇴하시겠습니까?") == true){
            alert("탈퇴되었습니다. Notive를 이용해주셔서 감사합니다.");
        }
        else{
            return ;
        } //if-else
    }) //onclick for withdrawBtn
    

    $("#myPage-cancleBtn").on("click", function(){
        
        location.href="/main";
    }) //onclick for cancleBtn



    //-----------------확인버튼 클릭
    $("#myPage-cancleBtn").on("click", function(){
        console.debug("cancleBtn is clicked.");

        location.href="/main"
    }) //onclick for cancleBtn


}) //.jq