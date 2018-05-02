<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.xa3ti.com/taglibs/dict" prefix="dict" %>
<%@ taglib uri="http://www.xa3ti.com/taglibs/depend" prefix="depend" %>  
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title>专家 编辑页面</title>
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
				var url ='../cms/expert/findExpertById';
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
								  									$("#name").val(data.object.name);
								     									$("#birth").val(data.object.birth);
								     									$("#contact").val(data.object.contact);
								     									$("#province").val(data.object.province);
								 									$("#city").val(data.object.city);
								 									$("#district").val(data.object.district);
								     									$("#gender").val(data.object.gender);
								     									$("#certificate").val(data.object.certificate);
								     									$("#email").val(data.object.email);
								     									$("#wechat").val(data.object.wechat);
								    								   set_checkbox_value("category",data.object.category);
								      									$("#socialFunction").val(data.object.socialFunction);
								     									$("#comment").val(data.object.comment);
								     									$("#filePath").val(data.object.filePath);
								     									$("#otherCategory").val(data.object.otherCategory);
								   								/********************加载数据*****************************/
								/********************加载字典数据*****************************/
								        								setDict_DISTRICT(data.object.province+","+data.object.city+","+data.object.district );     								setDict_SEX(data.object.gender );                     								/********************加载字典数据*****************************/
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
							submit_url = "../cms/expert/saveExpert";
						}
						else{
							submit_url = "../cms/expert/updateExpert";
						}
			        	/** 富文本操作预留,如需要可打开此注释
						var content=UE.getEditor('myEditor').getContent();*/
						$.ajax({
							url:submit_url,
							type:"POST",
							data:{
								  								name:$("#name").val(),
								    								birth:$("#birth").val(),
								    								contact:$("#contact").val(),
								    								province:$("#province").val(),
																city:$("#city").val(),
																district:$("#district").val(),
								    								gender:$("#gender").val(),
								    								certificate:$("#certificate").val(),
								    								email:$("#email").val(),
								    								wechat:$("#wechat").val(),
								   								category:deparam($("input[name='category']:checked").serialize()),
								   								socialFunction:$("#socialFunction").val(),
								    								comment:$("#comment").val(),
								    								filePath:$("#filePath").val(),
								    								otherCategory:$("#otherCategory").val(),
								  								tid:tid,
								token:token
								
							},
							success:function(data){
								if(data.code==1){
									window.location.href="expertList.jsp";
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
						url:'../cms/expert/photoUpload',
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
					<span class="badge badge-info">XX管理    &gt; 专家管理    &gt;  专家编辑</span>
				</div>
              	<div class="box">
					<div class="box-header">
						<br>
							<input type="button" value="确定" id="saveOrupdate" class="btn btn-success">
							<a href="#" onclick="document.location.href='expertList.jsp';" class="btn btn-info" >返回</a>
					</div>
					<div class="tab" id="usercontent">
						<input type="hidden" id="tid"/>
						<fieldset>
						             								 								<li class="controls">姓名:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写姓名" data-rule="姓名:required;length[0~50]" id="name" name="name" value="" />
								</li>
								  								<li class="controls">出生日期:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写出生日期" data-rule="length[0~10]" id="birth" name="birth" value="" />
								</li>
								  								<li class="controls">联系方式:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写联系方式" data-rule="length[0~100]" id="contact" name="contact" value="" />
								</li>
								   								<dict:list  key="DISTRICT" id="province,city,district" name="province,city,district" title="居住地省,市,区" titletag="li" titletagclass="controls" outtag="li"
									outtagstyle="width: 300px; text-align: left;" datarule="required;"/>
									 								   								<dict:list  key="SEX" id="gender" name="gender" title="性别" titletag="li" titletagclass="controls" outtag="li"
									outtagstyle="width: 300px; text-align: left;" datarule="required;"/>
									 								  								<li class="controls">证件号:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写证件号" data-rule="length[0~50]" id="certificate" name="certificate" value="" />
								</li>
								  								<li class="controls">邮箱:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写邮箱" data-rule="length[0~50]" id="email" name="email" value="" />
								</li>
								  								<li class="controls">微信:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写微信" data-rule="length[0~50]" id="wechat" name="wechat" value="" />
								</li>
								     <depend:checkbox  key="ExpertCategory.id" show="ExpertCategory.name" id="category" name="category" title="专家类别" titletag="li" titletagclass="controls" outtag="li"
									outtagstyle="width: 300px; text-align: left;" datarule=""/>
								   								  								<li class="controls">社会职务:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写社会职务" data-rule="length[0~2000]" id="socialFunction" name="socialFunction" value="" />
								</li>
								  								<li class="controls">备注:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写备注" data-rule="length[0~2000]" id="comment" name="comment" value="" />
								</li>
								  								<li class="controls">附件路径:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写附件路径" data-rule="length[0~200]" id="filePath" name="filePath" value="" />
								</li>
								  								<li class="controls">专家类别其他:</li>
								<li style="width:300px;text-align:left;">
								<input type="text" placeholder="请填写专家类别其他" data-rule="length[0~200]" id="otherCategory" name="otherCategory" value="" />
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