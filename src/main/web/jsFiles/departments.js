/**
 * Created by Vlad on 04.07.2015.
 */

var mXMLHttpRequest = new XMLHttpRequest();
var cloneRow;
var cloneRowWorker;
var cloneRowForChooseWorker;
var table;
var tableWorker;
var tableForChooseWorker;
var urlForAjax = "department.do";
var selectedDepartment = null;
//Objects
function createWorkerObject() {
    this.id;
    this.name;
    this.login;
    this.departmentName;

    this.setID = function (ID) {
        this.id = ID;
    }
    this.setName = function (name) {
        this.name = name;
    }
    this.setLogin = function (login) {
        this.login = login;
    }

    this.setDepartmentName = function (depName) {
        this.departmentName = depName;
    }


    this.getID = function () {
        return this.id;
    }
    this.getName = function () {
        return this.name;
    }
    this.getLogin = function () {
        return this.login;
    }

    this.getDepartmentName = function () {
        return this.departmentName;
    }


    this.objectValidation = function () {
        var itsOk = true;
        if (itsOk) {
            itsOk = isNameValid(this.name);
        }
        else {
            isNameValid(this.name);
        }
        if (itsOk) {
            itsOk = isLoginValid(this.login);
        }
        else {
            isLoginValid(this.login);
        }

        return itsOk;
    }
}

function createDepartmentObject() {
    this.id;
    this.name;
    this.workersCount;
    this.workers;
    this.objectVersion;

    this.setID = function (ID) {
        this.id = ID;
    }

    this.setName = function (name) {
        this.name = name;
    }

    this.setCountWorkers = function (countWorkers) {
        this.workersCount = countWorkers;
    }

    this.setWorkersList = function (workerList) {
        this.workers = workerList;
    }

    this.setObjectVersion = function (objectVersion) {
        this.objectVersion = objectVersion;
    }

    this.addWorker = function (worker) {
        if (worker == null) {
            /*NOP*/
        }
        else {
            if (this.workers === undefined || this.workers == null) {
                this.workers = new Array();
            }
            var exist = false;
            for (var i = 0; i < this.workers.length; i++) {
                if (this.workers[i].getID() == worker.getID()) {
                    exist = true;
                    break;
                }
            }
            if (!exist) {
                this.workers.push(worker);
            }
        }
    }

    this.removeWorker = function (worker) {
        if (worker == null) {
            /*NOP*/
        }
        else {
            if (this.workers === undefined || this.workers == null) {
                this.workers = new Array();
            }
            for (var i = 0; i < this.workers.length; i++) {
                if (this.workers[i].getID() == worker.getID()) {
                    this.workers.splice(i, 1);
                }

            }
        }
    }

    this.getID = function () {
        return this.id;
    }

    this.getName = function () {
        return this.name;
    }

    this.getCountWorkers = function () {
        return this.workersCount;
    }

    this.getWorkersList = function () {
        return this.workers;
    }

    this.getObjectVersion = function () {
        return this.objectVersion;
    }

    this.objectValidation = function () {
        var itsOk = true;
        itsOk = isNameValid(this.name);
        return itsOk;
    }

    this.createJSON = function () {
        var json = JSON.stringify(this);
        return json;
    }

}

function createDepartmentByXML(departmentXml) {
    var department = new createDepartmentObject();
    var id = departmentXml.getElementsByTagName("id")[0];
    var name = departmentXml.getElementsByTagName("name")[0];
    var countWorkers = departmentXml.getElementsByTagName("workersCount")[0];
    var workerList = departmentXml.getElementsByTagName("workers");
    var objVersion = departmentXml.getElementsByTagName("objectVersion")[0];
    (id === undefined || id.childNodes === undefined || id.childNodes[0] === undefined) ?
        id = null : (id = id.childNodes[0].nodeValue);
    (name === undefined || name.childNodes === undefined || name.childNodes[0] === undefined) ?
        name = null : (name = name.childNodes[0].nodeValue);
    (countWorkers === undefined || countWorkers.childNodes === undefined || countWorkers.childNodes[0] === undefined) ?
        countWorkers = null : (countWorkers = countWorkers.childNodes[0].nodeValue);
    (workerList === undefined || workerList == null || workerList.length == 0) ?
        workerList = new Array() : (workerList = getWorkers(workerList));
    (objVersion === undefined || objVersion.childNodes === undefined || objVersion.childNodes[0] === undefined) ?
        objVersion = null : (objVersion = objVersion.childNodes[0].nodeValue);
    department.setID(id);
    department.setName(name);
    department.setCountWorkers(countWorkers);
    department.setWorkersList(workerList);
    department.setObjectVersion(objVersion);

    return department;
}

