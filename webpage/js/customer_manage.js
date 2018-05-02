page.customer_manage = {
	storage:{
		tb1:'',
	},
	bindEvent:function(){
		var _this = this;
		$("#btn_query1").click(function(){
			_this.loadCustomerList();
		})
		$("#btn_add1").click(function(){
			utilObj.navigate('customer_modify');
		})
		$("input[name=province]").change(function(){
			
			var city = $(this).val();
			$.when(page.service.getCity(city))
			.then(function(d){
				var $ul = $("input[name=city]").parents(".dropdown-inner").children("ul");
				utilObj.loadSelectOptions($ul,{
					text:'value',
					value:'code'
				},d);
				utilObj.toSelect('input[name=city]','');
			})
		})
	},
	checkInfo:function(obj){
		var flag = $(obj).hasClass("sui-text-grey");
		if(flag){
			utilObj.alert({
				body:"当天浏览次数已超出"
			});
			return;
		}
		var tid = utilObj.getTid(obj);
		utilObj.navigate('customer_detail',{
			check:tid,
		});
	},
	modify:function(obj){
		var tid = utilObj.getTid(obj);
		utilObj.navigate('customer_modify',{
			modify:tid,
		});
	},
	delete:function(obj){
		var _this = this;
		var tid = utilObj.getTid(obj);
		var param = {
			tid:tid,
			callback:function(){
				var page = utilObj.getTablePage({
					tb:_this.storage.tb1,
					el:"#table1",
				})
				_this.loadCustomerList(page);
			}
		}
		utilObj.confirm({
			body:'请确认是否删除该客户?',
			okHide:function(){
				_this.deleteCustomer(param);
			}
		})
		
	},
	deleteCustomer:function(param){
		ajax({
			url:"/m/customer/operateCustomerById",
			type: "POST",
			data:{
				modelId:param.tid,
				status:3
			},
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					param.callback();
				}
			}
		})
	},
	loadCustomerList:function(pageNum){
		_this = this;
		var d = utilObj.getQueryData("#query-customer");
		var jsonFilter = {
			search_LIKE_name:d.name,
			search_LIKE_manager:d.manager,
			search_EQ_province:d.province,
			search_EQ_city:d.city,
		}	
		var param = {
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d,xhr){
				$(".head-num").text(d.totalElements);
				var op1={
					dataSpecial:{ 0:{ visible:true, class:'hide' } , 1:{ visible:true, class:'dt-name tdEllipsis' }},
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.customer_manage.goToPage_customer"},
					dataTitles:['tid','客户名称','公司负责人','所在地区','创建人'],
					columns:['tid','name','manager','area','converted_createUser'],
					hasCheckBox:false,
					buttons:[{text:"查看",eventname:"_this.checkInfo",class:'sui-text-black'},{text:"修改",eventname:"_this.modify",class:''},{text:"删除",eventname:"_this.delete",class:'sui-text-danger'}],
				}
				
				$.each(d.content,function(){
					this.area="";
					if(this.converted_province && this.converted_city){
						this.area=this.converted_province + this.converted_city;
					}
				});
				
				var tb1 = _this.storage.tb1;
				if(!tb1){
					_this.storage.tb1 = new tableBuilder("#table1",d.content,op1);
				}else{
					tb1.resetData(d.content);
					tb1.paginationObj.updatePages(d.totalPages,pageNum);
				}
				
				var responseHeaderInfo = xhr.getResponseHeader( "usesParams" );
				
				utilObj.redrawByIsUsed("#table1",_this.storage.tb1,responseHeaderInfo);
				
				if(page.userRole!="1" && page.userRole!="2"){
					$.each($("#table1 tbody tr"), function() {
						if(page.userRole=="3" && $(this).find("td:eq(4) span").text().trim()==page.userName){
							return true;
						}
						$(this).find("td:eq(5) li:eq(1)").hide();
						$(this).find("td:eq(5) li:eq(2)").hide();
					});
				}
			}
		}
		if(pageNum){
			param.nextPage = pageNum-1;
		}
		page.service.getCustomerData(param);
	},
	goToPage_customer:function(pageNum){
		this.loadCustomerList(pageNum);
	},
	init:function(){
		var _this = this;
		this.bindEvent();
		$.when(page.service.getProvince())
		.then(function(d){
			var $ul = $("input[name=province]").parents(".dropdown-inner").children("ul");
			utilObj.loadSelectOptions($ul,{
				text:'value',
				value:'code'
			},d);
		})
		this.loadCustomerList();
		page.userRole=JSON.parse(sessionStorage.getItem("userinfo")).user.roleId;
		page.userName=JSON.parse(sessionStorage.getItem("userinfo")).user.userName;
		if(page.userRole!="4") $(".addbtn").show();
	}
}
$(function(){
	page.customer_manage.init();
})