page.notice_manage = {
	storage:{
		tb1:'',
		tb2:'',
		tb3:'',
	},
	bindEvent:function(){
		var _this = this;
//		$(".form-intro .btn-upload").click(function(){	
//			utilObj.uploadFile("#upload-file1",_this.modifyIntro);
//		})
		$(".form-intro input[type=file]").change(function(){	
			utilObj.uploadFile("#upload-file1",_this.modifyIntro);
		})
		
	},
	delete_intro:function(obj){
		
		var tid = utilObj.getTid(obj);
		utilObj.confirm({
			body:'请确认是否删除该简介信息?',
			okHide:function(){
				var param = {
					tid:tid,
					callback:function(){
						var page = utilObj.getTablePage({
							tb:_this.storage.tb1,
							el:"#table1",
							tid:tid,
						})
						_this.loadIntroList(page);
					}
				}
				_this.deleteIntro(param);
			}
		})
	},
	toTopIntro:function(rowData){
		var _this = this;
		ajax({
			url:"/m/intro/updateIntro",
			type: "POST",
			data:{
				fileName:rowData.fileName,
				uptime:rowData.uptime,
				filePath:rowData.filePath,
				tid:rowData.tid,
				isTop:'true'
			},
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					var page = utilObj.getTablePage({
						tb:_this.storage.tb1,
					})
					_this.loadIntroList(page);
				}
			}
		})
	},
	toBottomIntro:function(rowData){
		var _this = this;
		ajax({
			url:"/m/intro/updateIntro",
			type: "POST",
			data:{
				fileName:rowData.fileName,
				uptime:rowData.uptime,
				filePath:rowData.filePath,
				tid:rowData.tid,
				isTop:'false'
			},
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					var page = utilObj.getTablePage({
						tb:_this.storage.tb1,
					})
					_this.loadIntroList(page);
				}
			}
		})
	},
	openIntroTop:function(obj){
		var _this = this;
		var rowData = utilObj.getRowDataForButton(_this.storage.tb1,obj);
		var isTop = rowData.isTop;
		if(isTop == false){
			var jsonFilter = {
				search_EQ_isTop:'1',
			}
			var param = {
				jsonFilter:JSON.stringify(jsonFilter),
				pageSize:pageSetting.Pagination_MaxPage,
			}
			$.when(page.service.getIntroData(param))
			.then(function(d){
				var num = d.content.length;
				if(num>=5){
					utilObj.alert({
						body:'最多可显示5个公司简介'
					})
					return;
				}
				_this.toTopIntro(rowData);	
			})
		}else{
			_this.toBottomIntro(rowData);
		}	
	},
