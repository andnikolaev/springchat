function Event(id, owner, assignee, eventType, message, timestamp) {
    this._id = id;
    this._owner = owner;
    this._assignee = assignee;
    this._eventType = eventType;
    this._message = message;
    this._timestamp = timestamp;
}

Event.prototype.getId = function () {
    return this._id;
};

Event.prototype.getOwner = function () {
    return this._owner;
};

Event.prototype.getAssignee = function () {
    return this._assignee;
};

Event.prototype.getEventType = function () {
    return this._eventType;
};

Event.prototype.getMessage = function () {
    return this._message;
};

Event.prototype.getTimestamp = function () {
    return this._timestamp;
};



