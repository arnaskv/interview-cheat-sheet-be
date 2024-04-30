package com.interview.manager.backend.services.comment;

import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import com.interview.manager.backend.exceptions.DataValidationException;
import com.interview.manager.backend.models.InterviewQuestion;
import com.interview.manager.backend.repositories.InterviewQuestionRepository;
import com.interview.manager.backend.types.DataValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import com.interview.manager.backend.dto.CommentResponseDto;
import com.interview.manager.backend.dto.CommentRequestDto;
import com.interview.manager.backend.models.Comment;
import com.interview.manager.backend.repositories.CommentRepository;
import com.interview.manager.backend.services.comment.mapper.CommentMapper;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final CommentMapper Mapper = CommentMapper.INSTANCE;
    private final InterviewQuestionRepository interviewQuestionRepository;

    public List<CommentResponseDto> getAll() {
        return commentRepository
            .findAll()
            .stream()
            .map(Mapper::commentToResponseDto)
            .collect(Collectors.toList());
    }

    public Optional<CommentResponseDto> getById(UUID id) {
        return commentRepository.findById(id)
            .map(Mapper::commentToResponseDto);
    }

    public List<CommentResponseDto> getAllByQuestionId(Long questionId) {
        if(!interviewQuestionRepository.existsById(questionId)) {
            throw new DataValidationException(DataValidation.Status.NOT_FOUND, "Question not found");
        }

        return commentRepository.getAllByQuestionId(questionId).stream()
            .map(Mapper::commentToResponseDto)
            .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto create(Long questionId, CommentRequestDto commentRequestDto) {
        InterviewQuestion question = interviewQuestionRepository.findById(questionId)
            .orElseThrow(() -> new DataValidationException(DataValidation.Status.NOT_FOUND, "Question not found"));

        Comment comment = Mapper.requestDtoToComment(commentRequestDto, question);
        comment = commentRepository.save(comment);

        return Mapper.commentToResponseDto(comment);
    }

    public void deleteById(UUID id) {
        commentRepository.findById(id)
            .ifPresentOrElse(
                comment -> commentRepository.deleteById(id),
                () -> {
                    throw new DataValidationException(DataValidation.Status.NOT_FOUND, "Comment not found");
                }
            );
    }
}
