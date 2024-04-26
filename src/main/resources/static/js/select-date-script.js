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
    // } else {
    //     selectedDate = new Date(today.getFullYear(), today.getMonth(), today.getDate());
    // }

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
        if (i % 5 == 2) { 
            q = " calendar-view-day-unabled";
            onclick = '';
        }
        var dateString = someCalendarDay.toString();

        if (selectedDate?.toString() == someCalendarDay.toString()) {
            q = " calendar-view-day-selected";
        }
        
        daysHtml += '<div class="calendar-view-day'+ q + '"' + 'data-date="' + dateString + '"' + onclick + '>' + someCalendarDay.getDate() + "</div>";
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
    console.log(selectedDate);

    var selectedDays = document.querySelectorAll(".calendar-view-day-selected");

    for (var i = 0; i < selectedDays.length; i++) {
        selectedDays[i].classList.remove("calendar-view-day-selected");
    }

    target.classList.add("calendar-view-day-selected");
    showTimes();
    unselectTime();
}


function configureTimes(list=[[9, 0], [10, 30], [11, 30], [13, 0], [15, 0]]) {
    var timesGrid = document.getElementsByClassName("time-view")[0]

    var timesHtml = '';
    for (var i = 0; i < list.length; i++) {
        var hours = list[i][0];
        var minutes = list[i][1] == 0 ? "00" : list[i][1]
        timesHtml += '<div data-hours="' + hours + '" data-minutes="' + minutes + '" onclick="selectTime(event)">' + hours + ':' + minutes + '</div>';
    }

    timesGrid.innerHTML = timesHtml;
}

function selectTime(event) {
    var target = event.target;

    var selectedTimes = document.querySelectorAll(".time-view .selected-time");

    for (var i = 0; i < selectedTimes.length; i++) {
        selectedTimes[i].classList.remove("selected-time");
    }

    selectedTime = [target.getAttribute("data-hours"), target.getAttribute("data-minutes")];
    console.log(selectedTime);

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
    configureTimes();
    hideDoneButton();
    hideTimes();
});

