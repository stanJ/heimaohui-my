<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.xa3ti.com/taglibs/dict" prefix="dict" %>
<%@ taglib uri="http://www.xa3ti.com/taglibs/depend" prefix="depend" %>  
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>消息 编辑页面</title>
        <meta content='width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no' name='viewport'>
        <!-- 基本操作控件,保留 -->
        <link href="../css/bootstrap-3.2.0/css/bootstrap.css" rel="stylesheet" type="text/css" />
        <link href="../css/bootstrap-box.css" rel="stylesheet" type="text/css" />
        <link href="../css/bootstrap-page.css" rel="stylesheet" type="text/css" />
        <link href="../css/myStyle.css" rel="stylesheet" type="text/css" />
        <link href="../js/validator/jquery.validator.css" rel="stylesheet" type="text/css" />
        <link href="../js/datapacker/css/bootstrap-datetimepicker.min.css" rel="stylesheet" type="text/css" />
		<link rel="stylesheet" type="text/css" href="../css/reset.css" />
		<link rel="stylesheet" type="text/css" href="../css/global.css" />
		<link rel="stylesheet" type="text/css" href="../css/body.css" />
        <script type="text/javascript" src="../js/jquery-1.11.0.min.js"></script>
        <script type="text/javascript" src="../js/bootstrap.min.js"></script>
        <script type="text/javascript" src="../js/jsviews.js"></script>
        <script type="text/javascript" src="../js/loadTmpl.js"></script>
        <script type="text/javascript" src="../js/base.js"></script>
        <script	type="text/javascript" src="../js/validator/jquery.validator.js"></script>
		<script type="text/javascript" src="../js/validator/local/zh_CN.js"></script>
		<!-- 富文本编辑依赖文件,不用建议删除 -->
    	<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.config.js"></script>
    	<script type="text/javascript" charset="utf-8" src="../ueditor/ueditor.all.min.js"></script>
    	<script type="text/javascript" charset="utf-8" src="../ueditor/lang/zh-cn/zh-cn.js"></script>
    	<!-- 日期操作控件 -->
        <script type="text/javascript" src="../js/datapacker/js/bootstrap-datetimepicker.min.js"></script>
        <script type="text/javascript" src="../js/datapacker/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
        <!-- ajax上传控件 -->
        <script type="text/javascript" src="../js/ajaxfileupload.js"></script>
        <!-- 自定义函数 -->
        <script type="text/javascript" src="../js/xa3ti.js"></script>
        <%String token=(String)request.getSession().getAttribute("token");%>
        <script type="text/javascript">
    		//富文本处理全局参数
        	var ue;
	    	
			$(function(){
			    var token='<%=token%>';
				var tid=getUrlParam("id");
				if(tid==""){
					alert("id参数不能为空");
					return;
				}
				$("#tid").val(tid);
				var url ='../cms/message/findMessageById';
				if(tid!=null && tid!=0 && tid!=""){
					$.ajax({
						url:url,
						type:'post',
						dataType:'json',
						data:{
							modelId:tid
						},
						success:function(data){
							if(data.code == 1){
								/********************加载数据*****************************/
								  									$("#channel").val(data.object.channel);
								     									$("#receiverId").val(data.object.receiverId);
								     									$("#senderId").val(data.object.senderId);
								     									$("#sendDate").val(data.object.sendDate);
								     									$("#title").val(data.object.title);
								     									$("#content").val(data.object.content);
								     									$("#receiverType").val(data.object.receiverType);
								     									$("#priority").val(data.object.priority);
								     									$("#retryTimes").val(data.object.retryTimes);
								     									$("#mobile").val(data.object.mobile);
								     									$("#mstatus").val(data.object.mstatus);
								     									$("#failureReason").val(data.object.failureReason);
								     									$("#groupId").val(data.object.groupId);
								   								/********************加载数据*****************************/
								/********************加载字典数据*****************************/
								                          								/********************加载字典数据*****************************/
							}else{
								alert(data.message);
							}
						}
					});
				}
				
				/** 富文本操作预留,需要可打开此注释
				ue = UE.getEditor('myEditor');*/
				/** 日期处理预留,需要可打开此注释
				$("#startTime").datetimepicker({minView: "month",format: 'yyyy-mm-dd',language : 'zh-CN',autoclose: true});
				$("#endTime").datetimepicker({minView: "month",format: 'yyyy-mm-dd',language : 'zh-CN',autoclose: true});*/
				
				$("#demo_31").validator({
					valid:function(form){
						/** 图片上传处理预留,有图片上传可打开此注释
			    		if(imgUrl.length == 0){
							alert("请上传图片!");
							return;
						}
						if(height&&width&&(height != v_height || width != )){
							alert("为保证终端显示效果,该图片像素建议为"+v_height+"px*"+v_width+"px,请重新上传!");
							return;
						}*/
					    var tid=$("#tid").val();
						var submit_url = "";
						if(typeof(tid)=="undefined" || tid == "" || tid == "0"){
							//新增
							tid = 0;
							submit_url = "../cms/message/saveMessage";
						}
						else{
							submit_url = "../cms/message/updateMessage";
						}
			        	/** 富文本操作预留,如需要可打开此注释
						var content=UE.getEditor('myEditor').getContent();*/
						$.ajax({
							url:submit_url,
							type:"POST",
							data:{
								  								channel:$("#channel").val(),
								    								receiverId:$("#receiverId").val(),
								    								senderId:$("#senderId").val(),
								    								sendDate:$("#sendDate").val(),
								    								title:$("#title").val(),
								    								content:$("#content").val(),
								    								receiverType:$("#receiverType").val(),
								    								priority:$("#priority").val(),
								    								retryTimes:$("#retryTimes").val(),
								    								mobile:$("#mobile").val(),
								    								mstatus:$("#mstatus").val(),
								    								failureReason:$("#failureReason").val(),
								    								groupId:$("#groupId").val(),
								  								tid:tid,
								token:token
								
							},
							success:function(data){
								if(data.code==1){
									window.location.href="messageList.jsp";
								}
								else{
									alert(data.message);
								}
							}
						});
					}
				}).on("click","#saveOrupdate",function(e){
					$(e.delegateTarget).trigger("validate");
				});
				/** 图片上传处理预留,有图片上传可打开此注释
				$(document).on('change','#uploadPhotoFile',function(){
					$.ajaxFileUpload({
						url:'../cms/message/photoUpload',
						secureuri:false,
						fileElementId:'uploadPhotoFile',
						type:'POST',
						dataType: 'json',			
						success: function (data, status){
							if(data.code){
							                          								$("#myImageShow").attr('src',"../"+data.object.picturePath);
								$("#myImageShow").attr('width','100px');
								$("#myImageShow").attr('height','100px');
								height = data.object.height;
								width = data.object.width;
								$("#imagePx").html("宽:"+width+"px,高:"+height+"px");
								if(height != v_height || width != v_width){
									alert("为保证终端显示效果,该图片像素建议为"+v_height+"px*"+v_width+"px,请重新上传!");
								}
							}
							else{
								alert(data.message);
							}
						},
						error: function (data, status, e){
							alert(data);
						}
					});
				});*/
			});
        </script>
    </head>
    <body>
    	<section>
      	<form id="demo_31" class="form-inline">
        	<div class="col-xs-12">
				<div class="span1">
					<span class="badge badge-info">XX管理    &gt; 消息管理    &gt;  消息编辑</span>
				</div>
              	<div class="box">
					<div class="box-header">
						<br>
							<input type="button" value="确定" id="saveOrupdate" class="btn btn-success">
							<a href="#" onclick="document.location.href='messageList.jsp';" class="btn btn-info" >返回</a>
					</div>
					<div class="tab" id="usercontent">
						<input type="hidden" id="tid"/>
						<fieldset>
						             								 								<li class="controls">渠道:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写渠道" data-rule="渠道:required;length[0~50]" id="channel" name="channel" value="" />
								</li>
								  								<li class="controls">接收者:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写接收者" data-rule="接收者:required;length[0~10]" id="receiverId" name="receiverId" value="" />
								</li>
								  								<li class="controls">发送者:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写发送者" data-rule="发送者:required;length[0~10]" id="senderId" name="senderId" value="" />
								</li>
								  								<li class="controls">发送日期:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写发送日期" data-rule="length[0~20]" id="sendDate" name="sendDate" value="" />
								</li>
								  								<li class="controls">标题:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写标题" data-rule="标题:required;length[0~500]" id="title" name="title" value="" />
								</li>
								  								<li class="controls">内容:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写内容" data-rule="内容:required;length[0~2000]" id="content" name="content" value="" />
								</li>
								  								<li class="controls">接收者类型:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写接收者类型" data-rule="接收者类型:required;length[0~10]" id="receiverType" name="receiverType" value="" />
								</li>
								  								<li class="controls">优先级:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写优先级" data-rule="优先级:required;length[0~10]" id="priority" name="priority" value="" />
								</li>
								  								<li class="controls">重试次数:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写重试次数" data-rule="length[0~10]" id="retryTimes" name="retryTimes" value="" />
								</li>
								  								<li class="controls">手机:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写手机" data-rule="length[0~50]" id="mobile" name="mobile" value="" />
								</li>
								  								<li class="controls">发送状态:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写发送状态" data-rule="发送状态:required;length[0~10]" id="mstatus" name="mstatus" value="" />
								</li>
								  								<li class="controls">失败原因:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写失败原因" data-rule="length[0~500]" id="failureReason" name="failureReason" value="" />
								</li>
								  								<li class="controls">接受者群号:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写接受者群号" data-rule="length[0~10]" id="groupId" name="groupId" value="" />
								</li>
								 						</fieldset>
						<br/>
					</div>
				</div>
			</div>
		</form>
		</section>
    </body>
</html>