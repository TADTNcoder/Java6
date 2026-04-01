package lab5.ui;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.io.IOException;
import java.net.HttpURLConnection;

public class StudentService {

    private final String HOST = "http://localhost:8080";
    private final ObjectMapper mapper = new ObjectMapper();

    public List<Student> findAll() throws IOException {
        String url = HOST + "/students";
        HttpURLConnection conn = HttpClient.openConnection("GET", url);
        byte[] data = HttpClient.readData(conn);
        return List.of(mapper.readValue(data, Student[].class));
    }

    public Student findById(String id) throws IOException {
        String url = HOST + "/students/" + id;
        HttpURLConnection conn = HttpClient.openConnection("GET", url);
        return mapper.readValue(HttpClient.readData(conn), Student.class);
    }

    public void create(Student s) throws IOException {
        String url = HOST + "/students";
        byte[] data = mapper.writeValueAsBytes(s);
        HttpURLConnection conn = HttpClient.openConnection("POST", url);
        HttpClient.writeData(conn, data);
    }

    public void update(String id, Student s) throws IOException {
        String url = HOST + "/students/" + id;
        byte[] data = mapper.writeValueAsBytes(s);
        HttpURLConnection conn = HttpClient.openConnection("PUT", url);
        HttpClient.writeData(conn, data);
    }

    public void delete(String id) throws IOException {
        String url = HOST + "/students/" + id;
        HttpURLConnection conn = HttpClient.openConnection("DELETE", url);
        HttpClient.readData(conn);
    }
}