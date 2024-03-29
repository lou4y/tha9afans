package controller;

import entities.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import services.*;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class PanierController implements Initializable {


    @FXML
    private TextField numberfield;
    @FXML
    private  TextField cardhfield;
    @FXML
    private TextField cvfield;
    @FXML
    private TextField expfield;
     @FXML
    private TextField email;
    @FXML
    private Label qproduit;
    @FXML
    private Label produitname;
    @FXML
    private HBox panier;
     @FXML
    private VBox paniervbox;
     @FXML
    private Label prixprodu;
      @FXML
    private Label total;
      @FXML
      private VBox vboxpanier;
      @FXML
        private ScrollPane scroll;

    @FXML
    private GridPane gridpanier;
    @FXML
    private Button check;
    MailSender sender = new MailSender();
    SendGrid mail = new SendGrid();
    private int counter = 0;
    private Panier p ;
    private Facture f ;


    ServicePanier sp = new ServicePanier();
    ServiceFacture sf = new ServiceFacture();
    ServiceCommandeProduit scp = new ServiceCommandeProduit();
    ServicePanierProduit spp = new ServicePanierProduit();
    ServicePersonne spersonne = new ServicePersonne();
    ServiceProduit sproduit = new ServiceProduit();


    AuthResponseDTO userlogged=UserSession.getUser_LoggedIn();

    public void checkbutton() throws IOException {
        String number = numberfield.getText();
        String cardh = cardhfield.getText();
        String cv = cvfield.getText();
        String exp = expfield.getText();
        String emailInput = email.getText();



        // Vérifie que le champ numberfield contient une valeur numérique à 16 chiffres qui commence par 4 ou 5
        if (!number.matches("^4[0-9]{15}$|^5[0-9]{15}$")) {
            JOptionPane.showMessageDialog(null, "Le numéro de carte est invalide.");
        }
        // Vérifie que le champ cardhfield contient une chaîne de caractères non vide
        else if (cardh.trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Le nom sur la carte est manquant.");
        }
        // Vérifie que le champ expfield contient une date au format yyyy-mm-dd
        else if (!exp.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
            JOptionPane.showMessageDialog(null, "La date d'expiration est invalide.");
        }
        // Vérifie que le champ cvfield contient une valeur numérique à 3 chiffres
        else if (!cv.matches("^[0-9]{3}$")) {
            JOptionPane.showMessageDialog(null, "Le code de sécurité est invalide.");
        }
        // Vérifie que le champ email contient une adresse email valide
        else if (!emailInput.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            JOptionPane.showMessageDialog(null, "L'adresse email est invalide.");
        }
        else {
            JOptionPane.showMessageDialog(null, "Paiement effectué avec succès.");

            //scp.ajouterproduitcommande(sp.GetPanierByUser(UserSession.getUser_LoggedIn()));

            // nb3th email
            String cardNumber = numberfield.getText().trim();
            String maskedCardNumber = "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
            // nformati fil date local
            LocalDateTime currentDate = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            String formattedDate = currentDate.format(formatter);

            gencommande();

            String message = "Bonjour, \n" +
                    "Nous vous confirmons que votre commande a bien été passe le . " + formattedDate +
                    " \n" + "informations de la carte:\n"+ "numero de carte  "+ maskedCardNumber + " \n" +  " nom sur la carte  "  + cardhfield.getText() + " \n" +
                    "Vous puvez consuler votre facture sur notre app  \n" +
                    "Merci pour votre confiance. \n" +
                    "Cordialement, \n" +
                    "Tha9afans Team. \n" +" \n" ;
            mail.Sendgrid(emailInput.trim(), message);
            //sender.SendMail(emailInput.trim(), message);
            numberfield.clear();
            cardhfield.clear();
            cvfield.clear();
            expfield.clear();
            email.clear();
        }
    }


public void gencommande(){

List<PanierProduit> list = new ArrayList<>();
    ServiceCommande scomande = new ServiceCommande();
    Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    Commande c = new Commande(spersonne.getOneById(userlogged.getIdUser()) ,timestamp,this.p.getTotal());

    scomande.ajouter(c);
    System.out.println("this cc"+c);
    list=spp.getproduitdanspanier(this.p);

System.out.println("this "+c);
    for (PanierProduit p : list){
        CommandeProduit cp = new CommandeProduit(scomande.getOneById(scomande.getId(c)),p.getQuantite(),p.getProduit());
        scp.ajouter(cp);
        for (int i = 0; i < p.getQuantite(); i++) {
            spp.supprimerProduitPanier(p.getProduit());
        }

    }

    Facture f = new Facture(scomande.getOneById(scomande.getId(c)),timestamp,12.6,"refdsqfqsd");
    sf.ajouter(f);

}







    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      // application();

        try {
            setDate();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scroll.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        calculerTotal();

    }

    private void setDate () throws IOException {
        ServicePersonne spp =new ServicePersonne();
        ServicePanierProduit sp = new ServicePanierProduit();
        ServicePanier spanier = new ServicePanier();
/*
        System.out.println(spp.getOneById(userlogged.getIdUser()));
*/
        int row=0;

        if (spanier.panierexiste(spp.getOneById(userlogged.getIdUser()))){
            Panier panier =spanier.GetPanierByUser(spp.getOneById(userlogged.getIdUser()));
            this.p=panier;
            List<PanierProduit> list = sp.getproduitdanspanier(panier);
            for (PanierProduit p : list){
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/test/boxpanier.fxml"));
                HBox box = loader.load();
                BoxpanierController controller = loader.getController();
                controller.setProduit(p);
                gridpanier.add(box, 0, row++);
                GridPane.setMargin(box, new Insets(10));
            }
        }
        else
        {
        spanier.ajouter(new Panier(0,spp.getOneById( userlogged.getIdUser()) ));
        }

        }

    public void calculerTotal(){

            ServicePanierProduit spp = new ServicePanierProduit();
            ServicePanier spanier = new ServicePanier();
            List<PanierProduit> list = spp.getproduitdanspanier(this.p);
            double totalpanier = 0;
            for (PanierProduit p : list){
                totalpanier += p.getQuantite() * p.getProduit().getPrix();
                sp.inserttotal(totalpanier,p.getPanier().getId());
            }

            this.total.setText(String.valueOf(totalpanier));




        }





    }









