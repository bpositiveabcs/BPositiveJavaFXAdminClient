package bpos.adminclient.ViewController.admin;

import bpos.adminclient.RestCommunication.services.ClientService;
import bpos.common.model.Center;
import bpos.common.model.Event;

import bpos.common.model.LogInfo;
import bpos.common.model.Person;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.catalina.Server;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public class MainController {
    @FXML
    private ListView<Event> listEvents;

    @FXML
    private ListView<Center> listCenters;

    @FXML
    private ListView<LogInfo> listUsers;
    @FXML
    private TableView<Event> eventsTable;

    @FXML
    private TableColumn<Event, String> nameColumn;

    @FXML
    private TableColumn<Event, String> beginningDateColumn;

    @FXML
    private TableColumn<Event, String> requirementsColumn;

    @FXML
    private TableColumn<Event, String> descriptionColumn;


    ObservableList<Event> eventList = FXCollections.observableArrayList();

    ObservableList<Center> centerList = FXCollections.observableArrayList();
    ObservableList<LogInfo> userList = FXCollections.observableArrayList();
    private ClientService clientService;


    private Optional<Person> loggedUser;
    public void initialize(){
        System.out.println("Main Controller initialized");

        // Initialize TableView columns
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        beginningDateColumn.setCellValueFactory(new PropertyValueFactory<>("eventStartDate"));
        requirementsColumn.setCellValueFactory(new PropertyValueFactory<>("eventRequirements"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("eventDescription"));
        eventsTable.setItems(eventList);


    }

    public void setServer(ClientService server)  {
        this.clientService = server;
        refreshListEvents();
        refreshListCenters();
        refreshListUsers();

    }

    public void setLoggedUser(Optional<Person> user) {
        this.loggedUser = user;

    }



    public void refreshListEvents()  {
        this.eventList.clear();

        Iterable<Event> excursii = clientService.allEvents();
        for (Event event : excursii) {
            this.eventList.add(event);
        }
        eventsTable.setItems(eventList);
    }

    public void refreshListCenters() {
        this.centerList.clear();
//        Iterable<Center> centers = server.findAllCenters();
//        for (Center center : centers) {
//            this.centerList.add(center);
//        }
        //listCenters.setItems(centerList);
    }

    public void refreshListUsers() {
//        this.userList.clear();
//        List<LogInfo> users = (List<LogInfo>) server.findAllLogInfos();
//        for (LogInfo user : users) {
//            this.userList.add(user);
//        }
        //listUsers.setItems(userList);
    }

    @FXML
    private void handleUploadDocument() {
        LogInfo selectedUser = listUsers.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            String username = selectedUser.getUsername(); // Assuming LogInfo has a getUsername() method

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Document to Upload");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("All Files", "*.*"),
                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
            );

            Stage stage = (Stage) listEvents.getScene().getWindow(); // Or another component in your scene
            File selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                try {
                    // Create the main directory if it doesn't exist
                    File mainDir = new File("user_medicalinfo");
                    if (!mainDir.exists()) {
                        mainDir.mkdir();
                    }

                    // Create the user-specific directory if it doesn't exist
                    File userDir = new File(mainDir, username);
                    if (!userDir.exists()) {
                        userDir.mkdir();
                    }

                    // Copy the file to the user-specific directory
                    Files.copy(selectedFile.toPath(), Paths.get(userDir.getPath(), selectedFile.getName()));
                    System.out.println("File uploaded successfully!");
                } catch (IOException e) {
                    System.out.println("Failed to upload file: " + e.getMessage());
                }
            }
        } else {
            System.out.println("No user selected.");
        }
    }

    public void handleApproveButton(ActionEvent actionEvent) {
        System.out.println("Approve button clicked!");

        // Example logic: Print the selected event
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedEvent);
        Event newEvent= new Event();
        selectedEvent.setEventAnnouncementDate(LocalDateTime.now());
        newEvent=clientService.updateEvent(selectedEvent);
        if (selectedEvent != null) {
            System.out.println("Selected Event: " + newEvent.getEventDescription());
        } else {
            System.out.println("No event selected.");
        }
        refreshListEvents();
    }

    public void handleDenyUpdate(ActionEvent actionEvent) {
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedEvent);
        Event newEvent= new Event();
        selectedEvent.setEventAnnouncementDate(LocalDateTime.now());
        newEvent=clientService.deleteEvent(selectedEvent);
    }

//    @FXML
//    private void handleUploadDocument() {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Select Document to Upload");
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("All Files", "*.*"),
//                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
//                new FileChooser.ExtensionFilter("Text Files", "*.txt")
//        );
//
//        Stage stage = (Stage) listEvents.getScene().getWindow(); // Or another component in your scene
//        File selectedFile = fileChooser.showOpenDialog(stage);
//
//        if (selectedFile != null) {
//            try {
//                File destDir = new File("user_medicalinfo");
//                if (!destDir.exists()) {
//                    destDir.mkdir();
//                }
//                Files.copy(selectedFile.toPath(), Paths.get(destDir.getPath(), selectedFile.getName()));
//                System.out.println("File uploaded successfully!");
//            } catch (IOException e) {
//                System.out.println("Failed to upload file: " + e.getMessage());
//            }
//        }
}







