package com.interview.manager.backend.repositories;

import com.interview.manager.backend.entities.Interview;
import com.interview.manager.backend.entities.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRepository extends JpaRepository<Interview, Long> {

    @Query("SELECT iq FROM InterviewQuestion iq WHERE iq.interview.id = :interviewId")
    List<InterviewQuestion> findAllByInterviewId(@Param("interviewId") Long interviewId);
}
