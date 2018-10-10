package com.ef.db;

import com.ef.db.decorator.PreparedStatementDecorator;
import com.ef.db.decorator.ResultSetDecorator;
import com.ef.exception.GeneralRuntimeException;
import com.ef.utils.ResourceUtils;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public abstract class AbstractDAO {
    private final DataSource dataSource;

    public AbstractDAO(DataSource source) {
        dataSource = source;
    }

    protected Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            throw new GeneralRuntimeException("During establish the connection", e);
        }
    }

    protected ResourceHolder prepareStatement(String sql) {
        final Connection connection = getConnection();
        try {
            final PreparedStatement preparedStatement = connection.prepareStatement(sql);
            return new ResourceHolder(new PreparedStatementDecorator(preparedStatement), connection);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("During prepared statement creating", e);
        }
    }

    public static class ResourceHolder {
        private final PreparedStatementDecorator preparedStatement;
        private final Connection connection;

        private ResourceHolder(PreparedStatementDecorator preparedStatement, Connection connection) {
            this.preparedStatement = preparedStatement;
            this.connection = connection;
        }

        public ResourceHolder params(Consumer<PreparedStatementDecorator> stmt) {
            stmt.accept(preparedStatement);
            return this;
        }

        public void executeBatch() {
            try {
                connection.setAutoCommit(false);
                preparedStatement.executeBatch();
                connection.commit();
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                ResourceUtils.closeResources(connection, preparedStatement);
            }
        }

        public <T> ResultMapper<T> map(Function<ResultSetDecorator, ? extends T> func) {
            return new ResultMapper<>(func, this);
        }
    }

    public static class ResultMapper<T> {
        private final Function<ResultSetDecorator, ? extends T> resultMapper;
        private final ResourceHolder resource;

        private ResultMapper(Function<ResultSetDecorator, ? extends T> resultMapper, ResourceHolder resource) {
            this.resultMapper = resultMapper;
            this.resource = resource;
        }

        public Optional<T> single() {
            ResultSetDecorator resultSet = null;
            try {
                resultSet = new ResultSetDecorator(resource.preparedStatement.executeQuery());
                if (!resultSet.next()) {
                    return Optional.empty();
                }
                return Optional.ofNullable(resultMapper.apply(resultSet));
            } finally {
                ResourceUtils.closeResources(resource.connection, resource.preparedStatement, resultSet);
            }
        }

        public List<T> list() {
            ResultSetDecorator resultSet = null;
            try {
                List<T> list = new ArrayList<>();
                resultSet = new ResultSetDecorator(resource.preparedStatement.executeQuery());
                while (resultSet.next()) {
                    list.add(resultMapper.apply(resultSet));
                }
                return list;
            } finally {
                ResourceUtils.closeResources(resource.connection, resource.preparedStatement, resultSet);
            }
        }

    }

}
