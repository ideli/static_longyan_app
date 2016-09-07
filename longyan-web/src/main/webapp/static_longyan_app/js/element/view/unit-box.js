/**
 * 头
 **/
define('js/element/view/unit-box', [
        'text!js/element/template/unit-box.tpl'
    ],
    function(UnitTpl) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.config = config || {};
                // t.disable = t.config.disable;
                t.events = events;
                t.render();
                t.initEvents();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(UnitTpl, {
                    data: t.config
                }));
            },
            initEvents: function() {
                var t = this;
                if (t.events && t.events.Click) {
                    t.$el.find('.' + t.config.fieldName).find('.title_edit').off('click');
                    t.$el.find('.' + t.config.fieldName).find('.title_edit').on('click', function(e) {
                        if ($(e.currentTarget).parent().hasClass(t.config.fieldName)) {
                            t.events.Click(e);
                        }
                    });
                }

                t.$el.find('.' + t.config.fieldName).find('.add-btn').off('click');
                t.$el.find('.' + t.config.fieldName).find('.add-btn').on('click', function(e) {
                    var addInput = t.$el.find('.' + t.config.fieldName).find('.add-input');
                    var addValue = addInput.find('input').val();
                    if (addValue) {
                        addInput.find('input').val('');
                        var str = '<div class="unit-item pre-add">' +
                            '<input class="" type="text" readonly value="' + addValue + '"/>' +
                            '<i class="iconfont icon-longyandel cancle"></i>' +
                            '</div>';
                        addInput.before(str);
                    }
                });

                t.$el.find('.' + t.config.fieldName).off('click', '.cancle');
                t.$el.find('.' + t.config.fieldName).on('click', '.cancle', function() {
                    alert('cancle event');
                })

                t.initDeleteListener();
            },
            initDeleteListener: function() {
                var t = this;
                t.$el.find('.' + t.config.fieldName).off('click', '.unit-box-content.edit .unit-item');
                t.$el.find('.' + t.config.fieldName).on('click', '.unit-box-content.edit .unit-item', function(e) {
                    var current = $(e.currentTarget);
                    if (current.hasClass('delete-select')) {
                        current.removeClass('delete-select');
                        current.find('.delete-icon').remove();
                    } else {
                        current.addClass('delete-select');
                        var str = '<i class="iconfont icon-longyanvillage delete-icon"></i>';
                        current.append(str);
                    }
                })
            },
            toEditView: function(callback) {
                var t = this;
                var unitContent = t.$el.find('.' + t.config.fieldName).find('.unit-box-content');
                if (unitContent.hasClass('edit')) {
                    unitContent.removeClass('edit');
                    unitContent.off('click');
                    unitContent.find('.delete-select').removeClass('delete-select');
                    unitContent.find('.delete-icon').remove();
                    //底部的按钮改成删除
                } else {
                    unitContent.addClass('edit');
                    t.initDeleteListener();
                }
                if (callback) {
                    callback();
                }
            },
            setReadonly: function() {
                var t = this;
                t.$el.find('.' + t.config.fieldName).find('.text').attr('readonly', 'readonly');
                t.$el.find('.' + t.config.fieldName).find('.text').addClass('readonly');
            },
            removeReadonly: function() {
                var t = this;
                t.$el.find('.' + t.config.fieldName).find('.text').removeAttr('readonly');
                t.$el.find('.' + t.config.fieldName).find('.text').removeClass('readonly');
                t.$el.find('.' + t.config.fieldName).find('.text').focus();
            }

        });
        return LayoutView;
    });