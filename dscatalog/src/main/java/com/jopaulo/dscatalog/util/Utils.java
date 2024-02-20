package com.jopaulo.dscatalog.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jopaulo.dscatalog.projections.idProjection;

public class Utils {

	public static <ID> List<? extends idProjection<ID>> replace(List<? extends idProjection<ID>> ordered,
			List<? extends idProjection<ID>> unordered) {

		Map<ID, idProjection<ID>> map = new HashMap<>();
		for (idProjection<ID> obj : unordered) {
			map.put(obj.getId(), obj);
		}

		List<idProjection<ID>> result = new ArrayList<>();
		for (idProjection<ID> obj : ordered) {
			result.add(map.get(obj.getId()));
		}

		return result;
	}

}
