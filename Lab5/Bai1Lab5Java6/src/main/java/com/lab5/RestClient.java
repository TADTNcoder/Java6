package com.lab5;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class RestClient {
    static String host = "http://localhost:8080";
    static ObjectMapper mapper = new ObjectMapper();

    private static void getAll() {
        String url = host + "/students";
        try {
            var connection = HttpClient.openConnection("GET", url);
            var response = HttpClient.readData(connection);
            System.out.println("GET ALL:");
            System.out.println(new String(response));
            System.out.println("--------------------------------");
        } catch (IOException ex) {
            System.out.println("GET ALL ERROR: " + ex.getMessage());
        }
    }

    private static void getById() {
        String id = "SV001";
        String url = host + "/students/" + id;
        try {
            var connection = HttpClient.openConnection("GET", url);
            var response = HttpClient.readData(connection);
            System.out.println("GET BY ID:");
            System.out.println(new String(response));
            System.out.println("--------------------------------");
        } catch (IOException ex) {
            System.out.println("GET BY ID ERROR: " + ex.getMessage());
        }
    }

    private static void post() {
        String url = host + "/students";
        try {
            Student student = new Student("SV999", "Nguyen Van Test", true, 7.5);
            byte[] data = mapper.writeValueAsBytes(student);

            var connection = HttpClient.openConnection("POST", url);
            var response = HttpClient.writeData(connection, data);

            System.out.println("POST:");
            System.out.println(new String(response));
            System.out.println("--------------------------------");
        } catch (IOException ex) {
            System.out.println("POST ERROR: " + ex.getMessage());
        }
    }

    private static void put() {
        String id = "SV999";
        String url = host + "/students/" + id;
        try {
            Student student = new Student("SV999", "Nguyen Van Test Updated", false, 9.0);
            byte[] data = mapper.writeValueAsBytes(student);

            var connection = HttpClient.openConnection("PUT", url);
            var response = HttpClient.writeData(connection, data);

            System.out.println("PUT:");
            System.out.println(new String(response));
            System.out.println("--------------------------------");
        } catch (IOException ex) {
            System.out.println("PUT ERROR: " + ex.getMessage());
        }
    }

    private static void delete() {
        String id = "SV999";
        String url = host + "/students/" + id;
        try {
            var connection = HttpClient.openConnection("DELETE", url);
            var response = HttpClient.readData(connection);

            System.out.println("DELETE:");
            String body = new String(response);
            System.out.println(body.isBlank() ? "Delete success!" : body);
            System.out.println("--------------------------------");
        } catch (IOException ex) {
            System.out.println("DELETE ERROR: " + ex.getMessage());
        }
    }

    public static void main(String[] args) {
        getAll();
        getById();
        post();
        getAll();
        put();
        getByIdCustom("SV999");
        delete();
        getAll();
    }

    private static void getByIdCustom(String id) {
        String url = host + "/students/" + id;
        try {
            var connection = HttpClient.openConnection("GET", url);
            var response = HttpClient.readData(connection);
            System.out.println("GET BY ID (" + id + "):");
            System.out.println(new String(response));
            System.out.println("--------------------------------");
        } catch (IOException ex) {
            System.out.println("GET BY ID ERROR: " + ex.getMessage());
        }
    }
}