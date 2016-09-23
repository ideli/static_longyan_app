/**
 * 首页 -> 社区列表
 **/
define('js/longyan/view/community_list', [
        'js/longyan/view/tabs-bottom',
        'text!js/longyan/template/community_list.tpl',
        'text!js/longyan/template/community_list_item.tpl',
        'text!js/longyan/template/no_data.tpl',
        'text!js/longyan/template/no_network.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/list-box',
        'js/api/community',
        'js/util/hybrid'
    ],
    function(TabsBottom, CommunityListTpl, CommunityListItemTpl, NoDataTpl, NoNetworkTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, CommunityApi, hybrid) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#community-list-view';
        var form_id = '#community-list-form';
        var city = '上海';
        var LayoutView = Backbone.View.extend({
            events: {},
            //
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.$el.off('click');
                t.render();
                //加载数据
                t.initEvents();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(CommunityListTpl, {}));
                var right_button_text = $('<i class="icon iconfont icon-longyanjiahao">+</i>');
                var right_button_action = function(e) {
                    e.preventDefault();
                    router.navigate('community_create', {
                        trigger: true
                    });
                };
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '我的小区',
                    goBackUrl: function() {
                        hybrid.backToHybrid("Mine", "direct");
                    }
                });
                if (right_button_text && right_button_text.length > 0) {
                    t.$el.find('#header-container').find('.right-box').html(right_button_text);
                    t.$el.find('.right-box').on('click', right_button_action);
                }
                t.list_box = new ListBox({
                    el: $('#community-list-box')
                }, {
                    scroll: true //支持下拉刷新
                }, {
                    loadData: function(page, handler) {
                        var page = page || 1;
                        var pageSize = 10;
                        var name = '';
                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        //我的小区列表
                        CommunityApi.getCommunityList(page, pageSize,
                            function(data) {
                                tipsAlert.close();
                                if (data && data.result) {
                                    var totalRecords = data.result.totalRecords;
                                    if (!totalRecords) {
                                        $('#scroller').hide();
                                        $('#scroller').after(tpl(NoDataTpl, {}));
                                        var $test = t.$el.find('.error-no-data').find('.button');
                                        //console.log($test)
                                        t.$el.find('.error-no-data').find('.button').on('click', function() {
                                            //添加小区
                                            t.list_box.removeEvent();
                                            router.navigate('community_create', {
                                                trigger: true
                                            });
                                        });
                                        $test = t.$el.find('.error-no-data').find('.button');
                                    } else {
                                        $('.error-view').remove();
                                        $('#scroller').show();
                                    }
                                    var totalPages = data.result.totalPages;
                                    var currentPage = data.result.currentPage;
                                    var currentRecords = data.result.currentRecords;
                                    if (handler) {
                                        handler(currentRecords, currentPage, totalPages, 0);
                                        //$test.click()
                                    }
                                }
                            },
                            function(code, msg) {
                                tipsAlert.close();
                                if (code && code == 408) {
                                    //请求超时
                                    $('#scroller').hide();
                                    var tmp = $('.error-view');
                                    if (tmp && tmp.length > 0) {

                                    } else {
                                        $('#scroller').after(tpl(NoNetworkTpl, {}));
                                        t.$el.find('.error-no-network').off('click');
                                        t.$el.find('.error-no-network').on('click', function() {
                                            //重新刷新 reload
                                            t.list_box.loadData();
                                        });
                                    }
                                    return;
                                }
                                tipsAlert.openAlert({
                                    content: msg
                                });
                            });
                    },
                    appendItem: function(data) {
                        var _data = data;
                        _data['url'] = '#community_info/' + data.id;
                        if (t.config.employee_id) {
                            _data['url'] = '#report_community/' + data.id;
                        }
                        var item = tpl(CommunityListItemTpl, {
                            data: _data
                        });
                        // container.append(item);
                        return item;
                        // $(item).delegate('click', function() {
                        //     console.log(data);
                        // });
                    },
                    clickItem: function(e, item) {
                        console.log(item);
                    },
                    slideEdit: function(elem) {
                        var id = $(elem).parents('.community-item-box').data('key');
                        CommunityApi.getCommunityById(id, function(data) {
                            tipsAlert.close();
                            //返回数据
                            if (data && data.community) {
                                //放入缓存
                                Cache.set('community-manager-object', data.community);
                                //跳转到更新界面
                                var community = data.community;
                                router.navigate('community_update/' + id, {
                                    trigger: true
                                });
                            } else {
                                tipsAlert.openAlert({
                                    content: '系统异常'
                                });
                            }
                        }, function(code, msg) {
                            tipsAlert.close();
                            //显示异常信息
                            tipsAlert.openAlert({
                                content: msg
                            });
                        });
                    },
                    slideDelete: function(elem) {
                        //表单类型
                        tipsAlert.open({
                            cancelText: '否',
                            confirmText: '是',
                            content: '你确定要删除此小区吗',
                            onConfirm: function(e) {
                                var $item = $(elem).parents('.community-item-box');
                                var id = $item.data('key');
                                tipsAlert.close();
                                var success = function(data) {
                                    $item.remove();
                                }
                                var error = function(code, msg) {
                                    tipsAlert.close();
                                    //显示异常信息
                                    tipsAlert.openAlert({
                                        content: msg
                                    });
                                };
                                CommunityApi.deleteCommunity(id, success, error);
                            },
                            onCancel: function(e) {
                                tipsAlert.close();
                            }
                        });
                    }
                });
                /*this.tabs_bottom = new TabsBottom({
                 el: $('body')
                 }, {
                 selectedIndex: 2
                 });*/
                /*//点击跳转到我创建的小区列表页
                 $('.skipPage').click(function(){
                 router.navigate('community_list_setup', {
                 trigger: true
                 });
                 });*/
            },
            //初始化监听器
            initEvents: function() {
                var t = this;
                t.$el.on('click', '.community-item-box', function(e) {
                    e.preventDefault();
                });
                if (t.config.employee_id) {
                    t.$el.find('#fix-button').hide();
                }
            },
            test: function() {
                console.log(123);
            },
            destroy: function() {
                $(window).off('scroll');
                this.list_box.removeEvent();
            }
        });
        return LayoutView;
    });