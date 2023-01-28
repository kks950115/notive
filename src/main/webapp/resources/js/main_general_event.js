// 메인페이지 이벤트 관리 스크립트
// Hover, Click, Fold ... 

// 프로필 이미지 ajax 처리 위한 전역변수 선언
var result;
var uuid;
var fileName;
var uploadPath;

// 폴더, 파일, 메모 아이콘 객체
// preview = 미리보기 큰 아이콘
// mini 일므 옆의 작은 아이콘
var icon = {

    "memo" : {
        "preview" : 
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text-fill preview-icon" viewBox="0 0 16 16">' +
            '<path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5 4h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm0 2h3a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1z"/></svg>'
            ,
        "mini" : 
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text-fill" viewBox="0 0 16 16">' +
            '<path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM5 4h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1zm0 2h3a.5.5 0 0 1 0 1H5a.5.5 0 0 1 0-1z"/></svg>'
            
    },
    "folder" : {
        "preview" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-folder-fill preview-icon" viewBox="0 0 16 16">' +
            '<path d="M9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.825a2 2 0 0 1-1.991-1.819l-.637-7a1.99 1.99 0 0 1 .342-1.31L.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3zm-8.322.12C1.72 3.042 1.95 3 2.19 3h5.396l-.707-.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981l.006.139z"/></svg>'
            ,
        "mini" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-folder-fill" viewBox="0 0 16 16">' +
            '<path d="M9.828 3h3.982a2 2 0 0 1 1.992 2.181l-.637 7A2 2 0 0 1 13.174 14H2.825a2 2 0 0 1-1.991-1.819l-.637-7a1.99 1.99 0 0 1 .342-1.31L.5 3a2 2 0 0 1 2-2h3.672a2 2 0 0 1 1.414.586l.828.828A2 2 0 0 0 9.828 3zm-8.322.12C1.72 3.042 1.95 3 2.19 3h5.396l-.707-.707A1 1 0 0 0 6.172 2H2.5a1 1 0 0 0-1 .981l.006.139z"/></svg>'
            ,
    },
    "img" : {
        "preview" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-image preview-icon" viewBox="0 0 16 16">' +
            '<path d="M8.002 5.5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0z"/>' +
            '<path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM3 2a1 1 0 0 1 1-1h8a1 1 0 0 1 1 1v8l-2.083-2.083a.5.5 0 0 0-.76.063L8 11 5.835 9.7a.5.5 0 0 0-.611.076L3 12V2z"/>'
            ,
        "mini" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-image" viewBox="0 0 16 16">' +
            '<path d="M8.002 5.5a1.5 1.5 0 1 1-3 0 1.5 1.5 0 0 1 3 0z"/>' +
            '<path d="M12 0H4a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2zM3 2a1 1 0 0 1 1-1h8a1 1 0 0 1 1 1v8l-2.083-2.083a.5.5 0 0 0-.76.063L8 11 5.835 9.7a.5.5 0 0 0-.611.076L3 12V2z"/>'
            
    },

    // 그 외의 파일 확장자들
    "txt" : {
        "preview" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text preview-icon" viewBox="0 0 16 16">' +
                '<path d="M5 4a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm0 2a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1H5z"/>' +
                '<path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z"/>'
            ,
        "mini" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-text" viewBox="0 0 16 16">' +
            '<path d="M5 4a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm-.5 2.5A.5.5 0 0 1 5 6h6a.5.5 0 0 1 0 1H5a.5.5 0 0 1-.5-.5zM5 8a.5.5 0 0 0 0 1h6a.5.5 0 0 0 0-1H5zm0 2a.5.5 0 0 0 0 1h3a.5.5 0 0 0 0-1H5z"/>' +
            '<path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z"/>'
    },
    
    "pptx" : {
        "preview" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-ppt preview-icon" viewBox="0 0 16 16">' +
            '<path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z"/>' +
            '<path d="M6 5a1 1 0 0 1 1-1h1.188a2.75 2.75 0 0 1 0 5.5H7v2a.5.5 0 0 1-1 0V5zm1 3.5h1.188a1.75 1.75 0 1 0 0-3.5H7v3.5z"/></svg>'
            ,

        "mini" :
        '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-ppt" viewBox="0 0 16 16">' +
        '<path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm10-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1z"/>' +
        '<path d="M6 5a1 1 0 0 1 1-1h1.188a2.75 2.75 0 0 1 0 5.5H7v2a.5.5 0 0 1-1 0V5zm1 3.5h1.188a1.75 1.75 0 1 0 0-3.5H7v3.5z"/></svg>'
    },

    "pdf" : {
        "preview" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-pdf preview-icon" viewBox="0 0 16 16">' +
                '<path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>' +
                '<path d="M4.603 12.087a.81.81 0 0 1-.438-.42c-.195-.388-.13-.776.08-1.102.198-.307.526-.568.897-.787a7.68 7.68 0 0 1 1.482-.645 19.701 19.701 0 0 0 1.062-2.227 7.269 7.269 0 0 1-.' +
                '43-1.295c-.086-.4-.119-.796-.046-1.136.075-.354.274-.672.65-.823.192-.077.4-.12.602-.077a.7.7 0 0 1 .477.365c.088.164.12.356.127.538.007.187-.012.395-.047.614-.084.51-.27 1.134-.52 1.794a10.954 ' +
                '10.954 0 0 0 .98 1.686 5.753 5.753 0 0 1 1.334.05c.364.065.734.195.96.465.12.144.193.32.2.518.007.192-.047.382-.138.563a1.04 1.04 0 0 1-.354.416.856.856 0 0 1-.51.138c-.331-.014-.654-.196-.933-.' +
                '417a5.716 5.716 0 0 1-.911-.95 11.642 11.642 0 0 0-1.997.406 11.311 11.311 0 0 1-1.021 1.51c-.29.35-.608.655-.926.787a.793.793 0 0 1-.58.029zm1.379-1.901c-.166.076-.32.156-.459.238-.328.194-.' +
                '541.383-.647.547-.094.145-.096.25-.04.361.01.022.02.036.026.044a.27.27 0 0 0 .035-.012c.137-.056.355-.235.635-.572a8.18 8.18 0 0 0 .45-.606zm1.64-1.33a12.647 12.647 0 0 1 1.01-.' +
                '193 11.666 11.666 0 0 1-.51-.858 20.741 20.741 0 0 1-.5 1.05zm2.446.45c.15.162.296.3.435.41.24.19.407.253.498.256a.107.107 0 0 0 .07-.015.307.307 0 0 0 .094-.125.436.436 0 0 0 .059-.' +
                '2.095.095 0 0 0-.026-.063c-.052-.062-.2-.152-.518-.209a3.881 3.881 0 0 0-.612-.053zM8.078 5.8a6.7 6.7 0 0 0 .2-.828c.031-.188.043-.343.038-.465a.613.613 0 0 0-.032-.198.517.517 0 0 0-.145.04c-.' +
                '087.035-.158.106-.196.283-.04.192-.03.469.046.822.024.111.054.227.09.346z"/>'
            ,
        "mini" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-pdf" viewBox="0 0 16 16">' +
            '<path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>' +
            '<path d="M4.603 12.087a.81.81 0 0 1-.438-.42c-.195-.388-.13-.776.08-1.102.198-.307.526-.568.897-.787a7.68 7.68 0 0 1 1.482-.645 19.701 19.701 0 0 0 1.062-2.227 7.269 7.269 0 0 1-.' +
            '43-1.295c-.086-.4-.119-.796-.046-1.136.075-.354.274-.672.65-.823.192-.077.4-.12.602-.077a.7.7 0 0 1 .477.365c.088.164.12.356.127.538.007.187-.012.395-.047.614-.084.51-.27 1.134-.52 1.794a10.954 ' +
            '10.954 0 0 0 .98 1.686 5.753 5.753 0 0 1 1.334.05c.364.065.734.195.96.465.12.144.193.32.2.518.007.192-.047.382-.138.563a1.04 1.04 0 0 1-.354.416.856.856 0 0 1-.51.138c-.331-.014-.654-.196-.933-.' +
            '417a5.716 5.716 0 0 1-.911-.95 11.642 11.642 0 0 0-1.997.406 11.311 11.311 0 0 1-1.021 1.51c-.29.35-.608.655-.926.787a.793.793 0 0 1-.58.029zm1.379-1.901c-.166.076-.32.156-.459.238-.328.194-.' +
            '541.383-.647.547-.094.145-.096.25-.04.361.01.022.02.036.026.044a.27.27 0 0 0 .035-.012c.137-.056.355-.235.635-.572a8.18 8.18 0 0 0 .45-.606zm1.64-1.33a12.647 12.647 0 0 1 1.01-.' +
            '193 11.666 11.666 0 0 1-.51-.858 20.741 20.741 0 0 1-.5 1.05zm2.446.45c.15.162.296.3.435.41.24.19.407.253.498.256a.107.107 0 0 0 .07-.015.307.307 0 0 0 .094-.125.436.436 0 0 0 .059-.' +
            '2.095.095 0 0 0-.026-.063c-.052-.062-.2-.152-.518-.209a3.881 3.881 0 0 0-.612-.053zM8.078 5.8a6.7 6.7 0 0 0 .2-.828c.031-.188.043-.343.038-.465a.613.613 0 0 0-.032-.198.517.517 0 0 0-.145.04c-.' +
            '087.035-.158.106-.196.283-.04.192-.03.469.046.822.024.111.054.227.09.346z"/>'
    },

    "word" : {
        "preview" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-word preview-icon" viewBox="0 0 16 16">' +
                '<path d="M4.879 4.515a.5.5 0 0 1 .606.364l1.036 4.144.997-3.655a.5.5 0 0 1 .964 0l.997 3.655 1.036-4.144a.5.5 0 0 1 .97.242l-1.5 6a.5.5 0 0 1-.967.01L8 7.402l-1.018 3.73a.5.5 0 0 1-.967-.01l-1.5-6a.5.5 0 0 1 .364-.606z"/>' +
                '<path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>'
            ,
        "mini" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-word" viewBox="0 0 16 16">' +
            '<path d="M4.879 4.515a.5.5 0 0 1 .606.364l1.036 4.144.997-3.655a.5.5 0 0 1 .964 0l.997 3.655 1.036-4.144a.5.5 0 0 1 .97.242l-1.5 6a.5.5 0 0 1-.967.01L8 7.402l-1.018 3.73a.5.5 0 0 1-.967-.01l-1.5-6a.5.5 0 0 1 .364-.606z"/>' +
            '<path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>'
    },

    "xlsx" : {
        "preview" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-excel preview-icon" viewBox="0 0 16 16">' +
                '<path d="M5.18 4.616a.5.5 0 0 1 .704.064L8 7.219l2.116-2.54a.5.5 0 1 1 .768.641L8.651 8l2.233 2.68a.5.5 0 0 1-.768.64L8 8.781l-2.116 2.54a.5.5 0 0 1-.768-.641L7.349 8 5.116 5.32a.5.5 0 0 1 .064-.704z"/>' +
                '<path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>'
            ,
        "mini" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-excel" viewBox="0 0 16 16">' +
            '<path d="M5.18 4.616a.5.5 0 0 1 .704.064L8 7.219l2.116-2.54a.5.5 0 1 1 .768.641L8.651 8l2.233 2.68a.5.5 0 0 1-.768.64L8 8.781l-2.116 2.54a.5.5 0 0 1-.768-.641L7.349 8 5.116 5.32a.5.5 0 0 1 .064-.704z"/>' +
            '<path d="M4 0a2 2 0 0 0-2 2v12a2 2 0 0 0 2 2h8a2 2 0 0 0 2-2V2a2 2 0 0 0-2-2H4zm0 1h8a1 1 0 0 1 1 1v12a1 1 0 0 1-1 1H4a1 1 0 0 1-1-1V2a1 1 0 0 1 1-1z"/>'
    },

    "zip" : {
        "preview" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-zip preview-icon" viewBox="0 0 16 16">' +
                '<path d="M6.5 7.5a1 1 0 0 1 1-1h1a1 1 0 0 1 1 1v.938l.4 1.599a1 1 0 0 1-.416 1.074l-.93.62a1 1 0 0 1-1.109 0l-.93-.62a1 1 0 0 1-.415-1.074l.4-1.599V7.5zm2 0h-1v.938a1 1 0 0 1-.03.243l-.4 1.598.93.62.93-.62-.4-1.598a1 1 0 0 1-.03-.243V7.5z"/>' +
                '<path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm5.5-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H9v1H8v1h1v1H8v1h1v1H7.5V5h-1V4h1V3h-1V2h1V1z"/>'
            ,
        "mini" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-zip" viewBox="0 0 16 16">' +
            '<path d="M6.5 7.5a1 1 0 0 1 1-1h1a1 1 0 0 1 1 1v.938l.4 1.599a1 1 0 0 1-.416 1.074l-.93.62a1 1 0 0 1-1.109 0l-.93-.62a1 1 0 0 1-.415-1.074l.4-1.599V7.5zm2 0h-1v.938a1 1 0 0 1-.03.243l-.4 1.598.93.62.93-.62-.4-1.598a1 1 0 0 1-.03-.243V7.5z"/>' +
            '<path d="M2 2a2 2 0 0 1 2-2h8a2 2 0 0 1 2 2v12a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2zm5.5-1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V2a1 1 0 0 0-1-1H9v1H8v1h1v1H8v1h1v1H7.5V5h-1V4h1V3h-1V2h1V1z"/>'
    },

    // 기타 확장자
    "etc" : {
        "preview" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-plus preview-icon" viewBox="0 0 16 16">' +
            '<path d="M8 6.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V11a.5.5 0 0 1-1 0V9.5H6a.5.5 0 0 1 0-1h1.5V7a.5.5 0 0 1 .5-.5z"/>' +
            '<path d="M14 4.5V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h5.5L14 4.5zm-3 0A1.5 1.5 0 0 1 9.5 3V1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h-2z"/>'
            ,
        "mini" :
            '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" class="bi bi-file-earmark-plus" viewBox="0 0 16 16">' +
            '<path d="M8 6.5a.5.5 0 0 1 .5.5v1.5H10a.5.5 0 0 1 0 1H8.5V11a.5.5 0 0 1-1 0V9.5H6a.5.5 0 0 1 0-1h1.5V7a.5.5 0 0 1 .5-.5z"/>' +
            '<path d="M14 4.5V14a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V2a2 2 0 0 1 2-2h5.5L14 4.5zm-3 0A1.5 1.5 0 0 1 9.5 3V1H4a1 1 0 0 0-1 1v12a1 1 0 0 0 1 1h8a1 1 0 0 0 1-1V4.5h-2z"/>'
    }
}


