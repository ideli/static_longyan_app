/**
 *my owner community list view
 *我负责的小区
 **/
define('js/longyan/view/my_owner_community_list', [
        'text!js/longyan/template/my_owner_community_list.tpl',
        'text!js/longyan/template/my_owner_community_list_item.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/list-box',
        'js/api/report'
    ],
    function(ListContailerTpl, OwnerCommunityListItemTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, ReportApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#my-owner-community-list-view';
        var form_id = '#my-owner-community-list-form';
        var LayoutView = Backbone.View.extend({
            events: {
                'click .message-item': '_clickItem'
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
                    text: '我的小区'
                });

                var i = 1;
                var mallId = t.config.id;
                t.list_box = new ListBox({
                    el: $('#my-owner-community-list-box')
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
                            haveComplate: false
                        }, {
                            id: 2,
                            name: '梅川二街坊',
                            mallId: 111,
                            mallName: '真北商场',
                            address: '上海市普陀区真北路233号',
                            distance: '4km',
                            lastDays: 1,
                            haveComplate: false
                        }, {
                            id: 3,
                            name: '梅川三街坊',
                            mallId: 111,
                            mallName: '真北商场',
                            address: '上海市普陀区真北路233号',
                            distance: '4km',
                            lastDays: 0,
                            haveComplate: true
                        }];
                        handler(currentRecords, currentPage, totalPages);
                    },
                    appendItem: function(data) {
                        console.log(data);
                        //住宅录入率
                        // var inputMemberRate = 0;
                        // if (data && data.inputCommunityRoomAmount) {
                        //     inputMemberRate = ((data.inputMemberAmount / data.inputCommunityRoomAmount) * 100).toFixed(0);
                        //     if (inputMemberRate > 100) {
                        //         inputMemberRate = 100;
                        //     }
                        // }



                        // var item = {
                        //     index: i,
                        //     name: data['xingMing'],
                        //     inputMemberAmount: data['inputMemberAmount'],
                        //     inputCommunityAmount: data['inputCommunityAmount'],
                        //     employeeCount: data['employeeCount'],
                        //     inputMemberRate: inputMemberRate,
                        //     url: '#report_employee_by_id/' + data['id']
                        // };
                        // i++;

                        return tpl(OwnerCommunityListItemTpl, {
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