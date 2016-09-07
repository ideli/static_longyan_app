/**
 *reg_employee_step1 view
 *非员工注册 步骤2
 **/
define('js/longyan/view/reg_user_step1', [
        'text!js/longyan/template/reg_user_step1.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/otp-box',
        'js/element/view/tips-bar',
        'js/api/user'
    ],
    function(ResetTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, OtpBox, TipsBar, UserApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#reset-view';
        var form_id = '#reset-form';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(ResetTpl, {}));
                var __keyup = function() {
                    // if (__isVerify()) {
                    //     t.submit_button.setDisable(false);
                    // } else {
                    //     t.submit_button.setDisable(true);
                    // }
                };

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '<span ><a href="#reg_employee_step1">员工注册</a></span>&nbsp;&nbsp;<span class="bar-activate">会员注册</span>'
                });

                //组装手机号输入框
                t.phone_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'phone-input',
                    text: '手机号',
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });

                //组装验证码输入框
                t.code_input = new OtpBox({
                    el: $(form_id)
                }, {
                    fieldName: 'otp-input',
                    text: '验证码'
                }, {
                    Keyup: function() {
                        __keyup();
                    },
                    BtnClick: function() {
                        //发送验证码逻辑
                        var is_phone = t.phone_input.isPhone();
                        if (is_phone) {
                            //调用发验证码接口
                            UserApi.sendUserRegisterOtp(
                                t.phone_input.getValue(),
                                function() {
                                    //短信验证码发送成功，开始倒计时
                                    t.code_input.setCountdown(60);
                                },
                                function(code, msg) {
                                    tipsAlert.openAlert({
                                        content: msg
                                    });
                                });
                        } else {
                            tipsAlert.openAlert({
                                content: '请输入正确的手机号'
                            });
                        }
                    }
                });

                //组装确认按钮
                t.submit_button = new ButtonBox({
                    el: $(view_id)
                }, {
                    fieldName: 'submit-button',
                    text: '下一步'
                }, {
                    Click: function(e) {
                        if (!t.code_input.isVerify()) {
                            //请填写验证码
                            tipsAlert.openAlert({
                                content: '请填写验证码'
                            });
                            return;
                        }
                        //缓存下一步的数据?                        
                        if (t.phone_input.isPhone()) {
                            var phone = t.phone_input.getValue();
                            var code = t.code_input.getValue();
                            Cache.set('pre-check-user', {
                                phone: phone,
                                code: code
                            });
                            router.navigate('reg_user_step2', {
                                trigger: true
                            });

                        } else {
                            tipsAlert.openAlert({
                                content: '请输入正确的手机号'
                            });
                        }


                    }
                });

            },
            destroy: function() {
                var t = this;
                //清理定时器
                t.code_input.clearCountdown();
            },
        });
        return LayoutView;
    });