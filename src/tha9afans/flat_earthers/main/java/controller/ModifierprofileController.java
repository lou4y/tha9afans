package controller;

import entities.Administrateur;
import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import org.w3c.dom.Text;
import services.AuthResponseDTO;
import services.ServicePersonne;
import services.UserSession;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class ModifierprofileController implements Initializable {
    @FXML
    private TextField textfieldnom;
    @FXML
    private TextField textfieldprenom;
    @FXML
    private TextField textfieldemail;
    @FXML
    private TextField textfieldtelephone;
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;
    @FXML
    private  TextField textfieldadresse;
    @FXML
    private DatePicker textfieldbirthdate;
    @FXML
    private TextField textfieldcin;
    @FXML
    private ImageView profileimageview;
    @FXML
    private Button modifierprofileuser;

    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();
    ServicePersonne sp=new ServicePersonne();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        textfieldcin.setText(userlogged.getCin());
        textfieldnom.setText(userlogged.getNom());
        textfieldprenom.setText(userlogged.getPrenom());
        textfieldemail.setText(userlogged.getEmail());
        textfieldadresse.setText(userlogged.getAdresse());
        textfieldtelephone.setText(userlogged.getTelephone());
        textfieldbirthdate.setValue(userlogged.getDateNaissance().toLocalDate());
        InputStream stream = userlogged.getImage();

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
            profileimageview.setImage(image);
            System.out.println(profileimageview);
            profileimageview.setPreserveRatio(false);

            // Set the fitWidth and fitHeight before setting the clip
            profileimageview.setFitWidth(100);
            profileimageview.setFitHeight(100);
            Circle clip = new Circle(profileimageview.getFitWidth() / 2, profileimageview.getFitHeight() / 2, profileimageview.getFitWidth() / 2);
            profileimageview.setClip(clip);
            clip.setStroke(Color.WHITE);
            clip.setStrokeWidth(2);

            System.out.println("Input stream loaded: " + (stream != null));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading image");
        }
        modifierprofileuser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                InputStream streamprofile = userlogged.getImage();
                try {
                    streamprofile.reset();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                if (userlogged.getRole().equals("utilisateur")){
                    Utilisateur u=new Utilisateur(userlogged.getIdUser(),textfieldcin.getText(),textfieldnom.getText(),textfieldprenom.getText(),textfieldemail.getText(),
                            userlogged.getPassword(),"utilisateur",textfieldtelephone.getText(),textfieldadresse.getText(),streamprofile,new Date(textfieldbirthdate.getValue().getYear()-1900,
                            textfieldbirthdate.getValue().getMonthValue(),textfieldbirthdate.getValue().getDayOfMonth()));
                    sp.modifier(u);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre profile est modifié", ButtonType.OK);
                    alert.showAndWait();

                } else {
                    Administrateur a=new Administrateur(userlogged.getIdUser(),textfieldcin.getText(),textfieldnom.getText(),textfieldprenom.getText(),textfieldemail.getText(),
                            userlogged.getPassword(),"administrateur",textfieldtelephone.getText(),textfieldadresse.getText(),streamprofile,new Date(textfieldbirthdate.getValue().getYear()-1900,
                            textfieldbirthdate.getValue().getMonthValue(),textfieldbirthdate.getValue().getDayOfMonth()));
                    sp.modifier(a);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre profile est modifié", ButtonType.OK);
                    alert.showAndWait();

                }



            }
        });


    }


    public void informationsbutton(MouseEvent event) {
        ap.setVisible(true);
        bp.setCenter(ap);

    }

    public void eventsbutton(MouseEvent event) throws IOException {
        ap.setVisible(false);
        loadPage("/test/securiteprofile");
    }

    private void loadPage(String page) throws IOException {
        Parent root=null;
        root= FXMLLoader.load(getClass().getResource(page+".fxml"));
        bp.setCenter(root);

    }
    public void securitebutton(MouseEvent event) throws IOException {
        ap.setVisible(false);
        loadPage("/test/securiteprofile");
    }

    public void storebutton(MouseEvent event) throws IOException {
        ap.setVisible(false);
        loadPage("/test/securiteprofile");
    }

    public void Reservationsbutton(MouseEvent mouseEvent) throws IOException {
        ap.setVisible(false);
        loadPage("/test/reservations");
    }

    public void Facturesbutton(MouseEvent mouseEvent) throws IOException {
        ap.setVisible(false);
        loadPage("/test/facture-view");
    }
}
