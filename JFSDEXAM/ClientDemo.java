package com.klef.jfsd.exam;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import java.util.List;

public class ClientDemo {

    private static SessionFactory factory;

    public static void main(String[] args) {
        // Create SessionFactory
        factory = new Configuration()
                .configure("hibernate.cfg.xml") // Make sure you have this configuration file
                .addAnnotatedClass(Client.class)
                .buildSessionFactory();

        // Insert records
        insertClient(new Client("John Doe", "Male", 30, "New York", "john.doe@example.com", "1234567890"));
        insertClient(new Client("Jane Smith", "Female", 25, "Los Angeles", "jane.smith@example.com", "0987654321"));

        // Print all records
        printAllClients();

        // Close the factory
        factory.close();
    }

    private static void insertClient(Client client) {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            session.save(client);
            transaction.commit();
            System.out.println("Client inserted: " + client);
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }

    private static void printAllClients() {
        Session session = factory.openSession();
        Transaction transaction = null;

        try {
            transaction = session.beginTransaction();
            Query<Client> query = session.createQuery("FROM Client", Client.class);
            List<Client> clients = query.getResultList();

            System.out.println("All Clients:");
            for (Client client : clients) {
                System.out.println(client);
            }

            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        } finally {
            session.close();
        }
    }
}