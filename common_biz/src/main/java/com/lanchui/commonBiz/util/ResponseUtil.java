package com.lanchui.commonBiz.util;

import com.lanchui.commonBiz.bean.constant.Constant;
import com.xiwa.base.bean.Response;

/**
 * Created by LeiYun on 2016/5/31.
 */
public class ResponseUtil {

    public static void setErrMsg(Response res,String message) {
        res.setCode(Constant.Http_Req_Error_Code);
        res.setOk(Boolean.FALSE);
        res.setMessage(message);
    }

    public static void setSuccessMsg(Response res,String message) {
        res.setCode(Constant.Http_Req_Success_Code);
        res.setOk(Boolean.TRUE);
        if(message==null){
            res.setMessage(Constant.Http_Req_Success_Message);
        }else{
            res.setMessage(message);
        }

    }
}
