function ChatView(model, controller) {
    this._model = model;
    this._controller = controller;
    this._user = null;
}

ChatView.prototype.init = function (user) {
    "use strict";
    var that = this;
    that._user = user;
    var btnSend = document.getElementsByClassName('btn-send')[0];
    btnSend.addEventListener('click', function (evt) {
        that._controller.sendMessage(document.getElementById('inputMsg').innerText);
    });

    that._model.onPageLoadUser.subscribe(function (user) {
        that._user = user;
        that.updateCurrentUserHeader(user);
    });

    that._model.onPageLoadMessages.subscribe(function (messages) {
        that.updateMessages(messages);
    });

    that._model.onPageLoadUsers.subscribe(function (users, user) {
        that.updateUserList(users, user);
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

    that._model.onLoginError.subscribe(function (reason) {
        that.loginError(reason);
    });

    that._model.onRegistration.subscribe(function (user) {
        that.register(user);
    });

    that._model.onLogout.subscribe(function (user) {
        that.logout(user);
    });
    that._model.onChatError.subscribe(function (reason) {
        that.chatError(reason);
    });
    that._model.onMessageSend.subscribe(function (message) {
        that.messageSend(that);
    });

    that._controller.loadPage();

    // var timer = setInterval(function () {
    //     that._controller.updateMessages();
    // }, 4000);
    //
    // var timerUsers = setInterval(function () {
    //     that._controller.updateUsers();
    // }, 4000);
};

ChatView.prototype.updateUserList = function (userList, currentUser) {
    var usersView = new UsersView();
    //TODO Притащить сюда текущего пользователя!!!!!!!!!!!!!!!!
    var renderedUsersVies = usersView.renderUsersView(userList);
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
    var messageView = new MessageView();
    messageView.renderMessagesView(messagesList);
    var messageArea = document.getElementById('chat_mes');
    messageArea.scrollTop = messageArea.scrollHeight - messageArea.clientHeight;

};

ChatView.prototype.updateMessageInput = function (user) {
    var input = document.getElementById('inputMsg');
    input.disabled = user == null;

    var btnSend = document.getElementsByClassName('btn-send')[0];
    btnSend.disabled = user == null;
};


ChatView.prototype.login = function () {
    var modal = document.getElementById('myModal');
    modal.innerHTML = "";
    modal.style.display = "none";
    history.pushState(null, null, '/chat');
    this._controller.loadPage();
};

ChatView.prototype.register = function () {
    var modal = document.getElementById('myModal');
    modal.innerHTML = "";
    modal.style.display = "none";
    alert("You have successfully registered in the chat. Login to start chatting.")
    history.pushState(null, null, '/chat');
    this._controller.loadPage();
};

ChatView.prototype.logout = function () {
    this._controller.loadPage();
};

ChatView.prototype.loginPageLoad = function () {
    var that = this;
    var modal = document.getElementById('myModal');
    var modalView = new ModalView();
    var renderedLoginPage = modalView.renderLoginPage();
    modal.appendChild(renderedLoginPage);
    document.getElementById("submit-login").addEventListener("click", function (ev) {
        that._controller.login();
    });
    var span = document.getElementsByClassName("close")[0];
    span.addEventListener("click", function () {
        modal.innerHTML = "";
        modal.style.display = "none";
        history.pushState(null, null, '/chat');
    });

    modal.style.display = "block";
};

ChatView.prototype.registrationPageLoad = function () {
    var that = this;
    var modal = document.getElementById('myModal');
    var modalView = new ModalView();
    var renderedLoginPage = modalView.renderRegistrationPage();
    modal.appendChild(renderedLoginPage);
    document.getElementById("submit-login").addEventListener("click", function (ev) {
        that._controller.registration();
    });
    var span = document.getElementsByClassName("close")[0];
    span.addEventListener("click", function () {
        modal.innerHTML = "";
        modal.style.display = "none";
        history.pushState(null, null, '/chat');
    });
    modal.style.display = "block";
};

ChatView.prototype.loginError = function (reason) {
    var that = this;
    var errorDiv = document.getElementsByClassName("login-error")[0];
    var reasonText = "";
    if (Array.isArray(reason['responseText'])) {
        reasonText = reason['responseText'][0];
    } else {
        reasonText = reason['responseText'];
    }
    reasonText = reasonText.replace('[', "");
    reasonText = reasonText.replace(']', "");
    reasonText = reasonText.split(',');
    reasonText = reasonText[reasonText.length - 1];
    if (reasonText === "\"userNotFound\"") {
        errorDiv.innerText = "Wrong login or password.";
    }

    if (reasonText === "\"userExist\"") {
        errorDiv.innerText = "User with this name already exists.";
    }

    if (reasonText === "\"name error\"") {
        errorDiv.innerText = "The name must not be empty and have a length of 3 to 20 characters."
    }

    if (reasonText === "\"password error\"") {
        errorDiv.innerText = "The password must not be empty and have a length of 3 to 20 characters."
    }


};

ChatView.prototype.messageSend = function (that) {
    var errorDiv = document.getElementById("chatError");
    errorDiv.innerText = "";
    var textArea = document.getElementById('inputMsg');
    textArea.value = "";
    that._controller.updateMessages();
};

ChatView.prototype.chatError = function (reason) {
    var errorDiv = document.getElementById("chatError");
    var reasonText = "";
    if (Array.isArray(reason['responseText'])) {
        reasonText = reason['responseText'][0];
    } else {
        reasonText = reason['responseText'];
    }
    reasonText = reasonText.replace('[', "");
    reasonText = reasonText.replace(']', "");
    reasonText = reasonText.split(',');
    reasonText = reasonText[reasonText.length - 1];
    if (reasonText === "\"text error\"") {
        errorDiv.innerText = "Message must have a length of 1 to 255";
    }
};