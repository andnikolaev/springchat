function MessageView() {

}

MessageView.prototype.renderMessagesView = function (messages) {
    var that = this;
    var elements = document.getElementById("chat_mes");
    elements.innerHTML = "";
    messages.forEach(function (message) {
        var element = that.renderMessage(message);
        console.log(element);
        elements.appendChild(that.renderMessage(message));
    });

    return elements;
};

MessageView.prototype.renderMessage = function (message) {
    var element = document.createElement('div');
    element.classList.add('message');
    element.appendChild(renderMessageInfo(message));
    element.appendChild(renderMessageText(message));

    return element;

    function renderMessageInfo(message) {

        var messageInfo = document.createElement('div');
        messageInfo.classList.add('message_info');

        var spanInfo = document.createElement("span");
        spanInfo.classList.add("label");
        if (message['_eventType'] !== "MESSAGE") {
            spanInfo.classList.add("label-danger");
            spanInfo.innerText = "SYSTEM";
        } else {
            spanInfo.classList.add("label-success");
            spanInfo.innerText = message["_owner"]["name"];
        }
        messageInfo.appendChild(spanInfo);

        var spanTime = document.createElement("span");
        spanTime.classList.add("label_info");
        spanTime.classList.add("label");
        spanTime.classList.add("label-default");
        spanTime.innerText = message["_timestamp"];
        messageInfo.appendChild(spanTime);
        return messageInfo;
    }

    function renderMessageText(message) {
        var messageInfo = document.createElement('div');
        messageInfo.classList.add('well');
        messageInfo.classList.add('well-sm');
        switch (message['_eventType']) {
            case 'LOGIN':
                messageInfo.innerText = message["_owner"]["name"] + " entered the chat.";
                break;
            case "LOGOUT":
                messageInfo.innerText = message["_owner"]["name"] + " left the chat.";
                break;
            case "MESSAGE":
                messageInfo.innerText = message["_message"];
                break;
            case 'REGISTERED':
                messageInfo.innerText = message["_owner"]["name"] + " registered in chat;";
                break;
            case 'KICKED':
                messageInfo.innerText = message["_assignee"]["name"] + " was kicked by " + message["_owner"]["name"];
                break;
            case 'BANNED':
                messageInfo.innerText = message["_assignee"]["name"] + " was banned by " + message["_owner"]["name"];
                break;
            case 'DELETED':
                messageInfo.innerText = message["_assignee"]["name"] + " was deleted by " + message["_owner"]["name"];
                break;
        }

        return messageInfo;
    }
};