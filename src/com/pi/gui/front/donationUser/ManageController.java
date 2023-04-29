package com.pi.gui.front.donationUser;


import com.pi.MainApp;
import com.pi.entities.Donation;
import com.pi.entities.DonationUser;
import com.pi.gui.front.MainWindowController;
import com.pi.gui.front.donation.ShowAllController;
import com.pi.services.DonationService;
import com.pi.services.DonationUserService;
import com.pi.utils.AlertUtils;
import com.pi.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.net.URL;
import java.time.LocalDate;
import java.util.Properties;
import java.util.ResourceBundle;

public class ManageController implements Initializable {

    @FXML
    public TextField amountTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        topText.setText("Ajouter donation user");
        btnAjout.setText("Ajouter");
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            DonationUser donationUser = new DonationUser(
                    MainApp.session,
                    Float.parseFloat(amountTF.getText()),
                    LocalDate.now()
            );

            if (DonationUserService.getInstance().add(donationUser)) {
                Donation donation = ShowAllController.currentDonation;
                donation.setRecivedAmount(donation.getRecivedAmount() + Float.parseFloat(amountTF.getText()));
                if (DonationService.getInstance().edit(donation)) {

                    try {
                        sendMail(donationUser.getUser().getEmail());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


                    AlertUtils.makeSuccessNotification("DonationUser ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_DONATION);
                } else {
                    AlertUtils.makeError("Error");
                }                MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_DONATION);
            } else {
                AlertUtils.makeError("Error");
            }
        }
    }

    public static void sendMail(String recepient) throws Exception {
        System.out.println("Preparing to send email");
        Properties properties = new Properties();

        //Enable authentication
        properties.put("mail.smtp.auth", "true");
        //Set TLS encryption enabled
        properties.put("mail.smtp.starttls.enable", "true");
        //Set SMTP host
        properties.put("mail.smtp.host", "smtp.gmail.com");
        //Set smtp port
        properties.put("mail.smtp.port", "587");

        //Your gmail address
        String myAccountEmail = "pidev.app.esprit@gmail.com";
        //Your gmail password
        String password = "jkemsuddgeptmcsb";

        //Create a session with account credentials
        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(myAccountEmail, password);
            }
        });

        //Prepare email message
        Message message = prepareMessage(session, myAccountEmail, recepient);

        //Send mail
        if (message != null) {
            Transport.send(message);
            System.out.println("Mail sent successfully");
        } else {
            System.out.println("Error sending the mail");
        }
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Notification");
            String htmlCode = "<h1>Notification</h1> <br/> <h2><b>Donation ajouté avec succes</b></h2>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private boolean controleDeSaisie() {

        if (amountTF.getText().isEmpty()) {
            AlertUtils.makeInformation("amount ne doit pas etre vide");
            return false;
        }

        try {
            Float.parseFloat(amountTF.getText());
        } catch (NumberFormatException ignored) {
            AlertUtils.makeInformation("amount doit etre un réel");
            return false;
        }

        return true;
    }
}