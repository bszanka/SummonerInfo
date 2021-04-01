package gg.test;

import com.merakianalytics.orianna.types.common.Region;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
//    final Region[] regions = new Region[]{EUROPE_NORTH_EAST, EUROPE_WEST};

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
    TextField summonerMWC;
    @FXML
    TextField champSelectMWC;
    @FXML
    ChoiceBox regionSelectMWC = new ChoiceBox();
    @FXML
    ScrollPane scrollPaneMWC;
    @FXML
    Button buttonMWC;

    @FXML
    private void masteryButtonAction(ActionEvent event) throws Exception {
        masteryPane.setVisible(false);
        topChampsPane.setVisible(false);
        summonerMWC.setVisible(true);
        champSelectMWC.setVisible(true);
        regionSelectMWC.setVisible(true);
        scrollPaneMWC.setVisible(true);
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

    @FXML
    private void startMWC(ActionEvent event) throws Exception{
        masteryWithChamp(summonerMWC.getText(), Region.EUROPE_NORTH_EAST, champSelectMWC.getText());
    }

}
