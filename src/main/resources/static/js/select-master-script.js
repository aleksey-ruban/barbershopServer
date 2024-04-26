"use strict";

var selectedMaster = null;

function selectMaster(event) {
    var elem = event.target;
    var target = elem.closest(".master-card");
    var masterId = parseInt(target.getAttribute("data-master-id"), 10);
    
    var selected = document.querySelector('.master-card[data-master-id="' + selectedMaster + '"]');
    if (selected) {
        var check = selected.querySelector('input[type="radio"]');
        check.checked = false;
    }

    selectedMaster = masterId;
    console.log(masterId);

    var selected = document.querySelector('.master-card[data-master-id="' + selectedMaster + '"]');
    if (selected) {
        var check = selected.querySelector('input[type="radio"]');
        check.checked = true;
    }
    
    showButton();
}

function showButton() {
    var button = document.querySelector(".select-service");
    button.style.display = "inline-block";
}

function hideButton() {
    var button = document.querySelector(".select-service");
    button.style.display = "none";
}


window.addEventListener("load", function() {
    hideButton();
})