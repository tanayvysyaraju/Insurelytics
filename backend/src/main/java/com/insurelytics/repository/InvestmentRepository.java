package com.insurelytics.repository;

import com.insurelytics.model.Investment;
import com.insurelytics.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Investment entities.
 */
@Repository
public interface InvestmentRepository extends JpaRepository<Investment, Long> {
    List<Investment> findByUser(UserAccount user);
}