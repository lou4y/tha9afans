package services;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Attachments;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.sendgrid.helpers.mail.objects.Personalization;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;


public class SendGrid extends ServicePersonne {

    public void Sendgrid(String to, String body ) throws IOException {

        final String apiKey = "";//api key
        Email from = new Email("marwen.hammami@myu.universitecentrale.tn");
        String subject = "Order";
        Email toEmail = new Email(to);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail(from, subject, toEmail, content);

        com.sendgrid.SendGrid sg = new com.sendgrid.SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }


    public void sendWithAttachment(String to, String subject, String body, String filename) throws IOException {
        final String apiKey = "";//api key
        Email from = new Email("marwen.hammami@myu.universitecentrale.tn"); // Replace with your email address

        Email toEmail = new Email(to);
        Content content = new Content("text/plain", body);
        Mail mail = new Mail();
        mail.setFrom(from);

        Personalization personalization = new Personalization();
        personalization.addTo(toEmail);
        personalization.setSubject(subject);
        mail.addPersonalization(personalization);

        Path path = Paths.get(filename);
        byte[] pdfBytes = Files.readAllBytes(path);
        String base64EncodedPdf = Base64.getEncoder().encodeToString(pdfBytes);

        Attachments attachments = new Attachments();
        attachments.setContent(base64EncodedPdf);
        attachments.setType("application/pdf");
        attachments.setFilename(filename);
        attachments.setDisposition("attachment");
        mail.addAttachments(attachments);

        mail.addContent(content);

        com.sendgrid.SendGrid sg = new com.sendgrid.SendGrid(apiKey);
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());
        } catch (IOException ex) {
            throw ex;
        }
    }

}
