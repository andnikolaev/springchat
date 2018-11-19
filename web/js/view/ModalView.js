function ModalView() {

}

ModalView.prototype.renderLoginPage = function () {
    var modalContainer = document.createElement("div");
    modalContainer.classList.add("modal-content");
    modalContainer.appendChild(renderLoginHeader("Sign in"));
    modalContainer.appendChild(renderModalBody());
    return modalContainer;


};

ModalView.prototype.renderRegistrationPage = function () {
    var modalContainer = document.createElement("div");
    modalContainer.classList.add("modal-content");
    modalContainer.appendChild(renderLoginHeader("Registration"));
    modalContainer.appendChild(renderModalBody());
    return modalContainer;
};

function renderLoginHeader(headerText) {
    var modalHeader = document.createElement('div');
    modalHeader.classList.add('modal-header');
    var spanContainer = document.createElement('span');
    spanContainer.classList.add("close");
    spanContainer.innerText = "×";
    modalHeader.appendChild(spanContainer);
    var header = document.createElement('h2');
    header.innerText = headerText;
    modalHeader.appendChild(header);
    return modalHeader;
}

function renderModalBody() {
    var bodyContainer = document.createElement('div');
    bodyContainer.classList.add('modal-body');
    bodyContainer.appendChild(createLabel("userName", "Name:"));
    bodyContainer.appendChild(document.createElement('br'));
    bodyContainer.appendChild(createInput("name", "userName", "text"));
    bodyContainer.appendChild(document.createElement('br'));

    bodyContainer.appendChild(createLabel("userPassword", "Password:"));
    bodyContainer.appendChild(document.createElement('br'));
    bodyContainer.appendChild(createInput("password", "userPassword", "text"));
    bodyContainer.appendChild(document.createElement('br'));
    bodyContainer.appendChild(document.createElement('br'));
    bodyContainer.appendChild(createInput("submitIn", "submit-login", "submit", "Submit"));

    bodyContainer.appendChild(document.createElement('br'));
    var errorDiv = document.createElement('div');
    errorDiv.classList.add('login-error');
    bodyContainer.appendChild(errorDiv);

    return bodyContainer;

    function createInput(name, id, type, value) {
        var inputElement = document.createElement('input');
        inputElement.name = name;
        inputElement.id = id;
        inputElement.type = type;
        if (value === undefined) {
            inputElement.value = "";
        } else {
            inputElement.value = value;
        }
        return inputElement;
    }

    function createLabel(labelFor, text) {
        var label = document.createElement('label');
        label.htmlFor = labelFor;
        label.innerText = text;
        return label;
    }
}