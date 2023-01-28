$(function () {

    console.log('memo jq started..');
	// $("#memoRegisterForm").hide();

	var dirNo;
	 
    //폴더번호 세션에서 가져오기.
	if(sessionStorage.getItem("dirNo") != null){
		dirNo = sessionStorage.getItem("dirNo");
		// console.log('dirNo', dirNo);
	}//if


 	//메모검색
    $("#search").keypress(function(e){
        console.debug("keypress for search...");

	    var search = $("#search").val();
		var resultStr = '" ' + search + ' "에 대한 검색 결과';

		if(search != "" ) {
			$("#registerBtn").hide();
			$(".search-result").show();
			
			$(".search-result").html(resultStr)
		} else {
			$("#registerBtn").show();
			$(".search-result").hide();
			
		} //if-else
		
		
		$(".memoModify").remove();

        showSearchMemo(search);
        
    }); //keypress for search


	//해당폴더의 메모목록으로
	$(document).on('click','#memoListBtn',function(){
        console.log('memoListBtn clicked..')

		$("#pfolderMemo").load("/memo/list?dirNo="+dirNo); 
		showMemo(dirNo);
	});// 
 

    //메모추가
    $(document).on('click','#memoRegisterBtn',function () {
        console.log('memoRegisterBtn clicked..')

        memoRegister(dirNo);
    });//memoRegisterBtn clicked
    
    
    // //메모 수정폼
    // $(document).on('click','#memoModify',function () {
    //     console.log('memoModifyBtn clicked..')
    //     var memoNo = $(this).data('memono');

    //     memoModifyForm(dirNo, memoNo);
 
    // });//memoModify clicked

	
    //메모수정
    // $(document).on('click','#memoModifyBtn',function () {
    //     console.log('memoModifyBtn clicked..');
 
    //     var form = $("#memoForm")[0];        
    //     console.log('form:', form);

    //     var data = new FormData(form);
    //     console.log('data: ', data);
       
    //     $.ajax({
    //         url:'/memo/modify',
    //         method:'POST',
    //         data: data,
    //         dataType: 'JSON',
    //         contentType: false,
    //         processData: false,
    //         success:function (result) {
    //             console.log('수정', result);
    //             $("#pfolderMemo").load("/memo/list?dirNo="+result.dirNo);   
    //             showMemo(result.dirNo);
    //         }//success
    //     });//ajax

    // });//memoModifyBtn clicked



    //메모삭제
    $(document).on('click', '#memoRemoveBtn',function () {
        console.log('memoRemoveBtn clicked..');
        var memoNo = $('input[name=memoNo]').val();
        memoRemove(dirNo, memoNo);
    });//memoRemoveBtn

    
});//jq


//메모추가
var memoRegister = function () {
    console.log('memoRegister invoked.')

    var form = $(".note-form").find("form")[0];        
    // var form = $("#memoForm")[0];        
    console.log('form:', form);

    var data = new FormData(form);
    console.log('data: ', data);

    $.ajax({
        url:'/memo/register',
        method: 'post',
        data: data,
        dataType: 'JSON',
        contentType: false,
        processData: false,
        success: function (result) {

            // $("#pfolderMemo").load("/memo/list?dirNo="+result.dirNo);   
            showMemo(result.dirNo); 

        }//success

    });//ajax

}//memoRegister

// 메모 수정
var memoModify = function (dirNo) {
    console.log('memoModify invoked.')

    var form = $(".note-form").find("form")[0];        
    // var form = $("#memoForm")[0];        
    console.log('form:', form);

    var data = new FormData(form);
    console.log('data: ', data);

    $.ajax({
        url:'/memo/modify',
        method: 'post',
        data: data,
        dataType: 'JSON',
        contentType: false,
        processData: false,
        success: function (result) {

            if($("#dirSection").is(":visible")){

                $("#pfolderMemo").load("/memo/list?dirNo="+result.dirNo);   
                showMemo(result.dirNo); 

            } else{

                showAllMemo();

            }

        }//success

    });//ajax

}//memoRegister

// 메모 수정 폼
var memoModifyForm = function (dirNo, memoNo) {

    console.log('memoModifyForm invoked.');


		if(sessionStorage.getItem("dirNo") != null){
			dirNo = sessionStorage.getItem("dirNo");
		}//if
	
        
        $.getJSON('/memo/modify', {'memoNo': memoNo, 'dirNo': dirNo}, function (result) {
            memoModifyBtn(result); // 폼양식 호출

            // $('input[name=memoTitle]').val(result.memoTitle);
            // $('#memoContent').val(result.memoContent);

        });//getJSON
    
}//memoModifyForm

