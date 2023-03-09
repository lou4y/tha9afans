package controller;

import entities.Evenement;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import services.AuthResponseDTO;
import services.ServicePersonne;
import services.UserSession;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.sql.SQLException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.chart.NumberAxis;

public class SidenavbarController implements Initializable {
    @FXML
    private Label Menu;

    @FXML
    private Label MenuClose;
    @FXML
    private AnchorPane slider,ap;
    @FXML
    private Text textusername;
    @FXML
    private BorderPane bp;
    @FXML
    private ImageView profileimageview;
    @FXML
    private Button buttonevents;
    @FXML
    private Button logoutbutton,dashboardbutton,buttonuser,buttonfacture,buttonlogout,profilebutton;

    @FXML
    private Button buttonquizz;
    @FXML
    PieChart userStatsByRegionPieChart;
    @FXML
    private BarChart<String, Integer> barChart;
    private ModifierUserController modifierUserController;
    AuthResponseDTO userlogged=UserSession.getUser_LoggedIn();
    public void logout(ActionEvent event) throws IOException {
        UserSession.getSameInstance(userlogged).Logout();

        Parent root = FXMLLoader.load(getClass().getResource("/test/login-view.fxml"));
        Stage window=(Stage) logoutbutton.getScene().getWindow();
        window.setScene(new Scene(root,1400,700));

    }
    private static final Map<Image, ImageView> imageCache = new HashMap<>();
    public void setUser(AuthResponseDTO user) {
        this.userlogged = user;
    }



    public void loadImage() {
        textusername.setText(userlogged.getNom()+" "+ userlogged.getPrenom());
        InputStream stream = userlogged.getImage();

        if (stream == null) {
            System.out.println("InputStream is null");
            return;
        }
        try {
            stream.reset();
            Image image = new Image(stream);


            /*if (image == null) {
                System.out.println("Image is null");
                return;
            }*/
            profileimageview.setImage(image);
            System.out.println(profileimageview);
            profileimageview.setPreserveRatio(false);

            // Set the fitWidth and fitHeight before setting the clip
            profileimageview.setFitWidth(50);
            profileimageview.setFitHeight(50);
            //rendre l'image cerclé

            Circle clip = new Circle(profileimageview.getFitWidth() / 2, profileimageview.getFitHeight() / 2, profileimageview.getFitWidth() / 2);
            profileimageview.setClip(clip);
            clip.setStroke(Color.WHITE);
            clip.setStrokeWidth(2);

            System.out.println("Input stream loaded: " + (stream != null));
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error loading image");
        } /*finally {
        try {
            //stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        VBox.setMargin(profileimageview, new Insets(10, 18, 0, 18));

        // Load the image when the interface is loaded
        loadImage();



    Map<String, Integer> stats = null;
        ServicePersonne sp=new ServicePersonne();

        Map<String, Integer> statsByRegion = null;
        try {
            statsByRegion = sp.getUserStatsByRegion();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        int totalUsers = statsByRegion.values().stream().mapToInt(Integer::intValue).sum();
        for (Map.Entry<String, Integer> entry : statsByRegion.entrySet()) {
            String name = entry.getKey();
            int value = entry.getValue();
            double percentage = ((double) value / totalUsers) * 100;
            String dataName = String.format("%s %.2f%%", name, percentage);
            pieChartData.add(new PieChart.Data(dataName, value));
        }

        Map<String, Integer> eventData =null;
        XYChart.Series<String, Integer> series = new XYChart.Series<>();
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("participants");
        yAxis.setTickUnit(100);
        try {
            eventData = sp.getEventData();
            for (Map.Entry<String, Integer> entry : eventData.entrySet()) {
                String eventName = entry.getKey();
                int participantCount = entry.getValue();
                XYChart.Data<String, Integer> data = new XYChart.Data<>(eventName, participantCount);
                StackPane stackPane = new StackPane();
                stackPane.setStyle("-fx-background-color: #1D3557;");
                data.setNode(stackPane);
                series.getData().add(data);
            }
        } catch (SQLException e) {
            // handle exception
        }
        series.setName("classification des évenements par nombre de participants");
        yAxis.setTickUnit(100);
        series.setNode(yAxis);
        barChart.getData().add(series);

        userStatsByRegionPieChart.setData(pieChartData);
       /* slider.setTranslateX(-176);
        Menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-176);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(false);
                MenuClose.setVisible(true);
            });
        });

        MenuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-176);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e) -> {
                Menu.setVisible(true);
                MenuClose.setVisible(false);
            });
        });*/



    }
    /*public void onShow() {
        // Load the image every time the interface is shown
        loadImage();
        VBox.setMargin(profileimageview, new Insets(10, 18, 0, 18));
    }*/


