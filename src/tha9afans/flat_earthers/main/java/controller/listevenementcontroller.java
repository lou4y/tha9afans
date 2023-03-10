package controller;

import entities.CategorieEvenement;
import entities.Evenement;
import entities.Personne;
import entities.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import javafx.util.converter.LocalTimeStringConverter;
import services.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class listevenementcontroller implements Initializable {
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
    private ComboBox<CategorieEvenement> ev_cat = new ComboBox<>();
    ;
    @FXML
    private ComboBox<String> filter_categorie_b;

    @FXML
    private ComboBox<String> filter_date_b;

    @FXML
    private ComboBox<String> filter_region_b;

    @FXML
    private VBox add_event_b;

    private List<Session> listsession = new ArrayList<>();
    private List<Evenement> liste = new ArrayList<>();
    private Evenement ev;


    ///////////////////////////////////services////////////////////////////////////////////////////////////////////////
    ServiceCategorieEvenement sc = new ServiceCategorieEvenement();
    ServiceEvenement se = new ServiceEvenement();
    ServiceSession ss = new ServiceSession();
    ServicePersonne sp =new ServicePersonne();

    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        add_event_b.setVisible(false);
        add_event_b.setManaged(false);
        this.liste = se.getAll();
        try {
            load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ////////////////////////////spinner/////////////////////////////////////////////////////////////////////////////
        SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1000);
        valueFactory.setValue(0);
        ev_pnb.setValueFactory(valueFactory);
        ////////////////////////////combobox////////////////////////////////////////////////////////////////////////////
        List<CategorieEvenement> listc = sc.getAll();
        ObservableList obsCategories = FXCollections.observableArrayList();
        String[] regions = {"Tunis", "Ariana", "Ben Arous", "Mannouba", "Bizerte", "Nabeul", "Béja", "Jendouba", "Zaghouan", "Siliana", "Le Kef", "Sousse", "Monastir", "Mahdia", "Kasserine", "Sidi Bouzid", "Kairouan", "Gafsa", "Sfax", "Gabès", "Médenine", "Tozeur", "Kebili", "Ttataouine"};
        for (String c : regions) {
            obsCategories.add(c);
        }
        ev_region.setItems(obsCategories);

        for (CategorieEvenement cat : listc) {
            ev_cat.getItems().add(cat);
        }
        ev_cat.setConverter(new StringConverter<CategorieEvenement>() {
            @Override
            public String toString(CategorieEvenement c) {
                return c.getNom();
            }

            @Override
            public CategorieEvenement fromString(String name) {
                return null;
            }
        });
        ////////////////////////// filter //////////////////////////////////////////////////////////////////////////////
        ObservableList obsCat = FXCollections.observableArrayList();
        String[] regions1 = {"All", "Tunis", "Ariana", "Ben Arous", "Mannouba", "Bizerte", "Nabeul", "Béja", "Jendouba", "Zaghouan", "Siliana", "Le Kef", "Sousse", "Monastir", "Mahdia", "Kasserine", "Sidi Bouzid", "Kairouan", "Gafsa", "Sfax", "Gabès", "Médenine", "Tozeur", "Kebili", "Ttataouine"};
        for (String c : regions1) {
            obsCat.add(c);
        }
        filter_region_b.setItems(obsCat);
        filter_region_b.setValue("All");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        filter_date_b.setItems(FXCollections.observableArrayList("--", "assending", "desending"));
        filter_date_b.setValue("--");
        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        filter_categorie_b.getItems().add("All");
        for (CategorieEvenement cat : sc.getAll()) {
            filter_categorie_b.getItems().add(cat.getNom());

        }
        filter_categorie_b.setValue("All");
        /////////////////////////////scrollpane/////////////////////////////////////////////////////////////////////////
        SPane.setStyle("-fx-background:  #f1faee; -fx-background-color:  #f1faee");
        ;
        /////////////////////////////text area//////////////////////////////////////////////////////////////////////////
        ev_description.setWrapText(true);

        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9]*")) {
                return change;
            }
            return null;
        };
        StringConverter<Integer> converter = new StringConverter<Integer>() {
            @Override
            public Integer fromString(String s) {
                try {
                    return Integer.valueOf(s);
                } catch (NumberFormatException e) {
                    return 0;
                }
            }

            @Override
            public String toString(Integer integer) {
                return integer.toString();
            }
        };

        TextFormatter<Integer> numericFilter = new TextFormatter<Integer>(converter, 0, change -> {
            if (change.getText().matches("[0-9]*")) {
                return change;
            } else {
                return null;
            }
        });
        ev_price.setTextFormatter(numericFilter);
    }


    private void load() throws IOException {
        event_grid.getChildren().clear();
        int col = 0;
        int row = 1;
        try {
            for (int i = 0; i < liste.size(); i++) {
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("/test/evenementcard.fxml"));
                VBox box = loader.load();
                evenementcardcontroller controller = loader.getController();
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

    private void ajouterevenement(ActionEvent event) throws IOException {
        if (ev_name.getText().isEmpty()||ev_description.getText().isEmpty()||ev_location.getText().isEmpty()||ev_price.getText().isEmpty()||ev_cat.getValue()==null||ev_region.getValue()==null||ev_pnb.getValue()==null||ev_date.getValue()==null){
            InputTest();
        } else {
            ServiceEvenement se = new ServiceEvenement();
            Evenement E = new Evenement(ev_name.getText(), ev_description.getText(), ev_cat.getValue(), Date.valueOf(ev_date.getValue()),sp.getOneById(userlogged.getIdUser()) , ev_region.getValue().toString() + ", " + ev_location.getText(), Integer.parseInt(ev_pnb.getValue().toString()), 40,Integer.parseInt(ev_price.getText()));
            this.ev=E;
            if (se.existe(E) == 1) {
                Alert a = new Alert(Alert.AlertType.ERROR, "event already exists ", ButtonType.OK);
                a.showAndWait();
            } else {
                se.ajouter(E);
                Alert a = new Alert(Alert.AlertType.INFORMATION, "event added !", ButtonType.OK);
                a.showAndWait();

            }
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
    void confirmer(ActionEvent event) {
        try {
            ajouterevenement(event);
            savesession();
            int id = se.getId(this.ev);
            ServiceReservation sr = new ServiceReservation();
            System.out.println(id);
            this.liste = se.getAll();
            load();
            sr.setAllBillet(id);
        } catch (IOException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }

    @FXML
    void addSe(ActionEvent event) {
        final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");
        HBox hbox = new HBox();
        hbox.setPrefHeight(40);
        VBox box1 = new VBox();
        VBox box2 = new VBox();
        VBox box3 = new VBox();
        VBox box4 = new VBox();
        VBox box5 = new VBox();
        HBox box6 = new HBox();
        HBox box7 = new HBox();
        Label label1 = new Label("Start :");
        Label label2 = new Label("End :");
        TextField field1 = new TextField();
        field1.setPromptText("Title");
        TextField field2 = new TextField();
        field2.setPromptText("Description");
        TextField field3 = new TextField();
        Button btn = new Button("X");
        CheckBox btn2 = new CheckBox();
        field3.setPromptText("Host");

        hbox.setPadding(new Insets(15, 0, 15, 0));
        box1.setPadding(new Insets(0, 5, 0, 5));
        box2.setPadding(new Insets(0, 5, 0, 5));
        box3.setPadding(new Insets(0, 5, 0, 5));
        box4.setPadding(new Insets(0, 5, 0, 5));
        box5.setPadding(new Insets(4, 5, 4, 5));
        box7.setPadding(new Insets(0, 5, 0, 5));
        box6.setPadding(new Insets(0, 5, 0, 5));
        label1.setStyle("-fx-text-fill: #f1faee");
        label1.setPadding(new Insets(5, 5, 5, 5));
        label2.setStyle("-fx-text-fill: #f1faee");
        label2.setPadding(new Insets(5, 5, 5, 5));
        Spinner<LocalTime> debit = new Spinner<>();
        debit.setValueFactory(new SpinnerValueFactory<LocalTime>() {
            {   setValue(Time.valueOf("8:00:00").toLocalTime());
                setConverter(new LocalTimeStringConverter(TIME_FORMATTER, null));
            }
            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps));
            }
            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps));
            }
        });
        Spinner<LocalTime> fin = new Spinner<>();
        fin.setValueFactory(new SpinnerValueFactory<LocalTime>() {{
                setValue(Time.valueOf("8:00:00").toLocalTime());
                setConverter(new LocalTimeStringConverter(TIME_FORMATTER, null));
            }
            @Override
            public void decrement(int steps) {
                setValue(getValue().minusMinutes(steps));
            }
            @Override
            public void increment(int steps) {
                setValue(getValue().plusMinutes(steps));
            }});
        box1.getChildren().add(field1);
        box2.getChildren().add(field2);
        box3.getChildren().add(field3);
        box4.getChildren().add(btn);
        box5.getChildren().add(btn2);
        box6.getChildren().addAll(label1,debit);
        box7.getChildren().addAll(label2,fin);
        btn2.setOnAction(e -> {
            if (btn2.isSelected()) {
                Session s = new Session(null, field1.getText(), field2.getText(), field3.getText(), Time.valueOf(debit.getValue()), Time.valueOf(fin.getValue()));
                this.listsession.add(s);
            } else {
                Session s = new Session(null, field1.getText(), field2.getText(), field3.getText(),Time.valueOf(debit.getValue()), Time.valueOf(fin.getValue()));
                this.listsession.remove(s);
            }
        });
        btn.setOnAction(e -> {
            sessionlist.getChildren().remove(hbox);
            Session s = new Session(null, field1.getText(), field2.getText(), field3.getText(), new Time(12, 5, 10), new Time(12, 5, 10));
            this.listsession.remove(s);
        });

        hbox.getChildren().addAll(box1, box2, box3, box6, box7,box5, box4);
        sessionlist.getChildren().add(hbox);

    }

    private void savesession() {
        int id = se.getId(new Evenement(ev_name.getText(), ev_description.getText(), ev_cat.getValue(), Date.valueOf(ev_date.getValue()), sp.getOneById(userlogged.getIdUser()), ev_region.getValue().toString() + ", " + ev_location.getText(), Integer.parseInt(ev_pnb.getValue().toString()), 40,Integer.parseInt(ev_price.getText())));
        for (Session s : listsession) {
            s.setEvenement(se.getOneById(id));
            ss.ajouter(s);
        }
        this.listsession.clear();

    }


    @FXML
    void search(KeyEvent event) throws IOException {
        filterAll();
        load();
    }

    @FXML
    void filter_region(ActionEvent event) throws IOException {
        filterAll();
        load();

    }

    @FXML
    void filter_categorie(ActionEvent event) throws IOException {
        filterAll();
        load();
    }

    @FXML
    void filter_date(ActionEvent event) throws IOException {
        filterAll();
        load();
    }

    private void filterAll() {
        this.liste = se.getAll();
        String date = filter_date_b.getValue().toString();
        String categorie = filter_categorie_b.getValue().toString();
        String region = filter_region_b.getValue().toString();
        String searched = searchfield.getCharacters().toString();
        if (categorie != "All") {
            this.liste = se.getAllByCategorie(categorie, this.liste);
        }
        if (date == "assending") {
            this.liste = se.tri(this.liste);
        } else if (date == "desending") {
            this.liste = se.tri_inverse(this.liste);
        }
        if (region != "All") {
            System.out.println(region);
            this.liste = se.getAllByRegion(region, this.liste);
        }
        if (!searched.isEmpty()) {
            this.liste = se.getAllByName(searched, this.liste);
            System.out.println(liste);
        }
    }
    @FXML
    void add_event(ActionEvent event) {
        if (add_event_b.isVisible()) {
            add_event_b.setVisible(false);
            add_event_b.setManaged(false);
        } else {
            add_event_b.setVisible(true);
            add_event_b.setManaged(true);


        }
    }
}




