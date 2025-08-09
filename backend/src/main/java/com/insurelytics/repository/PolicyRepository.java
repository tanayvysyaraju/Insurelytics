package com.insurelytics.repository;

import com.insurelytics.model.Policy;
import com.insurelytics.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for Policy entities.
 */
@Repository
public interface PolicyRepository extends JpaRepository<Policy, Long> {
    List<Policy> findByUser(UserAccount user);
}