/**
 * 头
 **/
define('js/element/view/think-input-box', [
        'text!js/element/template/think-input-box.tpl'
    ],
    function(InputTpl, Cache, AlertUI) {
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
                t.$el.append(tpl(InputTpl, {
                    data: t.config
                }));
                if (t.config.readonly) {
                    t.setReadOnly(true);
                }
            },
            initEvents: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (t.events && t.events.Keyup) {
                    t.$el.find('.' + t.config.fieldName).off('input');
                    t.$el.find('.' + t.config.fieldName).on('input', function(e) {
                        if ($(e.currentTarget).hasClass(t.config.fieldName) && !t.disable) {
                            t.events.Keyup(e);
                        }
                    });
                }

                container.find('.search-box').on('click', '.search-box-item', function(e) {
                    var _select = function() {
                        var currentTarget = $(e.currentTarget);
                        var value = currentTarget.attr('data-value');
                        var text = currentTarget.attr('data-text');
                        container.find('input').attr('data-value', value).val(text);
                        container.find('.search-box').html('');
                        console.log(value);
                        if (t.events && t.events.SelectValue) {
                            t.events.SelectValue(value, currentTarget.attr('data-text'), currentTarget.data('searchData'));
                        }
                    };

                    if (t.events.beforeSelect) {
                        //如果有事件声明
                        var value = $(e.currentTarget).attr('data-value');
                        var text = $(e.currentTarget).attr('data-text');
                        if (t.events.beforeSelect(value, text)) {
                            _select();
                        }
                    } else {
                        _select();
                    }
                });

            },
            focus: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input');
                    input.focus();
                }
            },
            getValue: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input');
                    return input.attr('data-value');
                }
            },
            getText: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input');
                    return input.val();
                }
            },
            setValue: function(value, text) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input');
                    input.attr('data-value', value);
                    input.val(text);
                }
            },
            //设置联想的内容
            setSearchBox: function(values, keyword, textFieldName, valueFieldName) {
                // var values = [{
                //     text: '123',
                //     value: '456'
                // }]
                var valueName = valueFieldName || 'value';
                var textName = textFieldName || 'text';
                var dataList = [];


                if (values && values.length > 0) {
                    $.each(values, function(i, item) {
                        var tmp = {};
                        tmp = item;
                        tmp['_text'] = item[textName];
                        tmp['_value'] = item[valueName];
                        dataList.push(tmp);
                    });
                }


                var t = this;
                var template = '<div class="search-box-item" data-value="<#=data._value#>" data-text="<#=data._text#>"><#=data._textDisplay#></div><div class = "search-box-item-line"></div>';
                if (t.config.template) {
                    //如果config里面有template就用config里边的template
                    template = t.config.template;
                }
                var container = t.$el.find('.' + t.config.fieldName);
                container.find('.search-box').html('');
                if (dataList && dataList.length > 0) {
                    $.each(dataList, function(i, item) {
                        if (keyword) {
                            item['_textDisplay'] = item['_text'].replace(keyword, '<span class="search-box-item-key">' + keyword + '</span>') || '';
                        } else {
                            item['_textDisplay'] = '';
                        }
                        container.find('.search-box').append($(tpl(template, {
                            data: item
                        })).data('searchData',item));
                    });
                } else {
                    container.find('.search-box').html('');
                }
            },
            clearSearchBox: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                container.find('.search-box').html('');
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
            //是否是手机号
            isPhone: function() {
                var t = this;
                var v = t.getValue();
                return $nvwa.string.isPhone(v);
            }
        });
        return LayoutView;
    });