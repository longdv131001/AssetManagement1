package com.nashtech.rookie.assetmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.nashtech.rookie.assetmanagement.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
