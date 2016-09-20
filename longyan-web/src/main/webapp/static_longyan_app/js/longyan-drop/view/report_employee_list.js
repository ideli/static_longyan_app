/**
 *report employee list view
 *集团大区报表
 **/
define('js/longyan/view/report_employee_list', [
        'text!js/longyan/template/report_employee_list.tpl',
        'text!js/longyan/template/report_employee_list_item.tpl',
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
    function(ListContailerTpl, EmployeeListItemTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, ReportApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#report-employee-list-view';
        var form_id = '#report-employee-list-form';
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
                    text: '员工'
                });

                var i = 1;
                var mallId = t.config.id;
                t.list_box = new ListBox({
                    el: $('#report-employee-list-box')
                }, {
                    scroll: true //支持下拉刷新
                }, {
                    loadData: function(page, handler) {

                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        ReportApi.getEmployeeList({
                                mallId: mallId,
                                page: page
                            },
                            function(data) {
                                tipsAlert.close();
                                console.log(data);
                                if (data && data.result) {
                                    var totalPages = data.totalPages;
                                    var currentPage = data.currentPage;
                                    var currentRecords = data.result;
                                    // t.list_box.setCurrentPage(currentPage);
                                    // t.list_box.setTotalPage(totalPages);
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
                        var inputMemberRate = 0;
                        if (data && data.inputCommunityRoomAmount) {
                            inputMemberRate = ((data.inputMemberAmount / data.inputCommunityRoomAmount) * 100).toFixed(0);
                            if (inputMemberRate > 100) {
                                inputMemberRate = 100;
                            }
                        }

                        var item = {
                            index: i,
                            name: data['xingMing'],
                            inputMemberAmount: data['inputMemberAmount'],
                            inputCommunityAmount: data['inputCommunityAmount'],
                            employeeCount: data['employeeCount'],
                            inputMemberRate: inputMemberRate,
                            url: '#report_employee_by_id/' + data['id']
                        };
                        i++;

                        return tpl(EmployeeListItemTpl, {
                            data: item
                        });
                    }
                });
            },
            //初始化监听器
            initEvents: function() {
                var t = this;
            },
            destroy: function() {
                $(window).off('scroll');
            }
        });
        return LayoutView;
    });