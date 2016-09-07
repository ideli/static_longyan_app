package com.lanchui.commonBiz.bean.constant;

/**
 * 订单支付方式
 * Created by bingcheng on 2015/5/18.
 */
public enum PayType {

    Weixin("pay_weixin"),//微信支付
    Cash("pay_cash"),//现金支付
    Alipay("pay_ali"),//支付宝支付
    Wopay("pay_wo"),//联通支付（沃）
    CommunityBalance("balance"),//仅余额支付
    CommunityAlipayBalance("alipay_balance"),//支付宝+余额
    CommunityWeixinBalance("weixin_balance");//微信+余额


    private String type;

    private PayType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }
}
