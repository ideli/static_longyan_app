/**
 * 社区详情 -> 社区编辑/添加社区
 **/
define('js/longyan/view/community_update', [
        'text!js/longyan/template/community_info.tpl',
        'text!js/longyan/template/community_search_item_in_community.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/picker-box',
        'js/element/view/input-box',
        'js/element/view/think-input-box',
        'js/element/view/search-input-box',
        'js/element/view/input-percentage-box',
        'js/element/view/location-current-city-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/radio-box',
        'js/element/view/tips-bar',
        'js/element/view/picker-box',
        'js/api/community',
        'js/api/common',
        'js/util/hybrid',
        'js/util/baiduMap'
    ],
    function(CommunityListTpl, CommunitySearchItemTpl, Cache, AlertUI, HeaderView, PickerBox, InputBox, ThinkInputBox, SearchInputBox, InputPercentageBox, locationCurrentCityBox, ButtonBox, LinkBox,radioBox, TipsBar, PickerSelectBox, CommunityApi, CommonApi, hybrid, baiduMap) {

        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#community-info-view';
        var form_id = '#community-info-form';
        var city;
        var LayoutView = Backbone.View.extend({
            events: {
            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.$el.off('click');
                t.render();
                t.loadData();
            },
            render: function() {
                var t = this;

                $('body').css('background-color', '#fff');
                t.$el.html(tpl(CommunityListTpl, {}));
                $('#community-info-view').addClass('community_create');
                hybrid.location(function(resp){
                    /*tipsAlert.openAlert({
                        content: resp.latitude+"|"+resp.longitude+"|"+resp.location+"|"+resp.province+"|"+resp.city+"|"+resp.district+"|"+resp.streetName+"|"+resp.streetNumber
                    });*/
                    city = resp.city;
                    t.location_input.setValue(city);
                });


                var right_button_text = '';
                var header_view_text = '';
                /*var right_button_action = function(e) {
                    var t = e.data.t;
                    console.log(e.data);
                    if (t.config && t.config.action && t.config.action == 'create') {
                        //create 小区
                        t.__add(t);
                    } else if (t.config && t.config.action && t.config.action == 'update') {
                        //update 小区
                        t.__update(t);
                    }
                };*/
                if (t.config && t.config.action && t.config.action == 'create') {
                    //创建小区
                    right_button_text = '保存'
                    header_view_text = '创建小区';
                } else if (t.config && t.config.action && t.config.action == 'update') {
                    //修改小区
                    right_button_text = '保存'
                    header_view_text = '修改小区';
                    t.config.readonly = true;
                }
                if (t.config.update_exist) {
                    header_view_text = '创建小区';
                }
                //==========heander view==========
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: header_view_text,
                    goBackUrl: function() {
                        tipsAlert.open({
                            cancelText: '否',
                            confirmText: '是',
                            content: '您是否放弃保存',
                            onConfirm: function(e) {
                                hybrid.backToHybrid("HomePage");
                            },
                            onCancel: function(e) {
                                tipsAlert.close();
                                //Backbone.history()
                            }
                        });
                    }
                });
                /*if (right_button_text && right_button_text.length > 0) {
                    t.$el.find('#header-container').find('.right-box').html(right_button_text);
                    t.$el.find('.right-box').on('click', {
                        t: t
                    }, right_button_action);
                }*/


                //获取所属商场的数据
                var mall_info = Cache.get('mall-info');

                var mall_object = mall_info[0];
                t.location_input_container = $("<div class='location-input-container'></div>").appendTo($(form_id));
                t.location_read_input_container = $("<div class='location-read-input-container'></div>").appendTo($(form_id));

                if (!mall_object) {
                    //没有所属商场的数据
                    t.location_input = new InputBox({
                        el: t.location_input_container
                    }, {
                        fieldName: 'location-input',
                        text: '当前城市',
                        readonly: true

                    });
                } else {
                    //有商场所属数据
                    // city
                    t.config.have_mall = true;
                    var _city = mall_object.city;


                    CommonApi.getAreaList({
                            _city: _city
                        },
                        function(data) {
                            if (data && data.result && data.result.length > 0) {
                                var _areaList = [];
                                $.each(data.result, function(i, item) {
                                    if (item && item.areaCode) {
                                        _areaList.push({
                                            text: item.area,
                                            value: item.areaCode
                                        });
                                    }
                                });
                                t.location_input = new InputBox({
                                    el: t.location_read_input_container
                                }, {
                                    fieldName: 'location-input',
                                    text: '当前城市',
                                    placeholder: '当前城市',
                                    // type: "number"
                                    columns: 1, //表示picker有多少列
                                    data: [
                                        _areaList
                                    ]
                                });
                                $('.location-input').find('.label').after("<div class='community-view-location-label'>" + _city + "</div>");
                            }

                        },
                        function(code, msg) {

                        });
                }

                //t.location_input.setValue(city);

                //不可编辑的地址显示框
                t.location_read_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'location-input-read',
                    text: '当前城市',
                    readonly: t.config.readonly,
                    value:city
                });
                //地址显示框和地址选择器的隐藏显示逻辑
                if (t.config && t.config.action && t.config.action == 'update') {
                    //只读状态
                    $('.location-read-input-container').hide();
                    $('.location-input-container').hide();
                } else {
                    //可编辑状态
                    $('.location-input-read').hide();
                }


                //====================正常的表单====================
                var _is_mall_employee = false; 
                var roleInfo = Cache.get('role-info');
                if (roleInfo && roleInfo.length > 0) {
                    $.each(roleInfo, function(i, item) {
                        if (item && item.alias && item.alias == 'role_mall_employee') {
                            //商场员工角色
                            _is_mall_employee = true;
                        }
                    });
                }
                t.community_name_input = new SearchInputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-name-input',
                    text: '小区名称',
                    placeholder: '小区名称',
                    search_placeholder: '小区名称',
                    header_view_text: '小区名称',
                    alias: 'community',
                    template: CommunitySearchItemTpl
                }, {
                    Keyup: function(e, v) {
                        //获取省市区地理位置
                        var area = t.getLocation();
                        if (area) {
                            //定位当前城市位置
                            var text = v;
                            if (text && text.length > 0) {
                                //调用接口检索联想内容
                                CommunityApi.searchName(text, city, function(data) {
                                    if (data && data.result && data.result.length > 0) {
                                        var _result = [];
                                        $.each(data.result, function(i, item) {
                                            var _tmp = item;
                                            item['_is_mall_employee'] = _is_mall_employee;
                                            _result.push(_tmp);
                                        });
                                        t.community_name_input.setSearchBox(_result, text, 'name', 'id');
                                    } else {
                                        t.community_name_input.clearSearchBox();
                                    }
                                }, function(code, msg) {
                                    //不需要显示异常信息
                                });
                            } else {
                                //清空搜索框
                                t.community_name_input.clearSearchBox();
                            }

                        }
                    },
                    SelectValue: function(value, text) {
                        if (value) {
                            //获取社区对象
                            tipsAlert.openLoading({
                                content: '加载中...'
                            });
                            CommunityApi.getCommunityById(value, function(data) {
                                tipsAlert.close();
                                //返回数据
                                if (data && data.community) {
                                    //放入缓存
                                    Cache.set('community-manager-object', data.community);
                                    //跳转到更新界面
                                    // router.navigate('community_update_exist/' + value, {
                                    //     trigger: true
                                    // });
                                    router.community_update_exist(value);
                                } else {
                                    tipsAlert.openAlert({
                                        content: '系统异常'
                                    });
                                }
                            }, function(code, msg) {
                                tipsAlert.close();
                                //显示异常信息
                                tipsAlert.openAlert({
                                    content: msg
                                });
                            });
                        }
                    },
                    beforeConfirm: function(value, text) {
                        // if (text && text.length > 0) {
                        //     //将小区名称缓存到本地
                        //     var __tmp_cache = t.getLocation();
                        //     __tmp_cache['name'] = text;
                        //     Cache.set('community-tmp-create-object', __tmp_cache);
                        //     //跳转到创建小区界面
                        //     router.community_create();
                        //     // return true;
                        // }
                        if (text && text.length > 0) {
                            if (t.config.update_exist) {
                                //如果是创建已有的小区的 case
                                //配置reset成create模式
                                t.config.action = 'create'
                                t.config.update_exist = false;
                                t.config.id = 0;

                                t.community_address_input.setReadOnly(false);

                                //隐藏只读框
                                $('.location-input-read').hide();
                                //显示编辑框
                                $('.location-read-input-container').show();
                                //清空表单
                                t.community_address_input.setValue('');
                                t.community_others_name_input.setValue('');
                                //t.community_area_covered_input.setValue('');
                                t.community_room_amount_input.setValue('');
                                t.community_building_amount_input.setValue('');
                                t.community_occupancy_rate_input.setValue(0);
                                t.community_price_section_input.setValue('');
                                t.community_building_type_input.setValue('');
                                t.community_building_renovation_input.setValue('');
                                t.community_building_age_input.clearValue('');
                                t.community_developer_input.setText('');
                                t.community_property_company_input.setValue('');
                                t.community_hotline_input.setValue('');
                            }
                            return true;
                        }
                        tipsAlert.openToast({
                            content: '请输入小区名称'
                        });
                        // t.config.action = 'create';
                        // t.config.update_exist = false;
                        return false;
                    },
                    beforeSelect: function(value, text) {
                        //选择前
                        if (value) {
                            var _item = $('.search-box-item[data-value="' + value + '"]');
                            if (_item && _item.attr('owner-id') != '0') {
                                // 该小区已创建，请输入其他小区名称
                                tipsAlert.openToast({
                                    content: '该小区已创建，请输入其他小区名称'
                                });
                                return false;
                            }
                        }
                        return true;
                    }
                });
                  $('<div class="gap basic-gap owner-gap"></div>').appendTo($(form_id));
                t.community_address_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-address-input',
                    text: '详细地址',
                    placeholder: '街道信息'
                });
                t.community_others_name_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-others-name-input',
                    text: '小区别称',
                    type: "text",
                    placeholder: '非必填'
                });
                /*t.community_area_covered_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-area-covered-input',
                    text: '占地面积',
                    type: "tel",
                    placeholder: '例如:100.000㎡'
                });*/
                  $('<div class="gap basic-gap owner-gap"></div>').appendTo($(form_id));
                t.community_room_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-room-amount-input',
                    text: '总户数',
                    type: "tel",
                    placeholder: '例如:200户'
                });
                t.community_building_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-building-amount-input',
                    text: '楼栋数',
                    type: "tel",
                    placeholder: '例如:20幢'
                });

                t.community_occupancy_rate_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'range-input',
                    text: '入住率',
                    type: "tel",
                    placeholder: '例如:75%'
                });

                /*t.community_occupancy_rate_input = new InputPercentageBox({
                    el: $(form_id)
                }, {
                    fieldName: 'range-input',
                    text: '入住率',
                    placeholder: '入住率'
                });*/
                // t.range_input.setValue(0.5);

                t.community_price_section_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-price-section-input',
                    text: '房屋均价',
                    type: "tel",
                    placeholder: '例如:28000元/㎡'

                }, {
                    Keyup: function(e) {
                        var v = t.community_price_section_input.getValue();
                        //最长6位
                        if (v && v.length > 6) {
                            t.community_price_section_input.setValue(v.substring(0, 6));
                        }
                    }
                });


                t.community_building_type_input = new radioBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-building-type-input',
                    text: '建筑类型',
                    selections: [{'value':'0','text':'商住'},{'value':'1','text':'别墅'},{'value':'2','text':'高层'}],
                    // type: "number"
                    data: [
                    ]
                });
                t.community_building_renovation_input = new radioBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-building-renovation-input',
                    text: '交房装修',
                    placeholder: '',
                    // type: "number"
                    selections: [{'value':'0','text':'精装'},{'value':'1','text':'毛坯'}],
                    data: [
                    ]
                });
                /*t.community_building_age_input = new PickerSelectBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-building-age-input',
                    text: '建筑年代',
                    placeholder: '年',
                    // type: "number"
                    columns: 1, //表示picker有多少列
                    data: [
                        yearData
                    ]
                });*/

                 var today = new Date();
                   $('<div class="gap basic-gap owner-gap"></div>').appendTo($(form_id));
               var year = today.getFullYear() + 5;
                var yearData = [];
                for (var i = 0; i < 100; i++) {
                    yearData.push({
                        text: (year - i) + '年',
                        value: year - i
                    });
                }
               t.community_building_age_input = new PickerSelectBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-building-age-input',
                    text: '交房时间',
                    placeholder: '年',
                    // type: "number"
                    columns: 1, //表示picker有多少列
                    data: [
                        yearData
                    ]
                });

                t.community_developer_input = new SearchInputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-developer-input',
                    text: '开发商',
                    placeholder: '公司名称',
                    search_placeholder: '公司名称',
                    header_view_text: '开发商',
                    alias: 'developer'
                }, {
                    Keyup: function(e, v) {
                        //获取联想开发商列表
                        var developer = v;
                        if (developer && developer.trim().length > 0) {
                            CommunityApi.searchDeveloper({
                                name: developer
                            }, function(data) {
                                if (data && data.result && data.result.length > 0) {
                                    t.community_developer_input.setSearchBox(data.result, developer, 'name', 'name');
                                } else {
                                    //清空搜索框
                                    t.community_developer_input.clearSearchBox();
                                }
                            }, function(code, msg) {

                            });
                        } else {
                            //清空搜索框
                            t.community_developer_input.clearSearchBox();
                        }
                    },
                    SelectValue: function(value, text) {}
                });
                t.community_property_company_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-property-company-input',
                    text: '物业公司',
                    placeholder: '物业公司',
               });


                t.community_hotline_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-hotline-input',
                    text: '物业电话',
                    placeholder: '区号-电话号码'
                });
                t.community_commit_input = new ButtonBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-commit-input',
                    text: '提交',
                }, {
                    Click: function(e) {
                        if (t.config && t.config.action && t.config.action == 'create') {
                            //create 小区
                            t.__add(t);
                        } else if (t.config && t.config.action && t.config.action == 'update') {
                            //update 小区
                           t.__update(t);
                        }
                    }
                });
                if (t.config && t.config.action && t.config.action == 'update') {
                    if (!t.config.update_exist) {
                        //更新小区
                        t.community_name_input.setReadOnly(true);
                    }
                    //更新下小区+更新已存在的小区
                    //t.community_address_input.setReadOnly(true);
                } else {
                    //创建小区
                    // var _tmp_create = Cache.get('community-tmp-create-object');
                    // if (_tmp_create) {
                    //     //填充创建框
                    //     t.setFormValue(_tmp_create);
                    // }
                }
            },
           //添加小区
            __add: function(t) {
                //添加小区逻辑
                if (t.checkForm()) {
                    //获取输入的地理位置
                    //获取输入的社区信息
                    var name = t.community_name_input.getText();
                    var address = t.community_address_input.getValue();

                    //var areaMonut = t.community_area_covered_input.getValue();
                    var shortName = t.community_others_name_input.getValue();
                    var roomMount = t.community_room_amount_input.getValue();
                    var buildingAmount = t.community_building_amount_input.getValue();
                    var checkMemberRate = t.community_occupancy_rate_input.getValue();
                    if (checkMemberRate) {
                        checkMemberRate = checkMemberRate.replace(/%/, "");
                    }

                    var priceSection = t.community_price_section_input.getValue();
                    //var buildingDate = t.community_building_age_input.getValue()['col1Key'];
                    var constructionTypes = t.community_building_type_input.getValue();
                    var renovations = t.community_building_renovation_input.getValue();
                    var deliveryTime = t.community_building_age_input.getValue()['col1Key'];
                    var developers = t.community_developer_input.getText();
                    var propertyName = t.community_property_company_input.getValue();
                    var hotline = t.community_hotline_input.getValue();

                    tipsAlert.openLoading({
                        content: '加载中...'
                    });


                    CommunityApi.createCommunity(
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
                        function(data) {
                            console.log(data)
                            tipsAlert.close();

                            if (data && data.id) {
                                //执行成功
                                Cache.set('community-data-id', data.id);
                                router.navigate('community_success', {
                                    trigger: true
                                });

                                /*tipsAlert.openToast({
                                    content: '恭喜您,添加成功',
                                    closeCallback: function() {
                                        tipsAlert.close();
                                        router.navigate('community_preview/' + data.id, {
                                            trigger: true
                                        });
                                    }
                                });*/
                            } else {
                                tipsAlert.openAlert({
                                    content: '系统异常'
                                });
                            }
                        },
                        function(code, msg) {
                            tipsAlert.close();
                            //显示异常信息
                            tipsAlert.openAlert({
                                content: msg
                            });
                        });
                }
            },
            //更新小区
            __update: function(t) {
                //更新小区逻辑
                var _tipsAlertConfirmText = '是否修改小区';
                var _tipsAlertSuccessText = '恭喜您,修改成功';
                if (t.config.update_exist) {
                    _tipsAlertConfirmText = '是否创建小区';
                    _tipsAlertSuccessText = '恭喜您,添加成功';
                }
                if (t.checkForm()) {
                    var community = Cache.get('community-manager-object');
                    var community_id = t.config.id;
                    var name = t.community_name_input.getText();
                    var address = t.community_address_input.getValue();

                    //var areaMonut = t.community_area_covered_input.getValue();
                    var shortName = t.community_others_name_input.getValue();
                    var roomMount = t.community_room_amount_input.getValue();
                    var buildingAmount = t.community_building_amount_input.getValue();
                    var checkMemberRate = t.community_occupancy_rate_input.getValue();
                    if (checkMemberRate) {
                        checkMemberRate = checkMemberRate.replace(/%/, "");
                    } 
                    var priceSection = t.community_price_section_input.getValue();
                    var constructionTypes = t.community_building_type_input.getValue();
                    var renovations = t.community_building_renovation_input.getValue();
                    var deliveryTime = t.community_building_age_input.getValue()['col1Key'];
                    var developers = t.community_developer_input.getText();
                    var propertyName = t.community_property_company_input.getValue();
                    var hotline = t.community_hotline_input.getValue();


                    tipsAlert.openLoading({
                        content: '加载中...'
                    });
                    CommunityApi.updateCommunity(
                        community_id,
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
                        function(data) {
                            tipsAlert.close();
                            tipsAlert.openToast({
                                content: _tipsAlertSuccessText,
                                closeCallback: function() {
                                    tipsAlert.close();
                                    if (t.config.update_exist) {
                                        // window.history.go(-2);

                                        router.navigate('community_preview/' + t.config.id, {
                                            trigger: true
                                        });
                                    } else {
                                        history.back();
                                    }
                                }
                            });
                        },
                        function(code, msg) {
                            tipsAlert.close();
                            //显示异常信息
                            tipsAlert.openAlert({
                               // content: msg
                            });
                        });
                }
            },
            loadData: function() {
                var t = this;
                if (t.config && t.config.action && t.config.action == 'update') {
                    //填充表单
                    var community = Cache.get('community-manager-object');

                    if (t.config.update_exist) {
                        //更新已有小区
                        CommunityApi.getCommunityById(t.config.id, function(data) {
                            tipsAlert.close();
                            //返回数据
                            if (data && data.community) {
                                Cache.set('community-manager-object', data.community);
                                t.setFormValue(data.community);
                            } else {
                                tipsAlert.openAlert({
                                    content: '系统异常'
                                });
                            }
                        }, function(code, msg) {
                            tipsAlert.close();
                            //显示异常信息
                            tipsAlert.openAlert({
                                content: msg
                            });
                        });

                        return;
                    }

                    if (community) {
                        t.setFormValue(community);
                    } else {
                        tipsAlert.openAlert({
                            content: '系统异常'
                        });
                    }
                }
            },
            //设置表单
            setFormValue: function(community) {
                var t = this;
                t.location_read_input.setValue(community.city);
                // t.community_name_input.setValue(community.name, community.name);
                t.community_name_input.setText(community.name);
                t.community_address_input.setValue(community.address);
                t.community_others_name_input.setValue(community.shortName);
                //总面积
                /*if (community.areaMonut) {
                    t.community_area_covered_input.setValue(community.areaMonut); 
                }*/
                //总户数
                if (community.roomMount) {
                    t.community_room_amount_input.setValue(community.roomMount);
                }
                //楼栋数
                if (community.buildingAmount) {
                    t.community_building_amount_input.setValue(community.buildingAmount);
                }
                //入住率
                if (community.occupanyRate) {
                    t.community_occupancy_rate_input.setValue(community.occupanyRate + '%');
                }
                //房价区间
                if (community.priceSection) {
                    t.community_price_section_input.setValue(community.priceSection);
                }
                //建筑类型
                if (community.constructionTypes) {
                    t.community_building_type_input.setValue(community.constructionTypes);
                }
                //交房装修
                if (community.renovations) {
                    t.community_building_renovation_input.setValue(community.renovations);
                }
                //交房时间
                if (community.deliveryTime) {
                    t.community_building_age_input.setValue(community.deliveryTime);
                }
                /*//建筑年代
                t.community_building_age_input.setValue(community.buildingDate);*/
                //开发商
                t.community_developer_input.setText(community.developers);
                //物业公司
                t.community_property_company_input.setValue(community.propertyName);
                //物业电话
                t.community_hotline_input.setValue(community.hotline);

            },
            //检查必填字段是否为空
            checkForm: function() {
                var t = this;
                var area = t.getLocation();
                var community_name = t.community_name_input.getText();
                var community_address = t.community_address_input.getValue();
                var community_others_name = t.community_others_name_input.getValue();
                if (!/^[a-zA-z0-9_\u4e00-\u9fa5]+$/g.test(community_name)) {
                    tipsAlert.openAlert({
                        content: '文本中不能含有特殊字符'
                    });
                    return true; 
                } 
                if (community_name.length>50) {
                    t.community_name_input.setValue(community_name.substr(0, 60));
                 }
                if (community_others_name.length>50) {
                    t.community_others_name_input.setValue(community_others_name.substr(0, 60));
                 }
                if (community_address.length>50) {
                    t.community_address_input.setValue(community_address.substr(0, 60));
                 }
                if (t.config && t.config.action && t.config.action == 'create') {
                    /*if (!area || !area.areaCode) {
                        tipsAlert.openToast({
                            content: '请选择省市区'
                        });
                        return false;
                    }*/

                    if (!$nvwa.string.isVerify(t.community_name_input.getText())) {
                        tipsAlert.openToast({
                            content: '请输入小区名称'
                        });
                        t.community_name_input.focus();
                        return false;
                    }
                }


                if (!t.community_address_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入小区详细地址'
                    });
                    t.community_address_input.focus();
                    return false;
                }
                /*if (!t.community_area_covered_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入占地面积'
                    });
                    t.community_area_covered_input.focus();
                    return false;
                }
                if (isNaN(t.community_area_covered_input.getValue())) {
                    tipsAlert.openToast({
                        content: '占地面积请输入数字'
                    });
                    t.community_area_covered_input.focus();
                    return false;
                }*/

                if (!t.community_room_amount_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入总户数'
                    });
                    t.community_room_amount_input.focus();
                    return false;
                }
                if (isNaN(t.community_room_amount_input.getValue())) {
                    tipsAlert.openToast({
                        content: '总户数请输入数字'
                    });
                    t.community_room_amount_input.focus();
                    return false;
                }
                if (!t.community_building_amount_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入楼栋数'
                    });
                    t.community_building_amount_input.focus();
                    return false;
                }
                if (isNaN(t.community_building_amount_input.getValue())) {
                    tipsAlert.openToast({
                        content: '楼栋数请输入数字'
                    });
                    t.community_building_amount_input.focus();
                    return false;
                }

                if (!t.community_price_section_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入房屋均价'
                    });
                    t.community_price_section_input.focus();
                    return false;
                }
                if (isNaN(t.community_price_section_input.getValue())) {
                    tipsAlert.openToast({
                        content: '房屋均价请输入数字'
                    });
                    t.community_price_section_input.focus();
                    return false;
                }
                /*if (!t.community_building_age_input.getValue() || !$nvwa.string.isVerify(t.community_building_age_input.getValue()['col1Key'])) {
                    tipsAlert.openToast({
                        content: '请输入建造年代'
                    });
                    return false;
                }*/
                if (!t.community_building_type_input.getValue() || !$nvwa.string.isVerify(t.community_building_type_input.getValue())) {
                    tipsAlert.openToast({
                        content: '请输入建造类型'
                    });
                    return false;
                }
                if (!t.community_building_renovation_input.getValue() || !$nvwa.string.isVerify(t.community_building_renovation_input.getValue())) {
                    tipsAlert.openToast({
                        content: '请输入交房装修'
                    });
                    return false;
                }
                if (!t.community_building_age_input.getValue() || !$nvwa.string.isVerify(t.community_building_age_input.getValue()['col1Key'])) {
                    tipsAlert.openToast({
                        content: '请输入交房时间'
                    });
                    return false;
                }
                if (!$nvwa.string.isVerify(t.community_developer_input.getText())) {
                    tipsAlert.openToast({
                        content: '请输入开发商名称'
                    });
                    // t.community_developer_input.focus();
                    return false;
                }
                if (!$nvwa.string.isVerify(t.community_property_company_input.getValue())) {
                    tipsAlert.openToast({
                        content: '请输入物业公司名称'
                    });
                    // t.community_developer_input.focus();
                    return false;
                }
                return true;
            },
            //获取省市区选择信息
            getLocation: function() {
                var t = this;
                var area = {};
                if (t.config.update_exist) {
                    //创建已存在的小区
                    var _community = Cache.get('community-manager-object');
                    var _city = _community.city;
                    area = {
                        _city: _city,
                    };
                    return area;
                }

                if (t.config.have_mall) {
                    //员工有所属商场的情况
                    //省市地址数据从员工商场关系带过来
                    var mall_info = Cache.get('mall-info');
                    var mall_object = {};
                    if (mall_info && mall_info.length > 0) {
                        mall_object = mall_info[0];
                    } else {
                        tipsAlert.openAlert({
                            content: '系统异常'
                        });
                    }
                    var _city = mall_object.city;

                    /*var _areaCode = null;
                    if (t.location_input && t.location_input.getValue() && t.location_input.getValue()['col1Key']) {
                        _areaCode = t.location_input.getValue()['col1Key'];
                    }*/

                   /* area = {
                        _city: _city,
                    };*/
                } else {
                    //员工没有所属商场的情况
                    area = t.location_input.getValue();

                }
                return area;
            }
        });
        return LayoutView;
    });