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

function openLoginForm(){
    var elementForInvisible = document.getElementById('loginBackground');
    changeVisible(elementForInvisible, true);
}


function closeLoginForm(){
    var elementForInvisible = document.getElementById('loginBackground');
    changeVisible(elementForInvisible, false);
    var elementsForClean = document.getElementById('authorization');
    for (i = 0; i < elementsForClean.childNodes.length; i++) {
        conditionElement = elementsForClean.childNodes[i];
        if (conditionElement.classList === undefined) {/*NOP*/
        }
        else if(conditionElement.classList.contains("objectRow")){
            conditionElement.value = '';
        }
    }
}

function openNewObjectForm(){
    var elementForInvisible = document.getElementById('objectsForm');
    changeVisible(elementForInvisible, true);
}


function closeObjectForm(){
    var elementForInvisible = document.getElementById('objectsForm');
    changeVisible(elementForInvisible, false);
    var elementsForClean = document.getElementById('newObjectWorker');
    for (i = 0; i < elementsForClean.childNodes.length; i++) {
        conditionElement = elementsForClean.childNodes[i];
        if (conditionElement.classList === undefined) {/*NOP*/
        }
        else if(conditionElement.classList.contains("objectRow")){
            conditionElement.value = '';
        }
    }
}
function openExceptionForm(exceptionMessage){
    var elementForInvisible = document.getElementById('exceptionForm');
    var elementMessage = document.getElementById('messageExc');
    elementMessage.innerHTML = exceptionMessage;
    changeVisible(elementForInvisible, true);
}

function closeExceptionForm(){
    var elementForInvisible = document.getElementById('exceptionForm');
    changeVisible(elementForInvisible, false);
}


