package controller;

import entities.Question;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import services.ServicesQuestion;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;



public class UpdateQuestionController implements Initializable {


    @FXML
    private Button fileChooser;

    @FXML
    private TextField firstPossibleAnswer;

    @FXML
    private TextField image;

    @FXML
    private TextField question;

    @FXML
    private TextField rightAnswer;

    @FXML
    private TextField secondPossibleAnswer;


    @FXML
    private TextField thirdPossibleAnswer;

    @FXML
    private ChoiceBox<Integer> timer;

    private int question_id;

    @FXML
     Button updateQuestion;

    ServicesQuestion sq = new ServicesQuestion();
    public void setQuestion_id(int question_id) {
        this.question_id = question_id;
        Question q = sq.getById(question_id);
        question.setText(q.getQuestion());
        firstPossibleAnswer.setText(q.getFirst_possible_answer());
        secondPossibleAnswer.setText(q.getSecond_possible_answer());
        thirdPossibleAnswer.setText(q.getThird_possible_answer());
        rightAnswer.setText(q.getRight_answer());
//        image.setText(q.getImage());
        timer.setValue(q.getTimer());

        updateQuestion.setOnAction(new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event){
                    Question ques = new Question(question_id ,question.getText(), firstPossibleAnswer.getText(), secondPossibleAnswer.getText(), thirdPossibleAnswer.getText(), rightAnswer.getText(), null, timer.getValue());
                    sq.update(ques);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Question updated successfully", ButtonType.OK);
                    alert.showAndWait();
            }
        });

    }

    @FXML
    public void ButtonAction(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            image.setText(selectedFile.getAbsolutePath());
        } else {
            System.out.println("File is not valid!");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer.getItems().addAll(10, 25, 30);
    }

}

