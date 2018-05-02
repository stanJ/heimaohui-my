<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//WAPFORUM//DTD XHTML Mobile 1.0//EN" "http://www.wapforum.org/DTD/xhtml-mobile10.dtd">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="width=device-width,initial-scale=1.0,maximum-scale=1.0,user-scalable=0" />
    <link media="screen" href="<%=request.getContextPath()%>/redEnvelope/static/css/redH5.css" rel="stylesheet" type="text/css">

    <title>红包打开</title>
    <script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.11.0.min.js"></script>
    <style>
        a:focus {
            text-decoration:none;
            outline:none;
        }
        a:hover {
            text-decoration:none;
            outline:none;
        }
        a:visited {
            text-decoration:none;
            outline:none;
        }
        .below{
             color: #ffd555;
             text-align:center;
        }
        .below a {
            text-decoration:none;
            display:block;
            margin-top:150px;
            color: #ffd555;
        }
    </style>
</head>

<body>
<div class="frame">
    <div class="above">

        <div class="head-sculpture">
            <img src="${imageServerPath}simg/${redPacketInfo.netbar.logo}.png" />
        </div>
        <div class="username">
           ${redPacketInfo.netbar.netbarName}
            <p>发了一个红包，奖品随机</p>
            <span>${errorMessage}</span>
        </div>

    </div>
    <div class="below">
        <c:if test="${code == 0}"><a href="<%=request.getContextPath()%>/m/b5RedPacket/viewB5RedPacket?userId=${userId}&packetId=${packetId}">看看大家的手气 </a></c:if>
    </div>
</div>
<script>
    screenHeight=$(window).height();
    screenWidth=$(window).width();
    $(".frame").css("height",screenHeight);

</script>
</body>
</html>
