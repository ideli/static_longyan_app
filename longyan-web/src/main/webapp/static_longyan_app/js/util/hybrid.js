///**
// * hybrid 对接方法
// * @return {[type]} [description]
// */
//define('js/util/hybrid', [], function() {
//    _app_callback = function(uuid, response) {
//        var handler = window.App[uuid];
//        if (handler) {
//            handler(response);
//        }
//    }
//    window.App = {
//        call: function(uuid, action, data, handler) {
//            window.App[uuid] = handler;
//        },
//        handler: function(uuid, data) {
//            var response = window.App[uuid];
//            if (response) {
//                response();
//            }
//        }
//    }
//    $nvwa.app = {
//        /*
//         * action:表示需要执行功能的枚举，比如:http请求
//         * data: 执行的参数
//         * handler:app执行完后调用的回调函数
//         */
//        request: function(action, request, responseHandler) {
//            var uuid = $nvwa.string.randomSN();
//            if (responseHandler) {
//                window.App[uuid] = responseHandler;
//            }
//            _app_call(uuid, action, $nvwa.string.objectToJsonString(request));
//        }
//    };
//    return $nvwa.app;
//});


define('js/util/hybrid', [], function() {
    $nvwa.hybrid = {
        locationDistance: function(posData, callback) { //返回我的坐标和目标距离
            if (window._isNative) {
                var request_data = {
                    parameter: posData
                };
                $nvwa.app.request("locationAndDistance", request_data, function(resp) {
                    var obj;
                    if (typeof resp == "object") {
                        obj = resp;
                        callback(obj);
                    } else {
                        try {
                            obj = eval("(" + resp + ")");
                        } catch (e) {
                            alert('ajax error url=' + url);
                        }
                    }
                });
            } else {
                console.log("不是native")
            }
        },
        location: function(callback) { //返回我的坐标和目标距离
            if (window._isNative) {
                var request_data = {
                    parameter: ""
                };
                $nvwa.app.request("location", request_data, function(resp) {
                    var obj;
                    if (typeof resp == "object") {
                        obj = resp;
                        callback(obj);
                    } else {
                        try {
                            obj = eval("(" + resp + ")");
                        } catch (e) {
                            alert('ajax error url=' + url);
                        }
                    }
                });
            } else {
                console.log("不是native")
            }
        },
        //返回到native页面
        //router_to_native
        //pop_to_native
        //dismiss_to_native
        backToHybrid: function(nativeTag, directGoback) {
            //console.log(Backbone.history)
            var historyLength = Backbone.history.history.length;
            var uuid = $nvwa.string.randomSN();
            if (directGoback == "direct") {
                parameter = {
                    "alias": nativeTag,
                    "parameter": ""
                }
                if (_isIOS()) {
                    _app_call(uuid, "router_to_native", parameter);
                } else {
                    window.hybrid._app_call(uuid, "router_to_native", parameter);
                }
            } else {
                if (historyLength <= 1) {
                    parameter = {
                        "alias": nativeTag,
                        "parameter": ""
                    }
                    if (_isIOS()) {
                        _app_call(uuid, "router_to_native", parameter);
                    } else {
                        window.hybrid._app_call(uuid, "router_to_native", parameter);
                    }
                } else {
                    Backbone.history.history.back();
                }
            }

        },
        //跳转到native新页面
        goToHybridPage: function(nativeTag, parameter) {
            //console.log(Backbone.history)
            var historyLength = Backbone.history.history.length;
            var uuid = $nvwa.string.randomSN();
            parameter = parameter || {};
            parameter['alias'] = nativeTag;
            if (_isIOS()) {
                _app_call(uuid, "router_to_native_page", $nvwa.string.objectToJsonString(parameter));
            } else {
                window.hybrid._app_call(uuid, "router_to_native_page", $nvwa.string.objectToJsonString(parameter));
            }
        }
    }

    return $nvwa.hybrid;
});