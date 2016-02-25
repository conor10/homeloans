package com.acme.homeloans.submission;

import com.acme.homeloans.model.Decision;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Decision repository.
 */
@Repository
public interface DecisionRepository extends CrudRepository<Decision, Long> {
}
