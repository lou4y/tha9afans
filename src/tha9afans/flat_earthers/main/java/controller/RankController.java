package controller;

import entities.Personne;
import entities.Question;
import entities.Score;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import services.ServiceScore;
import services.ServicesQuestion;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class RankController implements Initializable {
    ServiceScore serviceScore = new ServiceScore();

    @FXML
    ListView rankList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Score> top3Scores = serviceScore.getTop3UsersByScore();
        ObservableList<Score> scores = FXCollections.observableArrayList(top3Scores);
        rankList.setItems(scores);
        rankList.setCellFactory(new Callback<ListView<Score>, ListCell<Score>>() {
            @Override
            public ListCell<Score> call(ListView<Score> rankList) {
                return new ListCell<Score>() {
                    @Override
                    protected void updateItem(Score score, boolean empty) {
                        super.updateItem(score, empty);
                        if (score != null) {

                            setText(score.getPersonne().getNom() + "               " + score.getPersonne().getPrenom() + "              " + score.getScore());


                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });


    }
}
