package controller;

import entities.Personne;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import services.AuthResponseDTO;
import services.ServicePersonne;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.ResourceBundle;
//excel biblio
import java.io.FileOutputStream;
import java.util.function.Predicate;

import org.apache.poi.ss.usermodel.*;
import services.UserSession;


public class AdministrateurController implements Initializable {
    ServicePersonne sp=new ServicePersonne();

    @FXML
    private Button buttonmodifieruser,logoutbutton;
    @FXML
    private TextField searchField;
    @FXML
    private ListView<Personne> listview = new ListView<>();
    @FXML
    ComboBox<String> sortCombo = new ComboBox<>();
    AuthResponseDTO userlogged=UserSession.getUser_LoggedIn();
    @FXML
    public void supprimerLigne(ActionEvent event){
        sp.supprimer(listview.getSelectionModel().getSelectedItem().getId());
        listview.getItems().removeAll(listview.getSelectionModel().getSelectedItem());
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre utilisateur est supprimé", ButtonType.OK);
        alert.showAndWait();



    }
    public void modifierUser(ActionEvent event) throws IOException {
        Personne personne = listview.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/modifieruser-view.fxml"));
        Parent root = loader.load();

        ModifierUserController controller = loader.getController();
        controller.setUserId(personne.getId()); // Set the correct userId value
        loader.setController(controller);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        Stage currentStage = (Stage) buttonmodifieruser.getScene().getWindow();
        currentStage.close();
        stage.show();
    }
    @FXML
    public void downloadexcel(ActionEvent event) throws IOException {
        ObservableList<Personne> personnes = FXCollections.observableArrayList(sp.getAll());
        listview.setItems(personnes);
        ObservableList<Personne> items = listview.getItems();

        // Create a new workbook utilisée pour créer, lire ou modifier des fichiers Excel
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("liste utilisateurs");


        // Create the header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Cin");
        headerRow.createCell(1).setCellValue("Nom");
        headerRow.createCell(2).setCellValue("Prénom");
        headerRow.createCell(3).setCellValue("Email");
        headerRow.createCell(4).setCellValue("Num téléphone");
        headerRow.createCell(5).setCellValue("Adresse");


        // Write the data rows
        int rowNum = 1;
        for (Personne p : items) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(p.getCin());
            row.createCell(1).setCellValue(p.getNom());
            row.createCell(2).setCellValue(p.getPrenom());
            row.createCell(3).setCellValue(p.getEmail());
            row.createCell(4).setCellValue(p.getTelephone());
            row.createCell(5).setCellValue(p.getAdresse());
        }
        LocalDate currentDate = LocalDate.now();
        FileOutputStream outputStream = new FileOutputStream("D:/Projects/javafx/tha9afansOns/src/tha9afans/flat_earthers/main/java/excel/Listedesutilisateurs"+ currentDate.toString() +".xlsx");
        workbook.write(outputStream);
        workbook.close();
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre fichier excel est téléchargé", ButtonType.OK);
        alert.showAndWait();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ServicePersonne sp = new ServicePersonne();
        ObservableList<Personne> personnes = FXCollections.observableArrayList(sp.getAll());


// Step 2: Create a FilteredList with a predicate that matches Personne objects by name
        // Create ComboBox for sorting criteria

        sortCombo.getItems().addAll("Nom", "Date de naissance");
        sortCombo.setValue("Nom"); // set default value

// Create Comparator for sorting by birth date
        Comparator<Personne> dobComparator = Comparator.comparing(Personne::getDateNaissance);

// Create Comparator for sorting by name
        Comparator<Personne> nameComparator = Comparator.comparing(Personne::getNom);

// Create FilteredList for searching
        FilteredList<Personne> filteredPersonnes = new FilteredList<>(personnes);

// Create SortedList for sorting
        SortedList<Personne> sortedPersonnes = new SortedList<>(filteredPersonnes);

// Add listener to ComboBox to update sorting criteria
        sortCombo.setOnAction(e -> {
            if ("Date de naissance".equals(sortCombo.getValue())) {
                sortedPersonnes.setComparator(dobComparator);
            } else {
                sortedPersonnes.setComparator(nameComparator);
            }
        });

// Add listener to search field to update search criteria
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            String searchText = newValue.trim().toLowerCase();
            Predicate<Personne> predicate = personne -> {
                if (searchText.isEmpty()) {
                    return true;
                }
                String lowerCaseNom = personne.getNom().toLowerCase();
                String lowerCasePrenom = personne.getPrenom().toLowerCase();
                return (lowerCaseNom.contains(searchText) || lowerCasePrenom.contains(searchText));
            };
            filteredPersonnes.setPredicate(predicate);
        });

// Set ListView items and cell factory
        listview.setItems(sortedPersonnes);
        listview.setCellFactory(new Callback<ListView<Personne>, ListCell<Personne>>() {
            @Override
            public ListCell<Personne> call(ListView<Personne> listView) {
                return new ListCell<Personne>() {
                    @Override
                    protected void updateItem(Personne personne, boolean empty) {
                        super.updateItem(personne, empty);
                        if (personne != null) {
                            setText(personne.getCin()+"     "+personne.getNom() + "      " + personne.getPrenom()  +"       "+personne.getEmail()+"         "+
                                    personne.getDateNaissance()+"         "+personne.getTelephone()+"            "+personne.getAdresse()+ "          "+
                                    personne.getRole() );
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });




    }


}
