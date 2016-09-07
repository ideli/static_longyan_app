/**
 * 头
 **/
define('js/element/view/otp-box', [
        'text!js/element/template/otp-box.tpl'
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
                t.disableBtn = false;
                t.countdown = 0;
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
                t.initTextEvents();
                t.initBtnEvents();
            },
            removeEvents: function() {
                var t = this;
                t.removeBtnEvents();
            },
            initTextEvents: function() {
                var t = this;
                if (t.events && t.events.Keyup) {
                    t.$el.find('.' + t.config.fieldName).off('keyup');
                    t.$el.find('.' + t.config.fieldName).on('keyup', function(e) {
                        if ($(e.currentTarget).hasClass(t.config.fieldName) && !t.disable) {
                            t.events.Keyup(e);
                        }
                    });
                }
            },
            initBtnEvents: function() {
                var t = this;
                if (t.events && t.events.BtnClick) {
                    t.$el.find('.' + t.config.fieldName).find('.button').off('click');
                    t.$el.find('.' + t.config.fieldName).find('.button').on('click', function(e) {
                        if (!t.disable) {
                            t.events.BtnClick(e);
                        }
                    });
                }
            },
            removeBtnEvents: function() {
                var t = this;
                t.$el.find('.' + t.config.fieldName).find('.button').off('click');
            },
            setBtnDisable: function(value) {
                var t = this;
                t.disableBtn = value;
                if (value) {
                    //disable
                    t.$el.find('.' + t.config.fieldName).find('.button').addClass('disable');
                    t.removeEvents();
                } else {
                    //enable
                    t.$el.find('.' + t.config.fieldName).find('.button').removeClass('disable');
                    t.initBtnEvents();
                }
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
                }
            },
            //是否为空
            isVerify: function() {
                var t = this;
                var v = t.getValue();
                return $nvwa.string.isVerify(v);
            },
            setCountdown: function(seconds) {
                var t = this;
                if (seconds) {
                    t.countdown = seconds;
                    //设置定时器
                    t.$el.find('.' + t.config.fieldName).find('.button').html('重新获取(' + t.countdown + ')');
                    t.setBtnDisable(true);
                    __con = function() {
                        t.countdown--;
                        if (t.countdown < 1) {
                            window.clearInterval(t.interval);
                            console.log('clear 定时器');
                            t.$el.find('.' + t.config.fieldName).find('.button').html('获取验证码');
                            t.setBtnDisable(false);
                        } else {
                            t.$el.find('.' + t.config.fieldName).find('.button').html('重新获取(' + t.countdown + ')');
                        }
                        console.log(t.countdown);
                    };
                    if (t.interval && t.countdown && t.countdown < 1) {
                        window.clearInterval(t.interval);
                    }
                    t.interval = window.setInterval("__con()", 1000);
                }
            },
            clearCountdown: function() {
                var t = this;
                if (t.interval) {
                    window.clearInterval(t.interval);
                }
            }

        });
        return LayoutView;
    });