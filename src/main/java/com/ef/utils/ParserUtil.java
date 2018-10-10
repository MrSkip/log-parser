package com.ef.utils;

import com.ef.db.StatementHolder;
import com.ef.dao.model.LogModel;
import com.ef.mapper.LogMapper;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class ParserUtil {

    public static void parseLogFile(DataSource dataSource, String sql, String filepath) throws SQLException {
        List<CompletableFuture> futures = new ArrayList<>();
        StatementHolder holder = StatementHolder.of(dataSource, sql);

        int counter = 0;
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {

            String line;
            while ((line = br.readLine()) != null) {
                // number of values in one batch
                if (counter >= 5_000) {
                    // assign previous resources for a new thread and get a new resources for the next thread
                    holder = executeBatchInNewThread(dataSource, sql, futures, holder);
                    counter = 0;
                }

                // parse single row
                Optional<LogModel> logModel = ParserUtil.parseRow(line);
                if (!logModel.isPresent()) {
                    continue;
                }

                // adding new value to the batch
                addBatch(logModel.get(), holder.getPreparedStatement());
                counter++;

                // max number of java threads & DB connections
                if (futures.size() > 20) {
                    futures.forEach(CompletableFuture::join);
                    futures.clear();
                }
            }

        } catch (IOException e) {
            Logger.error("While parsing logs", e);
        }

        // execute the last rows
        executeBatchInNewThread(dataSource, sql, futures, holder);
        // wait for JDBC operation
        futures.forEach(CompletableFuture::join);
    }

    /**
     * A new thread for the new batch operation with unique resources
     * Execute SQL batch command at a new thread
     *
     * @param dataSource data source
     * @param sql        sql query
     * @param futures    list of features
     * @param holder     class holder of resources needed for a new thread
     * @return new resource holder for a new thread
     * @throws SQLException
     */
    private static StatementHolder executeBatchInNewThread(DataSource dataSource,
                                                           String sql,
                                                           List<CompletableFuture> futures,
                                                           StatementHolder holder) throws SQLException {
        CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
            try {
                holder.getPreparedStatement().executeBatch();
                holder.getConnection().commit();
                holder.closeResources();
            } catch (SQLException e) {
                Logger.error("", e);
            }
        });
        futures.add(future);
        return StatementHolder.of(dataSource, sql);
    }

    /**
     * Add a single value to the batch
     *
     * @param model Log model
     * @param stmt  prepared statement
     * @throws SQLException
     */
    private static void addBatch(LogModel model, PreparedStatement stmt) throws SQLException {
        stmt.setTimestamp(1, Timestamp.valueOf(model.getDate()));
        stmt.setString(2, model.getIp());
        stmt.setString(3, model.getRequest());
        stmt.setInt(4, model.getStatus());
        stmt.setString(5, model.getUserAgent());
        stmt.addBatch();
    }


    private static Optional<LogModel> parseRow(String row) {
        if (row == null || row.isEmpty()) {
            return Optional.empty();
        }

        String[] arr = row.split("\\|");
        if (arr.length != 5) {
            Logger.error("Unexpected number of columns in the row: {}", row);
            return Optional.empty();
        }

        return Optional.of(LogMapper.map(arr));
    }

}
