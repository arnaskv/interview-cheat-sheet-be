package com.interview.manager.backend.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseCategoryDto {

    private Long id;
    private String title;
}
