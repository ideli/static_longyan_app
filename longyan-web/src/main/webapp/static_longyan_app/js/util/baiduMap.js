define(
    'js/util/baiduMap', [],
    function () {
        var reaultInfo;
        var str ='';
        'use strict';
        var baiduMapBox = function (func) {

            var loadMap = function () {
                var script = document.createElement("script");
                script.type = "text/javascript";
                script.src = "http://api.map.baidu.com/api?v=2.0&ak=SVIyNPuDutO3orsDySU23Bnu&callback=baiduMap_init";
                document.body.appendChild(script);
            }
            loadMap();
            window.baiduMap_init = function () {
                // 创建地理编码实例
                var geolocation = new BMap.Geolocation();
                geolocation.getCurrentPosition(function (r) {
                    if (geolocation.getStatus() == BMAP_STATUS_SUCCESS) {
                        var obj = {
                            lat: r.point.lat,
                            lon: r.point.lng
                        }
                        var person = new BMap.Point(obj.lon, obj.lat);
                        var myGeo = new BMap.Geocoder();
                        myGeo.getLocation(person, function (result) {func(result);
                        });
                    }
                    else {
                        return;
                    }
                }, {enableHighAccuracy: true});


            };
            return str;
        };

        return baiduMapBox;
    }
);