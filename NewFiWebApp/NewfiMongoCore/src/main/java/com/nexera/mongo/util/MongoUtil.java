package com.nexera.mongo.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.mongodb.BasicDBList;

/**
 * 
 * Helper methods for MongoDB related operations
 * 
 * @author Samarth Bhargav
 * 
 */
public class MongoUtil {
	public static List<String> dbListToStringList(BasicDBList list) {
		if (list == null || list.isEmpty()) {
			return Collections.emptyList();
		}
		List<String> s = new ArrayList<String>();
		for (Object o : list) {
			s.add(o.toString());
		}
		return s;
	}

	public static List<Long> dbListToLongList(BasicDBList list) {
		if (list == null || list.isEmpty()) {
			return Collections.emptyList();
		}
		List<Long> s = new ArrayList<Long>();
		for (Object o : list) {
			s.add((Long) o);
		}
		return s;
	}

	public static String safeToString(Object o) {
		return o == null ? null : o.toString();
	}
}
