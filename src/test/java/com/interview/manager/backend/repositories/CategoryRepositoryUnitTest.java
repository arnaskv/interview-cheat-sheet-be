package com.interview.manager.backend.repositories;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.ActiveProfiles;

import com.interview.manager.backend.models.Category;

@DataJpaTest
@ActiveProfiles("test")
public class CategoryRepositoryUnitTest {
    
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private CategoryRepository categoryRepository;

    @BeforeEach
    public void setup() {
        entityManager.clear();
        Category category1 = new Category("Category 1");
        Category category2 = new Category("Category 2");
        entityManager.persist(category1);
        entityManager.persist(category2);
        entityManager.flush();
    }

    @Test
    void testFindAllByOrderByIdDesc() {
        List<Category> categories = categoryRepository.findAllByOrderByIdDesc();
        assertEquals(2, categories.size());
        assertEquals("Category 2", categories.get(0).getTitle());
        assertEquals("Category 1", categories.get(1).getTitle());
    }
}
