package com.jopaulo.dscatalog.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.jopaulo.dscatalog.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
