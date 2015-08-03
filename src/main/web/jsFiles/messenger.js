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
var UUIDGenerator = createUUID();
var currentChat;
var cookieValueWorker = getCookie('worker');
var wsUri = "ws://" + document.location.host + document.location.pathname + "/chat";


function createUUID() {
    var self = {};
    var lut = [];
    for (var i = 0; i < 256; i++) {
        lut[i] = (i < 16 ? '0' : '') + (i).toString(16);
    }
    self.generate = function () {
        var d0 = Math.random() * 0xffffffff | 0;
        var d1 = Math.random() * 0xffffffff | 0;
        var d2 = Math.random() * 0xffffffff | 0;
        var d3 = Math.random() * 0xffffffff | 0;
        return lut[d0 & 0xff] + lut[d0 >> 8 & 0xff] + lut[d0 >> 16 & 0xff] + lut[d0 >> 24 & 0xff] + '-' +
            lut[d1 & 0xff] + lut[d1 >> 8 & 0xff] + '-' + lut[d1 >> 16 & 0x0f | 0x40] + lut[d1 >> 24 & 0xff] + '-' +
            lut[d2 & 0x3f | 0x80] + lut[d2 >> 8 & 0xff] + '-' + lut[d2 >> 16 & 0xff] + lut[d2 >> 24 & 0xff] +
            lut[d3 & 0xff] + lut[d3 >> 8 & 0xff] + lut[d3 >> 16 & 0xff] + lut[d3 >> 24 & 0xff];
    }
    return self;
}

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
        this.id = chatID;
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
    this.getWorkers = function () {
        return this.workers;
    }
}

function createChatObjectFromJson(chatJson) {
    var chat = new createChatObject();
    chat.setId(chatJson.id);
    chat.setName(chatJson.name);
    for (var i = 0; i < chatJson.workers.length; i++) {
        if (chatJson.workers.length = 2) {
            if (chatJson.workers[i].id !== cookieValueWorker) {
                chat.setName(chatJson.workers[i].name);
            }
        }
        chat.addWorker(createWorkerFromJson(chatJson.workers[i]));
    }
    for (var j = 0; j < chatJson.messages.length; j++) {
        chat.addMessage(createMessageObjectFromJson(chatJson.messages[j]));
    }
    return chat;
}

function createWorkerObjectForChat() {
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
        return this.name;
    }
    this.setLogin = function (login) {
        this.login = login;
    }
    this.getLogin = function () {
        return this.login;
    }
}

function createWorkerFromJson(workerJson) {
    var worker = new createWorkerObjectForChat();
    worker.setID(workerJson.id);
    worker.setName(workerJson.name);
    worker.setLogin(workerJson.login);
    return worker;
}

function createWorkerToObject(worker, itNewMessage, itDeleted) {
    this.workerTo = worker;
    this.itNewMessage = itNewMessage;
    this.itDeleted = itDeleted;
}

function createMessageObject() {
    this.id;
    this.chatID;
    this.workerFrom;
    this.message;
    this.dateMessage;
    this.uuidFromBrowser;
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

    this.addWorkersTo = function (workerTo) {
        this.workersTo.push(workerTo);
    }
    this.getWorkersTo = function () {
        return this.workersTo;
    }

    this.setUuidFromBrowser = function (UUIDFromBrowser) {
        this.uuidFromBrowser = UUIDFromBrowser;
    }
    this.generateUuidFromBrowser = function () {
        this.uuidFromBrowser = UUIDGenerator.generate();
    }
    this.getUuidFromBrowser = function () {
        return this.uuidFromBrowser;
    }

    this.isItNewMessage = function () {
        var itIsNew = false;
        for (var i = 0; i < this.workersTo.length; i++) {
            if (this.workersTo[i].itNewMessage) {
                itIsNew = true;
                break;
            }
        }
        return itIsNew;
    }
    this.getWorkersWhichDontRead = function () {
        var workers = [];
        for (var i = 0; i < this.workersTo.length; i++) {
            if (this.workersTo[i].itNewMessage
                && this.workersTo[i].workerTo.getID() != cookieValueWorker) {
                workers.push(this.workersTo[i].workerTo);
            }
        }
        return workers;
    }

}

