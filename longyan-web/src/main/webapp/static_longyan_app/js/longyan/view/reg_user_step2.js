/**
 *reg_user_step2 view
 *非员工注册 步骤2
 **/
define('js/longyan/view/reg_user_step2', [
        'text!js/longyan/template/reg_user_step2.tpl',
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

                };
                var __isVerify = function() {

                    return true;
                }

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '<span ><a href="#reg_employee_step1">员工注册</a></span>&nbsp;&nbsp;<span class="bar-activate">会员注册</span>'
                });

                //组装输入新密码输入框
                t.set_password_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'set-password-input',
                    text: '设置密码',
                    type: 'password'
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });
                //组装确认新密码输入框
                t.confirm_password_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'confirm-password-input',
                    text: '确认密码',
                    type: 'password'
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });

                //组装确认按钮
                t.submit_button = new ButtonBox({
                    el: $(view_id)
                }, {
                    fieldName: 'submit-button',
                    text: '确认'
                }, {
                    Click: function(e) {
                        if (__isVerify()) {

                            if (!t.set_password_input.isVerify()) {
                                //请填写登陆密码
                                tipsAlert.openAlert({
                                    content: '请填写登陆密码'
                                });
                                return;
                            }
                            if (!t.confirm_password_input.isVerify()) {
                                //请填写确认密码
                                tipsAlert.openAlert({
                                    content: '请填写确认密码'
                                });
                                return;
                            }
                            if (t.set_password_input.getValue() != t.confirm_password_input.getValue()) {
                                //输入密码不一致
                                tipsAlert.openAlert({
                                    content: '输入密码不一致'
                                });
                                return;
                            }

                            var user = Cache.get('pre-check-user');
                            if (user) {
                                //发送注册请求
                                UserApi.userRegister({
                                    phone: user.phone,
                                    code: user.code,
                                    password: t.set_password_input.getValue()
                                }, function(data) {
                                    tipsAlert.openAlert({
                                        content: '注册成功',
                                        onConfirm: function(e) {
                                            tipsAlert.close();
                                            //注册成功,跳转到登录页面
                                            router.navigate('login', {
                                                trigger: true
                                            });
                                        }
                                    });
                                }, function(code, msg) {
                                    tipsAlert.openAlert({
                                        content: msg
                                    });
                                });
                            } else {
                                tipsAlert.openAlert({
                                    content: '系统异常'
                                });
                            }

                        } else {
                            tipsAlert.openAlert({
                                content: '请填写完整信息'
                            });
                        }
                    }
                });

            }
        });
        return LayoutView;
    });