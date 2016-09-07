/**
 *首页->关于->关于我们
 **/
define('js/longyan/view/aboutus_center', [
        'text!js/longyan/template/aboutus_center.tpl',
        'js/util/memory_cache',
        'js/components/alert_ui',
        'js/element/view/header',
        'js/element/view/menu-box',
        'js/element/view/list-box',
        'js/api/community'
    ],
    function(aboutuscenter, Cache, AlertUI, HeaderView, MenuBox, ListBox, CommunityApi) {
        var tipsAlert = tipsAlert || new AlertUI();
        var view_id = '#help-detail-view';
        var form_id = '#help-detail-form';
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
                t.isIos()   //判断手机终端信息

            },
            render: function() {
                var t = this;
                $('body').css('background-color', '#efeff4');

                var str = '<div class="aboutus_center_box"><ul>' +
                    tpl('<li><img src="<#=window.resource.image#>/aboutuscenter1.png" /></li>', {}) +
                    tpl('<li><img src="<#=window.resource.image#>/aboutuscenter2.png" /></li>', {}) +
                    tpl('<li class="telWay"><span><img src="<#=window.resource.image#>/aboutuscenter3.png" />longyan@chinaredstar.com</span></li>', {}) +
                    tpl('<li class="telWay"><a href="javascript:void(0)"><img src="<#=window.resource.image#>/aboutuscenter4.png" />400 6889 333</a></a></li>', {}) +
                    tpl('<li><img src="<#=window.resource.image#>/aboutuscenter6.png" /></li>', {}) +
                    '</ul></div>';

                t.$el.html(tpl(aboutuscenter, {
                    content: str
                }));

                t.header_view = new HeaderView({
                    el: $('#header-container')
                }, {
                    text: '关于我们',
                    goBackUrl: function() {
                        router.navigate('aboutus', {
                            trigger: true
                        });
                    }
                });
            },
            //初始化监听器
            initEvents: function() {
                var t = this;

            },
            isIos: function() {
                var t = this;
                var userAgentInfo = navigator.userAgent;
                var Agents = ["iPhone",
                    "iPad", "iPod"
                ];
                for (var v = 0; v < Agents.length; v++) {
                    if (userAgentInfo.indexOf(Agents[v]) > 0) {
                        $('.telWay a').attr('href','tel:4006889333'); 
                    }
                }
            }
        });
        return LayoutView;
    });