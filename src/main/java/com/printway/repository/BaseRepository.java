package com.printway.repository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaUpdate;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
@Transactional(readOnly = false)
public class BaseRepository<T, ID extends Serializable> {
    @PersistenceContext(unitName = "printwayEntityManagerFactory")
    private EntityManager entityManager;
    private final Class entityClass;

    public BaseRepository(Class entityClass) {
        this.entityClass = entityClass;
    }

    protected EntityManager getEntityManager() {
        return entityManager;
    }

    public void refreshEntity(T entity) {
        entityManager.refresh(entity);
    }

    @Transactional("printwayTransactionManager")
    public T insert(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Transactional("printwayTransactionManager")
    public List<T> insert(List<T> listEntities, int batchSize) {
        if (listEntities != null && !listEntities.isEmpty()) {
            for (int i = 0; i < listEntities.size(); i++) {
                insert(listEntities.get(i));
                if (i % batchSize == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }
        }
        return listEntities;
    }

    @Transactional("printwayTransactionManager")
    public List<T> insert(List<T> listEntities) {
        return insert(listEntities, 50);
    }

    //-------------------Creation
    //-------------------Find
    public T findOne(ID id) {
        return (T) entityManager.find(entityClass, id);
    }

    public List<T> findAll() {
        Query createQuery = entityManager.createQuery("From " + this.entityClass.getSimpleName());
        return createQuery.getResultList();
    }

    // ---------Query Builder
    public List<T> query(String query, boolean isNative, Map<String, Object> params) {
        return query(query, isNative, params, null, entityClass);
    }
    public <R> List<R> query(String query, boolean isNative, Map<String, Object> params, PageRequest paging, Class<R> resultClazz) {
        Query createdQuery = buildQueryHasParameters(query, isNative, params, resultClazz);
        if (paging != null) {
            return createdQuery.setFirstResult(paging.getPageNumber() * paging.getPageSize())
                    .setMaxResults(paging.getPageSize())
                    .getResultList();
        } else {
            return createdQuery.getResultList();
        }
    }
    ////xx
    public T queryOne(String query, boolean isNative, Map<String, Object> params) {
        return (T) queryOne(query, isNative, params, entityClass);
    }

    public <R> R queryOne(String query, boolean isNative, Map<String, Object> params, Class<R> resultClazz) {
        Query createdQuery = buildQueryHasParameters(query, isNative, params, resultClazz);
        try {
            createdQuery.setMaxResults(1);
            return (R) createdQuery.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    private Query buildQueryHasParameters(String query, boolean isNative, Map<String, Object> params, Class clazz) {
        Query createdQuery;
        if (clazz != null) {
            createdQuery = buildQuery(query, isNative, clazz);
        } else {
            createdQuery = buildQuery(query, isNative);
        }
        if (params != null && !params.isEmpty()) {
            for (String key : params.keySet()) {
                createdQuery.setParameter(key, params.get(key));
            }
        }
        return createdQuery;
    }

    private Query buildQuery(String query, boolean isNative) {
        return buildQuery(query, isNative, null);
    }

    private Query buildQuery(String query, boolean isNative, Class clazz) {
        Query createdQuery;
        if (isNative) {
            if (clazz != null) {
                createdQuery = entityManager.createNativeQuery(query, clazz);
            } else {
                createdQuery = entityManager.createNativeQuery(query);
            }
        } else {
            createdQuery = entityManager.createQuery(query);
        }
        return createdQuery;
    }


    //-------------------Query
    //-------------------UPDATE
    @Transactional("printwayTransactionManager")
    public T update(T entity) {
        entityManager.merge(entity);
        return entity;
    }


    @Transactional("printwayTransactionManager")
    public int update(String queryStr, boolean isNative) {
        Query query = buildQuery(queryStr, isNative);
        return query.executeUpdate();
    }

    @Transactional("printwayTransactionManager")
    public List<T> update(List<T> listEntities, int batchSize) {
        if (listEntities != null && !listEntities.isEmpty()) {
            for (int i = 0; i < listEntities.size(); i++) {
                update(listEntities.get(i));
                if (i % batchSize == 0) {
                    entityManager.flush();
                    entityManager.clear();
                }
            }
        }
        return listEntities;
    }

    @Transactional("printwayTransactionManager")
    public List<T> update(List<T> listEntities) {
        return update(listEntities, 50);
    }

    @Transactional("printwayTransactionManager")
    public int update(String queryStr, boolean isNative, Map<String, Object> params) {
        Query query = buildQueryHasParameters(queryStr, isNative, params);
        return query.executeUpdate();
    }

    public int updateNoneTransaction(String queryStr, boolean isNative) {
        return updateNoneTransaction(queryStr, isNative, null);
    }

    public int updateNoneTransaction(String queryStr, boolean isNative, Map<String, Object> params) {
        Query query = buildQueryHasParameters(queryStr, isNative, params);
        return query.executeUpdate();
    }
    private Query buildQueryHasParameters(String query, boolean isNative, Map<String, Object> params) {
        return buildQueryHasParameters(query, isNative, params, null);
    }
}
