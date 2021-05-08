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
import org.apache.log4j.BasicConfigurator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ViewControllerTest {

    private final Region region = Region.EUROPE_NORTH_EAST;
    private final String name = "balazs337";
    private final String champ = "Varus";

    @BeforeEach
    void setUp() {
        BasicConfigurator.configure();
        // Enter your Riot API key before testing:
        String key = "RGAPI-cf14e834-76ab-45f4-b671-e4c4fca10a52";
        Orianna.setRiotAPIKey(key);
    }

    @Test
    void testMasteryWithChamp() {
        final Region selectedRegion = region;
        final Summoner summoner = Summoner.named(name).withRegion(selectedRegion).get();
        final Champion champion = Champion.named(champ).withRegion(selectedRegion).get();
        final ChampionMastery cm = summoner.getChampionMastery(champion);

        assertNotNull(selectedRegion);
        assertNotNull(summoner);
        assertNotNull(champion);
        assertNotEquals(0, cm.getPoints());
        assertNotEquals(0, cm.getLevel());
        // pointsUntilNextLevel should not be tested, if you mastered the champion.
        // assertNotEquals(0, cm.getPointsUntilNextLevel());
    }

    @Test
    void testMainChamps() {
        final Region selectedRegion = region;
        final Summoner summoner = Summoner.named(name).withRegion(selectedRegion).get();
        final ChampionMasteries cms = summoner.getChampionMasteries();
        final SearchableList<ChampionMastery> pro = cms.filter((final ChampionMastery mastery) -> mastery.getLevel() >= 6);

        assertNotEquals(0, pro.size());
    }

    @Test
    void testMostPlayedChamps() {
        final Region selectedRegion = region;
        final Summoner summoner = Summoner.named(name).withRegion(selectedRegion).get();
        final MatchHistory matchHistory = MatchHistory.forSummoner(summoner).get();
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
        final List<Map.Entry<String, Integer>> entries = new ArrayList<>(playedChampions.entrySet());
        entries.sort((final Map.Entry<String, Integer> e0, final Map.Entry<String, Integer> e1) -> Integer.
                compare(e1.getValue(), e0.getValue()));

        for(int i = 0; i < 10 && i < entries.size(); i++) {
            final String championName = entries.get(i).getKey();
            final int count = entries.get(i).getValue();
        }

        assertNotEquals(0, playedChampions.size());
        assertNotEquals(0, entries.size());
    }
}