// 메모 삭제
var memoRemove= function (dirNo, memoNo) {

    console.log('memoRemove invoked.');

        
        console.log('['+memoNo+']');

	    var form = new FormData();
        form.append("memoNo",memoNo);
        form.append("dirNo",dirNo);
		
        $.ajax({
            url:'/memo/remove',
            method:'POST',
            data:form,
            dataType:'JSON',
            contentType: false,
            processData: false,
                success: function (result) {
                    // console.log(result);   
                    if($("#dirSection").is(":visible")){

                        $("#pfolderMemo").load("/memo/list?dirNo="+dirNo);   
                        showMemo(dirNo);

                    } else{
                        showAllMemo();
                    }
                    
                },//success
                error: function (e) {
                    console.log('에러', e);   
                }
        });//ajax

}//memoRemove

// 메모등록 모달
var memoRegisterBtn = function (dirNo){
    
	console.log('registerBtn clicked..');
        
        $.confirm({
            title: '메모',
            closeIcon: true,
            content: '<div class="note-form"></div>',
            boxWidth: '700px',
            useBootstrap: false,
            bgOpacity: 0.5,
            animateFromElement: false,
            animation: "opacity",
            closeAnimation: "scale",
            animationSpeed: 400,
            onOpenBefore: function(){

                var form = '<div class="memoRegisterForm format" style="display:none;"><form>' +
		                '<input type="hidden" name="memoNo">' +
		                '<input type="hidden" name="dirNo" value="${dirNo}">제목'+
		                '<input type="text" name = "memoTitle" class="form-control" placeholder="제목">내용' +
		                '<textarea id="memoContent" class="summernote" name="editordata" cols="1" rows="1" placeholder="메모를 입력하세요">'+
                        '</textarea></form></div>';
                        
                $(".note-form").append(form);

                $('input[name=dirNo]').val(dirNo);

                $(".note-form .memoRegisterForm").removeClass("format");

                var script = document.createElement("script"); 
                script.type = "text/javascript"; 
                script.src = "/resources/js/summernote.js"; 

                $(".note-form").append(script);
                $(".note-form div").show();
                
            },    
            buttons: {
                formSubmit: {
                    text: '등록',
                    btnClass: 'btn-notive-blue',
                    action: function () {
                        // $("form").submit();
                        memoRegister();
                    }
                },
                취소:  {
                    keys: ['esc']
                }
            }
        })

}//memoRegister

// 메모수정 모달
var memoModifyBtn = function (result){

	console.log('memoModifyBtn clicked..');
        
        $.confirm({
            title: '메모',
            closeIcon: true,
            content: '<div class="note-form"></div>',
            boxWidth: '700px',
            useBootstrap: false,
            bgOpacity: 0.5,
            animateFromElement: false,
            animation: "opacity",
            closeAnimation: "scale",
            animationSpeed: 300,
            onOpenBefore: function(){

                var form = '<div class="memoRegisterForm format" style="display:none;"><form>' +
		                '<input type="hidden" name="memoNo" value="' + result.memoNo + '">' +
		                '<input type="hidden" name="dirNo" value="' + result.dirNo + '">제목'+
		                '<input type="text" name = "memoTitle" class="form-control" placeholder="제목">내용' +
		                '<textarea id="memoContent" class="summernote" name="editordata" cols="1" rows="1" placeholder="메모를 입력하세요">'+
                        '</textarea></form></div>';
                        
                $(".note-form").append(form);

                $('input[name=memoTitle]').val(result.memoTitle);
                $('#memoContent').val(result.memoContent);

                var script = document.createElement("script"); 
                script.type = "text/javascript"; 
                script.src = "/resources/js/summernote.js"; 
                $(".note-form").append(script);
                $(".note-form div").show();
                
            },    
            buttons: {
                formSubmit: {
                    text: '수정',
                    btnClass: 'btn-notive-blue',
                    action: function () {
                        // $("form").submit();
                            memoModify();
                    }
                },
                취소:  {
                    keys: ['esc']
                }
            }
        })

}//memoRegister

// 메모 리스트
function showMemo(dirNo) {
    console.log('show memo invoked.');

    $.getJSON('/memo/getlist',{'dirNo': dirNo}, function (result) {

        var str = "";

        $(result).each(function (i, obj) {
		
		
		var updateDate = (obj.memoUpdateDate == null) ? obj.memoWriteDate : obj.memoUpdateDate; 
	
        str+= "<a id='memoModify'>"+
                    "<ul class='memo'  data-memono='"+obj.memoNo+"' data-dirno='"+obj.dirNo+"' data-memotitle='"+obj.memoTitle+"'>"+
                    "<li class='memoContent"+obj.memoNo+"' onclick='openMemo("+obj.dirNo+","+obj.memoNo+",&apos;"+obj.memoTitle+"&apos;)'>"+obj.memoContent+"</li>"+
                    "<li class='memoTitle'>"+
'<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text-fill" viewBox="0 0 16 16">'+
'<path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5 4h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm0 2h3a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1z"/></svg>'+
                    obj.memoTitle+"</li>"+
                    "<li class='memoWriteDate'>"+ "최종 수정일 : " +  updateDate +"</li>"+
                    "<li class='memoUpdateDate'>"+ "" +"</li>"+                        
                    "</ul>"+
                "</a>";

            $("#memoList").html(str);
        }); // each

    }); //getJSON
}//showMemo

