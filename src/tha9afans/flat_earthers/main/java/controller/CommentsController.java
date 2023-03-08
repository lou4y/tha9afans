package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import services.CommentFilter;

import java.net.URL;
import java.util.ResourceBundle;

public class CommentsController  implements Initializable {

    @FXML
    private TextField comments;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        CommentFilter.applyFilter(comments);
    }
}
