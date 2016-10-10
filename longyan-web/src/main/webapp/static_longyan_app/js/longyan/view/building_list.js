/**
 *building list view
 * 栋列表
 **/
define('js/longyan/view/building_list', [
        'text!js/longyan/template/building_list.tpl',
        'text!js/longyan/template/building_list_item.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/list-box',
        'js/api/community',
        'js/util/hybrid'
    ],
    function(ListContailerTpl, BuildingListItemTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, CommunityApi, HybridApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#building-list-view';
        var form_id = '#building-list-form';

        var LayoutView = Backbone.View.extend({
            events: {
                'click .building-list-item': '_clickItem'
            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.$el.off('click');
                t.render();
                //加载数据       
                t.initEvents();
            },
            render: function() {
                var t = this;
                t.room_amount = 0;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(ListContailerTpl, {}));
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '楼栋信息'
                });

                var i = 1;
                var mallId = t.config.id;

                t.list_box = new ListBox({
                    el: $('#building-list-box')
                }, {
                    scroll: false //支持下拉刷新
                }, {
                    loadData: function(page, handler) {

                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        CommunityApi.getCommunityBuildingList(1, 9999, t.config.id,
                            function(data) {
                                tipsAlert.close();
                                console.log(data);
                                if (data && data.redstarCommunityBuildings) {
                                    var building_data = data.redstarCommunityBuildings;
                                    //加载列表
                                    handler(building_data, 1, 1);
                                    //填写顶部的数字
                                    t.$el.find('#building-info-view .building-amount').html(building_data.length);
                                }
                            },
                            function(code, msg) {
                                tipsAlert.close();
                                tipsAlert.openAlert({
                                    content: msg
                                });
                            });
                    },
                    appendItem: function(data) {
                        var item = {
                            id: data.id,
                            building_no: data.buildingName,
                            floor_amount: data.floorAmount,
                            unit_amount: data.unitAmount,
                            room_amount: data.roomAmount,
                            community_id: data.communityId

                        };
                        if (data.roomAmount) {
                            room_amount = +data.roomAmount;
                        }
                        //填写顶部的数字
                        t.$el.find('#building-info-view .room-amount').html(room_amount);
                        return tpl(BuildingListItemTpl, {
                            data: item
                        });
                    }
                });

                t.$el.find('#building-distance-view').find('.button').on('click', function() {
                    //点击刷新位置
                    t.getCommunityBuildingDistance();
                });

                //判断是否是商场员工

                t.getCommunityBuildingDistance();

            },
            getCommunityBuildingDistance: function() {
                //判断是否在录入范围之内
                var community = Cache.get('community-cache');
                if (community && community.longitude && community.latitude && community.limitDistance && community.limitDistance > 0) {
                    //有经纬度
                    //录入距离大于0
                    //开始计算距离
                    // tipsAlert.openAlert({
                    //     content: '开始计算'
                    // });
                    tipsAlert.openLoading({
                        content: '加载中...'
                    });
                    HybridApi.locationDistance({
                        "latitude": community.latitude,
                        "longitude": community.longitude
                    }, function(resp) {
                        tipsAlert.close();
                        // alert($nvwa.string.objectToJsonString(resp));
                        if (resp && resp.longitude && resp.latitude) {
                            // //经度
                            // var longitude = resp.longitude;
                            // //纬度
                            // var latitude = resp.latitude;
                            //城市
                            // var city = resp.city;
                            var distance = resp.distance || 0;
                            $('#building-distance-view .item-label').html('【当前位置】距离小区' + parseInt(distance) + 'm');
                            // t.$el.find('#building-distance-view').show();
                            if (resp && resp.distance && resp.distance > 0 && resp.distance < community.limitDistance) {
                                //可以录入
                                t.$el.find('#building-info-view').show();

                                t.$el.find('#building-distance-view').addClass('yellow');
                                t.$el.find('#building-distance-view').removeClass('blue');

                            } else {
                                //不给录
                                t.$el.find('#building-info-view').hide();

                                t.$el.find('#building-distance-view').addClass('blue');
                                t.$el.find('#building-distance-view').removeClass('yellow');

                            }

                            // alert($nvwa.string.objectToJsonString(resp));

                        }

                    });
                } else {
                    //不用计算经纬度
                }
            },
            //初始化监听器
            initEvents: function() {
                var t = this;
                t.$el.find('#building-info-view .last').on('click', function() {
                    //缓存community数据
                    window.tmp_building = {
                        community_id: t.config.id
                    };
                    //添加楼栋
                    window.location.href = '#building_create';
                });
            },
            _clickItem: function(e) {
                var t = this;
                var id = $(e.currentTarget).attr('data-value');
                if (id) {
                    var building_no = $(e.currentTarget).find('.item-building-no').attr('data-value');
                    var floor_amount = $(e.currentTarget).find('.item-floor-amount').attr('data-value');
                    var unit_amount = $(e.currentTarget).find('.item-unit-amount').attr('data-value');
                    var room_amount = $(e.currentTarget).find('.item-room-amount').attr('data-value');
                    //缓存
                    window.tmp_building = {
                        building_no: building_no,
                        floor_amount: floor_amount,
                        unit_amount: unit_amount,
                        room_amount: room_amount,
                        community_id: t.config.id
                    };
                    //跳转
                    window.location.href = '#building_detail/' + id;
                } else {
                    tipsAlert.openToast({
                        content: '数据异常'
                    });
                }
            },
            destroy: function() {
                $(window).off('scroll');
            }
        });
        return LayoutView;
    });