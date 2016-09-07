/**
 *report area list view
 *集团大区报表
 **/
define('js/longyan/view/report_area_list', [
        'text!js/longyan/template/report_area_list.tpl',
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
    function(ListContailerTpl, AreaListItemTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, ReportApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#report-area-list-view';
        var form_id = '#report-area-list-form';
        var LayoutView = Backbone.View.extend({
            events: {
                'click .sub-area-list-show-button': '_sub_area_click'
            },
            //
            initialize: function(options, config) {
                var t = this;
                t.sub_area_list = [];
                t.sub_area_datamap = {};
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
                    text: '区域'
                });

                var i = 1;
                t.list_box = new ListBox({
                    el: $('#report-area-list-box')
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
                        ReportApi.getAreaList ({},
                            function(data) {
                                tipsAlert.close();
                                if (data && data.result) {
                                    var totalPages = 1;
                                    var currentPage = 1;
                                    var currentRecords = data.result;
                                    // t.list_box.setCurrentPage(currentPage);
                                    // t.list_box.setTotalPage(totalPages);
                                    if (data.sub_area) {
                                        t.sub_area_list = data.sub_area;
                                    }
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
                        //住宅录入率
                        var inputMemberRate = 0;
                        if (data && data.inputCommunityRoomAmount) {
                            inputMemberRate = ((data.inputMemberAmount / data.inputCommunityRoomAmount) * 100).toFixed(0);
                            if (inputMemberRate > 100) {
                                inputMemberRate = 100;
                            }
                        }

                        var _sub_area = [];
                        if (t.sub_area_list && t.sub_area_list.length > 0) {
                            $.each(t.sub_area_list, function(k, sub_area) {
                                if (sub_area && sub_area.parentId && sub_area.parentId > 0 && sub_area.parentId == data.id) {
                                    var _sub_inputMemberRate = 0;
                                    if (sub_area && sub_area.inputCommunityRoomAmount) {
                                        _sub_inputMemberRate = ((sub_area.inputMemberAmount / sub_area.inputCommunityRoomAmount) * 100).toFixed(0);
                                        if (_sub_inputMemberRate > 100) {
                                            _sub_inputMemberRate = 100;
                                        }
                                    }
                                    sub_area['inputMemberRate'] = _sub_inputMemberRate;
                                    _sub_area.push(sub_area);
                                }
                            });
                        }


                        var item = {
                            index: i,
                            id: data['id'],
                            name: data['name'],
                            inputMemberAmount: data['inputMemberAmount'],
                            inputCommunityAmount: data['inputCommunityAmount'],
                            employeeCount: data['employeeCount'],
                            inputMemberRate: inputMemberRate,
                            sub_area: _sub_area,
                            more_action: function() {
                                console.log(1111)
                            },
                            url: '#report_area_by_id/' + data['id']
                        };
                        i++;

                        return tpl(AreaListItemTpl, {
                            data: item
                        });
                    }
                });
            },
            //初始化监听器
            initEvents: function() {
                var t = this;
                // t.$el.find('.sub-area-list-show-button').on('click', function(e) {
                //     console.log(e);
                // });
            },
            _sub_area_click: function(e) {
                // var target = $(e.target);
                var target = $(e.currentTarget);
                if (target.parent().find('.sub-area-list-container').hasClass('hidden')) {
                    target.parent().find('.sub-area-list-container').removeClass('hidden');
                    target.parent().find('.sub-area-list-show-button').html('收起<i class="seemorm-icon position-icon"></i>');
                } else {
                    target.parent().find('.sub-area-list-container').addClass('hidden');
                    target.parent().find('.sub-area-list-show-button').html('查看更多<i class="seemorm-icon"></i>');
                }

            }
        });
        return LayoutView;
    });