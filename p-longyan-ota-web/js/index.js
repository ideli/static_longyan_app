/**
 * index
 */
define('index', [
    'router', //路由定义
    'js/util/error', //异常搜集
    'js/util/apiHome',
    'js/util/string', //StringUtil
    'js/util/dictionary', //Dictionary
    'js/util/array', //ArrayUtil
    'util/cookie',
    'js/util/cache', //Local cache
    'js/util/events', //events规范
    'js/util/header', //header util
    'js/util/storage', //storage util
    // 'lib/iscroll', //iscroll.js
    'lib/api'
    //    'text!lib/jQuery-File-Upload/css/jquery.fileupload.css'
], function(Router) {
    return {
        init: function() {
            // if (typeof(api) != 'undefined' && api) {
                // api.setStatusBarStyle({
                //     // style: 'light'
                //     style: 'dark',
                //     color: '#fff'
                // });
            // }

            $(document.body).empty();
            var router = new Router();
            window.router = router;
            Backbone.history.start();
        },
        initBackEvent: function() {
            var t = this;
            if (typeof(api) != 'undefined' && api) {
                api.addEventListener({
                    name: 'keyback'
                }, function(ret, err) {
                    var current = window.location.hash.split('/')[0];
                    var currentPage = window.location.pathname.split('/')[1];
                    if (current == '#login' || current == '') {
                        api.closeWidget();
                    } else if (current == '#index') {
                        api.closeWidget();
                    } else {
                        // history.back();
                        Backbone.history.history.back();
                    }
                });
            }
        }
    };
});