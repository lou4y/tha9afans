package controller;

import entities.Administrateur;
import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.PasswordField;
import org.mindrot.jbcrypt.BCrypt;
import services.AuthResponseDTO;
import services.ServicePersonne;
import services.UserSession;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class SecuriteprofileController implements Initializable {
    @FXML
    private PasswordField fieldpassword;
    @FXML
    private PasswordField fieldconfirmpassword,fieldpasswordactuel;
    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();
    ServicePersonne sp=new ServicePersonne();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void modifiermotdepasse(ActionEvent event) {
        String hashedPassword = userlogged.getPassword();
        if(!(BCrypt.checkpw(fieldpasswordactuel.getText(), hashedPassword.replace("$2y$", "$2a$")))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre mot de passe actuel est incorrecte", ButtonType.OK);
            alert.showAndWait();

        }
        else if  (!(fieldpassword.getText().equals(fieldconfirmpassword.getText()))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Les 2 mots de passe sont incompatible", ButtonType.OK);
            alert.showAndWait();


        }else if((fieldpassword.getText().length()<8) || !(fieldpassword.getText().matches(".*[A-Z].*")) || !(fieldpassword.getText().matches(".*[a-z].*"))){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez entrez un mot de passe contenant des lettres majuscules ,miniscules " +
                    "et de longeur supérieur à 8", ButtonType.OK);
            alert.showAndWait();

        }else {
            InputStream streamprofile = userlogged.getImage();
            try {
                streamprofile.reset();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            String salt = BCrypt.gensalt(13);
            String password = fieldpassword.getText();
            String hashedPasswordUser = BCrypt.hashpw(password, salt);
            if(userlogged.getRole().contains("ROLE_ADMIN")) {
                Administrateur a=new Administrateur(userlogged.getIdUser(),userlogged.getCin(),userlogged.getNom(),userlogged.getPrenom(),userlogged.getEmail(),
                        hashedPasswordUser,"administrateur",userlogged.getTelephone(),userlogged.getAdresse(),streamprofile,new Date(userlogged.getDateNaissance().getYear()-1900,
                        userlogged.getDateNaissance().getMonth(),userlogged.getDateNaissance().getDay()));
                sp.modifier(a);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre mot de passe est modifié", ButtonType.OK);
                alert.showAndWait();

            }
            else{
                Utilisateur u=new Utilisateur(userlogged.getIdUser(),userlogged.getCin(),userlogged.getNom(),userlogged.getPrenom(),userlogged.getEmail(),
                        hashedPasswordUser,"[]",userlogged.getTelephone(),userlogged.getAdresse(),streamprofile,new Date(userlogged.getDateNaissance().getYear()-1900,
                        userlogged.getDateNaissance().getMonth(),userlogged.getDateNaissance().getDay()));
                sp.modifier(u);
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Votre mot de passe est modifié", ButtonType.OK);
                alert.showAndWait();

            }
        }
    }
}
