package com.core;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

/**
 * Hello world!
 *
 */
public class App {
	
	
	public static void main(String[] args) {
		System.out.println("Mongo DB World!");
		
		try {

			/**** Connect to MongoDB ****/
			MongoClient mongo = new MongoClient("localhost", 27017);
			
			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("nicoDB");
		
			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("users");
			
			/**** Insert ****/
			// create a document to store key and value
			/*BasicDBObject document = new BasicDBObject();
			document.put("name", "nicolas");
			document.put("age", 30);
			document.put("telefono", "093818108");
			document.put("createdDate", new Date());
			table.insert(document);
			*/
			
			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("name", "nicolas");
			
			DBCursor cursor = table.find(searchQuery);
			
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
			
			/**** Update ****/
			// search document where name="mkyong" and update it with new values
			BasicDBObject query = new BasicDBObject();
			query.put("name", "nicolas");
		 
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("name", "nico");
		 
			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);
		 
			table.update(query, updateObj);
			
			
			
			
			
			
			
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		
	}
}
