function User(id, name, userStatus, userRole) {
    this._id = id;
    this._name = name;
    this._userStatus = userStatus;
    this._userRole = userRole;
}

User.prototype.setId = function (id) {
    this._id = id;
};

User.prototype.getId = function () {
    return this._id;
};

User.prototype.setName = function (name) {
    this._name = name;
};
User.prototype.getName = function () {
    return this._name;
};

User.prototype.setUserStatus = function (userStatus) {
    this._userStatus = userStatus;
};
User.prototype.getUserStatus = function () {
    return this._userStatus;
};

User.prototype.setUserRole = function (userRole) {
    this._userRole = userRole;
};
User.prototype.getUserRole = function () {
    return this._userRole;
};