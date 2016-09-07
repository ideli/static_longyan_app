/**
 * 拖拽进度条 组件
 **/
define('js/element/view/input-percentage-box', [
        'text!js/element/template/input-percentage-box.tpl',
        'rangeSlider'
    ],
    function(SimpleTpl) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config, events) {
                var t = this;
                t.config = $.extend({
                    sliderType: 'double', //double  single
                    min: 0,
                    max: 100,
                    from: 0,
                    to: 0,
                    step: 1,
                }, t.config, config);

                t.render();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(SimpleTpl, {
                    data: t.config
                }));

                //初始化
                $('.percentage-input').ionRangeSlider({
                    type: t.config.sliderType,
                    min: t.config.min,
                    max: t.config.max,
                    from: t.config.from,
                    to: t.config.to,
                    step: t.config.step,
                    hide_from_to: true,
                    hide_min_max: true,
                    from_fixed: true,
                    // disable: true,
                    // prettify: true,
                    onChange: function(e, ui) {
                        $('.irs-single').css('display', 'none');
                        $('.irs-min').css('display', 'none');
                        $('.irs-max').css('display', 'none');
                        $('.irs-bar').css('left', '1px');
                        $('.irs-slider.from').css('display', 'none');
                        $('.irs-from').css('display', 'none');
                        $('.irs-to').css('display', 'none');

                        var to = $('.percentage-input').data('ionRangeSlider').old_to;
                        if (to <= 0) {
                            to = 0;
                        }
                        $('.percentage-val .val').text(to);
                    },
                    onStart: function() {
                        $('.irs-bar').css('left', '1px');
                    }
                });

                $('.irs-slider.from').css('display', 'none');
                $('.irs-slider.from').css('z-index', '-1');
                $('.irs-bar').css('left', '1px');
                $('.irs-single').css('display', 'none');
                $('.irs-min').css('display', 'none');
                $('.irs-max').css('display', 'none');
                $('.irs-from').css('display', 'none');
                $('.irs-to').css('display', 'none');

                t.slider = $('.percentage-input').data('ionRangeSlider');

            },

            setReadOnly: function(value) {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var input = container.find('input');
                    if (value) {
                        input.attr('readonly', 'readonly');
                    } else {
                        input.removeAttr('readonly');
                    }

                }
            },
            getValue: function() {
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (container) {
                    var val = Number($('.percentage-val .val').text());
                    return val / 100;
                }
            },
            setValue: function(val) {
                var t = this;

                t.count = 0;
                var time = window.setInterval(function() {
                    t.count++;
                    if (t.count >= 100) {
                        window.clearInterval(time);
                    }
                    if (t.slider) {
                        window.clearInterval(time);

                        var text = val * 100;
                        t.slider.update({
                            to: text
                        });

                        $('.percentage-val .val').text(text);

                        $('.irs-slider.from').css('z-index', '-1');
                        $('.irs-bar').css('left', '1px');
                    }
                }, 50);

            },

        });
        return LayoutView;
    });