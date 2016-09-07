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
    <meta name="viewport" content="width=device-width,initial-scale=0.5, minimum-scale=0.5, maximum-scale=1.0, user-scalable=no"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <meta name="apple-mobile-web-app-status-bar-style" content="black"/>

    <SCRIPT LANGUAGE="javascript">
        window.resource = {
            image: 'http://t.longyan.com:1000/static_longyan_app/img',
            upload:'/s'
        }
    </SCRIPT>
    <!-- <link href="static_longyan_app/css/app.uncompressed.css" rel="stylesheet">-->

    <!-- build:css static_longyan_app/build/css/longyan-1.0.0.min-->
    <link rel="stylesheet"
          href="http://t.longyan.com:1000/static_longyan_app/css/iconfont.css">
    <link rel="stylesheet"
          href="http://t.longyan.com:1000/static_longyan_app/css/picker.css">
    <link rel="stylesheet"
          href="http://t.longyan.com:1000/static_longyan_app/css/element.css">
    <link rel="stylesheet"
          href="http://t.longyan.com:1000/static_longyan_app/css/main.css">
    <link rel="stylesheet"
          href="http://t.longyan.com:1000/static_longyan_app/css/demo.css">

    <!-- endbuild -->
    <!-- build:js static_longyan_app/build/js/longyan-1.0.0.min -->
    <script src="http://t.longyan.com:1000/static_longyan_app/nvwa-loader-1.7.0.js"
            api=""
            baseUrl="js"
            skin=""
            debug="true"
            lang="zh_CN"
            jsonp="true"
            preload="bootstrap.min,jquery.ui.widget,fileUpload,jquery.mask.min"></script>

    <!-- endbuild -->
    <style type="text/css">
        @font-face {font-family: "iconfont";
            src: url('<%=systemConfig.get("staticLongyanUrl")%>/fonts/iconfont.eot?t=1461416366'); /* IE9*/
            src: url('<%=systemConfig.get("staticLongyanUrl")%>/fonts/iconfont.eot?t=1461416366#iefix') format('embedded-opentype'), /* IE6-IE8 */
            url('<%=systemConfig.get("staticLongyanUrl")%>/fonts/iconfont.woff?t=1461416366') format('woff'), /* chrome, firefox */
            url('<%=systemConfig.get("staticLongyanUrl")%>/fonts/iconfont.ttf?t=1461416366') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+*/
            url('<%=systemConfig.get("staticLongyanUrl")%>/fonts/iconfont.svg?t=1461416366#iconfont') format('svg'); /* iOS 4.1- */
        }
        @font-face {
            font-family: 'FontAwesome';
            font-weight: normal;
            font-style: normal;
        }

        @font-face {
            font-family: "Ionicons";
            font-weight: normal;
            font-style: normal;
        }

    </style>
</head>

<body class="wechat-client pageContainer">
<div style="position: fixed;top: 0px;right: 0px;bottom: 0px;left: 0px;background-color: #777;">
    <span>系统载入中......</span>
</div>


</body>

</html>
