package com.chinaredstar.longyan.util;
import com.xiwa.base.bean.SimplePaginationDescribe;

import java.util.List;

/**
 * Created by niu on 2016/6/27.
 */
public class PageUtil  {
    public static SimplePaginationDescribe getSimplePagination(Integer page,Integer pageSize,Integer dataCount,List dataList){
        SimplePaginationDescribe simplePaginationDescribe = new SimplePaginationDescribe();
        simplePaginationDescribe.setCurrentPage(page);
        simplePaginationDescribe.setCurrentRecords(dataList);
        simplePaginationDescribe.setPageSize(pageSize);
        simplePaginationDescribe.setTotalRecords(dataList.size());
        simplePaginationDescribe.setTotalPages(dataCount%pageSize==0?dataCount/pageSize:dataCount/pageSize+1);
        return  simplePaginationDescribe;
    }

}
