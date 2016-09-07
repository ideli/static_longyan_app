/**
 * 排行榜-员工排行
 * employee_input_rank_list view
 **/
define('js/longyan/view/employee_input_rank_list', [
        'text!js/longyan/template/employee_input_rank_list.tpl',
        'text!js/longyan/template/rank_list_item.tpl',
        'js/element/view/header',
        'js/util/memory_cache',
        'js/element/view/input-box',
        'js/element/view/radio-box',
        'js/element/view/room-box',
        'js/element/view/select-box',
        'js/element/view/think-input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/pie-chart-box',
        'js/element/view/line-chart-box',
        'js/element/view/location-box',
        'js/element/view/employee-input-record-box',
        'js/element/view/list-box',
        'js/api/report'
    ],
    function(SimpleTpl, RankListItemTpl, HeaderView, Cache, InputBox, GenderBox, RoomBox, SelectBox, ThinkBox, ButtonBox, LinkBox, PieBox, LineBox, LocationBox, EmployeeRecordBox, ListBox, ReportApi) {
        var view_id = '#employee-input-rank-list-view';
        var form_id = '#employee-input-rank-list-form';
        var LayoutView = Backbone.View.extend({
            events: {
                // 'tap .reset-link': 'gotoReset'
            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                t.initEvents();
                t.loadData();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(SimpleTpl, {}));


                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '<span class="bar-activate">个人</span>&nbsp;&nbsp;<span><a href="#shopping_mall_input_rank_list">商场</a></span>'
                });


                var employee = Cache.get('user-info');
                console.log(employee);
                var rankItemObject = {
                    inputMemberAmountRank: employee.inputMemberAmountRank - 1,
                    xingMing: employee.xingMing,
                    inputMemberAmount: employee.inputMemberAmount,
                    inputCommunityAmount: employee.inputCommunityAmount
                };
                $(tpl(RankListItemTpl, {
                    data: rankItemObject
                })).appendTo($(form_id));

                $('<div class="gap"></div>').appendTo($(form_id));
                var list_title_bar = $('<div class="list-title-bar"><span class="left">全国排名</span></div>').appendTo($(form_id));
                t.list_container = $('<div></div>').appendTo($(form_id));


            },
            initEvents: function() {
                var t = this;
            },
            loadData: function() {
                var t = this;
                t.rank_list_box = new ListBox({
                    el: t.list_container
                }, {
                    scroll: true //支持下拉刷新
                }, {
                    loadData: function(page, handler) {
                        ReportApi.getEmployeeInputRankReport({
                            page: page
                        }, function(data) {
                            if (data.result.currentRecords) {
                                handler(data.result.currentRecords, data.result.currentPage, data.result.totalPages);
                            }
                        }, function(code, msg) {

                        });
                    },
                    appendItem: function(data) {
                        console.log(data);
                        var item = tpl(RankListItemTpl, {
                            data: data
                        });
                        return item;
                    },
                    clickItem: function(e, item) {
                        console.log(item);
                    }
                });
                $('.rank-list-item-box .right').hide();
            }

        });
        return LayoutView;
    });