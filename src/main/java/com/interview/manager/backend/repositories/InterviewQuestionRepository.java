package com.interview.manager.backend.repositories;

import com.interview.manager.backend.models.InterviewQuestion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewQuestionRepository extends JpaRepository<InterviewQuestion, Long> {

//    @Query(value = """
//            select q.id, q.title, c.title as category
//            from interview_questions q
//            inner join interview_categories c on q.category_id = c.id
//            """, nativeQuery = true)
//    List<InterviewQuestion> findAllQuestions();

}
