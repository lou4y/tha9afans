package controller;

import entities.Question;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.ServicesQuestion;

import javafx.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpdateDeleteQuestionController implements Initializable {

    @FXML
    Button updateQuestionButton;

    ServicesQuestion sq = new ServicesQuestion();
    @FXML
    private ListView<Question> questionsList = new ListView<>();

    public void deleteQuestion(){
        sq.delete(questionsList.getSelectionModel().getSelectedItem().getQuestion_id());
        questionsList.getItems().removeAll(questionsList.getSelectionModel().getSelectedItem());
    }

    @FXML
    public void updateQuestion(ActionEvent event) throws IOException{
        Question question = questionsList.getSelectionModel().getSelectedItem();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/UpdateQuestion.fxml"));
        Parent root = loader.load();

        UpdateQuestionController uqc = loader.getController();
        uqc.setQuestion_id(question.getQuestion_id());
        loader.setController(uqc);
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources){
        ServicesQuestion sq = new ServicesQuestion();


        ObservableList<Question> questions = FXCollections.observableArrayList(sq.getAll());
        questionsList.setItems(questions);
        questionsList.setCellFactory(new Callback<ListView<Question>, ListCell<Question>>() {
            @Override
            public ListCell<Question> call(ListView<Question> listView) {
                return new ListCell<Question>() {
                    @Override
                    protected void updateItem(Question question, boolean empty) {
                        super.updateItem(question, empty);
                        if (question != null) {

                            setText(question.getQuestion_id()+"        "+question.getQuestion() + "        " + question.getRight_answer()  +"       "+question.getTimer()+"        "+
                                    question.getFirst_possible_answer()+"        "+question.getSecond_possible_answer()+"        "+question.getThird_possible_answer()+ "        "+question.getQuestion_image());


                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }


}
