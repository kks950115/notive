<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div id="initSection">

	<!-- 메모영역 -->
	<div class="section_title">
		최근 메모
		<!-- 검색결과 -->
		<div class="search-result" style="font-size:initial">
		</div>	
	</div>
	<div id="pfolderMemo"  class="pfolderContent">
		<div id="memo_area" >
		
			<div id="memoList">
			<!-- 동적추가 -->
			</div>
		</div>
	</div>         
		
	<!-- 파일영역     -->
	<div class="section_title">
		모든 파일
		<!-- 검색결과 -->
		<div class="search-result" style="font-size:initial">
		</div>	
	</div>
	<div id="file_area" class="pfolderContent" >
		<div class="fileDrop" data-dirno="${dirNo}">
	    
			<div class="uploadDiv">
				<input type="hidden" name="user_id" value="${user_id}" >
				<input type="hidden" name="dirNo" id="inputDirNo" value="${dirNo}" >
				<input type="file" class="inputFile" name="uploadFile" style="display:none;"multiple>
			
			</div>
		
			<div class="uploadResult">
			
				<ul>
				<!-- 동적추가 -->
				</ul>
			</div>
		</div> 
	</div>
</div>
<div id="dirSection" style="display: none;">

	<!-- 메모영역 -->
	<div class="section_title">
		메모
		<!-- 검색결과 -->
		<div class="search-result" style="font-size:initial">
		</div>	
	</div>
	<div id="pfolderMemo"  class="pfolderContent">
		
	</div>         
		
	<!-- 파일영역     -->
	<div class="section_title">
		파일
		<!-- 검색결과 -->
		<div class="search-result" style="font-size:initial">
		</div>	
	</div>
	<div id="file_area" class="pfolderContent" >
	
	</div>
</div>

