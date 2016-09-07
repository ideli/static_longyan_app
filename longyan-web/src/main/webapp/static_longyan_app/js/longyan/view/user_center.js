/**
 *home view
 *首页
 **/
define('js/longyan/view/user_center', [
        'text!js/longyan/template/user_center.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/api/user'
    ],
    function(UserCenterTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, UserApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#user-center-view';
        var form_id = '#user-center-form';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                t.loadData();
                t.initEvents();
                // Header.initialize();                
            },
            render: function() {
                var t = this;
                //document.cookie = "_token=";
                $('body').css('background-color', '#fff');
                t.$el.html(tpl(UserCenterTpl, {}));

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '我的',
                    goBackUrl: function() {
                        router.navigate('index', {
                            trigger: true
                        });
                    }
                });
                t.community_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-input',
                    text: tpl('<img src="<#=window.resource.image#>/icon/wo_community_34x32@2x.png"/>我的小区', {}),
                    label_right: '<i class="icon iconfont">&#xe608;</i>'
                }, {
                    Click: function(e) {
                        //返回小区列表页
                        router.navigate('community_list', {
                            trigger: true 
                        });  
                    }

                });

                t.integral_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'integral-input',
                    text: tpl('<img src="<#=window.resource.image#>/icon/wo_credit_36x36@2x.png"/>我的积分', {}),
                    label_right: '<i class="icon iconfont">&#xe608;</i>'

               }, {
                    Click: function(e) {
                        //返回我的积分页
                        router.navigate('integral_explain_list', {
                            trigger: true
                        });                   
                    }
                });
                 $('<div class="gap basic-gap owner-gap"></div>').appendTo($(form_id));
                 t.feedback_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'feedback-input',
                     text: tpl('<img src="<#=window.resource.image#>/icon/wo_vomit_32x32@2x.png"/>吐槽', {}),
                   label_right: '<i class="icon iconfont">&#xe608;</i>'

                }, {
                    Click: function(e) {
                        //返回意见反馈页
                        router.navigate('feedback', {
                            trigger: true
                        });  
                    }
                });
                t.help_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'help-input',
                    text: tpl('<img src="<#=window.resource.image#>/icon/wo_QA_34x36@2x.png"/>常见问题', {}),
                    label_right: '<i class="icon iconfont">&#xe608;</i>'

                }, {
                    Click: function(e) {
                        //返回常见问题页
                        router.navigate('help_list', {
                            trigger: true
                        });  
                    }
                });

                //设置只读属性
                t.community_input.setReadOnly(true);
                t.integral_input.setReadOnly(true);
                t.feedback_input.setReadOnly(true);
                t.help_input.setReadOnly(true);
                /*t.$el.find('.modify-input').find('.label-right').html('<i class="icon iconfont icon-lylrightarrow"></i>');

                t.$el.find('.modify-input').on('click', function() {
                    t.gotoChangePassword();
                });*/
                 t.$el.find('.community-input').on('click', function() {
                    //去到我的小区页
                    router.navigate('community_list', {
                        trigger: true
                    });  

                });
                 t.$el.find('.integral-input').on('click', function() {
                    //去到我的积分页
                    router.navigate('integral_explain_list', {
                        trigger: true
                    });  

                });
                t.$el.find('.feedback-input').on('click', function() {
                    //去到吐槽页
                    router.navigate('feedback', {
                        trigger: true
                    });  

                });
                t.$el.find('.help-input').on('click', function() {
                    //返回常见问题页
                    router.navigate('help_list', {
                        trigger: true
                    });  

                });
                t.$el.find('.avatar-user').on('click', function() {
                    //返回个人中心页
                    router.navigate('user_info', {
                        trigger: true
                    });  

                });
            },
            loadData: function() {
                var t = this;
                if (window.last_load && window.last_load + 1000 * 1800 > Date.parse(new Date())) {
                    //不刷新缓存
                    var userInfo = Cache.get('user-info');
                    var mallInfo = Cache.get('mall-info');
                    var yesterday_data = Cache.get('yesterday-data');
                    //var roleInfo = Cache.get('role-info')[0];
                    if (userInfo) {
                        t.$el.find('.avatar-user .name-label').html(userInfo.xingMing);
                        if (userInfo.head) {
                            t.$el.find('.avatar-user .avatar-image').attr('src', window.resource.upload + '/' + userInfo.head);
                        }

                        if (userInfo.businessUnitName != null && userInfo.description == null) {
                            t.$el.find('.avatar-user .role-label').html(userInfo.businessUnitName);
                        } else if (userInfo.description != null && userInfo.businessUnitName == null) {
                            t.$el.find('.avatar-user .role-label').html(userInfo.description);
                        } else if (userInfo.description != null && userInfo.businessUnitName != null) {
                            t.$el.find('.avatar-user .role-label').html(userInfo.businessUnitName + '&nbsp;&nbsp;' + data.userInfo.description);
                        } else {
                            t.$el.find('.avatar-user .role-label').html("");
                        }

                    }
                    if (yesterday_data) {
                        _load_dashboard(yesterday_data);
                    }
                    // if (roleInfo && roleInfo.name) {
                    //     t.$el.find('.avatar-user .role-label').html(roleInfo.name);
                    // }
                } else {
                    //刷新缓存
                    tipsAlert.openLoading({
                        content: '加载中...'
                    });
                    UserApi.getUserInfo(function(data) {
                        console.log(data);
                        tipsAlert.close();
                        //success                        
                        //填充数据
                        if (data && data.userInfo) {
                            t.$el.find('.avatar-user .name-label').html(data.userInfo.xingMing);
                            if (data.userInfo.businessUnitName != null && data.userInfo.description == null) {
                                t.$el.find('.avatar-user .role-label').html(data.userInfo.businessUnitName);
                            } else if (data.userInfo.description != null && data.userInfo.businessUnitName == null) {
                                t.$el.find('.avatar-user .role-label').html(data.userInfo.description);
                            } else if (data.userInfo.description != null && data.userInfo.businessUnitName != null) {
                                t.$el.find('.avatar-user .role-label').html(data.userInfo.businessUnitName + '&nbsp;&nbsp;' + data.userInfo.description);
                            } else {
                                t.$el.find('.avatar-user .role-label').html("");

                            }
                            if (data && data.userInfo && data.userInfo.head) {
                                t.$el.find('.avatar-user .avatar-image').attr('src', window.resource.upload + '/' + data.userInfo.head);
                            }
                            var timestamp = Date.parse(new Date());
                            window.last_load = timestamp;
                            Cache.set('user-info', data.userInfo);
                            Cache.set('mall-info', data.shoppingMalls);
                        }

                    }, function(code, msg) {
                        tipsAlert.close();
                        //error
                        tipsAlert.openAlert({
                            content: msg
                        });
                    });
                }
          },
            initEvents: function() {
                var t = this;
            }


        });
        return LayoutView;
    });