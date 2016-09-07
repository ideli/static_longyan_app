/**
 * Common 模块 API
 * @return {[type]} [description]
 */
define('js/api/report', ['js/util/http'], function(http) {
	var _basePath = '/longyan/report';
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
		 * 获取员工录入报表
		 */
		getEmployeeInputReport : function(data, success, error) {
			var t = this;
			var url = _basePath + '/employee-input-report';
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 * 获取商场录入报表
		 */
		getMallInputReport : function(data, success, error) {
			var t = this;
			var url = _basePath + '/shopping-mall-input-report';
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 *  员工录入进度排名
		 */
		getEmployeeInputRankReport : function(data, success, error) {
			var t = this;
			var url = _basePath + '/employee-input-rank-report';
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 *  商场录入进度排名
		 */
		getMallInputRankReport : function(data, success, error) {
			var t = this;
			var url = _basePath + '/shopping-mall-input-rank-report';
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 *  集团报表
		 */
		getAllInputReport : function(data, success, error) {
			var t = this;
			var url = _basePath + '/all-input-report';
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 *  大区报表
		 */
		getAreaInputReport : function(data, success, error) {
			var t = this;
			var url = _basePath + '/area-input-report';
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		//大区列表
		getAreaList : function(data, success, error) {
			var t = this;
			var url = _basePath + '/root-area/list';
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		getMallList : function(data, success, error) {
			var t = this;
			var url = _basePath + '/area-shopping-mall/list';
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		getEmployeeList : function(data, success, error) {
			var t = this;
			var url = _basePath + '/shopping-mall-employee/list';
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},


	};
	return CommonAPI;
});