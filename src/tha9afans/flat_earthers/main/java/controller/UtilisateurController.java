package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import services.AuthResponseDTO;
import services.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UtilisateurController implements Initializable {
    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();
    @FXML
    Button retourbutton;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //System.out.println(userlogged.getNom()+" "+userlogged.getPrenom());

    }


    public void gotest(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/sidenavbar.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) retourbutton.getScene().getWindow();
        window.setTitle("dashboard");
        window.setScene(new Scene(root, 900, 450));

        // Reload the profile image
        SidenavbarController controller = loader.getController();
        //controller.loadImage();
    }

}
