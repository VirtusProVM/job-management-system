package com.jobentry.repository;

import com.jobentry.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query(value = "SELECT * FROM category c WHERE c.categoryID = ?", nativeQuery = true)
    public Category getCategoryByID(Integer id);

    @Query(value = "SELECT * FROM category c WHERE c.category_name = ?", nativeQuery = true)
    public Category getCategoryByName(String categoryName);
}
