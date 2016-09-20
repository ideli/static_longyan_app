/**
 * card-select-box
 * 卡片选择器
 **/
define('js/element/view/card-select-box', [
        'text!js/element/template/card-select-box.tpl'
    ],
    function(CardSelectBoxTpl) {
        var font_unselect = '&#xe60e;';
        var font_selected = '&#xe611;';

        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.config = config || {};
                t.disable = t.config.disable || false;
                t.events = events;
                t.render();
                t.initEvents();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(CardSelectBoxTpl, {
                    data: t.config
                }));
                // if (t.config.readonly) {
                //     t.setReadOnly(true);
                // }
            },
            initEvents: function() {
                var t = this;
                t.$el.find('.' + t.config.fieldName).find('.selection').on('click', function(e) {
                    var value = $(e.currentTarget).attr('data-value');
                    if (!t.disable) {
                        //判断当前是非只读状态
                        if (t.config.multipleSelect) {
                            //多选    
                            if ($(e.currentTarget).hasClass('activity')) {
                                $(e.currentTarget).removeClass('activity');
                            } else {
                                $(e.currentTarget).addClass('activity');
                            }
                        } else {
                            //单选    
                            t.$el.find('.' + t.config.fieldName).find('.activity').removeClass('activity');
                            $(e.currentTarget).addClass('activity');
                        }
                    }
                });
            },
            getValue: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                var value_array = [];
                if (container) {
                    // var input = container.find('input');
                    // return input.val();                    
                    var selectContainer = t.$el.find('.' + t.config.fieldName).find('.activity');
                    $.each(selectContainer, function(index, item) {
                        value_array.push($(item).attr('data-value'));
                    });
                }

                console.log(value_array.toString());

                return value_array.toString();
            },
            setValue: function(values) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var selectContainer = t.$el.find('.' + t.config.fieldName).find('.selection');
                    $.each(selectContainer, function(index, item) {
                        $.each(values, function(value_index, value_item) {
                            if (value_item && $(item).attr('data-value') && $(item).attr('data-value') == value_item) {
                                $(item).addClass('activity');
                            }
                        });
                    });
                }
            },
            //设置是否只读
            setReadOnly: function(value) {
                var t = this;
                if (value) {
                    t.disable = true;
                } else {
                    t.disable = false;
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