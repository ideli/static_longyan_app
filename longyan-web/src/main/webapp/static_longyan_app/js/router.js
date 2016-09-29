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
            "hybrid_landing_page/:data": "hybrid_landing_page",
            "login": "login", //登录
            //小区
            "community_list": "community_list",
            "community_info/:id": "community_info",
            "community_preview/:id": "community_preview",
            "community_create": "community_create",
            "community_create/:data": "community_create",
            "community_update/:id": "community_update",
            "community_update/:id/:data": "community_update",
            "community_update_exist/:id": "community_update_exist",
            "community_success": "community_success",
            "community_home/:id": "community_home",
            "building_list/:id": "building_list",
            "building_detail/:id": "building_detail",
            "building_create": "building_create",
            //附近的小区            
            "community_near_by/:status": "community_near_by",
            //我的小区
            "my_owner_community_list": "my_owner_community_list",
            //龙榜
            "integral_rank_list": "integral_rank_list",
            //龙榜 历史列表
            "integral_rank_list_history_champion": "integral_rank_list_history_champion",
            //消息中心
            "message_list/:source": "message_list",
            "message_list": "message_list",
            //我的->审核
            "my_review_list/:status": "my_review_list",
            "my_review_detail/:id": "my_review_detail",
            "my_submit": "my_submit",
            //我的->积分说明
            "integral_rule": "integral_rule",
            //我的->积分明细
            "integral_explain_list": "integral_explain_list",
            "integral_explain_detail": "integral_explain_detail",
            "feedback": "feedback",
            "feature_list": "feature_list",
            "help_list": "help_list",
            "help_detail": "help_detail"
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
        //hybrid中转页
        hybrid_landing_page: function(data) {
            var t = this;
            t.changePage('hybrid_landing_page', {
                data: data
            });
        },
        feature_list: function() {
            var t = this;
            window.debug = true;
            t.changePage('feature_list');
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


        community_home: function(id) {
            var t = this;
            t.changePage('community_home', {
                id: id
            });
        },
        //附近的小区(0=全部，1=可认领，2=可抢)
        community_near_by: function(status) {
            var t = this;
            t.changePage('community_near_by', {
                status: status
            });
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
        community_create: function(data) {
            var t = this;
            t.changePage('community_update', {
                action: 'create',
                data: data
            });
        },
        community_update: function(id, data) {
            var t = this;
            t.changePage('community_update', {
                action: 'update',
                id: id,
                data: data
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
        //楼栋
        building_list: function(id) {
            var t = this;
            t.changePage('building_list', {
                id: id
            });
        },
        building_detail: function(id) {
            var t = this;
            t.changePage('building_form', {
                id: id,
                info: true
            });
        },
        building_create: function() {
            var t = this;
            t.changePage('building_form', {
                create: true
            });
        },



        my_owner_community_list: function() {
            var t = this;
            t.changePage('my_owner_community_list');
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
        message_list: function(source) {
            var t = this;
            t.changePage('message_list', {
                source: source
            });
        },
        my_submit: function() {
            var t = this;
            t.changePage('my_review_list', {
                status: status
            });
        },
        my_review_list: function(status) {
            var t = this;
            t.changePage('my_review_list', {
                status: status
            });
        },
        my_review_detail: function(id) {
            var t = this;
            t.changePage('my_review_detail', {
                id: id
            });
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