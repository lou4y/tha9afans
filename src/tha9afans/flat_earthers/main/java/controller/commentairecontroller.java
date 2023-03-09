package controller;

import entities.Commentaire;
import entities.Evenement;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.ServiceCommentaire;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class commentairecontroller {
    @FXML
    private Label comment;

    @FXML
    private Label comment_owner;

    @FXML
    private Button edit_button;
    @FXML
    private Label comment_date;
    @FXML
    private Button delete_button;

    @FXML
    private ImageView profile_photo;
    private Commentaire commentaire;
    private Evenement ev;
    @FXML
    private VBox container;

    @FXML
    void delete_comment(ActionEvent event) throws IOException {
        ServiceCommentaire sc = new  ServiceCommentaire();
        sc.supprimer(this.commentaire.getId());
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/evenement.fxml"));
        Parent root = loader.load();
        evenementcontroller nextController = loader.getController();
        nextController.eventpage(this.ev);
        Stage stage = (Stage) delete_button.getScene().getWindow();
        Scene scene=new Scene(root,1020,700);
        stage.setScene(scene);
        stage.show();

    }



    @FXML
    void edit_comment(ActionEvent event) throws IOException {
        container.getChildren().clear();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/editcommentaire.fxml"));
        VBox root = loader.load();
        editcommentairecontroller nextController = loader.getController();
        nextController.editcomment(this.commentaire);
        container.getChildren().add(root);

    }

    public void setComment(Evenement evenement,Commentaire commentaire) throws FileNotFoundException {
        this.ev= evenement;
        this.commentaire = commentaire;
        this.profile_photo.setImage(new Image(commentaire.getuser().getPhoto()));
        comment_owner.setText(String.valueOf(commentaire.getuser().getNom()));
        comment.setText(commentaire.getCommentaire());
        comment_date.setText(String.valueOf(commentaire.getDate()));
    }


}
