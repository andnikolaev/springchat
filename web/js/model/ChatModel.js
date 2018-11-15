function ChatModel() {
    this._users = [];
    this._usersLoader = new UserLoader();
    this._messages = [];
    this._messageLoader = new MessageLoader();
    this._currentUser = null;
    this._auth = new Auth();
    this._currentUserLoader = new CurrentUserLoader();
    this.onPageLoadMessages = new EventEmitter();
    this.onPageLoadUsers = new EventEmitter();
    this.onPageLoadUser = new EventEmitter();
    this.onPageLoadMessageInput = new EventEmitter();
    this.onLogout = new EventEmitter();
    this.onLogin = new EventEmitter();
    this.onError = new EventEmitter();
}

ChatModel.prototype.pageLoad = function () {
    var that = this;
    this._usersLoader.loadUsers().then(function (users) {
        that.onPageLoadUsers.notify(users);
    }).catch(function (reason) {
        that.onError.notify(reason);
    });

    this._messageLoader.loadMessages().then(function (messages) {
        that.onPageLoadMessages.notify(messages);
    }).catch(function (reason) {
        that.onError.notify(reason);
    });

    this._currentUserLoader.loadUser().then(function (user) {
        console.log(user);
        that.onPageLoadUser.notify(user);
        that.onPageLoadMessageInput.notify(user);
    }).catch(function (error) {
        console.dir(error);
        that.onPageLoadUser.notify();
        that.onPageLoadMessageInput.notify();
        that.onError.notify(error);
    });
};

ChatModel.prototype.login = function () {
    var that = this;
    this._auth.login().then(function (value) {
        that.onLogin.notify(value);
    }).catch(function (reason) {
        console.dir(reason);
    })

};

ChatModel.prototype.logout = function () {
    var that = this;
    this._auth.logout().then(function (value) {
        that.onLogout.notify(value);
    });
};

