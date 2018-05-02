function ajax(params){
	
	//mis为了解决匿名登录的问题，现在取消临时token161123jc
	/*
	if(!sessionStorage.getItem("tt")){
		$.ajax({
			url:baseURL+"/m/userExtend/handshake",
			type:"POST",
			data:{
				agent:"web"
			},
			async:false,
			success:function(data){
				if(data.code==1 && data.object && data.object.token){
					sessionStorage.setItem("tt",data.object.token);
				}else{
					//107.匿名令牌申请被拒绝
					var c="";
					if(data.object=="107") c="(code:107)";
					alert("服务器访问失败"+c);
				}
			},
			error:function (data, status, e){
				alert("服务器访问失败");
			}
		});
	}
	*/
	
	var noToken = {
		'/j_spring_security_check':"",
		'/dict/constant':"",
		'/m/cmsuser/validataUser':"",
		'/m/userExtend/verifyVeriPic':"",
	};
	if(noToken[params.url]==undefined){
		if(!sessionStorage.getItem("t")){
			sessionStorage.setItem("message","令牌失效，请登录");
			utilObj.navigate('login');
		}
	}
	params.url = utilObj.getApiHost().baseUrl + params.url;
	var ajaxparams= $.extend({},params);
	if(!ajaxparams.data) ajaxparams.data={};
	/*
	//token默认赋值为临时token
	if(sessionStorage.getItem("tt")){
		ajaxparams.data.token=sessionStorage.getItem("tt");
	}
	if(sessionStorage.getItem("t")){
		ajaxparams.data.token=sessionStorage.getItem("t");
	}
	*/
	
	
	if(noToken[params.url]==undefined){
		ajaxparams.data.token=sessionStorage.getItem("t");
	}
	ajaxparams.success=function(data,status,xhr){
		//data.object 101.没有令牌 102.令牌失效 103.令牌超时 104.重复提交 105.匿名方式被拒绝  999.scope
//		if(dealTokenByData(data)){
			if(data && data.code==0 && data.message){
//				if(data.message=="当天浏览次数已超出"){
////					接口里已alert该信息
////					utilObj.alert(data.message);
//				}else 
				if(data.message.indexOf('已失效')!=-1 || data.message.indexOf('无效')!=-1){
					sessionStorage.setItem("message","登录过期,请重新登录");
					utilObj.navigate("login");
				}else if(data.object && data.object=="22001"){
					utilObj.alert("输入的文字过长");
				}else if(data.object && data.object.success==false){
//					utilObj.alert("输入的文字过长");
				}else{
					utilObj.alert(data.message);
				}
			}
			if(params.success instanceof Function) params.success(data,status,xhr);
//		}
	}
	ajaxparams.error=function(xhr){
		if(params.error instanceof Function) params.error(xhr);
	}
	return $.ajax(ajaxparams);
}
function ajaxfileupload(params){
	params.action = utilObj.getApiHost().baseUrl + params.action;
//	params.secureuri = utilObj.getApiHost().baseUrl + params.secureuri;
	var el = params.el;
	delete params.el;
	return $(el).ajaxfileupload(params);
}
//function relocate(){
//	var curHref = top.location.href;
//	var curHtml = curHref.slice(curHref.lastIndexOf('/')+1);
//	if(envValue==2){
//		if(curHtml && curHtml!='clinicentrance.html'){
//			top.location.href="../clinicentrance.html";
//		}	
//	}else{
//		if(curHtml && curHtml!='clinicentrance.html'){
//			top.location.href="../web/clinicentrance.html";
//		}
//	} 
//}
//function dealTokenByData(data){
//	if(data.code!=undefined && data.code==0 && data.object){
//		if(data.object==101 || data.object==102 || data.object==103){
//			sessionStorage.clear();
//			sessionStorage.setItem("message","系统超时，请重新登陆");
//			// sessionStorage.setItem("message","系统超时，请重新登陆(code:"+ data.object +")");
//			relocate();
//			return false;
//		}
//		else if(data.object==104){
//			alert("请求过于频繁,请稍候尝试");
//			if(params.error instanceof Function) params.error();
//			return false;
//		}
//		else if(data.object==105 || data.object==106 || data.object==999){
//			alert("请求服务器失败(code:"+ data.object +")");
//			return false;
//		}
//	}
//	return true;
//}
var utilObj = {
	envValue:0,
	userInfo:sessionStorage.userinfo?JSON.parse(sessionStorage.userinfo):{},
	bindEvent:function(){
		var _this = this;
		$("input[type=file]").on('change',function(){
			var previewFile = $(this).data("previewfile");
			var filename = this.files[0].name;
			if(previewFile){
				$("."+previewFile+"").val(filename);
			}
		})
//		$(document).on('click','.checkbox-pretty',function(e){
//			e.stopPropagation();
//		})
		$(document).on('click','.btn-download',function(){
			var filepath = $(this).siblings('.file-path').val();
			if(filepath){
				filepath = _this.fillPicPath(filepath);
				var filename = $(this).siblings('.file-name').val();
				if(!filename){
					filename = 'download'
				}
				_this.downloadAll(filepath,filename);
			}else{
				var picPath = $(this).data('href');
				if(picPath){
					var filename = utilObj.getFileName(picPath);
					picPath = _this.fillPicPath(picPath);
					
					_this.downloadAll(picPath,filename);
				}
				
			}
		})
		$(".table-wrapper").on('click','.customer-name',function(){
			var id = $(this).data('id');
			utilObj.navigate('customer_detail',{
				check: id
			})
		})
		$(".table-wrapper").on('click','.expert-name',function(){
			var id = $(this).data('id');
			utilObj.navigate('resource_detail',{
				check: id
			})
		})
	},
	getTablePage:function(obj){
		var _this = this;
		var tb = obj.tb;
		var page = tb.paginationObj.currentPage;
		if(obj.el){
			var curLength = _this.getTableLength(obj.el);
			var deleteLength = 1;
			if(obj.tid){
				if(utilObj.isAry(obj.tid)){
					deleteLength = obj.tid.length;
				}
			}
			if(curLength-deleteLength==0){
				if(page>1){
					page = page-1;
				}
			}
		}
		return page;
//		if(obj.func){
//			obj.func(page);
//		}
		
	},
	exportBrowse:function(jsonFilter){
		var href = utilObj.getApiHost().baseUrl+"/m/browse/exportBrowseEQStatusPage";
		href += '?';
		var obj = {
			nextPage:0,
			pageSize:pageSetting.Pagination_MaxPage,
			status:1,
			sortData:"[{property:'modifyTime',direction:'DESC'}]",		
			jsonFilter:jsonFilter,
			token:sessionStorage.t,
		}
		var urlEnd = $.param(obj);
		href +=  urlEnd;
		window.open(href);
	},
	downloadAll:function(url,name){
		var href = utilObj.getApiHost().baseUrl+"/m/userExtend/download";
		href += '?';
		var obj = {
			urlString:url,
			filename:name,
			token:sessionStorage.t,
		}
		var urlEnd = $.param(obj);
		href +=  urlEnd;
		window.open(href);
//		ajax({
//			url:"/m/userExtend/download",
//			type: "GET",
//			data:{
//				urlString:url,
//				filename:name,
//			},
//			success: function(data){
//				if(data.code==0){
//					//utilObj.alert({body:data.message});
//				}else{
//					data;
//				}
//			}
//		})
	},
	downloadFile:function(src){
		$("body").append("<a href='"+src+"' download=''></a>");
	    $("a").last()[0].click();
	},
	reSizeIndex:function(){
		var h =document.documentElement.clientHeight;
		var h1 = 0;
		var h2 = 0;
		if(h>=800){
			h1 = (h-296-20)/2;
			h2 = h-276-60-20;
		}else{
			h1 = 264;
			h2 = 488;
		}
		$(".sy-info-wrapper .bszk-block").css('height',h2+'px');
		$(".sy-show-top").css('height',h1+'px');
		$(".sy-show-bottom").css('height',h1+'px');
		
	},
	getUsedModels:function(str){
		var _this = this;
		if(!str || str=='null'){
			return;
		}
		var obj = JSON.parse(str);
		var ary = obj.params;
		var count = obj.use_count;
		var newAry = [];
		$.each(ary,function(){
			var str1 = this.user_params;
			var id = _this.getStrParam(str1,'modelId');
			newAry.push(id);
		})
		return {
			ary:newAry,
			count:count,
		}
	},
	redrawByIsUsed:function(container,tb,response){
		var trs = $(container).find("tbody tr");
		var used = utilObj.getUsedModels(response);
		if(!used){
			return;
		}
		var ary1 = used.ary;
		var count = used.count;
		if(ary1.length<count){
			return;
		}
		trs.each(function(i){
			var rowData = tb.getSelectedRow(this);
			var tid = rowData.tid;
			var a = $(this).children("td.dt-op").find("a").eq(0);
			if(ary1.length>5){
				ary1.shift();
			}
			
			var serial = _.indexOf(ary1,tid);
			if(serial==-1){
				a.addClass('sui-text-grey');
			}else{
				a.removeClass('sui-text-grey');
			}
		})
	},
	getTableLength:function(table){
		return $(table).find("tbody tr").length;
	},
	getHtmlName:function(){
		var name = location.href.slice(location.href.lastIndexOf('/')+1,location.href.lastIndexOf('.html'));
		return name;
	},
	getDict:function(){
		var obj = JSON.parse(sessionStorage.getItem('constant'));
		return obj;
	},
	changeCategoryToObj:function(data){
		var obj = {};
		$.each(data,function(i,val){
			obj[val.tid] = val.name;
		})
		return obj;
	},
	fillPicPath:function(path){
		if(path){
			path = this.getApiHost().picUrl+'/'+path;
		}
		return path;
	},
	loadUserType:function(name){
		$.when(page.service.getConstant())
		.then(function(){
			var dtd = $.Deferred();
			var obj = JSON.parse(sessionStorage.getItem('constant'));
			var userType = obj.constant.Role;
			var ary = utilObj.changeRole(userType);
			var $ul = $("input[name="+name+"]").parents(".dropdown-inner").children("ul");
			utilObj.loadSelectOptions($ul,{
				text:'text',
				value:'value'
			},ary);
			dtd.resolve();
			return dtd.promise();
		})
	},
	changeRole:function(obj){
		var ary = [];
		delete obj.SUPERMANAGER;
		for(key in obj){
			var obj1 = {};
			if(key == 'GM'){
				obj1.text = '总监';
				obj1.value = obj[key];	
			}else if(key == 'MANAGER'){
				obj1.text = '管理员';
				obj1.value = obj[key];
			}else if(key == 'USER'){
				obj1.text = '用户';
				obj1.value = obj[key];
			}else{
				//
			}
			ary.push(obj1);
		}
		return ary;
	},
	changeRoleToObj:function(obj){
		var obj1 = {};
//		delete obj.SUPERMANAGER;
		for(key in obj){
			if(key == 'GM'){
				obj1[obj[key]]= '总监';
			}else if(key == 'MANAGER'){
				obj1[obj[key]]= '管理员';
			}else if(key == 'USER'){
				obj1[obj[key]]= '用户';
			}else if(key == 'SUPERMANAGER'){
				obj1[obj[key]]= '超级管理员';
			}
		}
		return obj1;
	},
	parseStr:function(str){
		var ary = [];
		if(str){
			try{
				var parsedStr = JSON.parse(str);
				if(utilObj.isAry(parsedStr)){
					ary = parsedStr;
				}else{
					ary[0] = parsedStr;
				}
			}
			catch(err){
				ary[0] = str;
			}
		}
		return ary;
	},
	addSerial:function(data){
		$.each(data,function(i,val){
			val.serial = i+1;
		})
		return data;
	},
	bindUploadPhotosData:function(pics,container){
		var _this = this;
		if(pics){
			try{
				pics = JSON.parse(pics);
			}
			catch(err){
				var ary = [];
				ary[0] = pics;
				pics = ary;
			}
		}else{
			pics = [];
		}
		var h = '';
		$.each(pics,function(i,val){
			var filename = _this.getFileName(val);
			if(val){
				h += 			'<li class="upload-img-li">\
								<label class="checkbox-pretty inline"><input type="checkbox"><span></span></label>\
								<span class="serial-number">'+(i*1+1)+'</span>\
								<div class="preview-img" data-src="'+_this.fillPicPath(val)+'"></div>\
								<input type="text" class="input-large file-name" value="'+filename+'" readonly="">\
								<input type="hidden" name="pics" class="file-path" value="'+val+'">\
								<div class="file_wrapper">\
									<button class="sui-btn btn-file" type="button">选择文件</button>\
									<input type="file" class="input-file" accept="image/bmp,image/jpg,image/jpeg,image/tiff,">\
								</div>\
								<button class="sui-btn btn-success btn-download" type="button">下载原图</button>\
							</li>';
			}
			
		})
		if(!h){
			h += 			'<li class="upload-img-li">\
								<label class="checkbox-pretty inline"><input type="checkbox"><span></span></label>\
								<span class="serial-number">'+1+'</span>\
								<div class="preview-img"></div>\
								<input type="text" class="input-large file-name" value="" readonly="">\
								<input type="hidden" name="pics" class="file-path" value="">\
								<div class="file_wrapper">\
									<button class="sui-btn btn-file" type="button">选择文件</button>\
									<input type="file" class="input-file" accept="image/bmp,image/jpg,image/jpeg,image/tiff,">\
								</div>\
								<button class="sui-btn btn-success btn-download" type="button">下载原图</button>\
							</li>';
		}
		$(container).html(h);
		utilObj.previewPhotos(container,".preview-img");
	},
	bindShowPhotosData:function(pics,container){
		var _this = this;
		if(pics){
			try{
				pics = JSON.parse(pics);
			}
			catch(err){
				var ary = [];
				ary[0] = pics;
				pics = ary;
			}
		}else{
			pics = [];
		}
		var h = '';
		$.each(pics,function(i,val){
			if(val){
				h += 			'<div class="photo-group">\
								<div class="photo-show"></div>\
								<button class="sui-btn btn-success btn-download" data-href="'+val+'">下载原图</button>\
							</div>';	
			}
			
		})
		if(!h){
//			h += 			'<div class="photo-group">\
//								<div class="photo-show"></div>\
//								<button class="sui-btn btn-success btn-download" data-href="">下载原图</button>\
//							</div>';	
		}
		$(container).html(h);
		_this.showPhoto(container,'.photo-show');
	},
	showPhoto:function(container,show){
		$(container).find(show).each(function(i){
			var href = $(this).siblings('.btn-download').data('href');
			href = utilObj.getApiHost().picUrl+'/'+href;
			$(this).css('background-image','url('+href+')');
		})
	},
	previewPhotos:function(container,show){
		$(container).find(show).each(function(i){
			var href = $(this).data('src');
			if(href){
				$(this).css('background-image','url('+href+')');
			}
			
		})
	},
	downloadPic:function(src) {
	    var $a = $("<a></a>").attr("href", src).attr("download", "img.png");
	    $a[0].click();
	},
//	downloadFile:function(url){
//		window.open(url);
//	},
	removeFile:function(input){
		
		var filepathInput = $(input).data("filepath");
		if(filepathInput){
			$("."+filepathInput+"").val("");
		}
		var previewFile = $(input).data("previewfile");
		if(previewFile){
			$("."+previewFile+"").val("");
		}
		if($(input)[0].files.length == 0){
			return;
		}
		var file = $(input) 
		file.after(file.clone(true).val("")); 
		file.remove(); 
	},
	uploadFile:function(input,callback){
		this.uploadAll(input,{
			url:"/m/userExtend/fileUpload",
			param:'file',
			callback:function(responseText){
				var data = JSON.parse(responseText);
				if(data.code == 0){
					//utilObj.alert({body:data.message});
				}else{
					var filepathInput = $(input).data("filepath");
					if(filepathInput){
						$("."+filepathInput+"").val(data.object.filePath);
						if(callback instanceof Function){
							callback();
						}
						
						utilObj.showMessage({
							type:utilObj.showMessageType['msg-success'],
							body:'上传成功'
						});
					}
				}
			}
		})
	},
	uploadAll:function(file,option){
		if($(file)[0].files.length == 0){
			return;
		}
		var fileObj = $(file)[0].files[0];
		var FileController = utilObj.getApiHost().baseUrl + option.url; 
		var form = new FormData();
	    form.append(option.param, fileObj);
	    var xhr =null;
	    if(window.XMLHttpRequest) {     
	        xhr = new XMLHttpRequest();   
	        if(xhr.overrideMimeType) {  
	            xhr.overrideMimeType("text/html");  
	        }  
	    }else if(window.ActiveXObject){   
	        var activeName = ["MSXML2.XMLHTTP","Microsoft.XMLHTTP"];  
	        for(var i=0;i>activeName.length();i++) {  
	              try{    
	                  xhr = new ActiveXObject(activeName[i]);  
	                  break;  
	              }catch(e){  
	              }  
	        }  
	    }
	    xhr.open("post", FileController, true);
	    xhr.onload = function () {
//	    		console.log(xhr.responseText);
			option.callback(xhr.responseText);
	    };
		xhr.send(form);
	},
	getApiHost:function(){
		var apiObj = {};
		switch(this.envValue){
			case 0:
				apiObj.baseUrl = 'http://192.168.2.211:8080/xa-blackcat-web';
				apiObj.picUrl = 'http://test.3tichina.com:8026';
				break;
			case 1:
				apiObj.baseUrl = 'http://jkjy.3tichina.com:81/xa-blackcat-web';
				apiObj.picUrl = 'http://jkjy.3tichina.com';
				break;
			case 2:
				apiObj.baseUrl = 'http://www.bj-shthinktank.com/xa-blackcat-web';
				apiObj.picUrl = 'http://www.bj-shthinktank.com:81';
				break;
				
		}
		return apiObj;
	},
	loadSelectOptions:function($ul,param,data){
		var h = '';
		h += '<li role="presentation" class=""> <a role="menuitem" tabindex="-1" href="javascript:void(0);" value="">请选择</a> </li>';
		var text_key = param.text;
		var value_key = param.value;
		$.each(data, function(i,val) {
			h += '<li role="presentation" class=""> <a role="menuitem" tabindex="-1" href="javascript:void(0);" value="'+val[value_key]+'">'+val[text_key]+'</a> </li>';
		});
		$ul.html(h);
	},
	getQueryData:function(query){
		var obj = {};
		$(query).find("input").each(function(){
			var key = $(this).attr('name');
			if(key){
				obj[key] = $(this).val();
			}
		})
		return obj;
	},
	getFormData:function(form){
		var _this = this;
		var ary = $(form).serializeArray();
		var obj = {};
		$.each(ary, function(i,val) {
			if(!obj[val.name]){
				obj[val.name] = val.value;
			}else{
				if(_this.isAry(obj[val.name])){
					obj[val.name].push(val.value);
				}else{
					var ary = [];
					ary.push(obj[val.name]);
					obj[val.name] = ary;
					obj[val.name].push(val.value);
				}
			}
			
		});
		$.each(obj,function(i,val){
			if(_this.isAry(val)){
				obj[i] = JSON.stringify(val);
			}
		})
		return obj;
	},
	toSelect:function(input,value){
		var a = $(input).parent(".dropdown-toggle");
		var ul = a.siblings(".sui-dropdown-menu");
		if(value){
			var a1 = ul.children("li").children("a[value="+value+"]");
			var li = a1.parent("li");
			var text = a1.text();
			a.children("input").val(value);
			a.children("span").text(text);
			ul.children("li").removeClass("active");
			li.addClass("active");
		}else{
			var a1 = ul.children("li").children("a[value='']");
			var li = a1.parent("li");
			var text = a1.text();
			a.children("input").val(value);
			a.children("span").text(text);
			ul.children("li").removeClass("active");
			li.addClass("active");
		}
		
	},
	isSameName:function(ary,curName){
		if(utilObj.isAry(ary)){
			$.each(ary,function(i,val){
				if(val == curName){
					return true;
				}
			})
		}else{
			if(ary == curName){
				return true;
			}
		}
	},
	bindShowData:function(selector,data){
		$(selector).find(".bszk-data").each(function(i){
			
			var elType = this.nodeName;
			var elName = $(this).data('name');
			var val = data[elName];
			if(val=="") val="暂无";
			if(elType == 'INPUT'){
				if($(this).parent().hasClass("dropdown-toggle")){//select
					_this.toSelect(this,val);
				}else if($(this).hasClass("input-date")){//birth
					$(this).datepicker('update',_this.shortenDate(val));
				}else if($(this).hasClass(".multi-input")){
					//
				}else{
					$(this).val(val);
				}
			}else if(elType == 'TEXTAREA'){
				$(this).val(val);
			}else{
				if(elName == 'birth' || elName == 'contact'){
					if(!val){
						$(this).text('暂无');
					}else{
						if(elName == 'birth'){
							val = utilObj.shortenDate(val);
						}
						$(this).text(val);
					}
				}else{
					$(this).text(val);
				}
				
			}
		})
	},
	clearData:function(selector,option){
		var _this = this;
		$(selector).find("[name]").each(function(i){
			
			var elType = this.nodeName;
			var elName = $(this).attr('name');
			$(this).val('');
//			if(elType == 'INPUT'){
//				if($(this).parent().hasClass("dropdown-toggle")){//select
//					_this.toSelect(this,val);
//				}else if($(this).hasClass("input-date")){//birth
//					$(this).datepicker('update',_this.shortenDate(val));
//				}else if($(this).hasClass("multi-input")){
//					//
//				}else{
//					$(this).val(val);
//				}
//			}else if(elType == 'TEXTAREA'){
//				$(this).val(val);
//			}
		})
		if(!option){
			return;
		}
		if(option.multi_input){
			var d1 = data[option.multi_input];
			if(d1){
				d1 = JSON.parse(d1);
			}
			$(selector).find("input[name="+option.multi_input+"]").each(function(i){
			
				$(this).val(d1[i]);
				
			})
		}
	},
	bindData:function(selector,data,option){
		var _this = this;
		var exceptName = '';
		if(option){
			if(option.except){
				exceptName =  option.except;
			}
		}
		$(selector).find("[name]").each(function(i){
			
			var elType = this.nodeName;
			var elName = $(this).attr('name');
			if(utilObj.isSameName(exceptName,elName)){
				return;
			}
			var val = data[elName];
			if(elType == 'INPUT'){
				if($(this).parent().hasClass("dropdown-toggle")){//select
					_this.toSelect(this,val);
				}else if($(this).hasClass("input-date")){//birth
					$(this).datepicker('update',_this.shortenDate(val));
				}else if($(this).hasClass("multi-input")){
					//
				}else{
					$(this).val(val);
				}
			}else if(elType == 'TEXTAREA'){
				$(this).val(val);
			}
		})
		if(!option){
			return;
		}
		if(option.multi_input){
			var d1 = data[option.multi_input];
			if(d1){
				d1 = JSON.parse(d1);
			}
			$(selector).find("input[name="+option.multi_input+"]").each(function(i){
			
				$(this).val(d1[i]);
				
			})
		}
	},
	fillDate:function(date){
		if(date){
			date += ' 00:00:00'
		}
		return date;
	},
	shortenDate:function(date){
		if(date){
			date = date.slice(0,10);
		}
		return date;
	},
	filterDate:function(data,key){
		var _this = this;
		$.each(data,function(i,val){
			if(val[key]){
				val[key] = _this.shortenDate(val[key]);
			}
		})
		return data;
	},
	filterIsTop:function(data,key){
		var _this = this;
		$.each(data,function(i,val){
			if(i!=2 && i!=4 ){
				val[key] = 'false';
			}else{
				val[key] = 'true';
			}
			
		})
		return data;
	},
	filterContent:function(data,key){
		var _this = this;
		$.each(data,function(i,val){
//			if(val[key]){
				val[key] = val.ayear+'年已成交'+val.yearOrders+'单,'+val.byear+'年'+val.bmonth+'月已成交'+val.monthOrders+'单';
//			}
		})
		return data;
	},
	getTid:function(obj){
		return $(obj).parents('tr:first').children("td.hide").eq(0).children("span").text();
	},
	getRowDataForButton:function(tb,obj){
		var tr = $(obj).parents('tr:first');
		return tb.getSelectedRow(tr);
	},
	logout:function(){
		sessionStorage.clear();
		utilObj.navigate('login');
	},
	alert:function(option){
		var setting={
			backdrop: true,
			bgcolor: '#000',
			keyboard: true,
			title: '提示',
			body: '',
			okBtn : '确定',
	//		cancelBtn : '关闭',
			closeBtn: false,
			transition: true,
			hasfoot: true,
			width: 'normal'
			//height: {number|string(px)} 内容区（.modal-body）高度
	//		timeout: {number} 1000    单位毫秒ms ,对话框打开后多久自动关闭
	//		show:     fn --------------function(e){}
	//		shown:    fn
	//		hide:     fn
	//		hidden:   fn
	//		okHide:   function(e){alert('点击确认后、对话框消失前的逻辑,
	//		           函数返回true（默认）则对话框关闭，反之不关闭;若不传入则默认是直接返回true的函数
	//		           注意不要人肉返回undefined！！')}
	//		okHidden: function(e){alert('点击确认后、对话框消失后的逻辑')}
	//		cancelHide: fn  取消关闭前
	//		cancelHidden: fn  取消关闭后
		};
		if(typeof(option)=="string") setting.body=option;
		$.extend(true, setting, option);
		$.alert(setting);
	},
	confirm:function(option){
		var setting={
			backdrop: true,
			bgcolor: '#000',
			keyboard: true,
			title: '提示',
			body: '确定删除该项目?',
			okBtn : '确定',
			closeBtn: false,
			transition: true,
			hasfoot: true,
			width: 'normal',
			cancelBtn : '取消'
		};
		$.extend(true, setting, option);
		$.confirm(setting);
	},
	showMessageType:{
		'msg-error':'msg-error',
		'msg-stop':'msg-stop',
		'msg-success':'msg-success',
		'msg-warning':'msg-warning',
		'msg-notice':'msg-notice',
		'msg-tips':' msg-tips',
		'msg-info':'msg-info',
		'msg-question':'msg-question'
	},
	showSuccessMessage:function(text){
		this.showMessage({
			type:'msg-success',
			body:text,
		})
	},
	showMessage:function(options){
		var setting={
			type: utilObj.showMessageType["msg-tips"],
			body: '&nbsp;'
		};
		if(options) $.extend(true, setting, options);
		$(".sui-text-center").remove();
		var id="msg_"+new Date().valueOf();
		var message ='<div class="sui-text-center" style="position:fixed;top:0;width:100%;left:0;z-index:10000000"><div class="'+ id +' topmessage msg-large sui-msg '+ setting.type +' msg-inlineblock fade" style="z-index:10000"><div class="msg-con">'+ setting.body +'<button type="button" data-dismiss="msgs" class="sui-close">×</button></div><s class="msg-icon"></s></div></div>';
		$("body").append(message);
		setTimeout(function(){$("."+id).addClass("in");},50);
		setTimeout(function(){$("."+id).removeClass("in");},3050);
	},
	showLoading:function(options){
		var defaultConfig = {
			bgColor:'black',
			bgOpacity:0.6,
			sizeClass:'loading-large',
			el:'body'
		}
		if(options instanceof Object){
			for(key in options){
				defaultConfig[key] = options[key];
			}
		}else if(options){
			defaultConfig['el'] = options;
		}	
		if($(defaultConfig.el).children('.arm-loading-bg').length>0){
			return;
		}
		var styleH = 'style="background-color:'+defaultConfig.bgColor+';opacity:'+defaultConfig.bgOpacity+';"';
		var h = '<div class="arm-loading-bg" '+styleH+'><div class="sui-loading '+defaultConfig.sizeClass+' arm-loading" >\
				<i class="sui-icon icon-pc-loading"></i></div></div>';
		$(defaultConfig.el).eq(0).append(h);
	},
	hideLoading:function(el){
		$(el).children('.arm-loading-bg').remove();
	},
	getStrParam:function(url,key){
	    var theRequest = {};
        var strs = url.split("&");
        for(var i = 0; i < strs.length; i ++) {
            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
        }
	    return theRequest[key];
	},
	getUrlParam:function(key){
	    var url = location.search;
	    var theRequest = {};
	    if (url.indexOf("?") != -1) {
	        var str = url.substr(1);
	        var strs = str.split("&");
	        for(var i = 0; i < strs.length; i ++) {
	            theRequest[strs[i].split("=")[0]] = unescape(strs[i].split("=")[1]);
	        }
	    }
	    return theRequest[key];
	},
	navigate:function(href,obj){
		if(href.indexOf('.html')<0){
			href += '.html';
		}
		var newHref = app.host+'/page/'+href;
		if(obj){
			newHref += '?';
			var urlEnd = $.param(obj);
			newHref += urlEnd;
		}
		location.href = newHref;	
	},
	setFormValid:function(form){
		$(form).removeClass('form-invalid');
		$(form).addClass('form-valid');
	},
	setFileName:function(input,url){
		var filename = this.getFileName(url);
		$(input).val(filename);
	},
	getFileName:function(url){
		var filename = url.slice(url.lastIndexOf('/')+1);
		return filename;
	},
	getPageName:function(){
		var href = location.href;
		var pageName = href.slice(href.lastIndexOf('/')+1,href.indexOf('.html'));
		return pageName;
	},
	isAry:function(obj){
		return Object.prototype.toString.call(obj) === '[object Array]'; 
	},
	previewFile:function(obj){
		var preview =$(obj).parents('.upload-img-li').children('.preview-img')[0];
		var file  = obj.files[0];
		var reader = new FileReader();
		reader.onloadend = function () {
			$(preview).css('background-image','url('+reader.result+')');
		}
		if (file) {
			reader.readAsDataURL(file);
		} else {
			$(preview).css('background-image','url("")');
		}
		
	},
	bindDeleteEvent:function(tb,btnSelector,obj){
		$(btnSelector).click(function(){
			var tid = tb.getCheckedRowTids();
			if(tid.length==0){
				utilObj.alert({
					body:'请选择数据'
				})
				return;
			}
			if(obj.confirm_option){
				utilObj.confirm(obj.confirm_option);
			}else{
				tb.removeRows();
			}	
		})
	},
	addPhotoUploadLi:function(container){
		var num = $(container).find('li').length;
		if(num>=5){
			utilObj.alert({
				body:'最多添加五张照片'
			})
			return;
		}
		var lastSpan = $(container).find("span.serial-number:last");
		var serial = 1;
		if(lastSpan){
			serial = lastSpan.text()*1+1;
		}
		var h = '<li class="upload-img-li">\
					<label class="checkbox-pretty inline"><input type="checkbox"><span></span></label>\
					<span class="serial-number">'+serial+'</span>\
					<div class="preview-img" ></div>\
					<input type="text" class="input-large file-name" readonly/>\
					<input type="hidden" name="pics" class="file-path"/>\
					<div class="file_wrapper">\
						<button class="sui-btn btn-file" type="button">选择文件</button>\
						<input type="file" class="input-file" accept="image/bmp,image/jpg,image/jpeg,image/tiff,">\
					</div>\
					<button class="sui-btn btn-success btn-download" type="button">下载原图</button>\
				</li>';
		$(container).append(h);
	},
	deletePhotoUploadLi:function(container,param){
		var _this = this;
		var isDelete = false;
		$(container).find("label.checked").each(function(i){
			isDelete = true;
			var li = $(this).parents("li:first");
			var tid = li.find("input[type=file]").data("tid");
			if(param){
				if(param.callback){
					param.callback(tid,function(){
						li.remove();
						_this.resortLi(container);
					});
				}
			}else{
				li.remove();
			}
			
		})
		if(isDelete){
			this.resortLi(container);
		}
		
	},
	resortLi:function(container){
		$(container).find("span.serial-number").each(function(i){
			$(this).text(i+1);
		})
	},
	bindUploadPhotoEvent:function(container){
		var _this = this;
//		$(container).on('click','.btn-upload',function(){
//			var li = $(this).parents("li:first");
//			var inputFile = li.find("input[type=file]");
//			_this.uploadAll(inputFile,{
//				url:"/m/cooperate/photoUpload",
//				param:'photoFile',
//				callback:function(responseText){
//					var data = JSON.parse(responseText);
//					if(data.code == 0){
//						//utilObj.alert({body:data.message});
//					}else{
//						var filepathInput = li.find("input.file-path");
//						if(filepathInput){
//							$(filepathInput).val(data.object.picturePath);
//							utilObj.showMessage({
//								type:utilObj.showMessageType['msg-success'],
//								body:'上传成功'
//							});
//						}
//					}
//				}
//			})
//		})
		$(container).on('change','input[type=file]',function(){
			var li = $(this).parents("li:first");
			var preview = li.find(".preview-img");
			var filenameInput = li.find("input.file-name");
			var filename = this.files[0].name;
			if(filenameInput){
				$(filenameInput).val(filename);
			}
			if(preview){
//				_this.previewImg(preview,this);
				utilObj.previewImgBackground(preview,this);
			}
			
//			var li = $(this).parents("li:first");
			var inputFile = li.find("input[type=file]");
			_this.uploadAll(inputFile,{
				url:"/m/userExtend/fileUpload",
				param:'file',
				callback:function(responseText){
					var data = JSON.parse(responseText);
					if(data.code == 0){
						//utilObj.alert({body:data.message});
					}else{
						var filepathInput = li.find("input.file-path");
						if(filepathInput){
							$(filepathInput).val(data.object.filePath);
							utilObj.showMessage({
								type:utilObj.showMessageType['msg-success'],
								body:'上传成功'
							});
						}
					}
				}
			})
			
		})
	},
	previewImg:function(preview,obj){
		preview = preview[0];
		var file  = obj.files[0];
		var reader = new FileReader();
		reader.onloadend = function () {
			preview.src = reader.result;
		}
		if (file) {
			reader.readAsDataURL(file);
		} else {
			preview.src = "";
		}
	},
	previewImgBackground:function(preview,obj){
//		preview = preview[0];
		var file  = obj.files[0];
		var reader = new FileReader();
		reader.onloadend = function () {
//			preview.src = reader.result;
			$(preview).css('background-image','url('+reader.result+')');
		}
		if (file) {
			reader.readAsDataURL(file);
		} else {
//			preview.src = "";
			$(preview).css('background-image','url("")');
		}
	},
	exportExcel:function(titleData,jsonData){
//		titleData={
//			jsonData-attr1:"姓名",
//			jsonData-attr2:"年龄"
//		}
//utilObj.exportExcel({a:"姓名",b:"年龄"},[{a:'name1',b:"1"},{a:'name2',b:"2"}])
		if(!$.isArray(jsonData)) return;
		var th='<tr>',tr='',tb_name='tb_download-'+new Date().valueOf();
		for(var _key in titleData){
			th+='<td>'+ titleData[_key] +'</td>';
		}
		th+='</tr>';
		
		$.each(jsonData, function() {
			var obj = this;
			tr+='<tr>';
			for(var _key in titleData){
				if(obj[_key]){
					tr+='<td>'+ obj[_key] +'</td>';
				}else{
					tr+='<td></td>';
				}
			}
			tr+='</tr>';
		});
		
		var str='<table id="'+ tb_name +'">'+ th + tr +'</table>';
		$("body").append('<div class="div-download" style="display:none;">'+ str +'</div>');
		$("#"+tb_name).table2excel({
			exclude: ".noExl",
			// 导出的Excel文档的名称
			name: "download",
			// Excel文件的名称
			filename: "download"
		});
	}
};
utilObj.bindEvent();
(function($){
	var tableBuilder=function(el,datasource,options){
				if(!(datasource instanceof Array)){
					return null;
				}
				if(datasource.length<0){
					return null;
				}
				this.wrapper = typeof el == 'string' ? $(el) : el;
				var new_datasource = [];
				if(options.columns){
					for(var i=0;i<datasource.length;i++){
						new_datasource[i] = {};
						for(var j=0;j<options.columns.length;j++){
							var key = options.columns[j];
							new_datasource[i][key] = datasource[i][key];
						}
					}
					this.data=new_datasource;
				}else{
					this.data=datasource;
				}
				this.options = {
					dataTitles:[],
					hasCheckBox:false,
					checkboxTitle:"",
					diplayType:'auto', //fixed
					width:'100%',
					trHeight:null
				};
				for ( var i in options ) {
					this.options[i] = options[i];
				}
				//如果数据为空,就造一组假数据来正常生成table,之后再将数据移出
				if(this.data.length==0){
					var d1 = [{}];
					if(!this.options.columns) return null;
					$.each(this.options.columns,function(){
						d1[0][this]="";
					});
					this.data = d1;
					//标记数据为空
					this.options.isEmpty = true;
				}
				this._renderHTML();
				this._create();
			};
			tableBuilder.prototype={
				_renderHTML:function(){
					this.id='tb_'+new Date().valueOf();
					this.wrapper.css("overflow","auto");
					this.wrapper.html('<table class="'+ this.id +' cell-border" ></table>');
					this.idClass=$('.'+this.id);
				},
				_create:function(){
					var _this = this;//jc
					var targets_i=0;
					var obj=this;
					var setting = {
		//				fixedColumns: {
		//			        leftColumns: 1
		//			    },
						data:this.data,
					    info:false,
					    paging: false,
					    searching: false,
					    ordering:  false,
					    scrollX:false,
					    autoWidth: false,
					    columnDefs:[]
					};
					if(this.options.setting){
						$.extend(true, setting, this.options.setting);
					}
					
					if(this.options.tableHeight){
						setting.scrollY=this.options.tableHeight;
						setting.scrollX=true;
					}
					
					var columns=[];
					if(this.options.hasCheckBox){//如果有CheckBox
						setting.columnDefs.push({
						    targets: targets_i,
						    width:'1%',
						    title:this.options.checkBoxTitle?this.options.checkBoxTitle:"",
						    class:'dt-center',
						    render: function(data, type, row, meta){
						    	var html='<label class="checkbox-pretty inline"><input name="'+ obj.id +'_ck" type="checkbox"><span></span></label>';
						    	return html;
						    }
						});
						targets_i++;
					}
					
					var i=0;
					for(var _key in this.data[0]){
						var t=this.options.dataTitles[i];
						if(!this.options.dataTitles[i]) t=_key;
						var columndef={
							targets:targets_i,
							width:"",
							data:_key,
							title:t,
							class:'tdEllipsis dt-center',
							render: function(data, type, row, meta){
						    	return '<span dataRow="'+meta.row+'"  dataCol="'+meta.col+'" title="'+data+'">'+data+'</span>';
						    }
						};
						if(this.options.dataSpecial && this.options.dataSpecial[i]){
							$.extend(true, columndef, this.options.dataSpecial[i]);
						}
						setting.columnDefs.push(columndef);
						i++;
						targets_i++;
					}
					
					if(this.options.columnsExt && this.options.columnsExt instanceof Array){
						$.each(this.options.columnsExt, function() {
							this.targets=targets_i;
							setting.columnDefs.push(this);
							targets_i++;
						});
					}
					
					if(this.options.buttons && this.options.buttons instanceof Array){//[{text:"",eventname:""},{}]
						setting.columnDefs.push({
							targets:targets_i,
							title:this.options.buttonsTitle?this.options.buttonsTitle:"操作",
							class:this.options.buttonsClass?this.options.buttonsClass:"dt-op",
							data:this.options.buttonsKey?this.options.buttonsKey:"",
							render: function(data, type, row, meta){
								var btns='';
								$.each(obj.options.buttons, function() {
									if(this.eventname && typeof this.eventname == 'string' && this.eventname.length>0){
										btns+='<li><a href="javascript:void(0);" class="'+this.class+'" onClick="'+this.eventname+'(this);">'+ this.text +'</a></li>';
									}
									
								});
								
								var html=['<ul class="unstyled table-btns">',
												btns,
			'						          </ul>'].join("");

						    	return html;
						    }
						});
						targets_i++;
					}
					this.DataTableObj = this.idClass.DataTable(setting);
					//如果数据为空的话
					if(this.options.isEmpty){
						this.removeAllRows();
					}
					this.wrapper.css("overflow","inherit");		
					
					//分页
					if(this.options.pagination){
						$(this.wrapper).append('<div class="'+this.id+'_pagination"></div>');
						var paginationSetting={
						    pages: 10,
						    styleClass: ['pagination-centered'],
						    showCtrl: true,
						    displayPage: 6,
						    onSelect: function (num) {
						        console.log(num);
						    }        
						};
						$.extend(true, paginationSetting, this.options.pagination);
						paginationSetting.onSelect=function(num){
							if(paginationSetting.selectedEvent && typeof paginationSetting.selectedEvent == 'string' && paginationSetting.selectedEvent.length>0){
								eval(paginationSetting.selectedEvent+'('+num+')');
							}
						};
						this.paginationObj = $("."+this.id+"_pagination").pagination(paginationSetting);
					}
					
					if(this.options.hasCheckBox){//如果有CheckBox
						this.wrapper.on("click",".tdEllipsis",function(){
							var ck = $(this).parents("tr").find('label.checkbox-pretty:eq(0)').checkbox();
							ck.checkbox("toggle");
						});
					}
					
					if(this.options.tableHeight){
						this.wrapper.find(".dataTables_scrollBody").scroll(function(){
					        var viewH =$(this).height(),//可见高度
					        contentH =$(this).get(0).scrollHeight,//内容高度
					        scrollTop =$(this).scrollTop();//滚动高度
					        if(!obj._scrollstart) obj._scrollstart=0;
					        if(scrollTop - obj._scrollstart>0){//只有往下滚动时
					        	if(!obj._scrollTime) obj._scrollTime=0;
						        if(contentH - viewH - scrollTop <= 0 && (new Date().valueOf())-obj._scrollTime>1000 ) {
						        	obj._scrollTime=new Date().valueOf();
						        	if(obj.options.scrollBottom){
						        		eval(obj.options.scrollBottom+"()");
						        	}
						        }
					        }
					        obj._scrollstart=scrollTop;
					     });
					}
				},
				removeRow:function(tr){
					this.DataTableObj.row(tr).remove().draw();
				},
				removeRows:function(){
					var obj=this;
					$("."+obj.id+" label.checked").eq(0).each(function(){
						obj.removeRow($(this).parents("tr:first"));
					});
//					$("."+obj.id+" label.checked").each(function(){
//						obj.removeRow($(this).parents("tr:first"));
//					});
				},
				removeAllRows:function(){
					var obj=this;
					$("."+obj.id+" tbody tr").each(function(){
						obj.removeRow($(this));
					});
				},
				addRow:function(jsondata){
					this.DataTableObj.row.add(jsondata).draw();
				},
				addRows:function(jsondatas){
					var obj=this;
					$.each(jsondatas, function() {
						obj.addRow(this);
					});
				},
				resetData:function(jsondatas){
					this.removeAllRows();
					this.addRows(jsondatas);
				},
				getSelectedRow:function(tr){//获取选中行的数据
					return this.DataTableObj.row(tr).data();
				},
				getCheckedRowTids:function(){
					var _this = this;
					var obj = this;
					var ary = [];
					$("."+obj.id+" label.checked").each(function(){
						var tr = $(this).parents("tr:first");
						var tid = _this.getSelectedRow(tr).tid;
						ary.push(tid);
					});
					return ary;
				},
				getCheckedRowData:function(){
					var obj=this;
					var tr = $("."+obj.id+" label.checked").eq(0).parents("tr:first");
					return this.getSelectedRow(tr);
				}
				
			};
			if ( typeof module != 'undefined' && module.exports ) {
				module.exports = tableBuilder;
			} else {
				window.tableBuilder = tableBuilder;
			}
})(jQuery);
var modalBulider = {
	_currentZIndex:1000,
	_modalClass:null,				
	_creatElemHtml : function(modelselector,defaultOption){	
		this._modalClass='modal_'+new Date().valueOf();
		var bodyWidth = document.documentElement.clientWidth;
		var bodyHeight = document.documentElement.clientHeight;	
		$('body').append(template(modelselector));	
		$('body div[role="dialog"]:last').addClass(this._modalClass);					
		$('.'+ this._modalClass + " .modal-body").css({'max-width':defaultOption.maxWidth*bodyWidth + 'px','min-width':defaultOption.minWidth*bodyWidth +'px'});
		$('.'+ this._modalClass + " .modal-header h4").text(defaultOption.title);
		$('.'+ this._modalClass + " .modal-header").css('background',defaultOption.titlebgColor);
		$('.'+ this._modalClass + " .modal-body").css({'max-height':defaultOption.maxHeight*bodyHeight+'px','min-height':defaultOption.minHeight*bodyHeight + 'px'});
		if(defaultOption.contentheight && defaultOption.contentheight !='auto'){
			if(defaultOption.contentheight > defaultOption.maxHeight){
				$('.'+ this._modalClass + " .modal-body").css({'height':defaultOption.maxHeight*bodyHeight});
			}else{
				$('.'+ this._modalClass + " .modal-body").css({'height':defaultOption.contentheight*bodyHeight});
			}
			
		}else{
			$('.'+ this._modalClass + " .modal-body").css({'height':'auto'});
		}
		if(defaultOption.containerWidth && defaultOption.containerWidth !='auto'){
			if(defaultOption.containerWidth > defaultOption.maxWidth){
				$('.'+ this._modalClass).css({'width':defaultOption.maxWidth*bodyWidth + 'px','margin-left':'-'+(defaultOption.maxWidth*bodyWidth/2) + 'px','left':'50%'});
			}else{
				$('.'+ this._modalClass).css({'margin-left':'-'+(defaultOption.containerWidth*bodyWidth/2) + 'px','width':defaultOption.containerWidth*bodyWidth + 'px','left':'50%'});
			}
			
		}
	
	},
	_drapModal:function(){
		var p={};
        function getXY(eve) {
            var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
            var scrollLeft = document.documentElement.scrollLeft || document.body.scrollLeft;
            return {x : scrollLeft + eve.clientX,y : scrollTop + eve.clientY };
        }
        
        $(document).on("mouseup",function(ev){
        	p={};
        	$(document).off("mousemove");
        });
        
        $(".modal-header:last").on("mousedown",function(ev){
        	document.body.onselectstart=document.body.ondrag=function(){
				return false;
			}
        	p.y = ev.pageY - $(this).parents(".sui-modal")[0].offsetTop;
        	p.x = ev.pageX - $(this).parents(".sui-modal")[0].offsetLeft;
        	
            $(document).on("mousemove",function(ev){console.log("a");
        		var oEvent = ev || event;
                var pos = getXY(oEvent);
                $(".sui-modal:last").css({left:(pos.x-p.x) + "px",top:(pos.y-p.y) + "px","margin-left":"10px","margin-top":"10px"});
            });
        });
		$(document).on('hidden.bs.modal','.modal',function(e){
			$('.modal-dialog').css({'top': '0px','left': '0px'})
			document.body.onselectstart=document.body.ondrag=null;
		});
//		var dragModal={
//			mouseStartPoint:{"left":0,"top":  0},
//			mouseEndPoint : {"left":0,"top":  0},
//			mouseDragDown : false,
//			basePoint : {"left":0,"top":  0},
//			moveTarget:null,
//			topleng:0
//		}
//		$(document).on("mousedown",".modal-header",function(e){
//			var Iheight = document.documentElement.clientHeight;
//			//webkit内核和火狐禁止文字被选中
//			$('body').addClass('select');			
//			$('body').css('height',Iheight);
//			//ie浏览器禁止文字选中
//			document.body.onselectstart=document.body.ondrag=function(){
//				return false;
//				}
//			if($(e.target).hasClass("close"))//点关闭按钮不能移动对话框  
//				return;  
//			dragModal.mouseDragDown = true;  
//			dragModal.moveTarget = $(this).parent().parent().parent();         
//			dragModal.mouseStartPoint = {"left":e.clientX,"top":  e.pageY};  
//			dragModal.basePoint = dragModal.moveTarget.offset();  
//			dragModal.topLeng=e.pageY-e.clientY;
//		});  
//		$(document).on("mouseup",function(e){       
//			dragModal.mouseDragDown = false;  
//			dragModal.moveTarget = undefined;  
//			dragModal.mouseStartPoint = {"left":0,"top":  0};  
//			dragModal.basePoint = {"left":0,"top":  0};  
//		});  
//		$(document).on("mousemove",function(e){  
//			if(!dragModal.mouseDragDown || dragModal.moveTarget == undefined)return;          
//			var mousX = e.clientX;  
//			var mousY = e.pageY;  
//			if(mousX < 0)mousX = 0;  
//			if(mousY < 0)mousY = 25;  
//			dragModal.mouseEndPoint = {"left":mousX,"top": mousY};  
//			var width = dragModal.moveTarget.width();  
//			var height = dragModal.moveTarget.height();
//			var clientWidth=document.body.scrollWidth
//			var clientHeight=document.body.scrollHeight;
//			if(dragModal.mouseEndPoint.left<dragModal.mouseStartPoint.left - dragModal.basePoint.left){
//				dragModal.mouseEndPoint.left=0;
//			}
//			else if(dragModal.mouseEndPoint.left>=clientWidth-width+dragModal.mouseStartPoint.left - dragModal.basePoint.left){
//				dragModal.mouseEndPoint.left=clientWidth-width-38;
//			}else{
//				dragModal.mouseEndPoint.left =dragModal.mouseEndPoint.left-(dragModal.mouseStartPoint.left - dragModal.basePoint.left);//移动修正，更平滑  
//				
//			}
//			if(dragModal.mouseEndPoint.top-(dragModal.mouseStartPoint.top - dragModal.basePoint.top)<dragModal.topLeng){
//				dragModal.mouseEndPoint.top=dragModal.topLeng;
//			}else if(dragModal.mouseEndPoint.top-dragModal.topLeng>clientHeight-height+dragModal.mouseStartPoint.top - dragModal.basePoint.top){
//				dragModal.mouseEndPoint.top=clientHeight-height-38+dragModal.topLeng;
//			}
//			else{
//				dragModal.mouseEndPoint.top = dragModal.mouseEndPoint.top - (dragModal.mouseStartPoint.top - dragModal.basePoint.top);           
//			}
//			dragModal.moveTarget.offset(dragModal.mouseEndPoint);  
//		});   
//		$(document).on('hidden.bs.modal','.modal',function(e){
//			$('.modal-dialog').css({'top': '0px','left': '0px'})
//			$('body').removeClass('select');
//			document.body.onselectstart=document.body.ondrag=null;
//		})
	},
	modal:function(modalselector,option){
		var defaultOption ={
			title:'提示',//标题
			titlebgColor:'',//标题背景颜色
			containerWidth:'',//容器宽度百分比
			maxWidth : 0.7,
			minWidth : 0.2,
			contentheight:'',//内容高度
			maxHeight : 0.8,
			minHeight: 0.2	,
		};
		if(!$.isEmptyObject(option)){
			for(i in option){
				defaultOption[i] = option[i];
			}						
		}
		this._creatElemHtml(modalselector,defaultOption);
		$('.'+ this._modalClass).modal();
		
		if(option.shown){
			$('.'+ this._modalClass).on('shown', function(e){						
				option.shown();
			});
		}
		if(option.hidden){
			$('.'+ this._modalClass).on('hidden', function(e){						
				option.hidden();
				$(this).remove();
			});
		}
		else{
			$('.'+ this._modalClass).on('hidden', function(e){						
				$(this).remove();
			});
		}
		
		if(!defaultOption.containerWidth){//弹层自定义宽度展示后获取宽度值
			$('.'+ this._modalClass).css('width','auto');					
			var objwidth = $('.'+ this._modalClass).width();
			$('.'+ this._modalClass).css({'margin-left':'-'+(objwidth/2) + 'px','left':'50%'});
		}
		/** 拖拽模态框*/ 			
		this._drapModal();					
		$(".sui-modal-backdrop:last").css("z-index",this._currentZIndex);
		this._currentZIndex++;
		$(".sui-modal:last").css("z-index",this._currentZIndex);
		this._currentZIndex++;
	},
	resize:function(){
//		var left = (document.documentElement.clientWidth-$('.'+ this._modalClass).width())/2;
//		var height = (document.documentElement.clientHeight-$('.'+ this._modalClass).height())/2;
//		$('.'+ this._modalClass).css({top:top+"px",left:left+"px",margin:"0px"});

		var w = 0-$('.'+ this._modalClass).width()/2;
		var h = 0-$('.'+ this._modalClass).height()/2;
		$('.'+ this._modalClass).css({"margin-top":h+"px","margin-left":w+"px"});
	}
};

