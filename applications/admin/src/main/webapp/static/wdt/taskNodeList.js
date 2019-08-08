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
    window.location.href = urlhead + '/wdtTTaskItem/info?id=' + id +'&taskId='+taskId;
}
function addNode(content,model) {
    checkRequired('addNode_div',function(){
        var data = {}
        $('#addNode_div [data-name]').each(function (i,info) {
            if($(info).data('name') == 'startDate'){
                data[$(info).data('name')] = $(info).val()+' 00:00:00';
            }else if($(info).data('name') == 'requiredCompletionTime'){
                data[$(info).data('name')] = $(info).val()+' 23:59:59';
            } else{
                data[$(info).data('name')] = $(info).val();
            }
        })
        data['itemContent'] = content;
        var readerIds = data['readerIds'];
        if(readerIds){
            data['readerIds'] = readerIds.split(',');
        }else{
            data['readerIds'] = [];
        }

        delete data['readers'];
        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.ajax({
            url : urlhead+'/rest/wdtTTaskItem/save',
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
    var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
    $.ajax({
        url : urlhead+'/rest/wdtTTaskItem/info',
        type : 'post',
        data : JSON.stringify({
            taskItemId: id
        }),
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',
        success : function(res) {
            parent.layer.close(loadModel);
            if(res.result){
                var data = res.data;
                //console.log(data);
                var tpl = _.template($('#template-addNode').html());
                var timeTemp = new Date().getTime();
                var um,readerNames=[],readerIds=[];
                if(data.readerUsers){
                    _.each(data.readerUsers,function(item){
                        readerNames.push(item.name);
                        readerIds.push(item.id);
                    })
                    readerNames.join(',');
                    readerIds.join(',');
                    data['readers'] = readerNames;
                    data['readerIds'] = readerIds;
                }
                var startDay = $('#startDay').val();
                var endDay = $('#endDay').val();
                parent.layer.ready(function(){
                    var model = parent.parent.layer.open({
                        type: 1,
                        area: ['800px','450px'],
                        zIndex:15,
                        title: '修改节点',
                        content: tpl({
                            timeTemp: timeTemp,
                            data: data,
                            type: true
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
                            um = UE.getEditor('edit_'+timeTemp,{
                                toolbar: toolbar
                            });
                            checkdate();
                            um.ready(function() {//编辑器初始化完成再赋值
                                um.setContent(data.itemContent);  //赋值给UEditor
                            });
                        },
                        btn:['确定','取消'],
                        btn1: function(){
                            addNode(um.getContent(),model)
                        },
                        btn2: function(){
                            parent.layer.close(model)
                        }
                    });

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
function doDone(id){
    var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
    $.ajax({
        url : urlhead+'/rest/wdtTTaskItem/info',
        type : 'post',
        data : JSON.stringify({
            taskItemId: id
        }),
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',
        success : function(res) {
            parent.layer.close(loadModel);
            if(res.result){
                if(res.data.progress == 100){
                    parent.layer.ready(function(){
                        parent.layer.confirm('确认完成该节点任务吗？',{
                            btn:['确定','取消']
                        }, function () {
                            completeNode(id)
                        });
                    });
                }else{
                    parent.layer.alert(tipTexts.nodeComplete);
                }

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
function doTransfer(id){
    var tpl = _.template($('#template-removeNode').html());
    parent.layer.ready(function(){
        var model = parent.layer.open({
            type: 1,
            area: ['450px','180px'],
            zIndex:15,
            title: '移交节点',
            content: tpl(),
            success: function () {

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

                removeNode(id,model);
            },
            btn2: function(){
                parent.layer.close(model)
            }
        });

    });
}
function addTaskNode(){
    var tpl = _.template($('#template-addNode').html());
    var timeTemp = new Date().getTime();
    var um;
    var startDay = $('#startDay').val();
    var endDay = $('#endDay').val();
    parent.layer.ready(function(){
        var model = parent.layer.open({
            type: 1,
            area: ['700px','450px'],
            zIndex:15,
            title: '新增节点',
            content: tpl({
                timeTemp: timeTemp,
                data: '',
                type: false
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
                um = UE.getEditor('edit_'+timeTemp,{
                    toolbar: toolbar
                });
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

                addNode(um.getContent(),model)
            },
            btn2: function(){
                parent.layer.close(model)
            }
        });

    });
}
function checkdate(){
    var startDay = $('#startDay').val();
    var endDay = $('#endDay').val();
    $('body').on('change','[datepicker]',function(){
        if($('[data-name="requiredCompletionTime"]').val() !='' &&
            $('[data-name="startDate"]').val() !='' &&
            $('[data-name="startDate"]').val() > $('[data-name="requiredCompletionTime"]').val()){
            $(this).val('');
            layer.alert('节点开始时间不能大于节点结束时间');
        }
    });
}
function completeNode(id) {
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
                parent.layer.alert(tipTexts.success);
                window.reloadTable();
            }else{
                if(res.message){
                    parent.layer.alert(res.message);
                }else{
                    parent.layer.alert(tipTexts.fail);
                };
            }
        },
        error: function(){
            parent.layer.alert(tipTexts.error);
        }
    });

    return false;
}
function removeNode(id,model) {
    checkRequired('removeNode_div',function(){
        var data = {}
        $('#removeNode_div [data-name]').each(function (i,info) {
            data[$(info).data('name')] = $(info).val();
        })
        data['taskItemId'] = id;
        //console.log(data);

        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.ajax({
            url : urlhead+'/rest/wdtTTaskItem/handover',
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

$(function () {
    var isTaskOwner = eval($('#isTaskOwner').val());
    var taskStatus = eval($('#taskStatus').val());

    window.dt = $table.DataTable({
        "iDisplayLength": pageSize,
        "searching" : false,
        "bLengthChange":false,
        "bFilter": false,
        "ordering": false,
        "paging" : true,
        "serverSide" : true,
        "ajax" : function(data, callback, settings) {
            // make a regular ajax request using data.start and data.length
            var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
            $.ajax({
                "type": 'post',
                "url" : urlhead + '/rest/wdtTTaskItem',
                "contentType":'application/json;charset=utf-8',
                "data" : getData(data.start),
                "success":function(res) {
                    parent.layer.close(loadModel);
                    var dataList = res.data.list || [];
                    window.dataList = res.data.list || [];
                    for(var i =0;i<dataList.length;i++){
                        dataList[i].index=i+1+parseInt(data.start);
                    }
                    callback({
                        recordsTotal: res.data.count,
                        recordsFiltered: res.data.count,
                        data: dataList
                    })
                }
            })
        },
        "columns": [
            { 'data': 'index' },
            { 'data': 'itemName' },
            { 'data': 'progress' },
            { 'data': 'startDate' },
            { 'data': 'requiredCompletionTime' },
            { 'data': 'infactCompletionTime' },
            { 'data': 'owner' },
            { 'data': 'executionStatus' },
            { 'data': 'executionStatus' }
        ],
        "columnDefs" : [
            {
                "sWidth" : "10%",
                "targets" : 1,
                "sClass" : 'left',
                "render" : function(data, type, row) {
                    return '<a style="color:inherit;" onclick="goDetail(\''+ row.id +'\')" >'+ row.itemName + '</a>';
                }
            },
            {   "width": "auto",
                "render" : function(data, type, row) {
                	var statusClass='';
                	if(row.executionStatus=='6') {
                		statusClass = 'progress-bar-danger';
                	}
                    var progress = row.progress;
                    return '<div class="progress progress-mini progress-100">\
                                 <div style="width: '+progress+'%;min-width: 2em;" class="progress-bar '+statusClass+'">'+progress+'%</div>\
                            </div>';
                },
                "targets" : 2
            },{   "width": "auto",
                "render" : function(data, type, row) {
                    // return window.taskStatus[data]
                    return '<span style="color:#'+['EE7701','FF6600','FF0000','339900','FF6600','999999','FF0000','0099FF','0066FF','660099','660000'][data]+';">'+ window.taskStatus[data] +'</span>'
                },
                "targets" : 7
            },
            {   "width": "auto",
                "render" : function(data, type, row) {
                    return window.getButtonsByState({
                        state: data,
                        row: row,
                        type: 'nodeList',
                        isTaskOwner: isTaskOwner,
                        taskStatus: taskStatus
                    })
                },
                "targets" : 8,
                "sClass":'leftBtns'
            },
            {   "width": "auto",
                "render" : function(data, type, row) {
                    return formatDate(row.startDate)
                },
                "targets" : 3
            },
            {   "width": "auto",
                "render" : function(data, type, row) {
                    return formatDate(row.requiredCompletionTime)
                },
                "targets" : 4
            }
            ,
            {   "width": "auto",
                "render" : function(data, type, row) {
                    return formatDate(row.infactCompletionTime)
                },
                "targets" : 5
            }
        ]

    });
    window.reloadTable = function(){
        //dt.ajax.reload();
        window.location.reload();
    }
})