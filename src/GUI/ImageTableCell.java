/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;


import javafx.scene.control.TableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import entites.Tournament;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ImageTableCell extends TableCell<Tournament, String> {

    private final ImageView imageView;

    public ImageTableCell() {
        imageView = new ImageView();
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);
        setGraphic(imageView);
    }

    @Override
    protected void updateItem(String imagePath, boolean empty) {
        super.updateItem(imagePath, empty);
        if (empty || imagePath == null) {
            imageView.setImage(null);
        } else {
            try {
                FileInputStream input = new FileInputStream(imagePath);
                Image image = new Image(input);
                imageView.setImage(image);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
