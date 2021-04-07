package gg.test;

import com.merakianalytics.orianna.Orianna;
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
        stage.getIcons().add(new Image("/logo.png"));
        stage.setTitle("Summoner Info");

        stage.setWidth(1200);
        stage.setHeight(800);
        stage.setScene(scene);
        stage.show();
    }

//    private static MatchHistory filterMatchHistory(final Summoner summoner) {
//        final MatchHistory matchHistory = MatchHistory.forSummoner(summoner).withQueues(Queue.RANKED).withSeasons(Season.getLatest()).get();
//        return matchHistory;
//    }

    public static void main(final String[] args) {
        // Orianna framework git: https://github.com/meraki-analytics/orianna
        // RIOT API kulcs beállítása alternatív módszer: https://orianna.readthedocs.io/en/latest/configuring-orianna.html
        // Logolás bekapcsolása:
        BasicConfigurator.configure();
        String key = "RGAPI-cb041752-e61f-4ebc-a8ad-9cb98104fdbf";
        Orianna.setRiotAPIKey(key);
        launch(args);
    }
}