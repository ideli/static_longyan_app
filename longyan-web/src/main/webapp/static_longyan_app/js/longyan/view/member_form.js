/**
 *login view
 *登录
 **/
define('js/longyan/view/member_form', [
        'text!js/longyan/template/member_form.tpl',
        'text!js/longyan/template/community_search_item.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/think-input-box',
        'js/element/view/search-input-box',
        'js/element/view/location-box',
        'js/element/view/location-view',
        'js/element/view/radio-box',
        'js/element/view/room-box',
        'js/element/view/select-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/picker-box',
        'js/api/member',
        'js/api/community',
        'js/api/common'
    ],
    function(MemberListTpl, CommunitySearchItemTpl, Cache, AlertUI, HeaderView, InputBox, ThinkInputBox, SearchInputBox, LocationBox, LocationView, RadioBox, RoomBox, SelectBox, ButtonBox, LinkBox, TipsBar, PickerSelectBox, MemberApi, CommunityApi, CommonApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#member-info-view';
        var form_id = '#member-info-form';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                _log('config');
                _log(config);
                $('#member-fix-button-container').hide();
                t.$el.off('click');
                t.render();
                if (t.config.id || t.config.load_community) {
                    t.loadData();
                }
            },
            render: function() {
                _log('render');
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(MemberListTpl, {}));
                var __keyup = function() {

                };
                var __add = function(e) {
                    var t = e.data.t;
                    console.log(t);
                    //判断表单输入的合法性
                    if (t.checkForm()) {
                        //添加住户逻辑
                        console.log('add');
                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        MemberApi.createMember(t.getFormValue(),
                            function(data) {
                                tipsAlert.close();
                                //执行成功
                                tipsAlert.openToast({
                                    content: '恭喜您,创建成功',
                                    closeCallback: function() {
                                        tipsAlert.close();
                                        //跳转到详情
                                        if (data && data.id) {
                                            router.navigate('member_list', {
                                                trigger: true
                                            });
                                        } else {
                                            tipsAlert.openAlert({
                                                content: '系统异常'
                                            });
                                        }
                                    }
                                });
                            },
                            function(code, msg) {
                                tipsAlert.close();
                                //显示异常信息
                                tipsAlert.openAlert({
                                    content: msg
                                });
                            });
                    }
                };
                var __update = function(e) {
                    var t = e.data.t;
                    console.log('update');
                    //判断表单输入的合法性
                    if (t.checkForm()) {
                        //更新住户逻辑
                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        MemberApi.updateMember(t.getFormValue(),
                            function(data) {
                                tipsAlert.close();
                                //执行成功
                                tipsAlert.openToast({
                                    content: '恭喜您,修改成功',
                                    closeCallback: function() {
                                        tipsAlert.close();
                                        history.back();
                                    }
                                });
                            },
                            function(code, msg) {
                                tipsAlert.close();
                                //显示异常信息
                                tipsAlert.openAlert({
                                    content: msg
                                });
                            });
                    }

                };

                var buildHeaderRightButton = function(text, action) {
                    t.$el.find('#header-container').find('.right-box').html(text);
                    t.$el.find('#header-container').find('.right-box').off('click').on('click', {
                        t: t
                    }, action);
                }

                var header_text = '';
                var right_button_action = function() {};
                if (t.config && t.config.create) {
                    //创建住户
                    if(t.step == 2) {
                        var addressData = Cache.get('member-create-step2-search-data');
                        header_text = addressData.name;
                    } else {
                        header_text = '添加住宅';
                    }
                    right_button_action = __add;
                } else if (t.config && t.config.update) {
                    //修改住户                    
                    var addressData = Cache.get('member-create-step2-search-data');
                    _log('addressData');
                    _log(addressData)
                    header_text = addressData.communityName;
                    //header_text = '修改住宅';
                    right_button_action = __update;
                }

                //==========heander view==========
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: header_text,
                    goBackUrl: function() {
                        console.log(t.config);
                        if (t.config.info) {
                            //显示不可编辑的详情
                            Backbone.history.history.back();
                        } else {
                            //表单类型
                            tipsAlert.open({
                                cancelText: '否',
                                confirmText: '是',
                                content: '您是否放弃保存',
                                onConfirm: function(e) {
                                    tipsAlert.close();
                                    if (t.config.create) {
                                        //创建表单返回到上一页
                                        Backbone.history.history.back();
                                    } else {
                                        //更新表单,返回上一页
                                        Backbone.history.history.back();
                                    }
                                },
                                onCancel: function(e) {
                                    tipsAlert.close();
                                }
                            });
                        }

                    }
                });
                // Header右侧按钮
                var right_button_text = '';
                // 创建房屋第二步 或者 编辑
                if (t.config && t.config.create && t.step == 2 || t.config.update) {
                    right_button_text = '提交';
                }
                if (right_button_text && right_button_text.length > 0) {
                    t.$el.find('#header-container').find('.right-box').html(right_button_text);
                    t.$el.find('.right-box').on('click', {
                        t: t
                    }, right_button_action);
                }
                //==========form==========
                t.location_input = new LocationBox({
                    el: $(form_id)
                }, {
                    fieldName: 'location-input',
                    text: '选择地区',
                    placeholder: '请选择'
                });
                t.location_input.$el.find('#location-picker>.clear-both').before('<i class="icon iconfont icon-longyanlrightarrow"></i>');
                
                t.location_read_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'location-read-input',
                    text: '选择地区',
                    placeholder: '请选择',
                    readonly: true
                });
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

                var _template = CommunitySearchItemTpl;
                // var _template = '<div class="search-box-item" data-value="<#=data._value#>" data-text="<#=data._text#>"><#=data._textDisplay#></div><div class = "search-box-item-line"></div>';
                t.community_name_input = new SearchInputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-name-input',
                    text: '小区名称',
                    placeholder: '小区名称',
                    search_placeholder: '小区名称',
                    header_view_text: '小区名称',
                    alias: 'community',
                    template: _template
                }, {
                    Keyup: function(e, v) {
                        //获取省市区地理位置                
                        var area = t.location_input.getValue();
                        if (area && area.cityCode && area.areaCode) {
                            var provinceCode = area.provinceCode;
                            var cityCode = area.cityCode;
                            var areaCode = area.areaCode;
                            var text = v;
                            if (text && text.length > 0) {
                                CommunityApi.searchName(text, provinceCode, cityCode, areaCode, function(data) {
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
                    },
                    beforeConfirm: function(value, text, data) {
                        if (value) {
                            _log('beforeConfirm');
                            _log(data);
                            Cache.set('member-create-step2-search-data', data);
                            buildHeaderRightButton('下一步', headerNextRightButton);
                            return true;
                        } else {
                            if (text && text.length > 0) {
                                //没有选择商场
                                if (_is_mall_employee) {
                                    //商场的员工
                                    tipsAlert.open({
                                        cancelText: '否',
                                        confirmText: '是',
                                        content: '该小区不存在,是否立即创建?',
                                        onConfirm: function(e) {
                                            tipsAlert.close();
                                            router.navigate('community_create', {
                                                trigger: true
                                            });
                                        },
                                        onCancel: function(e) {
                                            tipsAlert.close();
                                        }
                                    });
                                } else {
                                    //非商场员工
                                    tipsAlert.openToast({
                                        content: '该小区不存在,请输入其他小区'
                                    });
                                }
                            } else {
                                tipsAlert.openToast({
                                    content: '请输入住宅名称'
                                });
                            }
                            return false;
                        }
                        return false;
                    },
                    beforeSelect: function(value, text) {
                        //选择前
                        if (value) {
                            var _item = $('.search-box-item[data-value="' + value + '"]');
                            if (_item && _item.attr('owner-id') == '0') {
                                return false;
                            }
                        }
                        return true;
                    },
                    beforeShowPopView: function() {
                        var locationInputVal = t.location_input.$el.find('#location-picker').attr('data-selected');
                        if(locationInputVal) {
                            return true;
                        } else {
                            //未选择省市区
                            tipsAlert.openToast({
                                content: '请先选择地区',
                                closeCallback: function() {
                                    tipsAlert.close();
                                }
                            });
                            return false;
                        }
                    }
                });
                var headerNextRightButton = function() {
                    // router.navigate('member_create_step2', {
                    //     trigger: true
                    // });
                    t.step = 2;
                    buildHeader();
                    buildLocationInfo();
                    buildBaseForm();
                }
                var buildHeader = function(){
                    var header_view_text = Cache.get('member-create-step2-search-data');
                    $('.header-box .text.title').text(header_view_text.name);
                    buildHeaderRightButton('提交', __add);
                }
                var buildBaseForm = function() {
                    t.base_info = new TipsBar({
                        el: $(form_id)
                    }, {
                        fieldName: 'base-info',
                        text: '基本信息'
                    });
                    t.building_input = new InputBox({
                        el: $(form_id)
                    }, {
                        fieldName: 'building-input',
                        text: '幢',
                        label_right: '幢',
                        // placeholder: '幢'
                    }, {
                        Keyup: function(e) {
                            var v = t.building_input.getValue();
                            //最长6位
                            if (v && v.length > 3) {
                                t.building_input.setValue(v.substring(0, 3));
                            }
                        }
                    });
                    t.unit_input = new InputBox({
                        el: $(form_id)
                    }, {
                        fieldName: 'unit-input',
                        text: '单&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;元',
                        // placeholder: '单元',
                        label_right: '单元'
                    }, {
                        Keyup: function(e) {
                            var v = t.unit_input.getValue();
                            //最长6位
                            if (v && v.length > 2) {
                                t.unit_input.setValue(v.substring(0, 2));
                            }
                        }
                    });
                    t.room_input = new InputBox({
                        el: $(form_id)
                    }, {
                        fieldName: 'room-input',
                        text: '房&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;号',
                        // placeholder: '室',
                        label_right: '室',
                        type: "tel"
                    }, {
                        Keyup: function(e) {
                            var v = t.room_input.getValue();
                            //最长6位
                            if (v && v.length > 6) {
                                t.room_input.setValue(v.substring(0, 6));
                            }
                        }
                    });
                    t.house_info = new TipsBar({
                        el: $(form_id)
                    }, {
                        fieldName: 'house-info',
                        text: '房屋信息'
                    });

                    if (t.config.update) {
                        t.building_input.setReadOnly(true);
                        t.unit_input.setReadOnly(true);
                        t.room_input.setReadOnly(true);
                    }

                    var renovation_status_input_container = $("<div class='renovation-status-input-container'></div>").appendTo($(form_id));
                    var room_layout_input_container = $("<div class='room-layout-input-container'></div>").appendTo($(form_id));
                    var room_type_input_container = $("<div class='room-type-input-container'></div>").appendTo($(form_id));
                    var room_area_input_container = $("<div class='room-area-input-container'></div>").appendTo($(form_id));

                    CommonApi.getRenovationStatus(function(data) {
                        console.log(data);
                        if (data && data.result && data.result.length > 0) {
                            var _dataList = [];
                            $.each(data.result, function(i, item) {
                                if (item && item.name) {
                                    _dataList.push({
                                        text: item.name,
                                        value: item.id
                                    });
                                }
                            });
                        }
                        t.renovation_status_input = new PickerSelectBox({
                            el: renovation_status_input_container
                        }, {
                            fieldName: 'renovation-status-input',
                            text: '装&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;修',
                            placeholder: '装修状况',
                            columns: 1, //表示picker有多少列
                            col_width: '90%', //每列的宽度
                            data: [
                                _dataList
                            ]
                        });
                        var _member = Cache.get('member-manager-object');
                        if (_member && _member.id && _member.id == t.config.id && _member.renovationStatusId) {
                            t.renovation_status_input.setValue(_member.renovationStatusId);
                        }

                    }, function(code, msg) {

                    });



                    CommonApi.getRoomType(function(data) {
                        console.log(data);
                        if (data && data.result && data.result.length > 0) {
                            var _dataList = [];
                            $.each(data.result, function(i, item) {
                                if (item && item.name) {
                                    _dataList.push({
                                        text: item.name,
                                        value: item.id
                                    });
                                }
                            });
                        }
                        t.room_type_input = new PickerSelectBox({
                            el: room_type_input_container
                        }, {
                            fieldName: 'room-type-input',
                            text: '房屋类型',
                            placeholder: '产权',
                            columns: 1, //表示picker有多少列
                            col_width: '90%', //每列的宽度
                            data: [
                                _dataList
                            ]
                        });
                        var _member = Cache.get('member-manager-object');
                        if (_member && _member.id && _member.id == t.config.id && _member.roomTypeId) {
                            t.room_type_input.setValue(_member.roomTypeId);
                        }
                    }, function(code, msg) {

                    });

                    var _room_layout_input_dataList = [
                        [{
                            text: '1室',
                            value: '1'
                        }, {
                            text: '2室',
                            value: '2'
                        }, {
                            text: '3室',
                            value: '3'
                        }, {
                            text: '4室',
                            value: '4'
                        }, {
                            text: '5室',
                            value: '5'
                        }, {
                            text: '6室',
                            value: '6'
                        }, {
                            text: '7室',
                            value: '7'
                        }, {
                            text: '8室',
                            value: '8'
                        }, {
                            text: '9室',
                            value: '9'
                        }],
                        [{
                            text: '0厅',
                            value: '0'
                        }, {
                            text: '1厅',
                            value: '1'
                        }, {
                            text: '2厅',
                            value: '2'
                        }, {
                            text: '3厅',
                            value: '3'
                        }, {
                            text: '4厅',
                            value: '4'
                        }, {
                            text: '5厅',
                            value: '5'
                        }]
                    ];
                    t.room_layout_input = new PickerSelectBox({
                        el: room_layout_input_container
                    }, {
                        fieldName: 'room-layout-input',
                        text: '户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型',
                        placeholder: '户型',
                        columns: 2, //表示picker有多少列
                        col_width: '40%', //每列的宽度
                        data: _room_layout_input_dataList
                    });


                    t.room_area_input = new InputBox({
                        el: $(form_id)
                    }, {
                        fieldName: 'room-area-input',
                        // placeholder: '㎡',
                        text: '面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;积',
                        label_right: '㎡',
                        type: "tel"
                    }, {
                        Keyup: function(e) {
                            var v = t.room_area_input.getValue();
                            //最长6位
                            if (v && v.length > 5) {
                                t.room_area_input.setValue(v.substring(0, 5));
                            }
                            console.log(v);
                        }
                    });
                }
                // 设置页面状态
                var setFormStatus = function() { 
                    if (t.config.update) {
                        t.community_name_input.setReadOnly(true);
                        $('.location-input').hide();
                        $('.location-read-input').hide();
                    } else {
                        $('.location-read-input').hide();
                    }_log(t.config);
                    if(t.config.load_community) {
                        buildHeaderRightButton('下一步', headerNextRightButton);
                    }
                }
                // 创建位置信息.
                var buildLocationInfo = function() {
                    if (t.config.update || t.config.create && t.step == 2) {
                        var addressData = Cache.get('member-create-step2-search-data');
                        _log(addressData);
                        var address = 
                            addressData.province + ' ' + 
                            addressData.city + ' ' + 
                            addressData.area;
                        if(t.config.update){
                            address += ' ' + addressData.communityAddress;
                        } else {
                            address += ' ' + addressData.address;
                        }
                        t.location_view = new LocationView({
                            el: $(form_id)
                        }, {
                            roomMount: addressData.roomMount,
                            alreadyInputAmount: addressData.alreadyInputAmount,
                            address: address,
                            // createAuth: '张三',
                            // admin: '张三',
                            // createDate: '张三',
                            fieldName: 'location-view',
                        });
                        t.location_input.$el.find('.location-input,.community-name-input').hide();
                    } else {
                        t.location_input.$el.hide();
                    }
                }
                // if (t.config.update || t.config.create && t.config.step == 2) {
                //     buildLocationInfo();
                //     buildBaseForm();
                // }
                // 编辑页面
                if(t.config.update) {
                    buildLocationInfo();
                    buildBaseForm();
                }
                setFormStatus();
            },
            loadData: function() {
                var t = this;
                if (t.config && (t.config.id || t.config.load_community)) {
                    var member = Cache.get('member-manager-object');
                    Cache.set('member-create-step2-search-data', member);
                    if (member && (t.config.update || t.config.load_community)) {
                        t.setFormValue(member);
                    }
                }
            },
            //设置表单值
            setFormValue: function(data) {
                var t = this;
                console.log(data);
                t.location_input.setValue(data.provinceCode, data.cityCode, data.areaCode);
                t.location_read_input.setValue(data.province + ' ' + data.city + ' ' + data.area);
                // t.community_name_input.setValue(data.communityId, data.communityName);
                t.community_name_input.setValue(data.communityId);
                t.community_name_input.setText(data.communityName);
                if (!t.config.load_community) {
                    if (data.building) {
                        t.building_input.setValue(data.building);
                    }
                    if (data.unit) {
                        t.unit_input.setValue(data.unit);
                    }
                    if (data.room) {
                        t.room_input.setValue(data.room);
                    }
                    if (data.renovationStatusId && t.renovation_status_input && t.renovation_status_input.setValue) {
                        t.renovation_status_input.setValue(data.renovationStatusId);
                    }
                    //房型
                    if (data.bedroomAmount && t.room_layout_input && t.room_layout_input.setValue) {
                        t.room_layout_input.setValue(data.bedroomAmount, data.hallAmount);
                    }
                    if (data.roomTypeId && t.room_type_input && t.room_type_input.setValue) {
                        t.room_type_input.setValue(data.roomTypeId);
                    }
                    //房屋面积
                    if (data.housingAreaAmount && t.room_area_input && t.room_area_input.setValue) {
                        t.room_area_input.setValue(data.housingAreaAmount);
                    }
                }
            },
            //获取表单值
            getFormValue: function() {
                var t = this;
                var member_id = 0;
                if (t.config && t.config.update) {
                    var member = Cache.get('member-manager-object');
                    member_id = member.id;
                }

                //省市区
                var area = t.location_input.getValue() || {};
                var provinceCode = area.provinceCode || '';
                var cityCode = area.cityCode || '';
                var areaCode = area.areaCode || '';
                //社区ID
                var communityId = t.community_name_input.getValue();
                //栋
                var building = t.building_input.getValue();
                //单元
                var unit = t.unit_input.getValue();
                //室
                var room = t.room_input.getValue();
                //装修情况
                var renovationStatus = '';
                if (t.renovation_status_input.getValue() && t.renovation_status_input.getValue()['col1Key']) {
                    renovationStatus = t.renovation_status_input.getValue()['col1Key'];
                }
                //户型
                var roomLayout = '';
                var bedroomAmount = 0;
                var hallAmount = 0;
                if (t.room_layout_input.getValue() && t.room_layout_input.getValue()['col1Key']) {
                    //房间数
                    bedroomAmount = t.room_layout_input.getValue()['col1Key'];
                    //厅数
                    hallAmount = t.room_layout_input.getValue()['col2Key'];
                }
                //房屋类型
                var roomType = '';
                if (t.room_type_input.getValue() && t.room_type_input.getValue()['col1Key']) {
                    roomType = t.room_type_input.getValue()['col1Key'];
                }
                //面积
                var _housingAreaAmount = t.room_area_input.getValue();
                return {
                    id: member_id,
                    provinceCode: provinceCode,
                    cityCode: cityCode,
                    areaCode: areaCode,
                    communityId: communityId,
                    building: building,
                    unit: unit,
                    room: room,
                    renovationStatusId: renovationStatus,
                    roomTypeId: roomType,
                    bedroomAmount: bedroomAmount,
                    hallAmount: hallAmount,
                    housingAreaAmount: _housingAreaAmount
                }
                // return {};
            },
            //检查必填项
            checkForm: function() {
                var t = this;
                if (!t.community_name_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请选择小区'
                    });
                    return false;
                }

                if (!t.building_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入住宅房号(幢/栋)'
                    });
                    return false;
                }

                if (!t.room_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入住宅房号'
                    });
                    return false;
                }

                if (!t.room_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入住宅房号'
                    });
                    return false;
                }
                if (!t.renovation_status_input.getValue()) {
                    tipsAlert.openToast({
                        content: '请选择装修情况'
                    });
                    return false;
                }
                if(!t.room_area_input.getValue()) {
                    t.room_area_input.setErrorByPlaceholder('此处不能为空.');
                    return false;
                }
                return true;
            }
        });
        return LayoutView;
    });