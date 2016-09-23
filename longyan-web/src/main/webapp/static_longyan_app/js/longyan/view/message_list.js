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
        'js/element/view/list-box',
        'js/api/message'
    ],
    function(ListContailerTpl, MessageListItemTpl, Cache, AlertUI, HeaderView, ListBox, MessageApi) {
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

                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        MessageApi.getMyMessageList({},
                            function(data) {
                                tipsAlert.close();
                                console.log(data);
                                if (data && data.page_data) {
                                    var totalPages = data.page_data.totalPages;
                                    var currentPage = data.page_data.currentPage;
                                    var currentRecords = data.page_data.currentRecords;
                                    // t.list_box.setCurrentPage(currentPage);
                                    // t.list_box.setTotalPage(totalPages);
                                    if (handler) {
                                        handler(currentRecords, currentPage, totalPages, 0);
                                    }
                                }
                            },
                            function(code, msg) {
                                tipsAlert.close();
                                tipsAlert.openAlert({
                                    content: msg
                                });
                            });

                        // var currentPage = 1;
                        // var totalPages = 2;
                        // var currentRecords = [{
                        //     id: 1,
                        //     message: '铁牛请假需要审批！',
                        //     dateStr: '2016.08.31',
                        //     timeStr: '16:14:22',
                        //     isRead: true
                        // }, {
                        //     id: 2,
                        //     message: '测试消息2',
                        //     dateStr: '2016.08.31',
                        //     timeStr: '16:14:22',
                        //     isRead: true
                        // }, {
                        //     id: 3,
                        //     message: '测试消息3',
                        //     dateStr: '2016.08.31',
                        //     timeStr: '16:14:22',
                        //     isRead: true
                        // }, {
                        //     id: 4,
                        //     message: '测试消息4',
                        //     dateStr: '2016.08.31',
                        //     timeStr: '16:14:22',
                        //     isRead: false
                        // }, {
                        //     id: 5,
                        //     message: '测试消息5',
                        //     dateStr: '2016.08.31',
                        //     timeStr: '16:14:22',
                        //     isRead: false
                        // }, {
                        //     id: 6,
                        //     message: '测试消息6',
                        //     dateStr: '2016.08.31',
                        //     timeStr: '16:14:22',
                        //     isRead: false
                        // }, {
                        //     id: 7,
                        //     message: '测试消息4',
                        //     dateStr: '2016.08.31',
                        //     timeStr: '16:14:22',
                        //     isRead: false
                        // }, {
                        //     id: 8,
                        //     message: '测试消息5',
                        //     dateStr: '2016.08.31',
                        //     timeStr: '16:14:22',
                        //     isRead: false
                        // }, {
                        //     id: 9,
                        //     message: '测试消息6',
                        //     dateStr: '2016.08.31',
                        //     timeStr: '16:14:22',
                        //     isRead: false
                        // }, {
                        //     id: 10,
                        //     message: '测试消息4',
                        //     dateStr: '2016.08.31',
                        //     timeStr: '16:14:22',
                        //     isRead: false
                        // }, {
                        //     id: 11,
                        //     message: '测试消息5',
                        //     dateStr: '2016.08.31',
                        //     timeStr: '16:14:22',
                        //     isRead: false
                        // }, {
                        //     id: 12,
                        //     message: '测试消息6',
                        //     dateStr: '2016.08.31',
                        //     timeStr: '16:14:22',
                        //     isRead: false
                        // }];
                        // handler(currentRecords, currentPage, totalPages, 0);
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
                        var readFlg = false;
                        if (!data.readFlg) {
                            readFlg = true;
                        }

                        var item = {
                            id: data.id,
                            message: data.messageTitle,
                            dateStr: data.createDate.substring(0, 10).replace('-', '.').replace('-', '.'),
                            timeStr: data.createDate.substring(10, 20),
                            isRead: readFlg
                        };

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
                if ($(e.currentTarget).hasClass('isRead')) {
                    var message_id = $(e.currentTarget).attr('data-value') || 0;
                    if (message_id < 1) {
                        tipsAlert.openAlert({
                            content: '数据异常'
                        });
                    }
                    console.log('isRead');
                    //调用服务器接口把消息标记成已读
                    tipsAlert.openLoading({
                        content: '加载中...'
                    });
                    MessageApi.readMessage({
                            message_id: message_id
                        },
                        function(data) {
                            tipsAlert.close();
                            console.log(data);
                            // if (data && data.page_data) {
                            //     var totalPages = data.page_data.totalPages;
                            //     var currentPage = data.page_data.currentPage;
                            //     var currentRecords = data.page_data.currentRecords;
                            //     // t.list_box.setCurrentPage(currentPage);
                            //     // t.list_box.setTotalPage(totalPages);
                            //     if (handler) {
                            //         handler(currentRecords, currentPage, totalPages);
                            //     }
                            // }

                            //未读标记取消掉
                            $(e.currentTarget).removeClass('isRead');
                            $(e.currentTarget).find('.message-item-title-readmark').html(' ');
                        },
                        function(code, msg) {
                            tipsAlert.close();
                            tipsAlert.openAlert({
                                content: msg
                            });
                        });

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