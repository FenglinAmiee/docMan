// $('#userModal').modal('hide');

function showUserDetail() {
    $.get('user/get?username='+username,null,function (res) {
        for (var item in res){
            $('#'+item).val(res[item]);
        }
        $('#modalTitle').text("用户中心");
        $('#userModal').modal('show');
    },'json');
}

$(".btn.btn-primary.btn-save-user").on("click",function () {
    const result = checkUser();
    if (!result) {
        $.get('user/save', getFormData('userForm'), function (res) {
            if (res.status === 'succeed') {
                Swal.fire({text: "保存成功"});
                $('#userModal').modal('hide');
                $('#table').bootstrapTable('refresh');
            } else {
                Swal.fire({text: "保存失败，请重试"});
            }
        }, 'json');
    } else {
        Swal.fire({text: result});
        return;
    }
});

function checkUser() {
    const form = getFormData('userForm');

    if (!(/^[a-zA-Z][a-zA-Z0-9]{3,15}$/).test(form.username)) {
        return '请输入以字母开头，4-16位字母和数字组合的用户名';
    }

    if (!(/^[a-zA-Z0-9]{6,}$/).test(form.password)) {
        return '请输入6位及以上长度的字母数字组合的密码';
    }

    if (!form.name) {
        return '请输入姓名';
    }

    if (!form.contact) {
        return '请输入联系方式';
    }
}