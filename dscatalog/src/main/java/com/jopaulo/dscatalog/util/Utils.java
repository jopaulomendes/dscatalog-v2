package com.jopaulo.dscatalog.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jopaulo.dscatalog.entities.Product;
import com.jopaulo.dscatalog.projections.ProductProjection;

public class Utils {

	public static List<Product> replace(List<ProductProjection> ordered, List<Product> unordered) {
		
		Map<Long, Product> map = new HashMap<>();
		for (Product product : unordered) {
			map.put(product.getId(), product);
		}
		
		List<Product> result = new ArrayList<>();
		for (ProductProjection productProjection : ordered) {
			result.add(map.get(productProjection.getId()));
		}
		
		return result;
	}

}
