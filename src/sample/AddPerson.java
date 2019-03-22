package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;
import static sample.Controller.observableList;

public class AddPerson {

    private String data = "";
    private String nameOfPhoto = "";
    private int maxValue;

    @FXML
    private TextField wprowadzImie;
    @FXML
    private TextField wprowadzNazwisko;
    @FXML
    private TextField wprowadzPesel;
    @FXML
    private TextArea warning;

    static String bigFirstLetter(String z) {
        return Character.toUpperCase(z.charAt(0)) + z.substring(1).toLowerCase();
    }

    static boolean isPeselCorrect(String z) {
        int control = 0;
        char[] pesel = new char[11];
        if (z.length() == 11) {
            pesel = z.toCharArray();
            control = 9 * Integer.valueOf(String.valueOf(pesel[0])) +
                    7 * Integer.valueOf(String.valueOf(pesel[1])) +
                    3 * Integer.valueOf(String.valueOf(pesel[2])) +
                    Integer.valueOf(String.valueOf(pesel[3])) +
                    9 * Integer.valueOf(String.valueOf(pesel[4])) +
                    7 * Integer.valueOf(String.valueOf(pesel[5])) +
                    3 * Integer.valueOf(String.valueOf(pesel[6])) +
                    Integer.valueOf(String.valueOf(pesel[7])) +
                    9 * Integer.valueOf(String.valueOf(pesel[8])) +
                    7 * Integer.valueOf(String.valueOf(pesel[9]));
            if (control % 10 == Integer.valueOf(String.valueOf(pesel[10])))
                return true;
            else
                return false;
        } else
            return false;
    }

    @FXML
    public void dodaj() throws IOException {
        FXMLLoader.load(getClass().getResource("AddPerson.fxml"));
        String s = "";
        String firstName = wprowadzImie.getText();
        String lastName = wprowadzNazwisko.getText();
        String pesel = wprowadzPesel.getText();


        if (firstName.isBlank() != true && lastName.isBlank() != true && pesel.isBlank() != true && isPeselCorrect(pesel)) {
            birthsdayDate(pesel);
            firstName = bigFirstLetter(firstName);
            lastName = bigFirstLetter(lastName);
            s = getId() + ";" + firstName + ";" + lastName + ";" + wprowadzPesel.getText() + ";" + data + ";" + nameOfPhoto;
            observableList.add(new Osoba(getId(), firstName, lastName, wprowadzPesel.getText(), data, nameOfPhoto));
            FileWriter fw = new FileWriter("osoby.csv", true);
            fw.write(s);
            fw.append('\n');
            fw.close();
            clear();
            warning.clear();
        } else {
            warning.setText("Niepełne lub błędne dane, Popraw!");
        }

    }

    @FXML
    public void clear() throws IOException {
        FXMLLoader.load(getClass().getResource("AddPerson.fxml"));
        wprowadzImie.clear();
        wprowadzNazwisko.clear();
        wprowadzPesel.clear();
    }

    public void birthsdayDate(String pesel) {
        StringBuilder sb = new StringBuilder();
        pesel = wprowadzPesel.getText();
        System.out.println(pesel);
        if (pesel.charAt(2) == '0' || pesel.charAt(2) == '1') {
            sb.append(19 + pesel.substring(0, 2));
            sb.append('-');
        } else if (pesel.charAt(2) == '2' || pesel.charAt(2) == '3') {
            sb.append(20 + pesel.substring(0, 2));
            sb.append('-');
        } else if (pesel.charAt(2) == '4' || pesel.charAt(2) == '5') {
            sb.append(21 + pesel.substring(0, 2));
            sb.append('-');
        } else if (pesel.charAt(2) == '6' || pesel.charAt(2) == '7') {
            sb.append(22 + pesel.substring(0, 2));
            sb.append('-');
        } else if (pesel.charAt(2) == '8' || pesel.charAt(2) == '9') {
            sb.append(18 + pesel.substring(0, 2));
            sb.append('-');
        }
        System.out.println(pesel.substring(0, 2));
        sb.append(pesel.substring(2, 4));
        sb.append('-');
        sb.append(pesel.substring(4, 6));
        data = sb.toString();
        System.out.println(data);
    }

    @FXML
    public String getPhotoName(ActionEvent event) throws IOException {

        Path source;
        Path target;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Otwórz plik");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
        );
        File file = fileChooser.showOpenDialog(new Stage());
        nameOfPhoto = getId() + ".jpg";
        source = Paths.get(file.getPath());
        File dir = new File("grafika");
        target = Paths.get(dir.getAbsolutePath().substring(0, dir.getAbsolutePath().length() - 7) + "/src/sample/grafika/" + nameOfPhoto);
        Files.copy(source, target, REPLACE_EXISTING);
        return nameOfPhoto;
    }

    public String getId() throws FileNotFoundException {
        FileReader fileReader = new FileReader("osoby.csv");
        Scanner scanner = new Scanner(fileReader);
        String string = "";
        List<Integer> integerList = new ArrayList<>();
        while (scanner.hasNextLine()) {
            String wers = scanner.nextLine();
            for (int i = 0; i < wers.length(); i++) {
                if (Character.isDigit(wers.charAt(i))) {
                    string += wers.charAt(i);
                    if (wers.charAt(i + 1) == ';') {
                        break;
                    }
                }
            }
            if (string.equals("") == false) {
                integerList.add(Integer.valueOf(string));
            }
            string = "";
        }
        int maxValue = Collections.max(integerList);
        return String.valueOf(maxValue + 1);
    }
}
