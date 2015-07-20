/**
 * Created by Vlad on 04.07.2015.
 */

var mXMLHttpRequest = new XMLHttpRequest();
var cloneRow;
var table;
var urlForAjax = "worker.do";
var selectedWorker = null;
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
        else if (name.length < 3) {
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

function isLoginValid(login) {
    var itValid = true;
    if (login == null || login.trim() == "") {
        itValid = false;
        writeMessageAboutValidation('field "login" need to be fill', "login");
    }
    else if (!/^[a-zA-Z]+[A-Za-z0-9\s.]*/.test(login)) {
        itValid = false;
        writeMessageAboutValidation('field "login" contains some illegal symbols', "login");
    }
    return itValid;
}

function isPasswordValid(pas) {
    var itValid = true;
    if (pas == null) {
        itValid = false;
        writeMessageAboutValidation('field "password" need to be fill', "password");
    }
    else {
        pas = pas.trim();
        if (pas == "") {
            itValid = false;
            writeMessageAboutValidation('field "password" need to be fill', "password");
        }
        else if (pas.length < 3) {
            itValid = false;
            writeMessageAboutValidation('length field "password" need to be more 3', "password");
        }
        else if (!/[A-Za-z0-9]*/.test(pas)) {
            itValid = false;
            writeMessageAboutValidation('field "password" contains some illegal symbols', "password");
        }

    }
    return itValid;
}

function isConfirmPasswordValid(password, confirmPassword) {
    var itValid = true;
    if (password != confirmPassword) {
        itValid = false;
        writeMessageAboutValidation("confirmPassword is wrong", "confirmPassword");
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
    getAllWorkers();
    initGlobalVariable();
}

function initGlobalVariable() {
    if (cloneRow == null) {
        cloneRow = document.getElementById("lineForCopy");
    }
    if (table == null) {
        table = document.getElementById("table1");
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
        var workers = responseXML.getElementsByTagName("workersHolder")[0];
        if (workers === undefined) {
            /*NOP*/
        }
        else {
            clearTable();
            fillTable(workers);
            return;

        }
        var workerXML = responseXML.getElementsByTagName("worker")[0];
        if (workerXML === undefined) {
            /*NOP*/
        }
        else {
            selectedWorker = createWorkerByXML(workerXML);
            fillObjectForm(selectedWorker);
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

function clearTable() {
    if (table.getElementsByTagName("tr").length > 1) {
        var child;
        for (var loop = table.childNodes.length - 1; loop >= 0; loop--) {
            child = table.childNodes[loop];
            if (child.id != "lineForCopy") {
                table.removeChild(child);
            }
        }
    }
}

function fillTable(workersHolder) {
    var workers = workersHolder.getElementsByTagName('workers');
    if (workers.length > 0) {
        for (var loop = 0; loop < workers.length; loop++) {
            var workerXml = workers[loop];
            var worker = createWorkerByXML(workerXml);
            createNewTableRow(worker);
        }
    }
}

function createNewTableRow(worker) {
    var itNewRow = false;
    var newRow = document.getElementById(worker.getID());
    if (newRow === undefined || newRow == null) {
        newRow = cloneRow.cloneNode(true);
        newRow.id = worker.getID();
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
        table.appendChild(newRow);
    }
    else {/*NOP*/
    }
}

function getAllWorkers() {
    sendAjax("GET", urlForAjax + "?action=getAll");
}

function createUpdateWorker() {
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
            if (newWorker.getPassword() != conditionElement.value) {
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

    }
    if (newWorker.objectValidation()) {
        //selectedWorker = null;
        sendAjaxFromWorkerObject(newWorker);
    }
}

function sendAjaxFromWorkerObject(worker) {
    if (worker.getID() === undefined || worker.getID() == null | worker.getID() == '') {
        sendAjaxForCreateWorker(worker);

    }
    else {
        sendAjaxForUpdateWorker(worker);
    }

}

function sendAjaxForCreateWorker(worker) {
    var url = urlForAjax + "?name=" + worker.getName() + "&login=" + worker.getLogin() + "&password=" + worker.getPassword();
    sendAjaxForWorkerChange("PUT", url);
}

function sendAjaxForUpdateWorker(worker) {
    var url = urlForAjax;
    url = url + "?id=" + worker.getID() + "&objVersion=" + worker.getObjectVersion() + "&depName=" + worker.getDepartmentName() +
    "&name=" + worker.getName() + "&login=" + worker.getLogin() + "&password=" + worker.getPassword();
     sendAjaxForWorkerChange("POST", url);
}

function sendAjaxForWorkerChange(type, url) {
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
        var workers = responseXML.getElementsByTagName("workersHolder")[0];
        if (workers === undefined) {
            /*NOP*/
        }
        else {
            fillTable(workers);
            selectedWorker = null;
            closeObjectForm('objectWorker');
            return;

        }
        var workerXML = responseXML.getElementsByTagName("worker")[0];
        if (workerXML === undefined) {
            /*NOP*/
        }
        else {
            var newWorker = createWorkerByXML(workerXML);
            createNewTableRow(newWorker);
            selectedWorker = null;
            closeObjectForm('objectWorker');
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

function selectRow(objectForSelect) {
    if (objectForSelect.classList.contains("selected")) {
        openSelectObjectForm();
    }
    else {
        removeSelectionFromAll("selected");
        objectForSelect.classList.add("selected");
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
    var newWorker = new createWorkerObject();
    var conditionElement;
    for (var i = 0; i < selectedElement.childNodes.length; i++) {
        conditionElement = selectedElement.childNodes[i];

        if (conditionElement.classList === undefined) {/*NOP*/
        }
        else if (conditionElement.classList.contains("workID")) {
            newWorker.setID(conditionElement.innerHTML);
            break;
        }
    }
    sendAjaxForFillObjectForm(newWorker);
}

function sendAjaxForFillObjectForm(worker) {
    var url = urlForAjax + "?action=getByID&id=" + worker.getID();
    sendAjax("GET", url);
}

function fillObjectForm(worker) {
    var elementForFill = document.getElementById('objectWorker');
    var conditionElement;
    for (var i = 0; i < elementForFill.childNodes.length; i++) {
        conditionElement = elementForFill.childNodes[i];
        if (conditionElement.classList === undefined) {/*NOP*/
        }
        /* else if (conditionElement.classList.contains("id")) {
         conditionElement.value = worker.getID();
         }*/
        else if (conditionElement.classList.contains("name")) {
            conditionElement.value = worker.getName();
        }
        else if (conditionElement.classList.contains("login")) {
            conditionElement.value = worker.getLogin();
        }
        else if (conditionElement.classList.contains("password")) {
            conditionElement.value = worker.getPassword();
        }
        /* else if (conditionElement.classList.contains("objectVersion")) {
         conditionElement.value = worker.getObjectVersion();
         }
         else if (conditionElement.classList.contains("depName")) {
         conditionElement.value = worker.getDepartmentName();
         }*/
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
    for (var i = 0; i < selectedElement.childNodes.length; i++) {
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
    var url = urlForAjax + "?id=" + worker.getID();
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

function closeObjectWorkerForm() {
    selectedWorker = null;
    closeObjectForm('objectWorker');
}

function closeExceptionWorkerForm() {
    selectedWorker = null;
    closeExceptionForm();
}

function closeQuestionWorkerForm() {
    selectedWorker = null;
    closeQuestionForm();
}

function search(valueForSearch) {
    if (valueForSearch == null || valueForSearch.trim() == "") {
        getAllWorkers();
    }
    else {
        var typeSearch = document.getElementById("searchType").value;
        sendAjax("GET", urlForAjax + "?action=findByPartOf&searchType=" + typeSearch + "&valueForSearch=" + valueForSearch);
    }

}

