package com.pi.services;

import com.pi.entities.DonationUser;
import com.pi.entities.User;
import com.pi.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DonationUserService {

    private static DonationUserService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public DonationUserService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static DonationUserService getInstance() {
        if (instance == null) {
            instance = new DonationUserService();
        }
        return instance;
    }

    public List<DonationUser> getAll() {
        List<DonationUser> listDonationUser = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `donation_user` AS x RIGHT JOIN `user` AS y ON x.id_user = y.id WHERE x.id_user = y.id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listDonationUser.add(new DonationUser(
                        resultSet.getInt("id"),
                        new User(resultSet.getInt("y.id"), resultSet.getString("y.email")),
                        resultSet.getFloat("amount"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("date")))

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) donationUser : " + exception.getMessage());
        }
        return listDonationUser;
    }

    public List<User> getAllUsers() {
        List<User> listUsers = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `user`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listUsers.add(new User(resultSet.getInt("id"), resultSet.getString("email")));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) users : " + exception.getMessage());
        }
        return listUsers;
    }


    public boolean add(DonationUser donationUser) {


        String request = "INSERT INTO `donation_user`(`id_user`, `amount`, `date`) VALUES(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, donationUser.getUser().getId());
            preparedStatement.setFloat(2, donationUser.getAmount());
            preparedStatement.setDate(3, Date.valueOf(donationUser.getDate()));

            preparedStatement.executeUpdate();
            System.out.println("DonationUser added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) donationUser : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(DonationUser donationUser) {

        String request = "UPDATE `donation_user` SET `id_user` = ?, `amount` = ?, `date` = ? WHERE `id`=" + donationUser.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, donationUser.getUser().getId());
            preparedStatement.setFloat(2, donationUser.getAmount());
            preparedStatement.setDate(3, Date.valueOf(donationUser.getDate()));

            preparedStatement.executeUpdate();
            System.out.println("DonationUser edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) donationUser : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `donation_user` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("DonationUser deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) donationUser : " + exception.getMessage());
        }
        return false;
    }
}
