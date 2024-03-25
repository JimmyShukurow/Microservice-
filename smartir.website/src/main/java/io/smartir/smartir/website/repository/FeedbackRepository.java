package io.smartir.smartir.website.repository;


import io.smartir.smartir.website.entity.Feedback;
import io.smartir.smartir.website.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface FeedbackRepository extends JpaRepository<Feedback, Long> {

}