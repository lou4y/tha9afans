package controller;

import entities.Commentaire;
import entities.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.VBox;
import services.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class Listcommentairecontroller implements Initializable {
    ServicePersonne sp =new ServicePersonne();

    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();

    @FXML
    private ScrollPane comment_pane;

    @FXML
    private TextArea comment_content;

    @FXML
    private VBox comment_grid;

    private Evenement ev;

    @FXML
    private Button post_button;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        CommentFilter.applyFilter(comment_content);
        comment_pane.setStyle("-fx-background:   #001A23; -fx-background-color:   #001A23");
    }

    public void setCommentList(Evenement evenement) throws IOException {
        System.out.println("working");
       this.ev= evenement;
        comment_grid.getChildren().clear();
        ServiceCommentaire sc = new ServiceCommentaire();
        List<Commentaire> lc= sc.getAllCommentsByEvent(this.ev);
        for (Commentaire c : lc){
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/test/commentaire.fxml"));
            VBox box = loader.load();
            commentairecontroller controller = loader.getController();
            controller.setComment(ev,c);
            comment_grid.getChildren().add(box);
        }}
    @FXML
    void post_comment(ActionEvent event) throws IOException {

        ServiceCommentaire sc = new ServiceCommentaire();
        Commentaire c = new Commentaire(sp.getOneById(userlogged.getIdUser()),ev, comment_content.getText(), Date.valueOf(LocalDate.now()));
        sc.ajouter(c);
        setCommentList(this.ev);

    }


}
