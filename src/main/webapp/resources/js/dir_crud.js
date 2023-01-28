                    
$(function () {

    console.log('dir jq started..');
	
	//폴더리스트

    $.ajax({
            url:'/dir/getlist',
            method: 'get',
            dataType:'JSON',

            success: function (arr) {
                // console.log(arr);

                var str ="";
        
                //각각의 폴더리스트 생성
                $(arr).each(function (i, obj) {
        
                        str +=  "<li id='topDir_"+obj.dirLevel+"_"+obj.dirNo+"' "+
                                    "class='subDir_"+obj.dirLevel+"_"+obj.dirTopNo+"'>"+
                                    "<div class='dirli' data-no="+obj.dirNo+" data-topno="+obj.dirTopNo+" data-level="+obj.dirLevel+" onclick='dirListBtn("+obj.dirLevel+", "+obj.dirNo+", "+obj.dirTopNo+", "+obj.dirGroup+")'>"+
                                        "<a>"+                           
                                            "<span id='originName"+obj.dirNo+"'>"+
                                                "<i class='xi-folder-o'></i>"+ 
                                                obj.dirName +    
                                            "</span>"+
                                        "</a>"+ 
                                        "<div class='dirRemove' onclick='dirRemoveBtn("+obj.dirLevel+", "+obj.dirNo+", "+obj.dirTopNo+")' data-no=\'"+obj.dirNo+"\'>"+
                                            "<i class='glyphicon glyphicon-minus ' style='font-size:5pt'></i>"+     
                                        "</div>"+    
                                        "<div class='dirRegisterBtn' onclick='dirRegisterBtn("+obj.dirLevel+", "+obj.dirNo+", "+obj.dirTopNo+")' >"+
                                            "<i class='glyphicon glyphicon-plus ' style='font-size:5pt'></i>"+     
                                        "</div>"+  
                                    "</div>"+  
                                "</li>";
                                
                    $(".directory ul").html(str);                     
                });//each
            }//success

        });//ajax


    //Drive 최상위 클릭   
    $("#drive").on('click',function(){
        console.log('dirve clicked..');

		$("#drive_new").on('click',function(e){
			if($("#topDir_0_0").css("display") == "block"){
				e.stopPropagation();
		    	//return false;    // 상위클릭 막기	
			};// if	
			//dirRegisterBtn(0,0,0,0);
		});
		
	
		$("#topDir_0_0").toggle(0,function(){   //ul toggle
					$(".subDir_0_0").show();    //첫번째 li show
					// console.log('subDir_0_0.show');
			});		

    }); //onclick for top_dir

}); //jq

//url변경
var changeUrl = function () {
    if (typeof (history.pushState) != "undefined") { //브라우저가 지원하는 경우

		//현재 주소를 가져온다.
		var renewURI = location.pathname;
		console.log('renewURI:', renewURI);
		//URI 변경
		renewURI = renewURI.replace(renewURI,'/main');		 
		 
		//페이지 갱신 실행!  history.pushState(state, title, renewURI);
		history.pushState(null, null, renewURI);
    }
    else {
        location.pathname = renewURI; //브라우저가 지원하지 않는 경우 페이지 이동처리
    }//if-else
}//changeUrl


//폴더클릭- show(), hide()
function dirListBtn(dirLevel, dirNo, dirTopNo, dirGroup ) {
        // console.log('dirListBtn (topDir_'+dirLevel+'_'+dirNo+') invoked.');
        $("#initSection").html("")
  
		changeUrl();
		 
		var subDirClass = ".subDir_"+(dirLevel+1)+"_"+dirNo+"";
        var topDirId = "#topDir_"+dirLevel+"_"+dirNo+"";

        $(subDirClass).toggle();//하위폴더 show, hide                              
        $(subDirClass).css("margin-left","20px");                           //하위폴더 margin-left : 20px
        $(topDirId).append($(subDirClass));         //상위폴더 마지막요소로 하위폴더 이동
		
		// console.log('dirNo', dirNo);
		sessionStorage.setItem("dirNo", dirNo);
		
		
		$("#pfolderMemo").load("/memo/list?dirNo="+dirNo);        
	    $("#file_area").load("/file/list?dirNo="+dirNo);

		showFile(dirNo);
		showMemo(dirNo);
		
        $("#trashSection").hide();
        $("#dirSection").show();
		
}//dirListBtn


