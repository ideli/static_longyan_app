/**
 * Common 模块 API
 * @return {[type]} [description]
 */
define('js/api/message', ['js/util/http'], function(http) {
	var _basePath = '/longyan/message';
	var __debug = false;
	var CommonAPI = {
		initialize: function() {},
		_executeRequest: function(url, data, handler, isJsonp, method, async) {
			// if (__debug) {
			// 	if (MockData[url]) {
			// 		handler(MockData[url](url, data));
			// 	} else {
			// 		console.warn('Fill mock data in mockdata.js!');
			// 	}
			// } else {
			// 	http.request(url, data, handler, isJsonp, method, async);
			// }
			http.request(url, data, handler, isJsonp, method, async);
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
		 * 消息推送注册
		 */
		appRegister: function(data, success, error) {
			var t = this;
			var url = _basePath + '/app/register';
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 * 消息推送解绑
		 */
		appDestroy: function(data, success, error) {
			var t = this;
			var url = _basePath + '/app/destroy';
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		}

	};
	return CommonAPI;
});