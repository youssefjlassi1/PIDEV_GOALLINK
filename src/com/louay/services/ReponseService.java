package com.louay.services;

import com.louay.entities.Reponse;
import com.louay.utils.DatabaseConnection;
import com.louay.utils.RelationObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReponseService {

    private static ReponseService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public ReponseService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static ReponseService getInstance() {
        if (instance == null) {
            instance = new ReponseService();
        }
        return instance;
    }

    public List<Reponse> getAll() {
        List<Reponse> listReponse = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `reponse` AS x RIGHT JOIN `feedback` AS y ON x.id_feedback_id = y.id WHERE x.id_feedback_id = y.id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listReponse.add(new Reponse(
                        resultSet.getInt("id"),
                        new RelationObject(resultSet.getInt("id_feedback_id"), resultSet.getString("y.sujet")),
                        resultSet.getString("texte")

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) reponse : " + exception.getMessage());
        }
        return listReponse;
    }

    public List<Reponse> getByFeedback(int idFeedback) {
        List<Reponse> listReponse = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement(
                    "SELECT * " +
                            "FROM `reponse` AS x RIGHT JOIN `feedback` AS y ON x.id_feedback_id = y.id " +
                            "WHERE x.id_feedback_id = y.id " +
                            "AND x.id_feedback_id = " + idFeedback);

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listReponse.add(new Reponse(
                        resultSet.getInt("id"),
                        new RelationObject(resultSet.getInt("id_feedback_id"), resultSet.getString("y.email")),
                        resultSet.getString("texte")

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) reponse : " + exception.getMessage());
        }
        return listReponse;
    }

    public List<RelationObject> getAllFeedbacks() {
        List<RelationObject> listFeedbacks = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `feedback`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listFeedbacks.add(new RelationObject(resultSet.getInt("id"), resultSet.getString("email")));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) feedbacks : " + exception.getMessage());
        }
        return listFeedbacks;
    }


    public boolean add(Reponse reponse) {


        String request = "INSERT INTO `reponse`(`id_feedback_id`, `texte`) VALUES(?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, reponse.getFeedbackId().getId());
            preparedStatement.setString(2, reponse.getTexte());

            preparedStatement.executeUpdate();
            System.out.println("Reponse added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) reponse : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Reponse reponse) {

        String request = "UPDATE `reponse` SET `id_feedback_id` = ?, `texte` = ? WHERE `id`=" + reponse.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, reponse.getFeedbackId().getId());
            preparedStatement.setString(2, reponse.getTexte());

            preparedStatement.executeUpdate();
            System.out.println("Reponse edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) reponse : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `reponse` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Reponse deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) reponse : " + exception.getMessage());
        }
        return false;
    }
}
