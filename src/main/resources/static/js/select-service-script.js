"use strict";

var selectedServices = [];

function selectService(event) {
    var elem = event.target;
    var target = elem.closest(".service-card");
    var serviceId = parseInt(target.getAttribute("data-service-id"), 10);
    console.log(serviceId);

    var selected = document.querySelector('.service-card[data-service-id="' + serviceId + '"]');

    if (selectedServices.includes(serviceId)) {
        var index = selectedServices.indexOf(serviceId);
        selectedServices.splice(index, 1);
        if (selected) {
            var check = selected.querySelector('input[type="checkbox"]');
            check.checked = false;
        }
    } else {
        selectedServices.push(serviceId);
        if (selected) {
            var check = selected.querySelector('input[type="checkbox"]');
            check.checked = true;
        }
    }
    
    if (selectedServices.length > 0) {
        showButton();
    } else {
        hideButton();
    }
}

function showButton() {
    var button = document.querySelector(".services-total");
    button.style.display = "flex";
}

function hideButton() {
    var button = document.querySelector(".services-total");
    button.style.display = "none";
}


window.addEventListener("load", function() {
    hideButton();
})