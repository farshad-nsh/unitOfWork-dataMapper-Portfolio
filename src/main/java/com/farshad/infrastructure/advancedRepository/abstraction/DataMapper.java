package com.farshad.infrastructure.advancedRepository.abstraction;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface DataMapper<Entity> {
    public Entity map(ResultSet rs) throws SQLException;
}

