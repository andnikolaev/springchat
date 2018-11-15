function ChatController() {
    this._model = new ChatModel();
    this._view = new ChatView(this._model, this);
}

ChatController.prototype.start = function () {
    var that = this;
    var userLoader = new CurrentUserLoader();
    userLoader.loadUser().then(function (user) {
        console.log("THEN");
        that._view.init(user);
    }).catch(function (reason) {
        console.log("Catch");
        that._view.init();
    })
};

ChatController.prototype.loadPage = function () {
    console.log("loadpage");
    this._model.pageLoad();
    // var that = this;
    // var isLoginPage = isLoginPath(window.location.pathname);
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
    // function isLoginPath(path) {
    //     return path.split("/")[2] === "login";
    // }
};

ChatController.prototype.login = function () {
    this._model.login();
};

ChatController.prototype.logout = function () {
    this._model.logout();
};

