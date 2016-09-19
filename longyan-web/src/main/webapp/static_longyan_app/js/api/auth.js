/**
 * Product 模块 API
 * @return {[type]} [description]
 */
define('js/api/auth', ['js/util/http', 'js/api/auth_mock'], function(http, MockData) {
	var _basePath = '/longyan/auth';
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
		/*
		 * 登陆
		 * data = {code:'会员编号'}
		 */
		login: function(data, success, error) {
			var t = this;
			var url = _basePath + '/login';
			var data = data;
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		//非加密的接口
		login_without_encryption: function(data, success, error) {
			var t = this;
			var url = _basePath + '/login_without_encryption';
			var data = data;
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		loginOld: function(data, success, error) {
			var t = this;
			var url = '/security/auth.action';
			var data = data;
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		logout: function(success, error) {
			var t = this;
			var url = _basePath + '/logout';
			var data = {};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		}
	};
	return AuthAPI;
});