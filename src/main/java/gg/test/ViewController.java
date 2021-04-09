package gg.test;

import com.merakianalytics.orianna.Orianna;
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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ViewController extends Main {

    private Region region;
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
    Circle circle1 = new Circle();
    @FXML
    Circle circle2 = new Circle();
    @FXML
    Label label1 = new Label();
    @FXML
    Label label2 = new Label();
    @FXML
    ImageView img1 = new ImageView();
    @FXML
    ImageView img2 = new ImageView();
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
    Pane settingsPane = new Pane();
    @FXML
    Button settingsButton = new Button();
    @FXML
    TextField keyField = new TextField();
    @FXML
    Button setKeyButton = new Button();

    

    @FXML
    private void masteryButtonAction(ActionEvent event) throws Exception {
        masteryPane.setVisible(false);
        topChampsPane.setVisible(false);
        settingsPane.setVisible(false);

        keyField.setVisible(false);
        setKeyButton.setVisible(false);

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
        regionSelectMWC.getSelectionModel().selectedIndexProperty().
                addListener((ov, value, newValue) -> region = regions[newValue.intValue()]);
    }

    @FXML
    public void topChampsButtonAction(ActionEvent event) throws Exception {
        masteryPane.setVisible(false);
        topChampsPane.setVisible(false);
        settingsPane.setVisible(false);

        keyField.setVisible(false);
        setKeyButton.setVisible(false);

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
        regionSelectTC.getSelectionModel().selectedIndexProperty().
                addListener((ov, value, newValue) -> region = regions[newValue.intValue()]);
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
        entries.sort((final Map.Entry<String, Integer> e0, final Map.Entry<String, Integer> e1) -> Integer.
                compare(e1.getValue(), e0.getValue()));

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
    
    public void showHome(boolean bool){
        masteryPane.setVisible(bool);
        topChampsPane.setVisible(bool);
        circle1.setVisible(bool);
        circle2.setVisible(bool);
        img1.setVisible(bool);
        img2.setVisible(bool);
        label1.setVisible(bool);
        label2.setVisible(bool);
        masteryButton.setVisible(bool);
        topChampsButton.setVisible(bool);
    }
    
    public void showSettings(boolean bool){
        settingsPane.setVisible(bool);
        keyField.setVisible(bool);
        setKeyButton.setVisible(bool);
    }
    
    public void showMWC(boolean bool){
        paneMWC.setVisible(bool);
        summonerMWC.setVisible(bool);
        champSelectMWC.setVisible(bool);
        regionSelectMWC.setVisible(bool);
        scrollPaneMWC.setVisible(bool);
        buttonMWC.setVisible(bool);
        textAreaMWC.setVisible(bool);
    }
    
    public void showTC(boolean bool){
        paneTC.setVisible(bool);
        summonerTC.setVisible(bool);
        regionSelectTC.setVisible(bool);
        scrollPaneTC.setVisible(bool);
        buttonTC.setVisible(bool);
        textAreaTC.setVisible(bool);
    }
    
    

    @FXML
    public void startMWC(ActionEvent actionEvent) throws Exception{
        textAreaMWC.clear();
        masteryWithChamp(summonerMWC.getText(), regionSelectMWC.getSelectionModel().getSelectedIndex(), champSelectMWC.getText());
    }

    @FXML
    public void startTC(ActionEvent actionEvent) throws Exception {
        textAreaTC.clear();
        mainChamps(summonerTC.getText(), regionSelectTC.getSelectionModel().getSelectedIndex());
        mostplayedChamps(summonerTC.getText(), regionSelectTC.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void goHome(ActionEvent actionEvent) {
        masteryPane.setVisible(true);
        topChampsPane.setVisible(true);
        settingsPane.setVisible(false);

        keyField.setVisible(false);
        setKeyButton.setVisible(false);

        circle1.setVisible(true);
        circle2.setVisible(true);
        img1.setVisible(true);
        img2.setVisible(true);
        label1.setVisible(true);
        label2.setVisible(true);
        masteryButton.setVisible(true);
        topChampsButton.setVisible(true);

        paneMWC.setVisible(false);
        summonerMWC.setVisible(false);
        champSelectMWC.setVisible(false);
        regionSelectMWC.setVisible(false);
        scrollPaneMWC.setVisible(false);
        buttonMWC.setVisible(false);
        textAreaMWC.setVisible(false);

        paneTC.setVisible(false);
        summonerTC.setVisible(false);
        regionSelectTC.setVisible(false);
        scrollPaneTC.setVisible(false);
        buttonTC.setVisible(false);
        textAreaTC.setVisible(false);
    }

    @FXML
    public void goQuit(ActionEvent actionEvent) {
        System.exit(0);
    }

    @FXML
    public void goSettings(ActionEvent actionEvent) {
        masteryPane.setVisible(false);
        topChampsPane.setVisible(false);
        settingsPane.setVisible(true);

        keyField.setVisible(true);
        setKeyButton.setVisible(true);

        paneMWC.setVisible(false);
        summonerMWC.setVisible(false);
        champSelectMWC.setVisible(false);
        regionSelectMWC.setVisible(false);
        scrollPaneMWC.setVisible(false);
        buttonMWC.setVisible(false);
        textAreaMWC.setVisible(false);

        paneTC.setVisible(false);
        summonerTC.setVisible(false);
        regionSelectTC.setVisible(false);
        scrollPaneTC.setVisible(false);
        buttonTC.setVisible(false);
        textAreaTC.setVisible(false);
    }

    @FXML
    public void setKey(ActionEvent actionEvent) {
        //RGAPI-51378e00-14e7-4135-ac67-758f90850d12
        String key = keyField.getText();
        Orianna.setRiotAPIKey(key);
        keyField.clear();

        masteryPane.setVisible(true);
        topChampsPane.setVisible(true);
        settingsPane.setVisible(false);

        keyField.setVisible(false);
        setKeyButton.setVisible(false);

        circle1.setVisible(true);
        circle2.setVisible(true);
        img1.setVisible(true);
        img2.setVisible(true);
        label1.setVisible(true);
        label2.setVisible(true);
        masteryButton.setVisible(true);
        topChampsButton.setVisible(true);

        paneMWC.setVisible(false);
        summonerMWC.setVisible(false);
        champSelectMWC.setVisible(false);
        regionSelectMWC.setVisible(false);
        scrollPaneMWC.setVisible(false);
        buttonMWC.setVisible(false);
        textAreaMWC.setVisible(false);

        paneTC.setVisible(false);
        summonerTC.setVisible(false);
        regionSelectTC.setVisible(false);
        scrollPaneTC.setVisible(false);
        buttonTC.setVisible(false);
        textAreaTC.setVisible(false);
    }
}
