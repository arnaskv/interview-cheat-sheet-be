package com.interview.manager.backend.services.comment;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.List;
import java.util.stream.Collectors;

import com.interview.manager.backend.models.InterviewQuestion;
import com.interview.manager.backend.repositories.InterviewQuestionRepository;
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

    public Optional<CommentResponseDto> getById(UUID id) {
        return commentRepository.findById(id)
            .map(commentMapper::map);
    }

    public List<CommentResponseDto> getAllByQuestionId(Long questionId) {
        return commentRepository.getAllByQuestionId(questionId).stream()
            .map(commentMapper::map)
            .collect(Collectors.toList());
    }

    public CommentResponseDto createComment(Long questionId, CommentRequestDto CommentRequestDto) throws IllegalStateException {
        InterviewQuestion question = interviewQuestionRepository.findById(questionId)
            .orElseThrow(() -> new IllegalStateException("Question not found with ID: " + questionId));

        Comment comment = CommentMapper.map(CommentRequestDto, question);

        comment = commentRepository.save(comment);

        return this.getById(comment.getId()).orElseThrow(IllegalStateException::new);
    }

    public void deleteById(UUID id) {
        commentRepository.findById(id)
            .ifPresentOrElse(
                comment -> commentRepository.deleteById(id),
                () -> {
                    throw new NoSuchElementException("Comment with ID " + id + " not found");
                }
            );
    }
}
