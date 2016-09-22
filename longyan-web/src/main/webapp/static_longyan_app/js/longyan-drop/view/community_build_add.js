/**
 *house list view
 *房屋信息  添加房栋号
 **/
define('js/longyan/view/member_build_add', [
        'text!js/longyan/template/member_build_add.tpl',
        'text!js/longyan/template/no_data.tpl',
        'text!js/longyan/template/no_network.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/api/build'
    ],
    function( CommunityBuildAddTpl, NoDataTpl, NoNetworkTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox,BuildApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#community-build-add-view';
        var form_id = '#community-build-add-form';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#f5f5f5');
                t.$el.html(tpl(CommunityBuildAddTpl, {}));

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '添加楼栋信息',
                    goBackUrl: function() {
                        tipsAlert.open({
                            cancelText: '否',
                            confirmText: '是',
                            content: '您是否放弃保存',
                            onConfirm: function(e) {
                                tipsAlert.close();
                                Backbone.history.history.back();
                            },
                            onCancel: function(e) {
                                tipsAlert.close();
                            }
                        });
                    }
                });

                t.building_name_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'building_name_input',
                    text: '楼栋号',
                    type: 'text',
                    placeholder: '例：“A、1"'
                });

                $('.building_name_input').after('<div class="gap basic-gap"></div>');

                t.unit_amount_input= new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'unit_amount_input',
                    type: 'text',
                    text: '单元数',
                    placeholder: '例：“1、甲”  没有则无需填写'
                });

                t.floor_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'floor_amount_input',
                    type: 'text',
                    text: '楼层数',
                    placeholder: '请填写楼栋层数'
                });

                t.room_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'room_amount_input',
                    type: 'text',
                    text: '总户数',
                    placeholder: '请填写楼栋总户数'
                });


                t.save_button = new ButtonBox({
                    el: $(form_id)
                }, {
                    fieldName: 'save-button',
                    id:'save-button',
                    text: '提交'
                }, {
                    Click: function(e) {
                        t.__add(t);
                    }
                });

                $('.save-button').css({'margin-top':'3rem'});

            },
            //添加小区
            __add: function(t) {
                var t =t;
                //添加小区逻辑
                if (t.checkForm()) {
                    var buildingName = t.building_name_input.getValue();
                    var unitAmount = t.unit_amount_input.getValue();
                    var floorAmount=t.floor_amount_input.getValue();
                    var roomAmount= t.room_amount_input.getValue();
                    var communityId="12345";
                    tipsAlert.openLoading({
                        content: '加载中...'
                    });
                    BuildApi.createBuilding(
                        communityId,
                        buildingName,
                        roomAmount,
                        unitAmount,
                        floorAmount,
                        function(data) {
                            tipsAlert.close();
                            if (data && data.id) {
                                //执行成功
                                router.navigate('community_success', {
                                    trigger: true
                                });

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
            checkForm: function() {
                var t = this;

                if (!t.building_name_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入楼栋号'
                    });
                    t.building_name_input.focus();
                    return false;
                }
                if (!t.floor_amount_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入楼层数'
                    });
                    t.floor_amount_input.focus();
                    return false;
                }
                if (isNaN(t.floor_amount_input.getValue())) {
                    tipsAlert.openToast({
                        content: '楼层数请输入数字'
                    });
                    t.floor_amount_input.focus();
                    return false;
                }
                if (!t.room_amount_input.isVerify()) {
                    tipsAlert.openToast({
                        content: '请输入楼层总户数'
                    });
                    t.room_amount_input.focus();
                    return false;
                }
                if (isNaN(t.room_amount_input.getValue())) {
                    tipsAlert.openToast({
                        content: '楼层总户数请输入数字'
                    });
                    t.room_amount_input.focus();
                    return false;
                }
                return true;
            }
        });
        return LayoutView;
    });