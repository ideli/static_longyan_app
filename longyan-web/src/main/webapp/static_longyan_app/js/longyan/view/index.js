/**
 *home view
 *首页
 **/
define('js/longyan/view/index', [
        'text!js/longyan/template/index.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/api/user',
        'js/api/message',
        'js/element/view/tool-bar-box'
    ],
    function(IndexTpl, Cache, AlertUI, UserApi, MessageApi, ToolBarBox) {
        var tipsAlert = tipsAlert || new AlertUI();

        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {

                var t = this;
                t.$el.off('click');
                t.render();
                t.$el.find('.communityManager').hide();
                // Header.initialize();        
                // var data = Cache.get('user-info');
                // if (!data) {
                //     t.loadData();
                // }

                t.loadData();
                t.initEvents();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(IndexTpl, {}));
                t.$el.find('.avatar-image').on('click', function() {
                    t.gotoUserInfo();
                });
                $('#index-view').css('height', screen.height * 2 + 'px');

                // //TEST CASE  底部菜单测试 start
                // var tool_bar = new ToolBarBox({
                //     el: $('#index-view') //组件注入的div或dom节点
                // }, {
                //     fieldName: 'tool_bar', //组件对应的字段名称(这个定义要求一个view上是全局唯一)
                //     item: [{
                //         text: '首页', //元素上的文字
                //         icon_class: 'icon-home', //元素上的图标
                //         width: '25%' //元素所占的宽度
                //     }, {
                //         text: '报表',
                //         icon_class: 'icon-report',
                //         width: '25%'
                //     }, {
                //         text: '小区',
                //         icon_class: 'icon-community',
                //         width: '25%'
                //     }, {
                //         text: '住宅',
                //         icon_class: 'icon-house',
                //         width: '25%'
                //     }]
                // }, {
                //     Click: function(e, index) {
                //         //点击按钮触发的事件                        
                //         //e:jquery事件对象
                //         //index:触发点击的index,左边第一个元素从0开始
                //     }
                // });
                // tool_bar.setIndex(1);
                // tool_bar.show(false);
                // //TEST CASE  底部菜单测试  end

            },
            initEvents: function() {
                var t = this;
                t.$el.find('.communityManager').on('click', function(e) {
                    t.gotoCommunityList();
                });
                t.$el.find('.memberManager').on('click', function(e) {
                    t.gotoMemberList();
                });
                t.$el.find('.reportManager').on('click', function(e) {
                    t.gotoReport();
                });
                t.$el.find('.aboutus').on('click', function(e) {
                    t.gotoAboutUs();
                });
                t.$el.find('.card-box').on('touchstart', function(e) {
                    var target = $(e.currentTarget);
                    t.$el.find('.card-box').css('background-color', '#fff');
                    target.css('background-color', '#f5f5f5');
                });
                t.$el.find('.card-box').on('touchend', function(e) {
                    t.$el.find('.card-box').css('background-color', '#fff');
                });
            },
            //小区管理
            gotoCommunityList: function() {
                router.navigate('community_list', {
                    trigger: true
                });
            },
            //住户管理
            gotoMemberList: function() {
                router.navigate('member_list', {
                    trigger: true
                });
            },
            //任务管理
            gotoTaskList: function() {
                router.navigate('report_shopping_mall_by_id/' + malls[0].id, {
                    trigger: true
                });
            },
            //排行
            gotoRankList: function() {
                router.navigate('employee_input_rank_list', {
                    trigger: true
                });
            },
            //报表
            gotoReport: function() {
                router.navigate('report_corporation', {
                    trigger: true
                });
            },
            //关于
            gotoAboutUs: function() {
                router.navigate('aboutus', {
                    trigger: true
                });
            },
            //加载数据
            loadData: function() {
                var t = this;

                var _load_dashboard = function(data) {
                    if (data) {
                        if (data.activeUserCount) {
                            //昨日访客
                            t.$el.find('.dashboard-box').find('.new-guest').find('.count').html(data.activeUserCount);
                        }
                        if (data.communityInputAmount) {
                            //昨日新增小区
                            t.$el.find('.dashboard-box').find('.new-community').find('.count').html(data.communityInputAmount);
                        }
                        if (data.memberInputAmount) {
                            //昨日新增住宅
                            t.$el.find('.dashboard-box').find('.new-member').find('.count').html(data.memberInputAmount);
                        }
                    }
                };
                if (window.last_load && window.last_load + 1000 * 1800 > Date.parse(new Date())) {
                    //不刷新缓存
                    var userInfo = Cache.get('user-info');
                    var mallInfo = Cache.get('mall-info');
                    var yesterday_data = Cache.get('yesterday-data');
                    //var roleInfo = Cache.get('role-info')[0];
                    if (userInfo) {
                        t.$el.find('.avatar-box .name-label').html(userInfo.xingMing);
                        if (userInfo.head) {
                            t.$el.find('.avatar-box .avatar-image').attr('src', window.resource.upload + '/' + userInfo.head);
                        }

                        if (userInfo.businessUnitName != null && userInfo.description == null) {
                            t.$el.find('.avatar-box .role-label').html(userInfo.businessUnitName);
                        } else if (userInfo.description != null && userInfo.businessUnitName == null) {
                            t.$el.find('.avatar-box .role-label').html(userInfo.description);
                        } else if (userInfo.description != null && userInfo.businessUnitName != null) {
                            t.$el.find('.avatar-box .role-label').html(userInfo.businessUnitName + '&nbsp;&nbsp;' + data.userInfo.description);
                        } else {
                            t.$el.find('.avatar-box .role-label').html("");
                        }
                        //注册激光推送
                        t.listenMessage(userInfo);

                    }
                    if (yesterday_data) {
                        _load_dashboard(yesterday_data);
                    }
                    // if (roleInfo && roleInfo.name) {
                    //     t.$el.find('.avatar-box .role-label').html(roleInfo.name);
                    // }
                    t.setMenu();
                } else {
                    //刷新缓存
                    tipsAlert.openLoading({
                        content: '加载中...'
                    });
                    UserApi.getUserInfo(function(data) {
                        tipsAlert.close();
                        //success                        
                        //填充数据
                        if (data && data.userInfo) {
                            t.$el.find('.avatar-box .name-label').html(data.userInfo.xingMing);
                            if (data.userInfo.businessUnitName != null && data.userInfo.description == null) {
                                t.$el.find('.avatar-box .role-label').html(data.userInfo.businessUnitName);
                            } else if (data.userInfo.description != null && data.userInfo.businessUnitName == null) {
                                t.$el.find('.avatar-box .role-label').html(data.userInfo.description);
                            } else if (data.userInfo.description != null && data.userInfo.businessUnitName != null) {
                                t.$el.find('.avatar-box .role-label').html(data.userInfo.businessUnitName + '&nbsp;&nbsp;' + data.userInfo.description);
                            } else {
                                t.$el.find('.avatar-box .role-label').html("");

                            }
                            if (data && data.userInfo && data.userInfo.head) {
                                t.$el.find('.avatar-box .avatar-image').attr('src', window.resource.upload + '/' + data.userInfo.head);
                            }
                            var timestamp = Date.parse(new Date());
                            window.last_load = timestamp;
                            Cache.set('user-info', data.userInfo);
                            Cache.set('mall-info', data.shoppingMalls);
                            // t.$el.find('.avatar-box .avatar-image').attr('src', window.resource.upload + '/' + data.avatar);

                            //注册激光推送
                            t.listenMessage(data.userInfo);

                        }

                        if (data && data.roleInfo) {
                            Cache.set('role-info', data.roleInfo);
                        }

                        if (data && data.yesterday) {
                            Cache.set('yesterday-data', data.yesterday);
                            //设置内容
                            _load_dashboard(data.yesterday);
                        }
                        if (data && data.today) {
                            Cache.set('today-data', data.today);
                        }

                        t.setMenu();
                    }, function(code, msg) {
                        tipsAlert.close();
                        //error
                        tipsAlert.openAlert({
                            content: msg
                        });
                    });
                }
            },
            listenMessage: function(userInfo) {
                var t = this;
                //消息推送注册
                if (userInfo.employeeCode && window._isNative) {
                    // tipsAlert.openAlert({
                    //     content: 'code=' + userInfo.employeeCode
                    // });                    
                    $nvwa.app.request('transfer', {
                        "userID": userInfo.employeeCode
                    }, function(data) {
                        // tipsAlert.openAlert({
                        //     content: 'data=' + data
                        // });
                        if (data && data.token) {

                            // tipsAlert.openAlert({
                            //     content: 'success=' + data.token
                            // });

                            var postData = {};
                            if (_isIOS()) {
                                //ios
                                postData.platformType = 'ios';
                            } else {
                                //android
                                postData.platformType = 'android';
                            }
                            postData.tag = data.token;
                            MessageApi.appRegister(postData, function(dataMap) {

                                //注册成功   
                            }, function(code, msg) {
                                //龙眼注册失败
                            });
                        }
                    });
                }
            },
            gotoUserInfo: function() {
                router.navigate('user_info', {
                    trigger: true
                });
            },
            setMenu: function() {
                var t = this;
                var roleInfo = Cache.get('role-info');
                if (roleInfo && roleInfo.length > 0) {
                    $.each(roleInfo, function(i, item) {
                        if (item && item.alias && item.alias == 'role_mall_employee') {
                            //商场员工角色
                            t.$el.find('.communityManager').show();
                        }
                    });
                }
                // t.$el.find('.communityManager').show();
            }

        });
        return LayoutView;
    });