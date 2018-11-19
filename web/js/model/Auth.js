function Auth() {

}

Auth.prototype.login = function () {
    var name = $("#userName").val();
    var password = $("#userPassword").val();
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: "POST",
            url: "/chat/api/sessions",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({"name": name, "password": password}),
            success: function (data) {
                resolve(data);
            },
            error: function (errMsg) {
                reject(errMsg);
            }
        });
    });
};

Auth.prototype.registration = function () {
    var name = $("#userName").val();
    var password = $("#userPassword").val();
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: "POST",
            url: "/chat/api/users",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({"name": name, "password": password}),
            success: function (data) {
                resolve(data);
            },
            error: function (errMsg) {
                reject(errMsg);
            }
        });
    });
};

Auth.prototype.logout = function () {
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: "DELETE",
            url: "http://localhost:8090/chat/api/sessions",
            contentType: "application/json",
            success: function (data) {
                resolve(data);
            },
            error: function (errMsg) {
                reject(errMsg);
            }
        });
    });
};