package com.ef.dao.repository;

import com.ef.dao.model.BlockedIpModel;
import com.ef.db.AbstractDAO;

import javax.sql.DataSource;
import java.util.List;

public class BlockedIpRepositoryImpl extends AbstractDAO implements BlockedIpRepository {
    private static final String SQL_INSERT;

    static {
        SQL_INSERT = "insert into blocked_ips (ip, message) values (?,?)";
    }

    public BlockedIpRepositoryImpl(DataSource source) {
        super(source);
    }

    @Override
    public void insert(List<BlockedIpModel> blockedIpModels) {
        prepareStatement(SQL_INSERT)
                .params(stmt -> blockedIpModels.forEach(blockedIp -> {
                    stmt.setString(1, blockedIp.getIp());
                    stmt.setString(2, blockedIp.getMessage());
                    stmt.addBatch();
                }))
                .executeBatch();
    }
}
