/**
 *login view
 *意见反馈
 **/
define('js/longyan/view/feedback_success', [
        'text!js/longyan/template/feedback_success.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/util/hybrid'
    ],
    function(feedbacksuccess, Cache, AlertUI, HeaderView, hybrid) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#reset-view';
        var form_id = '#reset-form';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                //加载数据  
                t.initEvents();

            },
            render: function() {
                var t = this;
                var str = '<div class="feedback_success_box"><ul>' +
                tpl('<li class="telWay"><img src="<#=window.resource.image#>/successinfo-icon.png" /></li>',{})+
                '</ul></div>';
                t.$el.html(tpl(feedbacksuccess, {
                    content: str
                }));


                 //头部
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '意见反馈',
                    goBackUrl: function() {
                       //alert(22222);
                        Cache.set('desc-Info', '');
                        hybrid.backToHybrid("Mine","direct");
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