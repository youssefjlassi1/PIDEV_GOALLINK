package com.pi.gui.back.project;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.pi.entities.Project;
import com.pi.gui.back.MainWindowController;
import com.pi.services.ProjectService;
import com.pi.utils.AlertUtils;
import com.pi.utils.Constants;
import com.restfb.BinaryAttachment;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ShowAllController implements Initializable {

    public static Project currentProject;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public TextField searchTF;

    List<Project> listProject;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listProject = ProjectService.getInstance().getAll();

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listProject);

        if (!listProject.isEmpty()) {
            for (Project project : listProject) {
                if (project.getName().toLowerCase().startsWith(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeProjectModel(project));
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

    public Parent makeProjectModel(
            Project project
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_PROJECT)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#ownerText")).setText("User : " + project.getOwner());
            ((Text) innerContainer.lookup("#nameText")).setText("Name : " + project.getName());
            ((Text) innerContainer.lookup("#themeText")).setText("Theme : " + project.getTheme());
            ((Text) innerContainer.lookup("#statusText")).setText("Status : " + project.getStatus());

            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + project.getDescription());
            ((Text) innerContainer.lookup("#locationText")).setText("Location : " + project.getLocation());
            ((Text) innerContainer.lookup("#createdText")).setText("Created : " + project.getCreated());
            ((Text) innerContainer.lookup("#updatedText")).setText("Updated : " + project.getUpdated());
            Path selectedImagePath = FileSystems.getDefault().getPath(project.getImage());
            if (selectedImagePath.toFile().exists()) {
                ((ImageView) innerContainer.lookup("#imageIV")).setImage(new Image(selectedImagePath.toUri().toString()));
            }

            
            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierProject(project));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerProject(project));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterProject(ActionEvent ignored) {
        currentProject = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_PROJECT);
    }

    private void modifierProject(Project project) {
        currentProject = project;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_PROJECT);
    }

    private void supprimerProject(Project project) {
        currentProject = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer project ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (ProjectService.getInstance().delete(project.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_PROJECT);
                } else {
                    AlertUtils.makeError("Could not delete project");
                }
            }
        }
    }


       
    

    @FXML
    public void genererExcel(ActionEvent ignored) {
        HSSFWorkbook workbook = new HSSFWorkbook();
        try {
            FileChooser chooser = new FileChooser();
            // Set extension filter
            FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel Files(.xls)", ".xls");
            chooser.getExtensionFilters().add(filter);

            HSSFSheet workSheet = workbook.createSheet("sheet 0");
            workSheet.setColumnWidth(1, 25);

            HSSFFont fontBold = workbook.createFont();
            fontBold.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
            HSSFCellStyle styleBold = workbook.createCellStyle();
            styleBold.setFont(fontBold);

            Row row1 = workSheet.createRow((short) 0);
            workSheet.autoSizeColumn(7);
            row1.createCell(0).setCellValue("Id");
            row1.createCell(1).setCellValue("Owner");
            row1.createCell(2).setCellValue("Name");
            row1.createCell(3).setCellValue("Theme");
            row1.createCell(4).setCellValue("Status");
            row1.createCell(5).setCellValue("Image");
            row1.createCell(6).setCellValue("Description");
            row1.createCell(7).setCellValue("Location");
            row1.createCell(8).setCellValue("Created");
            row1.createCell(9).setCellValue("Updated");

            int i = 0;
            for (Project project : listProject) {
                i++;
                Row row2 = workSheet.createRow((short) i);
                row2.createCell(0).setCellValue(project.getId());
                row2.createCell(1).setCellValue(project.getOwner().getName());
                row2.createCell(2).setCellValue(project.getName());
                row2.createCell(3).setCellValue(project.getTheme());
                row2.createCell(4).setCellValue(project.getStatus());
                row2.createCell(5).setCellValue(project.getImage());
                row2.createCell(6).setCellValue(project.getDescription());
                row2.createCell(7).setCellValue(project.getLocation());
                row2.createCell(8).setCellValue(project.getCreated().toString());
                row2.createCell(9).setCellValue(project.getUpdated().toString());

            }

            workbook.write(Files.newOutputStream(Paths.get("reclamation.xls")));
            Desktop.getDesktop().open(new File("reclamation.xls"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void search(KeyEvent ignored) {
        displayData(searchTF.getText());
    }

}
