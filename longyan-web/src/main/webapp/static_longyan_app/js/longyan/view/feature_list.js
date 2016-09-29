/**
 * 小区主页
 **/
define('js/longyan/view/feature_list', [
        'text!js/longyan/template/community_home.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/input-box',
        'js/element/view/button-box'
    ],
    function(CommunityHomeTpl, Cache, AlertUI, HeaderView, InputBox, ButtonBox) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#community-home-view';
        var form_id = '#community-home-form';
        var LayoutView = Backbone.View.extend({
            events: {
                "click .community-building-info": "_goto_building_list"
            },
            // 
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                t.$el.off('click');
                t.render();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(CommunityHomeTpl, {}));
                t.$el.find('#community-home-view').addClass('community-home-detail-view');

                //==========heander view==========
                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '功能项列表'
                });

                //$('<hr>').appendTo($(form_id));
                t.my_community = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'my-community',
                    text: '我的小区',
                    readonly: true,
                    label_right: '<a href="#my_owner_community_list"><i class="iconfont">&#xe602;</i></a>'

                });
                t.my_review = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'my-review',
                    text: '我的审核',
                    readonly: true,
                    label_right: '<a href="#my_review_list/0"><i class="iconfont">&#xe602;</i></a>'
                });
                t.my_submit = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'my-submit',
                    text: '我的提交',
                    readonly: true,
                    label_right: '<a href="#my_submit"><i class="iconfont">&#xe602;</i></a>'
                });
                t.my_jifen = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'my-jifen',
                    text: '我的积分',
                    readonly: true,
                    label_right: '<a href="#integral_explain_list"><i class="iconfont">&#xe602;</i></a>'
                });


                t.community_near_by = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-near-by',
                    text: '周边小区(未实装)',
                    readonly: true,
                    label_right: '<a href="#integral_explain_list"><i class="iconfont">&#xe602;</i></a>'
                });

                t.longbang = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'longbang',
                    text: '龙榜',
                    readonly: true,
                    label_right: '<a href="#integral_rank_list"><i class="iconfont">&#xe602;</i></a>'
                });

                t.message_list = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'message-list',
                    text: '消息列表',
                    readonly: true,
                    label_right: '<a href="#message_list"><i class="iconfont">&#xe602;</i></a>'
                });

                t.feedback = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'feedback',
                    text: '吐槽',
                    readonly: true,
                    label_right: '<a href="#feedback"><i class="iconfont">&#xe602;</i></a>'
                });
                t.question = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'question',
                    text: '常见问题',
                    readonly: true,
                    label_right: '<a href="#help_list"><i class="iconfont">&#xe602;</i></a>'
                });

            },
            //跳转到我的小区
            _goto_my_community: function() {
                var t = this;
                window.location.href = '#my_owner_community_list/';
            }
        });
        return LayoutView;
    });