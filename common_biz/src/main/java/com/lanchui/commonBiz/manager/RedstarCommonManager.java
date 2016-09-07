package com.lanchui.commonBiz.manager;

import com.lanchui.commonBiz.bean.RedstarCommunity;
import com.xiwa.base.bean.Identified;
import com.xiwa.base.bean.PaginationDescribe;
import com.xiwa.base.bean.SimplePaginationDescribe;
import com.xiwa.base.bean.search.SearchBean;
import com.xiwa.base.manager.BasicManager;
import com.xiwa.base.manager.ManagerException;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by lenovo on 2016/5/5.
 */
public interface RedstarCommonManager extends BasicManager {

    public List getDataList(Class beanClass) throws ManagerException;

    public List getDataList(Class beanClass, SearchBean searchBean) throws ManagerException;

    public Identified getDataById(int id, Class _thisClass) throws ManagerException;

    public Identified getDataById(int id, String beanName) throws ManagerException;

    public void addData(Object object) throws ManagerException;

    public void updateData(Object object) throws ManagerException;

    //查询sql
    public List<Object[]> queryBySql(String sql) throws ManagerException;

    public List queryUniqueBySql(String sql) throws ManagerException;

    //执行sql
    public int excuteBySql(String sql) throws ManagerException;

    public List excuteBySql(String sql,List<Object> paramList) throws ManagerException;

    public Integer getCountBySql(String sql,List<Object> paramList) throws ManagerException;


    public List  getDayList(String sortColumn,Boolean desc,Date beginDay,Integer limit);


    public Integer getHistoryUserCount();

    public List getDayReportDataList(Class beanClass,SearchBean searchBean,Integer limit);

    //多字段排序
    public List getDataList(Class c,SearchBean searchBean,String[] orderColumns,Boolean[] orders);

    public SimplePaginationDescribe getIdentifiedPageList(Class c,String selectColumnNames,String sql,String orderByColumns, List<Object> paramList, Integer page, Integer pageSize) throws ManagerException;

    //批量更新
    public void batchUpdateIdentified(Class c,List<Object> objects) throws ManagerException;

    public List getDataList(Class c, SearchBean searchBean, String[] orderColumns, Boolean[] orderDesc,Integer first,Integer max);

    public Map<String,Object> queryBySql(String sql,String countSql,List<Object> paramList,Class T,Integer page,Integer pageSize) throws ManagerException;

    public PaginationDescribe searchPage(int page, int pageSize, SearchBean searchBean, String orderColumn, boolean isAsc,Class _thisClass) throws ManagerException;


    public <T> List<T> getDataList(Collection<Object> idList, Class<T> targetClass,SearchBean searchBean, String orderColumn, Boolean desc) throws ManagerException ;
    public <T> List<T> getDataList(Collection<Object> idList, Class<T> targetClass,String queryColumn,SearchBean searchBean, String orderColumn, Boolean desc) throws ManagerException;
    public int excuteSql(String sql, List<Object> paramList) throws ManagerException;
    public <T> List<T> excuteSql(String sql, List<Object> paramList,Class<T> targetClass) throws ManagerException;
}
