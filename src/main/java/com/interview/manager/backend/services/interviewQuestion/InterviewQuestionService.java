package com.interview.manager.backend.services.interviewQuestion;

import com.interview.manager.backend.dto.InterviewQuestionEditRequestDto;
import com.interview.manager.backend.dto.InterviewQuestionRequestDto;
import com.interview.manager.backend.dto.InterviewQuestionResponseDto;
import com.interview.manager.backend.dto.InterviewSubQuestionDto;
import com.interview.manager.backend.exceptions.DataValidationException;
import com.interview.manager.backend.models.Category;
import com.interview.manager.backend.models.InterviewQuestion;
import com.interview.manager.backend.repositories.CategoryRepository;
import com.interview.manager.backend.repositories.InterviewQuestionRepository;
import com.interview.manager.backend.types.DataValidation;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class InterviewQuestionService {
    private final InterviewQuestionRepository interviewQuestionRepository;
    private final CategoryRepository categoryRepository;
    private static final InterviewQuestionMapper MAPPER = InterviewQuestionMapper.INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(InterviewQuestion.class);

    public Optional<InterviewQuestionResponseDto> findById(Long interviewId) {
        Optional<InterviewQuestion> optionalInterviewQuestion = interviewQuestionRepository.findById(interviewId);
        return optionalInterviewQuestion.map(InterviewQuestionResponseDto::of);
    }

    public List<InterviewQuestionResponseDto> getAllInterviewQuestions() {
        List<InterviewQuestion> parentQuestions = interviewQuestionRepository.findAllByParentQuestionIsNull();

        return parentQuestions.stream()
            .map(parentQuestion -> {
                InterviewQuestionResponseDto parentResponseDto = MAPPER.questionToResponseDto(parentQuestion);
                List<InterviewQuestionResponseDto> subQuestionsDtoList = parentQuestion.getSubQuestions().stream()
                    .map(MAPPER::questionToResponseDto)
                    .toList();
                parentResponseDto.setSubQuestions(subQuestionsDtoList);
                return parentResponseDto;
            })
            .toList();
    }

    @Transactional
    public InterviewQuestionResponseDto createInterviewQuestion(InterviewQuestionRequestDto requestDto) {
        Category category = categoryRepository.findById(requestDto.getCategoryId())
            .orElseThrow(() -> new DataValidationException(DataValidation.Status.NOT_FOUND, "Category not found"));

        InterviewQuestion parentQuestion = MAPPER.requestDtoToInterviewQuestion(requestDto);

        List<InterviewQuestion> savedSubQuestions = createSubQuestions(requestDto, category, parentQuestion);

        parentQuestion.setSubQuestions(savedSubQuestions);
        parentQuestion.setCategory(category);
        parentQuestion = interviewQuestionRepository.save(parentQuestion);

        return MAPPER.questionToResponseDto(parentQuestion);
    }

    private List<InterviewQuestion> createSubQuestions(InterviewQuestionRequestDto requestDto, Category category, InterviewQuestion parentQuestion) {
        List<InterviewQuestion> subQuestions = requestDto.getSubQuestions().stream()
            .map(subQuestionDto -> {
                InterviewQuestion subQuestion = MAPPER.requestSubQuestionDtoToInterviewQuestion(subQuestionDto);
                subQuestion.setCategory(category);
                subQuestion.setParentQuestion(parentQuestion);
                return subQuestion;
            })
            .toList();

        return interviewQuestionRepository.saveAll(subQuestions);
    }

    @Transactional
    public InterviewQuestionResponseDto createInterviewQuestion(InterviewSubQuestionDto requestDto, Long parentId) {
        InterviewQuestion parentQuestion = interviewQuestionRepository.findById(parentId)
            .orElseThrow(() -> new DataValidationException(DataValidation.Status.NOT_FOUND, "Category not found"));

        InterviewQuestion subQuestion = MAPPER.requestSubQuestionDtoToInterviewQuestion(requestDto);
        subQuestion.setCategory(parentQuestion.getCategory());
        subQuestion.setParentQuestion(parentQuestion);

        InterviewQuestion savedSubQuestion = interviewQuestionRepository.save(subQuestion);

        return MAPPER.questionToResponseDto(savedSubQuestion);
    }


    @Transactional
    public InterviewQuestionResponseDto editInterviewQuestion(InterviewQuestionEditRequestDto requestDto) {
        InterviewQuestion question = interviewQuestionRepository.findById(requestDto.getId())
            .orElseThrow(() -> new NoSuchElementException("Question with ID " + requestDto.getId() + " not found"));

        if (requestDto.getTitle() != null && !requestDto.getTitle().isEmpty()) {
            question.setTitle(requestDto.getTitle());
        }

        if (requestDto.getCategoryId() != null) {
            Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category with ID " + requestDto.getCategoryId() + " not found"));

            question.setCategory(category);
        }

        InterviewQuestion updatedQuestion = interviewQuestionRepository.save(question);
        return MAPPER.questionToResponseDto(updatedQuestion);
    }


    @Transactional
    public void deleteInterviewQuestionById(Long id) {
        interviewQuestionRepository.findById(id)
            .ifPresentOrElse(
                interviewQuestion -> interviewQuestionRepository.deleteById(id),
                () -> {
                    throw new NoSuchElementException("Interview question with an ID " + id + " not found");
                }
            );
    }
}
