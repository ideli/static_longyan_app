/**
 * radio box
 **/
define('js/element/view/radio-box', [
        'text!js/element/template/radio-box.tpl'
    ],
    function(RadioTpl, Cache, AlertUI) {
        var font_unselect = '&#xe60e;';
        var font_selected = '&#xe611;';

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
                t.$el.append(tpl(RadioTpl, {
                    data: t.config
                }));
                // if (t.config.readonly) {
                //     t.setReadOnly(true);
                // }
            },
            initEvents: function() {
                var t = this;
                if (t.events && t.events.Keyup) {
                    t.$el.find('.' + t.config.fieldName).off('keyup');
                    t.$el.find('.' + t.config.fieldName).on('keyup', function(e) {
                        if ($(e.currentTarget).hasClass(t.config.fieldName) && !t.disable) {
                            t.events.Keyup(e);
                        }
                    });
                }
                t.$el.find('.' + t.config.fieldName).find('i').on('click', function(e) {
                    var currentTarget = $(e.currentTarget);
                    var index = currentTarget.parent().parent().attr('index');
                    // currentTarget.addClass('hidden');
                     t.$el.find('.' + t.config.fieldName).find('.selection[index]').find('i').removeClass('selection-selected').addClass('selection-unselect').html(font_unselect);
                     t.$el.find('.' + t.config.fieldName).find('.selection[index="' + index + '"]').find('i').removeClass('selection-unselect').addClass('selection-selected').html(font_selected);
                    var value = t.$el.find('.selection[index="' + index + '"]').attr('data-value');
                    var container = t.$el.find('.' + t.config.fieldName);
                    if (container) {
                        var input = container.find('input');
                        input.val(value);
                    }
                });
            },
            getValue: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input');
                    return input.val();
                }
            },
            setValue: function(value) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input');
                    input.val(value);
                    container.find('.selection[index]').find('i').removeClass('selection-selected').addClass('selection-unselect').html(font_unselect);
                    container.find('.selection[data-value="' + value + '"]').find('i').removeClass('selection-unselect').addClass('selection-selected').html(font_selected);
                }
            },
            setReadOnly: function(value) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input');
                    if (value) {
                        input.attr('readonly', 'readonly');
                    } else {
                        input.removeAttr('readonly');
                    }

                }
            },
            //是否为空
            isVerify: function() {
                var t = this;
                var v = t.getValue();
                return $nvwa.string.isVerify(v);
            },

        });
        return LayoutView;
    });