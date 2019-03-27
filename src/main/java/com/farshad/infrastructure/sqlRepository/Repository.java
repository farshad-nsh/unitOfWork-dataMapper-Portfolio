package com.farshad.infrastructure.sqlRepository;

import com.farshad.domain.ETF;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.*;
import java.util.List;

@org.springframework.stereotype.Repository
public class Repository extends AbstractRepository{

    @PersistenceContext
    EntityManager em;


    @Autowired
    public Repository(EntityManager entityManager){
        this.em=entityManager;
    }

    @Transactional
    @Override
    public void save(Object entity) {
        em.persist(entity);
    }


    @Transactional
    public List<ETF> readETFByTypeUsingNativeQuery(String type) {
        Query q = em.createNativeQuery("SELECT * FROM etf WHERE type = :type ", ETF.class);
        q.setParameter("type", type);
        List<ETF> result = q.getResultList();
        return result;
    }

}