function getWorkers(workersHolder) {
    var workers = new Array(workersHolder.length);
    if (workersHolder.length > 0) {
        for (var loop = 0; loop < workersHolder.length; loop++) {
            var workerXML = workersHolder[loop];
            var worker = createWorkerByXML(workerXML);
            workers[loop] = worker;
        }
    }
    return workers;
}

function isNameValid(name) {
    var itValid = true;
    if (name == null) {
        itValid = false;
        writeMessageAboutValidation('field "name" need to be fill', "name");
    }
    else {
        name = name.trim();
        if (name == "") {
            itValid = false;
            writeMessageAboutValidation('field "name" need to be fill', "name");
        }
        else if (name.length < 2) {
            itValid = false;
            writeMessageAboutValidation('length field "name" need to be more 3', "name");
        }
        else if (!/^[a-zA-Z]+[A-Za-z0-9\s.]*/.test(name)) {
            itValid = false;
            writeMessageAboutValidation('field "name" contains some illegal symbols', "name");
        }
    }
    return itValid;
}

function createWorkerByXML(workerXml) {
    var worker = new createWorkerObject();
    var id = workerXml.getElementsByTagName("id")[0];
    var name = workerXml.getElementsByTagName("name")[0];
    var login = workerXml.getElementsByTagName("login")[0];
    var depName = workerXml.getElementsByTagName("departmentName")[0];
    (id === undefined || id.childNodes === undefined || id.childNodes[0] === undefined) ?
        id = null : (id = id.childNodes[0].nodeValue);
    (name === undefined || name.childNodes === undefined || name.childNodes[0] === undefined) ?
        name = null : (name = name.childNodes[0].nodeValue);
    (login === undefined || login.childNodes === undefined || login.childNodes[0] === undefined) ?
        login = null : (login = login.childNodes[0].nodeValue);
    (depName === undefined || depName.childNodes === undefined || depName.childNodes[0] === undefined) ?
        depName = null : (depName = depName.childNodes[0].nodeValue);
    worker.setID(id);
    worker.setName(name);
    worker.setLogin(login);
    worker.setDepartmentName(depName);
    return worker;
}

function createExceptionObject() {
    this.exceptionMessege;

    this.setExceptionMessage = function (excMess) {
        this.exceptionMessege = excMess;
    }

    this.getExceptionMessage = function () {
        return this.exceptionMessege;
    }
}

function createExceptionByXML(exceptionXML) {
    var exceptionFromServer = new createExceptionObject();
    var excMes = exceptionXML.getElementsByTagName("exceptionMessage")[0];
    (excMes === undefined || excMes.childNodes === undefined || excMes.childNodes[0] === undefined) ?
        excMes = null : (excMes = excMes.childNodes[0].nodeValue);
    exceptionFromServer.setExceptionMessage(excMes);
    return exceptionFromServer;
}
//Functions
function init() {
    getAllDepartments();
    initGlobalVariable();
}

function initGlobalVariable() {
    if (cloneRow == null) {
        cloneRow = document.getElementById("lineForCopy");
    }
    if (table == null) {
        table = document.getElementById("table1");
    }
    if (cloneRowWorker == null) {
        cloneRowWorker = document.getElementById("lineForCopyWorker");
    }
    if (tableWorker == null) {
        tableWorker = document.getElementById("tableWorker");
    }

    if (cloneRowForChooseWorker == null) {
        cloneRowForChooseWorker = document.getElementById("lineForCopyAllWorker");
    }
    if (tableForChooseWorker == null) {
        tableForChooseWorker = document.getElementById("tableAllWorker");
    }
}

function sendAjax(type, url) {
    mXMLHttpRequest.open(type, url, true);
    mXMLHttpRequest.onreadystatechange = getAnswerFromServer;
    mXMLHttpRequest.send(null);
}

function getAnswerFromServer() {
    if (mXMLHttpRequest.readyState == 4) {
        if (mXMLHttpRequest.status == 200) {
            parseMessages(mXMLHttpRequest.responseXML);
        }
        else {/*NOP*/
        }
    }
    else {/*NOP*/
    }
}

