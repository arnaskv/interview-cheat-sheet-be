package com.interview.manager.backend.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.Setter;
import lombok.RequiredArgsConstructor;

@Entity
@Table(name = "interview_category")
@Getter
@Setter
@NoArgsConstructor
@RequiredArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private Long id;

    @NonNull
    @Column(name = "category_title", length = 256, nullable = false)
    private String title;

}
