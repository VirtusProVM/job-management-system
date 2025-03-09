package com.jobentry.service;

import com.jobentry.entity.Category;
import com.jobentry.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> getAll() {

        return categoryRepository.findAll().stream().collect(Collectors.toList());
    }

    public Category getCurrentCategory(Integer id) {
        return categoryRepository.getCategoryByID(id);
    }

    public Category getSelectedCategory(int id) {
        return categoryRepository.findById(id).orElse(new Category());
    }

    public Category getCategoryByName(String categoryName) {
        return categoryRepository.getCategoryByName(categoryName);
    }

    public void saveCategory(Category category) {
        categoryRepository.save(category);
    }
}
