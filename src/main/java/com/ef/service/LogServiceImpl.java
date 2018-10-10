package com.ef.service;

import com.ef.dao.model.BlockedIpModel;
import com.ef.dao.repository.BlockedIpRepository;
import com.ef.dao.repository.BlockedIpRepositoryImpl;
import com.ef.dao.repository.LogRepository;
import com.ef.dto.LineArgumentDTO;
import com.ef.dao.repository.LogRepositoryImpl;

import javax.sql.DataSource;
import java.util.List;

public class LogServiceImpl implements LogService {
    private final LogRepository logRepository;
    private final BlockedIpRepository blockedIpRepository;

    public LogServiceImpl(DataSource source) {
        logRepository = new LogRepositoryImpl(source);
        blockedIpRepository = new BlockedIpRepositoryImpl(source);
    }

    @Override
    public List<BlockedIpModel> copyRequestsMadeMoreThanInSomePeriod(LineArgumentDTO argumentDTO) {
        List<BlockedIpModel> blockedIps = logRepository.findRequestsMadeMoreThatInSomePeriod(argumentDTO);
        blockedIpRepository.insert(blockedIps);
        return blockedIps;
    }
}
