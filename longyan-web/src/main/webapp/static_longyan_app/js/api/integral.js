/**
 * Integral 模块 API
 * @return {[type]} [description]
 */
define('js/api/integral', ['js/util/http', 'js/api/integral_mock'], function(http, MockData) {
	var _basePath = '/longyan/score';
	var __debug = true;
	var IntegralApi = {
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
		 * 获取积分记录
		 */
		getIntegralList: function(page, pageSize, success, error) {
			var t = this;
			var url = _basePath + '/my-score-history';

			var data = {
				page: page,
				pageSize: pageSize
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false);
		}
	};
	return IntegralApi;
});