package com.ef.db;

import com.ef.utils.ResourceUtils;
import lombok.Data;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

// class holder for connection and prepared statement to keep resources under one class
// and to be able to close unique resources per a new thread

@Data
public class StatementHolder {

    private Connection connection;
    private PreparedStatement preparedStatement;

    public static StatementHolder of(DataSource dataSource, String sql) throws SQLException {
        Connection connection = dataSource.getConnection();
        connection.setAutoCommit(false);

        StatementHolder statementHolder = new StatementHolder();
        statementHolder.connection = connection;
        statementHolder.preparedStatement = connection.prepareStatement(sql);

        return statementHolder;
    }

    public void closeResources() {
        ResourceUtils.closeResources(connection, preparedStatement);
    }

}
