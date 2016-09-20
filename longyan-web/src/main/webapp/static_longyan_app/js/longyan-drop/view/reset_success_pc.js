/**
 *login view
 *登录
 **/
define('js/longyan/view/reset_success_pc', [
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
        // var view_id = '#reset-view';
        // var form_id = '#reset-form';
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
                    if (t.phone_input.isVerify() &&
                        t.code_input.isVerify() &&
                        t.new_password_input.isVerify() &&
                        t.confirm_password_input.isVerify()) {
                        return true;
                    }
                    return false;
                }
                //登录成功信息
                t.tips_bar = new TipsBar({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'success-tips',
                    text: '恭喜您，新密码设置成功！'
                });

                //立即登录
                t.submit_button = new ButtonBox({
                    el: $('#logo-pc-box-content')
                }, {
                    fieldName: 'submit-button',
                    text: '立即登录'
                }, {
                    Click: function(e) {
                        //重置登陆密码成功,跳转到登录页面
                        router.navigate('login_pc', {
                            trigger: true
                        });

                    }
                });
                $('#logo-pc-box-content').addClass('reset_success');
               
            }
            
        });
        return LayoutView;
    });