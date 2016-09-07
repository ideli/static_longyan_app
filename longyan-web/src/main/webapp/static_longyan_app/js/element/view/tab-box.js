/**
 * tab
 **/
define('js/element/view/tab-box', [
        'text!js/element/template/tab-box.tpl'
    ],
    function(SimpleTpl, Cache, AlertUI) {
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
                t.$el.append(tpl(SimpleTpl, {
                    data: t.config
                }));
                if (t.config && t.config.selected_index && t.config.selected_index > 0) {
                    $(t.$el.find('.' + t.config.fieldName).find('.tab-item-box')[t.config.selected_index]).attr('selected', 'true');
                } else {
                    $(t.$el.find('.' + t.config.fieldName).find('.tab-item-box')[0]).attr('selected', 'true');
                }
                t.selected_index = t.config.selected_index || 0;
            },
            initEvents: function() {
                var t = this;
                t.$el.find('.' + t.config.fieldName).find('.tab-item-box').off('click');
                t.$el.find('.' + t.config.fieldName).find('.tab-item-box').on('click', function(e) {
                    t.$el.find('.' + t.config.fieldName).find('.tab-item-box').removeAttr('selected');
                    $(e.currentTarget).attr('selected', 'true');
                    var index = $(e.currentTarget).attr('index');
                    if (t.events && t.events.Selected && index != t.selected_index) {
                        t.selected_index = index;
                        t.events.Selected(index);
                    }
                });
            }

        });
        return LayoutView;
    });