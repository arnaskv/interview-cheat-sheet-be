package com.interview.manager.backend.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.interview.manager.backend.models.Category;
import com.interview.manager.backend.models.InterviewQuestion;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@SuperBuilder(toBuilder = true)
public class InterviewQuestionResponseDto {

    private Long id;
    private String title;
    private Category category;
    @JsonInclude(Include.NON_EMPTY)
    private List<InterviewQuestionResponseDto> subQuestions;

    public static InterviewQuestionResponseDto of(InterviewQuestion interviewQuestion) {
        List<InterviewQuestionResponseDto> subQuestionsDto = interviewQuestion.getSubQuestions().stream()
            .map(InterviewQuestionResponseDto::of)
            .toList();

        return InterviewQuestionResponseDto.builder()
            .id(interviewQuestion.getId())
            .title(interviewQuestion.getTitle())
            .category(interviewQuestion.getCategory())
            .subQuestions(subQuestionsDto)
            .build();
    }

}
