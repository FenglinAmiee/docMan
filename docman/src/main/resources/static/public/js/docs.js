$('#affectDate').datetimepicker({
    language: 'zh-CN',
    format: 'yyyy-mm-dd',
    autoclose: true,
    todayBtn: true,
    minView: 2
});

$('#files').fileinput({
    theme: 'zh',
    language: 'zh',
    enctype: 'multipart/form-data',
    uploadUrl: 'docs/upload',
    showPreview: false,
    showRemove: false,
    showCancel: false,
    showClose: false,
    maxFileSize: 52100,
    browseLabel: '浏览',
    browseClass: 'btn btn-files btn-sm btn-primary',
    uploadClass: 'btn btn-files btn-sm btn-success',
    maxFileCount: 1,
    msgSizeTooLarge: '文件 "{name}"超过了允许大小 50MB'
}).on("fileuploaded", function (event, data, previewId, index) {
    if (data.response.status === "succeed") {
        $('#path').val(data.response.path);
        $('#realName').val(data.response.realName);
        Swal.fire({text: "文件上传成功"});
    } else {
        Swal.fire({
            text: '文件上传失败，请重试！'
        });
    }
});


$(function () {
    initTable();
});

function search() {
    $('#table').bootstrapTable('refresh');
}

function add() {
    $('#docsForm')[0].reset();
    $('#docsModal').modal('show');
}

function modify() {
    let selection = $('#table').bootstrapTable('getSelections');

    if (selection.length != 1) {
        Swal.fire({text: "请选择需要修改的记录"});
        return false;
    } else {
        $('#docsModal').find("input[name='id']").val(selection[0].id);
        $('#docsModal').find("input[name='name']").val(selection[0].name);
        $('#docsModal').find("input[name='keyword']").val(selection[0].keyword);
        $('#docsModal').find("input[name='publisher']").val(selection[0].publisher);
        $('#docsModal').find("input[name='affectDate']").val(selection[0].affectDate);
        $('#docsModal').find("input[name='level']").val(selection[0].level);
        $('#docsModal').find("input[name='remark']").val(selection[0].remark);
        $('#docsModal').find("input[name='path']").val(selection[0].path);
        $('#docsModal').find("input[name='realName']").val(selection[0].realName);

        $('#realNameDiv').show();
        $('#docsModal').modal('show');
    }
}

function remove() {
    let selection = $('#table').bootstrapTable('getSelections');

    if (selection.length != 1) {
        Swal.fire({text: "请选择需要删除的记录"});
        return false;
    } else {
        Swal.fire({
            text: "是否确认删除本条记录！",
            confirmButtonText: '确定',
            showCancelButton: true,
            cancelButtonText: "取消"
        }).then(function (isConfirm) {
            if (isConfirm.value) {
                $.get('docs/remove?id=' + selection[0].id, null, function (res) {
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

function save() {
    let result = checkDoc();

    if (!result) {
        $.get('docs/save', getFormData('docsForm'), function (res) {
            if (res.status === 'succeed') {
                Swal.fire({text: "保存成功"});
                $('#docsModal').modal('hide');
                $('#table').bootstrapTable('refresh');
            } else {
                Swal.fire({text: "保存失败，请重试"});
            }
        }, 'json');
    } else {
        Swal.fire({text: result});
        return false;
    }

}

function checkDoc() {
    const form = getFormData("docsForm");
    if (!form.name) {
        return '请输入名称';
    }

    if (!form.publisher) {
        return '请输入发布人/单位';
    }

    if (!form.affectDate) {
        return '请选择发布时间';
    }

    if (!form.path) {
        return '请上传文档';
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
        url: 'docs/search',
        queryParams: function (params) {
            var queryParam = {};
            var keyword = $('#_keyword').val();
            if (keyword) queryParam.keyword = keyword;
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
                title: "名称",
                field: 'name'
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'center',
                title: "关键字",
                field: 'keyword',
                formatter: function (value) {
                    var keylist = value.split(" ");
                    var keystr = "";
                    for (var key in keylist) {
                        keystr += '<span class="badge badge-secondary">' + keylist[key] + '</span>&nbsp;';
                    }

                    return keystr;
                }
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'center',
                title: "发布人/发布单位",
                field: 'publisher'
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'center',
                title: "文件级别",
                field: 'level',
                formatter: function (value) {
                    return Number(value) === 0 ? "公开" : "非公开";
                }
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'center',
                title: "发布时间",
                field: 'affectDate'
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'left',
                title: "备注",
                field: 'remark'
            }
        ]
    });
}

function preview() {
    let selection = $('#table').bootstrapTable('getSelections');

    if (selection.length != 1) {
        Swal.fire({text: "请选择需要预览的文档"});
        return false;
    } else {
        let originName = selection[0].path;
        if (originName.endsWith("pdf")) {
            window.location.href = 'docs/preview?path=' + originName;
        } else {
            Swal.fire({text: "文档格式无法预览"});
            return false;
        }
    }
}

function download() {
    let selection = $('#table').bootstrapTable('getSelections');

    if (selection.length != 1) {
        Swal.fire({text: "请选择需要下载的文档"});
        return false;
    } else {
        window.location.href = 'docs/download?id=' + selection[0].id;
    }
}