package controller;
import entities.Categorie;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import org.controlsfx.control.Rating;
import services.ServiceCategorie;
import services.ServiceProduit;
import utils.DataSource;

import java.io.*;
import java.net.URL;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
public class ProduitAddController implements Initializable {
    ServiceCategorie sc= new ServiceCategorie();
    ServiceProduit sp =new ServiceProduit();
    public AnchorPane bp;
    @FXML
    private Label Menu;
    @FXML
    private TextField txtAddProductName;
    @FXML
    private GridPane product_grid;
    @FXML
    private TextField txtAddProductLibelle,produitremise;
    @FXML
    private TextField produitdescription;
    private List<Produit> liste = new ArrayList<>();
    @FXML
    private TextField txtAddProductPrice;
    @FXML
    private Rating produitrating;
    @FXML
    private Label MenuClose;
    @FXML
    private AnchorPane slider;
    @FXML
    private ComboBox<Categorie> cb_categorie= new ComboBox<>();;
    Connection cnx = DataSource.getInstance().getCnx();
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ServiceCategorie sc=new ServiceCategorie();
        List <Categorie> list = sc.getAll();
        liste=sp.getAllByUser(2);
        try {
            load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        for (Categorie c : list) {
            cb_categorie.getItems().add(c);
        }



    }
    private void load() throws IOException {
        product_grid.getChildren().clear();
        liste=sp.getAllByUser(2);
        System.out.println(liste);
        int col = 0;
        int row = 1;
        try {
            for (int i = 0; i < liste.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/test/produitcard.fxml"));
                VBox box = loader.load();
                produitcardcontroller controller = loader.getController();
                controller.setevenement(liste.get(i));
                if (col == 4) {
                    col = 0;
                    row++;
                }

                product_grid.add(box, col++, row);
                GridPane.setMargin(box, new Insets(10));//(child,column,row

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void btnAddProductOnAction(ActionEvent actionEvent) throws SQLException, FileNotFoundException {
        if (txtAddProductName.getText().equals("")|| produitdescription.getText().equals("") || txtAddProductLibelle.getText().equals("") ||
                txtAddProductPrice.getText().equals("") || cb_categorie.getValue().toString().equals("")||produitremise.getText().equals("")) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Complete produit details ", ButtonType.OK);
            a.showAndWait();}

        else{
            String nom=txtAddProductName.getText();
            String description=produitdescription.getText();
            int libelle = Integer.parseInt(txtAddProductLibelle.getText());
            Double prix = Double.parseDouble(txtAddProductPrice.getText());
            Categorie categorie=  cb_categorie.getValue();
            Double remise=Double.parseDouble(produitremise.getText());
            Double rate=produitrating.getRating();
            ServiceProduit sp= new ServiceProduit();
            Produit p = new Produit(nom,description,libelle,2,prix,null,categorie,remise,rate);
            sp.ajouter(p);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "produit added !", ButtonType.OK);
            alert.showAndWait();

        }

    }

}