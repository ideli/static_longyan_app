/**
 *login view
 *登录
 **/
define('js/longyan/view/reset_pc', [
        'text!js/longyan/template/login_pc.tpl',
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
        // var view_id = '#reset-view';
        // var form_id = '#reset-form';
        var msg_tips = '#login-pc-view .tips-bar.error-msg-tips .error-icon';
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
                    if (__isVerify()) {
                        t.submit_button.setDisable(false);
                    } else {
                        t.submit_button.setDisable(true);
                    }
                };
                var __isVerify = function() {
                    if (t.phone_input.isVerify() &&
                        t.code_input.isVerify() &&
                        t.new_password_input.isVerify() &&
                        t.confirm_password_input.isVerify()) {
                        return true;
                    }
                    return false;
                }
                /*标题*/
                t.tips_bar = new TipsBar({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'title-tips',
                    text: '找回密码'
                });

                /*msg提示框*/
                t.tips_bar = new TipsBar({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'error-msg-tips',
                    text: ''
                });

                //组装手机号入框
                t.phone_input = new InputBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'phone-input',
                    text: '手机号',
                    type: 'number'
                }, {
                    Keyup: function() {
                        __keyup();
                        t.verifylen();
                    }
                });

                //组装验证码输入框
                t.code_input = new OtpBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'otp-input',
                    text: '短信验证码',
                    type: 'text'
                }, {
                    Keyup: function() {
                        __keyup();
                    },
                    BtnClick: function() {
                        //发送验证码逻辑

                        var is_phone = t.phone_input.isPhone();
                        var req = "forget";
                        if (is_phone) {
                            //显示loading
                            tipsAlert.openLoading({
                                content: '加载中...'
                            });
                            UserApi.sendResetLoginPasswordOtp(
                                t.phone_input.getValue(),
                                req,
                                function(data) {
                                    //关闭loading
                                    tipsAlert.close();
                                    t.code_input.setCountdown(60);
                                },
                                function(code, msg) {
                                    //关闭loading
                                    tipsAlert.close();
                                    if (code && code == 200) {
                                       $(msg_tips).show().html(msg);
                                        /*tipsAlert.openAlert({
                                            content: msg
                                        });*/
                                    } else {
                                        console.log(code+"----"+msg);
                                        $(msg_tips).show().html(msg);
                                        /*tipsAlert.openAlert({
                                            content: msg
                                        });*/
                                    }
                                });

                        } else {
                            $(msg_tips).show().html('请输入正确的手机号');
                           /* tipsAlert.openAlert({
                                content: '请输入正确的手机号'
                            });*/
                        }
                    }
                });

                //组装输入新密码输入框
                t.new_password_input = new InputBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'new-password-input',
                    text: '设置密码',
                    type: 'password'
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });
                //组装确认新密码输入框
                t.confirm_password_input = new InputBox({
                    el: $('#logo-pc-box-content')
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
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'submit-button',
                    text: '确认'
                }, {
                    Click: function(e) {

                        if (__isVerify()) {
                            //重置密码流程
                            //重置密码流程
                            var newPass = t.new_password_input.getValue().length;
                            var confirm = t.confirm_password_input.getValue().length;
                            if (newPass>5 && newPass<20 && confirm>5 && confirm<20) {
                                    if (t.new_password_input.getValue() == t.confirm_password_input.getValue()) {
                                        //加载loading
                                        tipsAlert.openLoading({
                                            content: '加载中...'
                                        });
                                        UserApi.resetLoginPassword(
                                            t.phone_input.getValue(),
                                            t.code_input.getValue(),
                                            t.new_password_input.getValue(),
                                            function() {
                                                //关闭loading
                                                tipsAlert.close();
                                                //success
                                                tipsAlert.openAlert({
                                                    content: '重置登陆密码成功',
                                                    onConfirm: function(e) {
                                                        //重置登陆密码成功,跳转到登录页面
                                                        router.navigate('reset_success_pc', {
                                                            trigger: true
                                                        });
                                                    }
                                                });
                                            },
                                            function(code, msg) {
                                                //关闭loading
                                                tipsAlert.close();
                                                //error
                                                $(msg_tips).show().html(msg);
                                            });
                                    } else {
                                        $(msg_tips).show().html('2次输入的密码不一致');
                                    }
                            } else {
                                     //密码必须为6-20位数字或字母
                                $(msg_tips).show().html('密码必须为6-20位数字或字母');                                
                            }

                        } else {
                            $(msg_tips).show().html('请填写完整信息');
                        }
                    }
                });

                $('#login-pc-view .otp-box').addClass('g-hrz').find('input').addClass('u-full');
                t.code_input.setBtnDisable(true);
                t.submit_button.setDisable(true);
                $('#logo-pc-box-content').addClass('forgetPassword');
                $(".error-msg-tips").append("<p class='error-icon'></p>");


                // t.login_button.setDisable(false);
                var username = Cache.get('user-name');
                t.phone_input.setValue(username.username);
                t.verifylen();

            },
            verifylen:function(){
                        var t = this;
                        var v = t.phone_input.getValue().length;
                        //var phonelength = t.phone_input.getValue.length;
                        if (v==11) {
                            t.code_input.setBtnDisable(false);
                        } else {
                            t.code_input.setBtnDisable(true);
                        }

            }
        });
        return LayoutView;
    });