function parseMessages(responseXML) {
    initGlobalVariable();
    if (responseXML == null) {
        return false;
    } else {
        var departments = responseXML.getElementsByTagName("departmentsHolder")[0];
        if (departments === undefined) {
            /*NOP*/
        }
        else {
            clearTable(table);
            fillTable(departments);
            return;

        }
        var departmentXML = responseXML.getElementsByTagName("department")[0];
        if (departmentXML === undefined) {
            /*NOP*/
        }
        else {
            selectedDepartment = createDepartmentByXML(departmentXML);
            fillObjectForm(selectedDepartment);
            return;
        }

        var exceptionXML = responseXML.getElementsByTagName("exceptionForView")[0];
        if (exceptionXML === undefined) {/*NOP*/
        }
        else {
            var exceptionFromServer = createExceptionByXML(exceptionXML);
            openExceptionForm(exceptionFromServer.getExceptionMessage());
            return;
        }
        var workers = responseXML.getElementsByTagName("workersHolder")[0];
        if (workers === undefined) {
            /*NOP*/
        }
        else {
            clearTable(tableForChooseWorker);
            fillTableWorkersForAdd(workers);
            return;

        }

    }
}

function openObjectFromForCreate() {
    openNewObjectForm();
    selectedDepartment = new createDepartmentObject();
}

function clearTable(tableForClean) {
    var elementsForDelete = tableForClean.getElementsByTagName("tr");
    if (elementsForDelete.length > 1) {
        var child;
        for (var loop = elementsForDelete.length - 1; loop >= 0; loop--) {
            child = elementsForDelete[loop];
            if (child.classList === undefined) {
                /*NOP*/
            }
            else if (!child.classList.contains("lineForCopy")) {
                tableForClean.removeChild(child);
            }
        }
    }
}

function fillTable(departmentsHolder) {
    var departments = departmentsHolder.getElementsByTagName('departments');
    if (departments.length > 0) {
        for (var loop = 0; loop < departments.length; loop++) {
            var departmentXml = departments[loop];
            var department = createDepartmentByXML(departmentXml);
            createNewTableRow(department);
        }
    }
}

function createNewTableRow(department) {
    var itNewRow = false;
    var newRow = document.getElementById(department.getID());
    if (newRow === undefined || newRow == null) {
        newRow = cloneRow.cloneNode(true);
        newRow.id = department.getID();
        newRow.classList.remove("invisible");
        newRow.classList.remove("lineForCopy");
        itNewRow = true;
    }
    else {/*NOP*/
    }
    var child;
    for (i = 0; i < newRow.childNodes.length; i++) {
        child = newRow.childNodes[i];
        if (child.classList === undefined) {/*NOP*/
        }
        else if (child.classList.contains('depID')) {
            child.innerHTML = department.getID();
        }
        else if (child.classList.contains('depName')) {
            child.innerHTML = department.getName();
        }
        else if (child.classList.contains('depWorkers')) {
            child.innerHTML = department.getCountWorkers();
        }
    }
    if (itNewRow) {
        table.appendChild(newRow);
    }
    else {/*NOP*/
    }
}

function getAllDepartments() {
    sendAjax("GET", urlForAjax + "?action=getAll");
}

function createUpdateDepartment() {
    var newDepartment;
    if (selectedDepartment == null) {
        newDepartment = new createDepartmentObject();
    }
    else {
        newDepartment = selectedDepartment;
    }
    var elementNewObjectBlock = document.getElementById("objectDepartment");
    var conditionElement;
    for (var i = 0; i < elementNewObjectBlock.childNodes.length; i++) {
        conditionElement = elementNewObjectBlock.childNodes[i];
        if (conditionElement.classList === undefined) {/*NOP*/
        }
        else if (conditionElement.classList.contains("name")) {
            newDepartment.setName(conditionElement.value.trim());
        }
        // need fill workers
    }
    if (newDepartment.objectValidation()) {
        //selectedDepartment = null;
        sendAjaxFromDepartmentObject(newDepartment);
    }
}

function sendAjaxFromDepartmentObject(department) {

    if (department.getID() == undefined || department.getID() == null || department.getID() == '') {
        createAjaxForCreate(department);
    }
    else {
        createAjaxForUpdate(department);
    }
}

function createAjaxForCreate(department) {
    var url = urlForAjax;
    url = url + "?department=" + department.createJSON();
    sendAjaxForUpdateCreate("PUT", url)
}

