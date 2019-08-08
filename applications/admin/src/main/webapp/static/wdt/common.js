/**
 * Created by Bob.Bao on 2017/8/8.
 */
if($.fn.datepicker){
    $.fn.datepicker.languages['zh-CN'] = {
        format: 'yyyy年mm月dd日',
        days: ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'],
        daysShort: ['周日', '周一', '周二', '周三', '周四', '周五', '周六'],
        daysMin: ['日', '一', '二', '三', '四', '五', '六'],
        months: ['一月', '二月', '三月', '四月', '五月', '六月', '七月', '八月', '九月', '十月', '十一月', '十二月'],
        monthsShort: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月'],
        weekStart: 1,
        yearFirst: true,
        yearSuffix: '年'
    };
}

//时间格式化
Date.prototype.Format = function (formatStr) {
    var str = formatStr;
    var Week = ['日', '一', '二', '三', '四', '五', '六'];
    str = str.replace(/yyyy|YYYY/, this.getFullYear());
    str = str.replace(/yy|YY/, (this.getYear() % 100) > 9 ? (this.getYear() % 100).toString() : '0' + (this.getYear() % 100));
    str = str.replace(/MM/, (this.getMonth() + 1) > 9 ? (this.getMonth() + 1).toString() : '0' + (this.getMonth() + 1));
    str = str.replace(/M/g, (this.getMonth() + 1));
    str = str.replace(/w|W/g, Week[this.getDay()]);
    str = str.replace(/dd|DD/, this.getDate() > 9 ? this.getDate().toString() : '0' + this.getDate());
    str = str.replace(/d|D/g, this.getDate());
    str = str.replace(/hh|HH/, this.getHours() > 9 ? this.getHours().toString() : '0' + this.getHours());
    str = str.replace(/h|H/g, this.getHours());
    str = str.replace(/mm/, this.getMinutes() > 9 ? this.getMinutes().toString() : '0' + this.getMinutes());
    str = str.replace(/m/g, this.getMinutes());
    str = str.replace(/ss|SS/, this.getSeconds() > 9 ? this.getSeconds().toString() : '0' + this.getSeconds());
    str = str.replace(/s|S/g, this.getSeconds());
    return str
}
window.formatDate = function (str,type) {
    if(str == '' || str == null || str == 'null'){
        return '';
    }
    if(str){
        if(type){
        	
        	console.log(str);
        	typeof str == 'string' ? str = str.replace('+08:00','+0800') : null;
        	
            var dateStr = new Date(str).Format('yyyy-MM-dd');
        }else{
            var dateStr = new Date(str.replace(/-/g , '/')).Format('yyyy-MM-dd');
        }
        return dateStr;
    }else{
        return str;
    }

}
window.formatDateTime = function (str) {
    if(str == '' || str == null || str == 'null'){
        return '';
    }
    if(str){
        var dateStr = new Date(str).Format('yyyy-MM-dd HH:mm:ss');
        return dateStr;
    }else{
        return str;
    }

}
//UM富文本功能配置
window.toolbar = [
    'source | undo redo | bold italic underline strikethrough | superscript subscript | forecolor backcolor | removeformat |',
    'insertorderedlist insertunorderedlist | selectall cleardoc paragraph | fontfamily fontsize' ,
    '| justifyleft justifycenter justifyright justifyjustify |',
    'image |',
    'horizontal preview '
];
window.tipTexts = {
    success: '操作成功',
    fail: '操作失败',
    error: '请求异常',
    required: '请完成带*必填信息',
    progressNum: '请输入1-100的正整数',
    nodeComplete: '当前进度不是100%，不能完成!'
};
_.templateSettings={evaluate: /<@([\s\S]+?)@>/g, interpolate: /<@=([\s\S]+?)@>/g, escape: /<@-([\s\S]+?)@>/g}
var taskStatus = ['草稿','待接收','已拒绝','进行中','暂停','取消','逾期未完成','按时完成','按时办结','逾期完成','逾期办结'];
//执行状态（0：草稿 1：待接收 2：已拒绝 3：进行中 4：暂停 5：取消 6：逾期未完成 7：按时完成 8：按时办结 9：逾期完成 10：逾期办结 ）
function getButtonsByState(str,rp){
    var b = {
        VIEW : '<button class="btn btn-default btn-xs" type="button" onclick="goDetail(\''+str.row.id+'\')">'+
        '<i class="fa fa-eye"></i>&nbsp;&nbsp;<span class="bold">查看</span>'+
        '</button>',
        MODIFY:'<button class="btn btn-default btn-xs" type="button" onclick="doModify(\''+str.row.id+'\', \''+str.type+'\')">'+
        '<i class="fa fa-edit"></i>&nbsp;&nbsp;<span class="bold">修改</span>'+
        '</button>',
        SUBMIT: '<button class="btn btn-jieshou btn-xs" type="button" onclick="doSubmit(\''+str.row.id+'\', \''+str.type+'\')">'+
        '<i class="fa fa-upload"></i>&nbsp;&nbsp;<span class="bold">提交</span>'+
        '</button>',
        DELETE: '<button class="btn btn-default btn-xs" type="button" onclick="doDelete(\''+str.row.id+'\', \''+str.type+'\')">'+
        '<i class="fa fa-remove"></i>&nbsp;&nbsp;<span class="bold">删除</span>'+
        '</button>',
        ACCEPT:'<button class="btn btn-jieshou btn-xs" type="button" onclick="doAccept(\''+str.row.id+'\', \''+str.type+'\')">'+
        '<i class="fa fa-send"></i>&nbsp;&nbsp;<span class="bold">接收</span>'+
        '</button>',
        REFUSE: '<button class="btn btn-jujue btn-xs" type="button" onclick="rejectTask(\''+str.row.id+'\', \''+str.type+'\')">'+
        '<i class="fa fa-reply"></i>&nbsp;&nbsp;<span class="bold">拒绝</span>'+
        '</button>',
        DECOMPOSE: '<button class="btn btn-fenjie btn-xs" type="button" onclick="goNodeList(\''+str.row.id+'\')">'+
        '<i class="fa fa-tree"></i>&nbsp;&nbsp;<span class="bold">分解</span>'+
        '</button>',
        TRANSFER: '<button class="btn btn-fenjie btn-xs" type="button" onclick="doTransfer(\''+str.row.id+'\', \''+str.type+'\')">'+
        '<i class="fa fa-send"></i>&nbsp;&nbsp;<span class="bold">移交</span>'+
        '</button>',
        PUSH: '<button class="btn btn-cuiban btn-xs" type="button" onclick="remindTask(\''+str.row.id+'\')">'+
        '<i class="fa fa-calendar-minus-o"></i>&nbsp;&nbsp;<span class="bold">催办</span>'+
        '</button>',
        DELAY: '<button class="btn btn-cuiban btn-xs" type="button" onclick="delayTask(\''+str.row.id+'\', \''+str.type+'\')">'+
        '<i class="fa fa-calendar-plus-o"></i>&nbsp;&nbsp;<span class="bold">延期</span>'+
        '</button>',
        SUSPEND:'<button class="btn btn-zanting btn-xs" type="button" onclick="pauseTask(\''+str.row.id+'\', \''+str.type+'\')">'+
        '<i class="fa fa-pause"></i>&nbsp;&nbsp;<span class="bold">暂停</span>'+
        '</button>',
        CANCEL: '<button class="btn btn-jujue btn-xs" type="button" onclick="cancelTask(\''+str.row.id+'\', \''+str.type+'\')">'+
        '<i class="fa fa-close"></i>&nbsp;&nbsp;<span class="bold">取消</span>'+
        '</button>',
        CONTINUE: '<button class="btn btn-jieshou btn-xs" type="button" onclick="continueTask(\''+str.row.id+'\', \''+str.type+'\')">'+
        '<i class="fa fa-play-circle"></i>&nbsp;&nbsp;<span class="bold">继续</span>'+
        '</button>',
        DONE: '<button class="btn btn-banjie btn-xs" type="button" onclick="doConclude(\''+str.row.id+'\', \''+str.type+'\')">'+
        '<i class="fa fa-check"></i>&nbsp;&nbsp;<span class="bold">办结</span>'+
        '</button>',
        FINISH: '<button class="btn btn-banjie btn-xs" type="button" onclick="doDone(\''+str.row.id+'\')">'+
        '<i class="fa fa-check"></i>&nbsp;&nbsp;<span class="bold">完成</span>'+
        '</button>',
        RECALL: '<button class="btn btn-recall btn-xs" type="button" onclick="recallTask(\''+str.row.id+'\', \''+str.type+'\',\''+ rp +'\')">'+
        '<i class="fa fa-reply"></i>&nbsp;&nbsp;<span class="bold">撤回</span>'+
        '</button>',
        TURNBACK: '<button class="btn btn-jujue btn-xs" type="button" onclick="turnBackTask(\''+str.row.id+'\', \''+rp+'\')">'+
        '<i class="fa fa-reply"></i>&nbsp;&nbsp;<span class="bold">回退</span>'+
        '</button>'
    };
    var btList;
    if(str.type == 'nodeList'){  //节点信息页面标识
        switch(str.state-0){
            case 3:
                if(str.isTaskOwner&& (str.taskStatus == 3||str.taskStatus == 6)){ //主任务负责人 && 主任务状态是3进行中 && 超级权限             	
                	btList=[b.VIEW, b.MODIFY, b.FINISH, b.TRANSFER]; break;
                }else if(window.isSuperPower== 'true'&& (str.taskStatus == 3 ||str.taskStatus == 6 )){
                	btList=[b.VIEW, b.TRANSFER]; break;
                }if(str.row.istaskItemOwner && (str.taskStatus == 3||str.taskStatus == 6)){ //节点负责人 && 主任务状态是3进行中
                    btList=[b.VIEW, b.FINISH, b.TRANSFER]; break;
                }else{
                    btList=[b.VIEW]; break;
                }
            case 4: btList=[b.VIEW]; break;
            case 5: btList=[b.VIEW]; break;
            case 6:
            	if(str.isTaskOwner&& (str.taskStatus == 3||str.taskStatus == 6)){ //主任务负责人 && 主任务状态是3进行中 && 超级权限             	
                	btList=[b.VIEW, b.MODIFY, b.FINISH, b.TRANSFER]; break;
                }else if(window.isSuperPower== 'true'&& (str.taskStatus == 3 ||str.taskStatus == 6 )){
                	btList=[b.VIEW, b.TRANSFER]; break;
                }if(str.row.istaskItemOwner && (str.taskStatus == 3||str.taskStatus == 6)){ //节点负责人 && 主任务状态是3进行中
                    btList=[b.VIEW, b.FINISH, b.TRANSFER]; break;
                }else{
                    btList=[b.VIEW]; break;
                }
            case 7: btList=[b.VIEW]; break;
            case 8: btList=[b.VIEW]; break;
            case 9: btList=[b.VIEW]; break;
            case 10: btList=[b.VIEW]; break;
        }
    } else if(str.type == 'childTaskList'){  //子任务
        switch(str.state-0){
            case 0: btList=[b.VIEW]; break;
            case 1: btList=[b.VIEW]; break;
            case 2: btList=[b.VIEW]; break;
            case 3: btList=[b.VIEW]; break;
            case 4: btList=[b.VIEW]; break;
            case 5: btList=[b.VIEW]; break;
            case 6: btList=[b.VIEW]; break;
            case 7: btList=[b.VIEW]; break;
            case 8: btList=[b.VIEW]; break;
            case 9: btList=[b.VIEW]; break;
            case 10: btList=[b.VIEW]; break;
        }
    } else if(str.type == '0' || str.type == '1'){
    	//执行状态（0：草稿 1：待接收 2：已拒绝 3：进行中 4：暂停 5：取消 6：逾期未完成 7：已完成未办结 8：办结 ）
        switch(str.state-0){
            case 0: btList=str.row.taskCreateUser.id==window.USRID?[b.DELETE, b.MODIFY, b.SUBMIT]:[]; break;
            case 1: 
            	btList=[];
            	if(str.row.handOverUserId==window.USRID){
            		btList.push(b.RECALL);
            	}else{
            		if(str.row.handOverUserId=="" && str.row.taskCreateUser.id==window.USRID){
            			btList.push(b.RECALL);
            		}
            	}
            	if(str.row.owner.id==window.USRID){
            		btList.push(b.ACCEPT);
            		btList.push(b.REFUSE);
            	}
            	break;
            case 2: 
            	if(!str.row.handOverUserId){
            		btList=str.row.taskCreateUser.id==window.USRID?[b.MODIFY]:[];
            	}else{
            		btList=str.row.handOverUserId==window.USRID?[b.MODIFY]:[];
            	}
            	break;
            case 3:
            	//四个审批按钮提交后，按钮不要再显示，除非审批不通过，按钮才可显示   0:显示 1：不显示
            	if(str.row.owner.id==window.USRID){
            		btList=[b.MODIFY, b.DECOMPOSE, b.TRANSFER, b.PUSH];
            		if(str.row.cancalShow=='0') {
            			btList.push(b.CANCEL);
            		}
            		if(str.row.pauseShow=='0') {
            			btList.push(b.SUSPEND);
            		}
            		if(str.row.extensionShow=='0') {
            			btList.push(b.DELAY);
            		}
            		
            	}else if(window.isSuperPower== 'true'){
            		btList=[b.TRANSFER];
            	}else{
            		btList=[];
            	}
            	 break;
            case 4: btList=str.row.owner.id==window.USRID?[b.CONTINUE]:[]; break;
            case 5: btList=[]; break;
            case 6: 
            	//四个审批按钮提交后，按钮不要再显示，除非审批不通过，按钮才可显示   0:显示 1：不显示
            	if(str.row.owner.id==window.USRID){
            		btList=[b.MODIFY, b.DECOMPOSE, b.TRANSFER, b.PUSH];
            		if(str.row.cancalShow=='0') {
            			btList.push(b.CANCEL);
            		}
            		if(str.row.pauseShow=='0') {
            			btList.push(b.SUSPEND);
            		}
            		if(str.row.extensionShow=='0') {
            			btList.push(b.DELAY);
            		}
            		
            	}else if(window.isSuperPower== 'true'){
            		btList=[b.TRANSFER];
            	}else{
            		btList=[];
            	}

            	 break;
            	
            case 7: 
            	btList=(str.row.owner.id==window.USRID && str.row.banjieShow=='0')?[b.DONE,b.TURNBACK]:[]; break;
            case 8: btList=[];break;
            case 9: btList=(str.row.owner.id==window.USRID && str.row.banjieShow=='0')?[b.DONE,b.TURNBACK]:[]; break;
            case 10: btList=[];break;
        }
    } else{
        //任务管理列表
        //执行状态（0：草稿 1：待接收 2：已拒绝 3：进行中 4：暂停 5：取消 6：逾期未完成 7：按时完成 8：按时办结 9：逾期完成 10：逾期办结 ）
        switch(str.state-0){
            case 0: btList=str.row.taskCreateUser.id==window.USRID?[b.VIEW, b.MODIFY, b.SUBMIT, b.DELETE]:[]; break;
            case 1: 
            	btList=[];
            	btList.push(b.VIEW);
            	if(str.row.handOverUserId==window.USRID){
            		btList.push(b.RECALL);
            	}else{
            		if(str.row.handOverUserId==null && str.row.taskCreateUser.id==window.USRID){
            			btList.push(b.RECALL);
            		}
            		
            	}
            	if(str.row.owner.id==window.USRID){
            		btList.push(b.ACCEPT);
            		btList.push(b.REFUSE);
            	}
            	break;
            case 2: 
            	if(!str.row.handOverUserId){
            		btList=str.row.taskCreateUser.id==window.USRID?[b.VIEW,b.MODIFY]:[b.VIEW]; 
            	}else{
            		btList=str.row.handOverUserId==window.USRID?[b.VIEW,b.MODIFY]:[b.VIEW]; 
            	}
            	break;
            case 3: 
            	//四个审批按钮提交后，按钮不要再显示，除非审批不通过，按钮才可显示   0:显示 1：不显示
            	if(str.row.owner.id==window.USRID){
            		btList=[b.VIEW, b.MODIFY, b.DECOMPOSE, b.TRANSFER, b.PUSH];
            		if(str.row.cancalShow=='0') {
            			btList.push(b.CANCEL);
            		}
            		if(str.row.pauseShow=='0') {
            			btList.push(b.SUSPEND);
            		}
            		if(str.row.extensionShow=='0') {
            			btList.push(b.DELAY);
            		}
            		
            	}else if(window.isSuperPower== 'true'){
            		btList=[b.VIEW, b.TRANSFER];
            	}else{
            		btList=[b.VIEW];
            	}
            	 break;
            case 4: btList=str.row.owner.id==window.USRID?[b.VIEW, b.CONTINUE]:[b.VIEW]; break;
            case 5: btList=[b.VIEW]; break;
            case 6: 
            	if(str.row.owner.id==window.USRID){
            		btList=[b.VIEW, b.MODIFY, b.DECOMPOSE, b.TRANSFER, b.PUSH];
            		if(str.row.cancalShow=='0') {
            			btList.push(b.CANCEL);
            		}
            		if(str.row.pauseShow=='0') {
            			btList.push(b.SUSPEND);
            		}
            		if(str.row.extensionShow=='0') {
            			btList.push(b.DELAY);
            		}
            	}else if(window.isSuperPower== 'true'){
            		btList=[b.VIEW, b.TRANSFER];
            	}else{
            		btList=[b.VIEW];
            	}
            	 break;
            case 7: btList=(str.row.owner.id==window.USRID && str.row.banjieShow=='0')?[b.VIEW, b.DONE,b.TURNBACK]:[b.VIEW]; break;
            case 8: btList=[b.VIEW];break;
            case 9: btList=(str.row.owner.id==window.USRID && str.row.banjieShow=='0')?[b.VIEW, b.DONE,b.TURNBACK]:[b.VIEW]; break;
            case 10: btList=[b.VIEW];break;
        }
    }
    return btList.join(' ');
}
//校验必填项
function checkRequired(divId,fn){
    var isEmpty = false;
    $('#'+divId+' [required]').each(function (i,info) {
        if($(info).val() == ''){
            $(info).css({
                'border-color': '#ee7000'
            })
            isEmpty = true;
        }
    })
    if(isEmpty){
        layer.alert(tipTexts.required);
    }else{
        !!fn && fn();
    }
}
//校验进度值（正整数）
function checkProgressVal(obj){
    if(obj.value.length==1){
        obj.value=obj.value.replace(/[^1-9]/g,'1')
    }else{
        obj.value=obj.value.replace(/\D/g,'')
    }
    if(obj.value > 100){
        obj.value='';
        layer.tips(tipTexts.progressNum, obj);
    }else if(obj.value == '' || obj.value == 0){
        obj.value='';
        layer.tips(tipTexts.progressNum, obj);
    }
}

