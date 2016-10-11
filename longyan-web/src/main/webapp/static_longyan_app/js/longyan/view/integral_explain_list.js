/**
 * 首页 -> 积分明细
 **/
define('js/longyan/view/integral_explain_list', [
        'text!js/longyan/template/integral_explain_list.tpl',
        'text!js/longyan/template/integral_explain_list_item.tpl',
        'text!js/longyan/template/no_score.tpl',
        'text!js/longyan/template/no_network.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/list-box',
        'js/api/integral',
        'js/util/hybrid'
    ],
    function(IntegralExplainListTpl, IntegralExplainListItemTpl, NoScoreTpl, NoNetworkTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, IntegralApi, hybrid) {
        var tipsAlert = tipsAlert || new AlertUI();

        var view_id = '#integral-explain-list-view';
        var form_id = '#integral-explain-list-form';
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
            render: function(){
                var t = this;
                $('body').css('background-color', '#efeff4');

                t.$el.html(tpl(IntegralExplainListTpl, {}));

                IntegralApi.getIntegralList(1, 20,function(data){
                    if(data.score_month)
                        t.$el.find('.number-month-integral').text(data.score_month);
                    else 
                        t.$el.find('.number-month-integral').text(0);
                    if(data.score)
                        t.$el.find('.get-all-integral').text("总积分：" + data.score);
                    else
                        t.$el.find('.get-all-integral').text("总积分：" + 0);                       

                    if(data.page_data.currentRecords.length){
                        
                        t.list_box = new ListBox({
                            el: $('#integral-explain-list-box')
                        }, {
                            scroll: true //支持下拉刷新
                        }, {
                            loadData: function(page, handler) {
                                var page = page || 1;
                                var pageSize = 20;
                                var provinceCode = 0;
                                var cityCode = 0;
                                var name = '';
                                tipsAlert.openLoading({
                                    content: '加载中...'
                                });
                                IntegralApi.getIntegralList(page, pageSize,
                                        function(data) {           
                                            tipsAlert.close();
                                            if (data) {
                                                var totalRecords = data.page_data.totalRecords;//!totalRecords
                                                if (!totalRecords){
                                                    $('#scroller').hide();
                                                    $(".top-bar").hide();
                                                    $("#integralDetail-title").hide();
                                                    $('#scroller').after(tpl(NoScoreTpl, {}));
                                                } else {
                                                    $('.error-view').remove();
                                                    $('#scroller').show();
                                                }
                                                var totalPages = data.page_data.totalPages;
                                                var currentPage = data.page_data.currentPage;
                                                var currentRecords = data.page_data.currentRecords;

                                                if (handler) {
                                                    handler(currentRecords, currentPage, totalPages,0);
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
                                var item = tpl(IntegralExplainListItemTpl, {
                                    data: _data
                                });
                                return item;
                            }
                        });
                    }else{
                        $("#integralDetail-title").hide();
                        $("#integral-explain-list-box").append(NoScoreTpl);
                    }
                });
                

                var right_button_text = '积分说明';
                var right_button_action = function(e) {
                    e.preventDefault();
                    router.navigate('integral_explain_detail', {
                        trigger: true
                    });
                }; 



                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '我的积分',
                    goBackUrl: function() {
                        if (t.config.employee_id) {
                            //员工小区列表
                            hybrid.backToHybrid("Mine","direct");
                        } else {
                            //我的小区列表
                            hybrid.backToHybrid("Mine","direct");
                        }
                    }
                });

               if (right_button_text && right_button_text.length > 0) {
                    t.$el.find('#header-container').find('.right-box').html(right_button_text);
                    t.$el.find('.right-box').on('click', right_button_action);
                }

            },
            //初始化监听器
            initEvents: function() {
                var t = this;
            },
            destroy: function() {
                $(window).off('scroll');
                if(this.list_box)
                    this.list_box.removeEvent();
            }
        });
        return LayoutView;
    });