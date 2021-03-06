<%@ page pageEncoding="utf-8" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" dir="ltr" lang="ro-RO">
<head profile="http://gmpg.org/xfn/11">
<title>Robotik Custom Error Pages</title>
<meta charset="utf-8"/>
<meta http-equiv="Content-Language" content="ro"/>
<style type="text/css">
    /** 
 *
 * Robotik HTML Error Pages v 1.1
 * Developed by MogooLab - www.mogoolab.com
 *
 **/


/* =Reset default browser CSS. Based on work by Eric Meyer: http://meyerweb.com/eric/tools/css/reset/index.html
-------------------------------------------------------------- */

html, body, div, span, applet, object, iframe,
h1, h2, h3, h4, h5, h6, p, blockquote, pre,
a, abbr, acronym, address, big, cite, code,
del, dfn, em, font, ins, kbd, menu, q, s, samp,
small, strike, strong, sub, sup, tt, var,
dl, dt, dd, ol, ul, li,
fieldset, form, label, legend,
table, caption, tbody, tfoot, thead, tr, th, td {
    border: 0;
    font-family: inherit;
    font-size: 100%;
    font-style: inherit;
    font-weight: inherit;
    margin: 0;
    outline: 0;
    padding: 0;
    vertical-align: baseline;
}
:focus {
        outline: 0;
}
body {
    background: #fff;
    line-height: 1;
}
ol, ul, menu {
    list-style: none;
}
table {
        border-collapse: separate;
    border-spacing: 0;
}
caption, th, td {
    font-weight: normal;
    text-align: left;
}
blockquote:before, blockquote:after,
q:before, q:after {
    content: "";
}
blockquote, q {
    quotes: "" "";
}
a img {
    border: 0;
}
article, aside, details, figcaption, figure,
footer, header, hgroup, menu, nav, section {
    display: block;
}

a, a:hover, a:active {
    text-decoration:none;
}



/******************************************************************************/

body, html {
  width:100%;
  margin:0 auto;
  padding:0;
}

body {
  background: #f2f2f2 url("<%=request.getContextPath()%><%=request.getContextPath()%>/errorpage/images/background.png") top left;
}

/* page wrappers **************************************************************/


/* page Wrapper */
.wrapper { 
      width:100%;
      margin:-200px auto 0;
      display:table;
    position:absolute;
    top:50%;
    background: url("<%=request.getContextPath()%>/errorpage/images/container_background.png") repeat-x scroll left top transparent;
}

/* content wrapper */
.mainWrapper {
    margin: 0 auto;
    position: relative;
    width: 830px;    
}

/* main holders */
/* left holder - Logo, 404 Error */
.leftHolder {
    border-right: 1px solid #A99159;
    display: block;
    float: left;
    height: 269px;
    margin: 41px 0 0;
    position: relative;
    width: 410px;
}

/* right holder - Message, Robot, Try to, Search Form */
.rightHolder {
    display: block;
    float: right;
    height: 351px;
    margin: 0 0 31px;
    width: 410px;
}

/* your logo */
.logo {
    <%--background: url("<%=request.getContextPath()%>/errorpage/images/logo.png") no-repeat scroll left top transparent;--%>
    display: block;
    height: 40px;
    margin: -120px 0 0;
    text-indent: -9999px;
    width: 128px;
}

/* robot message holder */
.message {
    background: url("<%=request.getContextPath()%>/errorpage/images/message_stick.png") no-repeat scroll 41px 100% transparent;
    display: block;
    float: right;
    font-family: 'Istok web',sans-serif;
    font-size: 14px;
    line-height: 22px;
    margin: -30px 0 30px;
    overflow: hidden;
    padding: 0 0 10px;
    position: relative;
}
/* robot message text */
.message p {
    background: none repeat scroll 0 0 #41444B;
    border-radius: 10px 10px 10px 10px;
    color: #FFFFFF;
    padding: 10px;
    width: 319px;
}

/* error 404 */
.errorNumber {
    background: url("<%=request.getContextPath()%>/errorpage/images/404.png") no-repeat scroll left top transparent;
    color: #FFFFFF;
    display: block;
    float: left;
    height: 137px;
    left: 0;
    margin: 0;
    position: absolute;
    text-align: center;
    text-indent: -9999px;
    top: 52px;
    width: 335px;
}

.robotik {
    display: block;
    float: left;
    height: 129px;
    margin: 0 19px 47px 72px;
    width: 126px;
}

/* try to */

.tryToMessage {
    color: #000000;
    display: block;
    float: left;
       font-family: 'Istok Web',sans-serif;
    font-size: 18px;
    height: 129px;
    margin: 8px 0 0 10px;
    width: 181px;
}

