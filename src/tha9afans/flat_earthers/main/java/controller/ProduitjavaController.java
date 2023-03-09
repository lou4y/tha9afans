package controller;

import entities.Produit;
import entities.Categorie;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.util.StringConverter;
import services.ServiceCategorie;
import services.ServiceProduit;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ProduitjavaController implements Initializable {

    @FXML
    private ScrollPane SPane;
    @FXML
    private DatePicker ev_date;

    @FXML
    private TextArea ev_description;

    @FXML
    private TextField ev_location;

    @FXML
    private TextField ev_name;
    @FXML
    private BorderPane bp;
    @FXML
    private AnchorPane ap;

    @FXML
    private Spinner<Integer> ev_pnb;

    @FXML
    private TextField ev_price;


    @FXML
    private GridPane event_grid;

    @FXML
    private Button session;

    @FXML
    private TextField searchfield;

    @FXML
    private VBox sessionlist;

    @FXML
    public ComboBox ev_region = new ComboBox();

    @FXML
    private ComboBox<Categorie> ev_cat = new ComboBox<>();
    ;
    @FXML
    private ComboBox<String> filter_categorie_b;

    @FXML
    private ComboBox<String> filter_date_b;

    @FXML
    private ComboBox<String> filter_region_b;

    @FXML
    private VBox add_event_b;
    private List<Produit> liste = new ArrayList<>();


    ///////////////////////////////////services////////////////////////////////////////////////////////////////////////
    ServiceCategorie sc = new ServiceCategorie();
    ServiceProduit se = new ServiceProduit();
    //ServiceSession ss = new ServiceSession();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        this.liste = se.getAll();
        try {
            load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Categorie> listc = sc.getAll();

        for (Categorie cat : listc) {
            ev_cat.getItems().add(cat);
        }
        ev_cat.setConverter(new StringConverter<Categorie>() {
            @Override
            public String toString(Categorie c) {
                return c.getNom();
            }

            @Override
            public Categorie fromString(String name) {
                return null;
            }
        });


    }


    private void load() throws IOException {
        event_grid.getChildren().clear();
        //System.out.println(liste);
        int col = 0;
        int row = 1;
        try {
            for (int i = 0; i < liste.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/test/produitcard.fxml"));
                VBox box = loader.load();
                produitcardcontroller controller = loader.getController();
                controller.setevenement(liste.get(i));
                if (col == 4) {
                    col = 0;
                    row++;
                }

                event_grid.add(box, col++, row);
                GridPane.setMargin(box, new Insets(10));//(child,column,row

            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void InputTest(){

        if (ev_name.getText().isEmpty() ){
            Alert a = new Alert(Alert.AlertType.WARNING, "add name ", ButtonType.OK);
            a.showAndWait();

        }
        if (ev_date.getValue()==null){
            Alert a = new Alert(Alert.AlertType.WARNING, "add date ", ButtonType.OK);
            a.showAndWait();

        }
        if (ev_region.getValue()==null){
            Alert a = new Alert(Alert.AlertType.WARNING, "Select region ", ButtonType.OK);
            a.showAndWait();

        }
        if (ev_location.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.WARNING, "add location ", ButtonType.OK);
            a.showAndWait();

        }
        if (ev_price.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.WARNING, "add price ", ButtonType.OK);
            a.showAndWait();

        }
        if (ev_pnb.getValue()==null){
            Alert a = new Alert(Alert.AlertType.WARNING, "add number of participants ", ButtonType.OK);
            a.showAndWait();

        }
        if (ev_cat.getValue()== null){
            Alert a = new Alert(Alert.AlertType.WARNING, "Select category ", ButtonType.OK);
            a.showAndWait();

        }
        if (ev_description.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.WARNING, "add description ", ButtonType.OK);
            a.showAndWait();
        }
    }
    @FXML
    void search(KeyEvent event) throws IOException {
        filterAll();
        load();
    }


    private void filterAll() {
        ServiceProduit sp= new ServiceProduit();
        this.liste = sp.getAll();
        String searched = searchfield.getCharacters().toString();

        if (!searched.isEmpty()) {
            this.liste = sp.getAllByName(searched, this.liste);
        }
    }




    public void buttonvente(MouseEvent event) throws IOException {
        ap.setVisible(false);
        loadPage("/test/produit-add");

    }
    public void buttonachat(MouseEvent event) {
        ap.setVisible(true);
        bp.setCenter(ap);

    }

    private void loadPage(String page) throws IOException {
        Parent root=null;
        root= FXMLLoader.load(getClass().getResource(page+".fxml"));
        bp.setCenter(root);
    }


}
