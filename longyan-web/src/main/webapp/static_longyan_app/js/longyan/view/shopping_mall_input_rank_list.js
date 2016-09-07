/**
 * 排行榜-商场排行
 * shopping_mall_input_rank_list view
 * 员工录入详情
 **/
define('js/longyan/view/shopping_mall_input_rank_list', [
        'text!js/longyan/template/shopping_mall_input_rank_list.tpl',
        'text!js/longyan/template/rank_list_item.tpl',
        'js/element/view/header',
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
    function(SimpleTpl, RankListItemTpl, HeaderView, InputBox, GenderBox, RoomBox, SelectBox, ThinkBox, ButtonBox, LinkBox, PieBox, LineBox, LocationBox, EmployeeRecordBox, ListBox, ReportApi) {
        var view_id = '#shopping-mall-input-rank-list-view';
        var form_id = '#shopping-mall-input-rank-list-form';
        var LayoutView = Backbone.View.extend({
            events: {
                // 'tap .reset-link': 'gotoReset'
            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(SimpleTpl, {}));

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '<span><a href="#employee_input_rank_list">个人</a></span>&nbsp;&nbsp;<span  class="bar-activate">商场</span>'
                });

                $('<div class="gap"></div>').appendTo($(form_id));
                var list_title_bar = $('<div class="list-title-bar"><span class="left">全国排名</span></div>').appendTo($(form_id));
                var list_container = $('<div></div>').appendTo($(form_id));

                t.rank_list_box = new ListBox({
                    el: list_container
                }, {
                    scroll: true //支持下拉刷新
                }, {
                    loadData: function(page, handler) {
                        ReportApi.getMallInputRankReport({
                            page: page,
                            pageSize: 20
                        }, function(data) {
                            if (data.result.currentRecords) {
                                handler(data.result.currentRecords, data.result.currentPage, data.result.totalPages);
                            }
                        }, function(code, msg) {

                        });
                    },
                    appendItem: function(data) {
                        console.log(data);
                        var itemData = {
                            inputMemberAmountRank: data.inputMemberAmountRank - 1,
                            xingMing: data.name,
                            inputMemberAmount: data.inputMemberAmount,
                            inputCommunityAmount: data.inputCommunityAmount
                        }
                        var item = tpl(RankListItemTpl, {
                            data: itemData
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