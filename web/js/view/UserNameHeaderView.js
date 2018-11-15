function UserNameHeaderView() {

}

UserNameHeaderView.prototype.renderHeader = function (user) {
    var authHeaderDiv = document.createElement('div');
    authHeaderDiv.appendChild(this.renderLogoutButton());
    return authHeaderDiv;
};

UserNameHeaderView.prototype.renderLogoutButton = function () {
    var logoutButton = document.createElement('span');
    logoutButton.classList.add("logout");
    logoutButton.innerText = "Logout";
    return logoutButton;
};
