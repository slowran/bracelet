package org.bracelet.repository.impl;

import org.bracelet.repository.DomainRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class DomainRepositoryImpl<T, PK extends Serializable> implements DomainRepository<T, PK> {

    private Class<T> entityClass;

    private SessionFactory sessionFactory;

    protected final Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public DomainRepositoryImpl() {
        Type genType = getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        entityClass = (Class) params[0];
    }

    public T get(PK id) {
        Session session;
        Transaction transaction = null;
        T t = null;
        try {
            session = getCurrentSession();
            transaction = session.beginTransaction();
            t = (T)session.get(entityClass, id);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return t;
    }

    public List<T> findAll() {
        return new ArrayList<T>();
    }

    public void persist(T entity) {
        Session session;
        Transaction transaction = null;
        try {
            session = getCurrentSession();
            transaction = session.beginTransaction();
            session.persist(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public PK save(T entity) {
        Session session;
        Transaction transaction = null;
        PK pk = null;
        try {
            session = getCurrentSession();
            transaction = session.beginTransaction();
            pk = (PK)session.save(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
        return pk;
    }

    public void saveOrUpdate(T entity) {
        Session session;
        Transaction transaction = null;
        try {
            session = getCurrentSession();
            transaction = session.beginTransaction();
            session.saveOrUpdate(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void delete(PK id) {
        Session session;
        Transaction transaction = null;
        T t;
        try {
            session = getCurrentSession();
            transaction = session.beginTransaction();
            t = (T)session.get(entityClass, id);
            if (t != null) {
                session.delete(t);
            }
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void delete(T entity) {
        Session session;
        Transaction transaction = null;
        try {
            session = getCurrentSession();
            transaction = session.beginTransaction();
            session.delete(entity);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
}
