package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sample.AddPerson.bigFirstLetter;
import static sample.AddPerson.isPeselCorrect;
import static sample.Controller.*;

public class Edit implements Initializable {

    @FXML
    private TextField textField1;

    @FXML
    private TextField textField2;

    @FXML
    private TextField textField3;

    @FXML
    private Button button;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        textField1.setText(zaznaczonaOsoba.get(1));
        textField2.setText(zaznaczonaOsoba.get(2));
        textField3.setText(zaznaczonaOsoba.get(3));
    }

    @FXML
    public void saveData() throws IOException {
        String s = "";
        String firstName = textField1.getText();
        String lastName = textField2.getText();
        String pesel = textField3.getText();
        firstName = bigFirstLetter(firstName);
        lastName = bigFirstLetter(lastName);

        if (isPeselCorrect(pesel)) {
            zaznaczonaOsoba.set(1, firstName);
            zaznaczonaOsoba.set(2, lastName);
            zaznaczonaOsoba.set(3, pesel);
        }
        for (int i = 0; i < zaznaczonaOsoba.size(); i++) {
            if (i != zaznaczonaOsoba.size() - 1) {
                s += zaznaczonaOsoba.get(i) + ";";
            } else {
                s += zaznaczonaOsoba.get(i);
            }
        }
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i).startsWith(zaznaczonaOsoba.get(0))) {
                data.remove(i);
            }
        }
        data.add(s);
        for (int i = 0; i < observableList.size(); i++) {
            if (observableList.get(i).getId().equals(zaznaczonaOsoba.get(0))) {
                observableList.remove(i);
            }
        }
        observableList.add(new Osoba(zaznaczonaOsoba.get(0), zaznaczonaOsoba.get(1), zaznaczonaOsoba.get(2), zaznaczonaOsoba.get(3), zaznaczonaOsoba.get(4), zaznaczonaOsoba.get(5)));
        updateFile();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();
    }
}

