package com.acme.homeloans.submission;

import com.acme.homeloans.model.Submission;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * SubmissionRepository.
 */
@Repository
public interface SubmissionRepository extends CrudRepository<Submission, Long> {
}
