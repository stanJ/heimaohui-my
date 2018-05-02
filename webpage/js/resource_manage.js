page.resource_manage = {
	storage:{
		tb1:'',
		expertCategory:'',
	},
	bindEvent:function(){
		var _this = this;
		$("#btn_query1").click(function(){
			_this.loadExpertList();
		})
		$("#btn_add1").click(function(){
			utilObj.navigate('resource_modify');
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
		utilObj.navigate('resource_detail',{
			check:tid,
		});
	},
	modify:function(obj){
		var tid = utilObj.getTid(obj);
		utilObj.navigate('resource_modify',{
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
								tid:tid,
							})
				_this.loadExpertList(page);
			}
		}
		utilObj.confirm({
			body:'请确认是否删除该专家?',
			okHide:function(){
				_this.deleteExpert(param);
			}
		})
		
	},
	deleteExpert:function(param){
		ajax({
			url:"/m/expert/operateExpertById",
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
	getExpertCategoryCell:function(data,row){
		data = utilObj.parseStr(data);
		var _this = this;
		var obj = utilObj.changeCategoryToObj(_this.storage.expertCategory);
		var h = '';
		var text = '';
		$.each(data,function(i,val){
			if(obj[val]){
				h += '<button class="sui-btn btn-primary btn-sm-round no-hover nomr">'+obj[val]+'</button>';
				text += obj[val]+',';
			}
			
		})
		if(row.otherCategory){
			h += '<button class="sui-btn btn-primary btn-sm-round no-hover nomr">'+row.otherCategory+'</button>';
			text += row.otherCategory+',';
		}
		
		if(text){
			text = text.slice(0,-1);
		}
		return {
			html:h,
			text:text,
		};
	},
	loadExpertCategoryList:function(){
		var dtd = $.Deferred();
		var _this = this;
		var param = {};
		param.callback = function(d){
			if(!_this.storage.expertCategory){
				_this.storage.expertCategory = d.content;
			}
			_this.loadExpertList();
			dtd.resolve();
			var $ul = $("input[name=category]").parents(".dropdown-inner").children("ul");
			utilObj.loadSelectOptions($ul,{
				text:'name',
				value:'tid'
			},d.content);
		}
		page.service.getExpertCategoryData(param);
		return dtd.promise();
	},
	
	loadExpertList:function(pageNum){
		_this = this;
		var d = utilObj.getQueryData("#query-expert");
		var jsonFilter = {
			search_LIKE_name:d.name,
			search_LIKE_category:d.category,
			search_EQ_province:d.province,
			search_EQ_city:d.city,
		}
		var param = {
			jsonFilter:JSON.stringify(jsonFilter),
			callback:function(d,xhr){
				var op1={
					dataSpecial:{ 
						0:{ 
							visible:true, 
							class:'hide'
						},
						1:{ 
							visible:true, 
							class:'dt-name tdEllipsis'
						},
						2:{
							visible:true,
							render: function(data, type, row, meta){
								var obj = _this.getExpertCategoryCell(data,row);
						    		return '<span dataRow="'+meta.row+'"  dataCol="'+meta.col+'" title="'+obj.text+'">'+obj.html+'</span>';
						    }
						},
						4:{
							visible:false,
						}
					},
					pagination:{currentPage:1,pages:d.totalPages,displayPage:pageSetting.Pagination_DefaultPage,selectedEvent:"page.resource_manage.goToPage_expert"},
					dataTitles:['tid','专家名称','专家类别','创建人',],
					columns:['tid','name','category','converted_createUser','otherCategory'],
					hasCheckBox:false,
					buttons:[{text:"查看",eventname:"_this.checkInfo",class:'sui-text-black'},{text:"修改",eventname:"_this.modify",class:''},{text:"删除",eventname:"_this.delete",class:'sui-text-danger'}],
				}

				var tb1 = _this.storage.tb1;
				if(!tb1){
					_this.storage.tb1 = new tableBuilder("#table1",d.content,op1);
				}else{
					tb1.resetData(d.content);
					tb1.paginationObj.updatePages(d.totalPages,pageNum);
				}
				$(".head-num").text(d.totalElements);
				
				var responseHeaderInfo = xhr.getResponseHeader( "usesParams" );
				
				utilObj.redrawByIsUsed("#table1",_this.storage.tb1,responseHeaderInfo);
			
				
				if(page.userRole!="1" && page.userRole!="2"){
					$.each($("#table1 tbody tr"), function() {
						if(page.userRole=="3" && $(this).find("td:eq(3) span").text().trim()==page.userName){
							return true;
						}
						$(this).find("td:eq(4) li:eq(1)").hide();
						$(this).find("td:eq(4) li:eq(2)").hide();
					});
				}
			}
		}
		if(pageNum){
			param.nextPage = pageNum-1;
		}
		page.service.getExpertData(param);
	},
	goToPage_expert:function(pageNum){
		this.loadExpertList(pageNum);
	},
	init:function(){
		var _this = this;
		this.bindEvent();
		this.loadExpertCategoryList()
//		$.when(this.loadExpertCategoryList())
//		.then(this.loadExpertList());
		$.when(page.service.getProvince())
		.then(function(d){
			var $ul = $("input[name=province]").parents(".dropdown-inner").children("ul");
			utilObj.loadSelectOptions($ul,{
				text:'value',
				value:'code'
			},d);
		})
		
		page.userRole=JSON.parse(sessionStorage.getItem("userinfo")).user.roleId;
		page.userName=JSON.parse(sessionStorage.getItem("userinfo")).user.userName;
		if(page.userRole!="4") $(".addbtn").show();
	}
}
$(function(){
	page.resource_manage.init();
})