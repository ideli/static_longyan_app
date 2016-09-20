/**
 *house list view
 *房屋信息默认页
 **/
define('js/longyan/view/member_unit_list', [
        'text!js/longyan/template/member_unit_list.tpl',
        'text!js/longyan/template/member_unit_list_itemV2.tpl',
        'text!js/longyan/template/no_data.tpl',
        'text!js/longyan/template/no_network.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/components/alert_ui_v2',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/element/view/list-box',
        'js/element/view/title-box',
        'js/element/view/unit-box',
        'js/element/view/group-button-box',
        'js/api/member',
        'js/api/community'
    ],
    function(MemberUnitListTpl, MemberUnitListItemV2Tpl, NoDataTpl, NoNetworkTpl, Cache, AlertUI, AlertUIV2,
        HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, TitleBox, UnitBox, GroupButtonBox, MemberApi, CommunityApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var tipsAlertV2 = tipsAlertV2 || new AlertUIV2();
        var view_id = '#member-unit-list-view';
        var form_id = '#member-unit-list-form';
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
                // $('body').css('background-color', '#f5f5f5');
                $('body').css('background-color', '#fff');

                t.right_button_text = '编辑';
                t.bottom_delete_button_box = ''; //底部删除按钮
                var right_button_action = function(e) {
                    e.preventDefault();
                    //点击编辑 进入删除界面，勾选删除

                    t.toEditView();
                };
                t.$el.html(tpl(MemberUnitListTpl, {}));

                t.community = Cache.get('community-manager-object');
                var header_view_text = t.community.name;

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: header_view_text,
                    goBackUrl: function() {
                        Backbone.history.history.back();
                    }
                });

                if (t.right_button_text && t.right_button_text.length > 0) {
                    t.$el.find('#header-container').find('.right-box').html(t.right_button_text);
                    t.$el.find('#header-container').find('.right-box').on('click', right_button_action);
                }

                t.list_box = new ListBox({
                    el: $(form_id)
                }, {
                    scroll: false, //支持下拉刷新
                    marginTop: '0px',
                    hidenslideBar: true
                }, {
                    loadData: function(page, handler) {
                        var page = page || 1;
                        var pageSize = 10;

                        // var data = [{
                        //     text: '123',
                        //     id: 1
                        // }, {
                        //     text: '1234',
                        //     id: 2
                        // }, {
                        //     text: '12345',
                        //     id: 3
                        // }, {
                        //     text: '123',
                        //     id: 1
                        // }, {
                        //     text: '1234',
                        //     id: 2
                        // }, {
                        //     text: '12345',
                        //     id: 3
                        // }];
                        // if (handler) {
                        //     handler(data, 1, 1);
                        // }

                        tipsAlert.openLoading({
                            content: '加载中...'
                        });
                        CommunityApi.getCommunityBuildingList({
                                communityId: t.community.id || 12
                            }, function(data) {
                                tipsAlert.close();
                                _log(data);
                                if (data && data.redstarCommunityBuildings) {
                                    var totalRecords = data.redstarCommunityBuildings;
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

                                    // var totalPages = data.result.totalPages;
                                    // var currentPage = data.result.currentPage;
                                    var currentRecords = data.redstarCommunityBuildings;
                                    // t.list_box.setCurrentPage(currentPage);
                                    // t.list_box.setTotalPage(totalPages);
                                    if (handler) {
                                        handler(currentRecords, 1, 1);
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
                        var dom = tpl(MemberUnitListItemV2Tpl, {
                            data: data
                        });
                        return dom;
                    }
                });
                t.$el.find('#scroller').css("background-color", "#fff");

                //底部按钮组
                t.bottom_group_button_box = new GroupButtonBox({
                    el: $(form_id)
                }, {
                    fieldName: 'bottom_group_button_box',
                    clazz: 'fixed-bottom g-hrz',
                    btns: [{
                        text: '添加楼栋号',
                        clazz: 'blue u-full',
                        disable: false,
                        Click: function() {
                            //跳转到添加楼栋号界面
                            router.navigate('', {
                                trigger: true
                            });
                        }
                    }]
                });
                //删除按钮
                t.bottom_delete_button_box = new GroupButtonBox({
                    el: $(form_id)
                }, {
                    fieldName: 'bottom_delete_button_box',
                    clazz: 'fixed-bottom g-hrz hidden',
                    btns: [{
                        text: '删除楼栋号',
                        clazz: 'blue u-full ',
                        disable: false,
                        Click: function(e) {
                            //删除事件
                            tipsAlertV2.open({
                                cancelText: '取消',
                                confirmText: '确认',
                                content: '是否确认删除楼栋信息！',
                                onConfirm: function(e) {
                                    tipsAlertV2.close();
                                    var deleteDoms = t.$el.find('.member-unit-list-item-box.active');
                                    var ids = [];
                                    if (deleteDoms && deleteDoms.length > 0) {
                                        for (var i = 0; i < deleteDoms.length; i++) {
                                            var item = $(deleteDoms[i]);
                                            ids.push(item.attr("data-key"));
                                        }
                                        //TODO  调用接口，删除楼栋号
                                        tipsAlert.openLoading({
                                            content: '加载中...'
                                        });
                                        CommunityApi.deleteCommunityBuilding({
                                            ids: ids.join(','),
                                            communityId: t.community.id
                                        }, function(dataMap) {
                                            tipsAlert.close();
                                            //删除成功
                                            for (var i = 0; i < deleteDoms.length; i++) {
                                                var item = $(deleteDoms[i]);
                                                item.remove();
                                            }
                                            //回到列表页
                                            t.toEditView();
                                        }, function(code, msg) {
                                            tipsAlert.close();
                                            tipsAlert.openAlert({
                                                content: msg
                                            });
                                        });
                                    } else {
                                        tipsAlertV2.openAlert({
                                            content: '请先选择要删除的楼栋号'
                                        });
                                    }
                                },
                                onCancel: function(e) {
                                    tipsAlertV2.close();
                                }
                            });

                        }
                    }]
                });

                //设置默认事件
                t.detailListener();
                //刷新事件
                t.initEvents();
            },
            //改变底部按钮，是删除  还是 添加单元、保存按钮   返回参数是 表示是否是编辑界面
            changeBottomBtn: function() {
                var t = this;
                //delete按钮是否是影藏的
                if (t.bottom_delete_button_box && t.bottom_delete_button_box.isHidden()) {
                    //如果是影藏的，就显示删除按钮，影藏添加按钮
                    t.bottom_delete_button_box.setHidden(false);
                    t.bottom_group_button_box.setHidden(true);
                    return true;
                } else {
                    t.bottom_delete_button_box.setHidden(true);
                    t.bottom_group_button_box.setHidden(false);
                    return false;
                }
            },
            //跳转到编辑模式 或者非编辑模式
            toEditView: function() {
                var t = this;
                if (t.right_button_text === '编辑') {
                    t.right_button_text = '取消'
                } else {
                    t.right_button_text = '编辑'
                }
                t.$el.find('#header-container').find('.right-box').html(t.right_button_text);
                //改变按钮
                var toEditView = t.changeBottomBtn();
                if (toEditView) {
                    //说明是非编辑模式,点击转换成编辑模式
                    //添加编辑模式的样式
                    t.$el.find('.member-unit-list-item-box').addClass('edit');
                    t.editListener();
                } else {
                    t.$el.find('.member-unit-list-item-box').removeClass('edit');
                    t.detailListener();
                }
            },
            //非编辑模式下的点击事件
            detailListener: function() {
                var t = this;
                t.$el.find('.scrollBox').off('click', '.member-unit-list-item-box.edit');
                t.$el.find('.scrollBox').off('click', '.member-unit-list-item-box');
                t.$el.find('.scrollBox').on('click', '.member-unit-list-item-box', function(e) {
                    //TODO 应该是进入详情界面
                    router.navigate('', {
                        trigger: true
                    });
                });
            },
            //edit 监听
            editListener: function() {
                var t = this;
                t.$el.find('.scrollBox').off('click', '.member-unit-list-item-box');
                t.$el.find('.member-unit-list-item-box').off('click');
                t.$el.find('.member-unit-list-item-box.edit').off('click');
                t.$el.find('.member-unit-list-item-box.edit').on('click', function(e) {
                    //edit click event  换icon
                    var current = $(e.currentTarget);
                    if (current.hasClass('active')) {
                        current.removeClass('active');
                        current.find('.unit-list-right-icon').find('i').addClass('icon-longyantickhollow');
                        current.find('.unit-list-right-icon').find('i').removeClass('icon-longyantick');
                    } else {
                        current.addClass('active');
                        current.find('.unit-list-right-icon').find('i').removeClass('icon-longyantickhollow');
                        current.find('.unit-list-right-icon').find('i').addClass('icon-longyantick');
                    }
                });
            },
            //初始化监听器
            initEvents: function() {
                var t = this;
                t.$el.find('.updateLocation').on('click', function(e) {
                    e.preventDefault();
                    //TODO 调接口，返回地理数据，渲染界面
                });
            },
            destroy: function() {

            }

        });
        return LayoutView;
    });