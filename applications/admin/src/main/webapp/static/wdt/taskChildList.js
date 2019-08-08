/**
 * Created by bob.xu on 2017/8/9.
 */
var pageNo = 1, pageSize = 10, totalCount = 0;
var $table = $('#taskNode_list');
function getData(start){
    return JSON.stringify({
        "start": start,
        "length": pageSize,
        "taskId": taskId
    })
}
function goDetail(id){
    setCookieFromUrl();
    window.location.href = urlhead + '/rest/wdt/task/taskDetail?id='+id;
}
function addNode(model) {
    checkRequired('addNode_div',function(){
        var data = {}
        $('#addNode_div [data-name]').each(function (i,info) {
            if($(info).data('name') == 'startDate'){
                data[$(info).data('name')] = $(info).val()+' 00:00:00';
            }else if($(info).data('name') == 'endDate'){
                data[$(info).data('name')] = $(info).val()+' 23:59:59';
            } else{
                data[$(info).data('name')] = $(info).val();
            }
        })
        data['taskId'] = taskId;
        data['owner'] = {
            name: data['ownerName'],
            id: data['ownerId']
        };
        delete data['ownerName'];
        delete data['ownerId'];
        console.log(data);

        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.ajax({
            url : urlhead+'/rest/wdt/taskChild/save',
            type : 'post',
            data : JSON.stringify(data),
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            success : function(res) {
                parent.layer.close(loadModel);
                if(res.result){
                    parent.layer.alert(tipTexts.success);
                    parent.layer.close(model);
                    window.reloadTable();
                }else{
                    if(res.message){
                        parent.layer.alert(res.message);
                    }else{
                        parent.layer.alert(tipTexts.fail);
                    }
                }
            },
            error: function(){
                parent.layer.alert(tipTexts.error);
            }
        });

        return false;
    });
}
function doModify(id){
    window.location.href = urlhead+'/rest/wdt/task/editTaskPage?id=' + id;
}
function addTaskNode(){
    var startDay = formatDate($('#startDay').val(),'temp');
    var endDay = formatDate($('#endDay').val(),'temp');
    var tpl = _.template($('#template-addNode').html());
    parent.layer.ready(function(){
        var model = parent.layer.open({
            type: 1,
            area: ['500px','300px'],
            title: '新增子任务',
            zIndex:15,
            content: tpl({
                data: {},
                owner: {}
            }),
            success: function () {
                $('.layui-layer-btn').css('textAlign','center');
                $('[datepicker]').datepicker({
                    format: 'yyyy-mm-dd',
                    language: 'zh-CN',
                    autoHide: true,
                    startDate: startDay,
                    endDate: endDay
                })
                checkdate();
            },
            btn:['确定','取消'],
            btn1: function(){

                // 选人控件 必填项 判断
                var gu_continue = true;
                $('.gu-require').each(function() {
                    var $this = $(this);
                    if($this.find('input[name]').eq(0).val() == '') {
                        var msg = $this.data('msg')
                        parent.layer.msg('请选择'+ msg);
                        gu_continue = false;
                        return false;
                    }
                });
                if(!gu_continue) { return false; }


                addNode(model);
            },
            btn2: function(){
                parent.layer.close(model)
            }
        });

    });
}
function checkdate(){
    $('body').on('change','[datepicker]',function(){
        if($('[data-name="endDate"]').val() !='' &&
            $('[data-name="startDate"]').val() !='' &&
            $('[data-name="startDate"]').val() > $('[data-name="endDate"]').val()){
            $(this).val('');
            layer.alert('开始时间不能大于结束时间');
        }
    });
}


$(function () {
    window.dt = $table.DataTable({
        "iDisplayLength": pageSize,
        "searching" : false,
        "bLengthChange":false,
        "bFilter": false,
        "ordering": false,
        "paging" : true,
        "serverSide" : true,
        "ajax" : function(data, callback, settings) {
            var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
            $.ajax({
                "type": 'post',
                "url" : urlhead + '/rest/wdt/taskChild/searchChildTask',
                "contentType":'application/json;charset=utf-8',
                "data" : getData(data.start),
                "success":function(res) {
                    parent.layer.close(loadModel);
                    window.dataList = res.value.page.list || [];
                    for(var i =0;i<dataList.length;i++){
                        dataList[i].index=i+1+parseInt(data.start)
                    }
                    callback({
                        recordsTotal: res.value.page.count,
                        recordsFiltered: res.value.page.count,
                        data: dataList
                    })
                },
                "error": function(){
                    parent.layer.alert(tipTexts.error);
                }
            })
        },

        "columns": [
            { 'data': 'index' },
            { 'data': 'name' },
            { 'data': 'owner.name' },
            { 'data': 'executionStatus' },
            { 'data': 'executionStatus' }
        ],
        "columnDefs" : [
            {
                "sClass" : 'left',
                "targets": 1,
                "render": function(data,type,row) {
                    return '<a href="javascript:;" style="color:#333;" onclick="goDetail(\''+ row.id +'\')">'+data+'</a>';
                }

            },
            {   "width": "auto",
                "render" : function(data, type, row) {
                    // return window.taskStatus[data]
                    return '<span style="color:#'+['EE7701','FF6600','FF0000','339900','FF6600','999999','FF0000','0099FF','0066FF','660099','660000'][data]+';">'+ window.taskStatus[data] +'</span>'
                },
                "targets" : 3
            },
            {   "width": "auto",
                "render" : function(data, type, row) {
                    return window.getButtonsByState({
                        state: data,
                        row: row,
                        type: 'childTaskList',
                       isTaskOwner: isTaskOwner
                    })
                },
                "targets" : 4,
                "sClass"　:　"leftBtns"
            }
        ]

    });

    window.reloadTable = function(){
    	// dt.ajax.reload();
        window.location.reload();
    }
})