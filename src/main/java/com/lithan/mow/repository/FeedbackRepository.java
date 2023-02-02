package com.lithan.mow.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.lithan.mow.model.Feedback;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    
}
