package controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class QuizHomeController implements Initializable {

    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Button addQuestion;

    @FXML
    private Button playQuizHome;


    public void switchToAddQuestion() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/test/AddQuestion.fxml"));
        Stage window=(Stage) addQuestion.getScene().getWindow();
        window.setScene(new Scene(root,1400, 700));
        window.setTitle("");

    }

    public void switchToDeleteQuestion() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/test/UpdateDeleteQuestion.fxml"));
        Stage window=(Stage) addQuestion.getScene().getWindow();
        window.setScene(new Scene(root,1400, 700));
        window.setTitle("");

    }

    public void switchToDeleteQuiz() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/test/DeleteQuiz.fxml"));
        Stage window=(Stage) addQuestion.getScene().getWindow();
        window.setScene(new Scene(root,1400, 700));
        window.setTitle("");

    }

    public void switchToAddQuiz() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/test/QuizQuestion.fxml"));
        Stage window=(Stage) addQuestion.getScene().getWindow();
        window.setScene(new Scene(root,1400, 700));
        window.setTitle("");

    }

    public void switchToPlayQuiz() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/captcha.fxml"));
        Parent root = loader.load();
        CaptchaController captchaController = loader.getController();
        captchaController.verifyButton.setOnAction(event -> {
            captchaController.verify();
        });
        Stage window = (Stage) playQuizHome.getScene().getWindow();
        window.setScene(new Scene(root, 1400, 700));
        window.setTitle("");
    }






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
