page.customer_modify = {
	storage:{
		customerId:'',
		tb1:'',
		tb2:'',
	},
	bindEvent:function(){
		var _this = this;
		$("#btn_add1").click(function(){
			if(!_this.storage.customerId){
				utilObj.alert({
					body:'请先新建客户'
				})
				return;
			}
			if(utilObj.getTableLength("#table1")>=5){
				utilObj.alert({
					body:'最多添加5个联系人'
				})
				return;
			}
			_this.addContactor();
		})
		$("#btn_add2").click(function(){
			if(!_this.storage.customerId){
				utilObj.alert({
					body:'请先新建客户'
				})
				return;
			}
			_this.buildModifyWorkModal();
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
	validate:function(){
		var _this = this;
		$(".form-customer-info").validate({
			rules:{
				name:{
					required:true,
					maxlength:20,
				},
				manager:{
					maxlength:20,
				},
				comment:{
					maxlength:10
				},
				serviceArea:{
					maxlength:10
				},
				serviceObject:{
					maxlength:10
				},
				intro:{
					maxlength:50
				},
				email:{
					email:true,
				},
				contact:{
					
				},
				
			},
			success:function(){
				_this.modifyCustomer();
				return false;
			},
		})
	},
	validate_contactor:function(tid){
		var _this = this;
		$(".form-contactor").validate({
			rules:{
				name:{
					required:true,
				},
				contact:{
					
				},
				email:{
					email:true,
				},
			},
			success:function(){
				_this.modifyContactor(tid);
				return false;
			},
		})
	},
	bindCustomerData:function(){
		var customerId = this.storage.customerId;
		if(!customerId){
			return;
		}
		var _this = this;
		var jsonFilter = {
			search_EQ_tid:customerId,
		}
		var param = {
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var data = d.content[0];
				utilObj.bindData(".form-customer-info",data,{
					multi_input:'socialFunction'
				});
				
				var city = $("input[name=province]").val();
				$.when(page.service.getCity(city))
				.then(function(d){
					var $ul = $("input[name=city]").parents(".dropdown-inner").children("ul");
					utilObj.loadSelectOptions($ul,{
						text:'value',
						value:'code'
					},d);
					utilObj.toSelect('input[name=city]',data.city);
				})
			}
		}
		page.service.getCustomerData(param);
	},
	modifyCustomer:function(){
		var _this = this;
		var customerId = this.storage.customerId;
		var data = utilObj.getFormData(".form-customer-info");
		data.birth = utilObj.fillDate(data.birth);
		var setting = {
			type: "POST",
			data:data,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
					if(!_this.storage.customerId){
						$(".form-customer-info button[type=submit]").hide();
						utilObj.alert({
							body:'保存成功,请继续添加联系人等其他信息'
						})
					}else{
						utilObj.showMessage({
							type:utilObj.showMessageType['msg-success'],
							body:'保存成功'
						});
					}
					_this.storage.customerId = data.object.tid;
				}
			}
		}
		if(customerId){
			setting.url = "/m/customer/updateCustomer";
			setting.data.tid = customerId;
		}else{
			setting.url = "/m/customer/saveCustomer";
		}
		ajax(setting);
	},
	addContactor:function(tid){
		var _this = this;
		var title = '联系人信息添加';
		if(tid){
			title = '联系人信息修改';
		}
		var setKeyVal ={
			title:title,
			shown:function(){
				if(tid){
					_this.bindContactorData(tid);
				}
				_this.validate_contactor(tid);
			}
		}
		modalBulider.modal('template1',setKeyVal);
	},
	modifyContactor:function(tid){
		var _this = this;
		var param = utilObj.getFormData(".form-contactor");
		param.customerId = this.storage.customerId;
		var setting = {
			url:"/m/contactor/saveContactor",
			type: "POST",
			data:param,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
					_this.loadContactorList(1);
					$("#myModal1").modal('hide');
				}
			}
		}
		if(tid){
			setting.url = "/m/contactor/updateContactor";
			setting.data.tid = tid;
		}
		ajax(setting);
	},
	bindContactorData:function(tid){
		var _this = this;
		var jsonFilter = {
			search_EQ_tid:tid,
		}
		var param = {
			customerId:_this.storage.customerId,
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var data = d.content[0];
				utilObj.bindData(".form-contactor",data);
			}
		}
		page.service.getContactorData(param);
	},
	delete_contactor:function(obj){
		var _this = this;
		var tid = utilObj.getTid(obj);
		utilObj.confirm({
			body:'请确认是否删除该联系人?',
			okHide:function(){
				var param = {
					tid:tid,
					callback:function(){
						_this.storage.tb1.removeRow($(obj).parents('tr:first'));
					}
				}
				_this.deleteContactor(param);
			}
		})
	},
	modify_contactor:function(obj){
		var tid = utilObj.getTid(obj);
		this.addContactor(tid);
	},
	loadContactorList:function(){
		var customerId = this.storage.customerId;
		_this = this;
		var jsonFilter = {
			search_EQ_customerId:customerId,
		}	
		var param = {
			customerId:customerId,
			jsonFilter:JSON.stringify(jsonFilter),
			pageSize:pageSetting.Pagination_MaxPage,
			callback:function(d){
				var op1={
					dataSpecial:{ 0:{ visible:true, class:'hide' } },
					dataTitles:['tid','其他联系人','联系方式','微信','邮箱',],
					columns:['tid','name','contact','wechat','email',],
					buttons:[
					{text:"修改",eventname:"_this.modify_contactor",class:''},
					{text:"删除",eventname:"_this.delete_contactor",class:'sui-text-danger'}
					],
					hasCheckBox:true,
				}
				var tb1 = _this.storage.tb1;
				var data1 = utilObj.filterDate(d.content,'birth');
				if(!tb1){
					
					_this.storage.tb1 = new tableBuilder("#table1",data1,op1);
					utilObj.bindDeleteEvent(_this.storage.tb1,'#btn_remove1',{
						confirm_option:{
							body:'请确认是否删除该联系人?',
							okHide:function(){
								var tid = _this.storage.tb1.getCheckedRowTids();
								var param = {
									tid:tid,
									callback:function(){
										_this.storage.tb1.removeRows();
									}
								}
								_this.deleteContactor(param);
							}
						}
					});
				}else{
					tb1.resetData(data1);
				}
				
				
			}
		}
		page.service.getContactorData(param);
	},
	deleteContactor:function(param){
		if(utilObj.isAry(param.tid)){
			$.each(param.tid,function(i,val){
				ajax({
					url:"/m/contactor/operateContactorById",
					type: "POST",
					data:{
						modelId:val,
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
			})
			
		}else{
			ajax({
				url:"/m/contactor/operateContactorById",
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
		}
		
		
	},
	bindEventForModalWork:function(){
		$("#btn_add_tp2_1").click(function(){
			utilObj.addPhotoUploadLi(".ul-modal-work");
		})
		$("#btn_remove_tp2_1").click(function(){
			utilObj.confirm({
				body:'请确认是否删除该照片?',
				okHide:function(){
					utilObj.deletePhotoUploadLi(".ul-modal-work");
				}
			})
			
		})
		utilObj.bindUploadPhotoEvent(".ul-modal-work");
	},
	delete_work:function(obj){
		var _this = this;
		var tid = utilObj.getTid(obj);
		utilObj.confirm({
			body:'请确认是否删除该合作记录?',
			okHide:function(){
				var param = {
					tid:tid,
					callback:function(){
						var page = utilObj.getTablePage({
							tb:_this.storage.tb2,
							el:"#table2",
							tid:tid,
						})
						_this.loadWorkList(page);
					}
				}
				_this.deleteWork(param);
			}
		})
	},
	modify_work:function(obj){
		var tid = utilObj.getTid(obj);
		this.buildModifyWorkModal(tid);
	},
	check_work:function(obj){
		var tid = utilObj.getTid(obj);
		this.showWork(tid);
	},
	loadWorkList:function(pageNum){	
		var customerId = this.storage.customerId;
		_this = this;
		var jsonFilter = {
			search_EQ_customerId:customerId,
		}
		var param = {
			customerId:customerId,
			pageSize:5,
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var op={
					dataSpecial:{ 
						0:{ 
							visible:true, 
							class:'hide' 
						},
						2:{
							visible:true,
							render: function(data, type, row, meta){
								var h = '<span dataRow="'+meta.row+'"  dataCol="'+meta.col+'" title="'+data+'">';
								if(row.expertId){
									h += '<a class="expert-name" href="javascript:void(0)" data-id="'+row.expertId+'">';
								}
								h += data;
								if(row.expertId){
									h += '</a>'
								}
								h += '</span>';
						    	return h;
						    }
						},
						8:{
							visible:false
						}
					},
					dataTitles:['tid','对接人','专家名称','活动时间','活动地点','合同价(万元)','评价','创建人'],
					columns:['tid','linker','expertName','actionDate','actionAddress','contractPrice','comment','converted_createUser','expertId'],
					hasCheckBox:false,
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.customer_modify.goToPage_work"},
					buttons:[
					{text:"查看",eventname:"_this.check_work",class:'sui-text-black'},
					{text:"修改",eventname:"_this.modify_work",class:''},
					{text:"删除",eventname:"_this.delete_work",class:'sui-text-danger'}
					],
//					columnsExt: [{
//						title: "创建人",
//						class: 'bszk-table-input',
//						render: function(data, type, row, meta) {
//							return '<input type="text" value="" class="input-medium" >';
//						}
//					}],
				}
				var tb2 = _this.storage.tb2;
				var data = utilObj.filterDate(d.content,'actionDate');
				if(!tb2){
					
					_this.storage.tb2 = new tableBuilder("#table2",data,op);
//					utilObj.bindDeleteEvent(_this.storage.tb2,'#btn_remove2',{
//						confirm_option:{
//							body:'请确认是否删除该合作记录?',
//							okHide:function(){
//								var tid = _this.storage.tb2.getCheckedRowTids();
//								var param = {
//									tid:tid,
//									callback:function(){
//										var page = utilObj.getTablePage({
//											tb:_this.storage.tb2,
//											el:"#table2",
//											tid:tid,
//										})
//										_this.loadWorkList(page);
//									}
//								}
//								_this.deleteWork(param);
//							}
//						}
//					});
				}else{
					tb2.resetData(data);
					tb2.paginationObj.updatePages(d.totalPages,pageNum);
				}
				if(page.userRole!="1" && page.userRole!="2"){
					$.each($("#table2 tbody tr"), function() {
						if($(this).find("td:eq(7) span").text().trim()==page.userName){
							return true;
						}
						$(this).find("td.dt-op li:eq(1)").hide();
						$(this).find("td.dt-op li:eq(2)").hide();
					});
				}
				
			}
		}
		if(pageNum){
			param.nextPage = pageNum-1;
		}
		page.service.getWorkData(param);
	},
	deleteWork:function(param){
		if(utilObj.isAry(param.tid)){
			$.each(param.tid,function(i,val){
				ajax({
					url:"/m/work/operateWorkById",
					type: "POST",
					async:false,
					data:{
						modelId:val,
						status:3
					},
					success: function(data){
						if(data.code==0){
							//utilObj.alert({body:data.message});
						}else{
							
						}
					}
				})
			})
			param.callback();
		}else{
			ajax({
				url:"/m/work/operateWorkById",
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
		}
		
		
	},
	buildModifyWorkModal:function(tid){
		var _this = this;
		var title = '合作记录添加';
		if(tid){
			title = '合作记录修改';
		}
		var setKeyVal ={
			title:title,
			shown:function(){
				if(tid){
					_this.bindWorkData(tid);
				}
				_this.bindEventForModalWork();
				$(".form-work").validate({
					rules:{
						linker:{
							required:true,
							maxlength:20,
						},
						expertName:{
							required:true,
							maxlength:10,
						},
						contractPrice:{
							digits:true,
							gt:0,
						},
						actionAddress:{	
							maxlength:5,
						},
						comment:{	
							maxlength:5,
						},
					},
					success:function(){
						_this.modifyWork(tid);
						return false;
					}
				})
			}
		}
		modalBulider.modal('template2',setKeyVal);
	},
	modifyWork:function(tid){
		var _this = this;
		var param = utilObj.getFormData(".form-work");
		param.customerId = this.storage.customerId;
		param.actionDate = utilObj.fillDate(param.actionDate);
		var setting = {
			url:"/m/work/saveWork",
			type: "POST",
			data:param,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
					var page = utilObj.getTablePage({
						tb:_this.storage.tb2,
					})
					_this.loadWorkList(page);
					$("#myModal2").modal('hide');
				}
			}
		}
		if(tid){
			setting.url = "/m/work/updateWork";
			setting.data.tid = tid;
		}
		ajax(setting);
	},
	showWork:function(tid){
		var _this = this;
		var setKeyVal ={
			title:'合作记录详情',
			shown:function(){
				_this.bindWorkShowData(tid);
			}
		}
		modalBulider.modal('template3',setKeyVal);
	},
	bindWorkShowData:function(tid){
		var _this = this;
		var jsonFilter = {
			search_EQ_tid:tid,
		}
		var param = {
			customerId:_this.storage.customerId,
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var data = d.content[0];
				data.actionDate = utilObj.shortenDate(data.actionDate);
				utilObj.bindShowData(".show-modal-work",data);
				utilObj.bindShowPhotosData(data.pics,'.show-modal-work .photo-wrapper');
				modalBulider.resize();
			}
		}
		page.service.getWorkData(param);
	},
	bindWorkData:function(tid){
		var _this = this;
		var jsonFilter = {
			search_EQ_tid:tid,
		}
		var param = {
			customerId:_this.storage.customerId,
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var data = d.content[0];
				utilObj.bindData(".form-work",data,{
					except:['pics'],
				});
				utilObj.bindUploadPhotosData(data.pics,'.ul-modal-work');
			}
		}
		page.service.getWorkData(param);
	},
	goToPage_work:function(pageNum){
		this.loadWorkList(pageNum);
	},
	changeTitle:function(id){
		if(id){
			$(".sui-breadcrumb li.active").text('客户修改');
			$(".bszk-title").text('客户修改');
		}else{
			$(".sui-breadcrumb li.active").text('客户添加');
			$(".bszk-title").text('客户添加');
		}
	},
	init:function(){
		this.bindEvent();
		this.validate();
		
		this.storage.customerId = utilObj.getUrlParam('modify')
		this.changeTitle(this.storage.customerId);
		this.loadContactorList();
		this.loadWorkList();
		this.bindCustomerData()
		page.userRole=JSON.parse(sessionStorage.getItem("userinfo")).user.roleId;
		page.userName=JSON.parse(sessionStorage.getItem("userinfo")).user.userName;
		$.when(page.service.getProvince())
		.then(function(d){
			var $ul = $("input[name=province]").parents(".dropdown-inner").children("ul");
			utilObj.loadSelectOptions($ul,{
				text:'value',
				value:'code'
			},d);
		})
		
	},
}
$(function(){
	page.customer_modify.init();
})

