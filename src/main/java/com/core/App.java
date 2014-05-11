package com.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;

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
			auth = db.authenticate("nicolas513", "12345".toCharArray());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	  return auth;
		
	}
	


	
	public static void buscarItem(String parametro){
		
		 
		String site = null;
		String query = null;
		String currency = null;
		String stop_time = null;
		String condition = null;

		String titles = null;
		String subtitle = null;
		String price = null;
		String thumbnail = null;
		String pic = null;
		//String parametro = name.replaceAll(" ", "");
		String html = "";
		String id= null;
		//System.out.println(parametro);
		String symbolCurrency=null;
		String [] s = new String[50];
		
		
		DB db = mongo.getDB("nicoDB");
		
		/**** Get collection / table from 'testdb' ****/
		// if collection doesn't exists, MongoDB will create it for you
		DBCollection table = db.getCollection("coleccion_ipod");
		
		
		try {
		
			
			HttpClient client = new DefaultHttpClient();
		    
		    HttpGet get = new HttpGet("https://api.mercadolibre.com/sites/MLU/search?q="+ parametro);

		    HttpResponse response = client.execute(get);
		    BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

		    StringBuffer result = new StringBuffer();
		    String line = "";
		    while ((line = rd.readLine()) != null) {
		        result.append(line);
		    }

		    JSONObject json =(JSONObject) JSONSerializer.toJSON(result.toString());
		    
			site = json.getString("site_id");
			query = json.getString("query");

			// Obtengo el array de items
			JSONObject p = (JSONObject) json.get("paging");
			String total = p.getString("total");
			System.out.println("Total: " + total);

			JSONArray results = json.getJSONArray("results");
			
			System.out.println(results);
			//recorro el array de resultados.
			//for (int i = 0; i < results.size(); i++) {
				for (int i = 0; i < 10; i++) {

				JSONObject array = (JSONObject) results.get(i);
				titles = array.getString("title");

				/**** Insert ****/
				// create a document to store key and value
				BasicDBObject document = new BasicDBObject();
				document.put("_id", array.getString("id"));
				document.put("site_id", array.getString("site_id"));
				document.put("title", array.getString("title"));
				subtitle = array.getString("subtitle");
				if(subtitle.equals("null")){
					subtitle= "";
				}else{
					document.put("subtitle", subtitle);
					}
				
				document.put("price", array.getString("price"));
				document.put("permalink", array.getString("permalink"));
				document.put("thumbnail", array.getString("thumbnail"));
				document.put("currency_id", array.getString("currency_id"));
			
				table.insert(document);
						
				
			}
		


		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}
	
	
	public void insertDocument(String collection, String doc){
		
		DB db = mongo.getDB("nicoDB");
		
		/**** Get collection / table from 'testdb' ****/
		// if collection doesn't exists, MongoDB will create it for you
		DBCollection table = db.getCollection(collection);
		
		/**** Insert ****/
		// create a document to store key and value
		BasicDBObject document = new BasicDBObject();
		document.put("_id", "nico.tef22@gmail.com");
		document.put("name", "nicolas");
		document.put("age", 30);
		document.put("telefono", "093818108");
		document.put("createdDate", new Date());
		table.insert(document);
		
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
			/*BasicDBObject document = new BasicDBObject();
			document.put("_id", "nico.tef22@gmail.com");
			document.put("name", "nicolas");
			document.put("age", 30);
			document.put("telefono", "093818108");
			document.put("createdDate", new Date());
			table.insert(document);*/
			
			
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

			buscarItem("ipad");
		
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
