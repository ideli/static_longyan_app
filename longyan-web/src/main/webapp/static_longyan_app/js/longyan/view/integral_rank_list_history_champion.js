/**
 * 首页 -> 龙榜 -> 历史列表
 **/
define('js/longyan/view/integral_rank_list_history_champion', [
        'text!js/longyan/template/integral_rank_list_history_champion.tpl',
        'text!js/longyan/template/integral_rank_list_history_champion_item.tpl',
        'text!js/longyan/template/integral_rank_list_history_nodata.tpl',
        'text!js/longyan/template/no_network.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/list-box',
        'js/api/score'
    ],
    function(IntegralRankListHistoryChampionTpl, IntegralRankListHistoryChampionItemTpl, NoDataTpl, NoNetworkTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, ScoreApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#integralchampion-list-view';
        var form_id = '#integralchampion-list-form';
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
                t.$el.html(tpl(IntegralRankListHistoryChampionTpl, {}));

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '历史冠军',
                    goBackUrl: function() {
                        Backbone.history.history.back();
                    }
                });
                t.list_box = new ListBox({
                    el: $('#integralchampion-list-box')
                }, {
                    scroll: false,//支持下拉刷新
                    hidenslideBar:true  //是否关闭左滑功能
                }, {
                    loadData: function(page, handler) {
                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        ScoreApi.getScoreHistoryRank(
                            function(data) {
                                tipsAlert.close();
                                if (data && data.data) {
                                    var totalRecords = data.data.length;
                                    if (!totalRecords) {
                                        $('#scroller').hide();
                                        $('#scroller').after(tpl(NoDataTpl, {}));
                                    } else {
                                        $('.error-view').remove();
                                        $('#scroller').show();
                                    }
                                    var currentRecords = data.data;
                                    if (handler) {
                                        handler(currentRecords, 1, 1);
                                    }

                                }
                            },
                                function(code, msg) {
                                    tipsAlert.close();
                                    if (code && code == 408) {
                                        //请求超时
                                        $('#scroller').hide();
                                        var tmp = $('.error-view');
                                        if (tmp && tmp.length > 0) {

                                        } else {
                                            $('#scroller').after(tpl(NoNetworkTpl, {}));
                                            t.$el.find('.error-no-network').off('click');
                                            t.$el.find('.error-no-network').on('click', function() {
                                                //重新刷新 reload
                                                t.list_box.loadData();
                                            });
                                        }
                                        return;
                                    }
                                    tipsAlert.openAlert({
                                        content: msg
                                    });
                                });
                    },
                    appendItem: function(data) {
                        var _data = data;
                        var item = tpl(IntegralRankListHistoryChampionItemTpl, {
                            data: _data
                        });
                        return item;
                    }
                });
            },
            //初始化监听器
            initEvents: function() {
                var t = this;
            },
            destroy: function() {
                $(window).off('scroll');
                this.list_box.removeEvent();
            }
        });
        return LayoutView;
    });