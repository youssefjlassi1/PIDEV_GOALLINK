package com.pi.services;

import com.pi.entities.Project;
import com.pi.utils.DatabaseConnection;
import com.pi.utils.RelationObject;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ProjectService {

    private static ProjectService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public ProjectService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static ProjectService getInstance() {
        if (instance == null) {
            instance = new ProjectService();
        }
        return instance;
    }

    public List<Project> getAll() {
        List<Project> listProject = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `project` AS x RIGHT JOIN `user` AS y ON x.owner_id = y.id WHERE x.owner_id = y.id");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listProject.add(new Project(
                        resultSet.getInt("id"),
                        new RelationObject(resultSet.getInt("y.id"), resultSet.getString("y.email")),
                        resultSet.getString("name"),
                        resultSet.getString("theme"),
                        resultSet.getString("status"),
                        resultSet.getString("image"),
                        resultSet.getString("description"),
                        resultSet.getString("location"),
                        LocalDate.parse(String.valueOf(resultSet.getDate("created"))),
                        LocalDate.parse(String.valueOf(resultSet.getDate("updated")))
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) project : " + exception.getMessage());
        }
        return listProject;
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


    public boolean add(Project project) {


        String request = "INSERT INTO `project`(`owner_id`, `name`, `theme`, `status`, `image`, `description`, `location`, `created`, `updated`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, project.getOwner().getId());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setString(3, project.getTheme());
            preparedStatement.setString(4, project.getStatus());
            preparedStatement.setString(5, project.getImage());
            preparedStatement.setString(6, project.getDescription());
            preparedStatement.setString(7, project.getLocation());
            preparedStatement.setDate(8, Date.valueOf(project.getCreated()));
            preparedStatement.setDate(9, Date.valueOf(project.getUpdated()));

            preparedStatement.executeUpdate();
            System.out.println("Project added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) project : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Project project) {

        String request = "UPDATE `project` SET `owner_id` = ?, `name` = ?, `theme` = ?, `status` = ?, `image` = ?, `description` = ?, `location` = ?, `created` = ?, `updated` = ? WHERE `id`=" + project.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, project.getOwner().getId());
            preparedStatement.setString(2, project.getName());
            preparedStatement.setString(3, project.getTheme());
            preparedStatement.setString(4, project.getStatus());
            preparedStatement.setString(5, project.getImage());
            preparedStatement.setString(6, project.getDescription());
            preparedStatement.setString(7, project.getLocation());
            preparedStatement.setDate(8, Date.valueOf(project.getCreated()));
            preparedStatement.setDate(9, Date.valueOf(project.getUpdated()));

            preparedStatement.executeUpdate();
            System.out.println("Project edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) project : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `project` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Project deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) project : " + exception.getMessage());
        }
        return false;
    }
}
