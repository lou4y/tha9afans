package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.stage.Stage;
import services.AuthResponseDTO;
import services.UserSession;
import utils.DataSource;

import javax.swing.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    Connection cnx = DataSource.getInstance().getCnx();
    @FXML
    private TextField emailfield;
    @FXML
    private Button signupbutton,linkforgotpassword,loginbutton;
    @FXML
    private PasswordField passwordfield;
    AuthResponseDTO userLoggedIn;
    ResultSet rs;

    @FXML
    void login(ActionEvent event) throws SQLException {
        String email= emailfield.getText();
        String password=passwordfield.getText();

        if(email.equals("") && password.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez entrez un email et un mot de passe", ButtonType.OK);
            alert.showAndWait();
            //partie session


        }else{
            try {
                String req="select * from personnes where email=? and password=?";
                PreparedStatement ps = cnx.prepareStatement(req);
                ps.setString(1,email);
                ps.setString(2,password);

                rs=ps.executeQuery();

                if(rs.next()){
                    Blob blob = rs.getBlob(10);
                    InputStream inputStream = blob.getBinaryStream();
                    userLoggedIn = new AuthResponseDTO(rs.getInt("id"),rs.getString("cin"),rs.getString("nom"),
                            rs.getString("prenom"),rs.getString("email"),rs.getString("password"),rs.getString("role"),
                            rs.getString("telephone"),rs.getString("adresse"),rs.getDate(11), inputStream);
                    UserSession.getSameInstance(userLoggedIn);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vous etes authentifié avec succés", ButtonType.OK);
                    alert.showAndWait();
                    Parent root = FXMLLoader.load(getClass().getResource("/test/sidenavbaruser.fxml"));
                    Stage window=(Stage) loginbutton.getScene().getWindow();
                    window.setScene(new Scene(root,1400,700));
                }else{
                    Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez vérifier votre email et/ou mot de passe", ButtonType.OK);
                    alert.showAndWait();
                    emailfield.setText("");
                    passwordfield.setText("");
                    emailfield.requestFocus();
                }


            }
            catch (SQLException | IOException ex) {
                JOptionPane.showMessageDialog(null,ex);
            }

        }


    }
    //naviguer à l'interface Sign up
    public void passerinscrire() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/test/signup.fxml"));
        Stage window=(Stage) signupbutton.getScene().getWindow();
        window.setScene(new Scene(root,1400,700));
        window.setTitle("Sign Up");


    }
    public void gointerfaceforgotpassword() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/test/forgotpassword.fxml"));
        Stage window=(Stage) linkforgotpassword.getScene().getWindow();
        window.setScene(new Scene(root,1400,700));
        window.setTitle("forgot password");

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        emailfield.setText("test@gmail.com");
        passwordfield.setText("test");



    }
}
