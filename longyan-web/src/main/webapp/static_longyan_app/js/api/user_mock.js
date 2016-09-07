/**
 * mock data for m-web, except it in build process.
 * author: zhangxuechao
 */
define('js/api/user_mock', [], function() {
    return {
        '/longyan/user/reset-login-password-send-otp': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        },
        '/longyan/user/reset-login-password': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        },
        '/longyan/user/user-info': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {
                    "xingMing": "张三", //姓名
                    "phone": "15502173695", //手机号
                    "role": "管理员", //角色名称
                    "avatar": "xxxxxx.png", //头像，为storeName
                    "gender": "Female", //性别  Female=女，Male=男
                    "idCardNo": "450103197912121009", //身份证号
                    "bankCardNo": "**** **** **** 4501", //银行卡号
                }
            };
        },
        '/longyan/user/employee-pre-check': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {
                    "xingMing": "张三", //姓名
                    "phone": "15502173695", //手机号
                    "role": "招商部", //角色名称
                    "departmentDisplay": "真北商场 招商部", //显示名称
                    "avatar": "xxxxxx.png", //头像，为storeName
                    "gender": "female", //性别  female=女，male=男
                    "idCardNo": "450103197912121009", //身份证号
                    "bankCardNo": "**** **** **** 4501", //银行卡号
                }
            };
        },
        '/longyan/user/employee-register-send-otp': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        },
        '/longyan/user/employee-register': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        },
        '/longyan/user/user-register-send-otp': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        },
        '/longyan/user/user-register': function(url, opts) {
            return {
                "ok": true,
                "code": 0,
                "dataMap": {

                }
            };
        },
    };
});