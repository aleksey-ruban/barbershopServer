"use strict";

document.getElementById("select-date-button").addEventListener("click", function() {
    var searchString = window.location.search;
    var searchParams = new URLSearchParams(searchString);
    var paramsPart = searchParams.toString();


    var url = "/record/select-date";
    var queryString = "?" + paramsPart + "&";
    
    var selectedCheckboxes = document.querySelectorAll('input[type="checkbox"]:checked');

    for (var i = 0; i < selectedCheckboxes.length; i++) {
        var serviceCard = selectedCheckboxes[i].closest(".service-card");
        var serviceId = serviceCard.getAttribute("data-service-id");
        queryString += "service-id=" + serviceId;
        if (i < selectedCheckboxes.length - 1) {
            queryString += "&";
        }
    }

    window.location.href = url + queryString;
});