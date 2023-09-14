package com.jopaulo.dscatalog.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jopaulo.dscatalog.entities.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {

}
