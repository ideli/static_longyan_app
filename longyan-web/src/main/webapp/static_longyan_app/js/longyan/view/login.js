/**
 *login view
 *登录
 **/
define('js/longyan/view/login', [
        'text!js/longyan/template/login.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/util/aes',
        'js/api/auth'
    ],
    function(LoginTpl, Cache, AlertUI, InputBox, ButtonBox, LinkBox, Aes, AuthApi) {
        var tipsAlert = tipsAlert || new AlertUI();

        var LayoutView = Backbone.View.extend({
            events: {
                // 'tap .reset-link': 'gotoReset'
            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                if ($nvwa.string.getPar('login')) {
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
                //组装用户名输入框
                t.username_input = new InputBox({
                    el: $('#login-view')
                }, {
                    fieldName: 'username-input',
                    text: tpl('<img src="<#=window.resource.image#>/phone-icon.png" />', {}),
                    //type: 'number',
                    placeholder: '手机号'
                }, {
                    Keyup: function() {
                        __keyup();
                        var username = t.username_input.getValue();
                        Cache.set('user-name', {
                            username: username
                        });

                    }
                });
                //组装密码输入框
                t.password_input = new InputBox({
                    el: $('#login-view')
                }, {
                    type: 'password',
                    fieldName: 'password-input',
                    text: tpl('<img  src="<#=window.resource.image#>/password-icon.png" />', {}),
                    placeholder: '密码'
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });
                //组装登陆按钮
                t.login_button = new ButtonBox({
                    el: $('#login-view')
                }, {
                    fieldName: 'login-button',
                    text: '登录'
                }, {
                    Click: function(e) {
                        if (__keyup()) {
                            var username = t.username_input.getValue();
                            var password = t.password_input.getValue();
                            var password = t.Encrypt(password);//加密
                            if (username == "") {
                                tipsAlert.openAlert({
                                    content: '请输入手机号'
                                });
                                return true;
                            } /*else if (!/^[1][34578][0-9]{9}$/i.test(username)) {
                                tipsAlert.openAlert({
                                    content: '手机号格式不正确'
                                });
                                return true;
                            }*/
                            if (password == "") {
                                tipsAlert.openAlert({
                                    content: '请输入密码'
                                });
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
                                    window.location.href = '/crm/index.action?v=gb&app=CRM';
                                } else {
                                    //mobile
                                    Cache.set('user-info', false);
                                    Cache.set('mall-info', false);
                                    Cache.set('role-info', false);
                                    Cache.set('yesterday-data', false);
                                    Cache.set('today-data', false);

                                    window.last_load = 1;
                                    t.gotoIndex();
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
                    }
                });

                t.register_button = new LinkBox({
                    el: $('#login-view')
                }, {
                    fieldName: 'reg-link',
                    text: '注册龙眼'
                }, {
                    Click: function(e) {
                        router.navigate('reg_employee_step1', {
                            trigger: true
                        });
                    }
                });
                t.$el.find('.reg-link').on('click', function() {
                    router.navigate('reg_employee_step1', {
                        trigger: true
                    });
                });


                //组装忘记密码
                t.link_button = new LinkBox({
                    el: $('#login-view')
                }, {
                    fieldName: 'reset-link',
                    text: '忘记密码?'
                });
                t.login_button.setDisable(true);
                t.$el.find('.reset-link').on('click', function() {
                    var username = t.username_input.getValue();
                    /*if (username.length > 0) {
                        if (!/^[1][34578][0-9]{9}$/i.test(username)) {
                            tipsAlert.openAlert({
                                content: '手机号格式不正确'
                            });
                            return true;
                        }
                    }*/
                    //Cache.set('user-name',{username : username});
                    t.gotoReset();
                });
                t.loadData();


            },
            loadData: function() {
                var t = this;
                //登录成功一次，下次打开软件或者退出登录的情况下，手机号应该默认存在
                var username = t.username_input.getValue();
                if (username == "") {
                    var t = this;
                    var username = Cache.get('user-name');
                    t.username_input.setValue(username.username);
                    return true;
                }
            },
            gotoReset: function() {
                // alert('123');
                router.navigate('reset', {
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
                router.navigate('index', {
                    trigger: true
                });
            },
            Encrypt:function(password){  
                 var key = CryptoJS.enc.Utf8.parse("wZTc98PWEMqqCSCs");   
                 var iv  = CryptoJS.enc.Utf8.parse('0102030405060708');   
                 var srcs = CryptoJS.enc.Utf8.parse(password);  
                 var encrypted = CryptoJS.AES.encrypt(srcs, key, { iv: iv,mode:CryptoJS.mode.CBC});  
                 return encrypted.toString();  
            }  
        });
        return LayoutView;
    });