//폴더명 변경
function dirModifyBtn(dirLevel, dirNo, dirTopNo ) {
    // console.log('dirModifyBtn('+dirLevel+', '+dirNo+', '+dirTopNo+')' );
	 

    //기존폴더명
    let nameNo = "#originName"+dirNo;
    nametext=$(nameNo).text();

    //input태그 생성
    let input= $("<input>");
    input.attr("type","text");
    input.attr("id","inputName");
	input.attr("class","temp_input");
    input.attr("size", "15");
    input.attr("value",nametext);
    

    //기존꺼 숨기기 및 input추가
    let cloneIcon = $(nameNo).children().clone();
    $(nameNo).text("");
    $(nameNo).append(cloneIcon);
    $(nameNo).append(input);
    $(input).focus();
/*	$(".dirRemove").hide();
	$(".dirRegisterBtn").hide();*/


    console.log(nameNo);
    console.log(nametext);

    // ESC 입력할 때
    $("#inputName").keydown(function (key) {
        if (key.keyCode == 27 || key.which == 27 ) { // keyCode 27 = ESC
            
            console.log("ESC Key Pressed.");
            $("#inputName").remove();
            $(nameNo).append(cloneIcon);
            $(nameNo).after(nametext);
    
            
        }; // if
    }); // keydwon esc Key
    


    let subDirLevel=dirLevel+1;
    $(input).keydown(function(key) {
        //키의 코드가 13번일 경우 (13번은 엔터키)
        if (key.keyCode == 13 || key.which == 13 ) {
            inputText= input.val();
            // console.log(inputText);

            if(inputText == ''){
                alert('폴더명을 입력하세요');
                $("#inputName").focus();
                return false;    
            }//if

            $("#inputName").remove();

            $.ajax({
                url:'/dir/modify',
                    method:'POST',
                    data:{
                        "dirNo": dirNo,
                        "dirName": inputText,
                        "dirTopNo": dirTopNo,
                        "dirLevel": dirLevel
                       },
                        success: function (result) {
                            // console.log(result);

                            $(nameNo).append(cloneIcon);                   //Icon
                            $(nameNo).after(result.dirName);             //이름변경
                           /* $(".dirRemove").show();
							$(".dirRegisterBtn").show();*/
    
                        }//success

            });// $.ajax
            
        }//if
    });//enter keydown
}//dirModifyBtn


//폴더삭제
function dirRemoveBtn(dirLevel, dirNo, dirTopNo) {
    // console.log('dirRemoveBtn() invoked.', dirLevel, dirNo, dirTopNo);

    $.ajax({
        url: '/dir/remove',
        type: 'POST',
        data: {dirNo:dirNo},
            success: function() {

                $("#topDir_"+dirLevel+"_"+dirNo).remove();

				$("#pfolderMemo").load("/memo/list?dirNo="+dirTopNo);        
			    $("#file_area").load("/file/list?dirNo="+dirTopNo);

                // addTrashIntoBin(dirNo);
                $(".trash-list").html("");
				getTrashList();
		
				showFile(dirTopNo);
				showMemo(dirTopNo);

            }//success                 
    });//ajax
}//dirRemoveBtn


