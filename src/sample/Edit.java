package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
        textField1.setText(markedPerson.get(1));
        textField2.setText(markedPerson.get(2));
        textField3.setText(markedPerson.get(3));
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
}

