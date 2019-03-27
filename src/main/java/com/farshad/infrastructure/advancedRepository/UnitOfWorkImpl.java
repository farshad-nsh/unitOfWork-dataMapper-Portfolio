package com.farshad.infrastructure.advancedRepository;

import com.farshad.infrastructure.advancedRepository.abstraction.IUnitOfWork;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class UnitOfWorkImpl implements IUnitOfWork {

    private static Logger LOGGER =  LoggerFactory.getLogger(UnitOfWorkImpl.class);

    private Connection connection=null;
    private HashMap<String, List<Object>> context = new HashMap<>();
    private Database database;
    private List<Object> entitesToOperate ;

    public UnitOfWorkImpl(Connection connection) {
        this.connection = connection;
        this.database= new Database(connection);
        this.database.setTableName("portfolio");
    }


    private void register(Object entity, String operation) {
        entitesToOperate = context.get(operation);
        if (entitesToOperate == null) {
            entitesToOperate = new ArrayList<>();
        }
        entitesToOperate.add(entity);
        context.put(operation, entitesToOperate);
    }


    @Override
    public void registerNew(Object entity) {
        LOGGER.info("Registering {} for insert in context.", entity.getClass().getCanonicalName());
        register(entity, IUnitOfWork.INSERT);
    }

    @Override
    public void registerModified(Object entity) {
        LOGGER.info("Registering {} for modify in context.", entity.getClass().getCanonicalName());
        register(entity, IUnitOfWork.MODIFY);
    }

    @Override
    public void registerDeleted(Object entity) {
        LOGGER.info("Registering {} for delete in context.",entity.getClass().getCanonicalName());
        register(entity, IUnitOfWork.DELETE);
    }

    @Override
    public void commit() {
        if (context == null || context.size() == 0) {
            return;
        }
        LOGGER.info("Begin Transaction");
        if (context.containsKey(IUnitOfWork.INSERT)) {
            commitInsert();
        }

        if (context.containsKey(IUnitOfWork.MODIFY)) {
            commitModify();
        }
        if (context.containsKey(IUnitOfWork.DELETE)) {
            commitDelete();
        }

        try {
            database.getConnection().commit();
            LOGGER.info("Commit finished.");

            database.getConnection().close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void rollback() {
        try {
            database.getConnection().rollback();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void commitInsert() {
        List<Object> entitiesToBeInserted = context.get(IUnitOfWork.INSERT);
        for (Object object : entitiesToBeInserted) {
            LOGGER.info("Saving {} to database.", object.getClass().getCanonicalName());
            LOGGER.info("table name is :{}",this.database.getTableName());
            database.persistAdd(object);
        }
    }

    private void commitModify() {
        List<Object> modifiedEntities = context.get(IUnitOfWork.MODIFY);
        for (Object object : modifiedEntities) {
            database.persistUpdate(object);
        }
    }

    private void commitDelete() {
        List<Object> deletedEntites = context.get(IUnitOfWork.DELETE);
        for (Object object : deletedEntites) {
           database.persistDelete(object);
        }
    }
}

