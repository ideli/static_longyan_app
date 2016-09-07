/**
 * 订单状态  英文换中文 工具 Util
 * @return {[type]} [description]
 */
define('js/util/orderStatusUtil', [], function() {
	var orderStatus = {
		initialize: function() {},

		//获取订单状态中文
		getOrderStatusCh: function(status) {
			if (status) {
				switch (status) {
					case 'New':
						return '新建';
					case 'Waiting_Distribute':
						return '等待派单';
					case 'Waiting_RE_Distribute':
						return '等待重新派单';
					case 'Waiting_Worker_Confirm':
						return '等待工人确定';
					case 'Waiting_Worker_On_Board':
						return '等待工人上门';
					case 'Waiting_Worker_Finish':
						return '等待工人完工';
					case 'Waiting_Visit':
						return '等待回访';
					case 'Waiting_Settlement':
						return '等待结算';
					case 'Refuse':
						return '退单';
					case 'Finish':
						return '已完成';
					case 'Appointment':
						return '预约中';
					case 'In_Settlement':
						return '结算中';
					case 'User_Cancel':
						return '已取消';
					case 'Challenging':
						return '抢单中';
					case 'Deleted':
						return '已删除';
					case 'Waiting_User_Payment':
						return '等待付款';
					case 'Waiting_Delivery':
						return '等待配送';
					case 'Waiting_Take':
						return '等待收货';
					case 'Waiting_Evaluation':
						return '等待评价';
					default:
						return '';
				}
			} else {
				return '';
			}
		},
		//获取订单支付方式
		getPayTypeName: function(status) {
			if (status) {
				switch (status) {
					case 'pay_weixin':
						return '微信支付';
					case 'pay_cash':
						return '现金支付';
					case 'pay_ali':
						return '支付宝';
					case 'pay_sheng':
						return '盛付通';
					case 'balance':
						return '余额支付';
					default:
						return '';
				}
			} else {
				return '';
			}
		},
		//获取订单支付完成名称
		getPaymentStatusName: function(status) {
			if (status) {
				switch (status) {
					case 'Unpay':
						return '未支付';
					case 'Payed':
						return '已支付';
					default:
						return '';
				}
			} else {
				return '';
			}
		},
		//
		getTradingType: function(type) {
			if (type) {
				switch (type) {
					case 'buy':
						return '购买';
					case 'split':
						return '分佣';
					case 'recharge':
						return '充值';
					case 'withdrawal':
						return '取现';
					case 'activity_award':
						return '活动奖励';
					case 'refund':
						return '退款';
					default:
						return '';
				}
			} else {
				return '';
			}
		},
		getTradingStatusName: function(type) {
			if (type) {
				switch (type) {
					case 'InProgress':
						return '进行中';
					case 'SUCCESS':
						return '成功';
					case 'FAIL':
						return '失败';
					default:
						return '';
				}
			} else {
				return '';
			}
		}


	};
	return orderStatus;
});