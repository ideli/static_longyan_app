/**
 *reg_employee_step3 view
 *员工注册 步骤3
 **/
define('js/longyan/view/reg_employee_step3', [
        'text!js/longyan/template/reg_employee_step3.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/otp-box',
        'js/element/view/tips-bar',
        'js/api/user',
        'js/api/auth'

    ],
    function(ResetTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, OtpBox, TipsBar, UserApi, AuthApi) {
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
                //t.loadData();
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
                var __isVerify = function() {

                    return true;
                }
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '<span class="bar-activate">员工注册</span>'
                });

                //组装输入新密码输入框
                t.new_password_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'new-password-input',
                    text: '新密码',
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
                    el: $(form_id)
                }, {
                    fieldName: 'confirm-password-input',
                    text: '确认新密码',
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
                    el: $(view_id)
                }, {
                    fieldName: 'submit-button',
                    text: '确认'
                }, {
                    Click: function(e) {
                        if (__isVerify()) {

                            if (!t.new_password_input.isVerify()) {
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
                            var newPass = t.new_password_input.getValue().length;
                            var confirm = t.confirm_password_input.getValue().length;
                            if (newPass<6 || confirm<6 || newPass>20 || confirm>20 ) {
                                 //密码必须为6-20位数字或字母
                                tipsAlert.openAlert({
                                    content: '密码必须为6-20位数字或字母'
                                });
                                return false;
                            }

                            if (t.new_password_input.getValue() != t.confirm_password_input.getValue()) {
                                //输入密码不一致
                                tipsAlert.openAlert({
                                    content: '输入密码不一致'
                                });
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
                                            /*onConfirm: function(e) {
                                                tipsAlert.close();
                                                //注册成功,跳转到登录页面
                                                router.navigate('login', {
                                                    trigger: true
                                                });
                                            }*/
                                        });
                                        //注册成功,跳转到登录页面
                                        setTimeout(t.gotoIndex(phone,password), "2000");
                                        //setTimeout("router.navigate('login', {trigger: true});", "2000");

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
                                tipsAlert.openAlert({
                                    content: '请填写完整信息'
                                });
                            }
                        }

                    }
                });

                // t.submit_button.setDisable(true);
                $('#reset-form').addClass('registerPage');
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
            _isPC: function() {
                var t = this;
                var userAgentInfo = navigator.userAgent;
                var Agents = ["Android", "iPhone",
                    "SymbianOS", "Windows Phone",
                    "iPad", "iPod"
                ];
                var flag = true;
                for (var v = 0; v < Agents.length; v++) {
                    if (userAgentInfo.indexOf(Agents[v]) > 0 || t._isWeixin()) {
                        flag = false;
                        break;
                    }
                }
                return flag;
            },
            _isWeixin: function() {
                var ua = navigator.userAgent.toLowerCase();
                if (ua.match(/MicroMessenger/i) == "micromessenger") {
                    return true;
                } else {
                    return false;
                }
            },
            gotoIndex: function(phone,password) {
                var t = this;
                //验证密码状态
               AuthApi.login({
                    username: phone,
                    password: password
                }, function() {
                    tipsAlert.close();
                    if (t._isPC()) {
                        //PC
                        window.location.href = '/crm/index.action?v=gb&app=CRM';
                    } else {
                        //mobile
                        Cache.set('user-info', false);
                        Cache.set('mall-info', false);
                        Cache.set('role-info', false);
                        Cache.set('yesterday-data', false);
                        Cache.set('today-data', false);

                        window.last_load = 1;
                        router.navigate('index', {trigger: true});
                    }
                }, function(code, msg) {
                    tipsAlert.close();

                    if (code && code == 408) {
                        tipsAlert.openAlert({
                            content: '请求超时'
                        });
                    } else if (code && code == 1000) {
                        tipsAlert.openAlert({
                            content: '系统异常'
                        });
                    } else if (code && code == 1003) {
                        tipsAlert.openAlert({
                            content: '手机号不存在'
                        });
                    } else {
                        tipsAlert.openAlert({
                            content: '手机号或密码错误，请重新填写'
                        });
                    }
                });
            }
        });
        return LayoutView;
    });