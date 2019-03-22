package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;
public class Main extends Application {

    static String projectsName;
    static FileInputStream in;
    static Properties prop;
    @Override
    public void start(Stage primaryStage) throws Exception {
        prop = new Properties();
        in = new FileInputStream(setPath(new File("conf.properties")));
        prop.load(in);
//        projectsName = prop.getProperty("name");
//        setProjectName(projectsName);
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Lista osób");
        primaryStage.setScene(new Scene(root, 800, 500));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

    private String setPath(File file) {
        File f = new File(String.valueOf(file));
        return f.getAbsolutePath().substring(0,f.getAbsolutePath().length() - 16) + "/src/sample/conf.properties";
    }
}