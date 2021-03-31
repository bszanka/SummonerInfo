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

    public static void masteryWithChamp(String name, Region region, String champ){
        final Summoner summoner = Summoner.named(name).withRegion(region).get();
        final Champion champion = Champion.named(champ).withRegion(region).get();
        final ChampionMastery cm = summoner.getChampionMastery(champion);
//        System.out.println("Champion ID: " + cm.getChampion().getId());
        System.out.println("Mastery points: " + cm.getPoints());
        System.out.println("Mastery level: " + cm.getLevel());
        System.out.println("Points until next level: " + cm.getPointsUntilNextLevel());

        // ChampionMasteries cms = ChampionMasteries.forSummoner(summoner).get();
//        System.out.println(cms.get(3).getPoints());
//        System.out.println(cms.find(champion.getName()).getPoints());


    }

    public static void mainChamps(String name, Region region){
        final Summoner summoner = Summoner.named(name).withRegion(region).get();
        final ChampionMasteries cms = summoner.getChampionMasteries();
        System.out.println(summoner.getName() + " has mastery level 6 or higher on:");
        final SearchableList<ChampionMastery> pro = cms.filter((final ChampionMastery mastery) -> mastery.getLevel() >= 6);
        for(final ChampionMastery mastery : pro) {
            System.out.println(mastery.getChampion().getName());
        }
    }

    public static void mostplayedChamps(String name, Region region) {
        final Summoner summoner = Summoner.named(name).withRegion(region).get();
        final MatchHistory matchHistory = MatchHistory.forSummoner(summoner).get();
        // MatchHistory match_history = MatchHistory.forSummoner(summoner).withQueues([Queue.RANKED_SOLO_5x5]).withSeasons([Season.SEASON_7]).get();

        // Load the entire match history by iterating over all its elements so that we know how long it is.
        // Unfortunately since we are iterating over the match history and accessing the summoner's champion for each match,
        // we need to know what version of the static data the champion should have. To avoid pulling many different
        // static data versions, we will instead create a {champion_id -> champion_name} mapping and just access the champion's
        // ID from the match data (which it provides directly).
        final Map<Integer, String> championIdToNameMapping = new HashMap<>();
        for(final Champion champion : Champions.withRegion(region).get()) {
            championIdToNameMapping.put(champion.getId(), champion.getName());
        }
        final Map<String, Integer> playedChampions = new HashMap<>();
        for(final Match match : matchHistory) {
            final Integer championId = match.getParticipants().find(summoner).getChampion().getId();
            final String championName = championIdToNameMapping.get(championId);
            Integer count = playedChampions.get(championName);
            if(count == null) {
                count = 0;
                playedChampions.put(championName, count);
            }
            playedChampions.put(championName, playedChampions.get(championName) + 1);
        }
        System.out.println("Length of match history: " + matchHistory.size());

        // Top 10 champ
        final List<Entry<String, Integer>> entries = new ArrayList<>(playedChampions.entrySet());
        entries.sort((final Entry<String, Integer> e0, final Entry<String, Integer> e1) -> Integer.compare(e1.getValue(), e0.getValue()));

        for(int i = 0; i < 10 && i < entries.size(); i++) {
            final String championName = entries.get(i).getKey();
            final int count = entries.get(i).getValue();
            System.out.println(championName + " " + count);
        }
    }

    public static void main(final String[] args) {
        // Orianna framework git: https://github.com/meraki-analytics/orianna
        // RIOT API kulcs beállítása alternatív módszer: https://orianna.readthedocs.io/en/latest/configuring-orianna.html
        launch(args);
        // Logolás bekapcsolása:
        BasicConfigurator.configure();
        String key = "RGAPI-760c5258-a109-419d-9a9d-5734e13b3189";
        Orianna.setRiotAPIKey(key);
//        String name = "balazs337";
//        String champ = "Varus";
//        Region region = Region.EUROPE_NORTH_EAST;
//        final Summoner summoner = Summoner.named(name).withRegion(region).get();

    }
}