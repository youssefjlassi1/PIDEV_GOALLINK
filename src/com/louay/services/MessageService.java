package com.louay.services;

import com.louay.entities.Message;
import com.louay.utils.DatabaseConnection;
import com.louay.utils.RelationObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MessageService {

    private static MessageService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public MessageService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static MessageService getInstance() {
        if (instance == null) {
            instance = new MessageService();
        }
        return instance;
    }

    public List<Message> getAll() {
        List<Message> listMessage = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `message` AS x " +
                    "RIGHT JOIN `user` AS y ON x.receiver_id = y.id " +
                    "RIGHT JOIN `user` AS z ON x.sender_id = z.id " +
                    "WHERE x.receiver_id = y.id " +
                    "AND x.sender_id = z.id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listMessage.add(new Message(
                        resultSet.getInt("id"),
                        new RelationObject(resultSet.getInt("sender_id"), resultSet.getString("z.email")),
                        new RelationObject(resultSet.getInt("receiver_id"), resultSet.getString("y.email")),
                        resultSet.getString("message")

                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) message : " + exception.getMessage());
        }
        return listMessage;
    }

    public List<RelationObject> getAllUsers() {
        List<RelationObject> listUsers = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `user`");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                listUsers.add(new RelationObject(resultSet.getInt("id"), resultSet.getString("email")));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) users : " + exception.getMessage());
        }
        return listUsers;
    }

    public boolean add(Message message) {

        String request = "INSERT INTO `message`(`sender_id`, `receiver_id`, `message`) VALUES(?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, message.getSender().getId());
            preparedStatement.setInt(2, message.getReceiver().getId());
            preparedStatement.setString(3, message.getMessage());

            preparedStatement.executeUpdate();
            System.out.println("Message added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) message : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Message message) {

        String request = "UPDATE `message` SET `sender_id` = ?, `receiver_id` = ?, `message` = ? WHERE `id`=" + message.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, message.getSender().getId());
            preparedStatement.setInt(2, message.getReceiver().getId());
            preparedStatement.setString(3, message.getMessage());

            preparedStatement.executeUpdate();
            System.out.println("Message edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) message : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `message` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Message deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) message : " + exception.getMessage());
        }
        return false;
    }
}
