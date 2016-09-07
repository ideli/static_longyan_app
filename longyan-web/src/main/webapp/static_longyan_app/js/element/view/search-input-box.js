/**
 * 头
 **/
define('js/element/view/search-input-box', [
        'text!js/element/template/search-input-box.tpl',
        'js/element/view/header',
        'js/element/view/think-input-box'
    ],
    function(SelectTpl, HeaderView, ThinkInputBox, Cache, AlertUI) {
        var search_alias = {
            community: 'community'
        }
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.config = config || {};
                t.disable = t.config.disable;
                t.events = events || {
                    Keyup: function(e) {},
                    SelectValue: function(e) {},
                    goBackUrl: function(e) {},
                    beforeConfirm: function(e) {},
                    OnClose: function(e) {},
                    beforeShowPopView: function(e){}
                };
                t.render();
                t.initEvents();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(SelectTpl, {
                    data: t.config
                }));

                if (t.config.readonly) {
                    t.setReadOnly(true);
                }
                //设置头部
                var right_button_text = '确定';
                var header_view_text = '确定';
                // var right_button_action = function(e) {};
                if (t.config && t.config.alias && t.config.alias == search_alias.community) {
                    //创建小区      
                    header_view_text = '小区名称';
                } else if (t.config && t.config.alias && t.config.alias == search_alias.community) {
                    //修改小区
                    header_view_text = '开发商';
                }
                //==========heander view==========
                t.header_view = new HeaderView({
                    el: $('#header-container-' + t.config.fieldName)
                }, {
                    text: t.config.header_view_text,
                    goBackUrl: function() {
                        console.log(t.config);
                        //关闭浮层
                        console.log('close');
                        // if (t.events.beforeConfirm) {
                        //     if (t.events.beforeConfirm()) {
                        //         t.closePopView();
                        //     }
                        // } else {
                        //     //没有定义该事件
                        //     t.closePopView();
                        // }

                        t.closePopView();
                    }
                });
                if (right_button_text && right_button_text.length > 0) {
                    t.$el.find('#header-container-' + t.config.fieldName).find('.right-box').html(right_button_text);
                    t.$el.find('#header-container-' + t.config.fieldName).find('.right-box').on('click', {
                        t: t
                    }, function(e) {
                        //save action
                        e.preventDefault();
                        e.stopPropagation();
                        var value = t.temp_value;
                        var text = t.keyword_input.getText();
                        var searchData = t.temp_searchData;
                        if (t.events.beforeConfirm) {
                            if (t.events.beforeConfirm(value, text, searchData)) {
                                t.setText(text);
                                t.setValue(value, searchData);
                                t.closePopView();
                            }
                        } else {
                            //没有定义该事件
                            t.setText(text);
                            t.setValue(value);
                            t.closePopView();
                        }

                        // t.closePopView();
                    });
                }

                var _template = null;
                if (t.config.template) {
                    //如果config有配置
                    _template = t.config.template;
                }
                //设置input
                t.keyword_input = new ThinkInputBox({
                    el: $('#think-search-form-' + t.config.fieldName)
                }, {
                    fieldName: 'keyword-input',
                    text: '',
                    placeholder: t.config.search_placeholder,
                    template: _template
                }, {
                    Keyup: function(e) {

                        if (t.events.Keyup) {
                            var keyup_value = $('#think-search-form-' + t.config.fieldName).find('input').val();
                            t.events.Keyup(e, keyup_value);
                        }
                        //获取省市区选择器的信息

                    },
                    SelectValue: function(value, text, searchData) {
                        // t.setText(text);
                        // t.setValue(value);
                        _log(searchData);
                        t.temp_value = value;
                        t.temp_text = text;
                        t.temp_searchData = searchData;
                        if (value && t.events.SelectValue) {
                            t.events.SelectValue(value, text, searchData);
                        }
                    },
                    beforeSelect: t.events.beforeSelect
                });

                t.$el.find('.pop-view').css('height', screen.height * 2 + 'px');

            },
            initEvents: function() {
                var t = this;
                t.$el.find('.' + t.config.fieldName).on('click', function(e) {
                    if ($(e.currentTarget).hasClass(t.config.fieldName) && !t.disable) {
                        //on click events
                        t.keyword_input.setValue('');
                        if (!t.events.beforeShowPopView || t.events.beforeShowPopView && t.events.beforeShowPopView()) {
                            t.showPopView();
                        }
                    }
                });
            },
            //显示浮层前置判断
            beforeShowPopView: function() {
                return true;
            },
            //显示浮层
            showPopView: function() {
                var t = this;
                t.$el.find('.search-input-box-pop-' + t.config.fieldName).show();
                $('#header-container').hide();
                $('#think-search-view-' + t.config.fieldName + ' input').focus();
            },
            //关闭浮层
            closePopView: function() {
                var t = this;
                $('#header-container').show();
                // $('#header-container').css("z-index", "999");
                t.$el.find('.search-input-box-pop-' + t.config.fieldName).hide();
                if (t.events.OnClose) {
                    t.events.OnClose();
                }
            },
            getValue: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input[action="value"]');
                    return input.val();
                }
            },
            setValue: function(value, data) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input[action="value"]');
                    input.val(value);
                    input.data('searchData', data);
                }
            },
            getText: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input[action="text"]');
                    return input.val();
                }
            },
            setText: function(text) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input[action="text"]');
                    input.val(text);
                }
            },
            setReadOnly: function(value) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);

                if (container) {
                    var input = container.find('input');
                    if (value) {
                        t.disable = true;
                        input.attr('readonly', 'readonly');
                    } else {
                        t.disable = false;
                        input.removeAttr('readonly');
                    }

                }
            },
            //设置联想内容
            setSearchBox: function(values, keyword, textFieldName, valueFieldName) {
                var t = this;
                var valueName = valueFieldName || 'value';
                var textName = textFieldName || 'text';
                if (t.keyword_input && t.keyword_input.setSearchBox) {
                    t.keyword_input.setSearchBox(values, keyword, textFieldName, valueFieldName);
                }
            },
            //清空联想内容
            clearSearchBox: function() {
                var t = this;
                if (t.keyword_input && t.keyword_input.clearSearchBox) {
                    t.keyword_input.clearSearchBox();
                }
            },
            //是否为空
            isVerify: function() {
                var t = this;
                var v = t.getValue();
                return $nvwa.string.isVerify(v);
            }
        });
        return LayoutView;
    });