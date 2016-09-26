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
        'js/api/community'
    ],
    function(ListContailerTpl, CommunityNearByItemTpl, Cache, AlertUI, HeaderView, TipsBar, ListBox, CommunityApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#community-near-by-list-view';
        var form_id = '#community-near-by-list-form';
        var _requestType = ['allAroundCommunity', 'predistributionCommunity', 'occupyCommunity'];
        var LayoutView = Backbone.View.extend({
            events: {
                "click .item-box": "_clickStatus"
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
                        CommunityApi.aroundList({
                            type: _requestType[t.config.status],
                            longitude: 121.447682,
                            latitude: 31.337771,
                            provinceCode: 310000,
                            cityCode: 310100,
                            limitM: 5000
                        }, function(data) {
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
                        var item = {
                            id: data.communityId,
                            name: data.name,
                            mallName: data.ownerMallName,
                            address: data.address,
                            distance: '4km',
                            lastDays: 0,
                            status: 0
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

            },
            _clickItem: function(e) {

            },
            //点击状态栏
            _clickStatus: function(e) {
                var t = this;
                var index = $(e.currentTarget).attr('index');
                if (index != t.config.status) {
                    window.location.href = '#community_near_by/' + index;
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