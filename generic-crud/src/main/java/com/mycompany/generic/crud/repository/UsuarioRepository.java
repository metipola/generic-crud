package com.mycompany.generic.crud.repository;

import com.mycompany.generic.crud.entity.Usuario;
import com.mycompany.generic.crud.filter.UsuarioFilter;
import org.hibernate.Criteria;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(propagation = Propagation.REQUIRED)
public class UsuarioRepository extends AbstractHibernateRepository<Usuario, Long, UsuarioFilter> {

    @Autowired
    public UsuarioRepository(@Qualifier("sessionFactory") SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public Boolean existsLoginForNewUser(String login) {
        Usuario usuario = (Usuario) sessionFactory.getCurrentSession()
                .createQuery("SELECT u FROM Usuario u WHERE u.login = :login")
                .setParameter("login", login).uniqueResult();
        return usuario != null;
    }

    public Boolean existsLoginForOldUser(Long id, String login) {
        Usuario usuario = (Usuario) sessionFactory.getCurrentSession()
                .createQuery("SELECT u FROM Usuario u WHERE u.login = :login AND u.id != :id")
                .setParameter("login", login).setParameter("id", id)
                .uniqueResult();
        return usuario != null;
       
    }

    @Override
    public Criteria createCriteriaForFilter(UsuarioFilter filter) {
        Criteria criteria = sessionFactory.getCurrentSession().createCriteria(entityClass);

        if (filter.getNome() != null) {
            criteria.add(Restrictions.like("nome", filter.getNome(), MatchMode.ANYWHERE).ignoreCase());
        }

        if (filter.getLogin() != null) {
            criteria.add(Restrictions.like("login", filter.getLogin(), MatchMode.ANYWHERE).ignoreCase());
        }

        if (filter.getEmail() != null) {
            criteria.add(Restrictions.like("email", filter.getEmail(), MatchMode.ANYWHERE).ignoreCase());
        }

        return criteria;
    }

}
