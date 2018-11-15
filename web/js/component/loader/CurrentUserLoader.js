function CurrentUserLoader() {

}

CurrentUserLoader.prototype.loadUser = function () {
    return new Promise(function (resolve, reject) {
        $.ajax({
            url: 'http://localhost:8090/chat/api/users/session',             // указываем URL и
            dataType: "json",                   // тип загружаемых данных
            success: function (data, textStatus) { // вешаем свой обработчик на функцию success
                var user = data;
                console.log("CURRENT USER  =");
                console.log(user);
                resolve(user);
            },
            error: function (httpObj, textStatus) {
                console.log(httpObj);
                reject(httpObj);
            }
        });
    });
};
