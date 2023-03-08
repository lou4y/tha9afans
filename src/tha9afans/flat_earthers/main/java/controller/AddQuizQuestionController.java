package controller;

import entities.Question;
import entities.Quiz;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import services.ServiceQuizQuestion;
import services.ServicesQuestion;
import services.ServicesQuiz;

public class AddQuizQuestionController implements Initializable {




        @FXML
        private TextField description;

        @FXML
        private TextField firstPossibleAnswer;

        @FXML
        private TextField image;

        @FXML
        private TextField image1;

        @FXML
        private TextField numberOfQuestions;

        @FXML
        private TextField question;

        @FXML
        private ListView<Question> questionsList;

        @FXML
        private TextField quizName;

        @FXML
        private TextField rightAnswer;

        @FXML
        private TextField secondPossibleAnswer;

        @FXML
        private Pane submit;

        @FXML
        private Pane submit1;

        @FXML
        private Button submitButton;

        @FXML
        private TextField thirdPossibleAnswer;

        @FXML
        private ChoiceBox<Integer> timer = new ChoiceBox<>();

        private File selectedImage;

        private Quiz quiz;
        @FXML
        private GridPane gridId;

        private List<Question> selectedItems = new ArrayList<>();




    @FXML
    private  void addQuiz(ActionEvent event) throws IOException {
        ServicesQuiz sq = new ServicesQuiz();
        if (quizName.getText().isEmpty() || numberOfQuestions.getText().isEmpty() || description.getText().isEmpty() || selectedImage == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Field should not be empty!", ButtonType.OK);
            alert.showAndWait();


        } else if (Integer.parseInt(numberOfQuestions.getText()) != selectedItems.size()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Make sure you add the right number of questions", ButtonType.OK);
            alert.showAndWait();
        } else {

            this.quiz = new Quiz(quizName.getText(),description.getText(), Integer.parseInt(numberOfQuestions.getText()), null);
            try {
                FileInputStream fis = new FileInputStream(selectedImage);
                this.quiz.setQuiz_cover(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            sq.add(this.quiz);
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Quiz added", ButtonType.OK);
            alert.showAndWait();
        }
        int id = sq.getId(this.quiz);
        addQuizQuestions(id);
    }

   private void addQuizQuestions(int id){
       ServiceQuizQuestion sq = new ServiceQuizQuestion();
        for (Question q : selectedItems) {
            sq.add(id, q.getQuestion_id());
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

    @FXML
    public void ButtonActionQuestion (ActionEvent actionEvent){
        FileChooser fileChooser = new FileChooser();
        selectedImage = fileChooser.showOpenDialog(null);
        if (selectedImage != null) {
            image1.setText(selectedImage.getAbsolutePath());
        } else {
            System.out.println("File is not valid!");
        }
    }

    @FXML
    private void AddQuestion(ActionEvent event) throws IOException {
        if (question.getText().isEmpty() || firstPossibleAnswer.getText().isEmpty() || secondPossibleAnswer.getText().isEmpty() || thirdPossibleAnswer.getText().isEmpty() || rightAnswer.getText().isEmpty() || timer.getValue() == null || selectedImage == null){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Field should not be empty!", ButtonType.OK);
            alert.showAndWait();
        } else {
            ServicesQuestion sq = new ServicesQuestion();

            Question q = new Question( question.getText(), firstPossibleAnswer.getText(), secondPossibleAnswer.getText(), thirdPossibleAnswer.getText(), rightAnswer.getText(), null, timer.getValue());

            try {
                FileInputStream fis = new FileInputStream(selectedImage);
                q.setQuestion_image(fis);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            sq.add(q);

            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Question added", ButtonType.OK);
            alert.showAndWait();
            question.clear();
            firstPossibleAnswer.clear();
            secondPossibleAnswer.clear();
            thirdPossibleAnswer.clear();
            rightAnswer.clear();
            image1.clear();
            timer.setValue(null);

        }
        listQuestion();
    }


    public void listQuestion(){
        ServicesQuestion sq = new ServicesQuestion();
        List<Question> questions = sq.getAll();

        System.out.println("these are the selected items "+this.selectedItems);

        int i = 0;
        for (Question q : questions){
            HBox hbox = new HBox();
            hbox.setStyle("-fx-padding: 7;");
            Label label = new Label(q.getQuestion());
            CheckBox checkBox = new CheckBox();
            if(isQuestionSelected(q)){
                checkBox.setSelected(true);
                System.out.println(isQuestionSelected(q));
            }
            hbox.getChildren().addAll(checkBox, label);
            gridId.add(hbox, 0, i++);

            checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue) {
                    this.selectedItems.add(q);
                } else {
                    this.selectedItems.remove(q);
                }
            });
        }
    }

    public boolean isQuestionSelected(Question q) {
        return this.selectedItems.contains(q);
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        listQuestion();
        ObservableList<Integer> timerList = FXCollections.observableArrayList(10, 20, 30, 40, 50, 60);
        timer.setItems(timerList);

    }


}
