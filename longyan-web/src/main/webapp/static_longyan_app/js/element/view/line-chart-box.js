/**
 * 饼图组件 http://nnnick.github.io/Chart.js/docs-v2/#getting-started-scales
 **/
define('js/element/view/line-chart-box', [
        'text!js/element/template/line-chart-box.tpl',
        'js/components/chart'
    ],
    function(LineChartTpl) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.config.data = t.config.data || {
                    labels: ["1月", "2月", "3月", "4月", "5月"],
                    datasets: [{
                        label: "社区录入数",
                        borderColor: "#1d65f0",
                        fill: false,
                        // backgroundColor: "#fff",
                        data: [65, 59, 80, 81, 56],
                        tension: 0
                    }, {
                        label: "住户录入数",
                        borderColor: "#21c393",
                        fill: false,
                        // backgroundColor: "#fff",
                        // fillColor: "#1d65f0",
                        // strokeColor: "#1d65f0",
                        // pointColor: "#1d65f0",
                        // pointStrokeColor: "#fff",
                        // pointHighlightFill: "#fff",
                        // pointHighlightStroke: "#1d65f0",
                        data: [28, 48, 40, 19, 86],
                        tension: 0
                    }]
                };
                t.config.suggestedMax || 250;



                t.render();
            },
            render: function() {

                var t = this;
                t.$el.append(tpl(LineChartTpl, {
                    data: t.config
                }));
                t.initChart();
            },
            initChart: function() {
                var t = this;
                var ctx = $("#myChart-" + t.config.fieldName).get(0).getContext("2d");
                //This will get the first returned node in the jQuery collection.
                // var myNewChart = new Chart(ctx);
                // var config = {
                //     type: 'doughnut',
                //     data: t.config.data,
                //     options: {
                //         // responsive: true,
                //         legend: {
                //             position: 'top',
                //             display: false
                //         },
                //         title: {
                //             display: false
                //         }
                //     }
                // };

                var config = {
                    type: 'line',
                    data: t.config.data,
                    options: {
                        responsive: true,
                        title: {
                            display: false
                        },
                        legend: {
                            display: false
                        },
                        scales: {
                            xAxes: [{
                                display: true,
                                scaleLabel: {
                                    show: false,
                                    labelString: 'Month'
                                },
                                ticks: {
                                    fontSize: 20
                                },
                                gridLines: {
                                    display: false
                                },
                                angleLines: {
                                    display: false
                                }
                            }],
                            yAxes: [{
                                lineArc: false,
                                display: true,
                                scaleLabel: {
                                    show: false,
                                    labelString: 'Value'
                                },
                                ticks: {
                                    suggestedMin: 0,
                                    suggestedMax: t.config.suggestedMax,
                                    fontSize: 20
                                },
                                gridLines: {
                                    lineWidth: 2,
                                    color: '#ddd',
                                    drawBorder: false

                                },
                                angleLines: {
                                    display: false
                                }
                            }]
                        }
                    }
                };

                t.myLine = new Chart(ctx, config);
                var w = t.$el.find('#myChart-' + t.config.fieldName).css('width');
                t.$el.find('#myChart-' + t.config.fieldName).css('width', w - 30);
            },
            setValue: function(value) {
                var t = this;
                if (value) {
                    t.config.data = value;
                    t.myLine.destroy();
                    t.initChart();
                }
            },
            destroy: function() {
                var t = this;
                t.myLine.destroy();
            }
        });
        return LayoutView;
    });