package com.mycompany.generic.crud.repository;

import com.mycompany.generic.crud.entity.BaseEntity;
import com.mycompany.generic.crud.filter.GenericFilter;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projections;
import org.primefaces.model.SortOrder;


public abstract class AbstractHibernateRepository<E extends BaseEntity, ID extends Serializable, F extends GenericFilter> {

    protected final SessionFactory sessionFactory;

    protected final Class<?> entityClass;

    public AbstractHibernateRepository(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parametrizedType = (ParameterizedType) type;
        this.entityClass = (Class<?>) parametrizedType.getActualTypeArguments()[0];
    }

    public void saveOrUpdate(E entity) {
        sessionFactory.getCurrentSession().saveOrUpdate(entity);
    }

    public void delete(ID id) {
        E entity = (E)sessionFactory.getCurrentSession().load(entityClass, id);
        sessionFactory.getCurrentSession().delete(entity);
    }

    @SuppressWarnings("unchecked")
    public E findById(ID id) {
        return (E)sessionFactory.getCurrentSession().get(entityClass, id);
    }

    @SuppressWarnings("unchecked")
    public List<E> findAll() {
        return sessionFactory.getCurrentSession().createCriteria(entityClass).list();
    }

    public Long numberOfFilteredObjects(F filter) {
        Criteria criteria = createCriteriaForFilter(filter);
        criteria.setProjection(Projections.rowCount());
        return (Long) criteria.uniqueResult();
    }

    public List<E> findByFilters(Integer first, Integer pageSize, String sortField, SortOrder sortOrder, F filter) {
        Criteria criteria = createCriteriaForFilter(filter);
        criteria.setFirstResult(first)
                .setMaxResults(pageSize);

        return criteria.list();
    }

    public abstract Criteria createCriteriaForFilter(F filter);

}
