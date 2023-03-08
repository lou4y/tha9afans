package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import services.MailSender;
import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class ForgotPasswordController implements Initializable {
    @FXML
     private TextField emailfield;
    @FXML
     private TextField codefield;
    @FXML
    private Button verifcodebutton;
    @FXML
     private Button backloginbutton;
    MailSender sender = new MailSender();
    String codeMail;

    @FXML
    public void envoyercode(ActionEvent event) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        int targetStringLength = 6;
        Random random = new Random();
        codeMail = random.ints(leftLimit, rightLimit + 1)
                .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
                .limit(targetStringLength)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();

        sender.SendMail(emailfield.getText().trim(),  "Your verification code is :'" +codeMail+  "' ", "Verification code" );
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre email est envoyé", ButtonType.OK);
        alert.showAndWait();
    }
    @FXML
    private void verifCode(ActionEvent event) throws IOException {
        /*System.out.println(codeMail);*/
        //sender.SendMail(emailfield.getText().trim() , "Your verification code is :'" +codeMail+  "' ", "Verification code" );

        if((codefield.getText()).equals(codeMail))
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/ResetPassword.fxml"));
            Parent root = loader.load();

            ResetPasswordController controller = loader.getController();
            controller.setUserEmail(emailfield.getText()); // Set the correct userId value
            loader.setController(controller);
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            Stage currentStage = (Stage) verifcodebutton.getScene().getWindow();
            currentStage.close();
            stage.show();


        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez vérifier votre code", ButtonType.OK);
            alert.showAndWait();
        }





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
