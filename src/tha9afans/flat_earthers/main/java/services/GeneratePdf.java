package services;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import entities.Facture;

import javax.swing.*;
import java.io.FileOutputStream;
import java.io.IOException;

public class GeneratePdf {
    public void generate(Facture facture, String filename) throws IOException {
        // Create PDF document
        Document document = new Document();
        try {
            // Create a new file with the facture reference as the file name
            PdfWriter.getInstance(document, new FileOutputStream( filename));
            //PdfWriter.getInstance(document, new FileOutputStream("src/tha9afans/flat_earthers/main/java/files/" + filename));
            document.open();
            // Create a Paragraph for the selected facture
            String text = "Référence: " + facture.getRefrancefacture()
                    + "\nNom: " + facture.getCommande().getDateCommande()
                    + "\nPrénom: " + facture.getCommande().getPersonne().getNom()
                    + "\nEmail: " + facture.getCommande().getPersonne().getEmail()
                    + "\nAdresse: " + facture.getCommande().getPersonne().getAdresse()
                    + "\nTéléphone: " + facture.getCommande().getPersonne().getTelephone()
                    + "\nDate: " + facture.getDatefacture()
                    + "\nProduit: " + facture.getCommande().getProduit().getNom()
                    + "\nPrix: " + facture.getCommande().getProduit().getPrix()
                    + "\n\n";
            Paragraph paragraph = new Paragraph(text);
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            // Close the Document
            document.close();
        } catch (Exception e) {
            // Display an error message
            JOptionPane.showMessageDialog(null, "An error occurred while creating the facture: " + e.getMessage());
        }
    }

}
