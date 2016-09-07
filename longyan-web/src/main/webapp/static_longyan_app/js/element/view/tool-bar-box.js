/**
 * 头
 **/
define('js/element/view/tool-bar-box', [
        'text!js/element/template/tool-bar-box.tpl'
    ],
    function(InputTpl, Cache, AlertUI) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.config = config || {};
                t.events = events;
                t.render();
                t.initEvents();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(InputTpl, {
                    data: t.config
                }));
            },
            initEvents: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);

                if (t.events && t.events.Click) {
                    container.find('.bar-box-group-item').off('click');
                    container.find('.bar-box-group-item').on('click', function(e) {
                        var current = $(e.currentTarget);
                        var index = current.attr('num') || 0;
                        t.events.Click(e, index);
                    });
                }

                container.find('.bar-box-group').on('click', '.bar-box-group-item', function(e) {
                    var currentTarget = $(e.currentTarget);
                    container.find('.bar-box-group-item').removeClass('active');
                    currentTarget.addClass('active');
                });
            },
            show: function(tag) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (tag) {
                    container.removeClass('hidden');
                } else {
                    container.addClass('hidden');
                }
            },
            setIndex: function(index) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (t.config && t.config.item && index < t.config.item.length) {
                    container.find('.bar-box-group .bar-box-group-item').removeClass('active');
                    $(container.find('.bar-box-group .bar-box-group-item')[index]).addClass('active');
                }
            }

        });
        return LayoutView;
    });