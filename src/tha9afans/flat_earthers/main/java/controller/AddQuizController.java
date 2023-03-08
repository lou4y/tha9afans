package controller;

import entities.Question;
import entities.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import services.ServicesQuiz;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddQuizController implements Initializable {

    @FXML
    private TextField description;
    @FXML
    private TextField image;
    private File selectedImage;
    @FXML
    private TextField numberOfQuestions;
    @FXML
    private TextField quizName;
    @FXML
    private Button submitButton;


//    @FXML
//    private  void addQuiz(ActionEvent event) throws IOException {
//        if (quizName.getText().isEmpty() || numberOfQuestions.getText().isEmpty() || description.getText().isEmpty() || selectedImage == null){
//            Alert alert = new Alert(Alert.AlertType.ERROR, "Field should not be empty!", ButtonType.OK);
//            alert.showAndWait();
//        } else {
//            ServicesQuiz sq = new ServicesQuiz();
//            Quiz q = new Quiz(quizName.getText(),description.getText(), Integer.parseInt(numberOfQuestions.getText()), null);
//            try {
//                FileInputStream fis = new FileInputStream(selectedImage);
//                q.setQuiz_cover(fis);
//            } catch (FileNotFoundException e) {
//                e.printStackTrace();
//            }
//            sq.add(q);
//            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Question added", ButtonType.OK);
//            alert.showAndWait();
//        }}


    @FXML
    private void addQuiz(ActionEvent event) throws IOException {
        if (quizName.getText().isEmpty() || numberOfQuestions.getText().isEmpty() || description.getText().isEmpty() || selectedImage == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Field should not be empty!", ButtonType.OK);
            alert.showAndWait();
        } else {
            ServicesQuiz sq = new ServicesQuiz();
            Quiz q = new Quiz(quizName.getText(), description.getText(), Integer.parseInt(numberOfQuestions.getText()), null);
            try {
                FileInputStream fis = new FileInputStream(selectedImage);
                q.setQuiz_cover(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            if (sq.quizExists(q.getQuiz_name())) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Quiz with the same name already exists in the database!", ButtonType.OK);
                alert.showAndWait();
            } else {
                sq.add(q);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Quiz added to database.", ButtonType.OK);
                alert.showAndWait();
            }
        }
    }



    @FXML
    public void ButtonAction (ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        selectedImage = fileChooser.showOpenDialog(null);
        if (selectedImage != null) {
            image.setText(selectedImage.getAbsolutePath());
        } else {
            System.out.println("File is not valid!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}