(function($){
	var treeBuilder=function(el,datasource,options){
				if(!(datasource instanceof Array)){
					return null;
				}
				if(datasource.length<=0){
					return null;
				}
				this.wrapper = typeof el == 'string' ? $(el) : el;
				this.data=datasource;
				this.options = {
					displayType:"normal",
					types : {
		//				"#" : { "max_children" : 1, "max_depth" : 4, "valid_children" : ["root"] },
		//				"root" : { "icon" : "/static/3.3.3/assets/images/tree_icon.png", "valid_children" : ["default"] },
		//				"default" : { "valid_children" : ["default","file"] },
						"file" : { "icon" : "jstree-default jstree-file" }
					},
					plugins : ["wholerow","types","dnd", "state"],
					core : {
						"check_callback" : true,
						'force_text' : true,
						"animation" : 0,
						'data' : datasource
					}
				};
				if(options){
					for ( var i in options ) {
						this.options[i] = options[i];
					}
				}

				if(this.options.displayType=="hasCheck"){
					this.options.checkbox={keep_selected_style : false};
					this.options.plugins=["wholerow","checkbox","types","dnd", "state"];
				}
				
				this._create();
			};
			treeBuilder.prototype={
				_create:function(){
					var obj = this;
					this.treeObj = this.wrapper.jstree(this.options);
					this.treeObj = this.wrapper.jstree(
						{
							core:{
								"check_callback" : true
							}
						}
					);
					
					if(this.options.displayType=="normal"){
						this.wrapper.on("click",".jstree-node",function(event){
							event.stopPropagation();
							obj.wrapper.find(".tree-operate").remove();
							var url= frameObj.host();
							if($(this).find("a:first .tree-operate").length<=0){
								$(this).find("a:first").append('<div class="tree-operate" style="display: inline-block;padding-left:5px;"><i class="jstree-default jstree-icon add" style="background: url('+url+'/lib/jstree/images/32px2.png) -132px -68px no-repeat;" ></i><i class="jstree-default jstree-icon edit" style="background: url('+url+'/lib/jstree/images/32px2.png) -164px -68px no-repeat;" ></i><i class="jstree-default jstree-icon delete" style="background: url('+url+'/lib/jstree/images/32px2.png) -196px -68px no-repeat;" ></i></div>');
							}
						});
						this.wrapper.on("click",".add",function(){
							event.stopPropagation();
							obj.addNode();
						});
						this.wrapper.on("click",".edit",function(){
							event.stopPropagation();
							obj.renameNode();
						});
						this.wrapper.on("click",".delete",function(){
							event.stopPropagation();
							obj.deleteNode();
						});
					}
				},
				addNode:function(){
					var sel = this.treeObj.get_selected();
					if(!sel.length) { return false; }
					sel = sel[0];
					sel = this.treeObj.create_node(sel,  {"type":"file",text:"新节点"},'last',function(){console.log("new");});
					if(sel) {
						this.treeObj.edit(sel);
					}
				},
				renameNode:function(){
					var sel = this.treeObj.get_selected();
					if(!sel.length) { return false; }
					sel = sel[0];
					this.treeObj.edit(sel,null,function(){console.log("rename");});
				},
				deleteNode:function(){
					var sel = this.treeObj.get_selected();
					if(!sel.length) { return false; }
					this.treeObj.delete_node(sel);
				},
			};
			if ( typeof module != 'undefined' && module.exports ) {
				module.exports = treeBuilder;
			} else {
				window.treeBuilder = treeBuilder;
			}
})(jQuery);
