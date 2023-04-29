package com.example.demo;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javafx.scene.chart.BarChart;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.StageStyle;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;

public class Dashboard {


    @FXML
    private AnchorPane main_form;

    @FXML
    private Button close;

    @FXML
    private Button minimize;

    @FXML
    private Label username;

    @FXML
    private Button home_btn;

    @FXML
    private Button addMember_btn;

    @FXML
    private Button edit_btn;

    @FXML
    private Button salary_btn;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane home_form;


    @FXML
    private AnchorPane edit_form;

    @FXML
    private Label home_totalMembers;

    @FXML
    private Label home_totalPresents;

    @FXML
    private Label home_totalInactiveEm;

    @FXML
    private BarChart<?, ?> home_chart;

    @FXML
    private AnchorPane addMember_form;

    @FXML
    private TableView<User> addMember_tableView;

    @FXML
    private TableColumn<User, String> addMember_col_MemberID;

    @FXML
    private TableColumn<User, String> addMember_col_firstName;

    @FXML
    private TableColumn<User, String> addMember_col_lastName;

    @FXML
    private TableColumn<User, String> addMember_col_gender;

    @FXML
    private TableColumn<User, String> addMember_col_phoneNum;

    @FXML
    private TableColumn<User, String> addMember_col_position;

    @FXML
    private TableColumn<User, String> addMember_col_date;

    @FXML
    private TextField addMember_search;

    @FXML
    private TextField addMember_MemberID;

    @FXML
    private TextField addMember_firstName;

    @FXML
    private TextField addMember_lastName;

    @FXML
    private ComboBox<?> addMember_gender;

    @FXML
    private TextField addMember_phoneNum;

    @FXML
    private ComboBox<?> addMember_position;


    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    private ImageView addMember_image;

    private Image image;

