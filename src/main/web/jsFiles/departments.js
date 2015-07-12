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
    this.password;
    this.departmentName;
    this.objectVersion;
    this.confirmPassword;
    this.needCheckPassword;

    this.setID = function (ID) {
        this.id = ID;
    }
    this.setName = function (name) {
        this.name = name;
    }
    this.setLogin = function (login) {
        this.login = login;
    }
    this.setPassword = function (pas) {
        this.password = pas;
    }

    this.setDepartmentName = function (depName) {
        this.departmentName = depName;
    }

    this.setConfirmPassword = function (confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    this.setObjectVersion = function (objVers) {
        this.objectVersion = objVers
    }

    this.checkPassword = function (check) {
        this.needCheckPassword = check;
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
    this.getPassword = function () {
        return this.password;
    }

    this.getDepartmentName = function () {
        return this.departmentName;
    }

    this.getConfirmPassword = function () {
        return this.confirmPassword;
    }

    this.getObjectVersion = function () {
        return this.objectVersion;
    }

    this.isNeedCheckPassword = function () {
        return this.needCheckPassword;
    }

    this.objectValidation = function () {
        var itsOk = true;
        if (this.isNeedCheckPassword()) {
            itsOk = isConfirmPasswordValid(this.password, this.confirmPassword);
        }
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
        if (itsOk) {
            itsOk = isPasswordValid(this.password);
        }
        else {
            isPasswordValid(this.password);
        }
        return itsOk;
    }
}

function createDepartmentObject() {
    this.id;
    this.name;
    this.countWorkers;
    this.workersList;
    this.objectVersion;

    this.setID = function (ID) {
        this.id = ID;
    }

    this.setName = function (name) {
        this.name = name;
    }

    this.setCountWorkers = function (countWorkers) {
        this.countWorkers = countWorkers;
    }

    this.setWorkersList = function (workerList) {
        this.workersList = workerList;
    }

    this.setObjectVersion = function (objectVersion) {
        this.objectVersion = objectVersion;
    }

    this.getID = function () {
        return this.id;
    }

    this.getName = function () {
        return this.name;
    }

    this.getCountWorkers = function () {
        return this.countWorkers;
    }

    this.getWorkersList = function () {
        return this.workersList;
    }

    this.getObjectVersion = function () {
        return this.objectVersion;
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
        workerList = null : (workerList = getWorkers(workerList));
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
    var pas = workerXml.getElementsByTagName("password")[0];
    var depName = workerXml.getElementsByTagName("departmentName")[0];
    var objVersion = workerXml.getElementsByTagName("objectVersion")[0];
    (id === undefined || id.childNodes === undefined || id.childNodes[0] === undefined) ?
        id = null : (id = id.childNodes[0].nodeValue);
    (name === undefined || name.childNodes === undefined || name.childNodes[0] === undefined) ?
        name = null : (name = name.childNodes[0].nodeValue);
    (login === undefined || login.childNodes === undefined || login.childNodes[0] === undefined) ?
        login = null : (login = login.childNodes[0].nodeValue);
    (pas === undefined || pas.childNodes === undefined || pas.childNodes[0] === undefined) ?
        pas = null : (pas = pas.childNodes[0].nodeValue);
    (depName === undefined || depName.childNodes === undefined || depName.childNodes[0] === undefined) ?
        depName = null : (depName = depName.childNodes[0].nodeValue);
    (objVersion === undefined || objVersion.childNodes === undefined || objVersion.childNodes[0] === undefined) ?
        objVersion = null : (objVersion = objVersion.childNodes[0].nodeValue);
    worker.setID(id);
    worker.setName(name);
    worker.setLogin(login);
    worker.setPassword(pas);
    worker.setDepartmentName(depName);
    worker.setObjectVersion(objVersion);
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
            var newDepartment = createDepartmentByXML(departmentXML);
            createNewTableRow(newDepartment);
            closeObjectForm('objectDepartment');
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

function clearTable(tableForClean) {
    if (tableForClean.getElementsByTagName("tr").length > 1) {
        var child;
        for (var loop = tableForClean.childNodes.length - 1; loop >= 0; loop--) {
            child = tableForClean.childNodes[loop];
            if (child.id != "lineForCopy") {
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
    var newWorker;
    if (selectedWorker == null) {
        newWorker = new createWorkerObject();
    }
    else {
        newWorker = selectedWorker;
    }
    var elementNewObjectBlock = document.getElementById("objectWorker");
    var conditionElement;
    for (var i = 0; i < elementNewObjectBlock.childNodes.length; i++) {
        conditionElement = elementNewObjectBlock.childNodes[i];
        if (conditionElement.classList === undefined) {/*NOP*/
        }
        /*  else if (conditionElement.classList.contains("id")) {
         newWorker.setID(conditionElement.value);
         }*/
        else if (conditionElement.classList.contains("name")) {
            newWorker.setName(conditionElement.value.trim());
        }
        else if (conditionElement.classList.contains("login")) {
            newWorker.setLogin(conditionElement.value.trim());
        }
        else if (conditionElement.classList.contains("password")) {
            if (selectedWorker.getPassword() != conditionElement.value) {
                newWorker.checkPassword(true);
            }
            else {
                newWorker.checkPassword(false);
            }
            newWorker.setPassword(conditionElement.value);
        }
        else if (conditionElement.classList.contains("confirmPassword")) {
            newWorker.setConfirmPassword(conditionElement.value);
        }
        /*   else if (conditionElement.classList.contains("objectVersion")) {
         newWorker.setObjectVersion(conditionElement.value);
         }
         else if (conditionElement.classList.contains("depName")) {
         newWorker.setDepartmentName(conditionElement.value);
         }*/
    }
    if (newWorker.objectValidation()) {
        selectedWorker = null;
        sendAjaxFromWorkerObject(newWorker);
    }
}

function sendAjaxFromWorkerObject(worker) {
    var url = urlForAjax;
    if (worker.getID() == undefined || worker.getID() == null || worker.getID() == '') {
        url = url + "?action=create";
    }
    else {
        url = url + "?action=update&id=" + worker.getID() + "&objVersion=" + worker.getObjectVersion() + "&depName=" + worker.getDepartmentName();
    }
    url = url + "&name=" + worker.getName() + "&login=" + worker.getLogin() + "&password=" + worker.getPassword();
    sendAjax("GET", url);
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
        objectForSelect.classList.remove("selectedWorker");;
    }
    else {
        removeSelectionFromAll("selectedWorker");
        objectForSelect.classList.add("selectedWorker");
    }
}

function selectWorkerRowForChoose(objectForSelect) {
    if (objectForSelect.classList.contains("selectedWorkerForChoose")) {

    }
    else {
        removeSelectionFromAll("selectedWorkerForChoose");
        objectForSelect.classList.add("selectedWorkerForChoose");
    }
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
    sendAjaxGettingByID("GET", url);
}

function sendAjaxGettingByID(type, url) {
    mXMLHttpRequest.open(type, url, true);
    mXMLHttpRequest.onreadystatechange = getAnswerFromServerOnID;
    mXMLHttpRequest.send(null);
}

function getAnswerFromServerOnID() {
    if (mXMLHttpRequest.readyState == 4) {
        if (mXMLHttpRequest.status == 200) {
            parseWorkerForFillForm(mXMLHttpRequest.responseXML);
        }
        else {/*NOP*/
        }
    }
    else {/*NOP*/
    }
}

function parseWorkerForFillForm(responseXML) {
    if (responseXML == null) {
        return false;
    } else {
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
    }
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
        createNewTableWorkersRow(tableWorker, cloneRowWorker, workerList[i]);
    }
}

function createNewTableWorkersRow(tableForAdding, cloneRowWorker, worker) {
    var itNewRow = false;
    var newRow = document.getElementById("worker_" + worker.getID());
    if (newRow === undefined || newRow == null) {
        newRow = cloneRowWorker.cloneNode(true);
        newRow.id = "worker_" + worker.getID();
        newRow.classList.remove("invisible");
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

function deleteWorker() {
    var selectedElement = document.getElementsByClassName("selected")[0];
    var newWorker = new createWorkerObject();
    var conditionElement;
    for (i = 0; i < selectedElement.childNodes.length; i++) {
        conditionElement = selectedElement.childNodes[i];

        if (conditionElement.classList === undefined) {/*NOP*/
        }
        else if (conditionElement.classList.contains("workID")) {
            newWorker.setID(conditionElement.innerHTML);
            break;
        }
    }
    selectedWorker = null;
    sendAjaxForDeleteObjectForm(newWorker);
    closeQuestionForm();
}

function sendAjaxForDeleteObjectForm(worker) {
    var url = urlForAjax + "?action=deleteByID&id=" + worker.getID();
    sendAjaxDeletingByID("GET", url);
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
        var workerXML = responseXML.getElementsByTagName("worker")[0];
        if (workerXML === undefined) {
            /*NOP*/
        }
        else {
            var worker = createWorkerByXML(workerXML);
            deleteRowObject(worker);
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

function deleteRowObject(worker) {
    var elementForRemove = document.getElementById(worker.getID());
    elementForRemove.parentNode.removeChild(elementForRemove);

}

function closeObjectDepartmentForm() {
    selectedDepartment = null;
    closeObjectForm("objectDepartment");
    clearTable(tableWorker);

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

function openWorkersFromForAdd() {
    var elementForInvisible = document.getElementById('objectsFormFroAdd');
    changeVisible(elementForInvisible, true);
}