function createAjaxForUpdate(department) {
    var url = urlForAjax;
    url = url + "?department=" + department.createJSON();
    sendAjaxForUpdateCreate("POST", url)
}

function sendAjaxForUpdateCreate(type, url) {
    mXMLHttpRequest.open(type, url, true);
    mXMLHttpRequest.onreadystatechange = getAnswerFromServerOnCreateUpdate;
    mXMLHttpRequest.send(null);

}

function getAnswerFromServerOnCreateUpdate() {
    if (mXMLHttpRequest.readyState == 4) {
        if (mXMLHttpRequest.status == 200) {
            parseMessagesFromUpdateCreate(mXMLHttpRequest.responseXML);
        }
        else {/*NOP*/
        }
    }
    else {/*NOP*/
    }
}

function parseMessagesFromUpdateCreate(responseXML) {
    initGlobalVariable();
    if (responseXML == null) {
        return false;
    } else {
        var departments = responseXML.getElementsByTagName("departmentsHolder")[0];
        if (departments === undefined) {
            /*NOP*/
        }
        else {
            fillTable(departments);
            selectedDepartment = null;
            closeObjectDepartmentForm('objectDepartment');
            return;

        }
        var departmentXML = responseXML.getElementsByTagName("department")[0];
        if (departmentXML === undefined) {
            /*NOP*/
        }
        else {
            var newDepartment = createDepartmentByXML(departmentXML);
            createNewTableRow(newDepartment);
            selectedDepartment = null;
            closeObjectDepartmentForm('objectDepartment');
            return;
        }

        var exceptionXML = responseXML.getElementsByTagName("exceptionForView")[0];
        if (exceptionXML === undefined) {/*NOP*/
        }
        else {
            var exceptionFromServer = createExceptionByXML(exceptionXML);
            openExceptionForm(exceptionFromServer.getExceptionMessage());
            return;
        }
        var workers = responseXML.getElementsByTagName("workersHolder")[0];
        if (workers === undefined) {
            /*NOP*/
        }
        else {
            clearTable(tableForChooseWorker);
            fillTableWorkersForAdd(workers);
            return;

        }

    }
}

function selectRow(objectForSelect) {
    if (objectForSelect.classList.contains("selected")) {
        openSelectObjectForm();
    }
    else {
        removeSelectionFromAll("selected");
        objectForSelect.classList.add("selected");
    }
}

function selectWorkerRow(objectForSelect) {
    if (objectForSelect.classList.contains("selectedWorker")) {
        objectForSelect.classList.remove("selectedWorker");
        ;
    }
    else {
        removeSelectionFromAll("selectedWorker");
        objectForSelect.classList.add("selectedWorker");
    }
}

function selectWorkerRowForChoose(objectForSelect) {
    if (objectForSelect.classList.contains("selectedWorkerForChoose")) {
        var worker = createObjectWorkerFromSelectRow(objectForSelect);
        selectedDepartment.addWorker(worker);
        createNewTableWorkersRow(tableWorker, cloneRowWorker, worker, "worker_");
    }
    else {
        removeSelectionFromAll("selectedWorkerForChoose");
        objectForSelect.classList.add("selectedWorkerForChoose");
    }
}

function createObjectWorkerFromSelectRow(row) {
    var newWorker = new createWorkerObject();
    var conditionElement;
    for (var i = 0; i < row.childNodes.length; i++) {
        conditionElement = row.childNodes[i];

        if (conditionElement.classList === undefined) {/*NOP*/
        }
        else if (conditionElement.classList.contains("workID")) {
            newWorker.setID(conditionElement.innerHTML);
        }
        else if (conditionElement.classList.contains("workName")) {
            newWorker.setName(conditionElement.innerHTML);
        }
        else if (conditionElement.classList.contains("workLogin")) {
            newWorker.setLogin(conditionElement.innerHTML);
        }
        else if (conditionElement.classList.contains("workDep")) {
            newWorker.setDepartmentName(conditionElement.innerHTML);
        }
    }
    return newWorker;
}

function openSelectObjectForm() {
    var allSelectedElements = document.getElementsByClassName("selected");
    if (allSelectedElements.length == 0 || allSelectedElements == null) {
        openExceptionForm("no line is selected!");
    }
    else if (allSelectedElements.length > 1) {
        openExceptionForm("more then one line is selected!");
    }
    else {
        var elementForInvisible = document.getElementById('objectsForm');
        changeVisible(elementForInvisible, true);
        startFillObjectForm(allSelectedElements[0]);
    }
}

