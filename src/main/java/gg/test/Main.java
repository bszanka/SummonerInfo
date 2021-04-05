package gg.test;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery;
import com.merakianalytics.orianna.types.core.match.Match;
import com.merakianalytics.orianna.types.core.match.MatchHistory;
import com.merakianalytics.orianna.types.core.searchable.SearchableList;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
import com.merakianalytics.orianna.types.core.staticdata.Champions;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.log4j.BasicConfigurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.*;


public class Main extends Application {

    static final Region
            EUNE = Region.EUROPE_NORTH_EAST,
            EUW = Region.EUROPE_WEST,
            NA = Region.NORTH_AMERICA,
            KR = Region.KOREA;

    @Override
    public void start(Stage stage) throws Exception {
//        String url = FXMLLoader.load(getClass().getClassLoader().getResource("../../../java/gg.test/View.fxml")).toString();
//        System.out.println("DEBUG\n\n\n\n" + url + "\n\n\n\nDEBUG\n");
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("View.fxml"));
        //        Parent root = FXMLLoader.load(getClass().getResource("../../../java/gg.test/View.fxml"));
//        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View.fxml"));
//        ViewController controller = (ViewController) loader.getController();
//        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
//        stage.getIcons().add(new Image("/pics/nagyito2.png"));
        stage.setTitle("Summoner Info");

        stage.setWidth(993);
        stage.setHeight(616);
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
        String key = "RGAPI-64c419d0-a73c-4973-8001-c3052728d534";
        Orianna.setRiotAPIKey(key);
        launch(args);
//        String name = "balazs337";
//        String champ = "Varus";
//        Region region = Region.EUROPE_NORTH_EAST;
//        final Summoner summoner = Summoner.named(name).withRegion(region).get();

    }
}