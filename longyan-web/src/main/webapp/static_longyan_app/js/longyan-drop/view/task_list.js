/**
 *login view
 *登录
 **/
define('js/longyan/view/task_list', [
        'text!js/longyan/template/task_list.tpl',
        'text!js/longyan/template/task_list_item.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/list-box',
        'js/api/community'
    ],
    function(TaskListTpl, TaskListItemTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, CommunityApi) {
        //var tipsAlert = tipsAlert || new AlertUI();
        //var view_id = '#community-list-view';
        //var form_id = '#community-list-form';
        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                //加载数据
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(TaskListTpl, {}));
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '任务管理'
                });

                t.$el.find('#header-container').find('.right-box').html('创建小区');
                t.$el.find('.right-box').on('click', function() {
                    router.navigate('task_create', {
                        trigger: true
                    });
                });

                t.list_box = new ListBox({
                    el: $('#task-list-box')
                }, {}, {
                    loadData: function(page, handler) {
                        var page = 1;
                        var pageSize = 20;
                        var provinceCode = 0;
                        var cityCode = 0;
                        var name = '';
                        CommunityApi.getCommunityList(page, pageSize, provinceCode, cityCode, name,
                            function(data) {
                                console.log(data);
                                if (data) {
                                    var totalPages = data.totalPages;
                                    var currentPage = data.currentPage;
                                    var currentRecords = data.currentRecords;
                                    // t.list_box.setCurrentPage(currentPage);
                                    // t.list_box.setTotalPage(totalPages);
                                    if (handler) {
                                        handler(currentRecords, currentPage, totalPages);
                                    }
                                }

                            },
                            function(code, msg) {

                            });
                    },
                    appendItem: function(container, data) {
                        console.log(data);
                        container.append(tpl(TaskListItemTpl, {
                            data: data
                        }));

                    }
                });
            }

        });
        return LayoutView;
    });