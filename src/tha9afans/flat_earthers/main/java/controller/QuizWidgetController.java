package controller;

import entities.Quiz;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;

public class QuizWidgetController {
    @FXML
    private Label numberOfQuestions;

    @FXML
    private Button playNow;

    @FXML
    private Label quizDescription;

    @FXML
    private Label quizName;

    @FXML
    private Label timesPlayed;

    @FXML
    private ImageView quizCover;

    @FXML
    private VBox text;

    private Quiz quiz;
    

    public void setQuizWidget(Quiz quiz) {
        this.quiz = quiz;
        quizName.setText(quiz.getQuiz_name());
        quizDescription.setText(quiz.getQuiz_description());
        numberOfQuestions.setText(String.valueOf(quiz.getNumber_of_questions()) + " Questions");
        VBox.setMargin(text, new Insets(20, 0, 20, 20));
        try (InputStream stream = quiz.getQuiz_cover()) {
            if (stream != null) {
                Image image = new Image(stream);
//                quizCover.setFitWidth(300);
//                quizCover.setFitHeight(200);
                quizCover.setImage(image);
                quizCover.setPreserveRatio(false);

                // Create a rectangle clip with rounded corners
                Rectangle clip = new Rectangle(quizCover.getFitWidth(), quizCover.getFitHeight());
                clip.setArcWidth(20);
                clip.setArcHeight(20);
                quizCover.setClip(clip);

                // Set margin on the right
                VBox.setMargin(quizCover, new Insets(10, 18, 0, 18));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void playQuizNow(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/Question.fxml"));
        Parent root = loader.load();
        QuestionController qc = loader.getController();
        qc.setQuestionsList(this.quiz.getQuiz_id());
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();

    }



}
