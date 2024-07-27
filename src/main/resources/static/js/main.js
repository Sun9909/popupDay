(function($) {
    "use strict";

    // Setup the calendar with the current date 달력
    $(document).ready(function(){
        var date = new Date();
        var today = date.getDate();
        // Set click handlers for DOM elements
        $(".right-button").click({date: date}, next_year);
        $(".left-button").click({date: date}, prev_year);
        $(".month").click({date: date}, month_click);
        $("#add-button").click({date: date}, new_event);
        // Set current month as active
        $(".months-row").children().eq(date.getMonth()).addClass("active-month");
        init_calendar(date);
        var events = check_events(today, date.getMonth()+1, date.getFullYear());
        show_events(events, months[date.getMonth()], today);
    });

    // Initialize the calendar by appending the HTML dates 달력
    function init_calendar(date) {
        $(".tbody").empty();
        $(".events-container").empty();
        var calendar_days = $(".tbody");
        var month = date.getMonth();
        var year = date.getFullYear();
        var day_count = days_in_month(month, year);
        var row = $("<tr class='table-row'></tr>");
        var today = date.getDate();
        // Set date to 1 to find the first day of the month
        date.setDate(1);
        var first_day = date.getDay();
        // 35+firstDay is the number of date elements to be added to the dates table
        // 35 is from (7 days in a week) * (up to 5 rows of dates in a month)
        for(var i=0; i<35+first_day; i++) {
            // Since some of the elements will be blank,
            // need to calculate actual date from index
            var day = i-first_day+1;
            // If it is a sunday, make a new row
            if(i%7===0) {
                calendar_days.append(row);
                row = $("<tr class='table-row'></tr>");
            }
            // if current index isn't a day in this month, make it blank
            if(i < first_day || day > day_count) {
                var curr_date = $("<td class='table-date nil'>"+"</td>");
                row.append(curr_date);
            }
            else {
                var curr_date = $("<td class='table-date'>"+day+"</td>");
                var events = check_events(day, month+1, year);
                if(today===day && $(".active-date").length===0) {
                    curr_date.addClass("active-date");
                    show_events(events, months[month], day);
                }
                // Set onClick handler for clicking a date
                curr_date.click({events: events, month: month+1, day:day, year: year}, date_click);
                row.append(curr_date);
            }
        }
        // Append the last row and set the current year
        calendar_days.append(row);
        $(".year").text(year);
    }

    // Get the number of days in a given month/year
    function days_in_month(month, year) {
        var monthStart = new Date(year, month, 1);
        var monthEnd = new Date(year, month + 1, 1);
        return (monthEnd - monthStart) / (1000 * 60 * 60 * 24);
    }

    // Event handler for when a date is clicked
    function date_click(event) {
        $(".events-container").show(250);
        $("#dialog").hide(250);
        $(".active-date").removeClass("active-date");
        $(this).addClass("active-date");
        show_events(event.data.events, event.data.month, event.data.day);

        // 선택된 날짜 데이터를 event_data에 추가
        const selectedDate = {
            "occasion": "Selected Date Event",
            "year": event.data.year,
            "month": event.data.month,
            "day": event.data.day
        };
        event_data["events"].push(selectedDate);
        console.log("Selected Date Added: ", selectedDate);
        console.log("Updated event_data: ", event_data);

        // selectedDateAdded input에 값 설정 (형식을 'YYYY-MM-DD'로 변경)
        document.getElementById('selectedDateAdded').value = `${event.data.year}-${String(event.data.month).padStart(2, '0')}-${String(event.data.day).padStart(2, '0')}`;
    }

    // Event handler for when a month is clicked
    function month_click(event) {
        $(".events-container").show(250);
        $("#dialog").hide(250);
        var date = event.data.date;
        $(".active-month").removeClass("active-month");
        $(this).addClass("active-month");
        var new_month = $(".month").index(this);
        date.setMonth(new_month);
        init_calendar(date);
    }

    // Event handler for when the year right-button is clicked
    function next_year(event) {
        $("#dialog").hide(250);
        var date = event.data.date;
        var new_year = date.getFullYear()+1;
        $("year").html(new_year);
        date.setFullYear(new_year);
        init_calendar(date);
    }

    // Event handler for when the year left-button is clicked
    function prev_year(event) {
        $("#dialog").hide(250);
        var date = event.data.date;
        var new_year = date.getFullYear()-1;
        $("year").html(new_year);
        date.setFullYear(new_year);
        init_calendar(date);
    }

    // Display all events of the selected date in card views
    function show_events(events, month, day) {
        // Clear the dates container
        $(".events-container").empty();
        $(".events-container").show(250);
        console.log(event_data["events"]);
        // If there are no events for this date, notify the user
        if(events.length===0) {
            var event_card = $("<div class='event-card'></div>");
            var event_name = $("<div class='event-name'>There are no events planned for "+months[month-1]+" "+day+".</div>");
            $(event_card).css({ "border-left": "10px solid #FF1744" });
            $(event_card).append(event_name);
            $(".events-container").append(event_card);
        }
        else {
            // Go through and add each event as a card to the events container
            for(var i=0; i<events.length; i++) {
                var event_card = $("<div class='event-card'></div>");
                var event_name = $("<div class='event-name'>"+events[i]["occasion"]+"</div>");
                if(events[i]["cancelled"]===true) {
                    $(event_card).css({
                        "border-left": "10px solid #FF1744"
                    });
                }
                $(event_card).append(event_name);
                $(".events-container").append(event_card);
            }
        }
    }

    // Checks if a specific date has any events
    function check_events(day, month, year) {
        var events = [];
        for(var i=0; i<event_data["events"].length; i++) {
            var event = event_data["events"][i];
            if(event["day"]===day &&
                event["month"]===month &&
                event["year"]===year) {
                events.push(event);
            }
        }
        return events;
    }

    // Given data for events in JSON format
    var event_data = {
        "events": [
            {
                "occasion": " Repeated Test Event ",
                "year": 2020,
                "month": 5,
                "day": 10,
                "cancelled": true
            },
            {
                "occasion": " Test Event",
                "year": 2020,
                "month": 5,
                "day": 11
            }
        ]
    };

    const months = [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    ];

})(jQuery);

