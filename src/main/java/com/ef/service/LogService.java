package com.ef.service;

import com.ef.dto.LineArgumentDTO;
import com.ef.dao.model.BlockedIpModel;

import java.util.List;

public interface LogService {

    List<BlockedIpModel> copyRequestsMadeMoreThanInSomePeriod(LineArgumentDTO argumentDTO);

}
