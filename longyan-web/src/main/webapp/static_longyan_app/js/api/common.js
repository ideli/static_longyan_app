/**
 * Common 模块 API
 * @return {[type]} [description]
 */
define('js/api/common', ['js/util/http', 'js/api/common_mock'], function(http, MockData) {
	var _basePath = '/longyan/common';
	var __debug = false;
	var CommonAPI = {
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
		 * 获取省列表
		 */
		getProvinceList: function(success, error) {
			var t = this;
			var url = _basePath + '/location/province';
			var data = {};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 * 获取市列表
		 */
		getCityList: function(data, success, error) {
			var t = this;
			var url = _basePath + '/location/city';
			var data = data;
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 * 获取区列表
		 */
		getAreaList: function(data, success, error) {
			var t = this;
			var url = _basePath + '/location/area';
			var data = data;
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		getLoctions: function(success, error) {
			var t = this;
			var url = _basePath + '/get-locations';
			var data = data;
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		//获取房屋装修情况
		getRenovationStatus: function(success, error) {
			var t = this;
			var url = _basePath + '/renovationStatus';
			var data = {};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		//获取房屋类型
		getRoomType: function(success, error) {
			var t = this;
			var url = _basePath + '/roomType';
			var data = {};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		//获取房型
		getRoomLayout: function(success, error) {
			var t = this;
			var url = _basePath + '/roomLayout';
			var data = {};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		//获取面积
		getRoomArea: function(success, error) {
			var t = this;
			var url = _basePath + '/roomArea';
			var data = {};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 * 发送短信验证码
		 */
		sendSmsVerificationCode: function(phone, success, error) {
			var t = this;
			var url = _basePath + '/location/send_sms_verification_code';
			var data = {
				phone: phone //手机号
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},

	};
	return CommonAPI;
});