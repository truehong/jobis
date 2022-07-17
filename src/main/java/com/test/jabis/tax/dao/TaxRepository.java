package com.test.jabis.tax.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TaxRepository extends JpaRepository<TaxInfo,Long> {
    Optional<TaxInfo> findTopByUserId(String userId);
}
