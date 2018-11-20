function ChatModel() {
    this._users = [];
    this._usersLoader = new UserLoader();
    this._messages = [];
    this._messageLoader = new MessageLoader();
    this._currentUser = null;
    this._auth = new Auth();
    this._eventSender = new EventSender();
    this._currentUserLoader = new CurrentUserLoader();
    this.onPageLoadMessages = new EventEmitter();
    this.onPageLoadUsers = new EventEmitter();
    this.onPageLoadUser = new EventEmitter();
    this.onPageLoadMessageInput = new EventEmitter();
    this.onLogout = new EventEmitter();
    this.onRegistration = new EventEmitter();
    this.onLogin = new EventEmitter();
    this.onLoginPageLoad = new EventEmitter();
    this.onRegistrationPageLoad = new EventEmitter();
    this.onError = new EventEmitter();
    this.onLoginError = new EventEmitter();
    this.onMessageSend = new EventEmitter();
    this.onChatError = new EventEmitter();
    this.onKick = new EventEmitter();
    this.onKickError = new EventEmitter();
    this.onBan = new EventEmitter();
    this.onBanError = new EventEmitter();
}

ChatModel.prototype.pageLoad = function () {
    var that = this;

    this.updateUsers();
    this.updateMessages();

    this._currentUserLoader.loadUser().then(function (user) {
        that.onPageLoadUser.notify(user);
        that.onPageLoadMessageInput.notify(user);
    }).catch(function (error) {
        that.onPageLoadUser.notify();
        that.onPageLoadMessageInput.notify();
        that.onError.notify(error);
    });
};

ChatModel.prototype.updateMessages = function () {
    var that = this;
    this._messageLoader.loadMessages().then(function (messages) {
        that.onPageLoadMessages.notify(messages);
    }).catch(function (reason) {
        that.onError.notify();
    });
};

ChatModel.prototype.updateUsers = function () {
    var that = this;
    this._usersLoader.loadUsers().then(function (users) {
        that.onPageLoadUsers.notify(users);
    }).catch(function (reason) {
        that.onError.notify();
    });
};

ChatModel.prototype.loadLoginPage = function () {
    this.onLoginPageLoad.notify();
};

ChatModel.prototype.loadRegistrationPage = function () {
    this.onRegistrationPageLoad.notify();
};


ChatModel.prototype.login = function () {
    var that = this;
    this._auth.login().then(function (value) {
        that.onLogin.notify(value);
    }).catch(function (reason) {
        that.onLoginError.notify(reason);
    })

};

ChatModel.prototype.registration = function () {
    var that = this;
    this._auth.registration().then(function (value) {
        that.login();
        //   that.onRegistration.notify(value);
    }).catch(function (reason) {
        that.onLoginError.notify(reason);
    });
};

ChatModel.prototype.logout = function () {
    var that = this;
    this._auth.logout().then(function (value) {
        that.onLogout.notify(value);
    });
};

ChatModel.prototype.sendMessage = function (message) {
    var that = this;
    this._eventSender.sendMessage(message).then(function (value) {
        that.onMessageSend.notify(value);
    }).catch(function (reason) {
        if (reason.status !== 200) {
            that.onChatError.notify(reason);
            that.onError.notify(reason);
        } else {
            that.onMessageSend.notify();
        }
    })
};

ChatModel.prototype.kick = function (userId) {
    var that = this;
    this._auth.kick(userId).then(function (value) {
        that.onKick.notify(value);
    }).catch(function (reason) {
        that.onKickError.notify(reason);
    });
};

ChatModel.prototype.ban = function (userId) {
    var that = this;
    this._auth.ban(userId).then(function (value) {
        that.onBan.notify(value);
    }).catch(function (reason) {
        that.onBanError.notify(reason);
    });
};
