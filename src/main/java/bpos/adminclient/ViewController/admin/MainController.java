package bpos.adminclient.ViewController.admin;

import bpos.adminclient.RestCommunication.services.ClientService;
import bpos.common.model.Center;
import bpos.common.model.Event;

import bpos.common.model.LogInfo;
import bpos.common.model.Person;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
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
    @FXML
    private Button refreshButton;


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

    }

    public void setLoggedUser(Optional<Person> user) {
        this.loggedUser = user;

    }



    public void refreshListEvents()  {
        this.eventList.clear();

        Iterable<Event> events = clientService.allEvents();
        events.forEach(event -> {
            if (event.getEventStartDate().equals(LocalDateTime.of(0, 1, 1, 0, 0))) {
                this.eventList.add(event);
            }
        });
        eventsTable.setItems(eventList);
    }






    public void handleApproveButton(ActionEvent actionEvent) {
        System.out.println("Approve button clicked!");

        // Example logic: Print the selected event
        Event selectedEvent = eventsTable.getSelectionModel().getSelectedItem();
        System.out.println(selectedEvent);
        Event newEvent= new Event();
        selectedEvent.setEventStartDate(LocalDateTime.now());
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
        refreshListEvents();
    }

    public void handleLogoutButton(ActionEvent actionEvent) {
        System.out.println("Logout button clicked!");
        clientService.logout(loggedUser.get().getPersonLogInfo().getUsername());
        // Example logic: Close the current window
        //Stage stage = (Stage) listEvents.getScene().getWindow();
        //stage.close();
        // Oprește procesul curent
        Platform.exit();
    }

    public void handleRefreshButton(ActionEvent actionEvent) {
        refreshListEvents();
    }


}







