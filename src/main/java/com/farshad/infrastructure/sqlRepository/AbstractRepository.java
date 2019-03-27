package com.farshad.infrastructure.sqlRepository;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class AbstractRepository<T> {
    public abstract void save(T entity);
}
