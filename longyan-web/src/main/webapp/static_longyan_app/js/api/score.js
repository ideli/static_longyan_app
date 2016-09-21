/**
 * Community 模块 API
 * @return {[type]} [description]
 */
define('js/api/score', ['js/util/http', 'js/api/score_mock'], function(http, MockData) {
    var _basePath = '/longyan/score';
    var __debug = true;
    var ScoreApi = {
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
        /*
         * 获取龙榜
         */
        getScoreRank: function(success, error) {
            var t = this;
            var url = _basePath + '/rank';
            var data = {};
            //发送http psot请求
            t._executeRequest(url, data, function(response) {
                //返回的http请求数据
                t._executeResponse(response, success, error);
            }, false, 'POST', false, $nvwa.header.getHeaders());
        },

        /*
         * 获取龙榜
         */
        getScoreHistoryRank: function(success, error) {
            var t = this;
            var url = _basePath + '/rank-history';
            var data = {};
            //发送http psot请求
            t._executeRequest(url, data, function(response) {
                //返回的http请求数据
                t._executeResponse(response, success, error);
            }, false, 'POST', false, $nvwa.header.getHeaders());
        }
    };
    return ScoreApi;
});