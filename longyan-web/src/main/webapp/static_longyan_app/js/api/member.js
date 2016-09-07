/**
 * Community 模块 API
 * @return {[type]} [description]
 */
define('js/api/member', ['js/util/http', 'js/api/member_mock'], function(http, MockData) {
	var _basePath = '/longyan/member';
	var __debug = false;
	var CommunityApi = {
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
		 * 获取住户列表
		 */
		getMemberList: function(page, pageSize, provinceCode, cityCode, name, success, error) {
			var t = this;
			var url = _basePath + '/list';
			var data = {
				page: page,
				pageSize: pageSize,
				provinceCode: provinceCode,
				cityCode: cityCode,
				name: name
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		getMemberById: function(id, success, error) {
			var t = this;
			var url = _basePath + '/' + id;
			var data = {};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 * 创建住户
		 */
		createMember: function(data, success, error) {
			var t = this;
			var url = _basePath + '/create';
			// var data = {
			// 	phone: phone //手机号
			// };
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 * 更新住户信息
		 */
		updateMember: function(data, success, error) {
			var t = this;
			var url = _basePath + '/update';
			// var data = {
			// 	phone: phone //手机号
			// };
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		deleteMember: function (id, success, error) {
			var t = this;
			var url = _basePath + '/delete';
			// var data = {
			// 	phone: phone //手机号
			// };
			//发送http psot请求
			var data = {
				id: id
			};
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		}

	};
	return CommunityApi;
});