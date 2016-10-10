/**
 *my submit list view
 *我的提交列表
 **/
define('js/longyan/view/my_submit_list', [
        'text!js/longyan/template/my_submit_list.tpl',
        'text!js/longyan/template/my_submit_list_item.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/list-box',
        'js/api/audit',
        'js/util/hybrid'
    ],
    function(ListContailerTpl, SubmitListItemTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, AuditApi, HybridApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#my-submit-list-view';
        var form_id = '#my-submit-list-form';
        var LayoutView = Backbone.View.extend({
            events: {
                'click .item-box': '_clickItem',
                'click .my-owner-community-list-item': '_clickToAnother'
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
                    text: '我的提交',
                    goBackUrl: function() {
                        //返回 我的
                        HybridApi.backToHybrid("Mine", "direct");
                    }
                });

                var i = 1;
                var mallId = t.config.id;
                t.list_box = new ListBox({
                    el: $('#my-submit-list-box')
                }, {
                    scroll: false //支持下拉刷新
                }, {
                    loadData: function(page, handler) {
                        tipsAlert.openLoading({
                            content: "加载中"
                        });

                        var _request_type = ['NEEDACTION', 'OK', 'NG'];

                        AuditApi.viewUpdateList({
                                type: _request_type[t.config.status]
                            },
                            function(data) {
                                if (data && data.result) {
                                    tipsAlert.close();
                                    var result = data.result;
                                    var currentPage = result.currentPage;
                                    var totalPages = result.totalPages;
                                    var currentRecords = result.currentRecords;
                                    if (handler) {
                                        handler(currentRecords, currentPage, totalPages);
                                    }
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
                        var arrDay = '';
                        var arrSec = '';
                        if (data.auditShowDate) {
                            arrDay = data.auditShowDate.substring(0, 10);
                            arrSec = data.auditShowDate.substring(11);
                        }
                        var str = '<span>' + data.name + '</span>的小区信息变更申请';
                        if (t.config.status == 0) {
                            var showName = str;
                        } else if (t.config.status == 1) {
                            var showName = str + "通过";
                        } else if (t.config.status == 2) {
                            var showName = str + "未通过";
                        }

                        var item = {
                            id: data.id,
                            name: showName,
                            dateStr: arrDay,
                            timeStr: arrSec,
                            status: 0
                        };

                        return tpl(SubmitListItemTpl, {
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
                var t = this;
                // alert(123);
                var index = $(e.currentTarget).attr('index');
                if (index != t.config.status) {
                    window.location.href = '#my_submit_list/' + index;
                } else {
                    console.log("no action");
                }
            },

            _clickToAnother: function(e) {
                var t = this;
                var id = $(e.currentTarget).attr("data-id") || 0;
                AuditApi.auditDetails(id, function() {
                    tipsAlert.close();
                    $(".button-box.pass-button").attr('data-id', id);
                }, function(code, msg) {
                    tipsAlert.close();
                    tipsAlert.openAlert({
                        content: msg
                    });
                });
                window.location.href = "#my_submit_detail/" + id;
            },

            destroy: function() {
                $(window).off('scroll');
            }
        });
        return LayoutView;
    });