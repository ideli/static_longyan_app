/**
 *
 * 首页->关于
 **/
define('js/longyan/view/aboutus', [
        'text!js/longyan/template/aboutus.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/menu-box',
        'js/api/community'
    ],
    function(AboutusTpl, Cache, AlertUI, HeaderView, MenuBox, CommunityApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#aboutus-view';
        var form_id = '#aboutus-form';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                //加载数据  
                t.initEvents();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(AboutusTpl, {}));
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '关于龙眼',
                    goBackUrl: function() {
                        router.navigate('index', {
                            trigger: true
                        });
                    }
                });

                $('<div class="gap basic-gap"></div>').appendTo($('#aboutus-box'));

                t.menu_help_box = new MenuBox({
                    el: $('#aboutus-box')
                }, {
                    fieldName: 'menu_help_box',
                    text: '帮助中心',
                    icon: 'help.png'
                }, {
                    Click: function(e, item) {
                        router.navigate('help_list', {
                            trigger: true
                        });
                    }
                });

                t.menu_opinion_box = new MenuBox({
                    el: $('#aboutus-box')
                }, {
                    fieldName: 'menu_opinion_box',
                    text: '意见反馈',
                    icon: 'opinion.png'
                }, {
                    Click: function(e, item) {
                   
                        router.navigate('feedback', {
                            trigger: true
                        });
                    }
                });

                t.menu_aboutus_box = new MenuBox({
                    el: $('#aboutus-box')
                }, {
                    fieldName: 'menu_aboutus_box',
                    text: '关于我们',
                    icon: 'aboutus.png'
                }, {
                    Click: function(e, item) {
                        router.navigate('aboutus_center', {
                            trigger: true
                        });
                    }
                });

            },
            //初始化监听器
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
    });