function startFillObjectForm(selectedElement) {
    var newDepartment = new createDepartmentObject();
    var conditionElement;
    for (var i = 0; i < selectedElement.childNodes.length; i++) {
        conditionElement = selectedElement.childNodes[i];

        if (conditionElement.classList === undefined) {/*NOP*/
        }
        else if (conditionElement.classList.contains("depID")) {
            newDepartment.setID(conditionElement.innerHTML);
            break;
        }
    }
    sendAjaxForFillObjectForm(newDepartment);
}

function sendAjaxForFillObjectForm(department) {
    var url = urlForAjax + "?action=getByID&id=" + department.getID();
    sendAjax("GET", url);
}

function fillObjectForm(department) {
    var elementForFill = document.getElementById('objectDepartment');
    var conditionElement;
    for (var i = 0; i < elementForFill.childNodes.length; i++) {
        conditionElement = elementForFill.childNodes[i];
        if (conditionElement.classList === undefined) {/*NOP*/
        }
        /* else if (conditionElement.classList.contains("id")) {
         conditionElement.value = worker.getID();
         }*/
        else if (conditionElement.classList.contains("name")) {
            conditionElement.value = department.getName();
        }
    }
    var workerList = department.getWorkersList();
    for (var i = 0; i < workerList.length; i++) {
        createNewTableWorkersRow(tableWorker, cloneRowWorker, workerList[i], "worker_");
    }
}

function createNewTableWorkersRow(tableForAdding, cloneRowWorker, worker, beginID) {
    var itNewRow = false;
    var newRow = document.getElementById(beginID + worker.getID());
    if (newRow === undefined || newRow == null) {
        newRow = cloneRowWorker.cloneNode(true);
        newRow.id = beginID + worker.getID();
        newRow.classList.remove("invisible");
        newRow.classList.remove("lineForCopy");
        itNewRow = true;
    }
    else {/*NOP*/
    }
    var child;
    for (var i = 0; i < newRow.childNodes.length; i++) {
        child = newRow.childNodes[i];
        if (child.classList === undefined) {/*NOP*/
        }
        else if (child.classList.contains('workID')) {
            child.innerHTML = worker.getID();
        }
        else if (child.classList.contains('workName')) {
            child.innerHTML = worker.getName();
        }
        else if (child.classList.contains('workLogin')) {
            child.innerHTML = worker.getLogin();
        }
        else if (child.classList.contains('workDep')) {
            child.innerHTML = worker.getDepartmentName();
        }
    }
    if (itNewRow) {
        tableForAdding.appendChild(newRow);
    }
    else {/*NOP*/
    }
}

function deleteObject() {
    var allSelectedElements = document.getElementsByClassName("selected");
    if (allSelectedElements.length == 0 || allSelectedElements == null) {
        openExceptionForm("no line is selected!");
    }
    else if (allSelectedElements.length > 1) {
        openExceptionForm("more then one line is selected!");
    }
    else {
        openQuestionForm('Do you really want to delete selected object?!');
    }
}

function deleteDepartment() {
    var selectedElement = document.getElementsByClassName("selected")[0];
    var department = new createDepartmentObject();
    var conditionElement;
    for (i = 0; i < selectedElement.childNodes.length; i++) {
        conditionElement = selectedElement.childNodes[i];

        if (conditionElement.classList === undefined) {/*NOP*/
        }
        else if (conditionElement.classList.contains("depID")) {
            department.setID(conditionElement.innerHTML);
            break;
        }
    }
    selectedDepartment = null;
    sendAjaxForDeleteObjectForm(department);
    closeQuestionForm();
}

function sendAjaxForDeleteObjectForm(department) {
    var url = urlForAjax + "?id=" + department.getID();
    sendAjaxDeletingByID("DELETE", url);
}

function sendAjaxDeletingByID(type, url) {
    mXMLHttpRequest.open(type, url, true);
    mXMLHttpRequest.onreadystatechange = getAnswerFromServerOnDeleteID;
    mXMLHttpRequest.send(null);
}

function getAnswerFromServerOnDeleteID() {
    if (mXMLHttpRequest.readyState == 4) {
        if (mXMLHttpRequest.status == 200) {
            parseWorkerForDelete(mXMLHttpRequest.responseXML);
        }
        else {/*NOP*/
        }
    }
    else {/*NOP*/
    }
}

