package gg.test;

import com.merakianalytics.orianna.types.common.Region;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;


public class Main extends Application {

    static final Region
            EUNE = Region.EUROPE_NORTH_EAST,
            EUW = Region.EUROPE_WEST,
            NA = Region.NORTH_AMERICA,
            KR = Region.KOREA;

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View.fxml"));
        Scene scene = new Scene(root);
        root.getStylesheets().add("fullpackstyling.css");
        stage.getIcons().add(new Image("lol_icon.png"));
        stage.setTitle("Summoner Info");
        stage.setResizable(false);
        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(final String[] args) {
        // Logolás bekapcsolása:
        BasicConfigurator.configure();
        launch(args);
    }
}