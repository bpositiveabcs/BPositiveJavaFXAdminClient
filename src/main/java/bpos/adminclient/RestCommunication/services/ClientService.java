package bpos.adminclient.RestCommunication.services;

import bpos.common.model.Person;
import bpos.other.PersonResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class ClientService {
    private static final String BASE_URL = "http://localhost:55555/personActorService";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ClientService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }
    public void logout() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + "/logout"))
                .GET()
                .build();

    }
    public Person login(String username, String password) {
        String params = URLEncoder.encode("username", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(username, StandardCharsets.UTF_8) +
                "&" +
                URLEncoder.encode("password", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(password, StandardCharsets.UTF_8);

// Construiește URI-ul cu parametrii
        String uriWithParams = BASE_URL + "/login?" + params;

// Construiește cererea HTTP folosind URI-ul cu parametrii
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriWithParams))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);

        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            System.out.println(responseBody);

            // Deserializare răspuns JSON în obiectul PersonResponse
            PersonResponse personResponse = objectMapper.readValue(responseBody, PersonResponse.class);
            System.out.println(personResponse);
            // Acum poți accesa obiectul Person și lista de evenimente
            Person person = personResponse.getPerson();
            return person;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
