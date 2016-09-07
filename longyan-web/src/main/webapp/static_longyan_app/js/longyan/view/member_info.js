/**
 *login view
 *登录
 **/
define('js/longyan/view/member_info', [
        'text!js/longyan/template/member_info.tpl',
        'js/longyan/view/container',
        'js/element/view/linked-list-item',
        'js/element/view/location-view',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/think-input-box',
        'js/element/view/location-box',
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
    function(MemberListTpl, Container, LinkedListItem, LocationView, Cache, AlertUI, HeaderView, InputBox, ThinkInputBox, LocationBox, RadioBox, RoomBox, SelectBox, ButtonBox, LinkBox, TipsBar, PickerSelectBox, MemberApi, CommunityApi, CommonApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '.member-info-view';
        var form_id = '.body-container';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};

                t.$el.off('click');
                t.render();
                if (t.config && t.config.id) {
                    t.loadData();
                }
            },
            render: function() {
                var t = this;
                //设置背景
                $('body').css('background-color', '#f5f5f5');
                //设置模板
                t.$el.html(tpl(MemberListTpl, {}));
                t.container = new Container({
                    el: $(form_id)
                }, {
                    fieldName: 'member-info-form',
                })
                var $container = t.container.$el.find('.member-info-form')
                var __keyup = function() {

                };

                var right_button_text = '';
                var right_button_action = function(e) {
                };
                //==========heander view==========
                t.header_view = new HeaderView({
                    el: $('.header-container')
                }, {
                    text: '住宅',
                    goBackUrl: function() {
                        router.navigate('member_list', {
                            trigger: true
                        });
                    }
                });
                if (right_button_text && right_button_text.length > 0) {
                    t.$el.find('.header-container').find('.right-box').html(right_button_text);
                    t.$el.find('.right-box').on('click', {
                        t: t
                    }, right_button_action);
                }
                //==========fix button====
                // t.add_member_button = new ButtonBox({
                //     el: $('.member-fix-button-container')
                // }, {
                //     fieldName: 'add-member-button',
                //     text: '添加住宅'
                // }, {
                //     Click: function(e) {
                //         router.navigate('member_create_next', {
                //             trigger: true
                //         });
                //     }
                // });

                //==========body view==========

                // t.community_name_input = new InputBox({
                //     el: $container
                // }, {
                //     fieldName: 'community-name-input',
                //     placeholder: '名称',
                //     text: '',
                //     readonly: true,
                //     label_right: '>'
                // }, {

                // });
                t.community_name_input = new LinkedListItem({
                    el: $container,
                }, {
                    fieldName: 'community-name',
                    link: 'javascript:void(0);' 
                });
                // 设置地址信息
                t.location_view = new LocationView({
                    el: $container
                }, {
                    fieldName: 'location-view',
                });
                $('<div class="gap basic-gap"></div>').appendTo($container);
                // 设置基本信息
                t.renovationStatus = new InputBox({
                    el: $container
                }, {
                    fieldName: 'renovation-status',
                    text: '装&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;修:',
                    readonly: true
                });
                t.roomModel = new InputBox({
                    el: $container
                }, {
                    fieldName: 'room-model',
                    text: '户&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;型:',
                    readonly: true
                });
                t.roomType = new InputBox({
                    el: $container
                }, {
                    fieldName: 'room-type',
                    text: '房屋类型:',
                    readonly: true
                });
                t.housingAreaAmount = new InputBox({
                    el: $container
                }, {
                    fieldName: 'housing-area-amount',
                    text: '面&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;积:',
                    readonly: true
                });
                $('<div class="gap basic-gap"></div>').appendTo($container);
                // 按钮
                t.edit_member_button = new ButtonBox({
                    el: $container
                }, {
                    fieldName: 'edit-member-button',
                    text: '编辑'
                }, {
                    Click: function(e) {
                        if (t.config.id) {
                            router.navigate('member_update/' + t.config.id, {
                                trigger: true
                            });
                        }
                    }
                });
                $('<div class="gap basic-gap"></div>').appendTo($container);
                t.delete_member_button = new ButtonBox({
                    el: $container
                }, {
                    fieldName: 'delete-member-button',
                    text: '删除'
                }, {
                    Click: function(e) {
                        //表单类型
                        tipsAlert.open({
                            cancelText: '取消',
                            confirmText: '确定',
                            content: '你确定要删除此住宅吗?',
                            onConfirm: function(e) {
                                tipsAlert.close();
                                var success = function (data) {
                                    if (t.config.create) {
                                        //创建表单返回到上一页
                                        Backbone.history.history.back();
                                    } else {
                                        //更新表单,返回上一页
                                        Backbone.history.history.back();
                                    }
                                }
                                var error = function (code, msg) {
                                    tipsAlert.close();
                                    //显示异常信息
                                    tipsAlert.openAlert({
                                        content: msg
                                    });
                                };
                                MemberApi.deleteMember(t.config.id, success, error);
                            },
                            onCancel: function(e) {
                                tipsAlert.close();
                            }
                        });
                    }
                });
            },
            loadData: function() {
                var t = this;
                if (t.config && t.config.id) {
                    tipsAlert.openLoading({
                        content: '加载中...'
                    });
                    MemberApi.getMemberById(t.config.id, function(data) {
                        tipsAlert.close();
                        if (data) {
                            //设置缓存
                            Cache.set('member-manager-object', data);
                            //加载表单
                            t.setFormValue(data);
                        }
                    }, function(code, msg) {
                        tipsAlert.close();
                        tipsAlert.openAlert({
                            content: msg
                        });
                    });
                }
            },
            //设置表单值
            setFormValue: function(datas) {
                var data = datas.member;
                var t = this;
                Cache.set('member-create-step2-search-data', datas);
 
                var _address = '';
                t.header_view.setText(data.building + '栋' + data.unit + '单元' + data.room + '室');
                t.community_name_input.setValue(data.communityName, 'community_info/' + data.communityId);
                t.community_name_input.reRender();
                t.location_view.setValue({
                    roomMount: datas.roomMount,
                    alreadyInputAmount: datas.alreadyInputAmount,
                    address: 
                        data.province + ' ' + 
                        data.city + ' ' +
                        data.area + ' ' + 
                        data.communityAddress,
                    createAuth: data.createXingming,
                    createDate: data.createDate.split(' ')[0],
                    admin: data.ownerXingMing
                });
                t.location_view.reRender();
                //
                t.renovationStatus.setValue(data.renovationStatus);
                t.roomModel.setValue(data.bedroomAmount + ' 室 ' + data.hallAmount + ' 厅' );
                t.roomType.setValue(data.roomType);
                t.housingAreaAmount.setValue(data.housingAreaAmount + ' ㎡');
            },
            //获取表单值
            getFormValue: function() {
                var t = this;
            },
            //检查必填项
            checkForm: function() {
                var t = this;

                return true;
            }
        });
        return LayoutView;
    });