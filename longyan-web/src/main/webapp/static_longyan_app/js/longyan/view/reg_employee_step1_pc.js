/**
 *reg_employee_step1 view
 *员工注册 步骤1
 **/
define('js/longyan/view/reg_employee_step1_pc', [
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
                    if (t.code_input.isVerify()) {
                        return true;
                    }
                    return false;
                }
                /*标题*/
                t.tips_bar = new TipsBar({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'title-tips',
                    text: '账户注册'
                });

                /*msg提示框*/
                t.tips_bar = new TipsBar({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'error-msg-tips',
                    text: ''
                });


                //组装身份证输入框
                t.phone_input = new InputBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'idcard-no-input',
                    text: '手机号',
                    type: 'number'
                }, {
                    Keyup: function() {
                        __keyup();
                        if (t.phone_input.getValue().length > 0) {
                            t.code_input.setBtnDisable(false);
                        } else {
                            t.code_input.setBtnDisable(true);
                        }
                    }
                });


                t.code_input = new OtpBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'code-no-input',
                    text: '短信验证码',
                    type: 'text'
                }, {
                    Keyup: function() {
                        __keyup();
                    },

                    BtnClick: function() {
                        //发送验证码逻辑
                        var is_phone = t.phone_input.isPhone();
                        
                        if (is_phone) {
                            tipsAlert.openLoading({
                                content: '加载中...'
                            });

                            //调用发验证码接口
                            UserApi.sendEmployeeRegisterOtp(
                                t.phone_input.getValue(),
                                function() {
                                    tipsAlert.close();
                                    //短信验证码发送成功，开始倒计时
                                    t.code_input.setCountdown(60);
                                },
                                function(code, msg) {
                                    tipsAlert.close();
                                    if(msg == "该用户已注册!"){
                                        tipsAlert.openAlert({
                                            content: msg,
                                            cancelText:'确定',
                                            confirmText: '找回密码',
                                            onConfirm: function(e) {
                                                tipsAlert.close();
                                                router.navigate('reset_pc', {
                                                    trigger: true
                                                });
                                            },
                                        });
                                    } else {
                                        $(msg_tips).show().html(msg);
                                    }
                                });
                        } else {
                            $(msg_tips).show().html('请输入正确的手机号');
                        }
                    }

                });
                //组装确认按钮
                t.submit_button = new ButtonBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'submit-button',
                    text: '下一步'
                }, {
                    Click: function(e) {
                        if (__isVerify()) {
                            UserApi.employeePreCheck(t.phone_input.getValue(), t.code_input.getValue(),function(data) {
                                tipsAlert.close();
                                if (data && data.employee) {
                                    Cache.set('pre-check-employee', data.employee);
                                    Cache.set('accessToken', {accessToken : data.accessToken});
                                    //console.log(data.accessToken);
                                    router.navigate('reg_employee_step2_pc', {
                                        trigger: true
                                    });
                                    t.submit_button.setDisable(false);
                                } else {
                                    $(msg_tips).show().html('无员工数据');
                                }
                            }, function(code, msg) {
                                tipsAlert.close();
                                $(msg_tips).show().html(msg);
                            });
                        } else {
                            $(msg_tips).show().html('请填写完整信息');
                        }
                    }
                });

                $('#login-pc-view .otp-box').addClass('g-hrz').find('input').addClass('u-full');
                t.code_input.setBtnDisable(true);
                t.submit_button.setDisable(true);
                $('#logo-pc-box-content').addClass('registerPage');
                $(".error-msg-tips").append("<p class='error-icon'></p>");


            }
        });
        return LayoutView;
    });