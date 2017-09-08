package com.mycompany.generic.crud.entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity <I extends Serializable> implements Serializable {
    
    public abstract I getId();
    
    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.getId());
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity<?> other = (BaseEntity<?>) obj;
        return Objects.equals(this.getId(), other.getId());
    }
    
    
    
}
