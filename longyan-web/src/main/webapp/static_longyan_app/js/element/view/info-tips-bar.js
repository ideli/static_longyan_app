/**
 * 头
 **/
define('js/element/view/info-tips-bar', [
        'text!js/element/template/info-tips-bar.tpl'
    ],
    function(TipsTpl, Cache, AlertUI) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.config = config || {};
                t.events = events || {};
                t.render();
                t.initEvents();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(TipsTpl, {
                    data: t.config
                }));
            },
            setValue: function(value) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    container.find('.count-span').html(value);
                }
            },
            initEvents: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (t.events && t.events.Click) {
                    container.off('click');
                    container.on('click', function(e) {
                        if ($(e.currentTarget).hasClass(t.config.fieldName) && !t.disable) {
                            t.events.Click(e);
                        }
                    });
                }
            }

        });
        return LayoutView;
    });