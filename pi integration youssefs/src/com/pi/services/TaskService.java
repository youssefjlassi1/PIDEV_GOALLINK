package com.pi.services;

import com.pi.entities.Project;
import com.pi.entities.Task;
import com.pi.utils.DatabaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class TaskService {

    private static TaskService instance;
    PreparedStatement preparedStatement;
    Connection connection;

    public TaskService() {
        connection = DatabaseConnection.getInstance().getConnection();
    }

    public static TaskService getInstance() {
        if (instance == null) {
            instance = new TaskService();
        }
        return instance;
    }

    public List<Task> getAll() {
        List<Task> listTask = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `task`");

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listTask.add(new Task(
                        resultSet.getInt("id"),
                        resultSet.getInt("project_id"),
                        resultSet.getString("name"),
                        resultSet.getString("status"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getAll) task : " + exception.getMessage());
        }
        return listTask;
    }

    public boolean add(Task task) {
        String request = "INSERT INTO `task`(`project_id`, `name`, `status`, `description`) VALUES(?, ?, ?, ?)";
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, task.getProject_id());
            preparedStatement.setString(2, task.getName());
            preparedStatement.setString(3, task.getStatus());
            preparedStatement.setString(4, task.getDescription());

            preparedStatement.executeUpdate();
            System.out.println("Task added");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (add) task : " + exception.getMessage());
        }
        return false;
    }

    public boolean edit(Task task) {
        String request = "UPDATE `task` SET `project_id` = ?, `name` = ?, `status` = ?, `description` = ? WHERE `id`=" + task.getId();
        try {
            preparedStatement = connection.prepareStatement(request);

            preparedStatement.setInt(1, task.getProject_id());
            preparedStatement.setString(2, task.getName());
            preparedStatement.setString(3, task.getStatus());
            preparedStatement.setString(4, task.getDescription());

            preparedStatement.executeUpdate();
            System.out.println("Task edited");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (edit) task : " + exception.getMessage());
        }
        return false;
    }

    public boolean delete(int id) {
        try {
            preparedStatement = connection.prepareStatement("DELETE FROM `task` WHERE `id`=?");
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            preparedStatement.close();
            System.out.println("Task deleted");
            return true;
        } catch (SQLException exception) {
            System.out.println("Error (delete) task : " + exception.getMessage());
        }
        return false;
    }

    public List<Task> getTasksByProject(Project project) {
        List<Task> listTask = new ArrayList<>();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM `task` WHERE `project_id`=?");
            preparedStatement.setInt(1, project.getId());

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                listTask.add(new Task(
                        resultSet.getInt("id"),
                        resultSet.getInt("project_id"),
                        resultSet.getString("name"),
                        resultSet.getString("status"),
                        resultSet.getString("description")
                ));
            }
        } catch (SQLException exception) {
            System.out.println("Error (getTasksByProject) task : " + exception.getMessage());
        }
        return listTask;
    }
}
