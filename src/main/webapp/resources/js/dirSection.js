
$(function () {
    console.log("dirSection start...");
})

$(document).on('click', '.memoTitle, .memoWriteDate', function () {

    $(this).parent(".memo").toggleClass('select');
});

$(document).on('click', '.file-type-name, .writeDate', function () {
    $(this).parent(".file").toggleClass('select');
});