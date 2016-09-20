<%@ page import="org.springframework.web.context.WebApplicationContext" %>
<%@ page import="org.springframework.web.context.support.WebApplicationContextUtils" %>
<%@ page import="java.util.HashMap" %>
<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html>
<head>
    <%
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext());
        HashMap systemConfig = (HashMap) context.getBean("systemConfig");
    %>
    <meta name="description" content="">
    <title>龙眼 1.0</title>
    <meta name="HandheldFriendly" content="True">
    <%--<meta name="viewport"--%>
          <%--content="width=device-width, initial-scale=1.0, maximum-scale=1, user-scalable=no, minimal-ui">--%>
    <meta name="viewport" content="target-densitydpi=device-dpi,width=device-width,initial-scale=0.5, minimum-scale=0.5, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>

    <style type="text/css">
        body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"微软雅黑";}
    </style>
    <script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=jaf8o6NPGwlPF977K48R0iMgtFuk7Bhj"></script>

</head>

<body class="wechat-client pageContainer">
<div id="allmap"></div>
</body>
<script type="text/javascript">
    // 百度地图API功能
    var map = new BMap.Map("allmap");
    var point = new BMap.Point(116.404, 39.915);
    map.centerAndZoom(point, 15);

    //创建小狐狸
    var pt = new BMap.Point(116.417, 39.909);
    var myIcon = new BMap.Icon("http://developer.baidu.com/map/jsdemo/img/fox.gif", new BMap.Size(300,157));
    var marker2 = new BMap.Marker(pt,{icon:myIcon});  // 创建标注
    map.addOverlay(marker2);              // 将标注添加到地图中
</script>
</html>
