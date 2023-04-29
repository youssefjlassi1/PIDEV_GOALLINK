package com.pi.services;

import com.pi.entities.Donation;
import com.pi.entities.Project;
import com.pi.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DonationService {

    private static DonationService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public DonationService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static DonationService getInstance() {
        if (instance == null) {
            instance = new DonationService();
        }
        return instance;
    }

    public List<Donation> getAll() {
        List<Donation> listDonation = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `donation` AS x RIGHT JOIN `project` AS y ON x.project_id = y.id WHERE x.project_id = y.id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listDonation.add(new Donation(
                        resultSet.getInt("id"),
                        new Project(
                                resultSet.getInt("y.id"),
                                null,
                                resultSet.getString("y.name"),
                                resultSet.getString("y.theme"),
                                resultSet.getString("y.status"),
                                resultSet.getString("y.image"),
                                resultSet.getString("y.description"),
                                resultSet.getString("y.location"),
                                LocalDate.parse(String.valueOf(resultSet.getDate("y.created"))),
                                LocalDate.parse(String.valueOf(resultSet.getDate("y.updated")))
                        ),
                        resultSet.getFloat("goal_amount"),
                        resultSet.getFloat("recived_amount"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("created_at"))),
                        LocalDate.parse(String.valueOf(resultSet.getDate("updated_at")))

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) donation : " + exception.getMessage());
        }
        return listDonation;
    }

    public boolean add(Donation donation) {


        String request = "INSERT INTO `donation`(`project_id`, `goal_amount`, `recived_amount`, `created_at`, `updated_at`) VALUES(?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, donation.getProject().getId());
            preparedStatement.setFloat(2, donation.getGoalAmount());
            preparedStatement.setFloat(3, donation.getRecivedAmount());
            preparedStatement.setDate(4, Date.valueOf(donation.getCreatedAt()));
            preparedStatement.setDate(5, Date.valueOf(donation.getUpdatedAt()));

            preparedStatement.executeUpdate();
            System.out.println("Donation added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) donation : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Donation donation) {

        String request = "UPDATE `donation` SET `project_id` = ?, `goal_amount` = ?, `recived_amount` = ?, `created_at` = ?, `updated_at` = ? WHERE `id`=" + donation.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, donation.getProject().getId());
            preparedStatement.setFloat(2, donation.getGoalAmount());
            preparedStatement.setFloat(3, donation.getRecivedAmount());
            preparedStatement.setDate(4, Date.valueOf(donation.getCreatedAt()));
            preparedStatement.setDate(5, Date.valueOf(donation.getUpdatedAt()));

            preparedStatement.executeUpdate();
            System.out.println("Donation edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) donation : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `donation` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Donation deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) donation : " + exception.getMessage());
        }
        return false;
    }
  public boolean save(Donation donation) {
    // Save the donation
    // ...
    
    // Return true if the donation was saved successfully
    return true;
}

}
