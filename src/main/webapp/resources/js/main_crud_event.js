// 메인페이지 CRUD 이벤트 관리 스크립트

// 컨트롤러와 연결
// 값을 주고 받고 화면에 표시 
// ajax 사용

$(function () {
    console.log("main_crud_event.js invoked.");

	//그룹클릭 id="group_${group.group_no}"
	$("[id*=groupli]").on('click',function(){
        console.log('dirve clicked..');
		
		var groupNo = $(this).val();
		console.log('groupNo:',groupNo);
		
		$.get('dir/gdirNo',{groupNo: groupNo},function(dirNo){
			console.log(dirNo);

	        $("#pfolderMemo").load("/memo/list?dirNo="+dirNo);        
		    $("#file_area").load("/file/list?dirNo="+dirNo);
	
			showFile(dirNo);
			showMemo(dirNo);
		});
        $("#trashSection").hide();
        $("#dirSection").show();

    }); //onclick for top_dir




    
    //===========================================================//
    
    var isDuplicated;   // 중복체크 변수
    var group_name;     // 그룹 이름 받을 변수

    // "새 그룹" 버튼 클릭함수 변수에 저장
    var groupNew = function (e) {
        console.log(".group_new onClicked...");
        // 그룹 생성 버튼 클릭시 추가될 HTML source

        $(".group_new").off('click');  
        e.preventDefault();

        $li = $('<li class="temp-a-new">');
        $i = $('<i class="xi-folder-shared">');
        $form = $('<form>');
        $input1 = $('<input type="hidden" name="group_no">');
        $input2 = $('<input type="hidden" name="user_id">').attr('value', user_id);
        $input3 = $('<input type= "text" class="temp-input-new" name="user_group_name" autocomplete="off">');

        $form.append($input1);
        $form.append($input2);
        $form.append($input3);

        $li.append($i);
        $li.append($form);

        // 중복 안내 메세지 span 태그 삽입
        var messageDuplicate = '<span class="duplicated" style="display: none;">이미 존재하는 그룹 이름입니다.</span>';

        
        // 그룹창이 펼쳐져 있으면 + 클릭시 닫히지 않고 새 그룹 생성
        if($(".group").css("display") == "block"){
            e.stopPropagation();    // 상위클릭 막기
        }; // if


        // 삽입된 Input태그로 그룹명을 받아 그룹 생성
        $(".group").prepend(messageDuplicate);  // messageDuplicate 태그 삽입
        $(".group").prepend($li);  // newGroup 태그 삽입


        setTimeout(function () { // 포커스 버그 방지
            $(".temp-input-new").focus();   // 그룹이름 입력창 강제 포커스
        }, 0); 

        // 포커스 된 후 나머지 메뉴 블러처리 : 차후 구현
        //$(".aside").css("background-color", "rgba(0, 0, 0, 0.2)");

        // 포커스 in => 그룹이름 입력중...
        $(".temp-input-new").focusin(function () {
            console.log(".temp-input-new focused.");
            
            // 입력되는 값 실시간 검사
            // 모든 변경사항을 Check해서 이벤트를 발생시킨다.
            $(".temp-input-new").on("propertychange change keyup paste", function(){
                console.log("temp-input-new changed.");
                
                group_name = $('.temp-input-new').val();
                console.log("\t + group_name : ", group_name);

                // post로 보낼 데이터 바인딩
                var data = {"user_group_name" : group_name};
                
                // Post Request 전송
                // DB에서 중복값 검증
                // 중복X : result = 0;
                // 중복 : result = 1;
                $.ajax({
                    type : "post",
                    url : "/common/groupNameCheck",
                    data : data,
					dataType : "json",
                    success:function(result){ // 컨트롤러에서 넘어온 result값을 받는다 
                        if(result != 0){ // result가 0이 아니면 중복
                            
                            // 중복일 때 안내 표시
                            $(".duplicated").css("display", "inline-block");
                            // 중복일 때 스타일
                            $(".temp-a-new").css("border", "1px dashed #d50000");
 
                        } else{

                            // 중복 아닐 때 안내 숨기기
                            $(".duplicated").css("display", "none");
                            // 중복 아닐 때 스타일
                            $(".temp-a-new").css("border", "1px dashed #3f51b5");

                        } // if-else

                        // 결과값 변수에 담기 
                        isDuplicated = result;
                    }, // success:function

                    error:function(){
                        alert("error");
                    } // error:function
                    
                }); // ajax 종료	

            }); // focusin event
            
        });// focusin function 종료
        
        //===========================================================//
        // input에서 포커스가 사라지거나
        // Enter 키를 입력할 때
        // ESC 키를 입력 할 때
        //===========================================================//

        // 포커스 out 
        // 그룹 생성 함수를 실행시켜 조건에 맞으면 그룹생성 진행
        $(".temp-input-new").focusout(createGroup);
                    
        // 엔터키 입력할 때
        // 조건에 맞을 때 그룹 생성
        $(".temp-input-new").keydown(function (key) {
            if (key.keyCode == 13 || key.which == 13 ) { // keyCode 13 = Enter
                
                console.log("Enter Key Pressed.");

                // 입력한 그룹 이름이 있고, 중복되지 않을 때
                if(isDuplicated == 0 && group_name != ""){

                    // input에 포커스를 제거시키면 자동으로 .focusout(createGroup)이 수행된다.
                    $(".temp-input-new").blur();    // input에 포커스를 없앤다.
                }   // if
            }; // if
        }); // keydwon enter Key

        // ESC 입력할 때
        // 무조건 그룹을 생성하지 않음
        $(".temp-input-new").keydown(function (key) {
            if (key.keyCode == 27 || key.which == 27 ) { // keyCode 27 = ESC
                
                console.log("ESC Key Pressed.");

                    // 강제로 그룹이름 중복을 명시 후
                    isDuplicated = 1;           
                    $(".temp-input-new").blur();    // input에 포커스를 없앤다.

            }; // if
        }); // keydwon esc Key

        

    } // var groupNew define

    // 그룹생성 함수
    var createGroup = function () {
        console.log("createGroup function invoked.");
    
        console.log("\t + group_name : ", group_name);  // 입력받은 그룹이름
        console.log("\t + isDuplicated : ", isDuplicated);  // DB 중복체크 결과
        
        // 입력한 그룹 이름이 있고, 중복되지 않을 때 그룹 생성
        if(isDuplicated == 0 && group_name != ""){
            console.log("request post...");
            
            // 폼 전송
            let formObj = $(".temp-a-new form").serialize();
            
            console.log(formObj);
            
            $.ajax({
                type: "post",
                url: "main/group/create",
                data: formObj,
                dataType: "json",
                success: function (group_no) {

                    $(".temp-a-new form input:first").attr('value', group_no);
                    $(".temp-a-new i").after(group_name);
                    $(".temp-a-new").removeAttr('style');

                    // ************************이미 적용된 이벤트를 없애기 위해 태그를 삭제 후 재생성한다.
                    $(".temp-input-new").remove()
                    $input = $('<input type= "hidden" class="temp-input-change" name="user_group_name" autocomplete="off">').attr('value', group_name);
                    $(".temp-a-new form").append($input);

                    $(".temp-a-new").attr('class','groupli');
                    


					$.post('/dir/gregister',{'groupNo':group_no,'userId':user_id},function(obj){
						console.log(obj);
						
						$("#pfolderMemo").load("/memo/list?dirNo="+obj.dirNo);        
						$("#file_area").load("/file/list?dirNo="+obj.dirNo);
						
						showFile(obj.dirNo);
						showMemo(obj.dirNo);
					});//post
                } // success
                
            }) // ajax
            
        } else {
            // 그룹 생성 실패
            console.log("createGroup Faild!");
            $(".temp-a-new").remove();
        } // if-else
        
        // reBinding
        // 이유. 중복클릭 방지를 위해 (".group_new").off('click') 수행해서 이벤트가 ubind 됨
        // 다시 강제로 이벤트를 bind 해주어 "새 그룹" 클릭 이벤트가 작동되도록 한다.
        $(".group_new").on('click', groupNew);  // 새 그룹 이벤트 바인딩
        
        $(".duplicated").remove();  // 중복 안내창 태그 삭제

    }; // var createGroup define

    // 그룹 생성 버튼 클릭
    $(".group_new").on('click', groupNew);


}); // jquery end