function ChatController() {
    this._model = new ChatModel();
    this._view = new ChatView(this._model, this);
}

ChatController.prototype.start = function () {
    var that = this;
    var userLoader = new CurrentUserLoader();
    userLoader.loadUser().then(function (user) {
        that._view.init(user);
    }).catch(function (reason) {
        that._view.init();
    })
};

ChatController.prototype.getCurrentUser = function () {
    var that = this;
    var userLoader = new CurrentUserLoader();
    return userLoader.loadUser().then(function (user) {
        return user;
    }).catch(function (reason) {
        return null;
    });
};

ChatController.prototype.loadPage = function () {
    this._model.pageLoad();
    var that = this;
    var path = window.location.pathname;
    if (isLoginPath(path)) {
        that._model.loadLoginPage();
    }

    if (isRegistrationPath(path)) {
        that._model.loadRegistrationPage();
    }

    //
    // var userLoader = new CurrentUserLoader();
    // userLoader.loadUser().then(function (user) {
    //     that._view.initChatPage(user);
    // }).catch(function (reason) {
    //     if (isLoginPage) {
    //         that._view.initChatLoginPage();
    //     } else {
    //         that._view.initChatPage();
    //     }
    // });
    //
    function isLoginPath(path) {
        return path.split("/")[2] === "login";
    }

    function isRegistrationPath(path) {
        return path.split("/")[2] === "registration";
    }
};

ChatController.prototype.updateMessages = function () {
    this._model.updateMessages();
};

ChatController.prototype.updateUsers = function () {
    this._model.updateUsers();
};


ChatController.prototype.loadLoginPage = function () {
    this._model.loadLoginPage();
};

ChatController.prototype.loadRegistrationPage = function () {
    this._model.loadRegistrationPage();
};

ChatController.prototype.login = function () {
    this._model.login();
};

ChatController.prototype.registration = function () {
    this._model.registration();
};

ChatController.prototype.logout = function () {
    this._model.logout();
};

ChatController.prototype.sendMessage = function (message) {
    this._model.sendMessage(message);
};
