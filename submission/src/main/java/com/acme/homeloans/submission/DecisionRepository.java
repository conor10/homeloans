package com.acme.homeloans.submission;

import com.acme.homeloans.model.Decision;
import org.springframework.data.repository.CrudRepository;

/**
 * Decision repository.
 */
public interface DecisionRepository extends CrudRepository<Decision, Long> {
}