function getQueryString(strParame) {
    var args = new Object();
    var query = location.search;
    var newQuery = query.substring(query.indexOf('?') + 1);
    var pairs = newQuery.split("&"); // Break at ampersand
    for(var i = 0; i < pairs.length; i++) {
        var pos = pairs[i].indexOf('=');
        if(pos == -1)
            continue;
        var argname = pairs[i].substring(0, pos);
        var value = pairs[i].substring(pos + 1);
        value = decodeURIComponent(value);
        args[argname] = value;
    }
    return args[strParame] || "";
}

function setCookieFromUrl(){
    $.cookie('fromUrl', window.location.href, { path: '/',expires: 1 });
}



function JQAPIdownload(url,fileName){
    window.open(url);
}


// 任务详情 节点详情 人员信息中 点击 直接 @ 消息
function saveAtMsg(id,name) {

    window.guLock.creater = id;
    window.guLock.tip = '@人员';

    var tpl = _.template($('#template-reply').html());

    // var pickUser = '<div class="atMansBox"><span class="atBtn">@</span><textarea data-name="atMans" class="form-control" pickUser readonly  ></textarea><input type="hidden" name="atMans"><input type="hidden" id="chooseUserFilter"></div>';
    var pickUser =
        '<div class="atMansBox"><span class="atBtn">@</span><div class="gu-container" data-lockcheck="true">'
            + '<div class="gu-wrapper">'
                + '<div class="gu-box" data-max="8"></div>'
                + '<input type="text" class="gu-input valid" maxlength="16">'
                + '<input type="hidden" value="'+ id +'" name="atMans" >'
                + '<input type="hidden" value="'+ name +'" class="gu-return" >'
            + '</div>'
        + '</div></div>';


    var um;
    var temp = new Date().getTime();
    var model = parent.layer.open({
        type: 1,
        title:'@提醒',
        zIndex:10,
        area: ['800px', '320px'],
        content: tpl({
            temp: temp
        }) + pickUser,
        success: function(layero, index){

            window.guInit();

            $('.layui-layer-btn').css('textAlign','center');
            um = UE.getEditor('reply_'+temp,{
                toolbar: toolbar
            });
        },
        btn:['确定','取消'],
        btn1: function(){

            var ids = $('[name=atMans]').val()?$('[name=atMans]').val().split(','):[];

            var content = um.getContent();
            if(!content){
                parent.layer.alert('请输入评论内容');
                return false;
            }else{
                var contentTxt = um.getContentTxt();
                saveAtMsgAjax(content,ids,model);
            }

        },
        btn2: function(){
            parent.layer.close(model);
        }

    });
}

function saveAtMsgAjax(content,ids,model){
    var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
    var taskId = '';
    var taskItemId = '';
    if(window.saveAtMsgId.type) {
        taskId = window.saveAtMsgId.id;
    } else {
        taskItemId = window.saveAtMsgId.id;
    }
    $.ajax({
        url : window.saveAtMsgId.path + '/rest/wdt/task/saveAtMessage',
        type : 'post',
        data : JSON.stringify({
            taskId: taskId,        // 任务id
            taskItemId: taskItemId,        // 任务节点id
            content: content,
            atPerson: ids
        }),
        dataType: 'json',
        contentType:'application/json;charset=UTF-8',
        success : function(res) {
            parent.layer.close(loadModel);
            if(res.result){
                parent.layer.alert(tipTexts.success);
                parent.layer.close(model);
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
}