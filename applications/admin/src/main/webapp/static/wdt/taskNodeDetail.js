function getPara(strParame) {
	var args = {};
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

var prevLoaded = false, prevStart = 0;

var pin = getPara('pinId');
var msgType = getPara('messageType');

function toPin(){

	var timer = setInterval(function() {
		var allLoad = true;
		$('img').each(function(){
			if(!this.complete) {
				allLoad = false;
			}
		});
		if(allLoad) {
			clearInterval(timer);
			if( pin != 0) {
				var el = document.getElementById('pin_'+ pin);
				if(el) {
					var top = el.offsetTop - (( window.innerHeight/2 + 50)||window.screen.height/2-200);
					console.log(el.offsetTop);
					$('body,html').animate( { scrollTop: top}, 240 );
					$(el).css('background','#ffffdc');
				} else {
					console.log('pin element is not exist!');
				}
			}
		}
	},800);

};







/**
 * Created by bob.xu on 2017/8/10.
 */
var start = 0,pageSize=5;

$(function () {

	queryList(false, true);

	$('.load-prev').on('click',function(){
		prevStart -= 5;
		prevStart<0 ? prevStart=0 : null;
		queryListPrev();
	});

	$('.load-more').on('click',function(){
		start = parseInt(start) + parseInt(pageSize);
		queryList(true);
	});
});

function queryListPrev(){

	var tpl = _.template($('#template-taskProgressList').html());

	var data = {
		start: prevStart,
		length: 5,
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
				$('#taskProgress_list').prepend(tpl({
					list: res.data.list
				}));
				
				$(function () { $("[data-toggle='tooltip']").tooltip(); });

				res.data.pageNo==1 ? prevLoaded=true : null;
				if(prevLoaded){
					$('.load-prev').hide();
				}else{
					$('.load-prev').show();
				}
			}else{
				if(res.message){
					parent.layer.alert(res.message);
				}else{
					parent.layer.alert(tipTexts.fail);
				}
			}
			$('img[src^="/admin/static/ueditor/dialogs"]').css({'verticalAlign':'bottom','display':'inline-block'});
		},
		error: function(){
			parent.layer.alert(tipTexts.error);
		}
	});
	return false;
}

