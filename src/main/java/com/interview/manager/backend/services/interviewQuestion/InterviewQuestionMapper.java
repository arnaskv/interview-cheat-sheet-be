package com.interview.manager.backend.services.interviewQuestion;

import com.interview.manager.backend.dto.InterviewQuestionRequestDto;
import com.interview.manager.backend.dto.InterviewQuestionResponseDto;
import com.interview.manager.backend.dto.InterviewSubQuestionDto;
import com.interview.manager.backend.models.InterviewQuestion;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface InterviewQuestionMapper {
    InterviewQuestionMapper INSTANCE = Mappers.getMapper(InterviewQuestionMapper.class);

    InterviewQuestion requestDtoToInterviewQuestion(InterviewQuestionRequestDto interviewQuestionRequestDto);

    InterviewQuestion requestSubQuestionDtoToInterviewQuestion(InterviewSubQuestionDto interviewSubQuestionDto);
    InterviewQuestionResponseDto questionToResponseDto(InterviewQuestion question);
}