function createMessageObjectFromJson(messageJson) {
    var message = new createMessageObject();
    message.setID(messageJson.id);
    message.setChatID(messageJson.chatID);
    message.setMessage(messageJson.message);
    message.setWorkerFrom(createWorkerFromJson(messageJson.workerFrom));
    message.setDateMessage(new Date(messageJson.dateMessage));
    message.setUuidFromBrowser(messageJson.uuidFromBrowser);
    for (var i = 0; i < messageJson.workersTo.length; i++) {
        var worker = createWorkerFromJson(messageJson.workersTo[i].workerTo);
        var workerTo = new createWorkerToObject(worker, messageJson.workersTo[i].itNewMessage, messageJson.workersTo[i].itDeleted);
        message.addWorkersTo(workerTo);
    }

    return message;
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
    if (output === undefined) {
        setOutput();
    }
    var json = JSON.parse(str);
    console.log(json);
    if (json.departments !== undefined) {
        fillAllDepartments(json.departments);
    }
    else if (json.chatsNewMessages !== undefined) {
        setNewMessagesForChats(json.chatsNewMessages);
    }
    else if (json.online !== undefined) {
        changeOnlineStatus(json);
    }
    else if (json.messages !== undefined) {
        var chat = createChatObjectFromJson(json);
        changeChat(chat);
    }
    else if (json.uuidFromBrowser !== undefined) {
        var message = createMessageObjectFromJson(json);
        if (currentChat != null && currentChat.getID() == message.getChatID()) {
            writeMessageToScreen(message);
        }
        if (cookieValueWorker != message.getWorkerFrom().getID()) {
            addNewMessage(message);
        }
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

        var workers = departments[i].workers;
        var ulForAppend = newGroup.getElementsByTagName("ul")[0];
        ulForAppend.id = "ul_Dep_id_" + (i + 1);
        for (var j = 0; j < workers.length; j++) {
            var newWorker = elementWorkerForClone.cloneNode(true);
            newWorker.classList.remove("CloneClass");
            newWorker.id = "worker_" + workers[j].worker.id;
            newWorker.getElementsByClassName("workerFromDepartment")[0].innerHTML = "<span></span>" + workers[j].worker.name;

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

function setNewMessagesForChats(chatsMap) {
    for (var i = 0; i < chatsMap.length; i++) {
        var idForChange = 'chat_' + chatsMap[i].chat.id;
        if (document.getElementById(idForChange) == null) {
            idForChange = 'worker_' + chatsMap[i].chat.workers[0].id;
        }
        writeToScreenAboutCountNewMess(idForChange, parseInt(chatsMap[i].countNewMess));
    }
}

function writeToScreenAboutCountNewMess(idForWrite, countNewMes) {
    if (document.getElementById(idForWrite) != null) {
        var elements = document.getElementById(idForWrite).childNodes, el, i = 0;
        while (el = elements[i++]) {
            if (el.classList === undefined) {
                continue;
            }
            if (el.classList.contains("MessageCount")) {
                el.innerHTML = (el.innerHTML === undefined || el.innerHTML == null || el.innerHTML == '') ? countNewMes : parseInt(el.innerHTML, 10) + countNewMes;
                break;
            }
        }
        var parentsBrothers = document.getElementById(idForWrite).parentNode.parentNode.childNodes, element, j = 0;
        while (element = parentsBrothers[j++]) {
            if (element.classList === undefined) {
                continue;
            }
            if (element.classList.contains("MessageCount")) {
                element.innerHTML = (element.innerHTML === undefined || element.innerHTML == null || element.innerHTML == '') ? countNewMes : parseInt(element.innerHTML, 10) + countNewMes;
                break;
            }

        }
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
    var workerFrom = new createWorkerObjectForChat();
    workerFrom.setID(parseInt(cookieValueWorker, 10));
    var workerTo = new createWorkerObjectForChat();
    workerTo.setID(parseInt(idUserTo.substr((idUserTo.indexOf('_') + 1)), 10));
    chat.addWorker(workerFrom);
    chat.addWorker(workerTo);
    doSend(JSON.stringify(chat));

}

function changeChat(chat) {
    currentChat = chat;
    informationAboutMessagesInChat.thereAreAnyMessages = chat.thereSomeMoreMessages;
    var itsPreviousMessage = false;
    var messages = chat.getMessages();
    if (output.id != 'usersChat_' + chat.getID()) {
        removeChildrenRecursively(output);
        removeChildrenRecursively(document.getElementById('information_about_chat'));
        output.id = 'usersChat_' + chat.getID();
        setInformationAboutChat(chat.getWorkers());
        messages.sort(function (a, b) {
            return a.dateMessage.getTime() - b.dateMessage.getTime();
        });
    }
    else {
        messages.sort(function (a, b) {
            return b.dateMessage.getTime() - a.dateMessage.getTime();
        });
        itsPreviousMessage = true;
    }
    for (var i = 0; i < messages.length; i++) {
        writeMessageToScreen(messages[i], itsPreviousMessage, true);
    }
}

function removeChildrenRecursively(node) {
    if (!node) return;
    while (node.hasChildNodes()) {
        removeChildrenRecursively(node.firstChild);
        node.removeChild(node.firstChild);
    }
}

function setInformationAboutChat(informationWorkers) {
    for (var i = 0; i < informationWorkers.length; i++) {
        /*if (valueIdForCheck == null && informationWorkers[i].id != cookieValue) {
         valueIdForCheck = "lastUser_" + json.userList[i].id;
         userTo = json.userList[i];
         chatName = json.userList[i].name;
         }*/
        var pre = document.createElement("p");
        pre.innerHTML = informationWorkers[i].name;
        pre.id = 'chatsUsers_' + informationWorkers.id;
        document.getElementById('information_about_chat').appendChild(pre);
    }
}

function writeMessageToScreen(message, beggining, fromServer) {
    if (output === undefined) {
        setOutput();
    }
    var itsNewMessage = false;
    var messageToScreen = document.getElementById("message_" + message.getUuidFromBrowser());
    if (messageToScreen == null) {
        messageToScreen = document.getElementById("message_" + message.getUuidFromBrowser() + "_" + message.getID());
    }
    if (messageToScreen == null) {
        messageToScreen = cloneMassage.cloneNode(true);
        itsNewMessage = true;
    }
    //Something can change (newMessge and worker which don't read
    if (message.isItNewMessage()) {
        messageToScreen.classList.add('NewMessage');
    }


    var workersWhichDontRead = message.getWorkersWhichDontRead();

    var elementsForChange = messageToScreen.getElementsByClassName('WhoDontRead');
    var textForDontRead = "";
    if (workersWhichDontRead.length > 0) {
        textForDontRead = "Doesn't read: " + workersWhichDontRead[0].getLogin();
        for (var k = 1; k < workersWhichDontRead.length; k++) {
            textForDontRead = textForDontRead + "; " + workersWhichDontRead[k].getLogin();
        }
    }
    elementsForChange.innerHTML = textForDontRead;

    if (itsNewMessage) {
        messageToScreen.classList.remove("CloneClass");
        if (!fromServer) {
            messageToScreen.classList.add('SendingMessage');
            messageToScreen.id = "message_" + message.getUuidFromBrowser();
        }
        else {
            messageToScreen.id = "message_" + message.getUuidFromBrowser() + "_" + message.getID();
        }

        if (message.getWorkerFrom().getID() == cookieValueWorker) {
            messageToScreen.classList.add('MyMessage');
        }

        var options = {
            weekday: "long", year: "numeric", month: "short", hour12: false,
            day: "numeric", hour: "2-digit", minute: "2-digit", second: "2-digit"
        };
        var elements = messageToScreen.childNodes, el, i = 0;


        while (el = elements[i++]) {
            if (el.classList === undefined) {
                continue;
            }
            var text = null;

            if (el.classList.contains('WhoWright')) {
                text = message.getWorkerFrom().getName() + " " + (message.getDateMessage()).toLocaleTimeString(navigator.language, options);
            }
            else if (el.classList.contains('MessageText')) {
                text = message.getMessage();
            }
            if (text != null) {
                el.innerHTML = text;
            }
        }
        if (beggining) {
            output.insertBefore(messageToScreen, output.firstChild)
        }
        else {
            output.appendChild(messageToScreen);
            output.scrollTop = output.scrollHeight;
        }
        if (informationAboutMessagesInChat.minDateInChat > message.getDateMessage()) {
            informationAboutMessagesInChat.minDateInChat = message.getDateMessage();
        }
        informationAboutMessagesInChat.numbersMessagesInChat++;

    }
    else if (messageToScreen != null && fromServer) {
        if (messageToScreen.classList.contains("SendingMessage")) {
            messageToScreen.classList.remove("SendingMessage");
        }
        /* if (message.isExceptionWhenSending() && !messageToScreen.classList.contains("ExceptionMessage")) {
         messageToScreen.classList.add("ExceptionMessage");
         }
         else {*/
        messageToScreen.id = messageToScreen.id + "_" + message.getID();
        if (messageToScreen.classList.contains("ExceptionMessage")) {
            messageToScreen.classList.remove("ExceptionMessage");
        }
        if (informationAboutMessagesInChat.minDateInChat > message.getDateMessage()) {
            informationAboutMessagesInChat.minDateInChat = message.getDateMessage();
        }
        informationAboutMessagesInChat.numbersMessagesInChat++;
        /*message.removeFromLocalStorage();*/
        /*}*/
    }
}

function send_message() {
    if (output.id != 'usersChat_0') {
        var currentWorker;
        var mes = new createMessageObject();
        console.log(currentChat);
        for (var i = 0; i < currentChat.getWorkers().length; i++) {
            console.log(currentChat.getWorkers()[i]);
            if (currentChat.getWorkers()[i].getID() == cookieValueWorker) {
                currentWorker = currentChat.getWorkers()[i];
            }
            else {
                mes.addWorkersTo(new createWorkerToObject(currentChat.getWorkers()[i], true, false));
            }
        }
        mes.setChatID(currentChat.getID());
        mes.generateUuidFromBrowser();
        mes.setDateMessage(new Date());
        mes.setMessage(document.getElementById('textID').value);
        mes.setWorkerFrom(currentWorker);
        console.log(mes);
        var jsonStr = JSON.stringify(mes);
        /* mes.addToLocalStorage();*/
        writeMessageToScreen(mes);
        doSend(jsonStr);
    }
}


function functionOnScrollChat(div) {
    var scrolled = div.scrollTop;
    //when scrolled =0. then need more messages
    if (scrolled == 0 && informationAboutMessagesInChat.thereAreAnyMessages) {
        informationAboutMessagesInChat.howMuchScrollWas++;
        if (informationAboutMessagesInChat.howMuchScrollWas > 2 && informationAboutMessagesInChat.howMuchScrollWas < 5) {
            informationAboutMessagesInChat.howMuchMessagesWeNeedAfterScroll = 25;
        }
        else if (informationAboutMessagesInChat.howMuchScrollWas >= 5 && informationAboutMessagesInChat.howMuchScrollWas < 8) {
            informationAboutMessagesInChat.howMuchMessagesWeNeedAfterScroll = 35;
        }
        else if (informationAboutMessagesInChat.howMuchScrollWas >= 8) {
            informationAboutMessagesInChat.howMuchMessagesWeNeedAfterScroll = 50;
        }
        var json = JSON.stringify({
                "type": "MoreMessages",
                "operation": "gettingMoreMessageinChat",
                "chatID": parseInt(output.id.substring(output.id.indexOf("_") + 1, output.id.length), 10),
                "userFrom": cookieValue,
                "numberMessagesAlreadyInChat": informationAboutMessagesInChat.numbersMessagesInChat,
                "minDateInChat": informationAboutMessagesInChat.minDateInChat.getTime(),
                "howMuchWeNeed": informationAboutMessagesInChat.howMuchMessagesWeNeedAfterScroll
            }
        );
        doSend(json);

    }
}

function addNewMessage(message) {
    var idForChangeCountNewMEssage;
    if (message.getWorkersTo().length = 2) {
        idForChangeCountNewMEssage = 'worker_' + message.getWorkerFrom().getID();
    }
    else {
        idForChangeCountNewMEssage = 'chat_' + message.getID();
    }
    writeToScreenAboutCountNewMess(idForChangeCountNewMEssage, 1);
}

function readNewMessages() {
    if (output === undefined || output.id == 'usersChat_0') {
        return;
    }
    var elements = document.getElementsByClassName('NewMessage');
    for (var i = 0; i < elements.length; i++) {
        if (elements[i].classList.contains('MyMessage')) {
            continue;
        }
        if (checkIfElementInDivScope(output, elements[i])) {
            sendAboutReading(elements[i]);
        }
    }
}

function checkIfElementInDivScope(whereCheck, el) {
    var itIs = false;
    var scrollTop = whereCheck.scrollTop;
    var windowHeight = whereCheck.offsetHeight;
    var elementOffset = el.offsetTop;
    var elementHeight = el.offsetHeight;
    if (scrollTop <= (elementOffset - elementHeight) && (elementOffset - elementHeight <= (scrollTop + windowHeight))) {
        itIs = true;
    }
    return itIs;
}

function sendAboutReading(elem){
    var currentWorker;
    var mes = new createMessageObject();
    console.log(elem);
    for (var i = 0; i < currentChat.getWorkers().length; i++) {
        if (currentChat.getWorkers()[i].getID() != cookieValueWorker) {
            currentWorker = currentChat.getWorkers()[i];
        }
        else {
            mes.addWorkersTo(new createWorkerToObject(currentChat.getWorkers()[i], false, false));
        }
    }
    mes.setChatID(currentChat.getID());
    mes.generateUuidFromBrowser();
    mes.setDateMessage(new Date());
    mes.setMessage(document.getElementById('textID').value);
    mes.setWorkerFrom(currentWorker);
    console.log(mes);
    var jsonStr = JSON.stringify(mes);
    //doSend(jsonStr);
}