package controller;

import entities.Categorie;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.StringConverter;
import services.ServiceCategorie;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitModifyController implements Initializable {
    @FXML
    private Text viewProductResponse;
    @FXML
    private  TextField pm_name;
    @FXML
    private TextField pm_price;
    @FXML
    private TextField pm_libelle,remisefield;
    @FXML
    private ComboBox<Categorie> pm_categorie;
    @FXML
    private TextArea pm_description;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void btnEditProductOnAction(ActionEvent actionEvent) {
        ServiceCategorie sc=new ServiceCategorie();
        List<Categorie> list = sc.getAll();



        for (Categorie c : list) {
            pm_categorie.getItems().add(c);
        }
        pm_categorie.setConverter(new StringConverter<Categorie>() {
            @Override
            public String toString(Categorie ca) {
                return ca.getNom();
            }

            @Override
            public Categorie fromString(String name) {
                return null;
            }
        });
    }
}
