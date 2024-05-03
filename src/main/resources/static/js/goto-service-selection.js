"use strict";

document.getElementById("select-service-button").addEventListener("click", function() {
    var selectedRadio = document.querySelector('input[type="radio"]:checked');
    var masterCard = selectedRadio.closest(".master-card");
    var masterId = masterCard.getAttribute("data-master-id");

    var url = "/record/select-services";
    var queryString = '?master-id=' + masterId;
    window.location.href = url + queryString;
});