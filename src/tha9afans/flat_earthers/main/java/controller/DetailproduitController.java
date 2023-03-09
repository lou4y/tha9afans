package controller;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import entities.Categorie;
import entities.Personne;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import org.controlsfx.control.Rating;
import services.*;


import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DetailproduitController implements Initializable {
    private Produit p;
    @FXML
    private Text viewProductResponse;
    @FXML
    private TextField pm_name;
    @FXML
    private TextField pm_price;
    @FXML
    private TextField produitfieldprix, nomproduitfield, produitfieldlibelle, produitfieldremise;
    @FXML
    Button btnEditProductOnAction,btnaddpanier;
    @FXML
    private ComboBox<Categorie> pm_categorie;
    @FXML
    private TextArea produitfielddescription;
    @FXML
    TextField textnom, textcategorie, textlibelle, textdescription, textprix, textremise, textprixapresremise;
    @FXML
    private Rating  produitrating;
    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();

    @FXML
    private StackPane stackPane; // I DID ADD STACK PANE HERE !

    public void productpage(Produit p) throws IOException {
        p.setRating(produitrating.getRating());
        textnom.setText(p.getNom());
        textcategorie.setText(p.getCategorie().getNom());
        textlibelle.setText("" + p.getLibelle());
        textdescription.setText(p.getDescription());
        textprix.setText(p.getPrix() + "");
        textprix.setText(p.getPrix() + " ");
        nomproduitfield.setText(p.getNom());
        produitfieldlibelle.setText("" + p.getLibelle());
        produitfielddescription.setText(p.getDescription());
        produitfieldprix.setText(p.getPrix() + "");
        produitfieldremise.setText(p.getRemise() + "");
        ServiceCategorie sc = new ServiceCategorie();
        List<Categorie> list = sc.getAll();
        pm_categorie.setValue(p.getCategorie());
        ServiceProduit sp = new ServiceProduit();
        btnEditProductOnAction.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                //Double rate=produitrating.getRating();
                Double rate=produitrating.getRating();

                InputStream streamprofile = p.getImage();
                Produit produitadd = new Produit(p.getId(),
                        nomproduitfield.getText(),
                        produitfielddescription.getText(),
                        Integer.parseInt(produitfieldlibelle.getText()),
                        p.getId_vendeur(),
                        Double.parseDouble(produitfieldprix.getText()),
                        streamprofile,
                        p.getCategorie(),
                        Double.parseDouble(produitfieldremise.getText()),
                        rate);
                System.out.println(produitrating.getRating());
                sp.modifier(produitadd);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre produit est modifié avec succés", ButtonType.OK);
                alert.showAndWait();
            }

        });
        btnaddpanier.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ServicePanierProduit spp=new ServicePanierProduit();
                ServicePersonne sp=new ServicePersonne();
                Personne personne= sp.getOneById(userlogged.getIdUser());
                spp.ajouterProduitPanier(p,personne);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre produit est ajouté au panier", ButtonType.OK);
                alert.showAndWait();


            }

        });


    }

    public void btnEditProductOnAction(ActionEvent actionEvent) {
        ServiceCategorie sc = new ServiceCategorie();
        List<Categorie> list = sc.getAll();


        for (Categorie c : list) {
            pm_categorie.getItems().add(c);
        }
        pm_categorie.setConverter(new StringConverter<Categorie>() {
            @Override
            public String toString(Categorie ca) {
                return ca.getNom();
            }

            @Override
            public Categorie fromString(String name) {
                return null;
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)   {    // Load product data and display it in the UI

}



}