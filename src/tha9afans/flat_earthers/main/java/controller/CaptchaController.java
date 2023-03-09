package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class CaptchaController implements Initializable {

    @FXML
    private Label num1;
    @FXML
    private Label num2;
    @FXML
    private TextField answerField;
    @FXML
    Button verifyButton;

    private int expectedAnswer;

    private int attempts = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        generateCaptcha();
    }

    public boolean verify() {

        try {
            int userAnswer = Integer.parseInt(answerField.getText().trim());
            if (userAnswer == expectedAnswer) {
                System.out.println("CAPTCHA verified!");
                Parent playQuizHome = FXMLLoader.load(getClass().getResource("/test/PlayQuizHome.fxml"));
                Stage window = (Stage) answerField.getScene().getWindow();
                window.setScene(new Scene(playQuizHome, 1400, 700));
                window.setTitle("");
            } else {

                attempts++; // increment the attempts counter
                if (attempts == 3) { // if 3 attempts have been made
                    System.out.println("You have failed to verify the CAPTCHA 3 times. Going back to Quiz Home.");
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("CAPTCHA Verification Failed");
                    alert.setHeaderText(null);
                    alert.setContentText("You have failed to verify the CAPTCHA 3 times.");
                    alert.showAndWait();
                    Parent quizHome = FXMLLoader.load(getClass().getResource("/test/QuizHome.fxml"));
                    Stage window = (Stage) answerField.getScene().getWindow();
                    window.setScene(new Scene(quizHome, 1400, 700));
                    window.setTitle("");
                    attempts = 0; // reset the attempts counter
                } else {
                    System.out.println("Incorrect CAPTCHA answer. Please try again. Attempt " + attempts + " of 3.");
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Incorrect CAPTCHA Answer");
                    alert.setHeaderText(null);
                    alert.setContentText("Incorrect CAPTCHA answer. Please try again. Attempt " + attempts + " of 3.");
                    alert.showAndWait();
                    generateCaptcha();
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid integer.");
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Incorrect Numeric Input");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid integer.");
            alert.showAndWait();
            generateCaptcha();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }




    private void generateCaptcha() {
        Random random = new Random();
        int n1 = random.nextInt(10);
        int n2 = random.nextInt(10);
        expectedAnswer = n1 + n2;
        num1.setText(String.valueOf(n1));
        num2.setText(String.valueOf(n2));
        answerField.setText("");
    }
}
