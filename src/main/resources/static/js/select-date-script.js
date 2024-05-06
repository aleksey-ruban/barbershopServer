"use strict";

var selectedMounth = new Date().getMonth();
var selectedDate = null;
var selectedTime = null;

function configureCalendar(changeMounth=false) {
    var calendarGrid = document.getElementsByClassName("calendar-view")[0]

    var today = new Date();
    
    var currentYear = today.getFullYear();
    var currentMounth = today.getMonth();

    if (changeMounth) {
        currentMounth = selectedMounth;
    }

    var firstDayCurrentMounth = new Date(currentYear, currentMounth, 1);
    
    var firstDayWeekdayNumber = firstDayCurrentMounth.getDay();
    if (firstDayWeekdayNumber == 0) {
        firstDayWeekdayNumber = 7;
    }
    
    var firstCalendarDay = new Date(firstDayCurrentMounth);
    firstCalendarDay.setDate(firstDayCurrentMounth.getDate() - firstDayWeekdayNumber + (firstDayWeekdayNumber === 0 ? -6 : 1));
    
    var i = 0;
    var someCalendarDay = new Date(firstCalendarDay);
    var daysHtml = '';
    while (i < 35) {
        var onclick = ' onclick="selectDate(event)"';
        
        var q = "";
        
        var someCalendarDayString = "" + someCalendarDay.getFullYear() + "-" + (someCalendarDay.getMonth() + 1 < 10 ? ("0" + (someCalendarDay.getMonth() + 1)) : (someCalendarDay.getMonth() + 1)) + "-" + (someCalendarDay.getDate() < 10 ? ("0" + someCalendarDay.getDate()) : (someCalendarDay.getDate()));
        if (!(someCalendarDayString in schedule) || (someCalendarDay < new Date().setDate(today.getDate() - 1))) {
            q = " calendar-view-day-unabled";
            onclick = '';
        }

        let copy1 = new Date(selectedDate);
        let copy2 = new Date(someCalendarDay);

        copy1.setHours(0, 0, 0, 0);
        copy2.setHours(0, 0, 0, 0);
        
        if (copy1.getTime() === copy2.getTime()) {
            q = " calendar-view-day-selected";
        }
        
        daysHtml += '<div class="calendar-view-day'+ q + '"' + 'data-date="' + someCalendarDayString + '"' + onclick + '>' + someCalendarDay.getDate() + "</div>";
        i += 1;
        someCalendarDay.setDate(someCalendarDay.getDate() + 1);
    }
    
    calendarGrid.innerHTML = daysHtml;

    var months = [
        "Январь", "Февраль", "Март",
        "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь",
        "Октябрь", "Ноябрь", "Декабрь"
    ];
    var currentMounthLabel = document.querySelector(".calendar-current-mounth p");
    currentMounthLabel.innerHTML = months[currentMounth];

}

function loadPreviousMounth() {
    var today = new Date();
    var currentMounth = today.getMonth();

    if (selectedMounth - 1 >= currentMounth) {
        selectedMounth -= 1;
        configureCalendar(true);
    }
}

function loadNextMounth() {
    var today = new Date();
    var currentMounth = today.getMonth();

    if (selectedMounth + 1 <= currentMounth + 1) {
        selectedMounth += 1;
        configureCalendar(true);
    }
}

function selectDate(event) {
    var target = event.target;
    var newDate = new Date(target.getAttribute("data-date"));
    selectedDate = new Date(newDate);
    var selectedDayString = "" + selectedDate.getFullYear() + "-" + (selectedDate.getMonth() + 1 < 10 ? ("0" + (selectedDate.getMonth() + 1)) : (selectedDate.getMonth() + 1)) + "-" + (selectedDate.getDate() < 10 ? ("0" + selectedDate.getDate()) : (selectedDate.getDate()));
    configureTimes(selectedDayString);

    var selectedDays = document.querySelectorAll(".calendar-view-day-selected");

    for (var i = 0; i < selectedDays.length; i++) {
        selectedDays[i].classList.remove("calendar-view-day-selected");
    }

    target.classList.add("calendar-view-day-selected");
    
    showTimes();
    unselectTime();
}


function configureTimes(selectedDate) {
    var list = schedule[selectedDate];

    var timesGrid = document.getElementsByClassName("time-view")[0]

    var timesHtml = '';
    for (var i = 0; i < list.length; i++) {
        var displayTime = list[i].substring(0, list[i].length - 3);
        timesHtml += '<div data-record-time="' + list[i] + '" onclick="selectTime(event)">' + displayTime + '</div>';
    }

    timesGrid.innerHTML = timesHtml;
}

function selectTime(event) {
    var target = event.target;

    var selectedTimes = document.querySelectorAll(".time-view .selected-time");

    for (var i = 0; i < selectedTimes.length; i++) {
        selectedTimes[i].classList.remove("selected-time");
    }

    selectedTime = target.getAttribute("data-record-time");

    target.classList.add("selected-time");
    showDoneButton();
}

function unselectTime() {
    var selectedTimes = document.querySelectorAll(".time-view .selected-time");

    for (var i = 0; i < selectedTimes.length; i++) {
        selectedTimes[i].classList.remove("selected-time");
    }

    selectedTime = null;
    hideDoneButton();
}

function showDoneButton() {
    var button = document.querySelector(".create-record-button");
    button.style.display = "inline-block";
}

function hideDoneButton() {
    var button = document.querySelector(".create-record-button");
    button.style.display = "none";
}

function showTimes() {
    var title = document.querySelector(".select-time-header");
    title.style.display = "block";
    var panel = document.querySelector(".time-selection-container");
    panel.style.display = "flex";
}

function hideTimes() {
    var title = document.querySelector(".select-time-header");
    title.style.display = "none";
    var panel = document.querySelector(".time-selection-container");
    panel.style.display = "none";
}

window.addEventListener("load", function() {
    configureCalendar();
    hideDoneButton();
    hideTimes();
});

document.querySelector(".create-record-button").addEventListener("click", function() {
    var searchString = window.location.search;
    var searchParams = new URLSearchParams(searchString);
    var paramsPart = searchParams.toString();

    var url = "/record/save-record";
    var queryString = "?" + paramsPart + "&";
    
    var dateString = "" + selectedDate.getFullYear() + "-" + (selectedDate.getMonth() + 1 < 10 ? ("0" + (selectedDate.getMonth() + 1)) : (selectedDate.getMonth() + 1)) + "-" + (selectedDate.getDate() < 10 ? ("0" + selectedDate.getDate()) : (selectedDate.getDate()));
    queryString += "date=" + dateString + "&";
    queryString += "time=" + selectedTime;

    fetch(url + queryString, {
        method: "POST"
    }).then(response => {
        console.log("POST запрос выполнен успешно");
        window.location.href = "/authorization/account";
    }).catch(error => {
        console.error('Ошибка при отправке запроса:', error);
    });

});