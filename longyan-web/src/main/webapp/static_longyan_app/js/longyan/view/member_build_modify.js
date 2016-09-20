/**
 *house list view
 *房屋信息  修改
 **/
define('js/longyan/view/member_build_modify', [
        'text!js/longyan/template/member_build_modify.tpl',
        'text!js/longyan/template/member_build_modify_list_item.tpl',
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
        'js/api/member'
    ],
    function(MemberBuildModifyTpl, MemberBuildModifyListItemTpl, NoDataTpl, NoNetworkTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, MemberApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#member-build-modify-view';
        var form_id = '#member-build-modify-form';
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

                var right_button_text = $('<i class="icon iconfont icon-longyanjiahao"></i>');
                var right_button_action = function(e) {
                    e.preventDefault();
                    router.navigate('member_create', {
                        trigger: true
                    });
                }; 
                t.$el.html(tpl(MemberBuildModifyTpl, {}));
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '住宅列表',
                    goBackUrl: function() {
                        router.navigate('index', {
                            trigger: true
                        });
                    }
                });

                if (right_button_text && right_button_text.length > 0) {
                    t.$el.find('#header-container').find('.right-box').html(right_button_text);
                    t.$el.find('.right-box').on('click', right_button_action);
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
                                            router.navigate('member_create', {
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
                        return tpl(MemberBuildModifyListItemTpl, {
                            data: data
                        });
                    },
                    slideEdit: function (elem) {
                        var id = $(elem).parents('.member-item-box').data('key');
                        console.log(id);
                        router.navigate('member_update/' + id, {
                            trigger: true
                        });
                    },
                    slideDelete: function (elem) {
                        //表单类型
                        tipsAlert.open({
                            cancelText: '否',
                            confirmText: '是',
                            content: '你确定要删除此住宅吗',
                            onConfirm: function(e) {
                                var $item = $(elem).parents('.member-item-box');
                                var id = $item.data('key');
                                tipsAlert.close();
                                var success = function (data) {
                                    $item.remove();
                                }
                                var error = function (code, msg) {
                                    tipsAlert.close();
                                    //显示异常信息
                                    tipsAlert.openAlert({
                                        content: msg
                                    });
                                };
                                MemberApi.deleteMember(id, success, error);
                            },
                            onCancel: function(e) {
                                tipsAlert.close();
                            }
                        });
                    }
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