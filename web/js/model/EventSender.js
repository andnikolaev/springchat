function EventSender() {

}

EventSender.prototype.sendMessage = function (message) {
    var message = $("#inputMsg").val();
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: "POST",
            url: "/chat/api/messages",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({"text": message}),
            success: function (data) {
                resolve(data);
            },
            error: function (errMsg) {
                reject(errMsg);
            }
        });
    });
};