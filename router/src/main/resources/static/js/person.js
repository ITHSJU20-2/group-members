(() => {
    let urlParams = new URLSearchParams(window.location.search);
    let name = urlParams.get('name')
    let app = document.getElementById("app");
    let nameEl = document.querySelector(".name");

    if (name) {
        document.title = name;
        nameEl.classList.remove('loading')
        nameEl.innerText = name;
    }

    app.appendChild(nameEl);
})()




