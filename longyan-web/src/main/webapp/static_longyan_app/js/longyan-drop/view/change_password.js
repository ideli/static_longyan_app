/**
 * 首页 -> 用户中心 -> 修改密码
 **/
define('js/longyan/view/change_password', [
        'text!js/longyan/template/change_password.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/api/user'
    ],
    function(ResetTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, UserApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#change-password-view';
        var form_id = '#change-password-form';
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
                    if (t.origin_password_input.isVerify() &&
                        t.new_password_input.isVerify() &&
                        t.confirm_password_input.isVerify()) {
                        return true;
                    }
                    return false;
                }
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '修改密码',
                    goBackUrl: function() {
                        router.navigate('user_info', {
                            trigger: true
                        });
                    }
                });

                //组装手机号入框
                t.origin_password_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'origin-password-input',
                    text: '旧密码',
                    type: 'password',
                    placeholder: '请输入旧密码'
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });


                //组装输入新密码输入框
                t.new_password_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'new-password-input',
                    text: '新密码',
                    type: 'password',
                    placeholder: '请输入6-20位数字或字母'
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
                    text: '密码确认',
                    type: 'password',
                    placeholder: '请再次输入6-20位数字或字母'
                }, {
                    Keyup: function() {
                        __keyup();
                        t.verifyPassword();
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
                        var user = Cache.get('user-info');
                        if (__isVerify()) {
                            //重置密码流程
                            var origin = t.origin_password_input.getValue().length;
                            var newPass = t.new_password_input.getValue().length;
                            var confirm = t.confirm_password_input.getValue().length;
                            if (origin > 5 && origin < 20 && newPass > 5 && newPass < 20 && confirm > 5 && confirm < 20) {
                                if (t.new_password_input.getValue() == t.confirm_password_input.getValue()) {
                                    //加载loading
                                    tipsAlert.openLoading({
                                        content: '加载中...'
                                    });
                                    UserApi.changeLoginPassword({
                                        phone: user.mobilePhone,
                                        originPassword: t.origin_password_input.getValue(),
                                        newPassword: t.new_password_input.getValue()
                                    }, function() {
                                        //关闭loading
                                        tipsAlert.close();
                                        //显示修改密码成功提示
                                        tipsAlert.openToast({
                                            content: tpl("<img src='<#=window.resource.image#>/success-icon.png' class='success-info'> <span class='success-info'>登录密码修改成功!</span>", {}),
                                        });
                                        setTimeout("router.navigate('user_info', {trigger: true});", "2000");

                                    }, function(code, msg) {
                                        //关闭loading
                                        tipsAlert.close();
                                        console.log(msg);
                                        //显示异常信息
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
                                tipsAlert.openAlert({
                                    content: '密码必须为6-20位数字或字母'
                                });
                            }


                        } else {
                            tipsAlert.openAlert({
                                content: '密码不能为空'
                            });
                        }
                    }
                });

                 t.submit_button.setDisable(true);
                // t.login_button.setDisable(false);
                $("#change-password-form .confirm-password-input").append(tpl("<div class='passwordSuccess'><img src='<#=window.resource.image#>/successbar-icon.png' class='success-icon hide'><img src='<#=window.resource.image#>/failbar-icon.png' class='fail-icon hide'></div>"));

            },
            verifyPassword: function() {
                var t = this;
                var newval = t.new_password_input.getValue();
                var conval = t.confirm_password_input.getValue();
                if (newval.length > 5 && conval.length > 5) {
                    if (newval == conval) {
                        $('.passwordSuccess img').css("display", "none");
                        $('.passwordSuccess .success-icon').css("display", "block");
                        return true;
                    } else {
                        $('.passwordSuccess img').css("display", "none");
                        $('.passwordSuccess .fail-icon').css("display", "block");
                        return true;
                    }
                } else {
                    $('.passwordSuccess img').css("display", "none");
                    return true;
                }
            }

        });
        return LayoutView;
    });