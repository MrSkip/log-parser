package com.ef;

import com.ef.dto.LineArgumentDTO;
import com.ef.service.LogService;
import com.ef.utils.LineArgumentUtil;
import com.ef.utils.ParserUtil;
import com.ef.configuration.DatasourceFactory;
import com.ef.dao.model.BlockedIpModel;
import com.ef.service.LogServiceImpl;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.TimeZone;

public class Parser {
    private static final String URL;
    private static final String SQL;

    static {
        URL = "jdbc:mysql://localhost:3306/test_log" +
                "?password=root&user=root&useSSL=false&serverTimezone=UTC";
        SQL = "insert into logs (date, ip, request, status, userAgent) values (?, ?, ?, ?, ?)";
    }

    public static void main(String[] args) throws SQLException {
        // use UTC
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        // get command line arguments
        LineArgumentDTO arguments = LineArgumentUtil.getArguments(args);
        // load data source
        DataSource dataSource = DatasourceFactory.loadDataSource(URL);

        if (arguments.getPathToLogs() != null) {
            // parse file and insert into table
            ParserUtil.parseLogFile(dataSource, SQL, arguments.getPathToLogs());
        }

        // find IPs that mode more than a certain number of requests for a given time period
        LogService logService = new LogServiceImpl(dataSource);
        List<BlockedIpModel> blockedIps = logService.copyRequestsMadeMoreThanInSomePeriod(arguments);

        // write requests to the console
        blockedIps.forEach(logModel
                -> System.out.printf("IP: %s \n", logModel.getIp()));
    }
}
