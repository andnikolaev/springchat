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
    this.onLogout = new EventEmitter();
    this.onLogin = new EventEmitter();
}

ChatModel.prototype.pageLoad = function () {
    var that = this;
    this._usersLoader.loadUsers().then(function (users) {
        that.onPageLoadUsers.notify(users);
    });

    this._messageLoader.loadMessages().then(function (messages) {
        that.onPageLoadMessages.notify(messages);
    });

    this._currentUserLoader.loadUser().then(function (user) {
        that.onPageLoadUser.notify(user);
    }).catch(function (error) {
        console.log(error);
    });
};

ChatModel.prototype.login = function () {
    var that = this;
    this._auth.login().then(function (value) {
        that.onLogin.notify(value);
    })

};

ChatModel.prototype.logout = function () {
    var that = this;
    this._auth.logout().then(function (value) {
        that.onLogout.notify(value);
    });
};

