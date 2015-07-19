<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 01.07.2015
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Corporate Messenger</title>
    <link href="../cssFiles/main.css" type="text/css" rel="Stylesheet"/>
    <link href="../cssFiles/header.css" type="text/css" rel="Stylesheet"/>
    <script src="../jsFiles/main.js"></script>
    <script src="../jsFiles/visible.js"></script>
</head>
<body>
<div class="menu">
    <div class="box_log">
        <c:if test="${user.id == null}">
            <c:if test="${wrong}">
                <p>wrong login or password. Try again!</p>
            </c:if>
            <a href="javascript:" onclick="openLoginForm()" id="income">Log in</a>
        </c:if>
        <c:if test="${user!= null}">
            <p>${user.login}</p>
            <a href="exit.do" class="txt">logOut</a>
        </c:if>
    </div>
</div>
<div class="content">

    <div class="textHeader">
        <h1>Corporate messenger</h1>

        <h2>
            "Corporate messenger" is used for communication between a company's employees with preserving the history of
            correspondence
        </h2>
    </div>
    <div class="content_menu">
        <div class="box_m"><a href="/department.do" class="m_menu" onMouseOver="mouseOver('department')"
                              onmouseout="mouseOut('department')"><span>Departments</span>

            <div class="trg"></div>
        </a>

        </div>
        <div class="box_m"><a href="/worker.do" class="m_menu" onMouseOver="mouseOver('worker')"
                              onmouseout="mouseOut('worker')"><span>Workers</span>

            <div class="trg"></div>
        </a>
        </div>
        <div class="box_m"><a href="#" class="m_menu"
                              onMouseOver="mouseOver('messenger')"
                              onmouseout="mouseOut('messenger')"><span>Messenger</span>

            <div class="trg"></div>
        </a>
        </div>
    </div>
    <div class="boxInfoAboutButtons">
        <div class="box_info visible" id="mainInfo">
            <p class="">Place your mouse over the menu and you'll see a hint at this place!
                Smile! Be happy :-)
            </p>
        </div>
        <div class="box_info invisible" id="departmentInfo">
            <p class="">A list of departments. You can view in what department which people work.
                Attention! Only an administrator can create/remove a departments or move employees between them.
            </p>
        </div>
        <div class="box_info invisible" id="workerInfo">
            <p class="">A list of workers. You can view the information of all employees (name, login, division).
                You can also change your password.
                Attention! Only an administrator can create/remove a worker.
            </p>
        </div>
        <div class="box_info invisible" id="messengerInfo">
            <p class="">
                Messenger. You can write to any employee of the company.
                See how much new messages do you have and from whom.
            </p>
        </div>
    </div>
</div>
<div class="blockingBackground invisible" id="loginBackground">
    <form method="get" action="/authorization.do">
        <div class="visibleBlock" id="authorization">
            <input type="text" class="objectRow login" placeholder="login" name="login"/>
            <input type="password" class="objectRow password" placeholder="password" name="password"/>

            <div class="buttonsBox">
                <input type="submit" value="ok" class="buttonOK"/>

                <div class="buttonCancel" onclick="closeLoginForm()">cancel</div>
            </div>
        </div>
    </form>
</div>
</body>
</html>
