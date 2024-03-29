package controller;

import entities.CategorieEvenement;
import entities.Evenement;
import entities.Session;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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

public class editcontroller implements Initializable {
    @FXML
    private Button ev_add;
    @FXML
    private TextField link;

    @FXML
    private DatePicker ev_date;

    @FXML
    private TextField ev_description;

    @FXML
    private TextField ev_location;

    @FXML
    private TextField ev_name;

    @FXML
    private HBox evname;

    @FXML
    private ComboBox<String> fop;

    @FXML
    private ComboBox<String> oof;

    @FXML
    private ComboBox<CategorieEvenement> ev_cat1;

    @FXML
    private Button br;

    @FXML
    private VBox sessionlist =new VBox();
    private List<Session> listsession=new ArrayList<>();
    private List<Session> listsdb=new ArrayList<>();
    private Evenement ev;
    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();
    ServicePersonne sp = new ServicePersonne();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ServiceCategorieEvenement sc = new ServiceCategorieEvenement();
        List<CategorieEvenement> listc =sc.getAll();
        for (CategorieEvenement cat : listc) {
            ev_cat1.getItems().add(cat);
        }
        ev_cat1.setConverter(new StringConverter<CategorieEvenement>() {
            @Override
            public String toString(CategorieEvenement c) {
                return c.getNom();
            }

            @Override
            public CategorieEvenement fromString(String name) {
                return null;
            }
        });
        ObservableList obsfor = FXCollections.observableArrayList();
        String[] fop1 = {"Free","Paid"};
        for (String c : fop1) {
            obsfor.add(c);
        }
        fop.setItems(obsfor);

        ObservableList obsoof = FXCollections.observableArrayList();
        String[] oof1 = {"Online","Offline"};
        for (String c : oof1) {
            obsoof.add(c);
        }
        oof.setItems(obsoof);


    }


    public void setevent(Evenement e) {
        ServiceSession ss = new ServiceSession();
        this.ev = e;
        this.listsession = ss.getAllByEvent(e);
        ev_name.setText(e.getNom());
        ev_description.setText(e.getDescription());
        ev_cat1.setValue(e.getCategorieEvenement());
        ev_date.setValue(e.getDate().toLocalDate());
        ev_location.setText(e.getAddress());
        link.setText(e.getLink());
        oof.setValue(e.isOnline() == true ? "Offline" : "Online");
        fop.setValue(e.isFreeorpaid() == false ? "Free" : "Paid");
        this.listsdb = ss.getAllByEvent(ev);
        System.out.println(listsdb);
    }

    private void setsessions() {
        for (Session session : listsession) {
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
            field1.setText(session.getTitre());

            TextField field2 = new TextField();
            field2.setText(session.getDescription());
            TextField field3 = new TextField();
            field3.setText(session.getParlant());
            Button btn = new Button("X");
            CheckBox btn2 = new CheckBox();


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
                {
                    setValue(session.getDebit().toLocalTime());
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
            fin.setValueFactory(new SpinnerValueFactory<LocalTime>() {
                {
                    setValue(session.getFin().toLocalTime());
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
            box1.getChildren().add(field1);
            box2.getChildren().add(field2);
            box3.getChildren().add(field3);
            box4.getChildren().add(btn);
            box5.getChildren().add(btn2);
            box6.getChildren().addAll(label1, debit);
            box7.getChildren().addAll(label2, fin);
            btn2.setOnAction(e -> {
                if (btn2.isSelected()) {
                    Session s = new Session(null, field1.getText(), field2.getText(), field3.getText(), Time.valueOf(debit.getValue()), Time.valueOf(fin.getValue()));
                   // this.listsession.add(s);
                } else {
                    Session s = new Session(null, field1.getText(), field2.getText(), field3.getText(), Time.valueOf(debit.getValue()), Time.valueOf(fin.getValue()));
                    //this.listsession.remove(s);
                }
            });
            btn.setOnAction(e -> {
                sessionlist.getChildren().remove(hbox);
                Session s = new Session(null, field1.getText(), field2.getText(), field3.getText(), new Time(12, 5, 10), new Time(12, 5, 10));
                //this.listsession.remove(s);
            });

            hbox.getChildren().addAll(box1, box2, box3, box6, box7, box5, box4);
            sessionlist.getChildren().add(hbox);


        }

    }


    @FXML
    private void ajouterevenement(ActionEvent event) throws IOException {

        if (ev_name.getText().isEmpty() ||
                ev_date.getValue().toString().isEmpty() ||
                ev_description.getText().isEmpty() ||
                ev_location.getText().isEmpty() ||
                link.getText().isEmpty() ||

                ev_cat1.getValue().toString().isEmpty()) {
            Alert a = new Alert(Alert.AlertType.WARNING, "Complete event details ", ButtonType.OK);
            a.showAndWait();
        } else {
            ServiceEvenement se = new ServiceEvenement();

            Evenement E = new Evenement(ev.getId(),ev_name.getText(), ev_description.getText(), ev_cat1.getValue(), Date.valueOf(ev_date.getValue()), sp.getOneById(userlogged.getIdUser()),"" ,ev_location.getText(), fop.getValue()=="Free"?false:true, oof.getValue()=="Offline"?true:false, link.getText());
            se.modifier(E);
                Alert a = new Alert(Alert.AlertType.INFORMATION, "event edited !", ButtonType.OK);

                a.showAndWait();
                ev=se.getOneById(E.getId());

            }
        }

    @FXML
    void confirmer(ActionEvent event) {

        try {
            ajouterevenement(event);

        } catch (IOException ex) {
            Alert a = new Alert(Alert.AlertType.ERROR, ex.getMessage(), ButtonType.OK);
            a.showAndWait();
        }
    }
    @FXML
    void ret(ActionEvent event) throws IOException {
        Scene scene;
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/test/evenement.fxml"));
        Parent root = loader.load();

        evenementcontroller nextController = loader.getController();
        nextController.eventpage(ev);
        Stage stage = (Stage) br.getScene().getWindow();
        scene=new Scene(root,1400,700);
        stage.setScene(scene);
        stage.show();
    }

}
