/**
 * 头
t.member_title_box = new TitleBox({
                    el: $(form_id)
                }, {
                    fieldName: 'member_title_box',
                    text: '1号楼',
                    readonly: 'readonly'
                }, {
                    Click: function(e) {
                        t.member_title_box.removeReadonly();
                    },
                    Blur: function(e) {
                        t.member_title_box.setReadonly();
                    }
                });
 
 **/
define('js/element/view/title-box', [
        'text!js/element/template/title-box.tpl'
    ],
    function(TitleTpl) {
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
                t.$el.append(tpl(TitleTpl, {
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
                if (t.events && t.events.Blur) {
                    t.$el.find('.' + t.config.fieldName).find('.text').off('blur');
                    t.$el.find('.' + t.config.fieldName).find('.text').on('blur', function(e) {
                        if ($(e.currentTarget).parent().hasClass(t.config.fieldName)) {
                            t.events.Blur(e);
                        }
                    });
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