<%--
  Created by IntelliJ IDEA.
  User: jackson.liu
  Date: 2017/2/27
  Time: 20:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
<script>


  $.ajax({
    url: '...',
    type: 'post',
    data: data,
    dataType: 'json',
    success: function (data, status, xhr) {
      console.log(xhr.getResponseHeader('Date'));
    }
  });



</script>
<head>
    <title></title>
</head>
<body>

</body>
</html>
