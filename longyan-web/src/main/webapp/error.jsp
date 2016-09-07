<%@ page pageEncoding="utf-8" %>
<%@ taglib prefix="s" uri="/struts-tags" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>404错误 - GreatBee</title>
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta name="keywords" content="GreatBee,管理软件,制造,物流,外贸"/>
  <meta name="description" content="GreatBee,最出色的中小企业管理软件"/>

  <jsp:include page="common/head-site.jsp"/>
</head>

<body>
<!-- section header -->
<header class="header" data-spy="affix" data-offset-top="0">
  <!--nav bar helper-->
  <div class="navbar-helper">
    <div class="row-fluid">
      <!--panel site-name-->
      <div class="span2">
        <div class="panel-sitename">
          <h2><a href="/"><span class="color-teal">Great</span>Bee</a></h2>
        </div>
      </div>
      <!--/panel name-->
    </div>
  </div>
  <!--/nav bar helper-->
</header>

<!-- section content -->
<section class="section">
  <div class="container">
    <div class="signin-form row-fluid">
      <!--Sign In-->
      <div class="span9 offset1">
        <div class="box corner-all">
          <div class="box-header grd-teal color-white corner-top">
            <span>错误</span>
          </div>
          <div class="box-body bg-white">
            <div class="control-group">
              <div class="controls">
                <s:actionerror/>
              </div>
            </div>
            <div class="form-actions">
              <input type="button" class="btn btn-block btn-large btn-primary" value="返回首页" onclick="document.location.href='/'"/>
            </div>
          </div>
        </div>
      </div>
      <!--/Sign In-->
    </div>
    <!-- /row -->
  </div>
  <!-- /container -->
</section>

<!-- javascript
================================================== -->
<script type="text/javascript" charset="utf-8"
        src="${system_config.staticResourceUrl}/js/lib/jquery-1.8.1.min.js"></script>
<script type="text/javascript" charset="utf-8"
        src="${system_config.staticResourceUrl}/js/lib/bootstrap-2.3.1/js/bootstrap.min.js"></script>
</body>
</html>
