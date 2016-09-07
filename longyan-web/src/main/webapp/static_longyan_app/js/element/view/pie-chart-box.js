/**
 * 饼图组件
 **/
define('js/element/view/pie-chart-box', [
        'text!js/element/template/pie-chart-box.tpl',
        'js/components/chart'
    ],
    function(PieChartTpl) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.config.data = t.config.data || {
                    datasets: [{
                        data: [
                            30,
                            1
                        ],
                        backgroundColor: [
                            "#F7464A",
                            "#46BFBD"
                        ],
                        label: 'Dataset 1'
                    }],
                    labels: [
                        "Red",
                        "Green"
                    ]
                };
                t.render();
                t.initChart();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(PieChartTpl, {
                    data: t.config
                }));
            },
            initChart: function() {
                var t = this;
                var ctx = $("#myChart-" + t.config.fieldName).get(0).getContext("2d");
                //This will get the first returned node in the jQuery collection.
                // var myNewChart = new Chart(ctx);
                var config = {
                    type: 'doughnut',
                    data: t.config.data,
                    options: {
                        // responsive: true,
                        legend: {
                            position: 'top',
                            display: false
                        },
                        title: {
                            display: false
                        }
                    }
                };
                t.myLine = new Chart(ctx, config);
            },
            setValue: function(value) {
                var t = this;
                if (value) {
                    t.config.data = value;
                    t.myLine.destroy();
                    t.initChart();
                }
            },
            setDataLabel: function(value) {
                var t = this;
                t.$el.find('.data-label').html(value);
            },
            destroy: function() {
                var t = this;
                t.myLine.destroy();
            }
        });
        return LayoutView;
    });