function parseWorkerForDelete(responseXML) {
    if (responseXML == null) {
        return false;
    } else {
        var departmentXML = responseXML.getElementsByTagName("department")[0];
        if (departmentXML === undefined) {
            /*NOP*/
        }
        else {
            var department = createDepartmentByXML(departmentXML);
            deleteRowObject(department);
            return;
        }
        var exceptionXML = responseXML.getElementsByTagName("exceptionForView")[0];
        if (exceptionXML === undefined) {/*NOP*/
        }
        else {
            var exceptionFromServer = createExceptionByXML(exceptionXML);
            openExceptionForm(exceptionFromServer.getExceptionMessage());
            return;
        }
    }
}

function deleteRowObject(department) {
    var elementForRemove = document.getElementById(department.getID());
    elementForRemove.parentNode.removeChild(elementForRemove);

}

function closeObjectDepartmentForm() {
    selectedDepartment = null;
    closeObjectForm("objectDepartment");
    clearTable(tableWorker);

}

function closeContentAllWorkersForm() {
    closeFormForAdd("objectsFormForAdd");
    clearTable(tableForChooseWorker);

}

function closeExceptionDepartmentForm() {
    selectedWorker = null;
    closeExceptionForm();
}

function closeQuestionDepartmentForm() {
    selectedWorker = null;
    closeQuestionForm();
}

function search(valueForSearch) {
    if (valueForSearch == null || valueForSearch.trim() == "") {
        getAllDepartments();
    }
    else {
        var typeSearch = document.getElementById("searchType").value;
        sendAjax("GET", urlForAjax + "?action=findByPartOf&searchType=" + typeSearch + "&valueForSearch=" + valueForSearch);
    }

}

function openWorkersFormForAdd() {
    var elementForInvisible = document.getElementById('objectsFormForAdd');
    getAllWorkers();
    changeVisible(elementForInvisible, true);
}

function deleteWorkerRow() {
    var allSelectedElements = document.getElementsByClassName("selectedWorker");
    if (allSelectedElements.length == 0 || allSelectedElements == null) {
        openExceptionForm("no line is selected!");
    }
    else if (allSelectedElements.length > 1) {
        openExceptionForm("more then one line is selected!");
    }
    else {

        openQuestionFormAndSetOnclickAction('Do you really want to delete selected row?!', deleteRowFromTableWorkers);
    }
}

function deleteRowFromTableWorkers() {
    var selectedElement = document.getElementsByClassName("selectedWorker")[0];
    var conditionElement, nameForFind, position, idForFind = selectedElement.id;
    position = idForFind.indexOf("_") + 1;
    if (position > 0) {
        idForFind = idForFind.substr(position)
    }
    /*for (i = 0; i < selectedElement.childNodes.length; i++) {
     conditionElement = selectedElement.childNodes[i];

     if (conditionElement.classList === undefined) {/!*NOP*!/
     }
     else if (conditionElement.classList.contains("workName")) {
     nameForFind = conditionElement.innerHTML;
     break;
     }
     }*/
    for (var i = 0; i < selectedDepartment.getWorkersList().length; i++) {
        /*if (selectedDepartment.getWorkersList()[i].getName() == nameForFind) {
         selectedDepartment.getWorkersList().splice(i, 1);
         }*/
        if (selectedDepartment.getWorkersList()[i].getID() == idForFind) {
            selectedDepartment.getWorkersList().splice(i, 1);
        }

    }
    selectedElement.parentNode.removeChild(selectedElement);
    closeQuestionForm();
}

function getAllWorkers() {
    sendAjax("GET", urlForAjax + "?action=getAllWorkers");
}

function fillTableWorkersForAdd(workersHolder) {
    var workers = workersHolder.getElementsByTagName('workers');
    if (workers.length > 0) {
        for (var loop = 0; loop < workers.length; loop++) {
            var workerXml = workers[loop];
            var worker = createWorkerByXML(workerXml);
            createNewTableWorkersRow(tableForChooseWorker, cloneRowForChooseWorker, worker, "workerForAdd_")
        }
    }
}

function searchWorkers(valueForSearch) {
    if (valueForSearch == null || valueForSearch.trim() == "") {
        getAllWorkers();
    }
    else {
        var typeSearch = document.getElementById("searchWorkerType").value;
        sendAjax("GET", urlForAjax + "?action=findWorker&searchType=" + typeSearch + "&valueForSearch=" + valueForSearch);
    }

}
