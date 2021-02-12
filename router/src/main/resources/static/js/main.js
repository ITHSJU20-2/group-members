let suggestions = [];
const searchWrapper = document.querySelector('.search-wrapper');
const input = document.querySelector('.search-input');
const autocomplete = document.querySelector('.autocomplete');
const icon = document.querySelector('.search-icon');
const link = document.querySelector('.search-link');

fetch('/getusers').then(response => response.json()).then(res => {
    res.forEach(user => suggestions.push(user.firstName + ' ' + user.lastName));
    input.addEventListener('keyup', (e) => {
        let userInput = e.target.value;
        let suggArr;
        if (userInput) {
            searchWrapper.classList.add('active');
            icon.addEventListener('click', () => {
                link.setAttribute('href', '/person?firstName=' + userInput.substring(0, userInput.indexOf(' ')) + '&lastName=' + userInput.substring(userInput.indexOf(' ') + 1));
                link.click();
            });
            suggArr = suggestions.filter((data) => {
                return data.toLocaleLowerCase().startsWith(userInput.toLocaleLowerCase());
            });
            suggArr = suggArr.map((data) => {
                return data =
                    '<li class="autocomplete-item"><img src="/img/cat.png" style="height: 20px; width: auto; padding-right: 10px;">' +
                    data + '</li>';
            });
            showSuggestions(suggArr);
            let allList = autocomplete.querySelectorAll('.autocomplete-item');
            for (let i = 0; i < allList.length; i++) {
                allList[i].setAttribute('onclick', 'select(this)');
            }
        } else {
            searchWrapper.classList.remove('active');
        }
    });
    input.removeAttribute('disabled');
    input.setAttribute('placeholder', 'Search...');
});

const select = (el) => {
    let selectData = el.textContent;
    input.value = selectData;
    icon.addEventListener('click', () => {
        link.setAttribute('href', '/person?firstName=' + selectData.substring(0, selectData.indexOf(' ')) + '&lastName=' + selectData.substring(selectData.indexOf(' ') + 1));
        link.click();
    });
    searchWrapper.classList.remove('active');
}

const showSuggestions = (list) => {
    let listData;
    if (!list.length) {
        listData = '<li class="autocomplete-item">' + input.value + '</li>';
    } else {
        listData = list.join('');
    }
    autocomplete.innerHTML = listData;
}