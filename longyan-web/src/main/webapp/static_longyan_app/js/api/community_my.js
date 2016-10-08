/**
 * Created by a66 on 2016/10/3.
 */
/**
 * audit 模块 API
 * @return {[type]} [description]
 */
define('js/api/community_my', ['js/util/http', 'js/api/auth_mock'], function(http, MockData) {
    var _basePath = '/community/myList';
    var __debug = false;
    var AuthAPI = {
        initialize: function() {},
        _executeRequest: function(url, data, handler, isJsonp, method, async) {
            if (__debug) {
                if (MockData[url]) {
                    handler(MockData[url](url, data));
                } else {
                    console.warn('Fill mock data in mockdata.js!');
                }
            } else {
                http.request(url, data, handler, isJsonp, method, async);
            }
        },
        _executeResponse: function(response, success, error) {
            if (response && response.ok) {
                if (success) {
                    //return success function
                    success(response.dataMap);
                }
            } else {
                if (error) {
                    //return error function
                    error(response.code, response.message);
                }
            }
        },

        inChargeCommunity:function(queryInfo, success, error){
            var t = this;
            var url = _basePath;
            var data = queryInfo;
            //发送http post请求
            t._executeRequest(url, data, function(response) {
                //返回的http请求数据
                t._executeResponse(response, success, error);
            }, false, 'POST', false, $nvwa.header.getHeaders());
        }
    };
    return AuthAPI;
});