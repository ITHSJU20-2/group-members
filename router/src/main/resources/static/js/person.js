let urlParams = new URLSearchParams(window.location.search);
let firstName = urlParams.get('firstName');
let lastName = urlParams.get('lastName');
let nameEl = document.querySelector(".name");
let idEl = document.querySelector(".id");

let userObj = {
    firstName,
    lastName
};

$.post('/getbyfirstlast', userObj).done(data => {
    document.title = data.firstName + ' ' + data.lastName;
    nameEl.classList.remove('loading')
    nameEl.innerText = data.firstName + ' ' + data.lastName;
    idEl.innerText = data.id;
});



