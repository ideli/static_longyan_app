/**
 * HTTP
 * @return {[type]} [description]
 */
define('js/util/http', [], function() {
    $nvwa.http = {
        initialize: function() {

        },
        /**
         * ajax 请求封装
         * @param  {[type]}  url     [description]
         * @param  {[type]}  data    [description]
         * @param  {[type]}  handle  [description]
         * @param  {Boolean} isJsonp [description]
         * @param  {[type]}  method  [description]
         * @param  {[type]}  async   [description]
         * @return {[type]}          [description]
         */
        request: function(url, data, handle, isJsonp, method, async, headerValue) {
            headerValue = headerValue || {};
            //http request exception function
            var __errorException = function(responsoObject) {
                //exception object
                var exception = {
                    url: url,
                    data: data
                }
                _log('request exception!');
                if (responsoObject && responsoObject.message && responsoObject.message.length > 0) {
                    exception['message'] = responsoObject.message;
                }
                _log(exception);
            };
            var nvwaAPI = window._nvwaAPI;
            if (nvwaAPI) {
                url = nvwaAPI + url;
            }
            method = method || "GET";
            if (async == undefined) {
                async = true;
            }
            //如果http request map里存在object的对象(int string boolean以外的类型),则执行反序列化方法
            for (k in data) {
                if (typeof(data[k]) == 'object' && data[k] != null) {
                    data[k] = JSON.stringify(data[k]);
                }
            }
            var option = {
                url: url,
                // headers: {
                //     //     "X-Requested-With": "XMLHttpRequest"
                //     "headerKey": headerValue
                // },
                headers: headerValue,
                data: data,
                timeout: 15000, //超时时间设置，单位毫秒
                success: function(resp) {
                    var obj;
                    if (typeof resp == "object") {
                        obj = resp;
                    } else {
                        try {
                            obj = eval("(" + resp + ")");
                        } catch (e) {
                            alert('ajax error url=' + url);
                        }
                    }
                    if (obj && !obj.ok && obj.message && obj.message.length > 0) {
                        //execute ok == false , log the message to console
                        __errorException(obj);
                    }
                    if (obj && !obj.ok && obj.code && obj.code == -401) {
                        //no session
                        var loginURL = 'login';
                        if (window.router) {
                            router.navigate(loginURL, {
                                trigger: true
                            });
                        }
                    } else if (handle && handle != null) {
                        //have session
                        handle(obj);
                    }
                },
                error: function(resp) {
                    // $.messager.alert('错误', 'Server出现异常，请稍后访问', 'error');                    
                    __errorException();
                },
                complete: function(XMLHttpRequest, status) { //请求完成后最终执行参数                    　　　　
                    if (status == 'timeout') { //超时,status还有success,error等值的情况                        　　　　　                        
                        handle({
                            ok: false,
                            code: 408,
                            message: '请求超时'
                        });
                    } else if (status == 'error') {
                        handle({
                            ok: false,
                            code: 1000,
                            message: '网络异常'
                        });
                    }
                },
                type: method
            };
            if (isJsonp) {
                option.dataType = "jsonp";
                option.jsonp = "callback";
                option.type = "GET";
            } else {
                option.dataType = "json";
            }
            if (window._isNative) {

                var request_data = {
                    url: url,
                    method: method.toLowerCase(),
                    parameter: data
                };
                // alert(qeqwe);
                $nvwa.app.request('http', request_data, function(resp){
                    var obj;
                    if (typeof resp == "object") {
                        obj = resp;
                    } else {
                        try {
                            obj = eval("(" + resp + ")");
                        } catch (e) {
                            alert('ajax error url=' + url);
                        }
                    }
                    if (obj && !obj.ok && obj.message && obj.message.length > 0) {
                        //execute ok == false , log the message to console
                        __errorException(obj);
                    }
                    if (obj && !obj.ok && obj.code && obj.code == -401) {
                        //no session
                        var loginURL = 'login';
                        if (window.router) {
                            router.navigate(loginURL, {
                                trigger: true
                            });
                        }
                    } else if (handle && handle != null) {
                        //have session
                        handle(obj);
                    }
                });
            } else {
                return $.ajax(option);
            }
        }
    };
    return $nvwa.http;
});