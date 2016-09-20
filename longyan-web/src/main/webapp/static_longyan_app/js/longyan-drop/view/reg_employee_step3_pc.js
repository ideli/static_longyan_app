/**
 *reg_employee_step3 view
 *员工注册 步骤3
 **/
define('js/longyan/view/reg_employee_step3_pc', [
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
        /*var view_id = '#reset-view';
        var form_id = '#reset-form';*/
        var msg_tips = '#login-pc-view .tips-bar.error-msg-tips .error-icon';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                t.loadData();
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
                    if (t.new_password_input.isVerify() &&
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
                    text: '输入密码'
                });

                /*msg提示框*/
                t.tips_bar = new TipsBar({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'error-msg-tips',
                    text: ''
                });


                //组装输入新密码输入框
                t.new_password_input = new InputBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'new-password-input',
                    text: '设置密码',
                    type: 'password',
                    placeholder: '6位以上数字或字母'
                }, {
                    Keyup: function() {
                        __keyup();
                        t.verifyPassword();
                    }
                });

                //组装确认新密码输入框
                t.confirm_password_input = new InputBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'confirm-password-input',
                    text: '确认密码',
                    type: 'password',
                    placeholder: '请确认新密码'
                }, {
                    Keyup: function() {
                        __keyup();
                        t.verifyPassword();
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

                            if (!t.new_password_input.isVerify()) {
                                //请填写登陆密码
                                $(msg_tips).show().html('请填写登陆密码');
                                return;
                            }
                            if (!t.confirm_password_input.isVerify()) {
                                //请填写确认密码
                                $(msg_tips).show().html('请填写确认密码');
                                return;
                            }
                            var newPass = t.new_password_input.getValue().length;
                            var confirm = t.confirm_password_input.getValue().length;
                            if (!newPass>5 && newPass<20 && confirm>5 && confirm<20) {
                                 //密码必须为6-20位数字或字母
                                 $(msg_tips).show().html('密码必须为6-20位数字或字母');
                                return;
                            }

                            if (t.new_password_input.getValue() != t.confirm_password_input.getValue()) {
                                //输入密码不一致
                                $(msg_tips).show().html('两次输入的密码不一致，请重新输入');
                                return;
                            }

                            t.submit_button.setDisable(false);
                            var employee = Cache.get('pre-check-employee');
                            var accessToken = Cache.get('accessToken');
                            if (accessToken && accessToken.accessToken) {
                                var phone = employee.phone;
                                var password = t.confirm_password_input.getValue();
                                //显示loading
                                tipsAlert.openLoading({
                                    content: '加载中...'
                                });
                                //调用注册接口
                                UserApi.initPassword(accessToken.accessToken, phone, password,
                                    function() {
                                        //关闭loading
                                        tipsAlert.close();
                                        //弹出注册成功的信息
                                        tipsAlert.openToast({
                                            content: tpl("<img src='<#=window.resource.image#>/success-icon.png' class='success-info'> <span class='success-info'>恭喜您，注册成功!</span>",{}),
                                        });
                                        //注册成功,跳转到登录页面
                                        setTimeout("router.navigate('login_pc', {trigger: true});", "2000");
                                    },
                                    function(code, msg) {
                                        //关闭loading
                                        tipsAlert.close();
                                        //弹出异常alert
                                        tipsAlert.openAlert({
                                            content: msg
                                        });
                                    });
                            } else {
                                $(msg_tips).show().html('请填写完整信息');
                            }
                        }

                    }
                });

                 t.submit_button.setDisable(true);
                $('#logo-pc-box-content').addClass('registerPage');
                $(".error-msg-tips").append("<p class='error-icon'></p>");
                $(".registerPage .confirm-password-input").append(tpl("<div class='passwordSuccess'><img src='<#=window.resource.image#>/successbar-icon.png'></div>"));



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
            destroy: function() {
                var t = this;
                //清理定时器
                //t.code_input.clearCountdown();
                //清理缓存
                Cache.set('pre-check-employee', false);
            },
            loadData: function() {
                var t = this;
                var employee = Cache.get('pre-check-employee');
                // if (employee) {
                //     t.xingMing_input.setValue(employee.userName);
                //     t.phone_input.setValue(employee.phone);
                // } else {
                //     tipsAlert.openAlert({
                //         content: '系统异常'
                //     });
                // }
            }
        });
        return LayoutView;
    });