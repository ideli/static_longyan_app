/**
 *shopping_mall view
 *登录
 **/
define('js/longyan/view/shopping_mall', [
        'text!js/longyan/template/shopping_mall.tpl',
        'text!js/longyan/template/employee_input_list_item.tpl',
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
        'js/element/view/employee-input-record-box',
        'js/element/view/list-box'
    ],
    function(SimpleTpl, EmployeeInputListItemTpl, HeaderView, InputBox, GenderBox, RoomBox, SelectBox, ThinkBox, ButtonBox, LinkBox, PieBox, LineBox, LocationBox, EmployeeRecordBox, ListBox) {
        var view_id = '#shopping-mall-view';
        var form_id = '#shopping-mall-form';
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
                    text: '商场管理'
                });

                t.pie_box = new PieBox({
                    el: $(form_id)
                }, {
                    fieldName: 'mall-input-pie-box',
                    text: '住户录入率',
                    label: '',
                    data: {
                        datasets: [{
                            data: [
                                30,
                                1
                            ],
                            backgroundColor: [
                                "#61e090",
                                "#757575"
                            ],
                            label: 'Dataset 1'
                        }],
                        labels: [
                            "Red",
                            "Green"
                        ]
                    }

                });
                $('<div class="gap"></div>').appendTo($(form_id));
                var line_box_title_bar = $('<div class="list-title-bar"><span class="left">住户趋势</span><span class="right">小区趋势</span></div>').appendTo($(form_id));
                t.line_box = new LineBox({
                    el: $(form_id)
                }, {
                    fieldName: 'simple-line-box',
                    text: '住户趋势',
                    suggestedMax: 500, //Y轴的最大值
                    data: {
                        labels: ["1月", "2月", "3月", "4月", "5月"],
                        datasets: [{
                            label: "社区录入数",
                            borderColor: "#21c393",
                            fill: false,
                            data: [65, 59, 80, 81, 56],
                            tension: 0
                        }]
                    }
                });

                // {
                //     label: "住户录入数",
                //     borderColor: "#21c393",
                //     fill: false,                 
                //     data: [28, 48, 40, 19, 86],
                //     tension: 0
                // }
                $('<div class="gap"></div>').appendTo($(form_id));
                var list_title_bar = $('<div class="list-title-bar"><span class="left">员工进度</span><span class="right">更多</span></div>').appendTo($(form_id));

                var list_container = $('<div></div>');
                list_container.appendTo($(form_id));

                t.list_box = new ListBox({
                    el: list_container
                }, {}, {
                    loadData: function(page, handler) {
                        var currentRecords = [{
                            "id": 10996,
                            "xingMing": '张三',
                            "inputMemberAmount": 100,
                            "inputCommunityAmount": 10,
                            "inputCommunityRoomAmount": 10000
                        }, {
                            "id": 10996,
                            "xingMing": '张三',
                            "inputMemberAmount": 100,
                            "inputCommunityAmount": 10,
                            "inputCommunityRoomAmount": 10000
                        }, {
                            "id": 10996,
                            "xingMing": '张三',
                            "inputMemberAmount": 100,
                            "inputCommunityAmount": 10,
                            "inputCommunityRoomAmount": 10000
                        }];
                        var currentPage = 1;
                        var totalPages = 1;
                        handler(currentRecords, currentPage, totalPages);
                    },
                    appendItem: function(data) {
                        console.log(data);
                        var item = tpl(EmployeeInputListItemTpl, {
                            data: data
                        });
                        return item;
                    },
                    clickItem: function(e, item) {
                        console.log(item);
                    }
                });


                // t.register_button = new ButtonBox({
                //     el: $(view_id)
                // }, {
                //     fieldName: 'reg-button',
                //     text: '提交'
                // }, {
                //     Click: function(e) {
                //         // var v = t.username_input.getValue();
                //         // console.log(v);
                //         t.username_input.setSearchBox([{
                //             text: '张学超',
                //             value: 'zhangxuechao'
                //         }, {
                //             text: '张三',
                //             value: 'zhangsan'
                //         }], '张');
                //     }
                // });

                // t.set_button = new ButtonBox({
                //     el: $(view_id)
                // }, {
                //     fieldName: 'set-button',
                //     text: '设置'
                // }, {
                //     Click: function(e) {
                //         var v = t.username_input.setValue('zhangxuechao', '张学超');
                //         console.log(v);
                //     }
                // });

                // t.get_button = new ButtonBox({
                //     el: $(view_id)
                // }, {
                //     fieldName: 'get-button',
                //     text: 'getValue'
                // }, {
                //     Click: function(e) {
                //         var v = t.username_input.getValue();
                //         console.log(v);
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