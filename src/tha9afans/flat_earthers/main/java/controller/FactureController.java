package controller;

import entities.Facture;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.Callback;
import services.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.Collectors;


public class FactureController implements Initializable {
@FXML
        private TextField textref;
    SendGrid mail = new SendGrid();



    @FXML
    private ListView<Facture> listview = new ListView<>();

    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceFacture sf = new ServiceFacture();
        ServicePersonne sp = new ServicePersonne();

        ObservableList<Facture> factures = FXCollections.observableArrayList(sf.getAllByUser(sp.getOneById(userlogged.getIdUser())));
        listview.setItems(factures);
        listview.setCellFactory(new Callback<ListView<Facture>, ListCell<Facture>>() {
            @Override
            public ListCell<Facture> call(ListView<Facture> listView) {
                return new ListCell<Facture>() {
                    @Override
                    protected void updateItem(Facture facture, boolean empty) {
                        super.updateItem(facture, empty);
                        if (facture != null) {
                            setText("Référence: " + facture.getRefrancefacture()
                                    + "\nNom: " + facture.getCommande().getDateCommande()
                                    + "\nPrénom: " + facture.getCommande().getPersonne().getNom()
                                    + "\nEmail: " + facture.getCommande().getPersonne().getEmail()
                                    + "\nAdresse: " + facture.getCommande().getPersonne().getAdresse()
                                    + "\nTéléphone: " + facture.getCommande().getPersonne().getTelephone()
                                    + "\nDate: " + facture.getDatefacture()
                                    + "\nProduit: " + facture.getCommande().getProduit().getNom()
                                    + "\nPrix: " + facture.getCommande().getProduit().getPrix()
                                    + "\ntotal : " + facture.getCommande().getTotal()
                                    + "\ntotal avec tva: " + facture.getCommande().getTotal()*facture.getTva()

                                    + "\n\n");
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }



    public void telpdf(javafx.event.ActionEvent actionEvent) {
        // Get the selected item from the ListView
        Facture selectedFacture = listview.getSelectionModel().getSelectedItem();
        if (selectedFacture == null) {
            // If no item is selected, display an error message and return
            JOptionPane.showMessageDialog(null, "Please select a facture to download.");
            return;
        }

        // Generate PDF
        GeneratePdf pdfGenerator = new GeneratePdf();
        String pdfFilename ="src/tha9afans/flat_earthers/main/java/files/" + selectedFacture.getRefrancefacture() + ".pdf";
        try {
            pdfGenerator.generate(selectedFacture, pdfFilename);
        } catch (IOException e) {
            // Display an error message
            JOptionPane.showMessageDialog(null, "An error occurred while generating the facture: " + e.getMessage());
            return;
        }

        // Display a success message
        JOptionPane.showMessageDialog(null, "Facture downloaded successfully.");
    }
    public void mailpdf(javafx.event.ActionEvent actionEvent) {
        // Get the selected item from the ListView
        Facture selectedFacture = listview.getSelectionModel().getSelectedItem();
        if (selectedFacture == null) {
            // If no item is selected, display an error message and return
            JOptionPane.showMessageDialog(null, "Please select a facture to send.");
            return;
        }
        // Generate PDF
        GeneratePdf pdfGenerator = new GeneratePdf();
        String pdfFilename ="src/tha9afans/flat_earthers/main/java/files/" + selectedFacture.getRefrancefacture() + ".pdf";
        try {
            pdfGenerator.generate(selectedFacture, pdfFilename);
        } catch (IOException e) {
            // Display an error message
            JOptionPane.showMessageDialog(null, "An error occurred while generating the facture: " + e.getMessage());
            return;
        }
        // Send email with PDF attached
        String toEmail = selectedFacture.getCommande().getPersonne().getEmail();
        String body = "Please find attached the facture for reference " + selectedFacture.getRefrancefacture();
        SendGrid sendGrid = new SendGrid();
        try {
            sendGrid.sendWithAttachment(toEmail, "Facture", body, pdfFilename);
        } catch (IOException e) {
            // Display an error message
            JOptionPane.showMessageDialog(null, "An error occurred while sending the facture via email: " + e.getMessage());
            return;
        }
        // Display a success message
        JOptionPane.showMessageDialog(null, "Facture sent via email successfully.");
    }





            public void recherche(KeyEvent keyEvent) {
        // Get the search text from the search field
        String searchText = textref.getText().trim();

        // Create a new ServiceFacture object to retrieve all factures
        ServiceFacture sp = new ServiceFacture();
        ObservableList<Facture> allFactures = FXCollections.observableArrayList(sp.getAll());

        // Use API stream to filter factures by search text
        ObservableList<Facture> filteredFactures = allFactures.stream()
                .filter(facture -> facture.getRefrancefacture().toLowerCase().contains(searchText.toLowerCase()))
                .collect(Collectors.toCollection(FXCollections::observableArrayList));
        // Update the ListView with the filtered factures
        listview.setItems(filteredFactures);
    }


}