// 첫화면 메모 목록
function showAllMemo() {

    $.getJSON('/memo/getlistAll', function (result) {

        var str = "";

        $(result).each(function (i, obj) {

        if(i == 10) {
            return;
        }
		
		var updateDate = (obj.memoUpdateDate == null) ? obj.memoWriteDate : obj.memoUpdateDate; 
	
        str+= "<a id='memoModify'>"+
                    "<ul class='memo'  data-memono='"+obj.memoNo+"' data-dirno='"+obj.dirNo+"' data-memotitle='"+obj.memoTitle+"'>"+
                    "<li class='memoContent"+obj.memoNo+"' onclick='openMemo("+obj.dirNo+","+obj.memoNo+",&apos;"+obj.memoTitle+"&apos;)'>"+obj.memoContent+"</li>"+
                    "<li class='memoTitle'>"+
'<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text-fill" viewBox="0 0 16 16">'+
'<path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5 4h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm0 2h3a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1z"/></svg>'+
                    obj.memoTitle+"</li>"+
                    "<li class='memoWriteDate'>"+ "최종 수정일 : " +  updateDate +"</li>"+
                    "<li class='memoUpdateDate'>"+ "" +"</li>"+                        
                    "</ul>"+
                "</a>";

            $("#memoList").html(str);
        }); // each

    }); //getJSON
}//showMemo

// 메모 검색
function showSearchMemo(search){
    
    console.debug("showSearchMemo invoked.");
    console.log("search : ", search);
//	console.log("obj : ", obj);

    $.getJSON('/memo/getSearchList',{'search': search}, function (result) {
        console.log("result: " , result);
        console.log("result.length: " , result.length);

		//if(result.length = 0){
		//	$("#memoList").html("검색 결과가 없습니다.");
		//} //if : 검색결과가 없다면

        var str = "";

        $(result).each(function (i, obj) {
		 
		var updateDate = (obj.memoUpdateDate == null) ? obj.memoWriteDate : obj.memoUpdateDate; 
	 	
		console.log("obj : " , obj);
		
        str+= "<a class='memoModify'>"+
                    "<ul class='memo'  data-memono='"+obj.memoNo+"' data-dirno='"+obj.dirNo+" + ' data-memotitle='"+obj.memoTitle+"'>"+
                    "<li class='memoContent"+obj.memoNo+"'>"+obj.memoContent+"</li>"+
                    "<li class='memoTitle'>"+
'<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text-fill" viewBox="0 0 16 16">'+
'<path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5 4h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm0 2h3a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1z"/></svg>'+
                    obj.memoTitle+"</li>"+
                    "<li class='memoWriteDate'>"+ "최종 수정일 : " +  updateDate +"</li>"+
                    "<li class='memoUpdateDate'>"+ "" +"</li>"+                        
                    "</ul>"+
                "</a>";
 
            $("#memoList").html(str);
        }); // each

    }); //getJSON
}//showSearchMemo

// 메모 열기 모달
function openMemo(dirNo, memoNo, memoTitle) {
    $.confirm({
        title: memoTitle,
        closeIcon: true,
        content: '<div class="note-form"></div>',
        boxWidth: '700px',
        useBootstrap: false,
        bgOpacity: 0.5,
        animateFromElement: false,
        animation: "zoom",
        closeAnimation: "opacity",
        animationSpeed: 300,
        // scrollToPreviousElement: false,
        // scrollToPreviousElementAnimate: false,
        onOpenBefore: function(){

            $p = $($('.memoContent' + memoNo).html());

            $div = $('<div class="memoForm">');
            $input1 = $('<input type="hidden" name="memoNo">').attr('value', memoNo);
            $input2 = $('<input type="hidden" name="dirNo">').attr('value', dirNo);
            $input3 = $('<div name ="memoTitle">').val(memoTitle);
            // $input3 = $('<input type="text" name = "memoTitle" class="form-control" placeholder="제목">');
            $textarea = $('<div id="memoContent" class="" name="" cols="1" rows="1">').append($p);

            $div.append($input1);
            $div.append($input2);
            $div.append($input3);
            $div.append($textarea);



            // var form = '<div class="memoRegisterForm format" style="display:none;"><form>' +
            //         '<input type="hidden" name="memoNo" value="' + memoNo + '">' +
            //         '<input type="hidden" name="dirNo" value="' + dirNo + '">제목' +
            //         '<input type="text" name = "memoTitle" class="form-control" placeholder="제목">내용' +
            //         '<textarea id="memoContent" class="summernote" name="editordata" cols="1" rows="1" placeholder="메모를 입력하세요">'+
            //         '</textarea></form></div>';
                    
            $(".note-form").append($div);
        },
        buttons: {
            formSubmit: {
                text: '수정',
                btnClass: 'btn-notive-blue',
                action: function () {
                    // $("form").submit();
                    // this.close();
                    setTimeout(function () {
                        memoModifyForm(dirNo, memoNo);
                    }, 300)
                }
            },
            닫기:  {
                keys: ['esc']
            }
        }
    })
}