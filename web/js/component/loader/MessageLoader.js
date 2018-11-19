function MessageLoader() {

}

MessageLoader.prototype.loadMessages = function () {

    return new Promise(function (resolve, reject) {
        var messages = [];
        $.ajax({
            url: 'http://localhost:8090/chat/api/events',             // указываем URL и
            dataType: "json",                     // тип загружаемых данных
            success: function (data, textStatus) { // вешаем свой обработчик на функцию success
                messages['status'] = textStatus;
                $.each(data, function (i, val) {    // обрабатываем полученные данные
                    messages.push(new Event(val['id'], val['owner'], val['assignee'], val['eventType'], val['message'], val['timestamp']));
                });
                resolve(messages);
            },
            error: function (err) {
                reject(err);
            }
        });
    });

};
