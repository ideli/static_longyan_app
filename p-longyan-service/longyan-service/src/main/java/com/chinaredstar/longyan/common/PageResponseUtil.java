package com.chinaredstar.longyan.common;

import com.chinaredstar.longyan.api.vo.Response;
import org.springframework.data.domain.Page;

/**
 * Created by leiyun 2016/8/1.
 */
public class PageResponseUtil {
    /**
     * "currentRecordsNum":0,
     "totalPages":26,
     "totalRecords":129,
     "pageSize":5,
     "paginationDescribe":null
     * @param response
     */
    public static void setPage(Response response,Page page) {
        if(response != null) {
            response.addKey("totalPages",page.getTotalPages());
            response.addKey("totalRecords",page.getTotalElements());
            response.addKey("pageSize",page.getNumber());
        }
    }
}
