package controller;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.oned.Code128Writer;
import entities.Billet;
import entities.Personne;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.glxn.qrgen.QRCode;
import net.glxn.qrgen.image.ImageType;
import java.io.*;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

public class BilletController implements Initializable {

    @FXML
    private Label dateEvent;

    @FXML
    private Label eventName;

    @FXML
    private Label prix;

    @FXML
    private ImageView qrCode;

    @FXML
    private Label timeEvent;

    @FXML
    private ImageView codeABarre;

    @FXML
    private Label location;

    @FXML
    private Label num;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    public void getElements(Billet b, Personne user) throws WriterException, IOException {
        // TODO  QR CODE
        String code=String.valueOf(b.getEvenement().getNom()+" "+b.getId()+" "+user.getNom());
        ByteArrayOutputStream out_QR = QRCode.from(code).to(ImageType.PNG).withSize(320, 320).stream();
        ByteArrayInputStream in_QR = new ByteArrayInputStream(out_QR.toByteArray());
        Image image_QR = new Image(in_QR);

        // TODO  CODE A BARRE
        String barcodeText = b.getCode()+" "+user.getCin()+" "+b.getDate_validite();
        BitMatrix bitMatrix = new Code128Writer().encode(barcodeText, BarcodeFormat.CODE_128,210, 108);
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(bitMatrix, "png", out);
        InputStream in = new ByteArrayInputStream(out.toByteArray());
        Image image = new Image(in);


        eventName.setText(String.valueOf(b.getEvenement().getNom()));
        String theDate= String.valueOf(b.getEvenement().getDate());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate date = LocalDate.parse(theDate,formatter);
        String dayNum =String.valueOf(date.getDayOfMonth());
        String day = date.getDayOfWeek().toString();
        String month= date.getMonth().getDisplayName(TextStyle.SHORT, Locale.ENGLISH);
        dateEvent.setText(day+" "+dayNum+" "+ month);
        timeEvent.setText("8:00 AM");
        if(b.getPrix()==0)
            prix.setText("FREE");
        else
            prix.setText(String.valueOf((int)b.getPrix()+" DT"));
        qrCode.setImage(image_QR);
        codeABarre.setImage(image);
        location.setText(b.getEvenement().getLocalisation());
        num.setText("Num: "+b.getId()+(int)b.getCode().charAt(0)+b.getEvenement().getId());
        codeABarre.setRotate(90);
    }

}
