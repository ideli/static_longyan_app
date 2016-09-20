/**
 *reg_employee_step1 view
 *员工注册 步骤1
 **/
define('js/longyan/view/reg_employee_step1', [
        'text!js/longyan/template/reg_employee_step1.tpl',
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
                    if (t.code_input.isVerify()) {
                        return true;
                    }
                    return false;
                }
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '<span class="bar-activate">员工注册</span>',
                    goBackUrl: function() {
                        router.navigate('login', {
                            trigger: true
                        });
                    }
                });


                //组装身份证输入框
                t.phone_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'idcard-no-input',
                    text: '手机号',
                    type: 'number'
                    //placeholder: '请输入手机号码'
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });


                t.code_input = new OtpBox({
                    el: $(form_id)
                }, {
                    fieldName: 'code-no-input',
                    text: '验证码',
                    type: ''
                    //placeholder: '请输入短信验证码'
                }, {
                    Keyup: function() {
                        __keyup();
                    },

                    BtnClick: function() {
                        //发送验证码逻辑
                        var is_phone = t.phone_input.isPhone();

                        tipsAlert.openLoading({
                                content: '加载中...'
                            });
                        
                        if (is_phone) {
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
                                                router.navigate('reset', {
                                                    trigger: true
                                                });
                                            },
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

                t.tips_bar = new TipsBar({
                    el: $(view_id)
                }, {
                    fieldName: 'phone-tips',
                    text: '遇到问题？  服务电话 <a class="telPhone" href="javascript:void(0)"  style="color:#323232;">400 688 9333<a>'
                });

                //组装确认按钮
                t.submit_button = new ButtonBox({
                    el: $(view_id)
                }, {
                    fieldName: 'submit-button',
                    text: '下一步'
                }, {
                    Click: function(e) {
                        if (__isVerify()) {
                             tipsAlert.openLoading({
                                content: '加载中...'
                            });
                            UserApi.employeePreCheck(t.phone_input.getValue(), t.code_input.getValue(),function(data) {
                                tipsAlert.close();
                                if (data && data.employee) {
                                    Cache.set('pre-check-employee', data.employee);
                                    Cache.set('accessToken', {accessToken : data.accessToken});
                                    //console.log(data.accessToken);
                                    router.navigate('reg_employee_step2', {
                                        trigger: true
                                    });
                                    t.submit_button.setDisable(false);
                                } else {
                                    tipsAlert.openAlert({
                                        content: '无员工数据'
                                    });
                                }
                            }, function(code, msg) {
                                tipsAlert.close();
                                tipsAlert.openAlert({
                                    content: msg
                                });
                            });
                        } else {
                            tipsAlert.openAlert({
                                content: '请填写完整信息'
                            });
                        }
                    }
                });

                t.submit_button.setDisable(true);
                $('#reset-form').addClass('registerPage');

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