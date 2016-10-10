/**
 * 小区主页
 **/
define('js/longyan/view/community_home', [
        'text!js/longyan/template/community_home.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/location-view',
        'js/element/view/picker-box',
        'js/element/view/input-box',
        'js/element/view/think-input-box',
        'js/element/view/location-box',
        'js/element/view/input-percentage-box',
        'js/element/view/button-box',
        'js/element/view/link-box',
        'js/element/view/tips-bar',
        'js/api/community',
        'js/api/user',
        'js/util/hybrid'
    ],
    function(CommunityHomeTpl, Cache, AlertUI, HeaderView, LocationView, PickerBox, InputBox, ThinkInputBox, LocationBox, InputPercentageBox, ButtonBox, LinkBox, TipsBar, CommunityApi, UserApi, HybridApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#community-home-view';
        var form_id = '#community-home-form';
        var LayoutView = Backbone.View.extend({
            events: {
                "click .community-info": "_goto_update_community",
                "click .community-building-info": "_goto_building_list"
            },
            // 
            initialize: function(options, config) {
                var t = this;
                t.config = config || {};
                // alert('source=' + config.source);
                t.$el.off('click');
                t.render();
                t.loadData();
            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');
                t.$el.html(tpl(CommunityHomeTpl, {}));
                t.$el.find('#community-home-view').addClass('community-home-detail-view');

                //==========heander view==========                
                if (t.config.source && (t.config.source == 'near_by_community_map' || t.config.source == 'community_success')) {
                    //如果是从创建小区成功和附近的小区进来的,回到native首页
                    t.header_view = new HeaderView({
                        el: $('#header-container')
                    }, {
                        text: '载入中...',
                        goBackUrl: function() {
                            if (t.config.source == 'near_by_community_map') {
                                HybridApi.backToHybrid(null, 'direct');
                            } else {
                                HybridApi.backToHybrid("HomePage", 'direct');
                            }

                        }
                    });
                } else {
                    t.header_view = new HeaderView({
                        el: $('#header-container')
                    }, {
                        text: '载入中...'
                    });
                }

                t.community_owner = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-owner',
                    text: '管理员',
                    readonly: true,
                    // label_right: '<div class="owner-name"></div><div class="touxiang"><img src="' + window.resource.image + '/longyan_morentouxiang.png' + '" width="41" /></div><div class="clear-both"></div>'
                    // label_right: '<div class="owner-name"></div><div class="touxiang"><img src="data:image/jpeg;base64," width="100%" height="100%" /></div><div class="clear-both"></div>'
                    label_right: '<div class="owner-name"></div><div class="touxiang"></div><div class="clear-both"></div>'
                });


                // t.$el.find('.touxiang').css('background-image', 'url(data:image/gif;base64,' + image_base64 + ')');


                $('<div class="gap basic-gap owner-gap"></div>').appendTo($(form_id));

                //$('<hr>').appendTo($(form_id));
                t.community_info = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-info',
                    text: '小区信息',
                    readonly: true,
                    label_right: '<i class="iconfont">&#xe602;</i>'

                });

                t.community_building_info = new InputBox({
                    el: $(form_id)
                }, {
                    fieldName: 'community-building-info',
                    text: '楼栋信息',
                    readonly: true,
                    label_right: '<i class="iconfont">&#xe602;</i>'
                });
            },
            loadData: function() {
                var t = this;
                if (t.config.community_id) {
                    t.config.id = t.config.community_id;
                }
                if (t.config && t.config.id && t.config.id > 0) {
                    tipsAlert.openLoading({
                        content: '加载中...'
                    });

                    CommunityApi.getCommunityById(t.config.id, function(data) {
                        tipsAlert.close();
                        //success
                        console.log(data);
                        if (data && data.community) {
                            var data = data.community;
                            t.$el.find('#header-container').find('.title').html(data.name);
                            Cache.set('community-cache', data);
                            if (data && data.ownerId && data.ownerId > 0) {
                                t.$el.find('#community-home-form .owner-name').html(data.ownerXingMing);
                                t.laodPhoto(data.ownerId);
                            }

                        }

                    }, function(code, msg) {
                        tipsAlert.close();
                        //error
                        tipsAlert.openToast({
                            content: '系统异常'
                        });
                    });

                } else {
                    tipsAlert.openToast({
                        content: '数据异常'
                    });
                }

            },
            //加载员工图片
            laodPhoto: function(id) {
                var t = this;
                UserApi.getAvatar({
                    id: id
                }, function(data) {
                    console.log(data);
                    //加载头像
                    if (data && data.photo) {
                        t.$el.find('.touxiang').css('background-image', 'url(data:image/gif;base64,' + data.photo + ')');
                    }
                }, function(code, msg) {

                });
            },
            setData: function(data) {
                var t = this;
            },
            //跳转到小区编辑界面
            _goto_update_community: function() {
                var t = this;
                if (t.config && t.config.id && t.config.id > 0) {
                    window.location.href = '#community_update/' + t.config.id;
                } else {
                    tipsAlert.openToast({
                        content: '数据异常'
                    });
                }

            },
            //跳转到楼栋列表
            _goto_building_list: function() {
                var t = this;
                if (t.config && t.config.id && t.config.id > 0) {
                    window.location.href = '#building_list/' + t.config.id;
                } else {
                    tipsAlert.openToast({
                        content: '数据异常'
                    });
                }

            }
        });
        return LayoutView;
    });