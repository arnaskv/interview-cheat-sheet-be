package com.interview.manager.backend.services.comment;

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

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;


    private final CommentMapper commentMapper;
    private final InterviewQuestionRepository interviewQuestionRepository;

    public List<CommentResponseDto> getAll() {
        return commentRepository.findAll().stream()
            .map(commentMapper::map)
            .collect(Collectors.toList());
    }

    public CommentResponseDto getById(UUID id) {
        Comment comment = commentRepository.findById(id)
            .orElseThrow(() -> new DataValidationException(DataValidation.Status.NOT_FOUND, "Comment not found"));

        return commentMapper.map(comment);
    }

    public List<CommentResponseDto> getAllByQuestionId(Long questionId) {
        if(!interviewQuestionRepository.existsById(questionId)) {
            throw new DataValidationException(DataValidation.Status.NOT_FOUND, "Question not found");
        }

        return commentRepository.getAllByQuestionId(questionId).stream()
            .map(commentMapper::map)
            .collect(Collectors.toList());
    }

    public CommentResponseDto create(Long questionId, CommentRequestDto CommentRequestDto) {
        InterviewQuestion question = interviewQuestionRepository.findById(questionId)
            .orElseThrow(() -> new DataValidationException(DataValidation.Status.NOT_FOUND, "Question not found"));

        Comment comment = CommentMapper.map(CommentRequestDto, question);
        comment = commentRepository.save(comment);

        return commentMapper.map(comment);
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
