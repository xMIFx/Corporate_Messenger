/**
 * Created by Vlad on 04.07.2015.
 */
function changeVisible(elementForVisible, needVisualisation) {
    var needClass, removeClass;
    if (needVisualisation) {
        needClass = 'visible';
        removeClass = 'invisible';

    }
    else {
        needClass = 'invisible';
        removeClass = 'visible';
    }
    if (elementForVisible.classList.contains(removeClass)) {
        elementForVisible.classList.remove(removeClass);
    }
    if (!elementForVisible.classList.contains(needClass)) {
        elementForVisible.classList.add(needClass);
    }

}

function changeVisibleAnotherForm(classForInvisibleAllElement, exeptElem) {
    var elementsForInvisible = document.getElementsByClassName(classForInvisibleAllElement);
    var curElement;
    for (i = 0; i < elementsForInvisible.length; i++) {
        curElement = elementsForInvisible[i];
        if (curElement == exeptElem) {/*NOP*/
        }
        else {
            changeVisible(curElement, false);
        }
    }
}

function openLoginForm() {
    var elementForInvisible = document.getElementById('loginBackground');
    changeVisibleAnotherForm("blockingBackground", elementForInvisible);
    changeVisible(elementForInvisible, true);
}


function closeLoginForm() {
    var elementForInvisible = document.getElementById('loginBackground');
    changeVisible(elementForInvisible, false);
    var elementsForClean = document.getElementById('authorization');
    for (i = 0; i < elementsForClean.childNodes.length; i++) {
        conditionElement = elementsForClean.childNodes[i];
        if (conditionElement.classList === undefined) {/*NOP*/
        }
        else if (conditionElement.classList.contains("objectRow")) {
            conditionElement.value = '';
        }
    }
}

function openNewObjectForm() {
    var elementForInvisible = document.getElementById('objectsForm');
    changeVisibleAnotherForm("blockingBackground", elementForInvisible);
    changeVisible(elementForInvisible, true);
}


function closeObjectForm() {
    var elementForInvisible = document.getElementById('objectsForm');
    changeVisible(elementForInvisible, false);
    var elementsForClean = document.getElementById('objectWorker');
    for (i = 0; i < elementsForClean.childNodes.length; i++) {
        conditionElement = elementsForClean.childNodes[i];
        if (conditionElement.classList === undefined) {/*NOP*/
        }
        else if (conditionElement.classList.contains("objectRow")) {
            conditionElement.value = '';
        }
    }
}

function openExceptionForm(exceptionMessage) {
    var elementForInvisible = document.getElementById('exceptionForm');
    var elementMessage = document.getElementById('messageExc');
    elementMessage.innerHTML = exceptionMessage;
    changeVisibleAnotherForm("blockingBackground", elementForInvisible);
    changeVisible(elementForInvisible, true);
}

function closeExceptionForm() {
    var elementForInvisible = document.getElementById('exceptionForm');
    changeVisible(elementForInvisible, false);
}

function openQuestionForm(questionMessage) {
    var elementForInvisible = document.getElementById('questionForm');
    var elementMessage = document.getElementById('messageQuestion');
    elementMessage.innerHTML = questionMessage;
    changeVisible(elementForInvisible, true);
}

function closeQuestionForm() {
    var elementForInvisible = document.getElementById('questionForm');
    changeVisible(elementForInvisible, false);
}

function doItInvisible(elemId) {
    var element = document.getElementById(elemId);
    if (!element.classList.contains('invisible')) {
        element.classList.add('invisible');
    }
}

function removeSelection() {
    var t = event.target || event.srcElement;
    if (t.id == 'content' || t.id == 'searchType' || t.id == 'inputSearch') {
        removeSelectionFromAll();
    }
}

function removeSelectionFromAll() {
    var allSelectedElements = document.getElementsByClassName("selected");
    for (i = 0; i < allSelectedElements.length; i++) {
        allSelectedElements[i].classList.remove("selected");
    }
}

function writeMessageAboutValidation(message, className) {
    var element = document.getElementById(className+"Exception");
    if(element.classList.contains("invisible")){
        element.classList.remove("invisible");
    }
    element.innerHTML = message;
}



