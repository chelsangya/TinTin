package com.system.tintin.service;

import com.system.tintin.entity.Queries;
import com.system.tintin.pojo.QueriesPojo;

import java.util.List;

public interface QueryService {
    List<Queries> fetchAll();

    String save(QueriesPojo queriesPojo);
}
