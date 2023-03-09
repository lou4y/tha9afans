package controller;

import entities.Reservation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.json.JSONArray;
import javafx.scene.paint.Color;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.JSONException;
import org.json.JSONObject;
import services.AuthResponseDTO;
import services.ServicePersonne;
import services.ServiceReservation;
import services.UserSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class ReservationsController implements Initializable {
    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn(); //hedhi t7otouha f kol controlleur te5oulek luser eli 3mal login

    @FXML
    private TableView<Element> reservationTable;

    @FXML
    private TableColumn<Element, Button> billetCol;

    @FXML
    private TableColumn<Element,Date> dateResCol;

    @FXML
    private TableColumn<Element, Button> imprimerCol;

    @FXML
    private TableColumn<Element, String> nomEvenCol;

    @FXML
    private TableColumn<Element,String> paymentCol;

    @FXML
    private TextField searchfield;

    @FXML
    private Button supprimerButton;

    @FXML
    private Button Modifier;

    @FXML
    private WebView webView;

    private WebView mapWebView;



    private String encodeString(String string) {
        try {
            return URLEncoder.encode(string, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return "";
        }
    }

    private String getMapUrl(String address) {
        String url = "https://www.openstreetmap.org/export/embed.html?bbox=";
        String query = encodeString(address);
        String apiUrl = "https://nominatim.openstreetmap.org/search?q=" + query + "&format=json&addressdetails=1&limit=1";

        try {
            URL apiUrlObj = new URL(apiUrl);
            HttpURLConnection con = (HttpURLConnection) apiUrlObj.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("User-Agent", "Mozilla/5.0");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            JSONArray jsonArray = new JSONArray(response.toString());
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            String lat = jsonObject.getString("lat");
            String lon = jsonObject.getString("lon");

            double latitude = Double.parseDouble(lat);
            double longitude = Double.parseDouble(lon);

            // Set the delta value to adjust the size of the bounding box
            double delta = 0.02;

            // Calculate the coordinates of the bounding box
            double left = longitude - delta;
            double bottom = latitude - delta;
            double right = longitude + delta;
            double top = latitude + delta;

            // Add the bounding box to the URL
            url += left + "," + bottom + "," + right + "," + top;

            // Add a marker for the exact location
            url += "&marker=" + latitude + "," + longitude + ",red";

        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

//        String address = "Tunisia, Kerkennah ";
//        String mapUrl = getMapUrl(address);
//        WebEngine webEngine = webView.getEngine();
//        webEngine.load(mapUrl);
//
//        webView.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                // Get the coordinates of the mouse click
//                double mouseX = event.getX();
//                double mouseY = event.getY();
//
//                // Get the size of the WebView
//                double webViewWidth = webView.getWidth();
//                double webViewHeight = webView.getHeight();
//
//                // Calculate the coordinates of the click in the map's coordinate system
//                try {
//                    double left = (double) webEngine.executeScript("window.map.getBounds().getWest()");
//                    double top = (double) webEngine.executeScript("window.map.getBounds().getNorth()");
//                    double right = (double) webEngine.executeScript("window.map.getBounds().getEast()");
//                    double bottom = (double) webEngine.executeScript("window.map.getBounds().getSouth()");
//                    double longitude = left + (right - left) * mouseX / webViewWidth;
//                    double latitude = top + (bottom - top) * mouseY / webViewHeight;
//
//                    // Print the coordinates of the click
//                    System.out.println("Latitude: " + latitude + ", Longitude: " + longitude);
//                } catch (JSException e) {
//                    System.err.println("Error executing script: " + e.getMessage());
//                }
//
//            }
//        });
        style();
        nomEvenCol.setCellValueFactory(new PropertyValueFactory<Element,String>("nom"));
        dateResCol.setCellValueFactory(new PropertyValueFactory<Element,Date>("date"));
        paymentCol.setCellValueFactory(new PropertyValueFactory<Element,String>("payed"));
        billetCol.setCellValueFactory(new PropertyValueFactory<Element, Button>("billetButton"));
        imprimerCol.setCellValueFactory(new PropertyValueFactory<Element, Button>("imprimerButton"));

        ObservableList<Element> list1 = FXCollections.observableArrayList();
        //    int userId = 3;
        ServiceReservation serviceReservation = new ServiceReservation();
        ServicePersonne serviceUser = new ServicePersonne();
        List<Reservation> list= serviceReservation.getAllReservationsByUser(serviceUser.getOneById(userlogged.getIdUser()));
        for (Reservation r: list) {
            Date dateRes=r.getDate_reservation();
            String nomEvent=r.getBillet().getEvenement().getNom();
            String payment=(r.getIspPaid()) ? "FREE": String.valueOf(r.getBillet().getPrix()+" DT");
            Element row = new Element(nomEvent,dateRes,payment,r.getBillet(),r.getUser(),r);
            System.out.println(row);
            list1.add(row);
        }
        ObservableList<Element> filteredList = FXCollections.observableArrayList(list1);
        searchfield.textProperty().addListener((observable, oldValue, newValue) -> {
            String searched = searchfield.getText().toLowerCase();
            filteredList.clear();
            for (Element row : list1) {
                if (row.getNom().toLowerCase().contains(searched.toLowerCase())
                        || row.getDate().toString().toLowerCase().contains(searched.toLowerCase()))
                {
                    filteredList.add(row);
                }
            }
        });

        reservationTable.setItems(filteredList);
        reservationTable.setStyle("-fx-border-color: black; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-color: #f2f2f2;");
    }


    private void style() {
        reservationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        Callback<TableColumn<Element, String>, TableCell<Element, String>> cellFactory = col -> {
            return new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                    setAlignment(Pos.CENTER);
                }
            };
        };

        Callback<TableColumn<Element, Date>, TableCell<Element, Date>> dateCellFactory = col -> {
            return new TableCell<>() {
                private final SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");

                @Override
                protected void updateItem(Date item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("");
                        getTableRow().setStyle("");
                    } else {
                        setText(format.format(item));
                        if (item.toLocalDate().isBefore(LocalDate.now())) {
                            getTableRow().setStyle("-fx-background-color: #ABDADC;");
                        } else {
                            setStyle("");
                            getTableRow().setStyle("");
                        }
                    }
                    setAlignment(Pos.CENTER);
                }
            };
        };




        Callback<TableColumn<Element, String>, TableCell<Element, String>> paymentCellFactory = col -> {
            return new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                    setAlignment(Pos.CENTER);
                    if (item != null && item.equals("FREE")) {
                        setTextFill(Color.GREEN);
                    } else {
                        setTextFill(Color.RED);
                    }
                }
            };
        };

        Callback<TableColumn<Element, Button>, TableCell<Element, Button>> buttonCellFactory = col -> {
            return new TableCell<>() {
                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setGraphic(null);
                    } else {
                        setGraphic(item);
                    }
                    setAlignment(Pos.CENTER);
                }
            };
        };

        billetCol.setCellFactory(buttonCellFactory);
        imprimerCol.setCellFactory(buttonCellFactory);

        nomEvenCol.setCellFactory(cellFactory);
        dateResCol.setCellFactory(dateCellFactory);
        paymentCol.setCellFactory(paymentCellFactory);
    }

    @FXML
    void supprimerEvent(ActionEvent event) {
        TableView.TableViewSelectionModel<Element> selectionModel = reservationTable.getSelectionModel();
        Element selectedRow = selectionModel.getSelectedItem();
        ServiceReservation serviceReservation = new ServiceReservation();
        serviceReservation.supprimer(selectedRow.getReservation().getId());
        reservationTable.getItems().removeAll(reservationTable.getSelectionModel().getSelectedItem());
    }


    @FXML
    public void modifierReservation(ActionEvent event) throws IOException {
        Element selectedElement = reservationTable.getSelectionModel().getSelectedItem();
        if (selectedElement != null) {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/test/modifRes.fxml"));
            Parent modifResFXML = fxmlLoader.load();
            ModifResController controller = fxmlLoader.getController();
            controller.setReservation(selectedElement); // set the reservation field on the controller
            Stage stage = new Stage();
            stage.setScene(new Scene(modifResFXML));
            stage.showAndWait();
        }else{
            Alert a= new Alert(Alert.AlertType.WARNING,"IL FAUT SELECTIONNER UNE RESERVATION",ButtonType.OK);
            a.showAndWait();
        }
    }

}


