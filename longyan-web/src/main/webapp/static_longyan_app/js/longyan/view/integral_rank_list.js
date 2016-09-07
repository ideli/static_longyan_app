/**
 * 首页 -> 龙眼积分排行榜
 **/
define('js/longyan/view/integral_rank_list', [
        'text!js/longyan/template/integral_rank_list.tpl',
        'text!js/longyan/template/integral_rank_list_item.tpl',
        'text!js/longyan/template/integral_rank_list_nodata.tpl',
        'text!js/longyan/template/no_network.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/list-box',
        'js/api/score',
        'js/util/hybrid'
    ],
    function(IntegralRankListTpl, IntegralRankListItemTpl,CommonTipTpl, NoNetworkTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, ScoreApi, hybrid) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#integral-list-view';
        var form_id = '#integral-list-form';
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
                t.$el.html(tpl(IntegralRankListTpl, {}));

                var right_button_text = '历史冠军';
                var right_button_action = function(e) {
                    e.preventDefault();
                    router.navigate('integral_rank_list_history_champion', {
                        trigger: true
                    });
                };

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '龙榜',
                    goBackUrl: function() {
                        hybrid.backToHybrid("HomePage","direct");
                    }
                });

                if (right_button_text && right_button_text.length > 0) {
                    t.$el.find('#header-container').find('.right-box').html(right_button_text);
                    t.$el.find('.right-box').on('click', right_button_action);
                }

                t.list_box = new ListBox({
                    el: $('#integral-list-box')
                }, {
                    scroll: false, //不支持下拉刷新
                    hidenslideBar:true  //是否关闭左滑功能
                }, {
                    loadData: function(page, handler) {
                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        //龙榜列表
                        ScoreApi.getScoreRank(
                            function(data) {
                                tipsAlert.close();
                                if (data && data.rank) {
                                    var totalRecords = data.rank.length;
                                    if (!totalRecords) {
                                        $('#integral-list-box #scroller').hide();
                                        $('#integral-list-box').after(tpl(CommonTipTpl, {}));
                                        $("#integral-list-bottom").hide();
                                    } else {
                                        $('.error-view').remove();
                                        $('#integral-list-box #scroller').show();
                                        $('integral-list-bottom').show();
                                    }
                                    var currentRecords = data.rank;
                                    if(data.rank.length>0){
                                        if (handler) {
                                            handler(currentRecords, 1, 1);
                                        }
                                    }
                                    //加载个人的排名情况
                                    var item = tpl(IntegralRankListItemTpl, {
                                        data: data.currentUser
                                    });
                                    t.$el.find('#integral-list-top').append(item);
                                }
                            },
                            function(code, msg) {
                                tipsAlert.close();
                                $("#integral-list-bottom").hide();
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
                        var item = tpl(IntegralRankListItemTpl, {
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