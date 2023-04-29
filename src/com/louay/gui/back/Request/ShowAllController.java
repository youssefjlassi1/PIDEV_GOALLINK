package com.louay.gui.back.Request;

import com.louay.entities.Request;
import com.louay.gui.back.MainWindowController;
import com.louay.services.RequestService;
import com.louay.utils.AlertUtils;
import com.louay.utils.Constants;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
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
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.*;

public class ShowAllController implements Initializable {

    public static Request currentFeedback;

    @FXML
    public Text topText;

    public VBox mainVBox;
    @FXML
    public TextField searchTF;
    @FXML
    public Button exelButton;

    List<Request> listFeedback;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listFeedback = RequestService.getInstance().getAll();

        displayData("");
    }

    void displayData(String searchText) {
        mainVBox.getChildren().clear();

        Collections.reverse(listFeedback);

        if (!listFeedback.isEmpty()) {
            for (Request feedback : listFeedback) {
                if (feedback.getSujet().toLowerCase().startsWith(searchText.toLowerCase())) {
                    mainVBox.getChildren().add(makeFeedbackModel(feedback));
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

    public Parent makeFeedbackModel(Request feedback) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_FEEDBACK)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#userIdText")).setText("User : " + feedback.getUserId());
            ((Text) innerContainer.lookup("#sujetText")).setText("Sujet : " + feedback.getSujet());
            ((Text) innerContainer.lookup("#emailText")).setText("Email : " + feedback.getEmail());
            ((Text) innerContainer.lookup("#descriptionText")).setText("Description : " + feedback.getDescription());
            ((Text) innerContainer.lookup("#dateText")).setText("Date : " + feedback.getDate());

            String status = feedback.getStatus() == 0 ? "En cours" : feedback.getStatus() == 1 ? "Approuvé" : "Refusé";

            ((Text) innerContainer.lookup("#statusText")).setText("Status : " + status);

            ((Button) innerContainer.lookup("#approveButton")).setOnAction((event) -> approve(feedback));
            ((Button) innerContainer.lookup("#refuseButton")).setOnAction((event) -> refuse(feedback));

        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }


    @FXML
    private void search(KeyEvent event) {
        displayData(searchTF.getText());
    }

    private void approve(Request feedback) {
        currentFeedback = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir approuver ce feedback ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                feedback.setStatus(1);
                if (RequestService.getInstance().edit(feedback)) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_FEEDBACK);
                } else {
                    AlertUtils.makeError("Could not update feedback");
                }
            }
        }
    }

    private void refuse(Request feedback) {
        currentFeedback = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir approuver ce feedback ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                feedback.setStatus(-1);
                if (RequestService.getInstance().edit(feedback)) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_FEEDBACK);
                } else {
                    AlertUtils.makeError("Could not update feedback");
                }
            }
        }
    }


    @FXML
    public void makeExel() {
        System.out.println("making exel");
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

            row1.createCell(0).setCellValue("Sujet");
            row1.createCell(1).setCellValue("Description");
            row1.createCell(2).setCellValue("Date");
            row1.createCell(3).setCellValue("Status");
            row1.createCell(4).setCellValue("Email");
            row1.createCell(6).setCellValue("User");

            int i = 0;
            for (Request feedback : listFeedback) {
                i++;
                Row row2 = workSheet.createRow((short) i);
                row2.createCell(0).setCellValue(feedback.getSujet());
                row2.createCell(1).setCellValue(feedback.getDescription());
                row2.createCell(2).setCellValue(feedback.getDate().toString());
                row2.createCell(3).setCellValue(feedback.getStatus());
                row2.createCell(4).setCellValue(feedback.getEmail());
                row2.createCell(6).setCellValue(feedback.getUserId().getName());
            }

            workSheet.autoSizeColumn(0);
            workSheet.autoSizeColumn(1);
            workSheet.autoSizeColumn(2);
            workSheet.autoSizeColumn(3);
            workSheet.autoSizeColumn(4);
            workSheet.autoSizeColumn(6);

            workbook.write(Files.newOutputStream(Paths.get("feedback.xls")));
            Desktop.getDesktop().open(new File("feedback.xls"));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
