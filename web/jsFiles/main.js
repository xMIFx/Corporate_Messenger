/**
 * Created by Vlad on 02.07.2015.
 */

function mouseOver(nameOfBlock) {
    var elementForVisible = null;
    var mainElementInfo = document.getElementById('mainInfo');
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
        changeVisible(elementForVisible, true);
        changeVisible(mainElementInfo, false);
    }
    else {/*NOP*/
    }
}

function mouseOut(nameOfBlock) {
    var elementForInvisible = null;
    var mainElementInfo = document.getElementById('mainInfo');
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
        changeVisible(elementForInvisible, false);
        changeVisible(mainElementInfo, true);
    }
    else {/*NOP*/
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
