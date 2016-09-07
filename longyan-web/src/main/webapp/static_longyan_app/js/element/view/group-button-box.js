/**
 * 头
  //底部按钮组
                t.bottom_group_button_box = new GroupButtonBox({
                    el: $(form_id)
                }, {
                    fieldName: 'bottom_group_button_box',
                    clazz: 'fixed-bottom g-hrz',
                    btns: [{
                        text: '添加楼栋号',
                        clazz: 'blue u-full',
                        disable: false,
                        Click: function() {

                        }
                    }]
                });
 **/
define('js/element/view/group-button-box', [
        'text!js/element/template/group-button-box.tpl'
    ],
    function(GroupButtonTpl) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.config = config || {};
                t.disable = t.config.disable;
                t.btns = t.config.btns;
                t.events = events;
                t.render();
                t.initEvents();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(GroupButtonTpl, {
                    data: t.config
                }));
            },
            initEvents: function() {
                var t = this;
                // if (t.events && t.events.Click) {
                //     t.$el.find('.' + t.config.fieldName).off('click');
                //     t.$el.find('.' + t.config.fieldName).on('click', function(e) {
                //         if ($(e.currentTarget).hasClass(t.config.fieldName) && !t.disable) {
                //             t.events.Click(e);
                //         }
                //     });
                // }
                if (t.btns && t.btns.length > 0) {
                    for (var i = 0; i < t.btns.length; i++) {
                        var btn = t.btns[i];
                        if (btn.Click) {
                            t.$el.find('.' + t.config.fieldName).find('[num="' + i + '"]').off('click');
                            t.$el.find('.' + t.config.fieldName).find('[num="' + i + '"]').on('click', function(e) {
                                var index = $(e.currentTarget).attr('num');
                                if ($(e.currentTarget).parent().hasClass(t.config.fieldName) && !t.btns[index].disable) {
                                    t.btns[index].Click(e);
                                }
                            });
                        }
                    }
                }
            },
            //第几个按钮是影藏的
            isHidden: function(index) {
                var t = this;
                if (index) {
                    return t.$el.find('.' + t.config.fieldName).find('[num="' + index + '"]').hasClass('hidden');
                } else {
                    return t.$el.find('.' + t.config.fieldName).hasClass('hidden');
                }
            },
            //设置影藏或显示  isHide为true时 设置影藏  index没有值时，影藏按钮组
            setHidden: function(isHide, index) {
                var t = this;
                if (index) {
                    if (isHide) {
                        t.$el.find('.' + t.config.fieldName).find('[num="' + index + '"]').addClass('hidden');
                    } else {
                        t.$el.find('.' + t.config.fieldName).find('[num="' + index + '"]').removeClass('hidden');
                    }
                } else {
                    if (isHide) {
                        t.$el.find('.' + t.config.fieldName).addClass('hidden');
                    } else {
                        t.$el.find('.' + t.config.fieldName).removeClass('hidden');
                    }
                }
            },
            setDisable: function(index, value) {
                var t = this;
                t.btns[index].disable = value;
                if (value) {
                    t.$el.find('.' + t.config.fieldName).find('[num="' + index + '"]').addClass('disable');
                } else {
                    t.$el.find('.' + t.config.fieldName).find('[num="' + index + '"]').removeClass('disable');
                }
            }
        });
        return LayoutView;
    });