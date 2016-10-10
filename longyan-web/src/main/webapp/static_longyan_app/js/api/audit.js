/**
 * audit 模块 API
 * @return {[type]} [description]
 */
define('js/api/audit', ['js/util/http', 'js/api/auth_mock'], function(http, MockData) {
	var _basePath = '/longyan/audit';
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
		 *审核接口
		 * data = {code:'会员编号'}
		 */
		ok: function(data, success, error) {
			var t = this;
			var url = _basePath + '/viewAuditList';
			var data = data;
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},

		ng: function(data, success, error) {
			var t = this;
			var url = _basePath + '/viewAuditList';
			var data = data;
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},

		needAction: function(data, success, error) {
			var t = this;
			var url = _basePath + '/viewAuditList';
			var data = data;
			//发送http post请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},

		changeId: function(id, success, error) {
			var t = this;
			var url = _basePath + '/' + id;
			var data = data;
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},

		viewUpdateList: function(id, success, error) {
			var t = this;
			var url = _basePath + '/viewUpdateList';
			var data = id;
			t._executeRequest(url, data, function(response) {
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeader());
		},

		update: function(id, status, success, error) {
			var t = this;
			var url = _basePath + '/update';
			var data = {
				id: id,
				status: status
			}
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},

		auditDetails: function(id, success, error) {
			var t = this;
			var url = _basePath + '/' + id;
			var data = {};
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},

		//我的审核列表
		myReviewList: function(data, success, error) {
			var t = this;
			var url = _basePath + '/viewAuditList';
			var data = data;
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		}
	};
	return AuthAPI;
});