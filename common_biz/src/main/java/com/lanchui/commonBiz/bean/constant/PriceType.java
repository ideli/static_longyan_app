package com.lanchui.commonBiz.bean.constant;

import com.xiwa.base.util.StringUtil;

/**
 * 价格模式  产品表中的价格模式
 * Created by bingcheng on 2015/5/18.
 */
public enum PriceType {

    Fixed("fixed"),//固定价格
    Discuss("discuss"),//面议
    Cal("cal");//参与公式计算

    private String type;

    private PriceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    /**
     * 获取枚举值
     * 默认返回固定价格
     *
     * @param type
     * @return
     */
    public static final PriceType getPriceType(String type) {
        if (StringUtil.isValid(type)) {
            PriceType[] fsTypes = PriceType.values();
            for (PriceType filetype : fsTypes) {
                if (filetype.getType().equals(type)) {
                    return filetype;
                }
            }
        }
        return PriceType.Fixed;
    }
}
