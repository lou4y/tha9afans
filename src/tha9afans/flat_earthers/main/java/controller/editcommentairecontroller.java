package controller;

import entities.Commentaire;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;
import services.ServiceCommentaire;

import java.io.IOException;

public class editcommentairecontroller {
    @FXML
    private TextArea comment;

    @FXML
    private Label comment_owner;

    @FXML
    private Button confirm;

    private Commentaire commentaire;


    public void editcomment(Commentaire comment){
        this.commentaire =comment;
        this.comment.setText(comment.getCommentaire());
        this.comment_owner.setText(String.valueOf(comment.getId_user()));
    }
    @FXML
    void confirm(ActionEvent event) throws IOException {
        Commentaire c = new Commentaire(this.commentaire.getId(),this.commentaire.getId(),this.commentaire.getEvenement(),this.comment.getText(),this.commentaire.getDate());
        ServiceCommentaire sc = new ServiceCommentaire();
        sc.modifier(c);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/evenement.fxml"));
        Parent root = loader.load();
        evenementcontroller nextController = loader.getController();
        nextController.eventpage(this.commentaire.getEvenement());
        Stage stage = (Stage) confirm.getScene().getWindow();
        Scene scene=new Scene(root,1400,700);
        stage.setScene(scene);
        stage.show();
    }

}
