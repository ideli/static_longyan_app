/**
 * 报表-大区视图
 * report_shopping_mall view
 **/
define('js/longyan/view/report_shopping_mall', [
        'text!js/longyan/template/report_shopping_mall.tpl',
        'text!js/longyan/template/rank_list_item.tpl',
        'js/components/alert_ui',
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
        'js/element/view/list-box',
        'js/element/view/group-input-record-box',
        'js/element/view/info-tips-bar',
        'js/element/view/tab-box',
        'js/api/report'
    ],
    function(SimpleTpl, RankListItemTpl, AlertUI, HeaderView, InputBox, GenderBox, RoomBox, SelectBox, ThinkBox, ButtonBox, LinkBox, PieBox, LineBox, LocationBox, EmployeeRecordBox, ListBox, GroupInputRecordBox, InfoTipsBar, TabBox, ReportApi) {
        var view_id = '#report-shopping-mall-view';
        var form_id = '#report-shopping-mall-form';
        var tipsAlert = tipsAlert || new AlertUI();
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
                // t.initEvents();
                t.loadData();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(SimpleTpl, {}));


                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '数据报表'
                });


                t.record_box = new GroupInputRecordBox({
                    el: $(form_id)
                }, {
                    fieldName: 'records-box',
                    data: {
                        input_member_amount: 0,
                        uninput_member_amount: 0,
                        input_member_rate: 0,
                        input_community_amount: 0,
                        employee_count: 0
                    }
                });

                t.$el.find('.employee-count').find('.label').html('商场员工');

                $('<div class="gap basic-gap"></div>').appendTo($(form_id));

                t.tips_bar = new InfoTipsBar({
                    el: $(form_id)
                }, {
                    fieldName: 'sub-tips-bar',
                    text: '员工详情'
                }, {
                    Click: function() {
                        router.navigate('report_employee_list_by_mall/' + t.config.id, {
                            trigger: true
                        });
                    }
                });

                t.data.member = {
                    labels: [],
                    datasets: [{
                        label: "社区录入数",
                        borderColor: "#21c393",
                        fill: false,
                        data: []
                    }]
                };
                t.data.community = {
                    labels: [],
                    datasets: [{
                        label: "社区录入数",
                        borderColor: "#1680fa",
                        fill: false,
                        data: []
                    }]
                };
                $('<div class="gap basic-gap"></div>').appendTo($(form_id));
                //住宅趋势的“日月报”tab
                t.member_tab_box = new TabBox({
                    el: $(form_id)
                }, {
                    fieldName: 'member-input-tab-box',
                    column_width: '50%',
                    label: ['日报', '月报'],
                    selected_index: 0
                }, {
                    Selected: function(index) {
                        if (index == 0) {
                            t._loadMemberLine('daily');
                        } else if (index == 1) {
                            t._loadMemberLine('monthly');
                        }
                    }
                });

                t.member_input_line_container = $('<div class="member-input-line-container"></div>').appendTo($(form_id));

                var suggestMem = 0;
                if (!t.data.member.datasets.data)
                    suggestMem = 10;

                t.member_input_line_box = new LineBox({
                    el: $(form_id)
                }, {
                    fieldName: 'member-input-line-box',
                    suggestedMax: suggestMem, //Y轴的最大值
                    data: t.data.member
                });
                $('<div class="gap basic-gap"></div>').appendTo($(form_id));
                //小区趋势的“日月报”tab
                t.community_tab_box = new TabBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-input-tab-box',
                    column_width: '50%',
                    label: ['日报', '月报'],
                    selected_index: 0
                }, {
                    Selected: function(index) {
                        if (index == 0) {
                            t._loadCommunityLine('daily');
                        } else if (index == 1) {
                            t._loadCommunityLine('monthly');
                        }
                    }
                });

                var date = new Date;
                var year = date.getFullYear();
                var month = date.getMonth() + 1;
                t.community_input_line_container = $('<div class="community-input-line-container"></div>').appendTo($(form_id));

                $('<div class="list-title-bar"><span class="left">住宅趋势</span><span style="color:#21c393" class="right"><img style="padding-right:0.6rem;padding-bottom:0.1rem;" src="' + window.resource.image + '/report-line-point-green.png"/>' + year + '</span></div>').appendTo(t.member_input_line_container);
                $('<div class="list-title-bar"><span class="left">小区趋势</span><span style="color:#608fea" class="right"><img style="padding-right:0.6rem;padding-bottom:0.1rem;" src="' + window.resource.image + '/report-line-point-blue.png"/>' + year + '</span></div>').appendTo(t.community_input_line_container);


                // t.community_input_line_container = $('<div class="community-input-line-container"></div>').appendTo($(form_id));
                // $('<div class="list-title-bar"><span class="left">住宅趋势</span></div>').appendTo(t.member_input_line_container);
                // $('<div class="list-title-bar"><span class="left">小区趋势</span></div>').appendTo(t.community_input_line_container);

                var suggestCom = 0;
                if (!t.data.community.datasets.data)
                    suggestCom = 10;

                t.community_input_line_box = new LineBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-input-line-box',

                    suggestedMax: suggestCom, //Y轴的最大值

                    data: t.data.community
                });

            },
            initEvents: function() {
                var t = this;

            },
            loadData: function() {
                var t = this;

                var postData = {
                    mallId: t.config.id || 0
                };
                //打开loading
                tipsAlert.openLoading({
                    content: '加载中...'
                });
                ReportApi.getMallInputReport (postData, function(data) {
                        tipsAlert.close();
                        //success
                        console.log(data);
                        if (data && data.mallInfo && data.mallInfo.name) {
                            t.header_view.setText(data.mallInfo.name);
                        }
                        //顶部仪表盘数据
                        if (data && data.mallInfo) {
                            var input_member_rate = 0;
                            if (data.mallInfo.communityMemberAmount) {
                                input_member_rate = ((data.mallInfo.inputMemberAmount / data.mallInfo.communityMemberAmount) * 100).toFixed(0);
                                if (input_member_rate > 100) {
                                    input_member_rate = 100;
                                }
                            }
                            var _uninput_member_amount = data.mallInfo.communityMemberAmount - data.mallInfo.inputMemberAmount || 0;
                            if (_uninput_member_amount < 0) {
                                _uninput_member_amount = 0;
                            }
                            var record_data = {
                                input_member_amount: data.mallInfo.inputMemberAmount || 0,
                                uninput_member_amount: _uninput_member_amount,
                                input_member_rate: input_member_rate,
                                input_community_amount: data.mallInfo.inputCommunityAmount || 0,
                                employee_count: data.mallInfo.employeeCount || 0
                            }
                            t.record_box.setValue(record_data);
                            t.tips_bar.setValue(data.mallInfo.employeeCount);
                        }
                        //中间商场数据
                        if (data && data.mallCount) {
                            t.tips_bar.setValue(data.mallCount);
                        }
                        //月报折线图数据
                        var month_array = [];
                        var member_array = [];
                        var community_array = [];
                        if (data && data.monthDataList && data.monthDataList.length > 0) {

                            for (var i = data.monthDataList.length - 1; i >= 0; i--) {
                                console.log(i);
                                console.log(data.monthDataList[i]);
                                month_array.push(data.monthDataList[i]['month'] + '月');
                                member_array.push(data.monthDataList[i]['inputMemberAmount']);
                                community_array.push(data.monthDataList[i]['inputCommunityAmount']);
                            }
                            console.log(month_array);
                            console.log(member_array);
                            console.log(community_array);

                        }
                        t.data.member = {
                            labels: month_array,
                            datasets: [{
                                label: "社区录入数",
                                borderColor: "#21c393",
                                fill: false,
                                data: member_array
                            }]
                        };
                        t.data.community = {
                            labels: month_array,
                            datasets: [{
                                label: "社区录入数",
                                borderColor: "#1680fa",
                                fill: false,
                                data: community_array
                            }]
                        };


                        //日报的曲线图
                        var day_array = [];
                        var day_member_array = [];
                        var day_community_array = [];
                        if (data && data.dayDataList && data.dayDataList.length > 0) {


                            for (var i = data.dayDataList.length - 1; i >= 0; i--) {

                                day_array.push(data.dayDataList[i]['month'] + "-" + data.dayDataList[i]['day']);
                                day_member_array.push(data.dayDataList[i]['inputMemberAmount']);
                                day_community_array.push(data.dayDataList[i]['inputCommunityAmount']);
                            }
                        }

                        //住宅日报曲线
                        t.data.member_daily = {
                            labels: day_array,
                            datasets: [{
                                label: "社区录入数",
                                borderColor: "#21c393",
                                fill: false,
                                data: day_member_array
                            }]
                        };
                        //社区日报曲线
                        t.data.community_daily = {
                            labels: day_array,
                            datasets: [{
                                label: "社区录入数",
                                borderColor: "#1680fa",
                                fill: false,
                                data: day_community_array
                            }]
                        };
                        //加载日报
                        t._loadCommunityLine('daily');
                        t._loadMemberLine('daily');


                    },
                    function(code, msg) {
                        tipsAlert.close();
                        //弹出异常信息
                        tipsAlert.openAlert({
                            content: msg
                        });
                    });
            },
            _loadCommunityLine: function(type) {
                var t = this;
                if (type && type == 'monthly') {
                    //月报
                    t.community_input_line_box.setValue(t.data.community);
                } else if (type && type == 'daily') {
                    //日报
                    t.community_input_line_box.setValue(t.data.community_daily);
                }
            },
            _loadMemberLine: function(type) {
                var t = this;
                if (type && type == 'monthly') {
                    //月报

                    t.member_input_line_box.setValue(t.data.member);

                } else if (type && type == 'daily') {
                    //日报
                    t.member_input_line_box.setValue(t.data.member_daily);
                }
            },
        });
        return LayoutView;
    });