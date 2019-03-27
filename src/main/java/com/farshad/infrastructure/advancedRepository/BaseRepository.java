package com.farshad.infrastructure.advancedRepository;


import com.farshad.infrastructure.advancedRepository.abstraction.IUnitOfWork;
import com.farshad.infrastructure.advancedRepository.abstraction.Repository;
import com.farshad.infrastructure.advancedRepository.jdbc.JdbcConnectionFactory;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseRepository<TEntity>  implements Repository<TEntity> {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseRepository.class);

    private IUnitOfWork<TEntity> uow;


    public void add(TEntity entity) {
        if (uow==null){
            uow=new UnitOfWorkImpl(JdbcConnectionFactory.customizedMariadbSqlConnection());
            LOGGER.info("initialized unit of work : {}",uow.getClass().getCanonicalName());
        }
        uow.registerNew(entity);
    }

    public void update(TEntity entity) {
        uow.registerModified(entity);
    }

    public void delete(TEntity entity) {
        uow.registerDeleted(entity);
    }

    public void commit(){
        uow.commit();
    }
}
