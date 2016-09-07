/**
 * employee-input-record-box
 **/
define('js/element/view/employee-input-record-box', [
        'text!js/element/template/employee-input-record-box.tpl'
    ],
    function(BoxTpl, Cache, AlertUI) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.render();
            },
            render: function() {
                var t = this;
                t.$el.append(tpl(BoxTpl, {
                    data: t.config
                }));
            },
            setValue: function(value) {
                // value = {
                //     left: {
                //         label: 243333,
                //         text: '已录入住户111'
                //     },
                //     right: {
                //         label: 33,
                //         text: '已录入小区2'
                //     }
                // }
                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (value && value.left && value.left) {
                    container.find('.left').find('.pie-label').html(value.left.label);
                    container.find('.left').find('.pie-text').html(value.left.text);
                }
                if (value && value.right && value.right) {
                    container.find('.right').find('.pie-label').html(value.right.label);
                    container.find('.right').find('.pie-text').html(value.right.text);
                }
            }
        });
        return LayoutView;
    });