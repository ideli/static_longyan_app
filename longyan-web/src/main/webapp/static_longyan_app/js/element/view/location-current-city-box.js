/**
 * 头
 **/
define('js/element/view/location-current-city-box', [
        'text!js/element/template/location-box.tpl',
        'js/api/common'
        // 'js/components/picker'
    ],
    function(LocationTpl, CommonApi) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.config = config || {};
                t.disable = t.config.disable;
                t.events = events;
                t.render();

                t.isPickerReady = false;
                t.isNotArea = false;

                t.initEvents();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(LocationTpl, {
                    data: t.config
                }));
                // if ($(".picker-container").length == 0) {
                //     $('<div class="picker-container"></div>').appendTo($("body"));
                // }

                // t.initRegionPicker();

                require(["js/components/picker"], function(Picker) {
                    t.isPickerReady = true;
                    new Picker();
                    t.initPicker();
                });

            },
            initEvents: function() {
                var t = this;
                if (t.events && t.events.Keyup) {
                    t.$el.find('.' + t.config.fieldName).off('keyup');
                    t.$el.find('.' + t.config.fieldName).on('keyup', function(e) {
                        if ($(e.currentTarget).hasClass(t.config.fieldName) && !t.disable) {
                            t.events.Keyup(e);
                        }
                    });
                }
            },
            getValue: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    // var input = container.find('input');
                    // return input.val();
                    var codes = container.attr('data-selected') || t.select_ids;
                    var names = container.attr('data-selected-name') || t.select_name;

                    if (codes && names) {
                        var codeArray = codes.split(' ');
                        var nameArray = names.split(' ');
                        return {
                            "province": nameArray[0],
                            "provinceCode": codeArray[0],
                            "city": nameArray[1],
                            "cityCode": codeArray[1],
                            "area": nameArray[2],
                            "areaCode": codeArray[2]
                        }
                    }

                }
                // simple
                // {
                //     "province": "上海"
                //     "provinceCode": "310000",
                //     "city": "上海市"
                //     "cityCode": "310100",
                //     "area": "黄浦区"
                //     "areaCode": "310101",
                // }
            },
            setValue: function(provinceCode, cityCode, areaCode) {
                var t = this;
                console.log(provinceCode);
                console.log(cityCode);
                console.log(areaCode);
                t.count = 0;

                t.provinceCode = provinceCode;
                t.cityCode = cityCode;
                t.areaCode = areaCode;

                var time = window.setInterval(function() {
                    t.count++;
                    if (t.count >= 100) {
                        window.clearInterval(time);
                    }
                    if (t.isPickerReady) {

                        t.initPicker();


                        if (t.provinceList) {
                            window.clearInterval(time);
                            var container = t.$el.find('.' + t.config.fieldName);
                            if (container) {
                                var input = container.find('input');
                                var val = '';
                                var codeVal = provinceCode + ' ' + cityCode + ' ' + areaCode;
                                if (t.provinceList) {
                                    for (var i = 0; i < t.provinceList.length; i++) {
                                        if (provinceCode == t.provinceList[i].provinceCode) {
                                            val += t.provinceList[i].provinceName;
                                            if (t.cityList) {
                                                for (var j = 0; j < t.cityList.length; j++) {
                                                    if (cityCode == t.cityList[j].cityCode) {
                                                        val += ' ' + t.cityList[j].cityName;
                                                        if (t.areaList) {
                                                            for (var k = 0; k < t.areaList.length; k++) {
                                                                if (!t.areaList[k].areaCode) {
                                                                    continue;
                                                                }
                                                                if (areaCode == t.areaList[k].areaCode) {
                                                                    val += ' ' + t.areaList[k].areaName;
                                                                    break;
                                                                }
                                                            }
                                                        }
                                                        break;
                                                    }
                                                }
                                            }
                                            break;
                                        }
                                    }
                                }
                                input.val(val);
                                container.data('selected', codeVal);
                                container.data('selected-name', val);
                                t.select_ids = codeVal;
                                t.select_name = val;
                            }
                        }
                    }
                }, 50);

            },
            setReadOnly: function(value) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input');
                    if (value) {
                        input.attr('readonly', 'readonly');
                    } else {
                        input.removeAttr('readonly');
                    }

                }
            },
            //是否为空
            isVerify: function() {
                var t = this;
                var v = t.getValue();
                return $nvwa.string.isVerify(v);
            },
            initPickerParam: function(callback) {
                var t = this;
                /* "provinceList": [{
                                        "provinceName": "上海",
                                        "provinceCode": "310000",
                                        "cityList": [{
                                            "cityName": "上海市",
                                            "cityCode": "310100"
                                            "areaList": [{
                                                "areaName": "黄浦区",
                                                "areaCode": "310101"
                                            }, {
                                                "areaName": "卢湾区",
                                                "areaCode": "310103"
                                            }]
                                        }]
                                    },{
    
                                    }]
                */
                if (!t.apiload) {
                    t.apiload = true;
                    CommonApi.getLoctions(function(data) {
                        if (data && data.provinceList) {
                            t.provinceList = data.provinceList;
                            t.provinceCodes = [];
                            t.provinceNames = [];

                            t.cityList = data.provinceList[0].cityList;
                            for (var i = 0; i < t.provinceList.length; i++) {
                                t.provinceCodes.push(t.provinceList[i].provinceCode);
                                t.provinceNames.push(t.provinceList[i].provinceName);

                                if (t.provinceCode && t.provinceCode == t.provinceList[i].provinceCode) {
                                    t.cityList = data.provinceList[i].cityList;
                                }
                            }

                            if (t.cityList) {
                                t.cityCodes = [];
                                t.cityNames = [];

                                t.areaList = t.cityList[0].areaList;
                                for (var i = 0; i < t.cityList.length; i++) {
                                    if (t.cityList[i].cityCode === 'null' || t.cityList[i].cityCode === null) {
                                        continue;
                                    }
                                    t.cityCodes.push(t.cityList[i].cityCode);
                                    t.cityNames.push(t.cityList[i].cityName);
                                    if (t.cityCode && t.cityCode == t.cityList[i].cityCode) {
                                        t.areaList = t.cityList[i].areaList;
                                    }
                                }
                                if (t.areaList) {
                                    t.areaCodes = [];
                                    t.areaNames = [];
                                    for (var i = 0; i < t.areaList.length; i++) {
                                        if (t.areaList[i].areaCode === 'null' || t.areaList[i].areaCode === null) {
                                            continue;
                                        }
                                        t.areaCodes.push(t.areaList[i].areaCode);
                                        t.areaNames.push(t.areaList[i].areaName);
                                    }
                                }
                            }
                        }
                        if (callback) {
                            callback();
                        }
                    }, function(code, msg) {
                        if (callback) {
                            callback();
                        }
                    });
                } else {

                }


            },
            initPicker: function() {
                var t = this;

                t.initPickerParam(function() {

                    $('#location-picker').picker({
                        toolbarTemplate: '<div class="bar bar-nav location-picker">\
                      <button class="button button-link pull-right close-picker">完成</button>\
                      </div>',
                        cols: [{
                            textAlign: 'left',
                            // values: t.tplData.regionPicker.colProvince
                            values: t.provinceCodes,
                            displayValues: t.provinceNames
                        }, {
                            textAlign: 'center',
                            // values: t.tplData.regionPicker.colCity
                            values: t.cityCodes,
                            displayValues: t.cityNames
                        }, {
                            textAlign: 'right',
                            // values: t.tplData.regionPicker.colCity
                            values: t.areaCodes,
                            displayValues: t.areaNames
                        }],
                        // cols:['123','456'],
                        // needMask: true,
                        onOpen: function(p) {
                            var provinceSelected, citySelected, countrySelected;

                            $('.picker-items-col-left').css('width', '30%');
                            $('.picker-items-col-center').css('width', '30%');
                            $('.picker-items-col-right').css('width', '30%');
                            $('.picker-container .picker-modal .bar .title').css({
                                'line-height': '80px',
                                'font-size': '28px'
                            });

                            if (t.provinceCode) {
                                for (var i = 0; i < t.provinceList.length; i++) {
                                    if (t.provinceCode == t.provinceList[i].provinceCode) {
                                        p.cols[0].setValue(t.provinceCode);

                                        t.resetColCity(p, t.provinceCode);
                                    }
                                }
                            }

                            // var val = $('#location-picker').attr('data-selected').split(" ");
                            // if (val && val.length == 2) {
                            //     // p.cols.splice(2, 1);
                            //     // p.updateValue();
                            //     $('.picker-items-col-right .picker-items-col-wrapper').empty();
                            // }

                            $('input').blur();

                        },
                        onClose: function(p) {

                        },
                        onTouchEnd: function(p, activeIndex) {
                            console.log(activeIndex);
                            var targetValue = $(event.target).data("picker-value");
                            //仅做判断
                            var itemColDom = $(event.target).parent().parent();

                            var pickedValue = '';
                            if (targetValue && itemColDom.hasClass('picker-items-col-left')) {
                                pickedValue = p.cols[0].values[activeIndex];
                                var provinceCode = pickedValue;
                                // 更新月滚动列
                                t.resetColCity(p, provinceCode);
                            } else if (targetValue && itemColDom.hasClass('picker-items-col-center')) {
                                pickedValue = p.cols[1].values[activeIndex];
                                var provinceCode = $('.picker-items-col').eq(0).find('.picker-selected').eq(0).data('picker-value');
                                console.log('省：' + provinceCode);
                                var cityCode = pickedValue;
                                // 更新日滚动列
                                t.resetColArea(p, provinceCode, cityCode);
                                //
                            } else if (targetValue && itemColDom.hasClass('picker-items-col-right')) {
                                pickedValue = p.cols[2].values[activeIndex];
                            }
                            console.log('pickedValue' + pickedValue);
                            console.log('selected: ' + $('#location-picker').data('selected'));
                            console.log('selected: ' + $('#location-picker').data('selectedName'));
                            console.log('selected: ' + $('#location-picker').attr('data-selected'));
                        }
                    });
                    $(document).on('click', '.location-picker .close-picker', function(e) {
                        var target = $(e.currentTarget);
                        if (target.hasClass("close-picker")) {
                            //清楚选择的城市
                            t.provinceCode = '';
                            t.cityCode = '';
                            t.areaCode = '';


                            //确认选择逻辑
                            var selectedData = $('#location-picker').attr('data-selected');
                            var selectedDataName = $('#location-picker').attr('data-selected-name'); //.replace(/\s+/g, "")

                            if (t.isNotArea) {
                                var tmpArray = selectedData.split(' ');
                                tmpArray.splice(2, 1);
                                var newData = tmpArray.join(' ');
                                var tmpArray2 = selectedDataName.split(' ');
                                tmpArray2.splice(2, 1)
                                var newDataName = tmpArray2.join(' ');
                                $('#location-picker').attr('data-selected', newData);
                                $('#location-picker').attr('data-selected-name', newDataName);
                                $('#location-picker input').val(newDataName);
                            } else {
                                $('#location-picker input').val(selectedDataName);
                            }


                        }
                        $.closeModal(".picker-modal.modal-in");
                    });
                    $(document).on('click', '.location-picker .cancel-picker', function(e) {
                        $.closeModal(".picker-modal.modal-in");
                    });
                });


            },
            /*//更新月信息
            resetColCity: function(p, provinceCode) {
                var t = this;
                var cityParent = $('.picker-items-col').eq(1).find('.picker-item').eq(0).parent(),
                    resetCol = p.container.find('.picker-items-col').eq(1),
                    cityValues = [];
                var cityValueNames = [];

                //省变了
                cityParent.empty();

                if (t.provinceList) {
                    var activeNum = -1;
                    for (var i = 0; i < t.provinceList.length; i++) {
                        if (provinceCode == t.provinceList[i].provinceCode) {
                            t.cityCodes = [];
                            t.cityNames = [];

                            var activeCityCode = '';

                            t.cityList = t.provinceList[i].cityList;
                            for (var i = 0; i < t.cityList.length; i++) {
                                if (t.cityList[i].cityCode === 'null' || t.cityList[i].cityCode === null) {

                                    continue;
                                }
                                if (activeNum < 0) {
                                    //拿第一个有值得数据
                                    activeNum = i;
                                }

                                t.cityCodes.push(t.cityList[i].cityCode);
                                t.cityNames.push(t.cityList[i].cityName);

                                cityValues.push(t.cityList[i].cityCode);
                                cityValueNames.push(t.cityList[i].cityName);
                                var cityItem = '<div class="picker-item" data-picker-value="' + t.cityList[i].cityCode + '">' + t.cityList[i].cityName + '</div>';
                                cityParent.append(cityItem);

                                if (t.cityCode && t.cityCode == t.cityList[i].cityCode) {
                                    activeCityCode = t.cityCode;
                                }

                            }
                            p.cols[1].replaceValues(cityValues, cityValueNames);
                            p.updateValue();

                            if (activeCityCode) {
                                p.cols[1].setValue(activeCityCode);
                            }

                            //更新城市之后 更新区
                            t.resetColArea(p, provinceCode, t.cityList[activeNum].cityCode);

                            break;
                        }
                    }
                }
            },
            //更新日信息
            resetColArea: function(p, provinceCode, cityCode) {
                var t = this,
                    areaParent = $('.picker-items-col').eq(2).find('.picker-item').eq(0).parent(),
                    areaValues = [];
                var areaValueNames = [];
                //市变了
                areaParent.empty();

                if (t.cityList) {
                    for (var i = 0; i < t.cityList.length; i++) {
                        if (cityCode == t.cityList[i].cityCode) {
                            t.areaCodes = [];
                            t.areaNames = [];

                            var activeAreaCode = '';

                            t.areaList = t.cityList[i].areaList;
                            for (var i = 0; i < t.areaList.length; i++) {
                                if (t.areaList[i].areaCode === 'null' || t.areaList[i].areaCode === null) {
                                    t.areaList.splice(i, 1);
                                    continue;
                                }
                                t.areaCodes.push(t.areaList[i].areaCode);
                                t.areaNames.push(t.areaList[i].areaName);

                                areaValues.push(t.areaList[i].areaCode);
                                areaValueNames.push(t.areaList[i].areaName);
                                var areaItem = '<div class="picker-item" data-picker-value="' + t.areaList[i].areaCode + '">' + t.areaList[i].areaName + '</div>';
                                areaParent.append(areaItem);

                                if (t.areaCode && t.areaCode == t.areaList[i].areaCode) {
                                    activeAreaCode = t.areaCode;
                                }
                            }

                            if (t.areaList.length == 0) {
                                t.isNotArea = true;
                            } else {
                                t.isNotArea = false;
                            }



                            // if (areaValues && areaValues.length > 0) {
                            p.cols[2].replaceValues(areaValues, areaValueNames);
                            // } else {
                            //     // p.cols.splice(2, 1);
                            //     p.cols[2].setValue('');
                            // }
                            p.updateValue();

                            if (activeAreaCode) {
                                p.cols[2].setValue(activeAreaCode);
                            }
                            break;
                        }
                    }
                }
            },
            setDefaultPickerData: function(regionData) {
                var t = this,
                    province, city, provinceIndex = -1,
                    cityIndex = -1,
                    provinceList, cityList, provincesName = [],
                    cityName = [],
                    regionPicker = {};
                if (regionData && regionData.length > 0) {
                    var bwCache = Cache.get(CacheConst.CACHE_BW) ? (typeof Cache.get(CacheConst.CACHE_BW) == 'string') ? JSON.parse(Cache.get(CacheConst.CACHE_BW)) : Cache.get(CacheConst.CACHE_BW) : {};
                    bwCache.regionData = regionData[0];
                    Cache.set(CacheConst.CACHE_BW, JSON.stringify(bwCache));
                    console.log('181: ' + JSON.stringify(Cache.get(CacheConst.CACHE_BW)));

                    provinceList = bwCache.regionData.provinceList;

                    if (t.tplData.province && t.tplData.city) {
                        // console.log('t.tplData.province = ' + t.tplData.province);
                        for (var i = 0; i < provinceList.length; i++) {
                            if (provinceList[i].districtProvinceName == t.tplData.province) {
                                provinceIndex = i;
                                break;
                            }
                        }
                        if (provinceIndex > -1) {
                            cityList = bwCache.regionData.provinceList[provinceIndex].cityList;
                            for (var j = 0; j < cityList.length; j++) {
                                if (cityList[j].districtCityName == t.tplData.city) {
                                    cityIndex = j;
                                } else {
                                    cityIndex = 0;
                                }
                            }
                        } else {
                            provinceIndex = 0;
                            cityList = bwCache.regionData.provinceList[provinceIndex].cityList;
                            cityIndex = 0;
                        }
                    } else {
                        provinceIndex = 0;
                        cityList = bwCache.regionData.provinceList[0].cityList;
                        cityIndex = 0;
                        console.log('no tplData');
                    }
                    for (var m = 0; m < provinceList.length; m++) {
                        provincesName.push(provinceList[m].districtProvinceName);
                    }
                    for (var n = 0; n < cityList.length; n++) {
                        cityName.push(cityList[n].districtCityName);
                    }
                    console.log(provincesName);
                    console.log(cityName);
                    province = bwCache.regionData.provinceList[provinceIndex].districtProvinceName;
                    city = bwCache.regionData.provinceList[provinceIndex].cityList[cityIndex].districtCityName;
                    console.log(province + ' ' + city);
                    regionPicker.colProvince = provincesName;
                    regionPicker.colCity = cityName;

                    return regionPicker;
                }
            },*/

        });
        return LayoutView;
    });