/**
 *group-input-record-box
 **/
define('js/element/view/group-input-record-box', [
        'text!js/element/template/group-input-record-box.tpl'
    ],
    function(BoxTpl, Cache, AlertUI) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {
                    data: {
                        input_member_amount: 1000,
                        uninput_member_amount: 1000,
                        input_member_rate: 1000,
                        input_community_amount: 1000,
                        employee_count: 1000
                    }
                };
                t.render();
            },
            render: function() {
                var t = this;
                t.config.data['fieldName'] = t.config.fieldName;
                t.$el.append(tpl(BoxTpl, {
                    data: t.config.data
                }));
            },
            setValue: function(value) {
                // value = {
                //     input_member_amount: 1000,
                //     uninput_member_amount: 1000,
                //     input_member_rate: 1000,
                //     input_community_amount: 1000,
                //     employee_count: 1000
                // }

                var t = this;
                var container = t.$el.find('.' + t.config.fieldName);
                if (value && container) {
                    t.config.data = value;
                    container.find('.input-member-amount').find('.value').html(value.input_member_amount);
                    container.find('.uninput-member-amount').find('.value').html(value.uninput_member_amount);
                    container.find('.input-member-rate').find('.value').html(value.input_member_rate + '%');
                    container.find('.input-community-amount').find('.value').html(value.input_community_amount);
                    container.find('.employee-count').find('.value').html(value.employee_count);
                }
            }
        });
        return LayoutView;
    });