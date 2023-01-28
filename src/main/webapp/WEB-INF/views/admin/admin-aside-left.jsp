<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<style>

    .admin-aside {
        display: flex;
        flex-direction: column;

        width: 250px; 

        background-color: rgb(54, 53, 53);
    }

    #admin-title {
        font-family: "SCDream7";
        color: white;

        width: 220px;
        height: 60px;

        font-size: 30px;
        font-style: unset;

        text-align: center;
        align-self: center;

        direction: unset;

        margin-top: 20px;
        border-bottom: 1px solid rgba(129, 128, 128, 0.945);
    }

    .userMgt-block , .itemMgt-block{
        width: 249px;
        height: 45px;

        margin-top: 10px;

        font-size: 20px;

        color: white!important;
    }

    .userMgt-block img , .itemMgt-block img{
        width: 40px;
        height: 40px;

        /* margin-left: 5px; */

        clear: both;
    }

    #userMgt , #itemMgt{
        width: 100px;
        height: 20px;

        letter-spacing : 3px;
        font-family: "NotoSansKR-Light";

        margin-left: 20px;

        color: white;
    }

    #userListBtn , #itemListBtn {
        color: white;
    }

    
    

</style>
 
<div class="admin-aside">

    <div id="admin-title">        Notive
    </div>

    <hr>

    <div class="userMgt-block">
        <span>
            <i class="xi-caret-down-min xi-rotate-270"></i>
        </span>
        <img src="../resources/images/회원 관리.png">
        <span id="userMgt"><a href="/admin/userList" id="userListBtn">회원관리</a></span>
    </div>

    <div class="itemMgt-block">
        <span>
            <i class="xi-caret-down-min xi-rotate-270"></i>
        </span>
        <img src="../resources/images/상품관리.png">
        <span id="itemMgt"><a href="/admin/itemList" id="itemListBtn">상품관리</a></span>

    </div>
    
</div>