package controller;

import com.sun.javafx.charts.Legend;
import entities.PanierProduit;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import services.ServicePanierProduit;

import java.io.IOException;

public class BoxpanierController  {
    @FXML
    private Button btndelete;

    @FXML
    private Button minus;

    @FXML
    private HBox panier;

    @FXML
    private Button plus;

    @FXML
    private Label prixprodu;

    @FXML
    private Label produitname;

    @FXML
    private Label qproduit;

    private PanierProduit panierProduit;
    ServicePanierProduit servicePanierProduit = new ServicePanierProduit();
    private Legend.LegendItem total;


    public void setProduit( PanierProduit panierProduit) {
        this.panierProduit = panierProduit;
        produitname.setText(panierProduit.getProduit().getNom());
        prixprodu.setText(String.valueOf(panierProduit.getProduit().getPrix()));
        qproduit.setText(String.valueOf(panierProduit.getQuantite()));

    }


    public void delete(javafx.event.ActionEvent actionEvent) throws IOException {
        servicePanierProduit.supprimer(this.panierProduit.getProduit().getId());
        load();
    }

    public void max(javafx.event.ActionEvent actionEvent) throws IOException {
        servicePanierProduit.ajouterquantite(this.panierProduit.getProduit().getId());

        load();
    }

    public void min(javafx.event.ActionEvent actionEvent) throws IOException {
        servicePanierProduit.minQuantite(this.panierProduit.getProduit().getId());
        load();

    }



    public void load()throws IOException {
      /*  FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("panier-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1105, 660);
        Stage stage = (Stage)minus.getScene().getWindow();

        stage.setTitle("Panier");
        stage.setScene(scene);

        stage.show();*/

    }




}
