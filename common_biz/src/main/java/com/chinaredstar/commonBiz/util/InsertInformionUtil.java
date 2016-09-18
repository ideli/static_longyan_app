package com.chinaredstar.commonBiz.util;

import com.xiwa.base.bean.SimplePaginationDescribe;

import java.util.List;

/**
 * Created by LeiYun on 2016/6/23.
 */
public class InsertInformionUtil {

    public static SimplePaginationDescribe InsertInformion(int page,int pageSize,int totalRecords,List resultList){

        SimplePaginationDescribe Result = new SimplePaginationDescribe();

        Result.setCurrentPage(page);
        Result.setCurrentRecords(resultList);
        Result.setPageSize(pageSize);
        Result.setTotalRecords(totalRecords);

        int totalPages = totalRecords % pageSize == 0 ? totalRecords/pageSize : totalRecords/pageSize+1;

        Result.setTotalPages(totalPages);
       // Result.setTotalPages((int) Math.ceil(totalRecords / (double) pageSize));

        return Result;

    }



}
