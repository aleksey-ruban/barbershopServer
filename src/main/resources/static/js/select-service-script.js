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

    updateSelectionInfo();
}

function showButton() {
    var button = document.querySelector(".services-total");
    button.style.display = "flex";
}

function hideButton() {
    var button = document.querySelector(".services-total");
    button.style.display = "none";
}

function updateSelectionInfo() {
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');

    var totalCoast = 0;

    for (var i = 0; i < selectedCheckboxes.length; i++) {
        var serviceCard = selectedCheckboxes[i].closest(".service-card");
        var coastEl = serviceCard.querySelector(".service-name-coast > :nth-child(2)")

        var coast = coastEl.textContent.replace(/ /g, "");
        coast = coast.substring(0, coast.length - 1);
        coast = parseInt(coast);

        totalCoast += coast;
    }

    var countP = document.querySelector(".services-number");
    var coastP = document.querySelector(".services-coast");

    countP.innerHTML = selectedCheckboxes.length + " услуги";
    coastP.innerHTML = totalCoast + " ₽";
}

window.addEventListener("load", function() {
    hideButton();
})