package bpos.adminclient.RestCommunication.services;

import bpos.common.model.Event;
import bpos.common.model.Person;
import bpos.other.PersonResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

public class ClientService {
    private static final String BASE_URL = "http://localhost:55555/personActorService";
    private static final String BASE_URL2 = "http://localhost:55555";
    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public ClientService() {
        this.httpClient = HttpClient.newHttpClient();
        this.objectMapper = new ObjectMapper();
    }

    public void logout(String username) {
        // Crează un obiect HttpClient
        HttpClient httpClient = HttpClient.newHttpClient();
        String params = URLEncoder.encode("username", StandardCharsets.UTF_8) + "=" ;
        System.out.println(username);

// Construiește URI-ul cu parametrii
        String uriWithParams = BASE_URL + "/logoutAdmin?" + params+username;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(uriWithParams))
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();
        System.out.println(request);
        try {
            // Trimite cererea și primește un răspuns
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            // Verifică codul de stare al răspunsului
            if (response.statusCode() == 200) {
                System.out.println("Logout successful!");
            } else {
                System.out.println("Logout failed with status code: " + response.statusCode());
            }
        } catch (InterruptedException  e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    public List<Event> allEvents() {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL2 + "/events"))
                .GET()
                .build();
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        try {
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            String responseBody = response.body();
            System.out.println(responseBody);

            // Deserialize the JSON array into a list of Event objects
            List<Event> events = objectMapper.readValue(responseBody, new TypeReference<List<Event>>() {
            });

            return events;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Event updateEvent(Event event) {
        System.out.println(event);
        String eventJson = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            eventJson = objectMapper.writeValueAsString(event);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL2 + "/events"))
                .PUT(HttpRequest.BodyPublishers.ofString(eventJson))
                .header("Content-Type", "application/json")
                .build();

        System.out.println(request);

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            System.out.println(responseBody);

            if (response.statusCode() == 200) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    Event eventnou= objectMapper.readValue(responseBody, Event.class);
                    return eventnou;
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.err.println("Failed to update event: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }

    public Event deleteEvent(Event selectedEvent) {
        System.out.println(selectedEvent);
        String eventJson = null;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
            eventJson = objectMapper.writeValueAsString(selectedEvent);
        } catch (JsonProcessingException ex) {
            throw new RuntimeException(ex);
        }

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL2 + "/events"))
                .method("DELETE", HttpRequest.BodyPublishers.ofString(eventJson))
                .header("Content-Type", "application/json")
                .build();

        System.out.println(request);

        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();
            System.out.println(responseBody);

            if (response.statusCode() == 200) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    objectMapper.registerModule(new JavaTimeModule());
                    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
                    Event eventnou= objectMapper.readValue(responseBody, Event.class);
                    return eventnou;
                } catch (JsonProcessingException ex) {
                    throw new RuntimeException(ex);
                }
            } else {
                System.err.println("Failed to delete event: " + response.body());
                return null;
            }
        } catch (IOException | InterruptedException ex) {
            throw new RuntimeException(ex);
        }
    }
}


