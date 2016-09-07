/**
 * Product 模块 API
 * @return {[type]} [description]
 */
define('js/api/auth_mock', [], function() {
    return {
        '/longyan/auth/login': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        },
        '/longyan/auth/logout': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        }
    };
});