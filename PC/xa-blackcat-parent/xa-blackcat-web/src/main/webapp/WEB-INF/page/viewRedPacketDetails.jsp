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
    <link media="screen" href="<%=request.getContextPath()%>/redEnvelope/static/css/detail_duihuan.css" rel="stylesheet" type="text/css">
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.0.min.js"></script>
    <title>我的卡包</title>
</head>
	<script language="javascript">
		var infoDetail = '${redPacketInfo.redPacketNetbarType.netbarExplainConvertStr}';
		$(function(){
				var infoDetails = infoDetail.split("||");
				var infoHtml = "<ul>";
				for(var i=0;i<infoDetails.length;i++){
					infoHtml += "<li>"+infoDetails[i]+"</li>";
				}
				infoHtml += "</ul>";
				$("#infoId").html(infoHtml);
		});
	</script>
<body>
<div class="detailContent">
    <div class="detailPic">
        <img class="users" src="${imageServerPath}simg/${redPacketInfo.netbar.logo}.png">
        <c:if test="${state == 0 }">
        <img class="keduihuan" src="<%=request.getContextPath()%>/redEnvelope/static/image/keduihuan.png">
        </c:if>
        <c:if test="${state == 1 }">
        <img class="keduihuan" src="<%=request.getContextPath()%>/redEnvelope/static/image/v2_card_checked.png">
        </c:if>
        <c:if test="${state == 2 }">
        <img class="keduihuan" src="<%=request.getContextPath()%>/redEnvelope/static/image/v2_card_expired.png">
        </c:if>
        <p>${redPacketInfo.netbar.netbarName}</p>
        <h2>${redPacketInfo.redPacketNetbarType.details }</h2>
        <span>有效期：${redPacketInfo.beginTimeStr }-${redPacketInfo.expirationTimeStr }</span>
    </div>
    <div class="numberDetail">
        <h1>${redPacketInfo.cdkey }</h1>
        <span>兑换后卡券失效</span>
    </div>
    <div class="cardDetail">
        <div class="title">卡券详情</div>
        <div class="info"><span>${redPacketInfo.redPacketNetbarType.details }</span></div>


    </div>
    <div class="cardDetail">
        <div class="title">使用须知</div>
        <div class="info" id="infoId">
            <ul>
                <li></li>
            </ul>

        </div>


    </div>

</div>

</body>
</html>