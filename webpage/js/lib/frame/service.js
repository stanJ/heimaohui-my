var page = {
	service:{
		getConstant:function(){
			if(sessionStorage.getItem('constant')){
				return;
			}
			var dtd = $.Deferred();
			ajax({
				url:"/dict/constant",
				type: "GET",
				data:{
				},
				success: function(data){
					if(data.code==0){
						//utilObj.alert({body:data.message});
					}else{
						console.log(data);
						var d = data.object;
						sessionStorage.setItem('constant',JSON.stringify(d));
						dtd.resolve();
					}
				}
			})
			return dtd.promise();
		},
		getProvince:function(){
			var dtd = $.Deferred();
			ajax({
				url:"/dict/PROVINCE",
				type: "GET",
				data:{
				},
				success: function(data){
					if(data.code==0){
						//utilObj.alert({body:data.message});
					}else{
						console.log(data);
						var d = data;
						dtd.resolve(d);
					}
				}
			})
			return dtd.promise();
		},
		getCity:function(province){
			var dtd = $.Deferred();
			ajax({
				url:"/dict/code/"+province+"?key=CITY",
				type: "GET",
				data:{
				},
				success: function(data){
					if(data.code==0){
						//utilObj.alert({body:data.message});
					}else{
						console.log(data);
						var d = data;
						dtd.resolve(d);
					}
				}
			})
			return dtd.promise();
		},
		getDistrict:function(city){
			var dtd = $.Deferred();
			ajax({
				url:"/dict/code/"+city+"?key=DISTRICT",
				type: "GET",
				data:{
				},
				success: function(data){
					if(data.code==0){
						//utilObj.alert({body:data.message});
					}else{
						console.log(data);
						var d = data;
						dtd.resolve(d);
					}
				}
			})
			return dtd.promise();
		},
		getExpertCategoryData:function(param){
			var jsonFilter = "{}";
			if(param.jsonFilter){
				jsonFilter = param.jsonFilter;
			}
			ajax({
				url:"/m/expertCategory/findExpertCategoryEQStatusPage",
				type: "POST",
				data:{
					nextPage:0,
					pageSize:pageSetting.Pagination_MaxPage,
					status:1,
					sortData:"[{property:'modifyTime',direction:'DESC'}]",		
					jsonFilter:jsonFilter
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
		getExpertData:function(param){
			var jsonFilter = "{}";
			if(param.jsonFilter){
				jsonFilter = param.jsonFilter;
			}
			ajax({
				url:"/m/expert/findExpertEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:pageSetting.Pagination_DefaultPage,
					status:1,
					sortData:"[{property:'modifyTime',direction:'DESC'}]",		
					jsonFilter:jsonFilter
				},
				success: function(data,status,xhr){
					if(data.code==0){
						//utilObj.alert({body:data.message});
					}else{
						var d = data.object;
						param.callback(d,xhr);
					}
				}
			})
		},
		getAssistantData:function(param){
			var expertId = param.expertId;
			if(!expertId){
				param.callback({
					content:[],
				});
				return;
			}
			var jsonFilter = {
				search_EQ_expertId:expertId,
			}
			ajax({
				url:"/m/assistant/findAssistantEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:pageSetting.Pagination_MaxPage,
					status:1,
					sortData:"[{property:'modifyTime',direction:'DESC'}]",		
					jsonFilter:JSON.stringify(jsonFilter)
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
		getLectureContentData:function(param){
			var expertId = param.expertId;
			if(!expertId){
				param.callback({
					content:[],
					totalPages:0,
				});
				return;
			}
			var jsonFilter = {
				search_EQ_expertId:expertId,
			}
			ajax({
				url:"/m/lectureContent/findLectureContentEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:pageSetting.Pagination_DefaultPage,
					status:1,
					sortData:"[{property:'modifyTime',direction:'DESC'}]",		
					jsonFilter:JSON.stringify(jsonFilter)
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
		getCooperateData:function(param){
			if(!param.expertId){
				param.callback({
					content:[],
					totalPages:0,
				})
				return;
			}
			var jsonFilter = "{}";
			if(param.jsonFilter){
				jsonFilter = param.jsonFilter;
			}
			var sortData = "[{property:'modifyTime',direction:'DESC'}]";
			if(param.sortData){
				sortData = param.sortData;
			}
			ajax({
				url:"/m/cooperate/findCooperateEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:param.pageSize?param.pageSize:pageSetting.Pagination_DefaultPage,
					status:1,
					sortData:sortData,		
					jsonFilter:jsonFilter
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
		getWorkData:function(param){
			var customerId = param.customerId;
			if(!customerId){
				param.callback({
					content:[],
					totalPages:0,
				})
				return;
			}
			var jsonFilter = "{}";
			if(param.jsonFilter){
				jsonFilter = param.jsonFilter;
			}
			ajax({
				url:"/m/work/findWorkEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:param.pageSize?param.pageSize:pageSetting.Pagination_DefaultPage,
					status:1,
					sortData:"[{property:'modifyTime',direction:'DESC'}]",		
					jsonFilter:jsonFilter
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
		getCustomerData:function(param){
			var jsonFilter = "{}";
			if(param.jsonFilter){
				jsonFilter = param.jsonFilter;
			}
			ajax({
				url:"/m/customer/findCustomerEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:pageSetting.Pagination_DefaultPage,
					status:1,
					sortData:"[{property:'modifyTime',direction:'DESC'}]",		
					jsonFilter:jsonFilter
				},
				success: function(data,status,xhr){
					if(data.code==0){
						//utilObj.alert({body:data.message});
					}else{
						var d = data.object;
						param.callback(d,xhr);
					}
				}
			})
		},
		getCustomerDataById:function(param){
			ajax({
				url:"/m/customer/findCustomerJoinedById",
				type: "POST",
				data:{
					modelId:param.customerId,
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
		getExpertDataById:function(param){
			ajax({
				url:"/m/expert/findExpertJoinedById",
				type: "POST",
				data:{
					modelId:param.expertId,
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
		getPicsData:function(param){
			var expertId = param.expertId;
			if(!expertId){
				param.callback({
					content:[],
				});
				return;
			}
			var jsonFilter = "{}";
			if(param.jsonFilter){
				jsonFilter = param.jsonFilter;
			}
			ajax({
				url:"/m/pics/findPicsEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:param.pageSize?param.pageSize:pageSetting.Pagination_DefaultPage,
					status:1,
					sortData:"[{property:'modifyTime',direction:'DESC'}]",		
					jsonFilter:jsonFilter
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
		getContactorData:function(param){
			var customerId = param.customerId;
			if(!customerId){
				param.callback({
					content:[],
					totalPages:0,
				})
				return;
			}
			var jsonFilter = "{}";
			if(param.jsonFilter){
				jsonFilter = param.jsonFilter;
			}
			ajax({
				url:"/m/contactor/findContactorEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:param.pageSize?param.pageSize:pageSetting.Pagination_DefaultPage,
					status:1,
					sortData:"[{property:'modifyTime',direction:'DESC'}]",		
					jsonFilter:jsonFilter
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
		getIntroData:function(param){
			var dtd = $.Deferred();
			var jsonFilter = "{}";
			if(param.jsonFilter){
				jsonFilter = param.jsonFilter;
			}
			ajax({
				url:"/m/intro/findIntroEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:param.pageSize?param.pageSize:pageSetting.Pagination_DefaultPage,
					status:1,
					sortData:"[{property:'modifyTime',direction:'DESC'}]",		
					jsonFilter:jsonFilter
				},
				success: function(data){
					if(data.code==0){
						//utilObj.alert({body:data.message});
					}else{
						var d = data.object;
						dtd.resolve(d);
						if(param.callback instanceof Function){
							param.callback(d);
						}
						
					}
				}
			})
			return dtd.promise();
		},
		getAnnounceData:function(param){
			var jsonFilter = "{}";
			if(param.jsonFilter){
				jsonFilter = param.jsonFilter;
			}
			ajax({
				url:"/m/announce/findAnnounceEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:param.pageSize?param.pageSize:pageSetting.Pagination_DefaultPage,
					status:1,
					sortData:"[{property:'modifyTime',direction:'DESC'}]",		
					jsonFilter:jsonFilter
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
		getNewsData:function(param){
			var dtd = $.Deferred();
			var jsonFilter = "{}";
			if(param.jsonFilter){
				jsonFilter = param.jsonFilter;
			}
			ajax({
				url:"/m/news/findNewsEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:param.pageSize?param.pageSize:pageSetting.Pagination_DefaultPage,
					status:1,
					sortData:"[{property:'modifyTime',direction:'DESC'}]",		
					jsonFilter:jsonFilter
				},
				success: function(data){
					if(data.code==0){
						//utilObj.alert({body:data.message});
					}else{
						var d = data.object;
						if(param.callback instanceof Function){
							param.callback(d);
						}
						dtd.resolve(d);
					}
				}
			})
			return dtd.promise();
		},
		getUserData:function(param){
			var jsonFilter = "{}";
			if(param.jsonFilter){
				jsonFilter = param.jsonFilter;
			}
			ajax({
				url:"/m/userExtend/findUserExtendEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:pageSetting.Pagination_DefaultPage,
					status:1,
					sortData:"[{property:'modifyTime',direction:'DESC'}]",		
					jsonFilter:jsonFilter
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
		getBrowseData:function(param){
			
			var jsonFilter = "{}";
			if(param.jsonFilter){
				jsonFilter = param.jsonFilter;
			}
			ajax({
				url:"/m/browse/findBrowseEQStatusPage",
				type: "POST",
				data:{
					nextPage:param.nextPage?param.nextPage:0,
					pageSize:param.pageSize?param.pageSize:pageSetting.Pagination_DefaultPage,
					status:1,
					sortData:"[{property:'createTime',direction:'DESC'}]",		
					jsonFilter:jsonFilter
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
		buildCooperateChart:function(d){
			if(d.length==0){
				$(".bszk-chart").height("auto");
				$(".bszk-chart").html('<img src="../image/Group 19.png" style="width: 90%;margin:40px 0 30px 65px">');
				return;
			}else{
				$(".bszk-chart").html("");
				$(".bszk-chart").height("600");
				$(".bszk-chart").show();
			}
			var dataAxis = [];
			var data_contract = [];
			var data_cost = [];
			$.each(d,function(i,val){
				var actionDate = utilObj.shortenDate(val.actionDate);
				dataAxis.push(actionDate);
				data_contract.push(val.contractPrice.toString());
				data_cost.push(val.costPrice.toString());
			})
			
			var markpoint1={data:[],symbolSize:45};
			var markpoint2={data:[],symbolSize:45};
			$.each(data_contract, function(i,obj) {
				markpoint1.data.push({
					coord:[i,obj]
				});
			});
			$.each(data_cost, function(i,obj) {
				markpoint2.data.push({
					coord:[i,obj]
				});
			});
			
			var option = {
			    title: {
			        text: '合作价格对比图',
			  		show:false,
			    },
			    legend:{
			    		data:[{
			    			name:'合同价格',
			    			icon:'circle',	
			    		},{
			    			name:'成本价格',
			    			icon:'circle',	
			    		}],
			    		right:'20',
			    		top:'-1',
			    		textStyle:{
			    			fontSize:'20'
			    		}
			    },
			    tooltip:{
			        show:true,
			    },
			    xAxis: {
			        data: dataAxis,
			        axisLabel: {
			            inside: false,
			            textStyle: {
			                color: '#000'
			            }
			        },
			        axisTick: {
			            show: false
			        },
			        axisLine: {
			            show: true,
			            lineStyle:{
			            	color:'#1a88e4',
			            	width:2
			            }
			        },
			        z: 10
			    },
			    yAxis: {
			    	name:"合同价/万元",
			    	nameGap:35,
			    	nameTextStyle:{
			    		fontSize:20
			    	},
			        axisLine: {
			            show: false
			        },
			        axisTick: {
			            show: false
			        },
			        axisLabel: {
			            textStyle: {
			                color: '#999'
			            }
			        }
			    },
			    series: [
			        { // For data_contract
		        		name:'合同价格',
			            type: 'bar',
			            markPoint:markpoint1,
			            itemStyle: {
			                normal: {
			                	color: '#1a88e4',
//			                	label : {show: true}
			                },
			                
			            },
			            barWidth:30,
			            barGap:'0%',
			            barCategoryGap:'40%',
			            data: data_contract,
//			            animation: false
			        },
			        {// for data_cost
		        		name:'成本价格',
			            type: 'bar',
			            markPoint:markpoint2,
			            barWidth:30,
			            itemStyle: {
			                normal: {
			                    color: '#54c8fd',
//			                    label : {show: true}
			                },
			            },
			            data: data_cost
			        }
			    ]
			};
			//数据大于10条时，一开始界面显示10条
			if(d.length>10){
				option.dataZoom=[{
		            type: 'slider',
		            zoomLock:true,
		            start:0,
		            end:10/d.length*100
		        }]; 
			}
			
			var myChart = echarts.init(document.querySelector(".bszk-chart"));
			myChart.setOption(option);
			// Enable data zoom when user click bar.
			var zoomSize = 6;
			myChart.on('click', function (params) {
			    console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
			    myChart.dispatchAction({
			        type: 'dataZoom',
			        startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
			        endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data_contract.length - 1)]
			    });
			});
		},
	}
}