    public void userinterface(MouseEvent event) throws IOException {
        ap.setVisible(false);
        loadPage("/test/utilisateur-view");

    }


    public void setModifierUserController(ModifierUserController modifierUserController) {
        this.modifierUserController=modifierUserController;
    }
    public void dashboard(MouseEvent event) {
        ap.setVisible(true);
        bp.setCenter(ap);
    }
    public void gestionusers(MouseEvent event) throws IOException {
        ap.setVisible(false);
        loadPage("/test/administrateur-view");
    }
    public void profile(MouseEvent event) throws IOException {
        ap.setVisible(false);
        loadPage("/test/modifierprofile");
    }
    public void interfacequizz(MouseEvent event) throws IOException {
        ap.setVisible(false);
        loadPage("/test/modifierprofile");
    }
    public void setevent() throws IOException {
        System.out.println("workingggggg");
        ap.setVisible(false);
        loadPage("/test/evenement");

    }
    public void loadPage(String page) throws IOException {
        Parent root=null;
        root=FXMLLoader.load(getClass().getResource(page+".fxml"));
        bp.setCenter(root);

    }


    public void butttonhover(MouseEvent event) {
        dashboardbutton.setStyle("-fx-background-color:#1D3557;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
    }

    public void buttondishover(MouseEvent event) {
        dashboardbutton.setStyle("-fx-background-color:transparent;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
    }

    public void butttonhover3(MouseEvent event) {
        buttonfacture.setStyle("-fx-background-color:#1D3557;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
    }
    public void butttonhoverprofile(MouseEvent event) {
        profilebutton.setStyle("-fx-background-color:#1D3557;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
    }

    public void butttonhover2(MouseEvent event) {
        buttonuser.setStyle("-fx-background-color:#1D3557;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
    }

    public void butttonhover4(MouseEvent event) {
        logoutbutton.setStyle("-fx-background-color:#1D3557;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
    }

    public void buttondishover3(MouseEvent event) {
        buttonfacture.setStyle("-fx-background-color:transparent;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
    }

    public void buttondishover4(MouseEvent event) {
        logoutbutton.setStyle("-fx-background-color:transparent;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
    }

    public void buttondishover2(MouseEvent event) {
        buttonuser.setStyle("-fx-background-color:transparent;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
    }
    public void buttondishoverprofile(MouseEvent event) {
        profilebutton.setStyle("-fx-background-color:transparent;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
    }

    public void eventinterface(MouseEvent mouseEvent) throws IOException {
        ap.setVisible(false);
        loadPage("/test/listevenement");
    }

    public void buttonhoverevents(MouseEvent mouseEvent) {
        buttonevents.setStyle("-fx-background-color:#1D3557;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");

    }

    public void buttondishoverevents(MouseEvent mouseEvent) {buttonevents.setStyle("-fx-background-color:transparent;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
    }

    public void quizz(MouseEvent mouseEvent) throws IOException {
        ap.setVisible(false);
        loadPage("/test/QuizHome");
    }

    public void butttonhoverquizz(MouseEvent mouseEvent) {
        buttonquizz.setStyle("-fx-background-color:transparent;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
        System.out.println("hovered");
    }

    public void buttondishoverquizz(MouseEvent mouseEvent) {
        buttonquizz.setStyle("-fx-background-color:transparent;-fx-border-color:white;-fx-border-width: 0px 0px 1px 0px");
        System.out.println("disovered");
    }
}
