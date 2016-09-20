/**
 *message list view
 *消息列表
 **/
define('js/longyan/view/message_list', [
        'text!js/longyan/template/message_list.tpl',
        'text!js/longyan/template/message_list_item.tpl',
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
    function(ListContailerTpl, MessageListItemTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, ReportApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#message-list-view';
        var form_id = '#message-list-form';
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
                    text: '消息中心'
                });

                var i = 1;
                var mallId = t.config.id;
                t.list_box = new ListBox({
                    el: $('#message-list-box')
                }, {
                    scroll: true //支持下拉刷新
                }, {
                    loadData: function(page, handler) {

                        // tipsAlert.openLoading({
                        //     content: '加载中...'
                        // });
                        // ReportApi.getEmployeeList({
                        //         mallId: mallId,
                        //         page: page
                        //     },
                        //     function(data) {
                        //         tipsAlert.close();
                        //         console.log(data);
                        //         if (data && data.result) {
                        //             var totalPages = data.totalPages;
                        //             var currentPage = data.currentPage;
                        //             var currentRecords = data.result;
                        //             // t.list_box.setCurrentPage(currentPage);
                        //             // t.list_box.setTotalPage(totalPages);
                        //             if (handler) {
                        //                 handler(currentRecords, currentPage, totalPages);
                        //             }
                        //         }
                        //     },
                        //     function(code, msg) {
                        //         tipsAlert.close();
                        //         tipsAlert.openAlert({
                        //             content: msg
                        //         });
                        //     });

                        var currentPage = 1;
                        var totalPages = 2;
                        var currentRecords = [{
                            id: 1,
                            message: '铁牛请假需要审批！',
                            dateStr: '2016.08.31',
                            timeStr: '16:14:22',
                            isRead: true
                        }, {
                            id: 2,
                            message: '测试消息2',
                            dateStr: '2016.08.31',
                            timeStr: '16:14:22',
                            isRead: true
                        }, {
                            id: 3,
                            message: '测试消息3',
                            dateStr: '2016.08.31',
                            timeStr: '16:14:22',
                            isRead: true
                        }, {
                            id: 4,
                            message: '测试消息4',
                            dateStr: '2016.08.31',
                            timeStr: '16:14:22',
                            isRead: false
                        }, {
                            id: 5,
                            message: '测试消息5',
                            dateStr: '2016.08.31',
                            timeStr: '16:14:22',
                            isRead: false
                        }, {
                            id: 6,
                            message: '测试消息6',
                            dateStr: '2016.08.31',
                            timeStr: '16:14:22',
                            isRead: false
                        }, {
                            id: 7,
                            message: '测试消息4',
                            dateStr: '2016.08.31',
                            timeStr: '16:14:22',
                            isRead: false
                        }, {
                            id: 8,
                            message: '测试消息5',
                            dateStr: '2016.08.31',
                            timeStr: '16:14:22',
                            isRead: false
                        }, {
                            id: 9,
                            message: '测试消息6',
                            dateStr: '2016.08.31',
                            timeStr: '16:14:22',
                            isRead: false
                        }, {
                            id: 10,
                            message: '测试消息4',
                            dateStr: '2016.08.31',
                            timeStr: '16:14:22',
                            isRead: false
                        }, {
                            id: 11,
                            message: '测试消息5',
                            dateStr: '2016.08.31',
                            timeStr: '16:14:22',
                            isRead: false
                        }, {
                            id: 12,
                            message: '测试消息6',
                            dateStr: '2016.08.31',
                            timeStr: '16:14:22',
                            isRead: false
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

                        return tpl(MessageListItemTpl, {
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
                if ($(e.currentTarget).hasClass('isRead')) {
                    console.log('isRead');
                } else {
                    console.log('not to read');
                }
            },
            destroy: function() {
                $(window).off('scroll');
            }
        });
        return LayoutView;
    });