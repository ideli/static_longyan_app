/**
 *reg_employee_step1 view
 *员工注册 步骤1
 **/
define('js/longyan/view/reg_employee_step2', [
        'text!js/longyan/template/reg_employee_step2.tpl',
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
                t.loadData();
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
                    if (t.phone_input.isVerify()) {
                        return true;
                    }
                    return false;
                }
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '<span class="bar-activate">员工注册</span>'
                });


                //组装员工ID输入框
                t.employee_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'employee-input',
                    text: '员工ID',
                    readonly: true
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });

                //组装姓名输入框
                t.xingMing_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'xingMing-input',
                    text: '姓名',
                    readonly: true
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });

                //组装性别输入框
                t.gender_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'gender-input',
                    text: '性别',
                    readonly: true
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });

                //组装手机号输入框
                t.phone_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'phone-input',
                    text: '手机号',
                    readonly: true
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });
                //组装单位输入框
                t.businessUnitName_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'businessUnitName-input',
                    text: '单位',
                    readonly: true
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });
                //组装部门输入框
                t.department_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'department-input',
                    text: '部门描述',
                    readonly: true
                }, {
                    Keyup: function() {
                        __keyup();
                    }
                });

                //组装确认按钮
                t.submit_button = new ButtonBox({
                    el: $(view_id)
                }, {
                    fieldName: 'submit-button',
                    text: '下一步'
                }, {
                    Click: function(e) {
                            var employee = Cache.get('pre-check-employee');
                            if (t.phone_input.isVerify()) {
                                employee.phone = t.phone_input.getValue();
                                Cache.set('pre-check-employee', employee);
                            }
                            var accessToken = Cache.get('accessToken').accessToken;
                            Cache.set('accessToken', {accessToken : accessToken});
                            router.navigate('reg_employee_step3', {
                                trigger: true
                            });
                    }
                });

                // t.submit_button.setDisable(true);
                $('#reset-form').addClass('registerPage');

            },
            loadData: function() {
                var t = this;
                var employee = Cache.get('pre-check-employee');
                var accessToken = Cache.get('accessToken').accessToken;
                if (accessToken == "") {
                    var accessToken = accessToken.accessToken;
                }
                if (employee) {
                    t.employee_input.setValue(employee.openId);
                    t.xingMing_input.setValue(employee.userName);
                    if (employee.gender && (employee.gender == 'male' || employee.gender == '男')) {
                        t.gender_input.setValue('男');
                    } else {
                        t.gender_input.setValue('女');
                    }
                    t.phone_input.setValue(employee.mobilePhone);
                    t.businessUnitName_input.setValue(employee.businessUnitName);
                    t.department_input.setValue(employee.departmentCode);
                } else {
                    tipsAlert.openAlert({
                        content: '系统异常'
                    });
                }
            }
        });
        return LayoutView;
    });