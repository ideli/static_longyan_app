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

			//=========================================================================================================================
			//==================================================  已废弃  ==============================================================
			//=========================================================================================================================
			"index": "index", //主页  
			"reset": "reset", //找回密码
			"reset_pc": "reset_pc",
			"reset_success_pc": "reset_success_pc",
			"change_password": "change_password", //修改密码
			"user_center": "user_center", //用户信息
			"user_info": "user_info", //用户信息
			"community_create_by_member": "community_create_by_member",

			//other
			"search/:id": "search_view",
			"aboutus": "aboutus", //关于我们
			"aboutus_center": "aboutus_center", //关于我们
			"help_list": "help_list", //帮助中心
			"feedback": "feedback", //意见反馈
			"feedback_textarea": "feedback_textarea", //意见反馈输入
			"feedback_success": "feedback_success", //意见反馈成功
			"help_detail": "help_detail",

			//注册
			"reg_employee_step1": "reg_employee_step1",
			"reg_employee_step2": "reg_employee_step2",
			"reg_employee_step3": "reg_employee_step3",
			"reg_employee_step1_pc": "reg_employee_step1_pc",
			"reg_employee_step2_pc": "reg_employee_step2_pc",
			"reg_employee_step3_pc": "reg_employee_step3_pc",
			"reg_user_step1": "reg_user_step1",
			"reg_user_step2": "reg_user_step2",
			//商场员工角色
			"shopping_mall": "shopping_mall",
			"shopping_mall_employee": "shopping_mall_employee",
			"employee_input_dashboards": "employee_input_dashboards",
			"employee_input_dashboards_by_id/:id": "employee_input_dashboards_by_id",

			"employee_input_rank_list": "employee_input_rank_list", //员工排行
			"shopping_mall_input_rank_list": "shopping_mall_input_rank_list", //商场排行

			//报表
			"report_corporation": "report_corporation",
			"report_area_list": "report_area_list",
			"report_area_by_id/:id": "report_area_by_id",
			"report_mall_list_by_area/:id": "report_mall_list",
			"report_shopping_mall_by_id/:id": "report_shopping_mall_by_id",
			"report_employee_list_by_mall/:id": "report_employee_list_by_mall",
			"report_employee_by_id/:id": "report_employee",
			"report_community_list/:id": 'report_community_list',
			"report_community/:id": "report_community",



			//通讯录
			"contact_book": "contact_book",
			"contact_list": "contact_list",
			"contact_detail/:id": "contact_detail",

			//住宅
			"member_info/:id": "member_info",
			"member_update/:id": "member_update",
			"member_create": "member_create",
			"member_create_step2": "member_create_step2",
			"member_create_by_commonity": "member_create_by_commonity",
			"member_create_next": "member_create_next",
			//房屋信息
			"member_list": "member_list",
			"member_unit": "member_unit",

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
		reset: function() {
			var t = this;
			t.changePage('reset');
		},
		reset_pc: function() {
			var t = this;
			t.changePage('reset_pc');
		},
		reset_success_pc: function() {
			var t = this;
			t.changePage('reset_success_pc');
		},

		change_password: function() {
			var t = this;
			t.changePage('change_password');
		},
		user_center: function() {
			var t = this;
			t.changePage('user_center');
		},
		user_info: function() {
			var t = this;
			t.changePage('user_info');
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
		community_preview: function(id) {
			var t = this;
			t.changePage('community_info', {
				action: 'preview',
				id: id
			});
		},
		community_create: function() {
			var t = this;
			t.changePage('community_update', {
				action: 'create'
			});
		},
		community_create_by_member: function() {
			var t = this;
			t.changePage('community_update', {
				action: 'community_create_by_member'
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



		//=========================================================================================================================
		//==================================================  已废弃  ==============================================================
		//=========================================================================================================================
		member_list: function() {
			var t = this;
			t.changePage('member_list');
		},
		member_info: function(id) {
			var t = this;
			t.changePage('member_info', {
				info: true,
				id: id
			});
		},
		member_create: function() {
			var t = this;
			t.changePage('member_form', {
				create: true
			});
		},
		member_create_step2: function() {
			var t = this;
			t.changePage('member_form', {
				create: true,
				step: 2
			});
		},
		member_create_by_commonity: function() {
			var t = this;
			t.changePage('member_form', {
				create: true,
				load_community: true
			});
		},
		member_create_next: function() {
			var t = this;
			t.changePage('member_form', {
				create: true,
				load_community: true
			});
		},
		member_update: function(id) {
			var t = this;
			t.changePage('member_form', {
				update: true,
				id: id
			});
		},
		member_unit: function() {
			var t = this;
			t.changePage('member_unit');
		},
		member_unit_list: function() {
			var t = this;
			t.changePage('member_unit_list');
		},
		member_build: function() {
			var t = this;
			t.changePage('member_build');
		},
		member_build_add: function() {
			var t = this;
			t.changePage('member_build_add');
		},
		member_build_edit: function() {
			var t = this;
			t.changePage('member_build_edit');
		},
		member_build_modify: function() {
			var t = this;
			t.changePage('member_build_modify');
		},
		task_list: function() {
			var t = this;
			t.changePage('task_list');
		},
		task_create: function() {
			var t = this;
			t.changePage('task_info', {
				action: 'create'
			});
		},
		reg_employee_step1_pc: function() {
			var t = this;
			t.changePage('reg_employee_step1_pc');
		},
		reg_employee_step2_pc: function() {
			var t = this;
			t.changePage('reg_employee_step2_pc');
		},
		reg_employee_step3_pc: function() {
			var t = this;
			t.changePage('reg_employee_step3_pc');
		},
		reg_employee_step1: function() {
			var t = this;
			t.changePage('reg_employee_step1');
		},
		reg_employee_step2: function() {
			var t = this;
			t.changePage('reg_employee_step2');
		},
		reg_employee_step3: function() {
			var t = this;
			t.changePage('reg_employee_step3');
		},
		reg_user_step1: function() {
			var t = this;
			t.changePage('reg_user_step1');
		},
		reg_user_step2: function() {
			var t = this;
			t.changePage('reg_user_step2');
		},
		shopping_mall: function() {
			var t = this;
			t.changePage('shopping_mall');
		},
		shopping_mall_employee: function() {
			var t = this;
			t.changePage('shopping_mall_employee');
		},
		employee_input_dashboards: function() {
			var t = this;
			t.changePage('employee_input_dashboards');
		},
		employee_input_dashboards_by_id: function(id) {
			var t = this;
			t.changePage('employee_input_dashboards', {
				id: id
			});
		},
		employee_input_rank_list: function() {
			var t = this;
			t.changePage('employee_input_rank_list');
		},
		shopping_mall_input_rank_list: function() {
			var t = this;
			t.changePage('shopping_mall_input_rank_list');
		},
		report_corporation: function() {
			var t = this;
			t.changePage('report_corporation');
		},
		report_area_list: function() {
			var t = this;
			t.changePage('report_area_list');
		},
		report_area_by_id: function(id) {
			var t = this;
			t.changePage('report_area', {
				id: id
			});
		},
		report_mall_list: function(id) {
			var t = this;
			t.changePage('report_mall_list', {
				id: id
			});
		},
		report_shopping_mall_by_id: function(id) {
			var t = this;
			t.changePage('report_shopping_mall', {
				id: id
			});
		},
		report_employee_list_by_mall: function(id) {
			var t = this;
			t.changePage('report_employee_list', {
				id: id
			});
		},
		report_employee: function(id) {
			var t = this;
			t.changePage('report_employee', {
				id: id
			});
		},
		report_community_list: function(employee_id) {
			var t = this;
			t.changePage('community_list', {
				employee_id: employee_id
			});
		},
		report_community: function(id) {
			var t = this;
			t.changePage('community_info', {
				id: id,
				report_detail: true
			});
		},

		contact_book: function() {
			var t = this;
			t.changePage('contact_book');

		},
		contact_list: function() {
			var t = this;
			t.changePage('contact_list');
		},
		contact_detail: function(id) {
			var t = this;
			t.changePage('contact_detail', {
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