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
    <title>Departments</title>
    <link href="../cssFiles/departments.css" type="text/css" rel="Stylesheet"/>
    <link href="../cssFiles/header.css" type="text/css" rel="Stylesheet"/>
    <script src="../jsFiles/departments.js"></script>
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
            <div class="button_cl " onclick="openObjectFromForCreate()">create</div>
            <div class="button_cl " onclick="openSelectObjectForm()">update</div>
            <div class="button_cl " onclick="deleteObject()">delete</div>
        </div>
        <%-- <div class=" button_border fix"></div>--%>
        <div class="forSearch">
            <select class="searchType" id="searchType">
                <option>name</option>
            </select>
            <input type="search" id="inputSearch" onchange="search(this.value)">
        </div>
        <div class="header_id">
            <div class="tt1">Id</div><div class="tt1">Name</div><div class="tt1">Workers</div>
        </div>
    </div>
    <table id="table1">
        <tr class="invisible lineForCopy" id="lineForCopy" onclick="selectRow(this)">
            <td class="depID">1</td>
            <td class="depName">2</td>
            <td class="depWorkers">3</td>
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
    <div class="visibleObjectBlock" id="objectDepartment">
            <div class="invisible validationException" id="nameException"></div>
            <input type="text" onclick="doItInvisible('nameException')" onchange="isNameValid(this.value)" class="objectRow name" placeholder="name"
                   name="name"/>
        <div class="contentWorker" id="contentWorker" onclick="removeSelectionFromWorkerTable()">
            <div class="fixWorkerTable" id="fixWorkerTable">
                <div class="button_box">
                    <div class="button_cl " onclick="openWorkersFormForAdd()">add new worker</div>
                    <div class="button_cl " onclick="deleteWorkerRow()">delete worker</div>
                </div>
                <div class="header_WorkerId">
                    <div class="tt1">Name</div><div class="tt1">Login</div>
                </div>
            </div>
            <div class="scroller">
            <table id="tableWorker">
                <tr class="invisible lineForCopy" id="lineForCopyWorker" onclick="selectWorkerRow(this)">
                    <td class="workName">2</td>
                    <td class="workLogin">3</td>
                 </tr>
            </table>
            </div>
        </div>
            <div class="buttonsBox">
            <div class="buttonOK" onclick="createUpdateDepartment()">save</div>
            <div class="buttonCancel" onclick="closeObjectDepartmentForm()">cancel</div>
        </div>
    </div>
</div>

<div class="blockingBackground invisible" id="objectsFormForAdd">
    <div class="visibleObjectBlock forAdd" id="listWorkers">

        <div class="contentWorker" id="contentAllWorkers" onclick="removeSelection()">
            <div class="fixWorkerTable" id="fixAllWorkersTable">
                <div class="forSearch">
                    <select class="searchType" id="searchWorkerType">
                        <option>name</option>
                        <option>login</option>
                        <option>departmentName</option>
                    </select>
                    <input type="search" id="inputWorkerSearch" onchange="searchWorkers(this.value)">
                </div>
                <div class="header_WorkerAllId">
                    <div class="tt1">Id</div><div class="tt1">Name</div><div class="tt1">Login</div><div class="tt1">Department</div>
                </div>
            </div>
            <div class="scroller">
                <table id="tableAllWorker">
                    <tr class="invisible lineForCopy" id="lineForCopyAllWorker" onclick="selectWorkerRowForChoose(this)">
                        <td class="workID">1</td>
                        <td class="workName">2</td>
                        <td class="workLogin">3</td>
                        <td class="workDep">4</td>
                    </tr>
                </table>
            </div>
        </div>
        <div class="buttonsBox">
            <div class="buttonCancel" onclick="closeContentAllWorkersForm()">cancel</div>
        </div>
    </div>
</div>
<div class="blockingBackground invisible exceptionForm" id="exceptionForm">
    <div class="visibleBlock" id="exceptionVisibleForm">
        <div class="messageAboutSomething" id="messageExc"></div>
        <div class="buttonsBoxExc">
            <div class="buttonOK" onclick="closeExceptionDepartmentForm()">ok</div>
        </div>
    </div>
</div>
<div class="blockingBackground invisible questionForm" id="questionForm">
    <div class="visibleBlock">
        <div class="messageAboutSomething" id="messageQuestion"></div>
        <div class="buttonsBox">
            <div class="buttonOK" id="buttonOkInQuestion" onclick="deleteDepartment()">ok</div>
            <div class="buttonCancel" onclick="closeQuestionDepartmentForm()">cancel</div>
        </div>
    </div>
</div>
</body>
</html>
