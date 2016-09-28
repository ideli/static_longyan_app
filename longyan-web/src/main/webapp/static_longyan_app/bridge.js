
window.$nvwa=window.$nvwa||{};

var _isIOS = function() {
	var userAgentInfo = navigator.userAgent;
	var Agents = [ "iPhone",
		"iPad", "iPod"
	];
	var flag = false;
	for (var v = 0; v < Agents.length; v++) {
		if (userAgentInfo.indexOf(Agents[v]) > 0) {
			flag = true;
			break;
		}
	}
	return flag;
}

// window.hybrid._app_call=function(a,b,c){
// 	//空函数
// 	alert('123abc');
// }

var _app_callback = function(uuid, response) {
	//alert(uuid);
	// _app_log('_app_callback,uuid='+uuid+',response='+response);
	var handler = window.App[uuid];
	if (handler) {
		if(response&&response.length>0){
			var _response = eval("(" + response + ")");
			handler(_response);
		}
	}
}
window.App = {
	call: function(uuid, action, data, handler) {
		window.App[uuid] = handler;
	},
	handler: function(uuid, data) {
		//console.log('window.app.call');
		//alert("handler1"+data);
		var response = window.App[uuid];
		if (response) {
			response();
		}
	}
}



$nvwa.app = {
	/*
	 * action:表示需要执行功能的枚举，比如:http请求
	 * data: 执行的参数
	 * handler:app执行完后调用的回调函数
	 */
	request: function(action, request, responseHandler) {
		var uuid='';
		if(action&&action!='log'){
			uuid = $nvwa.string.randomSN();
		}
		//window.hybrid._app_log(responseHandler);
		if (responseHandler) {
			//alert(responseHandler)
			window.App[uuid] = responseHandler;

		}
		if(_isIOS()){
			_app_call(uuid, action, $nvwa.string.objectToJsonString(request));	
		}else{
			window.hybrid._app_call(uuid, action, $nvwa.string.objectToJsonString(request));
		}
	}
};




