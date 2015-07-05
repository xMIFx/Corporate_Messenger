/**
 * Created by Vlad on 04.07.2015.
 */

var mXMLHttpRequest = new XMLHttpRequest();
var cloneRow;
var table;
var urlForAjax = "worker.do";
//Objects
function createWorkerObject() {
    this.id;
    this.name;
    this.login;
    this.password;
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
    this.setPassword = function (pas) {
        this.password = pas;
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
    this.getPassword = function () {
        return this.password;
    }

    this.getDepartmentName = function () {
        return this.departmentName;
    }
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

        }
        var workerXML = responseXML.getElementsByTagName("worker")[0];
        if (workerXML === undefined) {
            /*NOP*/
        }
        else {
            var newWorker = creatWorkerByXML(workerXML);
            createNewTableRow(newWorker);
            closeObjectForm();
        }

    }
}

function clearTable() {
    if (table.getElementsByTagName("tr").length > 1) {
        var child;
        for (loop = table.childNodes.length - 1; loop >= 0; loop--) {
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
        for (loop = 0; loop < workers.length; loop++) {
            var workerXml = workers[loop];
            var worker = creatWorkerByXML(workerXml);
            createNewTableRow(worker);
        }
    }
}

function creatWorkerByXML(workerXml) {
    var worker = new createWorkerObject();
    var id = workerXml.getElementsByTagName("id")[0];
    var name = workerXml.getElementsByTagName("name")[0];
    var login = workerXml.getElementsByTagName("login")[0];
    var pas = workerXml.getElementsByTagName("password")[0];
    (id === undefined) ? id = null : (id = id.childNodes[0].nodeValue);
    (name === undefined) ? name = null : (name = name.childNodes[0].nodeValue);
    (login === undefined) ? login = null : (login = login.childNodes[0].nodeValue);
    (pas === undefined) ? pas = null : (pas = pas.childNodes[0].nodeValue);
    worker.setID(id);
    worker.setName(name);
    worker.setLogin(login);
    worker.setPassword(pas);
    worker.setDepartmentName(null);
    return worker;
}

function createNewTableRow(worker) {
    var newRow = document.getElementById(worker.getID());
    if (newRow === undefined || newRow == null) {
        newRow = cloneRow.cloneNode(true);
        newRow.id = worker.getID();
        newRow.classList.remove("invisible");
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
    table.appendChild(newRow);
}

function getAllWorkers() {
    sendAjax("GET", urlForAjax + "?action=getAll");
}

function createNewWorker() {
    var elementNewObjectBlock = document.getElementById("newObjectWorker");
    var newWorker = new createWorkerObject();
    var conditionElement;
    for (i = 0; i < elementNewObjectBlock.childNodes.length; i++) {
        conditionElement = elementNewObjectBlock.childNodes[i];
        if (conditionElement.classList === undefined) {/*NOP*/
        }
        else if (conditionElement.classList.contains("name")) {
            newWorker.setName(conditionElement.value);
        }
        else if (conditionElement.classList.contains("login")) {
            newWorker.setLogin(conditionElement.value);
        }
        else if (conditionElement.classList.contains("password")) {
            newWorker.setPassword(conditionElement.value);
        }
    }
    sendAjaxFromWorkerObject(newWorker);
}

function sendAjaxFromWorkerObject(worker) {
    var url = urlForAjax;
    if (worker.getID() == undefined || worker.getID() == null) {
        url = url + "?action=create";
    }
    else {
        url = url + "?action=update&id=" + worker.getID();
    }
    url = url + "&name=" + worker.getName() + "&login=" + worker.getLogin() + "&password=" + worker.getPassword();
    sendAjax("GET", url);
}
