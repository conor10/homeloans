package com.acme.homeloans.submission;

import com.acme.homeloans.model.Submission;
import org.springframework.data.repository.CrudRepository;

/**
 * SubmissionRepository.
 */
public interface SubmissionRepository extends CrudRepository<Submission, Long> {
}
