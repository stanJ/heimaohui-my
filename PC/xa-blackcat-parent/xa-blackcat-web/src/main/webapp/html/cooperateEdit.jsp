<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.xa3ti.com/taglibs/dict" prefix="dict" %>
<%@ taglib uri="http://www.xa3ti.com/taglibs/depend" prefix="depend" %>  
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>配合度 编辑页面</title>
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
				var url ='../cms/cooperate/findCooperateById';
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
								  									$("#expertId").val(data.object.expertId);
								     									$("#customerName").val(data.object.customerName);
								     									$("#actionDate").val(data.object.actionDate);
								     									$("#actionAddress").val(data.object.actionAddress);
								     									$("#contractPrice").val(data.object.contractPrice);
								     									$("#costPrice").val(data.object.costPrice);
								     									$("#expertContactor").val(data.object.expertContactor);
								     									$("#comment").val(data.object.comment);
								     									$("#pics").val(data.object.pics);
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
							submit_url = "../cms/cooperate/saveCooperate";
						}
						else{
							submit_url = "../cms/cooperate/updateCooperate";
						}
			        	/** 富文本操作预留,如需要可打开此注释
						var content=UE.getEditor('myEditor').getContent();*/
						$.ajax({
							url:submit_url,
							type:"POST",
							data:{
								  								expertId:$("#expertId").val(),
								    								customerName:$("#customerName").val(),
								    								actionDate:$("#actionDate").val(),
								    								actionAddress:$("#actionAddress").val(),
								    								contractPrice:$("#contractPrice").val(),
								    								costPrice:$("#costPrice").val(),
								    								expertContactor:$("#expertContactor").val(),
								    								comment:$("#comment").val(),
								    								pics:$("#pics").val(),
								  								tid:tid,
								token:token
								
							},
							success:function(data){
								if(data.code==1){
									window.location.href="cooperateList.jsp";
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
						url:'../cms/cooperate/photoUpload',
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
					<span class="badge badge-info">XX管理    &gt; 配合度管理    &gt;  配合度编辑</span>
				</div>
              	<div class="box">
					<div class="box-header">
						<br>
							<input type="button" value="确定" id="saveOrupdate" class="btn btn-success">
							<a href="#" onclick="document.location.href='cooperateList.jsp';" class="btn btn-info" >返回</a>
					</div>
					<div class="tab" id="usercontent">
						<input type="hidden" id="tid"/>
						<fieldset>
						         								    <depend:list  key="Expert.id" show="Expert.name" id="expertId" name="expertId" title="专家id" titletag="li" titletagclass="controls" outtag="li"
									outtagstyle="width: 300px; text-align: left;" datarule="required;"/>
								   								  								<li class="controls">客户名称:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写客户名称" data-rule="客户名称:required;length[0~50]" id="customerName" name="customerName" value="" />
								</li>
								  								<li class="controls">活动时间:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写活动时间" data-rule="活动时间:required;length[0~10]" id="actionDate" name="actionDate" value="" />
								</li>
								  								<li class="controls">活动地址:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写活动地址" data-rule="length[0~200]" id="actionAddress" name="actionAddress" value="" />
								</li>
								  								<li class="controls">合同价:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写合同价" data-rule="合同价:required;length[0~10]" id="contractPrice" name="contractPrice" value="" />
								</li>
								  								<li class="controls">成本价:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写成本价" data-rule="成本价:required;length[0~10]" id="costPrice" name="costPrice" value="" />
								</li>
								  								<li class="controls">专家联系人:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写专家联系人" data-rule="length[0~50]" id="expertContactor" name="expertContactor" value="" />
								</li>
								  								<li class="controls">评价:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写评价" data-rule="length[0~2000]" id="comment" name="comment" value="" />
								</li>
								  								<li class="controls">现场照片:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写现场照片" data-rule="length[0~200]" id="pics" name="pics" value="" />
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