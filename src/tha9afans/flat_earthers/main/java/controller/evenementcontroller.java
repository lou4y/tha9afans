package controller;
import javafx.scene.shape.*;
import entities.Commentaire;
import entities.Evenement;
import entities.Session;
import entities.jaime;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import services.ServiceEvenement;
import services.ServiceJaime;
import services.ServiceSession;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class evenementcontroller implements Initializable {

    @FXML
    private Label date;

    @FXML
    private Label desc;

    @FXML
    private Label like;

    @FXML
    private Label location;

    @FXML
    private Label price;

    @FXML
    private Label title;
    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private ScrollPane SPane;

    @FXML
    private ImageView image_ev;


    private Evenement ev;
    @FXML
    private VBox sessionview;
    @FXML
    public VBox commentlist;
    @FXML
    private Button like_button;
    ServiceEvenement se = new ServiceEvenement();
    ServiceSession ss = new ServiceSession();
    ServiceJaime sj = new ServiceJaime();
    private jaime j = new jaime();

    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SPane.setStyle("-fx-background:  #f1faee; -fx-background-color:  #f1faee");


    }

    @FXML
    void kl(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/listevenement.fxml"));
        Parent root = loader.load();
        Stage stage = (Stage) b1.getScene().getWindow();
        Scene scene = new Scene(root,1400,700);
        stage.setScene(scene);
        stage.show();
    }

    public void eventpage(Evenement e) throws IOException {
        this.ev=e;
        this.j= new jaime(15,ev);
        InputStream stream = new FileInputStream("D:/resources/tha9afans1.png");
        Image image = new Image(stream);
        image_ev.setImage(image);
        title.setText(e.getNom());
        desc.setText(e.getDescription());
        date.setText(e.getDate().toString());
        location.setText(e.getLocalisation());
        price.setText(""+e.getPrix()+"");
        like.setText(""+e.getNb_aime()+"");
        setSession();
        setlike();
    setcomment();
    }
    void setSession(){

        List<Session> ls= ss.getAllByEvent(se.getOneById(se.getId(ev)));
        for (Session s : ls){
            HBox h = new HBox();
            VBox box1= new VBox();
            VBox box2 = new VBox();
            VBox box3= new VBox();
            VBox box4= new VBox();
            VBox box5 = new VBox();
            box1.setPadding(new Insets(0, 5, 0, 5));
            box2.setPadding(new Insets(0, 5, 0, 5));
            box3.setPadding(new Insets(0, 5, 0, 5));
            box4.setPadding(new Insets(0, 5, 0, 5));
            box5.setPadding(new Insets(0, 5, 0, 5));
            h.setSpacing(60);
            h.setPadding(new Insets(15, 15, 15, 15));
            h.setStyle("-fx-border-color: #f1faee;-fx-border-radius: 15");
            Label titre = new Label(s.getTitre());
            box1.getChildren().add(titre);
            titre.setStyle("-fx-min-width: 50; -fx-font-size: 15px; -fx-text-fill:  #f1faee;");
            Label description = new Label(s.getDescription());
            box2.getChildren().add(description);
            description.setStyle("-fx-min-width: 50; -fx-font-size: 15px; -fx-text-fill:  #f1faee;");
            Label host = new Label(s.getParlant());
            box3.getChildren().add(host);
            host.setStyle("-fx-min-width: 50; -fx-font-size: 15px; -fx-text-fill:  #f1faee;");
            Label debuit = new Label(s.getDebit().toString());
            box4.getChildren().add(debuit);
            debuit.setStyle("-fx-min-width: 50; -fx-font-size: 15px; -fx-text-fill:  #f1faee;");
            Label fin = new Label(s.getFin().toString());
            box5.getChildren().add(fin);
            fin.setStyle("-fx-min-width: 50; -fx-font-size: 15px; -fx-text-fill:  #f1faee;");
            h.getChildren().addAll(box1,box2,box3,box4,box5);
            sessionview.getChildren().add(h);

        }
    }
    public void setcomment() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/test/Listcommentaire.fxml"));
        VBox box = loader.load();
        Listcommentairecontroller controller = loader.getController();
        controller.setCommentList(this.ev);
        commentlist.getChildren().add(box);

    }

    @FXML
    void deleteevent(ActionEvent event) throws IOException {


       List<Session> ls= ss.getAllByEvent(se.getOneById(se.getId(ev)));
        for (int i = 0; i < ls.size(); i++) {
            ss.supprimer(ls.get(i).getId());
        }
        se.supprimer(se.getId(ev));
        kl(event);
        sj.supprimer(j);
    }


    @FXML
    void edit(ActionEvent event) throws IOException {

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/edit.fxml"));
        Parent root = loader.load();
        editcontroller controller = loader.getController();
        controller.setevent(ev);
        Stage stage = (Stage) b2.getScene().getWindow();
        Scene scene = new Scene(root,1400,700);
        stage.setScene(scene);
        stage.show();
    }
    @FXML
    void like(ActionEvent event) {
        if (sj.existe(this.j)){
            sj.supprimer(j);
        }
        else {
           sj.ajouter(j);
        }
        setlike();


    }
    private void setlike(){
        if (sj.existe(this.j)){
            like_button.setStyle("-fx-background-color: #f1faee;-fx-text-fill: red;-fx-background-radius: 50");
        }
        else {
            like_button.setStyle("-fx-background-color:#f1faee ; -fx-text-fill: #001A23;-fx-background-radius: 50");
        }
        loadLikes();
    }

    private  void loadLikes(){
        ServiceJaime sj=new ServiceJaime();
        long nb = sj.getAll().stream().filter(e->e.getEvenement().equals(ev)).count();
        like.setText(String.valueOf(nb));

    }

}
