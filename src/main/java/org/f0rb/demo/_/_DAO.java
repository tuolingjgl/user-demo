package org.f0rb.demo._;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Administrator
 * Date: 2010-1-26
 * Time: 23:22:51
 * To change this template use File | Settings | File Templates.
 */
public interface _DAO<T> {
    //TN is short for table name
    public static String TN_CATEGORY = "Category";
    public static String TN_ZONE = "Zone";
    public static String TN_USER = "User";
    public static String TN_ARTICLE = "Article";
    public static String TN_ACOMMENT = "Article";

    //T uniqueResult(String hql, Parameter[] params);

    //List listResult(String hql, Parameter[] params);

    /**
     * Method save ...
     *
     * @param o of type Object
     * @return Integer
     */
    Integer save(Object o);

    /**
     * Method delete ...
     *
     * @param t of type T
     */
    void delete(T t);

    /**
     * Method update ...
     *
     * @param t of type T
     */
    void update(T t);

    /**
     * Method merge ...
     *
     * @param t of type T
     * @return T
     */
    T merge(T t);

    /**
     * Method attachDirty ...
     *
     * @param instance of type T
     */
    void attachDirty(T instance);

    /**
     * Method attachClean ...
     *
     * @param instance of type T
     */
    void attachClean(T instance);

    /**
     * Method getById ...
     *
     * @param id of type Integer
     * @return T
     */
    T getById(Integer id);

    /**
     * Method loadById ...
     *
     * @param id of type Integer
     * @return T
     */
    T loadById(Integer id);

    /**
     * Method uniqueByProperty ...
     *
     * @param propertyName of type String
     * @param value of type Object
     * @return T
     */
    T uniqueByProperty(String propertyName, Object value);

    /**
     * Method findAll ...
     * @return List
     */
    List findAll();

    /**
     * Method findByProperty ...
     *
     * @param propertyName of type String
     * @param value of type Object
     * @return List<T>
     */
    List<T> findByProperty(String propertyName, Object value);

    /**
     * Method updateByNamedQuery ...
     *
     * @param queryName of type String
     * @return int
     */
    int updateByNamedQuery(String queryName);

    /**
     * Method updateByNamedQuery ...
     *
     * @param queryName of type String
     * @param value of type Object
     * @return int
     */
    int updateByNamedQuery(String queryName, Object value);

    /**
     * Method updateByNamedQuery ...
     *
     * @param queryName of type String
     * @param values of type Object[]
     * @return int
     */
    int updateByNamedQuery(String queryName, Object[] values);

    /**
     * Method findByNamedQuery ...
     *
     * @param queryName of type String
     * @return List
     */
    List findByNamedQuery(String queryName);

    /**
     * Method findByNamedQuery ...
     *
     * @param queryName of type String
     * @param value of type Object
     * @return List
     */
    List findByNamedQuery(String queryName, Object value);

    /**
     * Method findByNamedQuery ...
     *
     * @param queryName of type String
     * @param values of type Object[]
     * @return List
     */
    List findByNamedQuery(String queryName, Object[] values);

    /**
     * Method findColumn ...
     *
     * @param columnName of type String
     * @return List
     */
    List findColumn(String columnName);

    /**
     * Method findColumns ...
     *
     * @param columnNames of type String[]
     * @return List
     */
    List findColumns(String columnNames[]);

    //List findColumnByProperty(String columnNames[], String propertyName, Object value);    

    /**
     * Method page ...
     *
     * @param start of type int
     * @param limit of type int
     * @return List
     */
    List page(int start, int limit);

    /**
     * Method pageByProperty ...
     *
     * @param propertyName of type String
     * @param value of type Object
     * @param start of type int
     * @param limit of type int
     * @return List<T>
     */
    List<T> pageByProperty(String propertyName, Object value, int start, int limit);

    /**
     * Method pageByNamedQuery ...
     *
     * @param queryName of type String
     * @param start of type int
     * @param limit of type int
     * @return List
     */
    List pageByNamedQuery(String queryName, int start, int limit);

    /**
     * Method pageByNamedQuery ...
     *
     * @param queryName of type String
     * @param value of type Object
     * @param start of type int
     * @param limit of type int
     * @return List
     */
    List pageByNamedQuery(String queryName, Object value, int start, int limit);

    /**
     * Method pageByNamedQuery ...
     *
     * @param queryName of type String
     * @param values of type Object[]
     * @param start of type int
     * @param limit of type int
     * @return List
     */
    List pageByNamedQuery(String queryName, Object[] values, int start, int limit);

    /**
     * Method totalByPropertyInTable ...
     *
     * @param cTableName of type String
     * @param propertyName of type String
     * @param value of type Object
     * @return long
     */
    long totalByPropertyInTable(String cTableName, String propertyName, Object value);

    /**
     * Method getTotalResults returns the totalResults of this DAO object.
     *
     *
     *
     * @return the totalResults (type long) of this DAO object.
     */
    long getTotalResults();

    /**
     * Method getByIdInTable ...
     *
     * @param cTableName of type String
     * @param id of type Object
     * @return Object
     */
    Object getByIdInTable(String cTableName, Object id);
}