// 드롭다운 사용하기
var dropdownElementList = [].slice.call(document.querySelectorAll('.dropdown-toggle'))
var dropdownList = dropdownElementList.map(function (dropdownToggleEl) {
    return new bootstrap.Dropdown(dropdownToggleEl)
})


$(function () {
    console.log("main_general_event.js invoked.");

    // 첫화면 section 로드
    $(document).ready(function () {
        console.log("main init load.");
        showAllMemo();
        showAllFile();
    }); // document ready functino

        //---------------------------------------------------//
    // Function List
    // (1)  accept          그룹초대 수락    
    // (2)  refuse          그룹초대 거절
    //---------------------------------------------------//


    // 그룹 초대 수락 함수
    var accept = function () {
        console.log("accept invoked.");
        var selected = $(this).parent("div");
        user_group_name = selected.prevAll("input[name=group_name]").val();

        var data = { 
            "group_no" : selected.prevAll("input[name=group_no]").val(),
            "user_id" : selected.prevAll("input[name=user_id]").val(),
            "user_group_name" : selected.prevAll("input[name=group_name]").val()
        };

        $.post('invitation/accept', data, function () {
            selected.parent().parent("li").remove();
            $("#accept").on('click', accept);
        });
    }

    // 그룹 초대 거절 함수
    var refuse = function () {
        console.log("refuse invoked.");
        var selected = $(this).parent("div");
        var group_name = selected.prevAll("input[name=group_name]").val();
        var data = { group_name, group_name};

        $.post('invitation/refuse', data, function () {
            selected.parent().parent("li").remove();
            $("#refuse").on('click', refuse);
        });
    }

    var activeClicked = function () {
        $(".dirli").removeClass('active');
        $(".groupli").removeClass('active');
        $("#bin").removeClass('active');
        $(".aside-left").removeClass('fold');
        
        $(this).toggleClass('active');
    }

    // 이전 페이지에서 펼친 메뉴 유지
    // if(sessionStorage.getItem("menu") != null){
    //     console.log("menu open");
    //     $('.' + sessionStorage.getItem("menu")).css("display","block");
    //     sessionStorage.clear();
    // };

    // a태그의 #링크 무력화
    $('a[href="#"]').click(function(e) {
        e.preventDefault();
    }); // a tag clicked

    // 모든 폴더종류 클릭시 
    $(document).on('click', ".groupli", activeClicked); // ("aside ul li").on('click', function () {}
    $(document).on('click', ".dirli", activeClicked); // ("aside ul li").on('click', function () {}

    // Drive 클릭
    $("#drive").click( function () {
        $(".folder").toggle();

        // 사이드바 접힌 상태에서 Drive 선택시
        // --1. 접힌 사이드바 열기
        // --2. Group 접기
        // --3. Drive 펼치기
        if($(".aside-left").hasClass("fold")){

            $(".group").hide();     // Group 접기
            $(".folder").show();    // Drive 펼치기

            $(".aside-left").toggleClass('fold'); // 사이드바 펼치기
            $(".aside_fold i").toggleClass("bi-caret-right bi-three-dots-vertical");
        }   // if
        
    }); // ("#drive").on('click', function () {}

    // Group 클릭
    $("#group").click( function () {
        $(".group").toggle();

        // 사이드바 접힌 상태에서 Group 선택시
        // --1. 접힌 사이드바 열기
        // --2. Drive 접기
        // --3. Group 펼치기
        if($(".aside-left").hasClass("fold")){

            $(".folder").hide();    // Drive 접기
            $(".group").show();     // Group 펼치기

            $(".aside-left").toggleClass('fold'); // 사이드바 펼치기
            $(".aside_fold i").toggleClass("bi-caret-right bi-three-dots-vertical");
        } // if

    }); // ("#group").on('click', function () {}

        // 휴지통 클릭
    $("#bin").click( function () {

        $("#initSection").html("")

        $(".dirli").removeClass('active');
        $("#bin").removeClass('active');
        $(".aside-left ul li").removeClass('active');
        
        if($(".aside-left").hasClass("fold")){
            
            $(".aside-left").toggleClass('fold');
            $(".aside_fold i").toggleClass("bi-caret-right bi-three-dots-vertical");
        } // if
        
        $(this).toggleClass('active');

        $(".trash-list").html("");
        getTrashList();

        $("#dirSection").hide();
        $("#trashSection").show();

        
    });

    // 접기 아이콘 클릭 이벤트
    $(document).on('click', '.aside_fold', function () {

        $("aside").toggleClass('fold');

        // 접기 아이콘 모양 변경
        $(".aside_fold i").toggleClass("bi-three-dots-vertical bi-caret-right");
    });

    // 우측 알림 메뉴 클릭 이벤트
    $(document).on('click', '#message', function () {
        console.log("message cliked.");

        // jquery-confirm의 $.dialog로 진행
        $.dialog({
            title: "알림",
            draggable: true, 
            bgOpacity: 0,               // 배경 흐림 X
            backgroundDismiss: true,    // 배경 클릭시 모달 닫힘
            type: 'notive-blue',               // 모달 색상
            // 모달 내부에서 ul태그 생성
            content: '<ul class="invit-list list-group"></ul>' , 
            animateFromElement: false,
            animation: "zoom",
            closeAnimation: "scale",
            animationSpeed: 400,
            onOpenBefore: function () {

                // 로그인한 유저의 아이디 전송
                var data = { "user_id": user_id };
                
                // 초대받은 리스트 가져오기
                $.post('/invitation/show', data, function (list) {
                    console.log(list);

                    // 초대 받은 모든 리스트 태그로 생성
                    list.forEach( invitGroup => {
                        var inviteList = 
                            '<div class="invite-group-list">' +
                            '<input type="hidden" name="group_no" value="' + invitGroup.group_no + '">' +
                            '<input type="hidden" name="user_id" value="' + invitGroup.user_id + '">' +
                            '<input type="hidden" name="group_name" value="' + invitGroup.group_name + '">' +
                            '<div>['+ invitGroup.invit_id + ']</div>' +
                            '<div class="invit-content">"'+ invitGroup.group_name + '" 에서 회원님을 초대했습니다.</div>' +
                            '<div class="invit-buttons">' +
                            '<button class="btn btn-primary" id="accept" type="button">수락</button>' +
                            '<button class="btn btn-primary" id="refuse" type="button">거절</button><div></div>';

                        li = $("<li>");
                        li.html(inviteList);
                        $(".list-group").append(li);

                    });
                    // 수락 버튼 클릭이벤트 바인드
                    $(document).on('click',"#accept", accept);
                    
                    // 거절 버튼 클릭이벤트 바인드
                    $(document).on('click',"#refuse", accept);
                    
                    // 초대 목록이 없을 때
                    if(list == 0){
                        var span = $("<span>");
                        span.text("새로운 알림이 없습니다.");
                        $(".invit-list.list-group").append(span);
                    };
                    
                    
                },
                "json"
                )
            }
        })
    });


    //------------------------------------------------
    // 프로필 이미지 조회하기
    (function(){

        $.getJSON("/user/getUserImage", {userID:user_id}, function(img){
            // console.log(img);

            // 저장된 이미지가 있다면 기본 프사 가리기
            if(img.uuid != ""){
                $("#user-default-image").hide();
            } //if

            //전역변수 uuid에 저장
            uuid = img.uuid;

            var str = "";

            var fileCallPath = encodeURIComponent(img.uploadPath + "/s_" + img.uuid + "_" + img.fileName);

            str += "<li data-path='"+img.uploadPath+"' data-uuid='"+img.uuid+"' data-filename='"+img.fileName+"'><div>";
           
            // user/display 컨트롤러 호출
            str += "<img src='/user/display?fileName="+fileCallPath+"'";
    
            // 썸네일 크기 조절
            str += " style= 'object-fit: cover; width: 42px; height: 42px; border-radius: 100%;'>"
            str += "</div>";
            str + "</li>";

            $(".main-profileImage ul").html(str);

        }); //getJSON


        var dropdownElementList = [].slice.call(document.querySelectorAll('.dropdown-toggle'))
        var dropdownList = dropdownElementList.map(function (dropdownToggleEl) {
            return new bootstrap.Dropdown(dropdownToggleEl)
        })
    })(); //IIFE : 프로필 이미지 조회

    // 드롭다운 토글
    $('.dropdown-toggle').dropdown();
    
}); // jquery end

