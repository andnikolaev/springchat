function AuthHeaderView() {

}

AuthHeaderView.prototype.renderAuthHeader = function () {
    var authHeaderDiv = document.createElement('div');
    authHeaderDiv.appendChild(this.renderLoginButton());
    authHeaderDiv.appendChild(this.renderRegisterButton());
    return authHeaderDiv;
};


AuthHeaderView.prototype.renderLoginButton = function () {
    var loginButton = document.createElement('span');
    loginButton.classList.add("login");
    loginButton.innerText = "Login";
    return loginButton;
};

AuthHeaderView.prototype.renderRegisterButton = function () {
    var registerButton = document.createElement('span');
    registerButton.classList.add("register");
    registerButton.innerText = "Register";
    return registerButton;
};
