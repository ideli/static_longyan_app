/**
 * 判断客户端工具类 Util
 * @return {[type]} [description]
 */
define('js/util/judgeClickEvent', [], function() {
	var JudgeClickEvent = {
		initialize: function() {},

		//判断是click时间还是touch事件
		isClickEvent: function() {
			if (_userAgent == 'PC') {
				return 'click';
			} else {
				return 'click';
			}
		}
	};
	return JudgeClickEvent;
});