/**
 * 头
 **/
define('js/element/view/header', [
        'text!js/element/template/header.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui'
    ],
    function(HeaderTpl, Cache, AlertUI) {
        var tipsAlert = tipsAlert || new AlertUI();

        var LayoutView = Backbone.View.extend({
            events: {
                // 'click .goback-button': 'goBack'
            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.$el.off('click');
                if (t.config.goBackUrl) {
                    t.url = t.config.goBackUrl;
                }

                t.render();
                t.$el.find('.goback-button').on('click', function() {
                    t.goBack();
                });
            },
            render: function() {
                var t = this;
                t.$el.removeClass('hidden');
                t.$el.html(tpl(HeaderTpl, {
                    data: t.config
                }));
            },
            goBack: function() {
                // history.back();
                var t = this;
                if (t.config.goBackUrl) {
                    t.config.goBackUrl();
                } else {
                    Backbone.history.history.back();
                }
            },
            setText: function(text) {
                var t = this;
                t.$el.find('.header-box').find('.title').html(text);
            }
        });
        return LayoutView;
    });