page.login = {
	bindEvent:function(){
		sessionStorage.removeItem('t');
		var _this = this;
		$(".yzm-btn").click(function(){
			_this.getVerifyCode();
		})
	},
	init:function(){
		if(sessionStorage.getItem('message')){
			utilObj.alert({
				body:sessionStorage.getItem('message')
			})
			sessionStorage.removeItem('message');
		}
		this.bindEvent();
		this.validate();
		page.service.getConstant();
	},
	getVerifyCode:function(){
		$(".yzm-btn img").attr('src',utilObj.getApiHost().baseUrl+"/m/userExtend/requestVericode?"+(new Date()));
	},
	verifyVeriPic:function(yzm){
		var uuid = Cookies.getJSON('uuid');
		var flag = false;
		ajax({
			url:"/m/userExtend/verifyVeriPic",
			type:"POST",
			async:false,
			data:{
				uuid:uuid,
				code:yzm,
			},
			success:function(data){
				if(data.code==0){
					utilObj.alert("验证码不正确");
				}else{
					flag = true;
				}
			}
		})
		return flag;
	},
	validateUser:function(formData){
		var _this = this;
		var flag = false;
		ajax({
			url:"/m/cmsuser/validataUser",
			type:"POST",
			async:false,
			data:{
				userName:formData.account,
				pw:formData.pw,
			},
			success:function(data){
				if(data.errCode!=0){
					utilObj.alert({
						body:data.message
					});
					
					$(".login-yzm").show();
					_this.getVerifyCode();
					$("input[name=yzm]").removeClass('novalidate');
				}else{
					flag = true;
				}
			}
		})
		return flag;
	},
	validate:function(){
		var _this = this;
		$("#form-login").validate({
			rules:{
				account:{
					required:true,
				},
				pw:{
					required:true,
					password:true,
				},
				yzm:{
					required:true,
				},
			},
			success:function(){
				_this.signIn();
				return false;
			},
		})
	},
	signIn:function(){
		var _this = this;
		var uuid = Cookies.getJSON('uuid');
		var formData = utilObj.getFormData("#form-login");
		if(formData.yzm){
			var isContinue = _this.verifyVeriPic(formData.yzm);
			if(!isContinue){
				return;
			}
		}
		var isValid = _this.validateUser(formData);
		if(!isValid){
			return;
		}
		
		var data={
			j_username:formData.account,
			j_password:formData.pw,
		};
		if(formData.yzm){
			data.uuid=uuid;
		}
		
		ajax({
			url:"/j_spring_security_check",
			type:"POST",
			data:data,
			beforeSend: function (xhr) {
				xhr.setRequestHeader("X-Ajax-call", "true");
			},
			success:function(data){
				var response = JSON.parse(data);
				if(response.authentication.authenticated==false){
//					utilObj.alert({
//						body:'用户名或密码错误'
//					})
//					_this.getVerifyCode();
//					$(".yzm-group").show();
//					$("input[name=yzm]").removeClass('novalidate');
					return;
				}
				sessionStorage.setItem('t',response.token);
				sessionStorage.setItem('userinfo',data);
				if(response.userExtend && response.userExtend.passwordUpdateTimes==0){
					sessionStorage.setItem('needchangepwd',"true");
					utilObj.navigate('change_password');
				}else{
					sessionStorage.removeItem("needchangepwd");
					utilObj.navigate('index');
				}

		
			},
			fail:function(data){
				alert("fail");
				utilObj.alert({
					body:"服务器出错了"
				})
			},
			error:function (data, status, e){
				utilObj.alert({
					body:"服务器出错了"
				})
//				alert(JSON.stringify(data));
//				alert(JSON.stringify(status));
//				alert(JSON.stringify(e));
			}
		});
	}
}
$(function(){
	seajs.use('cookie',function(){
		page.login.init();
	})
	$("form").submit(function(e){
		e.preventDefault();
	})
})
