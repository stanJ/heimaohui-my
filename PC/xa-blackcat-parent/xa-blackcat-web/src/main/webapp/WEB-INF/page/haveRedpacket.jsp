<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
	<link media="screen" href="<%=request.getContextPath()%>/redEnvelope/static/css/redH5.css" rel="stylesheet" type="text/css">
    <link media="screen" href="<%=request.getContextPath()%>/redEnvelope/static/css/redEnvelope.css" rel="stylesheet" type="text/css">
<title>红包打开</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.0.min.js"></script>
</head>

<body>

<div class="frame">
  <div class="above">
      <div id="btn">拆红包</div>
      <div class="head-sculpture">
         <img src="${imageServerPath}simg/${redPacketInfo.netbar.logo}.png" />
      </div>
      <div class="username">
      ${redPacketInfo.netbar.netbarName}
      <p>发了一个红包，奖品随机</p>
      <span>恭喜发财，大吉大利！</span>
      </div>
      
  </div>
  <div class="below">
  
  </div>
    <div class="mainContent">
        <div class="picContent">
            <img class="mainpic" src="<%=request.getContextPath()%>/redEnvelope/static/image/mainpic.png">
            <div class="picInfo">
                <img class="userPhoto" src="${imageServerPath}simg/${redPacketInfo.netbar.logo}.png" />
                <p>${redPacketInfo.netbar.netbarName}的红包</p>
                <span>恭喜发财，大吉大利！</span>
                <h3>${redPacketInfo.redPacketNetbarType.details }</h3>
                <span>已放入您的卡包，请尽快使用</span>
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
</div>
<script>
screenHeight=$(window).height();
screenWidth=$(window).width();
$(".frame").css("height",screenHeight);
var a=0;
$('#btn').click(function(){
    $(this).addClass("loading");
    $(this).text("加载中...");
    if(a==0){
        $(".above").animate({
            top: "-90%",
        }, 1000 );
        $(".below").animate({
            bottom: "-90%"
        }, 1000 );
        $(".frame").css("height",'100%');
    }else{}


});

</script>
</body>
</html>
