function Auth() {

}

Auth.prototype.login = function () {
    return new Promise(function (resolve, reject) {
        $.ajax({
            type: "POST",
            url: "http://localhost:8090/chat/api/sessions",
            contentType: "application/json",
            dataType: "json",
            data: JSON.stringify({"name": "An1sd", "password": "123"}),
            success: function (data) {
                resolve(data);
            },
            failure: function (errMsg) {
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
            failure: function (errMsg) {
                reject(errMsg);
            }
        });
    });
};