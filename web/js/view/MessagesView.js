function MessageView() {

}

MessageView.prototype.renderMessagesView = function (messages) {
    var that = this;
    var elements = document.createElement('div');
    elements.classList.add('message');
    messages.forEach(function (message) {
        elements.appendChild(that.renderMessage(message));
    });
    return elements;
};

MessageView.prototype.renderMessage = function (message) {
    var messageInfo = document.createElement('div');
    messageInfo.classList.add('message_info');
    return messageInfo;
};