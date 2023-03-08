package controller;


import entities.Quiz;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import services.ServicesQuiz;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PlayQuizHome implements Initializable {

    @FXML
    private GridPane quizList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Quiz> quizzes = getQuizzesFromDatabase();
        int row = 0;
        int column = 0;

        for (Quiz quiz : quizzes) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/QuizWidget.fxml"));
                AnchorPane quizWidget = loader.load();
                QuizWidgetController controller = loader.getController();
                controller.setQuizWidget(quiz);
                quizList.add(quizWidget, column, row);

                // Update the row and column indices
                column++;
                if (column > 2) {
                    column = 0;
                    row++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private List<Quiz> getQuizzesFromDatabase() {

        List<Quiz> quizzes = null;
        try {
            ServicesQuiz sq = new ServicesQuiz();
            quizzes = sq.getAll();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return quizzes;
    }

}
