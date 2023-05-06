package com.pi.gui.front.task;


import com.pi.MainApp;
import com.pi.entities.Donation;
import com.pi.entities.Task;
import com.pi.gui.front.MainWindowController;
import static com.pi.gui.front.project.ShowAllController.currentProject;
import com.pi.gui.front.task.ShowAllController;
import com.pi.services.DonationService;
import com.pi.services.TaskService;
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
import javafx.scene.control.DatePicker;

public class ManageController implements Initializable {

    public TextField amountTF;
    @FXML
    public Button btnAjout;
    @FXML
    public Text topText;
    @FXML
    private TextField nameTF;
    @FXML
    private TextField statusTF;
    @FXML
    private TextField descriptionTF;
    @FXML
    private DatePicker createdDP;
    @FXML
    private DatePicker updatedDP;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        topText.setText("Ajouter Task");
        btnAjout.setText("Ajouter");
    }

    @FXML
    private void manage(ActionEvent ignored) {

        if (controleDeSaisie()) {

            Task donationUser = new Task(currentProject.getId(),nameTF.getText(),statusTF.getText(),descriptionTF.getText());
                   
          

            if (TaskService.getInstance().add(donationUser)) {
                Task donation = ShowAllController.currentTask;
              
                if (TaskService.getInstance().edit(donation)) {

                    try {
                   //     sendMail(donationUser.getUser().getEmail());
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }


                    AlertUtils.makeSuccessNotification("Task ajouté avec succés");
                    MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_TASK);
                } else {
                    AlertUtils.makeError("Error");
                }                MainWindowController.getInstance().loadInterface(Constants.FXML_FRONT_DISPLAY_ALL_TASK);
            } else {
                AlertUtils.makeError("Error");
            }
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

        if (nameTF.getText().isEmpty()) {
            AlertUtils.makeInformation("name ne doit pas etre vide");
            return false;
        }

       

        return true;
    }
}