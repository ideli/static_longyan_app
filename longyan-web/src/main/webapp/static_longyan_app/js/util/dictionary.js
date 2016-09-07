define('js/util/dictionary', [], function() { //定义字典类型的常量数据
	$nvwa.dictionary = {
		storage_url: window._nvwaAPI + '/community/common/storage/',
		home_url: window._nvwaAPI,
		order_status_finish: 'Finish',
		order_status_not_finish: 'Not_Finish',
		appid: 'wxcb92f114c4411c42',
		// billingAttributes = {
		// 	xjgl: '夏季供冷',
		// 	djgr: '冬季供热',
		// 	shgs: '生活供水',
		// 	czft: '"垂直电梯',
		// 	zdft: '自动扶梯',
		// 	jxck: '机械车库',
		// 	afxfgd: '安防/消防供电',
		// 	lczm: '楼层照明',
		// 	ngwt: '内光外透'
		// },
		redirect_url: 'http://test-user.lanchui.com'
			//	redirect_url: 'http://weixin-dev2.usagilab.com'
	};
	return $nvwa.dictionary;
});