function getFormData(form_id) {
    var params = {};
    var form_data = $('#'+form_id).serializeArray();

    $.each(form_data, function() {
        params[this.name] = this.value;
    });
    return params;
}

function getFormDataStr(form_id) {
    var str = '';
    var form_data = $('#'+form_id).serializeArray();

    $.each(form_data, function() {
        str += this.name + '=' + this.value + '&';
    });
    return str;
}