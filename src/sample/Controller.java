package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

import static sample.Main.prop;

public class Controller implements Initializable {

    static List<String> data = new ArrayList<>();
    static List<String> markedPerson = new ArrayList<>();
    static ObservableList<Osoba> observableList = null;
    static String s = "";
    private Image image;
    @FXML
    private TableView<Osoba> myTable;
    @FXML
    private TableColumn<Osoba, String> id;
    @FXML
    private TableColumn<Osoba, String> firstName;
    @FXML
    private TableColumn<Osoba, String> lastName;
    @FXML
    private TableColumn<Osoba, String> pesel;
    @FXML
    private TableColumn<Osoba, String> pesel1;
    @FXML
    private TableColumn<Osoba, String> photo;
    @FXML
    private ImageView imageView;
    private Osoba rowToDelete;
    private Stage stage1 = null;

    static void updateFile() throws IOException {
        File temp = new File("osoby.csv");
        Files.delete(Path.of(temp.getAbsolutePath()));
        for (String text : data
        ) {
            FileWriter fileWriter = new FileWriter("osoby.csv", true);
            fileWriter.write(text);
            fileWriter.append('\n');
            fileWriter.close();
        }
    }

    @FXML
    public void addPerson() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("AddPerson.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Dodawanie nowej osoby");
        stage.setScene(new Scene(root, 600, 200));
        stage.show();
    }

    @FXML
    public void edit() throws IOException {
        rowToDelete = myTable.getSelectionModel().getSelectedItem();
        markedPerson.clear();
        markedPerson.add(rowToDelete.getId());
        markedPerson.add(rowToDelete.getImie());
        markedPerson.add(rowToDelete.getNazwisko());
        markedPerson.add(rowToDelete.getPpesel());
        markedPerson.add(rowToDelete.getData());
        markedPerson.add(rowToDelete.getPhotoName());
        Stage stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("edit.fxml"));
        stage.setTitle("Edycja");
        stage.setScene(new Scene(root, 600, 200));
        stage.show();
    }

    @FXML
    public void deleteButton() throws IOException {
        rowToDelete = myTable.getSelectionModel().getSelectedItem();
        StringBuilder sb = new StringBuilder();
        sb.append(rowToDelete.getId()).append(";").append(rowToDelete.getImie()).append(";").append(rowToDelete.getNazwisko()).append(";").append(rowToDelete.getPpesel()).append(";").append(rowToDelete.getData()).append(";").append(rowToDelete.getPhotoName());
        String delete = sb.toString();
        observableList.remove(delete);
        data.remove(delete);
        if (!rowToDelete.getPhotoName().equals("0.jpg")) {
            Files.delete(Paths.get(prop.get("absolutePath") + rowToDelete.getPhotoName()));
        }
        updateFile();
        myTable.getItems().removeAll(myTable.getSelectionModel().getSelectedItem());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            FileReader fileReader = new FileReader("osoby.csv");
            Scanner scanner = new Scanner(fileReader);
            while (scanner.hasNextLine()) {
                data.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        observableList = FXCollections.observableArrayList();
        for (String text : data
        ) {
            String[] dejta = text.split(";");
            if (dejta.length > 1) {
                Osoba osoba = new Osoba(dejta[0], dejta[1], dejta[2], dejta[3], dejta[4], dejta[5]);
                observableList.add(osoba);
            }
        }
        myTable.itemsProperty().setValue(observableList);

        id.setCellValueFactory(
                new PropertyValueFactory<>("ajdi")
        );
        firstName.setCellValueFactory(
                new PropertyValueFactory<>("imie")
        );
        lastName.setCellValueFactory(
                new PropertyValueFactory<>("nazwisko")
        );
        pesel.setCellValueFactory(
                new PropertyValueFactory<>("ppesel")
        );
        pesel1.setCellValueFactory(
                new PropertyValueFactory<>("data")
        );
        photo.setCellValueFactory(
                new PropertyValueFactory<>("photoname")
        );
        setImage();
    }

    @FXML
    public void setImage() {

        Osoba markedPerson = myTable.getSelectionModel().getSelectedItem();
        File file;
        if (markedPerson != null) {
            file = new File(prop.getProperty("absolutePath") + markedPerson.getPhotoName());
            s = file.toURI().toString();
        } else {
            file = new File(prop.getProperty("absolutePath") + "0.jpg");
            s = file.toURI().toString();
        }
        image = new Image(s, true);
        imageView.setImage(image);
    }

    @FXML
    public void onZoom() throws IOException {
        stage1 = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("Zoom.fxml"));
        stage1.initStyle(StageStyle.UNDECORATED);
        stage1.setScene(new Scene(root));
        stage1.alwaysOnTopProperty();
        stage1.show();
    }

    @FXML
    public void stopZoom() {
        stage1.close();
    }
}

