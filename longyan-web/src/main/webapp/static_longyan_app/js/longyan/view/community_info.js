/**
 * 社区列表 -> 社区详情
 **/
define('js/longyan/view/community_info', [
        'text!js/longyan/template/community_info.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/location-view',
        'js/element/view/picker-box',
        'js/element/view/input-box',
        'js/element/view/think-input-box',
        'js/element/view/location-box',
        'js/element/view/input-percentage-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/api/community'
    ],
    function(CommunityListTpl, Cache, AlertUI, HeaderView, LocationView, PickerBox, InputBox, ThinkInputBox, LocationBox, InputPercentageBox, ButtonBox, LinkBox, TipsBar, CommunityApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#community-info-view';
        var form_id = '#community-info-form';
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
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(CommunityListTpl, {}));
                t.$el.find('#community-info-view').addClass('community-info-detail-view');

                right_button_text = '编辑'
                t.config.readonly = true;
                //==========heander view==========
                if (t.config && t.config.action && t.config.action == 'preview') {
                    t.header_view = new HeaderView({
                        el: $('#header-container')
                    }, {
                        text: '载入中...',
                        goBackUrl: function() {
                            if (t.config.report_detail) {
                                //社区报表详情
                                Backbone.history.history.back();
                            } else {
                                //社区详情
                                router.navigate('community_list', {
                                    trigger: true
                                });
                            }
                        }
                    });
                } else {
                    t.header_view = new HeaderView({
                        el: $('#header-container')
                    }, {
                        text: '载入中...'
                    });
                }


                if (!t.config.report_detail) {
                    //非社区报表详情状态
                    if (right_button_text && right_button_text.length > 0) {
                        // t.$el.find('#header-container').find('.right-box').html(right_button_text);
                        // t.$el.find('.right-box').on('click', {
                        //     t: t
                        // }, function(e) {                            
                        //     if (t.config.id) {
                        //         router.navigate('community_update/' + t.config.id, {
                        //             trigger: true
                        //         });
                        //     }
                        // });
                    }
                } else {
                    $('#community-info-button-container').hide();
                }

                // 设置地址信息
                /*t.location_view = new LocationView({
                    el: $(form_id)
                }, {
                    fieldName: 'location-view',
                });
                $('<div class="gap basic-gap"></div>').appendTo( $(form_id));*/
                /*t.location_read_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'location-input-read',
                    text: '地区',
                    readonly: t.config.readonly
                });



                t.community_address_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-address-input',
                    text: '小区地址',
                    readonly: t.config.readonly
                });*/
                t.community_base_info_input = new TipsBar({
                    el: $(form_id)
                }, {
                    fieldName: 'community-base-info-tipsbar',
                    text: '基本信息',
                });
                t.community_address_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-address-input',
                    text: '详细地址:',
                    readonly: t.config.readonly,

                }, {
                    Keyup: function() {
                        var value = t.community_area_covered_input.getValue();
                        if (isNaN(value)) t.community_area_covered_input.setValue(value.substring(0, value.length - 1));
                    }
                });
                t.community_area_covered_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-area-covered-input',
                    text: '占地面积:',
                    readonly: t.config.readonly,
                    placeholder: '㎡',

                }, {
                    Keyup: function() {
                        var value = t.community_area_covered_input.getValue();
                        if (isNaN(value)) t.community_area_covered_input.setValue(value.substring(0, value.length - 1));
                    }
                });

                /*t.community_room_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-room-amount-input',
                    text: '总户数',
                    readonly: t.config.readonly,
                    placeholder: '户'

                });*/
                t.community_building_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-building-amount-input',
                    text: '楼幢数:',
                    readonly: t.config.readonly,
                    placeholder: '幢'

                });

                t.community_occupancy_rate_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-occupancy-rate-input',
                    text: '入住率:',
                    readonly: t.config.readonly,
                    placeholder: '%'

                });

                t.community_price_section_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-price-section-input',
                    text: '房价:',
                    readonly: t.config.readonly,
                    placeholder: '元/㎡'
                });
                $('<div class="gap basic-gap owner-gap"></div>').appendTo($(form_id));
                t.community_developer_info_input = new TipsBar({
                    el: $(form_id)
                }, {
                    fieldName: 'community-developer-info-tipsbar',
                    text: '开发商信息',
                });
                //$('<hr>').appendTo($(form_id));
                t.community_developer_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-developer-input',
                    text: '开发商:',
                    readonly: t.config.readonly
                });

                t.community_building_age_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-building-age-input',
                    text: '交房时间:',
                    readonly: t.config.readonly,
                    placeholder: '年'

                });

                t.community_hotline_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-hotline-input',
                    text: '物业电话:',
                    type: "tel",
                    readonly: t.config.readonly
                });
                $('<div class="gap basic-gap owner-gap"></div>').appendTo($(form_id));
                t.community_base_info_input = new TipsBar({
                    el: $(form_id)
                }, {
                    fieldName: 'community-base-info-tipsbar',
                    text: '管理信息',
                });
                /*t.ownerXingMing_input = new InputBox({
                     el: $(form_id)
                 }, {
                     fieldName: 'ownerXingMing-input',
                     text: '负责人',
                     readonly: t.config.readonly

                 });*/
                t.creator_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'createor-input',
                    text: '创建者:',
                    readonly: t.config.readonly
                });
                t.createDate_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'createDate-input',
                    text: '创建时间:',
                    readonly: t.config.readonly
                });
                t.createAdmin_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'createAdmin-input',
                    text: '管理员:',
                    readonly: t.config.readonly
                });
                t.createNumber_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'createNumber-input',
                    text: '已录入户数:',
                    readonly: t.config.readonly
                });

                if (t.config.report_detail) {
                    //社区报表详情状态,隐藏负责人和创建时间
                    t.$el.find('.ownerXingMing-input').hide();
                    t.$el.find('.createDate-input').hide();
                    t.$el.find('.owner-gap').hide();
                }

                //组装添加小区
                /*t.add_community_button = new ButtonBox({
                    el: $('#community-info-button-container')
                }, {
                    fieldName: 'add-community-button',
                    text: '添加小区'
                }, {
                    Click: function(e) {
                        Cache.set('community-tmp-create-object', false);
                        router.navigate('community_create', {
                            trigger: true
                        });
                    }
                });*/
                // 按钮
                t.edit_community_button = new ButtonBox({
                    el: $(form_id)
                }, {
                    fieldName: 'edit-community-button',
                    text: '楼栋信息'
                }, {
                    Click: function(e) {
                        //取出表单上显示的社区信息
                        var community = Cache.get('community-manager-object');
                        if (community) {
                            //将小区信息和地理位置信息写入到缓存
                            community.communityId = community.id;
                            community.communityName = community.name;
                            Cache.set('member-manager-object', community);
                            //跳转
                            router.navigate('member_list', {
                                trigger: true
                            });
                        } else {
                            //没有社区信息
                            tipsAlert.openAlert({
                                content: '系统异常'
                            });
                        }
                    }
                });
                /*$('<div class="gap basic-gap"></div>').appendTo($(form_id));
                t.delete_community_button = new ButtonBox({
                    el: $(form_id)
                }, {
                    fieldName: 'delete-community-button',
                    text: '删除'
                }, {
                    Click: function(e) {
                        //表单类型
                        tipsAlert.open({
                            cancelText: '取消',
                            confirmText: '确定',
                            content: '你确定要删除此小区吗?',
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
                                CommunityApi.deleteCommunity(t.config.id, success, error);
                                _log(t.config.id);

                            },
                            onCancel: function(e) {
                                tipsAlert.close();
                            }
                        });
                    }
                });*/
                //组装添加住户
                /*t.add_member_button = new ButtonBox({
                    el: $('#community-info-button-container')
                }, {
                    fieldName: 'add-member-button',
                    text: '添加住宅'
                }, {
                    Click: function(e) {
                        //取出表单上显示的社区信息
                        var community = Cache.get('community-manager-object');
                        if (community) {
                            //将小区信息和地理位置信息写入到缓存
                            community.communityId = community.id;
                            community.communityName = community.name;
                            Cache.set('member-manager-object', community);
                            // {
                            //     provinceCode: community.provinceCode,
                            //     cityCode: community.cityCode,
                            //     areaCode: community.areaCode,
                            //     province: community.province,
                            //     city: community.city,
                            //     area: community.area,
                            //     communityId: community.id,
                            //     communityName: community.name
                            // });
                            //跳转
                            router.navigate('member_create_by_commonity', {
                                trigger: true
                            });
                        } else {
                            //没有社区信息
                            tipsAlert.openAlert({
                                content: '系统异常'
                            });
                        }
                    }
                });*/


                if (t.config && t.config.action && t.config.action == 'update') {
                    //填充表单
                    var community = Cache.get('community-manager-object');
                    if (community) {
                        console.log(community);

                    } else {
                        tipsAlert.openAlert({
                            content: '系统异常'
                        });
                    }
                }


            },
            loadData: function() {
                var t = this;
                if (t.config && t.config.id) {
                    // alert(t.config.id);
                    tipsAlert.openLoading({
                        content: '加载中...'
                    });
                    CommunityApi.getCommunityById(t.config.id, function(data) {
                        tipsAlert.close();
                        //返回数据
                        if (data && data.community) {
                            //放入缓存
                            Cache.set('community-manager-object', data.community);
                            //跳转到更新界面
                            // router.navigate('community_update', {
                            //     trigger: true
                            // });
                            var community = data.community;
                            //_log(community);
                            t.header_view.setText(community.name);
                            //console.log(community.createXingMing);
                            /*t.location_view.setValue({
                                roomMount: community.roomMount,
                                alreadyInputAmount: community.alreadyInputAmount,
                                address: 
                                    community.province + ' ' + 
                                    community.city + ' ' +
                                    community.area + ' ' + 
                                    community.address,
                                createAuth: community.createXingMing,
                                createDate: community.createDate.split(' ')[0],
                                admin: community.ownerXingMing
                            });
                            if (community.createXingMing == null) {
                               $('.create-auth').find('span').html('');
                               //t.location_view.setValue({createAuth: null});
                            } */

                            $('.auth-info').show();
                            //t.location_read_input.setValue(community.province + ' ' + community.city + ' ' + community.area);

                            t.community_address_input.setValue(community.address);
                            //总面积
                            t.community_area_covered_input.setValue(community.areaMonut + '㎡');
                            //总户数
                            // t.community_room_amount_input.setValue(community.roomMount + '户');
                            if (community.buildingAmount) {
                                t.community_building_amount_input.setValue(community.buildingAmount + '幢');
                            } else {
                                t.community_building_amount_input.setValue('暂无');
                            }

                            //总入住户数
                            t.community_occupancy_rate_input.setValue(community.occupanyRate + '%');
                            if (community.priceSection) {
                                t.community_price_section_input.setValue(community.priceSection + '元/㎡');
                            } else {
                                t.community_price_section_input.setValue('暂无');
                            }

                            if (community.deliveryTime) {
                                t.community_building_age_input.setValue(community.deliveryTime + '年');
                            } else {
                                t.community_building_age_input.setValue('暂无');
                            }
                            t.community_developer_input.setValue(community.developers);
                            t.community_hotline_input.setValue(community.hotline);

                            t.creator_input.setValue(community.createXingMing);
                            t.createDate_input.setValue(community.createDate);
                            t.createAdmin_input.setValue(community.ownerXingMing);
                            if (community.alreadyInputAmount) {
                                t.createNumber_input.setValue(community.alreadyInputAmount);
                            } else {
                                t.createNumber_input.setValue(0);
                            }
                            /*if (community.createXingMing == null) {
                               $('.create-auth').find('span').html('');
                               //t.location_view.setValue({createAuth: null});
                            }*/
                            //t.ownerXingMing_input.setValue(community.ownerXingMing);
                            /*var createDate = community.createDate;
                            if (createDate && createDate.length > 9) {
                                createDate = createDate.substring(0, 10);
                            }
                            t.createDate_input.setValue(createDate);*/

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
            }
        });
        return LayoutView;
    });