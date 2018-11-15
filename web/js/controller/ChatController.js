function ChatController() {
    this._model = new ChatModel();
    this._view = new ChatView(this._model, this);
}

ChatController.prototype.start = function () {
    this._view.init();
};

ChatController.prototype.loadPage = function () {
    this._model.pageLoad();
};

ChatController.prototype.login = function () {
    this._model.login();
};

ChatController.prototype.logout = function () {
    this._model.logout();
};

