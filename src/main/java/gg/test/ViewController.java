package gg.test;

import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery;
import com.merakianalytics.orianna.types.core.match.Match;
import com.merakianalytics.orianna.types.core.match.MatchHistory;
import com.merakianalytics.orianna.types.core.searchable.SearchableList;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
import com.merakianalytics.orianna.types.core.staticdata.Champions;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ArrayChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;

import java.util.*;

import static com.merakianalytics.orianna.types.common.Region.*;

public class ViewController extends Main {

    private String name;
    private Region region;
    private String champ;

    final static Region[] regions = new Region[]{EUNE, EUW, NA, KR};
    ObservableList<String> regionsString = FXCollections.observableArrayList("EUNE", "EUW", "NA", "KR");

    @FXML
    AnchorPane mainAnchorPane = new AnchorPane();
    @FXML
    AnchorPane mainAnchorPane2 = new AnchorPane();
    @FXML
    Button masteryButton = new Button();
    @FXML
    Pane  masteryPane = new Pane();
    @FXML
    Button topChampsButton = new Button();
    @FXML
    Pane  topChampsPane = new Pane();
    @FXML
    TextField summonerMWC = new TextField();
    @FXML
    TextField champSelectMWC = new TextField();
    @FXML
    ChoiceBox regionSelectMWC = new ChoiceBox();
    @FXML
    ScrollPane scrollPaneMWC = new ScrollPane();
    @FXML
    Button buttonMWC = new Button();
    @FXML
    static TextArea textAreaMWC = new TextArea();

    @FXML
    private void masteryButtonAction(ActionEvent event) throws Exception {
        masteryPane.setVisible(false);
        topChampsPane.setVisible(false);
        summonerMWC.setVisible(true);
        champSelectMWC.setVisible(true);
        regionSelectMWC.setVisible(true);
        scrollPaneMWC.setVisible(true);
        buttonMWC.setVisible(true);

        regionSelectMWC.setItems(regionsString);
        regionSelectMWC.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number newValue) {
                region = regions[newValue.intValue()];
            }
        });
    }

    @FXML
    private void topChampsButtonAction(ActionEvent event) throws Exception {
        masteryPane.setVisible(false);
        topChampsPane.setVisible(false);
        summonerMWC.setVisible(false);
        champSelectMWC.setVisible(false);
        regionSelectMWC.setVisible(false);
        scrollPaneMWC.setVisible(false);
    }


    public static void mainChamps(String name, int regionIndex){
        final Region selectedRegion = regions[regionIndex];
        final Summoner summoner = Summoner.named(name).withRegion(selectedRegion).get();
        final ChampionMasteries cms = summoner.getChampionMasteries();
        System.out.println(summoner.getName() + " has mastery level 6 or higher on:");
        final SearchableList<ChampionMastery> pro = cms.filter((final ChampionMastery mastery) -> mastery.getLevel() >= 6);
        for(final ChampionMastery mastery : pro) {
            System.out.println(mastery.getChampion().getName());
        }
    }

    public static void mostplayedChamps(String name, int regionIndex) {
        final Region selectedRegion = regions[regionIndex];
        final Summoner summoner = Summoner.named(name).withRegion(selectedRegion).get();
        final MatchHistory matchHistory = MatchHistory.forSummoner(summoner).get();
        // MatchHistory match_history = MatchHistory.forSummoner(summoner).withQueues([Queue.RANKED_SOLO_5x5]).withSeasons([Season.SEASON_7]).get();

        // Load the entire match history by iterating over all its elements so that we know how long it is.
        // Unfortunately since we are iterating over the match history and accessing the summoner's champion for each match,
        // we need to know what version of the static data the champion should have. To avoid pulling many different
        // static data versions, we will instead create a {champion_id -> champion_name} mapping and just access the champion's
        // ID from the match data (which it provides directly).
        final Map<Integer, String> championIdToNameMapping = new HashMap<>();
        for(final Champion champion : Champions.withRegion(selectedRegion).get()) {
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
        final List<Map.Entry<String, Integer>> entries = new ArrayList<>(playedChampions.entrySet());
        entries.sort((final Map.Entry<String, Integer> e0, final Map.Entry<String, Integer> e1) -> Integer.compare(e1.getValue(), e0.getValue()));

        for(int i = 0; i < 10 && i < entries.size(); i++) {
            final String championName = entries.get(i).getKey();
            final int count = entries.get(i).getValue();
            System.out.println(championName + " " + count);
        }
    }

    public static void masteryWithChamp(String name, int regionIndex, String champ){
        final Region selectedRegion = regions[regionIndex];
        final Summoner summoner = Summoner.named(name).withRegion(selectedRegion).get();
        final Champion champion = Champion.named(champ).withRegion(selectedRegion).get();
        final ChampionMastery cm = summoner.getChampionMastery(champion);
//        System.out.println("Champion ID: " + cm.getChampion().getId());
        textAreaMWC.appendText("Mastery points: " + cm.getPoints());
        textAreaMWC.appendText("\nMastery level: " + cm.getLevel());
        textAreaMWC.appendText("\nPoints until next level: " + cm.getPointsUntilNextLevel());

        // ChampionMasteries cms = ChampionMasteries.forSummoner(summoner).get();
//        System.out.println(cms.get(3).getPoints());
//        System.out.println(cms.find(champion.getName()).getPoints());


    }

    @FXML
    private void startMWC(ActionEvent event) throws Exception{
        masteryWithChamp(summonerMWC.getText(), regionSelectMWC.getSelectionModel().getSelectedIndex(), champSelectMWC.getText());
    }

}
