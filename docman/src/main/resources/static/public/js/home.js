$(function () {
    initTable();
});

function search() {
    $('#table').bootstrapTable('refresh');
}

function preview(path) {

}

function download(id) {
    window.location.href = 'docs/download?id=' + id;
}

function initTable() {
    $('#table').bootstrapTable({
        locale: "zh-CN",
        classes: 'table table-hover table-striped',
        theadClasses: 'thead-dark',
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
            var keyword = $('#keyword').val();
            if (keyword) queryParam.keyword = keyword;
            queryParam.limit = params.limit;
            queryParam.offset = params.offset / params.limit;
            return queryParam;
        },
        columns: [
            {
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
                field: 'name',
            }, {
                halign: 'center',
                valign: 'middle',
                align: 'center',
                title: "关键字",
                field: 'keyword',
                formatter: function (value) {
                    var keylist = value.split(",");
                    var keystr = "";
                    for (var key in keylist) {
                        keystr += '<span class="badge badge-secondary">' + key + '</span>&nbsp;';
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
                align: 'center',
                title: "操作",
                formatter: function (value, row, index) {
                    return '<a href="docs/preview?path=" '+row.path+' style="color:greenyellow">预览</a>'
                        + '&nbsp;&nbsp;&nbsp;&nbsp;'
                        + '<a href="docs/download?id=' + row.id + '" style="color:greenyellow">下载</a>';
                }
            }
        ]
    });
}