package controller;

import com.google.zxing.WriterException;
import entities.Billet;
import entities.Personne;
import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.print.PrinterJob;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;
import java.sql.Date;

public class Element {
    public String nom;
    public Date date;
    public String payed;
    private Button billetButton;
    private Button imprimerButton;
    private Scene billetScene;
    private Billet billet;
    private Reservation reservation ;

    public Element(String nom, Date date, String b, Billet billet, Personne user, Reservation reservation) {
        this.billet=billet;
        this.nom = nom;
        this.date = date;
        this.payed = b;
        this.billetButton= new Button("Voir billet !");
        this.reservation=reservation;
        this.billetButton.setOnAction(e -> {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/test/billet.fxml"));
                Parent root = fxmlLoader.load();
                billetScene = new Scene(root);
                Stage stage = new Stage();
                stage.setScene(billetScene);
                stage.show();
                BilletController controller= fxmlLoader.getController();
                controller.getElements(billet,user);
            } catch (IOException ex) {
                ex.printStackTrace();
            } catch (WriterException ex) {
                ex.printStackTrace();
            }
        });

        this.imprimerButton= new Button("Imprimer");
        this.imprimerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                billetButton.fire();
                PrinterJob job = PrinterJob.createPrinterJob();
                if (job != null) {
                    boolean success = job.showPrintDialog(null);
                    if (success) {
                        job.printPage(billetScene.getRoot());
                        job.endJob();
                    }
                }
                if (billetScene != null) {
                    billetScene.getWindow().hide();
                }
            }
        });
    }

    @Override
    public String toString() {
        return "Whatever{" + "nom='" + nom + '\'' + ", date=" + date + ", b=" + payed + '}';
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getPayed() {
        return payed;
    }

    public void setPayed(String b) {
        this.payed = b;
    }

    public Button getBilletButton() {
        return billetButton;
    }

    public void setBilletButton(Button billetButton) {
        this.billetButton = billetButton;
    }

    public Button getImprimerButton() {
        return imprimerButton;
    }

    public void setImprimerButton(Button imprimerButton) {
        this.imprimerButton = imprimerButton;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
