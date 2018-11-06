package com.apap.tutorial6.service;

import java.util.Optional;

import com.apap.tutorial6.model.DealerModel;

/**
 * DealerService
 */
public interface DealerService {
    Optional<DealerModel> getDealerDetailById(Long id);

    DealerModel addDealer(DealerModel dealer);

    void deleteById(Long id);
}