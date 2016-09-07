/**
 *employee_input_dashboards view
 *员工录入详情
 **/
define('js/longyan/view/employee_input_dashboards', [
        'text!js/longyan/template/employee_input_dashboards.tpl',
        'text!js/longyan/template/employee_input_list_item.tpl',
        'js/api/report',
        'js/element/view/header',
        'js/element/view/pie-chart-box',
        'js/element/view/line-chart-box',
        'js/element/view/employee-input-record-box'
    ],
    function(SimpleTpl, EmployeeInputListItemTpl, ReportApi, HeaderView, PieBox, LineBox, EmployeeRecordBox) {
        var view_id = '#employee-input-detail-view';
        var form_id = '#employee-input-detail-form';
        var LayoutView = Backbone.View.extend({
            events: {
                // 'tap .reset-link': 'gotoReset'
            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.data = {};
                t.$el.off('click');
                t.render();
                t.initEvents();
                t.loadData();

            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(SimpleTpl, {}));

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: 'xxxx员工'
                });
                t.header_view.setText('好员工');

                t.employee_record_box = new EmployeeRecordBox({
                    el: $(form_id)
                }, {
                    fieldName: 'employee-record-area',
                    text: '录入详情',
                    data: {
                        left: {
                            label: 0,
                            text: '已录入住户'
                        },
                        right: {
                            label: 0,
                            text: '已录入小区'
                        }
                    }
                }, {

                });

                t.data.member = {
                    labels: ["1月", "2月", "3月", "4月", "5月"],
                    datasets: [{
                        label: "社区录入数",
                        borderColor: "#21c393",
                        fill: false,
                        data: [65, 59, 80, 81, 56],
                        tension: 0
                    }]
                };
                t.data.community = {
                    labels: ["1月", "2月", "3月", "4月", "5月"],
                    datasets: [{
                        label: "社区录入数",
                        borderColor: "#1680fa",
                        fill: false,
                        data: [65, 59, 80, 81, 56],
                        tension: 0
                    }]
                };

                $('<div class="gap basic-gap"></div>').appendTo($(form_id));
                t.member_input_line_container = $('<div class="member-input-line-container"></div>').appendTo($(form_id));
                t.community_input_line_container = $('<div class="community-input-line-container"></div>').appendTo($(form_id));
                $('<div class="list-title-bar"><span class="left">住户趋势</span><span class="right">小区趋势</span></div>').appendTo(t.member_input_line_container);
                $('<div class="list-title-bar"><span class="left">小区趋势</span><span class="right">住户趋势</span></div>').appendTo(t.community_input_line_container);
                t.member_input_line_box = new LineBox({
                    el: $(form_id)
                }, {
                    fieldName: 'member-input-line-box',
                    suggestedMax: 500, //Y轴的最大值
                    data: t.data.member
                });
                t.community_input_line_container.hide();
            },
            initEvents: function() {
                var t = this;
                t.$el.find('.member-input-line-container').find('.right').on('click', function(e) {
                    //切换到小区
                    t.member_input_line_box.setValue(t.data.community);
                    t.community_input_line_container.show();
                    t.member_input_line_container.hide();
                    // t.community_input_line_box.destroy();
                });
                t.$el.find('.community-input-line-container').find('.right').on('click', function(e) {
                    //切换到住户
                    t.member_input_line_box.setValue(t.data.member);
                    t.community_input_line_container.hide();
                    t.member_input_line_container.show();
                });
            },
            loadData: function() {
                var t = this;
                var postData = {};
                if (t.config && t.config.id) {
                    postData['employeeId'] = t.config.id
                }
                ReportApi.getEmployeeInputReport(postData, function(data) {
                        //success
                        console.log(data);
                        if (data) {
                            if (data.employee_name) {
                                t.header_view.setText(data.employee_name);

                            }
                            t.employee_record_box.setValue({
                                left: {
                                    label: data.employee_member_input_count,
                                    text: '已录入住户'
                                },
                                right: {
                                    label: data.employee_community_input_count,
                                    text: '已录入小区'
                                }
                            });
                        }
                    },
                    function(code, msg) {
                        //error
                    });
            }
        });
        return LayoutView;
    });