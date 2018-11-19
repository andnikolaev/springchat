function ChatView(model, controller) {
    this._model = model;
    this._controller = controller;
    this._user = null;
}

ChatView.prototype.init = function (user) {
    "use strict";
    this._user = user;
    var that = this;

    that._model.onPageLoadUser.subscribe(function (user) {
        that.updateCurrentUserHeader(user);
    });

    that._model.onPageLoadMessages.subscribe(function (messages) {
        that.updateMessages(messages);
    });

    that._model.onPageLoadUsers.subscribe(function (users) {
        that.updateUserList(users, that._user);
    });

    that._model.onPageLoadMessageInput.subscribe(function (user) {
        that.updateMessageInput(user);
    });

    that._model.onLoginPageLoad.subscribe(function (user) {
        that.loginPageLoad(user);
    });

    that._model.onRegistrationPageLoad.subscribe(function (user) {
        that.registrationPageLoad(user);
    });

    that._model.onLogin.subscribe(function (user) {
        that.login(user);
    });

    // that._model.onRegister.subscribe(function (user) {
    //     that.register(user);
    // });

    that._model.onLogout.subscribe(function (user) {
        that.logout(user);
    });
    that._controller.loadPage();
};

ChatView.prototype.updateUserList = function (userList, currentUser) {
    var usersView = new UsersView();
    var renderedUsersVies = usersView.renderUsersView(userList, currentUser);
    console.log(renderedUsersVies);
    var userListContainer = document.getElementById('user-list-container');
    userListContainer.innerHTML = "";
    userListContainer.appendChild(renderedUsersVies);

};

ChatView.prototype.updateCurrentUserHeader = function (user) {
    var that = this;
    var header;
    if (user === undefined) {
        var authHeaderView = new AuthHeaderView();
        header = authHeaderView.renderAuthHeader();
        header.getElementsByClassName("login")[0].addEventListener('click', function (evt) {
            that._controller.loadLoginPage();
        });
        header.getElementsByClassName("register")[0].addEventListener('click', function (evt) {
            that._controller.loadRegistrationPage();
        });
    } else {
        var userNameHeaderView = new UserNameHeaderView();
        header = userNameHeaderView.renderHeader(user);
        header.getElementsByClassName("logout")[0].addEventListener("click", function () {
            that._controller.logout();
        });
    }

    var userHeader = document.getElementsByClassName("user-header")[0];
    userHeader.innerHTML = "";
    userHeader.appendChild(header);
};

ChatView.prototype.updateMessages = function (messagesList) {
    console.log("messssasdasdasdasdads");
    console.log(messagesList);
    var messageView = new MessageView();
    var messages = messageView.renderMessagesView(messagesList);
    console.log("1111111111111111111111111111");
    console.log(messages);
};

ChatView.prototype.updateMessageInput = function (user) {
    var input = document.getElementById('inputMsg');
    input.disabled = user == null;
};


ChatView.prototype.login = function () {
    var modal = document.getElementById('myModal');
    modal.style.display = "none";
    history.pushState(null, null, '/chat');
    this._controller.loadPage();
};

ChatView.prototype.logout = function () {
    this._controller.loadPage();
};

ChatView.prototype.loginPageLoad = function () {
    var that = this;
    var modal = document.getElementById('myModal');
    modal.style.display = "block";
    document.getElementById("submit-login").addEventListener("click", function (ev) {
        that._controller.login();
    });
    var span = document.getElementsByClassName("close")[0];
    span.addEventListener("click", function () {
        modal.style.display = "none";
        history.pushState(null, null, '/chat');
    });
};

ChatView.prototype.registrationPageLoad = function () {
    var that = this;
    var modal = document.getElementById('myModal');
    modal.style.display = "block";
    document.getElementById("submit-login").addEventListener("click", function (ev) {
        that._controller.registration();
    });
    var span = document.getElementsByClassName("close")[0];
    span.addEventListener("click", function () {
        modal.style.display = "none";
        history.pushState(null, null, '/chat');
    });
};