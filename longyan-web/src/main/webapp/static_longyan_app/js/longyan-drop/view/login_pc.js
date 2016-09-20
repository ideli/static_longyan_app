/**
 *pc login view
 *pc 登录
 **/
define('js/longyan/view/login_pc', [
        'text!js/longyan/template/login_pc.tpl',
        'js/util/memory_cache',
        // 'js/util/hybrid',
        'js/components/alert_ui',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/api/auth'
    ],
    function(LoginTpl, Cache,  AlertUI, InputBox, ButtonBox, LinkBox, TipsBar,AuthApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var msg_tips = '#login-pc-view .tips-bar.error-msg-tips .error-icon';

        var LayoutView = Backbone.View.extend({
            events: {
                // 'tap .reset-link': 'gotoReset'
            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                if ($nvwa.string.getPar('login_pc')) {
                    t.gotoIndex();
                }
                t.render();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#fff');
                t.$el.html(tpl(LoginTpl, {}));
                var __keyup = function() {
                    var username = t.username_input.getValue();
                    var password = t.password_input.getValue();
                    t.login_button.setDisable(false);
                    return true;
                    /*if (username && username.length >= 0 && password && password.length >= 0) {
                        
                        return true;
                    } else {
                        t.login_button.setDisable(true);
                        return false;
                    }*/
                };
                /*标题*/
                t.tips_bar = new TipsBar({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'title-tips',
                    text: '账户登录'
                });

                /*msg提示框*/
                t.tips_bar = new TipsBar({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'error-msg-tips',
                    text: ''
                });

                //组装用户名输入框
                t.username_input = new InputBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'username-input',
                    text: tpl('<img src="<#=window.resource.image#>/pcprot/gray-user-icon2.png" />',{}),
                    type: 'number',
                    placeholder: '手机号'
                }, {
                    Keyup: function() {
                        __keyup();
                        var username = t.username_input.getValue();
                        Cache.set('user-name',{username : username});

                    }
                });

                //组装密码输入框
                t.password_input = new InputBox({
                    el: $('#logo-pc-box-content')
                }, {
                    type: 'password',
                    fieldName: 'password-input',
                    text: tpl('<img src="<#=window.resource.image#>/pcprot/gray-user-icon3.png" />',{}),
                    placeholder: '密码'
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });
                /*自动登录*/
                t.tips_bar = new TipsBar({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'auto-login-tips',
                    text: '自动登录'
                });

                //组装忘记密码
                t.register_button = new LinkBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'reg-link',
                    text: '立即注册'
                /*}, {
                    Click: function(e) {
                        router.navigate('reg_employee_step1_pc', {
                            trigger: true
                        });
                    }*/
                });


                //组装登陆按钮
                t.login_button = new ButtonBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'login-button',
                    text: '登录'
                }, {
                    Click: function(e) {
                        if (__keyup()) {
                            var username = t.username_input.getValue();
                            var password = t.password_input.getValue();

                            if (username == "") {
                               $(msg_tips).show().html('请输入手机号');
                                return true;
                            } else if (!/^[1][34578][0-9]{9}$/i.test(username)) {
                               $(msg_tips).show().html('手机号格式不正确');
                                return true;
                            }
                        if (password == "") {
                               $(msg_tips).show().html('请输入密码');
                                return true;
                            } 
                            tipsAlert.openLoading({
                                content: '加载中...'
                            });
                            AuthApi.login({
                                username: username,
                                password: password
                            }, function() {
                                tipsAlert.close();
                                if (t._isPC()) {
                                    //PC
                                    window.location.href = '/longyan_pc.jsp#main';
                                    //window.location.href = '/crm/index.action?v=gb&app=CRM';
                                } else {
                                    //mobile
                                    Cache.set('user-info', false); 
                                    window.last_load = 1;
                                    t.gotoIndex();
                                }
                            }, function(code, msg) {
                                tipsAlert.close();

                                if (code && code == 408) {
                                    $(msg_tips).show().html('请求超时');
                                } else if (code && code == 1000) {
                                    $(msg_tips).show().html('系统异常');
                                } else if (code && code == 1003) {
                                    $(msg_tips).show().html('手机号不存在');
                                } else {
                                    $(msg_tips).show().html('手机号或密码错误，请重新填写');
                                }
                            });
                        }
                    }
                });

                //组装忘记密码
                t.link_button = new LinkBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'reset-link',
                    text: '忘记密码?'
                });

                //设置按钮不可用
                t.login_button.setDisable(true);
                $(".error-msg-tips").append("<p class='error-icon'></p>");

                $('#logo-pc-box-content').addClass('loginPage');

                //input框输入时，label部分变色
                $('#logo-pc-box-content .input-box').focusin(function(){
                    var index =  $(this).index();
                    var tplstr = tpl('<#=window.resource.image#>/pcprot/blue-user-icon'+index+'.png',{});
                    var login_icon = $(this).find('.label').children('img').attr('src',tplstr);
                });
                $('#login-pc-view .input-box').focusout(function(){
                    var index =  $(this).index();
                    var tplstr = tpl('<#=window.resource.image#>/pcprot/gray-user-icon'+index+'.png',{});
                    var login_icon = $(this).find('.label').children('img').attr('src',tplstr);
                });
    
                //组装忘记密码点击跳转
                t.$el.find('.reset-link').on('click', function() {
                    var username = t.username_input.getValue();
                    if(username.length > 0){
                        if (!/^[1][34578][0-9]{9}$/i.test(username)) {
                               $(msg_tips).show().html('手机号格式不正确');
                            return true;
                        }
                    }
                    //Cache.set('user-name',{username : username});
                    t.gotoReset();
                });
                //组装立即注册点击跳转
                t.$el.find('.reg-link').on('click', function() {
                    t.gotoRegist();
                });

                //登录成功一次，下次打开软件或者退出登录的情况下，手机号应该默认存在
                var username = t.username_input.getValue();
                if(username == ""){
                    var t = this;
                    var username = Cache.get('user-name');
                    t.username_input.setValue(username.username);
                    return true;
                }

            },
            gotoReset: function() {
                // alert('123');
                router.navigate('reset_pc', {
                    trigger: true
                });
            },
            gotoRegist: function() {
                // alert('123');
                router.navigate('reg_employee_step1_pc', {
                    trigger: true
                });
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
            gotoIndex: function() {
                var local = window.location;
                console.log(local);
                router.navigate('index', {
                    trigger: true
                });
            }
        });
        return LayoutView;
    });