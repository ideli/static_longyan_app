/**
 * base api
 */
define('js/api/baseApi', ['js/util/http'], function(http) {
    var DEFAULT_INDEX = 'id';
    var BascAPI = {
        //execute response object
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
        //获取分页数据
        //handler -> page_data
        pageRequest: function(url, data, handler) {
            //默认分页参数
            var defaultData = {
                departmentId: departmentId
            };
            data = $.extend(defaultData, data);
            http.request(url, data, function(response) {
                //返回的http请求数据
                if (response.ok) {
                    if (response.dataMap && response.dataMap.page_data) {
                        //请求ok
                        if (handler != null) {
                            handler(response.dataMap.page_data);
                        }
                    } else if (response.dataMap && response.dataMap.data_list) {
                        //请求ok
                        var data_list = {
                            currentRecords: response.dataMap.data_list
                        }
                        if (handler != null) {
                            handler(data_list);
                        }
                    } else {
                        _log('该类型此接口不兼容');
                    }
                } else {
                    _log(response.message);
                }
            }, false, 'POST', false);
        }
    };
    return BascAPI;
});