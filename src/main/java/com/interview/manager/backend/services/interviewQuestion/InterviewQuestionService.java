package com.interview.manager.backend.services.interviewQuestion;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        if (parentQuestion.getSubQuestions() != null) {
            List<InterviewQuestion> savedSubQuestions = createSubQuestions(requestDto, category, parentQuestion);
            parentQuestion.setSubQuestions(savedSubQuestions);
        }

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
    public InterviewQuestionResponseDto editInterviewQuestion(InterviewQuestionRequestDto requestDto, Long id) {
        InterviewQuestion question = interviewQuestionRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Question with ID " + id + " not found"));

        if (!requestDto.getTitle().equals(question.getTitle())) {
            question.setTitle(requestDto.getTitle());
        }

        if (!requestDto.getCategoryId().equals(question.getCategory().getId())) {
            Category category = categoryRepository.findById(requestDto.getCategoryId())
                .orElseThrow(() -> new NoSuchElementException("Category with ID " + requestDto.getCategoryId() + " not found"));
            question.setCategory(category);
        }

        if (requestDto.getSubQuestions() != null && !requestDto.getSubQuestions().isEmpty()) {
            List<InterviewQuestion> newSubQuestions = addNewSubQuestions(requestDto, question);
            if (!newSubQuestions.isEmpty()) {
                question.getSubQuestions().addAll(newSubQuestions);
            }
        }
        question = interviewQuestionRepository.save(question);

        return MAPPER.questionToResponseDto(question);
    }

    private List<InterviewQuestion> addNewSubQuestions(InterviewQuestionRequestDto requestDto, InterviewQuestion question) {
        List<InterviewQuestion> existingSubQuestions = question.getSubQuestions();
        List<InterviewSubQuestionDto> subQuestionsToAdd = requestDto.getSubQuestions().stream()
            .filter(subQuestion -> existingSubQuestions.stream()
                .noneMatch(existingSubQuestion -> existingSubQuestion.getTitle().equals(subQuestion.getTitle())))
            .toList();

        return subQuestionsToAdd.stream()
            .map(MAPPER::requestSubQuestionDtoToInterviewQuestion)
            .peek(subQuestion -> {
                subQuestion.setCategory(question.getCategory());
                subQuestion.setParentQuestion(question);
            })
            .toList();
    }

    @Transactional
    public void deleteInterviewQuestionById(Long id) {
        InterviewQuestion questionToDelete = interviewQuestionRepository.findById(id)
            .orElseThrow(() -> new NoSuchElementException("Interview question with ID " + id + " not found"));

        if (questionToDelete.getParentQuestion() != null) {
            InterviewQuestion parentQuestion = questionToDelete.getParentQuestion();
            parentQuestion.getSubQuestions().remove(questionToDelete);
            interviewQuestionRepository.save(parentQuestion);
            interviewQuestionRepository.delete(questionToDelete);
        } else {
            interviewQuestionRepository.deleteById(id);
        }
    }
}
