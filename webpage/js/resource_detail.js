page.resource_detail = {
	storage:{
		expertId:'',
		tb1:'',
		tb2:'',
		tb3:'',
		tb4_1:'',
		tb4_2:'',
	},
	bindEvent:function(){
		var _this = this;
		$("#btn_add1").click(function(){
			_this.addAssitant();
		})
//		$("#btn_add4").click(function(){
//			_this.addEvaluation();
//		})
		$("#btn_add3").click(function(){
			var tb3 = _this.storage.tb3;
			var tid = '';
			if(tb3.getCheckedRowData()){
				tid = tb3.getCheckedRowData().tid;
			}	
			_this.buildModifyCooperateModal(tid);
		})
		$(".cooperate-actiondate").on('change',function(){
			var date = $(this).val();
			$(this).val($(this).val().substring(0,4));
			_this.loadCooperateChart(date);
		})
		$("#btn_export_all1").click(function(){
			_this.exportAll();
		})
		$(".download-link").click(function(){
			var _this = this;
			var picPath = $(this).data('href');
			if(picPath){
				var filename = $(this).siblings('span[data-name="fileName"]').text();
				picPath = utilObj.fillPicPath(picPath);
				
				utilObj.downloadAll(picPath,filename);
			}
		})
		
	},
	bindEventForModalCooperate:function(){
		$("#btn_add_tp2_1").click(function(){
			utilObj.addPhotoUploadLi(".ul-modal-cooperate");
		})
		$("#btn_remove_tp2_1").click(function(){
			utilObj.deletePhotoUploadLi(".ul-modal-cooperate");
		})
		utilObj.bindUploadPhotoEvent(".ul-modal-cooperate");
	},
	exportAll:function(){
		var _this = this;
		
		var jsonFilter = {
			search_EQ_expertId:_this.storage.expertId,
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
	loadExpertCategory:function(category,otherCategory){
		var data = utilObj.parseStr(category);
		
		var _this = this;
		var param = {
			callback:function(d){
				var obj = utilObj.changeCategoryToObj(d.content);
				var h = '';
				$.each(data,function(i,val){
					h += '<button class="sui-btn btn-primary btn-sm-round no-hover">'+obj[val]+'</button>';
				})
				if(otherCategory){
					h += '<button class="sui-btn btn-primary btn-sm-round no-hover">'+otherCategory+'</button>';
				}
				$(".show-category").html(h);
			}
		}
		page.service.getExpertCategoryData(param);
		
	},
	loadExpertSocialFunction:function(data,container){
		data = utilObj.parseStr(data);
		var h = '';
		if(data.length<=0){
			$(container).before("暂无");
			$(container).remove();
		}else{
			$.each(data,function(i,val){
				if(val && val.indexOf('    ')==-1){
					h += '<li class="data-li-num">'+val+'</li>';
				}
				
			});
			$(container).html(h);
		}
		
	},
	bindExpertShowData:function(){
		var expertId = this.storage.expertId;
		if(!expertId){
			return;
		}
		var _this = this;
		var param = {
			expertId:expertId,
			callback:function(d){
				var data = d;
//				data.fileName = utilObj.getFileName(data.filePath);
				data.districtDisplay = data.converted_province+data.converted_city;
				utilObj.bindShowData(".show-expert",data);
				_this.loadExpertCategory(data.category,data.otherCategory);
				_this.loadExpertSocialFunction(data.socialFunction,'.show-socialfunction');
				$(".download-link").attr('data-href',data.filePath);
				if(!data.filePath){
					$(".download-link").parents("li:first").hide();
				}
			}
		}
		page.service.getExpertDataById(param);
	},
	loadAssistantList:function(){
		var expertId = this.storage.expertId;
		var _this = this;
		var param = {
			expertId:expertId,
			callback:function(d){
				var op1={
					dataSpecial:{ 0:{ visible:true, class:'hide' } },
					dataTitles:['tid','助手姓名','出生日期','联系方式','微信','性别','证件号','邮箱',],
					columns:['tid','name','birth','contact','wechat','converted_gender','certificate','email',],
				}
				var tb1 = _this.storage.tb1;
				if(!tb1){
					var data1 = utilObj.filterDate(d.content,'birth');
					_this.storage.tb1 = new tableBuilder("#table1",data1,op1);
				}else{
					tb1.resetData(d.content);
				}
				
				
			}
		}
		page.service.getAssistantData(param);
	},
	loadPicsShowList:function(){
		var expertId = this.storage.expertId;
		var _this = this;
		var jsonFilter = {
			search_EQ_expertId:expertId,
		}
		var param = {
			expertId:expertId,
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var h = '';
				var pics = d.content;
				$.each(pics,function(i,val){
					if(val.pic){
						h += 			'<div class="photo-group">\
										<div class="photo-show"></div>\
										<button class="sui-btn btn-success btn-download" data-href="'+val.pic+'">下载原图</button>\
									</div>';	
					}
					
				})
				$(".photo-wrapper").html(h);
				utilObj.showPhoto(".photo-wrapper",'.photo-show');
				
			}
		}
		page.service.getPicsData(param);
	},
	loadLectureContentList:function(){
		var expertId = this.storage.expertId;
		var _this = this;
		var param = {
			expertId:expertId,
			callback:function(d){
				var h = '';
				$.each(d.content,function(){
					h += '<li class="clearfix content-li"><div class="mic-icon"></div><span>'+this.content+'</span></li>';
				})
				$('.content-wrapper').html(h);
			}
		}
		page.service.getLectureContentData(param);
	},
	check_cooperate:function(obj){
		var _this = this;
		var tid = utilObj.getTid(obj);
		_this.showCooperate(tid);
	},
	modify_cooperate:function(obj){
		var _this = this;
		var tid = utilObj.getTid(obj);
		_this.buildModifyCooperateModal(tid);
	},
	delete_cooperate:function(obj){
		var _this = this;
		var tid = utilObj.getTid(obj);
		utilObj.confirm({
			body:'请确认是否删除该评价?',
			okHide:function(){
				var param = {
					tid:tid,
					callback:function(){
						var page = utilObj.getTablePage({
							tb:_this.storage.tb3,
							el:"#table3",
							tid:tid,
						})
						_this.loadCooperateList(page);
					}
				}
				_this.deleteCooperate(param);
			}
		})
		
	},
	loadCooperateList:function(pageNum){	
		var expertId = this.storage.expertId;
		_this = this;
		var jsonFilter = {
			search_EQ_expertId:expertId,
		}
		var param = {
			expertId:expertId,
			pageSize:5,
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var op={
					dataSpecial:{ 
						0:{ 
							visible:true, 
							class:'hide' 
						},
						1:{
							visible:true,
							render: function(data, type, row, meta){
								var h = '<span dataRow="'+meta.row+'"  dataCol="'+meta.col+'" title="'+data+'">';
								if(row.customerId){
									h += '<a class="customer-name" href="javascript:void(0)" data-id="'+row.customerId+'">';
								}
								h += data;
								if(row.customerId){
									h += '</a>'
								}
								h += '</span>';
						    	return h;
						    }
						},
						9:{
							visible:false
						}
					},
					dataTitles:['tid','客户名称','活动时间','活动地址','合同价(万元)','成本价(万元)','专家联系人','评价','创建人',],
					columns:['tid','customerName','actionDate','actionAddress','contractPrice','costPrice','expertContactor','comment','converted_createUser','customerId'],
					hasCheckBox:false,
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.resource_detail.goToPage_cooperate"},
					buttons:[
					{text:"查看",eventname:"_this.check_cooperate",class:'sui-text-black'},
					{text:"修改",eventname:"_this.modify_cooperate",class:''},
					{text:"删除",eventname:"_this.delete_cooperate",class:'sui-text-danger'}
					],
				}
				var tb3 = _this.storage.tb3;
				var data = utilObj.filterDate(d.content,'actionDate');
				data = utilObj.addSerial(data);
				if(!tb3){
					
					_this.storage.tb3 = new tableBuilder("#table3",data,op);
//					utilObj.bindDeleteEvent(_this.storage.tb3,'#btn_remove3',{
//						confirm_option:{
//							body:'请确认是否删除该评价信息?',
//							okHide:function(){
//								var tid = _this.storage.tb3.getCheckedRowTids();
//								var param = {
//									tid:tid,
//									callback:function(){
//										var page = utilObj.getTablePage({
//											tb:_this.storage.tb3,
//											el:"#table3",
//											tid:tid,
//										})
//										_this.loadCooperateList(page);
//									}
//								}
//								_this.deleteCooperate(param);
//							}
//						}
//					});
				}else{
					tb3.resetData(data);
					tb3.paginationObj.updatePages(d.totalPages,pageNum);
				}
				
				if(page.userRole!="1" && page.userRole!="2"){
					$.each($("#table3 tbody tr"), function() {
						if($(this).find("td:eq(8) span").text().trim()==page.userName){
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
		page.service.getCooperateData(param);
	},
	loadCooperateChart:function(date){
		var expertId = this.storage.expertId;
		if(date){
			date = date.slice(0,4);
		}
		var _this = this;
		var jsonFilter = {
			search_EQ_expertId:expertId,
			search_LIKE_actionDate:date
		}
		var sortData =[
		{
			property:'actionDate',direction:'ASC'
		}
		]
		var param = {
			expertId:expertId,
			jsonFilter:JSON.stringify(jsonFilter),
			pageSize:pageSetting.Pagination_MaxPage,
			sortData:JSON.stringify(sortData),
			callback:function(d){
				page.service.buildCooperateChart(d.content);
			}
		}
		page.service.getCooperateData(param);
	},
	deleteCooperate:function(param){
		var _this = this;
		if(utilObj.isAry(param.tid)){
			var total = param.tid.length;
			$.each(param.tid,function(i,val){
				ajax({
					url:"/m/cooperate/operateCooperateById",
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
							if((i+1)==total){
								$(".cooperate-actiondate").change();
							}
						}
					}
				})
			})
			
		}else{
			ajax({
				url:"/m/cooperate/operateCooperateById",
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
						$(".cooperate-actiondate").change();
					}
				}
			})
		}
		
		
	},
	buildModifyCooperateModal:function(tid){
		var _this = this;
		var setKeyVal ={
			title:'配合度评价添加/修改',
			shown:function(){
				if(tid){
					_this.bindCooperateData(tid);
				}
				_this.bindEventForModalCooperate();
				$(".form-cooperate").validate({
					rules:{
						customerName:{
							required:true,
							maxlength:15,
						},
						actionDate:{
							required:true,
						},
						contractPrice:{
							required:true,
							digits:true,
							gt:0,
						},
						costPrice:{
							required:true,
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
						_this.modifyCooperate(tid);
						return false;
					}
				})
			}
		}
		modalBulider.modal('template2',setKeyVal);
	},
	modifyCooperate:function(tid){
		var _this = this;
		var param = utilObj.getFormData(".form-cooperate");
		param.expertId = this.storage.expertId;
		param.actionDate = utilObj.fillDate(param.actionDate);
		var setting = {
			url:"/m/cooperate/saveCooperate",
			type: "POST",
			data:param,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
					_this.loadCooperateList(1);
					$(".cooperate-actiondate").change();
					$("#myModal2").modal('hide');
				}
			}
		}
		if(tid){
			setting.url = "/m/cooperate/updateCooperate";
			setting.data.tid = tid;
		}
		ajax(setting);
	},
	showCooperate:function(tid){
		var _this = this;
		var setKeyVal ={
			title:'配合度评价详情',
			shown:function(){
				_this.bindCooperateShowData(tid);
			}
		}
		modalBulider.modal('template3',setKeyVal);
	},
	bindCooperateShowData:function(tid){
		var _this = this;
		var jsonFilter = {
			search_EQ_tid:tid,
		}
		var param = {
			expertId:_this.storage.expertId,
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var data = d.content[0];
				data.actionDate = utilObj.shortenDate(data.actionDate)
				utilObj.bindShowData(".show-modal-cooperate",data);
				utilObj.bindShowPhotosData(data.pics,'.show-modal-cooperate .photo-wrapper');
				modalBulider.resize();
			}
		}
		page.service.getCooperateData(param);
	},
	bindCooperateData:function(tid){
		var _this = this;
		var jsonFilter = {
			search_EQ_tid:tid,
		}
		var param = {
			expertId:_this.storage.expertId,
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var data = d.content[0];
				utilObj.bindData(".form-cooperate",data,{
					except:['pics'],
				});
				utilObj.bindUploadPhotosData(data.pics,'.ul-modal-cooperate');
			}
		}
		page.service.getCooperateData(param);
	},
	goToPage_cooperate:function(pageNum){
		this.loadCooperateList(pageNum);
	},
	loadBrowseList:function(pageNum){
		var expertId = this.storage.expertId;
		var _this = this;
		var jsonFilter = {
			search_EQ_expertId:expertId,
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
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.resource_detail.goToPage_browse"},
					dataTitles:['tid','账号','时间',],
					columns:['tid','converted_userName','btime',],
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
					columns:['tid','converted_userName','btime',],
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
	saveBrowse:function(){
		var _this = this;
		var param = {};
		param.userId = utilObj.userInfo.user.userId;
		param.expertId = this.storage.expertId;
		param.btime = moment().format("YYYY-MM-DD HH:mm:ss");
		var setting = {
			url:"/m/browse/saveBrowse",
			type: "POST",
			data:param,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
					_this.loadBrowseList(1);
				}
			}
		}
		ajax(setting);
	},
	init:function(){
		this.storage.expertId = utilObj.getUrlParam('check');
		this.bindEvent();
//		this.saveBrowse();
		this.bindExpertShowData();
		this.loadAssistantList();
		this.loadPicsShowList();
		this.loadLectureContentList();
		this.loadCooperateList();
		this.loadBrowseList();
		$(".cooperate-actiondate").change();
		page.userRole=JSON.parse(sessionStorage.getItem("userinfo")).user.roleId;
		page.userName=JSON.parse(sessionStorage.getItem("userinfo")).user.userName;
	},
}
$(function(){
	seajs.use('table2excel',function(){
		page.resource_detail.init();
	})
	
})
