package gg.test;

import com.merakianalytics.orianna.datapipeline.riotapi.exceptions.ForbiddenException;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    TextArea textAreaMWC = new TextArea();
    @FXML
    TextField summonerTC = new TextField();
    @FXML
    ChoiceBox regionSelectTC = new ChoiceBox();
    @FXML
    ScrollPane scrollPaneTC = new ScrollPane();
    @FXML
    Button buttonTC = new Button();
    @FXML
    TextArea textAreaTC = new TextArea();
    @FXML
    Pane paneMWC = new Pane();
    @FXML
    Pane paneTC = new Pane();

    @FXML
    private void masteryButtonAction(ActionEvent event) throws Exception {
        masteryPane.setVisible(false);
        topChampsPane.setVisible(false);

        paneMWC.setVisible(true);
        summonerMWC.setVisible(true);
        champSelectMWC.setVisible(true);
        regionSelectMWC.setVisible(true);
        scrollPaneMWC.setVisible(true);
        buttonMWC.setVisible(true);
        textAreaMWC.setVisible(true);

        paneTC.setVisible(false);
        summonerTC.setVisible(false);
        regionSelectTC.setVisible(false);
        scrollPaneTC.setVisible(false);
        buttonTC.setVisible(false);
        textAreaTC.setVisible(false);

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

        paneMWC.setVisible(false);
        summonerMWC.setVisible(false);
        champSelectMWC.setVisible(false);
        regionSelectMWC.setVisible(false);
        scrollPaneMWC.setVisible(false);
        textAreaMWC.setVisible(false);

        paneTC.setVisible(true);
        summonerTC.setVisible(true);
        regionSelectTC.setVisible(true);
        scrollPaneTC.setVisible(true);
        buttonTC.setVisible(true);
        textAreaTC.setVisible(true);


        regionSelectTC.setItems(regionsString);
        regionSelectTC.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue ov, Number value, Number newValue) {
                region = regions[newValue.intValue()];
            }
        });
    }


    public void mainChamps(String name, int regionIndex) throws ForbiddenException {
        try {
            final Region selectedRegion = regions[regionIndex];
            final Summoner summoner = Summoner.named(name).withRegion(selectedRegion).get();
            final ChampionMasteries cms = summoner.getChampionMasteries();
            textAreaTC.appendText(summoner.getName() + " has mastery level 6 or higher on:");
            final SearchableList<ChampionMastery> pro = cms.filter((final ChampionMastery mastery) -> mastery.getLevel() >= 6);
            for (final ChampionMastery mastery : pro) {
                textAreaTC.appendText("\n" + mastery.getChampion().getName());
            }
        } catch (ForbiddenException f){
            textAreaTC.appendText("Hozzáférés megtagadva!\n" + f.getMessage());
        }
    }

    public void mostplayedChamps(String name, int regionIndex) {
        final Region selectedRegion = regions[regionIndex];
        final Summoner summoner = Summoner.named(name).withRegion(selectedRegion).get();
        final MatchHistory matchHistory = MatchHistory.forSummoner(summoner).get();
        //final MatchHistory matchHistory = MatchHistory.forSummoner(summoner).withQueues([Queue.RANKED_SOLO_5x5]).withSeasons([Season.SEASON_7]).get();
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
        textAreaTC.appendText("\nLength of match history: " + matchHistory.size());

        // Top 10 champ
        final List<Map.Entry<String, Integer>> entries = new ArrayList<>(playedChampions.entrySet());
        entries.sort((final Map.Entry<String, Integer> e0, final Map.Entry<String, Integer> e1) -> Integer.compare(e1.getValue(), e0.getValue()));

        for(int i = 0; i < 10 && i < entries.size(); i++) {
            final String championName = entries.get(i).getKey();
            final int count = entries.get(i).getValue();
            textAreaTC.appendText("\n" + championName + " " + count);
        }
    }

    public void masteryWithChamp(String name, int regionIndex, String champ) throws ForbiddenException {
        try {
            final Region selectedRegion = regions[regionIndex];
            final Summoner summoner = Summoner.named(name).withRegion(selectedRegion).get();
            final Champion champion = Champion.named(champ).withRegion(selectedRegion).get();
            final ChampionMastery cm = summoner.getChampionMastery(champion);
            textAreaMWC.appendText("Mastery points: " + cm.getPoints());
            textAreaMWC.appendText("\nMastery level: " + cm.getLevel());
            textAreaMWC.appendText("\nPoints until next level: " + cm.getPointsUntilNextLevel());
        } catch (ForbiddenException f) {
            textAreaMWC.appendText("Hozzáférés megtagadva!\n" + f.getMessage());
        }
    }

    @FXML
    private void startMWC(ActionEvent actionEvent) throws Exception{
        textAreaMWC.clear();
        masteryWithChamp(summonerMWC.getText(), regionSelectMWC.getSelectionModel().getSelectedIndex(), champSelectMWC.getText());
    }

    public void startTC(ActionEvent actionEvent) throws Exception {
        textAreaTC.clear();
        mainChamps(summonerTC.getText(), regionSelectTC.getSelectionModel().getSelectedIndex());
        mostplayedChamps(summonerTC.getText(), regionSelectTC.getSelectionModel().getSelectedIndex());
    }
}
