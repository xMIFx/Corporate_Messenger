<%--
  Created by IntelliJ IDEA.
  User: Vlad
  Date: 04.07.2015
  Time: 15:58
  To change this template use File | Settings | File Templates.
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
<div class="content" id="content" onclick="removeSelection()">
    <div class="fix" id="fix">
        <div class="button_box">
            <div class="button_cl " onclick="openNewObjectForm()">create</div>
            <div class="button_cl " onclick="openSelectObjectForm()">update</div>
            <div class="button_cl " onclick="deleteObject()">delete</div>
        </div>
        <%-- <div class=" button_border fix"></div>--%>
        <div class="forSearch">
            <select class="searchType" id="searchType">
                <option>name</option>
                <option>login</option>
                <option>departmentName</option>
            </select>
            <input type="search" id="inputSearch" onchange="search(this.value)">
        </div>
        <div class="header_id">
            <div class="tt1">Id</div><div class="tt1">Name</div><div class="tt1">Login</div><div class="tt1">Department</div>
        </div>
    </div>
    <table id="table1">
        <tr class="invisible" id="lineForCopy" onclick="selectRow(this)">
            <td class="workID">1</td>
            <td class="workName">2</td>
            <td class="workLogin">3</td>
            <td class="workDep">4</td>
        </tr>
    </table>
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
<div class="blockingBackground invisible" id="objectsForm">
    <div class="visibleBlock" id="objectWorker">
        <%--<input type="text" class="objectRow depName invisible" placeholder="depName" name="depName"/>
        <input type="text" class="objectRow objectVersion invisible" placeholder="objectVersion" name="objectVersion"/>
        <input type="text" class="objectRow id invisible" placeholder="id" name="id"/>--%>
            <div class="invisible validationException" id="nameException"></div>
            <input type="text" onclick="doItInvisible('nameException')" onchange="isNameValid(this.value)" class="objectRow name" placeholder="name"
                   name="name"/>
            <div class="invisible  validationException" id="loginException"></div>
            <input type="text" onclick="doItInvisible('loginException')" onchange="isLoginValid(this.value)" class="objectRow login" placeholder="login"
                   name="login"/>
            <div class="invisible validationException" id="passwordException"></div>
            <input type="password" onclick="doItInvisible('passwordException')" onchange="isPasswordValid(this.value)" class="objectRow password"
                   placeholder="password"
                   name="password"/>
            <div class="invisible validationException" id = "confirmPasswordException"></div>
            <input type="password" onclick="doItInvisible('confirmPasswordException')" class="objectRow confirmPassword" placeholder="confirmPassword"
                   name="confirmPassword"/>

            <div class="buttonsBox">
            <div class="buttonOK" onclick="createUpdateWorker()">save</div>
            <div class="buttonCancel" onclick="closeObjectWorkerForm()">cancel</div>
        </div>
    </div>
</div>
<div class="blockingBackground invisible" id="exceptionForm">
    <div class="visibleBlock" id="exceptionVisibleForm">
        <div class="messageAboutSomething" id="messageExc"></div>
        <div class="buttonsBoxExc">
            <div class="buttonOK" onclick="closeExceptionWorkerForm()">ok</div>
        </div>
    </div>
</div>
<div class="blockingBackground invisible" id="questionForm">
    <div class="visibleBlock">
        <div class="messageAboutSomething" id="messageQuestion"></div>
        <div class="buttonsBox">
            <div class="buttonOK" onclick="deleteWorker()">ok</div>
            <div class="buttonCancel" onclick="closeQuestionWorkerForm()">cancel</div>
        </div>
    </div>
</div>
</body>
</html>
