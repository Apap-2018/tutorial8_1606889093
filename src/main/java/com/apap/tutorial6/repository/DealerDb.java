package com.apap.tutorial6.repository;

import com.apap.tutorial6.model.DealerModel;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * DealerDb
 */
@Repository
public interface DealerDb extends JpaRepository<DealerModel, Long> {

}