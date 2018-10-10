package com.ef.dao.repository;

import com.ef.dao.model.BlockedIpModel;

import java.util.List;

public interface BlockedIpRepository {

    void insert(List<BlockedIpModel> blockedIpModels);

}
