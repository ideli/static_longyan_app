/**
 *
 * 首页->通讯录-员工列表【搜索结果】
 **/
define('js/longyan/view/contact_book_employee_list', [
        'text!js/longyan/template/contact_book_employee_list.tpl'
    ],
    function (contactBookEmployeeTpl) {
        var LayoutView = Backbone.View.extend({
            events: {

            },
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                //加载数据
                t.initEvents();
            },
            render: function() {
                var t = this;
                t.$el.html(tpl(contactBookEmployeeTpl, {})); //模板渲染
            },
            initEvents: function() {
                var t = this;
                t.$el.find('#fix-button').on('click', function(e) {
                    e.preventDefault();

                    router.navigate('community_create', {
                        trigger: true
                    });
                });
                t.$el.on('click', '.community-item-box', function(e) {
                    e.preventDefault();
                });
            },
            test: function() {
            }
        })
        return LayoutView;

    }
)