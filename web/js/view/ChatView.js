function ChatView(model, controller) {
    this._model = model;
    this._controller = controller;
}

ChatView.prototype.init = function () {
    "use strict";
    var that = this;
    // this._model.onPageLoad.subscribe(function (newUserList, newMessageList, newCurrentUser) {
    //     that.updateUserList(newUserList);
    //     that.updateCurrentUser(newCurrentUser);
    //     that.updateMessages(newMessageList);
    // });

    that._model.onPageLoadUser.subscribe(function (user) {
        that.updateCurrentUserHeader(user);
    });

    that._model.onPageLoadMessages.subscribe(function (messages) {
        that.updateMessages(messages);
    });

    that._model.onPageLoadUsers.subscribe(function (users) {
        that.updateUserList(users);
    });

    that._model.onLogin.subscribe(function (user) {
        that.login(user);
    });

    that._model.onLogout.subscribe(function (user) {
        that.logout(user);
    });

    window.addEventListener("load", function () {
        that._controller.loadPage();
    });

    document.getElementById("login").addEventListener("click", function (ev) {
        that._controller.login();
    });

    document.getElementById("logout").addEventListener("click", function (ev) {
        that._controller.logout();
    });


};

ChatView.prototype.updateUserList = function (userList) {
    var usersView = new UsersView();
    var renderedUsersVies = usersView.renderUsersView(userList);
    console.log(renderedUsersVies);
    var userListContainer = document.getElementById('user-list-container');
    userListContainer.innerHTML = "";
    userListContainer.appendChild(renderedUsersVies);

};

ChatView.prototype.updateCurrentUserHeader = function (user) {
    console.log("USER WAS HERE");
    console.log(user);
    var userNameHeaderView = new UserNameHeaderView();
    var renderedUseNameHeader = userNameHeaderView.renderHeader();
};

ChatView.prototype.updateMessages = function (messagesList) {
    console.log(messagesList);
};


ChatView.prototype.login = function (user) {
    console.log("LOGIN SUCEES");
    this._controller.loadPage();
};

ChatView.prototype.logout = function (user) {
    console.log("LOGOUT SUCEES");
    this._controller.loadPage();
};
