/**
 * Created by bob.xu on 2017/8/10.
 */
var start = 0,pageSize=2;
$(function () {
    queryList(false);
    $('.load-more').on('click',function(){
        start = parseInt(start) + parseInt(pageSize);
        queryList(true);
    });
    $('#feedback_nav').on('click','li[data-type]',function(){
        $(this).addClass('active').siblings('li[data-type]').removeClass('active');
        if($(this).data('type') == 'progress'){
            $('#progress_div').show();
            $('#log_div').hide();
        }else if($(this).data('type') == 'log'){
            $('#progress_div').hide();
            $('#log_div').show();
        }
    });

    //日志列表
    $('#taskNode_list').DataTable({
        "searching" : false,
        "bLengthChange":false,
        "bFilter": false,
        "ordering": false,
        "paging" : true
    });
});
function queryList(more){
    var tpl = _.template($('#template-taskProgressList').html());
    if(!more){
        start = 0;
    }
    var data={
        type: 2,
        start: start,
        length: pageSize,
        taskItemId: taskNodeId
    };
    var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
    $.ajax({
        url : urlhead+'/rest/wdtTTaskComment/list',
        type : 'post',
        data : JSON.stringify(data),
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',
        success : function(res) {
            parent.layer.close(loadModel);
            if(res.result){
                if(more){
                    $('#taskProgress_list').append(tpl({
                        list: res.data.list
                    }))
                }else{
                    if(res.data.list && res.data.list.length != 0){
                        var listItem = res.data.list[0].schedule;
                        $('#node_gress').css('width',listItem+'%');
                        $('#node_gress').empty().html(listItem+'%');
                    }
                    $('#taskProgress_list').empty().html(tpl({
                        list: res.data.list
                    }))
                }
                if(parseInt(data.start)+parseInt(data.length) >= res.data.count){
                    $('.load-more').hide();
                }else{
                    $('.load-more').show();
                }
                setTimeout(function(){
                    checkPermission();
                },100);
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
}
function addBtn(){
    var tpl = _.template($('#template-nodeDetail').html());
    var timeTemp = new Date().getTime();
    var um;
    parent.layer.ready(function(){
        var model = parent.layer.open({
            type: 1,
            area: ['800px','500px'],
            title: '节点汇报',
            zIndex:1000,
            content: tpl({
                timeTemp: timeTemp,
                id: '',
                schedule: 0
            }),
            success: function () {
                um = UM.getEditor('edit_'+timeTemp,{
                    toolbar: toolbar
                });
                $('input[data-name="schedule"]').on('paste', function () {
                    checkProgressVal(this);
                }).on('keyup', function () {
                    checkProgressVal(this);
                }).on('blur', function () {
                    checkProgressVal(this);
                })
            },
            btn:['确定','取消'],
            btn1: function(){
                addNewProgress(um.getContent(),model);
            },
            btn2: function(){
                parent.layer.close(model)
            }
        });

    });
}
function addNewProgress(content,model){
    checkRequired('progress_detail', function () {
        var data={
            type: 2,
            id: $('#progress_detail input[data-name="id"]').val(),
            schedule: $('#progress_detail input[data-name="schedule"]').val(),
            content: content,
            taskItemId: taskNodeId
        };
        if(content == ''){
            parent.layer.alert(tipTexts.required);
            return false;
        }else if(data.schedule == 0){
            parent.layer.tips(tipTexts.progressNum, $('#progress_detail input[data-name="schedule"]'));
            return false;
        }

        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.ajax({
            url : urlhead+'/rest/wdtTTaskComment/save',
            type : 'post',
            data : JSON.stringify(data),
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            success : function(res) {
                parent.layer.close(loadModel);
                if(res.result){
                    parent.layer.alert(tipTexts.success);
                    parent.layer.close(model);
                    queryList(false);
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
    })
}
function updateProgress(id,schedule,content){
    var tpl = _.template($('#template-nodeDetail').html());
    var timeTemp = new Date().getTime();
    var um;
    parent.layer.ready(function(){
        var model = parent.layer.open({
            type: 1,
            area: ['800px','500px'],
            title: '节点汇报',
            zIndex:1000,
            content: tpl({
                timeTemp: timeTemp,
                id: id,
                schedule: schedule
            }),
            success: function () {
                um = UM.getEditor('edit_'+timeTemp,{
                    toolbar: toolbar
                });
                um.setContent(content)
                $('input[data-name="schedule"]').on('paste', function () {
                    checkProgressVal(this);
                }).on('keyup', function () {
                    checkProgressVal(this);
                }).on('blur', function () {
                    checkProgressVal(this);
                })
            },
            btn:['确定','取消'],
            btn1: function(){
                addNewProgress(um.getContent(),model);
            },
            btn2: function(){
                parent.layer.close(model)
            }
        });

    });
}
function doDone(id){
    if($('#node_gress').html() != '100%'){
        parent.layer.alert(tipTexts.nodeComplete);
        return false;
    }
    parent.layer.ready(function(){
        parent.layer.confirm('确认完成该节点任务吗？',{
            btn:['确定','取消']
        }, function () {
            completeNode(id)
        });
    });
}
function completeNode(id){
    var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
    $.ajax({
        url : urlhead+'/rest/wdtTTaskItem/finish',
        type : 'post',
        data : JSON.stringify({
            taskItemId: id
        }),
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',
        success : function(res) {
            parent.layer.close(loadModel);
            if(res.result){
                parent.layer.alert(tipTexts.success,function(){
                    goback();
                });

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
}
function checkPermission(){
    var executionStatus = $('#executionStatus').val();
    var isTaskOwner = eval($('#isTaskOwner').val());
    var isTaskItemOwner = eval($('#isTaskItemOwner').val());
    //校验完成按钮显示隐藏
    if(executionStatus != 3){ //进行中
        $('#finish_btn').hide();
    }else if(executionStatus == 3 && isTaskOwner){ //主任务负责人
        $('#finish_btn').show();
    }else if(executionStatus == 3 && isTaskItemOwner){ //节点负责人
        $('#finish_btn').show();
    }
    //校验进度汇报新增、编辑按钮显示隐藏
    if(executionStatus != 3 && executionStatus != 6){ //进行中、逾期未完成
        $('#addNewProgress').hide();
    }else if(executionStatus == 3 && isTaskOwner){ //进行中 & 主任务负责人
        $('#addNewProgress').show();
    }else if(executionStatus == 6 && isTaskOwner){ //逾期未完成 & 主任务负责人
        $('#addNewProgress').show();
    }
}
function goback(){
    window.history.go(-1);
}