.tryToMessage ul {
    font-size: 16px;
    margin: 0;
    padding: 10px 0 0 0;
    text-align: left;
}

.tryToMessage ul li {
    background: url("<%=request.getContextPath()%>/errorpage/images/list_bullet.png") no-repeat scroll left top transparent;
    display: block;
    margin: 0 0 0 10px;
    padding: 4px 0 4px 10px;
}


.tryToMessage ul li a {
    color:#6b571c;
}
.tryToMessage ul li a:hover {
    color:#261f09;
}




/*search holder */
.search {
    float: right;
    height: 36px;
    text-align: left;
    width: 339px;
    position:relative;
}

.errorSearch {
    background: none repeat scroll 0 0 #CB0101;
    color: #FFFFFF;
    display: block;
    font-family: 'Chivo',sans-serif;
    font-size: 13px;
    left: 13px;
    padding: 5px;
    position: absolute;
    top: 36px;
    display:none;
}

.searchInput, .searchButton {
    float:left;
    width:68px;
    height:36px;

}

.searchInput {
    background: url("<%=request.getContextPath()%>/errorpage/images/search_input_background.png") no-repeat scroll left top transparent;
    margin: 0;
    width: 280px;
}

.searchButton {
    background: url("<%=request.getContextPath()%>/errorpage/images/search_button_background.png") no-repeat scroll left top transparent;
    height: 36px;
    margin: 0;
    width: 59px;
}

.searchButtonHover {
    background: url("<%=request.getContextPath()%>/errorpage/images/search_button_background_.png") no-repeat scroll left top transparent;
    
}

/* search input */
.searchInput input[type="text"] {
    background: none repeat scroll 0 0 transparent;
    border: 0 none;
    color: #41444b;
    float: left;
    font-family: 'Chivo',sans-serif;
    font-size: 1em;
    height: 23px;
    letter-spacing: -1px;
    line-height: 23px;
    margin: 7px 0 0 10px;
    width: 270px;
}
/* search button */
.searchButton input[type="submit"] {
    background: url("<%=request.getContextPath()%>/errorpage/images/transparent.png") no-repeat scroll left top transparent;
    border: 0 none;
    color: #FFFFFF;
    display: block;
    float: left;
    font-family: 'Chivo',sans-serif;
    font-size: 16px;
    height: 36px;
    line-height: 23px;
    margin: 0;
    text-align: center;
    width: 58px;
}

footer {
    float: left;
    padding: 20px 0 0;
    width: 830px;
}



/* footer copy */
footer p.copy {
    color: #41444b;
    float: left;
    font-family: 'Istok Web',sans-serif;
    font-size: 0.7em;
    margin: 3px 0 0;
    text-align: left;
    width: 300px;
}

/* footer menu */
footer menu {
    float: right;
    margin: 0;
    padding: 0;
    text-align: right;
    width: 500px;
}

footer menu li {
    display: inline;
    font-family: 'Istok Web',sans-serif;
    font-size: 16px;
    padding: 0 8px;
}


footer menu li a {
    color:#41444b;
}
footer menu li a:hover {
    color:#8e9519;
}

footer menu li.last {
    margin-right:0;
    padding-right:0;
}


</style>

</head>

<body>


<div class="wrapper">

	<div class="mainWrapper">
        <div class="leftHolder">
        	<a href="http://www.datayes.com"  title="Robotik Logo" class="logo">Robotik Logo</a>
            <div class="errorNumber">404</div> 
        </div>
        <div class="rightHolder">
            <div class="message"><p>The page you are looking for might have been removed, had its name changed, or is temporarily unavailable.</p></div>
            <div class="robotik"><img src="<%=request.getContextPath()%>/errorpage/images/robotik.png" alt="Oooops....we can’t find that page." title="Oooops....we can’t find that page." id="robot"></div>
            <div class="tryToMessage">
                Try to:
                <ul>
                    <li>Go <a href="javascript:history.go(-1)" title="Back">back</a></li>
                </ul>
            </div>
          </div>
      


        <footer>
        <p class="copy">Copyright &copy; 2014. All rights reserved.</p>
        <menu>
            <li><a href="http://www.datayes.com" title="Home">Home</a></li>
            <li><a href="http://www.datayes.com/#/about" title="About Us">About us</a></li>
            <li class="last"><a href="mailto:support@datayes.com" title="Contact">Contact</a></li>
        </menu>
        </footer>
        <!-- end footer -->

	</div>

</div>
<!-- end .wrapper -->


</body>
</html>