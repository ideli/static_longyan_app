/**
 * 帮助文档详情
 **/
define('js/longyan/view/help_detail', [
        'text!js/longyan/template/help_detail.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/menu-box',
        'js/element/view/list-box',
        'js/api/community'
    ],
    function(HelpDetailTpl, Cache, AlertUI, HeaderView, MenuBox, ListBox, CommunityApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#help-detail-view';
        var form_id = '#help-detail-form';
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
                $('body').css('background-color', '#efeff4');

                var content = Cache.get("html");
                var title = Cache.get("title");
                //console.log(content+"----"+title);
                Cache.set("html", "");
                Cache.set('title', "");

                t.$el.html(tpl(HelpDetailTpl, {
                    content: content
                }));

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: title || '帮助中心',
                    goBackUrl: function() {
                        router.navigate('help_list', {
                            trigger: true
                        });
                    }
                });

            },
            //初始化监听器
            initEvents: function() {
                var t = this;

            },
            test: function() {
                console.log(123);
            }
        });
        return LayoutView;
    });