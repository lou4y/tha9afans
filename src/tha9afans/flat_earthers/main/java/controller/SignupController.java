package controller;

import entities.Personne;
import entities.Utilisateur;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.EmailValidator;
import services.ServicePersonne;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class SignupController implements Initializable {
    ServicePersonne sp=new ServicePersonne();
    @FXML
    Button signinbutton;
    @FXML
    private TextField cinfield, nomfield,prenomfield,emailfield,motdepassefield, confirmermotdepassefield,telephonefield,adressefield;

    @FXML
    private DatePicker naissancefield;
    EmailValidator validator=new EmailValidator();
    LocalDate date=null;
    @FXML
    void Signup(ActionEvent event) {
        String cin= cinfield.getText();
        String nom=nomfield.getText();
        String prenom=prenomfield.getText();
        String email= emailfield.getText();
        String motdepasse=motdepassefield.getText();
        String confirmerpassword=confirmermotdepassefield.getText();
        String telephone=telephonefield.getText();
        String adresse=adressefield.getText();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        List<Personne> lp=sp.getAll();
        //image user par défaut

        File file = new File("src/tha9afans/flat_earthers/main/gui/test/images/defaulticon.png");
        if (!file.exists()) {
            throw new RuntimeException("File not found: " + file.getAbsolutePath());
        }
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Convert the BufferedImage to an InputStream
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InputStream is = new ByteArrayInputStream(os.toByteArray());


        date = naissancefield.getValue();
        if (date!=null){
            String dateString = date.format(formatter);
            if(cin.equals("") || nom.equals("") || prenom.equals("") || email.equals("") || motdepasse.equals("") || telephone.equals("") ||
                    confirmerpassword.equals("") || adresse.equals("") || dateString.equals("")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez remplir tous les champs", ButtonType.OK);
                alert.showAndWait();



            }else if (!motdepasse.equals(confirmerpassword)){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "les 2 mots de passes sont incompatible", ButtonType.OK);
                alert.showAndWait();
            }else if((motdepasse.length()<8) || !(motdepasse.matches(".*[A-Z].*")) || !(motdepasse.matches(".*[a-z].*"))){
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez entrez un mot de passe contenant des lettres majuscules ,miniscules " +
                        "et de longeur supérieur à 8", ButtonType.OK);
                alert.showAndWait();

            }
             else if(!validator.validate(email)) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez entrez une adresse mail valide", ButtonType.OK);
                alert.showAndWait();
            }
             else if ((lp.stream().filter(p->p.getEmail().equals(email)).findFirst().isPresent())) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Cette adresse mail est déja utilisé", ButtonType.OK);
                alert.showAndWait();


            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION, "Vous etes inscrit avec succés", ButtonType.OK);
                alert.showAndWait();

                sp.ajouter(new Utilisateur(cin,nom,prenom,email,motdepasse,telephone,adresse,is,new Date(date.getYear()-1900,date.getMonthValue(),date.getDayOfMonth())));
                System.out.println(date);
                System.out.println(dateString);

            }



        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Veuillez entrer une date de naissance valide valide", ButtonType.OK);
            alert.showAndWait();
        }



    }

    public void goSignIn() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/test/login-view.fxml"));
        Stage window=(Stage) signinbutton.getScene().getWindow();
        window.setScene(new Scene(root,1400,700));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
