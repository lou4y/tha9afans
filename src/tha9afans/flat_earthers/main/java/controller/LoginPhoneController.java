package controller;

import javafx.event.ActionEvent;
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
import services.AuthResponseDTO;
import services.SmsService;
import services.UserSession;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class LoginPhoneController implements Initializable {
    @FXML
    TextField codesmsfield;
    @FXML
    Button backloginbutton;
    String codeSms;
    @FXML
    Button verifcodesmsbutton;
    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();
    public void envoyersms() {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        codeSms = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
        String phonenumber=userlogged.getTelephone();
        System.out.println(phonenumber);
        SmsService.sendSMS(phonenumber, "votre code est:"+codeSms);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Un code est envoyé à votre téléphone", ButtonType.OK);
        alert.showAndWait();

    }
    public void verifiercodeSms() throws IOException {
        if((codesmsfield.getText()).equals(codeSms))
        {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre code est correcte", ButtonType.OK);
            alert.showAndWait();
            if (userlogged.getRole().equals("utilisateur")){
                Parent root = FXMLLoader.load(getClass().getResource("/test/sidenavbaruser.fxml"));

                Stage window=(Stage) verifcodesmsbutton.getScene().getWindow();
                window.setScene(new Scene(root,1400,700));
            }
            else if(userlogged.getRole().equals("administrateur")){
                Parent root = FXMLLoader.load(getClass().getResource("/test/sidenavbar.fxml"));

                Stage window=(Stage) verifcodesmsbutton.getScene().getWindow();
                window.setScene(new Scene(root,1400,700));

            }
        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez vérifier votre code Sms", ButtonType.OK);
            alert.showAndWait();
        }


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void backlogin(ActionEvent event) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource("/test/login-view.fxml"));
        Stage window=(Stage) backloginbutton.getScene().getWindow();
        window.setScene(new Scene(root,1400,700));
    }
}
