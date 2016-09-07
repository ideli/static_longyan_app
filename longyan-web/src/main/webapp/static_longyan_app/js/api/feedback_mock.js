/**
 * mock data for m-web, except it in build process.
 * author: liqing
 */
define('js/api/feedback_mock', [], function() {
    return {
            '/longyan/feedback/typeList': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {
                    "descInfoContent": "我没有一个家，什么时候才有家", //姓名
                    "descInfoAlias": "15502173695", //手机号
                    "descInfoName": "张三", //角色名称
                    "descInfoPhone": "15502173695", //头像，为storeName
                }
            };
        },
        '/longyan/feedback/create': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        },
    };
});