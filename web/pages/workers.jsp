<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 04.07.2015
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Workers</title>
    <link href="../cssFiles/workers.css" type="text/css" rel="Stylesheet"/>
    <link href="../cssFiles/header.css" type="text/css" rel="Stylesheet"/>
    <script src="../jsFiles/workers.js"></script>
    <script src="../jsFiles/visible.js"></script>
</head>
<body onload="init()">
<div class="menu">
    <div class="back"><a href="../main.do">home</a></div>
    <div class="box_log">
        <a href="javascript:" onclick="openLoginForm()" id="income">Log in</a>
    </div>
</div>
<div class="content">
    <div class="fix">
        <div class="button_box">
            <div class="button_cl " onclick="openNewObjectForm()">create</div>
            <div class="button_cl ">update</div>
            <div class="button_cl ">delete</div>
        </div>
   <%-- <div class=" button_border fix"></div>--%>

    <div class="header_id">
    <div class="tt1">Id</div><div class="tt1">Name</div><div class="tt1">Login</div><div class="tt1">Department</div>
    </div>
    </div>
    <table id="table1">
            <tr class="invisible" id="lineForCopy">
                <td class="workID">1</td>
                <td class="workName">2</td>
                <td class="workLogin">3</td>
                <td class="workDep">4</td>
            </tr>
    </table>
</div>
<div class="blockingBackground invisible" id="loginBackground">
    <form method="post" action="/authorization.do">
        <div class="autoriz" id="authorization">
            <input type="text" class="objectRow login" placeholder="login" name="login"/>
            <input type="password" class="objectRow password" placeholder="password" name="password"/>

            <div class="buttonsBox">
                <input type="submit" value="ok" class="buttonOK"/>
                <div class="buttonCancel" onclick="closeLoginForm()">cancel</div>
            </div>
        </div>
    </form>
</div>
<div class="blockingBackground invisible" id="objectsFrom">
        <div class="autoriz" id="newObjectWorker">
            <input type="text" class="objectRow id invisible" placeholder="id" name="id"/>
            <input type="text" class="objectRow name" placeholder="name" name="name"/>
            <input type="text" class="objectRow login" placeholder="login" name="login"/>
            <input type="password" class="objectRow password" placeholder="password" name="password"/>
            <div class="buttonsBox">
                <div class="buttonOK" onclick="createNewWorker()">ok</div>
                <div class="buttonCancel" onclick="closeObjectForm()">cancel</div>
            </div>
        </div>
</div>
</body>
</html>
