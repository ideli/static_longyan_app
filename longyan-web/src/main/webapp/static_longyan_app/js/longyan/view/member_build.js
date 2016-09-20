/**
 *house list view
 *房屋信息默认页
 **/
define('js/longyan/view/member_build', [
        'text!js/longyan/template/member_build.tpl',
        'text!js/longyan/template/member_build_list_item.tpl',
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
        'js/element/view/group-button-box',
        'js/api/member'
    ],
    function(MemberBuildListTpl, MemberBuildListItemTpl, NoDataTpl, NoNetworkTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, GroupButtonBox, MemberApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#member-build-view';
        var form_id = '#member-build-form';
        var btn_id = '#member-build-btn';

        var LayoutView = Backbone.View.extend({
            events: {

            },
            //
            initialize: function(options, config) {
                var t = this;
                t.$el.off('click');
                t.render();
                // 加载数据
                t.initEvents();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#f5f5f5');

                var right_button_text = '取消';
                var right_button_action = function(e) {
                    e.preventDefault();
                    router.navigate('', {
                        trigger: true
                    });
                };
                t.$el.html(tpl(MemberBuildListTpl, {}));
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '选择楼号',
                    goBackUrl: function() {
                        Backbone.history.history.back();
                    }
                });

                if (right_button_text && right_button_text.length > 0) {
                    t.$el.find('#header-container').find('.right-box').html(right_button_text);
                    t.$el.find('#header-container').find('.right-box').on('click', right_button_action);
                }

                t.list_box = new ListBox({
                    el: $(form_id)
                }, {
                    scroll: true //支持下拉刷新
                }, {
                    loadData: function(page, handler) {
                        var page = page || 1;
                        var pageSize = 10;
                        var provinceCode = 0;
                        var cityCode = 0;
                        var name = '';
                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        MemberApi.getMemberList(page, pageSize, provinceCode, cityCode, name,
                            function(data) {
                                tipsAlert.close();
                                _log(data);
                                if (data && data.result) {
                                    var totalRecords = data.result.totalRecords;
                                    if (!totalRecords) {
                                        _log('no data');
                                        $('#scroller').hide();
                                        $('#scroller').after(tpl(NoDataTpl, {}));
                                        t.$el.find('.error-no-data').find('.button').on('click', function() {
                                            //添加小区
                                            router.navigate('', {
                                                trigger: true
                                            });
                                        });
                                    } else {
                                        $('.error-view').remove();
                                        $('#scroller').show();
                                    }

                                    var totalPages = data.result.totalPages;
                                    var currentPage = data.result.currentPage;
                                    var currentRecords = data.result.currentRecords;
                                    // t.list_box.setCurrentPage(currentPage);
                                    // t.list_box.setTotalPage(totalPages);
                                    if (handler) {
                                        handler(currentRecords, currentPage, totalPages);
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
                        return tpl(MemberBuildListItemTpl, {
                            data: data
                        });
                    }
                });
                //底部按钮组
                t.bottom_group_button_box = new GroupButtonBox({
                    el: $(btn_id)
                }, {
                    fieldName: 'bottom_group_button_box',
                    clazz: 'fixed-bottom g-hrz',
                    btns: [{
                        text: '复制',
                        clazz: 'u-full',
                        disable: false,
                        Click: function() {
                            t.addUnitAction();
                        }
                    }, {
                        text: '删除',
                        clazz: 'u-full',
                        disable: false,
                        Click: function() {

                        }
                    }]
                });

            },
            //初始化监听器
            initEvents: function() {
                // var t = this;
                // t.$el.find('#fix-button').on('click', function(e) {
                //     e.preventDefault();
                //     router.navigate('member_create', {
                //         trigger: true
                //     });
                // });
            },
            destroy: function() {
                $(window).off('scroll');
                this.list_box.removeEvent();

            }

        });
        return LayoutView;
    });