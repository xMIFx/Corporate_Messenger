/**
 * Created by Vlad on 24.07.2015.
 */

//parseInt(chatId, 10);
var cloneMassage;
var output;
var informationAboutMessagesInChat = {
    numbersMessagesInChat: 0,
    thereAreAnyMessages: true,
    howMuchMessagesWeNeedAfterScroll: 15,
    howMuchScrollWas: 0,
    minDateInChat: new Date()
}
var currentChat;
var cookieValueWorker = getCookie('worker');
var wsUri = "ws://" + document.location.host + document.location.pathname + "/chat";

websocket = new WebSocket(wsUri);
websocket.onopen = function (evt) {
    onOpen(evt)
};
websocket.onmessage = function (evt) {
    onMessage(evt)
};
websocket.onerror = function (evt) {
    onError(evt)
};
function closeIt() {
    websocket.close();
}
window.onunload = closeIt;

// Objects begin

function createChatObject() {
    //this.type = "chat";
    this.id;
    this.name;
    this.messages = [];
    this.workers = [];

    this.setId = function (chatID) {
        this.id = id;
    }
    this.getID = function () {
        return this.id;
    }

    this.setName = function (name) {
        this.name = name;
    }
    this.getName = function () {
        return this.id;
    }

    this.addMessage = function (message) {
        this.messages.push(message);
    }
    this.removeMessage = function (message) {
        for (var i = 0; i < this.messages.length; i++) {
            if (this.messages[i].getID() == message.getID()) {
                this.messages.splice(i, 1);
                break;
            }
        }
        return this.messages.length;
    }
    this.getMessages = function () {
        return this.messages;
    }
    this.addWorker = function (worker) {
        this.workers.push(worker);
    }
}

function createWorkerObject() {
    this.id;
    this.name;
    this.login;

    this.setID = function (id) {
        this.id = id;
    }
    this.getID = function () {
        return this.id;
    }
    this.setName = function (name) {
        this.name = name;
    }
    this.getName = function () {
        return this.id;
    }
    this.setLogin = function (login) {
        this.login = login;
    }
    this.getLogin = function () {
        return this.login;
    }
}

function createWorkerToObject(workerTo, itNewMessage, itDeleted) {
    this.workerTo = workerTo;
    this.itNewMessage = itNewMessage;
    this.itDeleted = itDeleted;
}

function createMessageObject() {
    this.id;
    this.chatID;
    this.workerFrom;
    this.message;
    this.dateMessage;
    this.workersTo = [];

    this.setID = function (id) {
        this.id = id;
    }
    this.getID = function () {
        return this.id;
    }

    this.setChatID = function (chatID) {
        this.chatID = chatID;
    }
    this.getChatID = function () {
        return this.chatID;
    }

    this.setWorkerFrom = function (workerFrom) {
        this.workerFrom = workerFrom;
    }
    this.getWorkerFrom = function () {
        return this.workerFrom;
    }

    this.setMessage = function (message) {
        this.message = message;
    }
    this.getMessage = function () {
        return this.message;
    }
    this.setDateMessage = function (dateMessage) {
        this.dateMessage = dateMessage;
    }
    this.getDateMessage = function () {
        return this.dateMessage;
    }
    this.addWorkersTo = function (worker) {
        this.workersTo.push(new createWorkerToObject(worker, true, false));
    }
    this.addWorkersTo = function (worker, itNewMessage, itDeleted) {
        this.workersTo.push(new createWorkerToObject(worker, itNewMessage, itDeleted));
    }
    this.getWorkersTo = function () {
        return this.workersTo;
    }
}

//Object the end

function getCookie(name) {
    var matches = document.cookie.match(new RegExp(
        "(?:^|; )" + name.replace(/([\.$?*|{}\(\)\[\]\\\/\+^])/g, '\\$1') + "=([^;]*)"
    ));
    return matches ? decodeURIComponent(matches[1]) : undefined;
}

function setOutput() {
    cloneMassage = document.getElementById("cloneMessage");
    var elements = document.getElementById("output_box").childNodes, i = 0, el;
    var re = new RegExp('usersChat', 'g');

    while (el = elements[i++]) {
        if (el.id === undefined) {
            continue;
        }
        if (el.id.match(re)) {
            output = el;
            break;
        }
    }
}

