var frameObj = {
	menuData:[
		{
			text:'资源管理',
			class:'resource-icon',
			url:['resource_manage','resource_modify','resource_detail',],
			powerid:{"1":"","2":"","3":"","4":""}
		},
		{
			text:'客户管理',
			class:'customer-icon',
			url:['customer_manage','customer_modify','customer_detail',],
			powerid:{"1":"","2":"","3":"","4":""}
		},
		{
			text:'公告管理',
			class:'notice-icon',
			url:'notice_manage',
			powerid:{"1":"","2":""}
		},
		{
			text:'账号管理',
			class:'account-icon',
			url:'account_manage',
			powerid:{"1":""}
		},
		{
			text:'个人办公',
			class:'personal-icon',
			url:'personal_work',
			powerid:{"1":"","2":"","3":"","4":""}
		},
	],
	pageBase:'page',
	host:function(){
		return location.href.substring(0,location.href.indexOf(this.pageBase));
	},
	loadFrame:function(){
		var page = utilObj.getPageName();
		if(!sessionStorage.getItem('t') && page!='login'){
			utilObj.navigate('login');
		}
		var _this = this;
		var pageName = utilObj.getPageName();
		if(pageName == 'login' || pageName == 'change_password'){
			return;
		}
		
		var h = '<div id="header-wrapper">\
			<div class="logo logo-frame" data-href="index"></div>\
			<div class="header-text">贝尚智库公司资源管理系统</div>\
			<div class="header-menu">\
				<div class="aboutus">'+JSON.parse(sessionStorage.getItem("userinfo")).user.userName+'</div>\
				<div class="signout"><button class="sui-btn signout-btn">注销</button></div class="signout">\
			</div>\
			<div class="header-mask"></div>\
		</div>\
		<div id="left-menu-wrapper">\
			<ul class="left-menu-list clearfix">';
		
		var userRole=null;
		if(sessionStorage.getItem("userinfo")){
			userRole=JSON.parse(sessionStorage.getItem("userinfo"));
			userRole=userRole.user.roleId;
		}
		for(var i=0;i<this.menuData.length;i++){
			if(this.menuData[i]["powerid"][userRole]!=undefined){
				var name = utilObj.getHtmlName();
				if(utilObj.isAry(this.menuData[i].url)){
					var isSame = _this.isSame(this.menuData[i].url,name);
					if(isSame){
						h += '<li class="left-menu-li selected" data-href="'+this.menuData[i].url[0]+'">\
							<div class="left-menu-icon '+this.menuData[i].class+'"></div>\
							<div class="left-menu-text">'+this.menuData[i].text+'</div>\
						 </li>';
					}else{
						h += '<li class="left-menu-li" data-href="'+this.menuData[i].url[0]+'">\
							<div class="left-menu-icon '+this.menuData[i].class+'"></div>\
							<div class="left-menu-text">'+this.menuData[i].text+'</div>\
						 </li>';
					}
				}else{
					if(name == this.menuData[i].url){
						h += '<li class="left-menu-li selected" data-href="'+this.menuData[i].url+'">\
							<div class="left-menu-icon '+this.menuData[i].class+'"></div>\
							<div class="left-menu-text">'+this.menuData[i].text+'</div>\
						 </li>';
					}else{
						h += '<li class="left-menu-li" data-href="'+this.menuData[i].url+'">\
							<div class="left-menu-icon '+this.menuData[i].class+'"></div>\
							<div class="left-menu-text">'+this.menuData[i].text+'</div>\
						 </li>';
					}
				}
				
				
			}
		}
		h += '</ul>\
			<div class="left-menu-mask"></div>\
		</div>';
		$("body").prepend(h);
		this.bindEvent();
	},
	isSame:function(ary,name){
		var flag = false;
		for(var i=0;i<ary.length;i++){
			if(ary[i] == name){
				flag = true;
			}
		}
		return flag;
	},
	bindEvent:function(){
		$(".left-menu-li").click(function(){
			var href = $(this).data("href");
			utilObj.navigate(href);
		})
		$(".sui-breadcrumb a").click(function(e){
			e.preventDefault();
			var href = $(this).data('href');
			utilObj.navigate(href);
		})
		$(".logo-frame").click(function(){
			var href = $(this).data("href");
			utilObj.navigate(href);
		})
		$(".signout-btn").click(function(){
			utilObj.logout();
			
		})
	}
}
frameObj.loadFrame();
var pageSetting={
	Pagination_DefaultPage:8,
	Pagination_MaxPage:1000,
}
function dopageing(){
	alert('selected');
}

//扩展validate
var pwd_string = function(value, element, param) {
	var asc='';
	for(var i=0;i<value.length;i++){
		asc = value[i].charCodeAt();
		if((asc>=48 && asc<=57) || (asc>=65 && asc<=122)) continue;
		else return false;
	}
	return true;
};
$.validate.setRule("pwd_string", pwd_string, '必须是字母或数字');