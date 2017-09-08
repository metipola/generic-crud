package com.mycompany.generic.crud.service;

import com.mycompany.generic.crud.entity.BaseEntity;
import com.mycompany.generic.crud.filter.GenericFilter;
import com.mycompany.generic.crud.repository.AbstractHibernateRepository;
import java.io.Serializable;
import java.util.List;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import com.mycompany.generic.crud.enumeration.MessageEnum;

@Service
@Transactional(propagation = Propagation.REQUIRED)
public abstract class GenericService<E extends BaseEntity, K extends Serializable, V extends MessageEnum, F extends GenericFilter> {

    protected AbstractHibernateRepository<E, K, F> genericRepository;

    public GenericService(AbstractHibernateRepository<E, K, F> genericRepository) {
        this.genericRepository = genericRepository;
    }

    public List<V> saveOrUpdate(E entity) {
        List<V> errors = validate(entity);
        if (errors.isEmpty()) {
            genericRepository.saveOrUpdate(entity);
        }
        return errors;
    }

    public void delete(K id){
        genericRepository.delete(id);
    }

    public List<E> findAll() {
        return genericRepository.findAll();
    }
    
    public List<E> findByFilters(Integer first, Integer pageSize, String sortField, SortOrder sortOrder, F filter){
        return genericRepository.findByFilters(first, pageSize, sortField, sortOrder, filter);
    }
    
    public Long numberOfFilteredObjects(F filter) {
        return genericRepository.numberOfFilteredObjects(filter);
    }

    protected abstract List<V> validate(E entity);
}
