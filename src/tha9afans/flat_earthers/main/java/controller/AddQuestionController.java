package controller;

import entities.Question;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import services.ServicesQuestion;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class AddQuestionController implements Initializable {

    @FXML
    private TextField question;
    @FXML
    private TextField firstPossibleAnswer;
    @FXML
    private TextField secondPossibleAnswer;
    @FXML
    private TextField thirdPossibleAnswer;
    @FXML
    private TextField rightAnswer;
    @FXML
    private ChoiceBox<Integer> timer = new ChoiceBox<>();
    @FXML
    private TextField image;
    private File selectedImage;

    @FXML
    private Button submitButton;




    @FXML
    private void AddQuestion(ActionEvent event) throws IOException {
        if (question.getText().isEmpty() || firstPossibleAnswer.getText().isEmpty() || secondPossibleAnswer.getText().isEmpty() || thirdPossibleAnswer.getText().isEmpty() || rightAnswer.getText().isEmpty() || timer.getValue() == null || selectedImage == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Field should not be empty!", ButtonType.OK);
            alert.showAndWait();
        } else {
            ServicesQuestion sq = new ServicesQuestion();

            Question q = new Question(question.getText(), firstPossibleAnswer.getText(), secondPossibleAnswer.getText(), thirdPossibleAnswer.getText(), rightAnswer.getText(), null, timer.getValue());

            try {
                FileInputStream fis = new FileInputStream(selectedImage);
                q.setQuestion_image(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            // Check if the question already exists in the database
            boolean questionExists = sq.questionExists(q);
            if (questionExists) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Question already exists in database!", ButtonType.OK);
                alert.showAndWait();
                return;
            }

            // If the question doesn't exist, add it to the database
            sq.add(q);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Question added", ButtonType.OK);
            alert.showAndWait();
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
    public void initialize (URL url, ResourceBundle resourceBundle){
        timer.getItems().addAll(10, 25, 30);
    }

}
