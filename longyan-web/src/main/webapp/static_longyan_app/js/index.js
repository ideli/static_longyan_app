/**
 * index
 */
define('index', [
    'router', //路由定义
    'lib/fastclick.lu',
    'js/util/error', //异常搜集
    'js/util/apiHome',
    'js/util/string', //StringUtil
    'js/util/dictionary', //Dictionary
    'js/util/array', //ArrayUtil
    'util/cookie',
    'js/util/cache', //Local cache
    'js/util/events', //events规范
    'js/util/header', //header util
    'js/util/storage' //storage util    

    //    'text!lib/jQuery-File-Upload/css/jquery.fileupload.css'
], function(Router, attachFastClick) {
    return {
        init: function() {

            //静态资源使用序列化的图片
            window.resource.image = window._serialStaticPath + '/img';

            $(document.body).empty();
            var router = new Router();
            window.router = router;
            Backbone.history.start();
            // attachFastClick(document.body);
            attachFastClick.attach(document.body);
        },
        initBackEvent: function() {
            var t = this;
        }
    };
});