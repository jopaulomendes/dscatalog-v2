package com.jopaulo.dscatalog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DscatalogApplication {

	public static void main(String[] args) {
		SpringApplication.run(DscatalogApplication.class, args);
	}
	
//	SELECT DISTINCT TB_PRODUCT.ID, TB_PRODUCT.NAME
//	FROM TB_PRODUCT 
//	INNER JOIN TB_PRODUCT_CATEGORY ON TB_PRODUCT.ID = TB_PRODUCT_CATEGORY.PRODUCT_ID
//	WHERE TB_PRODUCT_CATEGORY.CATEGORY_ID IN (1,3)
//	AND LOWER(TB_PRODUCT.NAME) LIKE '%ma%'
//	ORDER BY TB_PRODUCT.NAME

}
