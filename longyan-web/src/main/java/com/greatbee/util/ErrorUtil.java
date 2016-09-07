package com.greatbee.util;

import com.greatbee.bean.constant.LanchuiConstant;
import com.xiwa.base.bean.Response;

/**
 * Created by usagizhang on 16-7-16.
 */
public class ErrorUtil implements LanchuiConstant {
    public static void setErrMsg(Response res,String message) {
        res.setCode(HTTP_ERROR_CODE);
        res.setOk(Boolean.FALSE);
        res.setMessage(message);
    }
}
