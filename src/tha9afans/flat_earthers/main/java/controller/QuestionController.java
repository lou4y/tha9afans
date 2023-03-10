package controller;

import entities.Personne;
import entities.Question;
import entities.Quiz;
import entities.Score;
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



    private Quiz quiz;
    @FXML
    private Button firstPossibleAnswer;

    @FXML
    private ImageView image;


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

    private boolean isGameOver = false;

    private final Random rand = new Random();

    private int quiz_id;

//    public void setQuestionsList(int idd) {
//
//        id = idd;
//        System.out.println("Quiz ID: " + id);
//        ServiceQuizQuestion sq = new ServiceQuizQuestion();
//        this.questions = sq.getAllByQuiz(id);
//        setQuestion(this.questions);
//    }

    public void setQuestion(List<Question> questions, int quiz_id) {
        this.questions = questions;
        this.quiz_id = quiz_id;
        System.out.println("Quiz ID: " + quiz_id);
        System.out.println("Size:"+this.questions.size());

        int questionNumber = rand.nextInt(this.questions.size());
        Question q = this.questions.remove(questionNumber);

        // Stop and reset the timeline
        if (timeline != null) {
            timeline.stop();
            timeline.getKeyFrames().clear();
        }

        // Create a List with the answer options and shuffle them
        List<String> answerOptions = Arrays.asList(
                q.getFirst_possible_answer(),
                q.getSecond_possible_answer(),
                q.getThird_possible_answer());
        Collections.shuffle(answerOptions, rand);

        // Set the text of the answer buttons according to the shuffled order
        this.firstPossibleAnswer.setText(answerOptions.get(0));
        this.secondPossibleAnswer.setText(answerOptions.get(1));
        this.thirdPossibleAnswer.setText(answerOptions.get(2));

        this.question.setText(q.getQuestion());
        this.timer.setText(String.valueOf(q.getTimer()));
        startTimer();
        rightAnswer = q.getRight_answer();

        try (InputStream inputStream = q.getQuestion_image()) {
            Image image = new Image(inputStream);
            this.image.setImage(image);
            this.image.setPreserveRatio(false);
            Rectangle clip = new Rectangle(this.image.getFitWidth(), this.image.getFitHeight());
            clip.setArcWidth(20);
            clip.setArcHeight(20);
            this.image.setClip(clip);
        } catch (IOException e) {
            e.printStackTrace();
        }

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
            if (timeline != null && timeline.getStatus() == Timeline.Status.RUNNING) {
                // The timer is already running, so we don't need to start it again
                return;
            }
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
        qc.setQuestion(this.questions, this.quiz_id);
        qc.setScore(this.scoreCount);



        // Get the current scene and set its root to the new content
        Scene scene = firstPossibleAnswer.getScene();
        scene.setRoot(root);
    }
    @FXML
    void checkAnswer(ActionEvent event) throws IOException {

        ServicePersonne sp = new ServicePersonne();
        ServiceScore ss = new ServiceScore();
        ServicesQuiz sq = new ServicesQuiz();

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
        Personne user = sp.getOneById(userlogged.getIdUser());

        System.out.println("me before if "+ this.quiz_id);
        if (this.questions.size() == 0) {
            System.out.println("me after if "+this.quiz_id);


            // Game is over, show score alert and go back to PlayQuizHome.fxml
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Quiz Over");
            alert.setHeaderText("Congratulations!");
            alert.setContentText("Your score is: " + this.scoreCount);
            alert.showAndWait();

            // Check if the user has played this quiz before
            Score existingScore = ss.getByUserQuiz(user, sq.getById(this.quiz_id));

            if (existingScore != null) {
                // Update the existing score if the current score is higher
                if (this.scoreCount > existingScore.getScore()) {
                    existingScore.setScore(this.scoreCount);
                    existingScore.setTimes_played(existingScore.getTimes_played() + 1);
                    ss.update(existingScore);
                }
            } else {
                Score newScore = new Score(user, sq.getById(this.quiz_id), this.scoreCount, 1);
                ss.add(newScore);

            }

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/PlayQuizHome.fxml"));
            Parent root = loader.load();
            Scene scene = clickedButton.getScene();
            scene.setRoot(root);
        } else {
            // There are more questions, load the next one
            nextQuestionScene();
        }
    }

    public void setScore(int scoreCount) {
        this.scoreCount = scoreCount;
        this.score.setText("Score: " + this.scoreCount);
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
