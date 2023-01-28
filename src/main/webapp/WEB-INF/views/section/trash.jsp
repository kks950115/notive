<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<div class="section_title">휴지통</div>

<script>
$(function () {

    $(document).ready(function () {
        console.log("trash start");
        
        data = { "user_id" : user_id };
        
        $.ajax({
            method: "get",
            url: "/main/trash/list",
            async: false,
            data : data,
            contentType : "application/json",
            dataType : "json",
            success:function(data){
                console.log("ajax success!");
                
    
                for(var i in data) {
                    
                    $li = $('<li class="trash-list-item">').attr("id","trash" + data[i].trash_no);
    
                    // $div1 = $("<div class='left-date-delete'>").text("D-" + Math.floor(data[i].delete_date / 86400));
                    $div1 = $("<div class='left-date-delete'>").text("D-18"); // 테스트용 하드코딩
                    $div2 = $("<div class=''>");

                    $svg = $('<svg xmlns="http://www.w3.org/2000/svg" width="60" height="60" fill="currentColor" class="" viewBox="0 0 16 16">' +
                        '<path d="M.54 3.87.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.826a2 2 0 0 1-1.991-1.819l-.637-7a1.99 1.99 0 0 1 .342-1.31zM2.19 4a1 1 0 0 0-.996 1.09l.637 7a1 1 0 0 0 .995.91h10.348a1 1 0 0 0 .995-.91l.637-7A1 1 0 0 0 13.81 4H2.19zm4.69-1.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981l.006.139C1.72 3.042 1.95 3 2.19 3h5.396l-.707-.707z"/>'
                    );
                    if(data[i].sort_code == 1){
                        $svg.attr("class", "bi bi-folder");
                    }
                    $div2.append($svg);

                    $div3 = $("<div class='origin-name'>").text(data[i].origin_name);
    
                    $li.append($div1);
                    $li.append($div2);
                    $li.append($div3);
                    $(".trash-list").append($li);
                    console.log(data[i]);
                }
        
            } // success function
        
        }); 
    });

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

        $li = $('<li data-no = "'+ obj.fileNo +'">')

        $divPreview = $('<div class="preview">')

        if(ext == "txt"){
            var $svgPreview = $('<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text preview-icon" viewBox="0 0 16 16">').html(
                '<path d="M5 4a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm0 2a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1H5z"/>' +
                '<path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z"/>'
            )
            var $svgTypeName = $('<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text" viewBox="0 0 16 16">').html(
                '<path d="M5 4a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm0 2a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1H5z"/>' +
                '<path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z"/>'
            )
        } else {
            var $svgPreview = $('<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-plus preview-icon" viewBox="0 0 16 16">').html(
                '<path d="M8 6.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V11a.5.5 0 0 1-1 0V9.5H6a.5.5 0 0 1 0-1h1.5V7a.5.5 0 0 1 .5-.5z"/>' +
                '<path d="M14 4.5V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h5.5L14 4.5zm-3 0A1.5 1.5 0 0 1 9.5 3V1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h-2z"/>'
                )
            var $svgTypeName = $('<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-plus" viewBox="0 0 16 16">').html(
                '<path d="M8 6.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V11a.5.5 0 0 1-1 0V9.5H6a.5.5 0 0 1 0-1h1.5V7a.5.5 0 0 1 .5-.5z"/>' +
                '<path d="M14 4.5V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h5.5L14 4.5zm-3 0A1.5 1.5 0 0 1 9.5 3V1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h-2z"/>'
                )
        }
        
        $divPreview.html($svgPreview)

        $divTypeName = $('<div class="file-type-name">').text(obj.fileName)
        $divTypeName.prepend($svgTypeName)

        $divWriteDate = $('<div class="writeDate">').text('등록일자 : ' +  uploadDate )	
        
        $a.append($divPreview)
        $a.append($divTypeName)
        $a.append($divWriteDate)

        $li.append($a)
    }
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

    .trash-list li {
        border: 1px solid gray;
        width: 200px;
        height: 200px;
        position: relative;
        margin: 5px;
        border-radius: 10px;
    }

    .trash-list-item div i{
        text-align: center;
        font-size: 3em;
    }

    .left-date-delete {
        position: absolute;
        line-height: 60px;
        left: 15px;
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
        
    </ul>
</div>