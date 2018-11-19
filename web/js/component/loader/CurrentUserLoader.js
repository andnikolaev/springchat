function CurrentUserLoader() {

}

CurrentUserLoader.prototype.loadUser = function () {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: '/chat/api/users/session',             // указываем URL и
            dataType: "json",                   // тип загружаемых данных
            success: function (data, textStatus) { // вешаем свой обработчик на функцию success
                var user = data;
                resolve(user);
            },
            error: function (httpObj, textStatus) {
                reject(httpObj);
            }
        });
    });
};