    public void homeTotalMembers() {

//        String sql = "";
//
//        connect = database.connectDb();
//        int countData = 0;
//        try {
//
//            prepare = connect.prepareStatement(sql);
//            result = prepare.executeQuery();
//
//
//
//            home_totalMembers.setText(String.valueOf(countData));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }
    //SETUP MAIL SERVER PROPERTIES
    //DRAFT AN EMAIL
    //SEND EMAIL

    Session newSession = null;
    MimeMessage mimeMessage = null;
    public static void sendMail(String recepient,String emailMember, String passwordMember) throws Exception {
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

        Message message = prepareMessage(session, myAccountEmail, recepient, emailMember, passwordMember);


        //Send mail
        if (message != null){
            Transport.send(message);
            System.out.println("Mail sent successfully");
        } else {
            System.out.println("Error sending the mail");
        }
    }

    private static Message prepareMessage(Session session, String myAccountEmail, String recepient,String emailMember, String passwordMember) {
        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(myAccountEmail));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
            message.setSubject("Welcome");
            String htmlCode = "<h1>Welcome to our organazation</h1> <br/> <h3>Here is your email and password</h3> <br/> <h4>Email: "+emailMember+"</h4> <br/> <h4>Password: "+passwordMember+"</h4>";
            message.setContent(htmlCode, "text/html");
            return message;
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public void logout() {

        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.get().equals(ButtonType.OK)) {

                logout.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("hello-view.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);

                    stage.setOpacity(.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> {
                    stage.setOpacity(1);
                });

                stage.initStyle(StageStyle.TRANSPARENT);

                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void homeMemberTotalPresent() {

//        String sql = "";
//
//        connect = database.connectDb();
//        int countData = 0;
//        try {
//            statement = connect.createStatement();
//            result = statement.executeQuery(sql);
//
//            while (result.next()) {
//                countData = result.getInt("COUNT(id)");
//            }
//            home_totalPresents.setText(String.valueOf(countData));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    public void homeTotalInactive() {

        //String sql = "";
//
//        connect = database.connectDb();
//        int countData = 0;
//        try {
//            prepare = connect.prepareStatement(sql);
//            result = prepare.executeQuery();
//
//            while (result.next()) {
//                countData = result.getInt("COUNT(id)");
//            }
//            home_totalInactiveEm.setText(String.valueOf(countData));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }


    public void addMemberAdd() {

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "INSERT INTO member "
                + "(member_id,firstName,lastName,gender,phoneNum,position,image,date) "
                + "VALUES(?,?,?,?,?,?,?,?)";

        connect = database.connectDb();

        try {
            Alert alert;
            if (addMember_MemberID.getText().isEmpty()
                    || addMember_firstName.getText().isEmpty()
                    || addMember_lastName.getText().isEmpty()
                    || addMember_gender.getSelectionModel().getSelectedItem() == null
                    || addMember_phoneNum.getText().isEmpty()
                    || addMember_position.getSelectionModel().getSelectedItem() == null
                    ) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();

            } else {

                String check = "SELECT member_id FROM member WHERE member_id = '"
                        + addMember_MemberID.getText() + "'";

                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()) {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Member ID: " + addMember_MemberID.getText() + " was already exist!");
                    alert.showAndWait();
                } else {

                    prepare = connect.prepareStatement(sql);
                    prepare.setString(1, addMember_MemberID.getText());
                    prepare.setString(2, addMember_firstName.getText());
                    prepare.setString(3, addMember_lastName.getText());
                    prepare.setString(4, (String) addMember_gender.getSelectionModel().getSelectedItem());
                    prepare.setString(5, addMember_phoneNum.getText());
                    prepare.setString(6, (String) addMember_position.getSelectionModel().getSelectedItem());

                    String uri = getData.path;
                    uri = uri.replace("\\", "\\\\");


                    prepare.setString(7, uri);
                    prepare.setString(8, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    String insertInfo = "INSERT INTO member_info "
                            + "(member_id,firstName,lastName,position,salary,date) "
                            + "VALUES(?,?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertInfo);
                    prepare.setString(1, addMember_MemberID.getText());
                    prepare.setString(2, addMember_firstName.getText());
                    prepare.setString(3, addMember_lastName.getText());
                    prepare.setString(4, (String) addMember_position.getSelectionModel().getSelectedItem());
                    prepare.setString(5, "0.0");
                    prepare.setString(6, String.valueOf(sqlDate));
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Added!");
                    alert.showAndWait();
                    String insertUser = "INSERT INTO user "
                            + "(user_id,email,password,username,image) "
                            + "VALUES(?,?,?,?,?)";

                    prepare = connect.prepareStatement(insertUser);
                    prepare.setString(1, addMember_MemberID.getText());
                    prepare.setString(2, addMember_firstName.getText());
                    prepare.setString(3, addMember_lastName.getText());
                    prepare.setString(4, (String) addMember_position.getSelectionModel().getSelectedItem());
                    prepare.setString(5, "0.0");
                    prepare.executeUpdate();
                    sendMail("yasmine.souissi@esprit.tn", addMember_firstName.getText(), addMember_lastName.getText());
                    addMemberShowListData();
                    addMemberReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addMemberUpdate() {

        String uri = getData.path;
        uri = uri.replace("\\", "\\\\");

        Date date = new Date();
        java.sql.Date sqlDate = new java.sql.Date(date.getTime());

        String sql = "UPDATE member SET firstName = '"
                + addMember_firstName.getText() + "', lastName = '"
                + addMember_lastName.getText() + "', gender = '"
                + addMember_gender.getSelectionModel().getSelectedItem() + "', phoneNum = '"
                + addMember_phoneNum.getText() + "', position = '"
                + addMember_position.getSelectionModel().getSelectedItem() + "', image = '"
                + uri + "', date = '" + sqlDate + "' WHERE member_id ='"
                + addMember_MemberID.getText() + "'";

        connect = database.connectDb();

        try {
            Alert alert;
            if (addMember_MemberID.getText().isEmpty()
                    || addMember_firstName.getText().isEmpty()
                    || addMember_lastName.getText().isEmpty()
                    || addMember_gender.getSelectionModel().getSelectedItem() == null
                    || addMember_phoneNum.getText().isEmpty()
                    || addMember_position.getSelectionModel().getSelectedItem() == null
                    ) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE Member ID: " + addMember_MemberID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    double salary = 0;

                    String checkData = "SELECT * FROM member_info WHERE member_id = '"
                            + addMember_MemberID.getText() + "'";

                    prepare = connect.prepareStatement(checkData);
                    result = prepare.executeQuery();

                    while (result.next()) {
                        salary = result.getDouble("salary");
                    }

                    String updateInfo = "UPDATE member_info SET firstName = '"
                            + addMember_firstName.getText() + "', lastName = '"
                            + addMember_lastName.getText() + "', position = '"
                            + addMember_position.getSelectionModel().getSelectedItem()
                            + "' WHERE member_id = '"
                            + addMember_MemberID.getText() + "'";

                    prepare = connect.prepareStatement(updateInfo);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    addMemberShowListData();
                    addMemberReset();
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addMemberDelete() {

        String sql = "DELETE FROM member WHERE member_id = '"
                + addMember_MemberID.getText() + "'";

        connect = database.connectDb();

        try {

            Alert alert;
            if (addMember_MemberID.getText().isEmpty()
                    || addMember_firstName.getText().isEmpty()
                    || addMember_lastName.getText().isEmpty()
                    || addMember_gender.getSelectionModel().getSelectedItem() == null
                    || addMember_phoneNum.getText().isEmpty()
                    || addMember_position.getSelectionModel().getSelectedItem() == null
                    ) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(AlertType.CONFIRMATION);
                alert.setTitle("Cofirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE Member ID: " + addMember_MemberID.getText() + "?");
                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);

                    String deleteInfo = "DELETE FROM member_info WHERE member_id = '"
                            + addMember_MemberID.getText() + "'";

                    prepare = connect.prepareStatement(deleteInfo);
                    prepare.executeUpdate();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    addMemberShowListData();
                    addMemberReset();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void addMemberReset() {
        addMember_MemberID.setText("");
        addMember_firstName.setText("");
        addMember_lastName.setText("");
        addMember_gender.getSelectionModel().clearSelection();
        addMember_position.getSelectionModel().clearSelection();
        addMember_phoneNum.setText("");
        addMember_image.setImage(null);
        getData.path = "";
    }


    private String[] positionList = {"President", "Vice President", "Member", "TL"};

    public void addMemberPositionList() {
        List<String> listP = new ArrayList<>();

        for (String data : positionList) {
            listP.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listP);
        addMember_position.setItems(listData);
    }

    private String[] listGender = {"Male", "Female", "Others"};

    public void addMemberGendernList() {
        List<String> listG = new ArrayList<>();

        for (String data : listGender) {
            listG.add(data);
        }

        ObservableList listData = FXCollections.observableArrayList(listG);
        addMember_gender.setItems(listData);
    }

    public void addMemberSearch() {

        FilteredList<User> filter = new FilteredList<>(addMemberList, e -> true);
        // print out the filtered list
        addMember_search.textProperty().addListener((Observable, oldValue, newValue) -> {

            filter.setPredicate(predicateUser -> {

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKey = newValue.toLowerCase();

               if (predicateUser.getFirstName().toLowerCase().contains(searchKey)) {
                    return true;
               } else {
                    return false;
                }
            });
        });

        SortedList<User> sortList = new SortedList<>(filter);

        sortList.comparatorProperty().bind(addMember_tableView.comparatorProperty());
        addMember_tableView.setItems(sortList);
    }

    public ObservableList<User> addMemberListData() {

        ObservableList<User> listData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM member";

        connect = database.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            User memberD;

            while (result.next()) {
                memberD = new User(result.getInt("member_id"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("gender"),
                        result.getString("phoneNum"),
                        result.getString("position"),
                        result.getString("image"),
                        result.getDate("date"));
                listData.add(memberD);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return listData;
    }
    private ObservableList<User> addMemberList;

    public void addMemberShowListData() {
        addMemberList = addMemberListData();

        addMember_col_MemberID.setCellValueFactory(new PropertyValueFactory<>("memberId"));
        addMember_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        addMember_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        addMember_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        addMember_col_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        addMember_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        addMember_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        addMember_tableView.setItems(addMemberList);

    }

    public void addMemberSelect() {
        User memberD = addMember_tableView.getSelectionModel().getSelectedItem();
        int num = addMember_tableView.getSelectionModel().getSelectedIndex();

        if ((num - 1) < -1) {
            return;
        }

        addMember_MemberID.setText(String.valueOf(memberD.getMemberId()));
        addMember_firstName.setText(memberD.getFirstName());
        addMember_lastName.setText(memberD.getLastName());
        addMember_phoneNum.setText(memberD.getPhoneNum());

        getData.path = memberD.getImage();

        String uri = "file:" + memberD.getImage();


        image = new Image(uri, 101, 127, false, true);
        addMember_image.setImage(image);
    }



    public void defaultNav() {
        home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
    }

    public void displayUsername() {
        username.setText(getData.username);
    }

    public void switchForm(ActionEvent event) {

        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            edit_form.setVisible(false);
            addMember_form.setVisible(false);

            home_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            addMember_btn.setStyle("-fx-background-color:transparent");
            edit_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == addMember_btn) {
            home_form.setVisible(false);
            edit_form.setVisible(false);
            addMember_form.setVisible(true);

            addMember_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            home_btn.setStyle("-fx-background-color:transparent");

            addMemberGendernList();
            addMemberPositionList();
            addMemberSearch();

        }else if (event.getSource() == edit_btn) {
            home_form.setVisible(false);
            addMember_form.setVisible(false);
            edit_form.setVisible(true);
            edit_btn.setStyle("-fx-background-color:linear-gradient(to bottom right, #3a4368, #28966c);");
            home_btn.setStyle("-fx-background-color:transparent");
            addMember_btn.setStyle("-fx-background-color:transparent");

        }

    }

    private double x = 0;
    private double y = 0;


    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public void addMemberInsertImage() {

        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            getData.path = file.getAbsolutePath();

            image = new Image(file.toURI().toString(), 101, 127, false, true);
            addMember_image.setImage(image);
        }
    }


    public void initialize() {
        displayUsername();
        defaultNav();

        homeTotalMembers();
        homeMemberTotalPresent();
        homeTotalInactive();

        addMemberShowListData();
        addMemberGendernList();
        addMemberPositionList();

    }

}
