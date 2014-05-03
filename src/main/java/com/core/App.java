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
	
	

	//\
	//private static String json = "({\"user_id\" : \"nicomoraes\",\"password\" :\"01234\" ,\"date_of_join\" : \"16/10/2010\" ,\"community_members\" : [500,200,1500],"friends_id" : ["MMM123","NNN123","OOO123"],"ban_friends_id" :["BAN123","BAN456","BAN789"]}); - See more at: http://www.w3resource.com/mongodb/mongodb-remove-collection.php#sthash.fd8g2VfX.dpuf"
	
	
	
	
	
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
			BasicDBObject document = new BasicDBObject();
			document.put("name", "nicolas");
			document.put("age", 30);
			document.put("telefono", "093818108");
			document.put("createdDate", new Date());
			table.insert(document);
			
			/**** Find and display ****/
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("name", "nicolas");
			
			DBCursor cursor = table.find(searchQuery);
			
			while (cursor.hasNext()) {
				System.out.println(cursor.next());
			}
			
		
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	 
		
	}
}
