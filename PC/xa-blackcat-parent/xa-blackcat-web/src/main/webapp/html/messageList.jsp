<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.xa3ti.com/taglibs/dict" prefix="dict" %> 
<%@ taglib uri="http://www.xa3ti.com/taglibs/depend" prefix="depend" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>消息管理</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <link href="../css/bootstrap-3.2.0/css/bootstrap.css" rel="stylesheet" type="text/css" />
        <link href="../css/bootstrap-box.css" rel="stylesheet" type="text/css" />
        <link href="../css/bootstrap-page.css" rel="stylesheet" type="text/css" />
        <link href="../css/myStyle.css" rel="stylesheet" type="text/css" />
        <link href="../js/datapacker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="../js/resizableColumns/jquery.resizableColumns.css">
        
        <link rel="stylesheet" type="text/css" href="../css/reset.css" />
		<link rel="stylesheet" type="text/css" href="../css/global.css" />
		<link rel="stylesheet" type="text/css" href="../css/body.css" />
        
        <script type="text/javascript" src="../js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="../js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../js/resizableColumns/jquery.resizableColumns.js"></script>
        <script type="text/javascript" src="../js/jsviews.js"></script>
        <script type="text/javascript" src="../js/loadTmpl.js"></script>
        <script type="text/javascript" src="../js/base.js"></script>
        <script type="text/javascript" src="../js/tableGrid/jquery.tableGrid.js"></script>
        <script type="text/javascript" src="../js/datapacker/js/bootstrap-datetimepicker.min.js"></script>
        <script type="text/javascript" src="../js/datapacker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
         <!-- 自定义函数 -->
        <script type="text/javascript" src="../js/xa3ti.js"></script>
        <%String token=(String)request.getSession().getAttribute("token");%>
        <script type="text/javascript">
             var token='<%=token%>';
	        //记录当前页面的条件
        	function rememberRecord(){
        		
        		var currentModel=$("#currentModel").val();
        		sessionStorage.setItem("currentModel",currentModel);		//记录下当前的model名称
				/*****************记录当前的条件**********************************/
				                          				/*****************记录当前的条件**********************************/
				var pageSize= $("#selectPageSize").val();
				if(typeof(pageSize)=='undefined'){
					pageSize=10;
				}
				sessionStorage.setItem(currentModel+".pageSize",pageSize);		//当前页大小
				sessionStorage.setItem(currentModel+".nextPage",$("#_click_page").val());		//当前页
        	}
        	
        	//修正因删除最后一页的数据导致查询条件出错的问题
        	function reviseNextPage(){
        		
				var last=$("#_last").val();
				var first=$("#_first").val();
				var currentContentCount=$("#_currentCount").val();
				if(last=="true" && first=="false"){			//如果是最后一页
					if(delArray.length==currentContentCount){
						var currentModel=$("#currentModel").val();
						var nextPage=sessionStorage.getItem(currentModel+".nextPage");
						sessionStorage.setItem(currentModel+".nextPage",nextPage-1);
					}
				}
        	}
	        
        	//从后台取数据，并加载模板
        	function displayContent(){
	        	var currentModel=sessionStorage.getItem("currentModel");
	        	var nextPage=0;
	        	var pageSize=10;
	        	/***********************列出条件*************************************/
	        	 					var channel="";
				  					var receiverId="";
				  					var senderId="";
				  					var sendDate="";
				  					var title="";
				  					var content="";
				  					var receiverType="";
				  					var priority="";
				  					var retryTimes="";
				  					var mobile="";
				  					var mstatus="";
				  					var failureReason="";
				  					var groupId="";
				 	        	/***********************列出条件*************************************/
	        	if(currentModel==null){  
        			console.log("未进入session区域");
        			if($("#_click_page").val() != undefined && $("#_click_page").val()){
    					nextPage = $("#_click_page").val();
    				}
    				if(typeof(nextPage) == 'undefined' || nextPage==""){
    					nextPage = 0;
    					$("#_click_page").val(nextPage);
    				}
    				if($("#selectPageSize").val() && $("#selectPageSize").val() != undefined){
    					pageSize = $("#selectPageSize").val();
    				}
    				if(typeof(pageSize) == 'undefined' || pageSize==""){
    					pageSize = 10;
    					$("#selectPageSize").val(pageSize);
    				}
    				
    				/******************从页面中取出页面中的条件*******************************/
    				  					  channel=$("#search_channel").val();
					    					  receiverId=$("#search_receiverId").val();
					    					  senderId=$("#search_senderId").val();
					    					  sendDate=$("#search_sendDate").val();
					    					  title=$("#search_title").val();
					    					  content=$("#search_content").val();
					    					  receiverType=$("#search_receiverType").val();
					    					  priority=$("#search_priority").val();
					    					  retryTimes=$("#search_retryTimes").val();
					    					  mobile=$("#search_mobile").val();
					    					  mstatus=$("#search_mstatus").val();
					    					  failureReason=$("#search_failureReason").val();
					    					  groupId=$("#search_groupId").val();
					      				/******************从页面中取出页面中的条件*******************************/
        		}else{
        			console.log("进入了session区域");
        			nextPage =sessionStorage.getItem(currentModel+".nextPage");
        			pageSize =sessionStorage.getItem(currentModel+".pageSize");
        			
        			/******************从sessionStorge中取出页面中的条件*******************************/
        			  					  channel=sessionStorage.getItem(currentModel+".channel");
					   					  receiverId=sessionStorage.getItem(currentModel+".receiverId");
					   					  senderId=sessionStorage.getItem(currentModel+".senderId");
					   					  sendDate=sessionStorage.getItem(currentModel+".sendDate");
					   					  title=sessionStorage.getItem(currentModel+".title");
					   					  content=sessionStorage.getItem(currentModel+".content");
					   					  receiverType=sessionStorage.getItem(currentModel+".receiverType");
					   					  priority=sessionStorage.getItem(currentModel+".priority");
					   					  retryTimes=sessionStorage.getItem(currentModel+".retryTimes");
					   					  mobile=sessionStorage.getItem(currentModel+".mobile");
					   					  mstatus=sessionStorage.getItem(currentModel+".mstatus");
					   					  failureReason=sessionStorage.getItem(currentModel+".failureReason");
					   					  groupId=sessionStorage.getItem(currentModel+".groupId");
					     				/******************从sessionStorge中取出页面中的条件*******************************/
    				/******************将原来记住的值传重新赋值到页面上*******************************/
    				      				$("#search_channel").val(channel);
    				        				$("#search_receiverId").val(receiverId);
    				        				$("#search_senderId").val(senderId);
    				        				$("#search_sendDate").val(sendDate);
    				        				$("#search_title").val(title);
    				        				$("#search_content").val(content);
    				        				$("#search_receiverType").val(receiverType);
    				        				$("#search_priority").val(priority);
    				        				$("#search_retryTimes").val(retryTimes);
    				        				$("#search_mobile").val(mobile);
    				        				$("#search_mstatus").val(mstatus);
    				        				$("#search_failureReason").val(failureReason);
    				        				$("#search_groupId").val(groupId);
    				      				/******************将原来记住的值传重新赋值到页面上*******************************/
    				
        		}
	        	//字段名称拼接规则search_为固定查询标识,EQ为等于,filed为字段名
				//EQ等于, IN包含, ISNULL空, LIKE, GT大于, LT小于, GTE大于等于, LTE小于等于, NE不等于,LIKEIGNORE不区分大小写,FINDINSET 查找某个字符是否在集合中
				var _jsonFilter = "{";
				
				/************************拼装查询条件************************************/
				  					if(channel!=null && channel.length>0){
					_jsonFilter += "'search_EQ_channel':'"+channel+"',";
				}
			        					if(receiverId!=null && receiverId.length>0){
					_jsonFilter += "'search_EQ_receiverId':'"+receiverId+"',";
				}
			        					if(senderId!=null && senderId.length>0){
					_jsonFilter += "'search_EQ_senderId':'"+senderId+"',";
				}
			        					if(sendDate!=null && sendDate.length>0){
					_jsonFilter += "'search_EQ_sendDate':'"+sendDate+"',";
				}
			        					if(title!=null && title.length>0){
					_jsonFilter += "'search_EQ_title':'"+title+"',";
				}
			        					if(content!=null && content.length>0){
					_jsonFilter += "'search_EQ_content':'"+content+"',";
				}
			        					if(receiverType!=null && receiverType.length>0){
					_jsonFilter += "'search_EQ_receiverType':'"+receiverType+"',";
				}
			        					if(priority!=null && priority.length>0){
					_jsonFilter += "'search_EQ_priority':'"+priority+"',";
				}
			        					if(retryTimes!=null && retryTimes.length>0){
					_jsonFilter += "'search_EQ_retryTimes':'"+retryTimes+"',";
				}
			        					if(mobile!=null && mobile.length>0){
					_jsonFilter += "'search_EQ_mobile':'"+mobile+"',";
				}
			        					if(mstatus!=null && mstatus.length>0){
					_jsonFilter += "'search_EQ_mstatus':'"+mstatus+"',";
				}
			        					if(failureReason!=null && failureReason.length>0){
					_jsonFilter += "'search_EQ_failureReason':'"+failureReason+"',";
				}
			        					if(groupId!=null && groupId.length>0){
					_jsonFilter += "'search_EQ_groupId':'"+groupId+"',";
				}
			      				/************************拼装查询条件************************************/
				if(_jsonFilter != "{"){
					_jsonFilter = _jsonFilter.substring(0,_jsonFilter.length - 1);
				}
				_jsonFilter += "}";
				var url = "../cms/message/findMessageNEStatusPage";
				$.ajax({
					url:url,
					dataType:'json',
					type:'POST',
					data:{
						jsonFilter: _jsonFilter,
						nextPage: nextPage,
						pageSize:pageSize
					},
					success:function(data){
						//如果当前model对象不为空，相当于本次请求是通过编辑页面跳转过来的，此时清除掉该对象，保证再次点击查询按钮时更新查询条件
						if(currentModel!=null){
							sessionStorage.removeItem("currentModel");
						}
						if(data.code == 1){
			            	var tblContentHtml = $("#tableContentTmple").render(data.object.content);
			            	$("#mycontent").html(tblContentHtml);
			            	//加载分页器
			            	loadTmpl.renderExternalTemplate("page", "#displayPage", data.object);
						}
						else{
							alert(data.message);
						}
					}
				});
        	}
			
			//添加操作按钮事件
			$(function(){
			
				//回车事件
				document.onkeydown = function(e){
				    var ev = document.all ? window.event : e;
				    if(ev.keyCode==13) {
				    	var currentModel=sessionStorage.getItem("currentModel");
						if(currentModel!=null){
							sessionStorage.removeItem("currentModel");
						}
				    	displayContent();
				    	return false;
				     }
				};
				
				$("#example2").resizableColumns({});	//列拖动
				//行高亮,选中
				$("#example2").tableGrid({
					checkAllId:"_selectAll",	//全选框的ID属性
					singleCheckboxClass:"ckSelect",
					paging:"displayPage",
					pageAjax:function(){
						displayContent();
					}
				});
				
				//点击搜索
				$("#search").click(function(){
					var currentModel=sessionStorage.getItem("currentModel");
					if(currentModel!=null){
						sessionStorage.removeItem("currentModel");
					}
					$("#_click_page").val(0);
					displayContent();
				});
						
				//点击查询重置按钮
				$("#reset").click(function(){
					      				 $("#search_channel").val('');
					        				 $("#search_receiverId").val('');
					        				 $("#search_senderId").val('');
					        				 $("#search_sendDate").val('');
					        				 $("#search_title").val('');
					        				 $("#search_content").val('');
					        				 $("#search_receiverType").val('');
					        				 $("#search_priority").val('');
					        				 $("#search_retryTimes").val('');
					        				 $("#search_mobile").val('');
					        				 $("#search_mstatus").val('');
					        				 $("#search_failureReason").val('');
					        				 $("#search_groupId").val('');
					  				});
						
				//点击新增按钮
				$("#addSome").click(function(){
					rememberRecord();
					var url = "messageEdit.jsp";
					window.location.href=url;
				});
				
				//点击修改按钮
				$("#updateSome").click(function(){
					var updateArray = [];
					$(".ckSelect").each(function(){
                    	var check=$(this).is(':checked');
                    	if(check){
							var _id =$(this).attr("ckId");
							updateArray.push(_id);
                        }
                    });
					if(updateArray.length == 0){
						alert("请选择要编辑的项"); return;
					}
					if(updateArray.length > 1){
						alert("每次只能编辑一项"); return;
					}
					rememberRecord();
					var url = "messageEdit.jsp?id="+updateArray[0];
			    	window.location.href=url;
				});
				
				//点击删除按钮
				$("#deleteSome").click(function() {
					var checkError = false;
					var delArray = [];
					$(".ckSelect").each(function(){
                    	var check = $(this).is(':checked');
                    	if(check){
							var _id = $(this).attr("ckId");
							delArray.push(_id);
                        }
                    });
                    if(delArray.length == 0){
						alert("请选择要删除的项"); return;
                    }
                    if(confirm("你确定要删除选中项吗？")){
                    	var url="../cms/message/operateMessageByIds?modelIds=" + delArray.join(",")+"&token="+encodeURIComponent(token);;
                    	$.ajax({
							url: url,
							type: 'post',
							dataType: 'json',
							success: function(data){
								if(data.code==1){
									rememberRecord();
									//修正在删除最后一页的数据可能导致查询条件出问题的bug
									reviseNextPage();
									displayContent();
								}else{
									alert(data.message);
								}
							}
                        });
                    }
				});
				
				
				//点击锁定按钮
				$("#lockSome").click(function() {
					var checkError = false;
					var delArray = [];
					$(".ckSelect").each(function(){
                    	var check = $(this).is(':checked');
                    	if(check){
							var _id = $(this).attr("ckId");
							delArray.push(_id);
                        }
                    });
                    if(delArray.length == 0){
						alert("请选择要锁定的项"); return;
                    }
                    if(confirm("你确定要锁定选中项吗？")){
                    	var url="../cms/message/operateMessageByIds";
                    	$.ajax({
							url: url,
							type: 'post',
							data:{
								modelIds:delArray.join(","),
								status:-1
							},
							dataType: 'json',
							success: function(data){
								if(data.code==1){
									displayContent();
								}else{
									alert(data.message);
								}
							}
                        });
                    }
				});
				
				
				//点击启用按钮
				$("#openSome").click(function() {
					var checkError = false;
					var delArray = [];
					$(".ckSelect").each(function(){
                    	var check = $(this).is(':checked');
                    	if(check){
							var _id = $(this).attr("ckId");
							delArray.push(_id);
                        }
                    });
                    if(delArray.length == 0){
						alert("请选择要启用的项"); return;
                    }
                    if(confirm("你确定要启用选中项吗？")){
                    	var url="../cms/message/operateMessageByIds";
                    	$.ajax({
							url: url,
							type: 'post',
							data:{
								modelIds:delArray.join(","),
								status:1
							},
							dataType: 'json',
							success: function(data){
								if(data.code==1){
									displayContent();
								}else{
									alert(data.message);
								}
							}
                        });
                    }
				});
				
				
				//点击发布按钮
				$("#publishSome").click(function() {
					var checkError = false;
					var delArray = [];
					$(".ckSelect").each(function(){
                    	var check = $(this).is(':checked');
                    	if(check){
							var _id = $(this).attr("ckId");
							delArray.push(_id);
                        }
                    });
                    if(delArray.length == 0){
						alert("请选择要发布的项"); return;
                    }
                    if(confirm("你确定要发布选中项吗？")){
                    	var url="../cms/message/operateMessageByIds";
                    	$.ajax({
							url: url,
							type: 'post',
							data:{
								modelIds:delArray.join(","),
								status:2
							},
							dataType: 'json',
							success: function(data){
								if(data.code==1){
									displayContent();
								}else{
									alert(data.message);
								}
							}
                        });
                    }
				});
				
				
			});
        </script>
    </head>
    <body>
    <input type="hidden" id="currentModel" value="Message" />
		<section>
			<div class="col-xs-12">
				<div class="box">
					<div class="span1">
						<span class="badge badge-info">XXXX   &gt; 消息管理</span>
					</div>
					<div class="box-header">
						<form id="search_form" class="form-inline" >
							<div class="tabSelect">
															</div>
							<br />  
						</form>
					</div>
					<div class="box-header" style="text-align:right;margin:0px auto 0px auto;">
					 <input type="button" value="查询" id="search" class="btn btn-primary">
					 <input type="button" value="重置" id="reset" class="btn btn-info">
					</div>
					<div class="box-header">
						<input type="button" value="新增" id="addSome" class="btn btn-success">
						<input type="button" value="编辑" id="updateSome" class="btn btn-info">
						<!--<input type="button" value="启用" id="openSome" class="btn btn-success">
						<input type="button" value="锁定" id="lockSome" class="btn btn">
						<input type="button" value="发布" id="publishSome" class="btn btn-warning">
						-->
						<input type="button" value="删除" id="deleteSome" class="btn btn-danger">
					</div>
					<div class="box-body table-responsive">
						<table id="example2" class="tab1">
							<thead>
								<tr>
									<th width="30"><input type="checkbox" id="_selectAll" /></th>
									 									 <th width="80">渠道</th>
									  									 <th width="80">接收者</th>
									  									 <th width="80">发送者</th>
									  									 <th width="80">发送日期</th>
									  									 <th width="80">标题</th>
									  									 <th width="80">内容</th>
									  									 <th width="80">接收者类型</th>
									  									 <th width="80">优先级</th>
									  									 <th width="80">重试次数</th>
									  									 <th width="80">手机</th>
									  									 <th width="80">发送状态</th>
									  									 <th width="80">失败原因</th>
									  									 <th width="80">接受者群号</th>
									 									<th width="80">状态</th>
								  </tr>
							</thead>
							<!-- 表格内容 start -->
							<tbody id="mycontent"></tbody>
							<!-- 表格内容 end -->
						</table>
						<!-- 分页标签 start -->
						<div class="row" id="displayPage"></div>
						<!-- 分页标签 end -->
						<script id="tableContentTmple" type="text/x-jsrender">
							<tr>
								<td><input id="ck_{{:tid}}" ckId="{{:tid}}" ckStatus="{{:status}}" class="ckSelect" type="checkbox" /></td>
								 								   								  <td>{{:channel}}</td> 
								   								  								   								  <td>{{:receiverId}}</td> 
								   								  								   								  <td>{{:senderId}}</td> 
								   								  								   								  <td>{{:sendDate}}</td> 
								   								  								   								  <td>{{:title}}</td> 
								   								  								   								  <td>{{:content}}</td> 
								   								  								   								  <td>{{:receiverType}}</td> 
								   								  								   								  <td>{{:priority}}</td> 
								   								  								   								  <td>{{:retryTimes}}</td> 
								   								  								   								  <td>{{:mobile}}</td> 
								   								  								   								  <td>{{:mstatus}}</td> 
								   								  								   								  <td>{{:failureReason}}</td> 
								   								  								   								  <td>{{:groupId}}</td> 
								   								 								<td>{{if status==1}}正常{{else status==2}}已发布{{else status==3}}已删除{{else status==-1}}已锁定{{else}}无效{{/if}}</td>
							</tr>
						</script>
					</div>
				</div>
			</div>
		</section>
    </body>
</html>