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
		try (MongoClient mongoClient = MongoClients.create("mongodb://localHost:27017")) {

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
			System.out.println("PASO 4.1: Número de documentos en la colección Futbolistas: " + collection.countDocuments() + "\n");
			for (Futbolista fut : futbolistas) {
				collection.insertOne(fut.toDocument());
			}

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
			System.out.println("\nPASO 4.3: Futbolistas después de la modificación y antes del borrado:");
			collection.updateMany(Filters.gt("edad", 30), Updates.inc("edad", 100));
			for (Document doc : collection.find()) {
				System.out.println(doc.toJson());
			}

			// PASO 4.4: "DELETE" -> Borramos todos los futbolistas que sean internacionales
			System.out.println("\nPASO 4.4: Futbolistas después del borrado:");
			collection.deleteMany(Filters.eq("internacional", true));
			for (Document doc : collection.find()) {
				System.out.println(doc.toJson());
			}

			// *** CONCIERTOS ***
			ArrayList<Concierto> conciertos = new ArrayList<>();

			conciertos.add(new Concierto("SONATA ARCTICA", "Conciertos WiZink Center - Madrid", "Viernes 04 octubre 2024", "20:00 H."));
			conciertos.add(new Concierto("LOS CHICHOS - HASTA AQUI HEMOS LLEGADO (2)", "Conciertos WiZink Center - Madrid", "Viernes 04 octubre 2024", "20:45 H."));
			conciertos.add(new Concierto("LA PEGATINA - HASTA LUEGO MARI CARMEN", "Conciertos WiZink Center - Madrid", "Sabado 05 octubre 2024", "20:30 H."));
			conciertos.add(new Concierto("CEPEDA - CONTRADICCION TOUR", "Conciertos WiZink Center - Madrid", "Sabado 05 octubre 2024", "21:00 H."));
			conciertos.add(new Concierto("RAFA ESPINO - 15 ANIVERSARIO", "Conciertos WiZink Center - Madrid", "Domingo 06 octubre 2024", "19:00 H."));
			conciertos.add(new Concierto("MELANIE MARTINEZ - THE TRILOGY TOUR", "Conciertos WiZink Center - Madrid", "Lunes 07 octubre 2024", "21:00 H."));
			conciertos.add(new Concierto("MEUTE - EMPOR 2024", "Conciertos WiZink Center - Madrid", "Miercoles 09 octubre 2024", "21:00 H."));
			conciertos.add(new Concierto("CRUSH - OCTUBRE 2024", "Conciertos WiZink Center - Madrid", "Jueves 10 octubre 2024", "19:30 H."));
			conciertos.add(new Concierto("DABUTI - OCTUBRE 2024", "Conciertos WiZink Center - Madrid", "Viernes 11 octubre 2024", "18:30 H."));
			conciertos.add(new Concierto("LA CASA AZUL", "Conciertos WiZink Center - Madrid", "Viernes 11 octubre 2024", "21:00 H."));

			// Conexión a la base de datos
			MongoDatabase database2 = mongoClient.getDatabase("Concierto");
			System.out.println("Conexión realizada: " + database2.getName() + "\n");

			// PASO 5: Obtenemos una colección para trabajar con ella
			MongoCollection<Document> collection2 = database2.getCollection("Conciertos");
			System.out.println("PASO 5: Conexión realizada: " + collection2.getNamespace() + "\n");

			// Limpieza de la colección (opcional)
			collection2.drop();

			// PASO 6.1: "CREATE" -> Metemos los objetos Conciertos en la colección
			System.out.println("PASO 6.1: Número de documentos en la colección Conciertos: " + collection2.countDocuments() + "\n");
			for (Concierto con : conciertos) {
				collection2.insertOne(con.toDocument());
			}

			// PASO 6.2.1: "READ" -> Leemos todos los documentos de la base de datos
			System.out.println("\nPASO 6.2.1: Conciertos de la colección:");
			for (Document doc : collection2.find()) {
				System.out.println(doc.toJson());
			}

			// PASO 6.2.2: "READ" -> Hacemos una Query con condiciones y lo pasamos a un objeto Java
			System.out.println("\nPASO 6.2.2: Conciertos a las 21:00:");
			for (Document doc : collection2.find(Filters.regex("hora", "21:00 H."))) {
				System.out.println(doc.toJson());
			}

			// PASO 6.3: "UPDATE" -> Actualizamos los conciertos que empiecen a las 21:00 H.
			System.out.println("\nPASO 6.3: Conciertos después de la modificación y antes del borrado:");
			collection2.updateMany(Filters.eq("hora", "21:00 H."), Updates.set("hora", "23:00 H."));
			for (Document doc : collection2.find()) {
				System.out.println(doc.toJson());
			}

			// PASO 6.4: "DELETE" -> Borramos todos los Conciertos que sean el Viernes 04 octubre 2024
			System.out.println("\nPASO 6.4: Conciertos después del borrado:");
			collection2.deleteMany(Filters.eq("fecha", "Viernes 04 octubre 2024"));
			for (Document doc : collection2.find()) {
				System.out.println(doc.toJson());
			}

			// PASO FINAL: BORRAR TOD0
			System.out.println("PASO FINAL: Bases de datos borradas");
			database.drop();
			database2.drop();

		} catch (Exception e) {
			System.out.println("Exception al conectar al servidor de Mongo: " + e.getMessage());
		}
	}
}
