package com.ef.dao.repository;

import com.ef.dao.model.BlockedIpModel;
import com.ef.db.AbstractDAO;
import com.ef.dto.LineArgumentDTO;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

public class LogRepositoryImpl extends AbstractDAO implements LogRepository {
    private static final String SQL_QUERY_REQUESTS_MORE_THAN;

    static {
        SQL_QUERY_REQUESTS_MORE_THAN = "select l.ip, count(*) from logs l " +
                " where l.date between ? and ? " +
                " group by l.ip having count(*) > ?";
    }

    public LogRepositoryImpl(DataSource source) {
        super(source);
    }

    @Override
    public List<BlockedIpModel> findRequestsMadeMoreThatInSomePeriod(LineArgumentDTO argumentDTO) {
        LocalDateTime endDate = argumentDTO.getDuration().calculate(argumentDTO.getStartDate());
        String warningMsg = String.format("Made more than %d requests starting from %s to %s",
                argumentDTO.getThreshold(), argumentDTO.getStartDate(), endDate);

        return prepareStatement(SQL_QUERY_REQUESTS_MORE_THAN)
                .params(stmt -> {
                    stmt.setLocalDateTime(1,
                            argumentDTO.getStartDate());
                    stmt.setLocalDateTime(2,
                            endDate);
                    stmt.setInt(3, argumentDTO.getThreshold());
                })
                .map(rs -> new BlockedIpModel()
                        .setIp(rs.getString(1))
                        .setMessage(warningMsg))
                .list();
    }
}
