package com.interview.manager.backend.dto;

import com.interview.manager.backend.models.Category;
import com.interview.manager.backend.models.InterviewQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder(toBuilder = true)
public class InterviewQuestionResponseDto {

    private Long id;
    private String title;
    private Category category;

    public static InterviewQuestionResponseDto of(InterviewQuestion interviewQuestion) {
        return InterviewQuestionResponseDto.builder()
                .id(interviewQuestion.getId())
                .title(interviewQuestion.getTitle())
                .category(interviewQuestion.getCategory())
                .build();
    }
}
