/**
 * Community 模块 API
 * @return {[type]} [description]
 */
define('js/api/community', ['js/util/http', 'js/api/community_mock'], function(http, MockData) {
	var _basePath = '/longyan/community';
	var __debug = false;
	var CommunityApi = {
		initialize: function() {},
		_executeRequest: function(url, data, handler, isJsonp, method, async, headers) {
			if (__debug) {
				if (MockData[url]) {
					handler(MockData[url](url, data));
				} else {
					console.warn('Fill mock data in mockdata.js!');
				}
			} else {
				http.request(url, data, handler, isJsonp, method, async, headers); //这里再区分ajax 和 native
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
		 * 获取小区列表
		 */
		getCommunityList: function(page, pageSize, success, error) {
			var t = this;
			var url = _basePath + '/list';
			console.log(page + "_" + pageSize)
			var data = {
				page: page,
				pageSize: pageSize
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		}, //我创建的小区列表
		getCommunityListCreate: function(page, pageSize, city, name, success, error) {
			var t = this;
			var url = _basePath + '/create-list';
			var data = {
				page: page,
				pageSize: pageSize,
				city: city,
				name: name
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		getCommunityById: function(id, success, error) {
			var t = this;
			var url = _basePath + '/' + id;
			var data = {

			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 * 创建小区
		 */
		createCommunity: function(
			city,
			name,
			address,
			shortName,
			roomMount,
			buildingAmount,
			checkMemberRate,
			priceSection,
			constructionTypes,
			renovations,
			deliveryTime,
			developers,
			propertyName,
			hotline,
			success, error) {
			var t = this;
			var url = _basePath + '/create';
			var data = {
				city: city,
				name: name,
				address: address,
				shortName: shortName,
				roomMount: roomMount,
				buildingAmount: buildingAmount,
				checkMemberRate: checkMemberRate,
				priceSection: priceSection,
				constructionTypes: constructionTypes,
				renovations: renovations,
				deliveryTime: deliveryTime,
				developers: developers,
				propertyName: propertyName,
				hotline: hotline,
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 * 更新小区信息
		 */
		updateCommunity: function(
			id,
			address,
			shortName,
			roomMount,
			buildingAmount,
			checkMemberRate,
			priceSection,
			constructionTypes,
			renovations,
			deliveryTime,
			developers,
			propertyName,
			hotline,
			success, error) {
			var t = this;
			var url = _basePath + '/update';
			var data = {
				id: id,
				address: address,
				shortName: shortName,
				roomMount: roomMount,
				buildingAmount: buildingAmount,
				checkMemberRate: checkMemberRate,
				priceSection: priceSection,
				constructionTypes: constructionTypes,
				renovations: renovations,
				deliveryTime: deliveryTime,
				developers: developers,
				propertyName: propertyName,
				hotline: hotline,
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		/*
		 * 删除小区信息
		 */
		deleteCommunity: function(
			id,
			success, error) {
			var t = this;
			var url = _basePath + '/delete';
			var data = {
				id: id
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		searchName: function(name, city, success, error) {
			var t = this;
			var url = _basePath + '/search-by-name';
			var data = {
				city: city,
				name: name
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		searchDeveloper: function(data, success, error) {
			var t = this;
			var url = _basePath + '/developers';
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		getCommunityListByEmoloyee: function(page, pageSize, data, success, error) {
			var t = this;
			var url = _basePath + '/list_by_employee';
			//发送http psot请求			
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		//获取楼栋号列表
		getCommunityBuildingList: function(page, pageSize, communityId, success, error) {
			var t = this;
			var url = _basePath + '/building/list';
			var data = {
				page: page,
				pageSize: pageSize,
				communityId: communityId
			};
			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		//添加楼栋号
		addCommunityBuilding: function(communityId, buildingName, roomAmount, unitAmount, floorAmount, success, error) {
			var t = this;
			var url = _basePath + '/building/add';
			var data = {
				communityId: communityId,
				buildingName: buildingName,
				roomAmount: roomAmount,
				unitAmount: unitAmount,
				floorAmount: floorAmount
			};

			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		//编辑楼栋号
		updateCommunityBuilding: function(id, buildingName, roomAmount, unitAmount, floorAmount, success, error) {
			var t = this;
			var url = _basePath + '/building/update';
			var data = {
				id: id,
				buildingName: buildingName,
				roomAmount: roomAmount,
				unitAmount: unitAmount,
				floorAmount: floorAmount
			};

			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		deleteCommunityBuilding: function(ids, communityId, success, error) {
			var t = this;
			var url = _basePath + '/building/delete';
			var data = {
				ids: ids,
				communityId: communityId,
			};

			//发送http psot请求
			t._executeRequest(url, data, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
		aroundList: function(data, success, error) {
			var t = this;
			var url = _basePath + '/aroundList/' + data.type;
			var request_body = data || {};
			//发送http psot请求
			t._executeRequest(url, request_body, function(response) {
				//返回的http请求数据
				t._executeResponse(response, success, error);
			}, false, 'POST', false, $nvwa.header.getHeaders());
		},
	};
	return CommunityApi;
});