function queryList(more, isPin){
	var tpl = _.template($('#template-taskProgressList').html());

	if(!more){ start = 0; }

	var data={
		type: 2,
		start: start,
		length: 5,
		taskItemId: taskNodeId
	};

	if(isPin) {
		data['pinId'] = pin;
		data['messageType'] = msgType;
	}  // console.log(data);

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
					$('#taskProgress_list').empty().html(tpl({
						list: res.data.list
					}))
				}

				$(function () { $("[data-toggle='tooltip']").tooltip(); });

				isPin?start = prevStart = (res.data.pageNo-1)*5+1:null;

				res.data.pageNo==1 ? prevLoaded=true : null;
				if(prevLoaded){
					$('.load-prev').hide();
				}else{
					$('.load-prev').show();
				}

				// if(parseInt(data.start)+parseInt(data.length) >= res.data.count){
				if(res.data.pageNo*5 >= res.data.count){
					$('.load-more').hide();
				}else{
					$('.load-more').show();
				}
				
				// 跳转高亮
				isPin?toPin():null;

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
			$('img[src^="/admin/static/ueditor/dialogs"]').css({'verticalAlign':'bottom','display':'inline-block'});
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

	// var pickUser = '<div class="atMansBox"><span class="atBtn">@</span><textarea data-name="atMans" class="form-control" pickUser readonly  ></textarea><input type="hidden" name="atMans"><input type="hidden" id="chooseUserFilter"></div>';
	var pickUser =
		'<div class="atMansBox"><span class="atBtn">@</span><div class="gu-container">'
			+ '<div class="gu-wrapper">'
				+ '<div class="gu-box" data-max="8"></div>'
				+ '<input type="text" class="gu-input valid" maxlength="16">'
				+ '<input type="hidden" value="" name="atMans" >'
				+ '<input type="hidden" value="" class="gu-return" >'
			+ '</div>'
		+ '</div></div>';


	parent.layer.ready(function(){
		var model = parent.layer.open({
			type: 1,
			area: ['800px','320px'],
			zIndex:15,
			title: '节点汇报',
			content: tpl({
				timeTemp: timeTemp,
				id: '',
				schedule: 0
			}) + pickUser,
			success: function () {
				$('.layui-layer-btn').css('textAlign','center');
				um = UE.getEditor('edit_'+timeTemp,{
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

				var ids = $('[name=atMans]').val()?$('[name=atMans]').val().split(','):[];

				addNewProgress(um.getContent(),model,ids);
			},
			btn2: function(){
				parent.layer.close(model)
			}
		});

	});
}
function addNewProgress(content,model,ids){
	checkRequired('progress_detail', function () {
		var data={
			type: 2,
			id: $('#progress_detail input[data-name="id"]').val(),
			schedule: $('#progress_detail input[data-name="schedule"]').val(),
			content: content,
			taskItemId: taskNodeId,
			atPerson: ids
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
					$('#node_gress').css('width',data.schedule+'%');
					$('#node_gress').empty().html(data.schedule+'%');
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
function updateProgress(id,schedule,btn){

	var atPerson = $(btn).attr('data-atPerson');

	var atPersonID = '',atPersonName = '';
	if(atPerson) {
		var arr =atPerson.split(';');
		atPersonID = arr[0];
		atPersonName = arr[1];
	}


	var content = $('#taskDesc_'+id).html();
	var tpl = _.template($('#template-nodeDetail').html());
	var timeTemp = new Date().getTime();
	var um;

	// var pickUser = '<div class="atMansBox"><span class="atBtn">@</span><textarea data-name="atMans" class="form-control" pickUser readonly >'+ atPersonName +'</textarea><input type="hidden" name="atMans" value="'+ atPersonID +'"><input type="hidden" id="chooseUserFilter"></div>';
	var pickUser =
		'<div class="atMansBox"><span class="atBtn">@</span><div class="gu-container">'
			+ '<div class="gu-wrapper">'
				+ '<div class="gu-box" data-max="8"></div>'
				+ '<input type="text" class="gu-input valid" maxlength="16">'
				+ '<input type="hidden" value="'+ atPersonID +'" name="atMans" >'
				+ '<input type="hidden" value="'+ atPersonName +'" class="gu-return" >'
			+ '</div>'
		+ '</div></div>';



	parent.layer.ready(function(){
		var model = parent.layer.open({
			type: 1,
			area: ['800px','320px'],
			zIndex:15,
			title: '节点汇报',
			content: tpl({
				timeTemp: timeTemp,
				id: id,
				schedule: schedule
			}) + pickUser,
			success: function () {

				window.guInit();  // 选人控件手动回填数据

				$('.layui-layer-btn').css('textAlign','center');
				um = UE.getEditor('edit_'+timeTemp,{
					toolbar: toolbar
				});
				um.ready(function() {//编辑器初始化完成再赋值
					um.setContent(content);  //赋值给UEditor
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

				var ids = $('[name=atMans]').val()?$('[name=atMans]').val().split(','):[];

				addNewProgress(um.getContent(),model,ids);
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
	var executionStatus = $('#executionStatus').val(); //节点状态
	var taskStatus = $('#taskStatus').val();  //主任务状态
	var isTaskOwner = eval($('#isTaskOwner').val());
	var isTaskItemOwner = eval($('#isTaskItemOwner').val());
	if(taskStatus == 3 || taskStatus == 6){ //主任务状态是3进行，6逾期未完成中才能操作节点按钮
		//校验完成按钮显示隐藏
		if(executionStatus == 3 && isTaskOwner){ //主任务负责人
			$('#finish_btn').show();
		}else if(executionStatus == 3 && isTaskItemOwner){ //节点负责人
			$('#finish_btn').show();
		}else if(executionStatus == 6 && isTaskOwner){ //主任务负责人
			$('#finish_btn').show();
		}else if(executionStatus == 6 && isTaskItemOwner){ //节点负责人
			$('#finish_btn').show();
		}else {
			$('#finish_btn').hide();
		}
		//校验进度汇报新增、编辑按钮显示隐藏
		$('#addNewProgress').hide();
		$('.progress-edit').hide();
		if(executionStatus == 3){
			if(isTaskOwner){//进行中 & 主任务负责人
				//$('#addNewProgress').show();
				$('.progress-edit').show();
			}
			if( isTaskItemOwner){ //进行中 & 节点负责人
				$('#addNewProgress').show();
				$('.progress-edit').show();
			}
		}
		if(executionStatus == 6){ 
			if(isTaskOwner){//逾期未完成 & 主任务负责人
				//$('#addNewProgress').show();
				$('.progress-edit').show();
			}
			if(isTaskItemOwner){//逾期未完成 & 节点负责人
				 $('#addNewProgress').show();
				 $('.progress-edit').show();
			}
		}  
	   
	}else{
		$('#finish_btn').hide();
		$('#addNewProgress').hide();
		$('.progress-edit').hide();
	}
}
function goback(){
	window.history.go(-1);
}


function replyComment(comentsId,commentParentId,byUserId,replyName,revertAt,obj){

    if(window.canSaveAtMsg != 'true') {
        return false;
    }
    
	var replyName = replyName?replyName:"";

	var at = {
		id: '',
		name: ''
	};

	// 评论中 带 at
	if(commentParentId=='at') {
		at.id = byUserId;
		at.name = replyName;
		replyName = '';  // alert title
		commentParentId = '';  // ajax
	}

	// 评论回复中 带 at
	if(revertAt=='at') {
		var param = $(obj).parents('.staff_revert').data('revert').split(',');
		at.id = byUserId;
		at.name = replyName;
		byUserId = param[0];
		replyName = param[1];
	}


	var tpl = _.template($('#template-reply').html());

	// var pickUser = '<div class="atMansBox"><span class="atBtn">@</span><textarea data-name="atMans" class="form-control" pickUser readonly  ></textarea><input type="hidden" name="atMans"><input type="hidden" id="chooseUserFilter"></div>';
	var pickUser =
		'<div class="atMansBox"><span class="atBtn">@</span><div class="gu-container">'
			+ '<div class="gu-wrapper">'
				+ '<div class="gu-box" data-max="8"></div>'
				+ '<input type="text" class="gu-input valid" maxlength="16">'
				+ '<input type="hidden" value="'+ at.id +'" name="atMans" >'
				+ '<input type="hidden" value="'+ at.name +'" class="gu-return" >'
			+ '</div>'
		+ '</div></div>';



	var um;
	var temp = new Date().getTime();
	var model = parent.layer.open({
		type: 1,
		title:'回复:'+replyName,
		area: ['800px','320px'],
		zIndex:15,
		content: tpl({
			temp: temp
		}) + pickUser,
		success: function(layero, index){

			window.guInit();

			$("#taskId").val(taskId);
			$('.layui-layer-btn').css('textAlign','center');
			um = UE.getEditor('reply_'+temp,{
				toolbar: toolbar
			});
		},
		btn:['确定','取消'],
		btn1: function(){
			var content = um.getContent();

			var ids = $('[name=atMans]').val()?$('[name=atMans]').val().split(','):[];

			if(!content){
				parent.layer.alert('请输入评论内容');
				return false;
			}else{
				replyCommentAjax(comentsId,commentParentId,byUserId,content,model,ids);
			}

		},
		btn2: function(){
			parent.layer.close(model)
		}

	});

}
function replyCommentAjax(comentsId,commentParentId,byUserId,content,model,ids){
	var loadModel = parent.layer.load(0, {shade: [0.1,'#fff']});
	$.ajax({
		url : urlhead+'/rest/wdt/task/saveProgressReponse',
		type : 'post',
		data : JSON.stringify({
			taskItemId: taskNodeId,
			comentsId: comentsId,
			content: content,
			parentId: commentParentId?commentParentId:"",
			byUserId: byUserId?byUserId:"",
			atPerson: ids
		}),
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
}