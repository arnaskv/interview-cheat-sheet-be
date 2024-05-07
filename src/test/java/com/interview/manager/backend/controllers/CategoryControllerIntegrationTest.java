package com.interview.manager.backend.controllers;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.interview.manager.backend.models.Category;
import com.interview.manager.backend.repositories.CategoryRepository;

import jakarta.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
@WithMockUser
public class CategoryControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    void setUp() {
        clearRepository();
        setupTestData();
    }

    @AfterEach
    void tearDown() {
        clearRepository();
    }

    private void setupTestData() {
        Category category1 = new Category();
        category1.setTitle("Category 1");
        categoryRepository.save(category1);

        Category category2 = new Category();
        category2.setTitle("Category 2");
        categoryRepository.save(category2);
    }

    private void clearRepository() {
        categoryRepository.deleteAll();
    }

    @Test
    void getAllCategoriesReversed() throws Exception {
        mockMvc.perform(get("/api/v1/category"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$[0].id").exists())
            .andExpect(jsonPath("$[0].title").value("Category 2"))
            .andDo(print());
    }

    @Test
    void getCategoryById() throws Exception {
        Long categoryId = categoryRepository.findAll().get(0).getId();
        mockMvc.perform(get("/api/v1/category/{id}", categoryId))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(categoryId))
            .andExpect(jsonPath("$.title").isNotEmpty())
            .andDo(print());
    }

    @Test
    void getCategoryById_NotFound() throws Exception {
        mockMvc.perform(get("/api/v1/category/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound())
            .andDo(print());
    }

    @Test
    void createCategory() throws Exception {
        String categoryJson = "{\"title\":\"New Category\"}";
        mockMvc.perform(post("/api/v1/category")
            .contentType(MediaType.APPLICATION_JSON)
            .content(categoryJson))
            .andExpect(status().isCreated())
            .andExpect(header().exists("Location"))
            .andExpect(jsonPath("$.id").exists())
            .andExpect(jsonPath("$.title").value("New Category"))
            .andDo(print());
    }

    @Test
    void createCategoryWithInvalidData() throws Exception {
        String categoryJson = "{\"title\":\"\"}";
        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void editCategory() throws Exception {
        Long categoryId = categoryRepository.findAll().get(0).getId();
        String categoryJson = "{\"id\": \"" + categoryId + "\", \"title\":\"Updated Category\"}";
        mockMvc.perform(patch("/api/v1/category")
            .contentType(MediaType.APPLICATION_JSON)
            .content(categoryJson))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.title").value("Updated Category"))
            .andDo(print());
    }

    @Test
    void editNonExistingCategory() throws Exception {
        String categoryJson = "{\"id\": \"999\", \"title\":\"Non-existing Category\"}";
        mockMvc.perform(patch("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void deleteCategoryById() throws Exception {
        Long categoryId = categoryRepository.findAll().get(0).getId();
        mockMvc.perform(delete("/api/v1/category/{id}", categoryId))
            .andExpect(status().isNoContent())
            .andDo(print());
    }

    @Test
    void deleteNonExistingCategoryById() throws Exception {
        mockMvc.perform(delete("/api/v1/category/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    void createCategoryWithTooLongTitle() throws Exception {
        String categoryJson = "{\"title\":\"" + "a".repeat(300) + "\"}";
        mockMvc.perform(post("/api/v1/category")
                .contentType(MediaType.APPLICATION_JSON)
                .content(categoryJson))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    void getCategoryCheckResponseHeaders() throws Exception {
        Long categoryId = categoryRepository.findAll().get(0).getId();
        mockMvc.perform(get("/api/v1/category/{id}", categoryId))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/json"))
                .andDo(print());
    }

}