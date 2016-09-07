package com.lanchui.commonBiz.bean.constant;

/**
 * 校验类别
 * Created by bingcheng on 2015/9/16.
 */
public enum VerifyType {

    Verify("verify"),//已验证
    Refuse("refuse"),//验证不通过
    Verifying("verifying"),//正在验证
    Unverify("unverify");//待验证

    private String type;

    private VerifyType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
