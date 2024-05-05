"use strict";

document.querySelector(".delete-record-button").addEventListener("click", function(event) {
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