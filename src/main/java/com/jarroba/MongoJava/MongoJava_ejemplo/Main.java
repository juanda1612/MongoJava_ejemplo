package com.jarroba.MongoJava.MongoJava_ejemplo;

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;

public class Main {

	public static void main(String[] args) {

		// Crear una lista de futbolistas
		ArrayList<Futbolista> futbolistas = new ArrayList<>();

		futbolistas.add(new Futbolista("Iker", "Casillas", 33, new ArrayList<>(Arrays.asList("Portero")), true));
		futbolistas.add(new Futbolista("Carles", "Puyol", 36, new ArrayList<>(Arrays.asList("Central", "Lateral")), true));
		futbolistas.add(new Futbolista("Sergio", "Ramos", 28, new ArrayList<>(Arrays.asList("Lateral", "Central")), true));
		futbolistas.add(new Futbolista("Andrés", "Iniesta", 30, new ArrayList<>(Arrays.asList("Centrocampista", "Delantero")), true));
		futbolistas.add(new Futbolista("Fernando", "Torres", 30, new ArrayList<>(Arrays.asList("Delantero")), true));
		futbolistas.add(new Futbolista("Leo", "Baptistao", 22, new ArrayList<>(Arrays.asList("Delantero")), false));

		// Conexión al servidor de MongoDB
		try (MongoClient mongoClient = MongoClients.create("mongodb://192.168.10.51:27017")) {

			// PASO 1: Conexión al Server de MongoDB
			System.out.println("PASO 1: Conexión realizada: " + mongoClient.getClusterDescription() + "\n");

			// PASO 2: Conexión a la base de datos
			MongoDatabase database = mongoClient.getDatabase("Futbol");
			System.out.println("PASO 2: Conexión realizada: " + database.getName() + "\n");

			// PASO 3: Obtenemos una colección para trabajar con ella
			MongoCollection<Document> collection = database.getCollection("Futbolistas");
			System.out.println("PASO 3: Conexión realizada: " + collection.getNamespace() + "\n");

			// Limpieza de la colección (opcional)
			collection.drop();

			// PASO 4.1: "CREATE" -> Metemos los objetos futbolistas en la colección
			for (Futbolista fut : futbolistas) {
				collection.insertOne(fut.toDocument());
			}
			System.out.println("PASO 4.1: Número de documentos en la colección Futbolistas: " + collection.countDocuments() + "\n");

			// PASO 4.2.1: "READ" -> Leemos todos los documentos de la base de datos
			System.out.println("\nPASO 4.2.1: Futbolistas de la colección:");
			for (Document doc : collection.find()) {
				System.out.println(doc.toJson());
			}

			// PASO 4.2.2: "READ" -> Hacemos una Query con condiciones y lo pasamos a un objeto Java
			System.out.println("\nPASO 4.2.2: Futbolistas que juegan en la posición de Delantero:");
			for (Document doc : collection.find(Filters.regex("demarcacion", "Delantero"))) {
				System.out.println(doc.toJson());
			}

			// PASO 4.3: "UPDATE" -> Actualizamos la edad de los jugadores mayores de 30 años
			collection.updateMany(Filters.gt("edad", 30), Updates.inc("edad", 100));
			System.out.println("\nPASO 4.3: Futbolistas después de la modificación y antes del borrado:");
			for (Document doc : collection.find()) {
				System.out.println(doc.toJson());
			}

			// PASO 4.4: "DELETE" -> Borramos todos los futbolistas que sean internacionales
			collection.deleteMany(Filters.eq("internacional", true));
			System.out.println("\nPASO 4.4: Futbolistas después del borrado:");
			for (Document doc : collection.find()) {
				System.out.println(doc.toJson());
			}

		} catch (Exception e) {
			System.out.println("Exception al conectar al servidor de Mongo: " + e.getMessage());
		}
	}
}
