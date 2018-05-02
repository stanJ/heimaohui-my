<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head lang="en">
    <meta charset="UTF-8">
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
    <meta name="format-detection" content="telephone=no">
    <meta name="apple-mobile-web-app-status-bar-style" content="black" />
    <meta name="viewport" content="height=device-height,width=device-width,initial-scale=1,maximum-scale=1,minimum-scale=1,user-scalable=no" />
    <link media="screen" href="<%=request.getContextPath()%>/redEnvelope/static/css/common.css" rel="stylesheet" type="text/css">
    <link media="screen" href="<%=request.getContextPath()%>/redEnvelope/static/css/redEnvlist.css" rel="stylesheet" type="text/css">
    <title>我的卡包</title>
</head>
	<script language="javascript">
		function intoDetails(id){
			window.location.href = "<%=request.getContextPath()%>/m/b5RedPacket/viewB5RedPacketDetails?id="+id;
		}
	</script>
<body>
<div class="listContent">
    <c:forEach var="fuwa" items="${myRedPacketlist}" varStatus="s">
    <div class="listItem" onclick="intoDetails('${fuwa.id}');">
        <img src="${imageServerPath}simg/${fuwa.netbar.logo}.png">
        <div style="overflow:hidden;">
            <span class="username">${fuwa.netbar.netbarName}</span>
            <span class="date">${fuwa.robTimeStr}</span>
        </div>
        <div class="detail">${fuwa.redPacketNetbarType.details }，有效期至${fuwa.expirationTimeCNStr }。</div>
    </div>
    </c:forEach>
</div>
</body>
</html>