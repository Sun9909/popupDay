//날짜가져오기 
let date = new Date();
let currYear = date.getFullYear(),
    currMonth = date.getMonth();

const months = [
    'January',
    'February',
    'March',
    'April',
    'May',
    'June',
    'July',
    'August',
    'September',
    'October',
    'November',
    'December',
];

const currentDate = document.querySelector('.current-date');
currentDate.innerHTML = `${months[currMonth]} ${currYear}`;

const daysTag = document.querySelector('.days');
const renderCalendar = () => {
    currentDate.innerHTML = `${months[currMonth]} ${currYear}`;
    let lastDateofMonth = new Date(currYear, currMonth + 1, 0).getDate();
    let firstDayofMonth = new Date(currYear, currMonth, 1).getDay();
    let lastDayofMonth = new Date(currYear, currMonth, lastDateofMonth).getDay();
    let lastDateofLastMonth = new Date(currYear, currMonth, 0).getDate();
    let liTag = '';
    for (let i = firstDayofMonth; i > 0; i--) {
        liTag += `<li class = "inactive">${lastDateofLastMonth - i + 1}</li>`;
    }
    for (let i = 1; i <= lastDateofMonth; i++) {
        let isToday =
            i === date.getDate() &&
            currMonth === new Date().getMonth() &&
            currYear === new Date().getFullYear()
                ? 'active'
                : '';
        liTag += `<li class="${isToday}">${i}</li>`;
    }
    for (let i = lastDayofMonth; i < 6; i++) {
        liTag += `<li class = "inactive">${i - lastDayofMonth + 1}</li>`;
    }

    daysTag.innerHTML = liTag;
};
renderCalendar();

const prevButton = document.getElementById('prev');
const nextButton = document.getElementById('next');

prevButton.addEventListener('click', () => {
    currMonth--;
    if (currMonth < 0) {
        currMonth = 11;
        currYear--;
    }
    renderCalendar();
});

nextButton.addEventListener('click', () => {
    currMonth++;
    if (currMonth > 11) {
        currMonth = 0;
        currYear++;
    }
    renderCalendar();
});
