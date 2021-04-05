package gg.test;

import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.merakianalytics.orianna.types.common.Region.*;

public class ViewController extends Main {

    private String name;
    private Region region;
    private String champ;

    final Region[] regions = new Region[]{EUNE, EUW, NA, KR};
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

    public void masteryWithChamp(String name, Region region, String champ){
        final Summoner summoner = Summoner.named(name).withRegion(region).get();
        final Champion champion = Champion.named(champ).withRegion(region).get();
        final ChampionMastery cm = summoner.getChampionMastery(champion);
//        System.out.println("Champion ID: " + cm.getChampion().getId());
        textAreaMWC.appendText("Mastery points: " + cm.getPoints());
        textAreaMWC.appendText("Mastery level: " + cm.getLevel());
        textAreaMWC.appendText("Points until next level: " + cm.getPointsUntilNextLevel());

        // ChampionMasteries cms = ChampionMasteries.forSummoner(summoner).get();
//        System.out.println(cms.get(3).getPoints());
//        System.out.println(cms.find(champion.getName()).getPoints());


    }

    @FXML
    private void startMWC(ActionEvent event) throws Exception{
        masteryWithChamp(summonerMWC.getText(), Region.EUROPE_NORTH_EAST, champSelectMWC.getText());
    }

}
