package controller;

import entities.Evenement;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class evenementcardcontroller{

    @FXML
    private Label event_desc;

    @FXML
    private ImageView event_img;
    @FXML
    private BorderPane bp;
    @FXML
    private Label event_name;

    @FXML
    private Button findmore;

    private Evenement e;
    private Scene scene;

    @FXML
    void moredetails(MouseEvent event) throws IOException {

        InputStream stream = new FileInputStream("src/tha9afans/flat_earthers/main/gui/images/logo.png");
        Image image = new Image(stream);
    SidenavbaruserController sidenavbaruserController = new SidenavbaruserController();



    }


    public void setevenement(Evenement e) throws FileNotFoundException {
        InputStream stream = new FileInputStream("src/tha9afans/flat_earthers/main/gui/images/logo.png");
        Image image = new Image(stream);
        this.e=e;
        event_name.setText(e.getNom());
        event_desc.setText(e.getDescription());
        event_img.setImage(image);


}
}
