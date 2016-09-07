/**
 *home view
 *首页
 **/
define('js/longyan/view/user_info', [
        'text!js/longyan/template/user_info.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/api/auth'
    ],
    function(UserInfoTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, AuthApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#user-info-view';
        var form_id = '#user-info-form';
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
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(UserInfoTpl, {}));

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '个人信息',
                    goBackUrl: function() {
                        router.navigate('user_center', {
                            trigger: true
                        });
                    }
                });

                t.employee_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'employee-input',
                    text: '员工ID'
                }, {
                    Keyup: function() {

                    }
                });

                t.name_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'name-input',
                    text: '姓名'
                }, {
                    Keyup: function() {

                    }
                });
                t.sex_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'sex-input',
                    text: '性别'
                }, {
                    Keyup: function() {

                    }
                });
                t.market_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'market-input',
                    text: '公司'
                }, {
                    Keyup: function() {

                    }
                });
                t.dept_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'dept-input',
                    text: '部门'
                }, {
                    Keyup: function() {

                    }
                });
                t.modify_input = new InputBox({
                    el: $('#user-info-view')
                }, {
                    fieldName: 'modify-input',
                    text: '修改密码'
                });

                //退出登录按钮
                t.submit_button = new ButtonBox({
                    el: $(view_id)
                }, {
                    fieldName: 'logout-button',
                    text: '退出登录'
                }, {
                    Click: function(e) {
                        tipsAlert.open({
                            cancelText: '否',
                            confirmText: '是',
                            content: '是否退出登录',
                            onConfirm: function(e) {
                                tipsAlert.close();
                                //退出登录
                                tipsAlert.openLoading({
                                    content: '加载中...'
                                });
                                AuthApi.logout(function() {
                                    tipsAlert.close();
                                    //清理缓存
                                    // Cache.clear();
                                    Cache.set('community-tmp-create-object', false);
                                    Cache.set('member-manager-object', false);
                                    Cache.set('community-manager-object', false);
                                    Cache.set('desc-Info', false);
                                    Cache.set('feedback-index', false);
                                    Cache.set('html', false);
                                    Cache.set('title', false);
                                    Cache.set('user-info', false);
                                    Cache.set('mall-info', false);
                                    Cache.set('role-info', false);
                                    Cache.set('yesterday-data', false);
                                    Cache.set('today-data', false);
                                    Cache.set('pre-check-employee', false);
                                    Cache.set('accessToken', false);

                                    document.cookie = "_token=";
                                    //返回登陆页
                                    router.navigate('login', {
                                        trigger: true
                                    });
                                }, function(code, msg) {
                                    tipsAlert.close();
                                    tipsAlert.openAlert({
                                        content: msg
                                    });
                                });
                            },
                            onCancel: function(e) {
                                tipsAlert.close();
                            }
                        });
                    }
                });

                //设置只读属性
                t.employee_input.setReadOnly(true);
                t.name_input.setReadOnly(true);
                t.sex_input.setReadOnly(true);
                t.market_input.setReadOnly(true);
                t.dept_input.setReadOnly(true);
                t.modify_input.setReadOnly(true);
                t.$el.find('.modify-input').find('.label-right').html('<i class="icon iconfont icon-lylrightarrow"></i>');

                t.$el.find('.modify-input').on('click', function() {
                    t.gotoChangePassword();
                });
            },
            loadData: function() {
                var t = this;
                var data = Cache.get('user-info');
                //console.log(data);
                if (data) {
                    t.employee_input.setValue(data.employeeCode);
                    t.name_input.setValue(data.xingMing);
                    var gender = data.gender;
                    if (gender && (gender == 'male' || gender == '男')) {
                        t.sex_input.setValue('男');
                    } else {
                        t.sex_input.setValue('女');
                    }
                    t.market_input.setValue(data.businessUnitName);
                    t.dept_input.setValue(data.description);
                    if (data.head) {
                        $('#user-info-view .avatar-image').attr('src', window.resource.upload + '/' + data.head);
                    }
                }

            },
            gotoChangePassword: function() {
                var t = this;
                router.navigate('change_password', {
                    trigger: true
                });
            },
            initEvents: function() {
                var t = this;
                $('#user-info-view .avatar-image').on('click', function() {
                    $('#fileupload').fileupload({
                        dataType: 'json',
                        url: _nvwaAPI + '/longyan/user/setting-avatar',
                        progressall: function(e, data) {
                            var progress = parseInt(data.loaded / data.total * 100, 10);
                            $('#progress .bar').css(
                                'width',
                                progress + '%'
                            );
                        },
                        done: function(e, data) {
                            if (data && data.result && data.result.dataMap && data.result.dataMap.attachment && data.result.dataMap.attachment.length > 2) {
                                var imgUrl = $nvwa.dictionary.storage_url + '/' + eval('(' + data.result.dataMap.attachment + ')').storeName
                                $('.src_pic img').attr('src', imgUrl);
                                $('.result img').attr('src', imgUrl);

                                t.img = imgUrl;


                                var imgtemp = new Image(); //创建一个image对象
                                imgtemp.src = imgUrl;
                                imgtemp.onload = function() { //图片加载完成后执行
                                    var realWidth = this.width;
                                    var width = $('#target').width();
                                    t.ratio = realWidth / width;
                                }

                                t.iniJcropPic();
                            } else {
                                tipsAlert.openAlert({
                                    content: data.result.message
                                });
                            }
                        }
                    });
                });
            }


        });
        return LayoutView;
    });