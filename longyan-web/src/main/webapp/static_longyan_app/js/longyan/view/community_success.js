/**
 * 首页 -> 社区列表
 **/
define('js/longyan/view/community_success', [
        'text!js/longyan/template/community_success.tpl',
        'text!js/longyan/template/no_data.tpl',
        'text!js/longyan/template/no_network.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/button-box',
        'js/util/hybrid'
    ],
    function(CommunitySuccessTpl, NoDataTpl, NoNetworkTpl, Cache, AlertUI, HeaderView, ButtonBox, hybrid) {
        var tipsAlert = tipsAlert || new AlertUI();
        var LayoutView = Backbone.View.extend({
            events: {

            },
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
                t.$el.html(tpl(CommunitySuccessTpl, {}));

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '小区创建成功',
                    goBackUrl: function() {
                        //我的小区列表
                        hybrid.backToHybrid("HomePage", "direct");
                    }
                });
                t.add_floor_number = new ButtonBox({
                    el: $('#skipPage-btn')
                }, {
                    fieldName: 'add-floor-number',
                    text: '添加楼栋号'
                }, {
                    Click: function(e) {
                        var dataId = Cache.get('community-data-id');
                        router.navigate('building_list/' + dataId, {
                            trigger: true
                        });
                    }
                });
                t.get_into_community = new ButtonBox({
                    el: $('#skipPage-btn')
                }, {
                    fieldName: 'get-into-community',
                    text: '小区主页'
                }, {
                    Click: function(e) {
                        var dataId = Cache.get('community-data-id');
                        router.navigate('community_home/community_success/' + dataId, {
                            trigger: true
                        });
                    }
                });
                var header_view = parseInt($(window).height() - $('#header-container').height());
                $('#community-success-list-view').height(header_view);
            },
            //初始化监听器
            initEvents: function() {
                var t = this;
            },
            destroy: function() {
                $(window).off('scroll');
                //this.list_box.removeEvent();
            }
        });
        return LayoutView;
    });