"use strict";

function showContacts() {
    var url = "/templates/";
    var queryString = '?show=' + "contacts";
    window.location.href = url + queryString;
}

window.addEventListener("load", function() {
    var urlParams = new URLSearchParams(window.location.search);
    var showInfo = urlParams.get('show');

    if (showInfo == "contacts") {
        window.scrollTo({top: document.body.scrollHeight, behavior: 'smooth'});
    }
});