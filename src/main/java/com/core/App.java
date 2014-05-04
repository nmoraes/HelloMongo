package com.core;

import java.net.UnknownHostException;
import java.util.Date;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;

/**
 * Batch procces that connect with mongo DB
 * 
 * @author nico moraes
 *
 */
public class App {
	
	
	private static MongoClient mongo;
	
	public static boolean autenticar(){	
	    
	    boolean auth = false;
		try {
			/**** Connect to MongoDB ****/
			mongo = new MongoClient("localhost", 27017);
			
			DB db = mongo.getDB("nicoDB");
			auth = db.authenticate("nicolas2", "12345".toCharArray());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  return auth;
		
	}
	
	
	
	
	public static void main(String[] args) {
		
		System.out.println("Mongo DB World!");
		
		

			boolean auth = autenticar();
			
			if(auth){
			
			/**** Get database ****/
			// if database doesn't exists, MongoDB will create it for you
			DB db = mongo.getDB("nicoDB");
		
			/**** Get collection / table from 'testdb' ****/
			// if collection doesn't exists, MongoDB will create it for you
			DBCollection table = db.getCollection("users");
			
			/**** Insert ****/
			// create a document to store key and value
			BasicDBObject document = new BasicDBObject();
			document.put("_id", "nico.tef@gmail.com");
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
			
			/**** Update ****/
			// search document where name="mkyong" and update it with new values
			BasicDBObject query = new BasicDBObject();
			query.put("name", "nicolas");
		 
			BasicDBObject newDocument = new BasicDBObject();
			newDocument.put("name", "nico");
		 
			BasicDBObject updateObj = new BasicDBObject();
			updateObj.put("$set", newDocument);
		 
			table.update(query, updateObj);
			
				
	
		} else {
			
			System.out.println("Login is failed!");
			
		}
		
	}
}

//usuario administrador
//> use admin
//switched to db admin
//> db.addUser("administrador","12345")

//usuario nicodb
//users
//db.addUser("nicolasDB","12345") 
//> db.addUser("nicolas2","12345")
/*
 levantar el cliente:
  
  >./mongo								(levanto el cliente)
  > use nicoDB							(cambio de base)
  > db.auth("nicolasDB","12345")    (login en la db)
  > show collections    (muestra todas las collecciones)
  > db.users.find().pretty()   (busca en la colleccion users)
 
 > db.grantRolesToUser("nicolas513",[{ role: "readWrite", db: "nicoDB" }])
> db.system.users.find().pretty()
 
 > db.createUser({ user: "tefa",pwd: "kiko", readOnly = false,roles: [ { role: "readWrite", db: "nicoDB" }]})
 
 db.createUser({ user: "tefa",pwd: "kiko", readOnly = false,roles: [ { role: "readWrite", db: "nicoDB" }]})
 *
 *
 **/
