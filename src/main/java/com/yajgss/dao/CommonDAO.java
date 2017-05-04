package com.yajgss.dao;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by saravanan.s on 4/19/17.
 */
public interface CommonDAO {

    String ALIAS_PROPERTY_ACCESS ="^";

    String ALIAS_APPENDER = "Proj";

    Session getCurrentSession();

    <T> void create(T entity) throws Exception;

    <T> void refresh(T entity) throws Exception;

    <T> void update(T entity) throws Exception;

    <T> void delete(T entity) throws Exception;

    <T> void deleteAll(Collection<T> entities) throws Exception;

    void flush();

    <T> Map<String, Set<Object>> getColumns(Class<T> entityClass, Map<String, String[]> projections, Criterion... criterion) throws Exception;

    <T> List<T> getColumnsAsEntity(Class<T> entityClass, final Map<String, List<String>> projections, Criterion... criterion) throws Exception;

    <T> List<T> getList(Class<T> entityClass, Criterion... criterion) throws Exception;
}
