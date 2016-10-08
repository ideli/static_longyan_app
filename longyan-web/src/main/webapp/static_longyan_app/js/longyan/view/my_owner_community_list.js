/**
 *my owner community list view
 *我负责的小区
 **/
define('js/longyan/view/my_owner_community_list', [
        'text!js/longyan/template/my_owner_community_list.tpl',
        'text!js/longyan/template/my_owner_community_list_item.tpl',
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
    function(ListContailerTpl, OwnerCommunityListItemTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox, LinkBox, TipsBar, ListBox, CommunityApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#my-owner-community-list-view';
        var form_id = '#my-owner-community-list-form';
        var LayoutView = Backbone.View.extend({
            events: {
                'click .item-box': '_clickItem',
            },
            //相关介绍
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
                t.$el.html(tpl(ListContailerTpl, {config: t.config}));
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '我的小区'
                });

                var i = 1;
                var mallId = t.config.id;
                t.list_box = new ListBox({
                    el: $('#my-owner-community-list-box')
                }, {
                    scroll: false //支持下拉刷新
                }, {
                    loadData: function(page, handler) {
                        tipsAlert.openLoading({
                            content:"加载中"
                        });

                        var _request_type=['inChargeCommunity', 'updateCommunity'];
                        CommunityApi.myCommunityList({
                            queryType : _request_type[t.config.status]
                        },function(data){
                            if(data&&data.result){
                                tipsAlert.close();
                            }
                        },function(code, msg){
                            tipsAlert.close();
                            tipsAlert.openAlert({
                                content: msg
                            });
                        });
                    },
                    appendItem: function(data) {
                        console.log(data);

                        return tpl(OwnerCommunityListItemTpl, {
                            data: item
                        });
                    }
                });
            },

            //初始化监听器
            initEvents: function() {
                var t = this;
            },

            _clickItem : function(e) {
                var t = this;
                var index = $(e.currentTarget).attr('index');
                if (index != t.config.status) {
                    window.location.href = '#my_owner_community_list/' + index;
                } else {
                    console.log('no action');
                }
            },

            // _clickToAnother : function(e){
            //     //跳转页面
            //     var t = this;
            //     var id = $(e.currentTarget).attr('data-id') || 0;
            //     Community_myApi.inChargeCommunity({
            //
            //     })
            // },

            destroy: function() {
                $(window).off('scroll');
            }
        });
        return LayoutView;
    });