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

    <SCRIPT LANGUAGE="javascript">
        window.resource = {
            image: '<%=systemConfig.get("staticLongyanUrl")%>/img',
            upload:'/s'
        }
    </SCRIPT>
    <!-- <link href="static_longyan_app/css/app.uncompressed.css" rel="stylesheet">-->

    <!-- build:css static_longyan_app/build/css/longyan-1.0.0.min-->
		<link rel="stylesheet" href="<%=systemConfig.get("staticLongyanUrl")%>/build/release-40083d/css/longyan-1.0.0.min.77b363.css">
 <!-- endbuild -->
    <!-- build:js static_longyan_app/build/js/longyan-1.0.0.min -->
		<script src="<%=systemConfig.get("staticLongyanUrl")%>/nvwa-loader-1.7.0.js"
		api = ""
		baseUrl = "build/release-40083d/js"
		skin = ""
		debug = "true"
		lang = "zh_CN"
		jsonp = "true"
		staticDomain = "<%=systemConfig.get("staticDomain")%>"
		preload = "<%=systemConfig.get("staticLongyanUrl")%>/build/release-40083d/js/longyan-1.0.0.min.c00dce.js,bootstrap.min,jquery.ui.widget,fileUpload" > </script>
 <!-- endbuild -->
    <style type="text/css">
        @font-face {font-family: "iconfont";
            src: url('/static_longyan_app/fonts/iconfont.eot?t=1463673438'); /* IE9*/
            src: url('/static_longyan_app/fonts/iconfont.eot?t=1463673438#iefix') format('embedded-opentype'), /* IE6-IE8 */
            url('/static_longyan_app/fonts/iconfont.woff?t=1463673438') format('woff'), /* chrome, firefox */
            url('/static_longyan_app/fonts/iconfont.ttf?t=1463673438') format('truetype'), /* chrome, firefox, opera, Safari, Android, iOS 4.2+*/
            url('/static_longyan_app/fonts/iconfont.svg?t=1463673438#iconfont') format('svg'); /* iOS 4.1- */
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

<%--<script>--%>
    <%--var _hmt = _hmt || [];--%>
    <%--(function() {--%>
        <%--var hm = document.createElement("script");--%>
        <%--hm.src = "//hm.baidu.com/hm.js?874e9b81131d46924a1ff8a736e947c0";--%>
        <%--var s = document.getElementsByTagName("script")[0];--%>
        <%--s.parentNode.insertBefore(hm, s);--%>
    <%--})();--%>
<%--</script>--%>
<!-- 获取用户行为数据（js文件外部引用）-->
<script type="text/javascript">
    //页面类型，必填 ,（product|shop|active|normal|index，需要特殊关注的页面附加类型，可传入自定义类型定制开发报表）如无特需，默认请传入normal）
    _pType = 'normal';
    // 根据页面类型传入， product->商品id;shop->商户Id;active->活动id或者code或者名称;brand->品牌id或者名称
    //    _sKey1 = '111111';
    //title 如果不指定，默认从title标签中获取
    //_title = '首页';
    (function() {
        var ga = document.createElement('script');
        ga.type = 'text/javascript';
        ga.charset='utf-8';
        //ga.async = true 异步调用外部js文件，即不阻塞浏览器的解析
        ga.async = true;
        ga.src = 'http://stat.mklmall.com/js/stat.js?ver=0.1';
        //取得第一个tag名为script的元素
        var s = document.getElementsByTagName('script')[0];
        //在s前添加元素ga
        s.parentNode.insertBefore(ga, s);
    })();
</script>
</body>

</html>