<div id="trashSection" style="display: none;">
	<div class="section_title" >휴지통</div>

	<script>
		// 휴지통에 추가될 때 동적 디자인 추가
		function InsertIntoBin(data) {
			$li = $('<li class="trash-list-item">').attr({"id":"trash" + data.trash_no, "value":data.trash_no});

			// 확장자 추출
			var ext = data.origin_name.split('.').pop().toLowerCase();

			var now = new Date();
			// 남은 일 수
			var dday = 30 - (Math.floor((now.getTime() - data.delete_date) / (1000*60*60*24)));

			$div1 = $("<div class='left-date-delete' style='color:rgb("+ Math.round((30-dday) * 8) + ", 0, 0)'>").text("D-" + dday);	// 색상 동적 변경
			$div2 = $("<div class='preview-trash'>");
			$div3 = $("<div class='trash-name'>").text(data.origin_name);

			if(data.sort_code == 1) {

				var $svgPreview = icon.folder.preview;
				var $svgTypeName = icon.folder.mini;
				
			} else if(data.sort_code == 2) {

				var imgs = ['gif', 'jpg', 'jpeg', 'png', 'bmp' ,'ico', 'apng'];

				if(ext == "txt"){
					var $svgPreview = icon.txt.preview;
					var $svgTypeName = icon.txt.mini;
				} else if(imgs.includes(ext)){
					var $svgPreview = icon.img.preview;
					var $svgTypeName = icon.img.mini;
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

			} else {

				var $svgPreview = icon.memo.preview;
				var $svgTypeName = icon.memo.mini;

			}

			$div2.html($svgPreview);
			$div3.prepend($svgTypeName);

			$li.append($div1);
			$li.append($div2);
			$li.append($div3);
			$(".trash-list").append($li);

		}

		// 휴지통 열려있을 때 폴더 지울 때 -> 실시간으로 추가
		function addTrashIntoBin(dirNo) {
				
				data = { "user_id" : user_id, "dirNo" : dirNo };
				
				$.ajax({
					method: "get",
					url: "/main/trash/addTrashIntoBin",
					// async: false,
					data : data,
					contentType : "application/json",
					dataType : "json",
					success:function(data){

						InsertIntoBin(data);

					} // success function
				
				}); // ajax get /chatRoomList
		}
		
		// 리스트 불러오기
		function getTrashList() {
				// console.log("리스트 실행!!!");
				
				data = { "user_id" : user_id };
				
				$.ajax({
					method: "get",
					url: "/main/trash/list",
					// async: false,
					data : data,
					contentType : "application/json",
					dataType : "json",
					success:function(data){
						// console.log("ajax success!");
			
						for(var i in data) {
							InsertIntoBin(data[i]);
						}
				
					} // success function
				
				}); // ajax get /chatRoomList
		}

		// 선택 삭제
		function deleteTrash() {
			console.log("선택삭제 실행!!");

			var listSelectedTrash = [];

			$(".selectedTrash").each(function (i) {
				listSelectedTrash.push($(this).val());
			});
			console.log(listSelectedTrash);

			data = { 
						"selectedTrash" : listSelectedTrash,
						"user_id" : user_id
					};
			
			$.post(
				"/main/trash/remove", 
				data,
				function(result){
					$(".selectedTrash").remove();
				}
			); // ajax get /chatRoomList
		}

		// 전체 삭제
		function deleteAllTrash() {
			console.log("전체삭제 실행!!");
			data = { "user_id" : user_id };
			
			$.post(
				"/main/trash/removeAll", 
				data,
				function(result){
					$(".trash-list").html("");
				}
			); // ajax get /removeAll
		}

		// 선택 복원
		function restoreTrash() {
			console.log("선택복원 실행!!");

			var listSelectedTrash = [];

			$(".selectedTrash").each(function (i) {
				listSelectedTrash.push($(this).val());
			});

			data = { 
						"selectedTrash" : listSelectedTrash,
						"user_id" : user_id
					};
			
			$.post(
				"/main/trash/restore", 
				data,
				function(result){
					$(".selectedTrash").remove();

					for(var i in result) {
						showRegister(result[i]);
					};
				},
				"json"
			); // ajax get /chatRoomList

			getAllFileSize(function (storage) {
            updateProgress(storage);
        })
		}

		// 전체 복원
		function restoreAllTrash() {
			console.log("전체복원 실행!!");
			data = { "user_id" : user_id };
			
			$.post(
				"/main/trash/restoreAll", 
				data,
				function(result){
					$(".trash-list").html("");

					for(var i in result) {
						showRegister(result[i]);
					};
				},
				"json"
			); // ajax get /chatRoomList

			getAllFileSize(function (storage) {
            updateProgress(storage);
        })
		}

		// JQuery
		$(function () {

			// 시작시 휴지통 리스트 불러오기
			$(document).ready(function () {
				$(".trash-list").html("");
				getTrashList();
			});

			// 휴지통에서 클릭으로 선택시
			$(document).on('click', '.trash-list-item', function () {
				$(this).toggleClass('selectedTrash');
			});

		});

	</script>

	<style>
		.trash-header {
			display: flex;
			flex-direction: row;
			justify-content: flex-end
		}
		
		.trash-header-button {
			border: 1px solid #464646;
			background-color: white;
			border-radius: 10px;
			margin: 5px;
			color: #464646;
			transition: all 0.1s;
		}
		.trash-header-button:hover {
			background-color: #464646;
			color: white;
		}
	
		.trash-list {
			display: flex;
			flex-wrap: wrap;
		}

		.trash-list-item {
			width: 200px;
    		height: 200px;
		
			border: 1px solid #c7c7c7;
			border-radius: 5px;
		
			float: left;
		
			display: flex;
			flex-direction: column;
			/* justify-content: center; */

			position: relative;
		
			padding-top: 3px;
			margin: 10px 10px;
		}
	
		.trash-list-item div i{
			text-align: center;
			font-size: 3em;
		}
	
		.left-date-delete {
			position: absolute;
			font-weight: bold;
			left: 8px;
			cursor: default;
		}

		.preview-trash {
			height: 80%;

			text-align: center;
			line-height: 14.6;
		}

		.trash-name {
			border-top: solid 1px #c7c7c7;
			padding-left: 10px;
			padding-right: 10px;
			font-weight: bold;
			line-height: 40.27px;

			overflow: hidden;
			text-overflow: ellipsis;
			white-space: nowrap;
		}

		.trash-name .bi {
			margin-right: 10px;
    		vertical-align: -.125em;
		}
		
		.selectedTrash .trash-name {
			background-color: #e1f5fe;
		}
		
	</style>

	<div class="trash-header">
		<div class="">
			<button class="trash-header-button" onclick="restoreAllTrash();">전체복원</button>
		</div>
		<div class="">
			<button class="trash-header-button" onclick="deleteAllTrash();">전체삭제</button>
		</div>
		<div class="">
			<button class="trash-header-button" onclick="restoreTrash();">복원</button>
		</div>
		<div class="">
			<button class="trash-header-button" onclick="deleteTrash();">삭제</button>
		</div>
	
	</div>
	<div class="trash-area">
		<ul class="trash-list">
			<!-- 동적 추가 -->
			
		<!-- <li class="trash-list-item" id="trash115"><div class="left-date-delete">D-18</div><div class="origin-name">asdf</div></li></ul> -->
	</div>

</div>

<!--  메모 모달 -->
<!-- <div class="memoRegisterForm format" style="display:none;">
	<form>
		<input type="hidden" name="memoNo">
		<input type="hidden" name="dirNo" value="${dirNo}">
		제목
		<input type="text" name = "memoTitle" class="form-control" placeholder="제목"> 
		내용
		<textarea id="memoContent" class="summernote" name="editordata" cols="1" rows="1" placeholder="메모를 입력하세요">
		</textarea>
	</form>
</div> -->