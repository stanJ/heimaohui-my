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
    <link media="screen" href="<%=request.getContextPath()%>/redEnvelope/static/css/redEnvelope.css" rel="stylesheet" type="text/css">
    <title>红包列表</title>
</head>
<body>
<div class="mainContent">
    <div class="picContent">
        <img class="mainpic" src="<%=request.getContextPath()%>/redEnvelope/static/image/mainpic.png">
        <div class="picInfo">
            <img class="userPhoto" src="${imageServerPath}simg/${redPacketInfo.netbar.logo}.png">
            <p>${redPacketInfo.netbar.netbarName}</p>
            <span>恭喜发财，大吉大利！</span>
        </div>
    </div>
    <div class="redContent">
        ${packetCount }
    </div>
    <div class="commentDetails">
    	<c:forEach var="fuwa" items="${yilist}" varStatus="s">
        <div class="oneComment hasBottom">
            <img src="${imageServerPath}simg/${fuwa.user.headimgId}.png">
            <span class="name">${fuwa.user.nickname}</span>
            <span class="ticketinfo">${fuwa.redPacketNetbarType.details }</span>
        </div>
        </c:forEach>
    </div>
    <div class="shadow">
    	<%-- <div><a href="<%=request.getContextPath()%>/m/b5RedPacket/viewMyB5RedPacketList?userId=${userId}">进入我的卡包 > </a></div> --%>
    </div>

</div>
</body>
</html>