// 비밀번호 정규식 (문자 + 숫자 또는 특수문자 포함 형태의 8~16자리)
var passRule = /^(?=.*[a-zA-Z])((?=.*\d)|(?=.*\W)).{8,16}$/;

// ID(이메일) 형식 정규식
var idRule = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

// ID 중복체크 여부 확인(미확인:0, 확인:1)
var isCheckID = 0;

// 비밀번호 유효성검증 여부
var isCheckPass = 0;

// 비밀번호 재확인 여부
var isCheckPassConfirm = 0;

// 약관동의 체크 여부 확인
var isCheckTerms = 0;

// 이름입력
var isInputName ="#userName".value;

// 생년월일 입력 여부
var isSelectYear = "#year".value;
var isSelectMonth = "#month".value;
var isSelectDay = "#day".value;


// 약관동의 체크여부
var isCheckTerms = "#agreeTerms".chekced;

//-------------------------------------------------//


$(function(){
    console.clear();
    console.debug("jq started...!");

    //--------------ID 형식 유효성 검증
    $("#signUp-input-userID").keyup(function(){
        console.debug("keyup for userID");

        var userID = $("#signUp-input-userID").val();

        if (!idRule.test(userID)){
            $("#checkIDRule").css("color", "red");
            $("#checkIDRule").text("이메일 형식으로 입력해주세요.");
        } else {
            $("#checkIDRule").text("");
        }
    }) //keyup for userID

    //--------------ID중복확인
    $("#checkIDBtn").click(function(){
        console.clear;
        console.debug("checkID() invoked.");
    
        // userID로 받은 값을 대입
        var userID = $("#signUp-input-userID").val();
    
        $.ajax({
            async: false,
            method: "post",
            url: "checkID",
            data: userID,
            contentType: "application/x-www-form-urlencoded; charset=utf8",
            
            
            success: function (data) {
                console.log("**ajax success");
    
                if(data == "success") {
                    console.log("data : ${}" ,data);
                    alert("사용가능한 이메일입니다.");
                    
                    $("#divInputID").addClass("has-success")
                    $("#divInputID").removeClass("has-error")
                    
                    $("#userPass").focus();
    
                    isCheckID = 1;   //아이디 체크 성공

					$("#checkID").css("color", "black");
            		$("#checkID").text("");

                } else {
                    console.log("data: ${data}");
                    alert("이미 사용중인 이메일 입니다. 다른 이메일을 입력해주세요.");
                    
                    $("#divInputID").addClass("has-error")
                    $("#divInputID").removeClass("has-success")
                    
                    $("#userID").focus();

               isCheckID=0; //아이디 체크 실패
                } //if-else
                
                console.log("isCheckID: ${}", isCheckID);
            } //success
           , error: function(req, status, errThrown) {
              alert("이메일을 입력해주세요.");
           } //error
        }); //ajax
    }) //onclick for checkIDBtn

    $("#signUp-input-userID").focusout(function(){
        console.debug("focusout for userID...");

        // 아이디 중복확인을 하지 않았으면
        if(isCheckID==0){
            $("#checkID").css("color", "red");
            $("#checkID").text("이메일 중복확인을 해주세요.");
        } //if
    }) //on focusout for userID


    $("#userName").keyup(function(){
        console.debug("keyup event for userName...");

        var name = $(this).val();

        if(name.trim() == ""){
            $("#checkName").css("color", "red");
            $("#checkName").text("이름을 입력해주세요.");
        } else {
            $("#checkName").css("color", "black");
            $("#checkName").text("");
        } //if-else
    })


    //---------------비밀번호 유효성검사
    $("#userPass").keyup(function(){
        var f1 = document.forms[0];
        var userPass = f1.userPass.value;
    
        // 비밀번호 유효성검증이 실패하면...
        if(!passRule.test(userPass)){
            $("#checkPass").css("color", "red");
            $("#checkPass").text("비밀번호는 숫자 또는 특수문자를 포함한 8~16자리로 입력해주세요.");

            isCheckPass=0;  // 유효성검사 실패

        // 비밀번호 자릿수 유효성 검증이 통과하면...
        }else{
            $("#checkPass").text("");

            isCheckPass=1;  // 유효성검사 성공
        } //if-elseId

        console.log("isCheckPass: ${}", isCheckPass);
    }) //keyup for userPass

    
    //---------------비밀번호 일치여부 검사
    $("#userPassConfirm").keyup(function(){
        var f1 = document.forms[0];
        var userPass = f1.userPass.value;
        var userPassConfirm = f1.userPassConfirm.value;
    
        // 비밀번호 자릿수 유효성검증이 실패하면...
        if(userPass == userPassConfirm){
            $("#checkPassConfirm").text("");   

            isCheckPassConfirm=1; //검사 성공
        }else{
            $("#checkPassConfirm").css("color", "red");
            $("#checkPassConfirm").text("비밀번호가 일치하지 않습니다.");

            isCheckPassConfirm=0; //검사 실패
        } //if-else : 비밀번호 자릿수 유효성 검증       
      
        console.log("isCheckPassConfirm: ${}", isCheckPassConfirm);
    }) //keyup for userPassConfirm


    //-------------------날짜 select-box
    var now = new Date(); 
    var year = now.getFullYear(); 


    //년도 selectbox만들기 
    for(var i = 1900 ; i <= year ; i++) { 
        $("#year").append('<option value="" disabled hidden> 연도 </option>')
        $('#year').append('<option value="' + i + '">' + i + '년</option>'); 
    } 
        
    // 월별 selectbox 만들기 
    for(var i=1; i <= 12; i++) {
        var mm = i > 9 ? i : "0"+i ; 
        
        $("#month").append('<option value="" disabled hidden> 월 </option>')
        $('#month').append('<option value="' + mm + '">' + mm + '월</option>'); 
    } 
        
    // 일별 selectbox 만들기 
    for(var i=1; i <= 31; i++) { 
        var dd = i > 9 ? i : "0"+i ;

        $("#day").append('<option value="" disabled hidden> 일 </option>')
        $('#day').append('<option value="' + dd + '">' + dd+ '일</option>'); 
    }
    
    $("#year > option[value='']").attr("selected", "true"); 
    $("#month > option[value='']").attr("selected", "true"); 
    $("#day > option[value='']").attr("selected", "true");

    // 생년월일이 선택될 때
    $("#year", "#month", "#day").change(function(){
        console.debug("Bday is selected");

        // 연도, 월, 일이 모두 선택되었으면 경고메시지 없애기
        if (isSelectYear != '연도'
        && isSelectMonth != '월'
        && isSelectDay != '일'){
            
            $("#checkSelectBday").text("");
        } //if-else
    }) //change for Bday


    
    //---------------------약관보기
    $("#showTerms").click(function() {
        console.debug("showTerms clicked.");
        
        $.alert({
            title: 'NOTIVE 이용약관',
            content: '<b>제1조(목적)</b><br>이 약관은 NOTIVE 회사(전자상거래 사업자)가 운영하는 NOTIVE 사이버 몰(이하 “몰”이라 한다.)에서 제공하는 인터넷 관련 서비스(이하 “서비스”라 한다)를 이용함에 있어 사이버 몰과 이용자의 권리 의무 및 책임사항을 규정함을 목적으로 합니다. <p/>※「PC통신, 무선 등을 이용하는 전자상거래에 대해서도 그 성질에 반하지 않는한 이 약관을 준용합니다. <p/> <b>제2조(정의)</b> <br>① “몰”이란 회사가 재화 또는 용역(이하 “재화 등”이라 함)을 이용자에게 제공하기 위하여 컴퓨터 등 정보통신설비를 이용하여 재화 등을 거래할 수 있도록 설정한 가상의 영업장을 말하며, 아울러 사이버몰을 운영하는 사업자의 의미로도 사용합니다. <br>② “이용자”란 “몰”에 접속하여 이 약관에 따라 “몰”이 제공하는 서비스 를 받는 회원 및 비회원을 말합니다.<br> ③ ‘회원’이라 함은 “몰”에 회원등록을 한 자로서, 계속적으로 “몰”이 제공하는 서비스를 이용할 수 있는 자를 말합니다.<br> ④ ‘비회원’이라 함은 회원에 가입하지 않고 “몰”이 제공하는 서비스를 이용하는 자를 말합니다.',

        });

    }); //click for showTerms

    //--------------------약관확인
    $(".checkBox").change(function(){
        console.debug("checkBox clicked.");

        if($("#agreeTerms").is(":checked")){
            isCheckTerms = 1;   //약관동의 성공

            $("#checkAgreeTerms").css("color", "black");
            $("#checkAgreeTerms").text("");
        } else{
            isCheckTerms = 0;   //약관동의 실패
        }

    }); //click for checkbox

    //--------------------회원가입 form 전송
    $("#signUp-submitBtn").click(function(){
        console.debug("submitBtn clicked.");
        
        var formObj = $("form[action='/user/signUp']");

        var isInputName = $("#userName").val();
        
		var str = "";
        
        console.log("isCheckID: ${}, isCheckPass:${}, isCheckPassConfirm: ${}, isInputName:${}, isCheckTerms:${}", isCheckID, isCheckPass, isCheckPassConfirm, isInputName, isCheckTerms);
        console.log($("#year").selected);

        if(
            isCheckID == 1 
            && isCheckPass==1 
            && isCheckPassConfirm==1
            && isInputName != ""
			&& isCheckTerms == 1 
            && isSelectYear != ""
            && isSelectMonth != ""
            && isSelectDay != ""
			) {
            

            $(".uploadResult-userImage ul li").each(function(i, obj){
                var jobj = $(obj);

                console.dir(jobj);

                str+= "<input type='hidden' name='fileName' value='"+jobj.data("filename")+"'>";
                str+= "<input type='hidden' name='uuid' value='"+jobj.data("uuid")+"'>"; 
                str+= "<input type='hidden' name='uploadPath' value='"+jobj.data("path")+"'>";
                // str+= "<input type='hidden' name='fileType' value='"+jobj.data("type")+"'>";
            }) // 업로드된 이미지 정보 hidden으로 넘기기

            formObj.append(str);
            
            $("#signUp-submitBtn").attr("type","submit");    // 입력내용에 이상이 없으면 입력값 전송

        } else {
            if(isCheckID==0){
                $("#checkID").css("color", "red");
                $("#checkID").text("이메일 중복확인을 해주세요.");
            } //if

            if(isInputName.trim() == ""){
                $("#checkName").css("color", "red");
                $("#checkName").text("이름을 입력해주세요.");
            } //if

            if (isCheckTerms != 1){
                $("#checkAgreeTerms").css("color", "red");
                $("#checkAgreeTerms").text("약관에 동의해주세요.");
            } //if

            if (isCheckPass != 1) {
                $("#checkPass").css("color", "red");
                $("#checkPass").text("비밀번호를 입력해주세요.");
            } //if

           // if (isSelectYear == "연도"
                //|| isSelectMonth == "월"
              //  || isSelectDay == "일") {

                $("#checkSelectBday").css("color", "red");
                $("#checkSelectBday").text("생년월일을 입력해주세요.");
         //   }

            alert("입력하신 내용을 다시 확인해주세요.")
        } // outer if-else : 유효성검증 성공 / 실패
    }); //click for submitBtn



   //-----------------확인버튼 클릭
    $("#myPage-cancleBtn").on("click", function(){
        console.debug("cancleBtn is clicked.");

        location.href="/main"
    }) //onclick for cancleBtn


	$("#cancleBtn-modifyPass").on("click", function(){
	console.debug("cancleBtn is clicked");
	
	location.href="/user/myPage"
	})



}); //.jq 