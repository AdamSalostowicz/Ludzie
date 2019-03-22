package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.Controller.s;

public class Zoom implements Initializable {

    @FXML
    private ImageView imageView1;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Image image1 = null;
        try {
            image1 = new Image(new FileInputStream(s.substring(5)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView1.setImage(image1);
    }
}
