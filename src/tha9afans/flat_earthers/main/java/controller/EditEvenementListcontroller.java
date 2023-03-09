package controller;

import entities.Evenement;
import entities.Session;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import services.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.util.List;
import java.util.ResourceBundle;

public class EditEvenementListcontroller implements Initializable {
    @FXML
    private VBox listev;
    @FXML
    private ScrollPane spane;

    private List<Evenement> liste;
    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();

    ServiceEvenement se =new ServiceEvenement();
    ServicePersonne sp =new ServicePersonne();




    private void load() throws IOException {
        listev.getChildren().clear();

            for (int i = 0; i < liste.size(); i++) {
                Evenement ev =liste.get(i);
                VBox b1 =new VBox();
                VBox b2 =new VBox();
                VBox b3 =new VBox();
                HBox b4 =new HBox();
                b1.setPadding(new Insets(10));
                b2.setPadding(new Insets(10));
                b3.setPadding(new Insets(10));
                b4.setPadding(new Insets(10));
                Label label=new Label(liste.get(i).getNom());
                Button edit =new Button("Edit");
                Button delete =new Button("delete");
                b4.setAlignment(Pos.CENTER);
                b1.getChildren().add((Label)label);
                b2.getChildren().add(edit);
                b3.getChildren().add(delete);
                b4.getChildren().addAll(b1,b2,b3);
                listev.getChildren().add(b4);
                int finalI = i;
                edit.setOnAction(e -> {
                    Scene scene;
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/edit.fxml"));
                    Parent root = null;
                    try {
                        root = loader.load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }

                    editcontroller nextController = loader.getController();
                    nextController.setevent(ev);
                    Stage stage = new Stage();
                    scene=new Scene(root,1020,700);
                    stage.setScene(scene);
                    stage.show();

                });
                delete.setOnAction(e -> {

                    se.supprimer(se.getId(ev));
                    try {
                        liste=se.getAllByUser(sp.getOneById(userlogged.getIdUser()),se.getAll());
                        load();
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                });
            }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        spane.setStyle("-fx-background:  #f1faee; -fx-background-color:  #f1faee");
        liste=se.getAllByUser(sp.getOneById(userlogged.getIdUser()),se.getAll());
        try {
            load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
