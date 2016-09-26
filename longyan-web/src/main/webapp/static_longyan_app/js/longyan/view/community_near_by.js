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
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/list-box'
    ],
    function(ListContailerTpl, CommunityNearByItemTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#community-near-by-list-view';
        var form_id = '#community-near-by-list-form';
        var LayoutView = Backbone.View.extend({
            events: {

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
                t.$el.html(tpl(ListContailerTpl, {}));
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

                        var currentPage = 1;
                        var totalPages = 1;
                        var currentRecords = [{
                            id: 1,
                            name: '梅川一街坊',
                            mallId: 111,
                            mallName: '真北商场',
                            address: '上海市普陀区真北路233号',
                            distance: '4km',
                            lastDays: 45,
                            status: 1
                        }, {
                            id: 2,
                            name: '梅川二街坊',
                            mallId: 111,
                            mallName: '真北商场',
                            address: '上海市普陀区真北路233号',
                            distance: '200m',
                            lastDays: 1,
                            status: 2
                        }, {
                            id: 3,
                            name: '梅川三街坊',
                            mallId: 111,
                            mallName: '真北商场',
                            address: '上海市普陀区真北路233号',
                            distance: '4km',
                            lastDays: 0,
                            status: 0
                        }];
                        handler(currentRecords, currentPage, totalPages);
                    },
                    appendItem: function(data) {
                        console.log(data);
                        return tpl(CommunityNearByItemTpl, {
                            data: data
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
            destroy: function() {
                $(window).off('scroll');
            }
        });
        return LayoutView;
    });