/**
 *report mall list view
 *集团大区报表
 **/
define('js/longyan/view/report_mall_list', [
        'text!js/longyan/template/report_mall_list.tpl',
        'text!js/longyan/template/rank_list_item.tpl',
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
    function(ListContailerTpl, MallListItemTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, ReportApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#report-mall-list-view';
        var form_id = '#report-mall-list-form';
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
                    text: '商场'
                });

                var i = 1;
                var organizationId = t.config.id;
                t.list_box = new ListBox({
                    el: $('#report-mall-list-box')
                }, {
                    scroll: false //支持下拉刷新
                }, {
                    loadData: function(page, handler) {
                        var page = 1;
                        var pageSize = 20;
                        var provinceCode = 0;
                        var cityCode = 0;
                        var name = '';
                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        ReportApi.getMallList({
                                organizationId: organizationId
                            },
                            function(data) {
                                tipsAlert.close();
                                console.log(data);
                                if (data && data.result) {
                                    var totalPages = 1;
                                    var currentPage = 1;
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
                        if (data && data.communityMemberAmount) {
                            inputMemberRate = ((data.inputMemberAmount / data.communityMemberAmount) * 100).toFixed(0);
                            if (inputMemberRate > 100) {
                                inputMemberRate = 100;
                            }
                        }

                        var item = {
                            index: i,
                            name: data['name'],
                            inputMemberAmount: data['inputMemberAmount'],
                            inputCommunityAmount: data['inputCommunityAmount'],
                            employeeCount: data['employeeCount'],
                            inputMemberRate: inputMemberRate,
                            url: '#report_shopping_mall_by_id/' + data['id']
                        };
                        i++;

                        return tpl(MallListItemTpl, {
                            data: item
                        });
                    }
                });
            },
            //初始化监听器
            initEvents: function() {
                var t = this;
            }
        });
        return LayoutView;
    });