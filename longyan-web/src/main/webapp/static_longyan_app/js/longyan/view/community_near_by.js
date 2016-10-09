/**
 *community near by list view
 *我附近的小区
 **/
define('js/longyan/view/community_near_by', [
        'text!js/longyan/template/community_near_by.tpl',
        'text!js/longyan/template/community_near_by_item.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/tips-bar',
        'js/element/view/list-box',
        'js/api/community',
        'js/util/hybrid'
    ],
    function(ListContailerTpl, CommunityNearByItemTpl, Cache, AlertUI, HeaderView, TipsBar, ListBox, CommunityApi, HybridApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#community-near-by-list-view';
        var form_id = '#community-near-by-list-form';
        var _requestType = ['allAroundCommunity', 'predistributionCommunity', 'occupyCommunity'];
        var LayoutView = Backbone.View.extend({
            events: {
                "click .item-box": "_clickStatus",
                "click .community-near-by-list-item": "_clickItem"
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
            _get_request_body: function() {
                var t = this;
                var request_body = {
                    queryType: _requestType[t.config.status],
                    longitude: t.config.longitude || 0,
                    latitude: t.config.latitude || 0,
                    cityName: t.config.cityName || '',
                    limitM: 1000
                };
                return request_body;
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(ListContailerTpl, {
                    config: t.config
                }));
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '周边小区'
                });

                var i = 1;
                var mallId = t.config.id;

                t.list_box = new ListBox({
                    el: $('#community-near-by-list-box')
                }, {
                    scroll: false //支持下拉刷新
                }, {
                    loadData: function(page, handler) {
                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        var request_body = t._get_request_body();
                        // alert(123);
                        CommunityApi.aroundList(request_body, function(data) {
                            tipsAlert.close();
                            console.log(data);
                            if (data && data.result) {
                                var currentRecords = data.result;
                                if (handler) {
                                    handler(currentRecords, 1, 1, 0);
                                }
                            }
                        }, function(code, msg) {
                            tipsAlert.close();
                            tipsAlert.openAlert({
                                content: msg
                            });
                        });
                        // var currentPage = 1d;
                        // var totalPages = 1;
                        // var currentRecords = [{
                        //     id: 1,
                        //     name: '梅川一街坊',
                        //     mallId: 111,
                        //     mallName: '真北商场',
                        //     address: '上海市普陀区真北路233号',
                        //     distance: '4km',
                        //     lastDays: 45,
                        //     status: 1
                        // }, {
                        //     id: 2,
                        //     name: '梅川二街坊',
                        //     mallId: 111,
                        //     mallName: '真北商场',
                        //     address: '上海市普陀区真北路233号',
                        //     distance: '200m',
                        //     lastDays: 1,
                        //     status: 2
                        // }, {
                        //     id: 3,
                        //     name: '梅川三街坊',
                        //     mallId: 111,
                        //     mallName: '真北商场',
                        //     address: '上海市普陀区真北路233号',
                        //     distance: '4km',
                        //     lastDays: 0,
                        //     status: 0
                        // }];
                        // handler(currentRecords, currentPage, totalPages);
                    },
                    appendItem: function(data) {
                        console.log(data);
                        var _data_status = data.status ? data.status : 0;
                        var _distance = '0m';
                        if (data.distance && data.distance >= 1000) {
                            _distance = data.distance / 1000;
                            _distance = _distance.toFixed(1) + 'km';
                        } else if (data.distance && data.distance < 1000) {
                            _distance = data.distance + 'm';
                        }
                        var item = {
                            id: data.id,
                            name: data.name,
                            mallName: data.ownerMallName,
                            address: data.address,
                            distance: _distance,
                            lastDays: 0,
                            status: _data_status,
                            city: data.city,
                            address: data.address,

                            longitude: data.longitude,
                            latitude: data.latitude,

                            ownerId: data.ownerId,
                            ownerXingMing: data.ownerXingMing,

                            ownerMallId: data.ownerMallId,
                            ownerMallName: data.ownerMallName,

                        };
                        return tpl(CommunityNearByItemTpl, {
                            data: item
                        });
                    }
                });
            },
            //初始化监听器
            initEvents: function() {
                var t = this;
                window._getNearByTabIndex = function() {
                    return t.config.status;
                };

            },
            _clickItem: function(e) {
                var t = this;
                // alert(1);
                var community_id = $(e.currentTarget).attr('data-value') || 0;
                var community_status = $(e.currentTarget).attr('data-status') || 0;
                var city = $(e.currentTarget).attr('city');
                var community_name = $(e.currentTarget).find('item-name').html();
                var address = $(e.currentTarget).attr('address');
                var longitude = $(e.currentTarget).attr('longitude');
                var latitude = $(e.currentTarget).attr('latitude');

                var ownerId = $(e.currentTarget).attr('ownerId');
                var ownerXingMing = $(e.currentTarget).attr('ownerXingMing');

                var ownerMallId = $(e.currentTarget).attr('ownerMallId');
                var ownerMallName = $(e.currentTarget).attr('ownerMallName');
                // alert(2);
                // if (community_id > 0 && community_status == 0) {
                //     //跳转到小区首页
                //     window.location.href = "#community_home/" + community_id;
                // } else {
                //     //跳转到抢小区和认领小区的地图界面
                //     //跳转到native
                // }

                // alert('select_id->' + community_id);

                var community_object = {
                    "gotoPage": "community_update", //如果updateType=0，这个值填 community_home
                    "city": city,
                    "name": community_name,
                    "address": address,
                    "source": "near_by_community_map",
                    "longitude": longitude,
                    "latitude": latitude,
                    "community_id": community_id,
                    "ownerId": ownerId,
                    "ownerXingMing": ownerXingMing,
                    "ownerMallId": ownerMallId,
                    "ownerMallName": ownerMallName,
                    "updateType": community_status, // 0=正常更新 1=领小区 2=抢小区
                    "action": "update"
                };
                // alert(3);
                if (community_status == '0') {
                    community_object['gotoPage'] = 'community_home';
                }
                // alert(4);
                var community_str = encodeURIComponent($nvwa.string.objectToJsonString(community_object));
                // alert(community_str);
                //调用native接口
                // alert(5);
                HybridApi.goToHybridPage('community_near_by_detail', {
                    community_data: community_str
                });

            },
            //点击状态栏
            _clickStatus: function(e) {
                var t = this;
                var index = $(e.currentTarget).attr('index');
                if (index != t.config.status) {
                    // window.location.href = '#community_near_by/' + index;
                    var load_object = t._get_request_body();
                    load_object['status'] = index;
                    window.router.changePage('community_near_by', load_object);
                } else {
                    console.log('no action');
                }
            },
            destroy: function() {
                $(window).off('scroll');
            }
        });
        return LayoutView;
    });