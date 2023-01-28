<%@ page 
   language="java" 
   contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

    <script>


        $(document).ready(function () {


            $(function () {
                $('input:checkbox[name="itemNo"]').click(function () {

                	
                    if($('input:checkbox[name="itemNo"]').is(':checked')) {
                        console.log('체크');
                    } else {
                        console.log('체크해제');
                    }
                    
                })              
            }) // onclick


            $('#itemDelBtn').click(function (e) {

                if ($('input:checkbox[name="itemNo"]').is(':checked')) {
                    //
                } else {
                    e.preventDefault();

                    var paginationForm = $('#itemDelBtn');

                    paginationForm.attr ('action' , '#');
                }
                
            })  // onclick

            

            $('.item_selBtn').click(function () {

                

                var itemNo = $(this).html();
                var data = {"itemNo":itemNo};
                console.log(data);


                function getItem() {

                    $.get(
                            "/admin/getItem",
                            data,
                            function (result) {

                                var date1 = new Date(result.itemRegiDate);
                                var date2 = new Date(result.itemDelDate);

                                var regiDate = date1.getFullYear() + "-" + (date1.getMonth()+1) + "-" + date1.getDate() ;
                                var delDate ;
                                
                                if (delDate == null) {
                                    delDate = "　　　　　　　　　"
                                } else {
                                    delDate = date2.getFullYear() + "-" + (date2.getMonth()+1) + "-" + date2.getDate() ;                                
                                }

                                var item = 
                                '<div>' +
                                '<label for="itemNo" class="col-sm-2">상품 번호</label>' +
                                '<div class="itemNo col-sm-10">' + result.itemNo + '</div>' +
                                '<label for="itemName" class="col-sm-2 col-form-label">상품명</label>' +
                                '<div class="col-sm-10">' +
                                '<input type="text" class="form-control" name="itemName" value=" '+ result.itemName +' "></div>' +
                                '</div>' +
                                '<label for="itemCapa" class="col-sm-2 col-form-label">추가 용량</label>' +
                                '<div class="col-sm-10">' +
                                '<input type="text" class="form-control" name="itemCapa" value=" '+ result.itemCapa +' "></div>' +
                                '</div>' +
                                '<label for="itemPrice" class="col-sm-2 col-form-label">상품 가격</label>' +
                                '<div class="col-sm-10">' +
                                '<input type="text" class="form-control" name="itemPrice" value=" '+ result.itemPrice +' "></div>' +
                                '</div>' +                                
                                '<label for="itemRegiDate" class="etcInfo-label col-sm-2">등록 일자</label>' +
                                '<div class="etcInfo col-sm-10">' +
                                ''+ regiDate +'' +
                                '</div>' +
                                '<label for="itemDelDate" class="etcInfo-label col-sm-2">삭제 일자</label>' +
                                '<div class="etcInfo col-sm-10">' +
                                ''+ delDate +'' +                                
                                '</div>' +
                                '<label for="itemState" class="etcInfo-label col-sm-2">상품 상태</label>' +
                                '<div class="etcInfo col-sm-10">' +
                                ''+ result.itemState +'' +
                                '</div>'
                                
                                $(".itemInfo").append(item);

                            },
                            
                            "json"
                        );
                    
                }


                $.confirm({                    

                    title : '상품수정',
                    content : '' + '<br>' +
                    '<div class="itemInfo"></div>',

                    type : 'dark',
                    dragWindowGap: 0,
                    closeIcon: true,
                    columnClass : 'medium',

                    buttons: {
                           submit:{
                            text : '수정',
                            btnClass : 'btn-blue',

                            

                            action: function () {                                
                                
                                var itemName = $("input[name=itemName]").val();
                                var itemCapa = $('input[name=itemCapa]').val();
                                var itemPrice = $('input[name=itemPrice]').val();
                                
                                $.ajax({
                                    type : 'post',
                                    url : "/admin/itemModify",
                                    data : {itemNo:itemNo,itemCapa:itemCapa,itemPrice:itemPrice,itemName:itemName},
                                    dataType: "json",

                                    success : function (modify) {
                                        $.alert('수정완료');
                                        return false;
                                        
                                        
                                    }
                                    
                                })    
                                return false;
                            }

                        },
                        cancel: {
                            text: '확인',
                            btnClass: 'btn-dark',

                            action : function () {
                                location.reload();
                            }
                        },
                    },

                    onOpenBefore : function () {                        

                        getItem();
                        
                    } // onOpen

                }) // confirm
                
            })

            $('#itemRegisterBtn').click(function () {

                $.confirm ({
                    title : '상품등록',
                    content : "" +
                    '<div class="itemRegisterForm"></div>',

                    // columnClass : 'medium', 

                    theme : 'supervan',

                    buttons : {

                        submit : {
                            text : '등록',

                            action : function () {
                                    
                                $.alert ({
                                    title : "",
                                    content :'상품을 등록하시겠습니까 ?',                                    

                                    type : 'blue',
                                    buttons : {
                                        OK :{
                                            btnClass : 'btn-blue',
                                            action : function () {                                                
                                                
                                                var itemName = $("input[name=itemName]").val();
                                                var itemCapa = $("input[name=itemCapa]").val();
                                                var itemPrice = $("input[name=itemPrice]").val();
                                                
                                                $.ajax ({
                
                                                    type : 'post',
                                                    url : "/admin/itemRegister",
                                                    data : {adminID:user_id,itemName:itemName,itemCapa:itemCapa,itemPrice:itemPrice},
                                                    
                                                    success : function () {
                                                        location.reload();                                                            
                                                    }
                                                })
                                                return false;
                                            }
                                        },
                                        close : {  }                                            
                                    }
                                    
                                })
                                return false;                                        
                            }
                        } ,

                        close : {
                            text : '취소',

                            action : function () {
                                location.reload();
                            }
                        }

                    },

                    onOpenBefore : function () {

                        $.get (
                            "/admin/itemRegister",

                            function () {

                                var itemRegister = $('.itemRegister-block').clone();

                                itemRegister.find('.itemName').text('상품명');
                                itemRegister.find('.itemCapa').text('추가용량');
                                itemRegister.find('.itemPrice').text('가격');

                                $inputName = $('<input type="text" class="inputItemName form-control col-sm-10" name="itemName">')
                                $inputCapa = $('<input type="text" class="inputItemCapa form-control col-sm-10" name="itemCapa">')
                                $inputPrice = $('<input type="text" class="inputItemPrice form-control col-sm-10" name="itemPrice">')

                                itemRegister.find('.inputItemName').append($inputName);
                                itemRegister.find('.inputItemCapa').append($inputCapa);
                                itemRegister.find('.inputItemPrice').append($inputPrice);

                                $('.itemRegisterForm').append(itemRegister);

                                
                            }
                        )

                        "json"
                        
                    }
                })
                
            })



        }) // .jq
            
    </script>

    <style>

        header {
            padding: 0px;
            height: 0px;
        }

        section {
            border: 10px solid rgb(218, 216, 216);
            background-color: white;
        }

        .jconfirm-buttons button {
            padding : 0px!important;
        }

        .itemName, .itemCapa, .itemPrice {
            width: auto!important;
        }

        .inputItemName, .inputItemCapa, .inputItemPrice {
            margin-top: 5px!important;
            margin-bottom: 10px;
        }

        label {
            margin-bottom: 0px;        
        }

        .form-control {
            margin-top: 15px;
        }

        .col-form-label {
            padding-top: calc(.375rem + 18px);
            padding-bottom: calc(.375rem + 1px);
        }

        .etcInfo {
            margin-top: 10px;
        }

        .etcInfo-label {
            padding-top: calc(.375rem + 6px);
        
        }

        /* ======================================================================== */


        /* /* * {
            margin: 0 auto;
            padding: 0;
            text-decoration: none!important;
        } */

        #wrapper {
            width: 100%;

            font-family: 'Lucida Sans', 'Lucida Sans Regular', 'Lucida Grande',
                            'Lucida Sans Unicode', Geneva, Verdana, sans-serif;
            font-size: 14px;
        } 


        #itemListTable {
            width: 100%;

            
            border-collapse: collapse;

            text-align: center;
        }

        .item_selBtn {
            color: black;
        }

        #itemList_tr:hover {
            background-color: #e0dfdf;
        }

        #itemListTable td {
            padding: 13px;
        }

        #itemListTable th {
            font-size: 17px; 
            text-align: center;         
            
            padding: 10px;border-top: 2px solid black;
            border-bottom: 2px solid black;
            color: black;

        }

        input[type="checkbox"] {
            width: 20px;
            height: 20px;
        }

        input[type="checkbox"]:checked{
            background-color: red;
        }

        #settingBox {
            width: 250px;
            height: 50px;

            float: right;
        }

        button {
            width: 80px;
            height: 35px;
			
            border-radius: 10px;	
			
            margin-left: 12px;     
        }

        #itemRegisterBtn {
            color: black;
        }

        td {
            cursor: pointer;
        }

    </style>
    <div id="wrapper">

        <h2><a href="/admin/itemList">&nbsp;&nbsp;&nbsp;상품 목록</a></h2>
            
        <hr>

        <br>

        <form action="/admin/itemRemove" method="POST">

            <div id="settingBox">
                <button type="button"><a id="itemRegisterBtn">상품 등록</a></button>
                <button type="submit" id="itemDelBtn">상품 삭제</button>
                
            </div>
            
            <br>

            <table id="itemListTable">

                <input type="hidden" name="itemState" value="${itemList.itemState}">

                    
                        <tr>
                            <th></th>
                            <th>상품 번호</th>
                            <th>상품명</th>
                            <th>추가용량</th>
                            <th>상품가격</th>
                            <th>등록 일자</th>
                            <th>관리 일자</th>
                            <th>상품 상태</th>
                        </tr>
                    
                        <c:forEach items="${itemlist}" var="itemList">
                            <tr id="itemList_tr">
                                <td onclick="event.cancelBubble=true"><input type="checkbox" name="itemNo" value="${itemList.itemNo}" id="checkbox"></td>

                                <!-- <td><a href="/admin/getItem?itemNo=${itemList.itemNo}" class="item_selBtn">${itemList.itemNo}</a></td> -->
                                <td><a class="item_selBtn" id="itemNumber" >${itemList.itemNo}</a></td>

                                <td><a class="item_selBtn">${itemList.itemName}</a></td>

                                <td><a class="item_selBtn">${itemList.itemCapa}</a></td>

                                <td><a class="item_selBtn">${itemList.itemPrice}</a></td>

                                <td><a class="item_selBtn"><fmt:formatDate pattern="yyyy-MM-dd" value="${itemList.itemRegiDate}" /></a></td>

                                <td><a class="item_selBtn"><fmt:formatDate pattern="yyyy-MM-dd" value="${itemList.itemDelDate}" /></a></td>

                                <td><a class="item_selBtn">${itemList.itemState}</a></td>
                            </tr>
                        </c:forEach>                        
                    
                
            </table>
            
        </form>
        <div class="itemRegister-block">
    
            <label for="itemName" class="itemName col-sm-2"></label>
            <span class="inputItemName"></span>
            
            <label for="itemCapa" class="itemCapa col-sm-2"></label>
            <div class="inputItemCapa"></div>        
            
            <label for="itemPrice" class="itemPrice col-sm-2"></label>
            <div class="inputItemPrice"></div>        
    
        </div>
    </div>

