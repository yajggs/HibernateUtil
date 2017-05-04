package com.yajgss.dao.impl;

import com.yajgss.dao.CommonDAO;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.NonUniqueObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.sql.JoinType;
import org.hibernate.transform.ResultTransformer;

import javax.persistence.Id;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;

/**
 * Created by saravanan.s on 4/19/17.
 */
public class CommonDAOImpl implements CommonDAO{

    private static Logger logger = Logger.getLogger(CommonDAOImpl.class);

    private static String DOT = ".";

    protected Session session;

    protected SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public CommonDAOImpl(Session session) {
        this.session = session;
    }

    public CommonDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public Session getCurrentSession() {
        return session == null ? sessionFactory != null ? sessionFactory.getCurrentSession() : null : session;
    }

    public <T> void create(T entity) throws Exception {
        try {
            getCurrentSession().save(entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public <T> void refresh(T entity) throws Exception {
        try {
            getCurrentSession().refresh(entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public <T> void update(T entity) throws Exception {
        try {
            getCurrentSession().update(entity);
        } catch (Exception e) {
            if (e.getCause() instanceof NonUniqueObjectException) {
                entity = (T) getCurrentSession().merge(entity);
                getCurrentSession().saveOrUpdate(entity);
            } else if (e.getCause() instanceof HibernateException) {
                entity = (T) getCurrentSession().merge(entity);
                getCurrentSession().saveOrUpdate(entity);
            } else {
                logger.error(e.getMessage(), e);
                throw e;
            }
        }
    }

    public <T> void delete(T entity) throws Exception {
        try {
            getCurrentSession().delete(entity);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public <T> void deleteAll(Collection<T> entities)
            throws Exception {
        try {
            for (T t : entities) {
                getCurrentSession().delete(t);
            }
            flush();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
    }

    public void flush() {
        getCurrentSession().flush();
    }

    /**
     *
     * @param entityClass
     * @param projections
     * @param criterion
     * @param <T>
     * @return Map<K,V> Where K is the projection key/column name and V is the Set of column values.
     */

    public <T> Map<String, Set<Object>> getColumns(Class<T> entityClass, final Map<String, String[]> projections, Criterion... criterion){
        final Map<String, Set<Object>> tLI = new LinkedHashMap<String, Set<Object>>();
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        /**
         * Get the projections and create Alias upfront.
         */
        if(null != projections){
            Set<String> aliases = projections.keySet();
            for(String alias: aliases){
                if (alias.startsWith(ALIAS_PROPERTY_ACCESS)) {
                    String key = alias.substring(1);
                    criteria.createAlias(key, key+ALIAS_APPENDER);
                }
            }
        }
        if(null != criterion){
            for (Criterion otherCriterion : criterion) {
                criteria.add(otherCriterion);
            }
        }
        ProjectionList projectionList = Projections.projectionList();
        try{
            if(projections != null && !projections.isEmpty()) {
                for(Map.Entry<String, String[]> entry: projections.entrySet()){
                    if(entry.getValue().length > 0) {
                        String key = entry.getKey();
                        if (key.startsWith(ALIAS_PROPERTY_ACCESS)) {
                            key = key.substring(1);
                            key = key+ALIAS_APPENDER+DOT;
                        }
                        for (String _projection : entry.getValue()) {
                            projectionList.add(Projections.property(key+_projection).as(entry.getKey().substring(1).concat(DOT).concat(_projection)));
                        }
                    }
                }
                criteria.setProjection(Projections.distinct(projectionList));
                criteria.setResultTransformer(new ResultTransformer() {
                    /**
                     * objects will hold the column values and strings will hold the aliases.
                     */
                    public Object transformTuple(Object[] objects, String[] strings) {
                        int i = 0;
                        for(String alias: strings) {
                            if(!tLI.containsKey(alias)) {
                                tLI.put(alias, new LinkedHashSet<Object>());
                            }
                            tLI.get(alias).add(objects[i]); i++;
                        }
                        return tLI;
                    }

                    public List transformList(List list) {
                        return new ArrayList();
                    }
                });
            }
            else {
                criteria.setProjection(
                        Projections.distinct(Projections.id()))
                        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            }
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        criteria.list();
        return tLI;
    }

    /**
     *
     * @param c
     * @param p
     * @param cn
     * @param <T>
     * @return List<T> List of entityClasses.
     */
    public <T> List<T> getColumnsAsEntity(final Class<T> c, final Map<String, List<String>> p, Criterion... cn){
        final Map<String, T> _temp = new LinkedHashMap<String, T>();
        Criteria _cr = getCurrentSession().createCriteria(c);
        List<String> _al = new ArrayList<String>();
        /**
         * Get the projection and convert into alias
         *
         */
        if(null != p){
            Set<String> _k = p.keySet();
            for(String _a: _k){
                if (_a.startsWith(ALIAS_PROPERTY_ACCESS)) {
                    String _y = _a.substring(1);
                    String _t = "";
                    if(_a.contains(DOT)) {
                        String[] _sa = _y.split("\\.");
                        String _als = "";
                        for (int i = 0; i < _sa.length; i++) {
                            if(i == 0) _als = _sa[i];
                            else _als = _sa[i - 1].concat(ALIAS_APPENDER).concat(DOT).concat(_sa[i]);
                            _t =  _sa[i].concat(ALIAS_APPENDER);
                            if(!_al.contains(_t)) {
                                _cr.createAlias(_als, _t, JoinType.LEFT_OUTER_JOIN);
                                _al.add(_t);
                            }
                        }
                    }
                    else {
                        _t = _y+ALIAS_APPENDER;
                        if(!_al.contains(_t)) {
                            _cr.createAlias(_y, _t, JoinType.LEFT_OUTER_JOIN);
                            _al.add(_t);
                        }
                    }
                }
            }
        }
        if(null != cn){
            for (Criterion _cn : cn) {
                _cr.add(_cn);
            }
        }
        ProjectionList pl = Projections.projectionList();
        try{
            if(null != p && !p.isEmpty()) {
                for(Map.Entry<String, List<String>> entry: p.entrySet()){
                    if(!entry.getValue().isEmpty()) {
                        String _y = entry.getKey();
                        if (_y.startsWith(ALIAS_PROPERTY_ACCESS)) {
                            String _a = _y.substring(1);
                            if(!_a.contains(DOT)) {
                                _y = _a.concat(ALIAS_APPENDER).concat(DOT);
                            }
                            else{
                                _y = _a.substring(_a.lastIndexOf(DOT)+1).concat(ALIAS_APPENDER).concat(DOT);
                            }
                            for (String _p : entry.getValue()) {
                                pl.add(Projections.property(_y+_p).as(_a.concat(DOT).concat(_p)));
                            }
                        }
                        else {
                            for (String _p : entry.getValue()) {
                                pl.add(Projections.property(_p).as(_p));
                            }
                        }
                    }
                }
                _cr.setProjection(Projections.distinct(pl));
                _cr.setResultTransformer(new ResultTransformer() {
                    /**
                     * objects will hold column values and strings will hold aliases.
                     */
                    public Object transformTuple(Object[] o, String[] s) {
                        try {
                            T e = null;
                            int k = 0;
                            if (!_temp.containsKey(o[k].toString())) {
                                e = c.newInstance();
                            }
                            else{
                                e = _temp.get(o[k].toString());
                            }
                            for(int i=0; i < s.length; i++) {
                                processColumns(c, e, s[i], o[i]);
                            }

                            _temp.put(o[k].toString(), e);

                        } catch (InstantiationException e) {
                            logger.error(e.getMessage(), e);
                        } catch (IllegalAccessException e) {
                            logger.error(e.getMessage(), e);
                        }
                        return _temp;
                    }

                    public List transformList(List list) {
                        List values = new ArrayList<T>();
                        values.addAll(_temp.values());
                        return values;
                    }
                });
            }
            else {
                _cr.setProjection(
                        Projections.distinct(Projections.id()))
                        .setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            }
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return _cr.list();
    }

    /**
     *
     * @param c
     * @param p
     * @param <I>
     * @param <T>
     * @return Class return type Set/List/Entity
     */

    public static <I,T> Class<T> getInnerEntity(Class<I> c, String p) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(p, c);
            return (Class<T>) pd.getPropertyType();
        }
        catch( Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     *
     * @param c
     * @param p
     * @param e
     * @param <I>
     * @param <T>
     * @return Returns existing object by reading from parent entity
     */

    public static <I,T> Object getInnerEntityObject(Class<I> c, String p, Object e) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(p, c);
            return pd.getReadMethod().invoke(e);
        }
        catch( Exception ex) {
            logger.error(ex.getMessage(), ex);
            return null;
        }
    }

    /**
     *
     * @param c
     * @param p
     * @param <I>
     * @param <T>
     * @return Get the collection entity incase of Set/List.
     */

    public static <I,T> Class<T> getCollectionEntity(Class<I> c, String p) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(p, c);
            ParameterizedType t = (ParameterizedType) pd.getReadMethod().getGenericReturnType();
            return (Class<T>) t.getActualTypeArguments()[0];
        }
        catch( Exception e) {
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /**
     *
     * @param c
     * @param p
     * @param <I>
     * @return Get the object in a set to update
     */

    public static <I> Object getObject(Collection c, String p) {
        Object _e = null;
        try {
            boolean f = false;
            Object _t = null;
            if(!c.isEmpty()){
                Iterator iter = c.iterator();
                while(iter.hasNext()) {
                    _t = iter.next();
                }
            }
            Method[] m = _t.getClass().getMethods();
            PropertyDescriptor pd = new PropertyDescriptor(p, _t.getClass());
            for (Method _m : m) {
                if( _m.isAnnotationPresent(Id.class)){
                    if(null != _m.invoke(_t) && _m.getName().equals(pd.getReadMethod().getName())){
                        f = true;
                        break;
                    }
                }
            }
            if(f || _t == null){
                _e = _t.getClass().newInstance();
            }
            else{
                _e = _t;
            }
        }
        catch( Exception e) {
            //Keeping empty expected to throw exception on method not found.
        }
        return _e;
    }

    /**
     *
     * @param c Class name
     * @param p Property
     * @param v Values
     * @param o Entity Object
     * @return Creates and Sets value to the nested objects.
     *
     */

    public static <T> void processColumns(Class<T> c, Object o, String p, Object... v) {
        try {
            int _i = 0;
            if( (_i = p.indexOf(DOT)) != -1 ) {
                String _p = p.substring(0, _i);
                Class _c = getInnerEntity(c, _p);
                Object _e = null;
                if (_c.isInterface()) {
                    _c = getCollectionEntity(c, _p);
                    _e = getInnerEntityObject(c, _p, o);
                    if(null == _e) {
                        _e = _c.newInstance();
                    }
                    else{
                        _e = getObject(((Collection)_e), p.substring(++_i));
                        if(null == _e) {
                            _e = _c.newInstance();
                        }
                        --_i;
                    }
                    processColumns(_c, _e, p.substring(++_i), v);
                    processColumns(c, o, _p, _e);

                } else{
                    _e = getInnerEntityObject(c, _p, o);
                    if(null == _e) {
                        _e = _c.newInstance();
                    }
                    processColumns(_c, _e, p.substring(++_i), v);
                    processColumns(c, o, _p, _e);
                }
            }
            else {
                PropertyDescriptor pd = new PropertyDescriptor(p, c);
                Class _o = pd.getPropertyType();
                if (_o.isInterface()) {
                    if (Set.class.getName().equals(_o.getName())) {
                        Set _t = (Set) pd.getReadMethod().invoke(o);
                        if (null == _t) {
                            _t = new LinkedHashSet<Object>();
                        }
                        for(Object _temp: _t){
                            if (null != v && v.length > 0 && _temp.equals(v[0])) {
                                v = null;
                            }
                        }
                        if (null != v) {
                            CollectionUtils.addAll(_t, v);
                            pd.getWriteMethod().invoke(o, _t);
                        }
                    }
                    if (List.class.getName().equals(_o.getName())) {
                        List _t = (List) pd.getReadMethod().invoke(o);
                        if (null == _t) {
                            _t = new ArrayList<Object>();
                        }
                        for(Object _temp: _t){
                            if (null != v && v.length > 0 && _temp.equals(v[0])) {
                                v = null;
                            }
                        }
                        if (null != v) {
                            CollectionUtils.addAll(_t, v);
                            pd.getWriteMethod().invoke(o, _t);
                        }
                        pd.getWriteMethod().invoke(o, _t);
                    }
                } else {
                    pd.getWriteMethod().invoke(o, v == null ? null : v[0]);
                }
            }
        }  catch (IntrospectionException e) {
            logger.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            logger.error(e.getMessage(), e);
        } catch (InstantiationException e) {
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public <T> List<T> getList(Class<T> entityClass, Criterion... criterion)
            throws HibernateException {
        List<T> tLI = null;
        Criteria criteria = getCurrentSession().createCriteria(entityClass);
        try {
            criteria.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
            if(null != criterion) {
                for (Criterion ctrn : criterion) {
                    criteria.add(ctrn);
                }
            }
            tLI = criteria.list();
            if (tLI == null) {
                tLI = new ArrayList<T>();
            }
        } catch (HibernateException e) {
            logger.error(e.getMessage(), e);
            throw e;
        }
        return tLI;
    }
}
