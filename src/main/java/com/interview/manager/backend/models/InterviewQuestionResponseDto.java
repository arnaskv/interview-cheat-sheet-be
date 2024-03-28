package com.interview.manager.backend.models;

import com.interview.manager.backend.entities.InterviewQuestion;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@EqualsAndHashCode(callSuper = true)
@SuperBuilder(toBuilder = true)
public class InterviewQuestionResponseDto extends InterviewQuestionRequestDto {

    private UUID id;

    public static InterviewQuestionResponseDto of(InterviewQuestion interviewQuestion) {
        return InterviewQuestionResponseDto.builder()
                .id(interviewQuestion.getId())
                .title(interviewQuestion.getTitle())
                .build();
    }
}