package com.ef.dao.repository;

import com.ef.dto.LineArgumentDTO;
import com.ef.dao.model.BlockedIpModel;

import java.util.List;

public interface LogRepository {

    List<BlockedIpModel> findRequestsMadeMoreThatInSomePeriod(LineArgumentDTO argumentDTO);

}
