page.changepwd=function(){
	var setting = {
		url:"/m/userExtend/modifyUser",
		type: "POST",
		data:utilObj.getFormData(".sui-form"),
		success: function(data){
			if(data.code==0){
				//utilObj.alert({body:data.message});
			}else{
				sessionStorage.removeItem("needchangepwd");
				utilObj.alert({
					body:"密码修改成功",
					okHidden:function(){
						utilObj.navigate('index');
					}
				});
			}
		}
	}
	setting.data.userType=JSON.parse(sessionStorage.getItem("userinfo")).user.userType;
	ajax(setting);
}
$(function(){
	$("form").submit(function(e){
		e.preventDefault();
	})
	
	$(".sui-form").validate({
		rules:{
			userName:{
				required:true,
			},
			newPassword:{
				required:true,
				password:true,
			},
			newPassword2:{
				match:'newPassword',
				required:true,
				password:true,
			},
		},
		messages:{
			newPassword2:['请填写','请与新密码保持一致']
		},
		success:function(){
			page.changepwd();
		},
	})
})
