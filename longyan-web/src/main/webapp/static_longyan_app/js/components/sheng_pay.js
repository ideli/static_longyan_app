/*
 * 盛付通组件 
 */

define('js/components/sheng_pay', ['js/api/pay'], function(PayApi) {

    var util = {
        //跳转到盛付通
        gotoShengPay: function(bankCode, serialNumber) {
            var t = this;
            t.getShengPayParam(bankCode, serialNumber, function(data) {
                t.submitShengPayForm(data);
            });
        },
        //通过post表单的形式跳转到盛付通的支付流程页面
        submitShengPayForm: function(orderPayParam) {
            var t = this;

            // 生成表单，用post方式提交
            var chinapayForm = $("<form method='get' action=" + 'https://mas.shengpay.com/web-acquire-channel/cashier.htm' + "></form>");
            var NameInput = $("<input type='hidden' name='Name' value = " + orderPayParam.Name + ">");
            chinapayForm.append(NameInput);
            var VersionInput = $("<input type='hidden' name='Version' value = " + orderPayParam.Version + ">");
            chinapayForm.append(VersionInput);
            var CharsetInput = $("<input type='hidden' name='Charset' value = " + orderPayParam.Charset + ">");
            chinapayForm.append(CharsetInput);
            var MsgSenderInput = $("<input type='hidden' name='MsgSender' value = " + orderPayParam.MsgSender + ">");
            chinapayForm.append(MsgSenderInput);
            var SendTimeInput = $("<input type='hidden' name='SendTime' value = " + orderPayParam.SendTime + ">");
            chinapayForm.append(SendTimeInput);
            var OrderNoInput = $("<input type='hidden' name='OrderNo' value = " + orderPayParam.OrderNo + ">");
            chinapayForm.append(OrderNoInput);
            var OrderAmountInput = $("<input type='hidden' name='OrderAmount' value = " + orderPayParam.OrderAmount + ">");
            chinapayForm.append(OrderAmountInput);
            var OrderTimeInput = $("<input type='hidden' name='OrderTime' value = " + orderPayParam.OrderTime + ">");
            chinapayForm.append(OrderTimeInput);
            var PayTypeInput = $("<input type='hidden' name='PayType' value = " + orderPayParam.PayType + ">");
            chinapayForm.append(PayTypeInput);
            var PayChannelInput = $("<input type='hidden' name='PayChannel' value = " + orderPayParam.PayChannel + ">");
            chinapayForm.append(PayChannelInput);
            var InstCodeInput = $("<input type='hidden' name='InstCode' value = " + orderPayParam.InstCode + ">");
            chinapayForm.append(InstCodeInput);
            var PageUrlInput = $("<input type='hidden' name='PageUrl' value = " + orderPayParam.PageUrl + ">");
            chinapayForm.append(PageUrlInput);
            var BackUrlInput = $("<input type='hidden' name='BackUrl' value = " + orderPayParam.BackUrl + ">");
            chinapayForm.append(BackUrlInput);
            var NotifyUrlInput = $("<input type='hidden' name='NotifyUrl' value = " + orderPayParam.NotifyUrl + ">");
            chinapayForm.append(NotifyUrlInput);
            var ProductNameInput = $("<input type='hidden' name='ProductName' value = " + orderPayParam.ProductName + ">");
            chinapayForm.append(ProductNameInput);
            var BuyerContactInput = $("<input type='hidden' name='BuyerContact' value = " + orderPayParam.BuyerContact + ">");
            chinapayForm.append(BuyerContactInput);
            var BuyerIpInput = $("<input type='hidden' name='BuyerIp' value = " + orderPayParam.BuyerIp + ">");
            chinapayForm.append(BuyerIpInput);
            var Ext1Input = $("<input type='hidden' name='Ext1' value = " + orderPayParam.Ext1 + ">");
            chinapayForm.append(Ext1Input);
            var SignTypeInput = $("<input type='hidden' name='SignType' value = " + orderPayParam.SignType + ">");
            chinapayForm.append(SignTypeInput);
            var SignMsgInput = $("<input type='hidden' name='SignMsg' value = " + orderPayParam.SignMsg + ">");
            chinapayForm.append(SignMsgInput);

            chinapayForm.submit();
        },
        //获取订单参数
        getShengPayParam: function(bankCode, serialNumber, handler) {
            //获取盛付通的订单参数
            PayApi.getShengPayParam({
                // bankCode: 'SZPAB',
                // serialNumber: 'O-20160329-5986'
                bankCode: bankCode,
                serialNumber: serialNumber
            }, function(data) {
                //SUCCESS
                console.log(data);
                if (data && data.payParam && handler) {
                    handler(data.payParam);
                }
            }, function() {
                //ERROR
                alert('获取订单信息失败');
            })
        }
    };
    return util;
});