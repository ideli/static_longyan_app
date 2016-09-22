/**
 * 社区详情 -> 社区编辑/添加社区
 **/
define('js/longyan/view/building_form', [
        'text!js/longyan/template/building_form.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/picker-box',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/api/community',
        'js/api/common'
    ],
    function(BuildingFormTpl, Cache, AlertUI, HeaderView, PickerBox, InputBox, ButtonBox, CommunityApi, CommonApi) {

        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#building-view';
        var form_id = '#building-form';
        var city;
        var LayoutView = Backbone.View.extend({
            events: {},
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.$el.off('click');
                t.editable = false;
                t.render();

            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#fff');
                t.$el.html(tpl(BuildingFormTpl, {}));
                //==========heander view==========
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '楼栋信息'
                });

                t.building_number_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'building-number-input',
                    text: '楼栋号',
                    type: "text"
                });
                t.building_floor_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'building-floor-amount-input',
                    text: '楼层数',
                    type: "tel",
                    label_right: '层'
                });
                t.building_unit_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'building-unit-amount-input',
                    text: '单元数',
                    type: "tel",
                    placeholder: '非必填项',
                    label_right: '个'
                });
                t.building_room_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'building-room-amount-input',
                    text: '总户数',
                    type: "tel",
                    label_right: '户'
                });

                $('<div class="gap basic-gap owner-gap button-container"></div>').appendTo($(form_id));

                if (t.config.create) {
                    t.create_commit_button = new ButtonBox({
                        el: $('.button-container')
                    }, {
                        fieldName: 'create-commit-button',
                        text: '保存',
                    }, {
                        Click: function(e) {
                            //保存添加的记录
                            //提交表单
                            t.__add();
                        }
                    });
                    //保存按钮灰掉
                    t.create_commit_button.setDisable(true);

                } else if (t.config.info) {
                    //把表单设置为只读
                    t.building_number_input.setReadOnly(true);
                    t.building_floor_amount_input.setReadOnly(true);
                    t.building_unit_amount_input.setReadOnly(true);
                    t.building_room_amount_input.setReadOnly(true);
                    //加载数据
                    t.loadData();

                    t.save_commit_button = new ButtonBox({
                        el: $('.button-container')
                    }, {
                        fieldName: 'save-commit-button',
                        text: '保存',
                    }, {
                        Click: function(e) {
                            //保存更改的记录
                            //提交表单
                            t.__update();
                        }
                    });
                    t.delete_button = new ButtonBox({
                        el: $('.button-container')
                    }, {
                        fieldName: 'delete-button',
                        text: '删除',
                    }, {
                        Click: function(e) {
                            //保存更改的记录
                            //提交表单
                            t.__delete();
                        }
                    });

                    t.save_commit_button.hide();
                    t.delete_button.hide();

                    t.edit_button = new ButtonBox({
                        el: $('.button-container')
                    }, {
                        fieldName: 'edit-button',
                        text: '编辑',
                    }, {
                        Click: function(e) {
                            //显示保存按钮并且设置表单为可编辑
                            t.edit_button.hide();
                            t.save_commit_button.show();
                            t.delete_button.show();
                            t.building_number_input.setReadOnly(false);
                            t.building_floor_amount_input.setReadOnly(false);
                            t.building_unit_amount_input.setReadOnly(false);
                            t.building_room_amount_input.setReadOnly(false);

                        }
                    });
                    //加载数据
                    t.loadData();
                }
            },
            //添加
            __add: function(t) {
                //添加小区逻辑
                if (t.checkForm()) {
                    //获取输入的地理位置
                    //获取输入的社区信息

                }
            },
            //更新
            __update: function(t) {
                var t = this;
                //更新逻辑                
                if (t.checkForm()) {
                    var buildingName = t.building_number_input.getValue() || '';
                    var building_room_amount = t.building_room_amount_input.getValue() || 0;
                    var building_unit_amount = t.building_unit_amount_input.getValue() || 0;
                    var building_floor_amount = t.building_floor_amount_input.getValue() || 0;

                    tipsAlert.openLoading({
                        content: '加载中...'
                    });

                    CommunityApi.updateCommunityBuilding(t.config.id, buildingName, building_room_amount, building_unit_amount, building_floor_amount, function(data) {
                        tipsAlert.close();
                        Backbone.history.history.back();
                    }, function(code, msg) {
                        tipsAlert.close();
                        tipsAlert.openToast({
                            content: '添加楼栋失败'
                        });
                    });
                }
            },
            __delete: function() {
                //删除逻辑
                var t = this;
                var tmp_building = window.tmp_building;
                if (t.config && t.config.id && tmp_building) {
                    tipsAlert.openLoading({
                        content: '加载中...'
                    });
                    //调用API删除
                    CommunityApi.deleteCommunityBuilding(t.config.id, tmp_building.community_id, function(data) {
                        tipsAlert.close();
                        Backbone.history.history.back();
                    }, function(code, msg) {
                        tipsAlert.close();
                        tipsAlert.openToast({
                            content: '系统异常'
                        });
                    });
                } else {
                    tipsAlert.openToast({
                        content: '数据异常'
                    });
                }
            },
            //从缓存加载数据
            loadData: function() {
                var t = this;
                var tmp_building = window.tmp_building;
                if (tmp_building) {
                    t.setFormValue({
                        buildingName: tmp_building.building_no,
                        floorAmount: tmp_building.floor_amount,
                        unitAmount: tmp_building.unit_amount,
                        roomAmount: tmp_building.room_amount
                    });
                } else {
                    tipsAlert.openToast({
                        content: '数据异常'
                    });
                }
            },
            //设置表单
            setFormValue: function(data) {
                var t = this;
                if (data) {
                    t.building_number_input.setValue(data.buildingName);
                    t.building_floor_amount_input.setValue(data.floorAmount);
                    t.building_unit_amount_input.setValue(data.unitAmount);
                    t.building_room_amount_input.setValue(data.roomAmount);
                }
            },
            //检查必填字段是否为空
            checkForm: function() {
                var t = this;

                return true;
            }
        });
        return LayoutView;
    });