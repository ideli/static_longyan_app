/**
 * Router
 * @return {[type]} [description]
 */
define('router', ['js/longyan/view/layout'], function(LayoutView) {
    var Router = Backbone.Router.extend({
        routes: {
            //首页
            "simple": "simple",
            "": "login",

            "login": "login", //登录
            //小区
            "community_list": "community_list",
            "community_list_setup": "community_list_setup",
            "community_info/:id": "community_info",
            "community_preview/:id": "community_preview",
            "community_create": "community_create",
            "community_update/:id": "community_update",
            "community_update_exist/:id": "community_update_exist",
            "community_success": "community_success",


            "community_build": "member_build",
            "community_build_add": "member_build_add",
            "community_build_edit": "member_build_edit",
            "community_build_modify": "member_build_modify",

            //楼栋
            "community_build_add": "community_build_add",
            //龙榜
            "integral_rank_list": "integral_rank_list",
            //龙榜 历史列表
            "integral_rank_list_history_champion": "integral_rank_list_history_champion",
            //积分明细
            "integral_explain_list": "integral_explain_list",
            "integral_explain_detail": "integral_explain_detail",
            //积分说明
            "integral_rule": "integral_rule",

        },

        //初始化布局
        initialize: function() {
            // this._init();
        },
        pageStack: {},
        removeView: function() {
            var t = this;
            // if (!!t.pageStack) {
            //     for (var item in t.pageStack) {
            //         t.pageStack[item].remove();
            //         // t.pageStack[item].stopListening();
            //     }
            // }
            if (t.indexView && t.indexView.destroy) {
                //销毁
                t.indexView.destroy();
            }
        },
        changePage: function(view_id, config) {
            var t = this;
            config = config || {};
            t.removeView();
            requirejs(["js/longyan/view/" + view_id], function(IndexView) {
                var container = document.body;
                t.indexView = new IndexView({
                    el: container,
                    routes: t
                }, config, {}, null, false);
                t.pageStack[view_id] = t.indexView;
            });
        },
        //index
        index: function() {
            var t = this;
            t.removeView();
            // t._init();
            // $('#pg_box') && $('#pg_box').remove();
            // var container = '<div id="view"></div>';
            // $('.pageContainer').append(container);
            requirejs(["js/longyan/view/index"], function(IndexView) {
                //list oi
                var container = document.body;
                t.indexView = new IndexView({
                    el: container,
                    routes: t
                }, {}, {}, null, false);
                t.pageStack['index'] = t.indexView;
            });
        },
        simple: function() {
            var t = this;
            t.changePage('simple');
        },
        login: function() {
            var t = this;

            t.changePage('login');
            //根据PC和移动端自动切换登陆页面
            // if (t._isPC()) {
            //     //PC
            //     t.changePage('login_pc');
            // } else {
            //     //mobile
            //     t.changePage('login');
            // }
        },

        community_list: function() {
            var t = this;
            t.changePage('community_list');
        },
        community_list_setup: function() {
            var t = this;
            t.changePage('community_list_setup');
        },
        community_info: function(id) {
            var t = this;
            t.changePage('community_info', {
                action: 'info',
                id: id
            });
        },
        community_create: function() {
            var t = this;
            t.changePage('community_update', {
                action: 'create'
            });
        },
        community_update: function(id) {
            var t = this;
            t.changePage('community_update', {
                action: 'update',
                id: id
            });
        },
        community_update_exist: function(id) {
            var t = this;
            t.changePage('community_update', {
                action: 'update',
                update_exist: true,
                id: id
            });
        },
        community_success: function() {
            var t = this;
            t.changePage('community_success');
        },
        community_build_add: function() {
            var t = this;
            t.changePage('community_build_add');
        },

        search_view: function(alias) {
            var t = this;
            t.changePage('think_search', {
                alias: alias
            });
        },
        aboutus: function() {
            var t = this;
            t.changePage('aboutus');
        },
        aboutus_center: function() {
            var t = this;
            t.changePage('aboutus_center');
        },
        help_list: function() {
            var t = this;
            t.changePage('help_list');
        },
        feedback: function() {
            var t = this;
            t.changePage('feedback');
        },
        feedback_textarea: function() {
            var t = this;
            t.changePage('feedback_textarea');
        },
        feedback_success: function() {
            var t = this;
            t.changePage('feedback_success');
        },
        help_detail: function() {
            var t = this;
            t.changePage('help_detail');
        },
        integral_rank_list: function() {
            var t = this;
            t.changePage('integral_rank_list');
        },
        integral_rank_list_history_champion: function() {
            var t = this;
            t.changePage('integral_rank_list_history_champion');
        },
        integral_explain_list: function() {
            var t = this;
            t.changePage('integral_explain_list');
        },
        integral_explain_detail: function() {
            var t = this;
            t.changePage('integral_explain_detail');
        },
        integral_rule: function() {
            var t = this;
            t.changePage('integral_rule');
        },



        _isPC: function() {
            var t = this;
            var userAgentInfo = navigator.userAgent;
            var Agents = ["Android", "iPhone",
                "SymbianOS", "Windows Phone",
                "iPad", "iPod"
            ];
            var flag = true;
            for (var v = 0; v < Agents.length; v++) {
                if (userAgentInfo.indexOf(Agents[v]) > 0 || t._isWeixin()) {
                    flag = false;
                    break;
                }
            }
            return flag;
        },
        _isWeixin: function() {
            var ua = navigator.userAgent.toLowerCase();
            if (ua.match(/MicroMessenger/i) == "micromessenger") {
                return true;
            } else {
                return false;
            }
        },
    });
    return Router;
});