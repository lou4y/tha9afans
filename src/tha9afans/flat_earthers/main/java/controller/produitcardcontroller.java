package controller;
import entities.Produit;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
public class produitcardcontroller {
    @FXML
    private Label event_desc;

    @FXML
    private ImageView event_img;

    @FXML
    private Label event_name;

    @FXML
    private Button findmore;
    @FXML
    private Button buttondetail;

    private Produit p;
    private Scene scene;

    /*@FXML
    void moredetails(ActionEvent event) throws IOException {

        InputStream stream = new FileInputStream("D:/resources/tha9afans.png");
        Image image = new Image(stream);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/evenement.fxml"));
        Parent root = loader.load();
        evenementcontroller nextController = loader.getController();
        nextController.eventpage(e);
        Stage stage = (Stage) findmore.getScene().getWindow();
        scene=new Scene(root,1400,700);
        stage.setScene(scene);
        stage.show();

    }*/


    public void setevenement(Produit p) throws FileNotFoundException {
        InputStream stream = new FileInputStream("src/tha9afans/flat_earthers/main/gui/test/images/book.png");
        Image image = new Image(stream);
        this.p=p;
        event_name.setText(p.getNom());
        event_desc.setText(p.getDescription());
        event_img.setImage(image);

    }
    public void detailProduit(ActionEvent event) throws IOException {
        Scene scene;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/detailproduit.fxml"));
        Parent root = loader.load();

        DetailproduitController nextController = loader.getController();
        nextController.productpage(p);
        Stage stage = new Stage();
        scene=new Scene(root,1020,700);
        stage.setScene(scene);
        stage.show();
    }
}

