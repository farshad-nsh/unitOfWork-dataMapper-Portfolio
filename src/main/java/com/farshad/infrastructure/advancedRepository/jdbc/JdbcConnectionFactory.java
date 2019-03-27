package com.farshad.infrastructure.advancedRepository.jdbc;

import com.farshad.infrastructure.advancedRepository.concreteRepositories.PortfolioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcConnectionFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(JdbcConnectionFactory.class);


    public static Connection customizedMariadbSqlConnection() {
        String url="jdbc:mariadb://yourip:3306/myproxyforpando";
        String user = "myuser";
        String password = "mypwd";
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, password);
            LOGGER.info("url is {}",url);
            return connection;
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

    }
}
