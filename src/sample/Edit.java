package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.*;
import java.util.ResourceBundle;
import static sample.AddPerson.bigFirstLetter;
import static sample.AddPerson.isPeselCorrect;
import static sample.Controller.*;
import static sample.Main.prop;
import static sample.AddPerson.*;

public class Edit implements Initializable {

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private TextField textField3;

    @FXML
    private TextField textField4;

    @FXML
    private ImageView imageView3;

    @FXML
    private Button button;

    private File file;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textField1.setText(markedPerson.get(1));
        textField2.setText(markedPerson.get(2));
        textField3.setText(markedPerson.get(3));
        textField4.setText(markedPerson.get(5));
        try {
            File file = new File(prop.getProperty("absolutePath") + textField4.getText());
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            imageView3.setImage(image);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public void saveData() throws IOException {
        String s = "";
        String firstName = textField1.getText();
        String lastName = textField2.getText();
        String pesel = textField3.getText();
        Path target = Paths.get(prop.getProperty("absolutePath") + nameOfPhoto);
        InputStream source = new FileInputStream(file);
        try {
            Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
        }catch(DirectoryNotEmptyException e) {
            System.out.println("ALe fejl!");
        }
        firstName = bigFirstLetter(firstName);
        lastName = bigFirstLetter(lastName);

        if (isPeselCorrect(pesel)) {
            markedPerson.set(1, firstName);
            markedPerson.set(2, lastName);
            markedPerson.set(3, pesel);
        }
        for (int i = 0; i < markedPerson.size(); i++) {
            if (i != markedPerson.size() - 1) {
                s += markedPerson.get(i) + ";";
            } else {
                s += markedPerson.get(i);
            }
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).startsWith(markedPerson.get(0))) {
                data.remove(i);
            }
        }
        data.add(s);
        for (int i = 0; i < observableList.size(); i++) {
            if (observableList.get(i).getId().equals(markedPerson.get(0))) {
                observableList.remove(i);
            }
        }
        observableList.add(new Osoba(markedPerson.get(0), markedPerson.get(1), markedPerson.get(2), markedPerson.get(3), markedPerson.get(4), markedPerson.get(5)));
        updateFile();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
    @FXML
    private String takeNewImage() throws IOException {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Zmień plik z grafiką");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );
        file = fileChooser.showOpenDialog(new Stage());
        FileInputStream fileInputStream = new FileInputStream(file);
        Image image = new Image(fileInputStream);
        imageView3.setImage(image);
        return nameOfPhoto;
    }
}

