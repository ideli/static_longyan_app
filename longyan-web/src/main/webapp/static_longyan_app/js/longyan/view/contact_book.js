/**
 *
 * 首页->通讯录
 **/
define('js/longyan/view/contact_book', [
        'js/longyan/view/contact_book_searchbar',
        'js/longyan/view/contact_book_department_list',
        'text!js/longyan/template/contact_book.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/util/hybrid'
],
    function (contactBookSearchbarView,contactBookDepartmentView, contactBookTpl ,Cache, AlertUI, HeaderView, hybrid) {
        var tipsAlert = tipsAlert || new AlertUI();
        var LayoutView = Backbone.View.extend({
            events: {

            },
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                t.loadData();
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
                        hybrid.backToHybrid("Working","direct");
                    }
                });
                t.contact_book_searchbar = new contactBookSearchbarView({
                    el: $('#searchBar')
                })
                t.contact_book_department_list = new contactBookDepartmentView({
                    el: $('#contactShowbox')

                },{
                    loadData: function() {
                        alert(1)
                    }
                })

            },
            loadData: function(){
                //contactBookDepartmentView.loadData();
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
        })
        return LayoutView;

    }
)