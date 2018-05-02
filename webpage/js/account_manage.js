page.account_manage = {
	storage:{
		tb1:'',
	},
	bindEvent:function(){
		var _this = this;
		$("#btn_query1").click(function(){
			_this.loadUserList();
		})
		$("#btn_add1").click(function(){
			_this.buildUserModal();
		})
	},
	buildUserModal:function(){
		var _this = this;
		var setKeyVal ={
			title:'账号添加',
			shown:function(){
				var rules = {
					userName:{
						required:true,
					},
					password:{
						required:true,
//						pwd_string:"",
						password:true,
					},
					repeatPW:{
						match:'password',
						required:true,
					},
					company:{
						required:true,
					},
					contact:{
						required:true,
//						tel:true,
					},
					userType:{
						required:true,
					},
				}
				utilObj.loadUserType('userType');
				$(".form-user").validate({
					rules:rules,
					messages:{
						repeatPW:['请填写','请与新密码保持一致']
					},
					success:function(){
						_this.modifyUser();
						return false;
					}
				})
			}
		}
		modalBulider.modal('template1',setKeyVal);
	},
	buildUserModalInfo:function(tid){
		var _this = this;
		var setKeyVal ={
			title:'修改信息',
			shown:function(){
				var rules = {
					company:{
						required:true,
					},
					contact:{
						required:true,
					},
					userType:{
						required:true,
					},
				}
				$.when(utilObj.loadUserType('userType'))
				.then(_this.bindUserData(tid))
				$(".form-user").validate({
					rules:rules,
					success:function(){
						_this.modifyUser(tid);
						return false;
					}
				})
			}
		}
		modalBulider.modal('template2',setKeyVal);
	},
	buildUserModalPassword:function(tid){
		var _this = this;
		var setKeyVal ={
			title:'重置密码',
			shown:function(){
				var rules = {
					newPassword:{
						required:true,
						password:true,
					},
					repeatPW:{
						match:'newPassword',
						required:true,
					},
				}
				$.when(utilObj.loadUserType('userType'))
				.then(_this.bindUserData(tid))
				$(".form-user").validate({
					rules:rules,
					messages:{
						repeatPW:['请填写','请与新密码保持一致']
					},
					success:function(){
						_this.modifyUser(tid);
						return false;
					}
				})
			}
		}
		modalBulider.modal('template3',setKeyVal);
	},
	bindUserData:function(tid){
		var _this = this;
		var jsonFilter = {
			search_EQ_tid:tid,
		}
		var param = {
			expertId:_this.storage.expertId,
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var data = d.content[0];
				data.userName = data.name;
				utilObj.bindData(".form-user",data);
			}
		}
		page.service.getUserData(param);
	},
	modifyUser:function(tid){
		var _this = this;
		var param = utilObj.getFormData(".form-user");
		delete param.repeatPW;
		var setting = {
			url:"/m/userExtend/registerUser",
			type: "POST",
			data:param,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
//					console.log('保存成功');
					var page = utilObj.getTablePage({
						tb:_this.storage.tb1,
					})
					_this.loadUserList(page);
					$("#myModal1").modal('hide');
					$("#myModal2").modal('hide');
					$("#myModal3").modal('hide');
				}
			}
		}
		if(tid){
			setting.url = "/m/userExtend/modifyUser";
		}
		ajax(setting);
	},
	modifyAccountInfo:function(obj){
		var _this = this;
		var tid = utilObj.getTid(obj);
		_this.buildUserModalInfo(tid);
	},
	resetPassword:function(obj){
		var _this = this;
		var tid = utilObj.getTid(obj);
		_this.buildUserModalPassword(tid);
	},
	delete:function(obj){
		var _this = this;
//		var tid = utilObj.getTid(obj);
		utilObj.confirm({
			body:'请确认是否删除该账号?',
			okHide:function(){
				var userName = _this.storage.tb1.getSelectedRow($(obj).parents('tr:first')).name;
				var param = {
					userName:userName,
					callback:function(){
//						_this.storage.tb1.removeRow($(obj).parents('tr:first'));
						var page = utilObj.getTablePage({
							tb:_this.storage.tb1,
							el:"#table1",
						})
						_this.loadUserList(page);
					}
				}
				_this.deleteUser(param);
			}
		})
		
	},
	deleteUser:function(param){
		var obj =this;
		ajax({
			url:"/m/userExtend/deleteUser",
			type: "POST",
			data:{
				userName:param.userName,
			},
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					param.callback();
//					$(".sui-pagination li").removeClass("active");
//					$(".sui-pagination li").eq(0).addClass("active");
//					obj.loadUserList(1);
				}
			}
		})
	},
	filterUserType:function(data){
		var role = utilObj.getDict().constant.Role;
		var obj = utilObj.changeRoleToObj(role);
		$.each(data, function(i,val) {
			val['userType'] = obj[val['userType']];
		});
		return data;
	},
	loadUserList:function(pageNum){
		_this = this;
		var d = utilObj.getQueryData("#query-user");
		var jsonFilter = {
			search_LIKE_name:d.name,
			search_EQ_userType:d.userType,
			search_NE_userType:'1',
		}	
		var param = {
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var op1={
					dataSpecial:{ 0:{ visible:true, class:'hide' },1:{ visible:true, class:'dt-name tdEllipsis' }  },
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.account_manage.goToPage_user"},
					dataTitles:['tid','账号','所属公司','联系电话','用户类型'],
					columns:['tid','name','company','contact','userType'],
					hasCheckBox:false,
					buttons:[{text:"修改信息",eventname:"_this.modifyAccountInfo",class:''},{text:"重置密码",eventname:"_this.resetPassword",class:''},{text:"删除",eventname:"_this.delete",class:'sui-text-danger'}],
					buttonsClass:'dt-op-lg',
				}
				var tb1 = _this.storage.tb1;
				var data = _this.filterUserType(d.content);
				if(!tb1){
					_this.storage.tb1 = new tableBuilder("#table1",data,op1);
				}else{
					tb1.resetData(data);
					tb1.paginationObj.updatePages(d.totalPages,pageNum);
				}
				
				
			}
		}
		if(pageNum){
			param.nextPage = pageNum-1;
		}
		page.service.getUserData(param);
	},
	goToPage_user:function(pageNum){
		this.loadUserList(pageNum);
	},
	init:function(){
		this.bindEvent();
		this.loadUserList();
		utilObj.loadUserType('userType');
	},
}
$(function(){
	page.account_manage.init();
})