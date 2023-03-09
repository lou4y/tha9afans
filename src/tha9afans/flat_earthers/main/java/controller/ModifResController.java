package controller;

import entities.Reservation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import services.ServiceReservation;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class ModifResController implements Initializable {

    @FXML
    private Button buttonValider;

    @FXML
    DatePicker textDate;

    private Reservation reservation;

    public void setReservation(Element element) {
        this.reservation = element.getReservation();
        textDate.setValue(reservation.getDate_reservation().toLocalDate());
        buttonValider.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ServiceReservation sr=new ServiceReservation();
                Reservation r=new Reservation(reservation.getId(),new Date(textDate.getValue().getYear()-1900,textDate.getValue().getMonthValue(),textDate.getValue().getDayOfMonth())
                        ,reservation.getIspPaid(),reservation.getPayment_info(),reservation.getUser(),reservation.getBillet());
                sr.modifier(r);
            }
        });
    }


    public void initialize(URL location, ResourceBundle resources) {
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getText();
            if (text.matches("[0-9\\-]*")) {
                return change;
            }
            return null;
        };
        StringConverter<LocalDate> dateConverter = new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date == null) {
                    return "";
                }
                return dateFormatter.format(date);
            }

            @Override
            public LocalDate fromString(String string) {
                if (string == null || string.trim().isEmpty()) {
                    return null;
                }
                return LocalDate.parse(string, dateFormatter);
            }
        };
        TextFormatter<LocalDate> dateTextFormatter = new TextFormatter<>(dateConverter, null, filter);
    }
}
