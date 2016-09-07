/**
 * feedback 模块 API
 * @return {[type]} [description]
 */
define('js/api/feedback', ['js/util/http', 'js/api/feedback_mock'], function(http, MockData) {
	var _basePath = '/longyan/feedback';
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
		//===================关于我们 意见反馈 反馈类型列表接口========================
		getFeedbackList: function(success, error) {
			var t = this;
			var url = _basePath + '/typeList';
			var data = {};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},

		//===================关于我们 意见反馈 添加反馈的接口========================
		feedbackCreate: function(descInfoContent, descInfoAlias, descInfoName, descInfoPhone, success, error) {
			var t = this;
			var url = _basePath + '/create';
			var data = {
				content: descInfoContent,
				alias: descInfoAlias,
				name: descInfoName,
				phone: descInfoPhone
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
	};
	return AuthAPI;
});