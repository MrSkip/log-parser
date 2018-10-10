package com.ef.configuration;

import com.ef.exception.GeneralRuntimeException;
import com.mysql.cj.jdbc.MysqlDataSource;

import javax.sql.DataSource;

public class DatasourceFactory {

    public static DataSource loadDataSource(String url) {
        if (url == null || url.isEmpty()) {
            throw new GeneralRuntimeException("Bad DB connection URL");
        }

        if (url.startsWith("jdbc:mysql")) {
            MysqlDataSource ds = new MysqlDataSource();
            ds.setURL(url);
            return ds;
        }

        throw new GeneralRuntimeException("Datasource not defined");
    }

}
