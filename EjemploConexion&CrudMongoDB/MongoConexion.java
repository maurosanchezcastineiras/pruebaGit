/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.mongodbtiempo;

/**
 *
 * @author aleja
 */
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoConexion {
    private MongoDatabase bd;

    public MongoConexion(String db) {
        MongoClient mongoClient = MongoClients.create("mongodb://localhost:27017");
        this.bd = mongoClient.getDatabase(db);
    }

    public MongoDatabase getBd() {
        return bd;
    }
}

