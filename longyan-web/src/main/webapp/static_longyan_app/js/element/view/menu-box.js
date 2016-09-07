/**
 * 头
 **/
define('js/element/view/menu-box', [
        'text!js/element/template/menu-box.tpl'
    ],
    function(MenuBoxTpl) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.config = config || {};
                t.disable = t.config.disable;
                t.events = events;
                t.render();
                t.initEvents();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(MenuBoxTpl, {
                    data: t.config
                }));
            },
            initEvents: function() {
                var t = this;
                if (t.events && t.events.Click) {
                    t.$el.find('.' + t.config.fieldName).off('click');
                    t.$el.find('.' + t.config.fieldName).on('click', function(e) {
                        if ($(e.currentTarget).hasClass(t.config.fieldName) && !t.disable) {
                            t.events.Click(e);
                        }
                    });
                }
            },
            setDisable: function(value) {
                var t = this;
                t.disable = value;
                if (value) {
                    t.$el.find('.' + t.config.fieldName).find('.text').addClass('disable');
                } else {
                    t.$el.find('.' + t.config.fieldName).find('.text').removeClass('disable');
                }
            }
        });
        return LayoutView;
    });