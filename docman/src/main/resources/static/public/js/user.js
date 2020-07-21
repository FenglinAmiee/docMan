$(function () {
    initTable();
});

function search() {
    $('#table').bootstrapTable('refresh');
}

function add() {
    $('#userForm')[0].reset();
    $('#userModal').modal('show');
}

function modify() {
    const selection = $('#table').bootstrapTable('getSelections');

    if (selection.length != 1) {
        Swal.fire({text: "请选择需要修改的记录"});
        return;
    } else {
        for (let item in selection[0]) {
            $('#' + item).val(selection[0][item])
        }

        $('#userModal').modal('show');
    }
}

function remove() {
    const selection = $('#table').bootstrapTable('getSelections');

    if (selection.length != 1) {
        Swal.fire({text: "请选择需要删除的记录"});
    } else {
        Swal.fire({
            text: "是否确认删除本条记录！",
            confirmButtonText: '确定',
            showCancelButton: true,
            cancelButtonText: "取消",
        }).then(function (isConfirm) {
            if (isConfirm.value) {
                $.get('user/remove?id=' + selection[0].id, null, function (res) {
                    if (res.status === 'succeed') {
                        Swal.fire({text: "删除成功"});
                        $('#table').bootstrapTable('refresh');
                    } else {
                        Swal.fire({text: "删除失败，请重试"});
                    }
                }, 'json');
            }
        })
    }
}

function initTable() {
    $('#table').bootstrapTable({
        locale: "zh-CN",
        classes: 'table table-hover table-striped',
        theadClasses: 'thead-dark',
        clickToSelect: true,
        method: 'get',
        dataType: "json",
        queryParamsType: "limit",
        contentType: "application/x-www-form-urlencoded",
        pageList: [10],
        pagination: true,
        sidePagination: 'server',
        pageNumber: 1,
        pageSize: 10,
        url: 'user/search',
        queryParams: function (params) {
            var queryParam = {};
            var username = $('#_username').val();
            var name = $('#_name').val();
            if (username) queryParam.username = username;
            if (name) queryParam.name = name;
            queryParam.limit = params.limit;
            queryParam.offset = params.offset / params.limit;
            return queryParam;
        },
        columns: [
            {
                align: 'center',
                radio: true
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'center',
                title: "序号",
                formatter: function (value, row, index) {
                    return index + 1;
                }
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'center',
                title: "用户名",
                field: 'username'
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'center',
                title: "姓名",
                field: 'name'
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'center',
                title: "部门",
                field: 'department'
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'center',
                title: "用户等级",
                field: 'level',
                formatter: function (value) {
                    return Number(value) === 0 ? "普通用户" : "高级用户";
                }
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'center',
                title: "联系方式",
                field: 'contact'
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'left',
                title: "备注",
                field: 'remark'
            }]
    });
}