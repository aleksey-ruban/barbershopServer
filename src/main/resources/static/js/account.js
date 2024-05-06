"use strict";

document.addEventListener("DOMContentLoaded", function() {
    var button = document.querySelector(".delete-record-button");
    if (button != null) {
        button.addEventListener("click", function(event) {
            var id = event.target.getAttribute("record-id");
            fetch("/record/delete-record?record-id=" + id, {
                method: "DELETE",
            }).then(response => {
                console.log("DELETE запрос выполнен успешно");
                var url = "/authorization/account";
                window.location.href = url;
            }).catch(error => {
                console.error('Ошибка при отправке запроса:', error);
            });
        });
    }
})

document.querySelector(".account-logout").addEventListener("click", function(event) {
    window.location.href = "/logout";
});

document.querySelector(".account-delete").addEventListener("click", function(event) {
    fetch("/authorization/account", {
        method: "DELETE",
    }).then(response => {
        console.log("DELETE запрос выполнен успешно");
        var url = "/logout";
        window.location.href = url;
    }).catch(error => {
        console.error('Ошибка при отправке запроса:', error);
    });
});