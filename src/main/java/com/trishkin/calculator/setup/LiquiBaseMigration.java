package com.trishkin.calculator.setup;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;
import liquibase.resource.ResourceAccessor;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Startup
@Singleton
@TransactionManagement(TransactionManagementType.BEAN)
public class LiquiBaseMigration {
    private static final String STAGE = "development";
    private static final String INIT_FILE = "liquibase/db.init.xml";

    @Resource(lookup = "java:/magenta/datasource/test-distance-calculator")
    private DataSource dataSource;

    @PostConstruct
    protected void bootstrap() {
        ResourceAccessor resourceAccessor = new ClassLoaderResourceAccessor(getClass().getClassLoader());
        try (Connection connection = dataSource.getConnection()) {
            JdbcConnection jdbcConnection = new JdbcConnection(connection);
            Database database = DatabaseFactory.getInstance().findCorrectDatabaseImplementation(jdbcConnection);

            Liquibase liquibase = new Liquibase(INIT_FILE, resourceAccessor, database);
            liquibase.update(STAGE);
        } catch (SQLException | LiquibaseException e) {

        }
    }

}
