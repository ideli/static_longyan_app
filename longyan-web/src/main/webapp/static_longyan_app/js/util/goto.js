/**
 * 跳转工具类 Util
 * @return {[type]} [description]
 */
define('js/util/goto', [], function() {
	var GotoUtil = {
		initialize: function() {},

		//监听器
		initListener: function(t) {
			t.$el.on('error', function(e, msg) {
				_log(msg);
				alert(msg);
			});
			t.$el.on('success', function(e, url) {
				_log('success');
				router.navigate(url, {
					trigger: true
				});
			});
		}


	};
	return GotoUtil;
});