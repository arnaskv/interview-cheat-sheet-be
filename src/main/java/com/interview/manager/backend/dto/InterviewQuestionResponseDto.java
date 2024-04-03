package com.interview.manager.backend.dto;

import com.interview.manager.backend.models.InterviewQuestion;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class InterviewQuestionResponseDto extends InterviewQuestionRequestDto {

    private Long id;

    public static InterviewQuestionResponseDto of(InterviewQuestion interviewQuestion) {
        return InterviewQuestionResponseDto.builder()
                .id(interviewQuestion.getId())
                .title(interviewQuestion.getTitle())
                .build();
    }
}