package gg.test;

import com.merakianalytics.orianna.Orianna;
import com.merakianalytics.orianna.types.common.Queue;
import com.merakianalytics.orianna.types.common.Region;
import com.merakianalytics.orianna.types.common.Season;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMasteries;
import com.merakianalytics.orianna.types.core.championmastery.ChampionMastery;
import com.merakianalytics.orianna.types.core.match.Match;
import com.merakianalytics.orianna.types.core.match.MatchHistory;
import com.merakianalytics.orianna.types.core.match.Participant;
import com.merakianalytics.orianna.types.core.searchable.SearchableList;
import com.merakianalytics.orianna.types.core.staticdata.Champion;
import com.merakianalytics.orianna.types.core.staticdata.Champions;
import com.merakianalytics.orianna.types.core.summoner.Summoner;
import org.apache.log4j.BasicConfigurator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.*;


public class Main {

//    private static MatchHistory filterMatchHistory(final Summoner summoner) {
//        final MatchHistory matchHistory = MatchHistory.forSummoner(summoner).withQueues(Queue.RANKED).withSeasons(Season.getLatest()).get();
//        return matchHistory;
//    }


    public static void main(final String[] args) {
        // Orianna framework git: https://github.com/meraki-analytics/orianna
        // RIOT API kulcs beállítása alternatív módszer: https://orianna.readthedocs.io/en/latest/configuring-orianna.html
        // Logolás bekapcsolása:
        BasicConfigurator.configure();
        String key = "RGAPI-760c5258-a109-419d-9a9d-5734e13b3189";
        Orianna.setRiotAPIKey(key);
        String name = "balazs337";
        String champ = "Varus";
        Region region = Region.EUROPE_NORTH_EAST;
        final Summoner summoner = Summoner.named(name).withRegion(region).get();
//        final Champion champion = Champion.named(champ).withRegion(region).get();
//        final ChampionMastery cm = summoner.getChampionMastery(champion);
////        System.out.println("Champion ID: " + cm.getChampion().getId());
//        System.out.println("Mastery points: " + cm.getPoints());
//        System.out.println("Mastery level: " + cm.getLevel());
//        System.out.println("Points until next level: " + cm.getPointsUntilNextLevel());
//
//        // ChampionMasteries cms = ChampionMasteries.forSummoner(summoner).get();
//        final ChampionMasteries cms = summoner.getChampionMasteries();
////        System.out.println(cms.get(3).getPoints());
////        System.out.println(cms.find(champion.getName()).getPoints());
//
//        System.out.println(summoner.getName() + " has mastery level 6 or higher on:");
//        final SearchableList<ChampionMastery> pro = cms.filter((final ChampionMastery mastery) -> mastery.getLevel() >= 6);
//        for(final ChampionMastery mastery : pro) {
//            System.out.println(mastery.getChampion().getName());
//        }


//        // RATE LIMIT EXCEEDED :(((
//        final HashSet<String> unpulledAccountIds = new HashSet<>();
//        unpulledAccountIds.add(summoner.getAccountId());
//        final HashSet<String> pulledAccountIds = new HashSet<>();
//
//        final HashSet<Long> unpulledMatchIds = new HashSet<>();
//        final HashSet<Long> pulledMatchIds = new HashSet<>();
//
//        while(!unpulledAccountIds.isEmpty()) {
//            // Get a new summoner from our list of unpulled summoners and pull their match history
//            final String newAccountId = unpulledAccountIds.iterator().next();
//            final Summoner newSummoner = Summoner.withId(newAccountId).withRegion(region).get();
//            final MatchHistory matches = filterMatchHistory(newSummoner);
//            for(final Match match : matches) {
//                if(!pulledMatchIds.contains(match.getId())) {
//                    unpulledMatchIds.add(match.getId());
//                }
//            }
//            unpulledAccountIds.remove(newAccountId);
//            pulledAccountIds.add(newAccountId);
//
//            while(!unpulledMatchIds.isEmpty()) {
//                // Get a random match from our list of matches
//                final long newMatchId = unpulledMatchIds.iterator().next();
//                final Match newMatch = Match.withId(newMatchId).withRegion(region).get();
//                for(final Participant p : newMatch.getParticipants()) {
//                    if(!pulledAccountIds.contains(p.getSummoner().getAccountId()) && !unpulledAccountIds.contains(p.getSummoner().getAccountId())) {
//                        unpulledAccountIds.add(p.getSummoner().getId());
//                    }
//                }
//                // The above lines will trigger the match to load its data by iterating over all the participants.
//                // If you have a database in your datapipeline, the match will automatically be stored in it.
//                unpulledMatchIds.remove(newMatchId);
//                pulledMatchIds.add(newMatchId);
//            }
//        }



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

//        // Utolsó meccs
//        System.out.println("\n");
//
//        final Match match = matchHistory.get(0);
//        System.out.println("Match ID: " + match.getId());
//
//        final Participant participant = match.getParticipants().find(summoner);
//        System.out.println("\nSince the match was created from a matchref, we only know one participant:");
//        System.out.println(participant.getSummoner().getName() + " played " + participant.getChampion().getName());
//
//        System.out.println("\nNow pull the full match data by iterating over all the participants:");
//        for(final Participant p : match.getParticipants()) {
//            System.out.println(p.getSummoner().getName() + " played " + p.getChampion().getName());
//        }
//
//        System.out.println("\nIterate over all the participants again and note that the data is not repulled:");
//        for(final Participant p : match.getParticipants()) {
//            System.out.println(p.getSummoner().getName() + " played " + p.getChampion().getName());
//        }
//
//        System.out.println("\nBlue team won? " + match.getBlueTeam().isWinner());
//        System.out.println("Red team won? " + match.getRedTeam().isWinner());
//        System.out.println("Participants on blue team:");
//        for(final Participant p : match.getBlueTeam().getParticipants()) {
//            System.out.println(p.getSummoner().getName());
//        }
    }
}