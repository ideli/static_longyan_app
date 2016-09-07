/**
 *
 * 首页->通讯录
 **/
define('js/longyan/view/contact_list', [
        'js/longyan/view/contact_book_searchbar',
        'js/longyan/view/contact_book_employee_list',
        'text!js/longyan/template/contact_book.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header'
],
    function (contactBookSearchbarView,contactBookEmployeeListView, contactBookTpl ,Cache, AlertUI, HeaderView) {
        var tipsAlert = tipsAlert || new AlertUI();
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
                t.$el.html(tpl(contactBookTpl, {})); //模板渲染
                t.header_view = new HeaderView({ //title
                    el: $('#header-container')
                }, {
                    text: '通讯录',
                    goBackUrl: function() {
                        router.navigate('contact_book', {
                            trigger: true
                        });
                    }
                });
                t.contact_book_searchbar = new contactBookSearchbarView({
                    el: $('#searchBar')
                });
                t.contact_book_department_list = new contactBookEmployeeListView({
                    el: $('#contactShowbox')
                })

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
                console.log(123);
            }
        });
        return LayoutView;

    }
)