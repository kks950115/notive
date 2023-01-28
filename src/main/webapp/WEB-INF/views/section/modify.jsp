<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
 
 
    <script>

        $(function () {
            console.clear();
            console.log('jq started..');

            $("#listBtn").on('click',function () {
                
            	let dirNo=$(this).attr('value');
            	console.log('dirNo: ',dirNo);
            	
                location.href="/dir/get?dirNo="+dirNo;
               
            });//list btn clicked..
        });//jq

    </script>
 
    <div class="section_title">Memo</div>
	
    <form action="/memo/modify" method="post">
        <input type="hidden" name="memoNo" value="${__MEMO__.memoNo}">
        <input type="hidden" name="dirNo" value="${__MEMO__.dirNo}">
      	<input type="hidden" name="dirNo" value="${__MEMO__.userId}">
        제목
        <input type="text" name = "memoTitle" class="form-control" value="${__MEMO__.memoTitle}"> 
        내용
        <textarea class="summernote" name="editordata" cols="1" rows="1">
        	${__MEMO__.memoContent}
        </textarea><br>
        
        <input type="submit" value="변경">
        <button type="button" onclick="memoRemove(${__MEMO__.dirNo}, ${__MEMO__.memoNo}, ${__MEMO__.userId})" >삭제</button>
        <button type="button" id="listBtn" value="${__MEMO__.dirNo}">목록으로</button>
        
        
    </form>
    
    <script>
    $(function(){
    	
    	        
    		        
        var toolbar = [
                // 글꼴 설정
                ['fontname', ['fontname']],
                // 글자 크기 설정
                ['fontsize', ['fontsize']],
                // 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
                ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
                // 글자색
                ['color', [/* 'forecolor', */'color']],
                // 줄간격
                ['height', ['height']],
                // 그림첨부, 링크만들기, 동영상첨부
                ['insert',['picture']],
              
        ];

        
       
        
        $(".summernote").summernote({
          height: 300,                      // 에디터 높이
		  minHeight: null,                  // 최소 높이
		  maxHeight: null,                  // 최대 높이
		  focus: true,                      // 에디터 로딩후 포커스를 맞출지 여부
		  lang: "ko-KR",					// 한글 설정
          placeholder: "최대 2048자 가능.",	//placeholder 설정
          toolbar : toolbar,                //툴바설정
          // 추가한 글꼴
		  fontNames: ['맑은 고딕','굴림체','굴림','돋음체','바탕체','궁서','Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', 'sans-serif'],
          fontNamesIgnoreCheck: ['맑은 고딕','굴림체','굴림','돋음체','바탕체','궁서', 'Arial', 'Arial Black', 'Comic Sans MS', 'Courier New', 'sans-serif'],
         
          codemirror: { // codemirror options
          	theme: 'monokai'
          },
          callbacks: {                      //이미지 첨부
              onImageUpload: function (files, editor, welEditable) {
                  for(var i=files.length -1; i>=0; i--){
                      uploadSummernoteImageFile(files[i],this)
                  }//for
              }//onImageUpload
          }//callbacks
        });//summernote
        $('.summernote').summernote('fontName','맑은 고딕');
        // $('p').css('margin-bottom','0')
        // $('.note-resizebar').css('display','none');
	 //내용에는 기본css적용x > 무효화
	 //$("input").not("input.summernote").css("*","*");
       

    });//jq
       
	</script>
 