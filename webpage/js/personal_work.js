page.personal_work = {
	init:function(){
		var _this = this;
		var userName = utilObj.userInfo.user.userName;
		$.when(utilObj.loadUserType('userType'))
		.then(this.bindUserData(userName))
		this.validate_user();
	},
	transferUserType:function(data){
		var obj = JSON.parse(sessionStorage.getItem('constant'));
		var userType = obj.constant.Role;
		var userTypeObj = utilObj.changeRoleToObj(userType);
		var userTypeName = userTypeObj[data];
		return userTypeName?userTypeName:'';
	},
	bindUserData:function(userName){
		var _this = this;
		var param = {
			userName:userName,
			callback:function(d){
				var data = d;
				data.userName = data.username;
				data.userTypeText = _this.transferUserType(data.userType);
				utilObj.bindData(".form-user",data);
			}
		}
		_this.getUser(param);
	},
	getUser:function(param){
		ajax({
			url:"/m/userExtend/findUser",
			type: "POST",
			data:{
				userName:param.userName,
			},
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					var d = data.object;
					param.callback(d);
				}
			}
		})
	},
	validate_user:function(){
		var _this = this;
		$(".form-user").validate({
			rules:{
//				userName:{
//					required:true,
//				},
				password:{
					required:true,
//					password:true,
				},
				newPassword:{
					required:true,
					password:true,
				},
				repeatPassword:{
					match:'newPassword',
					required:true,
					
				},
//				company:{
//					
//				},
//				contact:{
//					
//				},
//				userType:{
//					required:true,
//				},
			},
			messages:{
				repeatPassword:['请填写','请与新密码保持一致']
			},
			success:function(){
				_this.modifyUser();
				return false;
			},
		})
	},
	modifyUser:function(){
		var _this = this;
		var param = utilObj.getFormData(".form-user");
		delete param.repeatPassword;
		delete param.userTypeText;
		var setting = {
			url:"/m/userExtend/modifyUser",
			type: "POST",
			data:param,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
					utilObj.showSuccessMessage('保存成功');
				}
			}
		}
		ajax(setting);
	},

}
$(function(){
	page.personal_work.init();
})