page.customer_detail = {
	storage:{
		customerId:'',
		tb1:'',
		tb2:'',
	},
	bindEvent:function(){
		var _this = this;
		$("#btn_add2").click(function(){
			var tb2 = _this.storage.tb2;
			var tid = '';
			if(tb2.getCheckedRowData()){
				tid = tb2.getCheckedRowData().tid;
			}	
			_this.buildModifyWorkModal(tid);
		})
//		$("#table2").on('click','.checkbox-pretty span',function(e){
//			e.stopImmediatePropagation();
//		})
//		$("#table2").on('click','tr',function(){
//			var tid = _this.storage.tb2.getSelectedRow(this).tid;
//			_this.showWork(tid);
//		});
		$("#btn_export_all1").click(function(){
			_this.exportAll();
		})
	},
	exportAll:function(){
		var _this = this;
		
		var jsonFilter = {
			search_EQ_expertId:_this.storage.customerId,
		}
		utilObj.exportBrowse(JSON.stringify(jsonFilter))
//		var param = {
//			jsonFilter:JSON.stringify(jsonFilter),
//			pageSize:pageSetting.Pagination_MaxPage,
//			callback:function(d){
//				var title = {
//					converted_createUser:'账号',
//					createTime:'时间',
//				}
//				utilObj.exportExcel(title,d.content);
//			}
//		}
//		page.service.getBrowseData(param);
	},
	bindCustomerShowData:function(){
		var customerId = this.storage.customerId;
		if(!customerId){
			return;
		}
		var _this = this;
		var param = {
			customerId:customerId,
			callback:function(d){
				var data = d;
				data.districtDisplay = data.converted_province+data.converted_city;
				utilObj.bindShowData(".show-customer",data);
			}
		}
		page.service.getCustomerDataById(param);
	},
	loadContactorList:function(){
		var customerId = this.storage.customerId;
		var _this = this;
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
					hasCheckBox:false,
				}
				var tb1 = _this.storage.tb1;
				if(!tb1){
					var data1 = utilObj.filterDate(d.content,'birth');
					_this.storage.tb1 = new tableBuilder("#table1",data1,op1);
					utilObj.bindDeleteEvent(_this.storage.tb1,'#btn_remove1',{
						confirm_option:{
							body:'请确认是否删除该联系人?',
							okHide:function(){
								var tid = _this.storage.tb1.getCheckedRowData().tid;
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
					tb1.resetData(d.content);
				}
				
				
			}
		}
		page.service.getContactorData(param);
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
			jsonFilter:JSON.stringify(jsonFilter),
			pageSize:5,
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
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.customer_detail.goToPage_work"},
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
		var setKeyVal ={
			title:'合作记录添加/修改',
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
	loadBrowseList:function(pageNum){
		var customerId = this.storage.customerId;
		var _this = this;
		var jsonFilter = {
			search_EQ_expertId:customerId,
		}
		var param = {
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var data = d.content;
				var length = data.length;
				var half = Math.ceil(length/2);
				var data1 = data.slice(0,half);
				var data2 = data.slice(half);
				//第一个table
				var op1={
					dataSpecial:{ 0:{ visible:true, class:'hide' } },
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.customer_detail.goToPage_browse"},
					dataTitles:['tid','账号','时间',],
					columns:['tid','userName','btime',],
					hasCheckBox:true,
				}
				
				var tb4_1 = _this.storage.tb4_1;
				if(!tb4_1){	
					_this.storage.tb4_1 = new tableBuilder("#table4-1",data1,op1);
					$("#table4-1").children().eq(1).css("transform","translateX(50%)");
				}else{
					tb4_1.resetData(data1);
					tb4_1.paginationObj.updatePages(d.totalPages,pageNum);
				}
				//第二个table
				var op2={
					dataSpecial:{ 0:{ visible:true, class:'hide' } },
					dataTitles:['tid','账号','时间',],
					columns:['tid','userId','btime',],
					hasCheckBox:true,
				}
				
				var tb4_2 = _this.storage.tb4_2;
				if(!tb4_2){	
					_this.storage.tb4_2 = new tableBuilder("#table4-2",data2,op2);
				}else{
					tb4_2.resetData(data2);
				}
				
				
			}
		}
		if(pageNum){
			param.nextPage = pageNum-1;
		}
		page.service.getBrowseData(param);
	},
	goToPage_browse:function(pageNum){
		this.loadBrowseList(pageNum);
	},
	init:function(){
		this.storage.customerId = utilObj.getUrlParam('check')
		this.bindEvent();
		this.bindCustomerShowData();
		this.loadContactorList();
		this.loadWorkList();
		this.loadBrowseList();
		page.userRole=JSON.parse(sessionStorage.getItem("userinfo")).user.roleId;
		page.userName=JSON.parse(sessionStorage.getItem("userinfo")).user.userName;
	}
}
$(function(){
	seajs.use('table2excel',function(){
		page.customer_detail.init();
	})
	
})