//	closeIntroTop:function(){
//		var _this = this;
//		var rowData = utilObj.getRowDataForButton(_this.storage.tb1,obj);
//		_this.toBottomIntro(rowData);	
//	},
	redrawByIsTop:function(container,tb){
		var trs = $(container).find("tbody tr");
		trs.each(function(i){
			var rowData = tb.getSelectedRow(this);
			var isTop = rowData.isTop;
			var a = $(this).children("td.dt-op").find("a").eq(0);
			if(isTop==true){
				a.addClass('sui-text-grey');
				a.text('关闭置顶');
			}else{
				a.removeClass('sui-text-grey');
				a.text('开启置顶');
			}
		})
	},
	loadIntroList:function(pageNum){
		_this = page.notice_manage;
		var param = {
			callback:function(d){
				var op={					
					dataSpecial:{ 0:{ visible:true, class:'hide' },3:{ visible:true, class:'hide' },4:{ visible:true, class:'hide' } },
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.notice_manage.goToPage_intro"},
					dataTitles:['tid','文本名称','上传时间','isTop','filePath'],
					columns:['tid','fileName','uptime','isTop','filePath'],
					buttons:[
					{text:"开启置顶",eventname:"_this.openIntroTop",class:'',keyValue:'false'},
//					{text:"关闭置顶",eventname:"_this.closeIntroTop",class:'sui-text-grey',keyValue:'true'},
					{text:"删除",eventname:"_this.delete_intro",class:'sui-text-danger'}
					],
					hasCheckBox:true,
//					buttonsKey:'isTop',
				}
				var tb1 = _this.storage.tb1;
				var data = d.content;
//				var data = utilObj.filterIsTop(d.content,'isTop');
				if(!tb1){
//					var data = utilObj.filterDate(d.content,'uptime');
					_this.storage.tb1 = new tableBuilder("#table1",data,op);
					utilObj.bindDeleteEvent(_this.storage.tb1,'#btn_remove1',{
						confirm_option:{
							body:'请确认是否删除该简介信息?',
							okHide:function(){
								var tid = _this.storage.tb1.getCheckedRowTids();
								var param = {
									tid:tid,
									callback:function(){
										var page = utilObj.getTablePage({
											tb:_this.storage.tb1,
											el:"#table1",
											tid:tid,
										})
										_this.loadIntroList(page);
									}
								}
								_this.deleteIntro(param);
							}
						}
					});
				}else{
					tb1.resetData(data);
					tb1.paginationObj.updatePages(d.totalPages,pageNum);
				}
				_this.redrawByIsTop('#table1',_this.storage.tb1);
			}
		}
		if(pageNum){
			param.nextPage = pageNum-1;
		}
		page.service.getIntroData(param);
	},
	deleteIntro:function(param){
		var obj = this;
		if(utilObj.isAry(param.tid)){
			$.each(param.tid,function(i,val){
				ajax({
					url:"/m/intro/operateIntroById",
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
			});
			param.callback();
		}else{
			ajax({
				url:"/m/intro/operateIntroById",
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
	modifyIntro:function(){
		var _this = page.notice_manage;
		var param = utilObj.getQueryData(".form-intro");
		var now = moment().format("YYYY-MM-DD HH:mm:ss");
		param.uptime = now;
		param.isTop = 'false';
		
		if(param.fileName){
			var tmp = param.fileName.split(".");
			if(tmp.length==2){
				if(tmp[0].length>30) tmp[0]=tmp[0].substring(0,30);
				param.fileName=tmp.join(".");
			}else{
				param.fileName="未知";
			}
		}
		
		var setting = {
			url:"/m/intro/saveIntro",
			type: "POST",
			data:param,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
					_this.loadIntroList(1);
				}
			}
		}
		ajax(setting);
		$(".previewfile1").val("");
	},
	goToPage_intro:function(pageNum){
		this.loadIntroList(pageNum);
	},
	delete_announce:function(obj){
		var tid = utilObj.getTid(obj);
		utilObj.confirm({
			body:'请确认是否删除该公告?',
			okHide:function(){
				var param = {
					tid:tid,
					callback:function(){
						var page = utilObj.getTablePage({
							tb:_this.storage.tb2,
							el:"#table2",
							tid:tid,
						})
						_this.loadAnnounceList(page);
					}
				}
				_this.deleteAnnounce(param);
			}
		})
	},
	loadAnnounceList:function(pageNum){
		var _this = this;
		var param = {
			callback:function(d){	
				var op={
					dataSpecial:{ 0:{ visible:true, class:'hide' } },
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.notice_manage.goToPage_announce"},
					dataTitles:['tid','公告内容','更新时间',],
					columns:['tid','content','createTime',],
					buttons:[{text:"删除",eventname:"_this.delete_announce",class:'sui-text-danger'}],
					hasCheckBox:true,
				}
				var tb2 = _this.storage.tb2;
				var data = utilObj.filterContent(d.content,'content');
				if(!tb2){
//					var data = utilObj.filterDate(d.content,'modifyTime');
					
					_this.storage.tb2 = new tableBuilder("#table2",data,op);
					utilObj.bindDeleteEvent(_this.storage.tb2,'#btn_remove2',{
						confirm_option:{
							body:'请确认是否删除该公告?',
							okHide:function(){
								var tid = _this.storage.tb2.getCheckedRowTids();
								var param = {
									tid:tid,
									callback:function(){
										var page = utilObj.getTablePage({
											tb:_this.storage.tb2,
											el:"#table2",
											tid:tid,
										})
										_this.loadAnnounceList(page);
									}
								}
								_this.deleteAnnounce(param);
							}
						}
					});
				}else{
					tb2.resetData(data);
					tb2.paginationObj.updatePages(d.totalPages,pageNum);
				}
			}
		}
		if(pageNum){
			param.nextPage = pageNum-1;
		}
		page.service.getAnnounceData(param);
	},
	deleteAnnounce:function(param){
		var obj = this;
		if(utilObj.isAry(param.tid)){
			$.each(param.tid,function(i,val){
				ajax({
					url:"/m/announce/operateAnnounceById",
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
				url:"/m/announce/operateAnnounceById",
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
	validate_announce:function(){
		var _this = this;
		$(".form-announce").validate({
			rules:{
				ayear:{
					required:true,
					digits:true,
					gt:999,
					lt:10000,
				},
				yearOrders:{
					required:true,
					digits:true,
					gt:0,
					lt:1000000,
				},
				byear:{
					required:true,
					digits:true,
					gt:999,
					lt:10000,
				},
				bmonth:{
					required:true,
					digits:true,
					gt:0,
					lt:13,
				},
				monthOrders:{
					required:true,
					digits:true,
					gt:0,
					lt:1000000,
				}
			},
			success:function(){
				_this.modifyAnnounce();
				return false;
			},
		})
	},
	modifyAnnounce:function(){
		var _this = this;
		var param = utilObj.getFormData(".form-announce");
		var setting = {
			url:"/m/announce/saveAnnounce",
			type: "POST",
			data:param,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
					_this.loadAnnounceList(1);
					utilObj.clearData('.form-announce');
				}
			}
		}
		ajax(setting);
	},
	goToPage_announce:function(pageNum){
		this.loadAnnounceList(pageNum);
	},
	delete_news:function(obj){
		var tid = utilObj.getTid(obj);
		utilObj.confirm({
			body:'请确认是否删除该消息?',
			okHide:function(){
				var param = {
					tid:tid,
					callback:function(){
						var page = utilObj.getTablePage({
							tb:_this.storage.tb3,
							el:"#table3",
							tid:tid,
						})
						_this.loadNewsList(page);
					}
				}
				_this.deleteNews(param);
			}
		})
	},
	toTopNews:function(rowData){
		var _this = this;
		ajax({
			url:"/m/news/updateNews",
			type: "POST",
			data:{
				content:rowData.content,
				tid:rowData.tid,
				isTop:'true'
			},
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					var page = utilObj.getTablePage({
						tb:_this.storage.tb3,
					})
					_this.loadNewsList(page);
				}
			}
		})
	},
	toBottomNews:function(rowData){
		var _this = this;
		ajax({
			url:"/m/news/updateNews",
			type: "POST",
			data:{
				content:rowData.content,
				tid:rowData.tid,
				isTop:'false'
			},
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					var page = utilObj.getTablePage({
						tb:_this.storage.tb3,
					})
					_this.loadNewsList(page);
				}
			}
		})
	},
	openNewsTop:function(obj){
		var _this = this;
//		var tid = utilObj.getTid(obj);
		var rowData = utilObj.getRowDataForButton(_this.storage.tb3,obj);
		var isTop = rowData.isTop;
		if(isTop == false){
			var jsonFilter = {
				search_EQ_isTop:'1',
			}
			var param = {
				jsonFilter:JSON.stringify(jsonFilter),
				pageSize:pageSetting.Pagination_MaxPage,
			}
			$.when(page.service.getNewsData(param))
			.then(function(d){
				var num = d.content.length;
				if(num>=10){
					utilObj.alert({
						body:'最多可显示10条消息'
					})
					return;
				}
				_this.toTopNews(rowData);	
			})
		}else{
			_this.toBottomNews(rowData);	
		}
	},
//	closeNewsTop:function(){
//		var _this = this;
//		var tid = utilObj.getTid(obj);
//		var content = utilObj.getRowDataForButton(_this.storage.tb3,obj).content;
//		_this.toBottomNews(rowData);	
//	},
	loadNewsList:function(pageNum){
		var _this = this;
		var param = {
			callback:function(d){							
				var op={
					dataSpecial:{ 0:{ visible:true, class:'hide' },3:{ visible:true, class:'hide' }  },
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.notice_manage.goToPage_news"},
					dataTitles:['tid','信息内容','更新时间','isTop'],
					columns:['tid','content','modifyTime','isTop'],
					buttons:[
					{text:"开启置顶",eventname:"_this.openNewsTop",class:'',keyValue:'false'},
//					{text:"关闭置顶",eventname:"_this.closeNewsTop",class:'sui-text-grey',keyValue:'true'},
					{text:"删除",eventname:"_this.delete_news",class:'sui-text-danger'}
					],
					hasCheckBox:true,
//					buttonsKey:'isTop',
				}
				var tb3 = _this.storage.tb3;
				var data = d.content;
//				data = utilObj.filterIsTop(data,'isTop');
				if(!tb3){
//					var data = utilObj.filterDate(d.content,'modifyTime');
					_this.storage.tb3 = new tableBuilder("#table3",data,op);
					utilObj.bindDeleteEvent(_this.storage.tb3,'#btn_remove3',{
						confirm_option:{
							body:'请确认是否删除该消息?',
							okHide:function(){
								var tid = _this.storage.tb3.getCheckedRowTids();
								var param = {
									tid:tid,
									callback:function(){
										var page = utilObj.getTablePage({
											tb:_this.storage.tb3,
											el:"#table3",
											tid:tid,
										})
										_this.loadNewsList(page);
									}
								}
								_this.deleteNews(param);
							}
						}
					});
				}else{
					tb3.resetData(data);
					tb3.paginationObj.updatePages(d.totalPages,pageNum);
				}
				_this.redrawByIsTop("#table3",_this.storage.tb3)
			}
		}
		if(pageNum){
			param.nextPage = pageNum-1;
		}
		page.service.getNewsData(param);
	},
	deleteNews:function(param){
		var obj = this;
		if(utilObj.isAry(param.tid)){
			$.each(param.tid,function(i,val){
				ajax({
					url:"/m/news/operateNewsById",
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
			});
			param.callback();
		}else{
			ajax({
				url:"/m/news/operateNewsById",
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
	validate_news:function(){
		var _this = this;
		$(".form-news").validate({
			rules:{
				content:{
					required:true,
					maxlength:30
				},
			},
			success:function(){
				_this.modifyNews();
				return false;
			},
		})
	},
	modifyNews:function(){
		var _this = this;
		var param = utilObj.getFormData(".form-news");
		param.isTop = 'false';
		var setting = {
			url:"/m/news/saveNews",
			type: "POST",
			data:param,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
					_this.loadNewsList(1);
					utilObj.clearData(".form-news");
				}
			}
		}
		ajax(setting);
	},
	goToPage_news:function(pageNum){
		this.loadNewsList(pageNum);
	},
	init:function(){
		this.bindEvent();
		this.validate_announce();
		this.validate_news();
		this.loadIntroList();
		this.loadAnnounceList();
		this.loadNewsList();	
	}
}
$(function(){
	page.notice_manage.init();
})
