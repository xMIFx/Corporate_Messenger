<%--
  Created by IntelliJ IDEA.
  User: bukatinvv
  Date: 02.04.2015
  Time: 13:49
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>

<head>
    <meta charset="utf-8">
    <title>Messanger</title>
    <script src="${pageContext.request.contextPath}/jsFiles/messenger.js"></script>
    <script src="${pageContext.request.contextPath}/jsFiles/showHideFunction.js"></script>
    <link href="../cssFiles/header.css" type="text/css" rel="Stylesheet"/>
    <link href="${pageContext.request.contextPath}/cssFiles/messangerStyle.css" rel="stylesheet" type="text/css">
    <%-- <link href="${pageContext.request.contextPath}/cssFiles/loadingStyle.css" rel="stylesheet" type="text/css">--%>
</head>

<body>
<div class="menu NoFix">
    <div class="back"><a href="../main.do">home</a></div>
    <div class="box_log">
        <c:if test="${worker.id == null}">
            <c:if test="${wrong}">
                <p>wrong login or password. Try again!</p>
            </c:if>
            <a href="javascript:" onclick="openLoginForm()" id="income">Log in</a>
        </c:if>
        <c:if test="${worker!= null}">
            <p>${worker.login}</p>
            <a href="/exit.do" class="txt">logOut</a>
        </c:if>
    </div>
</div>

<div class="center">
    <div class="user_box">
        <div class="groups" id="groups">

        </div>
        <div class="lastChats">
            <p>Last chats</p>

            <div class="" id="lastChats">
                <li id="lastChat_id"
                    class="user_ch"><a href="javascript:"
                                       onclick="functionChangingChatByID('${lastChat.idChat}')">
                    <span></span>name</a>
                </li>
                <li id="lastUser_id1"
                    class="user_ch"><a href="javascript:"
                                       onclick="functionChangingChat('${lastUser.cryptUUID}','${lastUser.id}')">
                    <span></span>name1</a>
                </li>
            </div>
        </div>
    </div>
    <div class="information_about_chat" id="information_about_chat">
        <p>information about current chat</p>
    </div>
    <div id="output_box" class="output_box" onload="initOutput()">
        <div class="chat_box" id="usersChat_0" onscroll="functionOnScrollChat(this)">
            <div class="MessageClass NewMessage">
                <p class="WhoWright">Admin среда, 13 мая 2015 г., 17:54:02</p>

                <p class="WhoDontRead">QA, Kos6ka, Julia</p>

                <p class="MessageText">ssssss</p>
            </div>

            <div class="MessageClass MyMessage">
                <p class="WhoWright"> I среда, 13 мая 2015 г., 17:54:02</p>

                <p class="WhoDontRead">QA, Kos6ka, Julia</p>

                <p class="MessageText">sssssss</p>
            </div>

        </div>
        <div style="text-align: center;">
            <form action="">
                <textarea rows="5" id="textID" name="message" value="Hello WebSocket!" type="text"></textarea>
                <input class="sendButton" onclick="send_message()" value="Send" type="button">
            </form>
        </div>
    </div>
</div>

<div class="MessageClass CloneClass" id="cloneMessage">
    <p class="WhoWright"></p>

    <p class="WhoDontRead"></p>

    <p class="MessageText"></p>
</div>

<div class="group CloneClass" id="cloneGroup">
    <div class="open"></div>
    <a href="javascript:" class="gr"
       onclick="functionAnimatedShowHide(this.parentNode.getElementsByTagName('ul')[0].id)"></a>

    <p class="MessageCount"></p>
    <ul id="id" class="slide-down">


    </ul>
</div>
<li id="cloneUser"
    class="offline user_ch CloneClass"><a class="workerFromDepartment" href="javascript:"
                              onclick="functionChangingChat(this.parentNode.id)">
    <span></span></a>

    <p class="MessageCount"></p>
</li>
</body>
</html>
