package com.chinaredstar.longyan.service;

import com.chinaredstar.longyan.api.vo.Response;

/**
 * Created by mdc on 2016/7/31.
 */
public class BasicService {

    public Response response;

    final static int SUCCESS_CODE = 200;

    final static int EXCEPTION_CODE = 500;

    public void successResponse(String msg) {
        this.response = response == null ? new Response() : response;
        response.setOk(true);
        response.setCode(SUCCESS_CODE);
        response.setMessage(msg);
        response.clearContent();
    }

    public void errorResponse(int code, String msg) {
        this.response = response == null ? new Response() : response;
        response.setOk(false);
        response.setCode(code);
        response.setMessage(msg);
        response.clearContent();
    }
}
