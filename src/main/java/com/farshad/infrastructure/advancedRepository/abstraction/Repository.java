package com.farshad.infrastructure.advancedRepository.abstraction;

import java.util.List;


public interface Repository<TEntity> {

    public void add(TEntity entity);
    public List<TEntity> getAll();
    public void update(TEntity entity);
    public void delete(TEntity entity);

}
