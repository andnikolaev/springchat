function UserLoader() {

}

UserLoader.prototype.loadUsers = function () {
    return new Promise(function (resolve, reject) {
        var users = [];
        $.ajax({
            url: '/chat/api/sessions',             // указываем URL и
            dataType: "json",                     // тип загружаемых данных
            success: function (data, textStatus) { // вешаем свой обработчик на функцию success
                users = [];
                $.each(data, function (i, val) {    // обрабатываем полученные данные
                    users.push(new User(val['id'], val['name'], val['userStatus'], val['userRole']));
                });
                resolve(users);
            },
            error: function (err) {
                reject(err);
            }
        });
    });
};
