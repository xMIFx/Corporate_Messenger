/**
 * Created by Vlad on 02.07.2015.
 */

function mouseOver(nameOfBlock) {
    var elementForVisible = null;
    if (nameOfBlock == 'department') {
        elementForVisible = document.getElementById('departmentInfo')
    }
    else if (nameOfBlock == 'worker') {
        elementForVisible = document.getElementById('workerInfo')
    }
    else if (nameOfBlock == 'messenger') {
        elementForVisible = document.getElementById('messengerInfo')
    }
    if (elementForVisible != null) {
        changeInfoVisible(elementForVisible, true);
    }
    else {/*NOP*/
    }
}

function mouseOut(nameOfBlock) {
    var elementForInvisible = null;
    if (nameOfBlock == 'department') {
        elementForInvisible = document.getElementById('departmentInfo')
    }
    else if (nameOfBlock == 'worker') {
        elementForInvisible = document.getElementById('workerInfo')
    }
    else if (nameOfBlock == 'messenger') {
        elementForInvisible = document.getElementById('messengerInfo')
    }
    if (elementForInvisible != null) {
        changeInfoVisible(elementForInvisible, false);
    }
    else {/*NOP*/
    }
}

function changeInfoVisible(elementForVisible, needVisualisation) {
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
    var mainElementInfo = document.getElementById('mainInfo');
    if (mainElementInfo.classList.contains(needClass)) {
        mainElementInfo.classList.remove(needClass);
    }
    if (!mainElementInfo.classList.contains(removeClass)) {
        mainElementInfo.classList.add(removeClass);
    }
}

/*
 function changeInfoVisible(elementForVisible) {
 var brotherElements = elementForVisible.parentNode.childNodes
 , i = 0
 , currentElement;

 while (currentElement = brotherElements[i++]) {
 if(currentElement.classList === undefined){
 continue;
 }
 if (currentElement == elementForVisible) {
 currentElement.classList.remove("invisible");
 currentElement.classList.add("visible");
 }
 else {
 if (currentElement.classList.contains('visible')) {
 currentElement.classList.remove("visible");
 }
 if (!currentElement.classList.contains('invisible')) {
 currentElement.classList.add("invisible");
 }
 }
 }


 }*/
