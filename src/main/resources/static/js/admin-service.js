"use strict";

document.getElementById("postForm").addEventListener("submit", function(event) {
    event.preventDefault();
    let formData = new FormData(document.getElementById("postForm"));
    fetch("/admin/services/", {
        method: "POST",
        body: formData
    }).then(response => {
        console.log("POST запрос выполнен успешно");
    }).catch(error => {
        console.error('Ошибка при отправке запроса:', error);
    });
        event.target.reset();
});

document.getElementById("deleteForm").addEventListener("submit", function(event) {
    event.preventDefault();
    let delData = new FormData(document.getElementById("deleteForm"));
    fetch("/admin/services/", {
        method: "DELETE",
        body: delData
    }).then(response => {
        console.log("DELETE запрос выполнен успешно");
    }).catch(error => {
        console.error('Ошибка при отправке запроса:', error);
    });
        event.target.reset();
});