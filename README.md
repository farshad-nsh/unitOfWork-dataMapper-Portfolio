# Unit Of Work and data mapper pattern
## Motivation
Well, ORM is antipattern specially if you have a complex rich domain in the context of domain driven design.
So why not just implement Martin Fowler's pattern on enterprise application architecture?
## Goals
* less database hit
* better handling the conversion of a graph of objects into data structures.
* better performance in comparison with Hibernate 
## Encapsulation and inversion of control(@infrastructure layer) 
So we just have an abstract class:
```java
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

```
So we just consider any object that is being modified or deleted or inserted. We keep track of those objects and
when the developer said commit, we provide a database hit at the infrastructure level so the developer
will not see these dirty stuff and he or she can concentrate on business logic without getting
worried about database stuff and SQL knowledge and Lazy Loading and many other technical stuff 
that only people in Oracle company may face it.
## Domain Experts
Everything is clean now. We just need to extend a class and rock:
```java 
public class PortfolioRepository extends BaseRepository<Portfolio> {

    private static final Logger LOGGER = LoggerFactory.getLogger(PortfolioRepository.class);


    @Override
    public void add(Portfolio portfolio) {
        super.add(portfolio);
        LOGGER.info(portfolio.getName());
    }

    @Override
    public List<Portfolio> getAll() {
        return null;
    }

    @Override
    public void update(Portfolio portfolio) {
        super.update(portfolio);
    }

    @Override
    public void delete(Portfolio portfolio) {
        super.delete(portfolio);
    }

    @Override
    public void commit() {
        super.commit();
    }

}

```


## result
```java 
2019-03-27 20:28:47.162  INFO 1952 --- [           main] c.f.i.advancedRepository.BaseRepository  : initialized unit of work : com.farshad.infrastructure.advancedRepository.UnitOfWorkImpl
2019-03-27 20:28:47.162  INFO 1952 --- [           main] c.f.i.advancedRepository.UnitOfWorkImpl  : Registering com.farshad.domain.Portfolio for insert in context.
2019-03-27 20:28:47.162  INFO 1952 --- [           main] c.f.i.a.c.PortfolioRepository            : farshadAsaMutualFundManager
2019-03-27 20:28:47.162  INFO 1952 --- [           main] c.f.i.advancedRepository.UnitOfWorkImpl  : Begin Transaction
2019-03-27 20:28:47.162  INFO 1952 --- [           main] c.f.i.advancedRepository.UnitOfWorkImpl  : Saving com.farshad.domain.Portfolio to database.
2019-03-27 20:28:47.162  INFO 1952 --- [           main] c.f.i.advancedRepository.UnitOfWorkImpl  : table name is :portfolio
2019-03-27 20:28:47.270  INFO 1952 --- [           main] c.f.i.advancedRepository.UnitOfWorkImpl  : Commit finished.
```