function initOutput() {
    setOutput();
}

function onOpen(evt) {

}

function onMessage(evt) {
    parseJsonStr(evt.data);
}

function onError(evt) {
    writeToScreen('ERROR: ' + evt.data);
}

function doSend(message) {

    websocket.send(message);
}

function writeToScreen(message) {
    if (output === undefined) {
        setOutput();
    }
    var pre = document.createElement("p");
    pre.style.wordWrap = "break-word";
    //  pre.style.textAlign = "right"; if current user
    pre.innerHTML = message;

    output.appendChild(pre);
}

function parseJsonStr(str) {
    var json = JSON.parse(str);
    console.log(json);
    if (json.departments !== undefined) {
        fillAllDepartments(json.departments);
    }
    else if (json.online !== undefined) {
        changeOnlineStatus(json);
    }
}

function fillAllDepartments(departments) {
    var elementGroupForClone = document.getElementById("cloneGroup");
    var elementWorkerForClone = document.getElementById("cloneUser");
    var parentForAdding = document.getElementById("groups");
    clearAllChildFromElement(parentForAdding);
    for (var i = 0; i < departments.length; i++) {
        var newGroup = elementGroupForClone.cloneNode(true);
        newGroup.classList.remove("CloneClass");
        newGroup.id = "departmentId_" + (i + 1);
        newGroup.getElementsByClassName("gr")[0].innerHTML = departments[i].department.name;
        newGroup.getElementsByClassName("MessageCount")[0].innerHTML = departments[i].countNewMessages;


        var workers = departments[i].workers;
        var ulForAppend = newGroup.getElementsByTagName("ul")[0];
        ulForAppend.id = "ul_Dep_id_" + (i + 1);
        for (var j = 0; j < workers.length; j++) {
            var newWorker = elementWorkerForClone.cloneNode(true);
            newWorker.classList.remove("CloneClass");
            newWorker.id = "worker_" + workers[j].worker.id;
            newWorker.getElementsByClassName("workerFromDepartment")[0].innerHTML = "<span></span>" + workers[j].worker.name;
            newWorker.getElementsByClassName("MessageCount")[0].innerHTML = workers[j].countNewMessages;
            if (workers[j].online) {
                if (newWorker.classList.contains("offline")) {
                    newWorker.classList.remove("offline");
                }
                if (!newWorker.classList.contains("online")) {
                    newWorker.classList.add("online");
                }
            }
            else {
                if (newWorker.classList.contains("online")) {
                    newWorker.classList.remove("online");
                }
                if (!newWorker.classList.contains("offline")) {
                    newWorker.classList.add("offline");
                }
            }


            ulForAppend.appendChild(newWorker);

        }
        parentForAdding.appendChild(newGroup);
    }
}

function clearAllChildFromElement(element) {
    var elementsForClean = element.childNodes;
    for (var i = 0; i < elementsForClean.length; i++) {
        element.removeChild(elementsForClean[i]);
    }
}

function changeOnlineStatus(json) {
    var elementForChange = document.getElementById("worker_" + json.worker.id);
    if (elementForChange != null) {
        if (json.online) {
            if (elementForChange.classList.contains("offline")) {
                elementForChange.classList.remove("offline");
            }
            elementForChange.classList.add("online");
        }
        else {
            if (elementForChange.classList.contains("online")) {
                elementForChange.classList.remove("online");

            }
            elementForChange.classList.add("offline");
        }
    }
}

function functionChangingChat(idUserTo) {
    var elements = document.getElementsByClassName('user_ch'), el, i = 0;
    while (el = elements[i++]) {
        if (el.classList.contains('active')) {
            el.classList.remove('active')
        }
    }

    if (document.getElementById(idUserTo) != null) {
        document.getElementById(idUserTo).classList.add('active');
    }
    var chat = new createChatObject();
    var workerFrom = new createWorkerObject();
    workerFrom.setID(parseInt(cookieValueWorker, 10));
    var workerTo = new createWorkerObject();
    workerTo.setID(parseInt(idUserTo.substr((idUserTo.indexOf('_') + 1)), 10));
    chat.addWorker(workerFrom);
    chat.addWorker(workerTo);
    doSend(JSON.stringify(chat));

}