// JavaScript Document
var get = function(key){return document.getElementById(key);};
Date.prototype.Format = function(fmt){
var o = {   
	"M+" : this.getMonth()+1,                 //月份   
	"d+" : this.getDate(),                    //日   
	"h+" : this.getHours(),                   //小时   
	"m+" : this.getMinutes(),                 //分   
	"s+" : this.getSeconds(),                 //秒   
	"q+" : Math.floor((this.getMonth()+3)/3), //季度   
	"S"  : this.getMilliseconds()             //毫秒   
};   
	if(/(y+)/.test(fmt))   
		fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));   
	for(var k in o)   
		if(new RegExp("("+ k +")").test(fmt))   
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
	return fmt;   
}

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {
	if (!RegExp.prototype.isPrototypeOf(reallyDo)){
		return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith); 
	} else { 
		return this.replace(reallyDo, replaceWith); 
	}
};

function froms(){
	for ( var i = 0; i < document.forms.length;i++) {
		this[i] = new function (){
			var from = document.forms[i];
			for ( var j = 0; j < from.elements.length; j++) {
				if(from.elements[j].name){
					this[from.elements[j].name] = from.elements[j].value;
				}
			};
		};
	};
}

function QueryString(){
	var name,value,i;
	var str=location.href;
	var num=str.indexOf("?");
	str=str.substr(num+1);
	var arrtmp=str.split("&");
	for(i=0;i < arrtmp.length;i++){
		num=arrtmp[i].indexOf("=");
		if(num>0){
			name=arrtmp[i].substring(0,num);
			value=arrtmp[i].substr(num+1);
			this[name]=value.replaceAll('#','');
		}
	}
}

/** 添加/删除 cookie */
function setCookie(name,value,exdays,path){
	var exdate=new Date();
	exdays=exdays || 365;
	exdate.setDate(exdate.getDate()+exdays);
	if(!value){
		value='';
		exdays=-3;
	}
	document.cookie=name+'='+encodeURIComponent(value)+
			(typeof exdays === 'undefined' ? '' : ';expires='+exdate.toGMTString())+
			';path='+(typeof path === 'undefined' ? '/' : path);
}

/** 获取 cookie */
function getCookie(name){
	if(document.cookie.length){
		var c_start=document.cookie.indexOf(name+'=');
		if(c_start !== -1){
			c_start=c_start+name.length+1;
			var c_end=document.cookie.indexOf(';',c_start);
			if(c_end === -1) c_end=document.cookie.length;
			var op=document.cookie.substring(c_start,c_end).replace(/[+]/g,'%20');
			return decodeURIComponent(op);
		}
	}
	return '';
}