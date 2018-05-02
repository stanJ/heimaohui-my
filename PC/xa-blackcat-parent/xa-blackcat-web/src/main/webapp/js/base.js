//获取项目根路径
function getRootPath(){ 
     var pathName = window.location.pathname.substring(1); 
     var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/')); 
     return window.location.protocol + '//' + window.location.host + '/'+ webName + '/'; 
}

var root=getRootPath();		//全局根路径

//获取浏览器参数
function getUrlParam(name){
	var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
	var r = window.location.search.substr(1).match(reg);  //匹配目标参数
	if (r!=null) return unescape(r[2]); return null; //返回参数值
}

function formatDate(date,fmt)   
{ 
  var o = {   
    "M+" : date.getMonth()+1,                 //月份   
    "d+" : date.getDate(),                    //日   
    "h+" : date.getHours(),                   //小时   
    "m+" : date.getMinutes(),                 //分   
    "s+" : date.getSeconds(),                 //秒   
    "q+" : Math.floor((date.getMonth()+3)/3), //季度   
    "S"  : date.getMilliseconds()             //毫秒   
  };   
  if(/(y+)/.test(fmt))   
    fmt=fmt.replace(RegExp.$1, (date.getFullYear()+"").substr(4 - RegExp.$1.length));   
  for(var k in o)   
    if(new RegExp("("+ k +")").test(fmt))   
  fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));   
  return fmt;   
} ;

$.views.converters("convertDateStr", function(date) {
	if(date==undefined || date==null){
		return "";
	}
	return formatDate((new Date(date)),"yyyy-MM-dd hh:mm:ss");
});

$.views.converters("pageSizeSelected",function(first,last){
	if(first==null) return "";
	return first==last? "selected=selected":"";
});

//以指定字符串结尾
String.prototype.endWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
     return false;
  if(this.substring(this.length-s.length)==s)
     return true;
  else
     return false;
  return true;
 };
 //以指定字符串打头
String.prototype.startWith=function(s){
  if(s==null||s==""||this.length==0||s.length>this.length)
   return false;
  if(this.substr(0,s.length)==s)
     return true;
  else
     return false;
  return true;
 };
 
//去掉字符串两边的空格并去掉双引号，如:"    dd" aa  ",格式化后"dd aa";
 function trimAndDelQuotation(str){
 	if(str==""){
 		return str;
 	}
 	var _temp=str.replace(/\"*/g,"");
 	_temp = $.trim(_temp);
 	return _temp;
 }
 $.views.converters("getSubStr", function(str, len) {
 	if(str==undefined || str==null){
 		return "";
 	}
 	if(str.length>len){
 		 return str.substr(0,len)+"...";
 	}
 	return str;
 });
 $.views.converters("getSubStrSplit", function(str, len) {
	 	if(str==undefined || str==null){
	 		return "";
	 	}
	 	if(str.length>=len){
	 		var substr = str.substr(0,len);
	 		if(substr == '1900-01-01 00:00'){
	 			return '';
	 		}	
	 		return substr;
	 	}
	 	return str;
	 });
 $.views.converters("getNoHTMLSubStr", function(str, len) {
	 str = removeHTMLTag(str);
 	if(str==undefined || str==null){
 		return "";
 	}
 	if(str.length>len){
 		 return str.substr(0,len)+"...";
 	}
 	return str;
 });
 function removeHTMLTag(str) {
     str = str.replace(/<\/?[^>]*>/g,''); //去除HTML tag
     str = str.replace(/[ | ]*\n/g,'\n'); //去除行尾空白
     //str = str.replace(/\n[\s| | ]*\r/g,'\n'); //去除多余空行
     str=str.replace(/&nbsp;/ig,'');//去掉&nbsp;
     return str;
}
 $.views.converters("getConcatStr", function(str1, str2) {
 	if(str2==undefined || str2==null){
 		return "";
 	}
 	return str1+str2;
 });
 
$(function(){
	$('input[type="text"]').addClass('inp_select');
	$('input[type="password"]').addClass('inp_select');
	$('select').addClass('inp_select');
});

String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {  
    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {  
        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);  
    } else {  
        return this.replace(reallyDo, replaceWith);  
    }  
} 

Array.prototype.remove=function(dx)
{
    if(isNaN(dx)||dx>this.length){return false;}
    for(var i=0,n=0;i<this.length;i++)
    {
        if(this[i]!=this[dx])
        {
            this[n++]=this[i];
        }
    }
    this.length-=1;
} 

function onlyNumber(obj){  
    //得到第一个字符是否为负号  
    var t = obj.value.charAt(0);    
    //先把非数字的都替换掉，除了数字和.   
    obj.value = obj.value.replace(/[^\d\.]/g,'');     
     //必须保证第一个为数字而不是.     
     obj.value = obj.value.replace(/^\./g,'');     
     //保证只有出现一个.而没有多个.     
     obj.value = obj.value.replace(/\.{2,}/g,'.');     
     //保证.只出现一次，而不能出现两次以上     
     obj.value = obj.value.replace('.','$#$').replace(/\./g,'').replace('$#$','.');  
     //如果第一位是负号，则允许添加  
     if(t == '-'){  
       obj.value = '-'+obj.value;  
     }  
} 