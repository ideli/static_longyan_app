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
                    text: '功能列表'
                });

                //组装用户名输入框
                t.username_input = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'username-input',
                    text: '手机号',
                    //type: 'number',
                    placeholder: '手机号'
                }, {
                    Keyup: function() {

                    }
                });


            }
        });
        return LayoutView;
    });