function fn_articleForm(isLogOn, articleForm, loginForm) {
    if(isLogOn === true) {
        location.href = articleForm;
    } else {
        Swal.fire({
            icon: 'info',
            title: '로그인 후 이용 가능합니다.',
            showConfirmButton: true,
            willOpen: () => {
                Swal.getContainer().style.paddingRight = '0px';

            }
        }).then(() => {
            location.href = loginForm + '?action=' + encodeURIComponent(articleForm);
        });
    }
}

//탈퇴한 회원으로 로그인시 알림
function dropMember() {
    //let isUserIdValid = false;
    //event.preventDefault()
    let userId = document.querySelector('input[name="user_id"]').value;
    console.log("User ID:", userId); // 디버깅용 로그

    $.ajax({
        url: "/login/drop-id",
        data: { 'user_id': userId },
        dataType: 'json',
        success: function (data) {
            console.log("AJAX success data:", data); // 디버깅용 로그
            if (data) {
                //alert("이미 탈퇴한 회원 입니다.");
            }
        }
    })
}
document.addEventListener('DOMContentLoaded', function () {
    document.querySelector('.login_btn').addEventListener('click', dropMember);
});

//달력으로 서치 js
// 달력 부분 추가
// JavaScript to handle date selection and form submission
document.addEventListener('DOMContentLoaded', function () {
    const dateCellsContainer = document.querySelector('.tbody');
    const selectedDateInput = document.getElementById('selectedDateAdded');
    let selectedDate = null;

    const year = 2024; // 예시 연도
    const month = 6; // 6월 (0-based index, 5가 6월)

    // 초기화
    function init_calendar(year, month) {
        dateCellsContainer.innerHTML = ''; // 기존 내용 비우기

        let date = new Date(year, month, 1);
        let day_count = days_in_month(month, year);
        let row = document.createElement('tr');
        row.className = 'table-row';
        let first_day = date.getDay();

        for (let i = 0; i < 35 + first_day; i++) {
            let day = i - first_day + 1;
            let dateCell;

            if (i % 7 === 0) {
                if (row.children.length > 0) {
                    dateCellsContainer.appendChild(row);
                }
                row = document.createElement('tr');
                row.className = 'table-row';
            }

            if (i < first_day || day > day_count) {
                dateCell = document.createElement('td');
                dateCell.className = 'table-date nil';
            } else {
                dateCell = document.createElement('td');
                dateCell.className = 'table-date';
                dateCell.textContent = day;
                let dateStr = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
                dateCell.setAttribute('data-date', dateStr);

                // 클릭 이벤트 처리
                dateCell.addEventListener('click', function () {
                    selectedDate = this.getAttribute('data-date');
                    selectedDateInput.value = selectedDate;

                    // 이전에 선택된 날짜 셀에서 선택 클래스 제거
                    const previouslySelected = document.querySelector('.table-date.selected-date');
                    if (previouslySelected) {
                        previouslySelected.classList.remove('selected-date');
                    }

                    // 현재 선택된 날짜 셀에 선택 클래스 추가
                    this.classList.add('selected-date');

                    console.log("Selected Date Added: ", selectedDate);
                });

                // 기본적으로 오늘 날짜를 선택 상태로 설정
                if (dateStr === getTodayDateStr(year, month)) {
                    dateCell.classList.add('selected-date');
                    selectedDateInput.value = dateStr;
                    selectedDate = dateStr;
                }
            }

            row.appendChild(dateCell);
        }

        dateCellsContainer.appendChild(row);
        document.querySelector('.year').textContent = year;

        // 현재 달을 활성화 상태로 설정
        const months = document.querySelectorAll('.months-row .month');
        months[month].classList.add('active-month');
    }

    // 달력의 마지막 날짜 수를 구하는 함수
    function days_in_month(month, year) {
        let monthStart = new Date(year, month, 1);
        let monthEnd = new Date(year, month + 1, 1);
        return (monthEnd - monthStart) / (1000 * 60 * 60 * 24);
    }

    // 오늘 날짜를 'YYYY-MM-DD' 형식으로 반환하는 함수
    function getTodayDateStr(year, month) {
        let today = new Date();
        let todayYear = today.getFullYear();
        let todayMonth = today.getMonth(); // 0-based index
        let todayDate = today.getDate();
        if (todayYear === year && todayMonth === month) {
            return `${year}-${String(month + 1).padStart(2, '0')}-${String(todayDate).padStart(2, '0')}`;
        }
        return null;
    }

    // 초기 달력 생성 호출
    init_calendar(year, month);
});

document.addEventListener('DOMContentLoaded', function() {
    var customSelect = document.querySelector('.custom-select');
    var selected = customSelect.querySelector('.select-selected');
    var items = customSelect.querySelector('.select-items');

    selected.addEventListener('click', function() {
        items.classList.toggle('select-show');
    });

    items.addEventListener('click', function(e) {
        if (e.target.tagName === 'DIV') {
            var value = e.target.getAttribute('data-value');
            var text = e.target.textContent;

            // 선택된 값을 기본 드롭다운에 설정
            customSelect.querySelector('#filterSelect').value = value;
            selected.textContent = text;

            // 커스텀 드롭다운 업데이트
            var selectedItems = items.querySelectorAll('div');
            selectedItems.forEach(function(item) {
                item.classList.remove('selected');
            });
            e.target.classList.add('selected');

            // 드롭다운 닫기
            items.classList.remove('select-show');
        }
    });

    // 클릭 외부 시 드롭다운 닫기
    document.addEventListener('click', function(e) {
        if (!customSelect.contains(e.target)) {
            items.classList.remove('select-show');
        }
    });
});
