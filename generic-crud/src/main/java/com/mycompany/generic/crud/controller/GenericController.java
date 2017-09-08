package com.mycompany.generic.crud.controller;

import com.mycompany.generic.crud.entity.BaseEntity;
import com.mycompany.generic.crud.filter.GenericFilter;
import com.mycompany.generic.crud.service.GenericService;
import com.mycompany.generic.crud.util.Mensagem;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import lombok.Getter;
import lombok.Setter;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.stereotype.Controller;
import com.mycompany.generic.crud.enumeration.MessageEnum;


@Controller
public abstract class GenericController <E extends BaseEntity, K extends Serializable, M extends MessageEnum, F extends GenericFilter> {
    
    @Getter
    @Setter
    protected E entity;
    
    @Getter
    @Setter
    protected LazyDataModel<E> dataValues;
    
    @Getter
    @Setter
    protected F filter;
    
    @Getter
    @Setter
    private Boolean newEntity;
    
    protected Mensagem message;
    
    protected GenericService<E, K, M, F> genericService;

    public GenericController(GenericService<E, K, M, F> genericService) {
        this.genericService = genericService;
    }
    
    @PostConstruct
    public void init(){
        findByFilters();
        entity = createEntityInstance();
        filter = createFilterInstance();
        message = Mensagem.getInstance();
        newEntity = true;
    }
    
    public void saveOrUpdate(){
        List<M> erros = genericService.saveOrUpdate(entity);
        
        if(erros.isEmpty()){
            printMessage(successMessage());
            entity = createEntityInstance();
            newEntity = true;
            findByFilters();
        }else{
            errorsMessage(erros);
        }
    }
    
    public void findByFilters(){
        dataValues = new LazyDataModel<E>(){
            @Override
            public List<E> load(int first, int pageSize,
                    String sortField, SortOrder sortOrder,
                    Map<String, Object> filters) {
                List<E> entities = genericService.findByFilters(first, pageSize, sortField, sortOrder, filter);
                Long numberOfFilteredObjects = genericService.numberOfFilteredObjects(filter);
                setRowCount(numberOfFilteredObjects.intValue());
                return entities;
            }
            
            @Override
            public Object getRowKey(E entity) {
                return entity;
            }

            @Override
            public E getRowData(String key) {
                return createEntityInstance();
            }
        };
    }
    
    public void update(E entity){
        this.entity =  entity;
        newEntity = false;
    }
    
    public void delete(K id){
        genericService.delete(id);
        findByFilters();
        printMessage(deleteMessage());
    }
    
   
    
    public void printMessage(M m){
        this.message.add(FacesMessage.SEVERITY_INFO, m);
    }
    
    public void errorsMessage(List<M> errors){
        for (M error : errors) {
            this.message.add(FacesMessage.SEVERITY_ERROR, error);
        }
    }
    
    public abstract M successMessage();
    public abstract M deleteMessage();
    public abstract E createEntityInstance();
    public abstract F createFilterInstance();
}
