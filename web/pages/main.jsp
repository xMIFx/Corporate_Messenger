<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 01.07.2015
  Time: 21:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title> Corporate Messenger</title>
    <link href="../cssFiles/main.css" type="text/css" rel="Stylesheet"/>
    <script src="../jsFiles/main.js"></script>
</head>
<body>
<div class="menu">
    <div class="box_log">
        <a href="#" id="vhod">Log in</a>
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
        <div class="box_m"
             onMouseOver = "mouseOver('department')"
                onmouseout="mouseOut('department')"><a href="#" class="m_menu"><span>Departments</span></a></div>
        <div class="box_m"
             onMouseOver = "mouseOver('worker')"
             onmouseout="mouseOut('worker')"><a href="#" class="m_menu"><span>Workers</span></a></div>
        <div class="box_m"
             onMouseOver = "mouseOver('messenger')"
             onmouseout="mouseOut('messenger')"><a href="#" class="m_menu"><span>Messenger</span></a></div>
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
        <div class="box_info invisible"id="messengerInfo">
            <p class="">
                Messenger. You can write to any employee of the company.
                See how much new messages do you have and from whom.
            </p>
        </div>
    </div>
</div>
</body>
</html>
