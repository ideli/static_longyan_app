/**
 * Community 模块 API
 * @return {[type]} [description]
 */
define('js/api/contactBook', ['js/util/http', 'js/api/contactBook_mock'], function(http, MockData) {
	var _basePath = '/longyan/contact';
	var __debug = false;

	var contactBookApi = {
		initialize: function() {},
		_executeRequest: function(url, data, handler, isJsonp, method, async) {
			if (__debug) {
				if (MockData[url]) {
					handler(MockData[url](url, data));
				} else {
					console.warn('Fill mock data in mockdata.js!');
				}
			} else {
				http.request(url, data, handler, isJsonp, method, async); //这里再区分ajax 和 native
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
		* 获取部门列表
		* */
		getContactDeptList: function(departmentId,  success, error){
			var t = this,
			    url = _basePath + '/deptList?departmentId='+ departmentId,
			    /*data = {
					departmentId:departmentId.toString()
				};*/
			    data ={

				};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				//alert(data.departmentId+"____api")
				//$(".u-full").append(data.departmentId	+"_")
				t._executeResponse(response, success, error);
			}, false, 'get', false, $nvwa.header.getHeaders());
		},
		/*
		* 获取员工列表
		* */
		getContacttEmployeeList: function (keyWord, success, error) {
			var t = this,
				url = _basePath + '/deptEmployeeList',
				data = {
					keyWord:keyWord
				};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'post', false, $nvwa.header.getHeaders());
		},
		/*
		 * 获取员工详细信息
		 * */
		getContactEmployeeInfo: function(employeeId,  success, error){
			var t = this,
				url = _basePath + '/employeeInfo',
				data = {
					employeeId:employeeId.toString()
				};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'post', false, $nvwa.header.getHeaders());
		}
	};
	return contactBookApi;
});