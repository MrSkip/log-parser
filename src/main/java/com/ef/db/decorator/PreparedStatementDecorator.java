package com.ef.db.decorator;

import com.ef.exception.GeneralRuntimeException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class PreparedStatementDecorator implements AutoCloseable {
    private final PreparedStatement preparedStatement;

    public PreparedStatementDecorator(PreparedStatement preparedStatement) {
        this.preparedStatement = preparedStatement;
    }

    public ResultSet executeQuery() {
        try {
            return preparedStatement.executeQuery();
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public void executeUpdate() {
        try {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public void executeBatch() {
        try {
            preparedStatement.executeBatch();
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public void addBatch() {
        try {
            preparedStatement.addBatch();
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public PreparedStatementDecorator setLong(int parameterIndex, long x) {
        try {
            preparedStatement.setLong(parameterIndex, x);
            return this;
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public PreparedStatementDecorator setLocalDateTime(int parameterIndex, LocalDateTime x) {
        try {
            preparedStatement.setTimestamp(parameterIndex, Timestamp.valueOf(x));
            return this;
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public PreparedStatementDecorator setInt(int parameterIndex, int x) {
        try {
            preparedStatement.setInt(parameterIndex, x);
            return this;
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public PreparedStatementDecorator setString(int parameterIndex, String x) {
        try {
            preparedStatement.setString(parameterIndex, x);
            return this;
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public PreparedStatementDecorator setBoolean(int parameterIndex, boolean x) {
        try {
            preparedStatement.setBoolean(parameterIndex, x);
            return this;
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    @Override
    public void close() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (Exception ioe) {
            // ignore
        }
    }
}
