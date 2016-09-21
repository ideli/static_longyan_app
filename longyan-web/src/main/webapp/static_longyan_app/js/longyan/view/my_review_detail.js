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
        'js/api/community'
    ],
    function(CommunityListTpl, Cache, AlertUI, HeaderView, LocationView, PickerBox, InputBox, ThinkInputBox, LocationBox, InputPercentageBox, ButtonBox, LinkBox, TipsBar, CommunityApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#my-review-detail-view';
        var form_id = '#my-review-detail-form';
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
                    text: '建筑类型',
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

                    }
                });



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