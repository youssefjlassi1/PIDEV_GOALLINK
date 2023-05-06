package com.pi.gui.front.task;

import com.pi.gui.front.project.*;
import com.pi.entities.Donation;
import com.pi.entities.Project;
import com.pi.entities.Task;
import com.pi.gui.front.MainWindowController;
import com.pi.services.DonationService;
import com.pi.services.ProjectService;
import com.pi.services.TaskService;
import com.pi.utils.Constants;
import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;

public class ShowAllController implements Initializable {

    public static Task currentTask;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public TextField searchTF;

    List<Task> listProject;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listProject = TaskService.getInstance().getAll();

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listProject);

        if (!listProject.isEmpty()) {
            for (Task project : listProject) {
                if (project.getName().toLowerCase().startsWith(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeTaskModel(project));
                }

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeTaskModel(
            Task project
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_FRONT_MODEL_TASK)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
        //    ((Text) innerContainer.lookup("#ownerText")).setText("User : " + project.getOwner());
            ((Text) innerContainer.lookup("#nameText")).setText("Name : " + project.getName());
           // ((Text) innerContainer.lookup("#themeText")).setText("Theme : " + project.getTheme());
            ((Text) innerContainer.lookup("#statusText")).setText("Status : " + project.getStatus());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + project.getDescription());
            //((Text) innerContainer.lookup("#locationText")).setText("Location : " + project.getLocation());
            //((Text) innerContainer.lookup("#createdText")).setText("Created : " + project.getCre());
           // ((Text) innerContainer.lookup("#updatedText")).setText("Updated : " + project.getUpdated());

          

        //    ((Text) innerContainer.lookup("#donationsText")).setText("Donations amount : " + totalAmount);
         //   Path selectedImagePath = FileSystems.getDefault().getPath(project.getImage());
           

 


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

 


    @FXML
    private void search(KeyEvent ignored) {
        displayData(searchTF.getText());
    }
    


}
