/**
 * Created by bob.xu on 2017/8/17.
 */
var pageNo = 1, pageSize = 10, totalCount = 0;
var $table = $('#task-table');
var $name = $('[name="name"]'),
    $content = $('[name="content"]'),
    $startDate = $('[name="startDate"]'),
    $endDate = $('[name="endDate"]'),
    $exeStatus = $('[name="executionStatus"]'),
    $themeId = $('[name="themeId"]'),
    $ownerOfficeId = $('[name="owner.office.id"]'),
    $ownerName = $('[name="owner.name"]');
var dataList;

function getData(start,size){
    return JSON.stringify({
        "start": start,
        "length": size || pageSize,
        "themeId": $themeId.val(),
        "name": $name.val(),
        "content": $content.val(),
        "startDate": $startDate.val()?($startDate.val()+' 00:00:00.0'):'',
        "endDate": $endDate.val()?($endDate.val()+' 23:59:59.999'):'',
        "executionStatus": $exeStatus.val(),
        "owner" : {"office":{"id":$ownerOfficeId.val()},"name" : $ownerName.val()}
        
    })
}
function goDetail(id){
    setCookieFromUrl();
    window.location.href = urlhead+'/rest/wdt/task/taskDetail?id=' + id;
}
function refreshPage(){
    window.location.reload();
}
function doModify(id){
    window.location.href = urlhead+'/rest/wdt/task/editTaskPage?id=' + id;
}
function doDelete(id,fromDetail){
    layer.confirm('请注意，删除的任务将无法恢复，是否确认删除？', {
        btn: ['确认','取消'] //按钮
    }, function(){
        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.ajax({
            url: urlhead+'/rest/task/delete',
            type : 'post',
            data : JSON.stringify({
                taskId: id
            }),
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            success : function(res) {
                parent.layer.close(loadModel);
                if(res.result){
                    parent.layer.alert(tipTexts.success);
                    if(fromDetail != "undefind"){
                        window.location.href = urlhead + '/rest/wdt/task';
                    }else{
                        window.reloadTable();
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
                parent.layer.close(loadModel);
                parent.layer.alert(tipTexts.error);
            }
        })
    });
}
function doSubmit(id, type){
    layer.confirm('是否确认提交？', {
        btn: ['确认','取消'] //按钮
    }, function(){
        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.ajax({
            url : urlhead+'/rest/wdt/task/submit?id='+id,
            type : 'post',
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            success : function(res) {
                parent.layer.close(loadModel);
                if(res.result){
                    parent.layer.alert(tipTexts.success);
                    //refresh page
                   refreshPage();
                    
                }else{
                    if(res.message){
                        parent.layer.alert(res.message);
                    }else{
                        parent.layer.alert(tipTexts.fail);
                    }
                }
                
            },
            error: function(){
                parent.layer.close(loadModel);
                parent.layer.alert(tipTexts.error);
            }
        });
    });
}
function doAccept(id, type){
    layer.confirm('是否确认接收？', {
        btn: ['确认','取消'] //按钮
    }, function(){
        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.ajax({
            url : urlhead+'/rest/wdt/task/receiveTask',
            type : 'post',
            data : JSON.stringify({
                id: id
            }),
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            success : function(res) {
                parent.layer.close(loadModel);
                if(res.result){
                    parent.layer.alert(tipTexts.success);
                    if(type == '0'){  //详情页子任务接收成功跳转至修改页
                        doModify(id);
                    }else if(type == '1'){ //详情页主任务接收成功刷新页面
                        refreshPage();
                    }else{
                        if(_.findWhere(dataList,{"id":id}).isRelationTask==0){//任务管理列表页子任务接收成功跳转至修改页
                            doModify(id);
                        }else{
                            window.reloadTable(); //任务管理列表主任务接收成功刷新列表
                        }
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
                parent.layer.close(loadModel);
                parent.layer.alert(tipTexts.error);
            }
        });
    });
}

function doConclude(id, type){
    var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
    $.ajax({
        url : urlhead+'/wdt/wdtTTaskBanjieLog/existUnclearSubTask',
        type : 'post',
        data : JSON.stringify({
            taskId: id
        }),
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',
        success : function(res) {
            parent.layer.close(loadModel);
            if(res.result){
                doConcludeTrue(id, type);
            }else{
                if(res.data){
                    var childTaskNamesArr = [];
                    var childTaskNames;
                    _.each(res.data,function(item){
                        childTaskNamesArr.push(item.name);
                    });
                    childTaskNames = childTaskNamesArr.join(',');
                }
                parent.layer.ready(function(){
                    var model = parent.layer.confirm('该主任务下有子任务：'+childTaskNames+'任务未完成，是否继续办结？', {
                        btn: ['确认','取消'] //按钮
                    }, function(){
                        parent.layer.close(model);
                        doConcludeTrue(id, type);
                    }, function(){
                        parent.layer.close(model);
                    });
                });
            }
        },
        error: function(){
            parent.layer.close(loadModel);
            parent.layer.alert(tipTexts.error);
        }
    });
}
function doConcludeTrue(id, type){
    getTaskDetail(id,function(res){
        var data = res;
        var tpl = _.template($('#record-banjie').html());
        parent.layer.ready(function(){
            var model = parent.layer.open({
                type: 1,
                area: ['500px','300px'],
                title: '任务办结',
                zIndex:1000,
                content: tpl({
                    data: data
                }),
                success: function () {
                  $('.layui-layer-btn').css('textAlign','center');
                },
                btn:['办结','取消'],
                btn1: function(){
                    doConcludeAjax(id,model, type);
                },
                btn2: function(){
                    parent.layer.close(model);
                },end:function() {
                	//refresh page
                    if(type!= "undefined" && type != '' && type != null){
                    	refreshPage();
                    }else {
                    	window.reloadTable();
                    }
                }
            });

        });
    });
}
function doConcludeAjax(id,model, type) {
    checkRequired('banjie_div',function(){
        var data = {}
        $('#banjie_div [data-name]').each(function (i,info) {
            data[$(info).data('name')] = $(info).val();
        })
        data['taskId'] = id;
        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.ajax({
            url : urlhead+'/rest/task/banjie',
            type : 'post',
            data : JSON.stringify(data),
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            success : function(res) {
                parent.layer.close(loadModel);
                if(res.result){
                	parent.layer.alert(res.message, function(index) {
	                     parent.layer.close(model);  
	                     layer.close(index); // 关闭提示框
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
                parent.layer.close(loadModel);
                parent.layer.alert(tipTexts.error);
            }
        });

        return false;
    });
}
/**
 * 任务催办 created by gorden
 *
 * */
function  postDatetoremiand(taskId,ids,model,val) {
    var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
    $.post(urlhead+'/rest/wdt/urgetask/saveUrges',{'taskId':taskId,'items':ids,'content':val},function (res) {
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
    })

}
function remindTask(id){
    var taskId=id;
    parent.layer.ready(function(){
        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.post(urlhead+'/rest/wdt/urgetask/getTaskItems',{'id':id},function (res) {

            parent.layer.close(loadModel);
             if(res.result==true){
                 if(res.value==undefined||res.value.length<1){
                     parent.layer.alert("该任务暂无催办节点");
                     return false;
                 }
                 var data = [];
                 if(res.value){
                     data = res.value;
                 }
                 var tpl = _.template($('#record-remind').html());
                 var model = parent.layer.open({
                     type: 1,
                     area: ['800px','300px'],
                     title: '任务催办',
                     zIndex:1000,
                     content:tpl({
                         data: data
                     }) + '<div class="panel-body" style="padding-top:0;"><textarea id="remindTextarea" placeholder="请输入催办建议"></textarea></div>',
                     success: function () {
                        $('.layui-layer-btn').css('textAlign','center');
                            // $('#remindTextarea').bind('keydown keyup focus blur',function() {
                            //     var val = $(this).val();
                            //     $('#remindRest').text('您还可以输入 '+ (300-val.length) +' 字');
                            // });
                     },
                     btn:['催办','取消'],
                     btn1: function(){
                         var str="";
                         var val = $('#remindTextarea').val();


                         if($(".remind_chekbox:checked").size()<1) {
                             parent.layer.alert("未勾选任务催办节点")
                             return false;
                         }

                         // if( val.length > 300) {
                         //     parent.layer.alert("催办建议超过长度限制");
                         //     return false;
                         // }


                         $(".remind_chekbox:checked").each(function(){
                             str+=$(this).attr("id")+",";
                         });

                         postDatetoremiand(taskId,str,model,val);
                     },
                     btn2: function(){
                         parent.layer.close(model)
                     }
                 });

             }
        })


    });
}
/**任务催办 end* */
function pauseTask(id,type){
    getTaskDetail(id,function(res){
        var data = res;
        var tpl = _.template($('#record-pause').html());
        parent.layer.ready(function(){
            var model = parent.layer.open({
                type: 1,
                area: ['500px','350px'],
                title: '任务暂停',
                zIndex:1000,
                content: tpl({
                    data: data
                }),
                success: function () {
                  $('.layui-layer-btn').css('textAlign','center');
                    $('[datepicker]').datepicker({
                        format: 'yyyy-mm-dd',
                        language: 'zh-CN',
                        autoHide: true,
                        startDate: new Date()
                    })
                },
                btn:['暂停','取消'],
                btn1: function(){
                    pauseTaskAjax(id,model,type)
                },
                btn2: function(){
                    parent.layer.close(model);
                    type = '';
                },end:function() {
                	//refresh page
                    if(type!= "undefined" && type != '' && type != null){
                    	refreshPage();
                    }else {
                    	window.reloadTable();
                    }
                }
            });

        });
    });
}
function pauseTaskAjax(id,model,type) {
    checkRequired('pause_div',function(){
        var data = {}
        $('#pause_div [data-name]').each(function (i,info) {
            if($(info).data('name') == 'newTime' && $(info).val()){
                data[$(info).data('name')] = $(info).val()+' 00:00:00';
            }else if($(info).data('name') == 'newTime' && !$(info).val()){
                data[$(info).data('name')] = null;
            } else{
                data[$(info).data('name')] = $(info).val();
            }
        })
        data['taskId'] = id;
        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.ajax({
            url : urlhead+'/rest/task/pause',
            type : 'post',
            data : JSON.stringify(data),
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            success : function(res) {
                parent.layer.close(loadModel);
                if(res.result){
                	parent.layer.alert(res.message, function(index) {
	                     parent.layer.close(model);  
	                     layer.close(index); // 关闭提示框
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
                parent.layer.close(loadModel);
                parent.layer.alert(tipTexts.error);
            }
        });

        return false;
    });
}
function cancelTask(id,type){
    getTaskDetail(id,function(res){
        var data = res;
        var tpl = _.template($('#record-cancel').html());
        parent.layer.ready(function(){
            var model = parent.layer.open({
                type: 1,
                area: ['500px','300px'],
                title: '任务取消',
                zIndex:1000,
                content: tpl({
                    data: data
                }),
                success: function () {
                  $('.layui-layer-btn').css('textAlign','center');
                },
                btn:['确定','取消'],
                btn1: function(){
                    cancelTaskAjax(id,model,type);
                },
                btn2: function(){
                    parent.layer.close(model);
                    type = '';
                },end:function() {
                	//refresh page
                    if(type!= "undefined" && type != '' && type != null){
                    	refreshPage();
                    }else {
                    	window.reloadTable();
                    }
                }
            });

        });
    });
}
function cancelTaskAjax(id,model,type) {
    checkRequired('cancel_div',function(){
        var data = {}
        $('#cancel_div [data-name]').each(function (i,info) {
            data[$(info).data('name')] = $(info).val();
        })
        data['taskId'] = id;
        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.ajax({
            url : urlhead+'/rest/task/cancel',
            type : 'post',
            data : JSON.stringify(data),
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            success : function(res) {
                parent.layer.close(loadModel);
                if(res.result){
                	parent.layer.alert(res.message, function(index) {
	                     parent.layer.close(model);  
	                     layer.close(index); // 关闭提示框
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
                parent.layer.close(loadModel);
                parent.layer.alert(tipTexts.error);
            }
        });

        return false;
    });
}
function delayTask(id,type){
    getTaskDetail(id,function(res){
        var data = res;
        var tpl = _.template($('#record-delay').html());
        parent.layer.ready(function(){
            var model = parent.layer.open({
                type: 1,
                area: ['500px','380px'],
                title: '任务延期',
                content: tpl({
                    data: data
                }),
                success: function () {
                  $('.layui-layer-btn').css('textAlign','center');
                    var endDay = '';
                    if(data.isRelationTask == 0){ //子任务
                        endDay = formatDate(data.parent.endDate);
                    }
                    $('[datepicker]').datepicker({
                        format: 'yyyy-mm-dd',
                        language: 'zh-CN',
                        autoHide: true,
                        startDate: formatDate(data.endDate),
                        endDate: endDay
                    })
                },
                btn:['延期','取消'],
                btn1: function(){
                    delayTaskAjax(id,model,type);
                },
                btn2: function(){
                    parent.layer.close(model);
                    type = '';
                },end:function() {
                	//refresh page
                    if(type!= "undefined" && type != '' && type != null){
                    	refreshPage();
                    }else {
                    	window.reloadTable();
                    }
                }
            });

        });
    });
}
function delayTaskAjax(id,model,type) {
    checkRequired('delay_div',function(){
        var data = {}
        $('#delay_div [data-name]').each(function (i,info) {
            if($(info).data('name') == 'newTime'){
                data[$(info).data('name')] = $(info).val()+' 00:00:00';
            } else{
                data[$(info).data('name')] = $(info).val();
            }
        })
        data['taskId'] = id;
        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.ajax({
            url : urlhead+'/rest/task/extension',
            type : 'post',
            data : JSON.stringify(data),
            dataType: 'json',
            contentType:'application/json;charset=UTF-8',
            success : function(res) {
                parent.layer.close(loadModel);
                if(res.result){
	            	parent.layer.alert(res.message, function(index) {
	                     parent.layer.close(model);  
	                     layer.close(index); // 关闭提示框
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
                parent.layer.close(loadModel);
                parent.layer.alert(tipTexts.error);
            }
        });

        return false;
    });
}
function getTaskDetail(id,fn){
    var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
    $.ajax({
        url : urlhead+'/rest/wdt/task/taskDetailById',
        type : 'post',
        data : JSON.stringify({
            id: id
        }),
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',
        success : function(res) {
            parent.layer.close(loadModel);
            if(res.result){
                if(res.data){
                    !!fn && fn(res.data);
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
            parent.layer.close(loadModel);
            parent.layer.alert(tipTexts.error);
        }
    });
}
function doTransfer(id, type){
    getTaskDetail(id,function(res){
        var data = res;
        var tpl = _.template($('#record-transfer').html());
        parent.layer.ready(function(){
            var model = parent.layer.open({
                type: 1,
                area: ['500px','400px'],
                title: '任务移交',
                zIndex:15,
                content: tpl({
                    data: data
                }),
                success: function () {
                  $('.layui-layer-btn').css('textAlign','center');
                },
                btn:['移交','取消'],
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
                    
                    
                    doTransferAjax(id,model,type)
                },
                btn2: function(){
                    parent.layer.close(model)
                }
            });

        });
    });
}
function doTransferAjax(id,model,type){
    checkRequired('transfer_div',function(){
        var data = {}
        $('#transfer_div [data-name]').each(function (i,info) {
            data[$(info).data('name')] = $(info).val();
        })
        data['taskId'] = id;
        delete data['user'];
        var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
        $.ajax({
            url : urlhead+'/rest/task/handover',
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
                    //refresh page
                    if(type!= "undefined" && type != '' && type != null){
                    	refreshPage();
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
                parent.layer.close(loadModel);
                parent.layer.alert(tipTexts.error);
            }
        });

        return false;
    });
}
function rejectTask(id,type){
    parent.layer.ready(function(){
        var model = parent.layer.confirm('是否确认拒绝？', {
            btn: ['拒绝','取消'] //按钮
        }, function(){
            rejectTaskAjax(id,model,type);
        }, function(){
            parent.layer.close(model);
        });
    });
}
function rejectTaskAjax(id,model,type){
    var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
    $.ajax({
        type: "POST",
        url:urlhead+'/rest/task/refuse',
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            taskId: id
        }),
        success : function(res) {
            parent.layer.close(loadModel);
            if(res.result){
                parent.layer.alert(tipTexts.success);
                parent.layer.close(model);
                window.reloadTable();
                //refresh page
                if(type!= "undefined" && type != '' && type != null){
                	refreshPage();
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
            parent.layer.close(loadModel);
            parent.layer.alert(tipTexts.error);
        }
    })
}

function turnBackTask(id,type){
    parent.layer.ready(function(){
        var model = parent.layer.confirm('是否确认回退？', {
            btn: ['回退','取消'] //按钮
        }, function(){
        	turnBackTaskAjax(id,model,type);
        }, function(){
            parent.layer.close(model);
        });
    });
}
function turnBackTaskAjax(id,model,type){
    var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
    $.ajax({
        type: "POST",
        url:urlhead+'/rest/task/turnBack',
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            taskId: id
        }),
        success : function(res) {
            parent.layer.close(loadModel);
            if(res.result){
                parent.layer.alert(res.message);
                parent.layer.close(model);
                
                //refresh page
                if(type!= "undefined" && type != '' && type != null){
                	refreshPage();
                }else{
                	window.reloadTable();
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
            parent.layer.close(loadModel);
            parent.layer.alert(tipTexts.error);
        },end:function() {
        	//refresh page
            if(type!= "undefined" && type != '' && type != null){
            	refreshPage();
            }else {
            	window.reloadTable();
            }
        }
    })
}
function continueTask(id,type){
    parent.layer.ready(function(){
        var model = layer.confirm('是否确认继续？', {
            btn: ['继续','取消'] //按钮
        }, function(){
            var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
            $.ajax({
                url : urlhead+'/rest/task/continue',
                type : 'post',
                data : JSON.stringify({
                    taskId: id
                }),
                dataType: 'json',
                contentType:'application/json;charset=UTF-8',
                success : function(res) {
                    parent.layer.close(loadModel);
                    if(res.result){
                        parent.layer.alert(tipTexts.success);
                        parent.layer.close(model);
                        window.reloadTable();
                        //refresh page
                        if(type!= "undefined" && type != '' && type != null){
                        	refreshPage();
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
                    parent.layer.close(loadModel);
                    parent.layer.alert(tipTexts.error);
                }
            });
        }, function(){
            layer.close(model);
        });
    });
}

function recallTask(id,model,type){
	parent.layer.ready(function(){
        var model = parent.layer.confirm('是否确认撤回？', {
            btn: ['确定','取消'] //按钮
        }, function(){
        	recallTaskAjax(id,model,type);
        }, function(){
            parent.layer.close(model);
        });
    });
}
function recallTaskAjax(id,model,type) {
	var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
    $.ajax({
        type: "POST",
        url:urlhead+'/rest/task/recall',
        contentType: "application/json",
        dataType: "json",
        data: JSON.stringify({
            taskId: id
        }),
        success : function(res) {
            parent.layer.close(loadModel);
            if(res.result){
                parent.layer.alert(tipTexts.success);
                parent.layer.close(model);
                window.reloadTable();
                //refresh page
                if(type!= "undefined" && type != '' && type != null){
                	refreshPage();
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
            parent.layer.close(loadModel);
            parent.layer.alert(tipTexts.error);
        }
    })
}
function goNodeList(id){
    window.location.href = urlhead+'/wdtTTaskItem?taskId='+id;
}

function reloadTable(){
    window.dt.ajax.reload();
}
$(function() {
    $('[datepicker]').datepicker({
        format: 'yyyy-mm-dd',
        autoHide: true,
        language: 'zh-CN'
    })
    window.dt = $table.DataTable({
        "iDisplayLength": pageSize,
        "searching" : false,
        // "bLengthChange":false,
        "dom": 'tlip',
        "language": {
            "sLengthMenu": "显示 _MENU_ 项 &nbsp;"
        },
        "bFilter": false,
        "ordering": false,
        "paging" : true,
        "serverSide" : true,
        "bAutoWidth" : false,
        "ajax" : function(data, callback, settings) {
            // make a regular ajax request using data.start and data.length
            var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
            var startNum = data.start;
            $.ajax({
                "type": 'post',
                "url" : urlhead+'/rest/wdt/task/searchTask',
                "contentType":'application/json;charset=utf-8',
                "data" : getData(data.start,$('[name=task-table_length]').val()),
                "success":function(res) {
                    // map your server's response to the DataTables format and pass it to
                    // DataTables' callback
                    parent.layer.close(loadModel);
                    var data = res.value.page.list || [];
                    dataList = res.value.page.list || [];
                    for(var i =0;i<data.length;i++){
                        data[i].index=i+1+parseInt(startNum);
                    }
                    callback({
                        recordsTotal: res.value.page.count,
                        recordsFiltered: res.value.page.count,
                        data: data
                    })
                }
            })
        },

        "columns": [
            { 'data': 'index' },
            { 'data': 'name' },
            { 'data': 'overCount' },
            { 'data': 'totalCount' },
            { 'data': 'days' },
            { 'data': 'updateDate' },
            { 'data': 'infactCompletionTime' },
            { 'data': 'executionStatus' },
            { 'data': 'executionStatus' }
        ],
        "columnDefs" : [
            {
                "sWidth" : "4%",
                "targets" : 0
            },{
                "sWidth" : "10%",
                "targets" : 1,
                "sClass" : 'left',
                "render" : function(data, type, row) {
                    var isRelationTask = row.isRelationTask==0?'<span class="tag-orange_child">子</span>':'<span class="tag-orange_main">主</span>';
                    return '<div class="ellipsis" style="max-width: 200px;">'+ isRelationTask + '<a style="color:inherit;" onclick="goDetail(\''+ row.id +'\')" >'+ row.name + '</a></div>';
                }
            },{
                "sWidth" : "10%",
                "render" : function(data, type, row) {
                    var progress = row.progress||row.index*10;
                    var statusClass = '';
                    if((row.executionStatus==3||row.executionStatus==6)&&row.showColor){

                        statusClass = row.showColor > 1 ? 'progress-bar-warning':'progress-bar-danger'

                    }
                    return  '<div class="progress progress-mini progress-100">'+
                            '<div style="width: '+(progress>24?progress+'%':'24px')+';" class="progress-bar '+statusClass+'">'+progress+'%</div>'+
                            '</div>';
                },
                "targets" : 2
            },{
                "sWidth" : "8%",
                "render" : function(data, type, row) {
                    return row.overCount +'/'+row.totalCount
                },
                "targets" : 3
            },
            {
                "sWidth" : "9%",
                "render" : function(data, type, row) {//todo回复数/阅读数
                    return row.reponseCount +'/'+row.readCount
                },
                "targets" : 4
            },
            {
                "sWidth" : "20%",
                "render" : function(data, type, row) {
                    return row.newCommentsInfo?row.newCommentsInfo:''
                },
                "targets" : 5
            },
            {
                "sWidth" : "8%",
                "render" : function(data, type, row) {
                    return row.infactCompletionTime?formatDate(row.infactCompletionTime):''
                },
                "targets" : 6
            },
            {
                "sWidth" : "8%",
                "render" : function(data, type, row) {
                    return '<span style="color:#'+['EE7701','FF6600','FF0000','339900','FF6600','999999','FF0000','0099FF','0066FF','660099','660000'][data]+';">'+ window.taskStatus[data] +'</span>'
                },
                "targets" : 7
            },
            {
                "sClass" : 'leftBtns',
                "render" : function(data, type, row) {
                    return window.getButtonsByState({
                        state: data,
                        row: row
                    })
                },
                "targets" : 8
            }
        ]

    });
    // 计算剩余天数
    $startDate.change(function () {
        if($endDate.val()){
            if($startDate.val()>$endDate.val()){
                layer.alert('开始日期不得晚于截止日期！');
                $startDate.val('')
            }
        }
    })
    $endDate.change(function () {
        if($startDate.val()){
            if($startDate.val()>$endDate.val()){
                layer.alert('开始日期不得晚于截止日期！');
                $endDate.val('')
            }
        }
    })
});