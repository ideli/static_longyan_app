/**
 *login view
 *登录
 **/
define('js/longyan/view/reset', [
        'text!js/longyan/template/reset.tpl',
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
                t.isIos()   //判断手机终端信息
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
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '忘记密码'
                });
                $('#reset-form').addClass('forgetPassword');
                //组装手机号入框
                t.phone_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'phone-input',
                    text: '手机号'
                }, {
                    Keyup: function() {
                        __keyup();
                        t.verifylen();
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
                                        tipsAlert.openAlert({
                                            content: msg
                                        });
                                    } else {
                                        tipsAlert.openAlert({
                                            content: msg
                                        });
                                    }
                                });

                        } else {
                            tipsAlert.openAlert({
                                content: '请输入正确的手机号'
                            });
                        }
                    }
                });

                //组装输入新密码输入框
                t.new_password_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'new-password-input',
                    text: '新密码',
                    type: 'password'
                }, {
                    Keyup: function() {
                        __keyup();
                        t.verifyPassword();
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
                        t.verifyPassword();
                    }
                });

                t.tips_bar = new TipsBar({
                    el: $(view_id)
                }, {
                    fieldName: 'phone-tips',
                    text: '遇到问题？  联系客服：<a class="telPhone" href="javascript:void(0)"  style="color:#323232;">400 688 9333<a>'
                });

                //组装确认按钮
                t.submit_button = new ButtonBox({
                    el: $(view_id)
                }, {
                    fieldName: 'submit-button',
                    text: '保存'
                }, {
                    Click: function(e) {

                        if (__isVerify()) {
                            //重置密码流程
                            var username = t.phone_input.getValue();
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
                                                tipsAlert.openToast({
                                                    content: tpl("<img src='<#=window.resource.image#>/success-icon.png' class='success-info'> <span class='success-info'>重置登录密码成功!</span>",{}),
                                                });
                                                //忘记密码，设置成功后，返回登录页面，手机号带到登录界面
                                                Cache.set('user-name', {username: username});
                                                setTimeout("router.navigate('login', {trigger: true});", "2000");
                                                /*tipsAlert.openAlert({
                                                    content: '重置登录密码成功',
                                                    onConfirm: function(e) {
                                                        //重置登陆密码成功,跳转到登录页面
                                                        router.navigate('login', {
                                                            trigger: true
                                                        });
                                                    }
                                                });*/
                                            },
                                            function(code, msg) {
                                                //关闭loading
                                                tipsAlert.close();
                                                //error
                                                tipsAlert.openAlert({
                                                    content: msg
                                                });
                                            });
                                    } else {
                                        tipsAlert.openAlert({
                                            content: '2次输入的密码不一致'
                                        });
                                    }
                            } else {
                                     //密码必须为6-20位数字或字母
                                tipsAlert.openAlert({
                                    content: '密码必须为6-20位数字或字母'
                                });
                                
                            }

                        } else {
                            tipsAlert.openAlert({
                                content: '请填写完整信息'
                            });
                        }
                    }
                });

                t.submit_button.setDisable(true);
                //t.code_input.setBtnDisable(true);

                // t.login_button.setDisable(false);
                var username = Cache.get('user-name');
                t.phone_input.setValue(username.username);
                t.verifylen();

                $(".forgetPassword .confirm-password-input").append(tpl("<div class='passwordSuccess'><img src='<#=window.resource.image#>/successbar-icon.png'></div>"));

            },            
            verifyPassword: function() {
                var t = this;
                //验证密码状态
               var newval = t.new_password_input.getValue();
               var conval = t.confirm_password_input.getValue();
               if(newval.length>5 && conval.length>5 ) {
                    if(newval == conval){
                        $('.passwordSuccess').css("display","block");
                    } else {
                        $('.passwordSuccess').css("display","none");                            
                    }
                } else { $('.passwordSuccess').css("display","none");}
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

            },
            isIos: function() {
                var t = this;
                var userAgentInfo = navigator.userAgent;
                var Agents = ["iPhone",
                    "iPad", "iPod"
                ];
                for (var v = 0; v < Agents.length; v++) {
                    if (userAgentInfo.indexOf(Agents[v]) > 0) {
                        $('.telPhone').attr('href','tel:4006889333'); 
                    }
                }
            }
      });
        return LayoutView;
    });