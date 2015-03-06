package com.nexera.mongo.util;

import java.net.UnknownHostException;
import java.util.Arrays;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

public class MongoConnector {
	private static final DB DATABASE;

	static {
		try {
			String username = MongoConstants.USERNAME;
			MongoClient client;
			// Attempt authentication only if username is defined
			if (username != null && username.length() != 0) {
				MongoCredential credential = MongoCredential
						.createMongoCRCredential(username, MongoConstants.HOST,
								MongoConstants.PASSWORD.toCharArray());
				client = new MongoClient(new ServerAddress(MongoConstants.HOST,
						MongoConstants.PORT), Arrays.asList(credential));
			} else {
				client = new MongoClient(MongoConstants.HOST,
						MongoConstants.PORT);
			}

			DATABASE = client.getDB(MongoConstants.DB_NAME);

		} catch (UnknownHostException e) {
			throw new IllegalStateException(
					"Exception thrown when Mongo was being initialized");
		}
	}

	/**
	 * Returns a DBCollection corresponding to <b>collectionName</b>
	 * 
	 * @param collectionName
	 * @return
	 */
	public static DBCollection getCollection(String collectionName) {
		return DATABASE.getCollection(collectionName);
	}
}