//폴더추가
function dirRegisterBtn(dirLevel, dirNo, dirTopNo ) {
    // console.log('dirRegisterBtn('+dirLevel+','+dirNo+','+dirTopNo+') invoked.')

    if($("#newInput").length != 0 ){
         setTimeout(function () { // 포커스 버그 방지
       	 	$("#newInput").focus();   // 그룹이름 입력창 강제 포커스
        }, 0); 
        return false;
    }

	let subDirLevel=dirLevel+1;

    if(dirNo==0){
		
       // $(".subDir_0_0").show();    //Drive + 버튼
        subDirLevel = 0;
    }else{

	
		let subDir = $(".subDir_"+subDirLevel+"_"+dirNo)
		// console.log('subDir: ',subDir);
		
		
        if(subDir.css("display")!="list-item" ){                          //폴더가 닫혀있다면 show()
			dirListBtn(dirLevel, dirNo, dirTopNo);
    
        } //if
        
        
    }//if-else
    

    let newdiv=$("<div>");
	newdiv.attr("class","newDiv");


    let icon=$("<i>");
	icon.attr("class","xi-folder-o");


    let newInput = $("<input>");
    newInput.attr("type", "text");
    newInput.attr("id", "newInput");
    newInput.attr("size", "15");
    newInput.attr("data-topno", dirNo);             //상위폴더의(클릭한 폴더) dirNo
    newInput.attr("data-level", dirLevel);
    
    console.log('icon',icon);
    
    newdiv.append(icon);
    newdiv.append(newInput);


    if( dirNo == 0){
        newdiv.css("margin-left","30px");
		newdiv.css("padding-left","5px");
        icon.css("margin-right","5px");
    }else{
        newdiv.css("margin-left","20px");
		//icon.css("margin-right","5px");
    }    //if
    console.log(newInput);
    
    $("#topDir_"+dirLevel+"_"+dirNo).append(newdiv);


     // ESC 입력할 때
    $("#newInput").keydown(function (key) {
        if (key.keyCode == 27 || key.which == 27 ) { // keyCode 27 = ESC
            
            console.log("ESC Key Pressed.");
			$(newdiv).remove();
		
        
        }; // if
    }); // keydwon esc Key


    $("#newInput").keydown(function(key) {
        //키의 코드가 13번일 경우 (13번은 엔터키)
        if (key.keyCode == 13) {
            let dirName = $("#newInput").val();
            console.log(dirName);

            if(dirName == ''){
                alert('폴더명을 입력하세요');
                $("#newInput").focus();
                return false;    
            }//if
			$(newdiv).remove();
			$(icon).remove();
            $("#newInput").remove();

            $.ajax({
                url:'/dir/register',
                    method:'POST',
                    data:{
                        "dirName": dirName,
                        "dirTopNo": dirNo,
                        "dirLevel": subDirLevel },
                        success: function (result) {
                            console.log(result);
 			                showRegister(result);
                            
                        }//success

            });// $.ajax
            
        }//if
    });//enter keydown
}//dirRegisterBtn


//ajax호출하여 폴더추가 후 화면적용
function showRegister(obj){
    console.log('showRegister() invoked')
    
    let str ="";
    
    str =  "<li id='topDir_"+obj.dirLevel+"_"+obj.dirNo+"'"+ 
                "class='subDir_"+obj.dirLevel+"_"+obj.dirTopNo+"'"+
				"style='display: list-item;  margin-left: 20px;'>"+
                "<div class='dirli' data-no="+obj.dirNo+" data-topno="+obj.dirTopNo+" data-level="+obj.dirLevel+">"+
                    "<a "+  // href='/dir/get?dirNo="+obj.dirNo+"'
                        "onclick='dirListBtn("+obj.dirLevel+", "+obj.dirNo+", "+obj.dirTopNo+")'> "+
                                                                
                        "<span id='originName"+obj.dirNo+"'>"+
                            "<i class='xi-folder-o'></i>"+ 
                            obj.dirName +    
                        "</span>"+
                    "</a>"+ 
                    "<div class='dirRemove' onclick='dirRemoveBtn("+obj.dirLevel+", "+obj.dirNo+", "+obj.dirTopNo+")' data-no=\'"+obj.dirNo+"\'>"+
                        "<i class='glyphicon glyphicon-minus ' style='font-size:5pt'></i>"+     
                    "</div>"+    
                    "<div class='dirRegisterBtn' onclick='dirRegisterBtn("+obj.dirLevel+", "+obj.dirNo+", "+obj.dirTopNo+")' >"+
                        "<i class='glyphicon glyphicon-plus ' style='font-size:5pt'></i>"+     
                    "</div>"+  
                "</div>"+  
            "</li>";

    let topDirLevel = obj.dirLevel-1;
    let topDir=$("#topDir_"+topDirLevel+"_"+obj.dirTopNo);


    if(obj.dirLevel == 0){
        topDir=$("#topDir_0_0");

    }//if
    console.log('topDir:', topDir) 
	
    topDir.append(str); 
    console.log('topDir.append()')

	if(obj.dirLevel == 0){        
		$("#topDir_"+obj.dirLevel+"_"+obj.dirNo).css("margin-left","");
    }//if

    
}//showRegister