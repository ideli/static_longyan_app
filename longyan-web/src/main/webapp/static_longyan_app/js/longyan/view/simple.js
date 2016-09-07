/**
 *simple view
 *登录
 **/
define('js/longyan/view/simple', [
        'text!js/longyan/template/simple.tpl',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/radio-box',
        'js/element/view/room-box',
        'js/element/view/select-box',
        'js/element/view/think-input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/pie-chart-box',
        'js/element/view/line-chart-box',
        'js/element/view/location-box',
        'js/element/view/employee-input-record-box'
    ],
    function(SimpleTpl, HeaderView, InputBox, GenderBox, RoomBox, SelectBox, ThinkBox, ButtonBox, LinkBox, PieBox, LineBox, LocationBox, EmployeeRecordBox) {
        var view_id = '#simple-view';
        var form_id = '#simple-form';
        var LayoutView = Backbone.View.extend({
            events: {
                // 'tap .reset-link': 'gotoReset'
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
                t.$el.html(tpl(SimpleTpl, {}));

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: 'Simple Page'
                });

                // t.username_input = new RoomBox({
                //     el: $(form_id)
                // }, {
                //     fieldName: 'room-input',
                //     text: '房号'
                // }, {

                // });

                // t.username_input = new ThinkBox({
                //     el: $(form_id)
                // }, {
                //     fieldName: 'room-input',
                //     text: '小区',
                //     placeholder: '请填写小区名称'
                // }, {

                // });



                t.username_input = new EmployeeRecordBox({
                    el: $(form_id)
                }, {
                    fieldName: 'room-input',
                    text: '录入详情',
                    data: {
                        left: {
                            label: 243,
                            text: '已录入住户'
                        },
                        right: {
                            label: 2,
                            text: '已录入小区'
                        }
                    }
                }, {

                });

                // t.username_input = new LocationBox({
                //     el: $(form_id)
                // }, {
                //     fieldName: 'gender-input',
                //     text: '性别'
                // }, {

                // });

                t.register_button = new ButtonBox({
                    el: $(view_id)
                }, {
                    fieldName: 'reg-button',
                    text: '提交'
                }, {
                    Click: function(e) {
                        // var v = t.username_input.getValue();
                        // console.log(v);
                        t.username_input.setSearchBox([{
                            text: '张学超',
                            value: 'zhangxuechao'
                        }, {
                            text: '张三',
                            value: 'zhangsan'
                        }], '张');
                    }
                });

                t.set_button = new ButtonBox({
                    el: $(view_id)
                }, {
                    fieldName: 'set-button',
                    text: '设置'
                }, {
                    Click: function(e) {
                        var v = t.username_input.setValue('zhangxuechao', '张学超');
                        console.log(v);
                    }
                });

                t.get_button = new ButtonBox({
                    el: $(view_id)
                }, {
                    fieldName: 'get-button',
                    text: 'getValue'
                }, {
                    Click: function(e) {
                        var v = t.username_input.getValue();
                        console.log(v);
                    }
                });

                // t.pie_box = new PieBox({
                //     el: $(form_id)
                // }, {
                //     fieldName: 'simple-pie-box',
                //     text: '张学超',
                //     label: '70%',
                //     data: {
                //         datasets: [{
                //             data: [
                //                 30,
                //                 1
                //             ],
                //             backgroundColor: [
                //                 "#1d68f1",
                //                 "#e2e2e2"
                //             ],
                //             label: 'Dataset 1'
                //         }],
                //         labels: [
                //             "Red",
                //             "Green"
                //         ]
                //     }

                // });

                // t.line_box = new LineBox({
                //     el: $(form_id)
                // }, {
                //     fieldName: 'simple-line-box',
                //     text: '张学超',
                //     label: '70%',
                //     data: {
                //         datasets: [{
                //             data: [
                //                 30,
                //                 1
                //             ],
                //             backgroundColor: [
                //                 "#1d68f1",
                //                 "#e2e2e2"
                //             ],
                //             label: 'Dataset 1'
                //         }],
                //         labels: [
                //             "Red",
                //             "Green"
                //         ]
                //     }

                // });

            }
        });
        return LayoutView;
    });