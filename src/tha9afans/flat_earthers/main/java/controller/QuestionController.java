package controller;

import entities.Question;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Duration;
import services.*;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;


public class QuestionController {


    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();

    @FXML
    private Button firstPossibleAnswer;

    @FXML
    private ImageView image;

    @FXML
    private Button pauseGame;

    @FXML
    private Label question;

    @FXML
    private Button quitGame;

    @FXML
    private Label score;

    @FXML
    private Button secondPossibleAnswer;

    @FXML
    private Button skipButton;

    @FXML
    private Button thirdPossibleAnswer;

    @FXML
    private Label timer;

    @FXML
    private Button nextButton;

    private List<Question> questions = new ArrayList<>();

    private String rightAnswer;

    private int id;
    private int scoreCount = 0;

    private Timeline timeline;

    private boolean isPaused = false;

    private Duration pausedTime;
    private boolean isGameOver = false;



    public void setQuestionsList(int id) {
        this.id = id;
        ServiceQuizQuestion sq = new ServiceQuizQuestion();
        this.questions = sq.getAllByQuiz(id);
        setQuestion(this.questions);

    }


    public void setQuestion(List<Question> questions){
        this.questions = questions;
        System.out.println(this.questions.size());
        Random rand = new Random();

        Question q = new Question();
        if (this.questions.size() == 0){
            q = this.questions.get(0);
            nextButton.setDisable(true);
            skipButton.setDisable(true);
        } else {
            int questionNumber = rand.nextInt(this.questions.size());
            q = this.questions.get(questionNumber);
            this.questions.remove(questionNumber);
        }

        // Stop and reset the timeline
        if (timeline != null) {
            timeline.stop();
            timeline.getKeyFrames().clear();
        }

        // Create an ArrayList to store the answer options and add them in the desired order
        List<String> answerOptions = new ArrayList<>();
        answerOptions.add(q.getFirst_possible_answer());
        answerOptions.add(q.getSecond_possible_answer());
        answerOptions.add(q.getThird_possible_answer());

        // Shuffle the ArrayList to randomize the order of the answer options
        Collections.shuffle(answerOptions, rand);

        // Set the text of the answer buttons according to the shuffled order
        this.firstPossibleAnswer.setText(answerOptions.get(0));
        this.secondPossibleAnswer.setText(answerOptions.get(1));
        this.thirdPossibleAnswer.setText(answerOptions.get(2));

        this.question.setText(q.getQuestion());
        this.timer.setText(String.valueOf(q.getTimer()));
        startTimer();
        rightAnswer = q.getRight_answer();
        InputStream inputStream = q.getQuestion_image();
        Image image = new Image(inputStream);
        this.image.setImage(image);
        this.image.setPreserveRatio(false);
        Rectangle clip = new Rectangle(this.image.getFitWidth(), this.image.getFitHeight());
        clip.setArcWidth(20);
        clip.setArcHeight(20);
        this.image.setClip(clip);

        int timerDuration = q.getTimer();
        timeline = new Timeline(
                new KeyFrame(Duration.seconds(timerDuration), e -> handleTimeUp())
        );
        timeline.setCycleCount(1);
        timeline.play();
    }



    public void handleTimeUp() {
        try {
            nextQuestionScene();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void startTimer() {
        if (!isPaused) {
            AtomicInteger timeLimit = new AtomicInteger(Integer.parseInt(timer.getText()));
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), evt -> {
                timeLimit.getAndDecrement();
                timer.setText(Integer.toString(timeLimit.get()));
                if (timeLimit.get() == 0) {
                    timeline.stop();
                    try {
                        nextQuestionScene();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }));
            timeline.setCycleCount(timeLimit.get());
            timeline.play();
        }
    }

    public void nextQuestionScene() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/Question.fxml"));
        Parent root = loader.load();
        QuestionController qc = loader.getController();
        qc.setQuestion(this.questions);
        qc.setScore(this.scoreCount);

        // Get the current scene and set its root to the new content
        Scene scene = firstPossibleAnswer.getScene();
        scene.setRoot(root);
    }

    public void setScore(int scoreCount) {
        this.scoreCount = scoreCount;
        this.score.setText("Score: " + this.scoreCount);
    }

    @FXML
    void checkAnswer(ActionEvent event) throws IOException {
        ServicePersonne sp = new ServicePersonne();


        Button clickedButton = (Button) event.getSource();
        String chosenAnswer = clickedButton.getText();

        if (!isGameOver && chosenAnswer.equals(rightAnswer)) {
            this.scoreCount += 100;
            this.score.setText("Score: " + this.scoreCount);

        }

        // Disable all answer buttons
        this.firstPossibleAnswer.setDisable(true);
        this.secondPossibleAnswer.setDisable(true);
        this.thirdPossibleAnswer.setDisable(true);

        // Check if there are any questions remaining
        if (this.questions.isEmpty()) {
            // Game is over, show score alert and go back to PlayQuizHome.fxml
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quiz Over");
            alert.setHeaderText("Congratulations!");
            alert.setContentText("Your score is: " + this.scoreCount);
            alert.showAndWait();


            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/PlayQuizHome.fxml"));
            Parent root = loader.load();
            Scene scene = clickedButton.getScene();
            scene.setRoot(root);
        } else {
            nextQuestionScene();
        }
    }

    @FXML
    void quitGame(ActionEvent event) throws IOException {
        isGameOver = true;
        if (timeline != null) {
            timeline.stop();
        }
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/PlayQuizHome.fxml"));
        Parent root = loader.load();
        Scene scene = quitGame.getScene();
        scene.setRoot(root);
    }






    @FXML
    void skipQuestion(ActionEvent event) throws IOException {
        nextQuestionScene();

    }

}
