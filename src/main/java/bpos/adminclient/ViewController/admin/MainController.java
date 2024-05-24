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

import java.util.List;
import java.util.Optional;

public class MainController {
    @FXML
    private ListView<Event> listEvents;

    @FXML
    private ListView<Center> listCenters;

    @FXML
    private ListView<LogInfo> listUsers;

    ObservableList<Event> eventList = FXCollections.observableArrayList();

    ObservableList<Center> centerList = FXCollections.observableArrayList();
    ObservableList<LogInfo> userList = FXCollections.observableArrayList();

    private ClientService server;
    private Person loggedUser;

    public void setServer(ClientService mserver)  {
        this.server = server;
//        refreshListEvents();
//        refreshListCenters();
//        refreshListUsers();
    }

    public void setLoggedUser(Person user) {
        this.loggedUser = user;

    }

    public void handleUploadDocument(ActionEvent actionEvent) {
    }


//    public void refreshListEvents()  {
//        this.eventList.clear();
//        Iterable<Event> excursii = server.findAllEvents();
//        for (Event excursie : excursii) {
//            this.eventList.add(excursie);
//        }
//        listEvents.setItems(eventList);
//    }
//
//    public void refreshListCenters()  {
//        this.centerList.clear();
//        Iterable<Center> centers = server.findAllCenters();
//        for (Center center : centers) {
//            this.centerList.add(center);
//        }
//        listCenters.setItems(centerList);
//    }
//
//    public void refreshListUsers()  {
//        this.userList.clear();
//        List<LogInfo> users = (List<LogInfo>) server.findAllLogInfos();
//        for (LogInfo user : users) {
//            this.userList.add(user);
//        }
//        listUsers.setItems(userList);
//    }
//
//    @FXML
//    private void handleUploadDocument() {
//        LogInfo selectedUser = listUsers.getSelectionModel().getSelectedItem();
//        if (selectedUser != null) {
//            String username = selectedUser.getUsername(); // Assuming LogInfo has a getUsername() method
//
//            FileChooser fileChooser = new FileChooser();
//            fileChooser.setTitle("Select Document to Upload");
//            fileChooser.getExtensionFilters().addAll(
//                    new FileChooser.ExtensionFilter("All Files", "*.*"),
//                    new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
//                    new FileChooser.ExtensionFilter("Text Files", "*.txt")
//            );
//
//            Stage stage = (Stage) listEvents.getScene().getWindow(); // Or another component in your scene
//            File selectedFile = fileChooser.showOpenDialog(stage);
//
//            if (selectedFile != null) {
//                try {
//                    // Create the main directory if it doesn't exist
//                    File mainDir = new File("user_medicalinfo");
//                    if (!mainDir.exists()) {
//                        mainDir.mkdir();
//                    }
//
//                    // Create the user-specific directory if it doesn't exist
//                    File userDir = new File(mainDir, username);
//                    if (!userDir.exists()) {
//                        userDir.mkdir();
//                    }
//
//                    // Copy the file to the user-specific directory
//                    Files.copy(selectedFile.toPath(), Paths.get(userDir.getPath(), selectedFile.getName()));
//                    System.out.println("File uploaded successfully!");
//                } catch (IOException e) {
//                    System.out.println("Failed to upload file: " + e.getMessage());
//                }
//            }
//        } else {
//            System.out.println("No user selected.");
//        }
//    }

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







