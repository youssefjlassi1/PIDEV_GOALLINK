package com.pi.gui.back.donation;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.pi.entities.Donation;
import com.pi.gui.back.MainWindowController;
import com.pi.services.DonationService;
import com.pi.utils.AlertUtils;
import com.pi.utils.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
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

    public static Donation currentDonation;

    @FXML
    public Text topText;
    @FXML
    public Button addButton;
    @FXML
    public VBox mainVBox;
    @FXML
    public ComboBox<String> sortCB;

    List<Donation> listDonation;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        listDonation = DonationService.getInstance().getAll();
        sortCB.getItems().addAll("Project", "GoalAmount", "RecivedAmount", "CreatedAt", "UpdatedAt");
        displayData();
    }

    void displayData() {
        mainVBox.getChildren().clear();

        Collections.reverse(listDonation);

        if (!listDonation.isEmpty()) {
            for (Donation donation : listDonation) {

                mainVBox.getChildren().add(makeDonationModel(donation));

            }
        } else {
            StackPane stackPane = new StackPane();
            stackPane.setAlignment(Pos.CENTER);
            stackPane.setPrefHeight(200);
            stackPane.getChildren().add(new Text("Aucune donnée"));
            mainVBox.getChildren().add(stackPane);
        }
    }

    public Parent makeDonationModel(
            Donation donation
    ) {
        Parent parent = null;
        try {
            parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(Constants.FXML_BACK_MODEL_DONATION)));

            HBox innerContainer = ((HBox) ((AnchorPane) ((AnchorPane) parent).getChildren().get(0)).getChildren().get(0));
            ((Text) innerContainer.lookup("#projectText")).setText("Project : " + donation.getProject());
            ((Text) innerContainer.lookup("#goalAmountText")).setText("GoalAmount : " + donation.getGoalAmount());
            ((Text) innerContainer.lookup("#recivedAmountText")).setText("RecivedAmount : " + donation.getRecivedAmount());
            ((Text) innerContainer.lookup("#createdAtText")).setText("CreatedAt : " + donation.getCreatedAt());
            ((Text) innerContainer.lookup("#updatedAtText")).setText("UpdatedAt : " + donation.getUpdatedAt());


            ((Button) innerContainer.lookup("#genererPDFButton")).setOnAction((event) -> genererPDF(donation));
            ((Button) innerContainer.lookup("#editButton")).setOnAction((event) -> modifierDonation(donation));
            ((Button) innerContainer.lookup("#deleteButton")).setOnAction((event) -> supprimerDonation(donation));


        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return parent;
    }

    @FXML
    private void ajouterDonation(ActionEvent ignored) {
        currentDonation = null;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_DONATION);
    }

    private void modifierDonation(Donation donation) {
        currentDonation = donation;
        MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_MANAGE_DONATION);
    }

    private void supprimerDonation(Donation donation) {
        currentDonation = null;

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmer la suppression");
        alert.setHeaderText(null);
        alert.setContentText("Etes vous sûr de vouloir supprimer donation ?");
        Optional<ButtonType> action = alert.showAndWait();

        if (action.isPresent()) {
            if (action.get() == ButtonType.OK) {
                if (DonationService.getInstance().delete(donation.getId())) {
                    MainWindowController.getInstance().loadInterface(Constants.FXML_BACK_DISPLAY_ALL_DONATION);
                } else {
                    AlertUtils.makeError("Could not delete donation");
                }
            }
        }
    }

    private void genererPDF(Donation donation) {
        Document document = new Document();
        try {
            PdfWriter writer = PdfWriter.getInstance(document, Files.newOutputStream(Paths.get("reclamation.pdf")));
            document.open();

            com.itextpdf.text.Font font = new com.itextpdf.text.Font();
            font.setSize(20);

            document.add(new Paragraph("- Reclamation -"));

            document.add(new Paragraph("Id : " + donation.getId()));
            document.add(new Paragraph("Project : " + donation.getProject()));
            document.add(new Paragraph("GoalAmount : " + donation.getGoalAmount()));
            document.add(new Paragraph("RecivedAmount : " + donation.getRecivedAmount()));
            document.add(new Paragraph("CreatedAt : " + donation.getCreatedAt()));
            document.add(new Paragraph("UpdatedAt : " + donation.getUpdatedAt()));
            document.newPage();
            document.close();

            writer.close();

            Desktop.getDesktop().open(new File("reclamation.pdf"));
        } catch (DocumentException | IOException e) {
            e.printStackTrace();
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
            row1.createCell(0).setCellValue("Id");
            row1.createCell(1).setCellValue("Project");
            row1.createCell(2).setCellValue("GoalAmount");
            row1.createCell(3).setCellValue("RecivedAmount");
            row1.createCell(4).setCellValue("CreatedAt");
            row1.createCell(5).setCellValue("UpdatedAt");

            int i = 0;
            for (Donation donation : listDonation) {
                i++;
                Row row2 = workSheet.createRow((short) i);
                row2.createCell(0).setCellValue(donation.getId());
                row2.createCell(1).setCellValue(donation.getProject().getName());
                row2.createCell(2).setCellValue(donation.getGoalAmount());
                row2.createCell(3).setCellValue(donation.getRecivedAmount());
                row2.createCell(4).setCellValue(donation.getCreatedAt().toString());
                row2.createCell(5).setCellValue(donation.getUpdatedAt().toString());
            }

            workSheet.autoSizeColumn(0);
            workSheet.autoSizeColumn(1);
            workSheet.autoSizeColumn(2);
            workSheet.autoSizeColumn(3);
            workSheet.autoSizeColumn(4);
            workSheet.autoSizeColumn(5);


            workbook.write(Files.newOutputStream(Paths.get("reclamation.xls")));
            Desktop.getDesktop().open(new File("reclamation.xls"));

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @FXML
    public void sort(ActionEvent ignored) {
        Constants.compareVar = sortCB.getValue();
        Collections.sort(listDonation);
        displayData();
    }

}
