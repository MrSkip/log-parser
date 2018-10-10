package com.ef.db.decorator;

import com.ef.exception.GeneralRuntimeException;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ResultSetDecorator implements AutoCloseable {
    private final ResultSet resultSet;

    public ResultSetDecorator(ResultSet resultSet) {
        this.resultSet = resultSet;
    }

    public boolean next() {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public String getString(String columnLabel) {
        try {
            return resultSet.getString(columnLabel);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public String getString(int columnIndex) {
        try {
            return resultSet.getString(columnIndex);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public int getInt(String columnLabel) {
        try {
            return resultSet.getInt(columnLabel);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public int getInt(int columnIndex) {
        try {
            return resultSet.getInt(columnIndex);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public long getLong(String columnLabel) {
        try {
            return resultSet.getLong(columnLabel);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public long getLong(int columnIndex) {
        try {
            return resultSet.getLong(columnIndex);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public boolean getBoolean(String columnLabel) {
        try {
            return resultSet.getBoolean(columnLabel);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public boolean getBoolean(int columnIndex) {
        try {
            return resultSet.getBoolean(columnIndex);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public Object getObject(String columnLabel) {
        try {
            return resultSet.getObject(columnLabel);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public Object getObject(int columnIndex) {
        try {
            return resultSet.getObject(columnIndex);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public <T> Object getObject(String columnLabel, Class<T> tClass) {
        try {
            return resultSet.getObject(columnLabel, tClass);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    public <T> T getObject(int columnIndex, Class<T> tClass) {
        try {
            return resultSet.getObject(columnIndex, tClass);
        } catch (SQLException e) {
            throw new GeneralRuntimeException("", e);
        }
    }

    @Override
    public void close() {
        try {
            if (resultSet != null) {
                resultSet.close();
            }
        } catch (Exception ioe) {
            // ignore
        }
    }
}
