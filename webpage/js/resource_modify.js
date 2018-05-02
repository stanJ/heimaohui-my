page.resource_modify = {
	storage:{
		expertId:'',
		tb1:'',
		tb2:'',
		tb3:''
	},
	bindEvent:function(){
		var _this = this;
//		$(".form-expert-info .btn-upload").click(function(){
//			utilObj.uploadFile("#upload-file1");
//		})
		$(".form-expert-info input[type=file]").change(function(){
			utilObj.uploadFile("#upload-file1");
		})
		$(".form-expert-info .btn-remove").click(function(){
			utilObj.removeFile("#upload-file1");
		})
		$("#btn_add1").click(function(){
			if(!_this.storage.expertId){
				utilObj.alert({
					body:'请先新建专家'
				})
				return;
			}
			if(utilObj.getTableLength("#table1")>=5){
				utilObj.alert({
					body:'最多添加5个助手'
				})
				return;
			}
			_this.addAssistant();
		})
		$("#btn_add2").click(function(){
			var tb2 = _this.storage.tb2;
			var tid = '';
			if(tb2.getCheckedRowData()){
				tid = tb2.getCheckedRowData().tid;
			}
			
		})
		$("#btn_add3").click(function(){
			if(!_this.storage.expertId){
				utilObj.alert({
					body:'请先新建专家'
				})
				return;
			}
			_this.buildModifyCooperateModal();
		})
		$(".cooperate-actiondate").on('change',function(){
			var date = $(this).val();
			_this.loadCooperateChart(date);
		})
		$(".category-btns").on('click','button',function(){
			if(!$(this).hasClass("bszk-btn-selected")){
				var num = $("button.bszk-btn-selected").length;
				if(num>=5){
					utilObj.alert({
						body:'最多选择5个专家类别'
					})
					return;
				}
			}
			$(this).toggleClass('bszk-btn-selected');
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
	bindEventForModalCooperate:function(){
		$("#btn_add_tp2_1").click(function(){
			utilObj.addPhotoUploadLi(".ul-modal-cooperate");
		})
		$("#btn_remove_tp2_1").click(function(){
			utilObj.deletePhotoUploadLi(".ul-modal-cooperate");
		})
		utilObj.bindUploadPhotoEvent(".ul-modal-cooperate");
	},
	bindEventForPics:function(){
		var _this = this;
		$("#btn_add_pics").click(function(){
			utilObj.addPhotoUploadLi(".ul-modal-pics");
		})
		$("#btn_remove_pics").click(function(){
			utilObj.deletePhotoUploadLi(".ul-modal-pics",{
				callback:function(tid,func){
					var setting = {
						url :"/m/pics/operatePicsById",
						type: "POST",
						data:{
							modelId:tid,
							status:3,
						},
						success: function(data){
							if(data.code==0){
								
							}else{
								if(func){
									func();
								}
							}
						}
					}
					ajax(setting);
				}
			});
		})
//		$(".ul-modal-pics").on('click','.btn-upload',function(){
//			var li = $(this).parents("li:first");
//			var inputFile = li.find("input[type=file]");
//			utilObj.uploadAll(inputFile,{
//				url:"/m/pics/photoUpload",
//				param:'photoFile',
//				callback:function(responseText){
//					var data = JSON.parse(responseText);
//					if(data.code == 0){
//						//utilObj.alert({body:data.message});
//					}else{
//						var filepathInput = li.find("input.file-path");
//						if(filepathInput){
//							$(filepathInput).val(data.object.picturePath);
//							utilObj.showMessage({
//								type:utilObj.showMessageType['msg-success'],
//								body:'上传成功'
//							});
//						}
//						_this.savePics(data.object.picturePath);
//					}
//				}
//			})
//		})
		$(".ul-modal-pics").on('change','input[type=file]',function(){
			var li = $(this).parents("li:first");
			var preview = li.find(".preview-img");
			var filenameInput = li.find("input.file-name");
			var filename = this.files[0].name;
			var tid = $(this).data("tid");
			if(filenameInput){
				$(filenameInput).val(filename);
			}
			if(preview){
//				utilObj.previewImg(preview,this);
				utilObj.previewImgBackground(preview,this);
				
			}
			
//			var li = $(this).parents("li:first");
			var inputFile = li.find("input[type=file]");
			utilObj.uploadAll(inputFile,{
				url:"/m/userExtend/fileUpload",
				param:'file',
				callback:function(responseText){
					var data = JSON.parse(responseText);
					if(data.code == 0){
						//utilObj.alert({body:data.message});
					}else{
						var filepathInput = li.find("input.file-path");
						if(filepathInput){
							$(filepathInput).val(data.object.picturePath);
							utilObj.showMessage({
								type:utilObj.showMessageType['msg-success'],
								body:'上传成功'
							});
						}
						_this.savePics(data.object.filePath,tid,inputFile);
					}
				}
			})
			
		})
	},
	loadPicsList:function(){
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
					var filename = utilObj.getFileName(val.pic);
					if(val){
						h += 			'<li class="upload-img-li">\
										<label class="checkbox-pretty inline"><input type="checkbox"><span></span></label>\
										<span class="serial-number">'+(i*1+1)+'</span>\
										<div class="preview-img" data-src="'+utilObj.fillPicPath(val.pic)+'"></div>\
										<input type="text" class="input-large file-name" value="'+filename+'" readonly="">\
										<input type="hidden" name="pics" class="file-path" value="'+val.pic+'">\
										<div class="file_wrapper">\
											<button class="sui-btn btn-file" type="button">选择文件</button>\
											<input type="file" class="input-file" accept="image/bmp,image/jpg,image/jpeg,image/tiff," data-tid="'+val.tid+'">\
										</div>\
										<button class="sui-btn btn-success btn-download" type="button">下载原图</button>\
									</li>';
					}
					
				})
				if(!h){
					h += 			'<li class="upload-img-li">\
										<label class="checkbox-pretty inline"><input type="checkbox"><span></span></label>\
										<span class="serial-number">'+1+'</span>\
										<div class="preview-img" ></div>\
										<input type="text" class="input-large file-name" value="" readonly="">\
										<input type="hidden" name="pics" class="file-path" value="">\
										<div class="file_wrapper">\
											<button class="sui-btn btn-file" type="button">选择文件</button>\
											<input type="file" class="input-file" accept="image/bmp,image/jpg,image/jpeg,image/tiff,">\
										</div>\
										<button class="sui-btn btn-success btn-download" type="button">下载原图</button>\
									</li>';
				}
				$(".ul-modal-pics").html(h);
				utilObj.previewPhotos(".ul-modal-pics",".preview-img");
				
			}
		}
		page.service.getPicsData(param);
	},
	savePics:function(pic,tid,input){
		var _this = this;
		var setting = {
			url :"/m/pics/savePics",
			type: "POST",
			data:{
				expertId:this.storage.expertId,
				pic:pic,
				serial:1,
			},
			success: function(data){
				if(data.code==0){
					
				}else{
					$(input).data('tid',data.object.tid);
				}
			}
		}
		if(tid){
			setting.url = "/m/pics/updatePics";
			setting.data.tid = tid;
		}
		ajax(setting);
	},
	validate:function(){
		var _this = this;
		$(".form-expert-info").validate({
			rules:{
				name:{
					required:true,
					maxlength:20,
				},
//				comment:{
//					maxlength:10
//				},
				certificate:{
					certificate:true,
				},
				socialFunction:{
					maxlength:50,
				},
				email:{
					email:true,
				},
				contact:{
					
				},
				
			},
			success:function(){
				_this.modifyExpert();
				return false;
			},
		})
	},
	validate_assistant:function(tid){
		var _this = this;
		$(".form-assistant").validate({
			rules:{
				name:{
					required:true,
				},
				contact:{
					
				},
				email:{
					email:true,
				},
				certificate:{
					certificate:true,
				},
			},
			success:function(){
				_this.modifyAssistant(tid);
				return false;
			},
		})
	},
	loadCategoryBtns:function(){
		var _this = this;
		var dtd = $.Deferred();
		var param = {
			callback:function(d){
				var h = '';
				$.each(d.content,function(i,val){
					h += '<button type="button" class="sui-btn btn-bordered btn-sm-round" data-tid="'+val.tid+'">'+val.name+'</button>';
				})
				$(".category-btns").html(h);
				dtd.resolve();
				_this.bindExpertData()
			}
		}
		page.service.getExpertCategoryData(param);
		return dtd.promise();
	},
	getSelectedCategory:function(){
		var ary = [];
		var str = '';
		$(".category-btns>button.bszk-btn-selected").each(function(i){
			ary.push($(this).data("tid"));
		})
		if(ary.length>0){
			str = JSON.stringify(ary);
		}
		return str;
	},
	bindExpertData:function(){
		var expertId = this.storage.expertId;
		if(!expertId){
			return;
		}
		var _this = this;
		var jsonFilter = {
			search_EQ_tid:expertId,
		}
		var param = {
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var data = d.content[0];
				utilObj.bindData(".form-expert-info",data,{
					multi_input:'socialFunction',
					except:'city',
				});
				_this.selectBtns(".category-btns",data.category);
//				utilObj.setFileName(".previewfile1",data.filePath);
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
		page.service.getExpertData(param);
	},
	modifyExpert:function(){
		var _this = this;
		var expertId = this.storage.expertId;
		var data = utilObj.getFormData(".form-expert-info");
		data.category = this.getSelectedCategory();
		data.birth = utilObj.fillDate(data.birth);
		var setting = {
			type: "POST",
			data:data,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
					
					if(!_this.storage.expertId){
						$(".form-expert-info button[type=submit]").hide();
						utilObj.alert({
							body:'保存成功,请继续添加助手等其他信息'
						})
					}else{
						utilObj.showMessage({
							type:utilObj.showMessageType['msg-success'],
							body:'保存成功'
						});
					}
					_this.storage.expertId = data.object.tid;
					
				}
			}
		}
		if(expertId){
			setting.url = "/m/expert/updateExpert";
			setting.data.tid = expertId;
		}else{
			setting.url = "/m/expert/saveExpert";
		}
		ajax(setting);
	},
	addAssistant:function(tid){
		var _this = this;
		var title = '助手信息添加';
		if(tid){
			title = '助手信息修改';
		}
		var setKeyVal ={
			title:title,
			shown:function(){
				if(tid){
					_this.bindAssistantData(tid);
				}
				_this.validate_assistant(tid);
			}
		}
		modalBulider.modal('template1',setKeyVal);
	},
	bindAssistantData:function(tid){
		var _this = this;
		var jsonFilter = {
			search_EQ_tid:tid,
		}
		var param = {
			expertId:_this.storage.expertId,
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d){
				var data = d.content[0];
				utilObj.bindData(".form-assistant",data);
			}
		}
		page.service.getAssistantData(param);
	},
	modifyAssistant:function(tid){
		var _this = this;
		var param = utilObj.getFormData(".form-assistant");
		param.expertId = this.storage.expertId;
		param.birth = utilObj.fillDate(param.birth);
		var setting = {
			url:"/m/assistant/saveAssistant",
			type: "POST",
			data:param,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
					_this.loadAssistantList(1);
					$("#myModal1").modal('hide');
				}
			}
		}
		if(tid){
			setting.url = "/m/assistant/updateAssistant";
			setting.data.tid = tid;
		}
		ajax(setting);
	},
	delete_assistant:function(obj){
		var _this = this;
		var tid = utilObj.getTid(obj);
		utilObj.confirm({
			body:'请确认是否删除该助手?',
			okHide:function(){
				var param = {
					tid:tid,
					callback:function(){
						_this.storage.tb1.removeRow($(obj).parents('tr:first'));
					}
				}
				_this.deleteAssistant(param);
			}
		})
	},
	modify_assistant:function(obj){
		var tid = utilObj.getTid(obj);
		this.addAssistant(tid);
	},
	loadAssistantList:function(){
		var expertId = this.storage.expertId;
		var _this = this;
		var jsonFilter = {
			search_EQ_expertId:expertId,
		}	
		var param = {
			expertId:expertId,
			jsonFilter:JSON.stringify(jsonFilter),
			pageSize:pageSetting.Pagination_MaxPage,
			callback:function(d){
				var op1={
					dataSpecial:{ 0:{ visible:true, class:'hide' } },
					dataTitles:['tid','助手姓名','出生日期','联系方式','微信','性别','证件号','邮箱',],
					columns:['tid','name','birth','contact','wechat','converted_gender','certificate','email',],
					buttons:[
					{text:"修改",eventname:"_this.modify_assistant",class:''},
					{text:"删除",eventname:"_this.delete_assistant",class:'sui-text-danger'}
					],
					hasCheckBox:true,
				}
				var tb1 = _this.storage.tb1;
				var data1 = utilObj.filterDate(d.content,'birth');
				if(!tb1){
					
					_this.storage.tb1 = new tableBuilder("#table1",data1,op1);
					utilObj.bindDeleteEvent(_this.storage.tb1,'#btn_remove1',{
						confirm_option:{
							body:'请确认是否删除该助手信息?',
							okHide:function(){
								var tid = _this.storage.tb1.getCheckedRowTids();
								var param = {
									tid:tid,
									callback:function(){
										_this.storage.tb1.removeRows();
									}
								}
								_this.deleteAssistant(param);
							}
						}
					});
				}else{
					tb1.resetData(data1);
				}
				
				
			}
		}
		page.service.getAssistantData(param);
	},
	deleteAssistant:function(param){
		
		if(utilObj.isAry(param.tid)){
			$.each(param.tid,function(i,val){
				ajax({
					url:"/m/assistant/operateAssistantById",
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
				url:"/m/assistant/operateAssistantById",
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
	delete_lectureContent:function(obj){
		var tid = utilObj.getTid(obj);
		utilObj.confirm({
			body:'请确认是否删除该演讲内容?',
			okHide:function(){
				var param = {
					tid:tid,
					callback:function(){
						_this.storage.tb2.removeRow($(obj).parents('tr:first'));
					}
				}
				_this.deleteLectureContent(param);
			}
		})
	},
	modify_lectureContent:function(obj){
		var _this = this;
		var tid = utilObj.getTid(obj);
		var text = $(obj).text();
		var tr = $(obj).parents("tr:first");
		var td = tr.find("td.dt-center").eq(1);
		var span = td.children("span");
		if(text == '修改'){
			td.children("span").hide();
			var h = '<input class="input-medium input-xxlarge" maxlength="20"/>'
			td.append(h);
			td.find("input").val(span.text());
			$(obj).text('保存');
		}else if(text == '保存'){
			var content = td.find("input").val();
			_this.modifyLectureContent(tid,content);
//			span.text(td.find("input").val());
//			td.find("input").remove();
			
		}	
	},
	//演讲相关
	loadLectureContentList:function(pageNum){
		var expertId = this.storage.expertId;
		var _this = this;
		
		
		var param = {
			expertId:expertId,
			callback:function(d){
				var op={
					dataSpecial:{ 0:{ visible:true, class:'hide' } },
					dataTitles:['tid','演讲内容',],
					columns:['tid','content',],
					hasCheckBox:true,
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.resource_modify.goToPage_lectureContent"},
					buttons:[
					{text:"修改",eventname:"_this.modify_lectureContent",class:''},
					{text:"删除",eventname:"_this.delete_lectureContent",class:'sui-text-danger'}
					],
				}
				var tb2 = _this.storage.tb2;
				if(!tb2){
					var data = d.content;
					_this.storage.tb2 = new tableBuilder("#table2",data,op);
					utilObj.bindDeleteEvent(_this.storage.tb2,'#btn_remove2',{
						confirm_option:{
							body:'请确认是否删除该演讲内容?',
							okHide:function(){
								var tid = _this.storage.tb2.getCheckedRowTids();
								var param = {
									tid:tid,
									callback:function(){
										_this.storage.tb2.removeRows();
									}
								}
								_this.deleteLectureContent(param);
							}
						}
					});
				}else{
					tb2.resetData(d.content);
					tb2.paginationObj.updatePages(d.totalPages,pageNum);
				}
				
				
			}
		}
		if(pageNum){
			param.nextPage = pageNum-1;
		}
		page.service.getLectureContentData(param);
	},
	goToPage_lectureContent:function(pageNum){
		this.loadLectureContentList(pageNum);
	},
	validate_lectureContent:function(){
		var _this = this;
		$(".form-lectureContent").validate({
			rules:{
				content:{
					required:true,
					maxlength:20
				},
			},
			success:function(){
				if(!_this.storage.expertId){
					utilObj.alert({
						body:'请先新建专家'
					})
					return false;
				}
				_this.modifyLectureContent();
				return false;
			},
		})
	},
	deleteLectureContent:function(param){
		if(utilObj.isAry(param.tid)){
			$.each(param.tid,function(i,val){
				ajax({
					url:"/m/lectureContent/operateLectureContentById",
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
				url:"/m/lectureContent/operateLectureContentById",
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
	modifyLectureContent:function(tid,content){
		var _this = this;
		var param = {};
		if(!content){
			param = utilObj.getFormData(".form-lectureContent");
		}else{
			param.content = content;
		}
		
		param.expertId = _this.storage.expertId;
		var setting = {
			url:"/m/lectureContent/saveLectureContent",
			type: "POST",
			data:param,
			success: function(data){
				if(data.code==0){
					//utilObj.alert({body:data.message});
				}else{
					//callback
					$("input[name=content]").val('');
					_this.loadLectureContentList(1);
				}
			}
		}
		if(tid){
			setting.url = "/m/lectureContent/updateLectureContent";
			setting.data.tid = tid;
		}
		ajax(setting);
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
//						_this.storage.tb3.removeRow($(obj).parents('tr:first'));
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
			pageSize:5,
			expertId:expertId,
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
					dataTitles:['tid','客户名称','活动时间','活动地址','合同价(万元)','成本价(万元)','专家联系人','评价','创建人'],
					columns:['tid','customerName','actionDate','actionAddress','contractPrice','costPrice','expertContactor','comment','converted_createUser','customerId'],
					hasCheckBox:false,
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.resource_modify.goToPage_cooperate"},
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
					async:false,
					data:{
						modelId:val,
						status:3
					},
					success: function(data){
						if(data.code==0){
							//utilObj.alert({body:data.message});
						}else{
							
							if((i+1)==total){
								$(".cooperate-actiondate").change();
							}
						}
					}
				})
			})
			param.callback();
			
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
		var title = '配合度评价添加';
		if(tid){
			title = '配合度评价修改';
		}
		var setKeyVal ={
			title:title,
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
							number:true,
							gt:0,
						},
						costPrice:{
							required:true,
							number:true,
							gt:0,
						},
						actionAddress:{	
							maxlength:5,
						},
						comment:{	
							maxlength:10,
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
					var page = utilObj.getTablePage({
						tb:_this.storage.tb3,
					})
					_this.loadCooperateList(page);
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
	selectBtns:function(selector,data){
		if(data){
			data = JSON.parse(data);
		}
		$.each(data,function(i,val){
			$(selector).find("button[data-tid="+val+"]").addClass("bszk-btn-selected");
		})
	},
	changeTitle:function(id){
		if(id){
			$(".sui-breadcrumb li.active").text('资源修改');
			$(".bszk-title").text('资源修改');
		}else{
			$(".sui-breadcrumb li.active").text('资源添加');
			$(".bszk-title").text('资源添加');
		}
	},
	init:function(){
		var _this = this;
		this.bindEvent();
		this.bindEventForPics();
		this.validate();
		this.validate_lectureContent();
		this.storage.expertId = utilObj.getUrlParam('modify')
		this.changeTitle(this.storage.expertId);
		this.loadAssistantList();
		this.loadCooperateList();
		this.loadPicsList();
		this.loadLectureContentList();
		$(".cooperate-actiondate").change();
		page.userRole=JSON.parse(sessionStorage.getItem("userinfo")).user.roleId;
		page.userName=JSON.parse(sessionStorage.getItem("userinfo")).user.userName;
//		$.when(this.loadCategoryBtns())
//		.then(this.bindExpertData())
		$.when(page.service.getProvince())
		.then(function(d){
			var dtd = $.Deferred();
			var $ul = $("input[name=province]").parents(".dropdown-inner").children("ul");
			utilObj.loadSelectOptions($ul,{
				text:'value',
				value:'code'
			},d);
			dtd.resolve();
			return dtd.promise();
		})
		.then(function(){
			var dtd = $.Deferred(); 
			_this.loadCategoryBtns();
			dtd.resolve();
			return dtd.promise();
		})
		
		
	},
}
$(function(){
	page.resource_modify.init();
})
