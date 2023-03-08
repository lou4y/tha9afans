package controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.DataSource;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ResourceBundle;

public class ResetPasswordController implements Initializable {
    PreparedStatement ps;
    Connection cnx = DataSource.getInstance().getCnx();
    private String email;
    @FXML
    Button backloginbutton,resetpasswordbutton;
    @FXML
    TextField passwordfield,confirmpasswordfield;

    public void setUserEmail(String email) {
        this.email=email;
        resetpasswordbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (passwordfield.getText().compareTo("") == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez entrez un mot de passe", ButtonType.OK);
                    alert.showAndWait();

                } else if (passwordfield.getText().compareTo(confirmpasswordfield.getText()) != 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "les 2 mots de passe sont incompatible", ButtonType.OK);
                    alert.showAndWait();
                    passwordfield.clear();
                    confirmpasswordfield.clear();
                } else if((passwordfield.getText().length()<8) || !(passwordfield.getText().matches(".*[A-Z].*")) || !(passwordfield.getText().matches(".*[a-z].*"))){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez entrez un mot de passe contenant des lettres majuscules ,miniscules " +
                            "et de longeur supérieur à 8", ButtonType.OK);
                    alert.showAndWait();

                }else {

                    try{

                        String password = passwordfield.getText();
                        String sql = "update personnes set PASSWORD  = '"+password +"' where EMAIL ='"+email+"' ";
                        ps=cnx.prepareStatement(sql);
                        ps.execute(sql);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre mot de passe est réintialisé", ButtonType.OK);
                        alert.showAndWait();
                    }catch (Exception e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vous n'avez pas un compte avec cette adresse mail", ButtonType.OK);
                        alert.showAndWait();

                    }

                }

            }
        });


    }
    public void gotologininterface() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/test/login-view.fxml"));
        Stage window=(Stage) backloginbutton.getScene().getWindow();
        window.setScene(new Scene(root,600,420));
        window.setTitle("Login");

    }
        @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
