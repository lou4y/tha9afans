package controller;
import entities.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import services.*;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class evenementcontroller implements Initializable {

    @FXML
    private Label date;

    @FXML
    private Label desc;

    @FXML
    private Label like;

    @FXML
    private Label location;

    @FXML
    private Label price;

    @FXML
    private Label title;
    @FXML
    private Button b1;
    @FXML
    private Button b2;
    @FXML
    private ScrollPane EPane;

    @FXML
    private ImageView image_ev;


    private Evenement ev;
    @FXML
    private VBox sessionview;
    @FXML
    public VBox commentlist;
    @FXML
    private Button like_button;

    @FXML
    private Button participate;
    @FXML
    private WebView map;
    ServiceEvenement se = new ServiceEvenement();
    ServiceSession ss = new ServiceSession();
    ServiceJaime sj = new ServiceJaime();
    ServiceReservation sr = new ServiceReservation();
    ServicePersonne sp =new ServicePersonne();
    ServiceGalerie sg = new ServiceGalerie();

    AuthResponseDTO userlogged= UserSession.getUser_LoggedIn();
    private Jaime j = new Jaime();





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

            JSONArray jsonArray = new JSONArray(ev.getLocalisation());
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            String lat = jsonObject.getString("lat");
            String lon = jsonObject.getString("lng");

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

        } catch ( JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return url;
    }




    @FXML
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        EPane.setStyle("-fx-background:  #f1faee; -fx-background-color:  #f1faee");
        this.desc.setWrapText(true);




    }

    public void eventpage(Evenement e) throws IOException {
        this.ev=e;
        this.j= new Jaime(sp.getOneById(userlogged.getIdUser()),ev);
        InputStream stream = new FileInputStream("src/tha9afans/flat_earthers/main/gui/images/tha9afans1.png");

        if (sg.getallbyevent(e.getId()).size()!=0){
            stream=sg.getallbyevent(e.getId()).get(0).getPhoto();
            stream.reset();
        }


        Image image = new Image(stream);
        image_ev.setImage(image);
        title.setText(e.getNom());
        desc.setText(e.getDescription());
        date.setText(e.getDate().toString());
        location.setText(e.getAddress());
        price.setText(e.isOnline()==true?"online":"offline");
        like.setText("");
        setSession();
        setlike();
        setcomment();

        if (sr.ReservationDispon(this.ev)==0){
            participate.setText("Sold out");
            participate.setDisable(true);
            participate.setStyle("-fx-background-color: #f1faee; -fx-text-fill: #1d3557");
        }
        else{
            participate.setText("participate");
            participate.setDisable(false);
        }

        String address = "Tunisie "+this.ev.getLocalisation();
        String mapUrl = getMapUrl(address);
        WebEngine webEngine = map.getEngine();
        webEngine.load(mapUrl);
    }
    void setSession(){

        List<Session> ls= ss.getAllByEvent(se.getOneById(se.getId(ev)));
        for (Session s : ls){
            HBox h = new HBox();
            VBox box1= new VBox();
            VBox box2 = new VBox();
            VBox box3= new VBox();
            VBox box4= new VBox();
            VBox box5 = new VBox();
            box1.setPadding(new Insets(0, 5, 0, 5));
            box2.setPadding(new Insets(0, 5, 0, 5));
            box3.setPadding(new Insets(0, 5, 0, 5));
            box4.setPadding(new Insets(0, 5, 0, 5));
            box5.setPadding(new Insets(0, 5, 0, 5));
            h.setSpacing(60);
            h.setPadding(new Insets(15, 15, 15, 15));
            h.setStyle("-fx-border-color: #f1faee;-fx-border-radius: 15");
            Label titre = new Label(s.getTitre());
            box1.getChildren().add(titre);
            titre.setStyle("-fx-min-width: 50; -fx-font-size: 15px; -fx-text-fill:  #f1faee;");
            Label description = new Label(s.getDescription());
            box2.getChildren().add(description);
            description.setStyle("-fx-min-width: 50; -fx-font-size: 15px; -fx-text-fill:  #f1faee;");
            Label host = new Label(s.getParlant());
            box3.getChildren().add(host);
            host.setStyle("-fx-min-width: 50; -fx-font-size: 15px; -fx-text-fill:  #f1faee;");
            Label debuit = new Label(s.getDebit().toString());
            box4.getChildren().add(debuit);
            debuit.setStyle("-fx-min-width: 50; -fx-font-size: 15px; -fx-text-fill:  #f1faee;");
            Label fin = new Label(s.getFin().toString());
            box5.getChildren().add(fin);
            fin.setStyle("-fx-min-width: 50; -fx-font-size: 15px; -fx-text-fill:  #f1faee;");
            h.getChildren().addAll(box1,box2,box3,box4,box5);
            sessionview.getChildren().add(h);

        }
    }
    public void setcomment() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/test/Listcommentaire.fxml"));
        VBox box = loader.load();
        Listcommentairecontroller controller = loader.getController();
        controller.setCommentList(this.ev);
        commentlist.getChildren().add(box);

    }



    @FXML
    void like(ActionEvent event) {
        if (sj.existe(this.j)){
            sj.supprimer(j);
        }
        else {
           sj.ajouter(j);
        }
        setlike();


    }
    private void setlike(){
        if (sj.existe(this.j)){
            like_button.setStyle("-fx-background-color: #f1faee;-fx-text-fill: red;-fx-background-radius: 50");
        }
        else {
            like_button.setStyle("-fx-background-color:#f1faee ; -fx-text-fill: #001A23;-fx-background-radius: 50");
        }
        loadLikes();
    }

    private  void loadLikes(){
        ServiceJaime sj=new ServiceJaime();
        long nb = sj.getAll().stream().filter(e->e.getEvenement().equals(ev)).count();
        like.setText(String.valueOf(nb));

    }

    public void participate(ActionEvent actionEvent) {
        if (sr.ReservationDispon(this.ev)==1){
            participate.setText("Sold out");
            participate.setDisable(true);
            participate.setStyle("-fx-background-color: #f1faee; -fx-text-fill: #1d3557");
        }

    }
}
