package com.farshad.infrastructure.advancedRepository.concreteRepositories;

import com.farshad.domain.Portfolio;
import com.farshad.infrastructure.advancedRepository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

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

