/**
 * User 模块 API
 * @return {[type]} [description]
 */
define('js/api/user', ['js/util/http', 'js/api/user_mock'], function(http, MockData) {
	var _basePath = '/longyan/user';
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
		 * 发送短信验证码
		 */
		sendResetLoginPasswordOtp: function(phone, req,success, error) {
			var t = this;
			var url = _basePath + '/employee-register-send-otp';
			var data = {
				phone: phone, //手机号
				req: req //区别注册验证字段
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		//重置登陆密码
		resetLoginPassword: function(phone, code, password, success, error) {
			var t = this;
			var url = _basePath + '/reset-password';
			var data = {
				phone: phone, //手机号
				code: code,
				password: password
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},

		changeLoginPassword: function(data, success, error) {
			var t = this;
			var url = _basePath + '/change-login-password';
			// var data = {
			// 	phone: phone, //手机号
			// 	otpCode: otpCode,
			// 	password: password
			// };
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		//获取用户信息
		getUserInfo: function(success, error) {
			var t = this;
			var url = _basePath + '/user-info';
			var data = {

			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},

		//===================员工注册流程========================
		employeePreCheck: function(phone,code,success, error) {
			var t = this;
			var url = _basePath + '/employee-register';
			var data = {
				phone: phone,
				code:code
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		sendEmployeeRegisterOtp: function(mobile, success, error) {
			var t = this;
			var url = _basePath + '/employee-pre-check';
			var data = {
				mobile: mobile
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		employeeRegister: function(data, success, error) {
			var t = this;
			var url = _basePath + '/employee-register';
			// var data = {

			// };
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		//===================会员注册流程========================
		sendUserRegisterOtp: function(phone, success, error) {
			var t = this;
			var url = _basePath + '/user-register-send-otp';
			var data = {
				phone: phone
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		userRegister: function(data, success, error) {
			var t = this;
			var url = _basePath + '/user-register';
			// var data = {

			// };
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		initPassword: function(accessToken,phone,password, success, error) {
			var t = this;
			var url = _basePath + '/init-password';
			var data = {
				accessToken:accessToken,
				phone :phone,
				password : password
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