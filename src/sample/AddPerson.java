package sample;

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
import static sample.Main.prop;

public class AddPerson {

    private String data = "";
    private String nameOfPhoto = "";

    @FXML
    private TextField enterFirstName;
    @FXML
    private TextField enterSurname;
    @FXML
    private TextField enterPesel;
    @FXML
    private TextArea warning;

    static String bigFirstLetter(String z) {
        String delimeter = "";
        String zZ = "";
        if (z.contains(" ")) {
            delimeter = " ";
        }
        if (z.contains("-")) {
            delimeter = "-";
        }
        if (!delimeter.equals("")) {
            String[] delimetrZ = z.split(delimeter);
            for (String part : delimetrZ
            ) {
                zZ += Character.toUpperCase(part.charAt(0)) + part.substring(1).toLowerCase() + delimeter;
                System.out.println(zZ);
            }
            zZ = zZ.substring(0, zZ.length() - 1);
        } else {
            zZ = Character.toUpperCase(z.charAt(0)) + z.substring(1).toLowerCase();
        }
        return zZ;
    }

    static boolean isPeselCorrect(String z) {
        int control = 0;
        char[] pesel;
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
            return control % 10 == Integer.valueOf(String.valueOf(pesel[10]));
        } else
            return false;
    }

    @FXML
    public void add() throws IOException {
        FXMLLoader.load(getClass().getResource("AddPerson.fxml"));
        String s = "";
        String firstName = enterFirstName.getText();
        String lastName = enterSurname.getText();
        String pesel = enterPesel.getText();

        if (!firstName.isBlank() && !lastName.isBlank() && !pesel.isBlank() && isPeselCorrect(pesel)) {
            birthsdayDate(pesel);
            firstName = firstName.replace(";", "");
            lastName = lastName.replace(";", "");
            firstName = bigFirstLetter(firstName);
            lastName = bigFirstLetter(lastName);
            if (nameOfPhoto.equals("")) {
                nameOfPhoto = "0.jpg";
            }
            s = getId() + ";" + firstName + ";" + lastName + ";" + enterPesel.getText() + ";" + data + ";" + nameOfPhoto;
            observableList.add(new Osoba(getId(), firstName, lastName, enterPesel.getText(), data, nameOfPhoto));
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
        enterFirstName.clear();
        enterSurname.clear();
        enterPesel.clear();
    }

    public void birthsdayDate(String pesel) {
        StringBuilder sb = new StringBuilder();
        pesel = enterPesel.getText();
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
    public String getPhotoName() throws IOException {
        try {
            Path source;
            Path target;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Otwórz plik");
            fileChooser.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg")
            );
            File file = fileChooser.showOpenDialog(new Stage());
            if (file != null) {
                nameOfPhoto = getId() + ".jpg";
            } else {
                nameOfPhoto = "0.jpg";
            }
            source = Paths.get(file.getPath());
            target = Paths.get(prop.getProperty("absolutePath") + nameOfPhoto);
            Files.copy(source, target, REPLACE_EXISTING);
            return nameOfPhoto;
        } catch (NullPointerException exc) {
            return null;
        }
    }

    private String getId() throws FileNotFoundException {
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
            if (!string.equals("")) {
                integerList.add(Integer.valueOf(string));
            }
            string = "";
        }
        int maxValue = Collections.max(integerList);
        return String.valueOf(maxValue + 1);
    }
}
