/**
 * 审核详情
 **/
define('js/longyan/view/my_review_detail', [
        'text!js/longyan/template/my_review_detail.tpl',
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
        'js/api/audit'
    ],
    function(CommunityListTpl, Cache, AlertUI, HeaderView, LocationView, PickerBox, InputBox, ThinkInputBox, LocationBox, InputPercentageBox, ButtonBox, LinkBox, TipsBar, AuditApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#my-review-detail-view';
        var form_id = '#my-review-detail-form';
        var LayoutView = Backbone.View.extend({
            events: {},
            // id
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
                t.$el.find('#my-review-detail-view').addClass('my-review-detail-detail-view');

                //==========heander view==========
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '审核详情'
                });

                t.community_city_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-city-input',
                    text: '城市',
                    readonly: true,
                });

                t.community_name_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-name-input',
                    text: '小区名称',
                    readonly: true,
                });

                t.community_short_name_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-short-name-input',
                    text: '小区别名',
                    readonly: true,
                });

                t.community_address_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-address-input',
                    text: '详细地址',
                    readonly: true,
                });

                //间隔
                $('<div class="gap basic-gap owner-gap"></div>').appendTo($(form_id));

                t.community_room_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-room-amount-input',
                    text: '总户数',
                    label_right: '户',
                    readonly: true,
                });

                t.community_building_amount_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-building-amount-input',
                    text: '楼栋数',
                    label_right: '栋',
                    readonly: true,


                });

                t.community_occupancy_rate_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-occupancy-rate-input',
                    text: '入住率',
                    label_right: '%',
                    readonly: true,


                });

                t.community_price_section_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-price-section-input',
                    text: '房屋均价',
                    label_right: '元/m²',
                    readonly: true,

                });


                t.community_construction_types_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-construction-types-input',
                    text: '物业类型',
                    readonly: true


                });

                t.community_renovations_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-renovations-input',
                    text: '交房装修',
                    readonly: true
                });


                t.community_delivery_time_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-delivery-time-input',
                    text: '交房时间',
                    readonly: true,
                });

                $('<div class="gap basic-gap owner-gap"></div>').appendTo($(form_id));
                t.community_developer_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-developer-input',
                    text: '开发商',
                    readonly: true
                });

                t.community_property_name_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-property-name-input',
                    text: '物业公司',
                    readonly: true
                });

                t.community_property_name_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-property-name-input',
                    text: '物业公司',
                    readonly: true
                });

                t.community_hotline_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-hotline-input',
                    text: '物业电话',
                    type: "tel",
                    readonly: true
                });

                t.community_owner_mall_name_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-owner-mall-name-input',
                    text: '所属商场',
                    readonly: true
                });

                $('<div class="gap basic-gap owner-gap button-container"></div>').appendTo($(form_id));

                //审核通过按钮
                t.pass_button = new ButtonBox({
                    el: $('.button-container')
                }, {
                    fieldName: 'pass-button',
                    text: '通过'
                }, {
                    Click: function(e) {
                        var id = $(".button-box.pass-button").attr('data-id');
                        AuditApi.update(id, "Ok", function(data) {
                            $('.basic-gap.owner-gap').hide();
                            console.log("成功");
                        }, function(code, msg) {
                            tipsAlert.close();
                            tipsAlert.openAlert({
                                content: msg
                            });
                        });
                    }
                });
                //审核驳回按钮
                t.rollback_button = new ButtonBox({
                    el: $('.button-container')
                }, {
                    fieldName: 'rollback-button',
                    text: '不通过'
                }, {
                    Click: function(e) {
                        var id = $(".button-box.pass-button").attr('data-id');
                        AuditApi.update(id, "nG", function(data) {
                            $('.basic-gap.owner-gap').hide();
                            console.log("失败");
                        }, function(code, msg) {
                            tipsAlert.close();
                            tipsAlert.openAlert({
                                content: msg
                            });
                        });
                    }
                });

                // if (t.config && t.config.action && t.config.action == 'update') {
                //     //填充表单
                //     var community = Cache.get('community-manager-object');
                //     if (community) {
                //         console.log(community);
                //     } else {
                //         tipsAlert.openAlert({
                //             content: '系统异常'
                //         });
                //     }
                // }
            },
            loadData: function() {
                var t = this;
                if (t.config && t.config.id) {
                    // alert(t.config.id);
                    tipsAlert.openLoading({
                        content: '加载中...'
                    });

                    AuditApi.changeId(t.config.id, function(data) {
                        tipsAlert.close();
                        //返回数据
                        if (data && data.community) {
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
                }
            },
            //设置表单
            setFormValue: function(data) {
                var t = this;
                if (data) {
                    if (data.editColumnName) {
                        var arr = data.editColumnName.split(",");
                        if (arr && arr.length > 0) {
                            $.each(arr, function(index, item) {
                                if (item) {
                                    switch (item) {
                                        case 'city':
                                            t.$el.find('.community-city-input').addClass('colorChange');
                                            break;
                                        case 'name':
                                            t.$el.find('.community-name-input').addClass('colorChange');
                                            break;
                                        case 'shortName':
                                            t.$el.find('.community-short-name-input').addClass('colorChange');
                                            break;
                                        case 'address':
                                            t.$el.find('.community-address-input').addClass('colorChange');
                                            break;
                                        case 'roomMount':
                                            t.$el.find('.community-room-amount-input').addClass('colorChange');
                                            break;
                                        case 'buildingAmount':
                                            t.$el.find('.community-building-amount-input').addClass('colorChange');
                                            break;
                                        case 'inputRate':
                                            t.$el.find('.community-occupancy-rate-input').addClass('colorChange');
                                            break;
                                        case 'priceSection':
                                            t.$el.find('.community-price-section-input').addClass('colorChange');
                                            break;
                                        case 'constructionTypes':
                                            t.$el.find('.community-construction-types-input').addClass('colorChange');
                                            break;
                                        case 'renovations':
                                            t.$el.find('.community-developer-input').addClass('colorChange');
                                            break;
                                        case 'deliveryTime':
                                            t.$el.find('.community-renovations-input').addClass('colorChange');
                                            break;
                                        case 'developers':
                                            t.$el.find('.community-developer-input').addClass('colorChange');
                                            break;
                                        case 'propertyName':
                                            t.$el.find('.community-property-name-input').addClass('colorChange');
                                            break;
                                        case 'hotline':
                                            t.$el.find('.community-hotline-input').addClass('colorChange');
                                            break;
                                        case 'ownerMallName':
                                            t.$el.find('.community-owner-mall-name-input').addClass('colorChange');
                                            break;
                                    }
                                }
                            });
                            $(".button-box.pass-button").attr('data-id', data.id);
                            if (t.config.status != 0) {
                                $('.basic-gap.owner-gap').hide();
                            }
                        }

                    }

                    //城市名称
                    t.community_city_input.setValue(data.city);
                    //小区名称
                    t.community_name_input.setValue(data.name);
                    //小区别名
                    t.community_short_name_input.setValue(data.shortName);
                    //详细地址
                    t.community_address_input.setValue(data.address);
                    //总户数
                    t.community_room_amount_input.setValue(data.roomMount);
                    //楼栋数
                    t.community_building_amount_input.setValue(data.buildingAmount);
                    //录入率
                    t.community_occupancy_rate_input.setValue(data.inputRate);
                    //房屋均价
                    t.community_price_section_input.setValue(data.priceSection);
                    //物业类型
                    var type_Con = null;
                    if(data.constructionTypes){
                        var dataCon = data.constructionTypes.split(",");
                        var type=[];
                        for(var i=0; i<dataCon.length; i++){
                            if(dataCon[i]==0){
                                type.push("普通住宅");
                            }else if(dataCon[i]==1){
                                type.push("别墅");
                            }else if(dataCon[i]==2){
                                type.push("商住");
                            }
                        }
                        var type_Con = type.join(",");
                    }else{
                        type_Con = data.constructionTypes;
                    }
                    t.community_construction_types_input.setValue(data.type_Con);
                    //交房装修
                    var renovations = null;
                    if(data.renovations){
                        var type_Con = [];
                        var dataCon = data.renovations.split(",");
                        for(var i=0; i<dataCon.length; i++){
                            if(dataCon[i]==0){
                                type_Con.push("毛坯");
                            }else if(dataCon[i]==1){
                                type_Con.push("精装");
                            }
                        }
                        var renovations = type_Con.join(",");
                    }else{
                        renovations = data.renovations;
                    }
                    t.community_renovations_input.setValue(renovations);
                    //交房时间
                    var time = null;
                    if (data.deliveryTime) {
                        var time = data.deliveryTime + "年";
                    }
                    t.community_delivery_time_input.setValue(time);
                    //开发商
                    t.community_developer_input.setValue(data.developers);
                    //物业公司
                    t.community_property_name_input.setValue(data.propertyName);
                    //物业电话
                    t.community_hotline_input.setValue(data.hotline);
                    //所属商场
                    t.community_owner_mall_name_input.setValue(data.ownerMallName);
                }
            }
        });
        return LayoutView;
    });