package controller;

import entities.Administrateur;
import entities.Personne;
import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import services.AuthResponseDTO;
import services.ServicePersonne;
import services.UserSession;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ModifierUserController implements Initializable {
    @FXML
    TextField fieldnom;
    @FXML
    TextField fieldprenom, fieldemail, fieldadresse, fieldtelephone,cinfield;
    @FXML
    PasswordField fieldmotdepasse;
    @FXML
    DatePicker fielddate;
    @FXML
    Button annulerbutton;
    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();
    @FXML
    private ImageView imageprofileuser;
    ServicePersonne sp=new ServicePersonne();

    // Définir la méthode setUserId pour initialiser la variable userId
    private int userId;
    private Image image;
    @FXML
    Button modifbutton;

    // Définir la méthode setUserId pour initialiser la variable userId
    public void setUserId(int userId) {
        this.userId = userId;



        System.out.println("setUserId called with userId = " + userId);
        Personne p=sp.getOneById(userId);
        fieldnom.setText(p.getNom());
        fieldprenom.setText(p.getPrenom());
        fieldemail.setText(p.getEmail());
        fieldadresse.setText(p.getAdresse());
        fieldmotdepasse.setText(p.getPassword());
        fieldtelephone.setText(p.getTelephone());
        fielddate.setValue(p.getDateNaissance().toLocalDate());
        cinfield.setText(p.getCin());
        InputStream stream = p.getPhoto();

        if (stream == null) {
            System.out.println("InputStream is null");
            return;
        }
        try {
            stream.reset();
            Image image = new Image(stream);


            /*if (image == null) {
                System.out.println("Image is null");
                return;
            }*/
            imageprofileuser.setImage(image);
            System.out.println(imageprofileuser);
            imageprofileuser.setPreserveRatio(false);

            // Set the fitWidth and fitHeight before setting the clip
            imageprofileuser.setFitWidth(100);
            imageprofileuser.setFitHeight(100);
            Circle clip = new Circle(imageprofileuser.getFitWidth() / 2, imageprofileuser.getFitHeight() / 2, imageprofileuser.getFitWidth() / 2);
            imageprofileuser.setClip(clip);
            clip.setStroke(Color.WHITE);
            clip.setStrokeWidth(2);

            System.out.println("Input stream loaded: " + (stream != null));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading image");
        }

        modifbutton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                InputStream streamprofile = p.getPhoto();
                try {
                    streamprofile.reset();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (p instanceof Utilisateur){
                    Utilisateur u=new Utilisateur(userId,cinfield.getText(),fieldnom.getText(),fieldprenom.getText(),fieldemail.getText(),
                            fieldmotdepasse.getText(),"utilisateur",fieldtelephone.getText(),fieldadresse.getText(),streamprofile,new Date(fielddate.getValue().getYear()-1900,
                            fielddate.getValue().getMonthValue(),fielddate.getValue().getDayOfMonth()));
                    sp.modifier(u);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre utilisateur est modifié avec succés", ButtonType.OK);
                    alert.showAndWait();

                } else {
                    Administrateur a=new Administrateur(userId,cinfield.getText(),fieldnom.getText(),fieldprenom.getText(),fieldemail.getText(),
                            fieldmotdepasse.getText(),"administrateur",fieldtelephone.getText(),fieldadresse.getText(),streamprofile,new Date(fielddate.getValue().getYear()-1900,
                            fielddate.getValue().getMonthValue(),fielddate.getValue().getDayOfMonth()));
                    sp.modifier(a);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre administrateur est modifié avec succés", ButtonType.OK);
                    alert.showAndWait();

                }



            }
    });
    }
    public void retournelistview() throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/sidenavbar.fxml"));
        Parent root = loader.load();
        Stage window = (Stage) annulerbutton.getScene().getWindow();
        window.setTitle("dashboard");
        window.setScene(new Scene(root, 900, 450));

        // get reference to the SidenavbarController instance and initialize it
        SidenavbarController sidenavbarController = loader.getController();
        //sidenavbarController.initialize(loader.getLocation(), loader.getResources());
        //SidenavbarController controller = loader.getController();
        //controller.setUser(userlogged);
        //controller.onShow();


        // pass the userlogged object to the loadPage method
        sidenavbarController.loadPage("/test/administrateur-view");
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

}